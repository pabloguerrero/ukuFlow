/**
 */
package eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EPeriodic EG</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.EPeriodicEG#getTime <em>Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEPeriodicEG()
 * @model
 * @generated
 */
public interface EPeriodicEG extends EventBaseOperator {
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
	 * @see eventbase.EventbasePackage#getEPeriodicEG_Time()
	 * @model required="true"
	 * @generated
	 */
	int getTime();

	/**
	 * Sets the value of the '{@link eventbase.EPeriodicEG#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(int value);

} // EPeriodicEG
