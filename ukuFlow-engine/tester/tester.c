#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "contiki.h"

#include "dev/leds.h"
#include "sys/etimer.h"
#include "node-id.h"

#include "net/rime.h"

#include "data-mgr.h"
#include "expression-eval.h"
//#include "scopes-logger.h"

#include "logger.h"

#include "workflow.h"
#include "ukuflow-mgr.h"
//#include "workflow-types.h"

#define ROOT_NODE_ID 1

/* Workflow specifications */

/* This workflow tests an event-based exclusive decision gateway (and its respective exclusive join gateway)
 * Depending on the events expressions of the outgoing flows of the e-b exclusive decision gateway,
 * tokens flow into them, or into the default outflow, if the evaluation of the other two returns false. */
#define WORKFLOW_SPEC_9 9, /* 1 = workflow id */ \
	7,  /* 7 = number of wf_elems (0..6)*/ \
	2,  /* 2 = number of scopes*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 3, /* 1 = 2nd wf_elem, event based decision gateway with 3 out flows */ \
	2 /* event operator goes to wf_elem id 2 */, 16 /*total outflow len*/, \
	   SIMPLE_FILTER /*type*/, 2/*channel_id*/, 1/*num_expressions*/, 5/*expression_len*/, PREDICATE_LT, UINT16_VALUE, 237, 24, REPOSITORY_VALUE, SENSOR_TEMPERATURE_RAW, \
	   PERIODIC_E_GEN/*type*/, 1/*channel id*/, SENSOR_TEMPERATURE_RAW/*sensor*/, 111/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
	3 /* event operator goes to wf_elem id 3 */, 6 /*total outflow len*/, \
	   PERIODIC_E_GEN/*type*/, 3/*channel id*/, NODE_ID/*sensor*/, 222/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
	4 /* event operator goes to wf_elem id 4 */, 6 /*total outflow len*/, \
	   PERIODIC_E_GEN/*type*/, 4/*channel id*/, SENSOR_HUMIDITY_RAW/*sensor*/, 222/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
	 \
	\
	2, EXECUTE_TASK, 4, 1, /* 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 50, \
	\
	3, EXECUTE_TASK, 4, 1, /* 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 52, \
	\
	4, EXECUTE_TASK, 4, 1, /* 5th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 54, \
	\
	5, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 6th wf_elem, exclusive merge with 2 incoming flows */ \
	2, 3, 4, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	6, END_EVENT, /* 7h wf_elem, end event */ \
	111, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 111, ttl 60 secs, length 5 bytes, spec */ \
	222, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60 secs, length 5 bytes, spec */

/* This workflow tests an event-based exclusive decision gateway (and its respective exclusive join gateway)
 * Depending on the events' expressions of the outgoing flows of the e-b exclusive decision gateway,
 * tokens flow into them. The event operators are simple, periodical event generators,  */
#define WORKFLOW_SPEC_8 8, /* 1 = workflow id */ \
	7,  /* 7 = number of wf_elems (0..6)*/ \
	2,  /* 2 = number of scopes*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 3 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 15 /*total outflow len*/, \
		   SIMPLE_FILTER /*type*/, 2/*channel_id*/, 1/*num_expressions*/, 5/*expression_len*/, PREDICATE_LT, UINT16_VALUE, 237, 24, REPOSITORY_VALUE, SENSOR_TEMPERATURE_RAW, \
		   PERIODIC_E_GEN/*type*/, 1/*channel id*/, SENSOR_TEMPERATURE_RAW/*sensor*/,	11/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
		3 /* event operator goes to wf_elem id 3 */, 6 /*total outflow len*/, \
		   PERIODIC_E_GEN/*type*/, 3/*channel id*/, NODE_ID/*sensor*/, 					22/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 50, \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 52, \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 3, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6h wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 11, ttl 60 secs, length 5 bytes, spec */ \
	22, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 22, ttl 60 secs, length 5 bytes, spec */

