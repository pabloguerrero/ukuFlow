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
 * \file	ukuflow-cmd-runner.c
 * \brief	ukuFlow Command Runner
 *
 *			TODO
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 13, 2011
 */

#include "ukuflow-cmd-runner.h"

/* logging */
#include "logger.h"

/* for memcpy */
#include "string.h"

/** for access to STRING_VALUE and REPOSITORY_VALUE */
#include "expression-eval.h"

#define CMD_LEN 255

/** for malloc and free: */
#include "stdlib.h"

/** for sprintf: */
#include "stdio.h"

/* Shell commands */
#include "../apps/shell/shell-blink.h"

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_cmd_runner_init() {
	shell_blink_init();

}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
char *ukuflow_cmd_runner_generate_command(struct statement_command *st_cmd,
		data_len_t *result_len, data_repository_id_t repo_id) {

	uint8_t *st_cmd_arr = (uint8_t*) st_cmd + sizeof(struct statement_command);

	char *result = (char*) malloc(CMD_LEN * sizeof(char));

	PRINTF(2, "(CMD-RUNNER) allocated %u bytes for cmd at %p\n",
			CMD_LEN*sizeof(char), result);

	if (result == NULL)
		return (NULL);

	data_len_t st_cmd_index;
	uint8_t param_nr;

	// copy command name by iterating over it and copy each character into result string and increasing the resulting length
	for (*result_len = st_cmd_index = 0; st_cmd_index < st_cmd->cmd_len;
			*result_len = (++st_cmd_index))
		result[*result_len] = (char) st_cmd_arr[st_cmd_index];

	// now iterate over parameters and copy them (or resolve them)
	for (param_nr = 0; param_nr < st_cmd->num_params; param_nr++) {

		// now add empty space and increase length
		result[(*result_len)++] = ' ';

		// find out whether this is a string constant or a variable
		switch (st_cmd_arr[st_cmd_index++]) {
		case (STRING_VALUE): {
//			printf("string, iterating %d of %d\n", param_nr, st_cmd->num_params);
			// it is a string, so simply copy it into resulting char array
			data_len_t string_len = st_cmd_arr[st_cmd_index++];
			memcpy(result + *result_len, st_cmd_arr + st_cmd_index, string_len);
			*result_len += string_len;
			st_cmd_index += string_len;
//			for (i = 0; i < total; i++)
//				result[(*result_len)++] = st_cmd_arr[st_cmd_index++];
//			printf("Result: '%s', st_cmd_index: %d, result_len: %d\n", result, st_cmd_index, *result_len);
			break;
		}
		case (REPOSITORY_VALUE): {
//			printf("repo, iterating %d of %d\n", param_nr, st_cmd->num_params);
			// read which entry must be selected from repository
			entry_id_t entry_id = st_cmd_arr[st_cmd_index++];
			data_len_t data_len;
			int16_t temp;
			// it is a variable, so
			uint8_t *dr_data = data_mgr_get_data(repo_id, entry_id, &data_len);
			if (dr_data) {
				temp = *((uint16_t*) dr_data);
				// now convert int to string:
//				printf("about to put it at %d\n", *result_len);
				*result_len += sprintf(result + *result_len, "%d", temp);
			} // else do nothing, thus fails
//			printf("Result: '%s', st_cmd_index: %d, result_len: %d\n", result, st_cmd_index, *result_len);
			break;
		} // case
		case (UINT8_VALUE): {
			uint8_t number = st_cmd_arr[st_cmd_index++];
			*result_len += sprintf(result + *result_len, "%u", number);
			break;
		}
		case (UINT16_VALUE): {
			uint16_t number = st_cmd_arr[st_cmd_index++];
			number |= ((uint16_t)st_cmd_arr[st_cmd_index++]) << 8;
			*result_len += sprintf(result + *result_len, "%u", number);
			break;
		} // case
		case (INT8_VALUE): {
			int8_t number = st_cmd_arr[st_cmd_index++];
			*result_len += sprintf(result + *result_len, "%d", number);
			break;
		} // case
		case (INT16_VALUE): {
			int16_t number = st_cmd_arr[st_cmd_index++];
			number |= ((uint16_t)st_cmd_arr[st_cmd_index++]) << 8;
			*result_len += sprintf(result + *result_len, "%d", number);
			break;
		}
		} // switch
	} // for

//	result[(*result_len)++] = '\n';
//	printf("cmd final: '%s'\n", result);

	return (result);
}
/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
int ukuflow_cmd_runner_run(char *command_line, data_len_t command_line_len,
		struct process **started_process) {

	PRINTF(1, "(CMD-RUNNER) about to run statement '%s', len %u\n", command_line,
			command_line_len);

	return (shell_start_command(command_line, command_line_len, NULL,
			started_process));
}
/** @} */
