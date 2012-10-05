package de.tudarmstadt.dvs.ukuflow.deployment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

import de.tudarmstadt.dvs.ukuflow.handler.ConvertCommand;

public class UndeploymentCommand extends ContributionItem implements
		IWorkbenchContribution {

	private ISelectionService srv;
	private Object selected;	

	
	public UndeploymentCommand() {
		this(null);
	}

	public UndeploymentCommand(String id) {
		super(id);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void fill(Menu menu, int index) {		

		if (srv == null)
			return;
		ISelection sel = srv.getSelection();
		if (!(sel instanceof IStructuredSelection))
			return;

		selected = ((IStructuredSelection) sel).getFirstElement();
		if (!(selected instanceof IFile))
			return;
		final IFile file = (IFile) selected;
		Map<String,String> devs = DeviceManager.getInstance().getDevices();
		for (final String key : devs.keySet()) {
			MenuItem devItem = new MenuItem(menu, SWT.PUSH);
			devItem.setText(devs.get(key) + " [" + key + "]");
			devItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {					
					int pid = ConvertCommand.getProcessID(file);
					try {
						DeviceManager.getInstance().undeploy(key, pid, 10000);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public void initialize(IServiceLocator serviceLocator) {
		System.out.println("DynamicNode is initialized");

		srv = (ISelectionService) serviceLocator
				.getService(ISelectionService.class);
		ISelection sel = srv.getSelection();
		if (!(sel instanceof IStructuredSelection))
			return;
		selected = ((IStructuredSelection) sel).getFirstElement();
	}

}