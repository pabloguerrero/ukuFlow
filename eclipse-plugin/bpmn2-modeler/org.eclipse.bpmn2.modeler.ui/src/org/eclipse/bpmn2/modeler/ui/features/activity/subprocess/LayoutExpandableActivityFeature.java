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
package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.activity.LayoutActivityFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class LayoutExpandableActivityFeature extends LayoutActivityFeature {

	public LayoutExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected boolean layoutHook(Shape shape, GraphicsAlgorithm ga, Object bo, int newWidth, int newHeight) {
		if (bo != null && ga instanceof Text) {
			int padding = GraphicsUtil.TASK_IMAGE_SIZE;
			int size = ((Text)ga).getFont().getSize();
			Graphiti.getGaService().setLocationAndSize(ga, 5, 10, newWidth - 10, size);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean layout(ILayoutContext context) {
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		Activity activity = BusinessObjectUtil.getFirstElementOfType(containerShape, Activity.class);
		try {
			BPMNShape shape = DIUtils.findBPMNShape(activity);
			
			if (shape.isIsExpanded()) {
				
				// SubProcess is expanded
				
				boolean needResize = false;
				GraphicsAlgorithm parentGa = containerShape.getGraphicsAlgorithm();
				
				for (PictogramElement pe : FeatureSupport.getContainerChildren(containerShape)) {
					GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
					if (ga!=null) {
						if (ga.getX() < 0 || ga.getY() < 0) {
							needResize = true;
							break;
						}
						if (ga.getX() + ga.getWidth() > parentGa.getWidth()) {
							needResize = true;
							break;
						}
						if (ga.getY() + ga.getHeight() > parentGa.getHeight()) {
							needResize = true;
							break;
						}
					}
				}
				if (needResize) {
					ResizeShapeContext resizeContext = new ResizeShapeContext(containerShape);
					resizeContext.setX(parentGa.getX());
					resizeContext.setY(parentGa.getY());
					resizeContext.setWidth(parentGa.getWidth());
					resizeContext.setHeight(parentGa.getHeight());
					IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(resizeContext);
					resizeFeature.resizeShape(resizeContext);
				}
				
				FeatureSupport.setContainerChildrenVisible(containerShape, true);
			}
			else {
				
				// SubProcess is collapsed
				
				FeatureSupport.setContainerChildrenVisible(containerShape, false);
			}
			
		} catch (Exception e) {
			// It's OK, I've played a programmer before...
			// e.printStackTrace();
		}
		
		return super.layout(context);
	}
}
