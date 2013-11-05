package de.tudarmstadt.dvs.ukuflow.features.eg;

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
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.FeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericLayoutFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericMoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericRemoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericResizeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;

public abstract class EGFeatureContainer implements FeatureContainer {

	public Object getApplyObject(IContext context) {
		if (context instanceof IAddContext) {
			return ((IAddContext) context).getNewObject();
		}
		return null;
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
		return new DefaultDeleteFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return null;
	}

	
	abstract public boolean canApplyTo(Object o);

	
	abstract public ICreateFeature getCreateFeature(IFeatureProvider fp);

	
	abstract public IAddFeature getAddFeature(IFeatureProvider fp);

	
	abstract public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb);
}
