package de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
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
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.StyleUtil;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.diagram.TutorialFeatureProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.eg.EGFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.*;

public class EGImmediateFeatureContainer extends EGFeatureContainer {

	@Override
	public Object getApplyObject(IContext context) {
		return super.getApplyObject(context);
	}

	public boolean canApplyTo(Object o) {
		return (o instanceof EGImmediate);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EGImmediateCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EGImmediateAddFeature(fp);
	}

	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {

		return new GenericUpdateFeature(fp);
	}

	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new GenericDirectEditFeature(fp);
	}

	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new GenericLayoutFeature(fp);
	}

	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return new GenericRemoveFeature(fp);
	}

	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new GenericMoveFeature(fp);
	}

	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new GenericResizeFeature(fp);
	}

	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return new DefaultDeleteFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return null;
	}

	class EGImmediateCreateFeature extends AbstractCreateFeature {

		public EGImmediateCreateFeature(IFeatureProvider fp) {
			super(fp, "Immediate", "Create an immediate event generator");
		}

		public EGImmediateCreateFeature(IFeatureProvider fp, String name,
				String description) {
			super(fp, name, description);
		}

		public boolean canCreate(ICreateContext context) {
			return context.getTargetContainer() instanceof Diagram;
		}

		public Object[] create(ICreateContext context) {
			EGImmediate newClass = EventbaseFactory.eINSTANCE
					.createEGImmediate();
			getDiagram().eResource().getContents().add(newClass);

			// Use the following instead of the above line to store the model
			// data in a seperate file parallel to the diagram file
			// try {
			// try {
			// TutorialUtil.saveToModelFile(newClass, getDiagram());
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// } catch (CoreException e) {
			// e.printStackTrace();
			// }

			// do the add
			addGraphicalRepresentation(context, newClass);

			// activate direct editing after object creation
			getFeatureProvider().getDirectEditingInfo().setActive(true);
			// return newly created business object(s)
			return new Object[] { newClass };
		}

	}

	public class EGImmediateAddFeature extends AbstractAddShapeFeature {

		public static final int INVISIBLE_RECT_RIGHT = 6;

		public EGImmediateAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EGImmediate)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}

		public PictogramElement add(IAddContext context) {
			final EGImmediate addedClass = (EGImmediate ) context.getNewObject();
			final Diagram targetDiagram = (Diagram) context
					.getTargetContainer();
			// CONTAINER SHAPE WITH ROUNDED RECTANGLE
			final IPeCreateService peCreateService = Graphiti
					.getPeCreateService();
			final ContainerShape containerShape = peCreateService
					.createContainerShape(targetDiagram, true);

			// check whether the context has a size (e.g. from a create feature)
			// otherwise define a default size for the shape
			final int width = context.getWidth() <= 0 ? 100 : context
					.getWidth();
			final int height = context.getHeight() <= 0 ? 50 : context
					.getHeight();

			final IGaService gaService = Graphiti.getGaService();
			//int xy[] = new int[] { 0, 0, 90, 0, 100, 25, 90, 50, 0, 50, };
			int xy[] = new int[] { 0, 0, EG_WIDTH-EG_OFFSET, 0, EG_WIDTH, EG_HEIGHT/2, EG_WIDTH-EG_OFFSET, EG_HEIGHT, 0, EG_HEIGHT, };
			Polygon polygon;
			{
				// create invisible outer rectangle expanded by
				// the width needed for the anchor
				final Rectangle invisibleRectangle = gaService
						.createInvisibleRectangle(containerShape);
				gaService.setLocationAndSize(invisibleRectangle,
						context.getX(), context.getY(), width
								+ INVISIBLE_RECT_RIGHT, height);

				polygon = gaService.createPolygon(invisibleRectangle, xy);
				polygon.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
				gaService.setLocationAndSize(polygon, 0, 0, width, height);

				// if addedClass has no resource we add it to the resource of
				// the diagram
				// in a real scenario the business model would have its own
				// resource
				if (addedClass.eResource() == null) {
					getDiagram().eResource().getContents().add(addedClass);
				}

				// create link and wire it
				link(containerShape, addedClass);
			}

			// SHAPE WITH TEXT
			{
				// create shape for text
				final Shape shape = peCreateService.createShape(containerShape,
						false);

				// create and set text graphics algorithm
				String name = addedClass.getClass().getSimpleName();
				final Text text = gaService.createPlainText(shape,
						name.substring(0, name.length() - 4));
				text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
				gaService.setLocationAndSize(text, 0, 10, width, 20);

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
			boxAnchor.setReferencedGraphicsAlgorithm(polygon);

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
}
