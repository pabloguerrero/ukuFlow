package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class ScopeFunction extends TaskScriptFunction implements Visitable{
	public String scopeName;
	public String functionName;
	public List<UkuExpression> params;

	public ScopeFunction(String name) {
		scopeName = name;
	}

	public ScopeFunction(String name, String function, List<UkuExpression> params) {
		this.functionName = function;
		this.scopeName = name;
		this.params = params;
		
	}
	
	public void setParams(List<UkuExpression> params){
		this.params = params;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public String toString(){
		String s = "";
		s += scopeName + " : " + functionName + "(";
		for(int i = 0; i < params.size(); i++){
			if(i == params.size()-1)
				s += params.get(i) + ")";
			else
				s += params.get(i) + ",";
		}
		return s;
	}
	
}
