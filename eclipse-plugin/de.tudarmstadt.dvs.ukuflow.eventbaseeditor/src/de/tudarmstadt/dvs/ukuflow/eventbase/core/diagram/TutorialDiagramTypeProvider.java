package de.tudarmstadt.dvs.ukuflow.eventbase.core.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class TutorialDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public TutorialDiagramTypeProvider() {
		super();
		setFeatureProvider(new UkuFlowFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new ukuFlowToolBehaviorProvider(
					this) };
		}
		return toolBehaviorProviders;
	}
}
