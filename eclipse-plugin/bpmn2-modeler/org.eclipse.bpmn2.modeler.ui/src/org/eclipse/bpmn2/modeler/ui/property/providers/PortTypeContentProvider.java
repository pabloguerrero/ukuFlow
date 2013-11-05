/*******************************************************************************
 * Copyright (c) 2005, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.property.providers;

import java.util.List;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;

/**
 * Content provider for PortTypes.
 * 
 * Expects a Role or a Definition or a context as input.
 */
public class PortTypeContentProvider extends AbstractContentProvider  {

	@Override
	public void collectElements(Object input, List list)  {
		
		if (input instanceof Definition) {
			Definition defn = (Definition) input;
			list.addAll ( defn.getEPortTypes() );
			
		} else if (input instanceof PortType) {
			
			PortType pt = (PortType) input;
			collectElements ( pt.eContainer(), list );
			
		}
	}
}
