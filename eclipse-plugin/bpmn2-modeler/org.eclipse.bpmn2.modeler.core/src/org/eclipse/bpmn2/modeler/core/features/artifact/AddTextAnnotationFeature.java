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
package org.eclipse.bpmn2.modeler.core.features.artifact;

import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddElementFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

public class AddTextAnnotationFeature extends AbstractBpmn2AddElementFeature<TextAnnotation> {

	public AddTextAnnotationFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return FeatureSupport.isValidArtifactTarget(context);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		TextAnnotation businessObject = getBusinessObject(context);

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape = peCreateService.createContainerShape(context.getTargetContainer(), true);

		IGaService gaService = Graphiti.getGaService();

		int width = this.getWidth(context);
		int height = this.getHeight(context);
		int commentEdge = 15;

		Rectangle rect = gaService.createInvisibleRectangle(containerShape);
		gaService.setLocationAndSize(rect, context.getX(), context.getY(), width, height);

		Shape lineShape = peCreateService.createShape(containerShape, false);
		Polyline line = gaService.createPolyline(lineShape, new int[] { commentEdge, 0, 0, 0, 0, height, commentEdge,
				height });
		line.setStyle(StyleUtil.getStyleForClass(getDiagram()));
		line.setLineWidth(2);
		gaService.setLocationAndSize(line, 0, 0, commentEdge, height);

		Shape textShape = peCreateService.createShape(containerShape, false);
		MultiText text = gaService.createDefaultMultiText(getDiagram(), textShape, businessObject.getText());
		StyleUtil.applyStyle(text, businessObject);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		gaService.setLocationAndSize(text, 5, 5, width - 5, height - 5);

		boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
		createDIShape(containerShape, businessObject, !isImport);
		link(textShape, businessObject);
		
		// hook for subclasses to inject extra code
		((AddContext)context).setWidth(width);
		((AddContext)context).setHeight(height);
		decorateShape(context, containerShape, businessObject);

		peCreateService.createChopboxAnchor(containerShape);
		AnchorUtil.addFixedPointAnchors(containerShape, rect);
		
		layoutPictogramElement(containerShape);
		return containerShape;
	}

	@Override
	public int getHeight() {
		return 100;
	}

	@Override
	public int getWidth() {
		return 50;
	}
}