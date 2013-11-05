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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.property.tasks.TaskPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class JbpmTaskPropertySection extends TaskPropertySection {

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			EObject object = BusinessObjectUtil.getBusinessObjectForSelection(selection);
			if (object!=null && Bpmn2Package.eINSTANCE.getTask().isInstance(object)) {
				List<EStructuralFeature> features = ModelUtil.getAnyAttributes(object);
				for (EStructuralFeature f : features) {
					if ("displayName".equals(f.getName()))
						// don't display this tab for Custom Tasks
						return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmTaskDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new JbpmTaskDetailComposite(parent,style);
	}

}
