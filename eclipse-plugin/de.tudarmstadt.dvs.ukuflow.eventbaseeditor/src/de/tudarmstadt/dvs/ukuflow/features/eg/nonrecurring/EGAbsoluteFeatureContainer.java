package de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring;

import java.util.Map;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.TimeUtil;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.FeatureUtil;
import de.tudarmstadt.dvs.ukuflow.features.eg.EGFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEGAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class EGAbsoluteFeatureContainer extends EGFeatureContainer {

	public boolean canApplyTo(Object o) {
		return (o instanceof EGAbsolute);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EGAbsoluteCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EGAbsoluteAddFeature(fp);
	}

	class EGAbsoluteCreateFeature extends UkuAbstractEGCreateFeature {

		public EGAbsoluteCreateFeature(IFeatureProvider fp) {
			super(fp, "Absolute", "Create an absolute event generator");
		}

		public EventBaseOperator getCreatingObject() {
			return EventbaseFactory.eINSTANCE.createEGAbsolute();
		}

		
	}

	public class EGAbsoluteAddFeature extends UkuAbstractEGAddShapeFeature {

		public static final int INVISIBLE_RECT_RIGHT = 6;

		public EGAbsoluteAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EGAbsolute)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}

	}

	public class EGAbsoluteDoubleClickFeature extends
			GenericEditPropertiesFeature {
		BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());

		public EGAbsoluteDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}

		public void execute(ICustomContext context) {
			EventBaseOperator bo = (EventBaseOperator) getBusinessObj(context);
			EGAbsolute eClass = (EGAbsolute) bo;
			Map<Integer, RequestContainer> properties = FeatureUtil
					.createQuestions(bo);

			String currentTime = eClass.getAbsoluteTime();
			if (currentTime == null || currentTime.equals("")) {

				currentTime = TimeUtil.getCurrentTime();
				eClass.setAbsoluteTime(currentTime);
				log.debug("absolute time is null or \"\"-> replace with default : "
						+ currentTime);
			}
			properties.put(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME,
					new RequestContainer(
							new RequestContainer.AbsoluteTimeValidator(),
							currentTime, "Absolute time ("
									+ TimeUtil.FULL_PATTERN + ")"));

			Map<Integer, RequestContainer> result = asking(bo, properties);
			if (result == null)
				return;
			hasDoneChanges = FeatureUtil.fetchAnswer(bo, result);

			String newTime = result
					.get(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME).result;
			if (!newTime.equals(currentTime)) {
				this.hasDoneChanges = true;
				eClass.setAbsoluteTime(newTime);
			}
		}
	}

	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		return new EGAbsoluteDoubleClickFeature(fb);
	}
}