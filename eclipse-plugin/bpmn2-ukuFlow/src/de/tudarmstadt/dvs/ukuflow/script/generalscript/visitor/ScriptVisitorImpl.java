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

package de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ComparisonExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuRepositoryField;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuString;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuVariable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuConstant;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ScopeFunction;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class ScriptVisitorImpl implements ScriptVisitor {

	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());

	protected Vector<Byte> out = new Vector<Byte>();

	public ScriptVisitorImpl() {
	}

	public byte toByte(int v) {
		if (v > 255) {
			log.info("Error: the value should not bigger than 255("+v+")");
			return (byte)255;
		}
		// TODO checking v
		return (byte) v;
	}

	@Override
	public void visit(UkuConstant uConstant) {
		if (uConstant.isBoolValue()) {
			boolean v = (Boolean) uConstant.getValue();
			out.add(toByte(UkuConstants.DataTypeConstants.UINT8_VALUE));
			if (v)
				out.add(toByte(1));
			else
				out.add(toByte(0));

		} else {
			int v = (Integer) uConstant.getValue();
			out.add(uConstant.getType());
			if (uConstant.getLength() == 2) {
				out.add(toByte(v));
				log.debug("length  = 2");
			} else if (uConstant.getLength() == 3) {
				out.add(toByte(v / 256));
				out.add(toByte(v % 256));
			}
			// out.add(toByte(v));
		}

	}

	@Override
	public void visit(UkuString uString) {
		out.add(toByte(UkuConstants.DataTypeConstants.STRING_VALUE));
		log.debug("string :" + uString.getString());

		out.add((byte) (uString.getLength() - 2));

		for (byte b : uString.getString().getBytes()) {
			out.add(b);
		}
	}

	@Override
	public void visit(UkuVariable uVariable) {
		out.add(toByte(UkuConstants.DataTypeConstants.REPOSITORY_VALUE));
		out.add(toByte(uVariable.getID()));
	}

	@Override
	public void visit(UkuRepositoryField field) {
		out.add(toByte(UkuConstants.DataTypeConstants.REPOSITORY_VALUE));
		out.add(toByte(field.getFieldID()));

	}
	
	@Override
	public void visit(UnaryNumericalExpression uNumExp) {
		out.add(toByte(uNumExp.getOperator()));
		uNumExp.getOperand().accept(this);
	}

	@Override
	public void visit(BinaryNumericalExpression bNumExp) {
		out.add(toByte(bNumExp.getOperator()));
		bNumExp.getOperand1().accept(this);
		bNumExp.getOperand2().accept(this);
	}

	@Override
	public void visit(ComparisonExpression compExp) {
		out.add(toByte(compExp.operator));
		compExp.getOperand1().accept(this);
		compExp.getOperand2().accept(this);
	}

	@Override
	public void visit(BinaryLogicalExpression bLogicExp) {
		out.add(toByte(bLogicExp.operator));
		bLogicExp.operand1.accept(this);
		bLogicExp.operand2.accept(this);
	}

	@Override
	public void visit(UnaryLogicalExpression uLogicExp) {
		out.add(toByte(uLogicExp.operator));
		uLogicExp.operand.accept(this);

	}

	@Override
	public void visit(LocalFunction localF) {
		String fname = localF.getFunctionName();
		// LOCAL_FUNCTION_STATEMENT
		out.add(toByte(UkuConstants.LOCAL_FUNCTION_STATEMENT));
		// id of variable
		if (localF.hasVariable()) {
			byte variable_id = (byte) localF.getVariable().getID();
			out.add(variable_id);
		} else {
			out.add((byte) 0);
		}

		int length = fname.length();
		// length of command
		out.add(toByte(length));
		// no of parameters
		out.add(toByte(localF.getParams().size()));
		// command
		for (byte b : fname.getBytes()) {
			out.add(b);
		}
		// parameters
		for (UkuExpression pa : localF.getParams()) {
			pa.accept(this);
		}
	}

	@Override
	public void visit(ScopeFunction scopeF) {
		out.add(toByte(UkuConstants.SCOPED_FUNCTION_STATEMENT));
		// Scope id
		byte scope_id = scopeF.getScopeID();
		out.add(scope_id);
		// command length
		String fName = scopeF.getFunctionName();
		out.add(toByte(fName.length()));

		// no of parameters
		out.add(toByte(scopeF.getParams().size()));

		// command
		for (byte b : fName.getBytes()) {
			out.add(b);
		}

		// parameters
		for (UkuExpression pa : scopeF.getParams()) {
			pa.accept(this);
		}
	}

	@Override
	public void visit(ComputationalFunction computationalF) {
		out.add(toByte(UkuConstants.COMPUTATION_STATEMENT));

		byte id = 0;
		id = toByte(computationalF.getVariable().getID());
		out.add(id);

		// int l = computationalF.getParam().getLength();
		int current_pos = out.size();
		out.add(toByte(1));
		computationalF.getParam().accept(this);
		int length = out.size() - current_pos - 1;
		out.set(current_pos, (byte) length);
	}

	public static void main(String[] args) {
		System.out.println("?@@@".getBytes()[0]);
		StringBuilder sb = new StringBuilder();
		char c = 256;
		for (int i = 0; i < 20; i++)
			sb.append((char) (c + i));

	}

	/*
	 * @Override public void visit(UkuTaskScript ukuTaskScript) { 
	 * Auto-generated method stub log.error("visit is called"); }
	 */
	@Override
	public Vector<Byte> getOutput() {
		return out;
	}

	@Override
	public void reset() {
		out.clear();
	}

	
}
