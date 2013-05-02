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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import de.tudarmstadt.dvs.ukuflow.handler.UkuConsole;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnsupportedElementException;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEntity;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEvent;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuExecuteTask;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuReceiveTask;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuScope;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuSequenceFlow;

/**
 * reading xml file will be executed in this class
 * 
 * @author Hien Quoc Dang
 * 
 */
public class BPMN2XMLParser {

	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	Set<String> idPool = new HashSet<String>();
	public static final String DEFAULT_GENERATED_ID = "generated_id_";

	ErrorManager errorManager = ErrorManager.getInstance();
	/**
	 * This reference maps each id(String) to its UkuEntity (one-to-one)
	 */
	Map<String, UkuEntity> reference;
	Element root;
	UkuConsole console = UkuConsole.getConsole();

	TypeClassifier classifier = TypeClassifier.getInstance();
	List<UkuProcess> processes = null;

	public BPMN2XMLParser(String file) {
		reInit(file);
	}

	public void reInit(String fileLocation) {
		reference = new HashMap<String, UkuEntity>();
		root = loadFile(fileLocation);
	}

	public int getProcessID() {
		if (root.getName().equals("definitions")) {
			for (Element child : root.getChildren()) {
				if (child.getName().equals("process")) {
					UkuProcess process = new UkuProcess(fetchID(child),fetchName(child));
					return process.getWorkflowID();
				}
			}
		}
		throw new NullPointerException();
	}

	/**
	 * this function will fetch all process, set the reference and the element
	 * id for each elements
	 */
	public void executeFetch() {

		// fetching processes
		processes = fetchProcesses(root);

		for (UkuProcess process : processes) {
			// optimization
			//optimize(process);
			
			// set reference
			for (UkuEntity entity : process.getEntities()) {
				entity.setReference(reference);
			}
						
		}
	}

	public List<UkuProcess> getProcesses() {
		return processes;
	}

