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

#include "ukuflow-event-mgr.h"
#include "workflow.h"
#include "event.h"

// for memcpy
#include "string.h"

#include "logger.h"

#include "expression-eval.h"

/*---------------------------------------------------------------------------*/
/** \brief Defines the time to live value for event operators running on a node. */
#define MAX_SUB_TTL CLOCK_SECOND * 60 * 3 * 2

/** \brief Defines the factor between */
#define SUBSCRIPTION_REANNOUNCE_FACTOR 1/3

/** \brief TODO */
typedef uint8_t request_type_t;

/** \brief TODO */
enum request_type {
	SUBSCRIPTION_REQUEST = 0, //
	UNSUBSCRIPTION_REQUEST = 1,
//
};

/** \brief Generic request for the event manager */
struct event_mgr_request {
	/** \brief pointer to next element in list */
	struct event_mgr_request *next;
	/** \brief request type */
	request_type_t request_type;
};

/** \brief Subscription request for the event manager */
struct subscription_request {
	/** \brief pointer to next element in list: */
	struct subscription_request *next;
	/** \brief request type */
	request_type_t request_type;
	/** \brief pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
	/** \brief size of the entire event operator expression (sequence of all event operators, in bytes) */
	data_len_t ev_op_len;
	/** \brief what needs to be notified */
	void (*notify)(struct event *event, data_len_t event_payload_len);
	/** \brief where to take scope specifications if needed: */
	struct workflow *wf;
};

/** \brief Unsubscription request for the event manager */
struct unsubscription_request {
	/** \brief pointer to next element in list */
	struct unsubscription_request *next;
	/** \brief request type */
	request_type_t request_type;
	/** \brief pointer to workflow token to use once unsubscription is through */
	struct workflow_token *token;
	/** \brief pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
};

/** \brief List of requests placed to the event manager */
LIST( event_mgr_requests);

/** \brief Data type for the subscription types */
typedef uint8_t subscription_type_t;

/** \brief TODO */
enum subscription_type {
	/** \brief Subscription is managed by this node */
	GLOBAL_SUBSCRIPTION = 0, //
	/** \brief Subscription is managed by another (scope root) node */
	LOCAL_SUBSCRIPTION = 1
};

/**
 * \brief	Generic data structure for a subscription containing an event operator
 */
struct generic_subscription {
	/** Pointer to next subscription in the list */
	struct generic_subscription *next;
	/** Type of subscription */
	subscription_type_t subscription_type;
	/** Length of the byte array containing the event operator(s) */
	data_len_t ev_op_len;
	/** Pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
};

/**
 * \brief	Data structure for a global subscription (i.e., the subscription is managed by this node)
 */
struct global_subscription {
	/** Pointer to next subscription in the list */
	struct global_subscription *next;
	/** Type of subscription */
	subscription_type_t subscription_type;
	/** Length of the byte array containing the event operator(s) */
	data_len_t ev_op_len;
	/** pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
	/** Callback timer indicating when to re announce the subscription */
	struct ctimer announcement_timer;
	/** Pointer to function to be invoked when a matching event is detected */
	void (*notify)(struct event *event, data_len_t event_payload_len);
	/** Pointer to workflow specification to (e.g.) get the specification of the needed scopes */
	struct workflow *wf;
};

/**
 * \brief	Data structure for a local subscription (i.e., the subscription is managed by another, scope root node)
 */
struct local_subscription {
	/** Pointer to next subscription in the list */
	struct local_subscription *next;
	/** Type of subscription */
	subscription_type_t subscription_type;
	/** Length of the byte array containing the event operator(s) */
	data_len_t ev_op_len;
	/** pointer to the byte array containing the entire sequence of event operator(s) */
	struct generic_event_operator *main_ev_op;
	/** Callback timer indicating remaining time to live (to remove the subscription locally) */
	struct ctimer ttl_timer;
};

/** \brief List of subscriptions */
LIST( subscriptions);

/**
 * \brief	Data structure for a running event operator
 */
struct running_event_op {
	/** \brief TODO */
	struct running_event_op *next;
	/** \brief TODO */
	channel_id_t input_channel_id;
	/** \brief TODO */
	struct ctimer event_operator_trigger_ctimer;
	/** \brief TODO */
	struct generic_event_operator *geo;
};

/** \brief List of running event operators */
LIST( running_event_ops);

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
// Flags for event operator properties:
/** \brief Constant for event operators of no input */
#define NULLARY 0x00
/** \brief Constant for event operators of single input */
#define UNARY 0x01
/** \brief Constant for event operators of binary input */
#define BINARY 0x02
/** \brief Constant for event operators of ternary input */
#define TERNARY 0x03
//...
/** \brief Constant for event operators that are distributable */
#define DISTRIBUTABLE 0x0

/**
 * \brief		Generic structure for an event operator
 */
struct ukuflow_event_operator {
	/** \brief TODO */
	uint8_t operator_properties;
	/** \brief Pointer to event operator initialization function */
	void (* init)(struct generic_event_operator *geo);
	/** \brief TODO */
	void (* remove)(struct generic_event_operator *geo);
	/** \brief TODO */
	void (* merge)(struct running_event_op *reo, struct event *event,
			data_len_t event_payload_len);
	/** \brief TODO */
	void (* evaluate)(struct event *event, data_len_t event_payload_len);
};

/** \brief Macro function to specify an event operator */
#define EVENT_OPERATOR(operator_name, operator_properties, init, remove, merge, evaluate)     \
  struct ukuflow_event_operator operator_name = { operator_properties, init, remove, merge, evaluate};

/*---------------------------------------------------------------------------*/
/** Declaration of static processes */
PROCESS(ukuflow_event_mgr_request_process, "subscription process");
static struct etimer event_mgr_control_timer;

/** \brief TODO */
PROCESS(ukuflow_event_mgr_publisher_process, "publisher process");

/*---------------------------------------------------------------------------*/
// Variables for the recursive handling of event operators (init/remove)
static channel_id_t channel_id;
static bool deployed;
static scope_id_t deployed_at_scope_id;

/*---------------------------------------------------------------------------*/
/** Forward declaration of functions and protothreads*/
struct ukuflow_event_operator *event_operators[];

/*---------------------------------------------------------------------------*/
/** Inter-protothread communication events */
/** \brief Generated when a subscription request was added to the event engine: */
static process_event_t event_mgr_request_ready_event;
/** \brief Generated when an event needs to be published */
static process_event_t event_mgr_publish_event;

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

