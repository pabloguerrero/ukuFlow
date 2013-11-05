/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cost Parameters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters#getFixedCost <em>Fixed Cost</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters#getUnitCost <em>Unit Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getCostParameters()
 * @model extendedMetaData="name='CostParameters' kind='elementOnly'"
 * @generated
 */
public interface CostParameters extends EObject {
	/**
	 * Returns the value of the '<em><b>Fixed Cost</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Cost</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Cost</em>' containment reference.
	 * @see #setFixedCost(Parameter)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getCostParameters_FixedCost()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='FixedCost' namespace='##targetNamespace'"
	 * @generated
	 */
	Parameter getFixedCost();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters#getFixedCost <em>Fixed Cost</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Cost</em>' containment reference.
	 * @see #getFixedCost()
	 * @generated
	 */
	void setFixedCost(Parameter value);

	/**
	 * Returns the value of the '<em><b>Unit Cost</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit Cost</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit Cost</em>' containment reference.
	 * @see #setUnitCost(Parameter)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getCostParameters_UnitCost()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='UnitCost' namespace='##targetNamespace'"
	 * @generated
	 */
	Parameter getUnitCost();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters#getUnitCost <em>Unit Cost</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit Cost</em>' containment reference.
	 * @see #getUnitCost()
	 * @generated
	 */
	void setUnitCost(Parameter value);

} // CostParameters
