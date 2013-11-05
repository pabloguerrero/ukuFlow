package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class JbpmGatewayPropertiesAdapter extends
		ExtendedPropertiesAdapter<Gateway> {

	public JbpmGatewayPropertiesAdapter(AdapterFactory adapterFactory,
			Gateway object) {
		super(adapterFactory, object);

    	EStructuralFeature ref;
    	
    	ref = Bpmn2Package.eINSTANCE.getGateway_GatewayDirection();
    	setFeatureDescriptor(ref, new FeatureDescriptor<Gateway>(adapterFactory,object,ref) {

    		@Override
    		public Hashtable<String, Object> getChoiceOfValues(Object context) {
    			EObject object = adopt(context);
    			Hashtable<String, Object> choices = super.getChoiceOfValues(context);
    			choices.remove("Unspecified");
    			choices.remove("Mixed");
    			return choices;
    		}
    	});
	}
	
}
