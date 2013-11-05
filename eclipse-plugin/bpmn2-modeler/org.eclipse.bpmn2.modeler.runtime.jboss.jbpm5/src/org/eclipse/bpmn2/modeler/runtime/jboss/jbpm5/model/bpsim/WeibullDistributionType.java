/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Weibull Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getScale <em>Scale</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getShape <em>Shape</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getWeibullDistributionType()
 * @model extendedMetaData="name='WeibullDistribution_._type' kind='empty'"
 * @generated
 */
public interface WeibullDistributionType extends DistributionParameter {
	/**
	 * Returns the value of the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale</em>' attribute.
	 * @see #isSetScale()
	 * @see #unsetScale()
	 * @see #setScale(double)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getWeibullDistributionType_Scale()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='scale'"
	 * @generated
	 */
	double getScale();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale</em>' attribute.
	 * @see #isSetScale()
	 * @see #unsetScale()
	 * @see #getScale()
	 * @generated
	 */
	void setScale(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScale()
	 * @see #getScale()
	 * @see #setScale(double)
	 * @generated
	 */
	void unsetScale();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getScale <em>Scale</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scale</em>' attribute is set.
	 * @see #unsetScale()
	 * @see #getScale()
	 * @see #setScale(double)
	 * @generated
	 */
	boolean isSetScale();

	/**
	 * Returns the value of the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape</em>' attribute.
	 * @see #isSetShape()
	 * @see #unsetShape()
	 * @see #setShape(double)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage#getWeibullDistributionType_Shape()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='shape'"
	 * @generated
	 */
	double getShape();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see #isSetShape()
	 * @see #unsetShape()
	 * @see #getShape()
	 * @generated
	 */
	void setShape(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShape()
	 * @see #getShape()
	 * @see #setShape(double)
	 * @generated
	 */
	void unsetShape();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType#getShape <em>Shape</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Shape</em>' attribute is set.
	 * @see #unsetShape()
	 * @see #getShape()
	 * @see #setShape(double)
	 * @generated
	 */
	boolean isSetShape();

} // WeibullDistributionType
