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
package org.eclipse.bpmn2.modeler.ui.features.activity.task;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.ManualTask;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AbstractCreateTaskFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AddTaskFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.activity.AppendActivityFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.MorphActivityFeature;
import org.eclipse.bpmn2.modeler.ui.features.event.AppendEventFeature;
import org.eclipse.bpmn2.modeler.ui.features.gateway.AppendGatewayFeature;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public class ReceiveTaskFeatureContainer extends AbstractTaskFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof ReceiveTask;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateReceiveTaskFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddReceiveTaskFeature(fp);
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(fp);
		ICustomFeature[] thisFeatures = new ICustomFeature[1 + superFeatures.length];
		int i;
		for (i=0; i<superFeatures.length; ++i)
			thisFeatures[i] = superFeatures[i];
		thisFeatures[i++] = new GotoEventViewerCustomFeature(fp);
		//thisFeatures[i++] = new AppendActivityFeature(fp);
		//thisFeatures[i++] = new AppendGatewayFeature(fp);
		//thisFeatures[i++] = new AppendEventFeature(fp);
		//thisFeatures[i++] = new MorphActivityFeature(fp);
		return thisFeatures;
	}
	public static class AddReceiveTaskFeature extends AbstractAddDecoratedTaskFeature<ReceiveTask> {

		public AddReceiveTaskFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_RECEIVE_TASK;
		}
	}

	public static class CreateReceiveTaskFeature extends AbstractCreateTaskFeature<ReceiveTask> {

		public CreateReceiveTaskFeature(IFeatureProvider fp) {
			super(fp, "Receive Task", "Create "+"Receive Task");
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_RECEIVE_TASK;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getReceiveTask();
		}
	}
}