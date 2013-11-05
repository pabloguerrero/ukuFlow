package de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring;

import java.util.Map;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.FeatureUtil;
import de.tudarmstadt.dvs.ukuflow.features.eg.EGFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.*;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEGAddShapeFeature;

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
		@Override
		public String getCreateImageId() {
			return EventImageProvider.GEARS_ICON;
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

	public class EGImmediateAddFeature extends UkuAbstractEGAddShapeFeature {

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

		
	}
	public class EGImmediateDoubleClickFeature extends GenericEditPropertiesFeature{

		public EGImmediateDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}
		public void execute(ICustomContext context){
			EventBaseOperator bo = (EventBaseOperator)getBusinessObj(context);
			if(bo == null)
				return;
			Map<Integer,RequestContainer> properties = FeatureUtil.createQuestions((EventBaseOperator)bo);
			Map<Integer,RequestContainer> results = asking(bo,properties);
			hasDoneChanges = FeatureUtil.fetchAnswer(bo, results);
		}
	}
	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		return new EGImmediateDoubleClickFeature(fb);
	}
}