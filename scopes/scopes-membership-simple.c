/**
 * \addtogroup scopes
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
 * \file	scopes-membership-simple.c
 * \brief	Simple membership checker using the expression evaluator module
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#include "scopes-membership-simple.h"

#include "net/rime.h"

#include "expression-eval.h"
#include "data-mgr.h"

static data_repository_id_t scopes_repository_id = 0;

/**
 * \brief		Checks this node's membership to a scope
 * \return		the result of the evaluation of the membership expression compared to the node's properties
 */
static bool check(void *specs, data_len_t spec_len) {
	//	LOG("[%u.%u:%10lu] %s::%s() : About to check expression: [",
	//			rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(),
	//			__FILE__, __FUNCTION__);
	//	int i;
	//	for (i = 0; i < spec_len; i++)
	//		LOG("%u,",((uint8_t*)specs)[i]);
	//	LOG("]\n");

	if (scopes_repository_id != 0) {
		expression_eval_set_repository(scopes_repository_id);
		return expression_eval_evaluate(specs, spec_len);
	} else {
		//		LOG("[%u.%u:%10lu] %s::%s() : No data repository to use for checking expression, canceling!\n",
		//				rimeaddr_node_addr.u8[0], rimeaddr_node_addr.u8[1], clock_time(),
		//				__FILE__, __FUNCTION__);
		return FALSE;
	}
}

/**
 * \brief		Initializes the simple scope membership checker module
 */
static void init() {
	scopes_repository_id = data_mgr_create(CLOCK_SECOND * 5);
}

/**
 * \brief		Declares this membership function
 */
MEMBERSHIP(simple_membership, "simple membership", init, check);

/** @} */
