#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "contiki.h"
#include "dev/leds.h"
#include "dev/serial-line.h"
#include "sys/etimer.h"

#include "data-repository.h"
#include "expression-eval.h"

PROCESS(tester_process, "data-repository tester");
AUTOSTART_PROCESSES(&tester_process);

PROCESS_THREAD(tester_process, ev, data) {
	PROCESS_BEGIN();

		leds_off(LEDS_ALL);

		static struct etimer w_timer;
		static data_repository_id_t repo_id;

		char *s_input;
//		static uint8_t serial_input_len;
//		static uint8_t decoded[MAX_DECODE_LEN];
//		static int decoded_len;

		static clock_time_t ttl;
		static data_len_t data_len;
		static uint8_t *dr_data;

		static int16_t temp;
		
		static entry_id_t entry_id;
		static entry_type_t entry_type;
		
		static uint8_t cmd_nr = 0;

		while(1) {

			printf("enter input %i:\n", cmd_nr++);

			PROCESS_WAIT_EVENT_UNTIL(ev == serial_line_event_message && data != NULL);
			//PROCESS_WAIT_EVENT();
		    //if (ev == serial_line_event_message) {

			s_input = (char *) data;
//			serial_input_len = strlen(data);

			printf("Received text was '%s'\n", s_input);

			if (s_input[0] == '0') {
				printf("wow\n");
				s_input++;
				clock_time_t ttl = atol(s_input);
				repo_id = data_repository_create(ttl);

				if (repo_id != 0) 
					printf("creation ok!, ttl=[%lu], repo_id=[%u]\n", ttl, repo_id);
				else
					printf("fail!\n");
				

			} // ------------------------------------------
			else if (s_input[0] == '1') {
				repo_id = atoi(++s_input);
				if (data_repository_remove(repo_id))
					printf("removal ok!, repo_id=[%u]\n", repo_id);
				else
					printf("fail, repo_id=[%u]!\n", repo_id);
			} // ------------------------------------------
			else if (s_input[0] == '2') {
			  // operation is wait
				ttl = atol(++s_input);
				etimer_set(&w_timer, CLOCK_SECOND * ttl);
				PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&w_timer));
				printf("waiting ok!, wait=[%lu]\n", ttl);

			} // ------------------------------------------
			else if (s_input[0] == '3') {
				// operation is get data
				repo_id = atoi(++s_input);
				s_input = strchr(s_input, ' ')+1;
				entry_id = atoi(++s_input);
//				printf("test ok! repo_id=[%u] entry_id=[%u] dr_data=[__] dr_data_len=[__]\n", repo_id, entry_id);
				dr_data = data_repository_get_data(repo_id, entry_id, &data_len);
				
				if (dr_data) {
					temp = *((uint16_t*)dr_data);
					printf("get data ok! repo_id=[%u] entry_id=[%u] dr_data_len=[%u] dr_data=[%d] \n", repo_id, entry_id, data_len, temp);
				} // else do nothing, thus fails

			} // ------------------------------------------
			else if (s_input[0] == '4') {
				// operation is set data
				repo_id = atoi(++s_input);
				s_input = strchr(s_input, ' ')+1;
				entry_id = atoi(++s_input);
				s_input = strchr(s_input, ' ')+1;
				entry_type = atoi(++s_input);
				s_input = strchr(s_input, ' ')+1;
				data_len = atoi(++s_input);
				s_input = strchr(s_input, ' ')+1;
				temp = atol(++s_input);
//				printf("test ok! repo_id=[%u] entry_id=[%u] dr_data=[__] dr_data_len=[__]\n", repo_id, entry_id);
				data_repository_set_data(repo_id, entry_id, entry_type, data_len, (uint8_t*)(&temp));
				
				printf("set data ok! repo_id=[%u] entry_id=[%u] entry_type=[%d] dr_data_len=[%u] dr_data=[%d] \n", repo_id, entry_id, entry_type, data_len, temp);
			 }
		
		}

		/*Try to create a data repository:
		 repo_id = data_repository_create(CLOCK_SECOND * 10);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id);

		 if (repo_id != 0) {

		 // wait for some time
		 etimer_set(&w_timer, CLOCK_SECOND * 3);
		 printf("[tester] Waiting 3 seconds...\n");
		 PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&w_timer));

		 data_len_t data_len;
		 uint8_t *data;
		 uint16_t temp;

		 data = data_repository_get_data(repo_id, SENSOR_LIGHT_PAR_RAW, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_LIGHT_PAR_RAW was %u\n", temp);

		 data = data_repository_get_data(repo_id, SENSOR_TEMPERATURE_CELSIUS, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_TEMPERATURE_CELSIUS was %u\n", temp);

		 // wait for some time
		 etimer_set(&w_timer, CLOCK_SECOND * 5);
		 printf("[tester] Waiting 5 seconds...\n");
		 PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&w_timer));

		 printf("\n[tester] Following results should be identical, since only 5 secs elapsed:\n");

		 data = data_repository_get_data(repo_id, SENSOR_LIGHT_PAR_RAW, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_LIGHT_PAR_RAW was %u\n", temp);
		 data = data_repository_get_data(repo_id, SENSOR_TEMPERATURE_CELSIUS, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_TEMPERATURE_CELSIUS was %u\n", temp);

		 // result should have been identical, since only 5 seconds elapsed


		 // wait for some time so that ttl elapses
		 etimer_set(&w_timer, CLOCK_SECOND * 7);
		 printf("[tester] Waiting 7 seconds...\n");
		 PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&w_timer));

		 printf("\n[tester] Following results should be new, since more than 10 secs elapsed:\n");

		 data = data_repository_get_data(repo_id, SENSOR_LIGHT_PAR_RAW, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_LIGHT_PAR_RAW was %u\n", temp);
		 data = data_repository_get_data(repo_id, SENSOR_TEMPERATURE_CELSIUS, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_TEMPERATURE_CELSIUS was %u\n", temp);

		 // result should have been new, since more than 10 seconds elapsed

		 // wait again for some time so that ttl elapses
		 etimer_set(&w_timer, CLOCK_SECOND * 12);
		 printf("[tester] Waiting 12 seconds...\n");
		 PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&w_timer));

		 printf("\n[tester] Following results should be newer, since more than 10 secs elapsed:\n");

		 data = data_repository_get_data(repo_id, SENSOR_LIGHT_PAR_RAW, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_LIGHT_PAR_RAW was %u\n", temp);
		 data = data_repository_get_data(repo_id, SENSOR_TEMPERATURE_CELSIUS, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field SENSOR_TEMPERATURE_CELSIUS was %u\n", temp);

		 // result should have been new, since more than 10 seconds elapsed

		 printf("\n[tester] Testing further entries:\n");
		 entry_id_t entry_id = NODE_ID+1;
		 uint16_t repo_data = 300;
		 printf("[tester] Setting field REPO_VALUE[%u] to %d\n", entry_id, repo_data);
		 data_repository_set_data(repo_id, entry_id, MANUAL_UPDATE_ENTRY, sizeof(uint16_t), (uint8_t*)&repo_data);

		 data = data_repository_get_data(repo_id, entry_id, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] REPO_VALUE[%u] was %u, len is %u\n", entry_id, temp, data_len);

		 clock_time_t clock = clock_time(), clock_answer = 0;
		 entry_id = 11;
		 printf("[tester] Setting field REPO_VALUE[%u] to %10lu\n", entry_id, clock);
		 data_repository_set_data(repo_id, entry_id, MANUAL_UPDATE_ENTRY, sizeof(clock_time_t), (uint8_t*)&clock);

		 data = data_repository_get_data(repo_id, entry_id, &data_len);
		 clock_answer = *((clock_time_t*)data);
		 printf("[tester] REPO_VALUE[%u] was %10lu\n", entry_id, clock_answer);

		 data = data_repository_get_data(repo_id, NODE_ID, &data_len);
		 temp = *((uint16_t*)data);
		 printf("[tester] Field NODE_ID was %u\n", temp);

		 printf("\n\n[tester] Testing expressions:\n");
		 expression_eval_set_repository(repo_id);

		 uint8_t expression[] = {OPERATOR_MULT, UINT8_VALUE, 3, UINT8_VALUE, 2};
		 long int result = expression_eval_evaluate(expression, 5);
		 printf("[tester] Calculating 3 * 2 : %li\n", result);

		 uint8_t expression2[] = {PREDICATE_LET, REPOSITORY_VALUE, NODE_ID, UINT8_VALUE, 1};
		 result = expression_eval_evaluate(expression2, 5);
		 printf("[tester] Calculating NODE_ID <= 1 : %li\n", result);

		 uint8_t expression3[] = {OPERATOR_ADD, UINT16_VALUE, 0, 200, REPOSITORY_VALUE, NODE_ID+1};
		 result = expression_eval_evaluate(expression3, 6);
		 printf("[tester] Calculating: 200 + REPO_VALUE[9] : %li\n", result);

		 unsigned long int lon = 4294967295UL;
		 data_repository_set_data(repo_id, entry_id, MANUAL_UPDATE_ENTRY, sizeof(clock_time_t), (uint8_t*)&lon);

		 uint8_t expression4[] = {REPOSITORY_VALUE, entry_id };
		 result = expression_eval_evaluate(expression4, 3);
		 printf("[tester] Calculating: MAX unsigned long integer : %ld\n", result);

		 uint8_t expression5[] = {PREDICATE_EQ, INT16_VALUE, 127, 255, REPOSITORY_VALUE, 11};
		 result = expression_eval_evaluate(expression5, 6);
		 printf("[tester] Calculating: 3479 == REPO_VALUE[clock] : %li\n", result);
		 }

		 printf("\n\n[tester] Testing data repository construction:\n");
		 data_repository_id_t repo_id_2 = data_repository_create(CLOCK_SECOND * 5);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id_2);
		 data_repository_remove(repo_id);
		 printf("[tester] Successfully removed repository with id %u...\n", repo_id);
		 data_repository_id_t repo_id_3 = data_repository_create(CLOCK_SECOND * 2);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id_3);
		 data_repository_id_t repo_id_4 = data_repository_create(CLOCK_SECOND * 2);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id_4);
		 data_repository_id_t repo_id_5 = data_repository_create(CLOCK_SECOND * 2);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id_5);
		 data_repository_id_t repo_id_6 = data_repository_create(CLOCK_SECOND * 2);
		 printf("[tester] Successfully created repository with id %u...\n", repo_id_6);
		 */
		PROCESS_END();
	}
