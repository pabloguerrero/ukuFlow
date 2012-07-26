package de.tudarmstadt.dvs.ukuflow.xml;

import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.exception.UnsupportedElementException;

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
		events.add("intermediateThrowEvent");
		events.add("intermediateCatchEvent");
		
		gateways.add("parallelGateway");
		gateways.add("exclusiveGateway");
		gateways.add("complexGateway");
		gateways.add("eventBasedGateway");
		gateways.add("inclusiveGateway");
		
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
	 * @throws UnsupportedElementException
	 */
	public int getType(String name) throws UnsupportedElementException{		
		if(connectors.contains(name)) return 1;
		if(tasks.contains(name)) return 2;
		if(events.contains(name)) return 3;
		if(gateways.contains(name)) return 4;
		
		throw new UnsupportedElementException(name);
	}
	
}
