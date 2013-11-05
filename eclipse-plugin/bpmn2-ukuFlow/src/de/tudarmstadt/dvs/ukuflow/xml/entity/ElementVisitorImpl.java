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

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.OperatorIDGenerator;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.tools.Base64Converter;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import org.apache.commons.codec.binary.Base64;

public class ElementVisitorImpl implements ElementVisitor {
	Set<String> runtimeError = new HashSet<String>();
	ScriptVisitor sVisitor = new ScriptVisitorImpl();
	EventBaseVisitor eVisitor = new EventBaseVisitorImpl();
	BpmnLog log = BpmnLog.getInstance(ElementVisitorImpl.class.getSimpleName());

	TypeClassifier classifier = TypeClassifier.getInstance();
	protected Vector<Byte> out = new Vector<Byte>();

	public byte toByte(int n) {
		return (byte) n;
	}

	public ElementVisitorImpl() {
		reset();
	}

	public Byte[] getOutput() {
		out.set(1, (byte) (out.size() - 2));
		return out.toArray(new Byte[out.size()]);
	}

	public byte[] getOutput64() {
		out.set(1, (byte) (out.size() - 2));
		byte[] result = new byte[out.size()];
		for (int i = 0; i < out.size(); i++) {
			result[i] = out.get(i);
		}
		return Base64.encodeBase64(result);
	}

	public String getOutputString64() {
		out.set(1, (byte) (out.size() - 2));
		return Base64Converter.getBase64String(out);
		/*
		byte[] result = new byte[out.size()];
		for (int i = 0; i < out.size(); i++) {
			result[i] = out.get(i);
		}
		return Base64.encodeBase64String(result);
		*/
	}

	public void reset() {
		OperatorIDGenerator.init();
		out.clear();
		sVisitor.reset();
		eVisitor.reset();
		out.add((byte) 0);
		out.add((byte) 0);
	}

