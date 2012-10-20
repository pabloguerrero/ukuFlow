package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class UkuSequenceFlow extends UkuEntity {
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public String source;
	public String target;

	private UkuEntity sourceEntity;
	private UkuEntity targetEntity;


	UkuExpression conditionExp = null;
	private boolean hasCondition = false;
	private int priority = -1;
	private boolean isDefault = false;

	public UkuSequenceFlow(String id, String source, String target) {
		super(id);
		this.source = source;
		this.target = target;
	}
	
	public UkuSequenceFlow(String id, UkuEntity source, UkuEntity target){
		super(id);
		this.sourceEntity = source;
		this.targetEntity = target;
	}
	
	public void setTargetID(String target) {
		this.target = target;
	}
	public void setTargetEntity(UkuEntity  target){
		this.targetEntity = target;
	}
	
	public void setSourceID(String source) {
		this.source = source;
	}
	
	public void setSourceEntity(UkuEntity source){
		this.sourceEntity = source;
	}
	
	public void setReference(Map<String, UkuEntity> ref) {
		if (ref.containsKey(source)) {
			sourceEntity = (UkuElement) ref.get(source);
		} else {
			System.out.println("error, no sourceentity in " + id);
			addErrorMessage(" no source entity");
		}
		if (ref.containsKey(target)) {
			targetEntity = (UkuElement) ref.get(target);
		} else {
			System.out.println("error, no target entity in " + id);
			addErrorMessage(" no target entity");
		}
	}

	public boolean isDefault() {
		return isDefault;
	}

	public UkuEntity getSourceEntity() {
		return sourceEntity;
	}

	public UkuEntity getTargetEntity() {
		return targetEntity;
	}

	public void setCondition(String condition) {
		if (condition == null || condition.equals("")){
			return;
		}
		hasCondition = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(condition);
		try {
			conditionExp = parser.parseCondition();			
		}catch (Error error) {
			if (parser.token != null)
				addErrorMessage("element " + id + ", at line: "
						+ parser.token.beginLine + "& col: "
						+ parser.token.beginColumn, "error near the token "
						+ parser.token);
			else
				addErrorMessage(error.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Error found at element " + id + " : "
					+ e.getMessage().replace("\n", " ");
			String location = "Element " + id + ", line: "
					+ parser.token.beginLine + "colum:"
					+ parser.token.beginColumn;
			addErrorMessage(location, msg);
		}

	}

	public boolean hasCondition() {
		return hasCondition;
	}
	
	public UkuExpression getConditionExp() {
		return conditionExp;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public void setDefaultGateway() {
		isDefault = true;

	}

	public void setPriority(int p) {
		this.priority = p;
	}

	public int getPriority() {
		return priority;
	}
}
