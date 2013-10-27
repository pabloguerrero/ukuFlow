/**
 * \defgroup event Event Data Type
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
 * \file	event.h
 * \brief	Header file for the event data type
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Aug 4, 2011
 */

#ifndef __EVENT_H__
#define __EVENT_H__

#include "event-types.h"

struct event *event_alloc(int num_fields, ...);
struct event *event_clone(struct event *source_event,
		data_len_t source_event_len);
data_len_t event_get_len(struct event *event);
uint8_t *event_get_value(struct event *event, uint8_t searched_field);
data_len_t event_get_field_len(uint8_t searched_field);
data_len_t event_operator_get_size(struct generic_event_operator *geo);
void event_populate(struct event *event, struct generic_egen *g_egen);
void event_print(struct event *event, data_len_t event_len);
void
event_set_value(struct event *event, uint8_t target_field, uint8_t *data);
uint8_t* event_custom_input_function(data_len_t *data_len,
		uint8_t requested_field, void *custom_input);
#endif /* __EVENT_H__ */
/** @} */
