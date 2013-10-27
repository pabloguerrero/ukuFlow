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

#include "logger.h"
#include "workflow.h"
#include "ukuflow-mgr.h"
//#include "workflow-types.h"

#define ROOT_NODE_ID 1
#define WITH_STOP 0

/* Workflow specifications */
/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway). Depending on the events expressions of the outgoing flows
 * 				of the e-b exclusive decision gateway, tokens flow into them, or into the default
 * 				outflow, if the evaluation of the other two returns false.
 **/
#define WORKFLOW_SPEC_11 11, /* 1 = workflow id */ \
	7,  /* 7 = number of wf_elems (0..6)*/ \
	2,  /* 2 = number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	1,  /* looping (no loop, just one execution)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 3, /* 1 = 2nd wf_elem, event based decision gateway with 3 out flows */ \
	2 /* event operator goes to wf_elem id 2 */, 16 /*total outflow len*/, \
	   SIMPLE_FILTER /*type*/, 2/*channel_id*/, 1/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, MAGNITUDE, UINT16_VALUE, 0, 23 /* 5888 */, \
	   PERIODIC_E_GEN/*type*/, 1/*channel id*/, SENSOR_TEMPERATURE_RAW/*sensor*/, 111/*scope_id*/, 60, 0/*period (60 seconds) [2 byte]*/, \
	3 /* event operator goes to wf_elem id 3 */, 6 /*total outflow len*/, \
	   PERIODIC_E_GEN/*type*/, 3/*channel id*/, NODE_ID/*sensor*/, 222/*scope_id*/, 60, 0/*period (60 seconds) [2 byte]*/, \
	4 /* event operator goes to wf_elem id 4 */, 6 /*total outflow len*/, \
	   PERIODIC_E_GEN/*type*/, 4/*channel id*/, SENSOR_HUMIDITY_RAW/*sensor*/, 222/*scope_id*/, 60, 0/*period (60 seconds) [2 byte]*/, \
	 \
	\
	2, EXECUTE_TASK, 4, 1, /* 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 4,/*  'local blink 4;' */ \
	\
	4, EXECUTE_TASK, 4, 1, /* 5th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	5, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 6th wf_elem, exclusive merge with 2 incoming flows */ \
	2, 3, 4, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	6, END_EVENT, /* 7h wf_elem, end event */ \
	111, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 111, ttl 60 secs, length 5 bytes, spec */ \
	222, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60 secs, length 5 bytes, spec */

/**
 * \brief	 	This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is a periodic event generator chained with a simple filter and a composition filter
 * 				- the second event expression is a local, offset event generator (to which a timer event of 700 seconds can map)
 **/
#define WORKFLOW_SPEC_10_2 10, /* 1 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	1,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 24 /*total outflow len*/, \
		   COUNT_COMPOSITION_EF/*type*/,	3/*ev_op_id*/,	30/*channel_id*/, 160, 0 /* window size*/, \
		   SIMPLE_EF/*type*/,				2/*ev_op_id*/,	20/*channel_id*/, 1/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, TIMESTAMP_F, UINT16_VALUE, 25, 0 /* 25 secs */, \
		   PERIODIC_EG/*type*/,				1/*ev_op_id*/,	10/*channel id*/, NODE_ID /* source*/,						11/*scope_id*/, 2/* infinite repetitions*/, 50, 0/*period (50 seconds) [2 byte]*/, \
		3 /* event operator goes to wf_elem id 3 */, 7 /*total outflow len*/, \
		   OFFSET_EG/*type*/,				4/*ev_op_id*/,	40/*channel id*/, NODE_TIME /* source */,	LOCAL_EVENT_GENERATOR /*scope_id*/, 188, 0 /* offset (700 secs) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 10, /* scope id 11, ttl 60 secs, length 5 bytes, spec */

/**
 * \brief	 	This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is a periodic event generator chained with a simple filter and a composition filter
 * 				- the second event expression is a local, offset event generator (to which a timer event of 700 seconds can map)
 **/
