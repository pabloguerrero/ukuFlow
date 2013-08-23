/**
 * \addtogroup Tools
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
 * \file	bitvector.c
 * \brief	Bit vector operations module
 * \author	Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 * \date Jul 1, 2011
 */

#include "bitvector.h"
#include "logger.h"

/**
 * \brief		Sets the value of the indicated bit from the bit vector passed as parameter
 **/
void bitvector_set(uint8_t *bitvector, uint8_t bit_nr) {
	/** first, a byte is created with the correct bit set: */
	uint8_t shifted_bit = 1 << (bit_nr % 8);
	PRINTF(4, "Shifted bit is now %u\n", shifted_bit);

	/** second, the correct byte is OR'ed with 'shifted_bit' */
	PRINTF(4, "bitvector[%u] was %u\n", bit_nr / 8, bitvector[bit_nr / 8]);
	bitvector[bit_nr / 8] |= shifted_bit;
	PRINTF(4, "bitvector[%u] is now %u\n", bit_nr / 8, bitvector[bit_nr / 8]);
}

/**
 * \brief		Returns the value of the indicated bit from the bit vector passed as parameter
 **/
int bitvector_get(uint8_t *bitvector, uint8_t bit_nr) {
	/**  first, a byte is created with the correct bit set: */
	uint8_t shifted_bit = 1 << (bit_nr % 8);

	PRINTF(4, "bitvector : shifted_bit is %u, byte to use is %2x\n", shifted_bit, bitvector[bit_nr/8]);

	/**  second, the corresponding byte is OR'ed against the byte with the 'shifted_bit' */
	return ((bitvector[bit_nr / 8] & shifted_bit) != 0);
}

/**
 * \brief		Returns whether all of the bits of the bit vector passed as parameter are 1
 * */
int bitvector_all_set(uint8_t *bitvector, uint8_t num_bits) {

	PRINTF(4, "bitvector_all_set : num_bits is %u\n", num_bits);

	/**  we need to check if there is a 1 on each bit in the bitvector */
	uint8_t one = 1;
	uint8_t result = 1;
	uint8_t bit_nr = 0;
	while ((bit_nr < num_bits) && (result)) {
		PRINTF(4, "with bit_nr %u, result is %u, bitvector[%u] is %u\n",
				bit_nr, result, bit_nr / 8, bitvector[bit_nr / 8]);
		result &= (bitvector[bit_nr / 8] & one) != 0;
		PRINTF(4, "after evaluating bit_nr %u, result is %u\n", bit_nr, result);
		bit_nr++;
		one <<= 1;
		if (one == 0)
			one = 1;
		PRINTF(4, "after updating, one is %u\n", one);
	}

	return (result);
}

/**
 *  \brief		Clears all the bits (setting them to 0) of the bit vector passed as parameter
 *
 * \param bitvector		the bitvector on which to clear the bits
 * \param num_bits		the number of bits to clear
 **/
void bitvector_clear(uint8_t *bitvector, uint8_t num_bits) {

	uint8_t bit_nr;
	PRINTF(4, "clearing %u bits\n", num_bits);
	for (bit_nr = 0; bit_nr < num_bits; bit_nr++) {
		PRINTF(4, "clearing bit nr %i\n", bit_nr);
		bitvector[bit_nr] = 0;
	}
}

/** @} */
