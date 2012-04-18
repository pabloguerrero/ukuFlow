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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.ui.property.tasks.DataAssociationPropertiesComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmDataAssociationPropertiesComposite extends DataAssociationPropertiesComposite {

	/**
	 * @param section
	 */
	public JbpmDataAssociationPropertiesComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public JbpmDataAssociationPropertiesComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void createBindings(EObject be) {
		super.createBindings(be);
		
		((GridData)mapTransformationButton.getLayoutData()).exclude = true;
		((GridData)advancedMappingButton.getLayoutData()).exclude = true;
		mapTransformationButton.setVisible(false);
		advancedMappingButton.setVisible(false);
		if (be instanceof DataOutput) {
			((GridData)mapExpressionButton.getLayoutData()).exclude = true;
			mapExpressionButton.setVisible(false);
		}
	}
}
