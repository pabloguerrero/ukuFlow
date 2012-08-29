package de.tudarmstadt.dvs.ukuflow.deployment;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.ui.internal.menus.DynamicMenuContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

public class DefineCommands extends DynamicMenuContributionItem {
	
	public DefineCommands(String id, IServiceLocator locator,
			IConfigurationElement dynamicAddition) {
		super(id, locator, dynamicAddition);
		try {
			dynamicAddition.createExecutableExtension("de.tudarmstadt.dvs.ukuflow.deployment.CommandFactory");			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 

} 