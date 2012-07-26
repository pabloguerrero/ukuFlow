package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

public class ukuProcess {
	public List<Entity> entities = new LinkedList<Entity>();
	public String id;
	public String name;
	public ukuProcess(String id) {
		this.id = id;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	
}
