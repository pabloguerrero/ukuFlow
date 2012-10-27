package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EAperiodicPatternedEG extends EventGenerator{	
	int time = 1;
	String pattern = "";	
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
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString(){
		String s ="[PATTERNED="+pattern+"("+time+"s)]";		
		return getVariable()+"PEG_"+getSensorType()+"_"+s+"@"+getScope();
	}
}