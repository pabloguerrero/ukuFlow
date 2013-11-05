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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * @author Bob Brodt
 *
 */
public class TaskPropertiesAdapter<T extends Task> extends ActivityPropertiesAdapter<T> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public TaskPropertiesAdapter(AdapterFactory adapterFactory, T object) {
		super(adapterFactory, object);
    	setProperty(Bpmn2Package.eINSTANCE.getInteractionNode_IncomingConversationLinks(), UI_CAN_CREATE_NEW, Boolean.FALSE);
    	setProperty(Bpmn2Package.eINSTANCE.getInteractionNode_OutgoingConversationLinks(), UI_CAN_CREATE_NEW, Boolean.FALSE);
	}

}
