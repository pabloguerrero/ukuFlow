/**
 * \addtogroup scopes
 * @{
 */

/*
 * Copyright (c) 2009, Steffen Kilb, TU Darmstadt
 * Copyright (c) 2009, Daniel Jacobi, TU Darmstadt
 * Copyright (c) 2012, Pablo Guerrero, TU Darmstadt, guerrero@dvs.tu-darmstadt.de
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

/**
 * \file	scopes.c
 * \brief	Main module of the Scopes framework
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#include <string.h>
#include <stdlib.h>

#include "contiki.h"
#include "contiki-lib.h"
#include "contiki-net.h"
#include "sys/ctimer.h"

#include "scopes-types.h"
#include "scopes-routing.h"
#include "scopes-membership.h"
#include "scopes-application.h"
#include "scopes.h"

#include "logger.h"

/* preprocessor macros */
/** \brief TODO */
#define CALLBACK(subscriber, name_args)                \
  do {                                                 \
    PROCESS_CONTEXT_BEGIN((subscriber)->process);      \
    (subscriber)->application->name_args;              \
    PROCESS_CONTEXT_END((subscriber)->process);        \
  } while(0)

/** \brief Checks if a scope is the child of another scope */
#define ARE_LINKED(super_scope, sub_scope)             \
  ((sub_scope)->super_scope_id == (super_scope)->scope_id)

//#define PRINT_SCOPE(x, scope)

/* static function declarations */
static struct scope *allocate_scope(data_len_t spec_len);
static void deallocate_scope(struct scope *scope);
static void add_scope(struct scope *scope);
static void remove_scope(struct scope *scope);
static void remove_scope_recursively(struct scope *scope);
static void remove_single_scope(struct scope *scope);
static void join_scope(struct scope *scope);
static void leave_scope(struct scope *scope);
static struct scope *lookup_scope_id(scope_id_t scope_id);
static void handle_scope_timeout(struct scope *scope);
static void reset_scope_timer(struct scope *scope);
static void announce_scope_open(struct scope *scope);
static void announce_scope_close(struct scope *scope);
//static void print_scope(struct scope *scope);
static struct scopes_subscriber *lookup_subscriber_id(subscriber_id_t id);

/* function declarations (functions used externally) */
void scopes_init(struct scopes_routing *routing,
		struct scopes_membership *membership);
void scopes_register(subscriber_id_t id, struct scopes_application *application);
void scopes_unregister(subscriber_id_t subscriber_id);
bool scopes_open(subscriber_id_t subscriber_id, scope_id_t super_scope,
		scope_id_t scope_id, void *specs, data_len_t spec_len,
		scope_flags_t flags, scope_ttl_t ttl);
void scopes_close(subscriber_id_t subscriber_id, scope_id_t scope_id);
void scopes_send(subscriber_id_t subscriber_id, scope_id_t scope_id,
		bool to_creator, void *data, data_len_t data_len);
void scopes_receive(struct scopes_msg_generic *gmsg);
/** \brief declaration of scopes process */
PROCESS(scopes_process, "scopes process");
/** \brief Static memory for scopes' information */
MEMB(scopes_mem, struct scope, SCOPES_MAX_SCOPES);
/** \brief Static memory for scopes' subscribers */
MEMB(subscribers_mem, struct scopes_subscriber, SCOPES_MAX_SUBSCRIBER);

/* data structures for scope and subscriber management */
/** \brief List of scope entries */
LIST(scopes);
/** \brief List of subscribing applications */
LIST(subscribers);

/** \brief Scopes routing parameterization component */
static struct scopes_routing *routing;
/** \brief Scopes membership function parameterization component */
static struct scopes_membership *membership;

/** \brief The world scope */
struct scope world_scope;

/** \brief Timer to delay scope announcement */
static struct ctimer anndelay_timer;

