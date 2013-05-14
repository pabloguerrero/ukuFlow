/**
 */
package eventbase;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ESequence Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.ESequenceFlow#getSource <em>Source</em>}</li>
 *   <li>{@link eventbase.ESequenceFlow#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getESequenceFlow()
 * @model
 * @generated
 */
public interface ESequenceFlow extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link eventbase.EventBaseOperator#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EventBaseOperator)
	 * @see eventbase.EventbasePackage#getESequenceFlow_Source()
	 * @see eventbase.EventBaseOperator#getOutgoing
	 * @model opposite="outgoing" resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	EventBaseOperator getSource();

	/**
	 * Sets the value of the '{@link eventbase.ESequenceFlow#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EventBaseOperator value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link eventbase.EventBaseOperator#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EventBaseOperator)
	 * @see eventbase.EventbasePackage#getESequenceFlow_Target()
	 * @see eventbase.EventBaseOperator#getIncoming
	 * @model opposite="incoming" resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	EventBaseOperator getTarget();

	/**
	 * Sets the value of the '{@link eventbase.ESequenceFlow#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EventBaseOperator value);

} // ESequenceFlow
