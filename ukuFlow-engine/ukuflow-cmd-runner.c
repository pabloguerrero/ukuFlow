/*
 * ukuflow-cmd-runner.c
 *
 *  Created on: Jul 13, 2011
 *      Author: guerrero
 */

#include "ukuflow-cmd-runner.h"

/* Shell commands */
#include "../apps/shell/shell-blink.h"

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_cmd_runner_init() {
	shell_blink_init();

}
/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
int ukuflow_cmd_runner_run(char *command_line, data_len_t command_line_len,
		struct process **started_process) {

	return shell_start_command(command_line, command_line_len, NULL,
			started_process);
}
