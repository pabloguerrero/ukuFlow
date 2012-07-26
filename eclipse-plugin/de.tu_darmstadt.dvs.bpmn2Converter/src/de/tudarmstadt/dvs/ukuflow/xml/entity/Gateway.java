package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

public class Gateway extends Entity{
	public List<String> incomming = new LinkedList<String>();
	public List<String> outgoing = new LinkedList<String>();
	
	public Gateway(String id){
		super(id);
	}
	
	public void addIncoming(String sequenceFlow){
		this.incomming.add(sequenceFlow);
	}
	
	public void addOutgoing(String sequenceFlow){
		this.outgoing.add(sequenceFlow);
	}
	
}
