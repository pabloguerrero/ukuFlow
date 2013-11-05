/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Numeric Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getCurrencyUnit <em>Currency Unit</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getTimeUnit <em>Time Unit</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getNumericParameterType()
 * @model extendedMetaData="name='NumericParameter_._type' kind='empty'"
 * @generated
 */
public interface NumericParameterType extends ConstantParameter {
	/**
	 * Returns the value of the '<em><b>Currency Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Currency Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Currency Unit</em>' attribute.
	 * @see #setCurrencyUnit(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getNumericParameterType_CurrencyUnit()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='currencyUnit'"
	 * @generated
	 */
	String getCurrencyUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getCurrencyUnit <em>Currency Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Currency Unit</em>' attribute.
	 * @see #getCurrencyUnit()
	 * @generated
	 */
	void setCurrencyUnit(String value);

	/**
	 * Returns the value of the '<em><b>Time Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Unit</em>' attribute.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeUnit
	 * @see #isSetTimeUnit()
	 * @see #unsetTimeUnit()
	 * @see #setTimeUnit(TimeUnit)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getNumericParameterType_TimeUnit()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='timeUnit'"
	 * @generated
	 */
	TimeUnit getTimeUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getTimeUnit <em>Time Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Unit</em>' attribute.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeUnit
	 * @see #isSetTimeUnit()
	 * @see #unsetTimeUnit()
	 * @see #getTimeUnit()
	 * @generated
	 */
	void setTimeUnit(TimeUnit value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getTimeUnit <em>Time Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimeUnit()
	 * @see #getTimeUnit()
	 * @see #setTimeUnit(TimeUnit)
	 * @generated
	 */
	void unsetTimeUnit();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getTimeUnit <em>Time Unit</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Time Unit</em>' attribute is set.
	 * @see #unsetTimeUnit()
	 * @see #getTimeUnit()
	 * @see #setTimeUnit(TimeUnit)
	 * @generated
	 */
	boolean isSetTimeUnit();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #isSetValue()
	 * @see #unsetValue()
	 * @see #setValue(long)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getNumericParameterType_Value()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
	long getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #isSetValue()
	 * @see #unsetValue()
	 * @see #getValue()
	 * @generated
	 */
	void setValue(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValue()
	 * @see #getValue()
	 * @see #setValue(long)
	 * @generated
	 */
	void unsetValue();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType#getValue <em>Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value</em>' attribute is set.
	 * @see #unsetValue()
	 * @see #getValue()
	 * @see #setValue(long)
	 * @generated
	 */
	boolean isSetValue();

} // NumericParameterType
