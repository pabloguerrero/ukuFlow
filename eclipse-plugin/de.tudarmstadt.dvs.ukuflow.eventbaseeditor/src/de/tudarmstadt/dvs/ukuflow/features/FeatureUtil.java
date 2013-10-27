package de.tudarmstadt.dvs.ukuflow.features;

import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

public class FeatureUtil {
	/**
	 * fetching the properties from <b>result</b> and put into <b>bo</b> <br />
	 * return true if at least a property has been changed.
	 * 
	 * @param bo
	 * @param result
	 * @return
	 */
	public static boolean fetchAnswer(EventBaseOperator bo,
			Map<Integer, RequestContainer> result) {
		// ///
		boolean changed = false;
		if (bo instanceof EventGenerator) {
			EventGenerator eg = (EventGenerator) bo;
			String scope = eg.getScope();
			String newScope = result
					.get(EventbasePackage.EVENT_GENERATOR__SCOPE).result;
			if (newScope.equalsIgnoreCase("local")
					|| newScope.equalsIgnoreCase("world")) {
				newScope = newScope.toUpperCase();
			}
			if (!newScope.equals(scope))
				changed = true;
			eg.setScope(newScope);
			String type = eg.getSensorType();
			String newType = result
					.get(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE).result;
			if (!newType.equals(type))
				changed = true;
			eg.setSensorType(newType);
			// repetation
			if (bo instanceof EGRecurring) {
				int newRepetition = Integer.parseInt(result
						.get(EventbasePackage.EG_RECURRING__REPETITION).result);
				if (((EGRecurring) bo).getRepetition() != newRepetition) {
					changed = true;
				}
				((EGRecurring) bo).setRepetition(newRepetition);
			}
		}
		return changed;
	}

	public static Map<Integer, RequestContainer> createQuestions(
			EventBaseOperator element) {
		Map<Integer, RequestContainer> properties = new HashMap<Integer, RequestContainer>();
		// general properties for recurring event
		if (element instanceof EGRecurring) {
			RequestContainer repetitions = new RequestContainer(
					new RequestContainer.IntegerValidator(0, 255),
					((EGRecurring) element).getRepetition(),
					"Number of repetitions (0-255, 0 means infinite)");
			properties.put(EventbasePackage.EG_RECURRING__REPETITION,
					repetitions);
		}

		// general properties for Event Generators
		if (element instanceof EventGenerator) {
			EventGenerator eg = ((EventGenerator) element);
			RequestContainer sensorType = new RequestContainer(null,
					UkuConstants.SensorTypeConstants.sensor_types,
					"Sensor type", eg.getSensorType());
			RequestContainer scope = new RequestContainer(null, eg.getScope(),
					"Scope identifier ('WORLD': for all nodes, 'LOCAL': only at base node):");

			properties.put(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE,
					sensorType);
			properties.put(EventbasePackage.EVENT_GENERATOR__SCOPE, scope);
		}
		return properties;
	}
}
