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

// for strlen and memcpy
#include "string.h"

/** \brief		to have access to the serial line */
#include "dev/serial-line.h"

/** \brief		TODO */
enum wf_operations {
	WF_REGISTER, /*								 0 */
	WF_DEREGISTER /*							 1 */
};

/** \brief		Defines the maximum length of the decoded data, in bytes				*/
#define MAX_DECODED_DATA 255

/** \brief		declaration of serial IO management process */
PROCESS(ukuflow_serial_process, "ukuFlow Serial Process");

AUTOSTART_PROCESSES(&ukuflow_serial_process);

PROCESS_THREAD( ukuflow_serial_process, ev, data) {
	PROCESS_BEGIN()
		;

		ukuflow_mgr_init();

		static uint8_t *s_input = NULL;
		static uint8_t decoded_data[MAX_DECODED_DATA];
		static uint8_t cmd_nr = 0;
		static uint16_t data_len = 0;

		/** Structure of serial commands:
		 * [ x, y, ...], where:
		 *   x is a command:
		 *     0 == WF_REGISTER, for registration of a workflow
		 *     1 == WF_DEREGISTER, for the deregistration
		 *
		 *   If the command is a registration, it must be followed by a byte with the length of the workflow specification
		 *   If the command is a deregistration, it must be followed by a byte with the id of the workflow to be deregistered
		 */

		while (1) {

			PRINTF(2, "(UF-SERIAL) Input [%d]-:\n", cmd_nr++);

			PROCESS_WAIT_EVENT_UNTIL(
					ev == serial_line_event_message && data != NULL);

			data_len = strlen(data);

			if (data_len < 4) {
				PRINTF(2, "(UF-SERIAL) Input received too short!\n");
				continue;
			}

			PRINTF(2, "(UF-SERIAL) Input received (%d chars): ", strlen(data));
			{
				PRINT_ARR(1, data, data_len);
			}


			data_len_t decoded_len = base64_decode((char*) data, decoded_data,
					MAX_DECODED_DATA);

			s_input = decoded_data;
			if (s_input == NULL) {
				PRINTF(2, "(UF-SERIAL) --------- No more memory!\n");
				continue;
			}

			PRINTF(2, "(UF-SERIAL) s_input (len: %d): ", decoded_len);
			{
				PRINT_ARR(1, s_input, decoded_len);
			}

			if (s_input[0] == WF_REGISTER) {
				PRINTF(2,
						"(UF-SERIAL) Registering wf (id: %d, length %d)...\n", s_input[2], s_input[1]);
				if (ukuflow_mgr_register(s_input + 2, s_input[1])) {
					PRINTF(2, "(UF-SERIAL) Registration successful!\n");
				} else
				PRINTF(2, "(UF-SERIAL) Registration failed!\n");
			} else if (s_input[0] == WF_DEREGISTER) {
				PRINTF(2,
						"(UF-SERIAL) Deregistering wf (id: %d)...\n", s_input[1]);
				if (ukuflow_mgr_deregister(s_input[1])) {
					PRINTF(2, "(UF-SERIAL) Deregistration successful!\n");
				} else
				PRINTF(2, "(UF-SERIAL) Deregistration failed!\n");
			} else
				PRINTF(2, "(UF-SERIAL) Input invalid!\n");

		}

	PROCESS_END();
}

/** @} */
