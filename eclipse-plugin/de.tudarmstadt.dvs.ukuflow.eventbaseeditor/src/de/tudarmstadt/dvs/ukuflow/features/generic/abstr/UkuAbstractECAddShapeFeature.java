package de.tudarmstadt.dvs.ukuflow.features.generic.abstr;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;

public abstract class UkuAbstractECAddShapeFeature extends UkuAbstractEFAddShapeFeature {

	public UkuAbstractECAddShapeFeature(IFeatureProvider fp) {
		super(fp);
	}
	public String getIconID() {
		return EventImageProvider.MERGING_ICON;
	}

}
