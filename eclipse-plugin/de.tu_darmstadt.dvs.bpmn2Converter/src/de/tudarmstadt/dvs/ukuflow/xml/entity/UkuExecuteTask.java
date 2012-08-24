package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.function.TaskScriptFunction;

public class UkuExecuteTask extends UkuElement{
	private List<TaskScriptFunction> statements; 
	private String script;
	public UkuExecuteTask(String id) {
		super(id);
		script ="";
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
			statements = parser.parseTaskScript();			
		} catch (Exception e) {
			String msg = e.getMessage().replace('\n', ' ');
			addErrorMessage(msg);
		}
	}
	
	public List<TaskScriptFunction> getStatements(){
		return statements;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);		
	}
	
}