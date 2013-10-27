/**
 * \addtogroup routing
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
 * \file	scopes-selfur.c
 * \brief	Main module of the Scopes framework
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */
#include "scopes.h"
#include "scopes-selfur.h"
#include "logger.h"

#ifdef FRAGMENTATION_ENABLED
#include "frag-unicast.h"
#endif

/* counts the number of scopes the local node has created */
static int scope_counter = 0;

/* lock timer */
static struct etimer lock_timer;

/* prototypes */
#ifdef FRAGMENTATION_ENABLED
static void
receive_frag_unicast_data(struct frag_unicast_conn *c, const rimeaddr_t *from);
#else
static void
receive_unicast_data(struct unicast_conn *c, const rimeaddr_t *from);
#endif
static int receive_tree_update(struct netflood_conn *c, const rimeaddr_t *from,
		const rimeaddr_t *originator, uint8_t seqno, uint8_t hops);
static void receive_activation(struct runicast_conn *c, const rimeaddr_t *from,
		uint8_t seqno);
static int receive_flood_data(struct netflood_conn *c, const rimeaddr_t *from,
		const rimeaddr_t *originator, uint8_t seqno, uint8_t hops);
static void receive_suppress(struct broadcast_conn *c, const rimeaddr_t *from);
static void build_routing_tree(void);
static void send_activation_msg(struct scope_entry *s);
static void add_routing_entry(const rimeaddr_t *root,
		const rimeaddr_t *next_hop, uint8_t hops);
static void update_routing_entry(struct routing_entry *r,
		const rimeaddr_t *next_hop, uint8_t hops);
static struct routing_entry *lookup_routing_entry(const rimeaddr_t *root);
static void print_routing_entry(struct routing_entry *r, char *msg);
static void remove_routing_entry(struct routing_entry *r);
static void add_scope_entry(scope_id_t scope_id, struct routing_entry *r);
static struct scope_entry *lookup_scope_entry(scope_id_t scope_id);
static void print_scope_entry(struct scope_entry *s, char *msg);

/* tree update connection and callbacks */
static struct netflood_conn tree_update;
static struct netflood_callbacks tree_update_callbacks = { receive_tree_update,
		NULL, NULL };
static uint8_t tree_seqno = 0;

/* data connection and callbacks for data to the scope members */
static struct netflood_conn data_flood;
static struct netflood_callbacks data_flood_callbacks = { receive_flood_data,
		NULL, NULL };
static uint8_t data_seqno = 0;

/** \brief reliable unicast connection and callbacks for activation messages */
static struct runicast_conn activation_runicast;
static struct runicast_callbacks activation_callbacks = { receive_activation,
		NULL, NULL };

/** \brief unicast connection and callbacks for data to the sink */
#ifdef FRAGMENTATION_ENABLED
static struct frag_unicast_conn data_unicast;
static struct frag_unicast_callbacks data_unicast_callbacks = {
		receive_frag_unicast_data };
#else
static struct unicast_conn data_unicast;
static struct unicast_callbacks data_unicast_callbacks =
{	receive_unicast_data};
#endif

/** \brief broadcast connection and callbacks for suppress messages */
static struct broadcast_conn suppress_broadcast;
static struct broadcast_callbacks suppress_broadcast_callbacks = {
		receive_suppress };
/* process declarations */
/** \brief		TODO */
PROCESS(tree_update_process, "tree updater");
/** \brief		TODO */
PROCESS(activation_process, "activator");
#ifndef SCOPES_NOINFO
/** \brief		TODO */
PROCESS(info_process, "info process");
#endif
/* memory management */
/** \brief		TODO */
MEMB(routing_mem, struct routing_entry, SELFUR_MAX_ROUTING_ENTRIES);
/** \brief		TODO */
MEMB(scopes_mem, struct scope_entry, SELFUR_MAX_SCOPE_ENTRIES);
/** \brief List for routing entries */
LIST(routing_list);
/** \brief List for the associated scope entries */
LIST(scopes_list);

