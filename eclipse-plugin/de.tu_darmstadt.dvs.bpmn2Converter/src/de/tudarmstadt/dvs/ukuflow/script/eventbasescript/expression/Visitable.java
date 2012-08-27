package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

public interface Visitable {
	public void accept(EventBaseVisitor visitor);
}
