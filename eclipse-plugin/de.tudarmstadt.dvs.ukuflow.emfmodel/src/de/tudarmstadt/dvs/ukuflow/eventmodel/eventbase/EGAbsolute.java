/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EG Absolute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute#getAbsoluteTime <em>Absolute Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEGAbsolute()
 * @model
 * @generated
 */
public interface EGAbsolute extends EGNonRecurring {
	/**
	 * Returns the value of the '<em><b>Absolute Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Absolute Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Absolute Time</em>' attribute.
	 * @see #setAbsoluteTime(String)
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEGAbsolute_AbsoluteTime()
	 * @model required="true"
	 * @generated
	 */
	String getAbsoluteTime();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute#getAbsoluteTime <em>Absolute Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Absolute Time</em>' attribute.
	 * @see #getAbsoluteTime()
	 * @generated
	 */
	void setAbsoluteTime(String value);

} // EGAbsolute
