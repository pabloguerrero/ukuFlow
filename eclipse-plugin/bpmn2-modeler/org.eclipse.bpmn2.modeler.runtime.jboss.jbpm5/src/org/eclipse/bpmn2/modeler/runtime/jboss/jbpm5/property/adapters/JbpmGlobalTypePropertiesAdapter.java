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

import java.util.Hashtable;

import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Bob Brodt
 *
 */
public class JbpmGlobalTypePropertiesAdapter extends ExtendedPropertiesAdapter<GlobalType> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public JbpmGlobalTypePropertiesAdapter(AdapterFactory adapterFactory, GlobalType object) {
		super(adapterFactory, object);

    	EStructuralFeature feature = DroolsPackage.eINSTANCE.getGlobalType_Identifier();
    	setFeatureDescriptor(feature,
			new FeatureDescriptor<GlobalType>(adapterFactory,object,feature) {
				@Override
				public String getLabel(Object context) {
					return "Name";
				}
    		});	

    	feature = DroolsPackage.eINSTANCE.getGlobalType_Type();
    	setProperty(feature, UI_CAN_CREATE_NEW, Boolean.TRUE);
    	setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	
    	setFeatureDescriptor(feature,
			new FeatureDescriptor<GlobalType>(adapterFactory,object,feature) {
				@Override
				public String getLabel(Object context) {
					return "Data Type";
				}
				
				@Override
				public void setValue(Object context, final Object value) {
					final GlobalType global = adopt(context);

					TransactionalEditingDomain domain = getEditingDomain(object);
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							global.setType(JbpmModelUtil.getDataType(value));
						}
					});
				}
				
				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					final GlobalType global = adopt(context);
					return JbpmModelUtil.collectAllDataTypes(global);
				}
				
				@Override
				public boolean isMultiLine(Object context) {
					return true;
				}
			}
    	);
    	
		
		setObjectDescriptor(new ObjectDescriptor<GlobalType>(adapterFactory, object) {

			@Override
			public String getLabel(Object context) {
				return "Global Variable";
			}

			@Override
			public String getDisplayName(Object context) {
				GlobalType object = adopt(context);
				return object.getIdentifier();
			}
		});

	}

}
