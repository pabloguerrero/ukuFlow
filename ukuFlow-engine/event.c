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
#include "string.h"
#include "stdarg.h"

#include "logger.h"
/*---------------------------------------------------------------------------*/
/** \brief		Size of event fields. These correspond to the enumeration 'event_field' in event-types.h */
data_len_t event_field_width[] = { //
		/** \brief EVENT_TYPE */
		sizeof(event_operator_type_t),
		/** \brief EVENT_OPERATOR_ID */
		sizeof(event_operator_id_t),
		/** \brief SOURCE */
		sizeof(uint8_t),
		/** \brief MAGNITUDE */
		sizeof(int16_t),
		/** \brief TIMESTAMP */
		sizeof(clock_time_t),
		/** \brief ORIGIN_NODE */
		sizeof(rimeaddr_t),
		/** \brief ORIGIN_SCOPE */
		sizeof(scope_id_t),
		/** \brief COUNT */
		sizeof(uint16_t),
		/** \brief SUM */
		sizeof(uint16_t) //
		};

/*---------------------------------------------------------------------------*/
/** \brief		Names of event fields. These correspond to the enumeration 'event_field' */
char *event_field_names[] = { //
		"event type", /**			EVENT_TYPE*/
		"event operator id", /** 	EVENT_OPERATOR_ID*/
		"source", /** 				SOURCE*/
		"magnitude", /** 			MAGNITUDE*/
		"timestamp", /** 			TIMESTAMP*/
		"origin node", /** 			ORIGIN_NODE*/
		"origin scope", /** 		ORIGIN_SCOPE*/
		"count", /** 				COUNT*/
		"sum", /** 					SUM*/
		};
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 * @param geo
 * @return
 */
data_len_t event_operator_get_size(struct generic_event_operator *geo) {
	data_len_t size = 0;
	switch (geo->ev_op_type) {
	case IMMEDIATE_EG: {
		size = sizeof(struct immediate_egen);
		break;
	}
	case ABSOLUTE_EG: {
		size = sizeof(struct absolute_egen);
		break;
	}
	case OFFSET_EG: {
		size = sizeof(struct offset_egen);
		break;
	}
	case RELATIVE_EG: {
		size = sizeof(struct offset_egen);
		break;
	}
	case PERIODIC_EG: {
		size = sizeof(struct periodic_egen);
		break;
	}
	case PATTERN_EG: {
		size = sizeof(struct pattern_egen);

		// now we calculate ceiling(pattern_len/8) using uint8_t arithmetic:
		size += (((struct pattern_egen*) geo)->pattern_len + 7) / 8;
		break;
	}
	case FUNCTIONAL_EG: {
		size = sizeof(struct functional_egen);
		break;
	}
	case SIMPLE_EF: {
		size = sizeof(struct simple_filter);
		uint8_t i;
		struct simple_filter *sf = (struct simple_filter*) geo;
		uint8_t *expression_ptr = ((uint8_t*) geo) + size;
		for (i = 0; i < sf->num_expressions; i++) {
			data_len_t expression_len = *expression_ptr;
			size = size + sizeof(data_len_t) + expression_len;
			expression_ptr = expression_ptr + sizeof(data_len_t)
					+ expression_len;
		}
		break;
	}
	case AND_COMPOSITION_EF:
		// intentionally left blank
	case OR_COMPOSITION_EF:
		// intentionally left blank
	case NOT_COMPOSITION_EF: {
		size = sizeof(struct logical_composition_filter);
		break;
	}
	case SEQUENCE_COMPOSITION_EF: {
		size = sizeof(struct temporal_composition_filter);
		break;
	}
	case MIN_COMPOSITION_EF:
		// intentionally left blank
	case MAX_COMPOSITION_EF:
		// intentionally left blank
	case COUNT_COMPOSITION_EF:
		// intentionally left blank
	case SUM_COMPOSITION_EF:
		// intentionally left blank
	case AVG_COMPOSITION_EF:
		// intentionally left blank
	case STDEV_COMPOSITION_EF: {
		size = sizeof(struct processing_composition_filter);
		break;
	}
	case INCREASE_EF:
		// intentionally left blank
	case DECREASE_EF:
		// intentionally left blank
	case REMAIN_EF: {
		size = sizeof(struct change_filter);
		break;
	}
	default: {
		size = 0;
		break;
	}
	} // switch

	return (size);
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

	PRINTF(5, "getting event field %u\n", requested_field);
	uint8_t field_nr;
	bool found = FALSE;
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	for (field_nr = 0; (field_nr < event->num_fields) && (!found); field_nr++) {
		if (*fvp == requested_field) {
			*data_len = event_field_width[requested_field];
			return (fvp + sizeof(uint8_t));
		}
		fvp += sizeof(uint8_t) + event_field_width[*fvp];
	}

	return (NULL);

}

