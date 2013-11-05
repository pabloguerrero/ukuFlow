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
package org.eclipse.bpmn2.modeler.ui.features.participant;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.DefaultDeleteBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.editor.DesignEditor;
import org.eclipse.bpmn2.modeler.ui.features.AbstractDefaultDeleteFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ChoreographyUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class DeleteParticipantFeature extends DefaultDeleteBPMNShapeFeature {

	public DeleteParticipantFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void delete(IDeleteContext context) {
		// Delete the pool's process and the BPMNDiagram page (if any).
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof Participant) {
			bo = ((Participant)bo).getProcessRef();
		}
		if (bo instanceof FlowElementsContainer) {
			BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram((BaseElement)bo);
			if (bpmnDiagram != null) {
				DIUtils.deleteDiagram(getDiagramEditor(), bpmnDiagram);
			}
			EcoreUtil.delete((EObject) bo);
		}

		super.delete(context);
	}

	@Override
	public boolean canDelete(IDeleteContext context) {
		// Participant bands in a ChoreographyTask only be "deleted" (from the model)
		// if there are no other references to the participant; but they can be "removed"
		// (from the ChoreographyTask's participantRef list) at any time.
		// @see RemoveChoreographyParticipantFeature
		PictogramElement pe = context.getPictogramElement();
		if (ChoreographyUtil.isChoreographyParticipantBand(pe)) {
			int referenceCount = 0;
			Participant participant = BusinessObjectUtil.getFirstElementOfType(pe, Participant.class);
			Definitions definitions = ModelUtil.getDefinitions(participant);
			TreeIterator<EObject> iter = definitions.eAllContents();
			while (iter.hasNext()) {
				EObject o = iter.next();
				for (EReference reference : o.eClass().getEAllReferences()) {
					if (!reference.isContainment() && !(o instanceof DiagramElement)) {
						if (reference.isMany()) {
							List list = (List)o.eGet(reference);
							for (Object referencedObject : list) {
								if (referencedObject==participant)
									++referenceCount;
							}
						}
						else {
							Object referencedObject = o.eGet(reference);
							if (referencedObject == participant)
								++referenceCount;
						}
					}
				}
			}
			return referenceCount <= 1;
		}
		return true;
	}

}