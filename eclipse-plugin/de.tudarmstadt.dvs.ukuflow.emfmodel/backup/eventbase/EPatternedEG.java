/**
 */
package eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EPatterned EG</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.EPatternedEG#getTime <em>Time</em>}</li>
 *   <li>{@link eventbase.EPatternedEG#getPattern <em>Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEPatternedEG()
 * @model
 * @generated
 */
public interface EPatternedEG extends EventBaseOperator {
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
	 * @see eventbase.EventbasePackage#getEPatternedEG_Time()
	 * @model required="true"
	 * @generated
	 */
	int getTime();

	/**
	 * Sets the value of the '{@link eventbase.EPatternedEG#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(int value);

	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' attribute.
	 * @see #setPattern(String)
	 * @see eventbase.EventbasePackage#getEPatternedEG_Pattern()
	 * @model required="true"
	 * @generated
	 */
	String getPattern();

	/**
	 * Sets the value of the '{@link eventbase.EPatternedEG#getPattern <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' attribute.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(String value);

} // EPatternedEG
