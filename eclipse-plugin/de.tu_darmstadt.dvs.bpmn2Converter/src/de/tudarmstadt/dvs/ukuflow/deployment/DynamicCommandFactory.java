package de.tudarmstadt.dvs.ukuflow.deployment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.internal.expressions.ReferenceExpression;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tudarmstadt.dvs.ukuflow.converter.Activator;
/**
 * 
 * @author Hien Quoc Dang
 * @deprecated DynamicNodeFactory is better ;)
 */
public class DynamicCommandFactory extends ExtensionContributionFactory {
	private static ImageDescriptor image;
	static {
		URL url = null;
		try {
			url = new URL(Activator.getDefault().getDescriptor()
					.getInstallURL(), "resource/usbport.png");
		} catch (MalformedURLException e) {
		}
		image = ImageDescriptor.createFromURL(url);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions) {
		MenuManager mm = new MenuManager("ukuFlow");
		MenuManager deployment = new MenuManager("Deploy To");
		CommandContributionItemParameter p1 = 
				new CommandContributionItemParameter(serviceLocator, "","de.tudarmstadt.dvs.ukuflow.converterBase64", SWT.PUSH);
		p1.label = "Convert to Base64";
		p1.mnemonic = "check & convert the bpmn diagram to a base64 encoded file";
		CommandContributionItem convertCommand = new CommandContributionItem(p1);
		convertCommand.setVisible(true);
		mm.add(convertCommand);
		DeviceMamager df = DeviceMamager.getInstance();
		Map<String, String> devs = df.getDevices();
		if (devs.size() > 0) {
			for (String key : devs.keySet()) {
				CommandContributionItemParameter p = new CommandContributionItemParameter(serviceLocator, "","de.tudarmstadt.dvs.ukuflow.deployment.deploycommand",SWT.PUSH);
				if (p.parameters == null) {
					//System.out.println("parameter is null");
					p.parameters = new HashMap<Object, Object>();
				}
				p.parameters.put("de.tudarmstadt.dvs.ukuflow.deployment.portID", key);
				p.label = devs.get(key) + " [" + key + "]";
				
				p.icon = image;
				CommandContributionItem item = new CommandContributionItem(p);
				item.setVisible(true);
				deployment.add(item);
			}
		} else {		
			deployment.setVisible(true);
			deployment.dispose();
		}
		// additions.addContributionItem(new DefineCommands(), null);
		mm.add(deployment);
		@SuppressWarnings("restriction")
		ReferenceExpression exp = new ReferenceExpression("de.tudarmstadt.dvs.ukuflow.menu.visiblity");
		additions.addContributionItem(mm, exp);
	}

}