	@Override
	public void visit(UkuEvent event) {
		log.debug("visiting " + event);
		if (event.hasElementID())
			out.add(event.getWorkflowElementID());

		if (event.hasIncomings() && !event.hasOutgoings()) // end event
			out.add(toByte(UkuConstants.WorkflowOperators.END_EVENT));
		else if (event.hasOutgoings() && !event.hasIncomings()) { // start event
			out.add(toByte(UkuConstants.WorkflowOperators.START_EVENT));
			if (event.getOutgoingEntity().size() != 1) {
			} else {
				UkuEntity outg = event.getOutgoingEntity().get(0);
				if (outg instanceof UkuSequenceFlow) {
					byte next = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity()
							).getWorkflowElementID();
					out.add(next);
				} else {
				}
			}
		}

	}

	@Override
	public void visit(UkuExecuteTask task) {
		log.debug("visiting executeTask: " + task);
		out.add(task.getWorkflowElementID());
		out.add(toByte(UkuConstants.WorkflowOperators.EXECUTE_TASK));
		if (task.getOutgoingEntity().size() != 1) {
			throw new NullPointerException("outgoing size is != 1");
		} else {
			UkuEntity outg = task.getOutgoingEntity().get(0);
			if (outg instanceof UkuSequenceFlow) {
				byte next = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity())
						.getWorkflowElementID();
				out.add(next);
			} else {
			}
		}
		int n = 0;
		if (task.getStatements() != null)
			n = task.getStatements().size();
		out.add(toByte(n));
		sVisitor.reset();
		for (int i = 0; i < n; i++) {
			task.getStatements().get(i).accept(sVisitor);
		}
		// out.add(toByte(sVisitor.getOutput().size()));
		out.addAll(sVisitor.getOutput());
	}

	@Override
	public void visit(UkuGateway gateway) {
		log.debug("visiting " + gateway);
		out.add(gateway.getWorkflowElementID());

		switch (gateway.getUkuType()) {
		case UkuConstants.WorkflowOperators.JOIN_GATEWAY:
			out.add(toByte(UkuConstants.WorkflowOperators.JOIN_GATEWAY));
			if (gateway.getOutgoingEntity().size() != 1) {
			} else {
				UkuEntity outg = gateway.getOutgoingEntity().get(0);
				if (outg instanceof UkuSequenceFlow) {
					byte next = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity())
							.getWorkflowElementID();
					out.add(next);
				} else {
				}
			}
			out.add(toByte(gateway.getIncomingEntity().size()));
			for (UkuEntity sq : gateway.getIncomingEntity()) {
				byte previous_id = ((UkuElement)((UkuSequenceFlow) sq).getSourceEntity())
						.getWorkflowElementID();
				out.add(previous_id);
			}
			break;
		case UkuConstants.WorkflowOperators.FORK_GATEWAY:
			out.add(toByte(UkuConstants.WorkflowOperators.FORK_GATEWAY));
			out.add(toByte(gateway.getOutgoingEntity().size()));
			for (UkuEntity outg : gateway.getOutgoingEntity()) {
				if (outg instanceof UkuSequenceFlow) {
					int id_ = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity())
							.getWorkflowElementID();
					out.add(toByte(id_));
				}
			}
			break;
		case UkuConstants.WorkflowOperators.EXCLUSIVE_DECISION_GATEWAY:
		case UkuConstants.WorkflowOperators.INCLUSIVE_DECISION_GATEWAY: // Hard work here!!

			out.add(toByte(gateway.getUkuType()));
			out.add(toByte(gateway.getOutgoingEntity().size())); // no of Outgoing
															// sequenceflow
			for (UkuEntity outg : gateway.getOutgoingEntity()) {
				UkuSequenceFlow outSeq = (UkuSequenceFlow) outg;
				outSeq.accept(this);
			}
			break;
		case UkuConstants.WorkflowOperators.EXCLUSIVE_MERGE_GATEWAY:
		case UkuConstants.WorkflowOperators.INCLUSIVE_JOIN_GATEWAY:
			out.add(toByte(gateway.getUkuType()));
			if (gateway.getOutgoingEntity().size() != 1) {
			} else {
				UkuEntity outg = gateway.getOutgoingEntity().get(0);
				if (outg instanceof UkuSequenceFlow) {
					byte next = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity())
							.getWorkflowElementID();
					out.add(next);
				} else {
				}
			}
			out.add(toByte(gateway.getIncomingEntity().size()));
			for (UkuEntity sq : gateway.getIncomingEntity()) {
				UkuElement provious_e = ((UkuElement)((UkuSequenceFlow) sq).getSourceEntity());
				if(provious_e instanceof UkuReceiveTask){
					provious_e = (UkuElement)provious_e.getIncomingEntity().get(0).getSourceEntity();
				}
				byte previous_id = provious_e.getWorkflowElementID();
				out.add(previous_id);
			}
			break;

		case UkuConstants.WorkflowOperators.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY:
			out.add(toByte(UkuConstants.WorkflowOperators.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY));
			out.add(toByte(gateway.getOutgoingEntity().size()));
			for (UkuEntity sq : gateway.getOutgoingEntity()){
				UkuEntity entity = ((UkuSequenceFlow)sq).getTargetEntity();
				if(entity instanceof UkuReceiveTask)
					visit((UkuReceiveTask)entity);
			}
			break;
		}

	}

	@Override
	public void visit(UkuSequenceFlow task) {
		log.debug("visiting sequeceFlow: " + task);
		out.add(((UkuElement)task.getTargetEntity()).getWorkflowElementID());
		
		if (task.hasCondition()) {
			sVisitor.reset();
			task.getConditionExp().accept(sVisitor);
			out.add(toByte(sVisitor.getOutput().size()));
			out.addAll(sVisitor.getOutput());
		} else if (task.isDefault()) {
			log.info("sequenceFlow "+task.id + " is a default sq");
			out.add(toByte(0));
		}
	}

	@Override
	public void visit(UkuProcess p) {
		log.debug("visiting process: " + p);
		out.add(p.getWorkflowID());
		out.add((byte) p.getElements().size());
		out.add(p.getNumberOfScope());
		log.info("fetching min, max instances and number of loop : "+p.minInstance + "/"+p.maxInstance+"/"+p.numberOfLoop);
		out.add((byte)p.minInstance);
		out.add((byte)p.maxInstance);
		out.add((byte)p.numberOfLoop);
		
		
		for (UkuElement e : p.getElements()) {
			if(e instanceof UkuReceiveTask)
				continue;
			e.accept(this);
		}
		
		for (UkuScope s : p.getScope()) {
			s.accept(this);
		}
		
	}

	public void visit(UkuScope us) {
		log.debug("visiting UkuScope: "+us);
		if(!us.hasScript()){
			us.addWarningMessage("no script");
			return;
		}
		out.add(toByte(us.getScopeID()));
		
		out.add(toByte(us.getTTL()%256));
		out.add(toByte(us.getTTL()/256));
		sVisitor.reset();
		us.getScopeExp().accept(sVisitor);
		out.add(toByte(sVisitor.getOutput().size()));
		out.addAll(sVisitor.getOutput());
	}

	/* 
	 * @see de.tudarmstadt.dvs.ukuflow.xml.entity.ElementVisitor#visit(de.tudarmstadt.dvs.ukuflow.xml.entity.UkuReceiveTask)
	 */
	@Override
	public void visit(UkuReceiveTask rTask) {
		log.debug("visiting ReceiveTask: " + rTask);
		if(!rTask.hasScript()){
			rTask.addWarningMessage("no script");
			return;
		}
		if (rTask.getOutgoingEntity().size() != 1) {
			throw new NullPointerException("outgoing size is != 1");
		} else {
			UkuEntity outg = rTask.getOutgoingEntity().get(0);
			if (outg instanceof UkuSequenceFlow) {
				byte next = ((UkuElement)((UkuSequenceFlow) outg).getTargetEntity())
						.getWorkflowElementID();
				out.add(next);
			}
		}
		eVisitor.reset();
		rTask.topOperator.accept(eVisitor);
		out.add(toByte(eVisitor.getOutput().size()));
		out.addAll(eVisitor.getOutput());
	}

}
