package de.tudarmstadt.dvs.ukuflow.features.ef.complex;

import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;


import de.tudarmstadt.dvs.ukuflow.eventbase.core.StyleUtil;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.diagram.UkuFlowFeatureProvider;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexEF;
/**
 * 
 * @author �Hien Quoc Dang�
 * @deprecated no thing for complex event
 */
public class EComplexEFAddFeature extends AbstractAddShapeFeature{

	public static final int INVISIBLE_RECT_RIGHT = 6;
	
	public EComplexEFAddFeature(IFeatureProvider fp) {
		super(fp);
	}

	public boolean canAdd(IAddContext context) {
		final Object newObject = context.getNewObject();
		if(newObject instanceof EComplexEF)
		if(context.getTargetContainer() instanceof Diagram){
			return true;
		}
		return false;
	}

	public PictogramElement add(IAddContext context) {
		final EComplexEF addedClass = (EComplexEF)context.getNewObject(); 
		final Diagram targetDiagram = (Diagram) context.getTargetContainer();
		// CONTAINER SHAPE WITH ROUNDED RECTANGLE
				final IPeCreateService peCreateService = Graphiti.getPeCreateService();
				final ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);
				final IGaService gaService = Graphiti.getGaService();
				
				// check whether the context has a size (e.g. from a create feature)
				// otherwise define a default size for the shape
				final int width = context.getWidth() <= 0 ? 100 : context.getWidth();
				final int height = context.getHeight() <= 0 ? 50 : context.getHeight();
				int xy[] = new int[] { 0, 25, 20, 0, 80,0,100,25,80,50,20,50,};
				gaService.createPolygon(containerShape,xy);
				
				RoundedRectangle roundedRectangle; // need to access it later
				{
					// create invisible outer rectangle expanded by
					// the width needed for the anchor
					final Rectangle invisibleRectangle = gaService.createInvisibleRectangle(containerShape);			
					gaService.setLocationAndSize(invisibleRectangle, context.getX(), context.getY(), width + INVISIBLE_RECT_RIGHT, height);

					// create and set visible rectangle inside invisible rectangle
					//gaService.create
					roundedRectangle = gaService.createPlainRoundedRectangle(invisibleRectangle, 5, 5);
					roundedRectangle.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
					gaService.setLocationAndSize(roundedRectangle, 0, 0, width, height);

					// if addedClass has no resource we add it to the resource of the diagram
					// in a real scenario the business model would have its own resource
					//if (addedClass.eResource() == null) {
					//	getDiagram().eResource().getContents().add(addedClass);
					//}

					// create link and wire it
					link(containerShape, addedClass);
				}

				// SHAPE WITH LINE
				{
					// create shape for line
					final Shape shape = peCreateService.createShape(containerShape, false);

					// create and set graphics algorithm
					final Polygon polyline = gaService.createPlainPolygon(shape,new int[]{0,0,width,height/2,0,height});
					//final Polyline polyline = gaService.createPlainPolyline(shape, new int[] { 0,0,0, 20, width, 10 });
					polyline.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
				}

				// SHAPE WITH TEXT
				{
					// create shape for text
					final Shape shape = peCreateService.createShape(containerShape, false);

					// create and set text graphics algorithm
					final Text text = gaService.createPlainText(shape, addedClass.getClass().getSimpleName());
					text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
					gaService.setLocationAndSize(text, 0, 0, width, 20);

					// create link and wire it
					link(shape, addedClass);
					//addedClass.setName(addedClass.getClass().getSimpleName());
					updatePictogramElement(shape);
					// provide information to support direct-editing directly
					// after object creation (must be activated additionally)
					final IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
					// set container shape for direct editing after object creation
					directEditingInfo.setMainPictogramElement(containerShape);
					// set shape and graphics algorithm where the editor for
					// direct editing shall be opened after object creation
					directEditingInfo.setPictogramElement(shape);
					directEditingInfo.setGraphicsAlgorithm(text);
				}

				// add a chopbox anchor to the shape
				peCreateService.createChopboxAnchor(containerShape);
				//peCreateService.createBoxRelativeAnchor(containerShape);

				// create an additional box relative anchor at middle-right
				final BoxRelativeAnchor boxAnchor = peCreateService.createBoxRelativeAnchor(containerShape);
				boxAnchor.setRelativeWidth(1.0);
				boxAnchor.setRelativeHeight(0.38); // Use golden section

				// anchor references visible rectangle instead of invisible rectangle
				boxAnchor.setReferencedGraphicsAlgorithm(roundedRectangle);

				// assign a graphics algorithm for the box relative anchor
				final Ellipse ellipse = gaService.createPlainEllipse(boxAnchor);

				// anchor is located on the right border of the visible rectangle
				// and touches the border of the invisible rectangle
				final int w = INVISIBLE_RECT_RIGHT;
				gaService.setLocationAndSize(ellipse, -w, -w, 2 * w, 2 * w);
				ellipse.setStyle(StyleUtil.getStyleForEClass(getDiagram()));

				
				ChopboxAnchor chopboxAnchor = peCreateService.createChopboxAnchor(containerShape);				
				UkuFlowFeatureProvider tfp = (UkuFlowFeatureProvider)getFeatureProvider();
				//EventObjIndependenceSolver pojoIndependenceSolver = tfp.getCustomIndependenceSolver();										
				//pojoIndependenceSolver.registerGraphicalObject(ChopboxAnchor.class, addedClass, chopboxAnchor);
				// call the layout feature
				layoutPictogramElement(containerShape);

				return containerShape;
	}

}
