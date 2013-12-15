/**
 * \addtogroup event
 * @{
 */

/*
 * Copyright (c) 2011, Pablo Guerrero, TU Darmstadt, guerrero@dvs.tu-darmstadt.de
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
 * \file	ukuflow-event-mgr.c
 * \brief	Event Manager in ukuFlow
 *
 *			The Event Manager takes care of the administration of subscriptions
 *			for a node and, in turn, for the network.
 *
 * \details	This file groups the functions of the event manager in ukuFlow.
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2005-2011
 */

#include "stdlib.h"

#include "sys/ctimer.h"
#include "lib/list.h"
#include "sys/clock.h"
#include "lib/random.h"

#include "ukuflow-event-mgr.h"
#include "workflow.h"
#include "event.h"
#include "bitvector.h"

/** for memcpy */
#include "string.h"

#include "logger.h"

#include "expression-eval.h"

/*---------------------------------------------------------------------------*/
/**
 * \brief		Defines the time to live value for event operators running on a node.
 *
 *         		When a local subscription is created, a timer with this length is configured
 *         		so that the subscription is deleted if no 'updates' are received.
 **/
#define MAX_SUB_TTL CLOCK_SECOND * 60 * 3 * 2

/**
 * \brief		Defines the time interval between subscription re-announcements
 * */
#define SUBSCRIPTION_REANNOUNCE_FACTOR 1/3

/**
 * \brief 		Defines the maximum tree height that can be assumed in the sensor network
 */
#define MAX_TREE_HEIGHT 10

/** \brief 		Data type for the event processing request */
typedef uint8_t request_type_t;

/** \brief TODO */
enum request_type {
	SUBSCRIPTION_REQUEST = 0, /* */
	UNSUBSCRIPTION_REQUEST = 1, /* */
	EVENT_PROCESSING_REQUEST = 2 /* */
};

/** \brief		Fields for a generic event manager request*/
#define GENERIC_EVENT_MGR_REQUEST_FIELDS	\
	/** \brief Pointer to next request */	\
	struct event_mgr_request *next;			\
	/** \brief Request type */				\
	request_type_t request_type;

/**
 * \brief		Generic structure for event processing requests (subscriptions, unsubscriptions, events)
 */
struct event_mgr_request {
	GENERIC_EVENT_MGR_REQUEST_FIELDS
};

/** \brief Subscription request for the event manager */
struct subscription_request {
	GENERIC_EVENT_MGR_REQUEST_FIELDS
	/** \brief Pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
	/** \brief size of the entire event operator expression (sequence of all event operators, in bytes) */
	data_len_t ev_op_len;
	/** \brief what needs to be notified */
	void
	(*notify)(struct event *event, data_len_t event_payload_len);
	/** \brief where to take scope specifications if needed: */
	struct workflow *wf;
};

/** \brief Unsubscription request for the event manager */
struct unsubscription_request {
	GENERIC_EVENT_MGR_REQUEST_FIELDS
	/** \brief pointer to workflow token to use once unsubscription is through */
	struct workflow_token *token;
	/** \brief pointer to the subscription that we are to unsubscribe */
	struct global_subscription *subscription;
};

/**
 * \brief		Data structure for event processing requests
 */
struct event_processing_request {
	GENERIC_EVENT_MGR_REQUEST_FIELDS
	/** \brief Pointer to event that needs to be processed */
	struct event *event;
};

/** \brief List of requests placed to the event manager */
LIST(event_mgr_requests);

/** \brief Data type for the subscription types */
typedef uint8_t subscription_type_t;

/** \brief TODO */
enum subscription_type {
	/** \brief Subscription is managed by this node */
	GLOBAL_SUBSCRIPTION = 0, /* */
	/** \brief Subscription is managed by another (scope root) node */
	LOCAL_SUBSCRIPTION = 1
};

/** \brief		TODO				*/
#define GENERIC_SUBSCRIPTION_FIELDS	\
	/** \brief Pointer to next subscription in the list */						\
	struct generic_subscription *next;											\
	/** \brief Type of subscription */											\
	subscription_type_t subscription_type;										\
	/** \brief Length of the byte array containing the event operator(s) */		\
	data_len_t ev_op_len;														\
	/** \brief Pointer to the byte array containing the entire sequence of event operator(s) */ \
	struct generic_event_operator *main_ev_op;

/**
 * \brief	Generic data structure for a subscription containing an event operator
 */
struct generic_subscription {
	GENERIC_SUBSCRIPTION_FIELDS
};

/**
 * \brief	Data structure for a global subscription (i.e., the subscription is managed
 * 			by this node)
 */
struct global_subscription {
	GENERIC_SUBSCRIPTION_FIELDS
	/** \brief */
	/** Callback timer indicating when to re announce the subscription */
	struct ctimer announcement_timer;
	/** \brief Pointer to function to be invoked when a matching event is detected */
	void
	(*notify)(struct event *event, data_len_t event_payload_len);
	/** \brief Pointer to workflow specification to (e.g.) get the specification of the needed scopes */
	struct workflow *wf;
};

/**
 * \brief	Data structure for a local subscription (i.e., the subscription is managed
 * 			by another, scope root node)
 */
struct local_subscription {
	GENERIC_SUBSCRIPTION_FIELDS
	/** \brief Callback timer indicating remaining time to live (to remove the subscription locally) */
	struct ctimer ttl_timer;
};

/** \brief List of locally managed subscriptions */
LIST(subscriptions);

/** \brief		Fields for a generic running event operator	*/
#define RUNNING_EVENT_OPERATOR_FIELDS				\
	/** \brief TODO */ 								\
	struct running_event_op *next; 					\
	/** \brief TODO */ 								\
	channel_id_t input_channel_id;					\
	/** \brief Pointer to subscription that owns this running event operator */	\
	struct generic_subscription *subscription;		\
	/** \brief TODO */								\
	struct generic_event_operator *geo;

/**
 * \brief	Data structure for a running event operator
 */
struct running_event_op {
	RUNNING_EVENT_OPERATOR_FIELDS
};

/**
 * \brief	Data structure for a recurrent running event operator
 */
struct egen_running_event_op {
	RUNNING_EVENT_OPERATOR_FIELDS
	/** \brief Timer trigger inside a period */
	struct ctimer intra_period_trigger_ctimer;
	/** \brief Timer triggering once per period */
	struct ctimer inter_period_trigger_ctimer;
	/** \brief Counter for the amount of repetitions to be executed left.
	 * Used only if it is _not_ an infinite event generator*/
	uint8_t repetitions_left;
};

/**
 * \brief	Data structure for a recurrent running event operator
 */
struct complex_egen_running_event_op {
	RUNNING_EVENT_OPERATOR_FIELDS
	/** \brief Timer to trigger the production of an event */
	struct ctimer event_operator_trigger_ctimer;
	/** \brief Counter for the amount of repetitions to be executed left.
	 * Used only if it is _not_ an infinite event generator*/
	uint8_t repetitions_left;
};

/**
 * \brief	Data structure for a processing composition running event operator
 */
struct pcf_running_event_op {
	RUNNING_EVENT_OPERATOR_FIELDS

	/** \brief Indicates whether this event operator has been initialized */
	bool initialized;

	/** \brief Timer to trigger the output of an event */
	struct ctimer inter_interval_ctimer;

	/** \brief Timer to add delay within an interval */
	struct ctimer intra_interval_ctimer;

	/** \brief Buffer (list) with events to process */ //
	LIST_STRUCT(event_buffer)
	;
};

/** \brief List of running event operators */
LIST(running_event_ops);

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/

/**
 * \brief		Structure containing only a list
 */
struct simple_list_struct {
	LIST_STRUCT(list)
	;
};

/**
 * \brief		Generic structure containing a scope id
 */
struct scope_id_node {
	/** \brief Pointer to next scope id */
	struct scope_id_node *next;
	/** \brief Id of scope that will be processed */
	scope_id_t scope_id;
};

/**
 * \brief		Generic structure containing a pointer to an event
 */
struct event_node {
	/** \brief Pointer to next event node */
	struct event_node *next;
	/** \brief Pointer to event */
	struct event *event_ptr;
};

/**
 * \brief		Generic structure for an event operator
 */
struct ukuflow_event_operator {
	/** \brief Pointer to event operator initialization function */
	void
	(*init)(struct generic_event_operator *geo, bool local,
			struct generic_subscription *subscription);
	/** \brief TODO */
	void
	(*remove)(struct generic_event_operator *geo, bool local);
	/** \brief TODO */
	void
	(*consume)(struct running_event_op *reo, struct event *event,
			data_len_t event_payload_len);
	/** \brief TODO */
	void
	(*evaluate)(struct running_event_op *reo, struct event *event,
			data_len_t event_payload_len);
	/** \brief TODO */
	void
	(*list_scopes)(struct generic_event_operator *geo,
			struct generic_event_operator **current,
			struct simple_list_struct *ls);
};

/** \brief Macro function to specify an event operator */
#define EVENT_OPERATOR(operator_name, init, remove, consume, evaluate, list_scopes)     \
  struct ukuflow_event_operator operator_name = { init, remove, consume, evaluate, list_scopes};

/*---------------------------------------------------------------------------*/
/** Declaration of static processes */
PROCESS(ukuflow_event_mgr_engine_pt, "event engine pt");

/**---------------------------------------------------------------------------*/
/** Variables for the recursive handling of event operators (init/remove) */
static channel_id_t channel_id;
static bool deployed;
static scope_id_t deployment_scope_id;

/*---------------------------------------------------------------------------*/
/** Forward declaration of functions and protothreads*/
struct ukuflow_event_operator *event_operators[];

/*---------------------------------------------------------------------------*/
/** Inter-protothread communication events */
/** \brief Used when an event manager request is made for the event engine: */
static process_event_t event_mgr_request_ready_event;

static void
forward_event_to_parent(struct event *event, data_len_t event_len);

/*---------------------------------------------------------------------------*/
/**
 * \brief		Event Manager initializer
 *
 * 				Initializes the data structures, (inter-protothread) events, and processes used by this event manager.
 */
