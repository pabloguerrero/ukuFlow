package de.tudarmstadt.dvs.ukuflow.script.function;

import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class ComputationalFunction implements Visitable{

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}
