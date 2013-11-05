package org.eclipse.bpmn2.modeler.core.merrimac.clad;

import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Provider class for the Default Properties sheet tab.
 * This simply returns a list of properties, containment ELists and references
 * to be rendered on the Default Properties tab. If the DefaultDetailComposite
 * is subclassed and the client does not specify an item provider, the default
 * behavior is to render all structural features for the business object.
 */
public abstract class AbstractPropertiesProvider {
	
	EObject businessObject;
	
	public AbstractPropertiesProvider(EObject object) {
		businessObject = object;
	}

	public abstract String[] getProperties();

	public void setProperties(String[] properties) {
	}
	
	public String getLabel(EObject object) {
		return ModelUtil.getLabel(object);
	}
	
	public String getLabel(EClass eclass) {
		return ModelUtil.getLabel(eclass);
	}
	
	public String getLabel(EObject object, EStructuralFeature feature) {
		return ModelUtil.getLabel(object, feature);
	}
}