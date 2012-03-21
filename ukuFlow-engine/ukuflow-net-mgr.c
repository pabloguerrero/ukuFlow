/*
 * ukuflow-net-mgr.c
 *
 *  Created on: Jul 13, 2011
 *      Author: guerrero
 */

#include "dev/leds.h"

/* ukuFlow */
#include "ukuflow-net-mgr.h"
#include "ukuflow-event-mgr.h"
#include "ukuflow-cmd-runner.h"

/* scopes app */
#include "scopes-application.h"
#include "scopes.h"

/* init */
#include "scopes-selfur.h"
#include "scopes-membership-simple.h"

/* logging */
#include "logger.h"

#define UKUFLOW_SCOPES_SID 1

/*---------------------------------------------------------------------------*/
/* Declaration of global variables */
// None
/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
static void add_scope(scope_id_t scope_id) {
	PRINTF(4,"[%u.%u:%10lu] %s::%s() : added scope: %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Invoked when a scope is being removed
 *
 * 				Tell to upper layers that a scope was removed in this node, so that necessary housekeeping can be done.
 */
static void remove_scope(scope_id_t scope_id) {
	PRINTF(4,"[%u.%u:%10lu] %s::%s() : removed scope: %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
static void join_scope(scope_id_t scope_id) {
	PRINTF(4,"[%u.%u:%10lu] %s::%s() : joined scope: %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id);

//#if DEBUG_DEPTH > 0
	if (scope_id == 11)
		leds_on(LEDS_RED);
	else if (scope_id == 22)
		leds_on(LEDS_GREEN);
	else if (scope_id == 33)
		leds_on(LEDS_BLUE);
//#endif
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
static void leave_scope(scope_id_t scope_id) {
	PRINTF(4,"[%u.%u:%10lu] %s::%s() : left scope: %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id);

//#if DEBUG_DEPTH > 0
	if (scope_id == 11)
		leds_off(LEDS_RED);
	else if (scope_id == 22)
		leds_off(LEDS_GREEN);
	else if (scope_id == 33)
		leds_off(LEDS_BLUE);
//#endif

	ukuflow_event_mgr_scope_left(scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_net_mgr_handler(scope_id_t scope_id, void *data,
		data_len_t data_len, bool to_creator, const rimeaddr_t *source) {

	PRINTF(1, "(NET-MGR) msg received, ");
	PRINT_ARR(1, data, data_len);

	struct ukuflow_generic_msg *msg = (struct ukuflow_generic_msg*) data;
	switch (msg->msg_type) {
	case SCOPED_FUNCTION_MSG: {
		struct sfs_msg *s_msg = (struct sfs_msg*) msg;
		ukuflow_cmd_runner_run(((char*) s_msg) + sizeof(struct sfs_msg),
				s_msg->cmd_len, NULL);
		break;
	}
	case SCOPED_EVENT_OPERATOR_SUB_MSG: {
		ukuflow_event_mgr_handle_subscription(
				(struct generic_event_operator*) (((uint8_t*) data)
						+ sizeof(struct ukuflow_generic_msg)), data_len
						- sizeof(struct ukuflow_generic_msg));
		break;
	}
	case SCOPED_EVENT_OPERATOR_UNSUB_MSG: {
		struct ukuflow_unsub_msg *msg = (struct ukuflow_unsub_msg*) data;
		ukuflow_event_mgr_handle_unsubscription(&(msg->main_ev_op_channel_id));
		break;
	}
	case SCOPED_EVENT_MSG: {
		struct ukuflow_event_msg *event_msg = (struct ukuflow_event_msg*) data;
		ukuflow_event_mgr_handle_event(event_msg);
		break;
	}
	}// switch

}

/*---------------------------------------------------------------------------*/
static struct scopes_application ukuflow_scopes_callbacks = { add_scope,
		remove_scope, join_scope, leave_scope, ukuflow_net_mgr_handler };

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_net_mgr_init() {

	/* Initialize other components: */
	/* Scopes */
	scopes_init(&scopes_selfur, &simple_membership);

	/* Register the event manager at the Scopes framework: */
	scopes_register(UKUFLOW_SCOPES_SID, &ukuflow_scopes_callbacks);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
bool ukuflow_net_mgr_open_scope(scope_id_t scope_id, void *specs,
		data_len_t spec_len, scope_ttl_t ttl) {

	PRINTF(3,"[%u.%u:%10lu] %s::%s() : Opening scope %u, len %u, ttl %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id, spec_len, ttl);

	return scopes_open(UKUFLOW_SCOPES_SID, SCOPES_WORLD_SCOPE_ID, scope_id,
			specs, spec_len, SCOPES_FLAG_INTERCEPT | SCOPES_FLAG_DYNAMIC, ttl); // alternatively, as flag SCOPES_FLAG_NONE could be used, but it wouldn't enable event processing at non-member nodes on the way towards the sink
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_net_mgr_close_scope(scope_id_t scope_id) {

	PRINTF(3,"[%u.%u:%10lu] %s::%s() : Closing scope %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id);

	scopes_close(UKUFLOW_SCOPES_SID, scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_net_mgr_send_scope(scope_id_t scope_id, bool to_creator,
		void *data, data_len_t data_len) {
	PRINTF(3,"[%u.%u:%10lu] %s::%s() : Sending msg through scope %u, msg len %u, direction %u\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__, scope_id, data_len, to_creator);
	scopes_send(UKUFLOW_SCOPES_SID, scope_id, to_creator, data, data_len);
}