/* external function definitions */
/** \brief Initializer of the Scopes framework */
void scopes_init(struct scopes_routing *r, struct scopes_membership *m) {
	PRINTF(3, "(SCOPES) Initializing scopes...\n");

	/* initialize static memory for scopes structures */
	memb_init(&scopes_mem);
	memb_init(&subscribers_mem);

	/* initialize scopes list and subscriber list */
	list_init(scopes);
	list_init(subscribers);

	/* create world scope */
	world_scope.scope_id = SCOPES_WORLD_SCOPE_ID;
	world_scope.super_scope_id = SCOPES_WORLD_SCOPE_ID;
	world_scope.ttl = 0;
	world_scope.status = SCOPES_FLAG_STATUS_MEMBER;
	world_scope.flags = SCOPES_FLAG_NONE;

	/* add world scope */
	list_add(scopes, &world_scope);

	/* set and initialize routing */
	PRINTF(3, "(SCOPES) using routing: %s\n", r->name);
	routing = r;
	routing->init();

	/* set and initialize membership checker */
	PRINTF(3, "(SCOPES) using membership: %s\n", m->name);
	membership = m;
	membership->init();

	/* start scopes process */
	process_start(&scopes_process, NULL);
}

/** \brief TODO */
void scopes_register(subscriber_id_t id, struct scopes_application *application) {
	/* first make sure that the subscriber hasn't subscribed before and
	 that the id is unused */
	struct scopes_subscriber *s;
	for (s = list_head(subscribers); s != NULL; s = s->next) {
		if (s->id == id) {
			PRINTF(3, "(SCOPES) process is already subscribed to scopes\n");
			return;
		}
	}

	struct scopes_subscriber *subscriber =
			(struct scopes_subscriber *) memb_alloc(&subscribers_mem);

	if (subscriber == NULL) {
		PRINTF(3, "Could not allocate memory for subscriber %d.\n", id);
		return;
	}

	/* set id, status, application and process */
	subscriber->id = id;
	subscriber->status = SCOPES_APPLICATION_SUBSCRIBED;
	subscriber->application = application;
	subscriber->process = PROCESS_CURRENT();

	/* add the subscriber */
	list_add(subscribers, subscriber);
	PRINTF(5, "(SCOPES) added subscriber with sid=%u\n", subscriber->id);
}

/** \brief TODO */
void scopes_unregister(subscriber_id_t subscriber_id) {
	/* check if the super scope is known */
	struct scopes_subscriber *subscriber = lookup_subscriber_id(subscriber_id);
	if (subscriber == NULL) {
		PRINTF(3, "(SCOPES) subscriber does not exist: %u\n", subscriber_id);
		return;
	}

	/* remove the subscriber */
	list_remove(subscribers, subscriber);
	PRINTF(5, "(SCOPES) removed subscriber with sid=%u\n", subscriber->id);
	memb_free(&subscribers_mem, subscriber);
}

/**
 * \brief		Opens a scope.
 *
 * @param[in] subscriber_id		the id of the subscribing application, which will own the scope
 * @param[in] super_scope_id	the id of the scope that will be parent of the scope being opened
 * @param[in] scope_id 			the id of the scope to open
 * @param[in] specs				the byte array with the membership specification to be passed to the membership module
 * @param[in] spec_len			the length of the byte array with the membership specification
 * @param[in] flags				flags to be used with the scope
 * @param[in] ttl				the time to live for this scope
 * \return						TRUE, if the scope was opened successfully, or FALSE otherwise
 **/
