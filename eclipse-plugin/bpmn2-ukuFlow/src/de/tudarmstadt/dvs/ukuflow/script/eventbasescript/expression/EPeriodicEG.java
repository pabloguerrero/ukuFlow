package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EPeriodicEG extends EventGenerator{
	int time;	
	public EPeriodicEG(){
		
	}	
	public void setTime(int time){
		this.time = time;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	@Override
	public String toString(){
		String s="";
		s+=time;
		return getVariable()+"PEG_"+getSensorType()+"_"+s+"@"+getScope();
	}
}
