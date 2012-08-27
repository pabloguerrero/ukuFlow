package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.util.LinkedList;
import java.util.List;


public class ComputationalFunction extends TaskScriptFunction {
	private String variable = "";
	public UkuExpression expression;
	public ComputationalFunction() {

	}

	public ComputationalFunction(String variable, UkuExpression expression) {
		this.variable = variable.trim();
		this.expression = expression;
	}

	public String getVariable() {
		return variable;
	}

	public UkuExpression getParam() {
		return expression;
	}

	public void setVariable(String name) {
		variable = name.trim();
	}

	public void setParameters(UkuExpression exp) {
		this.expression = exp;
	}
	@Override
	public String toString(){
		String result = "";
		result += variable +"<-";
		result += expression;
		return result;
	}
	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}	
}
