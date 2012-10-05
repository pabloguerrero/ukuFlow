package de.tudarmstadt.dvs.ukuflow.script.generalscript.functions;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.VariableManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuVariable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyVariableException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.VariableAlreadyExistException;


public class ComputationalFunction extends TaskScriptFunction {
	private UkuVariable variable = null;
	public UkuExpression expression;
	public ComputationalFunction() {

	}

	public ComputationalFunction(String variable, UkuExpression expression) {
		setVariable(variable);
		this.expression = expression;
	}

	public UkuVariable getVariable() {
		return variable;
	}

	public UkuExpression getParam() {
		return expression;
	}

	public void setVariable(String name) {
		variable = new UkuVariable(name);
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
