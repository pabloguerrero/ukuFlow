package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class LocalFunction implements Visitable {

	String function_name;
	List<PrimaryExpression> params;

	/**
	 * indicate if the result will be stored into a variable
	 */
	boolean saveResult2Variable = false;
	String variable;

	public LocalFunction(String variable, String name, List<PrimaryExpression> params) {
		if (params != null)
			this.params = params;

		function_name = name;

		if (variable != null) {
			saveResult2Variable = true;
			this.variable = variable;
		}
	}

	public LocalFunction(String name, List<PrimaryExpression> params) {
		this(null, name, params);
	}

	public LocalFunction(String function_name) {
		this(function_name, null);
	}

	public void setVariable(String s) {
		variable = s;
		saveResult2Variable = true;
	}

	public String getVariable() {
		return variable;
	}

	public List<PrimaryExpression> getParams() {
		return params;
	}

	public void setParams(List<PrimaryExpression> pr) {
		this.params = pr;
	}

	public String toString() {
		String r = "";
		r += variable + " = ";
		r += function_name + "(";
		boolean coma = false;
		for (PrimaryExpression pe : params) {
			if (coma)
				r += ", ";
			r += pe.toString();
			coma = true;
		}
		return r + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);

	}
}
