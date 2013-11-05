/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

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
	private Set<String> receiveTask = new HashSet<String>();
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
		
		receiveTask.add("receiveTask");
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
				return UkuConstants.WorkflowOperators.JOIN_GATEWAY;
			else if (name.equalsIgnoreCase("inclusiveGateway"))
				return UkuConstants.WorkflowOperators.INCLUSIVE_JOIN_GATEWAY;
			else if (name.equalsIgnoreCase("exclusiveGateway"))
				return UkuConstants.WorkflowOperators.EXCLUSIVE_MERGE_GATEWAY;

			else if (name.equals("eventBasedGateway")) {
				// TODO this won't happen
				// return UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY;
				return UkuConstants.WorkflowOperators.EXCLUSIVE_MERGE_GATEWAY;
			}

			break;
		case 2:// Deverging
			if (name.equalsIgnoreCase("parallelGateway"))
				return UkuConstants.WorkflowOperators.FORK_GATEWAY;
			else if (name.equalsIgnoreCase("inclusiveGateway"))
				return UkuConstants.WorkflowOperators.INCLUSIVE_DECISION_GATEWAY;
			else if (name.equalsIgnoreCase("exclusiveGateway"))
				return UkuConstants.WorkflowOperators.EXCLUSIVE_DECISION_GATEWAY;
			else if (name.equalsIgnoreCase("eventBasedGateway"))
				return UkuConstants.WorkflowOperators.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY;
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
	 * <li> 6 : ReceiveTask </li>
	 * <li> 7 : eventbaseGateway </li>
	 * <li> 8 : immediateCatchEvent </li>
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
		if (receiveTask.contains(name))
			return 6;
		if (name.equals("eventBasedGateway"))
			return 7;
		if(name.equals("intermediateCatchEvent")){
			return 8;
		}
		throw new UnsupportedElementException(name);
	}

	public int getEventType(String name) throws UnsupportedElementException {
		// TODO:
		// "intermediateThrowEvent"
		// "intermediateCatchEvent"

		if (name.equalsIgnoreCase("endEvent"))
			return UkuConstants.WorkflowOperators.END_EVENT;
		if (name.equalsIgnoreCase("startEvent"))
			return UkuConstants.WorkflowOperators.START_EVENT;
		throw new UnsupportedElementException("event " + name + " is undefined");

	}

}