/* functions called by rime */
static int receive_tree_update(struct netflood_conn *c, const rimeaddr_t *from,
		const rimeaddr_t *originator, uint8_t seqno, uint8_t hops) {

	PRINTF(5,
			"(SCOPES-SELFUR) Received a tree update from [%u.%u], hop-count: %u.\n",
			from->u8[0], from->u8[1], hops);

	/* check if the local node is the originator of the message */
	if (!rimeaddr_cmp(&rimeaddr_node_addr, originator)) {
		/* look up the originator's routing entry */
		struct routing_entry *r = lookup_routing_entry(originator);
		if (r != NULL) {
			PRINTF(5,
					"(SCOPES-SELFUR) there was already a routing entry for the originator [%u.%u]\n",
					originator->u8[0], originator->u8[1]);

			/* update the originator's existing routing entry */
			update_routing_entry(r, from, hops + 1);
		} else {
			PRINTF(5,
					"(SCOPES-SELFUR) there wasn't a routing entry for the originator [%u.%u], so one will be created.\n",
					originator->u8[0], originator->u8[1]);

			/* add a new routing entry for the originator */
			add_routing_entry(originator, from, hops + 1);
		}
		/* rebroadcast the tree update */
		return (1);
	}

	/* do not rebroadcast the tree update if the local node is the originator */
	return (0);
}

/** \brief		TODO */
static void receive_activation(struct runicast_conn *c, const rimeaddr_t *from,
		uint8_t seqno) {

	PRINTF(5, "(SCOPES-SELFUR) Received activation msg from [%u.%u]\n",
			from->u8[0], from->u8[1]);

	/* cast the message */
	struct route_activation_msg *msg =
			(struct route_activation_msg *) packetbuf_dataptr();

	/* look up the scope's entry */
	struct scope_entry *s = lookup_scope_entry(msg->scope_id);
	if (s != NULL) {
		/* check if the activation should be forwarded to the next hop */
		if (!rimeaddr_cmp(&(s->tree->next_hop), &(s->tree->root))
				&& !(SELFUR_HAS_STATUS(s, SELFUR_STATUS_MEMBER)
						|| SELFUR_HAS_STATUS(s, SELFUR_STATUS_FORWARDER))) {
			SELFUR_SET_STATUS(s, SELFUR_STATUS_ACTIVATE);
		}

		if (!SELFUR_HAS_STATUS(s, SELFUR_STATUS_FORWARDER)) {
			/* set status forwarder */
			SELFUR_SET_STATUS(s, SELFUR_STATUS_FORWARDER);

			PRINTF(5,
					"(SCOPES-SELFUR) forwarding enabled in scope: scope-id=%u\n",
					s->scope_id);

			/* send suppression message */
			packetbuf_clear();
			struct route_suppress_msg *smsg =
					(struct route_suppress_msg *) packetbuf_dataptr();
			smsg->scope_id = s->scope_id;
			packetbuf_set_datalen(sizeof(struct route_suppress_msg));
			broadcast_send(&suppress_broadcast);
		}
	}
}

/** \brief		TODO */
static int receive_flood_data(struct netflood_conn *c, const rimeaddr_t *from,
		const rimeaddr_t *originator, uint8_t seqno, uint8_t hops) {
	PRINTF(5,
			"(SCOPES-SELFUR) Received flood data from [%u.%u] orig [%u.%u] num scope_e %u, num rou_e %d, hops %d\n",
			from->u8[0], from->u8[1], originator->u8[0], originator->u8[1],
			list_length(scopes_list), list_length(routing_list), hops);
	PRINT_ARR(5, packetbuf_dataptr(), packetbuf_datalen());

	/* bail out if address is invalid! */
	if (rimeaddr_cmp(originator, &rimeaddr_null))
		return (0);

	/* whether to rebroadcast the message */
	int rebroadcast = 0;

	/* look up the root's routing entry */
	struct routing_entry *r = lookup_routing_entry(originator);
	if (r != NULL) {
		/* cast the message to the generic message type */
		struct scopes_msg_generic *gmsg =
				(struct scopes_msg_generic *) packetbuf_dataptr();
		struct scope_entry *s;

		/* check if the message should be rebroadcast */
		if (gmsg->scope_id == SCOPES_WORLD_SCOPE_ID
				|| ((s = lookup_scope_entry(gmsg->scope_id)) != NULL
						&& SELFUR_HAS_STATUS(s, SELFUR_STATUS_FORWARDER))) {
			PRINTF(5,
					"(SCOPES-SELFUR) forwarding message via parent scope with id %u\n",
					gmsg->scope_id);
			rebroadcast = 1;
		} //
#ifndef NETFLOOD_BUG_FIXED
				else { //
			// Hack to fix receiving msgs multiple times, adopted from revision 21439
			// Hack not needed if netflood bug is fixed
			PRINTF(5, "(SCOPES-SELFUR) performing netflood hack %u.%u\n",
					originator->u8[0], originator->u8[1]);
			rimeaddr_copy(&c->last_originator, originator);
			c->last_originator_seqno = seqno;
		}
#endif

		/* check the message type */
		switch (gmsg->type) {
		case SCOPES_MSG_OPEN: {
			/* cast the message to the correct type */
			struct scopes_msg_open *msg =
					(struct scopes_msg_open *) packetbuf_dataptr();

			/* look up the sub scope's entry */
			s = lookup_scope_entry(msg->sub_scope_id);
			if (s == NULL) {
				/* add an entry for the sub scope */
				add_scope_entry(msg->sub_scope_id, r);
			}
			break;
		}
		}

		/* call scopes */
		PRINTF(5,
				"(SCOPES-SELFUR) Invoking scopes_receive() with payload: from [%u.%u], orig [%u.%u], ",
				from->u8[0], from->u8[1], originator->u8[0], originator->u8[1]);
		PRINT_ARR(5, (uint8_t* )gmsg, packetbuf_datalen());
		scopes_receive(gmsg);
	}
	else
		PRINTF(5, "(SCOPES-SELFUR) Received flood data but no routing entry for that originator node [%u.%u]!\n", originator->u8[0], originator->u8[1]);

	/* return rebroadcast decision */
	return (rebroadcast);
}

