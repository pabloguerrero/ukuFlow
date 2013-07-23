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
 * \file	ukuflow-event-mgr.h
 * \brief	Header file for the event manager in ukuFlow
 *
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Aug 15, 2011
 */

#ifndef __UKUFLOW_EVENT_MGR_H__
#define __UKUFLOW_EVENT_MGR_H__

#include "ukuflow-net-mgr.h"
#include "event-types.h"
#include "event.h"
//Following file included because of function "subscribe"
#include "workflow-types.h"
//Following file included because of function "unsubscribe"
#include "ukuflow-engine.h"

void ukuflow_event_mgr_init();
bool ukuflow_event_mgr_subscribe(struct generic_event_operator *main_ev_op,
		data_len_t ev_op_len, void(*notify)(struct event *event,
				data_len_t event_payload_len), struct workflow *wf);
bool ukuflow_event_mgr_unsubscribe(struct generic_event_operator *main_ev_op,
		struct workflow_token* token);

void ukuflow_event_mgr_handle_subscription(
		struct generic_event_operator *main_ev_op, data_len_t ev_op_len, bool local);
void
ukuflow_event_mgr_handle_unsubscription(channel_id_t main_ev_op_channel_id, bool local);
void ukuflow_event_mgr_handle_event(struct ukuflow_event_msg *event_msg);

void ukuflow_event_mgr_scope_left(scope_id_t scope_id);

#endif /* __UKUFLOW_EVENT_MGR_H__ */

/** @} */
