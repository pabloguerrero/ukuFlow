package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.EBConstant;

public class EPeriodicEG extends EventGenerator{
	int sensor_type = -1;
	int time;
	String scopeName = null;
	public EPeriodicEG(){
		
	}
	
	public void setTime(int time){
		this.time = time;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	
}
