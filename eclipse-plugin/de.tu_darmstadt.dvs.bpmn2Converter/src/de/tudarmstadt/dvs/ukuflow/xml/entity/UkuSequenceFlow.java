package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class UkuSequenceFlow extends UkuEntity {
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public String source;
	public String target;
	private UkuElement sourceEntity;
	private UkuElement targetEntity;
	public String condition = null;
	UkuExpression conditionExp = null;
	private boolean isDefault = false;

	public UkuSequenceFlow(String id, String source, String target) {
		super(id);
		this.source = source;
		this.target = target;
	}

	public void setTargetID(String target) {
		this.target = target;
	}

	public void setSourceID(String source) {
		this.source = source;
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

	public UkuElement getSource() {
		return sourceEntity;
	}

	public UkuElement getTarget() {
		return targetEntity;
	}

	public void setCondition(String condition) {
		if (condition == null || condition.equals(""))
			return;
		this.condition = condition;
		ukuFlowScript parser = ukuFlowScript.getInstance(condition);
		try {
			conditionExp = parser.parseCondition();
		} catch (Exception e) {
			String msg = "Error found at element " + id + " : "
					+ e.getMessage().replace("\n", " ");
			String location = "Element " + id + ", line: "
					+ parser.token.beginLine + "colum:"
					+ parser.token.beginColumn;
			addErrorMessage(location, msg);
		}

	}

	public boolean hasCondition() {
		return (conditionExp != null);
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
}
