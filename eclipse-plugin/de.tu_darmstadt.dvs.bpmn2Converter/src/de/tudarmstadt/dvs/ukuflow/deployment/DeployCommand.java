package de.tudarmstadt.dvs.ukuflow.deployment;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeployCommand extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("deployCommand executed -> port " + event.getParameter("de.tudarmstadt.dvs.ukuflow.deployment.portID"));
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		
		Object firstElement = selection.getFirstElement();
		IFile file =(IFile) firstElement;
		System.out.println("source data: " + file.getLocation().toOSString());
		return null;
	}

}
