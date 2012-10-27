package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EComplexEF extends EventBaseOperator{

	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		String s;
		return null;
	}
	
}