/**
 * \addtogroup datamanager
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
 * \file	data-mgr.c
 * \brief	Data manager module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 */
#include "data-mgr.h"
#include "lib/list.h"

// for malloc
#include "stdlib.h"

// for memcpy
#include "string.h"

#include "net/rime/rimeaddr.h"
#include "logger.h"
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
#include "dev/sht11-sensor.h"
#include "dev/light-sensor.h"
#include "dev/battery-sensor.h"
#else
#if defined CONTIKI_TARGET_Z1
#include "dev/tmp102.h"
#include "dev/adxl345.h"
#include "dev/i2cmaster.h"
#endif
#endif

/**
 * \brief 		List of repositories
 */
LIST(repository_list);

static uint8_t initialized = 0;

/**
 * \brief		TODO
 *
 * \param data the memory section to update
 */
static void sensor_light_par_raw(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t *data16 = (uint16_t*) data;
	SENSORS_ACTIVATE(light_sensor);
	// no op to cause delay
	PRINTF(1,"\t");
	*data16 = light_sensor.value(LIGHT_SENSOR_PHOTOSYNTHETIC);
	SENSORS_DEACTIVATE(light_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_light_tsr_raw(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t *data16 = (uint16_t*) data;
	SENSORS_ACTIVATE(light_sensor);
	*data16 = light_sensor.value(LIGHT_SENSOR_TOTAL_SOLAR);
	SENSORS_DEACTIVATE(light_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_temperature_raw(void *data) {
	uint16_t *data16 = (uint16_t*) data;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	SENSORS_ACTIVATE(sht11_sensor);
	*data16 = sht11_sensor.value(SHT11_SENSOR_TEMP);
	SENSORS_DEACTIVATE(sht11_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	*data16 = tmp102_read_temp_raw();
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_temperature_celsius(void *data) {
	uint16_t *data16 = (uint16_t*) data;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t tmp;
	sensor_temperature_raw(&tmp);
	*data16 = (-39.60 + 0.01 * tmp);
#else
#ifdef CONTIKI_TARGET_Z1
	int16_t raw = tmp102_read_temp_raw();
	PRINTF(5,"RAW is %d\n", raw);
	uint16_t absraw;
	int16_t sign = 1;
	if (raw < 0) {		// Perform 2C's if sensor returned negative data
		absraw = (raw ^ 0xFFFF) + 1;
		sign = -1;
	} else
		absraw = raw;
	int16_t temp_integer_part = (absraw >> 8) * sign;
	uint16_t temp_fractional_part = ((absraw >> 4) % 16) * 625;	// Info in 1/10000 of degree
	PRINTF(5,"(DATA-MGR) Temperature is %d,%u \n", temp_integer_part, temp_fractional_part);

	*data16 = temp_integer_part;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_temperature_fahrenheit(void *data) {
	uint16_t *data16 = (uint16_t*) data;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t tmp;
	sensor_temperature_raw(&tmp);
	*data16 = (-39.60 + 0.01 * tmp) * 9 / 5 + 32;
#else
#ifdef CONTIKI_TARGET_Z1
	uint16_t tmp;
	sensor_temperature_celsius(&tmp);
	*data16 = (tmp * 9 / 5 + 32);
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_humidity_raw(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t *data16 = (uint16_t*) data;
	SENSORS_ACTIVATE(sht11_sensor);
	*data16 = sht11_sensor.value(SHT11_SENSOR_HUMIDITY);
	SENSORS_DEACTIVATE(sht11_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_humidity_percent(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t *data16 = (uint16_t*) data;
	SENSORS_ACTIVATE(sht11_sensor);
	int result = sht11_sensor.value(SHT11_SENSOR_HUMIDITY);
	*data16 = -4 + 0.0405 * result - 2.8e-6 * (result * result);
	SENSORS_DEACTIVATE(sht11_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_accm_x_axis(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
//	uint16_t *data16 = (uint16_t*) data;
//	*data16 = 0;
	data = NULL;
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_accm_y_axis(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
//	uint16_t *data16 = (uint16_t*) data;
//	*data16 = 0;
	data = NULL;
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_accm_z_axis(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
//	uint16_t *data16 = (uint16_t*) data;
//	*data16 = 0;
	data = NULL;
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_voltage_raw(void *data) {
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	uint16_t *data16 = (uint16_t*) data;
	SENSORS_ACTIVATE(battery_sensor);
	*data16 = battery_sensor.value(0);
	SENSORS_DEACTIVATE(battery_sensor);
#else
#ifdef CONTIKI_TARGET_Z1
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_co2_raw(void *data) {
	uint16_t *data16 = (uint16_t*) data;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	// TODO
	data = NULL;
#else
#ifdef CONTIKI_TARGET_Z1
	// TODO
	data = NULL;
#endif
#endif
}

/**
 * \brief		TODO
 */
static void sensor_co_raw(void *data) {
	uint16_t *data16 = (uint16_t*) data;
	data = NULL;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000
	// TODO
#else
#ifdef CONTIKI_TARGET_Z1
	// TODO
#endif
#endif
}

/**
 * \brief		TODO
 */
static void node_timestamp(void *data) {
	clock_time_t *data_clock = (clock_time_t*) data;
#if defined CONTIKI_TARGET_SKY || defined CONTIKI_TARGET_XM1000 || defined CONTIKI_TARGET_Z1
	printf("updating to %lu\n",*data_clock = clock_time());
	// TODO
#else
	// TODO
	data = NULL;
#endif
}

/**
 * \brief		Searches for a repository with a given id
 *
 * \param id the id of the repository being searched for
 */
static struct repository *
data_mgr_lookup(data_repository_id_t id) {
	struct repository *repo = list_head(repository_list);
	while ((repo != NULL) && (repo->id != id))
		repo = repo->next;
	return (repo);
}

/**
 * \brief		TODO
 */
static struct repository_entry *
repository_entry_lookup(struct repository* repo, entry_id_t entry_id) {
	PRINTF(3, "searching %d\n", entry_id);
	struct repository_entry *entry = list_head(repo->entry_list);
	while ((entry != NULL) && (entry->entry_id != entry_id)) {
		entry = entry->next;
		PRINTF(3, "entry %d\n", entry->entry_id);
	}
	PRINTF(3, "-*-*- %d\n", entry->entry_id);
	return (entry);
}

/**
 * \brief		Searches for the shortest ttl among all active repositories,
 * 				and assigns it to the common repository
 **/
static void adjust_ttl_common_repository() {
	// initialize value to maximum unsigned integer value
	clock_time_t min_ttl = -1;
	PRINTF(3, "initial max = %lu\n", min_ttl);
	struct repository *repo, *common_repo;
	common_repo = data_mgr_lookup(COMMON_REPOSITORY_ID);
	if (list_length(repository_list) == 1) {
		common_repo->ttl = 0;
	} else {
		for (repo = list_head(repository_list); repo != NULL; repo = repo->next)
			if ((repo->id != COMMON_REPOSITORY_ID) && (repo->ttl != 0)
					&& (repo->ttl < min_ttl))
				min_ttl = repo->ttl;
		common_repo->ttl = min_ttl;
		PRINTF(3, "found: %lu\n", min_ttl);
	}
}

/**
 * \brief		Initializes the data repository module (should be invoked only once)
 */
static void data_mgr_init() {

	if (!initialized) {
		struct repository *common_repo = malloc(sizeof(struct repository));
		if (common_repo != NULL) {

			// the system repository has id 1
			common_repo->id = COMMON_REPOSITORY_ID;
			// start common repository with largest ttl, then adjust to smallest
			common_repo->ttl = 0;
			LIST_STRUCT_INIT(common_repo, entry_list);

			// add repository to list of repositories
			list_add(repository_list, common_repo);

			clock_time_t now = clock_time();

			entry_id_t entry_id;
			// now create entries for sensors, and add them into common repository:
			for (entry_id = 0; entry_id < NODE_ID; entry_id++) {
				struct auto_repository_entry *entry = malloc(
						sizeof(struct auto_repository_entry)
								+ sizeof(uint16_t));
				if (entry != NULL) {
					// set entry type
					entry->entry_type = AUTOMATIC_UPDATE_ENTRY;

					// set entry_id
					entry->entry_id = entry_id;

					// set entry data length
					entry->data_len = sizeof(uint16_t);

					// set a timestamp to the future, such that the entry is automatically updated if data is requested right after initialization:
					entry->timestamp = now + 60 * CLOCK_SECOND;

					// now set pointer to updater function, if applies (e.g., for NODE_ID it doesn't)
					switch (entry_id) {
					case SENSOR_LIGHT_PAR_RAW:
						entry->updater = sensor_light_par_raw;
						break;
					case SENSOR_LIGHT_TSR_RAW:
						entry->updater = sensor_light_tsr_raw;
						break;
					case SENSOR_TEMPERATURE_RAW:
#ifdef CONTIKI_TARGET_Z1
						tmp102_init();
#endif
						entry->updater = sensor_temperature_raw;
						break;
					case SENSOR_TEMPERATURE_CELSIUS:
						entry->updater = sensor_temperature_celsius;
						break;
					case SENSOR_TEMPERATURE_FAHRENHEIT:
						entry->updater = sensor_temperature_fahrenheit;
						break;
					case SENSOR_HUMIDITY_RAW:
						entry->updater = sensor_humidity_raw;
						break;
					case SENSOR_HUMIDITY_PERCENT:
						entry->updater = sensor_humidity_percent;
						break;
					case SENSOR_ACCM_X_AXIS:
						entry->updater = sensor_accm_x_axis;
						break;
					case SENSOR_ACCM_Y_AXIS:
						entry->updater = sensor_accm_y_axis;
						break;
					case SENSOR_ACCM_Z_AXIS:
						entry->updater = sensor_accm_z_axis;
						break;
					case SENSOR_VOLTAGE_RAW:
						entry->updater = sensor_voltage_raw;
						break;
					case SENSOR_CO2_RAW:
						entry->updater = sensor_co2_raw;
						break;
					case SENSOR_CO_RAW:
						entry->updater = sensor_co_raw;
						break;
					} // switch

					list_add(common_repo->entry_list, entry);

				} // if (entry != NULL)

			} //for

			// now create entry for node id
			struct manual_repository_entry *entry_m = malloc(
					sizeof(struct manual_repository_entry) + sizeof(uint16_t));
			if (entry_m != NULL) {
				// set entry type
				entry_m->entry_type = MANUAL_UPDATE_ENTRY;

				// set entry_id
				entry_m->entry_id = NODE_ID;

				// set entry data length
				entry_m->data_len = sizeof(uint16_t);

				// set value
				uint16_t *value = (uint16_t*) (((uint8_t*) entry_m)
						+ sizeof(struct manual_repository_entry));
				*value = rimeaddr_node_addr.u8[0]
						+ (((uint16_t) rimeaddr_node_addr.u8[1]) << 8);

				list_add(common_repo->entry_list, entry_m);
			}

			// now create entry for timestamp
			struct auto_repository_entry *entry_a = malloc(
					sizeof(struct auto_repository_entry)
							+ sizeof(clock_time_t));
			if (entry_a != NULL) {
				// set entry type
				entry_a->entry_type = AUTOMATIC_UPDATE_ENTRY;

				// set entry_id
				entry_a->entry_id = NODE_TIME;

				// set entry data length
				entry_a->data_len = sizeof(clock_time_t);

				// set a timestamp to the future, such that the entry is automatically updated if data is requested right after initialization:
				entry_a->timestamp = now + 60 * CLOCK_SECOND;

				// now set pointer to updater function
				entry_a->updater = node_timestamp;

				list_add(common_repo->entry_list, entry_a);
			}

			initialized = 1;
		}
	}
}
/*------------------------------------------------------------------------------*/
/**
 * \brief			Creates a data repository on this node.
 * \param max_ttl	the maximum time to live that attributes in this data repository can have.
 * \return			id of the repository created, or 0 otherwise.
 */
data_repository_id_t data_mgr_create(clock_time_t max_ttl) {
	if (!initialized)
		data_mgr_init();

	if (list_length(repository_list) == MAX_REPOSITORIES)
		/* No space left for more repositories! */
		return (0);

	struct repository *repo = malloc(sizeof(struct repository));

	/* Check if it was possible to allocate memory for repository: */
	if (repo == NULL)
		return (0);

	/* Lowest repository id for users is 2 */
	data_repository_id_t id = 2;
	while (data_mgr_lookup(id) != NULL)
		id++;

	repo->id = id;
	repo->ttl = max_ttl;
	LIST_STRUCT_INIT(repo, entry_list);
	list_add(repository_list, repo);

	adjust_ttl_common_repository();
	return (id);

}

/**
 * \brief		Removes a repository from this node
 *
 * \param id	the id of the repository to be removed
 *
 * \return 		TRUE if the repository could be removed, FALSE otherwise
 */
bool data_mgr_remove(data_repository_id_t id) {

	if (initialized && (id != COMMON_REPOSITORY_ID)) {
		struct repository *repo = data_mgr_lookup(id);
		if (repo != NULL) {
			// repository exists, so it must be removed now

			// this means that all of its repository_entry structs must be removed:
			while (list_length(repo->entry_list) > 0) {
				struct repository_entry *entry = list_head(repo->entry_list);
				list_remove(repo->entry_list, entry);
				free(entry);
			}

			list_remove(repository_list, repo);
			free(repo);

			adjust_ttl_common_repository();

			return (TRUE);
		}
	}

	return (FALSE);
}

/*---------------------------------------------------------------------------*/
static void repository_entry_clear(struct repository *repo, entry_id_t entry_id) {
	struct repository_entry *entry = repository_entry_lookup(repo, entry_id);
	if (entry != NULL) {
		list_remove(repo->entry_list, entry);
		free(entry);
	}

}
/*---------------------------------------------------------------------------*/
static struct repository_entry *
repository_entry_create(struct repository *repo, entry_id_t entry_id,
		entry_type_t entry_type, data_len_t data_len) {

	struct repository_entry *entry = NULL;
	switch (entry_type) {
	case AUTOMATIC_UPDATE_ENTRY: {
		struct auto_repository_entry *a_entry = malloc(
				sizeof(struct auto_repository_entry) + data_len);
		if (a_entry == NULL)
			return (NULL);
		a_entry->updater = NULL;
		a_entry->timestamp = 0;
		entry = (struct repository_entry*) a_entry;
		break;
	}
	case MANUAL_UPDATE_ENTRY: {
		struct manual_repository_entry *m_entry = malloc(
				sizeof(struct manual_repository_entry) + data_len);
		if (m_entry == NULL)
			return (NULL);
		entry = (struct repository_entry*) m_entry;
		break;
	}
	}
	entry->entry_type = entry_type;
	entry->entry_id = entry_id;
	entry->data_len = data_len;

	return (entry);
}

/**
 * \brief		Sets the value of an entry within a data repository
 *
 * \param repo_id_param
 * \param entry_id
 * \param entry_type it can be updated manually, or periodically
 * \param data_len length of the value to be stored
 * \param data the actual data to use when setting a value
 */
void data_mgr_set_data( //
		data_repository_id_t repo_id_param, //
		entry_id_t entry_id, //
		entry_type_t entry_type, //
		data_len_t data_len, //
		uint8_t *data) {

	data_repository_id_t repo_id;

	// Are we being asked for a system-wide entry (entry_id <= NODE_ID) or a user-specific  one?
	if (entry_id <= NODE_TIME)
		// we are being asked for a system-wide entry (entry_id <= NODE_ID), hence take it from the common repository
		repo_id = COMMON_REPOSITORY_ID;
	else
		// we are being asked for a user-specific entry (entry_id > NODE_ID), hence take it from the specific repository
		repo_id = repo_id_param;

	struct repository *repo = data_mgr_lookup(repo_id);

	if (repo == NULL)
		return;

	/** ok, repository exists (whether it is the common repository or a user-specific one) */

	struct repository_entry *entry = repository_entry_lookup(repo, entry_id);

	/** check if entry exists with different type or data length: */
	if ((entry != NULL)
			&& ((entry->entry_type != entry_type)
					|| (entry->data_len != data_len))) {
		repository_entry_clear(repo, entry_id);
		entry = NULL;
	}

	if (entry == NULL) {
		// entry does not exists, so try to add it
		entry = repository_entry_create(repo, entry_id, entry_type, data_len);
	}

	if (entry == NULL)
		return;

	// ok, entry exists (now). put data into it:
	switch (entry_type) {
	case MANUAL_UPDATE_ENTRY: {
		memcpy(((uint8_t*) entry) + sizeof(struct manual_repository_entry),
				data, data_len);

		break;
	}
	case AUTOMATIC_UPDATE_ENTRY: {
		memcpy(((uint8_t*) entry) + sizeof(struct auto_repository_entry), data,
				data_len);

		break;
	}

	}

	list_add(repo->entry_list, entry);
}

/**
 * \brief		Associates an updater function to a data repository entry
 */
void data_mgr_set_updater( //
		data_repository_id_t id, //
		entry_id_t entry_id, //
		entry_update_function *updater_function) {

	struct repository *repo;
	data_repository_id_t repo_id;
	struct repository_entry *entry;

	// Are we being asked for a system-wide entry (entry_id <= NODE_TIME) or a user-specific one?
	if (entry_id <= NODE_TIME)
		// we are being asked for a system-wide entry (entry_id <= NODE_TIME), hence take it from the common repository
		repo_id = COMMON_REPOSITORY_ID;
	else
		// we are being asked for a user-specific entry (entry_id > NODE_TIME), hence take it from the specific repository
		repo_id = id;

	repo = data_mgr_lookup(repo_id);

	if (repo != NULL) {
		// ok, repository exists (whether it is the common repository or a user-specific one)

		entry = repository_entry_lookup(repo, entry_id);

		//		if (entry == NULL) {
		//			// entry does not exists, so try to add it
		//			entry = repository_entry_create(repo, entry_id);
		//		}

		if ((entry != NULL) && (entry->entry_type == AUTOMATIC_UPDATE_ENTRY)) {
			// ok, entry exists
			struct auto_repository_entry *a_entry =
					(struct auto_repository_entry *) entry;
			a_entry->updater = updater_function;
		}
	}
}

/**
 *  \brief		Returns a pointer to a byte array with the data requested, or NULL if the
 *				specified entry didn't exist. Also the length of the array is returned via the
 * 				by-reference parameter data_len.
 *
 * \param repo_id_param	the id of the repository to get data from
 * \param entry_id		the id of the entry to retrieve
 * \param data_len		an output parameter to specify the length of the returned field
 *
 * \return 				a pointer to the byte-array of the requested field, or NULL if the entry doesn't exist
 * */
uint8_t *
data_mgr_get_data(data_repository_id_t repo_id_param, entry_id_t entry_id,
		data_len_t *data_len) {

	data_repository_id_t repo_id;

	// Are we being asked for a system-wide entry (entry_id <= NODE_TIME) or a user-specific  one?
	if (entry_id <= NODE_TIME)
		// we are being asked for a system-wide entry (entry_id <= NODE_TIME), hence take it from the common repository
		repo_id = COMMON_REPOSITORY_ID;
	else
		// we are being asked for a user-specific entry (entry_id > NODE_TIME), hence take it from the specific repository
		repo_id = repo_id_param;

	struct repository *repo = data_mgr_lookup(repo_id);

	if (repo == NULL)
		return (NULL);

	// ok, repository exists (whether it is the common repository or a user-specific one)
	struct repository_entry *entry = repository_entry_lookup(repo, entry_id);

	PRINTF(3, "getting field %u, entry %p\n", entry_id, entry);

	if (entry == NULL)
		return (NULL);

	// ok, entry exists
	uint8_t *result = NULL;
	switch (entry->entry_type) {
	case AUTOMATIC_UPDATE_ENTRY: {

		struct auto_repository_entry *a_entry =
				(struct auto_repository_entry*) entry;

		// set pointer to data
		uint8_t *value = (uint8_t*) (((uint8_t*) a_entry)
				+ sizeof(struct auto_repository_entry));

		/* calculate age of entry: */
		clock_time_t now = clock_time(), age = now - a_entry->timestamp;

		// since this is an automatically updated entry, check whether the entry is outdated
		// and if there is an updater function, invoke it:
		PRINTF(3, "entry age: %lu, repo ttl: %lu\n", age, repo->ttl);
		if (((age > repo->ttl) && (a_entry->updater != NULL)) || (a_entry->entry_id == NODE_TIME)) {
			// value must be updated
			a_entry->timestamp = now;
			PRINTF(3, "entry old, updating!\n");

			a_entry->updater(value);
		}

		result = (uint8_t*) value;
		break;
	} // case
	case MANUAL_UPDATE_ENTRY: {
		struct manual_repository_entry *m_entry =
				(struct manual_repository_entry*) entry;

		// set pointer to data
		result = ((uint8_t*) m_entry) + sizeof(struct manual_repository_entry);

		break;
	} // case
	} // switch

	*data_len = entry->data_len;
	return (result);

}
/** @} */
