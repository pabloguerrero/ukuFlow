/**
 * \addtogroup scopes Scopes
 * @{
 */

/*
 * Copyright (c) 2009, Steffen Kilb, TU Darmstadt
 * Copyright (c) 2009, Daniel Jacobi, TU Darmstadt
 * Copyright (c) 2012, Pablo Guerrero, TU Darmstadt, guerrero@dvs.tu-darmstadt.de
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
 * \file	scopes.h
 * \brief	Header file for the scopes module
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#ifndef __SCOPES_H__
#define __SCOPES_H__

#include "scopes-types.h"
#include "scopes-routing.h"
#include "scopes-membership.h"
#include "scopes-application.h"

/* preprocessor macros */
/** \brief Checks whether a given scope is the world scope or not */
#define IS_WORLD_SCOPE(scope)                   \
  ((scope) == &world_scope)

/** \brief Checks a given scope for a specific status */
#define HAS_STATUS(scope, s)                    \
  ((scope)->status & s)

/** \brief Checks a given scope for a specific flag */
#define HAS_FLAG(scope, flag)                   \
  ((scope)->flags & flag)

/** \brief Looks up a scope out of an id */
#define SCOPE(scope_id)                         \
  (scopes_lookup(scope_id))

/** \brief Checks whether an application owns a scope or not */
#define IS_OWNER(scope, subscriber)             \
  ((scope)->owner->id == (subscriber)->id)

/** \brief Checks whether an application is subscribed in this node or not */
#define IS_SUBSCRIBED(subscriber)               \
  ((subscriber)->status == SCOPES_APPLICATION_SUBSCRIBED)

/* preprocessor constants */
/** \brief Constant specifying the maximum number of parallel scopes */
#define SCOPES_MAX_SCOPES 4
/** \brief Constant specifying the maximum number of parallel subscribers */
#define SCOPES_MAX_SUBSCRIBER 2

/** \brief Constant specifying the frequency (in seconds) with which dynamic scopes will be checked */
#define SCOPES_TIMER_DURATION 20 //2

/** \brief Constant specifying the factor for re announcements of scopes (compared to a scope's TTL)*/
#define SCOPES_REANNOUNCE_FACTOR 0.3

/** \brief Constant specifying that an application is not subscribed */
#define SCOPES_APPLICATION_UNSUBSCRIBED 0
/** \brief Constant specifying that an application is subscribed */
#define SCOPES_APPLICATION_SUBSCRIBED 1

/** \brief Constant specifying a flag for indicating that this node has no status yet for a given scope */
#define SCOPES_FLAG_STATUS_NONE 0x0
/** \brief Constant specifying a flag for indicating that this node is member of a given scope */
#define SCOPES_FLAG_STATUS_MEMBER 0x1
/** \brief Constant specifying a flag for indicating that this node is creator of a given scope */
#define SCOPES_FLAG_STATUS_CREATOR 0x2

/* Scope flags */
/** \brief Constant specifying a flag for indicating that a scope doesn't have special characteristics */
#define SCOPES_FLAG_NONE 0x0
/** \brief Constant specifying a flag for indicating that a scope must be dynamically reevaluated even at non-member nodes */
#define SCOPES_FLAG_DYNAMIC 0x1
/** \brief Constant specifying a flag for indicating that a scope should intercept traffic (potentially also at non-member nodes)*/
#define SCOPES_FLAG_INTERCEPT 0x2
//#define SCOPES_FLAG_OTHER_1 4
//#define SCOPES_FLAG_OTHER_2 8

/** \brief Constant specifying the message type indicating request to open a scope */
#define SCOPES_MSG_OPEN 0
/** \brief Constant specifying the message type indicating request to send data through a scope */
#define SCOPES_MSG_DATA 1
/** \brief Constant specifying the message type indicating request to close a scope */
#define SCOPES_MSG_CLOSE 2 

/* storage types */
/** \brief	Structure for storing information about a subscribed application */
struct scopes_subscriber {
	/** \brief Pointer to next subscriber*/
	struct scopes_subscriber *next;
	/** \brief ID of the subscriber */
	subscriber_id_t id;
	/** \brief Status of the application (subscribed/not subscribed) */
	subscriber_status_t status;
	/** \brief Pointer to application callbacks */
	struct scopes_application *application;
	/** \brief Process running the application */
	struct process *process;
};

/** \brief	Structure for storing information about a scope */
struct scope {
	/** \brief Pointer to next scope */
	struct scope *next;
	/** \brief Scope id */
	scope_id_t scope_id;
	/** \brief Scope id of parent scope */
	scope_id_t super_scope_id;
	/** \brief Subscribed application that owns (uses) this scope */
	struct scopes_subscriber *owner;
	/** \brief Time to live of the scope */
	scope_ttl_t ttl;
	/** \brief Callback timer for invoking the handling function when the ttl expires */
	struct ctimer ttl_timer;
	/** \brief Status of this scope */
	scope_status_t status;
	/** \brief Flags of this scope */
	scope_flags_t flags;
	/** \brief Usage counter (in case the application opens it more than once) */
	uint8_t use_counter;
	/** \brief Length of the specification */
	data_len_t spec_len;
	/** \brief Pointer to specification of the scope */
	void *specs;
};


#endif
/** @} */
