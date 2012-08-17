package de.tudarmstadt.dvs.ukuflow.xml.entity;

import de.tudarmstadt.dvs.ukuflow.script.expression.ScriptVisitor;

public interface ElementVisitor extends ScriptVisitor{
	public abstract void visit(UkuEvent event);
	public abstract void visit(UkuExecuteTask task);
	public abstract void visit(UkuGateway task);
	public abstract void visit(UkuSequenceFlow task);
}
