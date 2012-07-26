package de.tudarmstadt.dvs.ukuflow.xml.entity;

public class SequenceFlow extends Entity{
	public String source;
	public String target;
	public String condition;
	public SequenceFlow(String id) {
		super(id);
	}
	
	public SequenceFlow(String id, String source, String target){
		this(id);
		this.source = source;
		this.target = target;
	}
	
	public void setCondition(String condition){
		this.condition = condition;
	}
	
}
