package de.tudarmstadt.dvs.ukuflow.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.jdom2.

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.resources.IFile;
import org.jdom2.Element;

import de.tudarmstadt.dvs.ukuflow.deployment.DeviceFinder;
import de.tudarmstadt.dvs.ukuflow.exception.UnsupportedElementException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import de.tudarmstadt.dvs.ukuflow.xml.XMLNode;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEntity;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuExecuteTask;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuSequenceFlow;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEvent;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;

public class ConvertCommand extends AbstractHandler {
	/**
	 * Console name
	 */
	public static final String CONSOLE_NAME = "bpmn2 converter";

	TypeClassifier classifier = TypeClassifier.getInstance();

	MessageConsole myConsole = null;
	MessageConsoleStream out = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		myConsole = findConsole(CONSOLE_NAME);
		out = myConsole.newMessageStream();
		// focus and bring the console in front of other tabs when something are
		// being printed out
		out.setActivateOnWrite(true);

		System.out.println(DeviceFinder.getInstance().getDevices());
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		Object firstElement = selection.getFirstElement();
		if (selection.size() != 1) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please choose just one BPMN2 file");
			return null;
		}
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;
			String extension = file.getFileExtension();
			String oFileLocation = file.getLocation().toOSString();

			String nfileLocation = oFileLocation + "\n";
			nfileLocation = nfileLocation.replace(extension + "\n", "ukuf");
			if (extension.equals("bpmn") || extension.equals("bpmn2")) {
				// TODO: check, convert bpmn code to Ukuflow byte code
				out.println("checking..");
				out.println("converting..");

				BPMN2XMLParser parser = new BPMN2XMLParser();
				Element e = parser.loadFile(oFileLocation);
				XMLNode root = new XMLNode(e);
				List<UkuProcess> processes = fetchProcesses(e);
				out.println(processes.size()+"");
				/*
				 * File f = new File(oFileLocation); FileWriter fwrite = null;
				 * try { fwrite = new FileWriter(f); fwrite.write(content);
				 * fwrite.flush(); fwrite.close(); } catch (IOException ex) {
				 * ex.printStackTrace(); }
				 */
			} else {
				MessageDialog.openInformation(
						HandlerUtil.getActiveShell(event), "Information",
						"Please select a BPMN or BPMN2 source file");
			}

		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please select a BPMN or BPMN2 source file");
		}
		/*
		 * if (firstElement instanceof ICompilationUnit) { ICompilationUnit cu =
		 * (ICompilationUnit) firstElement; IResource res = cu.getResource();
		 * boolean newDirectory = true; directory = getPersistentProperty(res,
		 * path); System.out.println("directory: " + directory); if (directory
		 * != null && directory.length() > 0) { newDirectory =
		 * !(MessageDialog.openQuestion( HandlerUtil.getActiveShell(event),
		 * "Question", "Use the previous output directory?")); } if
		 * (newDirectory) { DirectoryDialog fileDialog = new DirectoryDialog(
		 * HandlerUtil.getActiveShell(event)); directory = fileDialog.open();
		 * 
		 * } if (directory != null && directory.length() > 0) { analyze(cu);
		 * setPersistentProperty(res, path, directory); write(directory, cu); }
		 * 
		 * } else {
		 * MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
		 * "Information", "Please select a Java source file"); }
		 */
		return null;
	}

	/*
	 * protected String getPersistentProperty(IResource res, QualifiedName qn) {
	 * try { return res.getPersistentProperty(qn); } catch (CoreException e) {
	 * return ""; } }
	 */

	/*
	 * protected void setPersistentProperty(IResource res, QualifiedName qn,
	 * String value) { try { res.setPersistentProperty(qn, value); } catch
	 * (CoreException e) { e.printStackTrace(); } }
	 */

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
			out.println("WARNING: element '" + e.getName() + "' is not a process -> ignored");
		}
		return result;
	}

	private List<UkuEntity> fetchEntities(Element e) {
		List<UkuEntity> result = new LinkedList<UkuEntity>();
		if (e.getName().equals("process")) {
			for (Element child : e.getChildren()) {
				UkuEntity tmp = fetchEntity(child);
				if (tmp != null)
					result.add(tmp);
				else {
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
			out.println(name + " is not supported : "+e1.getMessage());
			return null;
		}
		String id = e.getAttributeValue("id");
		switch (type) {
		case 1:
			String sourceRef = e.getAttributeValue("sourceRef");
			String targetRef = e.getAttributeValue("targetRef");
			UkuSequenceFlow result = new UkuSequenceFlow(id, sourceRef, targetRef);

			for (Element child : e.getChildren()) {
				if (child.getName().equals("conditionExpression"))
					;
				String condition = child.getTextTrim();
				((UkuSequenceFlow) result).setCondition(condition);
				break;
			}
			return result;
		case 2:
			UkuExecuteTask et = new UkuExecuteTask(id);
			for (Element child : e.getChildren()) {
				if (child.getName().equals("incoming"))
					continue;
				if (child.getName().equals("outgoing")) {
					et.setNextEntityID(child.getTextTrim());
					continue;
				}
				if (child.getName().equals("script")) {
					et.setScript(child.getTextTrim());
				}
			}
			return et;
		case 3:
			UkuEvent event = new UkuEvent(id);
			return event;
		case 4: // gateways
			UkuGateway gway = new UkuGateway(id);
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

	/*******************************************************************************************/
	/**
	 * find the console with name = {@code name}. If no console is found, create
	 * one.
	 * 
	 * @param name
	 * @return
	 */
	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}
