package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Hien Quoc Dang
 *
 */
public abstract class Entity {
	
	protected boolean syntax = false;
	protected List<String> errors = new LinkedList<String>();
	protected List<String> warnings = new LinkedList<String>();
	
	/**
	 * an unique id of for each entity
	 */
	public String id;
	
	public Entity(String id){
		this.id = id;
	}
	
	/**
	 * get the error messages
	 * @return
	 */
	public List<String> getError(){
		final List<String> result = new LinkedList<String>(errors);
		return result;
	}
	/**
	 * get the warning messages
	 * @return
	 */
	public List<String> getWarnings(){	
		final List<String> result = new LinkedList<String>(warnings);
		return result;
	}
	
	public void addErrorMessage(String msg){
		errors.add("ERROR:  \t"+id + "\t"+msg);
	}
	
	public void addWarningMessage(String msg){
		warnings.add("WARNING:\t"+id+"\t"+msg);
	}
	
	/**
	 * 
	 * @return true if the syntax of this entity was correct
	 */
	public  boolean isCorrect(){
		return syntax;
	}
}
