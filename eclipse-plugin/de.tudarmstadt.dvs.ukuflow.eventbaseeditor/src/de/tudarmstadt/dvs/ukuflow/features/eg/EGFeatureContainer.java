package de.tudarmstadt.dvs.ukuflow.features.eg;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.FeatureContainer;

public abstract class EGFeatureContainer implements FeatureContainer{
	public static int EG_HEIGHT= 50;
	public static int EG_WIDTH= 100;
	public static int EG_OFFSET= 7;
	
	public Object getApplyObject(IContext context) {
		if (context instanceof IAddContext) {
			return ((IAddContext) context).getNewObject();
		}
		/*else if (context instanceof IPictogramElementContext) {
			return BusinessObjectUtil.getFirstElementOfType(
					(((IPictogramElementContext) context).getPictogramElement()), BaseElement.class);
		}
		else if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext) context).getPictogramElements();
			if (pes.length==1)
				return BusinessObjectUtil.getFirstElementOfType(pes[0], BaseElement.class);
		}*/
		return null;
	}
	protected static GraphicsAlgorithmContainer getGraphicsAlgorithm(ContainerShape containerShape) {
		if (containerShape.getGraphicsAlgorithm() instanceof RoundedRectangle)
			return containerShape.getGraphicsAlgorithm();
		if (containerShape.getChildren().size()>0) {
			Shape shape = containerShape.getChildren().get(0);
			return shape.getGraphicsAlgorithm();
		}
		return null;
	}
}
