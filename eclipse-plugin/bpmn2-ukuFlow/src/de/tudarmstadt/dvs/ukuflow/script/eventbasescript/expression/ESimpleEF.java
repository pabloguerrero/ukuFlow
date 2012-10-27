package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

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
		visitor.visit(this);
	}
	public void setConstraints(List<ESimpleFilterConstraint> cons){
		this.constraints=cons;
	}
	public String toString(){
		String s = "SEF ";
		s+="[";
		for(ESimpleFilterConstraint c : constraints){
			s += c + ",";
		}
		s+="]-[";
		for(String v : sourceVariable){
			s+=v+",";
		}
		for(EventBaseOperator sd : sourceDirect){
			s+=sd+",";
		}
		s+="]";
		return s;
	}
}