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
package org.eclipse.bpmn2.modeler.core.features.activity.task;

import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.features.activity.AbstractAddActivityFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class AddTaskFeature<T extends Task> extends AbstractAddActivityFeature<T> {

	public AddTaskFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context)
		        || BusinessObjectUtil.containsElementOfType(context.getTargetContainer(), FlowElementsContainer.class);
	}

	@Override
	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
		Shape textShape = peService.createShape(containerShape, false);
		MultiText text = gaService.createDefaultMultiText(getDiagram(), textShape, businessObject.getName());
		int padding = GraphicsUtil.TASK_IMAGE_SIZE;
		gaService.setLocationAndSize(text, 0, padding, context.getWidth(), context.getHeight() - padding);
		StyleUtil.applyStyle(text, businessObject);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
//		text.setFont(gaService.manageFont(getDiagram(), GaServiceImpl.DEFAULT_FONT, 8, false, true));
		link(textShape, businessObject);
	}

	@Override
	public int getWidth() {
		return GraphicsUtil.getActivitySize(getDiagram()).getWidth();
//		return GraphicsUtil.TASK_DEFAULT_WIDTH;
	}

	@Override
	public int getHeight() {
		return GraphicsUtil.getActivitySize(getDiagram()).getHeight();
//		return GraphicsUtil.TASK_DEFAULT_HEIGHT;
	}
}