/**
 * \addtogroup workflow
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
 * \file	workflow-types.h
 * \brief	Workflow type declarations
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Mar 28, 2011
 */

#ifndef __WORKFLOW_TYPES_H__
#define __WORKFLOW_TYPES_H__

#include "contiki.h"

#include "scopes-types.h"
#include "data-mgr.h"
#include "event-types.h"

/** \brief Type definition for workflow element id */
typedef uint8_t wf_elem_id_t;

/** \brief Type definition for variable id */
typedef entry_id_t variable_id_t;

/** \brief Type definition for command id */
typedef uint8_t command_id_t;

/** \brief Type definition for workflow element type
 * \warning there is an enum with name wf_elem_type too */
typedef uint8_t wf_elem_type_t;

/** \brief Type definition for statement type
 * \warning there is an enum with name statement_type too */
typedef uint8_t statement_type_t;

/*---------------------------------------------------------------------------*/
/** \brief		Main data structure for representing a workflow in memory */
struct __attribute__((__packed__)) workflow {
	uint8_t workflow_id;

	/** \brief Number of workflow elements in this workflow specification */
	uint8_t num_wf_elems;

	/** \brief number of scopes that are found at the end of this workflow specification */
	uint8_t num_scopes;

	/** \brief defines the minimum number of instances that the workflow manager will try to keep
	 * 			instantiated in parallel. At runtime, however, there might be less than this number
	 * 			(e.g., because of missing resources). The lowest value for min_instances is 1 (i.e.,
	 * 			the user can't require there to be 0 parallel instances). The highest value for
	 * 			min_instances can be 255. */
	uint8_t min_wf_instances;

	/** \brief defines the maximum number of instances that the workflow manager will try to keep
	 * 			instantiated in parallel. At runtime there will never be more instances in parallel
	 * 			than those specified here. The lowest valid value for max_instances must be greater than
	 * 			or equal min_instances (if this doesn't hold, it will be set by the workflow manager
	 * 			to this value). The highest value for max_instances can be 255. */
	uint8_t max_wf_instances;

	/** \brief defines the looping properties. A workflow can be registered to be executed an infinite
	 * 			number of times (in this case the value 0 is used). Alternatively, it can be specified exactly
	 * 			how many times the workflow should be instantiated (in this case the looping value should
	 * 			be greater than 0, i.e., the user can't require it not to be executed, and less than or
	 * 			equal 255).*/
	uint8_t looping;

// followed by the workflow elements themselves
// and then followed by the scope information
};

/** \brief		Enumeration of types of tasks */
enum wf_elem_type {
	START_EVENT, /*								 0 */
	END_EVENT, /*								 1 */

	EXECUTE_TASK, /*							 2 */
	PUBLISH_TASK, /*							 3 */
	SUBSCRIBE_TASK, /*							 4 */
	SUBWORKFLOW_TASK, /*						 5 */

	FORK_GATEWAY, /*							 6 */
	JOIN_GATEWAY, /*							 7 */
	INCLUSIVE_DECISION_GATEWAY, /*				 8 */
	INCLUSIVE_JOIN_GATEWAY, /*					 9 */
	EXCLUSIVE_DECISION_GATEWAY, /*				10 */
	EXCLUSIVE_MERGE_GATEWAY, /*					11 */
	EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY /*	12 */
};

/*---------------------------------------------------------------------------*/
/** \brief		Set of elements contained by all workflow elements */
#define WF_GENERIC_ELEM_FIELDS	\
	/** \brief	TODO */ 		\
	wf_elem_id_t id;			\
	/** \brief	TODO */			\
	wf_elem_type_t elem_type;

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_generic_elem {
	WF_GENERIC_ELEM_FIELDS
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_start_event {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_end_event {
	WF_GENERIC_ELEM_FIELDS
};

/*---------------------------------------------------------------------------*/
/** \brief Enumeration for the types of statements */
enum statement_type {
	COMPUTATION_STATEMENT, /*					 0 */
	LOCAL_FUNCTION_STATEMENT, /*	 			 1 */
	SCOPED_FUNCTION_STATEMENT /*	 			 2 */
};

/** \brief	TODO */
struct __attribute__((__packed__)) statement {
	/** \brief	TODO */
	statement_type_t statement_type;
};

/** \brief	TODO */
struct __attribute__((__packed__)) computation_statement {
	/** \brief	TODO */
	statement_type_t statement_type;
	/** \brief	TODO */
	variable_id_t var_id;
	/** \brief	TODO */
	data_len_t data_expression_length;
// followed by uint8_t array with the data expression
};

/** \brief	TODO */
struct __attribute__((__packed__)) local_function_statement {
	/** \brief	TODO */
	statement_type_t statement_type;
	/** \brief	TODO */
	variable_id_t var_id;
// followed by statement_command
};

/** \brief	TODO */
struct __attribute__((__packed__)) scoped_function_statement {
	/** \brief	TODO */
	statement_type_t statement_type;
	/** \brief	TODO */
	scope_id_t scope_id;
// followed by statement_command
};

/** \brief	TODO */
struct __attribute__((__packed__)) statement_command {
	/** \brief	TODO */
	data_len_t cmd_len;
	/** \brief	TODO */
	uint8_t num_params;
// followed by char array with shell command name and list of parameters
};

/** \brief	TODO */
struct __attribute__((__packed__)) wf_ex_task {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	uint8_t num_statements;
// followed by a sequence of statements
};

/*---------------------------------------------------------------------------*/
/** \brief	TODO */
struct __attribute__((__packed__)) wf_publish_task {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
// ...
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_subscribe_task {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
// ...
};

/*---------------------------------------------------------------------------*/
/** \brief	TODO */
struct __attribute__((__packed__)) wf_subwf_task {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
// ...
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_fork_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	uint8_t out_flows;
// followed by sequence of wf_elem_id's of outgoing flows
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_join_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	uint8_t in_flows;
// followed by sequence of wf_elem_id's of incoming flows
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) conditional_flow {
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	uint8_t expression_length;
// followed by uint8_t array with the data expression
};

/*---------------------------------------------------------------------------*/
/** \brief	TODO */
struct __attribute__((__packed__)) wf_i_dec_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	uint8_t out_flows;
// followed by conditional flows, of which the last can be identified as default path by having an expression_length of 0
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_i_join_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	uint8_t in_flows;
// followed by sequence of wf_elem_id's of incoming flows
};

/*---------------------------------------------------------------------------*/
/** \brief	TODO */
struct __attribute__((__packed__)) wf_x_dec_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	uint8_t out_flows;
// followed by conditional flows, of which the last can be identified as default path by having an expression_length of 0
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_x_merge_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	uint8_t in_flows;
// followed by sequence of wf_elem_id's of incoming flows
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) event_operator_flow {
	/** \brief	TODO */
	wf_elem_id_t next_id;
	/** \brief	TODO */
	data_len_t total_event_operator_length; // in bytes
// followed by array with the event operator
};

/*---------------------------------------------------------------------------*/
struct __attribute__((__packed__)) wf_eb_x_dec_gw {
	WF_GENERIC_ELEM_FIELDS
	/** \brief	TODO */
	uint8_t num_out_flows;
// followed by the event operator flows
};

/*---------------------------------------------------------------------------*/
/** \brief	TODO */
struct __attribute__((__packed__)) scope_info {
	/** \brief	TODO */
	scope_id_t scope_id;
	/** \brief	TODO */
	scope_ttl_t scope_ttl;
	/** \brief	TODO */
	data_len_t scope_spec_len;
// followed by the scope specification
};

#endif /* __WORKFLOW_TYPES_H__ */

/** @} */
