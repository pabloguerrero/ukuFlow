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
 * \file	workflow.c
 * \brief	Functions for the workflow data type
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	May 18, 2011
 */

#include "workflow.h"
#include <stdio.h>
#include "logger.h"

#ifdef WITH_WF_NAMES
#undef WITH_WF_NAMES
#endif
//#define WITH_WF_NAMES 0
/*---------------------------------------------------------------------------*/
#ifdef WITH_WF_NAMES
/*---------------------------------------------------------------------------*/
/**
 * \brief		Collection of names for the different workflow elements
 */
char *wf_elem_names[] = {"start event", "end event", "execute task",
	"publish task", "subscribe task", "subworkflow task", "fork gateway",
	"join gateway", "inclusive decision gateway", "inclusive join gateway",
	"exclusive decision gateway", "exclusive merge gateway",
	"event based exclusive decision gateway"};
/*---------------------------------------------------------------------------*/
/**
 * \brief		Collection of names for the different statement types
 */
char *wf_statement_names[] = {"computation statement",
	"local function statement", "scoped function statement"};
#endif
/*---------------------------------------------------------------------------*/
/**
 * \brief		Returns the wf_elem with the requested id, or NULL otherwise
 *
 *				Searches and returns the struct wf_elem with the specified id
 *				within the workflow passed as parameter, if it exists, or
 *				NULL otherwise.
 *
 *	\param wf	the workflow in which the element needs to be looked up
 *	\param wf_elem_id the id of the element that is being searched for
 *
 * \return		the wf_elem with the requested id, or NULL otherwise
 */
