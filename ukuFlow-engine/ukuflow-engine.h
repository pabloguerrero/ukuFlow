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

/** \brief Delay to use after invoking a scope operation (open a scope / close a scope / send data through a scope)*/
#define SCOPE_OPERATION_DELAY 5


/*---------------------------------------------------------------------------*/
/** \brief		TODO */
struct workflow_node {
	/** \brief */
	struct workflow_node *next;
	/** \brief */
	struct workflow *wf;
};
/*---------------------------------------------------------------------------*/
/** \brief		TODO */
struct workflow_instance {
	/** \brief */
	struct workflow_instance *next;

	/** \brief */
	struct workflow_node *wfn;

	/** \brief */
	uint8_t instance_id;

	/** \brief */
	data_repository_id_t repository_id;

	/** \brief */
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
	/** \brief */
	uint8_t statement_nr;
};
/** \brief Token state for a local function statement */
struct lfs_ex_task_token_state {
	/** \brief */
	uint8_t statement_nr;
	/** \brief */
	struct process *child_command_process;
};
/** \brief Token state for a scoped function statement */
struct sfs_ex_task_token_state {
	/** \brief */
	uint8_t statement_nr;
};

/** \brief Token state union for any statement */
union ex_task_token_state {
	struct comp_ex_task_token_state comp_ex_task_token_state;
	struct lfs_ex_task_token_state lfs_ex_task_token_state;
	struct sfs_ex_task_token_state sfs_ex_task_token_state;
};
/*---------------------------------------------------------------------------*/
/** \brief Token state for a gateway */
struct gw_token_state {
	/** \brief */
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
void ukuflow_engine_register(struct workflow *wf);
void ukuflow_notify_unsubscription_ready(struct workflow_token *token);
/*---------------------------------------------------------------------------*/

#endif // __UKUFLOW_ENGINE_H__
/** @} */
