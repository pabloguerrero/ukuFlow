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
 * \file	ukuflow-net-mgr.h
 * \brief	Header file for the ukuFlow Network Manager
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 13, 2011
 */

#ifndef __UKUFLOW_NET_MGR_H__
#define __UKUFLOW_NET_MGR_H__

#include "contiki.h"

#include "event-types.h"
#include "scopes.h"

/*---------------------------------------------------------------------------*/
/* Definitions for messaging in ukuFlow */

/** \brief TODO */
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
	/** \brief TODO */
	ukuflow_msg_type_t msg_type;
};

/**
 * \brief	Header for scoped function statements
 */
struct __attribute__((__packed__)) sfs_msg {
	/** \brief TODO */
	ukuflow_msg_type_t msg_type;
	/** \brief TODO */
	data_len_t cmd_line_len;
// followed by char array with shell command and parameters
};

/**
 * \brief	Header for event operator subscription messages
 */
struct __attribute__((__packed__)) ukuflow_sub_msg {
	/** \brief Indication of the message type */
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
/** @} */
