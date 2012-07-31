package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class ComputationalFunction extends TaskScriptFunction implements Visitable {
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

	public UkuExpression getParams() {
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
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
