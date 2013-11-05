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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.merrimac.clad;


import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractDialogComposite extends Composite {
	
	protected EClass eclass;
	
	public AbstractDialogComposite(Composite parent, EClass eclass, int style) {
		super(parent,style);
		this.eclass = eclass;
	}
}
