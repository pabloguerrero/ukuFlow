/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getSequenceFlow <em>Sequence Flow</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgImmediate <em>Eg Immediate</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgAbsolute <em>Eg Absolute</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgOffset <em>Eg Offset</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgRelative <em>Eg Relative</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPeriodic <em>Eg Periodic</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPatterned <em>Eg Patterned</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgDistribution <em>Eg Distribution</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicAnd <em>Ef Logic And</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicOr <em>Ef Logic Or</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicNot <em>Ef Logic Not</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMin <em>Ef Processing Min</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMax <em>Ef Processing Max</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingCount <em>Ef Processing Count</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingSum <em>Ef Processing Sum</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingAvg <em>Ef Processing Avg</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingStDev <em>Ef Processing St Dev</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfTemporalSeq <em>Ef Temporal Seq</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfIncrease <em>Ef Increase</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfDecrease <em>Ef Decrease</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfRemain <em>Ef Remain</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument()
 * @model
 * @generated
 */
public interface RootDocument extends EObject {
	/**
	 * Returns the value of the '<em><b>Sequence Flow</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Flow</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Flow</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_SequenceFlow()
	 * @model
	 * @generated
	 */
	EList<ESequenceFlow> getSequenceFlow();

	/**
	 * Returns the value of the '<em><b>Eg Immediate</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Immediate</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Immediate</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgImmediate()
	 * @model
	 * @generated
	 */
	EList<EGImmediate> getEgImmediate();

	/**
	 * Returns the value of the '<em><b>Eg Absolute</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Absolute</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Absolute</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgAbsolute()
	 * @model
	 * @generated
	 */
	EList<EGAbsolute> getEgAbsolute();

	/**
	 * Returns the value of the '<em><b>Eg Offset</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Offset</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Offset</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgOffset()
	 * @model
	 * @generated
	 */
	EList<EGOffset> getEgOffset();

	/**
	 * Returns the value of the '<em><b>Eg Relative</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Relative</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Relative</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgRelative()
	 * @model
	 * @generated
	 */
	EList<EGRelative> getEgRelative();

	/**
	 * Returns the value of the '<em><b>Eg Periodic</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Periodic</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Periodic</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgPeriodic()
	 * @model
	 * @generated
	 */
	EList<EGPeriodic> getEgPeriodic();

	/**
	 * Returns the value of the '<em><b>Eg Patterned</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Patterned</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Patterned</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgPatterned()
	 * @model
	 * @generated
	 */
	EList<EGPatterned> getEgPatterned();

	/**
	 * Returns the value of the '<em><b>Eg Distribution</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eg Distribution</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eg Distribution</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EgDistribution()
	 * @model
	 * @generated
	 */
	EList<EGDistribution> getEgDistribution();

	/**
	 * Returns the value of the '<em><b>Ef Logic And</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Logic And</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Logic And</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfLogicAnd()
	 * @model
	 * @generated
	 */
	EList<EFLogicAnd> getEfLogicAnd();

	/**
	 * Returns the value of the '<em><b>Ef Logic Or</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Logic Or</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Logic Or</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfLogicOr()
	 * @model
	 * @generated
	 */
	EList<EFLogicOr> getEfLogicOr();

	/**
	 * Returns the value of the '<em><b>Ef Logic Not</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Logic Not</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Logic Not</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfLogicNot()
	 * @model
	 * @generated
	 */
	EList<EFLogicNot> getEfLogicNot();

	/**
	 * Returns the value of the '<em><b>Ef Processing Min</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing Min</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing Min</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingMin()
	 * @model
	 * @generated
	 */
	EList<EFProcessingMin> getEfProcessingMin();

	/**
	 * Returns the value of the '<em><b>Ef Processing Max</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing Max</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing Max</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingMax()
	 * @model
	 * @generated
	 */
	EList<EFProcessingMax> getEfProcessingMax();

	/**
	 * Returns the value of the '<em><b>Ef Processing Count</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing Count</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing Count</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingCount()
	 * @model
	 * @generated
	 */
	EList<EFProcessingCount> getEfProcessingCount();

	/**
	 * Returns the value of the '<em><b>Ef Processing Sum</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing Sum</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing Sum</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingSum()
	 * @model
	 * @generated
	 */
	EList<EFProcessingSum> getEfProcessingSum();

	/**
	 * Returns the value of the '<em><b>Ef Processing Avg</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing Avg</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing Avg</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingAvg()
	 * @model
	 * @generated
	 */
	EList<EFProcessingAvg> getEfProcessingAvg();

	/**
	 * Returns the value of the '<em><b>Ef Processing St Dev</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Processing St Dev</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Processing St Dev</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfProcessingStDev()
	 * @model
	 * @generated
	 */
	EList<EFProcessingStDev> getEfProcessingStDev();

	/**
	 * Returns the value of the '<em><b>Ef Temporal Seq</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Temporal Seq</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Temporal Seq</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfTemporalSeq()
	 * @model
	 * @generated
	 */
	EList<EFTemporalSequence> getEfTemporalSeq();

	/**
	 * Returns the value of the '<em><b>Ef Increase</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Increase</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Increase</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfIncrease()
	 * @model
	 * @generated
	 */
	EList<EFChangeIncrease> getEfIncrease();

	/**
	 * Returns the value of the '<em><b>Ef Decrease</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Decrease</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Decrease</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfDecrease()
	 * @model
	 * @generated
	 */
	EList<EFChangeDecrease> getEfDecrease();

	/**
	 * Returns the value of the '<em><b>Ef Remain</b></em>' reference list.
	 * The list contents are of type {@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ef Remain</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ef Remain</em>' reference list.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#getRootDocument_EfRemain()
	 * @model
	 * @generated
	 */
	EList<EFChangeRemain> getEfRemain();

} // RootDocument
