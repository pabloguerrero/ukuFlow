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
package org.eclipse.bpmn2.modeler.ui.property.tasks;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.CallableElement;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.InputSet;
import org.eclipse.bpmn2.OutputSet;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class IoParametersListComposite extends DefaultListComposite {

	/**
	 * 
	 */
	protected Activity activity;
	protected CallableElement element;
	protected InputOutputSpecification ioSpecification;
	protected EStructuralFeature ioFeature;
	protected boolean isInput;
	
	public IoParametersListComposite(IoParametersDetailComposite detailComposite, EObject container, InputOutputSpecification ioSpecification, EStructuralFeature ioFeature) {
		super(detailComposite, DEFAULT_STYLE);
		this.ioFeature = ioFeature;
		this.ioSpecification = ioSpecification;
		isInput = ("dataInputs".equals(ioFeature.getName()));
		if (container instanceof Activity) {
			this.activity = (Activity)container;
			columnProvider = new ListCompositeColumnProvider(this);
			EClass listItemClass = (EClass)ioFeature.getEType();
			setListItemClass(listItemClass);
			
			EStructuralFeature f;
			if (isInput) {
				f = PACKAGE.getActivity_DataInputAssociations();
				columnProvider.add(new IoParameterMappingColumn(activity,f)).setHeaderText("From");

				f = (EAttribute)listItemClass.getEStructuralFeature("name");
				columnProvider.add(new IoParameterNameColumn(activity,f)).setHeaderText("To");

				columnProvider.add(new TableColumn(activity,PACKAGE.getDataInput_IsCollection()));
			}
			else {
				f = (EAttribute)listItemClass.getEStructuralFeature("name");
				columnProvider.add(new IoParameterNameColumn(activity,f)).setHeaderText("From");

				f = PACKAGE.getActivity_DataOutputAssociations();
				columnProvider.add(new IoParameterMappingColumn(activity,f)).setHeaderText("To");

				columnProvider.add(new TableColumn(activity,PACKAGE.getDataOutput_IsCollection()));
			}
		}
		else if (container instanceof CallableElement) {
			this.element = (CallableElement)container;
		}
	}

	@Override
	protected EObject addListItem(EObject object, EStructuralFeature feature) {
		EObject param = null;
		
		// Make sure that the ioSpecification is contained in our Activity.
		InsertionAdapter.executeIfNeeded(ioSpecification);
		
		param = super.addListItem(object, feature);
		
		// make sure the ioSpecification has both a default InputSet and OutputSet
		if (ioSpecification.getInputSets().size()==0) {
			InputSet is = Bpmn2ModelerFactory.create(ioSpecification.eResource(), InputSet.class);
			ioSpecification.getInputSets().add(is);
		}
		if (ioSpecification.getOutputSets().size()==0) {
			OutputSet os = Bpmn2ModelerFactory.create(ioSpecification.eResource(), OutputSet.class);
			ioSpecification.getOutputSets().add(os);
		}
		
		if (activity!=null) {
			// this is an Activity - create an Input or Output DataAssociation
			if (param instanceof DataInput) {
				DataInputAssociation inputAssociation = createModelObject(DataInputAssociation.class);
				activity.getDataInputAssociations().add(inputAssociation);
				inputAssociation.setTargetRef((DataInput) param);
			}
			else if (param instanceof DataOutput)
			{
				DataOutputAssociation outputAssociation = createModelObject(DataOutputAssociation.class);
				activity.getDataOutputAssociations().add(outputAssociation);
				outputAssociation.getSourceRef().clear();
				outputAssociation.getSourceRef().add((DataOutput) param);
			}
		}
		else if (element!=null) {
			// this is a CallableElement - it has no DataAssociations so we're all done
		}
		return param;
	}

	@Override
	protected EObject editListItem(EObject object, EStructuralFeature feature) {
		return super.editListItem(object, feature);
	}

	@Override
	protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
		EList<EObject> list = (EList<EObject>)object.eGet(feature);
		EObject item = list.get(index);

		if (item instanceof DataInput) {
			// remove parameter from inputSets
			List<InputSet> inputSets = ioSpecification.getInputSets();
			for (InputSet is : inputSets) {
				if (is.getDataInputRefs().contains(item))
					is.getDataInputRefs().remove(item);
			}
		}
		else if (item instanceof DataOutput) {
			// remove parameter from outputSets
			List<OutputSet> OutputSets = ioSpecification.getOutputSets();
			for (OutputSet is : OutputSets) {
				if (is.getDataOutputRefs().contains(item))
					is.getDataOutputRefs().remove(item);
			}
		}
		
		if (activity!=null) {
			// this is an Activity
			// remove Input or Output DataAssociations
			if (item instanceof DataInput) {
				List<DataInputAssociation> dataInputAssociations = activity.getDataInputAssociations();
				List<DataInputAssociation> removed = new ArrayList<DataInputAssociation>();
				for (DataInputAssociation dia : dataInputAssociations) {
					if (dia.getTargetRef()!=null && dia.getTargetRef().equals(item))
						removed.add(dia);
				}
				dataInputAssociations.removeAll(removed);
			}
			else if (item instanceof DataOutput) {
				List<DataOutputAssociation> dataOutputAssociations = activity.getDataOutputAssociations();
				List<DataOutputAssociation> removed = new ArrayList<DataOutputAssociation>();
				for (DataOutputAssociation doa : dataOutputAssociations) {
					if (doa.getSourceRef()!=null && doa.getSourceRef().contains(item))
						removed.add(doa);
				}
				dataOutputAssociations.removeAll(removed);
			}
		}
		else if (element!=null) {
			// this is a CallableElement
		}
		else
			return false;

		return super.removeListItem(object, feature, index);
	}
	
}