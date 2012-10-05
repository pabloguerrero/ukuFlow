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

public class DeploymentDynamicMenu extends ContributionItem implements
		IWorkbenchContribution {

	private ISelectionService srv;
	private Object selected;
	private static Set<String> fileExtensions;
	// private String extension;

	static {
		fileExtensions = new HashSet<String>();
		// fileExtensions.add("bpmn");
		// fileExtensions.add("bpmn2");
		fileExtensions.add("uku");
		fileExtensions.add("uku64");
	}

	public DeploymentDynamicMenu() {
		this(null);
	}

	public DeploymentDynamicMenu(String id) {
		super(id);
	}

	@Override
	public boolean isEnabled() {
		if (selected != null && selected instanceof IFile) {
			IFile file = (IFile) selected;
			String extension = file.getFileExtension();
			if (fileExtensions.contains(extension.toLowerCase()))
				return true;
		}
		return false;
	}

	@Override
	public void fill(Menu menu, int index) {
		System.out.println("DynamicNode is filled");

		if (srv == null)
			return;
		ISelection sel = srv.getSelection();
		if (!(sel instanceof IStructuredSelection))
			return;

		selected = ((IStructuredSelection) sel).getFirstElement();
		if (!(selected instanceof IFile))
			return;
		final IFile file = (IFile) selected;
		Map<String, String> devs = DeviceManager.getInstance().getDevices();
		for (final String key : devs.keySet()) {
			MenuItem devItem = new MenuItem(menu, SWT.PUSH);
			devItem.setText(devs.get(key) + " [" + key + "]");
			devItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO process deployment request
					System.out.println("selected->");
					System.out.println("\t"
							+ file.getFullPath().toPortableString());
					System.out.println("\t" + key);
					String fname;
					String extension = file.getFileExtension();
					boolean converted = false;
					if (extension.equals("bpmn") || extension.equals("bpmn2")) {
						converted = ConvertCommand.convert(file);
						if (!converted) {
							return;
						}
						fname = file.getLocation().toOSString() + "\n";
						fname = fname.replace(extension + "\n", "uku64");
					} else {
						fname = file.getLocation().toOSString();
					}

					try {
						DeviceManager.getInstance().deploy(key, fname, 10000);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
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
