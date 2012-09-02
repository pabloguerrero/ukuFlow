package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */

public class UkuProcess implements VisitableElement {

	public List<UkuEntity> entities;
	List<String> errors = new LinkedList<String>();
	// public String name;
	private boolean correctness = true;
	// private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();
	public List<UkuElement> elements;
	public List<UkuScope> scopes;
	/**
	 * unique id
	 */
	public String id;

	public UkuProcess(String id) {
		this.id = id;
	}

	public void setEntities(List<UkuEntity> entities) {
		this.entities = entities;

		scopes = new LinkedList<UkuScope>();
		elements = new LinkedList<UkuElement>();
		for (UkuEntity e : entities) {
			// ref.put(e.getID(), e);
			if (e instanceof UkuElement) {
				elements.add((UkuElement) e);
			}

		}
	}

	/*
	 * public HashMap<String,UkuEntity> getReference(){ return ref; }
	 */
	public boolean isCorrect() {
		for (UkuEntity e : entities) {
			correctness &= e.isCorrect();
		}
		return correctness;
	}

	public List<String> getErrorMessages() {
		for (UkuEntity e : entities) {
			errors.addAll(e.getError());
		}
		return errors;
	}

	public List<UkuElement> getElements() {
		// TODO: is this enough?
		return elements;
	}

	public byte getNumberOfScope() {
		// TODO counting number of scope in the process
		return 0;
	}

	public byte getWorkflowID() {
		// TODO cope with inteference
		return (byte) (Math.abs(id.hashCode()) % 256);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public List<UkuScope> getScope() {
		return scopes;
	}

}
