/**
 * \addtogroup engine
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
 * \file	ukuflow-engine.c
 * \brief	Workflow Engine in ukuFlow
 *
 *			The workflow engine takes care of registering, instantiating and executing
 *			workflows. This is done by allocating the necessary tokens and releasing them
 *			as needed.
 *
 * \details	This file groups the functions of the event manager in ukuFlow.
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	May 18, 2011
 */

/** for malloc and free: */
#include "stdlib.h"

#include "contiki.h"
#include "net/rime.h"
#include "shell.h"

#include "ukuflow-engine.h"
#include "ukuflow-event-mgr.h"
#include "ukuflow-net-mgr.h"
#include "ukuflow-cmd-runner.h"

#include "scopes-selfur.h"

#include "expression-eval.h"
#include "logger.h"

#include "lib/memb.h"
#include "lib/list.h"

/** \brief Maximum amount of workflows that can be registered at this node */
#define MAX_REGISTERED_WORKFLOWS 5

/** \brief Maximum amount of workflows that can be instantiated at this node */
#define MAX_INSTANTIATED_WORKFLOWS 10

/** \brief Maximum amount of tokens that can be instantiated at this node */
#define MAX_ACTIVE_TOKENS 20

/**---------------------------------------------------------------------------*/
/**---------------------------------------------------------------------------*/
/** Declaration of global variables */

/**---------------------------------------------------------------------------*/
/** Declaration of static processes */

/** \brief Protothread for the long term scheduler*/
PROCESS(ukuflow_long_term_scheduler_process, "long term scheduler");

/** \brief Protothread for the short term scheduler*/
PROCESS(ukuflow_short_term_scheduler_process, "short term scheduler");

/** \brief Protothread for listening to terminated processes */
PROCESS(ukuflow_termination_listener_process, "process termination listener");

/** \brief Protothread for executing scoped function statements */
PROCESS(ukuflow_scoped_function_statement_process, "scoped function statement coordination process");

/**---------------------------------------------------------------------------*/
/** Forward declaration of functions and protothreads*/
void notified_by_event_mgr(struct event *event, data_len_t event_payload_len);

/**---------------------------------------------------------------------------*/

/** \brief Prototype for token processing function*/
typedef void (*token_processor)(struct workflow_token *,
		struct wf_generic_elem *);

