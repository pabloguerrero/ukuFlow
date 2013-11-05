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

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataState;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Bob Brodt
 *
 */
public class ItemAwareElementPropertiesAdapter<T extends ItemAwareElement> extends ExtendedPropertiesAdapter<T> {

	/**
	 * @param adapterFactory
	 * @param target
	 */
	public ItemAwareElementPropertiesAdapter(AdapterFactory adapterFactory, T object) {
		super(adapterFactory, object);
		
    	EStructuralFeature feature = Bpmn2Package.eINSTANCE.getItemAwareElement_ItemSubjectRef();
    	
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	setFeatureDescriptor(feature,
			new ItemDefinitionRefFeatureDescriptor<T>(adapterFactory, object, feature)
    	);
    	
    	feature = Bpmn2Package.eINSTANCE.getItemAwareElement_DataState();
    	setFeatureDescriptor(feature,
			new FeatureDescriptor<T>(adapterFactory,object,feature) {
				@Override
				public void setValue(Object context, Object value) {
					final ItemAwareElement element = adopt(context);
					if (value instanceof String) {
						// construct a DataState from the given name string
						DataState ds = Bpmn2ModelerFactory.create(DataState.class);
						ds.setName((String)value);
						value = ds;
					}
					if (value instanceof DataState) {
						final DataState oldValue = (DataState) element.eGet(feature);
						if (value != oldValue) {
							// if this DataState belongs to some other ItemAwareElement, make a copy
							final DataState newValue = (DataState)(((DataState)value).eContainer()!=null ?
								EcoreUtil.copy((DataState) value) : value);
							TransactionalEditingDomain editingDomain = getEditingDomain(element);
							if (editingDomain == null) {
								element.eSet(feature, value);
							} else {
								editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
									@Override
									protected void doExecute() {
										element.eSet(feature, newValue);
										newValue.setId(null);
										ModelUtil.setID(newValue);
									}
								});
							}
						}
					}
				}

				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					Hashtable<String,Object> choices = new Hashtable<String,Object>();
					try {
						Resource resource = ModelUtil.getResource(object);
						List<DataState> states = ModelHandler.getAll(resource, DataState.class);
						for (DataState s : states) {
							String label = s.getName();
							if (label==null || label.isEmpty())
								label = "ID: " + s.getId();
//							else
//								label += " (ID: " +  s.getId() + ")";
							choices.put(label,s);
						}
					} catch (Exception e) {
					}
					return choices;
				}
			}
    	);
    	
    	setProperty(Bpmn2Package.eINSTANCE.getItemAwareElement_DataState(), UI_IS_MULTI_CHOICE, Boolean.TRUE);
	}

}
