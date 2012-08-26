package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.eventbasescript.EBConstant;

public abstract class EventGenerator extends EventBaseOperator {
	
	protected String scope = null;
	protected int sensorType = -1;
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = EBConstant.getConstantWithName(sensorType);
	}
	
}
