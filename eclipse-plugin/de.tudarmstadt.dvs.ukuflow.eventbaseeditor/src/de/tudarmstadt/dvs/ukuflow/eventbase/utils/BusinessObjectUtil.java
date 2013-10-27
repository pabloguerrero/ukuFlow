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
 * @author Ivar Meikas
 ******************************************************************************/
package de.tudarmstadt.dvs.ukuflow.eventbase.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class BusinessObjectUtil {

	public static PictogramElement getPictogramElementForSelection(ISelection selection) {
		EditPart editPart = getEditPartForSelection(selection);
		if (editPart != null && editPart.getModel() instanceof PictogramElement) {
			return (PictogramElement) editPart.getModel();
		}
		if (selection instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection)selection).getFirstElement();
			if (o instanceof PictogramElement)
				return (PictogramElement)o;
		}
		return null;
	}

	public static EObject getBusinessObjectForSelection(ISelection selection) {
		PictogramElement pe = getPictogramElementForSelection(selection);
		return getBusinessObjectForPictogramElement(pe);
	}

	public static EObject getBusinessObjectForPictogramElement(PictogramElement pe) {
		if (pe!=null) {
			// Substitute the Connection for a ConnectionDecorator because these
			// do not have linked business objects although a connection decorator
			// can still be selected. The net effect is that when a connection
			// decorator is selected, it is the same as selecting the connection
			// that owns it.
			if (pe instanceof ConnectionDecorator) {
				pe = ((ConnectionDecorator)pe).getConnection();
			}
			Object be = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			if (be instanceof EObject)
				return (EObject) be;
		}
		return null;
	}

	public static EditPart getEditPartForSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection &&
				((IStructuredSelection) selection).isEmpty()==false) {
		
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			EditPart editPart = null;
			if (firstElement instanceof EditPart) {
				editPart = (EditPart) firstElement;
			} else if (firstElement instanceof IAdaptable) {
				editPart = (EditPart) ((IAdaptable) firstElement).getAdapter(EditPart.class);
			}
			return editPart;
		}
		return null;
	}
	
	public static List<Diagram> getAllDiagrams(Diagram diagram) {
		List<Diagram> list = new ArrayList<Diagram>();
		list.add(diagram);
		
		Resource resource = diagram.eResource();
		for (EObject o : resource.getContents()) {
			if (o instanceof Diagram && o!=diagram) {
				list.add((Diagram)o);
			}
		}
		
		return list;
	}
}