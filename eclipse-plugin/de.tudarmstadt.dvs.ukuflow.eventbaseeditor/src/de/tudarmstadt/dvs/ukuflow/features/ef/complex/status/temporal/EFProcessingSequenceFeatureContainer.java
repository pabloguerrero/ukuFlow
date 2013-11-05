package de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.temporal;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.EFTemporalFeatureContainer;

public class EFProcessingSequenceFeatureContainer extends EFTemporalFeatureContainer{

	@Override
	public boolean canApplyTo(Object o) {
		return o != null && o instanceof EFTemporal;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		// TODO Auto-generated method stub
		return null;
	}

}
