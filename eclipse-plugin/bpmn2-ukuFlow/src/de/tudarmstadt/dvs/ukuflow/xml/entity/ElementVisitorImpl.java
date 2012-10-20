package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitorImpl;
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
		out.clear();
		sVisitor.reset();
		eVisitor.reset();
		out.add((byte) 0);
		out.add((byte) 0);
	}

	@Override
	public void visit(UkuEvent event) {
		if (event.hasElementID())
			out.add(event.getWorkflowElementID());

		if (event.hasIncomings() && !event.hasOutgoings()) // end event
			out.add(toByte(UkuConstants.END_EVENT));
		else if (event.hasOutgoings() && !event.hasIncomings()) { // start event
			out.add(toByte(UkuConstants.START_EVENT));
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
		out.add(task.getWorkflowElementID());
		out.add(toByte(UkuConstants.EXECUTE_TASK));
		if (task.getOutgoingEntity().size() != 1) {
		} else {
			UkuEntity outg = task.getOutgoingEntity().get(0);
			if (outg instanceof UkuSequenceFlow) {
				byte next = ((UkuSequenceFlow) outg).getTargetEntity()
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
		out.add(gateway.getWorkflowElementID());

		switch (gateway.ukuGatewayType) {
		case UkuConstants.JOIN_GATEWAY:
			out.add(toByte(UkuConstants.JOIN_GATEWAY));
			if (gateway.getOutgoingEntity().size() != 1) {
			} else {
				UkuEntity outg = gateway.getOutgoingEntity().get(0);
				if (outg instanceof UkuSequenceFlow) {
					byte next = ((UkuSequenceFlow) outg).getTargetEntity()
							.getWorkflowElementID();
					out.add(next);
				} else {
				}
			}
			out.add(toByte(gateway.getIncoming().size()));
			for (UkuEntity sq : gateway.getIncoming()) {
				byte previous_id = ((UkuSequenceFlow) sq).getSourceEntity()
						.getWorkflowElementID();
				out.add(previous_id);
			}
			break;
		case UkuConstants.FORK_GATEWAY:
			out.add(toByte(UkuConstants.FORK_GATEWAY));
			out.add(toByte(gateway.getOutgoingEntity().size()));
			for (UkuEntity outg : gateway.getOutgoingEntity()) {
				if (outg instanceof UkuSequenceFlow) {
					int id_ = ((UkuSequenceFlow) outg).getTargetEntity()
							.getWorkflowElementID();
					out.add(toByte(id_));
				}
			}
			break;
		case UkuConstants.EXCLUSIVE_DECISION_GATEWAY:
		case UkuConstants.INCLUSIVE_DECISION_GATEWAY: // Hard work here!!

			out.add(toByte(gateway.ukuGatewayType));
			out.add(toByte(gateway.getOutgoingEntity().size())); // no of Outgoing
															// sequenceflow
			for (UkuEntity outg : gateway.getOutgoingEntity()) {
				UkuSequenceFlow outSeq = (UkuSequenceFlow) outg;
				outSeq.accept(this);
			}
			break;
		case UkuConstants.EXCLUSIVE_MERGE_GATEWAY:
		case UkuConstants.INCLUSIVE_JOIN_GATEWAY:
			out.add(toByte(UkuConstants.INCLUSIVE_JOIN_GATEWAY));
			if (gateway.getOutgoingEntity().size() != 1) {
			} else {
				UkuEntity outg = gateway.getOutgoingEntity().get(0);
				if (outg instanceof UkuSequenceFlow) {
					byte next = ((UkuSequenceFlow) outg).getTargetEntity()
							.getWorkflowElementID();
					out.add(next);
				} else {
				}
			}
			out.add(toByte(gateway.getIncoming().size()));
			for (UkuEntity sq : gateway.getIncoming()) {
				byte previous_id = ((UkuSequenceFlow) sq).getSourceEntity()
						.getWorkflowElementID();
				out.add(previous_id);
			}
			break;

		case UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY:
			// TODO need more info!
			break;
		}

	}

	@Override
	public void visit(UkuSequenceFlow task) {
		out.add(task.getTargetEntity().getWorkflowElementID());
		if (task.hasCondition()) {
			sVisitor.reset();
			task.getConditionExp().accept(sVisitor);
			out.add(toByte(sVisitor.getOutput().size()));
			out.addAll(sVisitor.getOutput());
		} else if (task.isDefault()) {
			System.out.println("default");
			out.add(toByte(0));
		}
	}

	@Override
	public void visit(UkuProcess p) {
		out.add(p.getWorkflowID());
		out.add((byte) p.getElements().size());
		out.add(p.getNumberOfScope());
		for (UkuElement e : p.getElements()) {
			e.accept(this);
		}
		
		for (UkuScope s : p.getScope()) {
			s.accept(this);
		}
	}

	public void visit(UkuScope us) {
		if(!us.hasScript()){
			us.addWarningMessage("no script");
			return;
		}
		out.add(toByte(us.getScopeID()));
		out.add(toByte(us.getTTL()/256));
		out.add(toByte(us.getTTL()%256));
		sVisitor.reset();
		us.getScopeExp().accept(sVisitor);
		out.add(toByte(sVisitor.getOutput().size()));
		out.addAll(sVisitor.getOutput());
	}

}
