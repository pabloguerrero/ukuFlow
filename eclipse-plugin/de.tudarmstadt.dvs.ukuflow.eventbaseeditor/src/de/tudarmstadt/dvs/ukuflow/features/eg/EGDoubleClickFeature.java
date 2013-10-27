package de.tudarmstadt.dvs.ukuflow.features.eg;

import java.util.Map;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

public class EGDoubleClickFeature extends GenericEditPropertiesFeature{

	public class EGNonRecurringDoubleClickFeature {

	}

	public EGDoubleClickFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}
	
	protected void addProperties(Map<Integer,RequestContainer> properties, EventBaseOperator ebo){
		EventGenerator eg = (EventGenerator)ebo;
		properties.put(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE,
				new RequestContainer(null,
						UkuConstants.SensorTypeConstants.sensor_types,
						"Sensor type", eg.getSensorType()));
		properties.put(EventbasePackage.EVENT_GENERATOR__SCOPE,
				new RequestContainer(null, (eg.getScope() == null ? ""
						: eg.getScope()),
						"Scope identifier ('WORLD': for all nodes & 'LOCAL': only at base node):"));
	}
}
