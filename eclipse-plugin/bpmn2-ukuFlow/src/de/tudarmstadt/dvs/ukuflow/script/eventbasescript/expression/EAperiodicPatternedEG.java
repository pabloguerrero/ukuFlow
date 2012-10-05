package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EAperiodicPatternedEG extends EventGenerator{
	int sensor_type = -1;
	int time = 1;
	String pattern = "";
	private String scopeName;
	public void setTime(int time){
		this.time = time;
	}
	public void setPattern(String pattern){
		if(!pattern.matches("[0-1]+")){
			this.pattern = pattern;
			//TODO
			return;
		}
		this.pattern = pattern;
	}
	public void setSensorType(String sensor){
		sensor_type = UkuConstants.getConstantWithName(sensor);
		if(sensor_type == -1){
			//TODO or not?
		}
	}
	public void setScope(String scope){
		this.scopeName = scope;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}