/**
 * \addtogroup event
 * @{
 */

/*
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
 * \file	event.c
 * \brief	Functions for the event data type in ukuFlow
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Aug 4, 2011
 */

#include "event.h"
#include "stdlib.h"

#include "logger.h"
/*---------------------------------------------------------------------------*/
/** \brief		Size of event fields. These correspond to the enumeration 'event_field' in event-types.h */
data_len_t event_field_width[] = { //
//		/** \brief EVENT_TYPE */
//		sizeof(event_type_t),
		/** \brief SENSOR */
		sizeof(uint8_t),
		/** \brief MAGNITUDE */
		sizeof(int16_t),
		/** \brief TIMESTAMP */
		sizeof(clock_time_t),
		/** \brief SOURCE_NODE */
		sizeof(rimeaddr_t),
		/** \brief SOURCE_SCOPE */
		sizeof(scope_id_t) //
		};

/*---------------------------------------------------------------------------*/
/** \brief		Names of event fields. These correspond to the enumeration 'event_field' */
char *event_field_names[] = { //
		//"event type",
				"sensor",//
				"magnitude", //
				"timestamp", //
				"source node", //
				"source scope" };
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
data_len_t event_operator_get_size(struct generic_event_operator *geo) {
	data_len_t size = 0;
	switch (geo->ev_op_type) {
	case IMMEDIATE_E_GEN: {
		size = sizeof(struct immediate_egen);
		break;
	}
	case ABSOLUTE_E_GEN: {
		size = sizeof(struct absolute_egen);
		break;
	}
	case OFFSET_E_GEN: {
		size = sizeof(struct offset_egen);
		break;
	}
	case RELATIVE_E_GEN: {
		// TODO: complete
		break;
	}
	case PERIODIC_E_GEN: {
		size = sizeof(struct periodic_egen);
		break;
	}
	case PATTERNED_E_GEN: {
		size = sizeof(struct patterned_egen);

		// now we calculate ceiling(pattern_len/8) using uint8_t arithmetic:
		size += (((struct patterned_egen*) geo)->pattern_len + 7) / 8;
		break;
	}
	case DISTRIBUTED_E_GEN: {
		size = sizeof(struct distribution_egen);
		break;
	}
	case SIMPLE_FILTER: {
		size = sizeof(struct simple_filter);
		uint8_t i;
		struct simple_filter *sf = (struct simple_filter*) geo;
		uint8_t *expression_ptr = ((uint8_t*) geo) + size;
		for (i = 0; i < sf->num_expressions; i++) {
			data_len_t expression_len = *expression_ptr;
			size += expression_len;
			expression_ptr += sizeof(data_len_t) + expression_len;
		}
		break;
	}
	case AND_COMPOSITION_FILTER:
		// intentionally left blank
	case OR_COMPOSITION_FILTER:
		// intentionally left blank
	case NOT_COMPOSITION_FILTER:
		// intentionally left blank
	case SEQUENCE_COMPOSITION_FILTER:
		// intentionally left blank
	case AVG_COMPOSITION_FILTER:
		// intentionally left blank
	case MIN_COMPOSITION_FILTER:
		// intentionally left blank
	case MAX_COMPOSITION_FILTER:
		// intentionally left blank
	case INCREASE_FILTER:
		// intentionally left blank
	case DECREASE_FILTER:
		// intentionally left blank
	case REMAIN_FILTER: {
		size = sizeof(struct composite_filter);
		break;
	}
	default: {
		size = 0;
	}
	} // switch

	return size;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		This function is passed to the expression evaluator in order to retrieve data from an event
 *
 * 				TODO
 *
 * @param[out] data_len			Returns the length, in bytes, of the input field requested
 * @param[in] requested_field	the number of the field requested
 * @param[in] custom_input		the event from where the field is gonna be retrieved
 * @return						the value of the field requested
 */
uint8_t* event_custom_input_function(data_len_t *data_len,
		uint8_t requested_field, void *custom_input) {
	struct event *event = (struct event*) custom_input;

	uint8_t field_nr;
	bool found = FALSE;
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	for (field_nr = 0; (field_nr < event->num_fields) && (!found); field_nr++) {
		if (*fvp == requested_field) {
			*data_len = event_field_width[requested_field];
			return fvp + sizeof(uint8_t);
		}
		fvp += sizeof(uint8_t) + event_field_width[*fvp];
	}

	return NULL;

}
/*---------------------------------------------------------------------------*/
/**
 * \brief	Allocates memory for a raw event and returns a pointer to it
 *
 * 			Allocates enough memory to store an event, and
 *			returns a pointer to the newly allocated memory segment.
 * 			If allocation fails, this function returns NULL.
 *
 * @param[out]	event_len	the length of the returned event
 *
 * \returns	Pointer to newly allocated memory containing empty, raw event, or
 * 			NULL if there was no space.
 * */
struct event *event_alloc_raw(data_len_t *event_len) {
	uint8_t field;
	// determine event payload size
	*event_len = 0;

	uint8_t num_fields = sizeof(event_field_width) / sizeof(uint8_t);

	/* now calculate the length of the event based on the individual lengths of the fields*/
	for (field = 0; field < num_fields; field++)
		/* the length is increased by 1 byte (for the field name) and the width of the field value */
		*event_len += sizeof(uint8_t) + event_field_width[field];

	struct event *event = malloc(sizeof(struct event) + *event_len);

	/* bail out if there was no space free*/
	if (event == NULL)
		return NULL;

	event->channel_id = 0;
	event->num_fields = num_fields;

	/* enter the field names already, but leave the values blank */
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	for (field = 0; field <= SOURCE_SCOPE; field++) {
		*fvp = field;
		fvp += sizeof(uint8_t) + event_field_width[field];
	}

	return event;
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Clones (copies) an event and returns a pointer to it.
 *
 *	 			Allocates enough memory to store the source event, copies the content
 * 				into it, and returns a pointer to the newly allocated memory segment.
 * 				If allocation fails, this function returns NULL.
 *
 * @param[in]	source_event		the source event
 * @param[in]	source_event_len	the length of the returned event
 *
 * \return		Pointer to newly allocated memory containing copied event, or
 * 				NULL if there was no space.
 * */
struct event *event_clone(struct event *source_event,
		data_len_t source_event_len) {

	struct event *event = malloc(source_event_len);

	if (event == NULL)
		return NULL;

	memcpy(event, source_event, source_event_len);

	return event;
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Populates an event's fields with primitive information.
 *
 * 				Populates an event's fields with primitive information.
 * 				The fields are filled with information from the common data
 * 				repository. Depending on the actual event generator's information
 * 				(which is passed as parameter), the respective data item from the
 * 				repository is fetched.
 *
 * @param[in] event	the event to populate
 * @param[in] g_egen
 */
void event_populate(struct event *event, struct generic_egen *g_egen) {

	data_len_t data_len;
	uint16_t *temp_data_ptr, temp_data;

	temp_data_ptr = (uint16_t*) data_mgr_get_data(0, g_egen->sensor, &data_len);

	if (temp_data_ptr == NULL)
		return;
	temp_data = *temp_data_ptr;

	clock_time_t time = clock_time();
	PRINTF(3,
			"(EVENT) time %lu, sensor %u, value %u\n", time, g_egen->sensor, temp_data);
	event->channel_id = g_egen->channel_id;
	event_set_value(event, SENSOR, &g_egen->sensor);
	event_set_value(event, MAGNITUDE, (uint8_t*) &temp_data);
	event_set_value(event, TIMESTAMP, (uint8_t*) &time);
	event_set_value(event, SOURCE_NODE, (uint8_t*) &rimeaddr_node_addr);
	event_set_value(event, SOURCE_SCOPE, &g_egen->scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Calculates and returns the size of the event.
 *
 * 				Calculates the size of the event, including the main structure
 * 				and the size of the individual fields-value-pairs. The returned
 * 				value is expressed in bytes.
 */
data_len_t event_get_size(struct event *event) {
	data_len_t event_size = sizeof(struct event);
	data_len_t field_size;
	uint8_t field;
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	for (field = 0; field < event->num_fields; field++) {
		field_size = sizeof(uint8_t) + event_field_width[*fvp];
		fvp += field_size;
		event_size += field_size;
	}

	return event_size;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Pretty prints an event's information
 *
 *  			TODO
 *  @param[in]	event Structure containing the event
 *  @param[in]	event_payload_len Length, in bytes, of the event's payload
 */
void event_print(struct event *event, data_len_t event_payload_len) {
	PRINTF(1,
			"Event - channel id %u, number of fields: %u, payload len: %u\n", event->channel_id, event->num_fields, event_payload_len);

	uint8_t field_nr, field;

	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	uint32_t value;

	for (field_nr = 0; field_nr < event->num_fields; field_nr++) {
		field = *fvp;
		fvp++;
		value = 0; // sets all 4 bytes from unit32_t to zero
		memcpy(&value, fvp, event_field_width[field]);
		fvp += event_field_width[field];
		PRINTF(1, "<%u, %s;%lu>\n", field, event_field_names[field], value);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static uint8_t *get_value_pointer(struct event *event, uint8_t searched_field) {
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);

	uint8_t field_nr = 0;
	while ((*fvp != searched_field) && (field_nr < event->num_fields)) {
		// if the current field is not the one we want to set, advance pointer
		fvp += sizeof(uint8_t) + event_field_width[*fvp];
	}

	if ((field_nr < event->num_fields) && (*fvp == searched_field))
		return fvp + sizeof(uint8_t);
	else
		return NULL;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void event_set_value(struct event *event, uint8_t searched_field, uint8_t *data) {
	// first, find position to store the data:
	uint8_t *fvp = get_value_pointer(event, searched_field);

	// if the field was found, copy
	if (fvp != NULL)
		memcpy(fvp, data, event_field_width[searched_field]);
	// else, the field was not found in this event!
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Returns the value of the specified field
 *
 * 				TODO
 */
uint8_t *event_get_value(struct event *event, uint8_t searched_field) {
	return get_value_pointer(event, searched_field);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Copies the fields of an event into the repository
 *
 * 				TODO
 */
data_repository_id_t event_into_repo(struct event *event) {

	data_repository_id_t repo_id = data_mgr_create(0);
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	if (repo_id != 0) {
		uint8_t field_nr;
		for (field_nr = 0; field_nr < event->num_fields; field_nr++) {
			// get value of field:
			uint8_t field = *fvp;
			// get pointer to value:
			uint8_t *data = fvp + 1;

			data_mgr_set_data(repo_id, field, MANUAL_UPDATE_ENTRY,
					event_field_width[field], data);

			// advance fvp pointer to next f-v-p
			fvp = fvp + event_field_width[field] + 1;
		}
	}

	return repo_id;
}

/** @} */
