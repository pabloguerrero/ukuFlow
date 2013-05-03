package de.tudarmstadt.dvs.ukuflow.features.ESimpleEF;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

//import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;

public class ESimpleEFCreateFeature extends AbstractCreateFeature{

	public ESimpleEFCreateFeature(IFeatureProvider fp){
		super(fp,"Simple EF","Create a simple event filter");
	}
	public ESimpleEFCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
		// TODO Auto-generated constructor stub
	}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		eventbase.ESimpleEF newClass = eventbase.EventbaseFactory.eINSTANCE.createESimpleEF();

		//		Use the following instead of the above line to store the model
		//		data in a seperate file parallel to the diagram file
		//		try {
		//			try {
		//				TutorialUtil.saveToModelFile(newClass, getDiagram());
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//			}
		//		} catch (CoreException e) {
		//			e.printStackTrace();
		//		}

		// do the add
		addGraphicalRepresentation(context, newClass);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		// return newly created business object(s)
		return new Object[] { newClass };
	}

}
