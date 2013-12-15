package de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.EFChangeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractECAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractEFCreateFeature;

public class EFChangeDecreaseFeatureContainer extends EFChangeFeatureContainer{

	public boolean canApplyTo(Object o) {
		return o != null && o instanceof EFChangeIncrease;
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp){
		return new EFChangeDecreaseCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp){
		return new EFChangeDecreaseAddFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp){
		return null; //TODO ???
	}
	
	public class EFChangeDecreaseCreateFeature extends UkuAbstractEFCreateFeature {

		public EFChangeDecreaseCreateFeature (IFeatureProvider fp) {
			super(fp, "Decrease", "Create a decrease event composer");
		}
		@Override
		public EventBaseOperator getCreatingObject() {
			EFChangeDecrease ef = EventbaseFactory.eINSTANCE.createEFChangeDecrease();
			ef.setChangeThreshold(15);
			ef.setWindowSize("5:00");
			return ef;
		}
	}
	
	class EFChangeDecreaseAddFeature extends UkuAbstractECAddShapeFeature{

		public EFChangeDecreaseAddFeature(IFeatureProvider fp) {
			super(fp);
		}
		public String getIconID() {
			return EventImageProvider.MERGING_ICON;
		}
		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EFChangeDecrease)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}
		
	}
}