bool scopes_open(subscriber_id_t subscriber_id, scope_id_t super_scope_id,
		scope_id_t scope_id, void *specs, data_len_t spec_len,
		scope_flags_t flags, scope_ttl_t ttl) {
	struct scopes_subscriber *subscriber = lookup_subscriber_id(subscriber_id);
	struct scope *super_scope = NULL;
	struct scope *scope;

	/* check if the application is subscribed */
	if (subscriber != NULL && !IS_SUBSCRIBED(subscriber)) {
		PRINTF(3, "(SCOPES) application not subscribed or not found\n");
		return (FALSE);
	}

	/* check if the scope id is already known */
	if ((scope = lookup_scope_id(scope_id)) != NULL) {
		PRINTF(3, "(SCOPES) increasing scope use counter to %u\n",
				scope->use_counter);
		scope->use_counter++;
		//		PRINTF(3,"scope id already in use: %u\n", scope_id);
		//		return FALSE;
		return (TRUE);
	}

	/* check if the super scope is known */
	super_scope = lookup_scope_id(super_scope_id);
	if (super_scope == NULL) {
		PRINTF(3, "(SCOPES) super scope does not exist: %u\n", super_scope_id);
		return (FALSE);
	}

	/* check if memory is available */
	scope = allocate_scope(spec_len);
	if (scope == NULL) {
		PRINTF(3, "(SCOPES) can't alloc memory for scope: %u\n", scope_id);
		return (FALSE);
	}

	/* fill in the scope information */
	scope->scope_id = scope_id;
	scope->super_scope_id = super_scope->scope_id;
	scope->owner = subscriber;
	scope->use_counter = 1;
	scope->ttl = ttl;
	scope->status = SCOPES_FLAG_STATUS_CREATOR;
	scope->flags = flags;
	scope->spec_len = spec_len;

	/* copy the specification */
	memcpy(scope->specs, specs, spec_len);

	/* add the scope to the scopes list */
	add_scope(scope);

	/* join the scope */
	join_scope(scope);

	/* set timer for the announcement of the creation of the scope on the network */
	ctimer_set(&(anndelay_timer), CLOCK_SECOND,
			(void (*)(void *)) announce_scope_open, scope);

	/* return TRUE, because scope was created correctly */
	return (TRUE);
}

/** \brief Returns whether this node is member of the scope with id passed as parameter */
bool scopes_member_of(scope_id_t scope_id) {
	struct scope *scope = lookup_scope_id(scope_id);
	return (HAS_STATUS(scope, SCOPES_FLAG_STATUS_MEMBER));
}

/** \brief Returns whether this node is creator (root) of the scope with id passed as parameter */
bool scopes_creator_of(scope_id_t scope_id) {
	struct scope *scope = lookup_scope_id(scope_id);
	return (HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR));
}

/** \brief Returns whether this node is forwarder of the scope with id passed as parameter */
bool scopes_forwarder_of(scope_id_t scope_id) {
	return (routing->has_status(scope_id, 0x2 /*SELFUR_STATUS_FORWARDER*/));
}

/** \brief Returns whether this node is forwarder of the scope with id passed as parameter */
uint8_t scopes_height_of(scope_id_t scope_id) {
	return (routing->node_distance(scope_id));
}

/** \brief TODO */
void scopes_close(subscriber_id_t subscriber_id, scope_id_t scope_id) {
	struct scopes_subscriber *subscriber = lookup_subscriber_id(subscriber_id);
	struct scope *scope = NULL;

	/* check if the application is subscribed */
	if (subscriber != NULL && !IS_SUBSCRIBED(subscriber)) {
		PRINTF(3, "(SCOPES) application not subscribed or not found\n");
		return;
	}

	/* check if the scope is known */
	scope = lookup_scope_id(scope_id);
	if (scope == NULL) {
		PRINTF(3, "(SCOPES) scope does not exist: %u\n", scope_id);
		return;
	}

	/* check if this scope may be deleted */
	if (!HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR)
			|| !IS_OWNER(scope, subscriber)) {
		PRINTF(3, "(SCOPES) not allowed to delete scope: %u\n",
				scope->scope_id);
		return;
	}

	//	PRINTF(3, "(SCOPES) count: %u\n", scope->use_counter);
	//	PRINTF(3, "(SCOPES) ttl: %u\n", scope->ttl);
	if (--scope->use_counter == 0) {

		/* announce the deletion of the scope on the network */
		announce_scope_close(scope);

		/* remove the scope from the scopes list */
		remove_scope(scope);
	}

}

