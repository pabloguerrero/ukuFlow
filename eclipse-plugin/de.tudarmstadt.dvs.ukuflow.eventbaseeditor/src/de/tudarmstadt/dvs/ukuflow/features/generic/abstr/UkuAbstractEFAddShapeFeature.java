package de.tudarmstadt.dvs.ukuflow.features.generic.abstr;

import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.ModelUtil;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.StyleUtil;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public abstract class UkuAbstractEFAddShapeFeature extends
		AbstractAddShapeFeature {
	public static final int HEIGHT = 50;
	public static final int WIDTH = 100;
	public static final int OFFSET = 5;
	private BpmnLog log = BpmnLog.getInstance(getClass().getSimpleName());

	public UkuAbstractEFAddShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public String getIconID() {
		return EventImageProvider.FUNNEL_ICON;
	}

	private String getName(Object e) {
		String name = e.getClass().getSimpleName();
		name = name.substring(2, name.length() - 4);
		return ModelUtil.toDisplayName(name).toLowerCase().replace(' ', '_');
	}

	protected GraphicsAlgorithmContainer getGraphicsAlgorithm(
			ContainerShape containerShape) {
		if (containerShape.getGraphicsAlgorithm() instanceof RoundedRectangle)
			return containerShape.getGraphicsAlgorithm();
		if (containerShape.getChildren().size() > 0) {
			Shape shape = containerShape.getChildren().get(0);
			return shape.getGraphicsAlgorithm();
		}
		return null;
	}

	public PictogramElement add(IAddContext context) {
		final EventBaseOperator addedClass = (EventBaseOperator) context
				.getNewObject();
		final Diagram targetDiagram = (Diagram) context.getTargetContainer();
		// CONTAINER SHAPE WITH ROUNDED RECTANGLE
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final ContainerShape containerShape = peCreateService
				.createContainerShape(targetDiagram, true);

		// final int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		// final int height = context.getHeight() <= 0 ? 50 :
		// context.getHeight();

		final IGaService gaService = Graphiti.getGaService();

		int xy[] = new int[] { 0, HEIGHT / 2, OFFSET, 0, WIDTH - OFFSET, 0,
				WIDTH, HEIGHT / 2, WIDTH - OFFSET, HEIGHT, OFFSET, HEIGHT };

		// create invisible outer rectangle expanded by
		// the width needed for the anchor
		final Rectangle invisibleRectangle = gaService
				.createInvisibleRectangle(containerShape);

		gaService.setLocationAndSize(invisibleRectangle, context.getX(),
				context.getY(), WIDTH, HEIGHT);// width +
												// INVISIBLE_RECT_RIGHT
		Shape polygonShape = peCreateService.createShape(containerShape, false);
		Polygon polygon;
		polygon = gaService.createPolygon(polygonShape, xy);
		polygon.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
		gaService.setLocationAndSize(polygon, 0, 0, WIDTH, HEIGHT);

		// if addedClass has no resource we add it to the resource of
		// the diagram
		// in a real scenario the business model would have its own
		// resource
		if (addedClass.eResource() == null) {
			getDiagram().eResource().getContents().add(addedClass);
		}

		// create link and wire it
		link(containerShape, addedClass);
		link(polygonShape, addedClass);
		// link(invisibleRectangle,addedClass);
		// SHAPE WITH TEXT
		{
			// create shape for text
			final Shape shape = peCreateService.createShape(containerShape,
					false);

			// create and set text graphics algorithm
			String name = addedClass.getElementName();
			if (name == null || name.equals("")) {
				name = getName(addedClass);
				addedClass.setElementName(name);
			}
			log.debug("adding " + addedClass.getClass().getSimpleName()
					+ " with name : " + name);
			final Text text = gaService.createPlainText(shape, name);
			text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
			gaService.setLocationAndSize(text, 0, 10, WIDTH, 20);

			// create link and wire it
			link(shape, addedClass);
			// addedClass.setName(addedClass.getClass().getSimpleName());
			updatePictogramElement(shape);
			// provide information to support direct-editing directly
			// after object creation (must be activated additionally)
			final IDirectEditingInfo directEditingInfo = getFeatureProvider()
					.getDirectEditingInfo();
			// set container shape for direct editing after object creation
			directEditingInfo.setMainPictogramElement(containerShape);
			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}
		// Shape with ICON
		{
			GraphicsAlgorithmContainer ga = getGraphicsAlgorithm(containerShape);
			IGaService service = Graphiti.getGaService();
			Image img = service.createImage(ga, getIconID());
			service.setLocationAndSize(img, 0, 0, 20, 20);
		}
		// add a chopbox anchor to the shape
		peCreateService.createChopboxAnchor(containerShape);
		// peCreateService.createBoxRelativeAnchor(containerShape);

		// create an additional box relative anchor at middle-right
		final BoxRelativeAnchor boxAnchor = peCreateService
				.createBoxRelativeAnchor(containerShape);
		boxAnchor.setRelativeWidth(1.0);
		boxAnchor.setRelativeHeight(0.38); // Use golden section

		// anchor references visible rectangle instead of invisible
		// rectangle
		boxAnchor.setReferencedGraphicsAlgorithm(invisibleRectangle);

		// assign a graphics algorithm for the box relative anchor
		final Ellipse ellipse = gaService.createEllipse(boxAnchor);
		// anchor is located on the right border of the visible rectangle
		// and touches the border of the invisible rectangle
		// final int w = INVISIBLE_RECT_RIGHT;
		gaService.setLocationAndSize(ellipse, 50, 25, 0, 0);
		ellipse.setStyle(StyleUtil.getStyleForEClass(getDiagram()));

		// call the layout feature
		layoutPictogramElement(containerShape);

		return containerShape;
	}

}