/** \brief Invoked when a data message has arrived to this node, flowing towards the creator. */
#ifdef FRAGMENTATION_ENABLED
static void receive_frag_unicast_data(struct frag_unicast_conn *c,
		const rimeaddr_t *from)
#else
static void receive_unicast_data(struct unicast_conn *c, const rimeaddr_t *from)
#endif
{
	/* A data message has arrived to this node, flowing towards the creator. */

	PRINTF(5, "(SCOPES-SELFUR) Data received from [%u.%u]\n", from->u8[0],
			from->u8[1]);

	/* Message is passed to upper layer, and then,
	 * if this node is not the creator of the scope,
	 * the message needs to be forwarded. */

	/* cast message to the correct type */
	struct scopes_msg_generic *gmsg;
#ifdef FRAGMENTATION_ENABLED
	gmsg = (struct scopes_msg_generic*) frag_unicast_buffer_ptr(&data_unicast);
#else
	gmsg = packetbuf_dataptr();
#endif

	/* Call scopes */
	scopes_receive(gmsg);

	PRINTF(5, "(SCOPES-SELFUR) Data passed to scopes_received ()\n");

	/* Remaining code commented out to avoid forwarding the message automatically.
	 * Instead, the upper layer will request the reforwarding based on its processing.
	 * This is needed when using Scopes with ukuFlow, since ukuFlow will process the data
	 * (e.g., events) and request the sending the data to the parent node. */

#ifdef SCOPES_AUTO_FORWARD_ENABLED
	/* Look up the scope's routing entry */
	struct scope_entry *s = lookup_scope_entry(gmsg->scope_id);

	/* Now check if message needs to be forwarded to next hop */
	if ((s != NULL) && (!rimeaddr_cmp(&rimeaddr_node_addr, &(s->tree->root)))) {

#ifdef FRAGMENTATION_ENABLED
		frag_unicast_send(&data_unicast, &(s->tree->next_hop));
#else
		unicast_send(&data_unicast, &(s->tree->next_hop));
#endif
		PRINTF(5,
				"(SCOPES-SELFUR) data sent to upper parent\n");

	}
#endif
}

/** \brief		TODO */
static void receive_suppress(struct broadcast_conn *c, const rimeaddr_t *from) {
	/* cast message to the correct type */
	struct route_suppress_msg *msg =
			(struct route_suppress_msg *) packetbuf_dataptr();

	/* look up the scope's entry */
	struct scope_entry *s = lookup_scope_entry(msg->scope_id);
	if (s != NULL) {
		/* check if the suppression message was sent by the local node's next hop */
		if (rimeaddr_cmp(&(s->tree->next_hop), from)) {
			/* set suppression status */
			print_scope_entry(s, "suppressing activation");
			SELFUR_SET_STATUS(s, SELFUR_STATUS_SUPPRESS);
		}
	}
}