/**
 * \brief		Allocates memory for a raw event and returns a pointer to it
 *
 *	 			Allocates enough memory to store an event with the fields passed
 *	 			as parameter. Returns a pointer to the newly allocated memory segment.
 * 				If allocation fails, this function returns NULL.
 *
 * @param[in] num_fields the number of fields that this event will have
 * @param[in] ... the list of variable length of arguments, which are the event fields
 * @return an event with space for the requested fields, or NULL if no space is left
 */
struct event *event_alloc(int num_fields, ...) {
	va_list arg_list;

	va_start(arg_list, num_fields);

	/** First we need to find out the final size of the event */
	data_len_t event_len = sizeof(struct event);

	uint8_t field_nr, field;
	/** now calculate the length of the event based on the individual lengths of the requested fields*/
	for (field_nr = 0; field_nr < num_fields; field_nr++) {
		/** the length is increased by 1 byte (for the field name) and the width of the field value */
		field = va_arg(arg_list, uint16_t);
		event_len += sizeof(uint8_t) + event_field_width[field];
	}

	va_end(arg_list);

	/** Now that we know the size, allocate it and add the fields */
	struct event *event = malloc(event_len);

	PRINTF(2, "(EVENT) allocated %u bytes for event at %p\n", event_len, event);

	if (event) {
		event->channel_id = 0;
		event->num_fields = num_fields;

		va_start(arg_list, num_fields);

		uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
		for (field_nr = 0; field_nr < num_fields; field_nr++) {
			field = va_arg(arg_list, uint16_t);
			*fvp = field;
			fvp += sizeof(uint8_t) + event_field_width[field];
		}

		va_end(arg_list);

	}

