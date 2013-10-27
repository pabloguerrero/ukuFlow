package de.tudarmstadt.dvs.ukuflow.eventbase.core.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEvaluableExpression;


public class AbstractEventBaseCreateFeature<T extends EEvaluableExpression> extends AbstractCreateFeature{

	public AbstractEventBaseCreateFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateContext context) {
		
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
