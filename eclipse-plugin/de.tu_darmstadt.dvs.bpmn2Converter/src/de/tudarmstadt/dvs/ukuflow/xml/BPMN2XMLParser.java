package de.tudarmstadt.dvs.ukuflow.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.console.MessageConsoleStream;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnsupportedElementException;
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
	
	Map<String, UkuEntity> reference;
	Element root;
	MessageConsoleStream out = null;
	TypeClassifier classifier = TypeClassifier.getInstance();
	List<UkuProcess> processes = null;

	public BPMN2XMLParser(String file, MessageConsoleStream out) {
		reInit(file, out);
	}

	public void reInit(String fileLocation, MessageConsoleStream out) {
		reference = new HashMap<String, UkuEntity>();
		root = loadFile(fileLocation);
		this.out = out;
	}
	/**
	 * this function will fetch all process, set the reference and the element id for each elements
	 */
	public void executeFetch(){
		
		//fetching processes
		processes = fetchProcesses(root);
		
		// set reference
		for (UkuProcess process : processes) {
			for (UkuEntity entity : process.entities) {
				entity.setReference(reference);
			}
		}
		
		for(UkuProcess up : processes){
			byte id = 0;					
			for(UkuEntity ue:up.entities){
				if(ue instanceof UkuElement){
					((UkuElement)ue).setElementID(id);
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
			out.println(e.getName()
					+ " is not a 'definitions' of bpmn2 file -> ignored");
		}
		return result;
	}

	private UkuProcess fetchUkuProcess(Element e) {
		UkuProcess result = null;
		if (e.getName().equals("process")) {
			result = new UkuProcess(e.getAttributeValue("id"));
			result.setEntities(fetchEntities(e));
		} else {
			out.println("WARNING: element '" + e.getName()
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
			out.println(name + " is not supported : " + e1.getMessage());
			return null;
		}
		String id = e.getAttributeValue("id");
		switch (type) {
		case 1:
			String sourceRef = e.getAttributeValue("sourceRef");
			String targetRef = e.getAttributeValue("targetRef");
			UkuSequenceFlow result = new UkuSequenceFlow(id, sourceRef,
					targetRef);

			for (Element child : e.getChildren()) {
				String childName = child.getName();
				if (childName.equals("conditionExpression")) {
					String condition = child.getTextTrim();
					System.out.println(condition);
					((UkuSequenceFlow) result).setCondition(condition);
				} else {
					log.info("Unknown child: " + childName + " -> ignored");
				}
			}
			return result;
		case 2:
			UkuExecuteTask et = new UkuExecuteTask(id);
			for (Element child : e.getChildren()) {
				log.debug(et.getID() + " " + child.getName());
				if (child.getName().equals("incoming")) {
					log.debug("add incoming");
					et.addIncoming(child.getTextTrim());					
				} else if (child.getName().equals("outgoing")) {
					log.debug("add outgoing");
					et.addOutgoing(child.getTextTrim());
				} else if (child.getName().equals("script")) {
					log.debug("add script");
					et.setScript(child.getTextTrim());
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
					out.println("WARNING!! unknown child : " + child.getName());
				}
			}
			return event;
		case 4: // gateways
			UkuGateway gway = new UkuGateway(id);
			gway.setElementType(name);
			String direction = e.getAttributeValue("gatewayDirection");
			String defaultGway =e.getAttributeValue("default");
			gway.setDefaultGway(defaultGway);
			if(direction != null){
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
					out.println("WARNING!! unknown child : " + child.getName());
					// TODO

				}
			}
			return gway;

		default:
			out.println("WARNING, unknown element: " + type + "/" + e.getName());
			// TODO:
			return null;
		}
	}
}