struct wf_generic_elem *workflow_get_wf_elem(struct workflow *wf,
		wf_elem_id_t wf_elem_id) {

	PRINTF(5,"searched: %u\n", wf_elem_id);
	// was the number of the element required correct?
	if (wf_elem_id < wf->num_wf_elems) {
		wf_elem_id_t wf_elem_nr = 0;
		struct wf_generic_elem *wfe = (struct wf_generic_elem*) ((uint8_t*) (wf)
				+ sizeof(struct workflow));

		while ((wf_elem_nr < wf->num_wf_elems) && (wfe->id != wf_elem_id)) {
			PRINTF(5,"found %u\n", wfe->id);
			wfe = (struct wf_generic_elem*) (((uint8_t*) wfe)
					+ workflow_wf_elem_size(wfe));
			wf_elem_nr++;
		}

		PRINTF(5,"found %u\n", wfe->id);

		/* was the element found? */
		if (wf_elem_nr < wf->num_wf_elems)
			// yes:
			return (wfe);
		//	else
	}
	// no:
	return (NULL);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Searches within an execute task for a statement with the given number.
 *
 *				Searches and returns the statement with the specified number
 *				within the execute task passed as parameter, if it exists, or
 *				NULL otherwise.
 *
 *	\param wf_ex		the execute task in which the statement needs to be looked up
 *	\param statement_nr the number of the statement that is being searched for
 *	\return				a statement with the requested number if one is found,
 *						or NULL otherwise.
 */
struct statement *workflow_get_statement(struct wf_ex_task *wf_ex,
		uint8_t statement_nr) {
	if (statement_nr < wf_ex->num_statements) {
		struct statement *st = (struct statement*) ((uint8_t*) wf_ex)
				+ sizeof(struct wf_ex_task);
		uint8_t st_nr;
		for (st_nr = 0; st_nr < wf_ex->num_statements && st_nr != statement_nr;
				st_nr++)
			st = (struct statement*) (((uint8_t*) st)
					+ workflow_statement_size(st));
		if (st_nr < wf_ex->num_statements)
			return (st);
		else
			return (NULL);
	} else
		return (NULL);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
uint8_t *workflow_get_data_expression(struct computation_statement *cst) {
	return (((uint8_t*) cst) + sizeof(struct computation_statement));
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
char *workflow_get_function_statement(struct statement *st) {
	if (st->statement_type == LOCAL_FUNCTION_STATEMENT)
		return (((char*) st) + sizeof(struct local_function_statement));
	else if (st->statement_type == SCOPED_FUNCTION_STATEMENT)
		return (((char*) st) + sizeof(struct scoped_function_statement));
	else
		return (NULL);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
data_len_t workflow_wf_elem_size(struct wf_generic_elem *wfe) {
	data_len_t size = 0;
	switch (wfe->elem_type) {
	case START_EVENT: {
		size = sizeof(struct wf_start_event);
		break;
	}
	case END_EVENT: {
		size = sizeof(struct wf_end_event);
		break;
	}
	case EXECUTE_TASK: {

		size = sizeof(struct wf_ex_task);
		PRINTF(5,"exec task, size +4? %u\n", sizeof(struct wf_ex_task));
		struct wf_ex_task *wf_ex = (struct wf_ex_task*) wfe;

		struct statement *st = (struct statement*) ((((uint8_t*) wf_ex)
				+ sizeof(struct wf_ex_task)));

		uint8_t statement_nr, statement_size;

		for (statement_nr = 0; statement_nr < wf_ex->num_statements;
				statement_nr++) {
			statement_size = workflow_statement_size(st);
			PRINTF(5,"statement size %u\n", statement_size);
			size += statement_size;
			st += statement_size;
		}
		break;
	}
	case PUBLISH_TASK: {
		/* TODO: calculate here */
		break;
	}
	case SUBSCRIBE_TASK: {
		/* TODO: calculate here */
		break;
	}
	case SUBWORKFLOW_TASK: {
		/* TODO: calculate here */
		break;
	}
	case FORK_GATEWAY: {
		size = sizeof(struct wf_fork_gw);
		struct wf_fork_gw *fgw = (struct wf_fork_gw*) wfe;
		size += fgw->out_flows;
		break;
	}
	case JOIN_GATEWAY:
		// intentionally left empty
	case INCLUSIVE_JOIN_GATEWAY:
		// intentionally left empty
	case EXCLUSIVE_MERGE_GATEWAY: {
		size = sizeof(struct wf_join_gw);
		struct wf_join_gw *jgw = (struct wf_join_gw*) wfe;
		size += jgw->in_flows;
		break;
	}
	case INCLUSIVE_DECISION_GATEWAY:
		// intentionally left empty
	case EXCLUSIVE_DECISION_GATEWAY: {
		size = sizeof(struct wf_i_dec_gw);
		struct wf_i_dec_gw *idgw = (struct wf_i_dec_gw*) wfe;
		struct conditional_flow *flow =
				(struct conditional_flow*) (((uint8_t*) wfe) + size);
		uint8_t i;
		for (i = 0; i < idgw->out_flows; i++) {
			size += sizeof(struct conditional_flow) + flow->expression_length;

			flow =
					(struct conditional_flow*) ((uint8_t*) flow
							+ sizeof(struct conditional_flow)
							+ flow->expression_length);

		}

		break;
	}
	case EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY: {
		size = sizeof(struct wf_eb_x_dec_gw);
		struct wf_eb_x_dec_gw *ebg = (struct wf_eb_x_dec_gw*) wfe;
		struct event_operator_flow *ev_op_flow =
				(struct event_operator_flow*) (((uint8_t*) wfe) + size);
		uint8_t out_flow;
		for (out_flow = 0; out_flow < ebg->num_out_flows; out_flow++) {
			size += sizeof(struct event_operator_flow)
					+ ev_op_flow->total_event_operator_length;
			ev_op_flow = (struct event_operator_flow*) (((uint8_t*) ev_op_flow)
					+ sizeof(struct event_operator_flow)
					+ ev_op_flow->total_event_operator_length);
		}

		break;
	}
	default: {
		return (0);
	}
	} // switch

	return (size);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Calculates and returns the size of a statement (in bytes)
 *
 * 				TODO
 */
data_len_t workflow_statement_size(struct statement *st) {
	data_len_t statement_len = 0;

	/* Treat a computation statement differently from the other (local function statement and scoped function statement) */
	if (st->statement_type == COMPUTATION_STATEMENT) {
		/* size is the sum of fixed-width structure plus variable-width expression  */
		statement_len = sizeof(struct computation_statement)
				+ ((struct computation_statement*) st)->data_expression_length;

	} else { // LOCAL_FUNCTION_STATEMENT or SCOPED_FUNCTION_STATEMENT

		struct statement_command *st_cmd;
		/* size equals the sum of: fixed-width structure + */
		if (st->statement_type == LOCAL_FUNCTION_STATEMENT)
			statement_len = sizeof(struct local_function_statement);
		else
			statement_len = sizeof(struct scoped_function_statement);

		st_cmd = (struct statement_command *) (((uint8_t*) st)
				+ sizeof(struct local_function_statement));

		/* + size of fixed-width statement command structure + length of variable-width command name + */
		statement_len += sizeof(struct statement_command) + st_cmd->cmd_len;
		uint8_t param_num;
		data_len_t cmd_index = 0;
		uint8_t *st_cmd_arr = ((uint8_t*) st_cmd)
				+ sizeof(struct statement_command) + st_cmd->cmd_len;

		for (param_num = 0; param_num < st_cmd->num_params; param_num++) {
			PRINTF(5,"param: %d %d %d st len %d \n", st_cmd_arr[cmd_index],
					st_cmd_arr[cmd_index + 1], st_cmd_arr[cmd_index + 2],
					statement_len);

			switch (st_cmd_arr[cmd_index]) {
			case (STRING_VALUE): {
				statement_len += 2 + st_cmd_arr[cmd_index + 1];
				cmd_index += 2 + st_cmd_arr[cmd_index + 1];
				break;
			} /* case */
			case (REPOSITORY_VALUE): {
				statement_len += 2;
				cmd_index += 2;
				break;
			} /* case */
			case (UINT8_VALUE):
				// intentionally left blank
			case (INT8_VALUE): {
				statement_len += 2;
				cmd_index += 2;
				break;
			} /* case */
			case (UINT16_VALUE):
				// intentionally left blank
			case (INT16_VALUE): {
				statement_len += 3;
				cmd_index += 3;
				break;
			} /* case */
			} /* switch */
		} /* for */
		PRINTF(5,"for lfs|sfs %d, size: %d\n", param_num, statement_len);
	} /* else */

	return (statement_len);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Return the event operator information contained in a workflow
 * 				specification
 *
 * 				Given a pointer to an event-based exclusive decision gateway
 * 				(which is a part of a workflow definition) and an outflow
 * 				number, returns a pointer to the event operator flow data
 * 				structure.
 **/
struct event_operator_flow *workflow_get_event_operator_flow(
		struct wf_eb_x_dec_gw* ebg, uint8_t outflow_nr) {

	if (outflow_nr > ebg->num_out_flows)
		return (NULL);

	struct event_operator_flow *ev_op_flow =
			(struct event_operator_flow *) ((uint8_t*) ebg
					+ sizeof(struct wf_eb_x_dec_gw));
	uint8_t current_flow = 0;
	while (outflow_nr != current_flow++)

		ev_op_flow = (struct event_operator_flow *) ((uint8_t*) ev_op_flow
				+ sizeof(struct event_operator_flow)
				+ ev_op_flow->total_event_operator_length);

	return (ev_op_flow);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		Return the scope info contained in a workflow specification
 *
 *	 			Given a workflow definition and a scope id, returns a pointer
 *	 			to the information of the scope (namely, its time-to-live and
 *	 			the scope spec).
 *
 **/
struct scope_info *workflow_get_scope_info(struct workflow *wf,
		scope_id_t searched_scope_id) {
	/* first advance to last wf_elem */
	uint8_t *ptr = (uint8_t*) workflow_get_wf_elem(wf, wf->num_wf_elems - 1);

	if (!ptr)
		return (NULL);

	/* now advance past the last wf_elem (should be an end event) */
	ptr += workflow_wf_elem_size((struct wf_generic_elem*) ptr);

	uint8_t scope_nr = 0;
	struct scope_info *s_i = (struct scope_info*) ptr;
	while (scope_nr < wf->num_scopes) {

		if (s_i->scope_id == searched_scope_id)
			return (s_i);
		else {
			s_i = (struct scope_info*) (((uint8_t*) s_i)
					+ sizeof(struct scope_info) + s_i->scope_spec_len);
			scope_nr++;
		}
	}
	return (NULL);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
char *get_wf_elem_type_name(struct wf_generic_elem *wfe) {
#ifdef WITH_WF_NAMES
	return (wf_elem_names[wfe->elem_type]);
#else
	return ("");
#endif
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
char *get_wf_statement_name(statement_type_t st) {
#ifdef WITH_WF_NAMES
	return (wf_statement_names[st]);
#else
	return ("");
#endif
}

/** @} */
