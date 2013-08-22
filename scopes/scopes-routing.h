/**
 * \defgroup routing Scopes Routing
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
 * \file	scopes-routing.h
 * \brief	Header file for the routing subsystem of the Scopes framework
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#ifndef __SCOPES_ROUTING_H__
#define __SCOPES_ROUTING_H__

#include "net/rime.h"
#include "scopes-types.h"

/**
 * \brief		Data structure for modules implementing the scopes routing API
 */
struct scopes_routing {
	/** \brief Name of the routing protocol */
	const char *name;
	/** \brief Initialization function */
	void (*init)(void);
	/** \brief Data transmission function */
	void (*send)(scope_id_t scope_id, bool to_creator);
	/** \brief Scope addition function */
	void (*add)(scope_id_t scope, bool is_creator);
	/** \brief Scope removal function */
	void (*remove)(scope_id_t scope);
	/** \brief Scope join function */
	void (*join)(scope_id_t scope);
	/** \brief Scope leave function */
	void (*leave)(scope_id_t scope);
	/** \brief Buffer clearing function */
	void (*buffer_clear)(bool to_creator);
	/** \brief Buffer access function */
	uint8_t* (*buffer_ptr)(bool to_creator);
	/** \brief Buffer length set function */
	void (*buffer_setlen)(bool to_creator, uint16_t len);
	/** \brief Scope status checking function */
	bool (*has_status)(scope_id_t scope_id, uint8_t status);
	/** \brief Scope status checking function */
	uint8_t (*node_distance)(scope_id_t scope_id);
};

/** \brief Macro for specifying a routing protocol */
#define ROUTING(  /**/ \
				name, /**/ \
				strname, /**/ \
				init, /**/ \
				send, /**/ \
				add, /**/ \
				remove, /**/ \
				join, /**/ \
				leave, /**/ \
				buffer_clear, /**/ \
				buffer_ptr, /**/ \
				buffer_setlen, /**/ \
				has_status, /**/ \
				node_distance) /**/ \
struct scopes_routing name = { /**/ \
		strname, /**/ \
		init, /**/ \
		send, /**/ \
		add, /**/ \
		remove, /**/ \
		join, /**/ \
		leave, /**/ \
		buffer_clear, /**/ \
		buffer_ptr, /**/ \
		buffer_setlen, /**/ \
		has_status, /**/ \
		node_distance }

/** \brief Constant specifying the scope id of the world scope (the universe) */
#define SCOPES_WORLD_SCOPE_ID 255

/**
 * \brief		Data structure for all scope messages
 */
struct scopes_msg_generic {
	/** \brief Scope id that this message refers to */
	scope_id_t scope_id;
	/** \brief Type of scope message */
	msg_type_t type;
};

extern void scopes_receive(struct scopes_msg_generic *gmsg);

#endif // __SCOPES_ROUTING_H__
/** @} */
