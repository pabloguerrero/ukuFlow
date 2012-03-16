/**
 * \defgroup datamanager Data Manager
 * \brief Data Manager files
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
 * \file	data-mgr.h
 * \brief	Header file for the data manager module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 */

#ifndef __DATA_REPOSITORY_H__
#define __DATA_REPOSITORY_H__

#include "contiki.h"

#include "lib/list.h"

/**  \brief Constant specifying the maximum amount of repositories that a node can hold */
#define MAX_REPOSITORIES 20
/**  \brief Constant specifying the maximum amount of entries in a single repository */
#define MAX_REPOSITORY_SIZE 20
/**  \brief Constant specifying the maximum amount of entries, across all repositories of a node */
#define TOTAL_REPOSITORY_ENTRIES 50 // MAX_REPOSITORIES*MAX_REPOSITORY_SIZE
/**  \brief Constant specifying the id of the common repository */
#define COMMON_REPOSITORY_ID 1

/**  \brief Type definition for updater functions */
typedef uint16_t ( entry_update_function)(void);

/**  \brief Type definition for data repository ids */
typedef uint8_t data_repository_id_t;

/**  \brief Type definition for data length */
typedef uint8_t data_len_t;

/**  \brief Type definition for data repository entry ids */
typedef uint8_t entry_id_t;

/**  \brief Boolean data type */
typedef uint8_t bool;

/**  \brief Constant for a boolean true */
#define TRUE 1
/**  \brief Constant for a boolean false */
#define FALSE 0

/**  \brief Data structure for repositories */
struct repository {
	/**  \brief Pointer to next element in list */
	struct repository *next;
	/**  \brief Id of this repository */
	data_repository_id_t id;
	/**  \brief General time to live value to use with this repository */
	clock_time_t ttl;
	/**  \brief List of data entries */
	LIST_STRUCT( entry_list);
};

/**  \brief Type definition for entry types */
typedef uint8_t entry_type_t;
/**  \brief Enumeration for entry types */
enum entry_type {
	MANUAL_UPDATE_ENTRY = 1, //
	AUTOMATIC_UPDATE_ENTRY,
//
};


/**  \brief TODO */
struct repository_entry {
	/**  \brief Pointer to next repository entry in the list */
	struct repository_entry *next;
	/**  \brief Type of the repository entry */
	entry_type_t entry_type;
	/**  \brief Id of the repository entry */
	entry_id_t entry_id;
	/**  \brief Length of the data array */
	data_len_t data_len;
};

/**  \brief TODO */
struct manual_repository_entry {
	/**  \brief Pointer to next repository entry in the list */
	struct repository_entry *next;
	/**  \brief Type of the repository entry */
	entry_type_t entry_type;
	/**  \brief Id of the repository entry */
	entry_id_t entry_id;
	/**  \brief Length of the data array */
	data_len_t data_len;
	// followed by data array
};

/**  \brief TODO */
struct auto_repository_entry {
	/**  \brief Pointer to next repository entry in the list */
	struct repository_entry *next;
	/**  \brief Type of the repository entry */
	entry_type_t entry_type;
	/**  \brief Id of the repository entry */
	entry_id_t entry_id;
	/**  \brief Length of the data array */
	data_len_t data_len;
	/**  \brief Timestamp for last update of the entry */
	clock_time_t timestamp;
	/**  \brief Pointer to updater function for this repository entry */
	entry_update_function *updater;
	// followed by data array
};

/** \brief Ids of the fields for the common repository */
enum repository_fields {
	SENSOR_LIGHT_PAR_RAW = 0, /*			 0*/
	SENSOR_LIGHT_TSR_RAW, /*				 1*/
	SENSOR_TEMPERATURE_RAW, /*				 2*/
	SENSOR_TEMPERATURE_CELSIUS, /*			 3*/
	SENSOR_TEMPERATURE_FAHRENHEIT, /*		 4*/
	SENSOR_HUMIDITY_RAW, /*					 5*/
	SENSOR_HUMIDITY_PERCENT, /*				 6*/
	SENSOR_VOLTAGE_RAW, /*					 7*/
	NODE_ID,
/**							 8*/
};

data_repository_id_t data_mgr_create(clock_time_t max_ttl);
bool data_mgr_remove(data_repository_id_t id);

void data_mgr_set_data(data_repository_id_t repo_id_param,
		entry_id_t entry_id, entry_type_t entry_type, data_len_t data_len,
		uint8_t *data);
void data_mgr_set_updater(data_repository_id_t id, entry_id_t entry_id,
		entry_update_function *updater_function);
uint8_t *data_mgr_get_data(data_repository_id_t repo_id_param,
		entry_id_t entry_id, data_len_t *data_len);

#endif //__DATA_REPOSITORY_H__
/** @} */