#define WORKFLOW_SPEC 10, /* 1 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	1,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 26 /*total outflow len*/, \
		   COUNT_COMPOSITION_EF/*type*/,	3/*ev_op_id*/,	30/*channel_id*/, 60, 0 /* window size*/, \
		   SIMPLE_EF/*type*/,				2/*ev_op_id*/,	20/*channel_id*/, 1/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, ORIGIN_NODE_F, UINT16_VALUE, 3, 0 /* NODE_ID >= 3 */, \
		   PATTERN_EG/*type*/,				1/*ev_op_id*/,	10/*channel id*/, NODE_ID /* source*/,				11/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, 2/*pattern length*/, 0x01 /* pattern: 0x01 = 00000001*/, \
		3 /* event operator goes to wf_elem id 3 */, 7 /*total outflow len*/, \
		   OFFSET_EG/*type*/,				4/*ev_op_id*/,	40/*channel id*/, NODE_TIME /* source */,	LOCAL_EVENT_GENERATOR /*scope_id*/, 200, 0 /* offset (200 secs) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_GET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 2, /* scope id 11, ttl 60 secs, length 5 bytes, spec */

/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is a periodic event generator chained with a simple filter
 * 				- the second event expression is a local, offset event generator (to which a timer event can map)
 * */
#define WORKFLOW_SPEC_9_4 9, /* 9 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	1,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 26 /* total outflow len*/, \
		   SIMPLE_EF /*type*/,	2/*ev_op_id*/, 2/*channel_id*/, 2/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, ORIGIN_NODE_F, UINT16_VALUE, 3, 0/* 3 */, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, TIMESTAMP_F, UINT16_VALUE, 94, 1 /* 350 secs */, \
		   PERIODIC_EG/*type*/,	1/*ev_op_id*/, 1/*channel id*/, NODE_ID /* source*/,						11/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		3 /* event operator goes to wf_elem id 3 */, 7 /*total outflow len*/, \
		   OFFSET_EG/*type*/,	3/*ev_op_id*/, 3/*channel id*/, NODE_TIME /* source */,	LOCAL_EVENT_GENERATOR /*scope_id*/, 188, 2 /* offset (700 secs) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 11, ttl 60 secs, length 5 bytes, spec (NODE_ID <= 3)*/


/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is an immediate event generator chained with a simple filter
 * 				- the second event expression is a local, offset event generator (to which a timer event can map)
 * */
#define WORKFLOW_SPEC_9_3 9, /* 9 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	1,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 16 /* total outflow len*/, \
		   SIMPLE_EF /*type*/,		2/*ev_op_id*/, 20/*channel_id*/, 1/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, ORIGIN_NODE_F, UINT16_VALUE, 3, 0/* 3 */, \
		   IMMEDIATE_EG/*type*/,	1/*ev_op_id*/, 10/*channel id*/, SENSOR_TEMPERATURE_CELSIUS /* source*/,						11/*scope_id*/, \
		3 /* event operator goes to wf_elem id 3 */, 7 /*total outflow len*/, \
		   OFFSET_EG/*type*/,		3/*ev_op_id*/, 30/*channel id*/, NODE_TIME /* source */,	LOCAL_EVENT_GENERATOR /*scope_id*/, 100, 0 /* offset (100 secs) [2 byte]*/, \
	\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 11, ttl 60 secs, length 5 bytes, spec (NODE_ID <= 3)*/


/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is a periodic event generator chained with a simple filter.
 * 				  The first constraint of the filter is that the id of the generator node is 3, while the
 * 				  second constraint is that the timestamp of the event is greater than or equal to 200 seconds.
 * 				- the second event expression is a periodic event generator local running at scope 44
 **/
#define WORKFLOW_SPEC_9_2 9, /* 9 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	2,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 26 /* total outflow len*/, \
		   SIMPLE_EF /*type*/,	2/*ev_op_id*/, 2/*channel_id*/, 2/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, ORIGIN_NODE_F, UINT16_VALUE, 3, 0, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, TIMESTAMP_F, UINT16_VALUE, 200, 0 /* 200 secs*/, \
		   PERIODIC_EG/*type*/,	1/*ev_op_id*/, 1/*channel id*/, NODE_ID /* source*/,				11/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		3 /* event operator goes to wf_elem id 3 */, 8 /*total outflow len*/, \
		   PERIODIC_EG/*type*/,	3/*ev_op_id*/, 3/*channel id*/, SENSOR_TEMPERATURE_RAW/*sensor*/,	44/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 11, ttl 60 secs, length 5 bytes, spec (NODE_ID <= 3)*/ \
	44, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 4,  /* scope id 44, ttl 60 secs, length 5 bytes, spec (NODE_ID == 4)*/ \

