#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "contiki.h"
#include "dev/leds.h"
#include "lib/random.h"
#include "node-id.h"

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

/* scope 1 */
#define SCOPE_1_ID 111
#define SCOPE_1_TTL 50
#define SCOPE_1_SPEC PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 8
/* scope 2 */
#define SCOPE_2_ID 222
#define SCOPE_2_TTL 50
#define SCOPE_2_SPEC PREDICATE_GT, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 8

PROCESS(tester_process, "tester_parallel")
;
AUTOSTART_PROCESSES(&tester_process);

//static unsigned char leds_status;

static uint8_t send_scope_id = 0;
static struct ctimer timer;
static char *test_data_towards_members_scope1 =
		"[12345678][12345678][12345678][12345678][12345678]";
static char *test_data_towards_members_scope2 =
		"[12345678][12345678][12345678][12345678][12345678]";
//static char
//		*test_data_towards_creator =
//				"0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 ";
//static char
//		*test_data_towards_creator =
//				"]87654321[]87654321[]87654321[]87654321[]87654321[";
static char
		*test_data_towards_creator =
				"]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[";
//static char
//		*test_data_towards_creator =
//				"]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[]87654321[";

/* scope functions */

void send_reply(void *id) {

	PRINTF(1,"(SCOPES TESTER) sending reply to scope %u\n", send_scope_id);
	leds_off(LEDS_RED);
	scopes_send(SCOPES_TESTER_SID, send_scope_id, TRUE,
			test_data_towards_creator, strlen(test_data_towards_creator));
}

void add_scope(scope_id_t scope_id) {
	PRINTF(3,"(SCOPES TESTER) added scope: %u\n", scope_id);
}

void remove_scope(scope_id_t scope_id) {
	PRINTF(3,"(SCOPES TESTER) removed scope: %u\n", scope_id);
	leds_off(LEDS_RED);
}

void join_scope(scope_id_t scope_id) {
	//  PRINTF(3,"(SCOPES TESTER) joined scope: %u\n", scope_id);
	if (scope_id == SCOPE_1_ID) {
		leds_on(LEDS_BLUE);
	} else if (scope_id == SCOPE_2_ID) {
		leds_on(LEDS_GREEN);
	}
}

void leave_scope(scope_id_t scope_id) {
	PRINTF(3,"(SCOPES TESTER) left scope: %u\n", scope_id);
	if (scope_id == SCOPE_1_ID) {
		leds_off(LEDS_BLUE);
	} else if (scope_id == SCOPE_2_ID) {
		leds_off(LEDS_GREEN);
	}
	leds_off(LEDS_RED);
	ctimer_stop(&timer);
}

void recv_data(scope_id_t scope_id, void *data, data_len_t data_len,
		bool to_creator, const rimeaddr_t *source) {

	PRINTF(1,"(SCOPES TESTER) received data via scope=%u, source=[%u.%u], to_creator=%u, len=%u: ", scope_id, source->u8[0], source->u8[1], to_creator, data_len);
	PRINT_ARR(1, data, data_len);

	if (!to_creator)
		leds_on(LEDS_RED);
	else if (node_id == ROOT_NODE_ID)
		leds_toggle(LEDS_RED);

	if (!to_creator && (node_id != ROOT_NODE_ID)) {
		clock_time_t delay = 3 * CLOCK_SECOND + (random_rand() % (20*CLOCK_SECOND));
		send_scope_id = scope_id;
		ctimer_set(&timer, delay, send_reply, NULL);
		PRINTF(1,"(SCOPES TESTER) reply timer set to ~%lu seconds, through scope %u\n", delay/CLOCK_SECOND, send_scope_id);
	}

}

/* end scope functions */

static struct scopes_application tester_callbacks = { add_scope, remove_scope,
		join_scope, leave_scope, recv_data };

