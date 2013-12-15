package de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.ef.EFCompositeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.EFChangeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractECAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFCreateFeature;

public class EFChangeIncreaseFeatureContainer extends EFChangeFeatureContainer{

	public boolean canApplyTo(Object o) {
		return o != null && o instanceof EFChangeIncrease;
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp){
		return new EFChangeIncreaseCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp){
		return new EFChangeIncreaseAddFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp){
		return null; //TODO ???
	}
	public class EFChangeIncreaseCreateFeature extends UkuAbstractEFCreateFeature {

		public EFChangeIncreaseCreateFeature(IFeatureProvider fp) {
			super(fp, "Increase", "Create an increase event composer");
		}
		@Override
		public EventBaseOperator getCreatingObject() {
			EFChangeIncrease ef= EventbaseFactory.eINSTANCE.createEFChangeIncrease();
			ef.setChangeThreshold(15);
			ef.setWindowSize("5:00");
			return ef;
		}
		
	}
	class EFChangeIncreaseAddFeature extends UkuAbstractECAddShapeFeature{

		public EFChangeIncreaseAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EFChangeIncrease)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}
		
	}
}
