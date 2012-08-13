package de.tudarmstadt.dvs.ukuflow.xml;

import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

import de.tudarmstadt.dvs.ukuflow.exception.UnsupportedElementException;

/**
 * @author Hien Quoc Dang
 * @deprecated : not use anymore
 */
@Deprecated
public class XMLNode {
	private final Element root;
	
	public XMLNode(String name){
		root = new Element(name);
	}
	/*
	public XMLNode (XMLNode parent, String name){
		root = new Element(name);
		parent.addChild(this);
	}
	*/
	public XMLNode(Element root){
		this.root = root;
	}
	public Element getElement(){
		return root;
	}
	
	public String getName(){
		return root.getName();
	}
	public void addContent(String content){
		root.addContent(content);
	}
	/**
	 * 
	 * @param mode : 0 means getTextMormalize(), 1 means getTextTrim() otherwise it means getText()
	 * @return String
	 */
	public String getText(int mode){
		switch (mode){
		case 0 : return root.getTextNormalize(); 
		case 1 : return root.getTextTrim();
		default : return root.getText();
		}
	}
	public List<XMLNode> getChildren(){
		List<Element> cd = root.getChildren();
		final List<XMLNode> r = new LinkedList<XMLNode>();
		for(Element c : cd){
			r.add(new XMLNode(c));
		}
		return r;
	}
	public int getType() throws UnsupportedElementException{
		return TypeClassifier.getInstance().getType(root.getName());
	}
	public boolean isAtom(){
		//TODO
		return false;
	}
	
}
