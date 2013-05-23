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

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterBinaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterPolicy;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterUnaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ETopExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;

public class EventBaseVisitorImpl implements EventBaseVisitor{
	Vector<Byte> out = new Vector<Byte>();
	@Override
	public void visit(EEventBaseScript eventBaseScript) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexEF ef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterBinaryExpression exp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterPolicy policy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterUnaryExpression exp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ESimpleEF sef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ESimpleFilterConstraint sec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ETopExpression top) {
		//top.i
	}

	@Override
	public void visit(EAperiodicDistributionEG a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EAperiodicPatternedEG a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EPeriodicEG ep) {
		out.add((byte)UkuConstants.PERIODIC_E_GEN);
		out.add(ChannelIDManager.getInstance().getChannelID(ep.getID()));
		out.add((byte)ep.getSensorType());
		byte code = 0;
		if(ep.getScope() == null || ep.getScope().equals(""))
			code = 0;
		else 
			code = (byte)ep.getScope().hashCode();
		//TODO scope manager;
		out.add(code);
		//out.add(ep.get)
		
	}

	@Override
	public void visit(EventBaseOperator op) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reset(){
		out.clear();
	}
}
