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
package org.eclipse.bpmn2.modeler.ui.features.gateway;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.features.gateway.AbstractCreateGatewayFeature;
import org.eclipse.bpmn2.modeler.core.features.gateway.AddGatewayFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

public class InclusiveGatewayFeatureContainer extends AbstractGatewayFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof InclusiveGateway;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddInclusiveGatewayFeature(fp);
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateInclusiveGatewayFeature(fp);
	}

	public class AddInclusiveGatewayFeature extends AddGatewayFeature<InclusiveGateway> {
		public AddInclusiveGatewayFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		protected void decorateShape(IAddContext context, ContainerShape containerShape, InclusiveGateway businessObject) {
			Ellipse ellipse = GraphicsUtil.createGatewayOuterCircle(containerShape);
			ellipse.setLineWidth(3);
		}
	}

	public static class CreateInclusiveGatewayFeature extends AbstractCreateGatewayFeature<InclusiveGateway> {

		public CreateInclusiveGatewayFeature(IFeatureProvider fp) {
			super(fp, "Inclusive Gateway", "Create "+"Inclusive Gateway");
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_INCLUSIVE_GATEWAY;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getInclusiveGateway();
		}
	}
}