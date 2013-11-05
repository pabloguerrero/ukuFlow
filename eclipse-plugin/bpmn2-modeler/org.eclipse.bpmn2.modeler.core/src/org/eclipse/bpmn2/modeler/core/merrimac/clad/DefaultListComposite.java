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
package org.eclipse.bpmn2.modeler.core.merrimac.clad;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ModelSubclassSelectionDialog;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ListDialog;

public class DefaultListComposite extends AbstractListComposite {
	protected EClass listItemClass;

	public DefaultListComposite(AbstractBpmn2PropertySection section, int style) {
		super(section, style);
	}

	public DefaultListComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public DefaultListComposite(Composite parent, int style) {
		super(parent, style);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractListComposite#addListItem(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected EObject addListItem(EObject object, EStructuralFeature feature) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		EClass listItemClass = getListItemClass(object,feature);
		EObject newItem = null;
		if (!(list instanceof EObjectContainmentEList)) {
			// this is not a containment list so we can't add it
			// because we don't know where the new object belongs
			
			MessageDialog.openError(getShell(), "Internal Error",
					"Can not create a new " +
					listItemClass.getName() +
					" because the list is not a control. " +
					"The default addListItem() method must be implemented."
			);
			return null;
		}
		else {
			if (listItemClass==null) {
				listItemClass = getListItemClassToAdd(listItemClass);
				if (listItemClass==null)
					return null; // user cancelled
			}
			newItem = Bpmn2ModelerFactory.createFeature(object,feature,listItemClass);
			if (newItem==null) {
				MessageDialog.openError(getShell(), "Internal Error",
						"Can not create a new " +
						listItemClass.getName() +
						" because its Object Factory is unknown."
				);
			}
			else if (!list.contains(newItem))
				list.add(newItem);
		}
		return newItem;
	}
	
	/**
	 * Find all subtypes of the given listItemClass EClass and display a selection
	 * list if there are more than 1 subtypes.
	 * 
	 * @param listItemClass
	 * @return
	 */
	public EClass getListItemClassToAdd(EClass listItemClass) {
		EClass eclass = null;
		ModelSubclassSelectionDialog dialog = new ModelSubclassSelectionDialog(getDiagramEditor(), businessObject, feature);
		if (dialog.open()==Window.OK){
			eclass = (EClass)dialog.getResult()[0];
		}
		return eclass;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractListComposite#createDetailComposite(org.eclipse.swt.widgets.Composite, java.lang.Class)
	 */
	public AbstractDetailComposite createDetailComposite(Composite parent, Class eClass) {
		AbstractDetailComposite composite = PropertiesCompositeFactory.createDetailComposite(eClass, parent, SWT.NONE);
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractListComposite#editListItem(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected EObject editListItem(EObject object, EStructuralFeature feature) {
		MessageDialog.openError(getShell(), "Internal Error",
				"A List Item Editor has not been defined for "+
				ModelUtil.getDisplayName(object, feature)
				);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractListComposite#removeListItem(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, int)
	 */
	protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		int[] map = buildIndexMap(object,feature);
		if (list instanceof EObjectContainmentEList) {
			if (!canDelete(list.get(map[index])))
				return null;
		}
		EObject selected = null;
		if (index<map.length-1)
			selected = list.get(map[index+1]);
		list.remove(map[index]);
		return selected;
	}
	
	protected Object deleteListItem(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		int[] map = buildIndexMap(object,feature);
		EObject removed = list.get(map[index]);
		if (list instanceof EObjectContainmentEList) {
			if (!canDelete(removed))
				return null;
		}
		EObject selected = null;
		if (index<map.length-1)
			selected = list.get(map[index+1]);
		// this ensures that all references to this Interface are removed
		EcoreUtil.delete(removed);
		return selected;
	}
	
	protected Object getListItem(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		int[] map = buildIndexMap(object,feature);
		return list.get(map[index]);
	}
	
	protected Object moveListItemUp(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		int[] map = buildIndexMap(object,feature);
		if (index>0) {
			list.move(map[index-1], map[index]);
			return list.get(map[index-1]);
		}
		return null;
	}

	protected Object moveListItemDown(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		int[] map = buildIndexMap(object,feature);
		if (index<map.length-1) {
			list.move(map[index+1], map[index]);
			return list.get(map[index+1]);
		}
		return null;
	}

	public void setListItemClass(EClass clazz) {
		this.listItemClass = clazz;
	}
	
	public EClass getListItemClass(EObject object, EStructuralFeature feature) {
		return listItemClass;
	}
	
	protected boolean canDelete(EObject objectToDelete) {
		// make sure this object is not being referenced
		// anywhere else. If it is, we can't delete it!
		List<EObject> allDeleted = new ArrayList<EObject>();
		allDeleted.add(objectToDelete);
		TreeIterator<EObject> iter = objectToDelete.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			allDeleted.add(o);
		}
		
		List<EObject> references = new ArrayList<EObject>();
		Definitions definitions = ModelUtil.getDefinitions(objectToDelete);
		iter = definitions.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			for (EReference reference : o.eClass().getEAllReferences()) {
				if (!reference.isContainment() && !(o instanceof DiagramElement)) {
					if (reference.isMany()) {
						List refList = (List)o.eGet(reference);
						for (Object referencedObject : refList) {
							if (allDeleted.contains(referencedObject)) {
								references.add(o);
								break;
							}
							
						}
					}
					else {
						Object referencedObject = o.eGet(reference);
						if (allDeleted.contains(referencedObject)) {
							references.add(o);
						}
					}
				}
			}
		}
		if (references.size()>0) {
			ListDialog dlg = new ListDialog(getShell());
			ReferencingObjectListProvider provider = new ReferencingObjectListProvider(references);
			dlg.setContentProvider(provider);
			dlg.setLabelProvider(provider);
			dlg.setInput(references);
			boolean plural = references.size()>1;
			dlg.setMessage(
					"The selected "+ModelUtil.getLabel(objectToDelete)+" can not be deleted\n"+
					"because it is required by "+(plural?"these other elements.":"this other element.")
			);
			dlg.setAddCancelButton(false);
			dlg.setTitle("Cannot Delete Item");

			dlg.open();
			return false;
		}
		return true;
	}
	
	protected class ReferencingObjectListProvider implements IStructuredContentProvider, ILabelProvider {

		List<EObject> references;
		
		public ReferencingObjectListProvider(List<EObject> references) {
			this.references = references;
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getText(Object element) {
			String type = ModelUtil.getLabel(element);
			String name = ModelUtil.getDisplayName(element);
			return type + ": " + name;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return references.toArray();
		}
		
	}
}