/** \brief TODO */
void scopes_send(subscriber_id_t subscriber_id, scope_id_t scope_id,
		bool to_creator, void *data, data_len_t data_len) {
	struct scopes_subscriber *subscriber = lookup_subscriber_id(subscriber_id);
	struct scope *scope = NULL;

	PRINT_ARR(3, data, data_len);
	/* check if the application is subscribed */
	if (subscriber != NULL && !IS_SUBSCRIBED(subscriber)) {
		PRINTF(3, "(SCOPES) application not subscribed or found\n");
		return;
	}

	/* check if the scope is known */
	scope = lookup_scope_id(scope_id);
	if (scope == NULL) {
		PRINTF(3, "(SCOPES) scope does not exist: %u\n", scope_id);
		return;
	}

	/** The following check was removed in order to enable ukuFlow to send data messages
	 * towards the root regardless of whether the node is member of the scope or not */
//	/* check if a message may be sent to this scope */
//	if (!HAS_STATUS(scope, SCOPES_STATUS_MEMBER)) {
//		PRINTF(1, "(SCOPES) not allowed to send to scope: %u\n", scope->scope_id);
//		return;
//	}
	/* reset the contents of the message buffer */
	routing->buffer_clear(to_creator);

	/* create the message */
	struct scopes_msg_data *msg =
			(struct scopes_msg_data *) routing->buffer_ptr(to_creator);

	msg->scope_id = scope->scope_id;
	msg->type = SCOPES_MSG_DATA;
	msg->to_creator = to_creator;
	msg->data_len = data_len;
	rimeaddr_copy(&msg->source, &rimeaddr_node_addr);

	/* copy the payload in the packet buffer */
	void *data_ptr = (void *) ((uint8_t *) msg + sizeof(struct scopes_msg_data));
	memcpy(data_ptr, data, data_len);

	/* set the message length */
	routing->buffer_setlen(to_creator,
			sizeof(struct scopes_msg_data) + data_len);

	/* tell the routing layer to send the packet */
	routing->send(scope->scope_id, to_creator);
}

