/**
 * \addtogroup scopes
 * @{
 */

/*
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
 * \file	scopes-application.h
 * \brief	Header file for scopes applications to use
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2010
 */

#ifndef __SCOPES_APPLICATION_H__
#define __SCOPES_APPLICATION_H__

#include "scopes-types.h"

/** \brief		Data structure for callback functions used by Scopes
 *
 * 				Any application that wants to use the Scopes framework needs to
 * 				implement this provide one such structure with the respective
 * 				pointers to callback functions. These can also be NULL if desired.
 */
struct scopes_application {
	/** \brief Callback invoked when a node adds a scope to its table */
	void (* add)(scope_id_t scope_id);
	/** \brief Callback invoked when a node removes a scope from its table */
	void (* remove)(scope_id_t scope_id);
	/** \brief Callback invoked when a node joins a scope */
	void (* join)(scope_id_t scope_id);
	/** \brief Callback invoked when a node leaves a scope */
	void (* leave)(scope_id_t scope_id);
	/** \brief Callback used when a node receives data via a scope */
	void (* recv)(scope_id_t scope_id, void *data, data_len_t data_len,
			bool to_creator, const rimeaddr_t *source);
};

/* external function declarations */
extern void scopes_init(struct scopes_routing *routing,
		struct scopes_membership *membership);

extern void scopes_register(subscriber_id_t id,
		struct scopes_application *application);

extern void scopes_unregister(subscriber_id_t subscriber_id);

extern bool scopes_open(subscriber_id_t subscriber_id, scope_id_t super_scope,
		scope_id_t scope_id, void *specs, data_len_t spec_len,
		scope_flags_t flags, scope_ttl_t ttl);

extern void scopes_close(subscriber_id_t subscriber_id, scope_id_t scope_id);

extern void scopes_send(subscriber_id_t subscriber_id, scope_id_t scope_id,
		bool to_creator, void *data, data_len_t data_len);

extern bool scopes_member_of(scope_id_t scope_id);

extern bool scopes_creator_of(scope_id_t scope_id);

#endif
/** @} */