/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its respective
 * 				exclusive join gateway).
 *
 * 				A workflow testing event expressions. Depending on the events' expressions of the outgoing
 * 				flows of the e-b exclusive decision gateway, tokens flow into them.
 * 				There are two event expressions:
 * 				- the first event expression is a pattern event generator chained with a simple filter.
 * 				  The first constraint of the filter is that the id of the generator node is 3, while the
 * 				  second constraint is that the timestamp of the event is greater than or equal to 200 seconds.
 * 				- the second event expression is a local, offset event generator (to which a timer event can map)
 **/
#define WORKFLOW_SPEC_9_1 9, /* 9 = workflow id */ \
	6,  /* number of wf_elems (0..5)*/ \
	1,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 2, /* 1 = 2nd wf_elem, event based decision gateway with 2 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 28 /* total outflow len*/, \
		   SIMPLE_EF /*type*/,	2/*ev_op_id*/, 20/*channel_id*/, 2/*num_expressions*/, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, ORIGIN_NODE_F, UINT16_VALUE, 3, 0, 6/*expression_len*/, PREDICATE_GET, CUSTOM_INPUT_VALUE, TIMESTAMP_F, UINT16_VALUE, 200, 0 /* 200 secs*/, \
		   PATTERN_EG/*type*/,	1/*ev_op_id*/, 10/*channel id*/, NODE_ID /* source*/,				11/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, 5/*pattern length*/, 0x05 /* pattern: 0x05 = 00000101*/, \
		3 /* event operator goes to wf_elem id 3 */, 7 /*total outflow len*/, \
		   OFFSET_EG/*type*/,		3/*ev_op_id*/, 30/*channel id*/, NODE_TIME /* source */,	LOCAL_EVENT_GENERATOR /*scope_id*/, 250, 0 /* offset (100 secs) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 4, 1, /* 2 = 3rd wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* 3 = 4th wf_elem, execute task, with 1 local function statement, goes to wf_elem id 4 */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* 4 = 5th wf_elem, goes to wf_elem id 5, exclusive merge with 2 incoming flows */ \
	2, 3, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	5, END_EVENT, /* 5 = 6th wf_elem, end event */ \
	11, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 11, ttl 60 secs, length 5 bytes, spec (NODE_ID <= 3)*/

/**
 * \brief		This workflow tests an event-based exclusive decision gateway (and its
 * 				respective exclusive join gateway).
 *
 * 				An event-based exclusive decision gateway evaluates the expressions of the
 * 				event operators on each outgoing flow. In this test case, the event operators
 * 				are simple, periodical event generators.
 */
#define WORKFLOW_SPEC_8 8, /* 8 = workflow id */ \
	7,  /* number of wf_elems (0..6)*/ \
	3,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* 0 = 1st wf_elem, start event, goes to wf_elem id 1*/ \
	\
	1, EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY, 3, /* 1 = 2nd wf_elem, event based decision gateway with 3 out flows */ \
		2 /* event operator goes to wf_elem id 2 */, 8 /*total outflow len*/, \
		   PERIODIC_EG/*type*/, 1/*ev_op_id*/, 1/*channel id*/, SENSOR_TEMPERATURE_RAW/*source*/,			22/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		3 /* event operator goes to wf_elem id 3 */, 8 /*total outflow len*/, \
		   PERIODIC_EG/*type*/,	2/*ev_op_id*/, 2/*channel id*/, SENSOR_TEMPERATURE_CELSIUS/*source*/,		33/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		4 /* event operator goes to wf_elem id 4 */, 8 /*total outflow len*/, \
		   PERIODIC_EG/*type*/,	3/*ev_op_id*/, 3/*channel id*/, SENSOR_TEMPERATURE_FAHRENHEIT/*source*/,	44/*scope_id*/, 0/* infinite repetitions*/, 60, 0/*period (60 seconds) [2 byte]*/, \
		\
	2, EXECUTE_TASK, 5, 1, /* 2 = 3rd wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 5, 1, /* 3 = 4th wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 4,/*  'local blink 4;' */ \
	\
	4, EXECUTE_TASK, 5, 1, /* 4 = 5th wf_elem, execute task, goes to wf_elem id 5, with 1 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 6,/*  'local blink 6;' */ \
	\
	5, EXCLUSIVE_MERGE_GATEWAY, 6, 3, /* 5 = 6th wf_elem, goes to wf_elem id 5, exclusive merge with 3 incoming flows */ \
	2, 3, 4, /* ids of the wf_elems from which this merge potentially waits tokens */ \
	\
	6, END_EVENT, /* 6 = 7th wf_elem, end event */ \
	22, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 2, /* scope id 111, ttl 60 secs, length 5 bytes, spec (NODE_ID == 2)*/ \
	33, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 222, ttl 60 secs, length 5 bytes, spec (NODE_ID == 3)*/ \
	44, 60, 0, 5, PREDICATE_EQ, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 4  /* scope id 333, ttl 60 secs, length 5 bytes, spec (NODE_ID == 4)*/

