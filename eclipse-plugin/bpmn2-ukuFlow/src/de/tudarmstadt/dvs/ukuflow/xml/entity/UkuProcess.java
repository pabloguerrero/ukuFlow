package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */

public class UkuProcess implements VisitableElement {

	private List<UkuEntity> entities;
	public UkuEvent startEvent = null;
	// private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();

	public List<UkuScope> scopes = new LinkedList<UkuScope>();
	/**
	 * unique id
	 */
	public String id;

	public String name;

	public UkuProcess(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addEntities(List<UkuEntity> newEntities) {
		for (UkuEntity e : newEntities) {
			addEntity(e);
		}
	}

	public void addEntity(UkuEntity newEntity) {
		if (newEntity instanceof UkuScope) {
			if (scopes == null)
				scopes = new LinkedList<UkuScope>();
			scopes.add((UkuScope) newEntity);
			return;
		} else if (entities.contains(newEntity)) {
			System.err.println("double element " + newEntity.getID());
			return;
		}
		if (startEvent == null && newEntity instanceof UkuEvent
				&& ((UkuEvent) newEntity).getType() == UkuConstants.START_EVENT) {
			startEvent = (UkuEvent) newEntity;
		}
		entities.add(newEntity);
	}

	public void removeEntity(UkuEntity e) {
		entities.remove(e);
		scopes.remove(e);
	}

	public void removeEntities(List<UkuEntity> es) {
		entities.removeAll(es);
		scopes.removeAll(es);
	}

	public void setEntities(List<UkuEntity> entities) {
		this.entities = new LinkedList<UkuEntity>();
		this.scopes = new LinkedList<UkuScope>();
		for (UkuEntity e : entities) {
			if (e instanceof UkuScope)
				scopes.add((UkuScope) e);
			else
				addEntity(e);
		}

	}

	public void setScopes(List<UkuScope> scopes) {
		this.scopes = scopes;
	}

	public void addScope(UkuScope scope) {
		this.scopes.add(scope);
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
		if (scopes != null)
			return (byte) scopes.size();
		return 0;
	}

	public byte getWorkflowID() {
		if (name == null)
			return (byte) (Math.abs(id.hashCode()) % 256);
		return (byte) (Math.abs(name.hashCode()) % 256);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public List<UkuScope> getScope() {
		return scopes;
	}

}
