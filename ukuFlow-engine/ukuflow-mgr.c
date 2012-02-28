/*
 * ukuflow-wf-mgr.c
 *
 *  Created on: Jul 14, 2011
 *      Author: guerrero
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
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_mgr_init() {

	PRINTF(3,"[%u.%u:%10lu] %s::%s() : Initializing ukuflow's manager\n",
			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(), __FILE__, __FUNCTION__);

	// Initialize other components
	// * ukuflow engine:
	ukuflow_engine_init();
	// * command runner
	ukuflow_cmd_runner_init();
	// * network manager:
	ukuflow_net_mgr_init();
	// * event manager:
	ukuflow_event_mgr_init();

}

/*---------------------------------------------------------------------------*/
/**
 * \brief		@todo
 *
 * 				@todo
 */
void ukuflow_mgr_register(struct workflow *wf) {

	ukuflow_engine_register(wf);
}
