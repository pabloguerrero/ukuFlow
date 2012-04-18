/*******************************************************************************
 * Copyright (c) 2011 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.preferences;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;

public class Bpmn2PropertyPage extends PropertyPage {

	private Bpmn2Preferences prefs;
	
	private Combo cboRuntimes;
	private Button btnShowAdvancedProperties;
	private Button btnExpandProperties;
	private Button btnVerticalOrientation;
	
	public Bpmn2PropertyPage() {
		super();
		setTitle("BPMN2");
	}

	@Override
	protected Control createContents(Composite parent) {
		
		initData();
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(3, false));

		Label lblRuntime = new Label(container, SWT.NONE);
		lblRuntime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblRuntime.setText(Bpmn2Preferences.PREF_TARGET_RUNTIME_LABEL);
		
		cboRuntimes = new Combo(container, SWT.NONE);
		cboRuntimes.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		
		TargetRuntime cr = prefs.getRuntime();
		int i = 0;
		for (TargetRuntime r : TargetRuntime.getAllRuntimes()) {
			cboRuntimes.add(r.getName());
			if (r == cr)
				cboRuntimes.select(i);
			++i;
		}
		
		btnShowAdvancedProperties = new Button(container, SWT.CHECK);
		btnShowAdvancedProperties.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnShowAdvancedProperties.setText(Bpmn2Preferences.PREF_SHOW_ADVANCED_PROPERTIES_LABEL);
		btnShowAdvancedProperties.setSelection( prefs.getShowAdvancedPropertiesTab() );

		btnExpandProperties = new Button(container, SWT.CHECK);
		btnExpandProperties.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnExpandProperties.setText(Bpmn2Preferences.PREF_EXPAND_PROPERTIES_LABEL);
		btnExpandProperties.setSelection( prefs.getExpandProperties() );
		
		btnVerticalOrientation = new Button(container, SWT.CHECK);
		btnVerticalOrientation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnVerticalOrientation.setText(Bpmn2Preferences.PREF_VERTICAL_ORIENTATION_LABEL);
		btnVerticalOrientation.setSelection( prefs.isVerticalOrientation() );

		return container;
	}

	private void restoreDefaults() {
		prefs.restoreDefaults();
		prefs.getRuntime();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		restoreDefaults();
	}

	private void initData() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
		prefs = Bpmn2Preferences.getInstance(project);
		prefs.load();
	}

	@Override
	public boolean performOk() {
		setErrorMessage(null);
		try {
			updateData();
		} catch (BackingStoreException e) {
			Activator.showErrorWithLogging(e);
		}
		return true;
	}

	@Override
	public void dispose() {
		prefs.dispose();
		super.dispose();
	}

	private void updateData() throws BackingStoreException {
		int i = cboRuntimes.getSelectionIndex();
		TargetRuntime rt = TargetRuntime.getAllRuntimes()[i];
		prefs.setRuntime(rt);
		
		boolean show = btnShowAdvancedProperties.getSelection();
		prefs.setShowAdvancedPropertiesTab(show);
		
		boolean expand = btnExpandProperties.getSelection();
		prefs.setExpandProperties(expand);
		
		boolean vertical = btnVerticalOrientation.getSelection();
		prefs.setVerticalOrientation(vertical);
		
		prefs.save();
	}
}
