/**
 * \addtogroup datamanager
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
 * \file	expression-eval.h
 * \brief	Header file for the expression evaluator module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 */

#ifndef __EXPRESSION_EVAL_H__
#define __EXPRESSION_EVAL_H__

#include "data-mgr.h"

/** \brief Constant used for signaling that there was no more input found */
#define NO_MORE_INPUT -1

/** \brief Macro function for checking whether a code is value or not */
#define IS_VALID(code)                          \
  ((code) >= 0 && (code) < sizeof(evaluator)/sizeof(eval_func))

/** \brief Type definition for pointer to functions of the parser */
typedef long int (*eval_func)(void);

/** \brief Operation codes */
enum opcodes {
	OPERATOR_AND, /*		 0*/
	OPERATOR_OR, /*			 1*/
	OPERATOR_NOT, /*		 2*/
	PREDICATE_EQ, /*		 3*/
	PREDICATE_NEQ, /*		 4*/
	PREDICATE_LT, /*		 5*/
	PREDICATE_GT, /*		 6*/
	PREDICATE_LET, /*		 7*/
	PREDICATE_GET, /*		 8*/
	OPERATOR_ADD, /*		 9*/
	OPERATOR_SUB, /*		10*/
	OPERATOR_DIV, /*		11*/
	OPERATOR_MULT, /*		12*/
	OPERATOR_MOD, /*		13*/
	UINT8_VALUE, /*			14*/
	UINT16_VALUE, /*		15*/
	INT8_VALUE, /*			16*/
	INT16_VALUE, /*			17*/
	STRING_VALUE, /*		18*/
	REPOSITORY_VALUE, /*	19*/
	CUSTOM_INPUT_VALUE /*	20*/
};

/** \brief Type definition for pointer to functions of custom input */
typedef uint8_t* (*custom_input_function_t)(data_len_t *data_len,
		uint8_t requested_field, void *custom_input);

void expression_eval_set_repository(data_repository_id_t id);
void expression_eval_set_custom_input(
		custom_input_function_t custom_input_function, void *input);
long int expression_eval_evaluate(uint8_t *expression_spec,
		data_len_t spec_len);

#endif // __EXPRESSION_EVAL_H__
/** @} */