static void processor_start_event(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_end_event(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_execute_task(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_publish_task(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_subscribe_task(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_subworkflow_task(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_fork_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_join_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_inclusive_decision_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_inclusive_join_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_exclusive_decision_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_exclusive_merge_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe);
static void processor_event_based_exclusive_decision_gateway(
		struct workflow_token *token, struct wf_generic_elem *wfe);

/**---------------------------------------------------------------------------*/
/** \brief Array of token processors */
token_processor token_processors[] = { //
		//
				&processor_start_event, /*								 0 */
				&processor_end_event, /*								 1 */
				/*NULL,//*/&processor_execute_task, /*								 2 */
				NULL,//&processor_publish_task, /*								 3 */
				NULL,//&processor_subscribe_task, /*							 4 */
				NULL,//&processor_subworkflow_task, /*							 5 */
				NULL,//&processor_fork_gateway, /*								 6 */
				NULL,//&processor_join_gateway, /*								 7 */
				NULL,//&processor_inclusive_decision_gateway, /*				 8 */
				NULL,//&processor_inclusive_join_gateway, /*					 9 */
				NULL,//&processor_exclusive_decision_gateway, /*				10 */
				&processor_exclusive_merge_gateway, /*					11 */
				&processor_event_based_exclusive_decision_gateway /*	12 */
		};

/**---------------------------------------------------------------------------*/
/** Inter-protothread communication events */
/** \brief Generated when a workflow gets registered: */
static process_event_t workflow_ready_event;

/** \brief Generated when a workflow instance terminates: */
static process_event_t instance_terminated_event;

/** \brief Generated when a token is put into the ready queue: */
static process_event_t token_ready_event;

/** \brief Generated when a token is freed: */
static process_event_t token_released_event;

/** \brief Generated when a token is freed: */
static process_event_t token_blocked_sfs_event;

/**---------------------------------------------------------------------------*/
/** Workflow management data structures: */

/** \brief		List of workflows which are waiting to be spawned */
LIST( wf_spawn_queue);

/** \brief		List of workflows that are currently running */
LIST( wf_running_queue);

/** \brief		Static memory block for storing pointers to workflow specifications */
MEMB(workflow_ptr_memb, struct workflow_node, MAX_REGISTERED_WORKFLOWS)
;

/*---------------------------------------------------------------------------*/
/* Workflow instance management structures */

/** \brief		List of workflow instances */
LIST( wf_instance_list);

/** \brief		Static memory block for storing pointers to workflow instances */
MEMB(instance_memb, struct workflow_instance, MAX_INSTANTIATED_WORKFLOWS)
;

/*---------------------------------------------------------------------------*/
/* Token management structures */

/** \brief		List of tokens waiting to be executed */
LIST( ready_token_queue);

/** \brief		List of tokens that are waiting for some operation to finish */
LIST( blocked_token_queue);

/** \brief		Static memory block for storing all tokens */
MEMB(token_memb, struct workflow_token, MAX_ACTIVE_TOKENS)
;

/*---------------------------------------------------------------------------*/
/**
 * \brief		Searches for and returns the smallest free id that can be used
 * 				for a workflow instance.
 *
 * 				Tries to obtain an instance id that is not in use. If one is
 * 				found, its value is returned. Otherwise the value 0 is returned
 *
 * \return		a free instance id, or 0 if no one is found
 */
static uint8_t find_free_instance_id() {

	uint8_t instance_id;
	bool occupied;
	for (instance_id = 1; instance_id < 0xFF; instance_id++) {
		struct workflow_instance *wfi;
		occupied = FALSE;
		for (wfi = list_head(wf_instance_list); (!occupied) && (wfi != NULL); wfi
				= wfi->next)
			if (wfi->instance_id == instance_id) {
				occupied = TRUE;
				break;
			}
		if (!occupied) {
			return instance_id;
		}
	}

	return 0;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Given a workflow, looks up the workflow node (container node) in
 * 				either the list of
 *
 * 				TODO
 */
static struct workflow_node *lookup_workflow_id(struct workflow *wf) {
	struct workflow_node *node;
	for (node = list_head(wf_spawn_queue); node != NULL; node = node->next)
		if (node->wf->workflow_id == wf->workflow_id)
			return node;
	for (node = list_head(wf_running_queue); node != NULL; node = node->next)
		if (node->wf->workflow_id == wf->workflow_id)
			return node;
	return NULL;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
static struct workflow_token *alloc_token(struct workflow_instance *wfi) {
	struct workflow_token *token = memb_alloc(&token_memb);

	if (token != NULL) {
		token->wf_instance = wfi;
	}

	return token;
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_engine_init() {

	PRINTF(3,"(UKUFLOW-ENGINE) Initializing ukuflow's workflow engine\n");

	/** Initialize instance management lists */
	list_init(wf_spawn_queue);
	list_init(wf_running_queue);
	memb_init(&workflow_ptr_memb);

	/** Initialized token management lists */
	list_init(ready_token_queue);
	list_init(blocked_token_queue);
	memb_init(&instance_memb);
	memb_init(&token_memb);

	/** Setup inter-protothread communication events */
	workflow_ready_event = process_alloc_event();
	instance_terminated_event = process_alloc_event();
	token_ready_event = process_alloc_event();
	token_released_event = process_alloc_event();
	token_blocked_sfs_event = process_alloc_event();

	/** Initialize the schedulers */
	process_start(&ukuflow_long_term_scheduler_process, NULL);
	process_start(&ukuflow_short_term_scheduler_process, NULL);
	process_start(&ukuflow_termination_listener_process, NULL);
	process_start(&ukuflow_scoped_function_statement_process, NULL);

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_engine_register(struct workflow *wf) {
	/** check if there is already a workflow with the specified id */
	if (!lookup_workflow_id(wf)) {
		struct workflow_node *wfn = (struct workflow_node*) memb_alloc(
				&workflow_ptr_memb);
		if (wfn != NULL) {
			wfn->wf = wf;
			list_add(wf_spawn_queue, wfn);
			process_post(&ukuflow_long_term_scheduler_process,
					workflow_ready_event, wfn);
		} else PRINTF(3,"(UKUFLOW-ENGINE) There is no space left for registering a new workflow!");
	} else PRINTF(3,"(UKUFLOW-ENGINE) A workflow with the specified id %u already exists!\n",
			wf->workflow_id);
}

/*---------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_start_event(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	struct wf_start_event *wfs = (struct wf_start_event*) wfe;
	PRINTF(3,"(UKUFLOW-ENGINE) Start event, current wf_elem_id is %u, next is %u\n",

			token->current_wf_elem_id, wfs->next_id);
	token->prev_wf_elem_id = token->current_wf_elem_id;
	token->current_wf_elem_id = wfs->next_id;

	/** Return token to ready queue */
	list_add(ready_token_queue, token);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_end_event(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	PRINTF(3,"(UKUFLOW-ENGINE) End event, current wf_elem_id is %u\n",

			token->current_wf_elem_id);

	/** Remove token from the workflow_instance's struct: */
	struct workflow_instance *wfi = token->wf_instance;
	wfi->num_tokens--;

	/** Release memory for token: */
	memb_free(&token_memb, token);

	/** Announce that a token was released: */
	process_post(&ukuflow_long_term_scheduler_process, token_released_event,
			NULL);

	/** Check whether the associated workflow_instance has further tokens.
	 * If it does not, remove the instance and put the workflow_node into the wf_spawn_queue */
	if (wfi->num_tokens == 0) {

		/** This was the last token belonging to this workflow instance! */
		struct workflow_node *wfn = wfi->wfn;

		/** Move workflow node (which includes workflow specification) from running to spawn */
		list_remove(wf_running_queue, wfn);
		list_add(wf_spawn_queue, wfn);

		/** Remove data repository: */
		data_mgr_remove(wfi->repository_id);

		/** Remove workflow_instance */
		/** 1: Release memory */
		memb_free(&instance_memb, wfi);

		/** 2: Announce that a workflow_instance was released: */
		process_post(&ukuflow_long_term_scheduler_process,
				instance_terminated_event, NULL);

		/** 3: Announce that this workflow is ready for re-running: */
		process_post(&ukuflow_long_term_scheduler_process,
				workflow_ready_event, NULL);

	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_execute_task(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	struct wf_ex_task *ex_task = (struct wf_ex_task*) wfe;
	PRINTF(3,"(UKUFLOW-ENGINE) Execute task, current wf_elem_id is %u, next will be %u\n",
			 token->current_wf_elem_id, ex_task->next_id);

	/** set up token state union for task execution: */
	union ex_task_token_state *token_state = NULL;

	if ((token_state = token->token_state) == NULL) {
		/** the token had no token state associated to it, so try to allocate one: */
		token->token_state = token_state = malloc(
				sizeof(union ex_task_token_state));

		/** did allocation work? */
		if (token_state == NULL) {
			/** System error: there was no memory for the token!*/
			list_add(ready_token_queue, token);
			return;
		} else
			token_state->comp_ex_task_token_state.statement_nr = 0;
	}

	/** have we ran all the statements? */
	if (token_state->comp_ex_task_token_state.statement_nr
			== ex_task->num_statements) {
		/** Yes, we already ran all the statements of this execute task,
		 * so advance to next task: */
		token->prev_wf_elem_id = token->current_wf_elem_id;
		token->current_wf_elem_id = ex_task->next_id;

		/** Release the memory associated to the token_state: */
		free(token->token_state);
		token->token_state = NULL;

		/** Return token to ready queue */
		list_add(ready_token_queue, token);

	} else {
		/** No, we still need to run more statements: */

		struct statement *st = workflow_get_statement(ex_task,
				token_state->comp_ex_task_token_state.statement_nr);

		PRINTF(3,"(UKUFLOW-ENGINE) statement nr %u, type \"%s\". \n",
				 token_state->comp_ex_task_token_state.statement_nr, get_wf_statement_name(st->statement_type));

		/*---------------------------------------------------------------------------*/
		switch (st->statement_type) {
		case (COMPUTATION_STATEMENT): {
			struct computation_statement *cst =
					(struct computation_statement*) st;
			uint8_t *data_expression = workflow_get_data_expression(cst);
			expression_eval_set_repository(token->wf_instance->repository_id);

			long int result = expression_eval_evaluate(data_expression,
					cst->data_expression_length);
			PRINTF(3,"(UKUFLOW-ENGINE) Result for var_%u is %ld \n",
					 cst->var_id, result);

			if (cst->var_id != 0) {
				data_mgr_set_data(token->wf_instance->repository_id,
						cst->var_id, MANUAL_UPDATE_ENTRY, sizeof(long int),
						(uint8_t*) &result);
			}

			/** Advance to next statement, or indirectly workflow_element: */
			token_state->comp_ex_task_token_state.statement_nr++;

			/** Return token to ready queue */
			list_add(ready_token_queue, token);
			break;
		}
		case (LOCAL_FUNCTION_STATEMENT): {
			struct local_function_statement *lfs =
					(struct local_function_statement*) workflow_get_statement(
							ex_task,
							token_state->lfs_ex_task_token_state.statement_nr);

			int
					return_val =
							ukuflow_cmd_runner_run(
									workflow_get_function_statement(st),
									lfs->cmd_len,
									&token_state->lfs_ex_task_token_state.child_command_process);

			//				if (err_msg == SHELL_NOTHING) {
			//					// there was a problem with the execution of the statement (command can't be found / command already running)
			//					continue;
			//				}

			if ((return_val == SHELL_FOREGROUND || //
					return_val == SHELL_BACKGROUND) //
					&& process_is_running(
							token_state->lfs_ex_task_token_state.child_command_process)) {

				/** Advance to next statement or workflow_element: */
				token_state->lfs_ex_task_token_state.statement_nr++;

				/** Move token to blocked queue: */
				list_add(blocked_token_queue, token);
			}
			break;
		}
		case (SCOPED_FUNCTION_STATEMENT): {

			/** Move token to blocked queue */
			list_add(blocked_token_queue, token);

			PRINTF(3,"(UKUFLOW-ENGINE) Starting to execute an SFS, thus blocked token, (ready: %u/blocked: %u)\n",
					 list_length(ready_token_queue), list_length(blocked_token_queue));

			/** Post event indicating that a scoped function statement needs to be ran */
			process_post(&ukuflow_scoped_function_statement_process,
					token_blocked_sfs_event, token);

			break;
		} /** (case) */

		} /** (switch) */
	} /** (if there are statements to run) */
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_publish_task(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	/** \todo publish event according to specs */
	struct wf_publish_task *wf2 = (struct wf_publish_task*) wfe;
			PRINTF(4,"Publish task, current wf_elem_id is %u, next is %u\n",
			token->current_wf_elem_id, wf2->next_id);
	token->prev_wf_elem_id = token->current_wf_elem_id;
	token->current_wf_elem_id = wf2->next_id;

	/** Return token to ready queue */
	list_add(ready_token_queue, token);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_subscribe_task(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	/** \todo subscribe to event according to specs and do a wait until the event is received*/
	struct wf_subscribe_task *wf2 = (struct wf_subscribe_task*) wfe;
	//		printf("Subscribe task, current wf_elem_id is %u, next is %u\n",
	//				token->current_wf_elem_id, wf2->next_id);
	token->prev_wf_elem_id = token->current_wf_elem_id;
	token->current_wf_elem_id = wf2->next_id;

	/** Return token to ready queue */
	list_add(ready_token_queue, token);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_subworkflow_task(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	/** TODO */
	struct wf_subwf_task *wf2 = (struct wf_subwf_task*) wfe;
	//		printf("Subworkflow, current wf_elem_id is %u, next is %u\n",
	//				token->current_wf_elem_id, wf2->next_id);
	token->prev_wf_elem_id = token->current_wf_elem_id;
	token->current_wf_elem_id = wf2->next_id;

	/** Return token to ready queue */
	list_add(ready_token_queue, token);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_fork_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	/** A fork gateway (aka parallel gateway) is used to create parallel flows. */

	struct wf_fork_gw *fgw = (struct wf_fork_gw*) wfe;

	/** Do we have enough memory left to allocate the required tokens?: */
	if (MAX_ACTIVE_TOKENS - (list_length(blocked_token_queue) + list_length(
			ready_token_queue)) < fgw->out_flows) {
		/** Return token to ready queue */
		list_add(ready_token_queue, token);
		return;
	}

	/** Move token to blocked queue */
	list_add(blocked_token_queue, token);

	/** Allocate state for token-specific data */
	struct gw_token_state *gw_token_state = malloc(
			sizeof(struct gw_token_state));
	token->token_state = gw_token_state;

	/** Set number of remaining tokens */
	gw_token_state->num_children_tokens = fgw->out_flows;

	/** Adjust number of tokens associated to this instance */
	token->wf_instance->num_tokens += fgw->out_flows;

	/** Create the tokens */
	int i;
	for (i = 0; i < fgw->out_flows; i++) {
		struct workflow_token *child_token = alloc_token(token->wf_instance);

		child_token->current_wf_elem_id = *((uint8_t*) fgw
				+ sizeof(struct wf_fork_gw) + i);
		child_token->prev_wf_elem_id = token->current_wf_elem_id;
		//				printf("elem assigned: %u\n", child_token->current_wf_elem_id);
		child_token->token_state = NULL;

		/** Add token to queue of ready tokens */
		list_add(ready_token_queue, child_token);

		/** Link child token to parent token */
		child_token->parent_token = token;
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_join_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {

	/** A join gateway (aka parallel gateway) is used to synchronize (combine)
	 * parallel flows. */

	/** Cast pointer to wf_element */
	struct wf_join_gw *jgw = (struct wf_join_gw*) wfe;

	/** Get pointer to parent token */
	struct workflow_token *parent_token = token->parent_token;

	/** Get pointer to token state*/
	struct gw_token_state *parent_token_state = parent_token->token_state;

	/** Decrease number of tokens that the associated workflow instance has */
	token->wf_instance->num_tokens--;

	/** Release memory from token */
	memb_free(&token_memb, token);

	/** Decrease number of remaining children tokens and
	 * check if that was the last child token */
	if ((--(parent_token_state->num_children_tokens)) == 0) {

		/** Release memoryÊof token state */
		free(parent_token_state);
		parent_token->token_state = NULL;

		/** Move parent token from blocked to ready */
		list_remove(blocked_token_queue, parent_token);
		list_add(ready_token_queue, parent_token);

		/** Advance token to next wf_elem */
		parent_token->prev_wf_elem_id = jgw->id;
		parent_token->current_wf_elem_id = jgw->next_id;

		/** Announce short-term-scheduler that a token became ready */
		process_post(&ukuflow_short_term_scheduler_process, token_ready_event,
				parent_token);
	}
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				Used to create alternative but parallel paths. All condition expressions
 * 				are evaluated. All outgoing flows that evaluate to true will obtain a token.
 * 				All combinations of the paths MAY be taken, from zero to all.
 *				However, it should be designed so that at least one path is taken.
 *				If and only if none of the conditions evaluates to true, the token is passed
 *				on the default outgoing flow.
 *				In case all conditions evaluate to false and a default flow has not been
 *				specified, the inclusive decision gateway throws an exception.
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_inclusive_decision_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {

	struct wf_i_dec_gw *idgw = (struct wf_i_dec_gw*) wfe;

	/** Do we have enough memory left to allocate the potentially required tokens?
	 * (remember that potentially each of the outgoing flows will receive a token):*/
	if (MAX_ACTIVE_TOKENS - (list_length(blocked_token_queue) + list_length(
			ready_token_queue)) < idgw->out_flows) {
		/** Return token to ready queue */
		list_add(ready_token_queue, token);
		return;
	}

	/** Allocate state for token-specific data */
	struct gw_token_state *gw_token_state = malloc(
			sizeof(struct gw_token_state));

	/** Check whether there was memory free for the token state: */
	if (gw_token_state == NULL) {
		/** Return token to ready queue */
		list_add(ready_token_queue, token);
		return;
	}

	token->token_state = gw_token_state;

	/** Move token to blocked queue: */
	list_add(blocked_token_queue, token);

	/** Set number of remaining tokens */
	gw_token_state->num_children_tokens = 0;

	/** Now evaluate out flows and eventually create the tokens */

	/** index for iteration*/
	uint8_t i;

	/** pointer for iteration*/
	struct conditional_flow *flow =
			(struct conditional_flow*) (((uint8_t*) idgw)
					+ sizeof(struct wf_i_dec_gw));
	for (i = 0; i < idgw->out_flows; i++) {

		/** get a pointer to the data expression: */
		uint8_t *expression = (uint8_t*) flow + sizeof(struct conditional_flow);

		/** Evaluate expression */

		/** Two options: either at least one of the out flows
		 * evaluates to true, or none evaluates to true and
		 * hence the default path is taken, assuming there is one. */
		expression_eval_set_repository(token->wf_instance->repository_id);

		if (((i == idgw->out_flows - 1) && //
				(flow->expression_length == 0) && //
				(gw_token_state->num_children_tokens == 0)) || //
				(flow->expression_length > 0 && //
						expression_eval_evaluate(expression,
								flow->expression_length))) {

			struct workflow_token *child_token =
					alloc_token(token->wf_instance);

			child_token->current_wf_elem_id = flow->next_id;
			child_token->prev_wf_elem_id = token->current_wf_elem_id;

			child_token->token_state = NULL;

			child_token->parent_token = token;

			/** Add child token to queue of ready tokens */
			list_add(ready_token_queue, child_token);

			/** Adjust number of tokens associated to the parent token */
			gw_token_state->num_children_tokens++;

			/** Adjust number of tokens associated to workflow instance */
			token->wf_instance->num_tokens++;

		} /** if instantiate */

		flow = (struct conditional_flow*) (((uint8_t*) flow)
				+ sizeof(struct conditional_flow) + flow->expression_length);
	} /** for */

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_inclusive_join_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {
	/*
	 * An inclusive join gateway (aka converging inclusive gateway) is used to merge a combination of alternative and parallel paths.
	 * A control flow token arriving at an inclusive join gateway MAY be synchronized with some other tokens that arrive later at this Gateway.
	 * The precise synchronization behavior of the Inclusive Gateway can be found on page 292.
	 * */
	processor_join_gateway(token, wfe);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Processes an exclusive decision gateway.
 *
 * 				Exclusive decision gateways are used to create alternative paths.
 * 				This is basically the Òdiversion point in the roadÓ for a process.
 * 				For a given instance of the process, only one of the paths can be
 * 				taken.
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_exclusive_decision_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {

	struct wf_x_dec_gw *edgw = (struct wf_x_dec_gw*) wfe;

	/** do we have enough space left to allocate the required token?: */
	if (MAX_ACTIVE_TOKENS - (list_length(blocked_token_queue) + list_length(
			ready_token_queue)) == 0) {
		/** There was no memory for the child token, so repost this task until there is memory free */
		/** Return token to ready queue */
		list_add(ready_token_queue, token);
		return;
	}

	/** Move token to blocked queue */
	list_add(blocked_token_queue, token);

	/** allocate state for token-specific data */
	struct gw_token_state *gw_token_state = malloc(
			sizeof(struct gw_token_state));
	token->token_state = gw_token_state;

	/** initialize number of remaining tokens to 0*/
	gw_token_state->num_children_tokens = 0;

	/** evaluate out flows and eventually create a child token */
	int i;
	struct conditional_flow *flow =
			(struct conditional_flow*) (((uint8_t*) edgw)
					+ sizeof(struct wf_x_dec_gw));
	for (i = 0; (i < edgw->out_flows) && (gw_token_state->num_children_tokens
			== 0); i++) {

		uint8_t *expression = (uint8_t*) flow + sizeof(struct conditional_flow);

		/** Evaluate expression */

		/** Two options: either exactly one of the out flows
		 * evaluates to true, or none evaluates to true and
		 * hence the default path is taken, assuming there is one. */

		expression_eval_set_repository(token->wf_instance->repository_id);
		if (((i == edgw->out_flows - 1) && //
				(flow->expression_length == 0))
				|| (flow->expression_length > 0 && expression_eval_evaluate(
						expression, flow->expression_length))) {

			struct workflow_token *child_token =
					alloc_token(token->wf_instance);

			child_token->current_wf_elem_id = flow->next_id;
			child_token->prev_wf_elem_id = token->current_wf_elem_id;

			child_token->token_state = NULL;

			child_token->parent_token = token;

			/** Add child token to queue of ready tokens */
			list_add(ready_token_queue, child_token);

			/** Increment number of tokens associated to the parent token */
			gw_token_state->num_children_tokens++;

			/** Adjust number of tokens associated to workflow instance */
			token->wf_instance->num_tokens++;

		} // if instantiate

		flow = (struct conditional_flow*) (((uint8_t*) flow)
				+ sizeof(struct conditional_flow) + flow->expression_length);
	} // for
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 *
 *	@param[in]	token		the current workflow token containing metadata about the process
 *	@param[in]	wfe			the element of the workflow which will be executed
 */
static void processor_exclusive_merge_gateway(struct workflow_token *token,
		struct wf_generic_elem *wfe) {

	processor_join_gateway(token, wfe);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Exactly one of the outgoing branches is activated afterwards
 * 				(branching behavior), depending on which of the events is detected first.
 *
 *
 * @param[in] token the current workflow token containing metadata about the process
 * @param[in] wfe the element of the workflow which will be executed
 */
static void processor_event_based_exclusive_decision_gateway(
		struct workflow_token *token, struct wf_generic_elem *wfe) {

	struct wf_eb_x_dec_gw *ebg = (struct wf_eb_x_dec_gw*) wfe;

	/** Allocate state for token-specific data */
	struct eb_gw_token_state *gw_token_state = malloc(
			sizeof(struct eb_gw_token_state));
	token->token_state = gw_token_state;

	/** Set number of child tokens, initially to zero. In the case of an ebg, at most this will grow to 1 */
	gw_token_state->num_children_tokens = 0;

	/** Set the number of registered subscriptions */
	gw_token_state->registered_subscriptions = 0;

	/** Indicate that unsubscriptions haven't been requested yet */
	gw_token_state->unsub_requested = FALSE;

	/** Begin by getting pointer to first event operator flow */
	struct event_operator_flow *ev_op_flow =
			(struct event_operator_flow *) ((uint8_t*) ebg
					+ sizeof(struct wf_eb_x_dec_gw));

	/** Now each of the main event operators of the outgoing flows need to be subscribed.
	 * Iterate through them and invoke ukuflow_event_mgr_subscribe() */
	uint8_t current_flow_nr = 0;

	bool all_ok = TRUE;
	while ((current_flow_nr++ < ebg->num_out_flows) && (all_ok)) {

		all_ok = ukuflow_event_mgr_subscribe(
				(struct generic_event_operator *) //
				(((uint8_t*) ev_op_flow) + sizeof(struct event_operator_flow)),
				ev_op_flow->total_event_operator_length, //
				notified_by_event_mgr, //
				token->wf_instance->wfn->wf);

		if (all_ok) {
			/** Increase counter of registered subscriptions for the token */
			gw_token_state->registered_subscriptions++;

			/** Adjust pointer to next event operator flow */
			ev_op_flow = (struct event_operator_flow *) ((uint8_t*) ev_op_flow
					+ sizeof(struct event_operator_flow)
					+ ev_op_flow->total_event_operator_length);
		}
	}

	/** Adjust token by moving it to blocked queue if all subscriptions were requested: */
	if (all_ok)
		/** Move token to blocked queue */
		list_add(blocked_token_queue, token);
	else
		/** Return token to ready queue so that system retries later */
		list_add(ready_token_queue, token);

	PRINTF(3,"(UKUFLOW-ENGINE) Finished requesting subscription, (ready: %u/blocked: %u), all ok: %u\n",
			 list_length(ready_token_queue), list_length(blocked_token_queue), all_ok);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Callback function for event notification
 *
 * 				This function is invoked by ukuflow-event-mgr whenever an event
 * 				is generated for which ukuflow-engine had subscribed earlier.
 *
 * 				The goal of the function is to look up which blocked tokens are
 *              awaiting for such event, and unblock them.
 * 				Once a token is found, the engine requests an unsubscription from
 *              the associated event operators.
 *
 * @param[in]	event 	the event received which matched with the main event operator expression
 * @param[in]	event_payload_len
 * 						the length, in bytes, of the event's payload
 */
void notified_by_event_mgr(struct event *event, data_len_t event_payload_len) {

	/** Search for tokens in the blocked queue such that they are associated
	 * to the type EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, and which have an
	 * outgoing event operator flow for which the event's channel_id
	 * matches the main event operator */
	struct workflow_token *token = list_head(blocked_token_queue);

	while (token != NULL) {
		struct wf_generic_elem *wfe = workflow_get_wf_elem(
				token->wf_instance->wfn->wf, token->current_wf_elem_id);
		if ((wfe != NULL) && //
				(wfe->elem_type == EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY)) {

			// ----------
			/** Get pointer to token state: */
			struct eb_gw_token_state *gw_token_state =
					(struct eb_gw_token_state *) token->token_state;
			// ----------
			if (gw_token_state->unsub_requested == FALSE) {
				/** We have found a token in the blocked queue that is waiting for an event.
				 * Now check if this token needs to be awaken.
				 * We do this by comparing the channel id of the event against each of the channel ids of the main event operators of the outgoing flows */
				struct wf_eb_x_dec_gw *ebg = (struct wf_eb_x_dec_gw*) wfe;

				uint8_t ev_op_flow_nr = 0;

				struct event_operator_flow *ev_op_flow =
						(struct event_operator_flow*) (((uint8_t*) ebg)
								+ sizeof(struct wf_eb_x_dec_gw));

				while (ev_op_flow_nr++ < ebg->num_out_flows) {
					struct generic_event_operator
							*geo =
									(struct generic_event_operator*) (((uint8_t*) ev_op_flow)
											+ sizeof(struct event_operator_flow));

					if (geo->channel_id == event->channel_id) {
						/** Indicate that unsubscriptions have been requested */
						gw_token_state ->unsub_requested = TRUE;

						/** Weehaa! found the right token! */

						/** ---------- */
						/** Unsubscribe from all outgoing event operator flows of the wf_eb_x_dec_gw: */

						/** Index for iteration */
						uint8_t inner_ev_op_flow_nr = 0;

						/** Pointer for iteration */
						struct event_operator_flow *inner_ev_op_flow =
								(struct event_operator_flow*) ((uint8_t*) ebg
										+ sizeof(struct wf_eb_x_dec_gw));

						while (inner_ev_op_flow_nr++ < ebg->num_out_flows) {
							struct generic_event_operator
									*geo_iter =
											(struct generic_event_operator *) (((uint8_t*) inner_ev_op_flow)
													+ sizeof(struct event_operator_flow));

							ukuflow_event_mgr_unsubscribe(geo_iter, token);

							inner_ev_op_flow
									= (struct event_operator_flow*) (((uint8_t*) inner_ev_op_flow)
											+ sizeof(struct event_operator_flow)
											+ inner_ev_op_flow->total_event_operator_length);
						}
						/** ---------- */

						/** ---------- */
						/** Allocate a new, child token that will follow the associated outgoing flow */
						struct workflow_token *child_token = //
								alloc_token(token->wf_instance);

						/** Set the data of the new child token */
						child_token->current_wf_elem_id = ev_op_flow->next_id;
						child_token->prev_wf_elem_id
								= token->current_wf_elem_id;

						child_token->token_state = NULL;

						child_token->parent_token = token;

						gw_token_state->child_token = child_token;
						/** ---------- */

						/** Add child token to queue of blocked tokens. Once the event manager is ready unsubscribing, the token will be unblocked */
						list_add(blocked_token_queue, child_token);

						/** Adjust number of tokens associated to the parent token */
						gw_token_state->num_children_tokens = 1;

						/** Adjust number of tokens associated to workflow instance */
						token->wf_instance->num_tokens++;

						/** Interrupt iteration by setting ev_op_flow_nr to maximum value: */
						ev_op_flow_nr = ebg->num_out_flows;

					}

					ev_op_flow
							= (struct event_operator_flow*) (((uint8_t*) ev_op_flow)
									+ sizeof(struct event_operator_flow)
									+ ev_op_flow->total_event_operator_length);

				} // while (ev_op_flow_nr < ebg->num_out_flows)

			}
		} // if (token is of correct type)

		/** Adjust token pointer to next blocked token*/
		token = token->next;
	} // while (token != NULL)
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 *				TODO
 */
void ukuflow_notify_unsubscription_ready(struct workflow_token *token) {

	/** Get pointer to token state: */
	struct eb_gw_token_state *gw_token_state =
			(struct eb_gw_token_state *) token->token_state;

	PRINTF(3,"(UKUFLOW-ENGINE) finished unsub, remaining: %u\n",
			gw_token_state->registered_subscriptions - 1);
	/** Decrease counter of registered subscriptions for the token */
	if ((--(gw_token_state->registered_subscriptions)) == 0) {

		//		printf("size %u %u\n", list_length(blocked_token_queue), list_length(ready_token_queue));
		list_remove(blocked_token_queue, gw_token_state->child_token);
		list_add(ready_token_queue, gw_token_state->child_token);
		//		printf("size %u %u\n", list_length(blocked_token_queue), list_length(ready_token_queue));

		/** Announce that child token is ready for execution: */
		process_post(&ukuflow_short_term_scheduler_process, token_ready_event,
				gw_token_state->child_token);
	}

}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 *				Goal of the long term scheduler is to determine for which of the
 *				registered workflows an instance is created, and depending on the
 *				type of start event, whether the workflow is 'spawned' and moved
 *				to the 'running' workflow queue, or it remains in the 'wf_spawn_queue'
 *
 */
PROCESS_THREAD( ukuflow_long_term_scheduler_process, ev, data) {
	PROCESS_BEGIN();

	static struct workflow_node *wfn = NULL;
	static struct wf_generic_elem *wfe = NULL;
	static struct workflow_instance *wfi = NULL;
	static struct workflow_token *token = NULL;

	while (1) {

		if (list_length(wf_spawn_queue) == 0) {
			/** block until a process posts the 'new workflow' event  (e.g. after registration) */
			PRINTF(1,"(UKUFLOW-ENGINE) lts, yielding till a workflow becomes ready\n");
			PROCESS_YIELD_UNTIL(ev == workflow_ready_event);
			PRINTF(1,"(UKUFLOW-ENGINE) lts, a workflow became ready!\n");
		}

		/** Now iterate through the queue of workflow_nodes to be spawned and
		 * check if an instance can be created for each. */
		for (wfn = list_head(wf_spawn_queue); wfn != NULL; wfn = wfn->next) {

			/** get first wf_elem from the workflow, which should be a start event: */
			wfe = workflow_get_wf_elem(wfn->wf, 0);

			if (wfe->elem_type == START_EVENT) {
				/** An instance must be created for this workflow.
				 * Try to allocate one, or block until a running instance is terminated: */
				wfi = NULL;
				do {
					if ((wfi = memb_alloc(&instance_memb)) == NULL)
						PROCESS_WAIT_EVENT_UNTIL(ev
								== instance_terminated_event);
				} while (wfi == NULL);

				if (wfi != NULL) {
					wfi->wfn = wfn;
					wfi->instance_id = find_free_instance_id();
					wfi->repository_id = data_mgr_create(CLOCK_SECOND
							* 10);
					wfi->num_tokens = 0;

					/** Move workflow_node from spawn to running (this part is specific to a particular start event, others could leave the workflow_node in the spawn_queue) */
					list_remove(wf_spawn_queue, wfn);
					list_add(wf_running_queue, wfn);

					/** Allocate a token and put it into the ready queue: */

					do {
						if ((token = alloc_token(wfi)) == NULL)
							PROCESS_WAIT_EVENT_UNTIL(ev == token_released_event);
					} while (token == NULL);

					wfi->num_tokens = 1;
					token->current_wf_elem_id = wfe->id;
					list_add(ready_token_queue, token);

					/** Announce short-term-scheduler that a token became ready */
					process_post(&ukuflow_short_term_scheduler_process,
							token_ready_event, token);

					PRINTF(1,"(UKUFLOW-ENGINE) lts, done my job, (ready: %u/blocked: %u)\n", list_length(ready_token_queue), list_length(blocked_token_queue));
				}

			} // if it's START EVENT

		} // for each workflow

	} // while (1)
	PROCESS_END();
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
PROCESS_THREAD( ukuflow_short_term_scheduler_process, ev, data) {
	static struct workflow_token *token;//, *current_token;
	PROCESS_BEGIN();
	while (1) {

		while ((token = list_head(ready_token_queue)) == NULL) {
			PRINTF(1,"(UKUFLOW-ENGINE) sts, yielding till a token becomes ready\n");
			PROCESS_WAIT_EVENT_UNTIL(ev == token_ready_event);

		}

		PRINTF(1,"(UKUFLOW-ENGINE) sts, finished yielding, (ready: %u/blocked: %u)\n", list_length(ready_token_queue), list_length(blocked_token_queue));

		/** get the token: */
		struct wf_generic_elem *wfe = workflow_get_wf_elem(
				token->wf_instance->wfn->wf, token->current_wf_elem_id);

		PRINTF(1, "(UKUFLOW-ENGINE) sts, token number is %u, wfe number is %u, type is %u\n", token->current_wf_elem_id, wfe->id, wfe->elem_type);

//		PRINTF(1,"(UKUFLOW-ENGINE) sts, current wf_elem_id is %u, %u\n",
//
//				token->current_wf_elem_id, token->wf_instance->num_tokens);

		/** Remove token from ready queue: */
		list_remove(ready_token_queue, token);

		/** Invoke the actual token processor function: */
		token_processors[wfe->elem_type](token, wfe);

	} /** while (1) */
	PROCESS_END();
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Listens for Contiki processes finishing their execution
 *
 * 				TODO
 */
PROCESS_THREAD( ukuflow_termination_listener_process, ev, data) {

	PROCESS_BEGIN();

	do {
		PRINTF(1,"(UKUFLOW-ENGINE) termination process, yielding\n");

		/** This process needs to catch two types of events:
		 * *: A task is ready for execution (task_ready_event)
		 * *: A task finished execution (when a started process ends)
		 * */

		PROCESS_YIELD_UNTIL(//(ev == PROCESS_EVENT_EXIT) || //
				(ev == PROCESS_EVENT_EXITED));

		//		if (ev == PROCESS_EVENT_EXIT) {
		//			// a process ended, so it needs to be exited
		//			//			process_exit(child_command);
		//			printf("notified about a process_event_exit, data is %p\n", data);
		//		} else

		struct workflow_token *unblock_token = NULL;

		if (ev == PROCESS_EVENT_EXITED) {
			//			printf(
			//					"notified about a process_event_exited, data is %p, number of tokens blocked: %u\n",
			//					data, list_length(blocked_token_queue));

			/** now search for a process in the blocked queue such that it is of type wf_ex_task and has the associated ex_task_token_state */
			struct workflow_token *token = list_head(blocked_token_queue);

			while ((token != NULL) && (unblock_token == NULL)) {
				//				printf("comparing blocked token %p, state is %p, type is %u, child %p, data %p\n", token, token->token_state, workflow_get_wf_elem(token->wf_instance->wfn->wf,
				//						token->current_wf_elem_id)->elem_type, ((struct ex_task_token_state*) token->token_state)->child_command_process, data);
				if ((token->token_state != NULL) && //
						(workflow_get_wf_elem(token->wf_instance->wfn->wf,
								token->current_wf_elem_id)->elem_type
								== EXECUTE_TASK) && //
						(((struct lfs_ex_task_token_state*) token->token_state)->child_command_process
								== data)) {
					unblock_token = token;
					//					printf("WOW\n");
				} else
					token = token->next;
			}
		}

		if (unblock_token != NULL) {
			// found token!
			PRINTF(1,"(UKUFLOW-ENGINE) termination process, blocked token finished its task, putting back to ready token queue\n");

			list_remove(blocked_token_queue, unblock_token);
			list_add(ready_token_queue, unblock_token);

			/** Announce short-term-scheduler that a token became ready */
			process_post(&ukuflow_short_term_scheduler_process,
					token_ready_event, unblock_token);
		} else PRINTF(3,"(UKUFLOW-ENGINE) termination process, no blocked token found for the recently finished process\n");

	} while (1);

	PROCESS_END();
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
PROCESS_THREAD( ukuflow_scoped_function_statement_process, ev, data) {

	static struct workflow_token *token = NULL;

	static union ex_task_token_state *token_state = NULL;

	static struct wf_generic_elem *wfe = NULL;

	static struct wf_ex_task *wf_ex = NULL;

	static struct scoped_function_statement *sfs = NULL;

	static struct scope_info *s_i = NULL;

	static struct etimer control_timer;

	PROCESS_BEGIN();

	do {
		PRINTF(1,"(UKUFLOW-ENGINE) sfs, yielding till an sfs needs to be ran\n");

		/** This process catches the event token_blocked_sfs_event, which is
		 * posted whenever a scoped function statement needs to be ran. */

		PROCESS_YIELD_UNTIL(ev == token_blocked_sfs_event);

		token = (struct workflow_token*) data;
		token_state = (union ex_task_token_state *) token->token_state;

		wfe = workflow_get_wf_elem(token->wf_instance->wfn->wf,
				token->current_wf_elem_id);

		wf_ex = (struct wf_ex_task*) wfe;

		sfs = (struct scoped_function_statement *) workflow_get_statement(
				wf_ex, token_state->sfs_ex_task_token_state.statement_nr);

		s_i = workflow_get_scope_info(token->wf_instance->wfn->wf,
				sfs->scope_id);

		/** Test if scope information is available: */
		if (s_i == NULL) {
			PRINTF(3,"(UKUFLOW-ENGINE) sfs, scope spec not found, halting!\n");
			return PT_ENDED;
		}

		/** Now request a scope to be opened. In case opening the scope fails, it won't be possible sending through it */
		if (!ukuflow_net_mgr_open_scope(sfs->scope_id, ((uint8_t*) s_i)
				+ sizeof(struct scope_info), s_i->scope_spec_len,
				s_i->scope_ttl)) {

			PRINTF(3,"(UKUFLOW-ENGINE) sfs, problem opening scope!\n");
			return PT_ENDED;
		}

		/** Now wait a bunch of seconds for the message to be disseminated */
		etimer_set(&control_timer, SCOPE_OPERATION_DELAY * CLOCK_SECOND);
		PRINTF(3,"(UKUFLOW-ENGINE) sfs, waiting 5 seconds...\n");
		PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));

		/** Prepare message with command */
		struct sfs_msg *s_msg = malloc(sizeof(struct sfs_msg) + sfs->cmd_len);
		s_msg->msg_type = SCOPED_FUNCTION_MSG;
		s_msg->cmd_len = sfs->cmd_len;
		memcpy(((uint8_t*) s_msg) + sizeof(struct sfs_msg), ((uint8_t*) sfs)
				+ sizeof(struct scoped_function_statement), sfs->cmd_len);

		/** Send command to scope members */
		ukuflow_net_mgr_send_scope(sfs->scope_id, FALSE, s_msg,
				sizeof(struct sfs_msg) + sfs->cmd_len);

		/** Wait a while (is this necessary?) */
		etimer_set(&control_timer, CLOCK_SECOND * 20);
		PRINTF(3,"(UKUFLOW-ENGINE) sfs, waiting 20 seconds...\n");
		PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));

		/** Now close the scope. If other workflow tasks/elements are using the same scope, it will not be closed but its usage counter decreased */
		ukuflow_net_mgr_close_scope(sfs->scope_id);

		token_state->sfs_ex_task_token_state.statement_nr++;

		PRINTF(3,"(UKUFLOW-ENGINE) sfs, incremented statement_nr to %u\n",
				token_state->sfs_ex_task_token_state.statement_nr);

		/** move token to ready queue */
		list_remove(blocked_token_queue, token);
		list_add(ready_token_queue, token);

		process_post(&ukuflow_short_term_scheduler_process, token_ready_event,
				token);

	} while (1);

	PROCESS_END();
}

/** @} */
