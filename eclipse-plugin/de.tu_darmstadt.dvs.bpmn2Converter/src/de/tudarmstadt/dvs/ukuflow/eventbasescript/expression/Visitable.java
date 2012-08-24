package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

public interface Visitable {
	public void accept(EventBaseVisitor visitor);
}
