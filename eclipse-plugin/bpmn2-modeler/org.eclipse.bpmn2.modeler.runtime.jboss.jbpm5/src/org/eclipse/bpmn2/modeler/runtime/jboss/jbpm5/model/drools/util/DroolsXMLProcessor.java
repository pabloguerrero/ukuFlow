/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.util;

import java.util.Map;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DroolsXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DroolsXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		DroolsPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the DroolsResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new DroolsResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new DroolsResourceFactoryImpl());
		}
		return registrations;
	}

} //DroolsXMLProcessor
