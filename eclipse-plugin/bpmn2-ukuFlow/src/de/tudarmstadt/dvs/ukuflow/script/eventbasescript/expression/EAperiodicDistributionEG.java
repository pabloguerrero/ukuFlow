package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EAperiodicDistributionEG extends EventGenerator{
	private String functionName;
	private List<Integer> parameters;
	public void setFunction(String name, List<Integer> params){
		this.functionName = name;
		this.parameters = params;
	}
	public String getFunction(){
		return functionName;
	}
	public List<Integer> getParameters(){
		return parameters;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		//visitor.visit(this);
		
	}
	@Override
	public String toString(){
		String s="";
		s+= "[DISTRIBUTION="+functionName+"(";		
		for(Integer i : parameters){
			s+= i +",";
		}
		s+="__";
		s= s.replace(",__",")");
		return getVariable()+"PEG_"+getSensorType()+"_"+s+"@"+getScope();
	}
}