/* This workflow tests an event-based exclusive decision gateway (and its respective exclusive join gateway)
 * Depending on the event operators' expressions of the outgoing flows of the e-b exclusive decision gateway,
 * tokens flow into them. The event operators are simple, periodical event generators */
#define WORKFLOW_SPEC 7, /* 1 = workflow id */ \
	7,  /* 7 = number of wf_elems (0..6)*/ \
	3,  /* 3 = number of scopes*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 3, /* 1 = 2nd wf_elem, event based decision gateway with 3 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 6 /*total outflow len*/, \
		   PERIODIC_E_GEN/*type*/, 1/*channel id*/, NODE_ID/*sensor*/,					11/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
		3 /* event operator goes to wf_elem id 2 */, 6 /*total outflow len*/, \
		   PERIODIC_E_GEN/*type*/, 2/*channel id*/, SENSOR_TEMPERATURE_RAW/*sensor*/,	22/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
		4 /* event operator goes to wf_elem id 3 */, 6 /*total outflow len*/, \
		   PERIODIC_E_GEN/*type*/, 3/*channel id*/, SENSOR_LIGHT_PAR_RAW/*sensor*/,		33/*scope_id*/, 60, 0/*period (60 seconds) [4 byte]*/, \
		\
	2, EXECUTE_TASK, 5, 1, /* 2 = 3rd wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 50, /* blink 2*/ \
	\
	3, EXECUTE_TASK, 5, 1, /* 3 = 4th wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 52, /* blink 4*/ \
	\
	4, EXECUTE_TASK, 5, 1, /* 4 = 5th wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 54, /* blink 6*/ \
	\
	5, EXCLUSIVE_MERGE_GATEWAY, 6, 3, /* 5 = 6th wf_elem, goes to wf_elem id 5, exclusive merge with 3 incoming flows */ \
	2, 3, 4, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	6, END_EVENT, /* 6 = 7th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 2, /* scope id 111, ttl 60 secs, length 5 bytes, spec */ \
	22, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 222, ttl 60 secs, length 5 bytes, spec */ \
	33, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 4  /* scope id 333, ttl 60 secs, length 5 bytes, spec */

/* This workflow tests an exclusive decision gateway (and its respective exclusive join gateway)
 * Depending on the conditions (the expressions) of the outgoing flows of the exclusive decision gateway,
 * tokens flow into them, or into the default outflow, if the evaluation of the other two returns false. */
#define WORKFLOW_SPEC_6 6, /* workflow id */ \
	8,  /* 8 = number of wf_elems (0..7) */ \
	0,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 3, /* second task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 2, UINT8_VALUE, 30, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 2, UINT8_VALUE, 40, \
	COMPUTATION_STATEMENT, NODE_ID + 3, 2, UINT8_VALUE, 50, \
	\
	2, EXCLUSIVE_DECISION_GATEWAY, 3, /* third task, inclusive decision gateway (OR-split) w. 3 outgoing flows */ \
		3, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID + 1, UINT8_VALUE, 35, \
		4, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID + 2, UINT8_VALUE, 45, \
		5, 0, \
	\
	3, EXECUTE_TASK, 6, 2, /* fourth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 4,  \
	\
	4, EXECUTE_TASK, 6, 3, /* fifth task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, REPOSITORY_VALUE, NODE_ID + 4, UINT8_VALUE, 1,  \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, REPOSITORY_VALUE, NODE_ID + 5, REPOSITORY_VALUE, NODE_ID + 5,  \
	COMPUTATION_STATEMENT, NODE_ID + 6, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 5,  \
	\
	5, EXECUTE_TASK, 6, 1, /* sixth task, execute w. 2 computation statements */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 53, \
	\
	6, EXCLUSIVE_MERGE_GATEWAY, 7, 3, /* seventh task, inclusive join w. 3 potential incoming flows */ \
	3, 4, 5, /* ids of the wf_elems from which this join potentially waits tokens */ \
	\
	7, END_EVENT /* eighth task, end event */