void ukuflow_event_mgr_init() {

	list_init(running_event_ops);
	list_init(subscriptions);
	list_init(event_mgr_requests);

	random_init(rimeaddr_node_addr.u8[0] + rimeaddr_node_addr.u8[1]);

	/** Setup inter-protothread communication events */
	event_mgr_request_ready_event = process_alloc_event();

	/** Initialize the event manager engine protothread */
	process_start(&ukuflow_event_mgr_engine_pt, NULL);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Searches for a subscription in the list of locally managed subscriptions
 *
 * 				TODO
 */
struct generic_subscription *
get_subscription(channel_id_t channel_id) {
	struct generic_subscription *subscription = list_head(subscriptions);

	while ((subscription != NULL)
			&& (subscription->main_ev_op->channel_id != channel_id))
		subscription = list_item_next(subscription);

	return (subscription);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Allocates memory for a new subscription
 *
 * 				TODO
 *
 * @param[in]	main_ev_op	TODO
 * @param[in]	ev_op_len	TODO
 * @param[in]	notify		TODO
 * @param[in]	wf			TODO
 */
static struct global_subscription *
alloc_global_subscription(struct generic_event_operator *main_ev_op,
		data_len_t ev_op_len,
		void (*notify)(struct event *event, data_len_t event_payload_len),
		struct workflow *wf) {

	struct global_subscription *global_sub = malloc(
			sizeof(struct global_subscription));

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for global sub @%p\n",
			sizeof(struct global_subscription), global_sub);

	if (global_sub != NULL) {
		global_sub->subscription_type = GLOBAL_SUBSCRIPTION;
		global_sub->notify = notify;
		global_sub->wf = wf;
		global_sub->ev_op_len = ev_op_len;
		global_sub->main_ev_op = main_ev_op;
	}
	return (global_sub);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Allocates memory for a new local subscription PLUS the memory
 * 				for the entire, main event operator(s)
 *
 * 				TODO
 *
 * @param[in]	main_ev_op	TODO
 * @param[in]	ev_op_len	TODO
 */
static struct local_subscription *
alloc_local_subscription(struct generic_event_operator *main_ev_op,
		data_len_t ev_op_len) {

	/* Allocate memory for the local subscription */
	struct local_subscription *local_sub = malloc(
			sizeof(struct local_subscription));

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for local sub @%p\n",
			sizeof(struct local_subscription), local_sub);

	if (local_sub == NULL)
		return (NULL);

	/* Allocate memory for the entire event operator */
	struct generic_event_operator *local_main_ev_op = malloc(ev_op_len);

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for local main_ev_op @%p\n",
			ev_op_len, local_main_ev_op);

	if (local_main_ev_op == NULL) {
		free(local_sub);
		return (NULL);
	}

	memcpy(local_main_ev_op, main_ev_op, ev_op_len);

	local_sub->subscription_type = LOCAL_SUBSCRIPTION;
	local_sub->ev_op_len = ev_op_len;
	local_sub->main_ev_op = local_main_ev_op;
	ctimer_set(&local_sub->ttl_timer, MAX_SUB_TTL,
			(void (*)(void *)) ukuflow_event_mgr_handle_unsubscription,
			(void*) (&(local_main_ev_op->channel_id)));

	return (local_sub);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Searches for an existing running event operator on this node
 * 				with the specified event operator type and channel id
 *
 * 				TODO
 */
static struct running_event_op *
get_running_event_op(struct generic_event_operator *geo) {
	struct running_event_op *reo = list_head(running_event_ops);

	while (reo != NULL) {
		if ((reo->geo != NULL) && /* */
		(reo->geo->ev_op_type == geo->ev_op_type) && /* */
		(reo->geo->channel_id == geo->channel_id))
			return (reo);
		else
			reo = list_item_next(reo);
	}
	return (NULL);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief Returns a list of scope ids referred to by the event operator expression
 *
 * @param main_ev_op
 * @param ev_op_len
 */
static void collect_scope_list(struct generic_event_operator *main_ev_op,
		struct simple_list_struct *ls) {

	LIST_STRUCT_INIT(ls, list);

	struct generic_event_operator *current = main_ev_op;

	event_operators[main_ev_op->ev_op_type]->list_scopes(main_ev_op, &current,
			ls);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Helper function to check if a scope id is present in the list passed as parameter
 * @param[in] scope_ids
 * @param[in] sid
 * @return
 */
static bool is_scope_id_present(struct simple_list_struct *ls, scope_id_t sid) {
	struct scope_id_node *node = list_head(ls->list);

	while (node != NULL) {
		if (node->scope_id == sid)
			return (TRUE);
		node = list_item_next(node);
	}
	return (FALSE);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Helper function to clear a list's contents
 * @param[in] ls struct with list whose elements will be freed
 */
static void free_list(struct simple_list_struct *ls) {
	struct list *item;

	while (list_length(ls->list) > 0) {
		item = list_pop(ls->list);

		PRINTF(2, "(EVENT-MGR) freed scope id node @%p\n", item);
		free(item);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Allocates memory for a new running event operator for the event
 * 				operator passed as parameter.
 *
 * 				This function allocates memory for the necessary record, and links
 * 				it to the list of running event operators.
 */
static struct running_event_op *
alloc_running_ev_op(struct generic_event_operator *geo,
		struct generic_subscription *subscription) {

	/** Define pointer for the running event operator that will be returned */
	struct running_event_op *reo = NULL;

	/** Allocate memory for running event operator depending on generic event operator passed as parameter */
	switch (geo->ev_op_type) {
	case (IMMEDIATE_EG): /**			intentionally left blank */
	case (ABSOLUTE_EG): /**				intentionally left blank */
	case (OFFSET_EG): /**				intentionally left blank */
	case (RELATIVE_EG): /**				intentionally left blank */
	case (PERIODIC_EG): /**				intentionally left blank */
	case (PATTERN_EG): /**				intentionally left blank */
	case (FUNCTIONAL_EG): {

		struct egen_running_event_op *egen_reo = malloc(
				sizeof(struct egen_running_event_op));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for egen_reo @%p\n",
				sizeof(struct egen_running_event_op), egen_reo);

		reo = (struct running_event_op*) egen_reo;

		if (egen_reo == NULL)
			return (NULL);

		egen_reo->repetitions_left = 0;

		/** if event operator is a 'recurrent' event generator, set
		 *  the number of repetitions accordingly */

		if (geo->ev_op_type >= PERIODIC_EG) {
			struct recurrent_egen *regen = (struct recurrent_egen *) geo;
			if (regen->repetitions > 0)
				egen_reo->repetitions_left = regen->repetitions;
		}
		break;
	}
	case (SIMPLE_EF): {
		reo = malloc(sizeof(struct running_event_op));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for reo @%p\n",
				sizeof(struct running_event_op), reo);

		break;
	}
		/** Composite event operators: */

//			// *** Logical filters:
//		case (AND_COMPOSITION_EF):  /** intentionally left blank */
//		case (OR_COMPOSITION_EF):  /** intentionally left blank */
//		case (NOT_COMPOSITION_EF): {
//			reo = malloc(sizeof(struct running_event_op));
//			break;
//		}
//			// *** Temporal filters:
//		case (SEQUENCE_COMPOSITION_EF): {
//			reo = malloc(sizeof(struct running_event_op));
//			break;
//		}
//			// *** Processing functions
	case (MIN_COMPOSITION_EF): /**		intentionally left blank */
	case (MAX_COMPOSITION_EF): /**		intentionally left blank */
	case (COUNT_COMPOSITION_EF): /**	intentionally left blank */
	case (SUM_COMPOSITION_EF): /**		intentionally left blank */
	case (AVG_COMPOSITION_EF): /**		intentionally left blank */
	case (STDEV_COMPOSITION_EF): {

		reo = malloc(sizeof(struct pcf_running_event_op));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for pcf reo @%p\n",
				sizeof(struct pcf_running_event_op), reo);

		break;
	}
//			// *** Change event filters:
//		case (INCREASE_EF):  /** intentionally left blank */
//		case (DECREASE_EF):  /** intentionally left blank */
//		case (REMAIN_EF): {
//			reo = malloc(sizeof(struct running_event_op));
//			break;
//		}
//		}

	} /**  switch */

	if (reo == NULL)
		return (NULL);

	/** Set input channel: */
	reo->input_channel_id = channel_id;

	/** Set owner subscription: */
	reo->subscription = subscription;

	/** Link reference from local record to event operator record */
	reo->geo = geo;

	/** Add local record to list */
	list_add(running_event_ops, reo);

	return (reo);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Handling of an event operator subscription
 *
 * 				TODO
 *
 * @param[in]	main_ev_op	pointer to the subscription
 * @param[in]	ev_op_len	length of the entire subscription (sequence of event operators)
 * @param[in]	local 		boolean value indicating whether the subscription to process was started locally or not
 */
void ukuflow_event_mgr_handle_subscription(
		struct generic_event_operator *main_ev_op, data_len_t ev_op_len,
		bool local) {

	PRINTF(7, "(EVENT-MGR) handling subscription for channel %u: ",
			main_ev_op->channel_id);
	PRINT_ARR(7, (uint8_t* ) main_ev_op, ev_op_len);

	struct generic_subscription *generic_sub = get_subscription(
			main_ev_op->channel_id);

	PRINTF(5, "(EVENT-MGR) sub was found? pointer [%p]\n", generic_sub);

	/** Check whether subscription is unknown at this node: */
	if (generic_sub == NULL) {

		/** Subscription is unknown, so allocate memory for a local subscription at this node for it */
		generic_sub = (struct generic_subscription*) alloc_local_subscription(
				main_ev_op, ev_op_len);

		if (generic_sub != NULL)
			list_add(subscriptions, generic_sub);

	} else if (generic_sub->subscription_type == LOCAL_SUBSCRIPTION) {
		/** Subscription is known, so restart timer */
		PRINTF(7,
				"(EVENT-MGR) subscription exists locally, restarting ttl timer\n");
		struct local_subscription *local_sub =
				(struct local_subscription*) generic_sub;
		ctimer_restart(&local_sub->ttl_timer);
	}

	if (generic_sub != NULL) {

		/** input channel id */
		channel_id = 0;

		PRINTF(7,
				"(EVENT-MGR) num reo before init: %u, ev. op. type: %u, oper ptr %p, init ptr %p\n",
				list_length(running_event_ops), main_ev_op->ev_op_type,
				event_operators[main_ev_op->ev_op_type],
				event_operators[main_ev_op->ev_op_type]->init);

		deployed = FALSE;
		/** Start recursive initialization, which makes a copy of the event operator
		 * into a new running event operator if needed */
		event_operators[main_ev_op->ev_op_type]->init(generic_sub->main_ev_op,
				local, generic_sub);

		PRINTF(7, "(EVENT-MGR) num reo after init: %u\n",
				list_length(running_event_ops));
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Handling of an event operator unsubscription
 *
 *				TODO
 *
 * @param[in]	main_ev_op_channel_id channel id of the main event operator
 * @param[in]	local boolean value indicating whether the unsubscription to process was started locally or not
 */
void ukuflow_event_mgr_handle_unsubscription(channel_id_t main_ev_op_channel_id,
		bool local) {

	struct generic_subscription *generic_sub = get_subscription(
			main_ev_op_channel_id);

	/** Check whether subscription is known here: */
	if (generic_sub != NULL) {

		PRINTF(2, "(EVENT-MGR) Sub found for channel %u, about to unsub\n",
				main_ev_op_channel_id);

		PRINTF(5, "(EVENT-MGR) num reo before remove: %u\n",
				list_length(running_event_ops));

		/** Start recursive removal, which deletes running event operators as needed */

		event_operators[generic_sub->main_ev_op->ev_op_type]->remove(
				generic_sub->main_ev_op, local);

		PRINTF(5, "(EVENT-MGR) num reo after remove: %u\n",
				list_length(running_event_ops));

		list_remove(subscriptions, generic_sub);

		/** In addition, if it is a local subscription we need to stop the ttl timer */
		if (generic_sub->subscription_type == LOCAL_SUBSCRIPTION) {
			PRINTF(2,
					"(EVENT-MGR) local sub found for channel %u, about to delete\n",
					generic_sub->main_ev_op->channel_id);
			struct local_subscription *local_sub =
					(struct local_subscription*) generic_sub;

			/** Stop timer to eliminate sub */
			ctimer_stop(&local_sub->ttl_timer);

			PRINTF(2, "(EVENT-MGR) engine pt, freed local main_ev_op @%p\n",
					local_sub->main_ev_op);

			/** Release memory of main event operator */
			free(local_sub->main_ev_op);
		} /** if */

		PRINTF(2, "(EVENT-MGR) engine pt, freed sub @%p\n", generic_sub);

		/** Release memory of local subscription */
		free(generic_sub);
	} /** if */
	else
	PRINTF(5, "(EVENT-MGR) local sub was null!\n");
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Handles an event message received from the network
 *
 * 				Handles an event message by unpacking it from the radio message and
 * 				queuing an event processing request with a copy of it
 *
 * @param[in]	event_msg 	Event message received from the network
 */
void ukuflow_event_mgr_handle_event(struct ukuflow_event_msg *event_msg) {
	struct event *received_event = (struct event*) (((uint8_t*) event_msg)
			+ sizeof(struct ukuflow_event_msg));

	struct event_processing_request *ev_request = malloc(
			sizeof(struct event_processing_request));

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for event req @%p\n",
			sizeof(struct event_processing_request), ev_request);

	/** if there was no space, bail out */
	if (ev_request == NULL)
		return;

	/** get pointer to event space */
	struct event *cloned_event = event_clone(received_event,
			event_msg->event_payload_len);

	if (cloned_event == NULL) {
		free(ev_request);
		return;
	}

	ev_request->request_type = EVENT_PROCESSING_REQUEST;
	ev_request->event = cloned_event;

	event_print(cloned_event, event_msg->event_payload_len);

	list_add(event_mgr_requests, ev_request);

	process_post(&ukuflow_event_mgr_engine_pt, event_mgr_request_ready_event,
			ev_request);

	PRINTF(7, "(EVENT-MGR) Finished posting received event, queue len %u\n",
			list_length(event_mgr_requests));

}

/** -------------------------------------------------------------------------*/
/**  Specification of event operator functions                               */
/** -------------------------------------------------------------------------*/

/*---------------------------------------------------------------------------*/
/**
 * \brief		Callback function for producing an event
 *
 * 				This function is invoked by a callback timer whenever an event needs to be produced.
 *
 * @param[in]	ptr 	the running event operator that needs to be attended by this callback function
 */
static void egen_produce(void *ptr) {
	struct egen_running_event_op *egen_reo = (struct egen_running_event_op*) ptr;
	struct generic_egen *eg = (struct generic_egen*) egen_reo->geo;

	/** By default we always produce an event */
	bool produce = TRUE;

	/** Now check if we need to reset a timer or so */
	switch (egen_reo->geo->ev_op_type) {
	case (PATTERN_EG): {
		struct pattern_egen *pateg = (struct pattern_egen *) egen_reo->geo;

		clock_time_t slot_duration = pateg->period * CLOCK_SECOND
				/ pateg->pattern_len, now = clock_time();
		uint8_t slot_nr = (now
				- egen_reo->inter_period_trigger_ctimer.etimer.timer.start)
				/ slot_duration;

		PRINTF(7,
				"(EVENT-MGR) egen_produce, slot %u duration: %lu now %lu timer:%lu\n",
				slot_nr, slot_duration, now,
				egen_reo->inter_period_trigger_ctimer.etimer.timer.start);
		if ((slot_nr + 1 < pateg->pattern_len)
				&& ctimer_expired(&egen_reo->intra_period_trigger_ctimer)) {
			ctimer_reset(&egen_reo->intra_period_trigger_ctimer);
			PRINTF(7, "(EVENT-MGR) egen_produce, resetting intra timer\n");
		} else
		PRINTF(7,
				"(EVENT-MGR) egen_produce, not resetting intra timer anymore\n");

		/** And here check if event needs to be produced in this slot or not */
		uint8_t *bitvector = ((uint8_t*) pateg) + sizeof(struct pattern_egen);
		if (!bitvector_get(bitvector, slot_nr))
			produce = FALSE;
		break;
	}
	case (FUNCTIONAL_EG): {
		// TODO
		break;
	}

	} // switch

	/** If desired, produce the event */

	if (produce) {
		data_len_t event_len;

		/** get an empty placeholder in dynamic memory to place the event's contents */
		struct event *event = event_alloc(7, EVENT_TYPE_F, EVENT_OPERATOR_ID_F,
				SOURCE_F, MAGNITUDE_F, TIMESTAMP_F, ORIGIN_NODE_F,
				ORIGIN_SCOPE_F);

		event_len = event_get_len(event);

		if (event == NULL)
			return;

		PRINTF(1,
				"(EVENT-MGR) producing event for channel %u, event %p, len %u: ",
				egen_reo->geo->channel_id, event, event_len);
		PRINT_ARR(1, (uint8_t* ) event, event_len);

		event_populate(event, eg);

		event_print(event, event_len);

		struct event_processing_request *ev_request = malloc(
				sizeof(struct event_processing_request));

		PRINTF(1, "(EVENT-MGR) alloc %u bytes for event req @%p\n",
				sizeof(struct event_processing_request), ev_request);

		if (ev_request == NULL) {
			free(event);
			return;
		}

		ev_request->event = event;
		ev_request->request_type = EVENT_PROCESSING_REQUEST;
		list_add(event_mgr_requests, ev_request);

		process_post(&ukuflow_event_mgr_engine_pt,
				event_mgr_request_ready_event, ev_request);

	}

}

/*---------------------------------------------------------------------------*/
static void egen_inter_interval(void *ptr) {

	struct egen_running_event_op *egen_reo = (struct egen_running_event_op*) ptr;
	struct recurrent_egen *rgeo = (struct recurrent_egen *) egen_reo;

	if (((rgeo->repetitions == 0) /** unlimited number of repetitions */
	|| (egen_reo->repetitions_left-- > 0) /** limited number of repetitions, and still some to go */)
			&& ctimer_expired(&egen_reo->inter_period_trigger_ctimer)) {
		ctimer_reset(&egen_reo->inter_period_trigger_ctimer);
		PRINTF(7, "(EVENT-MGR) egen_inter_interval, inter reset\n");
	}

	PRINTF(7, "(EVENT-MGR) egen_inter_interval callback: %lu \n",
			egen_reo->inter_period_trigger_ctimer.etimer.timer.start);

	switch (egen_reo->geo->ev_op_type) {
	case (PERIODIC_EG): {
		egen_produce(ptr);
		break;
	}
	case (PATTERN_EG): {
		struct pattern_egen *pateg = (struct pattern_egen*) egen_reo->geo;
		ctimer_set(&egen_reo->intra_period_trigger_ctimer,
				pateg->period * CLOCK_SECOND / pateg->pattern_len, egen_produce,
				egen_reo);

		egen_produce(egen_reo);
		break;
	}
	}

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Initializes an event generator on this node.
 *
 * 				TODO
 *
 * @param[in] geo
 * @param[in] local
 * @param[in] subscription
 */
static void egen_init(struct generic_event_operator *geo, bool local,
		struct generic_subscription *subscription) {
	struct generic_egen *eg = (struct generic_egen*) geo;

	struct egen_running_event_op *egen_reo;

	/** set initially the 'deployed' variable to FALSE */
	deployed = FALSE;

	/** and set the used scope id to the one requested  */
	deployment_scope_id = eg->scope_id;

	/**
	 * Check whether this event generator operator needs to be initialized at this node.
	 * This needs to be done iff:
	 * - first option: the event generator should run at the root and the parameter 'local' is TRUE, or
	 * - second option: the event generator should run at scope's member nodes, and this node is member
	 *   (but not root/creator) of the scope */

	if ( /* first option */
	((eg->scope_id == LOCAL_EVENT_GENERATOR) && (local))
			||
			/* second option */
			((eg->scope_id != LOCAL_EVENT_GENERATOR)
					&& (scopes_member_of(eg->scope_id))
					&& (!scopes_creator_of(eg->scope_id)))) {

		/** In this node there should be a running event operator for the event generator. */

		/** See if we need to allocate a running event operator */
		egen_reo = (struct egen_running_event_op*) get_running_event_op(geo);

		if (egen_reo == NULL) {
			/** Set the channel id to the 'null' id.
			 * By doing this, no input will be ever fed to this event generator */
			channel_id = 0;
			egen_reo = (struct egen_running_event_op*) alloc_running_ev_op(geo,
					subscription);

			if (egen_reo == NULL)
				return;

			deployed = TRUE;

			/** only continue deploying if it was possible to allocate running event operator */

			clock_time_t delay = 0;
			switch (geo->ev_op_type) {
			case (IMMEDIATE_EG): {
				delay = 0;
				break;
			}
			case (ABSOLUTE_EG): {
				struct absolute_egen *aeg = (struct absolute_egen *) eg;
				delay = aeg->when - clock_time();

				ctimer_set(&egen_reo->intra_period_trigger_ctimer, delay,
						egen_produce, egen_reo);

				break;
			}
			case (OFFSET_EG): {
				struct offset_egen *oeg = (struct offset_egen *) eg;
				delay = oeg->offset * CLOCK_SECOND;

				ctimer_set(&egen_reo->intra_period_trigger_ctimer, delay,
						egen_produce, egen_reo);

				break;
			}
			case (RELATIVE_EG): {
				delay = 0;

				// TODO
				break;
			}
			case (PERIODIC_EG): {
				struct periodic_egen *peg = (struct periodic_egen*) eg;
				delay = peg->period * CLOCK_SECOND;

				ctimer_set(&egen_reo->inter_period_trigger_ctimer, delay,
						egen_inter_interval, egen_reo);
				break;
			}

			case (PATTERN_EG): {
				struct pattern_egen *pateg = (struct pattern_egen*) eg;
				delay = pateg->period * CLOCK_SECOND;

				ctimer_set(&egen_reo->inter_period_trigger_ctimer, delay,
						egen_inter_interval, egen_reo);
				egen_inter_interval(egen_reo);
				break;
			}
			case (FUNCTIONAL_EG): {
				// TODO
				break;
			}
			} /** switch */

			PRINTF(5,
					"(EVENT-MGR) egen init, reo channel id set to %u, recursive channel id set to %u, delay %lu\n",
					egen_reo->input_channel_id, channel_id, delay);

		} /** if */
	}

	/** Update channel_id as input for next event operator */
	channel_id = geo->channel_id;

}

/*---------------------------------------------------------------------------*/
/**
 * \brief Adds to the list of scope ids the scope id of this event generator
 *
 * @param[in] geo
 * @param[in] current the current event operator
 * @param[in|out] ls simple list in which the scope ids are inserted
 */
static void egen_list_scopes(struct generic_event_operator *geo,
		struct generic_event_operator **current, struct simple_list_struct *ls) {

	struct generic_egen *egen = (struct generic_egen*) geo;

	if (!is_scope_id_present(ls, egen->scope_id)) {
		struct scope_id_node *node = malloc(sizeof(struct scope_id_node));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for scope id node @%p\n",
				sizeof(struct scope_id_node), node);

		if (node) {
			node->scope_id = egen->scope_id;
			list_add(ls->list, node);
		}
	}
	/** advance current pointer */
	*current = (struct generic_event_operator*) (((uint8_t*) (*current))
			+ event_operator_get_size(geo));
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static void egen_remove(struct generic_event_operator *geo, bool local) {
	/** check whether this event generator operator is running at this node: */
	struct egen_running_event_op *eg_reo =
			(struct egen_running_event_op *) get_running_event_op(geo);
	if (eg_reo != NULL) {
		ctimer_stop(&eg_reo->intra_period_trigger_ctimer);
		ctimer_stop(&eg_reo->inter_period_trigger_ctimer);
		list_remove(running_event_ops, eg_reo);
		PRINTF(1, "(EVENT-MGR) freed eg_reo @%p\n", eg_reo);
		free(eg_reo);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Initializes a simple filter for the specified parameters
 *
 * @param[in]	geo generic event operator to process
 * @param[in]	local boolean value indicating whether the operator to process was started locally or not
 */
static void simple_filter_init(struct generic_event_operator *geo, bool local,
		struct generic_subscription *subscription) {

	struct running_event_op *reo;

	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) (((uint8_t*) geo)
					+ event_operator_get_size(geo));

	/** First, continue initialization recursively:*/
	PRINTF(5,
			"(EVENT-MGR) sf init before recursion, channel id %u, deployed %u\n",
			channel_id, deployed);
	event_operators[next_geo->ev_op_type]->init(next_geo, local, subscription);
	PRINTF(5,
			"(EVENT-MGR) sf init after recursion, channel id %u, deployed %u\n",
			channel_id, deployed);

	/** Second, check whether this simple filter event operator needs to be initialized at this node.
	 * This must be done iff the previous, recursive event operator was deployed
	 */
	if (deployed) {

		if ((reo = get_running_event_op(geo)) == NULL) {
			PRINTF(2, "(EVENT-MGR) will create sf reo\n");
			reo = alloc_running_ev_op(geo, subscription);
		}
		/** only continue deploying if it was possible to allocate running event operator */
		if ((deployed = (reo != NULL))) {
			PRINTF(2, "(EVENT-MGR) reo is not null: %u %u\n", channel_id,
					geo->channel_id);
			PRINTF(2,
					"(EVENT-MGR) simple filter init, reo input channel id set to %u, recursive channel id set to %u\n",
					reo->input_channel_id, channel_id);

		}
	}

	/** Update channel_id as input for next event operator */
	channel_id = geo->channel_id;

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Removes this simple filter from this node
 *
 * 				Removes a running event operator of a simple filter passed as parameter.
 * 				First, recursively eliminates the nested event operator.
 *
 * @param[in]	geo Generic event operator to be used to find the running event operator(s) to delete
 * @param[in]	local boolean value indicating whether the operator to process was started locally or not
 */
static void simple_filter_remove(struct generic_event_operator *geo, bool local) {

	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) (((uint8_t*) geo)
					+ event_operator_get_size(geo));

	/** First, continue removal recursively: */
	PRINTF(5, "(EVENT-MGR) sf remove before recursion, num reo %d\n",
			list_length(running_event_ops));
	event_operators[next_geo->ev_op_type]->remove(next_geo, local);
	PRINTF(5, "(EVENT-MGR) sf remove after recursion, num reo %d\n",
			list_length(running_event_ops));

	/** Second, check whether this simple filter event operator was running
	 * at this node and in that case, remove it */
	struct running_event_op *reo;
	if ((reo = get_running_event_op(geo)) != NULL) {
		list_remove(running_event_ops, reo);
		PRINTF(2, "(EVENT-MGR) freed sf reo @%p\n", reo);
		free(reo);
		PRINTF(5, "(EVENT-MGR) After own remove, num reo %d\n",
				list_length(running_event_ops));
	} else
	PRINTF(5,
			"(EVENT-MGR) didn't remove sf reo because not found for geo with channel id %u, num reos %u\n",
			geo->channel_id, list_length(running_event_ops));
}

/*---------------------------------------------------------------------------*/
/**
 *  \brief		Filters out events that don't pass the filter's expressions
 *
 *				Takes the event and checks whether it passes the filter.
 *				If the event does not pass the filter, discard it.
 *				If the event passes the filter, republish it with corresponding
 *				channel id, so that potential next event operator can process it.
 *
 * @param[in]	reo				TODO
 * @param[in]	event				TODO
 * @param[in]	event_payload_len	TODO
 */
static void simple_filter_consume(struct running_event_op *reo,
		struct event *event, data_len_t event_len) {

	PRINTF(7, "(EVENT-MGR) Simple filter consuming event %p...\n", event);

	struct simple_filter *filter = (struct simple_filter *) reo->geo;

	bool filter_passed = TRUE;
	uint8_t num_expression;
	uint8_t *expression_pair = ((uint8_t*) filter)
			+ sizeof(struct simple_filter);
	uint8_t *expression_spec;
	PRINTF(2, "(EVENT-MGR) checking %u expressions:\n",
			filter->num_expressions);
	for (num_expression = 0;
			(filter_passed) && (num_expression < filter->num_expressions);
			num_expression++) {
		data_len_t expr_len = *expression_pair;
		PRINTF(5, "checking expression %u, length %u\n", num_expression,
				expr_len);
		expression_spec = expression_pair + sizeof(data_len_t);
		expression_eval_set_custom_input(&event_custom_input_function,
				(void*) event);
		filter_passed = expression_eval_evaluate(expression_spec, expr_len);
		PRINTF(5, "eval %u\n", filter_passed);
		expression_pair += expr_len + sizeof(data_len_t);
	}

	PRINTF(2, "(EVENT-MGR) Filter passed? %u\n", filter_passed);

	if (filter_passed) {
		/** Event passed the filter, hence:
		 * 1. create a clone of the event,
		 * 2. change the channel id to the simple filter's channel id,
		 * 3. change the "event operator id" of the event, and
		 * 4. publish it.
		 *
		 * We need to adjust the channel id, because otherwise the cloned event will be
		 * fed to the simple filter again, while what we want is that it is evaluated by
		 * the parent event operator (or sent to the root if this filter is the top level
		 * event operator). */
		struct event *cloned_event = event_clone(event, event_len);

		if (cloned_event == NULL)
			return;

		struct event_processing_request *ev_request = malloc(
				sizeof(struct event_processing_request));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for event req @%p\n",
				sizeof(struct event_processing_request), ev_request);

		if (ev_request == NULL) {
			free(cloned_event);
			return;
		}

		ev_request->event = cloned_event;
		ev_request->request_type = EVENT_PROCESSING_REQUEST;

		/** change the channel id to the simple filter's channel id */
		cloned_event->channel_id = reo->geo->channel_id;
		/** change the "event operator id" of the event */
		event_set_value(cloned_event, EVENT_OPERATOR_ID_F,
				&(reo->geo->event_operator_id));

		list_add(event_mgr_requests, ev_request);

		process_post(&ukuflow_event_mgr_engine_pt,
				event_mgr_request_ready_event, ev_request);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief Adds to the list of scope_ids the scope id of this simple filter by looking recursively at input events
 *
 * @param[in] geo
 * @param[in] ev_op_len
 * @param[in] current
 * @param[in] scope_ids
 */
static void simple_filter_list_scopes(struct generic_event_operator *geo,
		struct generic_event_operator **current, struct simple_list_struct *ls) {

	/** get pointer to child event operator: */
	struct generic_event_operator *child =
			(struct generic_event_operator *) ((uint8_t*) geo
					+ event_operator_get_size(geo));

	/** advance current pointer */
	*current = child;
	event_operators[child->ev_op_type]->list_scopes(child, current, ls);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Produces the output of a processing composition function-event operator
 *
 * 				At this node, the timer that triggers the generation of the (possibly
 * 				intermediate) event output has expired. Depending on whether this node is
 * 				a leave/inner node (or by the contrary, a root node), the output must be
 * 				sent to the parent (or evaluated and published, correspondingly).
 *
 * @param[in] ptr the running event operator
 */
static void pcf_produce(void *ptr) {
	struct pcf_running_event_op *pcf_reo = (struct pcf_running_event_op *) ptr;

	PRINTF(2, "(EVENT-MGR) pcf_produce, %u events\n",
			list_length(pcf_reo->event_buffer));

	if (list_length(pcf_reo->event_buffer) == 0)
		return;

	/** Get event to output: */
	struct event_node *event_node = list_pop(pcf_reo->event_buffer);

	scope_id_t *scope_id = event_get_value(event_node->event_ptr,
			ORIGIN_SCOPE_F);

	/** Now we need to decide whether this event needs to be evaluated and published
	 * locally at root, or forwarded to parent */
	if (*scope_id == LOCAL_EVENT_GENERATOR || scopes_creator_of(*scope_id)) {
		/** must be evaluated and published locally */

		event_operators[pcf_reo->geo->ev_op_type]->evaluate(
				(struct running_event_op*) pcf_reo, event_node->event_ptr,
				event_get_len(event_node->event_ptr));

	} else {
		/** must be sent to parent */
		forward_event_to_parent(event_node->event_ptr,
				event_get_len(event_node->event_ptr));

		PRINTF(2, "(EVENT-MGR) freed event @%p\n", event_node->event_ptr);
		/** Release the event memory */
		free(event_node->event_ptr);

	}

	PRINTF(2, "(EVENT-MGR) freed event node @%p\n", event_node);
	free(event_node);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 * @param[in] event
 * @param[in] event_payload_len
 */
static void pcf_evaluate(struct running_event_op *reo,
		struct event *received_event, data_len_t event_payload_len) {
	struct pcf_running_event_op *pcf_reo = (struct pcf_running_event_op *) reo;

	struct event_processing_request *ev_request = malloc(
			sizeof(struct event_processing_request));

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for event req @%p\n",
			sizeof(struct event_processing_request), ev_request);

	if (ev_request == NULL)
		return;

	struct event *event = NULL;

	switch (reo->geo->ev_op_type) {
	case (MIN_COMPOSITION_EF): /**  	intentionally blank */
	case (MAX_COMPOSITION_EF): {
		// in this case nothing needs to be done to the received_event, as it is ready for its publication
		break;
	}
	case (COUNT_COMPOSITION_EF): {
		// in this case we must simply put the count in as the result of the count and the sum
		event = event_alloc(5, EVENT_TYPE_F, EVENT_OPERATOR_ID_F, MAGNITUDE_F,
				TIMESTAMP_F, ORIGIN_SCOPE_F);

		if (event) {
			event_type_t ev_type = COMPOSITE_EVENT;
			event_set_value(event, EVENT_TYPE_F, (uint8_t*) &ev_type);
			event_set_value(event, EVENT_OPERATOR_ID_F,
					(uint8_t*) &pcf_reo->geo->event_operator_id);
			event_set_value(event, MAGNITUDE_F,
					(uint8_t*) event_get_value(received_event, COUNT_F));
			event_set_value(event, ORIGIN_SCOPE_F,
					event_get_value(received_event, ORIGIN_SCOPE_F));
//			printf("eval final magnitude %u\n",
//					*((uint16_t*) event_get_value(event, MAGNITUDE_F)));

			PRINTF(2, "(EVENT-MGR) engine pt, freed event @%p\n",
					received_event);
			/** Release the event memory */
			free(received_event);
		}
		break;
	}
	case (SUM_COMPOSITION_EF): {
		// in this case nothing needs to be done to the received_event, as it is ready for its publication
		break;
	}
	case (AVG_COMPOSITION_EF): {
		// in this case we must put the avg as the result of the count and the sum
		break;
	}
	case (STDEV_COMPOSITION_EF): {
		// in this case we must calculate the stdev
		break;
	}
	}

	if (event == NULL) {
		free(ev_request);
		return;
	}

	ev_request->event = event;
	ev_request->request_type = EVENT_PROCESSING_REQUEST;
	ev_request->event->channel_id = pcf_reo->geo->channel_id;

	list_add(event_mgr_requests, ev_request);

	PRINTF(5, "(EVENT-MGR) len of list of reqs: %u\n",
			list_length(event_mgr_requests));

	process_post(&ukuflow_event_mgr_engine_pt, event_mgr_request_ready_event,
			ev_request);
}

/*---------------------------------------------------------------------------*/
/**
 *
 * @param[in] ptr
 */
static void pcf_inter_interval_trigger(void *ptr) {
	struct pcf_running_event_op *pcf_reo = (struct pcf_running_event_op *) ptr;

	struct processing_composition_filter *pcf_eo =
			(struct processing_composition_filter *) pcf_reo->geo;

	/** Check if inter-interval ctimer needs to be set or restarted */
	if (!pcf_reo->initialized) {
		pcf_reo->initialized = TRUE;
		ctimer_set(&pcf_reo->inter_interval_ctimer,
				pcf_eo->window_size * CLOCK_SECOND, pcf_inter_interval_trigger,
				ptr);
	} else
		ctimer_reset(&pcf_reo->inter_interval_ctimer);

	/** Now prepare things for intra-interval ctimer */

	/** Find out height of node in scope's routing tree, for this we need to identify one scope */
	struct simple_list_struct ls;
	collect_scope_list((struct generic_event_operator*) pcf_eo, &ls);

	struct scope_id_node *scope_id_node = (struct scope_id_node *) list_head(
			ls.list);

	if (scope_id_node != NULL) {
		uint8_t node_height = scopes_height_of(scope_id_node->scope_id);
		clock_time_t slot_duration = pcf_eo->window_size / MAX_TREE_HEIGHT;
		clock_time_t lower_bound = ((MAX_TREE_HEIGHT - node_height - 1)
				* slot_duration) * CLOCK_SECOND;
		clock_time_t random_delay = random_rand()
				% (slot_duration * 2 * CLOCK_SECOND / 3); /** two thirds of a slot */

		PRINTF(5, "(EVENT-MGR) pcf's random timer: %lu\n",
				lower_bound + random_delay);

		ctimer_set(&pcf_reo->intra_interval_ctimer, lower_bound + random_delay,
				pcf_produce, pcf_reo);

	}

	free_list(&ls);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Initializes a processing composition filter (pcf)
 *
 * @param[in]	geo generic event operator to process
 * @param[in]	local boolean value indicating whether the operator to process was started locally or not
 * @param[in]	subscription the subscription to which the possibly created running event operator needs to be linked
 */
static void pcf_init(struct generic_event_operator *geo, bool local,
		struct generic_subscription *subscription) {

	struct running_event_op *reo;

	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) (((uint8_t*) geo)
					+ event_operator_get_size(geo));

	/** First, continue initialization recursively:*/
	PRINTF(5,
			"(EVENT-MGR) pcf init before recursion, channel id %u, deployed %u\n",
			channel_id, deployed);
	event_operators[next_geo->ev_op_type]->init(next_geo, local, subscription);
	PRINTF(5,
			"(EVENT-MGR) pcf init after recursion, channel id %u, deployed %u\n",
			channel_id, deployed);

	/** Second, check whether this composition event operator needs to be initialized at this node.
	 * This must be done iff this node is member, forwarder or creator of the scopes it recursively relates to.
	 * Since the scope might not have been created yet, and thus we can't know if this node creator of the scope,
	 * we rely on this initialization being started locally at the manager node
	 */

	/** boolean value to find out whether this filter operator needs to be deployed in this node */
	bool deploy_here = FALSE;

	if (scopes_member_of(deployment_scope_id)
			|| scopes_forwarder_of(deployment_scope_id)
			|| scopes_creator_of(deployment_scope_id) || local)
		deploy_here = TRUE;

	if (deploy_here) {

		if ((reo = get_running_event_op(geo)) == NULL) {
			PRINTF(2, "(EVENT-MGR) will create pcf reo\n");
			reo = alloc_running_ev_op(geo, subscription);

			if (reo != NULL) {
				struct pcf_running_event_op *pcf_reo =
						(struct pcf_running_event_op *) reo;
				/** First initialize pcf_reo's fields (list and inter-interval ctimer): */
				LIST_STRUCT_INIT(pcf_reo, event_buffer);
				pcf_reo->initialized = FALSE;
				pcf_inter_interval_trigger(pcf_reo);
			}
		}

		/** only continue deploying if it was possible to allocate running event operator */
		if ((deployed = (reo != NULL))) {
			PRINTF(2,
					"(EVENT-MGR) pcf reo is not null, is @%p: %u %u %u, event lis %u\n",
					reo, channel_id, geo->channel_id, reo->input_channel_id,
					list_length(
							((struct pcf_running_event_op* ) reo)->event_buffer));
//			if (list_length(((struct pcf_running_event_op*) reo)->event_buffer)
//					> 0) {
//				struct event_node *ev_n = list_head(
//						((struct pcf_running_event_op*) reo)->event_buffer);
////				printf("what was here:\n");
////				event_print(ev_n->event_ptr, event_get_len(ev_n->event_ptr));
//			}
		}

	} /** if operator can be deployed here */

	/** Indicate that this operator has not been fully deployed locally (because it is a processing composition event filter) */
	deployed = FALSE;

	/** Update channel_id as input for next event operator */
	channel_id = geo->channel_id;

	PRINTF(2, "(EVENT-MGR) pcf init, recursive channel id set to %u\n",
			channel_id);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Removes this processing composition filter from this node
 *
 * 				Removes a running event operator of a processing composition filter passed as parameter.
 * 				First, recursively eliminates the nested event operator.
 *
 * @param[in]	geo Generic event operator to be used to find the running event operator(s) to delete
 * @param[in]	local boolean value indicating whether the operator to process was started locally or not
 */
static void pcf_remove(struct generic_event_operator *geo, bool local) {

	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) (((uint8_t*) geo)
					+ event_operator_get_size(geo));

	/** First, continue removal recursively: */
	PRINTF(5, "(EVENT-MGR) pcf remove before recursion, num reo %d\n",
			list_length(running_event_ops));
	event_operators[next_geo->ev_op_type]->remove(next_geo, local);
	PRINTF(5, "(EVENT-MGR) pcf remove after recursion, num reo %d\n",
			list_length(running_event_ops));

	/** Second, check whether this pcf event operator was running
	 * at this node and in that case, remove it */
	struct running_event_op *reo;
	if ((reo = get_running_event_op(geo)) != NULL) {
		struct pcf_running_event_op *pcf_reo =
				(struct pcf_running_event_op *) reo;
		list_remove(running_event_ops, reo);
		PRINTF(2, "(EVENT-MGR) freed pcf reo @%p\n", reo);
		ctimer_stop(&pcf_reo->inter_interval_ctimer);
		ctimer_stop(&pcf_reo->intra_interval_ctimer);

		struct event_node *event_node;
		while ((event_node = list_pop(pcf_reo->event_buffer)) != NULL) {
			PRINTF(2, "(EVENT-MGR) freed event @%p\n", event_node->event_ptr);
			/** Release the event memory */
			free(event_node->event_ptr);

			PRINTF(2, "(EVENT-MGR) freed event node @%p\n", event_node);
			/** Release the event node's memory */
			free(event_node);
		}
		free(reo);
		PRINTF(2, "(EVENT-MGR) After pcf remove, num reo %d\n",
				list_length(running_event_ops));
	} else
	PRINTF(2,
			"(EVENT-MGR) didn't remove pcf reo because not found for geo with channel id %u, num reos %u\n",
			geo->channel_id, list_length(running_event_ops));
}

/*---------------------------------------------------------------------------*/
/**
 * \brief Adds to the list of scope_ids the scope id of this simple filter by looking recursively at input events
 *
 * @param geo
 * @param ev_op_len
 * @param current
 * @param scope_ids
 */
static void pcf_list_scopes(struct generic_event_operator *geo,
		struct generic_event_operator **current, struct simple_list_struct *ls) {

	/** get pointer to child event operator: */
	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) ((uint8_t*) geo
					+ event_operator_get_size(geo));

	/** advance current pointer */
	*current = next_geo;
	event_operators[next_geo->ev_op_type]->list_scopes(next_geo, current, ls);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Consumes an event by computing its processing composition function against
 *  			the events previously received in this slot
 *
 * @param[in] reo the running event operator which needs to be used to consume the event
 * @param[in] received_event the event to consume
 * @param[in] event_payload_len the length of the event to consume
 */
static void pcf_consume(struct running_event_op *reo,
		struct event *received_event, data_len_t event_payload_len) {

	PRINTF(1, "(EVENT-MGR) pcf consuming event %p\n", received_event);

	struct pcf_running_event_op *pcf_reo = (struct pcf_running_event_op *) reo;
	struct processing_composition_filter *pcf_eo =
			(struct processing_composition_filter *) reo->geo;

	struct event *local_event = NULL;
	struct event_node *local_event_node = NULL;

	switch (pcf_eo->ev_op_type) {
	case (COUNT_COMPOSITION_EF): {
		local_event_node = list_head(pcf_reo->event_buffer);
		uint16_t local_count = 0, received_count = 0, resulting_count = 0;

		if (local_event_node == NULL) {
			/** no previous event, thus create one with magnitude 0 */

			local_event_node = malloc(sizeof(struct event_node));

			PRINTF(2, "(EVENT-MGR) alloc %u bytes for event_node @%p\n",
					sizeof(struct event_node), local_event_node);

			//data_len_t event_len;

			if ((local_event_node != NULL)
					&& ((local_event = (local_event_node->event_ptr =
							event_alloc(6, EVENT_TYPE_F, EVENT_OPERATOR_ID_F,
									COUNT_F, TIMESTAMP_F, ORIGIN_NODE_F,
									ORIGIN_SCOPE_F))) != NULL)) {
				/** It was possible to allocate both the local_event_node and the local_event */

				local_event->channel_id = pcf_reo->input_channel_id;
				event_type_t ev_type = COMPOSITE_EVENT;
				event_set_value(local_event, EVENT_TYPE_F, (uint8_t*) &ev_type);
				event_set_value(local_event, EVENT_OPERATOR_ID_F,
						(uint8_t*) &pcf_eo->event_operator_id);
				event_set_value(local_event, ORIGIN_NODE_F,
						(uint8_t*) &rimeaddr_node_addr);

				/** Find out scope through which this event needs to be sent */
				struct simple_list_struct ls;
				collect_scope_list((struct generic_event_operator*) pcf_eo,
						&ls);
				struct scope_id_node *scope_id_node =
						(struct scope_id_node *) list_head(ls.list);
				if (scope_id_node != NULL)
					event_set_value(local_event, ORIGIN_SCOPE_F,
							&scope_id_node->scope_id);
				free_list(&ls);

				local_count = 0;

				list_add(pcf_reo->event_buffer, local_event_node);
//			printf("added ev to pcf_reo, len is %u\n",
//					list_length(pcf_reo->event_buffer));
			}
		} else {
//		printf("there was previous\n");
			local_event = local_event_node->event_ptr;
			memcpy(&local_count,
					event_get_value(local_event_node->event_ptr, COUNT_F),
					event_get_field_len(COUNT_F));
		}

		if ((local_event_node == NULL) || (local_event == NULL)) {
			/** Not possible to find or allocate a local event or its local_event_node, so escape */
			return;
		}

		/** Now we need to consume the incoming received_event with the newly created received_event.
		 * Depending on whether the incoming event is simple or composite, we need to */
		uint8_t ev_type;
		memcpy(&ev_type, event_get_value(received_event, EVENT_TYPE_F),
				event_get_field_len(EVENT_TYPE_F));

		if (ev_type == SIMPLE_EVENT) {
			received_count = 1;
		} else if (ev_type == COMPOSITE_EVENT) {
			memcpy(&received_count, event_get_value(received_event, COUNT_F),
					event_get_field_len(COUNT_F));
		}

		resulting_count = local_count + received_count;

		event_set_value(local_event, COUNT_F, (uint8_t*) &resulting_count);

		clock_time_t curr_time = clock_time() / CLOCK_SECOND;
		event_set_value(local_event, TIMESTAMP_F, (uint8_t*) &curr_time);

		PRINTF(1, "(EVENT-MGR) set count to %u. Event:\n", resulting_count);
		event_print(local_event, event_get_len(local_event));

		break;
	}
	} /** switch */
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 * @param[in]	ptr		pointr to subscription record from which a
 * 						subscription request will be built.
 */
static void reannounce_subscription(void *ptr) {
	struct global_subscription *subscription =
			(struct global_subscription *) ptr;

	PRINTF(7, "(EVENT-MGR) Reannouncing main channel id %u!!!\n",
			subscription->main_ev_op->channel_id);

	if (!ukuflow_event_mgr_subscribe(subscription->main_ev_op,
			subscription->ev_op_len, subscription->notify, subscription->wf))
		ctimer_reset(&subscription->announcement_timer);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Registers interest for a given event operator expression.
 *
 * 				Registers interest for a given event operator expression, passed as parameter.
 * 				Whenever a matching event is detected, the <b>notify</b> function is invoked.
 *
 * @param[in]	main_ev_op  The pointer to the main, generic event operator to be subscribed to
 * @param[in]	ev_op_len   The number of bytes to copy
 * @param[in]	notify      The pointer to the function that must be invoked when a matching event is detected
 * @param[in]	wf          The pointer to the workflow specification, which is used to find the scope specifications when needed
 * \returns                TRUE if subscription succeeded, FALSE otherwise
 */
bool ukuflow_event_mgr_subscribe(/* */ //
		struct generic_event_operator *main_ev_op, //
		data_len_t ev_op_len, //
		void (*notify)(struct event *event, data_len_t event_payload_len), //
		struct workflow *wf) {

	struct subscription_request *sub_request = malloc(
			sizeof(struct subscription_request));

	PRINTF(2, "(EVENT-MGR) alloc %u bytes for sub request @%p\n",
			sizeof(struct subscription_request), sub_request);

	if (sub_request == NULL)
		return (FALSE);

	sub_request->request_type = SUBSCRIPTION_REQUEST;
	sub_request->main_ev_op = main_ev_op;
	sub_request->ev_op_len = ev_op_len;
	sub_request->notify = notify;
	sub_request->wf = wf;

	list_add(event_mgr_requests, sub_request);

	process_post(&ukuflow_event_mgr_engine_pt, event_mgr_request_ready_event,
			sub_request);

	return (TRUE);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		At this node, a scope is about to be removed. Delete running event operators
 * 				that are associated to that scope id.
 *
 * 				TODO
 *
 * @param[in]	scope_id	Scope id of the scope which is being removed
 */
void ukuflow_event_mgr_scope_removed(scope_id_t scope_id) {
	PRINTF(7, "(EVENT-MGR) about to leave scope %u\n", scope_id);

	struct running_event_op *reo = NULL, *next = NULL;
	struct generic_subscription *sub;
	bool reo_deleted;

	PRINTF(5, "(EVENT-MGR) inspecting %u reos and %u subs\n",
			list_length(running_event_ops), list_length(subscriptions));
	/** Iterate over running event operators and check whether the conditions for their
	 * existence in this node are still fulfilled or they need to be removed */
	for (reo = list_head(running_event_ops); //
			reo != NULL; //
			reo = next) {
		/** pointer to next in case we delete this reo */
		next = list_item_next(reo);

		reo_deleted = FALSE;
		sub = reo->subscription;

		switch (reo->geo->ev_op_type) {
		case (IMMEDIATE_EG): /**  		intentionally blank */
		case (ABSOLUTE_EG): /**			intentionally blank */
		case (OFFSET_EG): /**			intentionally blank */
		case (RELATIVE_EG): /**			intentionally blank */
		case (PERIODIC_EG): /**			intentionally blank */
		case (PATTERN_EG): /**			intentionally blank */
		case (FUNCTIONAL_EG): {
			struct generic_egen *g_egen = (struct generic_egen*) reo->geo;

			if (g_egen->scope_id == scope_id) {

				/** Invoke removal of running event operator:*/
				event_operators[reo->geo->ev_op_type]->remove(reo->geo,
				FALSE);
				reo_deleted = TRUE;
			}

			break;
		}
		case (SIMPLE_EF): {
			struct simple_list_struct ls;
			collect_scope_list(reo->geo, &ls);
			struct scope_id_node *node = (struct scope_id_node *) list_head(
					ls.list);
			if ((node != NULL) && (node->scope_id == scope_id)) {
				/** Invoke removal of running event operator:*/
				event_operators[reo->geo->ev_op_type]->remove(reo->geo,
				FALSE);
				reo_deleted = TRUE;
			}

			free_list(&ls);
			break;
		}
			//		case (AND_COMPOSITION_EF): // intentionally blank
			//		case (OR_COMPOSITION_EF): // intentionally blank ...
		} // switch

		/** In case that a reo has been deleted, now check if the
		 * subscription has still reo's. If it doesn't have
		 * any more reo's, it must be deleted. */
		if (reo_deleted) {
			bool unsubscribe = TRUE;
			struct running_event_op *reo2 = NULL;
			for (reo2 = list_head(running_event_ops);
					reo2 != NULL && unsubscribe; reo2 = list_item_next(reo2)) {
				if (reo2->subscription == sub)
					unsubscribe = FALSE;
			}
			if (unsubscribe) {
				PRINTF(5,
						"(EVENT-MGR) deleting sub because no more reos for sub \n");
				ukuflow_event_mgr_handle_unsubscription(
						sub->main_ev_op->channel_id, FALSE);
			} // if
			else
			PRINTF(5,
					"(EVENT-MGR) not deleting because there were reos for sub\n");

			PRINTF(5, "(EVENT-MGR) reo is %p, next is %p, list has %u\n", reo,
					next, list_length(running_event_ops));
		}

	} // for
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Adds a request to unsubscribe from the specified event operator
 *
 *
 * @param[in]	main_ev_op	pointer to main event operator
 * @param[in]	token		pointer to workflow token to use once unsubscription is through
 *
 */
bool ukuflow_event_mgr_unsubscribe(struct generic_event_operator *main_ev_op,
		struct workflow_token* token) {

	struct generic_subscription *generic_sub = get_subscription(
			main_ev_op->channel_id);

	if ((generic_sub != NULL)
			&& (generic_sub->subscription_type == GLOBAL_SUBSCRIPTION)) {

		struct unsubscription_request *unsub_request = malloc(
				sizeof(struct unsubscription_request));

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for unsub request @%p\n",
				sizeof(struct unsubscription_request), unsub_request);

		if (unsub_request) {

			unsub_request->request_type = UNSUBSCRIPTION_REQUEST;
			unsub_request->subscription =
					(struct global_subscription*) generic_sub;
			unsub_request->token = token;
			list_add(event_mgr_requests, unsub_request);

			ctimer_stop(&unsub_request->subscription->announcement_timer);

			process_post(&ukuflow_event_mgr_engine_pt,
					event_mgr_request_ready_event, unsub_request);

			return (TRUE);
		}
	}
	return (FALSE);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 * @param event the event to forward
 * @param event_len the length of the event that needs to be forwarded
 */
static void forward_event_to_parent(struct event *event, data_len_t event_len) {

	scope_id_t *scope_id;
	struct global_subscription *global_sub;

	static data_len_t msg_size;
	static uint8_t *msg_data;

	/** If control arrived here, it means that there was no local event operator that
	 * matched the channel, thus the event was not consumed locally.
	 * Hence, depending on whether the node is root or not,
	 * the event must be passed to the parent node: */
	scope_id = (scope_id_t*) event_get_value(event, ORIGIN_SCOPE_F);

	event_print(event, event_len);

	if ((*scope_id == LOCAL_EVENT_GENERATOR) || scopes_creator_of(*scope_id)) {
		/** This node is scope root (creator), so process locally by notifying the subscriber*/
		PRINTF(7,
				"(EVENT-MGR) engine pt, event reached root, notifying subscriber \n");

		/** We arrived at the root, so look up the subscription and invoke the registered notification function */

		PRINTF(4, "(EVENT-MGR) engine pt, will search among %u subscriptions\n",
				list_length(subscriptions));
		global_sub = (struct global_subscription*) get_subscription(
				event->channel_id);

		if (global_sub != NULL) {

			PRINTF(7,
					"(EVENT-MGR) engine pt, subscription found %p, notify ptr %p\n",
					global_sub, global_sub->notify);
			global_sub->notify(event, event_len);
		} else {
			PRINTF(7,
					"(EVENT-MGR) engine pt, subscription not found! (out-of-order event?)\n");
		}

	} else {
		/** This node is not scope root (creator), so forward */
		PRINTF(7,
				"(EVENT-MGR) forwarding event %p with len %u to parent scope %u, member %u\n",
				event, event_len, *scope_id, scopes_member_of(*scope_id));
		msg_size = sizeof(struct ukuflow_event_msg) + event_len;
		msg_data = malloc(msg_size);

		PRINTF(2, "(EVENT-MGR) alloc %u bytes for event msg @%p\n", msg_size,
				msg_data);

		if (msg_data != NULL) {
			PRINT_ARR(3, (uint8_t* )event, event_len);
			struct ukuflow_event_msg *event_msg =
					(struct ukuflow_event_msg *) msg_data;
			event_msg->msg_type = SCOPED_EVENT_MSG;
			event_msg->event_payload_len = event_len;
			memcpy(msg_data + sizeof(struct ukuflow_event_msg), event,
					event_len);
			ukuflow_net_mgr_send_scope(*scope_id, TRUE, msg_data, msg_size);

			/** Release the scoped message memory */
			PRINTF(2, "(EVENT-MGR) engine pt, freed event msg @%p\n", msg_data);
			free(msg_data);
		} // message was not null
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Protothread for the managing of event tasks.
 *
 * 				This protothread is in charge of processing subscription and unsubscriptions,
 * 				as well as of chaining events between event operators.
 * 				The protothread sleeps until a request is posted for processing, e.g.
 * 				when an event operator generates its event output.
 *
 * 				When the request is about processing an event, the protothread scans
 * 				for further, running event operators at this node.
 * 				If one is found, the event is passed to it by invoking the 'evaluate'
 * 				function of the found operator.
 * 				If no running event operator is found, it means that the event
 * 				was generated by the main event operator of a subscription, thus
 * 				it has to be sent to the root, where it will be used to invoke
 * 				the notify function registered with the subscription.
 */ //
PROCESS_THREAD( ukuflow_event_mgr_engine_pt, ev, data) {

	/** Variables for dealing with (un)sub requests */
	static struct event_mgr_request *generic_request;
	static struct subscription_request *sub_request;
	static struct unsubscription_request *unsub_request;
	static struct generic_subscription *generic_sub;
	static struct global_subscription *global_sub;
	static struct etimer event_mgr_control_timer;
	static struct simple_list_struct ls;
	static struct scope_id_node *sid_node;
	static struct ukuflow_sub_msg *sub_msg;
	static struct ukuflow_unsub_msg unsub_msg;
	static struct scope_info *s_i;
	static bool new_sub;

	/** Variables for dealing with event processing requests */
	static struct event_processing_request *ev_request;
	static struct event *event;
	static data_len_t event_len;
	static struct running_event_op *reo;
	static bool consumed_locally;

	PROCESS_BEGIN()
		;

		/** Prepare unsubscription message in advance */
		unsub_msg.msg_type = SCOPED_EVENT_OPERATOR_UNSUB_MSG;

		while (1) {

			/** This process takes a request from the queue, and processes it
			 * assuming the necessary resources are available (memory, scope information, etc.)
			 * */

			while ((generic_request = list_head(event_mgr_requests)) == NULL) {
				PRINTF(7,
						"(EVENT-MGR) engine pt, yielding til a request is ready\n");
				PROCESS_WAIT_EVENT_UNTIL(ev == event_mgr_request_ready_event);
			}

			PRINTF(7,
					"(EVENT-MGR) engine pt, continuing with request type %u, queue len %u\n",
					generic_request->request_type,
					list_length(event_mgr_requests));
			/*---------------------------------------------------------------------------*/
			if (generic_request->request_type == SUBSCRIPTION_REQUEST) {

				sub_request = (struct subscription_request*) generic_request;
				PRINTF(7,
						"(EVENT-MGR) engine pt, processing subscription for ev. channel %u\n",
						sub_request->main_ev_op->channel_id);
				new_sub = FALSE;

				/** Find out whether subscription already exists (i.e. this is a re announcement): */
				if ((generic_sub = get_subscription(
						sub_request->main_ev_op->channel_id)) == NULL) {

					/** Subscription didn't exist, so allocate memory for it */
					generic_sub =
							(struct generic_subscription*) alloc_global_subscription(
									sub_request->main_ev_op,
									sub_request->ev_op_len, sub_request->notify,
									sub_request->wf);
					if (generic_sub != NULL)
						new_sub = TRUE;
				}

				/** Check if it was possible to allocate memory for the subscription by comparing pointer against NULL */
				/** Also check if that pointer points to a subscription of type 'global' */

				if ((generic_sub == NULL)
						|| (generic_sub->subscription_type
								!= GLOBAL_SUBSCRIPTION)) {
					/** It wasn't possible to allocate memory, or, the subscription found is of wrong type.
					 * Hence wait a bunch of seconds and retry */
					etimer_set(&event_mgr_control_timer,
					SCOPE_OPERATION_DELAY * CLOCK_SECOND);
					PROCESS_WAIT_EVENT_UNTIL(
							etimer_expired(&event_mgr_control_timer));
					continue;
				}

				global_sub = (struct global_subscription*) generic_sub;

				/** Add global subscription to local list */
				list_add(subscriptions, global_sub);

				/** Deploy this global subscription locally */
				ukuflow_event_mgr_handle_subscription(global_sub->main_ev_op,
						global_sub->ev_op_len,
						TRUE);

				/** Now get list of scopes associated to expression */
				collect_scope_list(global_sub->main_ev_op, &ls);

				/** If list contains any scope id, send subscription
				 * to them (after opening them if necessary)! */
				if (list_length(ls.list) > 0) {

					/** Allocate memory for network message that will contain the subscription data and will be sent through scope */
					while ((sub_msg = malloc(
							sizeof(struct ukuflow_sub_msg)
									+ global_sub->ev_op_len)) == NULL) {

						/** If there was no memory free for message, postpone processing of subscription request */
						PRINTF(2,
								"(EVENT-MGR) No memory free (%u bytes) for sub msg, waiting couple sec\n",
								sizeof(struct ukuflow_sub_msg)
										+ global_sub->ev_op_len);
						etimer_set(&event_mgr_control_timer,
						SCOPE_OPERATION_DELAY * CLOCK_SECOND);
						PROCESS_WAIT_EVENT_UNTIL(
								etimer_expired(&event_mgr_control_timer));
					}

					PRINTF(2, "(EVENT-MGR) alloc %u bytes for sub msg @%p\n",
							sizeof(struct ukuflow_sub_msg)
									+ global_sub->ev_op_len, sub_msg);

					/** Prepare subscription message: */
					sub_msg->msg_type = SCOPED_EVENT_OPERATOR_SUB_MSG;
					memcpy(
							((uint8_t*) sub_msg)
									+ sizeof(struct ukuflow_sub_msg),
							global_sub->main_ev_op, global_sub->ev_op_len);

					/** Iterate over each scope id of the expression */
					for (sid_node = list_head(ls.list); sid_node != NULL;
							sid_node = list_item_next(sid_node)) {
						if (sid_node->scope_id != LOCAL_EVENT_GENERATOR) {
							/** Subscription has event operators that need to be deployed through a scope. */

							/** Find out whether we need to open the scope.
							 * This must be done iff:
							 * - the event generator is not associated to the 'world' scope
							 * - this is a new subscription (new_sub), i.e. it is not a reannouncement
							 * (if it is a reannouncement, the scope should have already been open the previous time)
							 **/
							if ((sid_node->scope_id != SCOPES_WORLD_SCOPE_ID)
									&& (new_sub)) {

								PRINTF(2,
										"(EVENT-MGR) engine pt, event expression to be sent through scope %u\n",
										sid_node->scope_id);
								s_i = workflow_get_scope_info(global_sub->wf,
										sid_node->scope_id);

								/** Test if scope information is available and whether opening the scope works.
								 * In case opening the scope fails, it won't be possible sending through it, so cancel */
								if ((s_i != NULL) && //
										ukuflow_net_mgr_open_scope(
												sid_node->scope_id,
												((uint8_t*) s_i)
														+ sizeof(struct scope_info),
												s_i->scope_spec_len,
												s_i->scope_ttl)) {

									PRINTF(2,
											"(EVENT-MGR) engine pt, opened scope %u, waiting some seconds\n",
											s_i->scope_id);
									/** Now wait a bunch of seconds for the scope creation to succeed */
									etimer_set(&event_mgr_control_timer,
									SCOPE_OPERATION_DELAY * CLOCK_SECOND);
									PROCESS_WAIT_EVENT_UNTIL(
											etimer_expired(
													&event_mgr_control_timer));

								} else {
									PRINTF(2,
											"(EVENT-MGR) engine pt, ERROR s_i %p, sid %u\n",
											s_i, sid_node->scope_id);
									continue;
								}

							}

							/** Send through Scopes to the member nodes: */
							ukuflow_net_mgr_send_scope(sid_node->scope_id,
							FALSE, sub_msg,
									sizeof(struct ukuflow_generic_msg)
											+ global_sub->ev_op_len);

							PRINTF(2,
									"(EVENT-MGR) engine pt, sent subscription through scope %u, waiting some seconds\n",
									s_i->scope_id);
							/** Now wait a bunch of seconds for the message to be disseminated */
							etimer_set(&event_mgr_control_timer,
							SCOPE_OPERATION_DELAY * CLOCK_SECOND);
							PROCESS_WAIT_EVENT_UNTIL(
									etimer_expired(&event_mgr_control_timer));

						}
					}				// for each scope

					PRINTF(2, "(EVENT-MGR) engine pt, freed sub msg @%p\n",
							sub_msg);
					/** Release memory of subscription message */
					free(sub_msg);
				}

				/** Now empty list of scope ids */
				free_list(&ls);

				/** If this was a new global subscription (instead of a re announcement), set the callback timer. */
				if (new_sub)
					ctimer_set(&global_sub->announcement_timer,
					MAX_SUB_TTL * SUBSCRIPTION_REANNOUNCE_FACTOR,
							reannounce_subscription, global_sub);
				else
					/** otherwise, this is not a new subscription (it's a re-announcement, triggered by a callback timer), thus simply reset the callback timer*/
					ctimer_reset(&global_sub->announcement_timer);

				PRINTF(7, "(EVENT-MGR) engine pt, finished subscribing\n");
			} // if (generic_request->request_type == SUBSCRIPTION_REQUEST) {
			/*---------------------------------------------------------------------------*/
			else if (generic_request->request_type == UNSUBSCRIPTION_REQUEST) {

				unsub_request =
						(struct unsubscription_request*) generic_request;
				PRINTF(7,
						"(EVENT-MGR) engine pt, processing unsubscription for channel %u\n",
						unsub_request->subscription->main_ev_op->channel_id);

				PRINTF(5,
						"(EVENT-MGR) engine pt, there are %u subscriptions in the list\n",
						list_length(subscriptions));

				global_sub = unsub_request->subscription;

				PRINTF(5,
						"(EVENT-MGR) engine pt, about to unsub channel id %u\n",
						global_sub->main_ev_op->channel_id);
				/** Deregister global subscription locally*/
				ukuflow_event_mgr_handle_unsubscription(
						global_sub->main_ev_op->channel_id, TRUE);

				/** Now get list of scopes associated to expression and send an
				 * unsubscription message to them (followed by closing scope)! */
				collect_scope_list(global_sub->main_ev_op, &ls);

				if (list_length(ls.list) > 0) {

					/** Prepare unsubscription message */
					unsub_msg.main_ev_op_channel_id =
							global_sub->main_ev_op->channel_id;

					for (sid_node = list_head(ls.list); sid_node != NULL;
							sid_node = list_item_next(sid_node)) {
						if (sid_node->scope_id != LOCAL_EVENT_GENERATOR) {
							/** Subscription has event operators that need to be undeployed through a scope. */

							/** Send unsub message through Scopes to the member nodes: */
							ukuflow_net_mgr_send_scope(sid_node->scope_id,
							FALSE, &unsub_msg,
									sizeof(struct ukuflow_unsub_msg));

							PRINTF(4,
									"(EVENT-MGR) engine pt, sent unsubscription through scope %u, waiting some seconds\n",
									s_i->scope_id);
							/** Now wait a bunch of seconds for the unsub message to be disseminated */

							PRINTF(7,
									"(EVENT-MGR) engine pt, before etimer, sid_node is %p, scope id is %u\n",
									sid_node, sid_node->scope_id);
							etimer_set(&event_mgr_control_timer,
							SCOPE_OPERATION_DELAY * CLOCK_SECOND);
							PROCESS_WAIT_EVENT_UNTIL(
									etimer_expired(&event_mgr_control_timer));
							PRINTF(7,
									"(EVENT-MGR) engine pt, after etimer, sid_node is %p, scope id is %u\n",
									sid_node, sid_node->scope_id);

							/** Now close the scope, unless it is the world scope */
							if (sid_node->scope_id != SCOPES_WORLD_SCOPE_ID) {

								ukuflow_net_mgr_close_scope(sid_node->scope_id);

								PRINTF(7,
										"(EVENT-MGR) engine pt, closed scope %u, waiting some seconds\n",
										sid_node->scope_id);

								/** Now wait a bunch of seconds for the scope removal to succeed */
								etimer_set(&event_mgr_control_timer,
										(SCOPE_OPERATION_DELAY / 1)
												* CLOCK_SECOND);
								PROCESS_WAIT_EVENT_UNTIL(
										etimer_expired(
												&event_mgr_control_timer));

							} // if (! scope is scope_world)

						} // scope id != local

					} // for (each scope id)

					/** Now empty list of scope ids */
					free_list(&ls);

					ukuflow_notify_unsubscription_ready(unsub_request->token);

				} // if (generic_sub != NULL)

				PRINTF(5,
						"(EVENT-MGR) engine pt, now there are %u subscriptions in the list\n",
						list_length(subscriptions));

			} // if (generic_request->request_type == UNSUBSCRIPTION_REQUEST)
			/*---------------------------------------------------------------------------*/
			else if (generic_request->request_type
					== EVENT_PROCESSING_REQUEST) {
				ev_request = (struct event_processing_request*) generic_request;

				event = ev_request->event;

				event_len = event_get_len(event);

				PRINTF(2,
						"(EVENT-MGR) engine pt, finished yielding, event %p, len %u, channel %u\n",
						event, event_len, event->channel_id);

				/** look for running event operators interested in consuming the event being published */
				consumed_locally = FALSE;
				for (reo = list_head(running_event_ops); reo != NULL; reo =
						list_item_next(reo)) {

					/** Test whether output channel (contained in event) matches input
					 * channel of local event operators: */
					if (reo->input_channel_id == event->channel_id) {
						/** there was a match */
						PRINTF(2,
								"(EVENT-MGR) engine pt, event match with reo @%p, try to consume\n",
								reo);
						consumed_locally = TRUE;
						event_operators[reo->geo->ev_op_type]->consume(reo,
								event, event_len);
					}
				}

				if (!consumed_locally) {
					forward_event_to_parent(event, event_len);
				}

				PRINTF(2, "(EVENT-MGR) engine pt, freed event @%p\n", event);
				/** Release the event memory */
				free(event);

			}
			/*---------------------------------------------------------------------------*/
			else {
				PRINTF(7, "(EVENT-MGR) Unknown request type!\n");
			}

			/** Remove request from request list and release its memory */
			list_remove(event_mgr_requests, generic_request);
			PRINTF(2, "(EVENT-MGR) engine pt, freed request @%p\n",
					generic_request);

			free(generic_request);

			PRINTF(2,
					"(EVENT-MGR) engine pt, finished processing, queue len %u\n",
					list_length(event_mgr_requests));
		} // while (1)

	PROCESS_END();
}

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
// Declaration of event operators
EVENT_OPERATOR(immediate_egen_oper, egen_init, egen_remove, NULL, NULL,
	egen_list_scopes)
//EVENT_OPERATOR(absolute_egen_oper, NULLARY | DISTRIBUTABLE, absolute_egen_init, _remove, NULL, NULL)
EVENT_OPERATOR(offset_egen_oper, egen_init, egen_remove, NULL, NULL,
	egen_list_scopes)
//EVENT_OPERATOR(relative_egen_oper, NULLARY | DISTRIBUTABLE, relative_egen_init, _remove, NULL, NULL)
EVENT_OPERATOR(periodic_egen_oper, egen_init, egen_remove, NULL, NULL,
	egen_list_scopes)
EVENT_OPERATOR(pattern_egen_oper, egen_init, egen_remove, NULL, NULL,
	egen_list_scopes)
//EVENT_OPERATOR(functional_egen_oper, NULLARY | DISTRIBUTABLE, distributed_egen_init, _remove, NULL, NULL)
EVENT_OPERATOR(simple_filter_oper, simple_filter_init, simple_filter_remove,
	simple_filter_consume, NULL, simple_filter_list_scopes)
//EVENT_OPERATOR(and_composite_oper, BINARY | DISTRIBUTABLE, and_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(or_composite_oper, BINARY | DISTRIBUTABLE, or_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(not_composite_oper, UNARY | DISTRIBUTABLE, not_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(sequence_composite_oper, UNARY | DISTRIBUTABLE, sequence_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(min_processing_function_oper, UNARY | DISTRIBUTABLE, min_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(max_processing_function_oper, UNARY | DISTRIBUTABLE, max_processing_function_init, _remove, NULL, NULL)
EVENT_OPERATOR(count_pcf_oper, pcf_init, pcf_remove, pcf_consume, pcf_evaluate,
	pcf_list_scopes)
//EVENT_OPERATOR(sum_processing_function_oper, UNARY | DISTRIBUTABLE, sum_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(avg_processing_function_oper, UNARY | DISTRIBUTABLE, avg_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(stdev_processing_function_oper, UNARY | DISTRIBUTABLE, stdev_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(inc_change_oper, UNARY | DISTRIBUTABLE, inc_change_init, _remove, NULL, NULL)
//EVENT_OPERATOR(dec_change_oper, UNARY | DISTRIBUTABLE, dec_change_init, _remove, NULL, NULL)
//EVENT_OPERATOR(rem_change_oper, UNARY | DISTRIBUTABLE, rem_change_init, _remove, NULL, NULL)

/*---------------------------------------------------------------------------*/

/**
 * \brief		Array of active event operators
 */
struct ukuflow_event_operator *event_operators[] = { //
	&immediate_egen_oper, /* */
	NULL, /*						absolute_egen_oper*/
	&offset_egen_oper, /* */
	NULL, /*						relative_egen_oper*/
	&periodic_egen_oper, /* */
	&pattern_egen_oper, /* */
	NULL, /*						functional_egen_oper*/
	&simple_filter_oper, /* */
	NULL, /*						and_composite_filter_oper*/
	NULL, /*						or_composite_filter_oper*/
	NULL, /*						not_composite_filter_oper*/
	NULL, /*						sequence_composite_filter_oper*/
	NULL, /*						min_processing_function_oper*/
	NULL, /*						max_processing_function_oper*/
	&count_pcf_oper, /* */
	NULL, /*						sum_processing_function_oper*/
	NULL, /*						avg_processing_function_oper*/
	NULL, /*						stdev_processing_function_oper*/
	NULL, /*						inc_change_oper*/
	NULL, /*						dec_change_oper*/
	NULL /*							rem_change_oper*/
	};

/*---------------------------------------------------------------------------*/

/** @} */