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


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.INonEMFEventbaseElement;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;
import org.eclipse.emf.ecore.impl.EClassImpl;
public abstract class EEvaluableExpression implements INonEMFEventbaseElement,IEEvaluableExpression,Visitable {
	private String nonEMF_ID;
	private List<IEConnection> incomings;
	private List<IEConnection> outgoings;
	

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.INonEMFEventbaseElement#getID()
	 */
	@Override
	public String getID() {
		if(nonEMF_ID == null) {
			nonEMF_ID = UUID.randomUUID().toString();
		}
		return nonEMF_ID;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.INonEMFEventbaseElement#setID(java.lang.String)
	 */
	@Override
	public void setID(String id) {
		nonEMF_ID = id;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#getIncomings()
	 */
	@Override
	public List<IEConnection> getIncomings() {
		return incomings;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#getOutgoings()
	 */
	@Override
	public List<IEConnection> getOutgoings() {
		return outgoings;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#addIncoming(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEConnection)
	 */
	@Override
	public void addIncoming(IEConnection in) {
		if(incomings == null)
			incomings = new LinkedList<IEConnection>();
		incomings.add(in);
		
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#addOutgoing(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEConnection)
	 */
	@Override
	public void addOutgoing(IEConnection out) {
		if(outgoings == null)
			outgoings = new LinkedList<IEConnection>();
		outgoings.add(out);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#setIncomings(java.util.List)
	 */
	@Override
	public void setIncomings(List<IEConnection> ins) {
		incomings = ins;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEEvaluableExpression#setOutgoings(java.util.List)
	 */
	@Override
	public void setOutgoings(List<IEConnection> outs) {
		outgoings = outs;
	}

}