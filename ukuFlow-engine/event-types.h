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
	IMMEDIATE_E_GEN /** 			 0 */,
	ABSOLUTE_E_GEN /**				 1 */,
	OFFSET_E_GEN /**				 2 */,
	RELATIVE_E_GEN /**				 3 */,

	// ** recurring:
	PERIODIC_E_GEN /**				 4 */,
	// *** aperiodic:
	PATTERNED_E_GEN /**				 5 */,
	DISTRIBUTED_E_GEN /**			 6 */,

	// * Filters:
	// ** Simple event filter:
	SIMPLE_FILTER /**				 7 */,

	// ** Composite event operators:
	// *** Logical filters:
	AND_COMPOSITION_FILTER /**		 8 */,
	OR_COMPOSITION_FILTER /**		 9 */,
	NOT_COMPOSITION_FILTER /**		10 */,
	// *** Temporal filters:
	SEQUENCE_COMPOSITION_FILTER /**	11 */,
	// *** Processing functions
	MIN_COMPOSITION_FILTER /**		12 */,
	MAX_COMPOSITION_FILTER /**		13 */,
	COUNT_COMPOSITION_FILTER /**	14 */,
	SUM_COMPOSITION_FILTER /**		15 */,
	AVG_COMPOSITION_FILTER /**		16 */,
	//MEDIAN?
	STDEV_COMPOSITION_FILTER /**	17 */,
	//HISTOGRAM?

	// *** Change event filters:
	INCREASE_FILTER /**				18 */,
	DECREASE_FILTER /**				19 */,
	REMAIN_FILTER /**				20 */,
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

#define GENERIC_EVENT_GENERATOR_FIELDS					\
	GENERIC_EVENT_OPERATOR_FIELDS						\
	/** Sensor which will be read */					\
	/** (corresponds to enum repository_fields */		\
	/** in data-repository.h) */						\
	uint8_t sensor;               						\
	/** Id of scope whose members will participate */ 	\
	scope_id_t scope_id;

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
	clock_time_t when;
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) offset_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	clock_time_t offset;
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) relative_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
// TODO: complete
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) periodic_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	/** \brief Period for generating the events, in seconds */
	uint16_t period;
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) patterned_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	clock_time_t period;
	uint8_t pattern_len; // the length of the pattern is expressed in number of bits
// followed by as many bytes as ceiling([pattern_len]/8)
};

/** \brief		TODO				*/
typedef uint8_t probability_distribution_function_t;
enum probability_distribution_function {
	NORMAL_DISTRIBUTION = 0, // http://en.wikipedia.org/wiki/Normal_distribution
	CHI_SQUARE_DISTRIBUTION = 1, // http://en.wikipedia.org/wiki/Chi-square_distribution
	PARETO_DISTRIBUTION = 2,
// http://en.wikipedia.org/wiki/Pareto_distribution
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) distribution_egen {
	GENERIC_EVENT_GENERATOR_FIELDS
	uint16_t period;
	probability_distribution_function_t pdf_type;
};

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**                                    Filters                               */
/*---------------------------------------------------------------------------*/
/** \brief		TODO				*/
struct __attribute__((__packed__)) simple_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	uint8_t num_expressions;
// followed by pairs of <expression length, the expression itself>
};

/** \brief		TODO				*/
struct __attribute__((__packed__)) composite_filter {
	GENERIC_EVENT_OPERATOR_FIELDS
	uint16_t window;
};
/*
 struct __attribute__((__packed__)) logical_composite_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 // TODO: complete
 };

 struct __attribute__((__packed__)) temporal_composite_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 // TODO: complete
 };

 // TODO: replace with corresponding processing functions
 struct __attribute__((__packed__)) processing_function_filter {
 GENERIC_EVENT_OPERATOR_FIELDS
 };

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
/** \brief		Main structure for an event: */
struct __attribute__((__packed__)) event {
	channel_id_t channel_id;
	uint8_t num_fields;
// followed by <field-value> pairs
};

/** \brief		Types of event */
typedef uint8_t event_type_t;

/** \brief		Enumeration for event fields */
enum event_field {
//	/** \brief	TODO */
//	EVENT_TYPE = 0,
	/** \brief	Valid values for the SENSOR field are those from the 'enum repository_fields' in data-mgr.h */
	SENSOR,
	/** \brief	TODO */
	MAGNITUDE,
	/** \brief	TODO */
	TIMESTAMP,
	/** \brief	TODO */
	SOURCE_NODE,
	/** \brief	TODO */
	SOURCE_SCOPE
};

#endif /** __EVENTTYPES_H__ */
/**@}*/