/** \brief Invoked from the lower routing layer when a message is received */
void scopes_receive(struct scopes_msg_generic *gmsg) {
	//  /* cast the contents of the packet buffer to a generic scopes message */
	//  struct scopes_msg_generic *gmsg = (struct scopes_msg_generic *) packetbuf_dataptr();

	/* now we can access the type field in the message */
	switch (gmsg->type) {
	case SCOPES_MSG_OPEN: {
		/* cast the message to the correct type */
		struct scopes_msg_open *msg =
				(struct scopes_msg_open *) (struct scopes_msg_data *) gmsg;

		PRINTF(3,
				"(SCOPES) SCOPES_MSG_OPEN: scope-id=%u, subscope-id=%u, owner-sid=%u, ttl=%u, flags=%u, spec-len=%u\n",
				msg->scope_id, msg->sub_scope_id, msg->owner_sid, msg->ttl,
				msg->flags, msg->spec_len);

		struct scope *super_scope = lookup_scope_id(msg->scope_id);
		struct scope *new_scope = lookup_scope_id(msg->sub_scope_id);

		/* check if the super scope is known */
		if (super_scope == NULL)
			break;

		/* check if the node is a member in the super scope */
		if (!HAS_STATUS(super_scope, SCOPES_FLAG_STATUS_MEMBER))
			break;

		/* calculate the position of the specification */
		void *specs_msg_ptr = (void *) ((uint8_t *) gmsg
				+ sizeof(struct scopes_msg_open));

		/* check membership */
		int should_be_member = membership->check(specs_msg_ptr, msg->spec_len);

		PRINTF(5, "(SCOPES) Membership evaluation result: %d\n",
				should_be_member);

		/* check if the new scope is known in this node */
		if (new_scope == NULL) {
			/* (the scope is not known in this node, so try to create) */

			/* check if the owner exists on this node and is subscribed */
			struct scopes_subscriber *owner = lookup_subscriber_id(
					msg->owner_sid);

			if (owner != NULL && IS_SUBSCRIBED(owner)) {
				//				/* calculate the position of the specification */
				//				void *specs_msg_ptr = (void *) ((uint8_t *) gmsg
				//						+ sizeof(struct scopes_msg_open));

				//				/* check membership */
				//				int should_be_member = membership->check(specs_msg_ptr,
				//						msg->spec_len);

				/* add the scope if the node meets the membership criteria
				 or if the scope is a dynamic scope */
				if (should_be_member || (msg->flags & SCOPES_FLAG_DYNAMIC)) {
					/* try to get memory for the new scope */
					new_scope = allocate_scope(msg->spec_len);

					/* check if memory could be obtained */
					if (new_scope != NULL) {
						/* fill in the scope information */
						new_scope->scope_id = msg->sub_scope_id;
						new_scope->super_scope_id = msg->scope_id;
						new_scope->owner = owner;
						new_scope->ttl = msg->ttl;
						new_scope->status = SCOPES_FLAG_STATUS_NONE;
						new_scope->flags = msg->flags;
						new_scope->spec_len = msg->spec_len;

						/* copy the specification */
						memcpy(new_scope->specs, specs_msg_ptr, msg->spec_len);

						/* add the scope to the scopes list */
						add_scope(new_scope);

						/* join if the node should be a member */
						if (should_be_member) {
							join_scope(new_scope);
						}
					}
				}
			}
		} else {
			/* (the scope is already known in this node, so just refresh it) */

			/* check if the new scope is the super scope's sub scope */
			if (ARE_LINKED(super_scope, new_scope)
					&& !HAS_STATUS(new_scope, SCOPES_FLAG_STATUS_CREATOR)) {

				/* check if the timer should be reset */
				if (should_be_member || (msg->flags & SCOPES_FLAG_DYNAMIC)) {
					PRINTF(3,
							"(SCOPES) received re-announcement for scope: %u\n",
							new_scope->scope_id);
					/* reset the sub scope's TTL timer */
					reset_scope_timer(new_scope);

				} else {
					/* remove the scope */
					remove_scope(new_scope);
				}
			}
		}

		break;
	}
	case SCOPES_MSG_CLOSE: {
		/* cast the message to the correct type */
		struct scopes_msg_close *msg = (struct scopes_msg_close *) gmsg;

		PRINTF(3, "(SCOPES) SCOPES_MSG_CLOSE, scope-id=%u, subscope-id=%u\n",
				msg->scope_id, msg->sub_scope_id);

		struct scope *super_scope = lookup_scope_id(msg->scope_id);
		struct scope *sub_scope = lookup_scope_id(msg->sub_scope_id);

		/* check if the scope should be removed */
		if (super_scope != NULL && sub_scope != NULL
				&& ARE_LINKED(super_scope, sub_scope)
				&& !HAS_STATUS(sub_scope, SCOPES_FLAG_STATUS_CREATOR)) {
			/* remove the scope */
			PRINTF(3, "(SCOPES) had %d scopes\n", list_length(scopes));
			remove_scope(sub_scope);
			PRINTF(3, "(SCOPES) removed %d, now only %d scopes!\n",
					sub_scope->scope_id, list_length(scopes));
		}
		break;
	}
	case SCOPES_MSG_DATA: {
		/* cast the message to the correct type */
		struct scopes_msg_data *msg = (struct scopes_msg_data *) gmsg;

		PRINTF(3,
				"(SCOPES) SCOPES_MSG_DATA, scope-id=%u, to-creator=%u, source=[%u.%u], data-len=%u\n",
				msg->scope_id, msg->to_creator, msg->source.u8[0],
				msg->source.u8[1], msg->data_len);

		struct scope *scope = lookup_scope_id(msg->scope_id);

		/* message will be passed to upper layer iff:
		 * a) scope is known and node is member, or
		 * b) INTERCEPT flag was set.
		 * */

		/* check if the scope is known */
		if (scope != NULL) {
			/* check if the node is a member */
			if (HAS_STATUS(scope, SCOPES_FLAG_STATUS_MEMBER) || //
					(msg->to_creator && HAS_FLAG(scope, SCOPES_FLAG_INTERCEPT))||
					(!msg->to_creator && HAS_FLAG(scope, SCOPES_FLAG_INTERCEPT) && routing->has_status(scope->scope_id, 0x2 /*SELFUR_STATUS_FORWARDER*/))){
					/* check if the message should be delivered */
					if ((msg->to_creator
									&& HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR))
							|| (!(msg->to_creator)
									&& !HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR))
							|| (msg->to_creator
									&& HAS_FLAG(scope, SCOPES_FLAG_INTERCEPT))) {
						/* check if the owner is subscribed */
						if (IS_SUBSCRIBED(scope->owner)) {
							/* calculate the position of the payload */
							void *payload_ptr = (void *) ((uint8_t *) gmsg
									+ sizeof(struct scopes_msg_data));

							/* notify the owner */
							CALLBACK(scope->owner,
									recv(scope->scope_id, payload_ptr, msg->data_len, msg->to_creator, &msg->source));
						}
					}
				}
			}
		break;
	}
	default:
		PRINTF(3, "(SCOPES) Unknown message type received: %u\n", gmsg->type);
		break;
	}
}

