/**
 * \defgroup workflow Workflow Data Type
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
 * \file	workflow.h
 * \brief	Header file for the workflow data type
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	May 18, 2011
 */

#ifndef __WORKFLOW_H__
#define __WORKFLOW_H__

#include "workflow-types.h"
#include "expression-eval.h"

struct wf_generic_elem *workflow_get_wf_elem(struct workflow *wf,
		wf_elem_id_t wf_elem_id);
data_len_t workflow_wf_elem_size(struct wf_generic_elem *wfe);
data_len_t workflow_statement_size(struct statement *st);
struct statement *workflow_get_statement(struct wf_ex_task *wf_ex, uint8_t statement_nr);
struct event_operator_flow *workflow_get_event_operator_flow(struct wf_eb_x_dec_gw* ebg, uint8_t outflow_nr);
uint8_t *workflow_get_data_expression(struct computation_statement *cst);
char *workflow_get_function_statement(struct statement *st);

struct scope_info *workflow_get_scope_info(struct workflow  *wf, scope_id_t searched_scope_id);
char *get_wf_elem_type_name(struct wf_generic_elem *wfe);
char *get_wf_statement_name(statement_type_t st);

#endif // __WORKFLOW_H__
/** @} */
