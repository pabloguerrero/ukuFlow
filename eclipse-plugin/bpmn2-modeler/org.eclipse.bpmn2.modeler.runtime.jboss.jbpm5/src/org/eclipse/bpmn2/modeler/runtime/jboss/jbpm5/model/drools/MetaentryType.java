/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metaentry Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getMetaentryType()
 * @model extendedMetaData="name='metaentry_._type' kind='elementOnly'"
 * @generated
 */
public interface MetaentryType extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getMetaentryType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getMetaentryType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='value' namespace='##targetNamespace'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // MetaentryType
