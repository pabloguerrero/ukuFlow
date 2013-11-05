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

package org.eclipse.bpmn2.modeler.runtime.example;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.BooleanObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class SampleAssociationPropertySection extends DefaultPropertySection {

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new SampleAssociationDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new SampleAssociationDetailComposite(parent,style);
	}
	
	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			EObject eObj = this.getBusinessObjectForSelection(selection);
			if (eObj instanceof Association) {
				EStructuralFeature f = ModelUtil.getAnyAttribute(eObj, "affectsTaskExecution");
				return f!=null;
			}
		}
		return false;
	}

	public class SampleAssociationDetailComposite extends DefaultDetailComposite {

		/**
		 * @param section
		 */
		public SampleAssociationDetailComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}

		public SampleAssociationDetailComposite(Composite parent, int style) {
			super(parent, style);
		}
		
		@Override
		public void createBindings(EObject be) {
			EStructuralFeature attr = ModelUtil.getAnyAttribute(be, "affectsTaskExecution");
			if (attr!=null) {
				ObjectEditor editor = new BooleanObjectEditor(this,be,attr);
				editor.createControl( getAttributesParent(), "Affects Task");
			}
			else
				super.createBindings(be);
		}
	}
}
