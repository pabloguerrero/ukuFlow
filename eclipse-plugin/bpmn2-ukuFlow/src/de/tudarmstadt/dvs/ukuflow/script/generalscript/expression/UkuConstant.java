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

package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.io.Serializable;
import java.math.BigInteger;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.InvalidConstantValueException;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;

/**
 * represents a ukuFlow Constant. It could be either an integer constant or
 * boolean constant
 * 
 * @author Hien Quoc Dang
 */
public class UkuConstant extends PrimaryExpression {

	byte type = 0;

	int intValue;

	boolean boolValue;

	boolean isBool;
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());

	public UkuConstant(int value) throws InvalidConstantValueException {
		setValue(value);
		isBool = false;
	}

	public UkuConstant(boolean value) {
		boolValue = value;
		isBool = true;
	}

	public UkuConstant(String s) throws InvalidConstantValueException {
		boolean tmp = false;
		try {
			tmp = Boolean.parseBoolean(s);
			boolValue = tmp;
			isBool = true;
		} catch (Exception e) {
			try {
				int radix = 10;
				if (s.startsWith("0x")) {
					radix = 16;
					s = s.substring(2);
				} else if (s.startsWith("0b")) {
					radix = 2;
					s = s.substring(2);
				}
				int t = new BigInteger(s, radix).intValue();
				setValue(t);
				isBool = false;
			} catch (InvalidConstantValueException ex) {
				throw ex;
			} catch (Exception ex) {
				log.debug("The token is neither boolean nor integer");
				e.printStackTrace();
			}
		}
	}

	public void setValue(int intValue) throws InvalidConstantValueException {
		if (intValue >= 0 && intValue < 256)
			type = (byte) UkuConstants.DataTypeConstants.UINT8_VALUE;
		else if (intValue >= 0 && intValue < 65536)
			type = (byte) UkuConstants.DataTypeConstants.UINT16_VALUE;
		else if (intValue >= -128 && intValue < 128)
			type = (byte) UkuConstants.DataTypeConstants.INT8_VALUE;
		else if (intValue >= -32768 && intValue < 32768)
			type = (byte) UkuConstants.DataTypeConstants.INT16_VALUE;
		else {
			throw new InvalidConstantValueException();
		}
		this.intValue = intValue;
	}

	/**
	 * @return
	 * @uml.property name="boolValue"
	 */
	public boolean isBoolValue() {
		return isBool;
	}

	public Serializable getValue() {
		if (isBool)
			return boolValue;
		else
			return intValue;
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 
	 * @return
	 */
	public byte getType() {
		return type;
	}

	public String toString() {
		return "" + (isBool ? boolValue : intValue);
	}

	public int getLength() {

		switch (getType()) {
		case UkuConstants.DataTypeConstants.UINT8_VALUE:
		case UkuConstants.DataTypeConstants.INT8_VALUE:
			return 2;
		case UkuConstants.DataTypeConstants.UINT16_VALUE:
		case UkuConstants.DataTypeConstants.INT16_VALUE:
			return 3;
		}
		return 0;
	}
}