/**
 * \brief	This workflow tests an exclusive decision gateway (and its respective exclusive join gateway).
 *
 * An exclusive decision gateway evaluates the conditions (the expressions) of its outgoing flows in
 * order to decide where the token would flow to. If none of the normal outflows evaluate to true, the
 * default outflow is chosen.
 */
#define WORKFLOW_SPEC_7_2 7, /* 7 = workflow id */ \
	6,  /* number of wf_elems (0..5) */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXCLUSIVE_DECISION_GATEWAY, 2, /* third task, inclusive decision gateway (OR-split) w. 2 outgoing flows */ \
		2, 6, PREDICATE_GT, REPOSITORY_VALUE, NODE_TIME, UINT16_VALUE, 0, 100, /* time > 25600 ? */ \
		3, 0, \
	\
	2, EXECUTE_TASK, 4, 1, /* fourth task, execute w. 2 computation statements */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, EXECUTE_TASK, 4, 1, /* fifth task, execute w. 3 computation statements */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 10,/*  'local blink 10;' */ \
	\
	4, EXCLUSIVE_MERGE_GATEWAY, 5, 2, /* seventh task, inclusive join w. 3 potential incoming flows */ \
	2, 3, /* ids of the wf_elems from which this join potentially waits tokens */ \
	\
	5, END_EVENT /* eighth task, end event */

/**
 * \brief	This workflow tests an exclusive decision gateway (and its respective exclusive join gateway).
 *
 * An exclusive decision gateway evaluates the conditions (the expressions) of its outgoing flows in
 * order to decide where the token would flow to. If none of the normal outflows evaluate to true, the
 * default outflow is chosen.
 */
#define WORKFLOW_SPEC_7_1 7, /* 7 = workflow id */ \
	8,  /* 8 = number of wf_elems (0..7) */ \
	0,  /* 0 = number of scopes*/ \
	1,  /* 1 = minimum number of instances required */ \
	1,  /* 1 = maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 3, /* second task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 2, UINT8_VALUE, 30, /* 'var1 = 30;' */ \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 2, UINT8_VALUE, 40, /* 'var2 = 40;' */ \
	COMPUTATION_STATEMENT, USER_FIELD + 2, 2, UINT8_VALUE, 50, /* 'var3 = 50;' */ \
	\
	2, EXCLUSIVE_DECISION_GATEWAY, 3, /* third task, inclusive decision gateway (OR-split) w. 3 outgoing flows */ \
		3, 5, PREDICATE_GT, REPOSITORY_VALUE, USER_FIELD + 0, UINT8_VALUE, 35, /* var1 > 35 ? */ \
		4, 5, PREDICATE_GT, REPOSITORY_VALUE, USER_FIELD + 1, UINT8_VALUE, 45, /* var2 > 45 ? */ \
		5, 0, \
	\
	3, EXECUTE_TASK, 6, 2, /* fourth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 3, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 3,  \
	\
	4, EXECUTE_TASK, 6, 3, /* fifth task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, REPOSITORY_VALUE, USER_FIELD + 3, UINT8_VALUE, 1,  \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, REPOSITORY_VALUE, USER_FIELD + 4, REPOSITORY_VALUE, USER_FIELD + 4,  \
	COMPUTATION_STATEMENT, USER_FIELD + 5, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 4,  \
	\
	5, EXECUTE_TASK, 6, 1, /* sixth task, execute w. 1 computation statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	6, EXCLUSIVE_MERGE_GATEWAY, 7, 3, /* seventh task, inclusive join w. 3 potential incoming flows */ \
	3, 4, 5, /* ids of the wf_elems from which this join potentially waits tokens */ \
	\
	7, END_EVENT /* eighth task, end event */

