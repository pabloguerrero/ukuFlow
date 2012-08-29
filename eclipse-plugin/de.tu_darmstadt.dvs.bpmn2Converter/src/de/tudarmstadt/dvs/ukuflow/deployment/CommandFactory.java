package de.tudarmstadt.dvs.ukuflow.deployment;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class CommandFactory implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {
		// CommandContributionItemParameter p = new
		// CommandContributionItemParameter(serviceLocator, "",
		// "org.eclipse.ui.file.exit", SWT.PUSH);
		// p.label = "Exit the application";
		// p.icon = Activator.getImageDescriptor("icons/alt_window_16.gif");

		// CommandContributionItem item = new CommandContributionItem(p);
		// item.setVisible(true);
		return null;
	}

}