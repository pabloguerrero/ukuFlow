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
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class JbpmOnEntryScriptPropertiesAdapter extends ExtendedPropertiesAdapter<OnEntryScriptType> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public JbpmOnEntryScriptPropertiesAdapter(AdapterFactory adapterFactory, OnEntryScriptType object) {
		super(adapterFactory, object);

    	EStructuralFeature ref = DroolsPackage.eINSTANCE.getOnEntryScriptType_Script();
    	setFeatureDescriptor(ref,
			new FeatureDescriptor<OnEntryScriptType>(adapterFactory,object,ref) {
    		
	    		@Override
				public boolean isMultiLine(Object context) {
					return true;
				}
    	});
	}

}
