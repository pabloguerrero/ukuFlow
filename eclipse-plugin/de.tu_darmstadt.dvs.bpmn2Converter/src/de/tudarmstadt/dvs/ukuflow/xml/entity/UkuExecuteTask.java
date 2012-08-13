package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.ukuFlowScript;

public class UkuExecuteTask extends UkuElement{
	public UkuEntity nextEntity;
	public String nextEntityID;
	
	public String script;
	public UkuExecuteTask(String id) {
		super(id);
		script ="";
	}
	
	public void setNextEntity(UkuEntity nextEntity){
		this.nextEntity = nextEntity;
	}
	
	public void setNextEntityID(String id){
		this.nextEntityID = id;
	}
	@Override
	public void setReference(Map<String, UkuEntity> ref){
		super.setReference(ref);
		
		if (incoming.size() == 0) {
			addWarningMessage(" this element may never be reached. It has no incoming sequence flow");
		}
		
		if (outgoing.size() == 0) {
			addWarningMessage(" no outgoing sequence flow");
		}
		if(outgoing.size() > 1 || incoming.size() >1){
			addErrorMessage("this element has multiple incoming- or outgoing- sequence flows");
		}
	}
	public void setScript(String script){
		this.script = script;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		try{
			System.out.println(parser.parseTaskScript());
		} catch (Exception e) {
			String msg = e.getMessage().replace('\n', ' ');
			addErrorMessage(msg);
		}
	}
	
}
