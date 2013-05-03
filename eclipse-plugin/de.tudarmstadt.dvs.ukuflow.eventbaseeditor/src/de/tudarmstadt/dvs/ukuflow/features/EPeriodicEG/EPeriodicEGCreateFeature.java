package de.tudarmstadt.dvs.ukuflow.features.EPeriodicEG;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator;
import eventbase.EventbaseFactory;
import eventbase.EventbasePackage;

public class EPeriodicEGCreateFeature extends AbstractCreateFeature{
	
	public EPeriodicEGCreateFeature(IFeatureProvider fp){
		super(fp,"EPeriodic EG","Create a periodic event generator");		
	}
	public EPeriodicEGCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
		// TODO Auto-generated constructor stub
	}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		//EventbasePackage.eINSTANCE.getEPeriodicEG();
		eventbase.EPeriodicEG newClass = EventbaseFactory.eINSTANCE.createEPeriodicEG();
		//EPeriodicEG newClass = new EPeriodicEG();

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
