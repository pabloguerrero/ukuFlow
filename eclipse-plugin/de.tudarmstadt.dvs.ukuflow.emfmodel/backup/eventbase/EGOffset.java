/**
 */
package eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EG Offset</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.EGOffset#getOffsetTime <em>Offset Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEGOffset()
 * @model
 * @generated
 */
public interface EGOffset extends EGNonRecurring {
	/**
	 * Returns the value of the '<em><b>Offset Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset Time</em>' attribute.
	 * @see #setOffsetTime(int)
	 * @see eventbase.EventbasePackage#getEGOffset_OffsetTime()
	 * @model required="true"
	 * @generated
	 */
	int getOffsetTime();

	/**
	 * Sets the value of the '{@link eventbase.EGOffset#getOffsetTime <em>Offset Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset Time</em>' attribute.
	 * @see #getOffsetTime()
	 * @generated
	 */
	void setOffsetTime(int value);

} // EGOffset
