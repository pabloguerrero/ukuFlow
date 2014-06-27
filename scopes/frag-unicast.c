/**
 * \addtogroup fragmentation
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
 * \file	frag-unicast.c
 * \brief	Fragmentation-enabled unicast
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date Jul 1, 2011
 */

#include "net/rime.h"

// for memcpy
#include "string.h"

#include "frag-unicast.h"
#include "bitvector.h"
#include "logger.h"

/** \brief Returns the minimum of two values */
#define MIN(a,b) ((a)>(b)?(b):(a))

/** \brief		Defines the level with which the main messages of this module are logged	*/
#define FRAG_UNICAST_NORMAL_DEBUG_LEVEL	5

/** \brief		Defines the level with which error messages of this module are logged		*/
#define FRAG_UNICAST_ERROR_DEBUG_LEVEL	6

/*---------------------------------------------------------------------------*/
static void drop_fragments();

/**
 * \brief		Handles a received message by potentially merging it to other, previously received fragments.
 *
 * 				This function is invoked when receiving a message from the unicast layer.
 * 				The message, treated as a fragment, is put at the correct position in a buffer.
 * 				If the whole packet (made of segments) is completed, the upper layer is notified.
 *
 * \param unicast the Rime unicast connection
 * \param from the Rime address from which the message came via unicast
 */
static void recv_from_unicast(struct unicast_conn *unicast,
		const rimeaddr_t *from) {

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast from %u.%u, datalen %u\n",
			from->u8[0], from->u8[1], packetbuf_datalen());

	uint8_t data[packetbuf_datalen()];
	uint16_t payload_len = packetbuf_copyto(data) - sizeof(struct fragment_hdr);

	struct fragment_hdr *hdr = (struct fragment_hdr *) data;
	uint8_t *payload = data + sizeof(struct fragment_hdr);

	struct frag_unicast_conn *fu_conn = (struct frag_unicast_conn *) unicast;

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast : dataptr's len: %u, contents: ",
			packetbuf_datalen());
	PRINT_ARR(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, data, payload_len);

	uint8_t received_packet_id = hdr->packet_id;
	uint8_t fragment_nr = hdr->fragment_nr;
	uint16_t buffer_len = hdr->buffer_len;
	uint8_t num_fragments = (buffer_len + FRAGMENT_SIZE - 1) / FRAGMENT_SIZE;
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,
			"(FRAG-UC) recv_from_unicast from %u.%u, last_received_packet_id: %u, received_packet_id: %u, fragment_nr: %u, num_fragments: %u\n",
			from->u8[0], from->u8[1], fu_conn->last_received_packet_id,
			received_packet_id, fragment_nr, num_fragments);

