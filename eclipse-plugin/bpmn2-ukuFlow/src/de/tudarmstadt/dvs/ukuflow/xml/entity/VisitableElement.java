package de.tudarmstadt.dvs.ukuflow.xml.entity;

public interface VisitableElement{
	public void accept(ElementVisitor visitor);	
}