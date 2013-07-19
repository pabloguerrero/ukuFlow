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
 * \file	event-types.h
 * \brief	Header file for the definition of types and structures for the event data type
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Aug 4, 2011
 */

#ifndef __EVENTTYPES_H__
#define __EVENTTYPES_H__

#include "scopes-types.h"
#include "net/rime.h" // for enabling referencing rimeaddr_t
/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**                              Event Operators                             */
/*---------------------------------------------------------------------------*/

/** \brief Types of operators */
typedef uint8_t event_operator_type_t;
/** \brief Connecting channel id */
typedef uint8_t channel_id_t;

/** \brief Types of event operators */
enum event_operator_type {
	// * Event generators
	// ** non-recurring:
	IMMEDIATE_EG, /** 							 0 */
	ABSOLUTE_EG, /**							 1 */
	OFFSET_EG, /**								 2 */
	RELATIVE_EG, /**							 3 */

	// ** recurring:
	PERIODIC_EG, /**							 4 */
	PATTERN_EG, /**								 5 */
	FUNCTIONAL_EG, /**							 6 */

	// * Filters:
	// ** Simple event filter:
	SIMPLE_EF, /**								 7 */

	// ** Composite event operators:
	// *** Logical filters:
	AND_COMPOSITION_EF, /**						 8 */
	OR_COMPOSITION_EF, /**						 9 */
	NOT_COMPOSITION_EF, /**						10 */
	// *** Temporal filters:
	SEQUENCE_COMPOSITION_EF, /**				11 */
	// *** Processing functions
	MIN_COMPOSITION_EF, /**						12 */
	MAX_COMPOSITION_EF, /**						13 */
	COUNT_COMPOSITION_EF, /**					14 */
	SUM_COMPOSITION_EF, /**						15 */
	AVG_COMPOSITION_EF, /**						16 */
	//MEDIAN?
	STDEV_COMPOSITION_EF, /**					17 */
	//HISTOGRAM?

	// *** Change event filters:
	INCREASE_EF, /**							18 */
	DECREASE_EF, /**							19 */
	REMAIN_EF /**								20 */
};

/** \brief		TODO				*/
#define GENERIC_EVENT_OPERATOR_FIELDS	\
	/** \brief	Type of event */  		\
	event_operator_type_t ev_op_type;	\
	/** \brief	Output channel id (i.e., events that match with this event operator will be labeled with this channel number) */ \
	channel_id_t channel_id;

/** \brief		TODO				*/
struct __attribute__((__packed__)) generic_event_operator {
	GENERIC_EVENT_OPERATOR_FIELDS
};

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**                             Event Generators                             */
/*---------------------------------------------------------------------------*/

/** \brief Macro definition for the case of a scope id of local event generator */
#define LOCAL_EVENT_GENERATOR 0

/** \brief Generic event generator fields */
#define GENERIC_EVENT_GENERATOR_FIELDS					\
	GENERIC_EVENT_OPERATOR_FIELDS						\
	/** \brief Source from which data will be read */	\
	/* (corresponds to enum repository_fields */		\
	/* in data-mgr.h) */								\
	uint8_t source;               						\
	/* Id of scope whose members will participate, or LOCAL_EVENT_GENERATOR if only local */ 	\
	scope_id_t scope_id;

/** \brief		Abstract definition for recurrent event generators	*/
#define RECURRENT_EVENT_GENERATOR_FIELDS						\
	GENERIC_EVENT_GENERATOR_FIELDS								\
	/** \brief	Number of repetitions (or 0 for infinite) */	\
	uint8_t repetitions;										\
	/** \brief Period for generating the events, in seconds */	\
	uint16_t period;

/** \brief		TODO				*/
struct __attribute__((__packed__)) generic_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) immediate_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) absolute_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	/** \brief	TODO */
	clock_time_t when;
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) offset_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	/** \brief	TODO */
	uint16_t offset;
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) relative_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	/** \brief	TODO */
	clock_time_t offset;
// followed by expression to which this event is relative
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) recurrent_egen {
	RECURRENT_EVENT_GENERATOR_FIELDS
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) periodic_egen {
	RECURRENT_EVENT_GENERATOR_FIELDS
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) pattern_egen {
	RECURRENT_EVENT_GENERATOR_FIELDS
	/** \brief	The length of the pattern, expressed in number of bits */
	uint8_t pattern_len;
// followed by as many bytes as ceiling([pattern_len]/8)
};

/** \brief		TODO				*/
typedef uint8_t functional_generator_t;

/** \brief		TODO				*/
enum functional_generator {
	GAUSSIAN_DISTRIBUTION = 0, /*    http://en.wikipedia.org/wiki/Gaussian_distribution */
	CHI_SQUARE_DISTRIBUTION = 1, /*  http://en.wikipedia.org/wiki/Chi-square_distribution */
	PARETO_DISTRIBUTION = 2 /*       http://en.wikipedia.org/wiki/Pareto_distribution */
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) functional_egen {
	RECURRENT_EVENT_GENERATOR_FIELDS
	/** \brief	TODO */
	functional_generator_t generator_function;
// followed by parameters to generator function
};

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**                                    Filters                               */
/*---------------------------------------------------------------------------*/
/** \brief		Structure for simple filters				*/
struct __attribute__((__packed__)) simple_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	/** \brief	TODO */
	uint8_t num_expressions;
// followed by pairs of <expression length, the expression itself>
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) composite_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	/** \brief	TODO */
	uint16_t window_size;
};
struct __attribute__((__packed__)) logical_composite_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	// TODO: complete
};

// TODO: replace with corresponding processing functions
struct __attribute__((__packed__)) processing_function_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
};

struct __attribute__((__packed__)) temporal_composite_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	// TODO: complete
};

/*
 struct __attribute__((__packed__)) increase_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 };

 struct __attribute__((__packed__)) decrease_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 };

 struct __attribute__((__packed__)) remain_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 };
 */

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**                                   Events                                 */
/*---------------------------------------------------------------------------*/
/** \brief		Main structure for an event for its network transport: */
struct __attribute__((__packed__)) event {
	channel_id_t channel_id;
	uint8_t num_fields;
// followed by <field-value> pairs
};

/** \brief		Types of event */
typedef uint8_t event_type_t;

/** \brief		Enumeration for event types */
enum event_type {
//	/** \brief	TODO */
	SIMPLE_EVENT = 0,
	/** \brief	TODO */
	COMPOSITE_EVENT
};

/** \brief		Enumeration for event fields */
enum event_field {
//	/** \brief	TODO */
	EVENT_TYPE = 0,
	/** \brief	Valid values for the SOURCE field are those from the 'enum repository_fields' in data-mgr.h */
	SOURCE,
	/** \brief	TODO */
	MAGNITUDE,
	/** \brief	TODO */
	TIMESTAMP,
	/** \brief	TODO */
	ORIGIN_NODE,
	/** \brief	TODO */
	ORIGIN_SCOPE
};

#endif /** __EVENTTYPES_H__ */
/**@}*/
