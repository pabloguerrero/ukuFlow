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

package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.ArrayList;
import java.util.Collection;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class ESimpleFilterConstraint implements Visitable {
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	private int type = -1;
	private int value = -1;
	private int comparator = -1;
	private boolean valueFirst;

	public ESimpleFilterConstraint(String type, String op, String value,
			boolean valueFirst) {
		setType(type);
		setValue(value);
		setComparator(op);
		this.valueFirst = valueFirst;
	}
	public byte getComparator(){
		return (byte) comparator;
	}
	public Collection<? extends Byte> getType() {
		ArrayList<Byte> result = new ArrayList<Byte>();
		result.add(UkuConstants.DataTypeConstants.CUSTOM_INPUT_VALUE);
		result.add((byte)type);
		return result;
	}
	
	public Collection<? extends Byte> getValues() {
		ArrayList<Byte> result = new ArrayList<Byte>();
		int length = 0;
		switch (type) {
		case UkuConstants.EventFields.EVENT_OPERATOR_ID_F:
		case UkuConstants.EventFields.SOURCE_F:
		case UkuConstants.EventFields.ORIGIN_SCOPE_F:
		case UkuConstants.EventFields.EVENT_TYPE_F:
			result.add(UkuConstants.DataTypeConstants.UINT8_VALUE);
			length = 1;
			break;
		case UkuConstants.EventFields.ORIGIN_NODE_F:
		case UkuConstants.EventFields.MAGNITUDE_F:
			result.add(UkuConstants.DataTypeConstants.UINT16_VALUE);
			length = 2;
			break;
		case UkuConstants.EventFields.TIMESTAMP_F:
			result.add(UkuConstants.DataTypeConstants.UINT16_VALUE);
			length = 2;
			break;
		}
		int tmp = value;
		log.debug(" getValues() : " + value);
		for (int i = 0; i < length; i++) {
			result.add((byte) (tmp % 256));
			tmp = tmp / 256;
		}
		return result;
	}

	public void setType(String type) {
		// double check. type should end with _F. i.e EVENT_TYPE_F
		if(!type.endsWith("_F")){
			type += "_F";
		}
		this.type = UkuConstants.getConstantByName(type);
	}

	public void setValue(String value) {
		int v = UkuConstants.getConstantByName(value);
		if (v == -1)
			try {
				v = Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		this.value = v;
	}

	public void setComparator(String op){
		if(op.equals("=="))
			comparator = UkuConstants.OperatorConstants.PREDICATE_EQ;
		else if (op.equals("!="))
			comparator = UkuConstants.OperatorConstants.PREDICATE_NEQ;
		else if (op.equals(">"))
			comparator = UkuConstants.OperatorConstants.PREDICATE_GT;
		else if (op.equals(">="))
			comparator = UkuConstants.OperatorConstants.PREDICATE_GET;
		else if (op.equals("<"))
			comparator = UkuConstants.OperatorConstants.PREDICATE_LT;
		else if (op.equals("<="))
			comparator = UkuConstants.OperatorConstants.PREDICATE_LET;
		else 
			log.error("Operation '"+op+"' is not supported");
	}

	public boolean isValueFirst() {
		return valueFirst;
	}

	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}

}
