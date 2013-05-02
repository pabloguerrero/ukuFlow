/**
 */
package eventbase;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ESimple EF</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.ESimpleEF#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link eventbase.ESimpleEF#getSourceEvent <em>Source Event</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getESimpleEF()
 * @model
 * @generated
 */
public interface ESimpleEF extends EventBaseOperator {
	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' reference list.
	 * The list contents are of type {@link eventbase.ESimpleFilterConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' reference list.
	 * @see eventbase.EventbasePackage#getESimpleEF_Constraints()
	 * @model required="true"
	 * @generated
	 */
	EList<ESimpleFilterConstraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Source Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Event</em>' reference.
	 * @see #setSourceEvent(EventBaseOperator)
	 * @see eventbase.EventbasePackage#getESimpleEF_SourceEvent()
	 * @model required="true"
	 * @generated
	 */
	EventBaseOperator getSourceEvent();

	/**
	 * Sets the value of the '{@link eventbase.ESimpleEF#getSourceEvent <em>Source Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Event</em>' reference.
	 * @see #getSourceEvent()
	 * @generated
	 */
	void setSourceEvent(EventBaseOperator value);

} // ESimpleEF
