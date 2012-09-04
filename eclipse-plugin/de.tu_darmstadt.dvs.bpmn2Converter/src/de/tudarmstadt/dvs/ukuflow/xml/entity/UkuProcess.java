package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.validation.ErrorMessage;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */

public class UkuProcess implements VisitableElement {

	private List<UkuEntity> entities;
	List<ErrorMessage> errors = new LinkedList<ErrorMessage>();
	// public String name;
	private boolean correctness = true;
	// private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();

	public List<UkuScope> scopes;
	/**
	 * unique id
	 */
	public String id;

	public UkuProcess(String id) {
		this.id = id;
	}

	public void addEntities(List<UkuEntity> newEntities) {
		for (UkuEntity e : newEntities) {
			addEntity(e);
		}
	}

	public void addEntity(UkuEntity newEntity) {
		if (entities.contains(newEntity)) {
			System.out.println("double element " + newEntity.getID());
			return;
		}
		entities.add(newEntity);
	}

	public void removeEntity(UkuEntity e) {
		entities.remove(e);
	}

	public void removeEntities(List<UkuEntity> es) {
		entities.removeAll(es);
	}

	public void setEntities(List<UkuEntity> entities) {
		this.entities = entities;
		scopes = new LinkedList<UkuScope>();

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

	public List<ErrorMessage> getErrorMessages() {
		for (UkuEntity e : entities) {
			errors.addAll(e.getError());
		}
		return errors;
	}

	public List<UkuElement> getElements() {
		final List<UkuElement> elements = new LinkedList<UkuElement>();
		for (UkuEntity e : entities) {
			// ref.put(e.getID(), e);
			if (e instanceof UkuElement) {
				elements.add((UkuElement) e);
			}
		}
		return elements;
	}

	public List<UkuEntity> getEntities() {
		return new LinkedList<UkuEntity>(entities);
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
