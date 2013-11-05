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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

public class JbpmIoParametersPropertySection extends IoParametersPropertySection {
	
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmIoParametersDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new JbpmIoParametersDetailComposite(parent,style);
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject be = super.getBusinessObjectForSelection(selection);
		if (be instanceof ScriptTask || be==null)
			return null;
		return be;
	}
}
