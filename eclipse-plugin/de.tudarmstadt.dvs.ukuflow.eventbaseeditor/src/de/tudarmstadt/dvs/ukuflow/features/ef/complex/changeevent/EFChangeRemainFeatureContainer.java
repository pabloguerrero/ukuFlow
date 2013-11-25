package de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.EFChangeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractECAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFCreateFeature;

public class EFChangeRemainFeatureContainer extends EFChangeFeatureContainer {

	public boolean canApplyTo(Object o) {
		return o != null && o instanceof EFChangeIncrease;
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EFChangeRemainCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EFChangeRemainAddFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return null; // TODO ???
	}

	public class EFChangeRemainCreateFeature extends UkuAbstractEFCreateFeature {

		public EFChangeRemainCreateFeature(IFeatureProvider fp) {
			super(fp, "Remain", "Create a remain event composer");
		}

		@Override
		public EventBaseOperator getCreatingObject() {
			return EventbaseFactory.eINSTANCE.createEFChangeRemain();
		}

	}

	class EFChangeRemainAddFeature extends UkuAbstractECAddShapeFeature {

		public EFChangeRemainAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EFChangeRemain)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}

	}
}
