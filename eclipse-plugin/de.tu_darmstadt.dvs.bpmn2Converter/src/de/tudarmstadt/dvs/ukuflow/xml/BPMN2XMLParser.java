package de.tudarmstadt.dvs.ukuflow.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

	/**
	 * this function will fetch all process, set the reference and the element
	 * id for each elements
	 */
	public void executeFetch() {

		// fetching processes
		processes = fetchProcesses(root);

		for (UkuProcess process : processes) {
			// optimization
			optimize(process);
			// set reference
			for (UkuEntity entity : process.getEntities()) {
				entity.setReference(reference);
			}

			/*
			 * set workflow-element-id for each element this id will be used
			 * later in the bytecode format output
			 */
			byte id = 0;
			for (UkuEntity ue : process.getEntities()) {
				if (ue instanceof UkuElement) {
					((UkuElement) ue).setWorkflowElementID(id);
					id++;
				}
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
			result = new UkuProcess(fetchID(e));
			result.setEntities(fetchEntities(e));
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

	private UkuEntity fetchEntity(Element e) {
		String name = e.getName();
		int type = 0;
		try {
			type = classifier.getType(name);
		} catch (UnsupportedElementException e1) {
			errorManager.addError(e.getParentElement().getAttributeValue("id"),
					name + " is not supported");
			log.debug(name + " is not supported : " + e1.getMessage());
			return null;
		}
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
			UkuGateway gway = new UkuGateway(id);
			gway.setElementType(name);
			String direction = e.getAttributeValue("gatewayDirection");
			String defaultGway = e.getAttributeValue("default");
			gway.setDefaultGway(defaultGway);
			if (direction != null) {
				gway.setDirection(direction);
			}

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

		default:
			log.debug("WARNING, unknown element: " + type + "/" + e.getName());
			return null;
		}
	}

	private String fetchID(Element e) {
		String id = e.getAttributeValue("id");
		idPool.add(id);
		return id;
	}

	/**
	 * this function will search for a mixed gateway in a process and try to
	 * split it in 2 simple gateway and also add a sequence flow between them
	 * 
	 * @param process
	 * @need tests!
	 */
	private void optimize(UkuProcess process) {
		List<UkuEntity> tmp = new LinkedList<UkuEntity>();
		for (UkuEntity entity : process.getEntities()) {
			if (entity instanceof UkuGateway) {
				UkuGateway gway = (UkuGateway) entity;
				if (gway.getOutgoingID().size() > 1
						&& gway.getIncomingID().size() > 1) {
					System.out.println("optimizing");
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
							generateID("generated_sequenceflow"), left.getID(),
							right.getID());
					left.addOutgoing(seq.getID());
					right.addIncoming(seq.getID());

					tmp.add(left);
					reference.put(left.getID(), left);
					tmp.add(right);
					reference.put(right.getID(), right);
					tmp.add(seq);
					reference.put(seq.getID(), seq);
				}
			}
		}
		process.addEntities(tmp);
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
