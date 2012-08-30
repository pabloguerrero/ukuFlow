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
 * \file	ukuflow-serial.c
 * \brief	Serial IO manager in ukuFlow
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 6, 2012
 */

#include "ukuflow-mgr.h"

/** \brief		to decode input strings from base64 into binary */
#include "base64.h"

#include "logger.h"
#include "stdlib.h"

/** \brief		to have access to the serial line */
#include "dev/serial-line.h"

/** \brief		TODO */
enum wf_operations {
	WF_REGISTER, /*								 0 */
	WF_DEREGISTER /*							 1 */
};

/** \brief		Defines the maximum lenght of the decoded data, in bytes				*/
#define MAX_DECODED_DATA 255

/** \brief		declaration of serial IO mgmt. process */
PROCESS(ukuflow_serial_process, "ukuFlow Serial Process");

AUTOSTART_PROCESSES(&ukuflow_serial_process);

PROCESS_THREAD( ukuflow_serial_process, ev, data) {
	PROCESS_BEGIN()
		;

		ukuflow_mgr_init();

		static uint8_t *s_input = NULL;
		static uint8_t decoded_data[MAX_DECODED_DATA];
//		static uint8_t *last = NULL;
		static uint8_t cmd_nr = 0;
		static uint16_t data_len = 0;
//		static uint8_t last_len = 0;

		static uint8_t depl = 0;

		while (1) {

			PRINTF(1, "(SERIAL) Input %i-%d:\n", cmd_nr++, depl);

			PROCESS_WAIT_EVENT_UNTIL(
					ev == serial_line_event_message && data != NULL);

			data_len = strlen(data);

			PRINTF(1, "(SERIAL) Input has length %d\n", strlen(data));

			PRINTF(1, "data: ");
			{
				PRINT_ARR(1, data, data_len);
			}

			data_len_t decoded_len = base64_decode((char*)data, decoded_data, MAX_DECODED_DATA);

			s_input = malloc(decoded_len);
			memcpy(s_input, decoded_data, decoded_len);

			PRINTF(1, "s_input: ");
			{
				PRINT_ARR(1, s_input, decoded_len);
			}


			//			PRINTF(1, "last: ");
//			{
//				PRINT_ARR(1, last, last_len);
//			}

			if (s_input[0] == WF_REGISTER) {
				depl = 1;
				struct workflow *wf = malloc(s_input[1]);

				if (wf) {

					PRINTF(1, "(SERIAL) Registering wf with len %d...\n", s_input[1]);
					// copy contents of workflow definition into memory:
					memcpy(wf, s_input + 2, s_input[1]);

					ukuflow_mgr_register(wf);
				}
			} else if (s_input[0] == WF_DEREGISTER) {
				ukuflow_mgr_deregister(s_input[1]);
			} else if (s_input[0] == 'a') {
				printf("aaaaaaaa\n");

#define WORKFLOW_SPEC 2, /* workflow id */ \
	5,  /* number of wf_elems*/ \
	2,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 2, /* second task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 1,  \
	\
	2, EXECUTE_TASK, 3, 3,  /* third task, execute with 2 local function statements:*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, STRING_VALUE, 2, 49, 49, /*  'local blink 1' */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, NODE_ID + 2, /*  'local blink $var2'  (equals 'local blink 4')*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, STRING_VALUE, 2, 49, 49, /*  'local blink 1' */ \
	\
	3, EXECUTE_TASK, 4, 1,  /* fourth task, execute w. scoped function statement 'blink $var2', scope id 111*/ \
		SCOPED_FUNCTION_STATEMENT, 222, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, NODE_ID + 2, \
	\
	4, END_EVENT, \
	\
	111, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 111, ttl 60, length 5, spec */ \
	222, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60, length 5, spec */

//				ukuflow_mgr_deregister
			} else
				printf("(SERIAL) Input invalid!\n");

		}

	PROCESS_END();
}

/** @} */
