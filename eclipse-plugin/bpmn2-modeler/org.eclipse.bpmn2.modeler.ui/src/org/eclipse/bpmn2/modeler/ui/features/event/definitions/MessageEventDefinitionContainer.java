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
package org.eclipse.bpmn2.modeler.ui.features.event.definitions;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.modeler.core.features.event.definitions.AbstractEventDefinitionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.event.definitions.AbstractCreateEventDefinitionFeature;
import org.eclipse.bpmn2.modeler.core.features.event.definitions.DecorationAlgorithm;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.Envelope;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil.FillStyle;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class MessageEventDefinitionContainer extends AbstractEventDefinitionFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof MessageEventDefinition;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateMessageEventDefinition(fp);
	}

	@Override
	protected Shape drawForStart(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	@Override
	protected Shape drawForEnd(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilledEnvelope(algorithm, shape);
	}

	@Override
	protected Shape drawForThrow(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilledEnvelope(algorithm, shape);
	}

	@Override
	protected Shape drawForCatch(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	@Override
	protected Shape drawForBoundary(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	private Shape drawEnvleope(DecorationAlgorithm algorithm, ContainerShape shape) {
		BaseElement be = BusinessObjectUtil.getFirstElementOfType(shape, BaseElement.class, true);
		Shape envelopeShape = Graphiti.getPeService().createShape(shape, false);
		Envelope env = GraphicsUtil.createEventEnvelope(envelopeShape);
		StyleUtil.setFillStyle(env.rect, FillStyle.FILL_STYLE_BACKGROUND);
		StyleUtil.applyStyle(env.rect, be);
		StyleUtil.setFillStyle(env.line, FillStyle.FILL_STYLE_BACKGROUND);
		StyleUtil.applyStyle(env.line, be);
		return envelopeShape;
	}

	private Shape drawFilledEnvelope(DecorationAlgorithm algorithm, ContainerShape shape) {
		BaseElement be = BusinessObjectUtil.getFirstElementOfType(shape, BaseElement.class, true);
		Shape envelopeShape = Graphiti.getPeService().createShape(shape, false);
		Envelope env = GraphicsUtil.createEventEnvelope(envelopeShape);
		StyleUtil.setFillStyle(env.rect, FillStyle.FILL_STYLE_INVERT);
		StyleUtil.applyStyle(env.rect, be);
		StyleUtil.setFillStyle(env.line, FillStyle.FILL_STYLE_INVERT);
		StyleUtil.applyStyle(env.line, be);
		return envelopeShape;
	}

	public static class CreateMessageEventDefinition extends AbstractCreateEventDefinitionFeature<MessageEventDefinition> {

		public CreateMessageEventDefinition(IFeatureProvider fp) {
			super(fp, "Message Event Definition", "Create "+"Message Event Definition");
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_MESSAGE;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature#getBusinessObjectClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getMessageEventDefinition();
		}
	}
}
