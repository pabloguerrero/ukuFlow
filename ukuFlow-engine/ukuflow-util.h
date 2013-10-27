/**
 * \addtogroup engine
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
 * \file	ukuflow-util.h
 * \brief	Header file with the ukuFlow utilities
 *
 * \details	TODO
 *
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	Apr 14, 2011
 */

#ifndef _UKUFLOW_UTIL_H_
#define _UKUFLOW_UTIL_H_

#include "contiki.h"
#include "contiki-net.h"

/** \brief		Defines a debug level depth				*/
#define DEBUG						3

#if DEBUG
#include <stdio.h>
/** \brief		Macro to output a debug message				*/
#define PRINTF(x, ...) 				if (x<=DEBUG) printf(__VA_ARGS__)
/** \brief		Macro to output the contents of a memory section				*/
#define PRINT_ARR(x, data, length)	\
									uint8_t *data2 = data;						\
									PRINTF(x,"[");								\
									int XX = 0;									\
									for (XX = 0; XX < length; XX++)				\
										PRINTF(x,"%u,",data2[XX]);				\
									PRINTF(x,"]\n")
#else
#define PRINTF(x, ...)
#define PRINT_ARR(x, data, length)
#endif


#endif /* _UKUFLOW_UTIL_H_ */
/** @} */
