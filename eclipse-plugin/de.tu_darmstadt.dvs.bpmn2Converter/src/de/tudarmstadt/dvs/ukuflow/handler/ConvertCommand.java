package de.tudarmstadt.dvs.ukuflow.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.tudarmstadt.dvs.ukuflow.deployment.DeviceFinder;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.xml.BPMN2XMLParser;
import de.tudarmstadt.dvs.ukuflow.xml.entity.ElementVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;

public class ConvertCommand extends AbstractHandler {
	/**
	 * Console name
	 */
	private BpmnLog log = BpmnLog.getInstance(ConvertCommand.class
			.getSimpleName());
	public static final String CONSOLE_NAME = "bpmn2 converter";

	MessageConsole myConsole = null;
	MessageConsoleStream out = null;

	private void writeMarkers(IResource resource, String location, String msg) {
		IMarker m = null;
		try {
			m = resource.createMarker(IMarker.PROBLEM);
			m.setAttribute(IMarker.LOCATION, location);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

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
		System.out.println(firstElement.getClass());
		if (selection.size() != 1) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please choose just one BPMN2 file");
			return null;
		}
		
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;

			try {
				file.deleteMarkers(IMarker.PROBLEM, true,
						IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				e.printStackTrace();
			}

			String extension = file.getFileExtension();
			String oFileLocation = file.getLocation().toOSString();

			String nfileLocation = oFileLocation + "\n";
			String nfileLocation64 = oFileLocation + "\n";
			nfileLocation = nfileLocation.replace(extension + "\n", "uku");
			nfileLocation64 = nfileLocation64
					.replace(extension + "\n", "uku64");
			if (extension.equals("bpmn") || extension.equals("bpmn2")) {

				BPMN2XMLParser parser = new BPMN2XMLParser(oFileLocation, out);
				parser.executeFetch();
				List<UkuProcess> processes = parser.getProcesses();
				log.info("got " + processes.size() + " processes");
				StringBuilder sb = new StringBuilder();
				int errcounter = 0;
				for (UkuProcess up : processes) {
					log.info(up);
					for (String errs : up.getErrorMessages()) {
						sb.append(errs);
						writeMarkers(file, "",errs.replace("\n", "\\"));
						sb.append("\n");
						errcounter++;
					}
				}
				if (errcounter > 0) {
					out.println("There are(is) "
							+ errcounter
							+ " error(s) in the workflow diagram. Please fix them first");
					out.println(sb.toString());

					return null;
				} else {
					out.println("No error!");
				}

				ElementVisitorImpl visitor = new ElementVisitorImpl();

				for (UkuProcess ue : processes) {
					visitor.reset();
					ue.accept(visitor);
					out.println("***");
					for (byte b : visitor.getOutput())
						out.print(b + " ");
					out.println("\n-------------");
					out.println(visitor.getOutputString64());
					out.println("***");
				}

				File f = new File(nfileLocation64);
				FileWriter fwrite = null;
				try {
					fwrite = new FileWriter(f);
					fwrite.write(visitor.getOutputString64());
					fwrite.flush();
					fwrite.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				f = new File(nfileLocation);
				fwrite = null;
				try {
					fwrite = new FileWriter(f);
					for (byte b : visitor.getOutput())
						fwrite.write(b + " ");
					fwrite.flush();
					fwrite.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

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
