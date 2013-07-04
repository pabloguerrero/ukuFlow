/**
 * \addtogroup Tools
 * @{
 */

/*
 * Copyright (c) 2010, Pablo Guerrero, TU Darmstadt, guerrero@dvs.tu-darmstadt.de
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
 * \file	logger.h
 * \brief	Header file for logger functions
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date 	Aug 29, 2011
 */

#ifndef __LOGGER_H__
#define __LOGGER_H__

#ifndef DEBUG_DEPTH
/**
 * \brief		Defines the debug log depth.
 * 				Only debug messages requested with a depth lower
 * 				than or equal to it will be considered
 */
#define DEBUG_DEPTH 1
#endif

#if DEBUG_DEPTH
#include <stdio.h>
/**
 * \brief		printf macro which considers the debug depth
 */
#define PRINTF(x, ...) if (x<=DEBUG_DEPTH) printf(__VA_ARGS__)
/**
 * \brief		printf macro for outputting an entire array
 */
#define PRINT_ARR(x, data, length)				\
	uint8_t *data2 = (uint8_t *)data;			\
	PRINTF(x,"[");								\
	int XX = 0;									\
	for (XX = 0; XX < length; XX++)				\
		PRINTF(x,"%u,",data2[XX]);				\
	PRINTF(x,"]\n")
#else
#define PRINTF(x, ...)
#define PRINT_ARR(x, data, length)
#endif


#endif /* __LOGGER_H__ */
/** @} */
