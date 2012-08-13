package de.tudarmstadt.dvs.ukuflow.script.expression;

import java.util.LinkedList;
import java.util.List;

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
	
	/*********************************************/

	private List<Variable> variables;

	private VariableManager() {
		variables = new LinkedList<Variable>();
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
	public static void destroy() {
		INSTANCE = null;
	}

	/**
	 * add a variable to VariableManager instance
	 * 
	 * @param v
	 */
	public void addVariable(Variable v) {
		variables.add(v);
	}

	/**
	 * 
	 * @param v
	 * @return {@code true} if the variable v exists already, {@code false}
	 *         otherwise
	 */
	public boolean containsVariable(Variable v) {
		return variables.contains(v);
	}

	@Override
	public String toString() {
		return variables.toString();
	}
	public List<Variable> getVariables(){
		final List<Variable> result = new LinkedList<Variable>(variables);
		return result;
	}
}
