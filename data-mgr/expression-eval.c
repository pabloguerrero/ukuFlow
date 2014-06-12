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
 * \file	expression-eval.c
 * \brief	Expression evaluator module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 */

#include "expression-eval.h"
#include "logger.h"

/*---------------------------------------------------------------------------*/
/* Internal variables for processing an expression */
static int16_t pos;
static int16_t length;
static uint8_t *bytecode;
static uint8_t invalid_input;
static data_repository_id_t repo_id;

static custom_input_function_t custom_input_function;
static void *custom_input;
/*---------------------------------------------------------------------------*/
/* Function prototypes */
static long int eval_operator_and(void);
static long int eval_operator_or(void);
static long int eval_operator_not(void);
static long int eval_predicate_eq(void);
static long int eval_predicate_neq(void);
static long int eval_predicate_lt(void);
static long int eval_predicate_gt(void);
static long int eval_predicate_let(void);
static long int eval_predicate_get(void);
static long int eval_operator_add(void);
static long int eval_operator_sub(void);
static long int eval_operator_div(void);
static long int eval_operator_mult(void);
static long int eval_operator_mod(void);
static long int eval_uint8_value(void);
static long int eval_uint16_value(void);
static long int eval_int8_value(void);
static long int eval_int16_value(void);
static long int eval_string_value(void);
static long int eval_repository_value(void);
static long int eval_custom_input_value(void);

/*---------------------------------------------------------------------------*/
/* array of evaluator functions */
static eval_func evaluator[] = {
//
		eval_operator_and, //
		eval_operator_or, //
		eval_operator_not, //
		eval_predicate_eq, //
		eval_predicate_neq, //
		eval_predicate_lt, //
		eval_predicate_gt, //
		eval_predicate_let, //
		eval_predicate_get, //
		eval_operator_add, //
		eval_operator_sub, //
		eval_operator_div, //
		eval_operator_mult, //
		eval_operator_mod, //
		eval_uint8_value, //
		eval_uint16_value, //
		eval_int8_value, //
		eval_int16_value, //
		eval_string_value, //
		eval_repository_value, //
		eval_custom_input_value //
		};

/*---------------------------------------------------------------------------*/
/** \brief Read single byte from bytecode */
static int read_byte(void) {
	/* check if there is input left */
	if (pos < length) {
		uint8_t retval = bytecode[pos];
		pos++;
		return (retval);
	}

	/* no more input */
	invalid_input = 1;
	return (NO_MORE_INPUT);
}

/*---------------------------------------------------------------------------*/
/** \brief Evaluate operand of unary operator or predicate */
static void eval_unary(long int *operand) {
	int code;

	/* evaluate operand */
	code = read_byte();

	if (IS_VALID(code)) {
		*operand = evaluator[code]();
	} else {
		invalid_input = 1;
	}
}

/*---------------------------------------------------------------------------*/
/* evaluate operands of binary operator or predicate */
static void eval_binary(long int *operand1, long int *operand2) {
	int code;

	/* evaluate first operand */
	code = read_byte();

	if (IS_VALID(code)) {
		*operand1 = evaluator[code]();
		PRINTF(5, "binary, op1 %li\n", *operand1);

	} else {
		invalid_input = 1;
	}

	/* evaluate second operand */
	code = read_byte();

	if (IS_VALID(code)) {
		*operand2 = evaluator[code]();
		PRINTF(5, "binary, op2 %li\n", *operand2);
	} else {
		invalid_input = 1;
	}
}

