/**
 * \defgroup fragmentation Fragmentation-enabled Unicast
 * @{
 */

/*
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
 * \file	frag-unicast.h
 * \brief	Header file for the fragmentation-enabled unicast module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Jul 1, 2011
 *
 * \section channels Channels
 *
 * The frag_unicast module uses 1 channel.
 *
 */

#ifndef __FRAG_UNICAST_H__
#define __FRAG_UNICAST_H__

#include "net/rime.h"
#include "net/rime/unicast.h"
#include "dev/cc2420.h"

/** \brief Defines the maximum number of fragments that this connection can use to fragment a large packet */
#define MAX_FRAGMENTS 8

/** \brief Defines the size for the bitvector which checks received fragments */
#define FRAGMENT_BITVECTOR_SIZE ((MAX_FRAGMENTS + 7) / 8)

/** \brief Defines the size of a fragment, in bytes */
#define FRAGMENT_SIZE (CC2420_MAX_PACKET_LEN - 27)
/** \brief Defines the size of the buffer to be kept in local memory. It is the result of FRAGMENT_SIZE x MAX_FRAGMENTS */
#define FRAGMENTATION_BUFFER_SIZE FRAGMENT_SIZE * MAX_FRAGMENTS

/** \brief Defines the timeout for reassembling a packet */
#define REASSEMBLY_TIMEOUT (5 * CLOCK_SECOND)

struct frag_unicast_conn;

/**
 * \brief		Structure defining the header data for individual fragment packets
 */
struct fragment_hdr {
	/** \brief id of the packet */
	uint8_t packet_id;
	/** \brief fragment number of the the packet */
	uint8_t fragment_nr;
	/** \brief length of the buffer */
	uint16_t buffer_len;
};

/**
 * \brief		Callback functions for the fragmentation unicast connection
 */
struct frag_unicast_callbacks {
	/** \brief Callback function invoked when a fully, correctly reassembled packet arrives */
	void (* recv)(struct frag_unicast_conn *c, const rimeaddr_t *from);
};

/**
 * \brief		Enumeration for the status of a fragmentation unicast connection
 */
enum frag_unicast_status {
	IDLE, //
	SENDING, //
	RECEIVING
};

/**
 * \brief		Error types from a fragmentation unicast transmission
 */
enum frag_error_msgs {
	/** \brief Used when the transmission was OK. */
	FRAG_SEND_OK,

	/** \brief Used when the transmission had a problem sending one or more of the fragments. */
	FRAG_SEND_ERROR,

	/** \brief Used when the specified packet length was too long for this connection's buffer. */
	FRAG_MSG_TOO_LONG,

	/** \brief Used when the fragmentation unicast connection was busy, either sending or receiving. */
	FRAG_CONNECTION_BUSY
};

/**
 * \brief		Structure containing the elements necessary for a fragmentation unicast connection
 */
struct frag_unicast_conn {
	/** \brief TODO */
	struct unicast_conn c;
	/** \brief TODO */
	const struct frag_unicast_callbacks *u;
	/** \brief TODO */
	uint8_t buffer[FRAGMENTATION_BUFFER_SIZE];
	/** \brief TODO */
	uint16_t buffer_len;
	/** \brief TODO */
	uint8_t last_received_packet_id;
	/** \brief TODO */
	uint8_t last_sent_packet_id;
	/** \brief TODO */
	rimeaddr_t from;
	/** \brief TODO */
	uint8_t bitvector_received_fragments[FRAGMENT_BITVECTOR_SIZE];
	/** \brief TODO */
	enum frag_unicast_status status;
	/** \brief TODO */
	struct ctimer reassembly_timer;
};

void frag_unicast_open(struct frag_unicast_conn *c, uint16_t channel,
		const struct frag_unicast_callbacks *u);
void frag_unicast_close(struct frag_unicast_conn *c);
int frag_unicast_send(struct frag_unicast_conn *c, const rimeaddr_t *receiver);
uint8_t *frag_unicast_buffer_ptr(struct frag_unicast_conn *c);
uint16_t frag_unicast_buffer_getlen(struct frag_unicast_conn *c);
void frag_unicast_buffer_setlen(struct frag_unicast_conn *c, uint16_t len);
void frag_unicast_buffer_clear(struct frag_unicast_conn *c);

#endif /* __FRAG_UNICAST_H__ */

/** @} */