/* \brief		This workflow tests an inclusive decision gateway (and its respective inclusive
 * 				join gateway). Depending on the conditions (the expressions) of the outgoing flows
 * 				of the inclusive decision gateway, tokens flow into them, or into the default
 * 				outflow, if the evaluation of the other two returns false.
 **/
#define WORKFLOW_SPEC_6 6, /* workflow id */ \
	8,  /* 8 = number of wf_elems (0..7) */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 3, /* second task, execute w. 3 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 2, UINT8_VALUE, 30, \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 2, UINT8_VALUE, 40, \
	COMPUTATION_STATEMENT, USER_FIELD + 2, 2, UINT8_VALUE, 50, \
	\
	2, INCLUSIVE_DECISION_GATEWAY, 3, /* third task, inclusive decision gateway (OR-split) w. 3 outflows (2 of these are normal outflows, 1 is a default outflow)*/ \
		3, 5, PREDICATE_GT, REPOSITORY_VALUE, USER_FIELD + 0, UINT8_VALUE, 35, \
		4, 5, PREDICATE_GT, REPOSITORY_VALUE, USER_FIELD + 1, UINT8_VALUE, 45, \
		5, 0, \
	\
	3, EXECUTE_TASK, 6, 2, /* fourth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 3, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 3,  \
	\
	4, EXECUTE_TASK, 6, 2, /* fifth task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 3,  \
	COMPUTATION_STATEMENT, USER_FIELD + 4, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 4,  \
	\
	5, EXECUTE_TASK, 6, 2, /* sixth task, execute w. 2 computation statements */ \
	LOCAL_FUNCTION_STATEMENT, 0, 7, 0, 98, 108, 105, 110, 107, 32, 53, \
	SCOPED_FUNCTION_STATEMENT, 222, 7, 0, 98, 108, 105, 110, 107, 32, 53, \
	\
	6, INCLUSIVE_JOIN_GATEWAY, 7, 3, /* seventh task, inclusive join w. 3 potential incoming flows */ \
	3, 4, 5, /* ids of the wf_elems from which this join potentially waits tokens */ \
	\
	7, END_EVENT, /* eighth task, end event */ \
	\
	222, 60, 0, 5, PREDICATE_LT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60, length 5, spec */

/**
 * \brief		This workflow tests nested fork gateways (and respective join gateways)
 **/
#define WORKFLOW_SPEC_5 5, /* workflow id */ \
	9,  /* 9 = number of wf_elems (0..8) */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, FORK_GATEWAY, 2, /* second task, fork (AND-split) w. 2 outgoing flows */ \
	2, 3, /* ids of the wf_elems to which this fork goes */ \
	\
	2, EXECUTE_TASK, 7, 2, /* third task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, /* 'var1 = 1 + 2;' */ \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 0,  /* 'var2 = 1 + $var1;' */ \
	\
	3, FORK_GATEWAY, 2, /* fourth task, fork (AND-split) w. 2 outgoing flows */ \
	4, 5, /* ids of the wf_elems to which this fork goes */ \
	\
	4, EXECUTE_TASK, 6, 1,  /* fifth task, execute w. local function statement */ \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 1,  /* 'var1 = 1 + $var2;' */ \
	\
	5, EXECUTE_TASK, 6, 1,  /* sixth task, execute w. local function statement */ \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 1,  /* 'var1 = 1 + $var2;' */ \
	\
	6, JOIN_GATEWAY, 7, 2, /* seventh task, join gateway w. 2 incoming flows, next wf_elem_id is 7*/\
	4, 5, /* ids of the wf_elems from which this join is expecting tokens */\
	\
	7, JOIN_GATEWAY, 8, 2, /* eighth task, join gateway w. 2 incoming flows, next wf_elem_id is 8*/\
	2, 6, /* ids of the wf_elems from which this join is expecting tokens */\
	\
	8, END_EVENT /* ninth workflow element */

/**
 * \brief		This workflow tests the fork gateway and the join gateway.
 * 				Also the correct creation and removal of a data repository
 * 				per workflow instance is stress-tested */
