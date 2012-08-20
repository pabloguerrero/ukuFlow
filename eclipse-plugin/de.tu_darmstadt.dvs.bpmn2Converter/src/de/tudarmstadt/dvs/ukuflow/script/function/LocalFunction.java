package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Variable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.ScriptVisitor;

public class LocalFunction extends TaskScriptFunction  {

	String function_name;
	List<UkuExpression> params;

	/**
	 * indicate if the result will be stored into a variable
	 */
	boolean saveResult2Variable = false;
	Variable variable;

	public LocalFunction(Variable variable, String fname, List<UkuExpression> params) {
		if (params != null)
			this.params = params;

		function_name = fname;

		if (variable != null) {
			setVariable(variable);
		}
	}

	public LocalFunction(String name, List<UkuExpression> params) {
		this(null, name, params);
	}

	public LocalFunction(String function_name) {
		this(function_name, null);
	}
	public String getFunctionName(){
		return function_name;
	}
	public void setVariable(Variable s) {
		variable = s;
		saveResult2Variable = true;
	}
	public boolean hasVariable(){
		return saveResult2Variable;
	}

	public Variable getVariable() {
		return variable;
	}

	public List<UkuExpression> getParams() {
		return params;
	}

	public void setParams(List<UkuExpression> pr) {
		this.params = pr;
	}

	public String toString() {
		String r = "";
		r += variable + " = ";
		r += function_name + "(";
		boolean coma = false;
		for (UkuExpression pe : params) {
			if (coma)
				r += ", ";
			r += pe.toString();
			coma = true;
		}
		return r + ")";
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);

	}
}