/*---------------------------------------------------------------------------*/
/* Processing perators */
static long int eval_operator_and(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 && op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_or(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 || op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_add(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 + op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_sub(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 - op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_div(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 / op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_mult(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 * op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_mod(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 % op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_operator_not(void) {

	/* evaluate operand */
	long int op = 0;
	eval_unary(&op);

	/* calculate result */
	long int retval = !op;

	return (retval);
}

/*---------------------------------------------------------------------------*/
/* Predicate functions  */
static long int eval_predicate_eq(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	/* calculate result */
	long int retval = (op1 == op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_predicate_neq(void) {

	long int retval = !eval_predicate_eq();

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_predicate_lt(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	PRINTF(5, "comparing %lu with %lu\n", op1, op2);

	/* calculate result */
	long int retval = (op1 < op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_predicate_gt(void) {

	/* evaluate operands */
	long int op1, op2;
	eval_binary(&op1, &op2);

	PRINTF(5, "op1:%li, op2: %li\n", op1, op2);
	/* calculate result */
	long int retval = (op1 > op2);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_predicate_let(void) {

	long int retval = !eval_predicate_gt();

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_predicate_get(void) {

	long int retval = !eval_predicate_lt();

	return (retval);
}

/*---------------------------------------------------------------------------*/
/* values */
static long int eval_uint8_value(void) {
	uint8_t retval = read_byte();

	PRINTF(5, "uint8 converted value is %li\n", (long int)retval);
	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_uint16_value(void) {
	uint16_t v1 = read_byte(), v2 = read_byte(), retval = (v2 << 8 | v1);

	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_int8_value(void) {
	int8_t value = read_byte();

	long int retval = value;
	return (retval);
}

/*---------------------------------------------------------------------------*/
static long int eval_int16_value(void) {
	int16_t v1 = read_byte(), v2 = read_byte(), retval = v2<<8 | v1;

	return (retval);
}

/*---------------------------------------------------------------------------*/
/**
 * \brief		String operations are not supported yet, we only return the length
 */
static long int eval_string_value(void) {
	uint8_t length = read_byte();

	// now 'consume' all the bytes of the string
	uint8_t i;
	for (i = 0; i < length; i++)
		read_byte();

	return (length);
}

/*---------------------------------------------------------------------------*/
static long int eval_repository_value(void) {
	int index;

	if ((index = read_byte()) != NO_MORE_INPUT) {

		data_len_t data_len;

		/* read value from repository */
		uint8_t *data = data_mgr_get_data(repo_id, (entry_id_t) index,
				&data_len);

		if (data != NULL) {
			long int retval;
			memcpy(&retval, data, data_len);

			PRINTF(5, "(EXPR-EVAL) repo value: %ld, %d %d %d %d\n", retval, data[0], data[1], data[2], data[3]);

			return (retval);
		}
		PRINTF(5, "(EXPR-EVAL) Repository entry not found!\n");
	}
	return (0);
}

/*---------------------------------------------------------------------------*/
static long int eval_custom_input_value(void) {
	int index;

	long int retval = 0;
	if ((index = read_byte()) != NO_MORE_INPUT) {

		data_len_t data_len;

		/* read value from repository */
		if (custom_input_function == NULL)
			return (0);

		uint8_t *data = (*custom_input_function)(&data_len, /*requested_field:*/
		index, custom_input);

		if (data != NULL) {
			switch (data_len) {
			case (sizeof(uint8_t)): {
				retval = *((uint8_t*) data);
				break;
			}
			case (sizeof(uint16_t)): {
				retval = *((uint16_t*) data);
				break;
			}
			case (sizeof(clock_time_t)): {
				retval = *((clock_time_t*) data);
				break;
			}
			} // switch
			PRINTF(5, "output value: %lu\n", retval);

		} // if
	}
	PRINTF(5, "custom input can't be obtained because expression is malformed!\n");
	return (retval);
}

/**
 * \brief		Sets the repository from which data will be extracted
 */
void expression_eval_set_repository(data_repository_id_t id) {
	repo_id = id;
}

/**
 * \brief		Sets a custom input function
 */
void expression_eval_set_custom_input(custom_input_function_t function,
		void *input) {
	custom_input = input;
	custom_input_function = function;
}

/**
 * \brief		TODO
 */
long int expression_eval_evaluate(uint8_t *expression_spec, data_len_t spec_len) {
	/* initialize position, length, and invalid flag */
	pos = 0;
	length = spec_len;
	invalid_input = 0;
	/** set default result to 0 */
	long int result = 0;

	/* set byte code */
	bytecode = expression_spec;

	/* get first code */
	int code = read_byte();

	if (IS_VALID(code)) {
		/* call evaluator */
		long int value = evaluator[code]();

		printf("res eval %d %d %d %d\n", ((uint8_t*)&value)[0], ((uint8_t*)&value)[1], ((uint8_t*)&value)[2], ((uint8_t*)&value)[3]);

		/* check if invalid input flag is set */
		if (!invalid_input) {
			result = value;
		}
	}

	/* set back inputs */
	repo_id = 0;
	custom_input = NULL;
	custom_input_function = NULL;
	/* return */
	return (result);
}

/** @} */
