/**
 */
package eventbase;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EDistribution EG</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eventbase.EDistributionEG#getFunctionName <em>Function Name</em>}</li>
 *   <li>{@link eventbase.EDistributionEG#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see eventbase.EventbasePackage#getEDistributionEG()
 * @model
 * @generated
 */
public interface EDistributionEG extends EventBaseOperator {
	/**
	 * Returns the value of the '<em><b>Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Name</em>' attribute.
	 * @see #setFunctionName(String)
	 * @see eventbase.EventbasePackage#getEDistributionEG_FunctionName()
	 * @model required="true"
	 * @generated
	 */
	String getFunctionName();

	/**
	 * Sets the value of the '{@link eventbase.EDistributionEG#getFunctionName <em>Function Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Name</em>' attribute.
	 * @see #getFunctionName()
	 * @generated
	 */
	void setFunctionName(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute.
	 * @see #setParameters(byte[])
	 * @see eventbase.EventbasePackage#getEDistributionEG_Parameters()
	 * @model required="true"
	 * @generated
	 */
	byte[] getParameters();

	/**
	 * Sets the value of the '{@link eventbase.EDistributionEG#getParameters <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameters</em>' attribute.
	 * @see #getParameters()
	 * @generated
	 */
	void setParameters(byte[] value);

} // EDistributionEG
