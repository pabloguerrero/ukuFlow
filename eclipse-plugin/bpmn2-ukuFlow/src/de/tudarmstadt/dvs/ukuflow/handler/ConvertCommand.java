package de.tudarmstadt.dvs.ukuflow.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//import org.jdom2.
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.EventLoopProgressMonitor;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.model.WorkbenchPartLabelProvider;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.tools.Base64Converter;
import de.tudarmstadt.dvs.ukuflow.tools.QuickFileReader;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;
import de.tudarmstadt.dvs.ukuflow.validation.UkuProcessValidation;
import de.tudarmstadt.dvs.ukuflow.xml.BPMN2XMLParser;
import de.tudarmstadt.dvs.ukuflow.xml.entity.ElementVisitorImpl;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;

@SuppressWarnings("restriction")
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
		boolean validateonly = new Boolean(event.getParameter("de.tudarmstadt.dvs.ukuflow.convert.validateonly"));
		
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
			if(validateonly){
				return validate(file);
			} else {
				return convert(file);
			}
		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please select a BPMN or BPMN2 source file");
		}
		return null;
	}
	public static boolean validate(IFile file){
		deleteMarker(file);
		boolean saved = saveAllResources(file);
		if(!saved)
			return false;
		String extension = file.getFileExtension();
		String oFileLocation = file.getLocation().toOSString();
		String nfileLocation =  oFileLocation+ "\n";
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
			console.info("Validator",
					"Issued "
							+ em.getWarnings().size()
							+ (em.getWarnings().size() == 1 ? " warming"
									: " warnings "));
			if (!em.isValid()) {
				console.info("Validator", "There are(is) "
						+ em.getErrors().size()
						+ " errors in the diagram, please fix them (it) first");
				em.reset();
				sm.reset();
				return false;
			}
			em.reset();
			sm.reset();
		}
		
		return true;
	}
	public static boolean convert(IFile file) {
		deleteMarker(file);
		boolean saved = saveAllResources(file);
		if(!saved)
			return false;
		return convert(file.getLocation().toOSString(), file.getFileExtension());

	}

	public static int getProcessID(IFile file) {
		// deleteMarker
		String extension = file.getFileExtension();
		if (extension.equals("bpmn") || extension.equals("bpmn2")) {
			BPMN2XMLParser parser = new BPMN2XMLParser(file.getLocation()
					.toOSString());
			return parser.getProcessID();
		} else if (extension.equals("uku64")) {
			String s = QuickFileReader.getFileContent(file.getLocation()
					.toOSString());
			return Base64Converter.decodeFrom(s)[2];
		} else if (extension.equals("uku")) {
			String s = QuickFileReader.getFileContent(file.getLocation()
					.toOSString());
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
			console.info("Validator",
					"Issued "
							+ em.getWarnings().size()
							+ (em.getWarnings().size() == 1 ? " warming"
									: " warnings "));
			if (!em.isValid()) {
				console.info("Validator", "There are(is) "
						+ em.getErrors().size()
						+ " errors in the diagram, please fix them (it) first");
				em.reset();
				sm.reset();
				return false;
			}			

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
			visitor = null;
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean saveAllResources(IFile file) {
		
		List selected = new LinkedList();
		Set<IEditorInput> inputs = new HashSet<IEditorInput>();
		List result = new LinkedList();
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				IEditorPart[] editors = page.getDirtyEditors();
				for (IEditorPart editor: editors) {					
					IEditorInput input = editor.getEditorInput();
					if (input instanceof IFileEditorInput) {
						IFileEditorInput fileInput = (IFileEditorInput) input;
						if(file.equals(fileInput.getFile()))
							selected.add(editor);
						//if (files.contains(fileInput.getFile())) {
							if (!inputs.contains(input)) {
								inputs.add(input);
								result.add(editor);
							}
						//}
					}
				}
			}
		}		
		final IWorkbenchWindow iwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ListSelectionDialog lsd = new ListSelectionDialog(windows[0].getShell(), result, new ArrayContentProvider(), new WorkbenchPartLabelProvider(), "message");
		if(result.isEmpty())
			return true;
		//if(selected)
		lsd.setInitialElementSelections(selected);
		if(lsd.open()==Window.CANCEL)
			return false;
		result = Arrays.asList(lsd.getResult());	
		if(result.isEmpty())
			return true;
		final List finalModels = result;
		IRunnableWithProgress progressOp = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(
						monitor);
				monitorWrap.beginTask(WorkbenchMessages.Saving_Modifications, finalModels.size());
				for (Iterator i = finalModels.iterator(); i.hasNext();) {
					IEditorPart model = (IEditorPart) i.next();
					// handle case where this model got saved as a result of saving another
					if (!model.isDirty()) {
						monitor.worked(1);
						continue;
					}
					model.doSave(monitorWrap);
					if (monitorWrap.isCanceled()) {
						break;
					}
				}
				monitorWrap.done();
			}
		};
	
		return runProgressMonitorOperation(WorkbenchMessages.Save_All, progressOp, iwindow, iwindow);
	}
	
	private static boolean runProgressMonitorOperation(String opName,
			final IRunnableWithProgress progressOp,
			final IRunnableContext runnableContext, final IShellProvider shellProvider) {
		final boolean[] success = new boolean[] { false };
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				progressOp.run(monitor);
				// Only indicate success if the monitor wasn't canceled
				if (!monitor.isCanceled())
					success[0] = true;
			}
		};

		try {
			runnableContext.run(false, true, runnable);
		} catch (InvocationTargetException e) {
			String title = NLS.bind(WorkbenchMessages.EditorManager_operationFailed, opName ); 
			Throwable targetExc = e.getTargetException();
			WorkbenchPlugin.log(title, new Status(IStatus.WARNING,
					PlatformUI.PLUGIN_ID, 0, title, targetExc));			
			StatusUtil.handleStatus(title, targetExc, StatusManager.SHOW,
					shellProvider.getShell());
		} catch (InterruptedException e) {

		} catch (OperationCanceledException e) {

		}
		return success[0];
	}

}
