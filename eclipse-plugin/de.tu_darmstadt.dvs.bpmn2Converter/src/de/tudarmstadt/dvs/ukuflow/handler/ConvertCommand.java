package de.tudarmstadt.dvs.ukuflow.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.jdom2.
import org.eclipse.swt.graphics.Color;
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

import de.tudarmstadt.dvs.ukuflow.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.deployment.DeviceFinder;
import de.tudarmstadt.dvs.ukuflow.exception.UnsupportedElementException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import de.tudarmstadt.dvs.ukuflow.xml.XMLNode;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement;
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
	private BpmnLog log = BpmnLog.getInstance(ConvertCommand.class.getSimpleName());
	public static final String CONSOLE_NAME = "bpmn2 converter";

	

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
				out.println("checking..");
				out.println("converting");
				out.println("converting..");

				BPMN2XMLParser parser = new BPMN2XMLParser(oFileLocation,out);
				
				//XMLNode root = new XMLNode(e);
				List<UkuProcess> processes = parser.getProcesses();
				log.info("got "+processes.size() + " processes");
				for(UkuProcess up : processes){
					log.info(up);
					for(String errs:up.getErrorMessages()){
						out.println(errs);
					}
					for(UkuEntity e : up.entities){
						if(e instanceof UkuElement){
							UkuElement ue =(UkuElement)e;
							if(ue.getIncoming().size() > 0){
								for(UkuEntity xx: ue.getIncoming())
									System.out.print(((UkuSequenceFlow)xx).getSource() + ", ");
								System.out.print( "->");
							}							
							System.out.print(e.getID());
							if(ue.getOutgoing().size() >0){
								System.out.print( "->");
								for(UkuEntity xx: ue.getOutgoing())
									System.out.print(((UkuSequenceFlow)xx).getTarget() + ", ");
							}
							System.out.println();
						}
						
					}
				}
				
				out.println(processes.size()+"");
				/*
				 * File f = new File(oFileLocation); FileWriter fwrite = null;
				 * try { fwrite = new FileWriter(f); fwrite.write(content);
				 * fwrite.flush(); fwrite.close(); } catch (IOException ex) {
				 * ex.printStackTrace(); }
				 */
			} else {
				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "Information",
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
