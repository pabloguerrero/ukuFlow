package de.tu.darmstadt.dvs.ukuflow.script.function;

import de.tu.darmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tu.darmstadt.dvs.ukuflow.script.expression.Visitor;

public class ComputationalFunction implements Visitable{

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}