/* interface functions */
/** \brief		TODO */
static void selfur_init(void) {
	/* initialize memory */
	memb_init(&routing_mem);
	memb_init(&scopes_mem);
	list_init(routing_list);
	list_init(scopes_list);

	/* open a channel for tree updates */
	netflood_open(&tree_update, SELFUR_FLOODING_QUEUE_TIME,
			SELFUR_TREE_UPDATE_CHANNEL, &tree_update_callbacks);

	/* open a channel for activation messages */
	runicast_open(&activation_runicast, SELFUR_ACTIVATION_CHANNEL,
			&activation_callbacks);

	/* open a channel for suppression messages */
	broadcast_open(&suppress_broadcast, SELFUR_SUPPRESS_CHANNEL,
			&suppress_broadcast_callbacks);

	/* open channels for data */
	netflood_open(&data_flood, SELFUR_FLOODING_QUEUE_TIME,
			SELFUR_DATA_FLOOD_CHANNEL, &data_flood_callbacks);

#ifdef FRAGMENTATION_ENABLED
	frag_unicast_open(&data_unicast, SELFUR_DATA_UNICAST_CHANNEL,
			&data_unicast_callbacks);
#else
	unicast_open(&data_unicast, SELFUR_DATA_UNICAST_CHANNEL,
			&data_unicast_callbacks);
#endif

	/* start tree update process */
	process_start(&tree_update_process, NULL);

	/* start activation process */
	process_start(&activation_process, NULL);

#ifndef SCOPES_NOINFO
	/* start info process */
	process_start(&info_process, NULL);
#endif
}

/** \brief		TODO */
static void selfur_send(scope_id_t scope_id, bool to_creator) {
	/* check if the lock timer has expired */
	if (!etimer_expired(&lock_timer)) {
		PRINTF(5,
				"(SCOPES-SELFUR) Refused to send data for scope %u during lock interval.\n",
				scope_id);
		return;
	} else PRINTF(5,
			"(SCOPES-SELFUR) Accepted to send data for scope %u, lock interval is over.\n",
			scope_id);

	/* check the direction of the message */
	if (to_creator) {

		/* look up the scope's entry */
		struct scope_entry *s = lookup_scope_entry(scope_id);
		if (s != NULL) {
#ifdef FRAGMENTATION_ENABLED
			frag_unicast_send(&data_unicast, &(s->tree->next_hop));
			PRINTF(5, "(SCOPES-SELFUR) sent up!\n");
#else
			unicast_send(&data_unicast, &(s->tree->next_hop));
#endif
			return;
		}
	} else {
		/* flood everything else */
		netflood_send(&data_flood, data_seqno++);
	}
}

/** \brief		TODO */
static void selfur_add(scope_id_t scope_id, bool create) {
	PRINTF(5, "(SCOPES-SELFUR) Added scope %u\n", scope_id);

	/* check if the local node is the root */
	if (create) {
		/* check scope counter */
		if (scope_counter == 0) {
			/* build routing tree */
			build_routing_tree();
		}

		/* increase scope counter */
		scope_counter++;
	}
}

/** \brief		TODO */
static void selfur_buffer_clear(bool to_creator) {
#ifdef FRAGMENTATION_ENABLED
	if (to_creator)
		frag_unicast_buffer_clear(&data_unicast);
	else
		packetbuf_clear();
#else
	packetbuf_clear();
#endif
}

/** \brief		TODO */
static uint8_t *selfur_buffer_ptr(bool to_creator) {
#ifdef FRAGMENTATION_ENABLED
	if (to_creator)
		return (frag_unicast_buffer_ptr(&data_unicast));
	else
		return (packetbuf_dataptr());
#else
	return (packetbuf_dataptr());
#endif
}

/** \brief		TODO */
static void selfur_buffer_setlen(bool to_creator, uint16_t len) {
#ifdef FRAGMENTATION_ENABLED
	if (to_creator)
		frag_unicast_buffer_setlen(&data_unicast, len);
	else
		packetbuf_set_datalen(len);
#else
	packetbuf_set_datalen(len);
#endif
}

/** \brief		TODO */
static void selfur_remove(scope_id_t scope_id) {
	PRINTF(5, "(SCOPES-SELFUR) Removed scope %u\n", scope_id);

	/* check if the local node is the root */
	struct scope_entry *s = lookup_scope_entry(scope_id);
	if (s == NULL) { // We assume only existing scopes are removed.
		/* check scope counter */
		if (scope_counter > 0) {
			/* decrease scope counter */
			scope_counter--;
		}
	}
}

