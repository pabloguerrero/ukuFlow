/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType#getMetaentry <em>Metaentry</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getMetadataType()
 * @model extendedMetaData="name='metadata_._type' kind='elementOnly'"
 * @generated
 */
public interface MetadataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Metaentry</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metaentry</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metaentry</em>' containment reference list.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getMetadataType_Metaentry()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='metaentry' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<MetaentryType> getMetaentry();

} // MetadataType
