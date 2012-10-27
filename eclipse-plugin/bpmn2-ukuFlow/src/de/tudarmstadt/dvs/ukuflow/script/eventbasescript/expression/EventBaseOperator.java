package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public abstract class EventBaseOperator extends EEvaluableExpression {
	private String variable = null;

	public void setVariable(String var) {
		this.variable = var;
	}

	public String getVariable() {
		return variable;
	}

	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	public abstract String toString();
}
