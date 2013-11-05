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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.property;

import java.io.IOException;

import org.eclipse.bpmn2.di.impl.BPMNDiagramImpl;
import org.eclipse.bpmn2.modeler.core.ModelHandlerLocator;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public class AdvancedPropertySection extends DefaultPropertySection {

	private AdvancedDetailComposite composite;

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
	 */
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new AdvancedDetailComposite(this);
	}
	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new AdvancedDetailComposite(parent,style);
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject be = super.getBusinessObjectForSelection(selection);
//		if (be==null) {
//			// maybe it's the Diagram (editor canvas)?
//			be = BusinessObjectUtil.getFirstElementOfType(pe, BPMNDiagram.class);
//		}
		if (be instanceof BPMNDiagramImpl) {
			try {
				be = ModelHandlerLocator.getModelHandler(be.eResource()).getDefinitions();
			} catch (IOException e) {
				Activator.showErrorWithLogging(e);
			}
		}
		return be;
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return false;
	}

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		BPMN2Editor editor = (BPMN2Editor)part.getAdapter(BPMN2Editor.class);
		if (editor!=null)
			return editor.getPreferences().getShowAdvancedPropertiesTab();
		return false;
	}

}
