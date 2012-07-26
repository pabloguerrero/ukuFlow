package de.tudarmstadt.dvs.ukuflow.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

//import org.jdom2.

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.resources.IFile;
import org.jdom2.Attribute;
import org.jdom2.Element;

import de.tudarmstadt.dvs.ukuflow.deployment.DeviceFinder;
import de.tudarmstadt.dvs.ukuflow.exception.UnsupportedElementException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import de.tudarmstadt.dvs.ukuflow.xml.XMLNode;
import de.tudarmstadt.dvs.ukuflow.xml.entity.Entity;
import de.tudarmstadt.dvs.ukuflow.xml.entity.ExecuteTask;
import de.tudarmstadt.dvs.ukuflow.xml.entity.Gateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.SequenceFlow;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuflowEvent;
import de.tudarmstadt.dvs.ukuflow.xml.entity.ukuProcess;

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
				out.println("number of processes\t" + fetchProcesses(e).size());
				// printProcesses(e);
				// new Converter(input,null).execute();
				// Converter.execute(input);
				// new Converter(input, "").execute();
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

	private List<ukuProcess> fetchProcesses(Element e) {
		List<ukuProcess> result = new LinkedList<ukuProcess>();
		if (e.getName().equals("definitions")) {
			for (Element child : e.getChildren()) {
				ukuProcess tmp = fetchUkuProcess(child);
				if (tmp != null)
					result.add(tmp);
			}
		} else {
			out.println(e.getName()
					+ " is not a 'definitions' of bpmn2 file -> ignored");
		}
		return result;
	}

	private ukuProcess fetchUkuProcess(Element e) {
		ukuProcess result = null;
		if (e.getName().equals("process")) {
			result = new ukuProcess(e.getAttributeValue("id"));
			result.setEntities(fetchEntities(e));
		} else {
			out.println("element '" + e.getName()
					+ "' is not a process -> ignored");
		}
		return result;
	}

	private List<Entity> fetchEntities(Element e) {
		List<Entity> result = new LinkedList<Entity>();
		if (e.getName().equals("process")) {
			for (Element child : e.getChildren()) {
				Entity tmp = fetchEntity(child);
				if (tmp != null)
					result.add(tmp);
				else {
					// System.out.println("not a process");
				}
			}
		}
		return result;
	}

	private Entity fetchEntity(Element e) {
		String name = e.getName();
		int type = 0;
		try {
			type = classifier.getType(name);
		} catch (UnsupportedElementException e1) {
			e1.printStackTrace();
			// TODO warning!
			return null;
		}
		String id = e.getAttributeValue("id");
		switch (type) {
		case 1:
			String sourceRef = e.getAttributeValue("sourceRef");
			String targetRef = e.getAttributeValue("targetRef");
			SequenceFlow result = new SequenceFlow(id, sourceRef, targetRef);

			for (Element child : e.getChildren()) {
				if (child.getName().equals("conditionExpression"))
					;
				String condition = child.getTextTrim();
				((SequenceFlow) result).setCondition(condition);
				break;
			}
			return result;
		case 2:
			ExecuteTask et = new ExecuteTask(id);
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
			UkuflowEvent event = new UkuflowEvent(id);
			return event;
		case 4: // gateways
			Gateway gway = new Gateway(id);
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
	// create a console
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

	/********************************************************************************************/
	private void printProcesses(Element e) {
		if (e.getName().equals("definitions")) {
			for (Element ex : e.getChildren()) {
				if (ex.getName().equals("process")) {
					printProcesses(ex);
				}
			}
		}
		if (e.getName().equals("process")) {
			Map<String, Element> connector = new HashMap<String, Element>();
			Map<String, Element> elements = new HashMap<String, Element>();
			List<String> connector_Id = new ArrayList<String>();

			TypeClassifier a = TypeClassifier.getInstance();
			for (Element child : e.getChildren()) {
				int x = 0;
				try {
					x = a.getType(child.getName());
				} catch (Exception e1) {
					e1.printStackTrace();
					// TODO:
					continue;
				}
				switch (x) {
				case 1:
					connector.put(child.getAttributeValue("id"), child);
					connector_Id.add(child.getAttributeValue("id"));
					
					out.println("connector added :"
							+ child.getAttributeValue("id"));
					break;
				case 2:
				case 3:
				case 4:
					elements.put(child.getAttributeValue("id"), child);
					out.println("Element added" + child.getAttributeValue("id"));
					break;
				default:
					out.println("warning: element with name " + child.getName()
							+ ", id: " + child.getAttributeValue("id")
							+ " is not defined");
				}
			}
			StringBuilder sb = new StringBuilder();
			for (Element ex : elements.values()) {
				if (ex.getName().equals("startEvent")) {
					sb.append("0, START_EVENT, ");
					for (Element exx : ex.getChildren()) {
						if (!exx.getName().equals("outgoing"))
							continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim()) + 1)
								+ ", ");
						System.out.println(ex.getTextTrim());
					}
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("\n");
				} else if (ex.getName().equals("endEvent")) {
					for (Element exx : ex.getChildren()) {
						if (!exx.getName().equals("incoming"))
							continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim()) + 1)
								+ ", ");
						System.out.println(ex.getTextTrim());
					}
					sb.append("END_EVENT");
					sb.append("\n");
				} else {
					for (Element exx : ex.getChildren()) {
						if (!exx.getName().equals("incoming"))
							continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim()) + 1)
								+ ", ");
						System.out.println(ex.getTextTrim());
					}
					sb.append(ex.getName() + ", ");
					for (Element exx : ex.getChildren()) {
						if (!exx.getName().equals("outgoing"))
							continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim()) + 1)
								+ ", ");
						System.out.println(ex.getTextTrim());
					}
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("\n");
				}
			}
			System.out.println(sb.toString());
		}
	}

	private void printElement(Element e, String prefix) {
		System.out.print(prefix);

		System.out.print(e.getName() + ":" + e.getTextTrim());
		System.out.print(" -> (");
		for (Attribute attrb : e.getAttributes()) {
			System.out.print(attrb.getName() + ":" + attrb.getValue() + "; ");
		}
		System.out.println(")");
		for (Element child : e.getChildren()) {
			printElement(child, prefix + "   ");
		}
	}

	private String readFile(InputStream is) {
		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		Reader in = null;

		try {
			in = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int read = -1;
		do {
			try {
				read = in.read(buffer, 0, buffer.length);
			} catch (IOException e) {
				// TODO: handle exception
			}
			if (read > 0) {
				out.append(buffer, 0, read);
			}
		} while (read >= 0);

		String result = out.toString();
		return result;
	}
}
