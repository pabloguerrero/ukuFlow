/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Base Operator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getElementName <em>Element Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEventBaseOperator()
 * @model abstract="true"
 * @generated
 */
public interface EventBaseOperator extends EObject {
	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow}.
	 * It is bidirectional and its opposite is '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEventBaseOperator_Incoming()
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getTarget
	 * @model opposite="target" resolveProxies="false" ordered="false"
	 * @generated
	 */
	EList<ESequenceFlow> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow}.
	 * It is bidirectional and its opposite is '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEventBaseOperator_Outgoing()
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getSource
	 * @model opposite="source" resolveProxies="false" ordered="false"
	 * @generated
	 */
	EList<ESequenceFlow> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Name</em>' attribute.
	 * @see #setElementName(String)
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEventBaseOperator_ElementName()
	 * @model required="true"
	 * @generated
	 */
	String getElementName();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getElementName <em>Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Name</em>' attribute.
	 * @see #getElementName()
	 * @generated
	 */
	void setElementName(String value);

} // EventBaseOperator
