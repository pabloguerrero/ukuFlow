/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel.util;

import java.util.Map;

import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MyModelXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyModelXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		MyModelPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the MyModelResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new MyModelResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new MyModelResourceFactoryImpl());
		}
		return registrations;
	}

} //MyModelXMLProcessor
