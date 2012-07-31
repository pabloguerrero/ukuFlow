package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.tudarmstadt.dvs.ukuflow.script.ukuFlowScript;

public class UkuSequenceFlow extends UkuEntity{
	public String source;
	public String target;
	public String condition;
	public UkuSequenceFlow(String id) {
		super(id);
	}
	
	public UkuSequenceFlow(String id, String source, String target){
		this(id);
		this.source = source;
		this.target = target;
	}
	
	public void setCondition(String condition){
		this.condition = condition;
		System.out.println(condition);
		InputStream is = new ByteArrayInputStream(condition.getBytes());
		ukuFlowScript parser = new ukuFlowScript(is);
		try{
			System.out.println(parser.taskScript());
			//TODO
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
