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

package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAbsoluteEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChiSquareFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterBinaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterPolicy;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterUnaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EGausianFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EImmediateEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ELogicalCompositionEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EOffsetEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EParetoFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EProcessingEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERecurringEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERelativeEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterNestedConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ETopExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EVariable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;


public interface EventBaseVisitor { 
	public Vector<Byte> getOutput();
	void visit(EEventBaseScript eventBaseScript);
	void visit(EComplexEF ef);
	void visit(EComplexFilterBinaryExpression exp);
	void visit(EComplexFilterPolicy policy);
	void visit(EComplexFilterUnaryExpression exp);
	void visit(ESimpleEF sef);
	void visit(ESimpleFilterConstraint sec);
	void visit(EAperiodicDistributionEG a);
	void visit(EAperiodicPatternedEG a);
	void visit(EPeriodicEG ep);
	void visit(EAbsoluteEG e);
	void visit(EImmediateEG e);
	void visit(EOffsetEG e);
	void visit(ERelativeEG e);
	void visit(EVariable e);
	void visit(ESimpleFilterNestedConstraint e);
	void reset();
	/**
	 * @param eChiSquareFunction
	 */
	void visit(EChiSquareFunction eChiSquareFunction);
	/**
	 * @param eGausianFunction
	 */
	void visit(EGausianFunction eGausianFunction);
	/**
	 * @param eParetoFunction
	 */
	void visit(EParetoFunction eParetoFunction);
	
	void visit(EProcessingEF f);
	
	void visit(ELogicalCompositionEF ef);
}