#define WORKFLOW_SPEC_4 4, /* workflow id */ \
	6,  /* 6 = number of wf_elems (0..6) */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, FORK_GATEWAY, 2, /* second task, fork (AND-split) w. 2 outgoing flows */ \
	2, 3, /* ids of the wf_elems to which this fork goes */ \
	\
	2, EXECUTE_TASK, 4, 2, /* third task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, /* 'var1 = 1 + 2;' */ \
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 0,  /* 'var2 = 1 + $var1;' */\
	\
	3, EXECUTE_TASK, 4, 2,  /* fourth task, execute w. 2 local function statement */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, USER_FIELD + 1,/*  'local blink $var2'; // local blink 4; */ \
	\
	4, JOIN_GATEWAY, 5, 2, /* fifth task, join gateway w. 2 incoming flows, next wf_elem_id is 6*/\
	2, 3, /* ids of the wf_elems from which this join is expecting tokens */\
	5, END_EVENT

/**
 * \brief		This workflow tests all the statements types: computation statements,
 * 				local function statements, and scoped function statements
 **/
#define WORKFLOW_SPEC_3 3, /* workflow id */ \
	5,  /* number of wf_elems*/ \
	2,  /* number of scopes*/ \
	1,  /* minimum number of instances required */ \
	1,  /* maximum number of instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 2, /* second task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, /* 'var1 = 1 + 2;' */\
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 0, /* 'var2 = 1 + $var1; ' // 4 */\
	\
	2, EXECUTE_TASK, 3, 3,  /* third task, execute with 2 local function statements:*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, USER_FIELD + 1, /*  'local blink $var2'  (equals 'local blink 4')*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, STRING_VALUE, 1, 51, /*  'local blink 3' */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2' */ \
	\
	3, EXECUTE_TASK, 4, 1,  /* fourth task, execute w. scoped function statement 'blink $var2', scope id 222*/ \
		SCOPED_FUNCTION_STATEMENT, 222, 5, 1, 98, 108, 105, 110, 107, UINT16_VALUE, 2, 0, \
	\
	4, END_EVENT, \
	\
	111, 60, 0, 5, PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3, /* scope id 111, ttl 60, length 5, spec (NODE_ID <= 3)*/ \
	222, 60, 0, 5, PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 3 /* scope id 222, ttl 60, length 5, spec (NODE_ID > 3)*/

/**
 * \brief		This workflow tests parallel instances through two statements types:
 * 				computation statements and local function statements.
 **/
#define WORKFLOW_SPEC_2_5 2, /* workflow id */ \
	4,  /* number of wf_elems*/ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of parallel instances required */ \
	2,  /* maximum number of parallel instances required */ \
	3,  /* looping (loop 3 times)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 2, /* second task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, /* 'var1 = 1 + 2;' */\
	COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 0, /* 'var2 = 1 + $var1;' */ \
	\
	2, EXECUTE_TASK, 3, 3,  /* third task, execute with 2 local function statements:*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, USER_FIELD + 1, /*  'local blink $var2'  (equals 'local blink 4')*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, STRING_VALUE, 1, 51, /*  'local blink 3' */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2' */ \
	\
	3, END_EVENT

/**
 * \brief		This workflow tests parallel instances through two statements types:
 * 				computation statements and local function statements.
 **/
#define WORKFLOW_SPEC_2_4 2, /* workflow id */ \
	3,  /* number of wf_elems 0..2 */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of parallel instances required */ \
	1,  /* maximum number of parallel instances required */ \
	3,  /* looping (loop 3 times)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 1, /* second task, execute w. 2 computation statements */ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 1,/*  'local blink 1' */ \
	\
	2, END_EVENT

/**
 * \brief		This workflow tests an instance with two statements types:
 * 				computation statements and local function statements.
 * 				One statement simply measures temperature.
 * 				The LEDs blink 2 times
 **/
#define WORKFLOW_SPEC_2_3 2, /* workflow id */ \
	4,  /* number of wf_elems 0..3 */ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of parallel instances required */ \
	1,  /* maximum number of parallel instances required */ \
	0,  /* looping (loop infinite times)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 1, /* second task, execute w. 1 computation statement */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 2, REPOSITORY_VALUE, SENSOR_TEMPERATURE_CELSIUS, /* var1 = temp;' */ \
	\
	2, EXECUTE_TASK, 3, 1,  /* third task, execute with 1 local function statement:*/ \
	LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2;' */ \
	\
	3, END_EVENT

/**
 * \brief		This workflow tests an instance with two statements types:
 * 				computation statements and local function statements.
 *
 * 				The LEDs blink as many times as the current temperature in degrees
 **/
