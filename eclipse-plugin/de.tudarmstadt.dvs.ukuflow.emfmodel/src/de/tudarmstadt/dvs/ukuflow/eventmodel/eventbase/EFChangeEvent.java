/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EF Change Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getWindowSize <em>Window Size</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getChangeThreshold <em>Change Threshold</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEFChangeEvent()
 * @model
 * @generated
 */
public interface EFChangeEvent extends EFComposite {

	/**
	 * Returns the value of the '<em><b>Window Size</b></em>' attribute.
	 * The default value is <code>"1:00"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window Size</em>' attribute.
	 * @see #setWindowSize(String)
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEFChangeEvent_WindowSize()
	 * @model default="1:00"
	 * @generated
	 */
	String getWindowSize();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getWindowSize <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Size</em>' attribute.
	 * @see #getWindowSize()
	 * @generated
	 */
	void setWindowSize(String value);

	/**
	 * Returns the value of the '<em><b>Change Threshold</b></em>' attribute.
	 * The default value is <code>"10"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Threshold</em>' attribute.
	 * @see #setChangeThreshold(int)
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEFChangeEvent_ChangeThreshold()
	 * @model default="10"
	 * @generated
	 */
	int getChangeThreshold();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getChangeThreshold <em>Change Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change Threshold</em>' attribute.
	 * @see #getChangeThreshold()
	 * @generated
	 */
	void setChangeThreshold(int value);
} // EFChangeEvent
