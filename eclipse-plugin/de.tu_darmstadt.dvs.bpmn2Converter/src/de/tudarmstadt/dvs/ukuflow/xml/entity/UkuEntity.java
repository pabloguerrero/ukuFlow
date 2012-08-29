package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.Visitable;

/**
 * @author Hien Quoc Dang
 *
 */
public abstract class UkuEntity implements VisitableElement{
	
	protected boolean syntax = false;
	protected List<String> errors = new LinkedList<String>();
	protected List<String> warnings = new LinkedList<String>();
	
	/**
	 * an unique id of for each entity
	 */
	protected String id;
	
	public UkuEntity(String id){
		this.id = id;
	}
	
	public String getID(){
		return id;
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
		errors.add("ERROR:\t at element '"+id+"' -> " + msg);
	}
	
	public void addWarningMessage(String msg){
		warnings.add("WARNING:\t at element '"+id+"' -> "+msg);
	}
	
	/**
	 * 
	 * @return true if the syntax of this entity was correct
	 */
	public  boolean isCorrect(){
		return syntax;
	}
	public abstract void setReference(Map<String, UkuEntity> ref);
	@Override
	public String toString(){
		return id;
	}
}