//struct scope *
//scopes_lookup(scope_id_t scope_id)
//{
//  return lookup_scope_id(scope_id);
//}

/* static function definitions */
static struct scope *
allocate_scope(data_len_t spec_len) {
	/* try to get memory for the scope */
	struct scope *scope = (struct scope *) memb_alloc(&scopes_mem);

	/* check if memory could be obtained */
	if (scope == NULL) {
		return (NULL);
	}

	/* try to get memory for the specification */
	void *specs_ptr = malloc(spec_len);

	PRINTF(2, "(SCOPES) allocated %u bytes for scope_spec @%p\n", spec_len,
			specs_ptr);
	/* check if memory could be obtained */
	if (specs_ptr == NULL) {
		/* free memory for scope structure */
		memb_free(&scopes_mem, scope);
		return (NULL);
	}

	/* set memory address of specification */
	scope->specs = specs_ptr;

	/* return new scope */
	return (scope);
}

static void deallocate_scope(struct scope *scope) {
	PRINTF(2, "(SCOPES) freed specs of scope %p @%p\n", scope,
			scope->specs);

	/* free specification memory */
	free(scope->specs);

	/* free scope memory */
	memb_free(&scopes_mem, scope);
}

static void add_scope(struct scope *scope) {
	/* start the TTL timer for this scope */
	reset_scope_timer(scope);

	/* add the scope to the scopes list */
	list_add(scopes, scope);

	PRINTF(3, "(SCOPES) Added scope %u\n", scope->scope_id);

	PRINTF(3,
			"(SCOPES) scope-id=%u, super-scope-id=%u, owner-sid=%u, ttl=%lu, flags=%u, status=%u, spec-len=%u\n",
			scope->scope_id, scope->super_scope_id, scope->owner->id,
			timer_remaining(&(scope->ttl_timer.etimer.timer)) / CLOCK_SECOND,
			scope->flags, scope->status, scope->spec_len);

	/* inform the routing processes */
	routing->add(scope->scope_id, HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR));

	/* inform the owner */
	CALLBACK(scope->owner, add(scope->scope_id));
}

/** \brief Invoked when the scope is being removed (regardless whether the node is member of the scope or not) */
static void remove_scope(struct scope *scope) {
	if (!IS_WORLD_SCOPE(scope)) {
		/* recursively delete all sub-scopes */
		remove_scope_recursively(scope);

		/* remove the scope */
		remove_single_scope(scope);
	}
}

