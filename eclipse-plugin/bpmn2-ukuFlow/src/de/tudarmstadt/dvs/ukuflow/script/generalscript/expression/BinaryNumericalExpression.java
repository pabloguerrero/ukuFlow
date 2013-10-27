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

import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

/**
 * represent a binary numerical expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public class BinaryNumericalExpression extends NumericalExpression {
	public BinaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="operand1"
	 * @uml.associationEnd  
	 */
	protected UkuExpression operand1;
	/**
	 * @uml.property  name="operand2"
	 * @uml.associationEnd  
	 */
	protected UkuExpression operand2;
	public int getOperator(){
		return operator;
	}
	public int getLength(){
		return 1 + operand1.getLength() + operand2.getLength();
	}
	
	/**
	 * @param op1
	 * @uml.property  name="operand1"
	 */
	public void setOperand1(UkuExpression op1) {
		operand1 = op1;
	}

	/**
	 * @param op2
	 * @uml.property  name="operand2"
	 */
	public void setOperand2(UkuExpression op2) {
		operand2 = op2;
	}

	/**
	 * @return
	 * @uml.property  name="operand1"
	 */
	public UkuExpression getOperand1() {
		return operand1;
	}

	/**
	 * @return
	 * @uml.property  name="operand2"
	 */
	public UkuExpression getOperand2() {
		return operand2;
	}

	public String toString() {
		return ("(" + operand1.toString() + " " + getStringOperator() + " "
				+ operand2.toString() + ")");
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}
}
