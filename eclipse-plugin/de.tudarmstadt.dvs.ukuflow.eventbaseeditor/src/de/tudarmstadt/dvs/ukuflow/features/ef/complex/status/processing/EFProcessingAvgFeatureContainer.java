package de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
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
import de.tudarmstadt.dvs.ukuflow.eventbase.core.StyleUtil;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.EFProcessingFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFAddShapeFeature;

public class EFProcessingAvgFeatureContainer extends
		EFProcessingFeatureContainer {

	public boolean canApplyTo(Object o) {
		return (o instanceof EFProcessingAvg);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EFProcessingAvgCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EFProcessingAvgAddFeature(fp);
	}

	class EFProcessingAvgCreateFeature extends AbstractCreateFeature {

		public EFProcessingAvgCreateFeature(IFeatureProvider fp) {
			super(fp, "Avg", "Create composition 'average' event filter");
		}

		@Override
		public String getCreateImageId() {
			return EventImageProvider.FUNNEL_ICON;
		}

		public EFProcessingAvgCreateFeature(IFeatureProvider fp, String name,
				String description) {
			super(fp, name, description);
		}

		public boolean canCreate(ICreateContext context) {
			return context.getTargetContainer() instanceof Diagram;
		}

		public Object[] create(ICreateContext context) {
			EFProcessingAvg newClass = EventbaseFactory.eINSTANCE
					.createEFProcessingAvg();
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

	public class EFProcessingAvgAddFeature extends UkuAbstractEFAddShapeFeature {

		public EFProcessingAvgAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EFProcessingAvg)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}
	}
}
