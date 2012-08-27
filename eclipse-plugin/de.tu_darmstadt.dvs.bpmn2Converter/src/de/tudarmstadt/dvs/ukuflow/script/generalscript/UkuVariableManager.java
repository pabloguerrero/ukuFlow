package de.tudarmstadt.dvs.ukuflow.script.generalscript;

import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyVariableException;

public class UkuVariableManager {
	public static final int DEFAULT_NODE_ID = 13;
	private Map<String, Integer> mapping = new HashMap<String, Integer>();
	public static final int MAX_NUMBER_OF_VARIABLE = 256;
	private int current_id = DEFAULT_NODE_ID; //TODO
	public boolean isRegistered(String v) {
		
		return mapping.containsKey(v);
	}

	public Integer getIDOf(String var) {
		if (!isRegistered(var))
			return null;
		else
			return mapping.get(var);
	}

	public boolean registerVariable(String var) throws TooManyVariableException{
		if (isRegistered(var))
			return false;
		else if(current_id >= MAX_NUMBER_OF_VARIABLE){
			throw new TooManyVariableException();			
		} else {
			current_id++;
			mapping.put(var, current_id);
			return true;
		}
	}
	
	public Map<String,Integer> getMap(){		
		final Map<String, Integer> result = new HashMap<String,Integer>(mapping);		
		return result;
	}
}
