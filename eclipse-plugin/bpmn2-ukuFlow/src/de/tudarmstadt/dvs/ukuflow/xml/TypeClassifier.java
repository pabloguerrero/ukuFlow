package de.tudarmstadt.dvs.ukuflow.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnsupportedElementException;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEventGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuExclusiveGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuInclusiveGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuParallelGateway;

public class TypeClassifier {
	private Set<String> connectors = new HashSet<String>();
	private Set<String> tasks = new HashSet<String>();
	private Set<String> gateways = new HashSet<String>();
	private Set<String> events = new HashSet<String>();
	private Set<String> textAnnotation = new HashSet<String>();
	private static TypeClassifier INSTANCE = null;
	private Map<String, Class> gatewayClass = new HashMap<String, Class>();
	BpmnLog log = BpmnLog.getInstance(TypeClassifier.class.getSimpleName());

	private TypeClassifier() {
		connectors.add("sequenceFlow");

		tasks.add("scriptTask");
		//tasks.add("userTask");

		events.add("endEvent");
		events.add("startEvent");
		//events.add("intermediateThrowEvent");
		//events.add("intermediateCatchEvent");
		
		gateways.add("parallelGateway");
		gateways.add("exclusiveGateway");
		gateways.add("complexGateway");
		gateways.add("eventBasedGateway");
		gateways.add("inclusiveGateway");
		
		textAnnotation.add("textAnnotation");
		
		gatewayClass.put("parallelGateway", UkuParallelGateway.class);
		gatewayClass.put("inclusiveGateway", UkuInclusiveGateway.class);
		gatewayClass.put("exclusiveGateway", UkuExclusiveGateway.class);
		gatewayClass.put("eventBasedGateway", UkuEventGateway.class);
	}

	public static TypeClassifier getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TypeClassifier();
		}
		return INSTANCE;
	}
	public Class getGatewayClass(String name){
		return gatewayClass.get(name);
	}
	public int getGatewayType(UkuGateway gway)
			throws UnspecifiedGatewayException {
		String name = gway.getElementType();
		//log.debug("gateway " + name + " -> " + gway.getType());
		switch (gway.calculateType()) {
		case 1:// Converging
			if (name.equals("parallelGateway"))
				return UkuConstants.JOIN_GATEWAY;
			else if (name.equalsIgnoreCase("inclusiveGateway"))
				return UkuConstants.INCLUSIVE_JOIN_GATEWAY;
			else if (name.equalsIgnoreCase("exclusiveGateway"))
				return UkuConstants.EXCLUSIVE_MERGE_GATEWAY;

			else if (name.equals("eventBasedGateway")) {
				// TODO this won't happen
				// return UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY;
				return UkuConstants.EXCLUSIVE_MERGE_GATEWAY;
			}

			break;
		case 2:// Deverging
			if (name.equalsIgnoreCase("parallelGateway"))
				return UkuConstants.FORK_GATEWAY;
			else if (name.equalsIgnoreCase("inclusiveGateway"))
				return UkuConstants.INCLUSIVE_DECISION_GATEWAY;
			else if (name.equalsIgnoreCase("exclusiveGateway"))
				return UkuConstants.EXCLUSIVE_DECISION_GATEWAY;
			else if (name.equalsIgnoreCase("eventBasedGateway"))
				return UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY;
			break;		
		}
		throw new UnspecifiedGatewayException(name + " is unspecified");

	}

	/**
	 * get type of element base on the xml tag.
	 * @param name (xml tag)
	 * @return type
	 * <li> 1 : connector </li>
	 * <li> 2 : task </li> 
	 * <li> 3 : event </li>
	 * <li> 4 : gateway </li>
	 * <li> 5 : textAnnotation </li>
	 * 
	 * @throws UnsupportedElementException
	 */
	public int getType(String name) throws UnsupportedElementException {
		if (connectors.contains(name))
			return 1;
		if (tasks.contains(name))
			return 2;
		if (events.contains(name))
			return 3;
		if (gateways.contains(name))
			return 4;
		if (textAnnotation.contains(name))
			return 5;

		throw new UnsupportedElementException(name);
	}

	public int getEventType(String name) throws UnsupportedElementException {
		// TODO:
		// "intermediateThrowEvent"
		// "intermediateCatchEvent"

		if (name.equalsIgnoreCase("endEvent"))
			return UkuConstants.END_EVENT;
		if (name.equalsIgnoreCase("startEvent"))
			return UkuConstants.START_EVENT;
		throw new UnsupportedElementException("event " + name + " is undefined");

	}

}
