package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * this is an abstract class, which can be used to represent an execute task
 * ({@code UkuExecuteTask}), a gateway ({@code UkuGateway}) and a start- or end-event 
 * ({@code UkuEvent}). 
 * @author Hien Quoc Dang
 *
 */
public abstract class UkuElement extends UkuEntity{
	private byte element_id = -1;
	private boolean hasElementID = false;
	protected List<String> incoming = new LinkedList<String>();
	protected List<String> outgoing = new LinkedList<String>();	
	
	protected List<UkuEntity> incomingEntities = new LinkedList<UkuEntity>();
	protected List<UkuEntity> outgoingEntities = new LinkedList<UkuEntity>();
	
	public UkuElement(String id) {
		super(id);
	}
	public boolean hasElementID(){
		return hasElementID;
	}
	public void setElementID(byte id){
		if(hasElementID){
			//TODO error!
			return;
		}
		element_id = id;
		hasElementID = true;
	}
	public byte getElementID(){
		return element_id;
	}
	public void addIncoming(String sequenceFlow){
		this.incoming.add(sequenceFlow);
	}
	
	public void addOutgoing(String sequenceFlow){
		this.outgoing.add(sequenceFlow);
	}
	
	public List<UkuEntity> getOutgoing() {
		return outgoingEntities;
	}
	
	public List<UkuEntity> getIncoming(){
		return incomingEntities;
	}
	public boolean hasIncomings(){
		return incoming.size()>0;
	}
	public boolean hasOutgoings(){
		return outgoing.size()>0;
	}
	public void setReference(Map<String, UkuEntity> ref){		
		for(String entity:incoming){
			if(ref.containsKey(entity)){
				incomingEntities.add(ref.get(entity));
			} else {
				addErrorMessage("the reference of '"+id+"' doesnot contain element "+entity+"(outgoing entity)");
			}
		}
		
		for(String entity:outgoing){
			if(ref.containsKey(entity)){
				outgoingEntities.add(ref.get(entity));
			} else {
				addErrorMessage("the reference of '"+id+"' does not contain element "+entity+"(outgoing entity)");
			}
		}
	}
}
