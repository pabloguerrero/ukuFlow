/**
 * \addtogroup routing
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
 * \file	scopes-selfur.h
 * \brief	Header file for the selfur routing protcol of the Scopes framework
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#ifndef __SCOPES_SELFUR_H__
#define __SCOPES_SELFUR_H__

#include "contiki-net.h"
#include "sys/ctimer.h"

#include "scopes-routing.h"

/* rime channels */
/** \brief Channel number for the tree creation/update messages (netflood) */
#define SELFUR_TREE_UPDATE_CHANNEL 129
/** \brief Channel number for the parent activation messages (runicast) */
#define SELFUR_ACTIVATION_CHANNEL 130
/** \brief Channel number for the downstream messages (netflood) */
#define SELFUR_DATA_FLOOD_CHANNEL 131
/** \brief Channel number for the upstream messages (unicast/frag-unicast) */
#define SELFUR_DATA_UNICAST_CHANNEL 132
/** \brief Channel number for the suppression messages (broadcast) */
#define SELFUR_SUPPRESS_CHANNEL 133

/* selfur status definitions */
/** \brief Routing protocol value for clean status */
#define SELFUR_STATUS_NONE 0x0
/** \brief Routing protocol value for member status */
#define SELFUR_STATUS_MEMBER 0x1
/** \brief Routing protocol value for forwarder status */
#define SELFUR_STATUS_FORWARDER 0x2
/** \brief Routing protocol value for activation status */
#define SELFUR_STATUS_ACTIVATE 0x4
/** \brief Routing protocol value for suppression status */
#define SELFUR_STATUS_SUPPRESS 0x8
/** \brief Routing protocol value for scope creator status */
#define SELFUR_STATUS_CREATOR 0x10

/* other definitions */
/** \brief		Time interval within which downstream messages are reforwarded by nodes
 *
 * 				The Scopes selfur protocol uses two flooding channels for sending
 * 				data downstream (i.e. from the root to the members): one for tree
 * 				construction and one for data messages. Nodes forward these messages using
 * 				the ipolite primitive, which takes a time interval as parameter. Nodes
 * 				choose a random point in time within this interval to perform its
 *	 			transmission. This constant specifies exactly this time interval.
 *
 * 				During this time, the message is queued in the channel. Shorter values lead
 * 				to faster message dissemination, but also higher collision rates. Lower values
 * 				are more energy efficient, but decrease throughput.
 **/
#define SELFUR_FLOODING_QUEUE_TIME 50 // CLOCK_SECOND/2
/** \brief		Amount of retries for sending an activation message to the parent node
 *
 * 				Whenever a node joins a routing tree, it informs its parent that it has a
 * 				child. This is signaled with an 'activation' message (via runicast). The parent
 * 				node then replies with a 'suppression' message (via broadcast) so that other
 * 				children don't have to send their activation messages.
 * 				The activation messages are sent via runicast (reliable unicast), which means
 * 				that the sender node waits for an acknowledgment. If no acknowledgment is
 * 				received, the sender node retries a number of times. The maximum number of
 * 				times a node retries is specified with this constant.
 **/
#define SELFUR_ACTIVATION_RETRANSMISSIONS 10

/** \brief		Maximum amount of routing entries that a node can store */
#define SELFUR_MAX_ROUTING_ENTRIES 10

/** \brief		Maximum amount of routing entries that a node can store */
#define SELFUR_MAX_SCOPE_ENTRIES 10

/**
 * \brief		Time to live of a routing entry.
 *
 *  			Routing entries are set this value on creation. If an entry
 *  			doesn't get updated during this interval, it is automatically deleted.
 *  			This constant's value should be (e.g. 3x) higher than the value of
 *  			the constant SELFUR_ROUTING_REFRESH_INTERVAL, so that entries have
 *  			a chance to be updated before they are automatically deleted.
 *
 * 		 		The value is expressed in seconds.
 **/
#define SELFUR_ROUTING_ENTRY_TTL 300

/**
 * \brief		Frequency with which a node updates its routing tree.
 *
 *				Whenever a node needs to create a routing tree (i.e. because it
 *				is requested to open a scope), it resorts to a timer to periodically
 *				rebuild the tree. Nodes that receive the tree creation/update message
 *				update correspondingly their routing entries.
 *
 *				The value is expressed in seconds.
 **/
#define SELFUR_ROUTING_REFRESH_INTERVAL 100

/** \brief		Period length (in seconds) for nodes to wait for checking whether
 * 				it is necessary to send activation msgs.
 **/
#define SELFUR_ACTIVATION_MAX_DELAY 1

/** \brief		TODO */
#define SELFUR_LOCK_TIMER_DURATION 1

/** \brief		TODO */
#define SELFUR_INFO_TIMER_DURATION 10

/** \brief		TODO */
#define SELFUR_HAS_STATUS(r, s)                  \
  ((r)->status & s)

/** \brief		TODO */
#define SELFUR_SET_STATUS(r, s)                  \
  ((r)->status |= s)

/** \brief		TODO */
#define SELFUR_UNSET_STATUS(r, s)                \
  ((r)->status &= ~s)

/** \brief		TODO */
struct routing_entry {
	/** \brief	TODO */
	struct routing_entry *next;
	/** \brief	TODO */
	rimeaddr_t root;
	/** \brief	TODO */
	rimeaddr_t next_hop;
	/** \brief	TODO */
	uint8_t hop_count;
	/** \brief	TODO */
	struct ctimer ttl_timer;
};

/** \brief		TODO */
struct scope_entry {
	/** \brief	TODO */
	struct scope_entry *next;
	/** \brief	TODO */
	scope_id_t scope_id;
	/** \brief	TODO */
	uint8_t status;
	/** \brief	TODO */
	struct routing_entry *tree;
};

/** \brief		TODO */
struct route_activation_msg {
	/** \brief	TODO */
	scope_id_t scope_id;
};

/** \brief		TODO */
struct route_suppress_msg {
	/** \brief	TODO */
	scope_id_t scope_id;
};

/** \brief		TODO */
extern struct scopes_routing scopes_selfur;

#endif // __SCOPES_SELFUR_H__
/** @} */
