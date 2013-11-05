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

import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * @author Bob Brodt
 *
 */
public class SequenceFlowPropertiesAdapter extends ExtendedPropertiesAdapter<SequenceFlow> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public SequenceFlowPropertiesAdapter(AdapterFactory adapterFactory, SequenceFlow object) {
		super(adapterFactory, object);
		
		// TODO: any special handling here?
	}

}
