/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EG Relative</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative#getDelayTime <em>Delay Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEGRelative()
 * @model
 * @generated
 */
public interface EGRelative extends EGNonRecurring {
	/**
	 * Returns the value of the '<em><b>Delay Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delay Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delay Time</em>' attribute.
	 * @see #setDelayTime(int)
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEGRelative_DelayTime()
	 * @model required="true"
	 * @generated
	 */
	int getDelayTime();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative#getDelayTime <em>Delay Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delay Time</em>' attribute.
	 * @see #getDelayTime()
	 * @generated
	 */
	void setDelayTime(int value);

} // EGRelative