	return (event);
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
 * @param[in]	source_event_len	the length of the event to be cloned
 *
 * \return		Pointer to newly allocated memory containing copied event, or
 * 				NULL if there was no space.
 * */
struct event *event_clone(struct event *source_event,
		data_len_t source_event_len) {

	struct event *cloned_event = malloc(source_event_len);

	PRINTF(2,
			"(EVENT) allocated %u bytes for cloned event at %p\n", source_event_len, cloned_event);

	if (cloned_event == NULL)
		return (NULL);

	memcpy(cloned_event, source_event, source_event_len);

	return (cloned_event);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Populates an event's fields with respective information.
 *
 * 				Populates an event's fields with primitive information.
 * 				The fields are filled with information from the common data
 * 				repository. Depending on the actual event generator's information
 * 				(which is passed as parameter), the respective data item from the
 * 				repository is fetched.
 *
 * @param[in] event the event to populate
 * @param[in] g_egen the event generator which indicates the data source from where to get the magnitude
 */
void event_populate(struct event *event, struct generic_egen *g_egen) {

	data_len_t data_len;
	uint16_t *temp_data_ptr, temp_data;

	temp_data_ptr = (uint16_t*) data_mgr_get_data(0, g_egen->source, &data_len);

	if (temp_data_ptr == NULL)
		return;
	temp_data = *temp_data_ptr;

	clock_time_t curr_time = clock_time() / CLOCK_SECOND;
	PRINTF(5,
			"(EVENT) time %lu, source %u, value %u\n", curr_time, g_egen->source, temp_data);
	event->channel_id = g_egen->channel_id;
	event_operator_type_t ev_type = SIMPLE_EVENT;
	event_set_value(event, EVENT_TYPE_F, (uint8_t*) &ev_type);
	event_set_value(event, EVENT_OPERATOR_ID_F, &g_egen->event_operator_id);
	event_set_value(event, SOURCE_F, &g_egen->source);
	event_set_value(event, MAGNITUDE_F, (uint8_t*) &temp_data);
	event_set_value(event, TIMESTAMP_F, (uint8_t*) &curr_time);
	event_set_value(event, ORIGIN_NODE_F, (uint8_t*) &rimeaddr_node_addr);
	event_set_value(event, ORIGIN_SCOPE_F, &g_egen->scope_id);
}

/*---------------------------------------------------------------------------*/
/**
 *
 * \brief		Calculates and returns the size of the event.
 *
 * 				Calculates the size of the event, including the main structure
 * 				and the size of the individual field-value-pairs. The returned
 * 				value is expressed in bytes.
 *
 * @param[in] event the event from which the length will be calculated
 * @return
 */
data_len_t event_get_len(struct event *event) {
	data_len_t event_size = sizeof(struct event);
	data_len_t field_size;
	uint8_t field;
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	for (field = 0; field < event->num_fields; field++) {
		field_size = sizeof(uint8_t) + event_field_width[*fvp];
		fvp += field_size;
		event_size += field_size;
	}

	return (event_size);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Pretty prints an event's information
 *
 *  			Prints the content's of an event in a pretty fashion :)
 *
 * @param[in]	event Structure containing the event
 * @param[in]	event_len Length, in bytes, of the event
 */
void event_print(struct event *event, data_len_t event_len) {
	PRINTF(2,
			"(EVENT) Event info: mem: %p, channel id %u, number of fields: %u, len: %u\n", event, event->channel_id, event->num_fields, event_len);

	uint8_t field_nr, field;

	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);
	uint32_t value;

	for (field_nr = 0; field_nr < event->num_fields; field_nr++) {
		field = *fvp;
		fvp++;
		value = 0; // sets all 4 bytes from unit32_t to zero
		memcpy(&value, fvp, event_field_width[field]);
		fvp += event_field_width[field];
		PRINTF(2, "<%u, %s;%lu>\n", field, event_field_names[field], value);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 * @param[in] event
 * @param[in] searched_field
 * \return
 */
static uint8_t *get_value_pointer(struct event *event, uint8_t searched_field) {
	uint8_t *fvp = ((uint8_t*) event) + sizeof(struct event);

	uint8_t field_nr = 0;
	while ((*fvp != searched_field) && (field_nr < event->num_fields)) {
		// if the current field is not the one we want to set, advance pointer
		fvp += sizeof(uint8_t) + event_field_width[*fvp];
	}

	if ((field_nr < event->num_fields) && (*fvp == searched_field))
		return (fvp + sizeof(uint8_t));
	else
		return (NULL);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief	TODO
 *
 * @param[in,out] event the event that receives the new value
 * @param[in] target_field the field that needs to be set
 * @param[in] data the data to set to this event
 */
void event_set_value(struct event *event, uint8_t target_field, uint8_t *data) {
	// first, find position to store the data:
	uint8_t *fvp = get_value_pointer(event, target_field);

	// if the field was found, copy
	if (fvp != NULL) {
		memcpy(fvp, data, event_field_width[target_field]);
	}
	// else, the field was not found in this event!
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Returns the value of the specified field
 *
 * @param[in] event the event where to search for the field
 * @param[in] searched_field the field searched
 * \return a pointer to the value
 */
uint8_t *event_get_value(struct event *event, uint8_t searched_field) {
	return (get_value_pointer(event, searched_field));
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Returns the length of the field specified field
 *
 * @param[in] searched_field the field for which we want to find out the width
 * \return the length of the field
 */
data_len_t event_get_field_len(uint8_t searched_field) {
	return (event_field_width[searched_field]);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Copies the fields of an event into the repository
 *
 *				TODO
 *
 * @param event
 * \return
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

	return (repo_id);
}

/** @} */
