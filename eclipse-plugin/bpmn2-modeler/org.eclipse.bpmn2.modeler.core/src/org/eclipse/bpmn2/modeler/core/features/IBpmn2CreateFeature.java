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
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.context.IContext;

public interface IBpmn2CreateFeature<T extends EObject, C extends IContext> {

	public T createBusinessObject(C context);
	public T getBusinessObject(C context);
	public void putBusinessObject(C context, T businessObject);
	public EClass getBusinessObjectClass();
	public void postExecute(IExecutionInfo executionInfo);
}
