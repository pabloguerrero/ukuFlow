package de.tudarmstadt.dvs.ukuflow.script.eventbasescript;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public interface Visitable {
	public void accept(EventBaseVisitor visitor);
}
