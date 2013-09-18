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

package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * this is an abstract class, which can be used to represent an execute task (
 * {@code UkuExecuteTask}), a gateway ({@code UkuGateway}) and a start- or
 * end-event ({@code UkuEvent}).
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class UkuElement extends UkuEntity {
	private byte element_id = -1;
	private boolean hasElementID = false;
	protected List<String> incoming = new LinkedList<String>();
	protected List<String> outgoing = new LinkedList<String>();

	protected List<UkuSequenceFlow> incomingEntities = new LinkedList<UkuSequenceFlow>();
	protected List<UkuSequenceFlow> outgoingEntities = new LinkedList<UkuSequenceFlow>();
	protected boolean hasReference = false;
	public UkuElement(String id) {
		super(id);
	}

	public boolean hasElementID() {
		return hasElementID;
	}

	public void setWorkflowElementID(byte id) {
		if (hasElementID) {
			// TODO error!
			return;
		}
		element_id = id;
		hasElementID = true;
	}
	public List<UkuElement> getNextElements(){
		List<UkuElement> result = new LinkedList<UkuElement>();
		for(UkuEntity e: outgoingEntities){
			
		}
		return result;
	}
	public byte getWorkflowElementID() {
		return element_id;
	}

	public void setIncoming(List<String> in) {
		this.incoming = in;
	}

	public void setOutgoingEntity(List<UkuSequenceFlow> outg) {
		this.outgoingEntities = outg;
	}

	public void setIncomingEntity(List<UkuSequenceFlow> in) {
		this.incomingEntities = in;
	}

	public void setOutgoing(List<String> outg) {
		this.outgoing = outg;
	}

	public List<String> getOutgoingID() {
		return this.outgoing;
	}

	public List<String> getIncomingID() {
		return this.incoming;
	}

	public void addIncoming(String sequenceFlow) {
		this.incoming.add(sequenceFlow);
	}

	public void addOutgoing(String sequenceFlow) {
		this.outgoing.add(sequenceFlow);
	}

	public List<UkuSequenceFlow> getOutgoingEntity() {
		return outgoingEntities;
	}

	public List<UkuSequenceFlow> getIncomingEntity() {
		return incomingEntities;
	}

	public boolean hasIncomings() {
		return incoming.size() > 0;
	}

	public boolean hasOutgoings() {
		return outgoing.size() > 0;
	}

	public void setReference(Map<String, UkuEntity> ref) {
		//hasReference = true;
		for (String entity : incoming) {
			if (ref.containsKey(entity)) {
				incomingEntities.add((UkuSequenceFlow)ref.get(entity));
			} else {
				addErrorMessage("The reference of '" + id
						+ "' doesnot contain element " + entity
						+ "(outgoing entity)");
			}
		}

		for (String entity : outgoing) {
			if (ref.containsKey(entity)) {
				outgoingEntities.add((UkuSequenceFlow)ref.get(entity));
			} else {
				addErrorMessage("The reference of '" + id
						+ "' does not contain element " + entity
						+ "(outgoing entity)");
			}
		}
	}
	public List<UkuGateway> getPreviousGateways(){
		List<UkuGateway> result = new LinkedList<UkuGateway>();
		
		for(UkuEntity flow : incomingEntities){
			UkuEntity tmp = flow;
			while(!(tmp instanceof UkuGateway)){
				if(tmp instanceof UkuSequenceFlow){
					tmp = ((UkuSequenceFlow)tmp).getSourceEntity();
				} else if(tmp instanceof UkuExecuteTask){
					tmp = ((UkuExecuteTask)tmp).getIncomingEntity().get(0);
				} else if(tmp instanceof UkuEvent){
					break;
				}
				
			}
			if(tmp instanceof UkuGateway){
				result.add((UkuGateway)tmp);
			}
		}
		return result;
	}
	public List<UkuGateway> getNextGateways(){
		List<UkuGateway> result = new LinkedList<UkuGateway>();		
		for(UkuEntity flow : outgoingEntities){
			UkuEntity tmp = flow;
			while(!(tmp instanceof UkuGateway)){
				if(tmp instanceof UkuSequenceFlow){
					tmp = ((UkuSequenceFlow)tmp).getTargetEntity();
				} else if(tmp instanceof UkuExecuteTask){
					tmp = ((UkuExecuteTask)tmp).getOutgoingEntity().get(0);
				} else if(tmp instanceof UkuEvent){
					break;
				}
				
			}
			if(tmp instanceof UkuGateway){
				result.add((UkuGateway)tmp);
			}
		}
		return result;
	}
}
