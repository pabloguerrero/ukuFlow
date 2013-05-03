/**
 */
package eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EG Periodic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.EGPeriodic#getTime <em>Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEGPeriodic()
 * @model
 * @generated
 */
public interface EGPeriodic extends EGRecurring {
	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(int)
	 * @see eventbase.EventbasePackage#getEGPeriodic_Time()
	 * @model
	 * @generated
	 */
	int getTime();

	/**
	 * Sets the value of the '{@link eventbase.EGPeriodic#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(int value);

} // EGPeriodic