/** \brief		TODO */
static void selfur_join(scope_id_t scope_id) {
	PRINTF(5, "(SCOPES-SELFUR) Joined scope %u\n", scope_id);

	/* look up the scope's entry */
	struct scope_entry *s = lookup_scope_entry(scope_id);
	if (s != NULL) {
		SELFUR_SET_STATUS(s, SELFUR_STATUS_MEMBER);
		/* check if the next hop is the root */
		if (!rimeaddr_cmp(&(s->tree->next_hop), &(s->tree->root))) {
			SELFUR_SET_STATUS(s, SELFUR_STATUS_ACTIVATE);
		}
	}
}

/** \brief		TODO */
static void selfur_leave(scope_id_t scope_id) {
	PRINTF(5, "(SCOPES-SELFUR) Left scope %u\n", scope_id);

	/* look up the scope's entry */
	struct scope_entry *s = lookup_scope_entry(scope_id);
	if (s != NULL) {
		SELFUR_UNSET_STATUS(s, SELFUR_STATUS_MEMBER);
	}
}

/**
 * \brief		Checks for the status of a routing entry
 */
static bool selfur_has_status(scope_id_t scope_id, uint8_t status) {
	/* Look up the scope's routing entry */
	struct scope_entry *s = lookup_scope_entry(scope_id);

	return ((s != NULL) ? SELFUR_HAS_STATUS(s, status) : FALSE);
}

static uint8_t selfur_node_distance(scope_id_t scope_id) {
	/* Look up the scope's routing entry */
	struct scope_entry *s = lookup_scope_entry(scope_id);

	return ((s != NULL) ? s->tree->hop_count : 0);
}

/** \brief Declares the scopes-selfur routing protocol */
ROUTING(scopes_selfur, //
		"selfur",//
		selfur_init,//
		selfur_send,//
		selfur_add,//
		selfur_remove,//
		selfur_join,//
		selfur_leave,//
		selfur_buffer_clear,//
		selfur_buffer_ptr,//
		selfur_buffer_setlen,//
		selfur_has_status,//
		selfur_node_distance);

/* helper functions */
/** \brief		TODO */
static void build_routing_tree(void) {
	PRINTF(5, "(SCOPES-SELFUR) building routing tree\n");

	/* clear the contents of the packet buffer */
	packetbuf_clear();

	/* send a message with no payload */
	netflood_send(&tree_update, tree_seqno++);

	/* start the lock timer */
	etimer_set(&lock_timer, SELFUR_LOCK_TIMER_DURATION * CLOCK_SECOND);
	PRINTF(5, "(SCOPES-SELFUR) lock timer started (%d seconds)\n",
			SELFUR_LOCK_TIMER_DURATION);
}

/** \brief		TODO */
static void send_activation_msg(struct scope_entry *s) {
	PRINTF(5,
			"(SCOPES-SELFUR) Sending activation: scope-id=%u, next-hop=[%u.%u]\n",
			s->scope_id, (s->tree->next_hop).u8[0], (s->tree->next_hop).u8[1]);

	/* clear the contents of the packet buffer */
	packetbuf_clear();

	/* prepare the message */
	struct route_activation_msg *msg =
			(struct route_activation_msg *) packetbuf_dataptr();
	msg->scope_id = s->scope_id;
	packetbuf_set_datalen(sizeof(struct route_activation_msg));

	/* send the message */
	runicast_send(&activation_runicast, &(s->tree->next_hop),
			SELFUR_ACTIVATION_RETRANSMISSIONS);
}

/** \brief		TODO */
static void add_routing_entry(const rimeaddr_t *root,
		const rimeaddr_t *next_hop, uint8_t hops) {
	//  if (rimeaddr_cmp(root, next_hop) != 0) {
	/* try to get memory for the routing entry */
	struct routing_entry *r = (struct routing_entry *) memb_alloc(&routing_mem);
	if (r != NULL) {
		/* store the root's address */
		rimeaddr_copy(&(r->root), root); //r->root = *root;

		/* store the next hop */
		update_routing_entry(r, next_hop, hops);

		/* add the entry to the routing list */
		list_add(routing_list, r);
	}
	//  }
}

