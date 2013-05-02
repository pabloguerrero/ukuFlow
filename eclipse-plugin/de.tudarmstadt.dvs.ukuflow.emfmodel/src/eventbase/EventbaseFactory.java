/**
 */
package eventbase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eventbase.EventbasePackage
 * @generated
 */
public interface EventbaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventbaseFactory eINSTANCE = eventbase.impl.EventbaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>ESequence Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ESequence Flow</em>'.
	 * @generated
	 */
	ESequenceFlow createESequenceFlow();

	/**
	 * Returns a new object of class '<em>EPeriodic EG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EPeriodic EG</em>'.
	 * @generated
	 */
	EPeriodicEG createEPeriodicEG();

	/**
	 * Returns a new object of class '<em>EPatterned EG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EPatterned EG</em>'.
	 * @generated
	 */
	EPatternedEG createEPatternedEG();

	/**
	 * Returns a new object of class '<em>EDistribution EG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EDistribution EG</em>'.
	 * @generated
	 */
	EDistributionEG createEDistributionEG();

	/**
	 * Returns a new object of class '<em>ESimple Filter Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ESimple Filter Constraint</em>'.
	 * @generated
	 */
	ESimpleFilterConstraint createESimpleFilterConstraint();

	/**
	 * Returns a new object of class '<em>ESimple EF</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ESimple EF</em>'.
	 * @generated
	 */
	ESimpleEF createESimpleEF();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EventbasePackage getEventbasePackage();

} //EventbaseFactory
