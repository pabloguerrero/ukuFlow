package de.tudarmstadt.dvs.ukuflow.features.eg.periodic;

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
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.TimeUtil;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.FeatureUtil;
import de.tudarmstadt.dvs.ukuflow.features.eg.EGFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericLayoutFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericMoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericRemoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericResizeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEGAddShapeFeature;

public class EGPeriodicFeatureContainer extends EGFeatureContainer {

	@Override
	public Object getApplyObject(IContext context) {
		return super.getApplyObject(context);
	}

	public boolean canApplyTo(Object o) {
		return (o instanceof EGPeriodic);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EGPeriodicCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EGPeriodicAddFeature(fp);
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

	class EGPeriodicCreateFeature extends UkuAbstractEGCreateFeature {

		public EGPeriodicCreateFeature(IFeatureProvider fp) {
			super(fp, "Periodic", "Create an periodic event generator");
		}

		@Override
		public EventBaseOperator getCreatingObject() {
			return EventbaseFactory.eINSTANCE.createEGPeriodic();
		}

	

	}

	public class EGPeriodicAddFeature extends UkuAbstractEGAddShapeFeature {

		public static final int INVISIBLE_RECT_RIGHT = 6;

		public EGPeriodicAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EGPeriodic)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}
	}

	public class EGPeriodicDoubleClickFeature extends
			GenericEditPropertiesFeature {

		public EGPeriodicDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}

		public void execute(ICustomContext context) {
			EventBaseOperator bo = (EventBaseOperator) getBusinessObj(context);
			Map<Integer, RequestContainer> properties = FeatureUtil
					.createQuestions(bo);

			EGPeriodic off = (EGPeriodic) bo;
			properties.put(
					EventbasePackage.EG_PERIODIC__TIME,
					new RequestContainer(
							new RequestContainer.DatePatternValidator(
									TimeUtil.SHORT_TIME_PATTERN), ""
									+ off.getTime(),
							"Period duration (in mm:ss format)"));

			Map<Integer, RequestContainer> result = asking(bo, properties);
			if (result == null)
				return;
			hasDoneChanges = FeatureUtil.fetchAnswer(bo, result);

			String currentTime = off.getTime();
			String newTime = result.get(EventbasePackage.EG_PERIODIC__TIME).result;
			if (newTime != currentTime) {
				this.hasDoneChanges = true;
				off.setTime(newTime);
				// updatePictogramElement(pes[0]);
			}
		}
	}

	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		
		return new EGPeriodicDoubleClickFeature(fb);
	}
}