/** \brief		TODO */
static void update_routing_entry(struct routing_entry *r,
		const rimeaddr_t *next_hop, uint8_t hops) {
	//  if (rimeaddr_cmp(&(r->root), next_hop) != 0) {

	/* set a new next hop */
	rimeaddr_copy(&(r->next_hop), next_hop); // r->next_hop = *next_hop;

	/* reset the entry's timer */
	ctimer_set(&(r->ttl_timer), SELFUR_ROUTING_ENTRY_TTL * CLOCK_SECOND,
			(void (*)(void *)) remove_routing_entry, r);

	/* update the node's height in tree */
	PRINTF(5, "(SCOPES-SELFUR) updating hops to %u\n", hops);
	r->hop_count = hops;

	/* go through the scopes list */
	struct scope_entry *s;
	for (s = list_head(scopes_list); s != NULL; s = s->next) {
		/* check if the scope belongs to the tree that was updated */
		if (rimeaddr_cmp(&(s->tree->root), &(r->root))) {
			/* unset forwarder, suppress, and activate status indicators */
			SELFUR_UNSET_STATUS(s, SELFUR_STATUS_FORWARDER);
			SELFUR_UNSET_STATUS(s, SELFUR_STATUS_SUPPRESS);
			SELFUR_UNSET_STATUS(s, SELFUR_STATUS_ACTIVATE);

			/* check if an activation message needs to be sent to the next hop */
			if (!rimeaddr_cmp(&(s->tree->next_hop),
					&(s->tree->root)) && SELFUR_HAS_STATUS(s, SELFUR_STATUS_MEMBER)) {
				SELFUR_SET_STATUS(s, SELFUR_STATUS_ACTIVATE);
			}
		}
	}

	print_routing_entry(r, "updated routing entry");
}

/** \brief		TODO */
static struct routing_entry *
lookup_routing_entry(const rimeaddr_t *root) {
	/* go through the routing list */
	struct routing_entry *r;
	for (r = list_head(routing_list); r != NULL; r = r->next) {
		/* find the one with the matching root address */
		if (rimeaddr_cmp(root, &(r->root))) {
			return (r);
		}
	}
	return (NULL);
}

/** \brief		TODO */
static void print_routing_entry(struct routing_entry *r, char *msg) {
	PRINTF(5, "(SCOPES-SELFUR) %s: root=%u.%u, next-hop=%u.%u, route-ttl=%u\n",
			msg, (r->root).u8[0], (r->root).u8[1], (r->next_hop).u8[0],
			(r->next_hop).u8[1],
			(unsigned int) (timer_remaining(&(r->ttl_timer.etimer.timer)) / CLOCK_SECOND));
}

/** \brief		TODO */
static void remove_routing_entry(struct routing_entry *r) {
	PRINTF(5,
			"(SCOPES-SELFUR) Removed routing entry for root %u.%u because it's old (num s_e %d num r_e %d)\n",
			r->root.u8[0], r->root.u8[1], list_length(scopes_list),
			list_length(routing_list));

	print_routing_entry(r, "removing routing entry");

	/* go through the scopes list */
	struct scope_entry *current = list_head(scopes_list), *previous = NULL;
	while (current != NULL) {
		/* check if the scope belongs to the expired tree */
		if (rimeaddr_cmp(&(current->tree->root), &(r->root))) {
			/* remove the scope entry */
			print_scope_entry(current, "removing scope entry");
			if (previous == NULL) {
				list_remove(scopes_list, current);
				memb_free(&scopes_mem, current);
				current = list_head(scopes_list);
			} else {
				previous->next = current->next;
				memb_free(&scopes_mem, current);
				current = previous->next;
			}
		} else {
			previous = current;
			current = current->next;
		}
	}

	/* remove the routing entry and free its memory */
	ctimer_stop(&r->ttl_timer);
	list_remove(routing_list, r);
	memb_free(&routing_mem, r);

	PRINTF(5,
			"(SCOPES-SELFUR) Removed routing entry because it's old (num s_e %d num r_e %d)\n",
			list_length(scopes_list), list_length(routing_list));
}

