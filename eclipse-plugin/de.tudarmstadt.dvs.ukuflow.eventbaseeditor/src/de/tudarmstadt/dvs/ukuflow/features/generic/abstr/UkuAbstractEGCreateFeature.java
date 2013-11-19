package de.tudarmstadt.dvs.ukuflow.features.generic.abstr;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;

public abstract class UkuAbstractEGCreateFeature extends AbstractCreateFeature{
	
	public UkuAbstractEGCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}
	public String getCreateImageId(){
		return EventImageProvider.GEARS_ICON;
	}
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public abstract EventBaseOperator getCreatingObject();
	
	public Object[] create(ICreateContext context) {
		EventBaseOperator newClass = getCreatingObject();
		
		getDiagram().eResource().getContents().add(newClass);
		// do the add
		addGraphicalRepresentation(context, newClass);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		// return newly created business object(s)
		return new Object[] { newClass };
	}
}
