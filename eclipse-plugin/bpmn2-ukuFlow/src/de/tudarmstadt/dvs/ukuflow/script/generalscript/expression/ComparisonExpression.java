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

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

/**
 * A comparison expression, which can compare two {@link #UkuExpression}. <br />
 * Supported operators:
 * {@link #EQUAL}, {@link #UNEQUAL}, {@link #GREATERTHAN}, {@link #LESSTHAN}, {@link #GREATEROREQUAL} or {@link #LESSOREQUAL}
 * @author Hien Quoc Dang
 *
 */
public class ComparisonExpression extends BoolExpression {
	
	public static final int EQUAL = UkuConstants.OperatorConstants.PREDICATE_EQ;
	public static final int UNEQUAL = UkuConstants.OperatorConstants.PREDICATE_NEQ;
	public static final int GREATERTHAN = UkuConstants.OperatorConstants.PREDICATE_GT;
	public static final int LESSTHAN = UkuConstants.OperatorConstants.PREDICATE_LT;
	public static final int GREATEROREQUAL = UkuConstants.OperatorConstants.PREDICATE_GET;
	public static final int LESSOREQUAL = UkuConstants.OperatorConstants.PREDICATE_LET;

	/**
	 * @uml.property  name="operator"
	 */
	public int operator = 0;
	/**
	 * @uml.property  name="operand1"
	 * @uml.associationEnd  
	 */
	public UkuExpression operand1;
	/**
	 * @uml.property  name="operand2"
	 * @uml.associationEnd  
	 */
	public UkuExpression operand2;
	
	public ComparisonExpression(int operator) {
		this.operator = operator;
	}
	
	public ComparisonExpression(int operator, UkuExpression exp1,
			UkuExpression exp2) {
		this(operator);
		this.operand1 = exp1;
		this.operand2 = exp2;
	}
	public int getLength(){
		return 1 + operand1.getLength() + operand2.getLength();
	}
	/**
	 * @return
	 * @uml.property  name="operand2"
	 */
	public UkuExpression getOperand2() {
		return operand2;
	}

	/**
	 * @param operand2
	 * @uml.property  name="operand2"
	 */
	public void setOperand2(UkuExpression operand2) {
		this.operand2 = operand2;
	}

	/**
	 * @return
	 * @uml.property  name="operand1"
	 */
	public UkuExpression getOperand1() {
		return operand1;
	}

	/**
	 * @param operand1
	 * @uml.property  name="operand1"
	 */
	public void setOperand1(UkuExpression operand1) {
		this.operand1 = operand1;
	}
	
	@Override
	public void accept(ScriptVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		String operator = "";
		switch (this.operator) {
		case EQUAL:
			operator = "==";
			break;
		case UNEQUAL:
			operator = "!=";
			break;
		case GREATERTHAN:
			operator = ">";
			break;
		case LESSTHAN:
			operator = "<";
			break;
		case GREATEROREQUAL:
			operator = ">=";
			break;
		case LESSOREQUAL:
			operator = "<=";
			break;
		}
		String result = "(";
		if(operand1 != null)
			result += operand1 + " ";
		
		result +=operator + " ";
		if(operand2 != null)
			result += operand2;
		result += ")";
		return result;
		
	}

}