/* This workflow tests an inclusive decision gateway (and its respective inclusive join gateway)
 * Depending on the conditions (the expressions) of the outgoing flows of the inclusive decision gateway,
 * tokens flow into them, or into the default outflow, if the evaluation of the other two returns false. */
#define WORKFLOW_SPEC_5 5, /* workflow id */ \
	8,  /* 8 = number of wf_elems (0..7) */ \
	1,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 3, /* second task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 2, UINT8_VALUE, 30, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 2, UINT8_VALUE, 40, \
	COMPUTATION_STATEMENT, NODE_ID + 3, 2, UINT8_VALUE, 50, \
	\
	2, INCLUSIVE_DECISION_GATEWAY, 3, /* third task, inclusive decision gateway (OR-split) w. 3 outflows (2 of these are normal outflows, 1 is a default outflow)*/ \
		3, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID + 1, UINT8_VALUE, 35, \
		4, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID + 2, UINT8_VALUE, 45, \
		5, 0, \
	\
	3, EXECUTE_TASK, 6, 2, /* fourth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 4,  \
	\
	4, EXECUTE_TASK, 6, 2, /* fifth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 4,  \
	COMPUTATION_STATEMENT, NODE_ID + 5, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 5,  \
	\
	5, EXECUTE_TASK, 6, 2, /* sixth task, execute w. 2 computation statements */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 53, \
	SCOPED_FUNCTION_STATEMENT, 222, 7, 98, 108, 105, 110, 107, 32, 53, \
	\
	6, INCLUSIVE_JOIN_GATEWAY, 7, 3, /* seventh task, inclusive join w. 3 potential incoming flows */ \
	3, 4, 5, /* ids of the wf_elems from which this join potentially waits tokens */ \
	\
	7, END_EVENT, /* eighth task, end event */ \
	\
	222, 60, 0, 5, PREDICATE_LT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60, length 5, spec */

/* This workflow tests nested fork gateways (and respective join gateways) */
#define WORKFLOW_SPEC_4 4, /* workflow id */ \
	9,  /* 9 = number of wf_elems (0..8) */ \
	0,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, FORK_GATEWAY, 2, /* second task, fork (AND-split) w. 2 outgoing flows */ \
	2, 3, /* ids of the wf_elems to which this fork goes */ \
	\
	2, EXECUTE_TASK, 7, 2, /* third task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 1,  \
	\
	3, FORK_GATEWAY, 2, /* fourth task, fork (AND-split) w. 2 outgoing flows */ \
	4, 5, /* ids of the wf_elems to which this fork goes */ \
	\
	4, EXECUTE_TASK, 6, 1,  /* fifth task, execute w. local function statement */ \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 2,  \
	\
	5, EXECUTE_TASK, 6, 1,  /* sixth task, execute w. local function statement */ \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 2,  \
	\
	6, JOIN_GATEWAY, 7, 2, /* seventh task, join gateway w. 2 incoming flows, next wf_elem_id is 7*/\
	4, 5, /* ids of the wf_elems from which this join is expecting tokens */\
	\
	7, JOIN_GATEWAY, 8, 2, /* eighth task, join gateway w. 2 incoming flows, next wf_elem_id is 8*/\
	2, 6, /* ids of the wf_elems from which this join is expecting tokens */\
	\
	8, END_EVENT /* ninth task */

/* This workflow tests the fork gateway and the join gateway. */
/* Also the correct creation and removal of a data repository per workflow instance is stress-tested */
#define WORKFLOW_SPEC_3 3, /* workflow id */ \
	6,  /* 6 = number of wf_elems (0..6) */ \
	0,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, FORK_GATEWAY, 2, /* second task, fork (AND-split) w. 2 outgoing flows */ \
	2, 3, /* ids of the wf_elems to which this fork goes */ \
	\
	2, EXECUTE_TASK, 4, 2, /* third task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 1,  \
	\
	3, EXECUTE_TASK, 4, 1,  /* fourth task, execute w. local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 53, \
	\
	4, JOIN_GATEWAY, 5, 2, /* fifth task, join gateway w. 2 incoming flows, next wf_elem_id is 6*/\
	2, 3, /* ids of the wf_elems from which this join is expecting tokens */\
	5, END_EVENT

