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
 * \file	ukuflow-mgr.c
 * \brief	Workflow Manager in ukuFlow
 *
 *			TODO
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 14, 2011
 */

#include "net/rime/rimeaddr.h"

#include "ukuflow-mgr.h"
#include "ukuflow-net-mgr.h"
#include "ukuflow-cmd-runner.h"
#include "ukuflow-event-mgr.h"
#include "ukuflow-engine.h"

#include "logger.h"

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
void ukuflow_mgr_init() {

	PRINTF(3,
			"[%u.%u:%10lu] %s::%s() : Initializing ukuflow's manager\n", rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__);

	/* Initialize other components */
	/* * ukuflow engine: */
	ukuflow_engine_init();
	/* * command runner */
	ukuflow_cmd_runner_init();
	/* * network manager: */
	ukuflow_net_mgr_init();
	/* * event manager: */
	ukuflow_event_mgr_init();

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		Registers a workflow in this node.
 *
 * 				Registers a workflow in this node. The node makes a copy of the workflow specification in dynamic memory.
 *
 */
bool ukuflow_mgr_register(uint8_t *workflow_def, data_len_t workflow_def_len) {

	return (ukuflow_engine_register(workflow_def, workflow_def_len));

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		TODO
 *
 * 				TODO
 */
bool ukuflow_mgr_deregister(uint8_t workflow_id) {
	return ukuflow_engine_deregister(workflow_id);
}
/*---------------------------------------------------------------------------*/

/** @} */
