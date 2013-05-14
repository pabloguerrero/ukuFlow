/**
 */
package eventbase;

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
 *   <li>{@link eventbase.EventBaseOperator#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link eventbase.EventBaseOperator#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEventBaseOperator()
 * @model abstract="true"
 * @generated
 */
public interface EventBaseOperator extends EObject {
	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link eventbase.ESequenceFlow}.
	 * It is bidirectional and its opposite is '{@link eventbase.ESequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see eventbase.EventbasePackage#getEventBaseOperator_Incoming()
	 * @see eventbase.ESequenceFlow#getTarget
	 * @model opposite="target" resolveProxies="false" ordered="false"
	 * @generated
	 */
	EList<ESequenceFlow> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link eventbase.ESequenceFlow}.
	 * It is bidirectional and its opposite is '{@link eventbase.ESequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see eventbase.EventbasePackage#getEventBaseOperator_Outgoing()
	 * @see eventbase.ESequenceFlow#getSource
	 * @model opposite="source" resolveProxies="false" ordered="false"
	 * @generated
	 */
	EList<ESequenceFlow> getOutgoing();

} // EventBaseOperator