/** \brief TODO */
static void remove_scope_recursively(struct scope *scope) {
	struct scope *current, *previous;
	current = list_head(scopes);
	previous = NULL;

	while (current != NULL) {
		if (current->super_scope_id == scope->scope_id) {
			remove_scope_recursively(current);
			remove_single_scope(current);

			if (previous == NULL) {
				current = list_head(scopes);
			} else {
				current = previous;
			}
		}
		previous = current;
		if (current != NULL) {
			current = current->next;
		}
	}
}

/** \brief TODO */
static void remove_single_scope(struct scope *scope) {
	/* leave scope in case node is a member */
	if (HAS_STATUS(scope, SCOPES_FLAG_STATUS_MEMBER)) {
		leave_scope(scope);
	}

	/* inform the owner */
	CALLBACK(scope->owner, remove(scope->scope_id));

	/* inform the routing process */
	routing->remove(scope->scope_id);

	/* remove the scope */
	list_remove(scopes, scope);

	PRINTF(3, "(SCOPES) Removed scope %u\n", scope->scope_id);

	/* stop the scope's TTL timer */
	ctimer_stop(&(scope->ttl_timer));

	/* deallocate scope memory */
	deallocate_scope(scope);
}

/** \brief TODO */
static void join_scope(struct scope *scope) {
	/* set member status */
	scope->status |= SCOPES_FLAG_STATUS_MEMBER;

	PRINTF(3, "(SCOPES) Joined scope %u\n", scope->scope_id);

	/* inform the routing processes */
	routing->join(scope->scope_id);

	/* inform the owner */
	CALLBACK(scope->owner, join(scope->scope_id));
}

/** \brief Invoked when the scope is being removed AND the node was member of the scope  */
static void leave_scope(struct scope *scope) {
	PRINTF(3, "(SCOPES) Left scope %u\n", scope->scope_id);

	/* inform the owner */
	CALLBACK(scope->owner, leave(scope->scope_id));

	/* inform the routing process */
	routing->leave(scope->scope_id);

	/* unset member status */
	scope->status &= ~SCOPES_FLAG_STATUS_MEMBER;
}

/** \brief TODO */
static struct scope *
lookup_scope_id(scope_id_t scope_id) {
	struct scope *s;

	/* go through the list of scopes and find the one with the given id */
	for (s = list_head(scopes); s != NULL; s = s->next) {
		if (s->scope_id == scope_id) {
			return s;
		}
	}
	return (NULL);
}

/** \brief TODO */
static void handle_scope_timeout(struct scope *scope) {
	/* check if the node is the creator of the scope and if the owner is
	 subscribed */
	if (HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR) && IS_SUBSCRIBED(scope->owner)) {
		/* re-announce the scope on the network */
		PRINTF(3, "(SCOPES) Re-announcing scope %u\n", scope->scope_id);
		announce_scope_open(scope);

		/* reset the timer to ensure periodic re-announcements */
		reset_scope_timer(scope);
	} else {
		/* remove the expired scope */
		PRINTF(3, "(SCOPES) Scope %u has expired\n", scope->scope_id);
		remove_scope(scope);
	}
}

/** \brief TODO */
static void reset_scope_timer(struct scope *scope) {
	clock_time_t timeout = CLOCK_SECOND * scope->ttl;

	if (HAS_STATUS(scope, SCOPES_FLAG_STATUS_CREATOR)) {
		/* shorten the creator's timer so the scope is re-announced in time */
		timeout = timeout * SCOPES_REANNOUNCE_FACTOR;
	}

	/* reset the timer */
	PRINTF(3, "(SCOPES) Resetting scope %d's timer to %d, left was %lu\n",
			scope->scope_id, scope->ttl,
			timer_remaining(&(scope->ttl_timer.etimer.timer)));

	ctimer_set(&(scope->ttl_timer), timeout,
			(void (*)(void *)) handle_scope_timeout, scope);
}

