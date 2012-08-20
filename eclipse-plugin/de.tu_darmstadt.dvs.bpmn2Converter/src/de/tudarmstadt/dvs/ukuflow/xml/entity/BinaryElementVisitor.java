package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.converter.constant.EventTypes;
import de.tudarmstadt.dvs.ukuflow.converter.constant.WorkflowTypes;
import de.tudarmstadt.dvs.ukuflow.script.expression.ukuFlowBinaryVisitor;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;

public class BinaryElementVisitor extends ukuFlowBinaryVisitor implements ElementVisitor {
	byte element_id = 0;
	TypeClassifier classifier = TypeClassifier.getInstance();
	//protected Vector<Byte> out = new Vector<Byte>();
	public BinaryElementVisitor(){
		reset();
	}
	public Byte[] getOutput(){
		return out.toArray(new Byte[out.size()]);
	}
	
	public void reset(){
		out.clear();
		element_id = 0;
	}
	private byte generateID(){
		byte tmp = element_id;
		element_id++;
		return tmp;
	}
	
	@Override
	public void visit(UkuEvent event) {
		if(event.hasElementID())
			out.add(event.getElementID());
		else 
			out.add(generateID());
		
		if(event.hasIncomings() && !event.hasOutgoings()) //end event
			out.add(toByte(WorkflowTypes.END_EVENT));
		else if(event.hasOutgoings() && !event.hasIncomings()) { //start event
			out.add(toByte(WorkflowTypes.START_EVENT));
			if(event.getOutgoing().size() != 1){
				//TODO: error: start event has more than 2 outgoing
			} else {
				UkuEntity outg= event.getOutgoing().get(0);
				if(outg instanceof UkuSequenceFlow){
					byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
					out.add(next);
				} else {
					//TODO ERROR: outgoing ist not a sequenceflow
				}
			}
		}
		
	}

	@Override
	public void visit(UkuExecuteTask task) {
		out.add(task.getElementID());
		out.add(toByte(WorkflowTypes.EXECUTE_TASK));
		if(task.getOutgoing().size() != 1){
			//TODO: error: start event has more than 2 outgoing
		} else {
			UkuEntity outg= task.getOutgoing().get(0);
			if(outg instanceof UkuSequenceFlow){
				byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
				out.add(next);
			} else {
				//TODO ERROR: outgoing ist not a sequenceflow				
			}
		}
		int n = 0;
		if(task.getStatements() != null)
			n = task.getStatements().size();
		out.add(toByte(n));
		for(int i = 0; i< n;i++){
			task.getStatements().get(i).accept(this);
		}
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
		int tp = classifier.getGatewayType(gateway);
		switch(tp){
			case WorkflowTypes.JOIN_GATEWAY:
				out.add(toByte(tp));
				if(gateway.getOutgoing().size() != 1){
					//TODO: error: start event has more than 2 outgoing
				} else {
					UkuEntity outg= gateway.getOutgoing().get(0);
					if(outg instanceof UkuSequenceFlow){
						byte next = ((UkuSequenceFlow)outg).getTarget().getElementID();
						out.add(next);
					} else {
						//TODO ERROR: outgoing ist not a sequenceflow
					}
				}
				out.add(toByte(gateway.getIncoming().size()));
				for(UkuEntity sq : gateway.getIncoming()){
					byte previous_id = ((UkuSequenceFlow)sq).getSource().getElementID();
					out.add(previous_id);
				}
			case 2:
			case 3:
		}
		
	}

	@Override
	public void visit(UkuSequenceFlow task) {
		// TODO Auto-generated method stub
		
	}

}
