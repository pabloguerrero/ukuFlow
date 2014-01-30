/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
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
package de.tudarmstadt.dvs.ukuflow.tools;

import java.util.ArrayList;
import java.util.Collection;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

/**
 * @author Mr Dang
 *
 */
public class NumericTools {
	public static int toInt(String in){
		return Integer.parseInt(in);
	}
	public static Collection<Byte> toByteArray(int in){
		Collection<Byte> result = new ArrayList<Byte>();
		byte type = getMinimalType(in);
		result.add(type);
		if(type == UkuConstants.DataTypeConstants.UINT8_VALUE || type==UkuConstants.DataTypeConstants.INT8_VALUE){
			result.add((byte)in);
		} else { // 2 bytes (16 bit)
			result.add((byte)(in%256));
			result.add((byte)(in/256));
		}
		return result;
	}
	public static byte getMinimalType(int in){
		if(0<= in){
			if(in <= 255)
				return UkuConstants.DataTypeConstants.UINT8_VALUE;
			if(in <= 65535)
				return UkuConstants.DataTypeConstants.UINT16_VALUE;
		} else { // in < 0
			if(-128<=in)
				return UkuConstants.DataTypeConstants.INT8_VALUE;
			if(-32768<=in)
				return UkuConstants.DataTypeConstants.INT16_VALUE;
		}
		System.out.println("input value '"+in+"' is not matched any type");
		return UkuConstants.DataTypeConstants.INT16_VALUE;
	}
}