#if DEBUG_DEPTH>=FRAG_UNICAST_NORMAL_DEBUG_LEVEL
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,"(FRAG-UC) recv_from_unicast : Status is [");
	switch (fu_conn->status) {
		case (IDLE): {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,"IDLE");
			break;
		}
		case (SENDING): {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,"SENDING");
			break;
		}
		case (RECEIVING): {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,"RECEIVING");
			break;
		}
	}
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,"]\n");
#endif

	if ((fu_conn->status == IDLE) || // either this node was idle, or
			((fu_conn->status == RECEIVING) && //
					(rimeaddr_cmp(&fu_conn->from, from)) && //
					(received_packet_id == fu_conn->last_received_packet_id))) { // it was already receiving from that sender node

		fu_conn->status = RECEIVING;

		PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast, status is correct\n");

		if (fragment_nr == 0) {

			rimeaddr_copy(&fu_conn->from, from);

			bitvector_clear(fu_conn->bitvector_received_fragments,
			FRAGMENT_BITVECTOR_SIZE);

			fu_conn->last_received_packet_id = received_packet_id;
			fu_conn->buffer_len = buffer_len;
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast, setting timer\n");
			ctimer_set(&fu_conn->reassembly_timer, REASSEMBLY_TIMEOUT,
					drop_fragments, fu_conn);
		} else {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast, resetting timer\n");
			ctimer_restart(&fu_conn->reassembly_timer);
		}

		bitvector_set(fu_conn->bitvector_received_fragments, fragment_nr);

		/* Move pointer to correct position */
		uint8_t *buffer_ptr = ((uint8_t*) fu_conn->buffer)
				+ fragment_nr * FRAGMENT_SIZE;

		memcpy(buffer_ptr, payload, payload_len);

		PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) after fragment %u, added %u bytes: ", fragment_nr,
				payload_len);
		PRINT_ARR(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, frag_unicast_buffer_ptr(fu_conn),
				fragment_nr * FRAGMENT_SIZE + payload_len);

		if (bitvector_all_set(fu_conn->bitvector_received_fragments,
				num_fragments)) {
			ctimer_stop(&fu_conn->reassembly_timer);
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast, stopping timer\n");

			fu_conn->status = IDLE;
			if (fu_conn->u->recv != NULL)
				fu_conn->u->recv(fu_conn, from);
		}

	} else {
		if (fu_conn->status == SENDING) {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,
					"(FRAG-UC) recv_from_unicast, node was already sending, packet discarded!\n");
		} else if (!rimeaddr_cmp(&fu_conn->from, from)) {
			PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,
					"(FRAG-UC) recv_from_unicast, node was already receiving fragments from node %u.%u, packet discarded!\n",
					fu_conn->from.u8[0], fu_conn->from.u8[1]);
			return;

		}

	}

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) recv_from_unicast, end\n");

}

/**
 * \brief		Data structure pointing to single function used by the fragmentation-unicast layer
 */
static const struct unicast_callbacks unicast_cbs = { recv_from_unicast };

/**
 * \brief		Opens a fragmentation unicast connection
 */
void frag_unicast_open(struct frag_unicast_conn *fu_conn, uint16_t channel,
		const struct frag_unicast_callbacks *u) {

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) Size of bitvector_received_fragments is %u\n",
			sizeof(fu_conn->bitvector_received_fragments));

	unicast_open(&fu_conn->c, channel, &unicast_cbs);
	fu_conn->last_sent_packet_id = 0;
	fu_conn->status = IDLE;
	fu_conn->u = u;

}

/**
 * \brief		Closes a fragmentation unicast connection
 */
void frag_unicast_close(struct frag_unicast_conn *fu_conn) {
	unicast_close(&fu_conn->c);
}

/**
 * \brief		Sends data through a fragmentation unicast connection
 **/
