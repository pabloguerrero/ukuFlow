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
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

public class JbpmCustomTaskPropertySection extends JbpmTaskPropertySection implements ITabbedPropertyConstants {

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
	 */
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmCustomTaskDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		 return new JbpmCustomTaskDetailComposite(parent, style);
	}

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			// only show this property section if the selected Task is a "custom task"
			// that is, it has a "taskName" extension attribute
			BPMN2Editor editor = (BPMN2Editor)part.getAdapter(BPMN2Editor.class);
			if (editor!=null) {
				EObject object = BusinessObjectUtil.getBusinessObjectForSelection(selection);
				
				if (object.eClass() == Bpmn2Package.eINSTANCE.getTask() && isModelObjectEnabled(object)) {
					List<EStructuralFeature> features = ModelUtil.getAnyAttributes(object);
					for (EStructuralFeature f : features) {
						if ("displayName".equals(f.getName()))
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject be = super.getBusinessObjectForSelection(selection);
		if (be instanceof Task)
			return be;
		return null;
	}
}