	/** Setup inter-protothread communication events */
	event_mgr_request_ready_event = process_alloc_event();
	event_mgr_publish_event = process_alloc_event();

	/** Initialize the (un)subscription manager process */
	process_start(&ukuflow_event_mgr_request_process, NULL);
	/** Initialize the */
	process_start(&ukuflow_event_mgr_publisher_process, NULL);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Searches for a subscription in the list of locally managed subscriptions
 *
 * 				TODO
 */
struct generic_subscription *get_subscription(channel_id_t channel_id) {
	struct generic_subscription *subscription = list_head(subscriptions);

	while ((subscription != NULL) && (subscription->main_ev_op->channel_id
			!= channel_id))
		subscription = subscription->next;

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
static struct global_subscription *alloc_global_subscription(
		struct generic_event_operator *main_ev_op, data_len_t ev_op_len,
		void(*notify)(struct event *event, data_len_t event_payload_len),
		struct workflow *wf) {

	struct global_subscription *global_sub = malloc(
			sizeof(struct global_subscription));

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
static struct local_subscription *alloc_local_subscription(
		struct generic_event_operator *main_ev_op, data_len_t ev_op_len) {

	/* Allocate memory for the local subscription */
	struct local_subscription *local_sub = malloc(
			sizeof(struct local_subscription));

	if (local_sub == NULL)
		return (NULL);

	/* Allocate memory for the entire event operator */
	struct generic_event_operator *local_main_ev_op = malloc(ev_op_len);

	if (local_main_ev_op == NULL) {
		free(local_sub);
		return (NULL);
	}

	memcpy(local_main_ev_op, main_ev_op, ev_op_len);