/** \brief TODO */
static void announce_scope_open(struct scope *scope) {
	/* reset the contents of the packet buffer */
	routing->buffer_clear(FALSE);

	/* create the message */
	struct scopes_msg_open *msg =
			(struct scopes_msg_open *) routing->buffer_ptr(FALSE);

	msg->scope_id = scope->super_scope_id;
	msg->type = SCOPES_MSG_OPEN;
	msg->sub_scope_id = scope->scope_id;
	msg->owner_sid = scope->owner->id;
	msg->ttl = scope->ttl;
	msg->flags = scope->flags;
	msg->spec_len = scope->spec_len;

	/* copy the scope specifications in the packet buffer */
	void *specs_ptr = (void *) ((uint8_t *) routing->buffer_ptr(FALSE)
			+ sizeof(struct scopes_msg_open));
	memcpy(specs_ptr, scope->specs, scope->spec_len);

	/* set the message length */
	routing->buffer_setlen(FALSE,
			sizeof(struct scopes_msg_open) + scope->spec_len);

	/* tell the routing layer to send the packet */
	routing->send(scope->super_scope_id, FALSE);
}

/** \brief TODO */
static void announce_scope_close(struct scope *scope) {
	/* reset the contents of the packet buffer */
	routing->buffer_clear(FALSE);

	/* create the message */
	struct scopes_msg_close *msg =
			(struct scopes_msg_close *) routing->buffer_ptr(FALSE);
	msg->scope_id = scope->super_scope_id;
	msg->type = SCOPES_MSG_CLOSE;
	msg->sub_scope_id = scope->scope_id;

	/* set the message length */
	routing->buffer_setlen(FALSE, sizeof(struct scopes_msg_close));

	/* tell the routing layer to send the packet */
	routing->send(scope->super_scope_id, FALSE);
}

/** \brief TODO */
static struct scopes_subscriber *
lookup_subscriber_id(subscriber_id_t id) {
	struct scopes_subscriber *s;

	/* go through the list of subscribers and find the one with the given id */
	for (s = list_head(subscribers); s != NULL; s = s->next) {
		if (s->id == id) {
			return (s);
		}
	}
	return (NULL);
}

/** \brief Scopes process
 * */

PROCESS_THREAD( scopes_process, ev, data) {
	PROCESS_BEGIN()
		;

		PRINTF(3, "(SCOPES) scopes process started\n");

		/* create and start an event timer */
		static struct etimer scopes_timer;
		etimer_set(&scopes_timer, SCOPES_TIMER_DURATION * CLOCK_SECOND);

		do {
			/* wait till the timer expires and then reset it immediately */
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&scopes_timer));
			etimer_reset(&scopes_timer);

			struct scope *s;

			/* check memberships of dynamic scopes */
			//			PRINTF(3, "(SCOPES) checking dynamic scope memberships\n");
			for (s = list_head(scopes); s != NULL; s = s->next) {
				/* only check scopes not created by the local node */
				if (HAS_FLAG(s, SCOPES_FLAG_DYNAMIC)
						&& !HAS_STATUS(s, SCOPES_FLAG_STATUS_CREATOR)) {
					/* check membership */
					int should_be_member = membership->check(s->specs,
							s->spec_len);

					PRINTF(3, "(SCOPES) should be member: %u\n",
							should_be_member);

					/* decide action */
					if (should_be_member
							&& !HAS_STATUS(s, SCOPES_FLAG_STATUS_MEMBER)) {
						/* join scope */
						join_scope(s);
					} else if (!should_be_member
							&& HAS_STATUS(s, SCOPES_FLAG_STATUS_MEMBER)) {
						/* leave scope */
						leave_scope(s);
					}
				}
			}

			//			/* print scopes information */
			//			PRINTF(3, "(SCOPES) known scopes:\n");
			//			PRINTF(3,"-------------\n");
			//			for (s = list_head(scopes); s != NULL; s = s->next) {
			//				PRINT_SCOPE(3, s);
			//			}
			//			PRINTF(3, "(SCOPES) -------------\n");

		} while (1);

	PROCESS_END();
}

/** @} */
