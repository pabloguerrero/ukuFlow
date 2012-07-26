package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class ComputationalFunction implements Visitable {
	private String variable = "";
	private List<PrimaryExpression> params = new LinkedList<PrimaryExpression>();

	public ComputationalFunction() {

	}

	public ComputationalFunction(String variable, List<PrimaryExpression> params) {
		this.params = params;
		this.variable = variable.trim();
	}

	public String getVariable() {
		return variable;
	}

	public List<PrimaryExpression> getParams() {
		return params;
	}

	public void setVariable(String name) {
		variable = name.trim();
	}

	public void setParameters(List<PrimaryExpression> params) {
		this.params = params;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
