/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package de.tudarmstadt.dvs.ukuflow.eventbase.core.property;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


public class TutorialEClassSection extends GFPropertySection implements ITabbedPropertyConstants {

	private Text nameText;
	private CCombo comboBox;
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);
		FormData data;

		nameText = factory.createText(composite, ""); //$NON-NLS-1$
		nameText.setEditable(false);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, VSPACE);
		nameText.setLayoutData(data);
		nameText.setEditable(true);
		nameText.setVisible(false);
		CLabel valueLabel = factory.createCLabel(composite, "Name:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(nameText, -HSPACE);
		data.top = new FormAttachment(nameText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);
		valueLabel.setVisible(false);
		comboBox = factory.createCCombo(composite);
		String[] ITEMS = { "A", "B", "C", "D", "E", "F" };
		comboBox.setItems(ITEMS);
        //comboBox.add("AND");
        //comboBox.add("OR");
        //comboBox.add("XOR");
        data = new FormData();
        data.left = new FormAttachment(0,0);
        data.right = new FormAttachment(comboBox,-5);
        data.top = new FormAttachment(comboBox,-100,SWT.CENTER);
       // comboBox.setLayoutData(data);
        comboBox.setEditable(true); 
        comboBox.setVisible(true);
	}

	@Override
	public void refresh() {
		PictogramElement pe = getSelectedPictogramElement();
		if (pe != null) {
			Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			// the filter assured, that it is a EClass
			if (bo == null)
				return;
			String name = null;
			//if(bo instanceof EPeriodicEG){
			//	name = ""+((EPeriodicEG)bo).getTime();
			//}
			//String name = ((EClass) bo).getName();
			nameText.setText(name == null ? "" : name); //$NON-NLS-1$
		}
	}
}
