package de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.temporal;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.EFTemporalFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractECAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFCreateFeature;

public class EFTemporalSequenceFeatureContainer extends
		EFTemporalFeatureContainer {

	public boolean canApplyTo(Object o) {
		return o != null && o instanceof EFTemporalSequence;
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EFTemporalSequenceCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EFTemporalSequenceAddFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return null;
	}

	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		// TODO Auto-generated method stub
		return null;
	}

	public class EFTemporalSequenceCreateFeature extends
			UkuAbstractEFCreateFeature {
		public EFTemporalSequenceCreateFeature(IFeatureProvider fp) {
			super(fp, "Sequence", "Create a temporal sequence event composer");
		}

		public EventBaseOperator getCreatingObject() {
			return EventbaseFactory.eINSTANCE.createEFTemporalSequence();
		}

	}

	public class EFTemporalSequenceAddFeature extends
			UkuAbstractECAddShapeFeature {

		public EFTemporalSequenceAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EFProcessingMin)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}

	}
}