int frag_unicast_send(struct frag_unicast_conn *fu_conn,
		const rimeaddr_t *receiver) {

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) frag_unicast_send, invoked with receiver %u.%u\n",
			receiver->u8[0], receiver->u8[1]);

	/* Check if connection is idle */
	if (fu_conn->status != IDLE)
		return (FRAG_CONNECTION_BUSY);

	/* Check if packet fits in buffer */
	if (fu_conn->buffer_len > FRAGMENTATION_BUFFER_SIZE)
		return (FRAG_MSG_TOO_LONG);

	/* Set state to sending */
	fu_conn->status = SENDING;

	uint16_t remaining_len = fu_conn->buffer_len;
	uint8_t *current_buffer_fragment = fu_conn->buffer;

	fu_conn->last_sent_packet_id++;
	int result = 1;

	/* Calculate how many fragments are necessary.
	 * E.g. assuming FRAGMENT_SIZE = 3:
	 * buffer_len:	num_fragments:
	 * 1				1
	 * 2				1
	 * 3				1
	 * 4				2
	 * 5				2
	 * 6				2
	 * 7				3
	 * ...			...
	 * */
	uint8_t num_fragments = (fu_conn->buffer_len + FRAGMENT_SIZE - 1)
			/ FRAGMENT_SIZE;

	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,
			"(FRAG-UC) frag_unicast_send, number of fragments to be sent: %u\n",
			num_fragments);

	uint8_t fragment_nr;
	for (fragment_nr = 0; fragment_nr < num_fragments; fragment_nr++) {

		uint8_t min = MIN(remaining_len, FRAGMENT_SIZE);

		uint8_t fragment[sizeof(struct fragment_hdr) + min];

		/* Prepare header */
		struct fragment_hdr *hdr = (struct fragment_hdr *) fragment;
		hdr->packet_id = fu_conn->last_sent_packet_id;
		hdr->fragment_nr = fragment_nr;
		hdr->buffer_len = fu_conn->buffer_len;

		/* Set packetbuf data length: */
		//		packetbuf_set_datalen(sizeof(struct fragment_hdr)
		//				+ MIN(remaining_len, FRAGMENT_SIZE));
		/* Prepare payload */
		uint8_t *payload = ((uint8_t *) fragment) + sizeof(struct fragment_hdr);
		memcpy(payload, current_buffer_fragment, min);

		PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL,
				"(FRAG-UC) frag_unicast_send, sending with last_sent_packet_id %u, fragment nr %u, num_fragments %u, to %u.%u, len will be %u\n",
				fu_conn->last_sent_packet_id, fragment_nr, num_fragments,
				receiver->u8[0], receiver->u8[1], min);

		packetbuf_copyfrom(fragment, sizeof(fragment));

		PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) frag_unicast_send, dataptr's len: %u, contents: ",
				packetbuf_datalen());
		PRINT_ARR(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, packetbuf_dataptr(), packetbuf_datalen());

		result &= unicast_send(&fu_conn->c, receiver);

		/* advance pointer to data */
		current_buffer_fragment += min;

		/* adjust remaining length */
		remaining_len -= min;

		PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) frag_unicast_send, result till now is %u\n",
				result);
	}

	/* Set state back to idle */
	fu_conn->status = IDLE;

	if (result)
		return (FRAG_SEND_OK);
	else
		return (FRAG_SEND_ERROR);
}
/**
 * \brief		Clears up the connection by resetting its values and clearing the bitvector
 *
 * \param arg	the connection which should be drop
 */
static void drop_fragments(void *arg) {

	PRINTF(FRAG_UNICAST_ERROR_DEBUG_LEVEL,
			"(FRAG-UC) drop_fragments, dropping fragments due to expired timer!\n");

	struct frag_unicast_conn *fu_conn = (struct frag_unicast_conn *) arg;

	fu_conn->buffer_len = 0;
	rimeaddr_copy(&fu_conn->from, &rimeaddr_null);
	bitvector_clear(fu_conn->bitvector_received_fragments,
	FRAGMENT_BITVECTOR_SIZE);
	fu_conn->status = IDLE;

}

/**
 * \brief		Returns a pointer to the buffer used by this fragmentation unicast connection
 */
uint8_t *frag_unicast_buffer_ptr(struct frag_unicast_conn *fu_conn) {
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) returning pointer to fragmentation buffer\n");
	return (fu_conn->buffer);
}

/**
 * \brief		Returns the length of the buffer used by this fragmentation unicast connection
 */
uint16_t frag_unicast_buffer_getlen(struct frag_unicast_conn *fu_conn) {
	return (fu_conn->buffer_len);
}

/**
 * \brief		Sets the length of the buffer used by this fragmentation unicast connection
 */
void frag_unicast_buffer_setlen(struct frag_unicast_conn *fu_conn, uint16_t len) {
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) frag_unicast_buffer_setlen() : setting length to %u\n",
			len);
	fu_conn->buffer_len = len;
}

/**
 * \brief		Clears the buffer used by this fragmentation unicast connection
 */
void frag_unicast_buffer_clear(struct frag_unicast_conn *fu_conn) {
	PRINTF(FRAG_UNICAST_NORMAL_DEBUG_LEVEL, "(FRAG-UC) clearing buffer\n");
	uint16_t i;
	for (i = 0; i < FRAGMENTATION_BUFFER_SIZE; i++)
		fu_conn->buffer[i] = 0;
	fu_conn->buffer_len = 0;
}

/** @} */
