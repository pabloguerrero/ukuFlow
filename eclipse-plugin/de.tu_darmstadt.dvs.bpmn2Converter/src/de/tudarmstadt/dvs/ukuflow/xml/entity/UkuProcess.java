package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Hien Quoc Dang
 *
 */

public class UkuProcess {
	
	public List<UkuEntity> entities = new LinkedList<UkuEntity>();
	List<String> errors = new LinkedList<String>();
	public String name;
	private boolean correctness = true; 
	private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();
	/**
	 * unique id
	 */
	public String id;
	
	public UkuProcess(String id) {
		this.id = id;
	}

	public void setEntities(List<UkuEntity> entities) {
		this.entities = entities;
		
		for(UkuEntity e : entities){
			correctness &= e.isCorrect();
			errors.addAll(e.getError());
			ref.put(e.getID(), e);
		}
	}
	public HashMap<String,UkuEntity> getReference(){
		return ref;
	}
	public boolean isCorrect(){
		return correctness;
	}
	public List<String> getErrorMessages(){
		return errors;		
	}
}
