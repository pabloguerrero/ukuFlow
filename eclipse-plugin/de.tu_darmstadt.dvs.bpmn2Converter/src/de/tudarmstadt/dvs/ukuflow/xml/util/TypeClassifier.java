package de.tudarmstadt.dvs.ukuflow.xml.util;

import java.util.HashSet;
import java.util.Set;

public class TypeClassifier {
	private Set<String> connectors = new HashSet<String>();
	private Set<String> tasks = new HashSet<String>();
	private Set<String> gateways = new HashSet<String>();
	private Set<String> events = new HashSet<String>();
	private static TypeClassifier INSTANCE = null;
	private TypeClassifier(){
		connectors.add("sequenceFlow");
		
		tasks.add("scriptTask");
		tasks.add("userTask");
		
		events.add("endEvent");
		events.add("startEvent");
		
	}
	public static TypeClassifier getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TypeClassifier();
		}
		return INSTANCE;
	}
	/**
	 * 
	 * @param name 
	 * @return 
	 * 1 : connector
	 * 2 : task
	 * 3 : event
	 * 4 : gateway
	 */
	public int getType(String name){
		if(name.endsWith("Event")) return 3;
		if(name.endsWith("Flow") || name.equals("association")) return 1;
		if(name.endsWith("Task") || name.endsWith("task")) return 2;
		if(name.endsWith("Gateway")) return 4;
		return 0;
	}
	
}