	local_sub->subscription_type = LOCAL_SUBSCRIPTION;
	local_sub->ev_op_len = ev_op_len;
	local_sub->main_ev_op = local_main_ev_op;
	ctimer_set(&local_sub->ttl_timer, MAX_SUB_TTL,
			(void(*)(void *)) ukuflow_event_mgr_handle_unsubscription,
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
static struct running_event_op *get_running_event_op(
		struct generic_event_operator *geo) {
	struct running_event_op *reo = list_head(running_event_ops);

	while (reo != NULL)
		if ((reo->geo != NULL) && //
				(reo->geo->ev_op_type == geo->ev_op_type) && //
				(reo->geo->channel_id == geo->channel_id))
			return (reo);
		else
			reo = reo->next;

	return (NULL);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Allocates memory for a new running event operator for the event
 * 				operator passed as parameter.
 *
 * 				This function allocates memory for the necessary record, and links
 * 				it to the list of running event operators.
 */
static struct running_event_op *alloc_running_ev_op(
		struct generic_event_operator *geo) {

	/** Allocate memory for running event operator */
	struct running_event_op *reo = malloc(sizeof(struct running_event_op));

	if (reo == NULL)
		return (NULL);

	/** Set input channel: */
	reo->input_channel_id = channel_id;

	/** Update channel_id as input for next event operator */
	channel_id = geo->channel_id;

	/** Link reference from local record to event operator record */
	reo->geo = geo;

	/** Add local record to list */
	list_add(running_event_ops, reo);

	return (reo);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief	Generic handling of subscriptions of event operators
 *
 * @param[in] main_ev_op	pointer to the subscription
 * @param[in] ev_op_len		length of the entire subscription (sequence of event operators)
 */
void ukuflow_event_mgr_handle_subscription(
		struct generic_event_operator *main_ev_op, data_len_t ev_op_len) {

	PRINTF(1,"(EVENT-MGR) handling subscription: ");
	PRINT_ARR(1,(uint8_t*) main_ev_op,ev_op_len);

	struct generic_subscription *generic_sub = get_subscription(
			main_ev_op->channel_id);

	/** Check whether subscription is unknown at this node: */
	if (generic_sub == NULL) {

		/** Subscription is unknown, so allocate memory at this node for it */
		generic_sub = (struct generic_subscription*) alloc_local_subscription(
				main_ev_op, ev_op_len);

		if (generic_sub != NULL)
			list_add(subscriptions, generic_sub);

	} else if (generic_sub->subscription_type == LOCAL_SUBSCRIPTION) {
		/** Subscription is known, so reset timer */
		PRINTF(3,"(EVENT-MGR) subscription exists locally, resetting timer\n");
		struct local_subscription *local_sub =
				(struct local_subscription*) generic_sub;
		ctimer_reset(&local_sub->ttl_timer);
	}

	if (generic_sub != NULL) {

		// input channel id
		channel_id = 0;

		PRINTF(1, "(EVENT-MGR) num reo: %u\n",list_length(running_event_ops));

		/* Start recursive initialization, which makes a copy of the event operator
		 * into a new running event operator if needed */
		//		if (event_operators[main_ev_op->ev_op_type] != NULL)
		event_operators[main_ev_op->ev_op_type]->init(generic_sub->main_ev_op);
		//		else
		//			printf("Problem: pointer to event_operator needed was null\n");
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Handling of an event operator unsubscription
 *
 *				TODO
 *
 * @param[in]	main_ev_op_channel_id channel id of the main event operator
 */
void ukuflow_event_mgr_handle_unsubscription(
		channel_id_t *main_ev_op_channel_id) {

	struct generic_subscription *generic_sub = get_subscription(
			*main_ev_op_channel_id);

	/** Check whether subscription is known here: */
	if (generic_sub != NULL) {

		event_operators[generic_sub->main_ev_op->ev_op_type]->remove(
				generic_sub->main_ev_op);

		list_remove(subscriptions, generic_sub);

		if (generic_sub->subscription_type == LOCAL_SUBSCRIPTION) {
			struct local_subscription *l_sub =
					(struct local_subscription*) generic_sub;
			/** Release memory of main event operator */
			free(l_sub->main_ev_op);

			/** Release memory of local subscription */
		} // if

		free(generic_sub);
	} // if
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Handles an event message
 *
 * 				Handles an event message by unpacking it from the radio message and invoking the publish function with it
 *
 * @param[in]	event_msg 	Event message received from the network
 */
void ukuflow_event_mgr_handle_event(struct ukuflow_event_msg *event_msg) {
	struct event *event = (struct event*) (((uint8_t*) event_msg)
			+ sizeof(struct ukuflow_event_msg));

	process_post(&ukuflow_event_mgr_publisher_process, event_mgr_publish_event,
			event);
	//publish(event, event_msg->event_payload_len);

}

/*---------------------------------------------------------------------------*/
// Specification of event operator functions
/*---------------------------------------------------------------------------*/
/**
 * \brief		Callback function for generating events periodically
 *
 * 				This function is invoked by a callback timer whenever a periodic event needs to be generated.
 *
 * @param[in]	ptr 	the running event operator that needs to be attended by this callback function
 */
static void periodic_egen_generate(void *ptr) {
	struct running_event_op *reo = (struct running_event_op*) ptr;
	struct periodic_egen *peg = (struct periodic_egen*) reo->geo;

	data_len_t event_len;
	struct event *event = event_alloc_raw(&event_len);

	event_populate(event, (struct generic_egen*) peg);

	PRINTF(1,
			"(EVENT-MGR) generating event for channel %u, len %u\n",
			reo->geo->channel_id, event_len);

	process_post(&ukuflow_event_mgr_publisher_process, event_mgr_publish_event,
			event);

	//	publish(event, event_len);

	//	if (ctimer_expired(&reo->ctimer))
	ctimer_reset(&reo->event_operator_trigger_ctimer);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Initializes a periodic event generator on this node.
 *
 * 				TODO
 */
static void periodic_egen_init(struct generic_event_operator *geo) {
	struct periodic_egen *peg = (struct periodic_egen*) geo;
	struct running_event_op *reo;

	/** set initially the 'deployed' variable to FALSE */
	deployed = FALSE;
	/** check whether this event generator operator needs to be initialized at this node: */
	if (scopes_member_of(peg->scope_id) && !scopes_creator_of(peg->scope_id)) {
		if ((reo = get_running_event_op(geo)) == NULL)
			reo = alloc_running_ev_op(geo);

		/** only continue deploying if it was possible to allocate running event operator */
		if ((deployed = (reo != NULL))) {

			deployed_at_scope_id = peg->scope_id;

			/** chain the null channel id with the channel id of this event operator */
			reo->input_channel_id = 0;
			/** reset global variable channel_id to 0 */
			channel_id = geo->channel_id;

			/** set timer for periodic generation */
			PRINTF(1, "(EVENT-MGR) periodic generator init, set timer %u\n", peg->period);
			ctimer_set(&reo->event_operator_trigger_ctimer, peg->period
					* CLOCK_SECOND, periodic_egen_generate, reo);
		}
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static void periodic_egen_remove(struct generic_event_operator *geo) {
	// check whether this event generator operator is running at this node:
	struct running_event_op *reo = get_running_event_op(geo);
	if (reo != NULL) {
		ctimer_stop(&reo->event_operator_trigger_ctimer);
		list_remove(running_event_ops, reo);
		free(reo);
	}
}

///*---------------------------------------------------------------------------*/
///**
// * \brief		TODO
// *
// * 				TODO
// */
//static void periodic_egen_evaluate(struct event *event,
//		data_len_t event_payload_len) {
//}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Initializes a simple filter for the specified parameters
 *
 * 				TODO
 */
static void simple_filter_init(struct generic_event_operator *geo) {

	struct running_event_op *reo;

	struct generic_event_operator *next_geo =
			(struct generic_event_operator *) (((uint8_t*) geo)
					+ event_operator_get_size(geo));

	/** First continue initialization recursively:*/
	event_operators[next_geo->ev_op_type]->init(next_geo);

	/** Check whether this simple filter event operator needs to be initialized at this node.
	 * This must be done if either the previous, recursive event operator was deployed, or if
	 * this node is the root of the scope indicated in the subscription
	 */
	if ((deployed) || (scopes_creator_of(deployed_at_scope_id))) {
		if ((reo = get_running_event_op(geo)) == NULL)
			reo = alloc_running_ev_op(geo);

		/** only continue deploying if it was possible to allocate running event operator */
		if ((deployed = (reo != NULL))) {

			/** chain the channel id from the previous event operator in the recursive
			 * initialization with the channel id of this event operator */
			reo->input_channel_id = channel_id;
			/** set global variable channel_id to the channel id of this event operator */
			channel_id = geo->channel_id;

		}
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static void simple_filter_remove(struct generic_event_operator *geo) {
	/** check whether this event generator operator is running at this node */
	struct running_event_op *reo = get_running_event_op(geo);
	if (reo != NULL) {
		ctimer_stop(&reo->event_operator_trigger_ctimer);
		free(reo);
	}
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
 * @param reo[in]				TODO
 * @param event[in]				TODO
 * @param event_payload_len[in]	TODO
 */
static void simple_filter_merge(struct running_event_op *reo,
		struct event *event, data_len_t event_payload_len) {

	struct simple_filter *filter = (struct simple_filter*) reo->geo;

	bool filter_passed = TRUE;
	uint8_t num_expression;
	uint8_t *expression_pair = ((uint8_t*) reo) + sizeof(struct simple_filter);
	uint8_t *expression_spec;
	for (num_expression = 0; (filter_passed) && (num_expression < filter->num_expressions); num_expression++) {
		data_len_t expr_len = *expression_pair;
		expression_spec = expression_pair + sizeof(data_len_t);
		expression_eval_set_custom_input(&event_custom_input_function,
				(void*) event);
		filter_passed = expression_eval_evaluate(expression_spec, expr_len);
	}

	if (filter_passed) {
		// republish
		struct event *cloned_event = event_clone(event, event_payload_len
				+ sizeof(struct event));
		cloned_event->channel_id = reo->geo->channel_id;
		process_post(&ukuflow_event_mgr_publisher_process,
				event_mgr_publish_event, cloned_event);
	}
}

///*---------------------------------------------------------------------------*/
///**
// * \brief		TODO
// *
// * 				TODO
// * @param[in] 	event	TODO
// * @param[in]	event_payload_len	TODO
// */
//static void count_processing_function_merge(struct event *event,
//		data_len_t event_payload_len) {
//	// take the event and check whether it is a raw input or a partial state record.
//
//	// do the processing accordingly, updating partial state record
//
//	// when timer slot of epoch expires:
//	// if this node is not root, mark event as being a partial state record and forward event upstream with original channel id, so that parent node in tree can run the merge again
//	// if this node is the root, republish with corresponding channel id, so that potential next event operator can process it
//}

//...

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
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
 * @param[in]  main_ev_op  The pointer to the main, generic event operator to be subscribed to
 * @param[in]  ev_op_len   The number of bytes to copy
 * @param[in]  notify      The pointer to the function that must be invoked when a matching event is detected
 * @param[in]  wf          The pointer to the workflow specification, which is used to find the scope specifications when needed
 * \returns                TRUE if subscription succeeded, FALSE otherwise
 */
bool ukuflow_event_mgr_subscribe(//
		struct generic_event_operator *main_ev_op, //
		data_len_t ev_op_len, //
		void(*notify)(struct event *event, data_len_t event_payload_len), //
		struct workflow *wf) {

	struct subscription_request *sub_request = malloc(
			sizeof(struct subscription_request));

	if (!sub_request)
		return (FALSE);

	sub_request->request_type = SUBSCRIPTION_REQUEST;
	sub_request->main_ev_op = main_ev_op;
	sub_request->ev_op_len = ev_op_len;
	sub_request->notify = notify;
	sub_request->wf = wf;

	list_add(event_mgr_requests, sub_request);

	process_post(&ukuflow_event_mgr_request_process,
			event_mgr_request_ready_event, NULL);

	return (TRUE);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 * @param[in]	scope_id	Scope id of the scope which is being left
 */
void ukuflow_event_mgr_scope_left(scope_id_t scope_id) {
	PRINTF(4,"(EVENT-MGR) removed scope: %u\n", scope_id);

	/** \todo iterate over subscriptions to see if they need to be cleaned up */
	struct generic_subscription *subscription = list_head(subscriptions);

	while (subscription != NULL) {
		subscription = subscription->next;
	}
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

	struct unsubscription_request *unsub_request = malloc(
			sizeof(struct unsubscription_request));

	if (!unsub_request)
		return (FALSE);

	unsub_request->request_type = UNSUBSCRIPTION_REQUEST;
	unsub_request->main_ev_op = main_ev_op;
	unsub_request->token = token;
	list_add(event_mgr_requests, unsub_request);

	process_post(&ukuflow_event_mgr_request_process,
			event_mgr_request_ready_event, NULL);

	return (TRUE);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Protothread for processing subscription and unsubscription requests
 *
 * 				This protothread is in charge of executing subscription and unsubscriptions
 */
PROCESS_THREAD( ukuflow_event_mgr_request_process, ev, data) {

	static struct event_mgr_request *generic_request;
	static struct subscription_request *sub_request;
	static struct unsubscription_request *unsub_request;
	static struct generic_subscription *generic_sub;
	static struct global_subscription *global_sub;
	static struct ukuflow_sub_msg *sub_msg;
	static struct ukuflow_unsub_msg unsub_msg;
	static struct generic_event_operator *geo_iter;
	static struct generic_egen *g_egen;
	static struct scope_info *s_i;
	/** \brief Length of event operator processed so far */
	static data_len_t processed_len;
	static bool new_sub;
	//	static clock_time_t timestamp;

	PROCESS_BEGIN();

	/** Prepare unsubscription message in advance */
	unsub_msg.msg_type = SCOPED_EVENT_OPERATOR_UNSUB_MSG;

	while (1) {

		/** This process takes a request from the queue, and processes it
		 * assuming the necessary resources are available (memory, scope information, etc.)
		 * */

		while ((generic_request = list_head(event_mgr_requests)) == NULL) {
			PRINTF(
					1,
					"(EVENT-MGR) request-handler, yielding til (un)subscription request\n");
			PROCESS_WAIT_EVENT_UNTIL(ev == event_mgr_request_ready_event);
		}

		//		printf("continuing with request type %u, list size: %u\n", generic_request->request_type,list_length(event_mgr_requests));
		/*---------------------------------------------------------------------------*/
		if (generic_request->request_type == SUBSCRIPTION_REQUEST) {

			sub_request = (struct subscription_request*) generic_request;
			PRINTF(1,"(EVENT-MGR) processing subscription for ev. channel %u\n",
					sub_request->main_ev_op->channel_id);
			new_sub = FALSE;

			/** Find out whether subscription already exists (i.e. this is a re announcement): */
			if ((generic_sub = get_subscription(
					sub_request->main_ev_op->channel_id)) == NULL) {

				/** Subscription didn't exist, so allocate memory for it */
				generic_sub
						= (struct generic_subscription*) alloc_global_subscription(
								sub_request->main_ev_op,
								sub_request->ev_op_len, sub_request->notify,
								sub_request->wf);
				new_sub = TRUE;
			}

			/** Check if it was possible to allocate memory for the subscription by comparing pointer against NULL */
			/** Also check if it that pointer points to a subscription of type global */

			if ((generic_sub == NULL) || (generic_sub->subscription_type
					!= GLOBAL_SUBSCRIPTION)) {
				/** It wasn't possible to allocate memory, or, the subscription found is of wrong type.
				 * Hence wait a bunch of seconds and retry */
				etimer_set(&event_mgr_control_timer, CLOCK_SECOND * 5);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
						&event_mgr_control_timer));
				continue;
			}

			global_sub = (struct global_subscription*) generic_sub;

			/** Allocate memory for network message that will contain the subscription data and will sent through scope */
			sub_msg = malloc(sizeof(struct ukuflow_sub_msg)
					+ global_sub->ev_op_len);

			//			printf("size is %u + %u\n", sizeof(struct ukuflow_sub_msg), global_sub->ev_op_len);

			/** If there was no memory free for message, postpone processing of subscription request */
			if (sub_msg == NULL) {
				free(global_sub);
				etimer_set(&event_mgr_control_timer, CLOCK_SECOND * 5);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
						&event_mgr_control_timer));
				continue;
			}

			/** Add global subscription to local list */
			list_add(subscriptions, global_sub);

			/** If this was a new global subscription (instead of a re announcement), set the callback timer. */
			if (new_sub)
				ctimer_set(&global_sub->announcement_timer, MAX_SUB_TTL
						* SUBSCRIPTION_REANNOUNCE_FACTOR,
						reannounce_subscription, global_sub);
			else
				/** otherwise, this is not a new subscription (it's a re announcement), thus simply reset the callback timer*/
				ctimer_reset(&global_sub->announcement_timer);

			/** Remove subscription request from list and release its memory */
			list_remove(event_mgr_requests, generic_request);
			free(sub_request);

			/** Prepare subscription message: */
			sub_msg->msg_type = SCOPED_EVENT_OPERATOR_SUB_MSG;
			memcpy(((uint8_t*) sub_msg) + sizeof(struct ukuflow_sub_msg),
					global_sub->main_ev_op, global_sub->ev_op_len);

			/** Now iterate over the event operators.
			 * Whenever an event *generator* is found, lookup its associated scope,
			 * and send the event operator expression through it. */
			processed_len = 0;
			geo_iter = global_sub->main_ev_op;

			while (processed_len < global_sub->ev_op_len) {

				/** check whether this event operator is an event generator */
				if (geo_iter->ev_op_type <= DISTRIBUTED_E_GEN) {
					/** We have found an event operator that is an event generator, so send it to its associated scope */
					g_egen = (struct generic_egen*) geo_iter;

					/** Now open the scope, unless it is the world scope or the scope is already open */
					if ((g_egen->scope_id != SCOPES_WORLD_SCOPE_ID)
							&& (!scopes_creator_of(g_egen->scope_id))) {

						s_i = workflow_get_scope_info(global_sub->wf,
								g_egen->scope_id);

						/** Test if scope information is available and whether opening the scope works.
						 * In case opening the scope fails, it won't be possible sending through it, so cancel */
						if ((s_i != NULL) && //
								ukuflow_net_mgr_open_scope(g_egen->scope_id,
										((uint8_t*) s_i)
												+ sizeof(struct scope_info),
										s_i->scope_spec_len, s_i->scope_ttl)) {

							PRINTF(
									3,
									"(EVENT-MGR) request-handler, opened scope %u, waiting some seconds\n",
									s_i->scope_id);
							/** Now wait a bunch of seconds for the scope creation to succeed */
							etimer_set(&event_mgr_control_timer,
									SCOPE_OPERATION_DELAY * CLOCK_SECOND);
							PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
									&event_mgr_control_timer));

						} else {
							PRINTF(1, "ERROR s_i %p, sid %u\n", s_i, g_egen->scope_id);
							free(sub_msg);
							continue;
						}

					}

					/** Send through Scopes to the member nodes: */
					ukuflow_net_mgr_send_scope(g_egen->scope_id, FALSE,
							sub_msg, sizeof(struct ukuflow_generic_msg)
									+ global_sub->ev_op_len);

					PRINTF(
							3,
							"(EVENT-MGR) request-handler, sent subscription through scope %u, waiting some seconds\n",
							s_i->scope_id);
					/** Now wait a bunch of seconds for the message to be disseminated */
					etimer_set(&event_mgr_control_timer, SCOPE_OPERATION_DELAY
							* CLOCK_SECOND);
					PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
							&event_mgr_control_timer));
				}

				data_len_t oper_len = event_operator_get_size(geo_iter);
				/** Decrease remaining_len: */
				processed_len += oper_len;
				/** Adjust pointer to next event operator:*/
				geo_iter
						= (struct generic_event_operator*) (((uint8_t*) geo_iter)
								+ oper_len);
			}

			/** Handle subscription locally (since Scopes doesn't deliver the message at the root) */
			ukuflow_event_mgr_handle_subscription(global_sub->main_ev_op,
					global_sub->ev_op_len);

			/** Release memory of subscription message */
			free(sub_msg);

			//			printf("finished subscribing\n", list_length(event_mgr_requests));
		}
		/*---------------------------------------------------------------------------*/
		else if (generic_request->request_type == UNSUBSCRIPTION_REQUEST) {
			unsub_request = (struct unsubscription_request*) generic_request;
			PRINTF(1,"(EVENT-MGR) processing unsubscription for channel %u\n",
					unsub_request->main_ev_op->channel_id);

			generic_sub = get_subscription(
					unsub_request->main_ev_op->channel_id);

			if ((generic_sub != NULL) && (generic_sub->subscription_type
					== GLOBAL_SUBSCRIPTION)) {

				global_sub = (struct global_subscription*) generic_sub;

				ctimer_stop(&global_sub->announcement_timer);

				/** Prepare unsubscription message */
				unsub_msg.main_ev_op_channel_id
						= global_sub->main_ev_op->channel_id;

				/** Now iterate over the event operators.
				 * We need to send the unsubscription message to the members of the scopes which
				 * previously received the subscription message.
				 * Whenever an event *generator* is found, lookup its associated scope,
				 * and send the unsubscription through it. */
				processed_len = 0;
				geo_iter = global_sub->main_ev_op;
				while (processed_len < global_sub->ev_op_len) {

					if (geo_iter->ev_op_type <= DISTRIBUTED_E_GEN) {
						/** We have found an event operator that is an event generator, so send unsubscription message through the associated scope */
						g_egen = (struct generic_egen*) geo_iter;

						/** Send unsub message through Scopes to the member nodes: */
						ukuflow_net_mgr_send_scope(g_egen->scope_id, FALSE,
								&unsub_msg, sizeof(struct ukuflow_unsub_msg));

						PRINTF(
								3,
								"(EVENT-MGR) request-handler, sent unsubscription through scope %u, waiting some seconds\n",
								s_i->scope_id);
						/** Now wait a bunch of seconds for the unsub message to be disseminated */
						etimer_set(&event_mgr_control_timer,
								SCOPE_OPERATION_DELAY * CLOCK_SECOND);
						PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
								&event_mgr_control_timer));

						/** Now close the scope, unless it is the world scope */
						if (g_egen->scope_id != SCOPES_WORLD_SCOPE_ID) {

							ukuflow_net_mgr_close_scope(g_egen->scope_id);

							printf("closed\n");
							PRINTF(
									3,
									"(EVENT-MGR) request-handler, closed scope %u, waiting some seconds\n",
									g_egen->scope_id);
							/** Now wait a bunch of seconds for the scope removal to succeed */
							//							printf("setting timer\n");
							etimer_set(&event_mgr_control_timer,
									SCOPE_OPERATION_DELAY * CLOCK_SECOND);
							PROCESS_WAIT_EVENT_UNTIL(etimer_expired(
									&event_mgr_control_timer));
							//							printf("timer exp\n");

						} // if ! scope is scope_world

					} // if event operator is event generator

					data_len_t oper_len = event_operator_get_size(geo_iter);
					/** Increase remaining_len: */
					processed_len += oper_len;
					/** Adjust pointer to next event operator:*/
					geo_iter
							= (struct generic_event_operator*) (((uint8_t*) geo_iter)
									+ oper_len);

				} // while (each event operator)

				ukuflow_event_mgr_handle_unsubscription(
						&(global_sub->main_ev_op->channel_id));

				ukuflow_notify_unsubscription_ready(unsub_request->token);

			} // if (generic_sub != NULL)

			/** Remove unsubscription request from list and release its memory */
			list_remove(event_mgr_requests, unsub_request);
			free(unsub_request);

		} // if (generic_request->request_type == UNSUBSCRIPTION_REQUEST)


		/*---------------------------------------------------------------------------*/

	} // while (1)

	PROCESS_END();
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Protothread for publishing events.
 *
 * 				This protothread is in charge of chaining events between event operators.
 * 				The protothread sleeps until an event is posted for processing, e.g.
 * 				when an event operator generates its event output.
 *
 * 				The publisher scans for further, running event operators at this node.
 * 				If one is found, the event is passed to it by invoking the evaluate
 * 				function of the found operator.
 * 				If no running event operator is found, it means that the event
 * 				was generated by the main event operator of a subscription, thus
 * 				it has to be sent to the root, where it will be used to invoke
 * 				the notify function registered with the subscription.
 */
PROCESS_THREAD( ukuflow_event_mgr_publisher_process, ev, data) {

	static struct event *event;
	static struct running_event_op *reo;
	static scope_id_t *scope_id;
	static struct global_subscription *subscription;
	static data_len_t msg_size;
	static uint8_t *msg_data;

	PROCESS_BEGIN();

	while (1) {

		PROCESS_WAIT_EVENT_UNTIL(ev == event_mgr_publish_event);

		event = (struct event*) data;
		data_len_t event_len = event_get_size(event);

		/** look for a running event operator that is interested in the event being published */
		for (reo = list_head(running_event_ops); reo != NULL; reo = reo->next) {
			/** test whether output channel (contained in event) matches input channel of local event operators: */
			if (reo->input_channel_id == event->channel_id) {
				// there was a match
				event_operators[reo->geo->ev_op_type]->merge(reo, event,
						event_len - sizeof(struct event));
				continue;
			}
		}

		/** If control arrived here, it means that there was no local event operator that
		 * matched the channel. Hence, depending on whether the node is root or not,
		 * a message must be passed to the parent node:*/
		scope_id = (scope_id_t*) event_get_value(event, SOURCE_SCOPE);

		event_print(event, event_len - sizeof(struct event));

		PRINTF(1,"(EVENT-MGR) Publishing to scope %u, event_payload_len %u\n", *scope_id,
				event_len - sizeof(struct event));

		if (scopes_creator_of(*scope_id)) {
			/** This node is scope root (creator) */
			PRINTF(1,"(EVENT-MGR) event reached root, notifying consumer \n");

			/** We arrived at the root, so look up the subscription and invoke the registered notification function */

			subscription = (struct global_subscription*) get_subscription(
					event->channel_id);
			subscription->notify(event, event_len - sizeof(struct event));

			//			event_operators[reo->geo->ev_op_type]->evaluate(event, event_len
			//					- sizeof(struct event));
		} else {
			/** This node is not scope root (creator), so forward */
			PRINTF(1,"(EVENT-MGR) forwarding to parent\n");
			msg_size = sizeof(struct ukuflow_event_msg) + sizeof(struct event)
					+ event_len - sizeof(struct event);
			msg_data = malloc(msg_size);
			if (msg_data != NULL) {
				PRINT_ARR(3,(uint8_t*)event,event_len);
				memcpy(msg_data + sizeof(struct ukuflow_event_msg), event,
						event_len);
				struct ukuflow_event_msg *event_msg =
						(struct ukuflow_event_msg *) msg_data;
				event_msg->msg_type = SCOPED_EVENT_MSG;
				event_msg->event_payload_len = event_len - sizeof(struct event);
				ukuflow_net_mgr_send_scope(*scope_id, TRUE, msg_data, msg_size);
				free(msg_data);
			} // message was not null
		}

		free(event);

	} // while (1)
	PROCESS_END();
}
/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
// Declaration of event operators
//EVENT_OPERATOR(immediate_egen_oper, NULLARY | DISTRIBUTABLE, immediate_egen_init, _remove, NULL, NULL)
//EVENT_OPERATOR(absolute_egen_oper, NULLARY | DISTRIBUTABLE, absolute_egen_init, _remove, NULL, NULL)
//EVENT_OPERATOR(offset_egen_oper, NULLARY | DISTRIBUTABLE, offset_egen_init, _remove, NULL, NULL)
//EVENT_OPERATOR(relative_egen_oper, NULLARY | DISTRIBUTABLE, relative_egen_init, _remove, NULL, NULL)
EVENT_OPERATOR(periodic_egen_oper, NULLARY | DISTRIBUTABLE, periodic_egen_init, periodic_egen_remove, NULL, NULL)
//EVENT_OPERATOR(patterned_egen_oper, NULLARY | DISTRIBUTABLE, patterned_egen_init, _remove, NULL, NULL)
//EVENT_OPERATOR(distributed_egen_oper, NULLARY | DISTRIBUTABLE, distributed_egen_init, _remove, NULL, NULL)
EVENT_OPERATOR(simple_filter_oper, UNARY | DISTRIBUTABLE, simple_filter_init, simple_filter_remove, simple_filter_merge, NULL)
//EVENT_OPERATOR(and_composite_oper, BINARY | DISTRIBUTABLE, and_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(or_composite_oper, BINARY | DISTRIBUTABLE, or_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(not_composite_oper, UNARY | DISTRIBUTABLE, not_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(sequence_composite_oper, UNARY | DISTRIBUTABLE, sequence_composite_init, _remove, NULL, NULL)
//EVENT_OPERATOR(min_processing_function_oper, UNARY | DISTRIBUTABLE, min_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(max_processing_function_oper, UNARY | DISTRIBUTABLE, max_processing_function_init, _remove, NULL, NULL)
//EVENT_OPERATOR(count_processing_function_oper, UNARY | DISTRIBUTABLE, count_processing_function_init, _remove, NULL, NULL)
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
struct ukuflow_event_operator *event_operators[] = {//
		// List of operators:
				NULL, //
				NULL, //
				NULL, //
				NULL, //
				&periodic_egen_oper, //
				NULL, //
		};

//struct ukuflow_event_operator event_operators[] = {//
//		// List of operators:
//				immediate_egen_oper, //
//				absolute_egen_oper, //
//				offset_egen_oper,//
//				relative_egen_oper,//
//				periodic_egen_oper,//
//				patterned_egen_oper,//
//				distributed_egen_oper,//
//				simple_filter_oper,//
//				and_composite_filter_oper, //
//				or_composite_filter_oper, //
//				not_composite_filter_oper, //
//				sequence_composite_filter_oper, //
//				min_processing_function_oper, //
//				max_processing_function_oper, //
//				count_processing_function_oper, //
//				sum_processing_function_oper, //
//				avg_processing_function_oper, //
//				stdev_processing_function_oper, //
//				inc_change_oper, //
//				dec_change_oper, //
//				rem_change_oper //
//		};

/*---------------------------------------------------------------------------*/

/** @} */
