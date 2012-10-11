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

import de.tudarmstadt.dvs.ukuflow.deployment.DeviceManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.tools.Base64Converter;
import de.tudarmstadt.dvs.ukuflow.tools.QuickFileReader;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;
import de.tudarmstadt.dvs.ukuflow.validation.UkuProcessValidation;
import de.tudarmstadt.dvs.ukuflow.xml.BPMN2XMLParser;
import de.tudarmstadt.dvs.ukuflow.xml.entity.ElementVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;

public class ConvertCommand extends AbstractHandler {
	/**
	 * Console name
	 */
	private BpmnLog log = BpmnLog.getInstance(ConvertCommand.class
			.getSimpleName());
	private static UkuConsole console = UkuConsole.getConsole();

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

	private static void deleteMarker(IFile file) {
		try {
			file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
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
			return convert(file);
		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please select a BPMN or BPMN2 source file");
		}
		return null;
	}

	public static boolean convert(IFile file) {
		deleteMarker(file);
		return convert(file.getLocation().toOSString(), file.getFileExtension());

	}
	public static int getProcessID(IFile file){
		//deleteMarker
		String extension = file.getFileExtension();
		if(extension.equals("bpmn") || extension.equals("bpmn2")){
			BPMN2XMLParser parser = new BPMN2XMLParser(file.getLocation().toOSString());
			return parser.getProcessID();
		} else if(extension.equals("uku64")){
			String s = QuickFileReader.getFileContent(file.getLocation().toOSString());
			return Base64Converter.decodeFrom(s)[2];
		} else if(extension.equals("uku")){
			String s = QuickFileReader.getFileContent(file.getLocation().toOSString());
			return Integer.valueOf(s.split(" ")[2]);
		}
		
		throw new NullPointerException("");
	}

	private static boolean convert(String oFileLocation, String extension) {
		// String extension = file.getFileExtension();
		// String oFileLocation = file.getLocation().toOSString();
		String nfileLocation = oFileLocation + "\n";
		String nfileLocation64 = oFileLocation + "\n";
		nfileLocation = nfileLocation.replace(extension + "\n", "uku");
		nfileLocation64 = nfileLocation64.replace(extension + "\n", "uku64");

		if (extension.equals("bpmn") || extension.equals("bpmn2")) {
			BPMN2XMLParser parser = new BPMN2XMLParser(oFileLocation);
			parser.executeFetch();
			List<UkuProcess> processes = parser.getProcesses();

			UkuProcessValidation validator = new UkuProcessValidation(
					processes.get(0));
			validator.validate();
			ErrorManager em = ErrorManager.getInstance();
			ScopeManager sm = ScopeManager.getInstance();
			em.exportTo(console);
			console.info("Validator", "Report:");
			console.info("Validator", em.getWarnings().size()
					+ " warnings are found");
			if (!em.isValid()) {
				console.info("Validator", "There are(is) "
						+ em.getErrors().size()
						+ " errors in the diagram, please fix them (it) first");
				em.reset();
				sm.reset();
				return false;
			}
			console.info("Validator", "No error");
			
			ElementVisitorImpl visitor = new ElementVisitorImpl();

			UkuProcess ue = processes.get(0);
			visitor.reset();
			ue.accept(visitor);
			console.info("output", visitor.getOutputString64());
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
			em.reset();
			sm.reset();
		}
		return true;
	}
}
