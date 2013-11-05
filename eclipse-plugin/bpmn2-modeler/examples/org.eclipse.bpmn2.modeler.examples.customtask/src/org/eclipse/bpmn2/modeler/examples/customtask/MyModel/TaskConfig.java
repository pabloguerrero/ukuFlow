/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTaskConfig()
 * @model
 * @generated
 */
public interface TaskConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTaskConfig_Parameters()
	 * @model containment="true"
	 *        extendedMetaData="name='parameter' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Parameter> getParameters();

} // TaskConfig
