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
 * \file	base64.c
 * \brief	Base64 decoder module.
 *
 * 			For more info visit: http://en.wikipedia.org/wiki/Base64
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \author	Adam Dunkels <adam@sics.se>
 * \date 	Aug 29, 2010
 */

#include "base64.h"

// for strlen
#include "string.h"
#include "logger.h"
#include <ctype.h> // for isspace()
/**
 *
 * \brief Structure definition for the decoder
 **/
struct base64_decoder_state {
	/** \brief Pointer to the data to decode */
	int dataptr;
	/** \brief Placeholder for decode the input*/
	unsigned long tmpdata;
	/** \brief Pointer to the data to decode */
	int sextets;
	/** \brief Pointer to the data to decode */
	int padding;
} /** \brief Struct variable for the decoder*/s;

/*-----------------------------------------------------------------------------------*/
/** 
 * \brief		Helper function to decode a single character using the Base64 index table.
 *
 * \internal
 * \param		c the char to decode
 * \return		the decoded char
 */
static int base64_decode_char(char c) {
	if (c >= 'A' && c <= 'Z') {
		return (c - 'A');
	} else if (c >= 'a' && c <= 'z') {
		return (c - 'a' + 26);
	} else if (c >= '0' && c <= '9') {
		return (c - '0' + 52);
	} else if (c == '+') {
		return (62);
	} else if (c == '/') {
		return (63);
	} else {
		return (0);
	}
}
/*-----------------------------------------------------------------------------------*/
/** 
 * \brief		Helper function that accumulates sextets
 * \internal
 */
static int base64_add_char(struct base64_decoder_state *s, char c,
		uint8_t *output) {
	if (isspace(c)) {
		return (0);
	}

	if (c == '=') {
		++s->padding;
	}

	s->tmpdata = (s->tmpdata << 6) | base64_decode_char(c);
	PRINTF(7,
			"char: %c, sextet %u: %d\n", c, s->sextets, base64_decode_char(c));
	++s->sextets;
	if (s->sextets == 4) {
		s->sextets = 0;
		output[s->dataptr] = (uint8_t)(s->tmpdata >> 16);
		output[s->dataptr + 1] = (uint8_t)(s->tmpdata >> 8);
		output[s->dataptr + 2] = (uint8_t)(s->tmpdata);
		PRINTF(7,
				"4 sextets decoded: [%u][%u][%u]\n", output[s->dataptr], output[s->dataptr + 1], output[s->dataptr + 2]);
		s->dataptr += 3;
		return (3 - (s->padding));
	}
	return (0);
}

/**
 * \brief           Decodes ASCII input in Base64 format into its binary equivalent.
 *
 *                  This function decodes ASCII input in Base64 format into its binary equivalent.
 *                  Note that given an input of n bytes, the size of the output is around n * 4 / 3
 *                  (or 1.33333n for large n). Before the actual decoding takes place, the function
 *                  will calculate the potential output length for the string passed as parameter,
 *                  and will return if the provided output space is not sufficient.
 *
 * @param[in] input				A pointer to the input to be decoded
 * @param[out] output			A pointer to the memory space where output will be placed
 * @param[out] output_max_len	The maximum length that the output can occupy
 * \return						The size of the decoded output, in bytes, or 0 if nothing was decoded
 *
 */
data_len_t base64_decode(char *input, uint8_t *output,
		data_len_t output_max_len) {

	// Check whether the specified output length suffices:
	if (output_max_len >= strlen(input) * 3 / 4) {
		data_len_t i;
		data_len_t decoded_len = 0;

		// Initialize state
		s.sextets = s.dataptr = s.padding = 0;

		for (i = 0; i < strlen(input); ++i) {
			decoded_len += base64_add_char(&s, input[i], output);
		}

		return (decoded_len);
	} else
		return (0);
}
/** @} */
