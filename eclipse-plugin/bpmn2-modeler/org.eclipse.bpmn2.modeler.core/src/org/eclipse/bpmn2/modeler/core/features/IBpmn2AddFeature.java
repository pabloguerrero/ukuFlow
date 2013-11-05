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
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.context.IAddContext;

public interface IBpmn2AddFeature<T extends EObject> {

	public T getBusinessObject(IAddContext context);
	public void putBusinessObject(IAddContext context, T businessObject);
	public void postExecute(IExecutionInfo executionInfo);
}
