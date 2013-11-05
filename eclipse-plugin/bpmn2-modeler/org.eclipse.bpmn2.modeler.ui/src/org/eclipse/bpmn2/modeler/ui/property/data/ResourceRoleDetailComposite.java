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

package org.eclipse.bpmn2.modeler.ui.property.data;

import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class ResourceRoleDetailComposite extends DefaultDetailComposite {

	/**
	 * @param parent
	 * @param style
	 */
	public ResourceRoleDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * @param section
	 */
	public ResourceRoleDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"name",
						"resourceRef",
						"resourceAssignmentExpression",
						"resourceParameterBindings",
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}
	
	@Override
	protected void bindReference(Composite parent, EObject object, EReference reference) {
		if ("resourceAssignmentExpression".equals(reference.getName()) &&
				isModelObjectEnabled(object.eClass(), reference)) {
			
			ResourceAssignmentExpressionDetailComposite composite =
					new ResourceAssignmentExpressionDetailComposite(this, SWT.NONE);
			EObject value = (EObject)object.eGet(reference);
			if (value==null) {
				value = modelHandler.create((EClass)reference.getEType());
				InsertionAdapter.add(object, reference, value);
			}
			composite.setBusinessObject(value);
		}
		else
			super.bindReference(parent, object, reference);
	}
}
