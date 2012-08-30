package de.tudarmstadt.dvs.ukuflow.deployment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tudarmstadt.dvs.ukuflow.converter.Activator;

public class DynamicCommandFactory extends ExtensionContributionFactory {
	private static ImageDescriptor image;
	  static {
	    URL url = null;
	    try {
	    url = new URL(Activator.getDefault().getDescriptor().getInstallURL(),
	                  "resource/usbport.png");
	    } catch (MalformedURLException e) {
	    }
	    image = ImageDescriptor.createFromURL(url);
	  }
	@Override
	public void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions) {
		MenuManager mm = new MenuManager("ukuFlow");
		
		DeviceFinder df = DeviceFinder.getInstance();
		HashMap<String, String> devs = df.getDevices();
		System.out.println(devs.size());
		for (String key : devs.keySet()) {
			CommandContributionItemParameter p = new CommandContributionItemParameter(
					serviceLocator, "",
					"de.tudarmstadt.dvs.ukuflow.deployment.deploycommand",
					SWT.PUSH);			
			if(p.parameters == null){
				System.out.println("parameter is null");
				p.parameters = new HashMap<Object, Object>();
			}
			p.parameters.put("de.tudarmstadt.dvs.ukuflow.deployment.portID", key);
			p.label = devs.get(key) + " [port:" +key+"]";
			p.icon = image;
			CommandContributionItem item = new CommandContributionItem(p);			
			item.setVisible(true);
			mm.add(item);			
		}
		//additions.addContributionItem(new DefineCommands(), null);
		additions.addContributionItem(mm, null);
	}
	

}