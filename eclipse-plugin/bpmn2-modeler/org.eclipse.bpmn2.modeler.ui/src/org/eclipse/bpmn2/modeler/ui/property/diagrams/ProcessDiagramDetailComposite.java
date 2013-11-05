/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.property.diagrams;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

public class ProcessDiagramDetailComposite extends DefaultDetailComposite {

	private AbstractPropertiesProvider propertiesProvider;

	public ProcessDiagramDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	public ProcessDiagramDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider == null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						// Hien:
						// code for additional process's properties
						// begin:
						"mindotInstances",
						"maxdotInstances",
						"number_ofLoops",
						// end
						
						"id", "name",
						"anyAttribute",
						"processType",
						"isExecutable",
						"isClosed",
						"correlationSubscriptions"
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
	public boolean needRefresh(EObject be) {
		return super.needRefresh(be);
	}
}
