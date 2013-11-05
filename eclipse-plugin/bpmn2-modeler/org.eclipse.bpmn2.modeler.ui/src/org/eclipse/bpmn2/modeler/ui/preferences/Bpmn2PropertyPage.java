/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
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
import org.eclipse.bpmn2.modeler.ui.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;

public class Bpmn2PropertyPage extends PropertyPage {

	private Bpmn2Preferences prefs;
	
	private Combo cboRuntimes;
	private Button btnShowAdvancedProperties;
	private Button btnDoCoreValidation;
	private Button btnShowDescriptions;
	private Button btnShowIds;
	private Button btnCheckProjectNature;
	private BPMNDIAttributeDefaultCombo cboIsHorizontal;
	private BPMNDIAttributeDefaultCombo cboIsExpanded;
	private BPMNDIAttributeDefaultCombo cboIsMessageVisible;
	private BPMNDIAttributeDefaultCombo cboIsMarkerVisible;
	private Composite runtimeComposite;
	
	public Bpmn2PropertyPage() {
		super();
		setTitle("BPMN2");
		setDescription(Messages.Bpmn2PreferencePage_HomePage_Description);
	}

	@Override
	protected Control createContents(Composite parent) {
		loadPrefs();
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(3, false));

		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		label.setText(Bpmn2Preferences.PREF_TARGET_RUNTIME_LABEL);
		
		cboRuntimes = new Combo(container, SWT.READ_ONLY);
		cboRuntimes.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		cboRuntimes.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnDoCoreValidation!=null) {
					int index = cboRuntimes.getSelectionIndex();
					if (index>=0) {
						String item = cboRuntimes.getItem(index);
						TargetRuntime rt = (TargetRuntime)cboRuntimes.getData(item);
						if (TargetRuntime.BPMN2_MARKER_ID.equals(rt.getProblemMarkerId())) {
							btnDoCoreValidation.setEnabled(false);
							btnDoCoreValidation.setSelection(true);
						}
						else {
							btnDoCoreValidation.setEnabled(true);
							btnDoCoreValidation.setSelection(prefs.getDoCoreValidation());
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		btnDoCoreValidation = new Button(container, SWT.CHECK);
		btnDoCoreValidation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		btnDoCoreValidation.setText(Bpmn2Preferences.PREF_DO_CORE_VALIDATION_LABEL);
		
		btnShowAdvancedProperties = new Button(container, SWT.CHECK);
		btnShowAdvancedProperties.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		btnShowAdvancedProperties.setText(Bpmn2Preferences.PREF_SHOW_ADVANCED_PROPERTIES_LABEL);
		
		btnShowDescriptions = new Button(container, SWT.CHECK);
		btnShowDescriptions.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		btnShowDescriptions.setText(Bpmn2Preferences.PREF_SHOW_DESCRIPTIONS_LABEL);
		
		btnShowIds = new Button(container, SWT.CHECK);
		btnShowIds.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		btnShowIds.setText(Bpmn2Preferences.PREF_SHOW_ID_ATTRIBUTE_LABEL);

		btnCheckProjectNature = new Button(container, SWT.CHECK);
		btnCheckProjectNature.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		btnCheckProjectNature.setText(Bpmn2Preferences.PREF_CHECK_PROJECT_NATURE_LABEL);

		// Default values for optional BPMN DI attributes
		Group group = new Group(container, SWT.BORDER);
		group.setLayout(new GridLayout(3,false));
		group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		group.setText("Default values for BPMN Diagram Interchange (DI) optional attributes");

		cboIsHorizontal = new BPMNDIAttributeDefaultCombo(group);
		cboIsHorizontal.setText(Bpmn2Preferences.PREF_IS_HORIZONTAL_LABEL);
		
		cboIsExpanded = new BPMNDIAttributeDefaultCombo(group);
		cboIsExpanded.setText(Bpmn2Preferences.PREF_IS_EXPANDED_LABEL);
		
		cboIsMessageVisible = new BPMNDIAttributeDefaultCombo(group);
		cboIsMessageVisible.setText(Bpmn2Preferences.PREF_IS_MESSAGE_VISIBLE_LABEL);
		
		cboIsMarkerVisible = new BPMNDIAttributeDefaultCombo(group);
		cboIsMarkerVisible.setText(Bpmn2Preferences.PREF_IS_MARKER_VISIBLE_LABEL);
		
		initData();

		return container;
	}

	private void restoreDefaults() {
		prefs.restoreDefaults(true);
		prefs.getRuntime();
		initData();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		restoreDefaults();
	}
	
	public void loadPrefs() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
		prefs = Bpmn2Preferences.getInstance(project);
		prefs.load();
	}

	private void initData() {
		btnShowAdvancedProperties.setSelection( prefs.getShowAdvancedPropertiesTab() );
		btnDoCoreValidation.setSelection( prefs.getDoCoreValidation() );
		btnShowDescriptions.setSelection( prefs.getShowDescriptions() );
		btnShowIds.setSelection( prefs.getShowIdAttribute() );
		btnCheckProjectNature.setSelection( prefs.getCheckProjectNature() );
		cboIsHorizontal.setValue(prefs.getIsHorizontal());
		cboIsExpanded.setValue( prefs.getIsExpanded() );
		cboIsMessageVisible.setValue( prefs.getIsMessageVisible() );
		cboIsMarkerVisible.setValue( prefs.getIsMarkerVisible() );
		
		TargetRuntime rt = prefs.getRuntime();
		int i = 0;
		for (TargetRuntime r : TargetRuntime.getAllRuntimes()) {
			cboRuntimes.add(r.getName());
			cboRuntimes.setData(r.getName(), r);
			if (r == rt)
				cboRuntimes.select(i);
			++i;
		}
		if (TargetRuntime.BPMN2_MARKER_ID.equals(rt.getProblemMarkerId())) {
			btnDoCoreValidation.setEnabled(false);
			btnDoCoreValidation.setSelection(true);
		}
		else {
			btnDoCoreValidation.setEnabled(true);
			btnDoCoreValidation.setSelection(prefs.getDoCoreValidation());
		}
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
		
		prefs.setShowAdvancedPropertiesTab(btnShowAdvancedProperties.getSelection());
		prefs.setDoCoreValidation(btnDoCoreValidation.getSelection());
		prefs.setShowDescriptions(btnShowDescriptions.getSelection());
		prefs.setShowIdAttribute(btnShowIds.getSelection());
		prefs.setCheckProjectNature(btnCheckProjectNature.getSelection());
		prefs.setIsHorizontal(cboIsHorizontal.getValue());
		prefs.setIsExpanded(cboIsExpanded.getValue());
		prefs.setIsMessageVisible(cboIsMessageVisible.getValue());
		prefs.setIsMarkerVisible(cboIsMarkerVisible.getValue());
		
		prefs.save();
	}
}
