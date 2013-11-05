/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EF Processing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing#getWindowSize <em>Window Size</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEFProcessing()
 * @model
 * @generated
 */
public interface EFProcessing extends EFStatusEvent {
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
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getEFProcessing_WindowSize()
	 * @model default="1:00"
	 * @generated
	 */
	String getWindowSize();

	/**
	 * Sets the value of the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing#getWindowSize <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Size</em>' attribute.
	 * @see #getWindowSize()
	 * @generated
	 */
	void setWindowSize(String value);

} // EFProcessing
