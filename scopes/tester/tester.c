#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "contiki.h"
#include "dev/leds.h"
#include "lib/random.h"
#include "node-id.h"
#include "net/rime/rimeaddr.h"

/* init */
#include "scopes-selfur.h"
//#include "scopes-flooding.h"
#include "scopes-membership-simple.h"
#include "expression-eval.h"

/* scopes app */
#include "scopes-application.h"
#include "scopes.h"

/* logging */
#include "logger.h"
#define SCOPES_TESTER_SID 1
#define ROOT_NODE_ID 1
#define AUTOMATIC 1
#ifdef AUTOMATIC
#include "sys/ctimer.h"
#else
#include "dev/button-sensor.h"
#endif

/* \brief definition of super scope */
#define SUPER_SCOPE_ID 111
#define SUPER_SCOPE_TTL 60
#define SUPER_SCOPE_SPEC PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 18

/* \brief definition of sub scope */
#define SUB_SCOPE_ID 222
#define SUB_SCOPE_TTL 60
#define SUB_SCOPE_SPEC PREDICATE_GET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 8

/* \brief number of iterations this test will run */
#define NUM_ITERATIONS 1

PROCESS(tester_process, "tester");
AUTOSTART_PROCESSES(&tester_process);

//static unsigned char leds_status;

static uint8_t send_scope_id = 0;
static struct ctimer timer;
static char *test_data_towards_members =
		"[12345678][12345678][12345678][12345678][12345678]";
//static char
//		*test_data_towards_creator =
//				"0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 ";
static char *test_data_towards_creator =
		"]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[";

/* helper functions */

//static int
//readline(int fd, char *buffer)
//{
//  int i;
//  for (i = 0; i < LOG_MSG_MAX; i++) {
//    if (cfs_read(fd, buffer + i, 1) > 0) {
//      if (buffer[i] == '\n') {
//        return ++i;
//      }
//    }
//    else {
//      return 0;
//    }
//  }
//  return LOG_MSG_MAX;
//}
//
//static unsigned long
//time_milliseconds(void)
//{
//  clock_time_t cclock = clock_time();
//  unsigned long mseconds = clock_seconds() * 1000 + cclock - (cclock / CLOCK_SECOND) * CLOCK_SECOND;
//
//  return mseconds;
//}
/* time broadcast functions */

/* scope functions */

void send_reply(void *id) {

	PRINTF(1, "(SCOPES TESTER) sending reply to scope %u\n", send_scope_id);
	leds_off(LEDS_RED);
	scopes_send(SCOPES_TESTER_SID, send_scope_id, TRUE,
			test_data_towards_creator, strlen(test_data_towards_creator));
}

void add_scope(scope_id_t scope_id) {
	PRINTF(1, "(SCOPES TESTER) added scope: %u\n", scope_id);
}

void remove_scope(scope_id_t scope_id) {
	PRINTF(1, "(SCOPES TESTER) removed scope: %u\n", scope_id);
	leds_off(LEDS_RED);
}

void join_scope(scope_id_t scope_id) {
	PRINTF(1, "(SCOPES TESTER) joined scope: %u\n", scope_id);
	if (scope_id == SUPER_SCOPE_ID) {
		leds_on(LEDS_BLUE);
	} else if (scope_id == SUB_SCOPE_ID) {
		leds_on(LEDS_GREEN);
	}
}

void leave_scope(scope_id_t scope_id) {
	PRINTF(1, "(SCOPES TESTER) left scope: %u\n", scope_id);
	if (scope_id == SUPER_SCOPE_ID) {
		leds_off(LEDS_BLUE);
	} else if (scope_id == SUB_SCOPE_ID) {
		leds_off(LEDS_GREEN);
	}
	leds_off(LEDS_RED);
	ctimer_stop(&timer);
}

void recv_data(scope_id_t scope_id, void *data, data_len_t data_len,
		bool to_creator, const rimeaddr_t *source) {

	PRINTF(1,
			"(SCOPES TESTER) received data via scope=%u, source=[%u.%u], to_creator=%u, len=%u: [", scope_id, source->u8[0], source->u8[1], to_creator, data_len);
	PRINT_ARR(1, data, data_len);

	if (!to_creator)
		leds_on(LEDS_RED);
	else if (node_id == ROOT_NODE_ID)
		leds_toggle(LEDS_RED);

	if (!to_creator && (node_id != ROOT_NODE_ID)) {
		uint8_t delay = 3 + (random_rand() % 10 * CLOCK_SECOND);
		send_scope_id = scope_id;
		ctimer_set(&timer, delay, send_reply, NULL);
		PRINTF(1, "(SCOPES TESTER) reply timer set to %d seconds\n", delay);
	}

}

/* end scope functions */

static struct scopes_application tester_callbacks = { add_scope, remove_scope,
		join_scope, leave_scope, recv_data };