#define WORKFLOW_SPEC_2_2 2, /* workflow id */ \
	4,  /* number of wf_elems*/ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of parallel instances required */ \
	1,  /* maximum number of parallel instances required */ \
	0,  /* looping (loop infinite times)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	\
	1, EXECUTE_TASK, 2, 1, /* second task, execute w. 2 computation statements */ \
	COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 0, REPOSITORY_VALUE, SENSOR_TEMPERATURE_CELSIUS, /* var1 = 0 + temp;' */ \
	\
	2, EXECUTE_TASK, 3, 1,  /* third task, execute with 1 local function statements:*/ \
		LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, USER_FIELD + 0, /*  'local blink $var1'  (equals 'local blink 4')*/ \
	\
	3, END_EVENT

/**
 * \brief		This workflow tests an instance with two statements types:
 * 				computation statements and local function statements.
 **/
#define WORKFLOW_SPEC_2_1 2, /* workflow id */ \
		4,  /* number of wf_elems*/ \
		0,  /* number of scopes*/ \
		1,  /* minimum number of parallel instances required */ \
		1,  /* maximum number of parallel instances required */ \
		3,  /* looping (loop 3 times)*/ \
		0, START_EVENT, 1, /* first task, start event */ \
		\
		1, EXECUTE_TASK, 2, 2, /* second task, execute w. 2 computation statements */ \
		COMPUTATION_STATEMENT, USER_FIELD + 0, 5, OPERATOR_ADD, UINT8_VALUE, 1, UINT8_VALUE, 2, /* var1 = 1 + 2;' */ \
		COMPUTATION_STATEMENT, USER_FIELD + 1, 5, OPERATOR_ADD, UINT8_VALUE, 1, REPOSITORY_VALUE, USER_FIELD + 0,  /* var2 = 1 + $var1;' */ \
		\
		2, EXECUTE_TASK, 3, 3,  /* third task, execute with 3 local function statements:*/ \
			LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, REPOSITORY_VALUE, USER_FIELD + 1, /*  'local blink $var2'  (equals 'local blink 4')*/ \
			LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, STRING_VALUE, 1, 51, /*  'local blink 3' */ \
			LOCAL_FUNCTION_STATEMENT, 0, 5, 1, 98, 108, 105, 110, 107, UINT8_VALUE, 2,/*  'local blink 2' */ \
		\
		3, END_EVENT

/**
 * \brief		This dummy workflow tests workflow and token instantiation
 **/
#define WORKFLOW_SPEC_1 1, /* workflow id */ \
	2,  /* number of wf_elems*/ \
	0,  /* number of scopes*/ \
	1,  /* minimum number of parallel instances required */ \
	1,  /* maximum number of parallel instances required */ \
	0,  /* looping (loop infinitely)*/ \
	0, START_EVENT, 1, /* first task, start event */ \
	1, END_EVENT
// ----

PROCESS(tester_process, "workflow tester");
AUTOSTART_PROCESSES(&tester_process);

static uint8_t wf_spec[] = { WORKFLOW_SPEC };
#if (WITH_STOP==1)
static struct etimer control_timer;
#endif

PROCESS_THREAD( tester_process, ev, data) {
	PROCESS_BEGIN()
		;

		leds_off(LEDS_ALL);

		PRINTF(3, "(UF-TESTER) Starting\n");

		/*	LOG("[%u.%u:%10lu] tester::main() : size of a 'struct wf_elem' is %u, size of a 'struct wf_start_event' is %u\n",
		 rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), sizeof(struct wf_elem), sizeof(struct wf_start_event));

		 LOG("[%u.%u:%10lu] tester::main() : size of 'wf_spec' array is %u\n",
		 rimeaddr_node_addr.u8[0],rimeaddr_node_addr.u8[1], clock_time(), sizeof(wf_spec)); */

		ukuflow_mgr_init();

		if (node_id == ROOT_NODE_ID) {

			PRINTF(3, "(UF-TESTER) Requesting workflow registration: ");
			PRINT_ARR(3, wf_spec, sizeof(wf_spec));

			ukuflow_mgr_register(wf_spec, sizeof(wf_spec));

#if (WITH_STOP==1)
			/* wait a while*/
			static clock_time_t ww = CLOCK_SECOND * 500L;
			etimer_set(&control_timer, ww);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
			PRINTF(1,
					"(UF-TESTER) Requesting deregistration after %lu second timer\n",
					ww);
			ukuflow_mgr_deregister(wf_spec[0]);
#endif

		}
	PROCESS_END();
}

