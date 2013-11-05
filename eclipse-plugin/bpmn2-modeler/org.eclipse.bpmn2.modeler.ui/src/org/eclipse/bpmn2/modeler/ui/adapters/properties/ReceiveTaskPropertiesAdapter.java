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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

/**
 * @author Bob Brodt
 *
 */
public class ReceiveTaskPropertiesAdapter extends TaskPropertiesAdapter<ReceiveTask> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public ReceiveTaskPropertiesAdapter(AdapterFactory adapterFactory, ReceiveTask object) {
		super(adapterFactory, object);

    	EStructuralFeature feature = Bpmn2Package.eINSTANCE.getReceiveTask_MessageRef();
    	setProperty(feature, UI_CAN_CREATE_NEW, Boolean.TRUE);
    	setProperty(feature, UI_CAN_EDIT, Boolean.TRUE);
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	
    	setFeatureDescriptor(feature, new MessageRefFeatureDescriptor<ReceiveTask>(adapterFactory,object,feature) {
    		
    		@Override
    		public void setValue(Object context, final Object value) {
    			if (value instanceof Message || value==null) {
	    			final ReceiveTask object = adopt(context);
	    			final Message message = (Message)value; 
					final TransactionalEditingDomain editingDomain = getEditingDomain(object);
					if (editingDomain == null) {
						setMessageRef(object, message);
					} else {
						editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
							@Override
							protected void doExecute() {
								setMessageRef(object, message);
							}
						});
					}
    			}
    		}
    		
    	});

    	feature = Bpmn2Package.eINSTANCE.getReceiveTask_OperationRef();
    	setProperty(feature, UI_CAN_CREATE_NEW, Boolean.TRUE);
    	setProperty(feature, UI_CAN_EDIT, Boolean.FALSE);
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);

		setFeatureDescriptor(feature, new OperationRefFeatureDescriptor<ReceiveTask>(adapterFactory,object,feature) {
    		
    		@Override
   		public void setValue(Object context, final Object value) {
    			if (value instanceof Operation || value==null) {
	    			final ReceiveTask object = adopt(context);
	    			final Operation operation = (Operation)value; 
					final TransactionalEditingDomain editingDomain = getEditingDomain(object);
					if (editingDomain == null) {
						setOperationRef(object, operation);
					} else {
						editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
							@Override
							protected void doExecute() {
								setOperationRef(object, operation);
							}
						});
					}
    			}
    		}
    		
    	});
	}
	
	private void setMessageRef(ReceiveTask receiveTask, Message message) {
		ResourceSet resourceSet = receiveTask.eResource().getResourceSet();
		
		// first change the MessageRef on the ReceiveTask
		receiveTask.setMessageRef(message);
		
		// If there are any INCOMING Message Flows attached to this SendTask figure,
		// make sure the MessageFlow.messageRef is the same as ours
		List<ContainerShape> shapes = DIUtils.getContainerShapes(resourceSet, receiveTask);
		for (ContainerShape shape : shapes) {
			for (Anchor a : shape.getAnchors()) {
				for (Connection c : a.getIncomingConnections()) {
					Object o = BusinessObjectUtil.getFirstBaseElement(c);
					if (o instanceof MessageFlow) {
						((MessageFlow)o).setMessageRef(message);
					}
					// also set the "messageRef" on the source of this Message Flow
					// (the source should be a SendTask)
					o = BusinessObjectUtil.getFirstBaseElement(c.getStart().getParent());
					if (o instanceof SendTask) {
						((SendTask)o).setMessageRef(message);
					}
				}
			}
		}
	}

	private void setOperationRef(ReceiveTask receiveTask, Operation operation) {
		receiveTask.setOperationRef(operation);
		Message message = operation==null ? null : operation.getInMessageRef();
		setMessageRef(receiveTask, message);
	}
}
