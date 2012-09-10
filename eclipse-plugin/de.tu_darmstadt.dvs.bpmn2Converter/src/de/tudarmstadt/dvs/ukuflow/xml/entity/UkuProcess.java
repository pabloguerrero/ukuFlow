package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */

public class UkuProcess implements VisitableElement {

	private List<UkuEntity> entities;

	// private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();

	public List<UkuScope> scopes = new LinkedList<UkuScope>();
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
		// scopes = new LinkedList<UkuScope>();

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
		// TODO cope with interference
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