/** \brief		TODO */
static void add_scope_entry(scope_id_t scope_id, struct routing_entry *r) {
	/* check if memory is available */
	PRINTF(5, "(SCOPES-SELFUR) Adding scope routing entry with root %u.%u\n",
			r->root.u8[0], r->root.u8[1]);
	struct scope_entry *s = (struct scope_entry *) memb_alloc(&scopes_mem);
	if (s != NULL) {
		/* store the scope id and associate the scope with a tree */
		s->scope_id = scope_id;
		s->status = SELFUR_STATUS_NONE;
		s->tree = r;

		/* add the entry to the scopes list */
		list_add(scopes_list, s);

		print_scope_entry(s, "added scope entry");
	}
}

/** \brief		TODO */
static struct scope_entry *
lookup_scope_entry(scope_id_t scope_id) {
	/* go through the scopes list */
	struct scope_entry *s;
	PRINTF(5, "(SCOPES-SELFUR) lookup_scope_entry, list size: %u\n",
			list_length(scopes_list));
	for (s = list_head(scopes_list); s != NULL; s = s->next) {
		/* find the one with the matching scope id */
		PRINTF(5, "(SCOPES-SELFUR) comparing %u with %u\n", scope_id,
				s->scope_id);
		if (s->scope_id == scope_id) {
			return (s);
		}
	}
	return (NULL);
}

/** \brief		TODO */
static void print_scope_entry(struct scope_entry *s, char *msg) {
	PRINTF(5,
			"(SCOPES-SELFUR) %s: scope-id=%u, status=%u, root=%u.%u, next-hop=%u.%u, route-ttl=%u\n",
			msg, s->scope_id, s->status, (s->tree->root).u8[0],
			(s->tree->root).u8[1], (s->tree->next_hop).u8[0],
			(s->tree->next_hop).u8[1],
			(unsigned int) (timer_remaining(&(s->tree->ttl_timer.etimer.timer)) / CLOCK_SECOND));
}

/** \brief		TODO */

PROCESS_THREAD( tree_update_process, ev, data) {
	PROCESS_BEGIN()
		;

		PRINTF(5, "(SCOPES-SELFUR) tree update process started\n");

		static struct etimer tree_update_timer;
		etimer_set(&tree_update_timer,
				SELFUR_ROUTING_REFRESH_INTERVAL * CLOCK_SECOND);

		while (1) {
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&tree_update_timer));
			etimer_reset(&tree_update_timer);

			/* check if the routing tree needs to be rebuilt */
			if (scope_counter > 0) {
				/* build routing tree */
				build_routing_tree();
			}
		}

	PROCESS_END();
}

/** \brief		TODO */

PROCESS_THREAD( activation_process, ev, data) {
PROCESS_BEGIN()
	;

	PRINTF(5, "(SCOPES-SELFUR) activation process started\n");

	static struct etimer activation_timer;
	etimer_set(&activation_timer, SELFUR_ACTIVATION_MAX_DELAY * CLOCK_SECOND);

	while (1) {
		PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&activation_timer));
		etimer_reset(&activation_timer);

		/* send activations */
		struct scope_entry *s;
		for (s = list_head(scopes_list); s != NULL; s = s->next) {
			if (SELFUR_HAS_STATUS(s, SELFUR_STATUS_ACTIVATE)
					&& !SELFUR_HAS_STATUS(s, SELFUR_STATUS_SUPPRESS)) {
				SELFUR_UNSET_STATUS(s, SELFUR_STATUS_ACTIVATE);
				send_activation_msg(s);
			}
		}
	}

PROCESS_END();
}

#ifndef SCOPES_NOINFO
/** \brief		TODO */ //
PROCESS_THREAD( info_process, ev, data) {
PROCESS_BEGIN()
;

PRINTF(5, "(SCOPES-SELFUR) info process started\n");

static struct etimer info_timer;
etimer_set(&info_timer, SELFUR_INFO_TIMER_DURATION * CLOCK_SECOND);

while (1) {
	PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&info_timer));
	etimer_reset(&info_timer);

	PRINTF(5, "(SCOPES-SELFUR) known trees:\n");
	struct routing_entry *r;
	for (r = list_head(routing_list); r != NULL; r = r->next) {
		print_routing_entry(r, "");
	}

	PRINTF(5, "(SCOPES-SELFUR) known scopes:\n");
	struct scope_entry *s;
	for (s = list_head(scopes_list); s != NULL; s = s->next) {
		print_scope_entry(s, "");
	}
}

PROCESS_END();
}
#endif
/** @} */
