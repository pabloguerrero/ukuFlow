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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class JbpmOnExitScriptPropertiesAdapter extends ExtendedPropertiesAdapter<OnExitScriptType> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public JbpmOnExitScriptPropertiesAdapter(AdapterFactory adapterFactory, OnExitScriptType object) {
		super(adapterFactory, object);

    	EStructuralFeature ref = DroolsPackage.eINSTANCE.getOnExitScriptType_Script();
    	setFeatureDescriptor(ref,
			new FeatureDescriptor<OnExitScriptType>(adapterFactory,object,ref) {
    		
	    		@Override
				public boolean isMultiLine(Object context) {
					return true;
				}
    	});
	}

}