/* This workflow tests all the statements types: computation statements, local function statements, and scoped function statements */
#define WORKFLOW_SPEC_2 2, /* workflow id */ \
	5,  /* number of wf_elems*/ \
	2,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 2, /* second task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, NODE_ID + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, NODE_ID + 2, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, NODE_ID + 1,  \
	\
	2, EXECUTE_TASK, 3, 1,  /* third task, execute w. local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 98, 108, 105, 110, 107, 32, 53, \
	\
	3, EXECUTE_TASK, 4, 1,  /* fourth task, execute w. scoped function statement, scope id 111, cmd length 7 */ \
	SCOPED_FUNCTION_STATEMENT, 222, 7, 98, 108, 105, 110, 107, 32, 53, \
	\
	4, END_EVENT, \
	\
	111, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 111, ttl 60, length 5, spec */ \
	222, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60, length 5, spec */

/* This dummy workflow tests workflow and token instantiation */
#define WORKFLOW_SPEC_1 1, /* workflow id */ \
	2,  /* number of wf_elems*/ \
	0,  /* number of scopes*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	1, END_EVENT
// ----


// Defines whether the workflow specification is put into dynamic memory (1) or used from the stack (0)
#define DYNAMIC 0

PROCESS(tester_process, "workflow tester")
;
AUTOSTART_PROCESSES(&tester_process);

PROCESS_THREAD( tester_process, ev, data) {
	PROCESS_BEGIN();
	static uint8_t wf_spec[] = { WORKFLOW_SPEC};
	static struct workflow *wf;

	leds_off(LEDS_ALL);

	PRINTF(1,"[%u.%u:%10lu] tester::main() : Starting\n",
			rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time());

	/*	LOG("[%u.%u:%10lu] tester::main() : size of a 'struct wf_elem' is %u, size of a 'struct wf_start_event' is %u\n",
	 rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), sizeof(struct wf_elem), sizeof(struct wf_start_event));

	 LOG("[%u.%u:%10lu] tester::main() : size of 'wf_spec' array is %u\n",
	 rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), sizeof(wf_spec)); */

	uint8_t *al = NULL;

	ukuflow_mgr_init();

	if (node_id == ROOT_NODE_ID) {

		if (DYNAMIC) {

			//			printf("allocating...\n");
			al = malloc(sizeof(wf_spec));
			if (al != NULL) {

				printf("allocation ok\n");

				/* copy the specification */
				memcpy(al, wf_spec, sizeof(wf_spec));

			}
			//			else
			//			printf ("Problem while allocating (not enough RAM?)!\n");

		} else {
			al= (uint8_t*)wf_spec;
		}

		if (al != NULL) {
			wf = (struct workflow *)al;

			leds_off(LEDS_ALL);

			PRINTF(3,"[%u.%u:%10lu] tester::main() : workflow id: %u, length: %u bytes\n",
					rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1],clock_time(), wf->workflow_id, sizeof(wf_spec));
			PRINTF(3,"[%u.%u:%10lu] tester::main() : number of workflow elements: %u\n",
					rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), wf->num_wf_elems);
			PRINTF(3,"[%u.%u:%10lu] tester::main() : number of scopes: %u\n",
					rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), wf->num_scopes);
		}


		PRINTF(1,"[%u.%u:%10lu] tester::main() : About to register a workflow\n",
				rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time());
		ukuflow_mgr_register(wf);

		static struct etimer control_timer;
		/* wait a while*/
		etimer_set(&control_timer, CLOCK_SECOND * 250L);
		PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
		PRINTF(1,"[%u.%u:%10lu] tester::main() : After 250 second timer\n",
				rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time());
//		ukuflow_mgr_unregister(wf);

		}
		PROCESS_END();
	}
