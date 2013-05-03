package de.tudarmstadt.dvs.ukuflow.features.EAperiodicPatternedEG;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

//import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;

public class EAperiodicPatternedEGCreateFeature extends AbstractCreateFeature{

	public EAperiodicPatternedEGCreateFeature(IFeatureProvider fp){
		super(fp,"Patterned EG","Create an aperiodic patterned event generator");
	}
	public EAperiodicPatternedEGCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
		// TODO Auto-generated constructor stub
	}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		eventbase.EPatternedEG newClass = eventbase.EventbaseFactory.eINSTANCE.createEPatternedEG();
		//new EAperiodicPatternedEG();

		// do the add
		addGraphicalRepresentation(context, newClass);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		// return newly created business object(s)
		return new Object[] { newClass };
	}
	
}
