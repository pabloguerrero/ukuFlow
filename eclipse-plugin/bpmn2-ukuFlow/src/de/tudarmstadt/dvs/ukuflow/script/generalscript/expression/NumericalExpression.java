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
import de.tudarmstadt.dvs.ukuflow.script.*;
/**
 * Numerical Expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class NumericalExpression extends UkuExpression {
	public static final int ADD = UkuConstants.OperatorConstants.OPERATOR_ADD;
	public static final int SUB = UkuConstants.OperatorConstants.OPERATOR_SUB;
	public static final int MUL = UkuConstants.OperatorConstants.OPERATOR_MULT;
	public static final int DIV = UkuConstants.OperatorConstants.OPERATOR_DIV;
	public static final int MOD = UkuConstants.OperatorConstants.OPERATOR_MOD;
	
	public static final int MINUS = 0;//TODO: mapping negative & positive number
	public static final int PLUS = 0;//TODO:

	/**
	 * @uml.property  name="operator"
	 */
	protected int operator = -1;

	public NumericalExpression(int operator) {
		this.operator = operator;
	}

	/**
	 * @param operator
	 * @uml.property  name="operator"
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getStringOperator(){
		switch(operator) {
		case ADD: return "+";
		case SUB: return "-";
		case MUL: return "*";
		case DIV: return "/";
		case MOD: return "%";
		default : return "?";
		//case MINUS: 
		}
	}
}