	/**
	 * fetching elements from the file
	 * 
	 * @param configFile
	 * @return
	 */
	private Element loadFile(String configFile) {
		File file = new File(configFile);
		if (!file.isFile()) {
			throw new IllegalArgumentException("File is a directory!");
		}
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			return loadXML(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	private Element loadXML(InputStream in) {
		SAXBuilder builder = new SAXBuilder();
		// builder.setValidation(false);
		builder.setIgnoringElementContentWhitespace(true);
		try {
			Document doc = builder.build(in);
			Element root = doc.getRootElement();

			return root;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<UkuProcess> fetchProcesses(Element e) {
		List<UkuProcess> result = new LinkedList<UkuProcess>();
		if (e.getName().equals("definitions")) {
			for (Element child : e.getChildren()) {
				UkuProcess tmp = fetchUkuProcess(child);
				if (tmp != null)
					result.add(tmp);
			}
		} else {
			log.debug(e.getName()
					+ " is not a 'definitions' of bpmn2 file -> ignored");
		}
		return result;
	}

	private UkuProcess fetchUkuProcess(Element e) {
		UkuProcess result = null;
		if (e.getName().equals("process")) {
			//String name = fetchName(e);
			result = new UkuProcess(fetchID(e),fetchName(e));			
			result.setEntities(fetchEntities(e));
			if(result.name==null || result.name.equals("")){
				errorManager.addError(result.id, "name of the process is not specified");
			} else if(result.name.equals("Default Process")){
				errorManager.addWarning("process: " +result.name, "default name for process is used, it could lead to interference with other processes");
			}
		} else {
			log.debug("WARNING: element '" + e.getName()
					+ "' is not a process -> ignored");
		}
		return result;
	}

	private List<UkuEntity> fetchEntities(Element e) {
		List<UkuEntity> result = new LinkedList<UkuEntity>();
		if (e.getName().equals("process")) {
			for (Element child : e.getChildren()) {
				UkuEntity tmp = fetchEntity(child);
				if (tmp != null) {
					result.add(tmp);
					reference.put(tmp.getID(), tmp);
				} else {
					// System.out.println("not a process");
				}

			}
		}
		return result;
	}
	/**
	 * 
	 * @param e
	 * @return null if element is not supported
	 */
	private UkuEntity fetchEntity(Element e) {
		String name = e.getName();
		int type = 0;
		try {
			type = classifier.getType(name);
		} catch (UnsupportedElementException e1) {
			errorManager.addWarning(e.getParentElement()
					.getAttributeValue("id"), name + " is not supported");
			log.debug(name + " is not supported : " + e1.getMessage());
			return null;
		}
		System.out.println("["+this.getClass().getSimpleName()+"]" +
				"\t"+type);
		
		String id = fetchID(e);
		switch (type) {
		case 1:
			String sourceRef = e.getAttributeValue("sourceRef");
			String targetRef = e.getAttributeValue("targetRef");
			String priority = e.getAttributeValue("priority");

			UkuSequenceFlow result = new UkuSequenceFlow(id, sourceRef,
					targetRef);

			if (priority != null)
				try {
					int p = Integer.parseInt(priority);
					result.setPriority(p);
				} catch (Exception e2) {
					// not a number
				}

			for (Element child : e.getChildren()) {
				String childName = child.getName();
				if (childName.equals("conditionExpression")) {
					String condition = child.getTextTrim();
					((UkuSequenceFlow) result).setCondition(condition);
				} else {
					errorManager.addWarning(id, "Unknown property of "
							+ childName + " -> ignored");
				}
			}
			return result;
		case 2:
			UkuExecuteTask et = new UkuExecuteTask(id);
			for (Element child : e.getChildren()) {
				log.debug(et.getID() + " " + child.getName());
				if (child.getName().equals("incoming")) {
					et.addIncoming(child.getTextTrim());
				} else if (child.getName().equals("outgoing")) {
					et.addOutgoing(child.getTextTrim());
				} else if (child.getName().equals("script")) {
					et.setScript(child.getTextTrim());
				} else {
					errorManager.addWarning(id,
							"Unknown property of " + child.getName()
									+ " -> ignored");
				}
			}
			return et;
		case 3:
			UkuEvent event = new UkuEvent(id);
			for (Element child : e.getChildren()) {
				String n = child.getName();
				String n_id = child.getTextTrim();
				if (n.equals("incoming")) {
					event.addIncoming(n_id);
				} else if (n.equals("outgoing")) {
					event.addOutgoing(n_id);
				} else {
					errorManager.addWarning(id,
							"Unknown property of " + child.getName()
									+ " -> ignored");
				}
			}
			try {
				event.setType(classifier.getEventType(name));
			} catch (UnsupportedElementException e1) {
				errorManager.addError(id, name + " is not supported");
				event.setType(-1);
			}
			return event;
		case 4: // gateways
			UkuGateway gway = null;
			try {
				gway = (UkuGateway) classifier.getGatewayClass(name).getConstructor(String.class).newInstance(id);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}			
			gway.setElementType(name);
			String direction = e.getAttributeValue("gatewayDirection");
			String isdefaultGway = e.getAttributeValue("default");
			gway.setDefaultGway(isdefaultGway);			
			gway.setDirection(direction);
			
			for (Element child : e.getChildren()) {
				String n = child.getName();
				String n_id = child.getTextTrim();
				if (n.equals("incoming")) {
					gway.addIncoming(n_id);
				} else if (n.equals("outgoing")) {
					gway.addOutgoing(n_id);
				} else {
					errorManager.addWarning(id,
							"Unknown property of " + child.getName()
									+ " -> ignored");
				}
			}
			return gway;
		case 5: // Text Annotation
			UkuScope scope = new UkuScope(id);
			String scope_desc = "";
			for (Element child : e.getChildren()) {
				if (child.getName().equals("text")) {
					scope_desc = child.getTextTrim();
					scope.setScript(scope_desc);
					break;
				}
			}
			return scope;
		case 6: // Receive Task
			//TODO UkuR
			UkuReceiveTask rTask = new UkuReceiveTask(id);
			for(Element ee : e.getChildren()){
				String n = ee.getName();
				String n_id = ee.getTextTrim();
				if (n.equals("incoming")) {
					rTask.addIncoming(n_id);
				} else if (n.equals("outgoing")) {
					rTask.addOutgoing(n_id);
				}
			}
			String script = fetchEBScript(e);			
			rTask.setScript(script);
			return rTask;
		default:
			log.debug("WARNING, unknown element: " + type + "/" + e.getName());
			return null;
		}
	}
	private Element getChildwithName(Element e, String name){
		for(Element ee : e.getChildren()){
			if(ee.getName().equals(name))
				return ee;
		}
		return null;
	}
	private String fetchEBScript(Element e){
		Element dataInputA  = getChildwithName(e,"dataInputAssociation");
		if(dataInputA == null)
			return null;
		Element assignment = getChildwithName(dataInputA,"assignment");
		if(assignment == null)
			return null;
		Element from = getChildwithName(assignment,"from");
		if(from == null)
			return null;
		return from.getTextTrim();
	}
	private String fetchID(Element e) {
		String id = e.getAttributeValue("id");
		idPool.add(id);
		return id;
	}
	private String fetchName(Element e){
		String name = e.getAttributeValue("name");
		return name;
	}

	/**
	 * this function will search for a mixed gateway in a process and try to
	 * split it in 2 simple gateway and also add a sequence flow between them
	 * 
	 * @param process 
	 * @deprecated
	 */
	private void optimize(UkuProcess process) {
		List<UkuEntity> tmp = new LinkedList<UkuEntity>();
		boolean done = false;
		while (!done) {
			for (UkuEntity entity : process.getEntities()) {
				if (entity instanceof UkuGateway) {
					UkuGateway gway = (UkuGateway) entity;
					if (gway.getOutgoingID().size() > 1
							&& gway.getIncomingID().size() > 1) {
						log.info("optimizing");
						process.removeEntity(gway);
						reference.remove(gway.getID());
						UkuGateway left = gway.clone(generateID(gway.getID()));
						left.setIncoming(gway.getIncomingID());
						for (String inc : left.getIncomingID()) {
							UkuSequenceFlow sf = (UkuSequenceFlow) reference
									.get(inc);
							sf.setTargetID(left.getID());
						}
						UkuGateway right = gway.clone(generateID(gway.getID()));
						right.setOutgoing(gway.getOutgoingID());
						for (String outg : right.getOutgoingID()) {
							UkuSequenceFlow sf = (UkuSequenceFlow) reference
									.get(outg);
							sf.setSourceID(right.getID());
						}
						UkuSequenceFlow seq = new UkuSequenceFlow(
								generateID("generated_sequenceflow"),
								left.getID(), right.getID());
						left.addOutgoing(seq.getID());
						right.addIncoming(seq.getID());

						tmp.add(left);
						reference.put(left.getID(), left);
						tmp.add(right);
						reference.put(right.getID(), right);
						tmp.add(seq);
						reference.put(seq.getID(), seq);
						process.addEntities(tmp);
						break;
					}
				}
			}
			done = true;
		}		
	}

	private String generateID(String rootID) {
		String newID = null;
		if (rootID == null)
			newID = DEFAULT_GENERATED_ID;
		else
			newID = rootID + "_";

		int i = 0;
		while (idPool.contains(newID + i)) {
			i++;
		}
		idPool.add(newID + i);
		log.info("add new id to pool -> " + (newID + i));
		return newID + i;
	}
}
