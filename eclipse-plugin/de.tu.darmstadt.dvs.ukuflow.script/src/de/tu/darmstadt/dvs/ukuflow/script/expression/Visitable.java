package de.tu.darmstadt.dvs.ukuflow.script.expression;


public interface Visitable {
	public void accept(Visitor visitor);
}