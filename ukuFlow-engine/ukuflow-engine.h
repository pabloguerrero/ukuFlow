/**
 * \defgroup engine Workflow Engine
 * Implementation of ukuFlow's workflow engine
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
 * \file	ukuflow-engine.h
 * \brief	Header file for the workflow engine in ukuFlow
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	May 25, 2011
 */

#ifndef __UKUFLOW_ENGINE_H__
#define __UKUFLOW_ENGINE_H__

#include "lib/list.h"

#include "workflow.h"

/** \brief Delay to use after invoking a scope operation (open a scope / close a scope / send data through a scope) */
#define SCOPE_OPERATION_DELAY 5


/*---------------------------------------------------------------------------*/
/** \brief		Enumeration of workflow_node states */
enum wf_node_state_type {
	WFN_SPAWN /*								 0 */,
	WFN_RUNNING/*								 1 */,
	WFN_BLOCKED /*								 2 */,
};
/** \brief Type definition for workflow_node state type
 * \warning there is an enum with name wf_elem_type too */
typedef uint8_t wf_node_state_type_t;

/** \brief		Structure to manage the execution of a workflow */
struct workflow_node {
	/** \brief Pointer to next element whenever this workflow node is inserted in a list */
	struct workflow_node *next;

	/** \brief Pointer to workflow specification */
	struct workflow *wf;

	/** \brief Keeps track of the number of currently running instances, associated to this workflow node */
	uint8_t num_parallel_wf_instances;

	/** \brief Keeps track of the state the workflow node is (i.e., in which queue/list it currently is located) */
	wf_node_state_type_t state;

	/** \brief Keeps track of the number of times that this workflow needs to get instantiated */
	uint8_t num_loops_left;

};
/*---------------------------------------------------------------------------*/
/** \brief		TODO */
struct workflow_instance {
	/** \brief TODO */
	struct workflow_instance *next;

	/** \brief TODO */
	struct workflow_node *wfn;

	/** \brief TODO */
	data_repository_id_t repository_id;

	/** \brief Keeps track of the number of tokens associated to this instance */
	uint8_t num_tokens;
};
/*---------------------------------------------------------------------------*/
/** \brief		TODO */
struct workflow_token {
	/** \brief pointer to next token (for linking it in lists) */
	struct workflow_token *next;

	/** \brief pointer to parent token (for stacking/unstacking in gateways) */
	struct workflow_token *parent_token;

	/** \brief workflow instance to which this token belongs */
	struct workflow_instance *wf_instance;

	/** \brief current wf_elem_id of this token */
	wf_elem_id_t current_wf_elem_id;

	/** \brief previous wf_elem_id of this token */
	wf_elem_id_t prev_wf_elem_id;

	/** \brief pointer to token-specific state*/
	void *token_state;
};
/*---------------------------------------------------------------------------*/
/** \brief Token state for a computation statement */
struct comp_ex_task_token_state {
	/** \brief TODO */
	uint8_t statement_nr;
};
/** \brief Token state for a local function statement */
struct lfs_ex_task_token_state {
	/** \brief TODO */
	uint8_t statement_nr;
	/** \brief TODO */
	struct process *child_command_process;
};
/** \brief Token state for a scoped function statement */
struct sfs_ex_task_token_state {
	/** \brief TODO */
	uint8_t statement_nr;
	/** \brief TODO */
	char *cmd_line;
	/** \brief TODO */
	data_len_t cmd_line_len;
};

/** \brief Token state union for any statement */
union ex_task_token_state {
	/** \brief TODO */
	struct comp_ex_task_token_state comp_ex_task_token_state;
	/** \brief TODO */
	struct lfs_ex_task_token_state lfs_ex_task_token_state;
	/** \brief TODO */
	struct sfs_ex_task_token_state sfs_ex_task_token_state;
};
/*---------------------------------------------------------------------------*/
/** \brief Token state for a gateway */
struct gw_token_state {
	/** \brief TODO */
	uint8_t num_children_tokens;
};
/*---------------------------------------------------------------------------*/
/** \brief Token state for an event-based exclusive decision gateway */
struct eb_gw_token_state {
	/** \brief Number of children tokens that this gateway triggered so far (at most should be 1)*/
	uint8_t num_children_tokens;

	/** \brief Number of subscriptions already requested for this gateway */
	uint8_t registered_subscriptions;

	/** \brief Pointer to token created as child */
	struct workflow_token *child_token;

	/** \brief Indicator of whether unsubscribing has been requested or not */
	bool unsub_requested;
};
/*---------------------------------------------------------------------------*/
void ukuflow_engine_init();
bool ukuflow_engine_register(struct workflow *wf);
struct workflow *ukuflow_engine_deregister(uint8_t workflow_id);
void ukuflow_notify_unsubscription_ready(struct workflow_token *token);
/*---------------------------------------------------------------------------*/

#endif // __UKUFLOW_ENGINE_H__
/** @} */
