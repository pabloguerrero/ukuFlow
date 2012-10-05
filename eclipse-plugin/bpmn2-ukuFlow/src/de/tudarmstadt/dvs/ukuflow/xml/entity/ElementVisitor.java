package de.tudarmstadt.dvs.ukuflow.xml.entity;

public interface ElementVisitor {
	public abstract void visit(UkuEvent e);

	public abstract void visit(UkuExecuteTask t);

	public abstract void visit(UkuGateway g);

	public abstract void visit(UkuSequenceFlow s);

	public abstract void visit(UkuProcess p);

	public abstract void visit(UkuScope s);
}
