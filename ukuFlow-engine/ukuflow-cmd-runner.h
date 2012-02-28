/*
 * ukuflow-cmd-runner.h
 *
 *  Created on: Jul 13, 2011
 *      Author: guerrero
 */

#ifndef __UKUFLOW_CMD_RUNNER_H__
#define __UKUFLOW_CMD_RUNNER_H__

#include "workflow-types.h"

#include "sys/process.h"

void ukuflow_cmd_runner_init();
int ukuflow_cmd_runner_run(char *command_line, data_len_t command_line_len,
		struct process **started_process);

#endif /* __UKUFLOW_CMD_RUNNER_H__ */
