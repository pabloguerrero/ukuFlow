/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage
 * @generated
 */
public interface EventbaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventbaseFactory eINSTANCE = de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>ESequence Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ESequence Flow</em>'.
	 * @generated
	 */
	ESequenceFlow createESequenceFlow();

	/**
	 * Returns a new object of class '<em>Event Generator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Generator</em>'.
	 * @generated
	 */
	EventGenerator createEventGenerator();

	/**
	 * Returns a new object of class '<em>EG Non Recurring</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Non Recurring</em>'.
	 * @generated
	 */
	EGNonRecurring createEGNonRecurring();

	/**
	 * Returns a new object of class '<em>EG Immediate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Immediate</em>'.
	 * @generated
	 */
	EGImmediate createEGImmediate();

	/**
	 * Returns a new object of class '<em>EG Absolute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Absolute</em>'.
	 * @generated
	 */
	EGAbsolute createEGAbsolute();

	/**
	 * Returns a new object of class '<em>EG Offset</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Offset</em>'.
	 * @generated
	 */
	EGOffset createEGOffset();

	/**
	 * Returns a new object of class '<em>EG Relative</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Relative</em>'.
	 * @generated
	 */
	EGRelative createEGRelative();

	/**
	 * Returns a new object of class '<em>EG Recurring</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Recurring</em>'.
	 * @generated
	 */
	EGRecurring createEGRecurring();

	/**
	 * Returns a new object of class '<em>EG Periodic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Periodic</em>'.
	 * @generated
	 */
	EGPeriodic createEGPeriodic();

	/**
	 * Returns a new object of class '<em>EG Patterned</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Patterned</em>'.
	 * @generated
	 */
	EGPatterned createEGPatterned();

	/**
	 * Returns a new object of class '<em>EG Distribution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EG Distribution</em>'.
	 * @generated
	 */
	EGDistribution createEGDistribution();

	/**
	 * Returns a new object of class '<em>Event Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Filter</em>'.
	 * @generated
	 */
	EventFilter createEventFilter();

	/**
	 * Returns a new object of class '<em>EF Simple</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Simple</em>'.
	 * @generated
	 */
	EFSimple createEFSimple();

	/**
	 * Returns a new object of class '<em>EF Composite</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Composite</em>'.
	 * @generated
	 */
	EFComposite createEFComposite();

	/**
	 * Returns a new object of class '<em>EF Status Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Status Event</em>'.
	 * @generated
	 */
	EFStatusEvent createEFStatusEvent();

	/**
	 * Returns a new object of class '<em>EF Logic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Logic</em>'.
	 * @generated
	 */
	EFLogic createEFLogic();

	/**
	 * Returns a new object of class '<em>EF Processing</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing</em>'.
	 * @generated
	 */
	EFProcessing createEFProcessing();

	/**
	 * Returns a new object of class '<em>EF Temporal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Temporal</em>'.
	 * @generated
	 */
	EFTemporal createEFTemporal();

	/**
	 * Returns a new object of class '<em>EF Logic And</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Logic And</em>'.
	 * @generated
	 */
	EFLogicAnd createEFLogicAnd();

	/**
	 * Returns a new object of class '<em>EF Logic Or</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Logic Or</em>'.
	 * @generated
	 */
	EFLogicOr createEFLogicOr();

	/**
	 * Returns a new object of class '<em>EF Logic Not</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Logic Not</em>'.
	 * @generated
	 */
	EFLogicNot createEFLogicNot();

	/**
	 * Returns a new object of class '<em>EF Processing Min</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing Min</em>'.
	 * @generated
	 */
	EFProcessingMin createEFProcessingMin();

	/**
	 * Returns a new object of class '<em>EF Processing Max</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing Max</em>'.
	 * @generated
	 */
	EFProcessingMax createEFProcessingMax();

	/**
	 * Returns a new object of class '<em>EF Processing Sum</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing Sum</em>'.
	 * @generated
	 */
	EFProcessingSum createEFProcessingSum();

	/**
	 * Returns a new object of class '<em>EF Processing Count</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing Count</em>'.
	 * @generated
	 */
	EFProcessingCount createEFProcessingCount();

	/**
	 * Returns a new object of class '<em>EF Processing Avg</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing Avg</em>'.
	 * @generated
	 */
	EFProcessingAvg createEFProcessingAvg();

	/**
	 * Returns a new object of class '<em>EF Processing St Dev</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Processing St Dev</em>'.
	 * @generated
	 */
	EFProcessingStDev createEFProcessingStDev();

	/**
	 * Returns a new object of class '<em>EF Temporal Sequence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Temporal Sequence</em>'.
	 * @generated
	 */
	EFTemporalSequence createEFTemporalSequence();

	/**
	 * Returns a new object of class '<em>EF Change Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Change Event</em>'.
	 * @generated
	 */
	EFChangeEvent createEFChangeEvent();

	/**
	 * Returns a new object of class '<em>EF Change Increase</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Change Increase</em>'.
	 * @generated
	 */
	EFChangeIncrease createEFChangeIncrease();

	/**
	 * Returns a new object of class '<em>EF Change Decrease</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Change Decrease</em>'.
	 * @generated
	 */
	EFChangeDecrease createEFChangeDecrease();

	/**
	 * Returns a new object of class '<em>EF Change Remain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EF Change Remain</em>'.
	 * @generated
	 */
	EFChangeRemain createEFChangeRemain();

	/**
	 * Returns a new object of class '<em>Root Document</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root Document</em>'.
	 * @generated
	 */
	RootDocument createRootDocument();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EventbasePackage getEventbasePackage();

} //EventbaseFactory