PROCESS_THREAD(tester_process, ev, data) {
	PROCESS_BEGIN()
		;

		PRINTF(1, "(SCOPES TESTER) Node %u started\n", node_id);

		scopes_init(&scopes_selfur, &simple_membership);
		//		scopes_init(&scopes_flooding, &simple_membership);

		scopes_register(SCOPES_TESTER_SID, &tester_callbacks);

		leds_off(LEDS_ALL);

		static uint8_t test_iteration = 0;

		PRINTF(1, "(SCOPES TESTER) ready to handle commands\n");

		if (node_id == ROOT_NODE_ID) {
			while (++test_iteration <= NUM_ITERATIONS) {

				PRINTF(1, "(SCOPES TESTER) start test iteration %d\n", test_iteration);
				static uint8_t super_spec[] = { SUPER_SCOPE_SPEC };
				static uint8_t sub_spec[] = { SUB_SCOPE_SPEC };

				/* wait for button push or for timer to elapse*/
#ifdef AUTOMATIC
				static struct etimer control_timer;
				etimer_set(&control_timer, CLOCK_SECOND * 2);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
				SENSORS_ACTIVATE(button_sensor);
#endif

				/* run test */

				/* open super scope */
#ifdef AUTOMATIC
				PRINTF(1,
						"(SCOPES TESTER) Waiting 10 seconds to open super scope\n");
				etimer_set(&control_timer, CLOCK_SECOND * 10);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
				PRINTF(1,"(SCOPES TESTER) press button to open super scope\n");
				PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
						data == &button_sensor );
#endif
//			leds_toggle(LEDS_YELLOW);
				PRINTF(1, "(SCOPES TESTER) opening super scope\n");
				scopes_open(SCOPES_TESTER_SID, SCOPES_WORLD_SCOPE_ID,
						SUPER_SCOPE_ID, (void *) super_spec,
						sizeof(super_spec) / sizeof(uint8_t),
						SCOPES_FLAG_INTERCEPT | SCOPES_FLAG_DYNAMIC,
						SUPER_SCOPE_TTL);

//#ifdef AUTOMATIC
//			PRINTF(1,"(SCOPES TESTER) Waiting 5 seconds to send message to super scope\n");
//			etimer_set(&control_timer, CLOCK_SECOND * 5);
//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
//#else
//			PRINTF(1,"(SCOPES TESTER) press button to send message to super scope\n");
//			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
//					data == &button_sensor );
//#endif
//			PRINTF(1,"(SCOPES TESTER) sending data message to super scope\n");
//			scopes_send(SCOPES_TESTER_SID, SUPER_SCOPE_ID, FALSE, test_data_towards_members, strlen(test_data_towards_members));

//			/* open sub scope */
//#ifdef AUTOMATIC
//			PRINTF(1,"(SCOPES TESTER) Waiting 30 seconds to open sub scope\n");
//			etimer_set(&control_timer, CLOCK_SECOND * 30);
//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
//#else
//			PRINTF(1,"(SCOPES TESTER) press button to open sub scope\n");
//			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
//					data == &button_sensor );
//#endif
//			PRINTF(1,"(SCOPES TESTER) opening sub scope\n");
//			scopes_open(SCOPES_TESTER_SID, SUPER_SCOPE_ID, SUB_SCOPE_ID,
//					(void *) sub_spec, sizeof(sub_spec)/sizeof(uint8_t), SCOPES_FLAG_NONE//SCOPES_FLAG_INTERCEPT
//					, SUB_SCOPE_TTL);
//
//#ifdef AUTOMATIC
//			PRINTF(1,"(SCOPES TESTER) Waiting 5 seconds to send message to sub scope\n");
//			etimer_set(&control_timer, CLOCK_SECOND * 5);
//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
//#else
//			PRINTF(1,"(SCOPES TESTER) press button to send message to sub scope\n");
//			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
//					data == &button_sensor );
//#endif
//			PRINTF(1,"(SCOPES TESTER) sending data message to sub scope\n");
//			scopes_send(SCOPES_TESTER_SID, SUB_SCOPE_ID, FALSE, test_data_towards_members, strlen(test_data_towards_members));
//
				/* wait a while */
				//			etimer_set(&control_timer, 15 * CLOCK_SECOND);
				//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
				//			PRINTF(1,"(SCOPES TESTER) press button to send message to sub scope\n");
				//			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
				//					data == &button_sensor );
				//			PRINTF(1,"(SCOPES TESTER) sending data message to sub scope\n");
				//			scopes_send(SCOPES_TESTER_SID, SUB_SCOPE_ID, FALSE, test_data_towards_members, strlen(test_data_towards_members));
				//
				//			/* wait a while */
				//			//			etimer_set(&control_timer, 100 * CLOCK_SECOND);
				//			//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
				//			//			etimer_set(&control_timer, 100 * CLOCK_SECOND);
				//			//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
				//			//			etimer_set(&control_timer, 100 * CLOCK_SECOND);
				//			//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
				/* close super scope */
#ifdef AUTOMATIC
				PRINTF(1,
						"(SCOPES TESTER) Waiting 300 seconds to close super scope\n");
				etimer_set(&control_timer, CLOCK_SECOND * 300);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
				PRINTF(1,"(SCOPES TESTER) press button to close super scope\n");
				PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
						data == &button_sensor );
#endif
				PRINTF(1, "(SCOPES TESTER) closing super scope\n");
				scopes_close(SCOPES_TESTER_SID, SUPER_SCOPE_ID);


				PRINTF(1,
						"(SCOPES TESTER) Waiting %d seconds for nodes who didn't hear close message.\n", SUPER_SCOPE_TTL);
				etimer_set(&control_timer, CLOCK_SECOND * SUPER_SCOPE_TTL);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
				PRINTF(1, "(SCOPES TESTER) finish test iteration %d\n", test_iteration);
//			//			while (1) {
//			//				/* simulation end,  blink green led */
//			//				etimer_set(&control_timer, CLOCK_SECOND / 2);
//			//				leds_on(LEDS_GREEN);
//			//				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
//			//				leds_off(LEDS_GREEN);
//			//
//			//			}
//
//#ifdef AUTOMATIC
//			PRINTF(1,"(SCOPES TESTER) Waiting 100 seconds to restart test\n");
//			etimer_set(&control_timer, CLOCK_SECOND * 100);
//			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
//#else
//			PRINTF(1,"(SCOPES TESTER) press button to restart test\n");
//			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
//					data == &button_sensor );
//#endif
//			PRINTF(1,"(SCOPES TESTER) test will restart\n");

			}
		}
	PROCESS_END();
}
