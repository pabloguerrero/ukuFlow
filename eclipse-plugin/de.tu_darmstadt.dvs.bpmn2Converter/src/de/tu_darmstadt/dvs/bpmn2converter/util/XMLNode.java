package de.tu_darmstadt.dvs.bpmn2converter.util;

import org.jdom2.Element;


public class XMLNode {
	private final Element root;
	
	public XMLNode(String name){
		root = new Element(name);
	}
	
	public XMLNode(XMLNode parent, String name){
		root = new Element(name);
		parent.addChild(this);
	}
	
	public XMLNode(Element root){
		this.root = root;
	}
	public void addChild(XMLNode child){
		
	}
}
