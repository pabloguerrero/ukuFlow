package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

import java.util.ArrayList;
import java.util.List;

public class ESimpleEF extends EventBaseOperator{
	List<ESimpleFilterConstraint> constraints = new ArrayList<ESimpleFilterConstraint>();
	List<String> sourceVariable = new ArrayList<String>();
	List<EventBaseOperator> sourceDirect = new ArrayList<EventBaseOperator>();
	
	public void addSource(String variable){
		sourceVariable.add(variable);
	}
	
	public void addSource(EventBaseOperator source){
		sourceDirect.add(source);
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		
	}
}