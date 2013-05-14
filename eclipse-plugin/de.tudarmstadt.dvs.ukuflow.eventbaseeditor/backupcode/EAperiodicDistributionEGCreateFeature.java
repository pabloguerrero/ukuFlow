package de.tudarmstadt.dvs.ukuflow.features.eg.distribution;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

//import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;


public class EAperiodicDistributionEGCreateFeature extends AbstractCreateFeature{

	public EAperiodicDistributionEGCreateFeature(IFeatureProvider fp){
		super(fp,"Distribution EG","Create an aperiodic distribution event generator");
	}
	public EAperiodicDistributionEGCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
		// TODO Auto-generated constructor stub
	}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		eventbase.EDistributionEG newClass = eventbase.EventbaseFactory.eINSTANCE.createEDistributionEG();

		// do the add
		addGraphicalRepresentation(context, newClass);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		// return newly created business object(s)
		return new Object[] { newClass };
	}

}
