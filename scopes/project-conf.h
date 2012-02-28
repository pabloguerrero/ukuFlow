/**
 * \addtogroup scopes
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
 * \file	project-conf.h
 * \brief	Configuration of the Scopes project
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date	2011
 */

//
#ifdef NETSTACK_CONF_NETWORK
#undef NETSTACK_CONF_NETWORK
#endif
#define NETSTACK_CONF_NETWORK					rime_driver

// Radio Duty Cycling (RDC) channel check rate
#ifdef NETSTACK_CONF_RDC_CHANNEL_CHECK_RATE
#undef NETSTACK_CONF_RDC_CHANNEL_CHECK_RATE
#endif
#define NETSTACK_CONF_RDC_CHANNEL_CHECK_RATE	128
//#define NETSTACK_CONF_RDC_CHANNEL_CHECK_RATE	16

// radio duty cycling (RDC) driver: (nullrdc_driver | contikimac_driver |
#ifdef NETSTACK_CONF_RDC
#undef NETSTACK_CONF_RDC
#endif
#define NETSTACK_CONF_RDC						nullrdc_driver
//#define NETSTACK_CONF_RDC						contikimac_driver


// MAC driver: (nullmac_driver | csma_driver )
#ifdef NETSTACK_CONF_MAC
#undef NETSTACK_CONF_MAC
#endif
#define NETSTACK_CONF_MAC						nullmac_driver
//#define NETSTACK_CONF_MAC						csma_driver

// MAC framer, responsible for constructing and parsing the header in MAC frames (framer_nullmac | framer_802154 )
// Note 2011/07/27: With framer_nullmac, messages towards the root node with frag_unicast are NOT sent at all!!
#ifdef NETSTACK_CONF_FRAMER
#undef NETSTACK_CONF_FRAMER
#endif
//#define NETSTACK_CONF_FRAMER					framer_nullmac
#define NETSTACK_CONF_FRAMER					framer_802154


/** \brief Enables or disables scopes fragmentation */
#define FRAGMENTATION_ENABLED
/** \brief Enables or disables scopes data information */
#define SCOPES_NOINFO

/** @} */
