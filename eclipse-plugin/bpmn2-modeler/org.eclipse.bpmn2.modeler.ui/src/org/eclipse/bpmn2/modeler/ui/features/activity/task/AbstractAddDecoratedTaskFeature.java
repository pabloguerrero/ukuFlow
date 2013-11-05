package org.eclipse.bpmn2.modeler.ui.features.activity.task;

import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AddTaskFeature;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public abstract class AbstractAddDecoratedTaskFeature<T extends Task> extends AddTaskFeature<T> {
	
	public AbstractAddDecoratedTaskFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
		super.decorateShape(context, containerShape, businessObject);
		
		GraphicsAlgorithmContainer ga = getGraphicsAlgorithm(containerShape);
		IGaService service = Graphiti.getGaService();
		Image img = service.createImage(ga, getStencilImageId());
		service.setLocationAndSize(img, 2, 2, GraphicsUtil.TASK_IMAGE_SIZE, GraphicsUtil.TASK_IMAGE_SIZE);
	}
	
	protected abstract String getStencilImageId();

}