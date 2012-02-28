/*
 * ukuflow-net-mgr.h
 *
 *  Created on: Jul 13, 2011
 *      Author: guerrero
 */

#ifndef __UKUFLOW_NET_MGR_H__
#define __UKUFLOW_NET_MGR_H__

#include "contiki.h"

#include "event-types.h"
#include "scopes.h"

/*---------------------------------------------------------------------------*/
/* Definitions for messaging in ukuFlow */

typedef uint8_t ukuflow_msg_type_t; // warning: there is an enum with name ukuflow_msg_type too!

/** \brief		Message types */
enum ukuflow_msg_type {
	SCOPED_FUNCTION_MSG = 0, //
	SCOPED_EVENT_OPERATOR_SUB_MSG, //
	SCOPED_EVENT_OPERATOR_UNSUB_MSG, //
	SCOPED_EVENT_MSG
};

/**
 * \brief		Generic header for network messages used in ukuFlow
 */
struct __attribute__((__packed__)) ukuflow_generic_msg {
	/** \brief @todo */
	ukuflow_msg_type_t msg_type;
};

/**
 * \brief	Header for scoped function statements
 */
struct __attribute__((__packed__)) sfs_msg {
	ukuflow_msg_type_t msg_type;
	uint8_t cmd_len;
// followed by char array with shell command and parameters
};

/**
 * \brief	Header for event operator subscription messages
 */
struct __attribute__((__packed__)) ukuflow_sub_msg {
	/** \brief @todo */
	ukuflow_msg_type_t msg_type;
	// followed by byte array with entire event operator(s) expression
};

/**
 * \brief	Header for event operator unsubscription messages
 */
struct __attribute__((__packed__)) ukuflow_unsub_msg {
	ukuflow_msg_type_t msg_type;
	channel_id_t main_ev_op_channel_id;
};

/**
 * \brief	Header for event messages
 */
struct __attribute__((__packed__)) ukuflow_event_msg {
	ukuflow_msg_type_t msg_type;
	data_len_t event_payload_len;
	// followed by byte array with entire event
};

/*---------------------------------------------------------------------------*/
void ukuflow_net_mgr_init();

bool ukuflow_net_mgr_open_scope(scope_id_t scope_id, void *specs,
		data_len_t spec_len, scope_ttl_t ttl);
void ukuflow_net_mgr_close_scope(scope_id_t scope_id);
void ukuflow_net_mgr_send_scope(scope_id_t scope_id, bool to_creator,
		void *data, data_len_t data_len);
/*---------------------------------------------------------------------------*/

#endif /* __UKUFLOW_NET_MGR */
