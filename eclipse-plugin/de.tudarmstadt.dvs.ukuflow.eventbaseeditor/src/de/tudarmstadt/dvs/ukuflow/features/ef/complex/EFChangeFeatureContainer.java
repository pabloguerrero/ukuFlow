package de.tudarmstadt.dvs.ukuflow.features.ef.complex;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.ef.EFCompositeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;

public abstract class EFChangeFeatureContainer extends
		EFCompositeFeatureContainer {

	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		return new EFChangeDoubleClickFeature(fb);
	}

	public class EFChangeDoubleClickFeature extends
			GenericEditPropertiesFeature {

		public EFChangeDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}

		public void execute(ICustomContext context) {
			EventBaseOperator bo = (EventBaseOperator) getBusinessObj(context);
			EFChangeEvent event = (EFChangeEvent) bo;
			Map<Integer, RequestContainer> properties = new HashMap<Integer, RequestContainer>();
			// window size:
			String currentWindowSize = event.getWindowSize();
			properties.put(EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE,
					new RequestContainer(
							new RequestContainer.OffsetTimeValidator(),
							currentWindowSize + "", "Window Size (mm:ss)"));
			// threshold:
			int currentThreshold = event.getChangeThreshold();
			properties.put(EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD,
					new RequestContainer(
							new RequestContainer.IntegerValidator(),
							currentThreshold + "", "Threshold"));

			Map<Integer, RequestContainer> result = asking(bo, properties);
			if (result == null)
				return;

			String newWindow = result
					.get(EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE).result;
			if (!newWindow.equals(currentWindowSize)) {
				this.hasDoneChanges = true;
				event.setWindowSize(newWindow);
			}
			int newThreshold = Integer
					.parseInt(result
							.get(EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD).result);
			if (newThreshold != currentThreshold) {
				this.hasDoneChanges = true;
				event.setChangeThreshold(newThreshold);
			}
		}

	}

}
