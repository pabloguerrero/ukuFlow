package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Hien Quoc Dang
 *
 */

public class ukuProcess {
	
	public List<Entity> entities = new LinkedList<Entity>();
	
	public String name;
	private boolean correctness = false; 
	
	/**
	 * unique id
	 */
	public String id;
	
	public ukuProcess(String id) {
		this.id = id;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
		
		for(Entity e : entities){
			correctness &= e.isCorrect();
		}
	}
	
}
