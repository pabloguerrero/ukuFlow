/*
 * ukuflow-util.h
 *
 *  Created on: Apr 14, 2011
 *      Author: guerrero
 */

#ifndef _UKUFLOW_UTIL_H_
#define _UKUFLOW_UTIL_H_

#include "contiki.h"
#include "contiki-net.h"

#define DEBUG						3

#if DEBUG
#include <stdio.h>
#define PRINTF(x, ...) 				if (x<=DEBUG) printf(__VA_ARGS__)
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
