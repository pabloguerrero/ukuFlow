/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package de.tudarmstadt.dvs.ukuflow.deployment;

import java.util.Map;

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

import de.tudarmstadt.dvs.ukuflow.converter.Activator;
import de.tudarmstadt.dvs.ukuflow.handler.ConvertCommand;
import de.tudarmstadt.dvs.ukuflow.preference.UkuPreference;

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
		final int timeout = 1000*Activator.getDefault().getPreferenceStore().getInt(UkuPreference.TIMEOUT);
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
						DeviceManager.getInstance().undeploy(key, pid, timeout);
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