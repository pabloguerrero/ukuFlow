/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel.util;

import org.eclipse.bpmn2.impl.ResourceImpl;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerResourceFactoryImpl;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerResourceImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.util.MyModelResourceImpl
 * @generated NOT
 */
public class MyModelResourceFactoryImpl extends Bpmn2ModelerResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyModelResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri) {
		Bpmn2ModelerResourceImpl resource = new Bpmn2ModelerResourceImpl(uri);
		return resource;
	}

} //MyModelResourceFactoryImpl
