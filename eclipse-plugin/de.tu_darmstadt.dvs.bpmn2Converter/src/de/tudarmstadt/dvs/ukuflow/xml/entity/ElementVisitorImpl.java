package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.constant.EventTypes;
import de.tudarmstadt.dvs.ukuflow.constant.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.constant.WorkflowTypes;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import org.apache.commons.codec.binary.Base64;
public class ElementVisitorImpl implements ElementVisitor {
	ScriptVisitor sVisitor = new ScriptVisitorImpl();
	EventBaseVisitor eVisitor = new EventBaseVisitorImpl();
	BpmnLog log = BpmnLog.getInstance(ElementVisitorImpl.class.getSimpleName());
	
	TypeClassifier classifier = TypeClassifier.getInstance();
	protected Vector<Byte> out = new Vector<Byte>();
	
	public byte toByte(int n){
		return (byte)n;
	}
	
	public ElementVisitorImpl(){
		reset();
	}
	public Byte[] getOutput(){
		return out.toArray(new Byte[out.size()]);
	}
	public byte[] getOutput64(){
		byte[] result = new byte[out.size()];
		for(int i = 0; i < out.size(); i++){
			result[i] = out.get(i);
		}
		return Base64.encodeBase64(result);		
	}
	public String getOutputString64(){
		byte[] result = new byte[out.size()];
		for(int i = 0; i < out.size(); i++){
			result[i] = out.get(i);
		}
		return Base64.encodeBase64String(result);
	}
	public void reset(){
		out.clear();
		sVisitor.reset();
		eVisitor.reset();
	}

	
	@Override
	public void visit(UkuEvent event) {
		if(event.hasElementID())
			out.add(event.getElementID());
		
		if(event.hasIncomings() && !event.hasOutgoings()) //end event
			out.add(toByte(UkuConstants.END_EVENT));
		else if(event.hasOutgoings() && !event.hasIncomings()) { //start event
			out.add(toByte(UkuConstants.START_EVENT));
			if(event.getOutgoing().size() != 1){
			} else {
				UkuEntity outg= event.getOutgoing().get(0);
				if(outg instanceof UkuSequenceFlow){
					byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
					out.add(next);
				} else {					
				}
			}
		}
		
	}

	@Override
	public void visit(UkuExecuteTask task) {
		out.add(task.getElementID());
		out.add(toByte(UkuConstants.EXECUTE_TASK));
		if(task.getOutgoing().size() != 1){
		} else {
			UkuEntity outg= task.getOutgoing().get(0);
			if(outg instanceof UkuSequenceFlow){
				byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
				out.add(next);
			} else {
			}
		}
		int n = 0;
		if(task.getStatements() != null)
			n = task.getStatements().size();
		out.add(toByte(n));
		sVisitor.reset();
		for(int i = 0; i< n;i++){
			task.getStatements().get(i).accept(sVisitor); 
		}
		//out.add(toByte(sVisitor.getOutput().size()));
		out.addAll(sVisitor.getOutput());
	}

	@Override
	public void visit(UkuGateway gateway) {
		out.add(gateway.getElementID());
		/*
		0 : still unknown (1-1)
		1 : converging (n-1)
		2 : diverging (1-n)
		3 : mixed (n-n)
		*/
		int tp =  0;
		try{
			tp = classifier.getGatewayType(gateway);
		} catch (UnspecifiedGatewayException e) {
			gateway.addErrorMessage(e.getMessage());
			return;
		}
		log.debug("visit gateway " +gateway + "->"+ tp);
		switch(tp){
			case UkuConstants.JOIN_GATEWAY:
				out.add(toByte(UkuConstants.JOIN_GATEWAY));
				if(gateway.getOutgoing().size() != 1){
				} else {
					UkuEntity outg= gateway.getOutgoing().get(0);
					if(outg instanceof UkuSequenceFlow){
						byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
						out.add(next);
					} else {
					}
				}
				out.add(toByte(gateway.getIncoming().size()));
				for(UkuEntity sq : gateway.getIncoming()){
					byte previous_id = ((UkuSequenceFlow)sq).getSource().getElementID();
					out.add(previous_id);
				}
				break;
			case UkuConstants.FORK_GATEWAY:
				out.add(toByte(UkuConstants.FORK_GATEWAY));
				out.add(toByte(gateway.getOutgoing().size()));
				for(UkuEntity outg: gateway.getOutgoing()){				
					if(outg instanceof UkuSequenceFlow){
						int id_ = ((UkuSequenceFlow)outg).getTarget().getElementID();
						out.add(toByte(id_));
					}
				}
				break;
			case UkuConstants.EXCLUSIVE_DECISION_GATEWAY:
			case UkuConstants.INCLUSIVE_DECISION_GATEWAY: // Hard work here!!
				
				out.add(toByte(tp));
				out.add(toByte(gateway.getOutgoing().size())); // no of Outgoing sequenceflow
				for(UkuEntity outg :gateway.getOutgoing()){
					UkuSequenceFlow outSeq = (UkuSequenceFlow)outg;					
					outSeq.accept(this);
				}
				break;
			case UkuConstants.EXCLUSIVE_MERGE_GATEWAY:		
			case UkuConstants.INCLUSIVE_JOIN_GATEWAY:
				out.add(toByte(UkuConstants.INCLUSIVE_JOIN_GATEWAY));
				if(gateway.getOutgoing().size() != 1){
				} else {
					UkuEntity outg= gateway.getOutgoing().get(0);
					if(outg instanceof UkuSequenceFlow){
						byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
						out.add(next);
					} else {
					}
				}
				out.add(toByte(gateway.getIncoming().size()));
				for(UkuEntity sq : gateway.getIncoming()){
					byte previous_id = ((UkuSequenceFlow)sq).getSource().getElementID();
					out.add(previous_id);
				}
				break;
						
			case UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY:
				//TODO need more info!
				break;
		}
		
	}

	@Override
	public void visit(UkuSequenceFlow task) {
		out.add(task.getTarget().getElementID());
		if(task.hasCondition()){
			sVisitor.reset();
			task.getConditionExp().accept(sVisitor);
			out.add(toByte(sVisitor.getOutput().size()));
			out.addAll(sVisitor.getOutput());
		} else if(task.isDefault()){
			System.out.println("default");
			out.add(toByte(0));
		}
	}
	
	@Override
	public void visit(UkuProcess p){
		out.add(p.getWorkflowID());
		out.add((byte)p.getElements().size());
		out.add(p.getNumberOfScope());
		for(UkuElement e : p.getElements()){
			e.accept(this);
		}
		for(UkuScope s : p.getScope()){
			s.accept(this);
		}
	}
	
	public void visit(UkuScope us){
		//TODO: implemetation
	}

}