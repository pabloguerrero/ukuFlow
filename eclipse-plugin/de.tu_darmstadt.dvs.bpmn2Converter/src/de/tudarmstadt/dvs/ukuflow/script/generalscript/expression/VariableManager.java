package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.constant.EventTypes;
import de.tudarmstadt.dvs.ukuflow.constant.RepositoryField;
import de.tudarmstadt.dvs.ukuflow.constant.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.constant.WorkflowTypes;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.NotRegisteredVariableException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyVariableException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.VariableAlreadyExistException;

/**
 * this class represent a variable table, which contains all created and used
 * variable of a process
 * 
 * @author Hien Quoc Dang
 * 
 */
public class VariableManager {
	
	/** static declaration of VariableManage instance*/
	private static VariableManager INSTANCE = null;
	public static final int NODE_ID = UkuConstants.NODE_ID;
	private int current_id;
	private static BpmnLog log = BpmnLog.getInstance(VariableManager.class.getSimpleName());
	public static final int MAX_ID = 255 - NODE_ID;
	
	/*********************************************/

	private Map<String,Integer> variables;

	private VariableManager() {
		variables = new HashMap<String,Integer>();
		current_id = NODE_ID;
	}

	/**
	 * 
	 * @return the instance of VariableManager
	 */
	public static VariableManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new VariableManager();
		return INSTANCE;

	}

	/**
	 * destroy the current instance. all variables inside the VariableManager
	 * will be lost
	 */
	public void destroy() {
		INSTANCE = null;
	}
	public void reInit(){
		variables = new HashMap<String, Integer>();
		current_id = NODE_ID;
	}
	public int getVariableID(String name){
		if(name.startsWith("$"))
			name = name.substring(1);
		if(variables.containsKey(name))
			return variables.get(name);
		return -1;
	}
	
	/**
	 * add a variable to VariableManager instance
	 * 
	 * @param v
	 * @throws VariableAlreadyExistException 
	 */
	public int registerVariable(UkuVariable v) throws TooManyVariableException, VariableAlreadyExistException{
		
		if(variables.containsKey(v.name)){
			throw new VariableAlreadyExistException();
		}
		
		if(current_id > MAX_ID) 
			throw new TooManyVariableException();
		
		v.setID(current_id);
		variables.put(v.name,current_id);
		current_id ++;
		return current_id-1;
		
	}

	/**
	 * 
	 * @param v
	 * @return {@code true} if the variable v exists already, {@code false}
	 *         otherwise
	 */
	public boolean containsVariable(UkuVariable v) {
		return variables.containsKey(v.name);
	}

	@Override
	public String toString() {
		return variables.toString();
	}
	public Map<String, Integer> getVariables(){
		final Map<String, Integer> result = new HashMap<String, Integer>(variables);
		return result;
	}
}
