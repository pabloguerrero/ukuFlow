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
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AbstractCreateTaskFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AddTaskFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public class SendTaskFeatureContainer extends AbstractTaskFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof SendTask;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateSendTaskFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddSendTaskFeature(fp);
	}

	public static class AddSendTaskFeature extends AbstractAddDecoratedTaskFeature<SendTask> {

		public AddSendTaskFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_SEND_TASK;
		}
	}
	
	public static class CreateSendTaskFeature extends AbstractCreateTaskFeature<SendTask> {

		public CreateSendTaskFeature(IFeatureProvider fp) {
			super(fp, "Send Task", "Create "+"Send Task");
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_SEND_TASK;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getSendTask();
		}
	}
}