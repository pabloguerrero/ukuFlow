package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.List;

public class EAperiodicDistributionEG extends EventGenerator{
	private String functionName;
	private List<Integer> parameters;
	public void setFunction(String name, List<Integer> params){
		this.functionName = name;
		this.parameters = params;
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}