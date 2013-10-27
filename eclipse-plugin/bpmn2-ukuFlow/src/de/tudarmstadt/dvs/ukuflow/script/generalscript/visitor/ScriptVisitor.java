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

import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ComparisonExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuConstant;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuRepositoryField;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuString;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuVariable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ScopeFunction;


public interface ScriptVisitor {
	public void reset();
	// visit primaryexpression
	public void visit(UkuConstant uConstant);
	public void visit(UkuString uString);
	public void visit(UkuVariable uVariable);
	//visit numericalexpression
	public void visit(UnaryNumericalExpression uNumExp);
	public void visit(BinaryNumericalExpression bNumExp);
	// visit boolexpression
	public void visit(ComparisonExpression compExp);
	public void visit(BinaryLogicalExpression bLogicExp);
	public void visit(UnaryLogicalExpression uLogicExp);
	// visit 3 types of statement
	public void visit(LocalFunction localF);
	public void visit(ScopeFunction scopeF);
	public void visit(ComputationalFunction computationalF);
	
	// visit taskScript
	//public void visit(UkuTaskScript ukuTaskScript);
	
	public void visit(UkuRepositoryField field);
	public Vector<Byte> getOutput();
}
