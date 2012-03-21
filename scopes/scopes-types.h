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
 * \file	scopes-types.h
 * \brief	Header file for the data types of the Scopes framework
 * \author	Steffen Kilb
 * \author	Daniel Jacobi
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2009
 */

#ifndef __SCOPES_TYPES_H__
#define __SCOPES_TYPES_H__

#include "contiki.h"
#include "data-mgr.h"

/* type definitions */
/** \brief Id of a scope */
typedef uint8_t scope_id_t;
/** \brief Id of a subscriber */
typedef uint8_t subscriber_id_t;
/** \brief Time to live of a scope */
typedef uint16_t scope_ttl_t;
/** \brief Flags of a scope */
typedef uint8_t scope_flags_t;
/** \brief Status of a scope */
typedef uint8_t scope_status_t;
/** \brief Type of a scope's message */
typedef uint8_t msg_type_t;
/** \brief Direction of a scope's message (either towards the root or not) */
typedef uint8_t msg_direction_t;
/** \brief Status of a subscriber */
typedef uint8_t subscriber_status_t;

#endif
/** @} */
