/**
 * \addtogroup engine
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
 * \file	ukuflow-net-mgr.c
 * \brief	ukuFlow Net Manager
 *
 *			TODO
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 13, 2011
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

/** \brief Definition of the scope application subscriber id */
#define UKUFLOW_SCOPES_SID 1

/*---------------------------------------------------------------------------*/
/* Declaration of global variables */
// None
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static void add_scope(scope_id_t scope_id) {
	PRINTF(1,
			"(UF-NET-MGR) scope %d added\n", scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Invoked when a scope is being removed
 *
 * 				Tell to upper layers that a scope was removed in this node, so that necessary housekeeping can be done.
 */
static void remove_scope(scope_id_t scope_id) {
	PRINTF(1,
			"(UF-NET-MGR) scope %d removed\n", scope_id);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static void join_scope(scope_id_t scope_id) {
	PRINTF(1,
			"(UF-NET-MGR) joined scope: %u\n", scope_id);

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
 * \brief		TODO
 *
 * 				TODO
 */
static void leave_scope(scope_id_t scope_id) {
	PRINTF(1,
			"(UF-NET-MGR) left scope %u\n", scope_id);

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
 * \brief		Invoked when a message arrives at node and is of interest
 * 				to the ukuFlow framework. This generic function forwards
 *
 * @param[in] scope_id	the scope through which the message arrived
 * @param[in] data		the message's payload
 * @param[in] data_len	the length of the payload
 * @param[in] to_creator	the direction of the message (towards root or towards scope members)
 * @param[in] source	the id of the node who sent the message
 */
void ukuflow_net_mgr_handler(scope_id_t scope_id, void *data,
		data_len_t data_len, bool to_creator, const rimeaddr_t *source) {

	PRINTF(1, "(UF-NET-MGR)  msg received, ");
	PRINT_ARR(2, data, data_len);

	struct ukuflow_generic_msg *msg = (struct ukuflow_generic_msg*) data;
	switch (msg->msg_type) {
	case SCOPED_FUNCTION_MSG: {
		struct sfs_msg *s_msg = (struct sfs_msg*) msg;
		ukuflow_cmd_runner_run(((char*) s_msg) + sizeof(struct sfs_msg),
				s_msg->cmd_line_len, NULL);
		break;
	}
	case SCOPED_EVENT_OPERATOR_SUB_MSG: {
		ukuflow_event_mgr_handle_subscription(
				(struct generic_event_operator*) (((uint8_t*) data)
						+ sizeof(struct ukuflow_generic_msg)),
				data_len - sizeof(struct ukuflow_generic_msg));
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
	} // switch

}

/*---------------------------------------------------------------------------*/
static struct scopes_application ukuflow_scopes_callbacks = { add_scope,
		remove_scope, join_scope, leave_scope, ukuflow_net_mgr_handler };

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
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
 * \brief		TODO
 *
 * 				TODO
 */
bool ukuflow_net_mgr_open_scope(scope_id_t scope_id, void *specs,
		data_len_t spec_len, scope_ttl_t ttl) {

	PRINTF(1,
			"(UF-NET-MGR) Opening scope %u, len %u, ttl %u\n", scope_id, spec_len, ttl);

	return (scopes_open(UKUFLOW_SCOPES_SID, SCOPES_WORLD_SCOPE_ID, scope_id,
			specs, spec_len, SCOPES_FLAG_INTERCEPT | SCOPES_FLAG_DYNAMIC, ttl));
	// alternatively, as flag SCOPES_FLAG_NONE could be used, but it wouldn't enable event processing at non-member nodes on the way towards the sink
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_net_mgr_close_scope(scope_id_t scope_id) {

	PRINTF(1,
			"(UF-NET-MGR) Closing scope %u\n", scope_id);

	scopes_close(UKUFLOW_SCOPES_SID, scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_net_mgr_send_scope(scope_id_t scope_id, bool to_creator,
		void *data, data_len_t data_len) {
	PRINTF(1,
			"(UF-NET-MGR) Sending msg through scope %u, msg len %u, direction %u\n", scope_id, data_len, to_creator);
	scopes_send(UKUFLOW_SCOPES_SID, scope_id, to_creator, data, data_len);
}
/** @} */
