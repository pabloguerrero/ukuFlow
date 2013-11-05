/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getInstance <em>Instance</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getResult <em>Result</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getValidFor <em>Valid For</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getParameterValue()
 * @model extendedMetaData="name='ParameterValue' kind='empty'"
 * @generated
 */
public interface ParameterValue extends EObject {
	/**
	 * Returns the value of the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance</em>' attribute.
	 * @see #setInstance(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getParameterValue_Instance()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='instance'"
	 * @generated
	 */
	String getInstance();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getInstance <em>Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance</em>' attribute.
	 * @see #getInstance()
	 * @generated
	 */
	void setInstance(String value);

	/**
	 * Returns the value of the '<em><b>Result</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResultType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' attribute.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResultType
	 * @see #isSetResult()
	 * @see #unsetResult()
	 * @see #setResult(ResultType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getParameterValue_Result()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='result'"
	 * @generated
	 */
	ResultType getResult();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getResult <em>Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result</em>' attribute.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResultType
	 * @see #isSetResult()
	 * @see #unsetResult()
	 * @see #getResult()
	 * @generated
	 */
	void setResult(ResultType value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getResult <em>Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResult()
	 * @see #getResult()
	 * @see #setResult(ResultType)
	 * @generated
	 */
	void unsetResult();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getResult <em>Result</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Result</em>' attribute is set.
	 * @see #unsetResult()
	 * @see #getResult()
	 * @see #setResult(ResultType)
	 * @generated
	 */
	boolean isSetResult();

	/**
	 * Returns the value of the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid For</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid For</em>' attribute.
	 * @see #setValidFor(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getParameterValue_ValidFor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IDREF"
	 *        extendedMetaData="kind='attribute' name='validFor'"
	 * @generated
	 */
	String getValidFor();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue#getValidFor <em>Valid For</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid For</em>' attribute.
	 * @see #getValidFor()
	 * @generated
	 */
	void setValidFor(String value);

} // ParameterValue