PROCESS_THREAD( tester_process, ev, data) {
	PROCESS_BEGIN();

	leds_off(LEDS_ALL);

	PRINTF(1,"(SCOPES TESTER) application started at node %u\n", node_id);

	scopes_init(&scopes_selfur, &simple_membership);
	//		scopes_init(&scopes_flooding, &simple_membership);
	scopes_register(SCOPES_TESTER_SID, &tester_callbacks);

	leds_off(LEDS_ALL);

	PRINTF(1,"(SCOPES TESTER) ready to handle commands\n");

	while (node_id == ROOT_NODE_ID) {

		static uint8_t scope_1_spec[] = { SCOPE_1_SPEC};
		static uint8_t scope_2_spec[] = {SCOPE_2_SPEC};

#ifdef AUTOMATIC
				static struct etimer control_timer;
#else
				SENSORS_ACTIVATE(button_sensor);
#endif

#ifdef AUTOMATIC
				/* wait for a short time */
				etimer_set(&control_timer, CLOCK_SECOND * 2);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#endif

				/* run test */

				/* open scope 1*/
#ifdef AUTOMATIC
				PRINTF(1,"(SCOPES TESTER) Waiting 10 seconds to open scope 1\n");
			etimer_set(&control_timer, CLOCK_SECOND * 10);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
			PRINTF(3,"(SCOPES TESTER) press button to open scope 1\n");
			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
					data == &button_sensor );
#endif
			PRINTF(1,"(SCOPES TESTER) opening scope 1\n");
			scopes_open(SCOPES_TESTER_SID, SCOPES_WORLD_SCOPE_ID, SCOPE_1_ID,
					(void *) scope_1_spec, sizeof(scope_1_spec)/sizeof(uint8_t), SCOPES_FLAG_NONE, //SCOPES_FLAG_INTERCEPT|SCOPES_FLAG_DYNAMIC,
					SCOPE_1_TTL);

			/* open scope 2 */
#ifdef AUTOMATIC
			PRINTF(1,"(SCOPES TESTER) Waiting 10 seconds to open scope 2\n");
			etimer_set(&control_timer, CLOCK_SECOND * 10L);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
			PRINTF(3,"(SCOPES TESTER) press button to open scope 2\n");
			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
					data == &button_sensor );
#endif
			PRINTF(1,"(SCOPES TESTER) opening scope 2\n");
			scopes_open(SCOPES_TESTER_SID, SCOPES_WORLD_SCOPE_ID, SCOPE_2_ID,
					(void *) scope_2_spec, sizeof(scope_2_spec)/sizeof(uint8_t), SCOPES_FLAG_NONE//SCOPES_FLAG_INTERCEPT
					, SCOPE_2_TTL);

			#ifdef AUTOMATIC
						PRINTF(3,"(SCOPES TESTER) Waiting 10 seconds to send message to scope 1\n");
						etimer_set(&control_timer, CLOCK_SECOND * 10);
						PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
			#else
						PRINTF(3,"(SCOPES TESTER) press button to send message to scope 1\n");
						PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
								data == &button_sensor );
			#endif
						PRINTF(3,"(SCOPES TESTER) sending data message to scope 1\n");
						scopes_send(SCOPES_TESTER_SID, SCOPE_1_ID, FALSE, test_data_towards_members_scope1, strlen(test_data_towards_members_scope1));

			#ifdef AUTOMATIC
						PRINTF(3,"(SCOPES TESTER) Waiting 30 seconds to send message to scope 2\n");
						etimer_set(&control_timer, CLOCK_SECOND * 30);
						PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
			#else
						PRINTF(3,"(SCOPES TESTER) press button to send message to scope 2\n");
						PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
								data == &button_sensor );
			#endif
						PRINTF(3,"(SCOPES TESTER) sending data message to scope 2\n");
						scopes_send(SCOPES_TESTER_SID, SCOPE_2_ID, FALSE, test_data_towards_members_scope2, strlen(test_data_towards_members_scope2));

			/* close scope 1 */
#ifdef AUTOMATIC
			PRINTF(1,"(SCOPES TESTER) Waiting 500 seconds to close scope 1\n");
			etimer_set(&control_timer, CLOCK_SECOND * 500L);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
			PRINTF(3,"(SCOPES TESTER) press button to close scope 1\n");
			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
					data == &button_sensor );
#endif
			PRINTF(1,"(SCOPES TESTER) closing scope 1\n");
			scopes_close(SCOPES_TESTER_SID, SCOPE_1_ID);

			/* close scope 2 */
#ifdef AUTOMATIC
			PRINTF(1,"(SCOPES TESTER) Waiting 10 seconds to close scope 2\n");
			etimer_set(&control_timer, CLOCK_SECOND * 10L);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
			PRINTF(3,"(SCOPES TESTER) press button to close scope 2\n");
			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
					data == &button_sensor );
#endif
			PRINTF(1,"(SCOPES TESTER) closing scope 2\n");
			scopes_close(SCOPES_TESTER_SID, SCOPE_2_ID);

			//			while (1) {
			//				/* simulation end,  blink green led */
			//				etimer_set(&control_timer, CLOCK_SECOND / 2);
			//				leds_on(LEDS_GREEN);
			//				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
			//				leds_off(LEDS_GREEN);
			//
			//			}

#ifdef AUTOMATIC
			PRINTF(1,"(SCOPES TESTER) Waiting 300 seconds to restart test\n");
			etimer_set(&control_timer, CLOCK_SECOND * 300L);
			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&control_timer));
#else
			PRINTF(3,"(SCOPES TESTER) press button to restart test\n");
			PROCESS_WAIT_EVENT_UNTIL( ev == sensors_event &&
					data == &button_sensor );
#endif
			PRINTF(1,"(SCOPES TESTER) test will restart\n");

		}

						PROCESS_END();
					}
