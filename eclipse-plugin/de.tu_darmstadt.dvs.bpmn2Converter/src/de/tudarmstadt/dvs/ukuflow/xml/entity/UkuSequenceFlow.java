package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.script.ukuFlowScript;

public class UkuSequenceFlow extends UkuEntity{
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public String source;
	public String target;
	private UkuEntity sourceEntity;
	private UkuEntity targetEntity;
	public String condition = null;
	
	public UkuSequenceFlow(String id, String source, String target){
		super(id);		
		this.source = source;
		this.target = target;
	}
	public void setReference(Map<String, UkuEntity> ref){
		if(ref.containsKey(source)){
			sourceEntity = ref.get(source);
		} else {
			addErrorMessage(" no source entity");
		}
		if(ref.containsKey(target)){
			targetEntity = ref.get(target);
		} else {
			addErrorMessage(" no target entity");
		}
	}
	
	public UkuEntity getSource(){
		return sourceEntity;
	}
	
	public UkuEntity getTarget(){
		return targetEntity;
	}
	public void setCondition (String condition){
		this.condition = condition;
		ukuFlowScript parser = ukuFlowScript.getInstance(condition);
		try{
			log.debug(parser.parseCondition());
			//TODO: check syntax
		} catch (Exception e) {
			String msg = "Error found at element "+id+" : "+e.getMessage().replace("\n", " ");
			addErrorMessage(msg);
		}
		
	}
	
}
