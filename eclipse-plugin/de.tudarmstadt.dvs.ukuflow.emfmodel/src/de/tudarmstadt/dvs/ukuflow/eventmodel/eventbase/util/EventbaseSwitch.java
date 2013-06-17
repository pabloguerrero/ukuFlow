/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.util;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage
 * @generated
 */
public class EventbaseSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EventbasePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventbaseSwitch() {
		if (modelPackage == null) {
			modelPackage = EventbasePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case EventbasePackage.EVENT_BASE_OPERATOR: {
				EventBaseOperator eventBaseOperator = (EventBaseOperator)theEObject;
				T result = caseEventBaseOperator(eventBaseOperator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.ESEQUENCE_FLOW: {
				ESequenceFlow eSequenceFlow = (ESequenceFlow)theEObject;
				T result = caseESequenceFlow(eSequenceFlow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.ESIMPLE_FILTER_CONSTRAINT: {
				ESimpleFilterConstraint eSimpleFilterConstraint = (ESimpleFilterConstraint)theEObject;
				T result = caseESimpleFilterConstraint(eSimpleFilterConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EVENT_GENERATOR: {
				EventGenerator eventGenerator = (EventGenerator)theEObject;
				T result = caseEventGenerator(eventGenerator);
				if (result == null) result = caseEventBaseOperator(eventGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_NON_RECURRING: {
				EGNonRecurring egNonRecurring = (EGNonRecurring)theEObject;
				T result = caseEGNonRecurring(egNonRecurring);
				if (result == null) result = caseEventGenerator(egNonRecurring);
				if (result == null) result = caseEventBaseOperator(egNonRecurring);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_IMMEDIATE: {
				EGImmediate egImmediate = (EGImmediate)theEObject;
				T result = caseEGImmediate(egImmediate);
				if (result == null) result = caseEGNonRecurring(egImmediate);
				if (result == null) result = caseEventGenerator(egImmediate);
				if (result == null) result = caseEventBaseOperator(egImmediate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_ABSOLUTE: {
				EGAbsolute egAbsolute = (EGAbsolute)theEObject;
				T result = caseEGAbsolute(egAbsolute);
				if (result == null) result = caseEGNonRecurring(egAbsolute);
				if (result == null) result = caseEventGenerator(egAbsolute);
				if (result == null) result = caseEventBaseOperator(egAbsolute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_OFFSET: {
				EGOffset egOffset = (EGOffset)theEObject;
				T result = caseEGOffset(egOffset);
				if (result == null) result = caseEGNonRecurring(egOffset);
				if (result == null) result = caseEventGenerator(egOffset);
				if (result == null) result = caseEventBaseOperator(egOffset);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_RELATIVE: {
				EGRelative egRelative = (EGRelative)theEObject;
				T result = caseEGRelative(egRelative);
				if (result == null) result = caseEGNonRecurring(egRelative);
				if (result == null) result = caseEventGenerator(egRelative);
				if (result == null) result = caseEventBaseOperator(egRelative);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_RECURRING: {
				EGRecurring egRecurring = (EGRecurring)theEObject;
				T result = caseEGRecurring(egRecurring);
				if (result == null) result = caseEventGenerator(egRecurring);
				if (result == null) result = caseEventBaseOperator(egRecurring);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_PERIODIC: {
				EGPeriodic egPeriodic = (EGPeriodic)theEObject;
				T result = caseEGPeriodic(egPeriodic);
				if (result == null) result = caseEGRecurring(egPeriodic);
				if (result == null) result = caseEventGenerator(egPeriodic);
				if (result == null) result = caseEventBaseOperator(egPeriodic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_PATTERNED: {
				EGPatterned egPatterned = (EGPatterned)theEObject;
				T result = caseEGPatterned(egPatterned);
				if (result == null) result = caseEGRecurring(egPatterned);
				if (result == null) result = caseEventGenerator(egPatterned);
				if (result == null) result = caseEventBaseOperator(egPatterned);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EG_DISTRIBUTION: {
				EGDistribution egDistribution = (EGDistribution)theEObject;
				T result = caseEGDistribution(egDistribution);
				if (result == null) result = caseEGRecurring(egDistribution);
				if (result == null) result = caseEventGenerator(egDistribution);
				if (result == null) result = caseEventBaseOperator(egDistribution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EVENT_FILTER: {
				EventFilter eventFilter = (EventFilter)theEObject;
				T result = caseEventFilter(eventFilter);
				if (result == null) result = caseEventBaseOperator(eventFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_SIMPLE: {
				EFSimple efSimple = (EFSimple)theEObject;
				T result = caseEFSimple(efSimple);
				if (result == null) result = caseEventFilter(efSimple);
				if (result == null) result = caseEventBaseOperator(efSimple);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_COMPOSITE: {
				EFComposite efComposite = (EFComposite)theEObject;
				T result = caseEFComposite(efComposite);
				if (result == null) result = caseEventFilter(efComposite);
				if (result == null) result = caseEventBaseOperator(efComposite);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_STATUS_EVENT: {
				EFStatusEvent efStatusEvent = (EFStatusEvent)theEObject;
				T result = caseEFStatusEvent(efStatusEvent);
				if (result == null) result = caseEFComposite(efStatusEvent);
				if (result == null) result = caseEventFilter(efStatusEvent);
				if (result == null) result = caseEventBaseOperator(efStatusEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_LOGIC: {
				EFLogic efLogic = (EFLogic)theEObject;
				T result = caseEFLogic(efLogic);
				if (result == null) result = caseEFStatusEvent(efLogic);
				if (result == null) result = caseEFComposite(efLogic);
				if (result == null) result = caseEventFilter(efLogic);
				if (result == null) result = caseEventBaseOperator(efLogic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING: {
				EFProcessing efProcessing = (EFProcessing)theEObject;
				T result = caseEFProcessing(efProcessing);
				if (result == null) result = caseEFStatusEvent(efProcessing);
				if (result == null) result = caseEFComposite(efProcessing);
				if (result == null) result = caseEventFilter(efProcessing);
				if (result == null) result = caseEventBaseOperator(efProcessing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_TEMPORAL: {
				EFTemporal efTemporal = (EFTemporal)theEObject;
				T result = caseEFTemporal(efTemporal);
				if (result == null) result = caseEFStatusEvent(efTemporal);
				if (result == null) result = caseEFComposite(efTemporal);
				if (result == null) result = caseEventFilter(efTemporal);
				if (result == null) result = caseEventBaseOperator(efTemporal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_LOGIC_AND: {
				EFLogicAnd efLogicAnd = (EFLogicAnd)theEObject;
				T result = caseEFLogicAnd(efLogicAnd);
				if (result == null) result = caseEFLogic(efLogicAnd);
				if (result == null) result = caseEFStatusEvent(efLogicAnd);
				if (result == null) result = caseEFComposite(efLogicAnd);
				if (result == null) result = caseEventFilter(efLogicAnd);
				if (result == null) result = caseEventBaseOperator(efLogicAnd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_LOGIC_OR: {
				EFLogicOr efLogicOr = (EFLogicOr)theEObject;
				T result = caseEFLogicOr(efLogicOr);
				if (result == null) result = caseEFLogic(efLogicOr);
				if (result == null) result = caseEFStatusEvent(efLogicOr);
				if (result == null) result = caseEFComposite(efLogicOr);
				if (result == null) result = caseEventFilter(efLogicOr);
				if (result == null) result = caseEventBaseOperator(efLogicOr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_LOGIC_NOT: {
				EFLogicNot efLogicNot = (EFLogicNot)theEObject;
				T result = caseEFLogicNot(efLogicNot);
				if (result == null) result = caseEFLogic(efLogicNot);
				if (result == null) result = caseEFStatusEvent(efLogicNot);
				if (result == null) result = caseEFComposite(efLogicNot);
				if (result == null) result = caseEventFilter(efLogicNot);
				if (result == null) result = caseEventBaseOperator(efLogicNot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_MIN: {
				EFProcessingMin efProcessingMin = (EFProcessingMin)theEObject;
				T result = caseEFProcessingMin(efProcessingMin);
				if (result == null) result = caseEFProcessing(efProcessingMin);
				if (result == null) result = caseEFStatusEvent(efProcessingMin);
				if (result == null) result = caseEFComposite(efProcessingMin);
				if (result == null) result = caseEventFilter(efProcessingMin);
				if (result == null) result = caseEventBaseOperator(efProcessingMin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_MAX: {
				EFProcessingMax efProcessingMax = (EFProcessingMax)theEObject;
				T result = caseEFProcessingMax(efProcessingMax);
				if (result == null) result = caseEFProcessing(efProcessingMax);
				if (result == null) result = caseEFStatusEvent(efProcessingMax);
				if (result == null) result = caseEFComposite(efProcessingMax);
				if (result == null) result = caseEventFilter(efProcessingMax);
				if (result == null) result = caseEventBaseOperator(efProcessingMax);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_SUM: {
				EFProcessingSum efProcessingSum = (EFProcessingSum)theEObject;
				T result = caseEFProcessingSum(efProcessingSum);
				if (result == null) result = caseEFProcessing(efProcessingSum);
				if (result == null) result = caseEFStatusEvent(efProcessingSum);
				if (result == null) result = caseEFComposite(efProcessingSum);
				if (result == null) result = caseEventFilter(efProcessingSum);
				if (result == null) result = caseEventBaseOperator(efProcessingSum);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_COUNT: {
				EFProcessingCount efProcessingCount = (EFProcessingCount)theEObject;
				T result = caseEFProcessingCount(efProcessingCount);
				if (result == null) result = caseEFProcessing(efProcessingCount);
				if (result == null) result = caseEFStatusEvent(efProcessingCount);
				if (result == null) result = caseEFComposite(efProcessingCount);
				if (result == null) result = caseEventFilter(efProcessingCount);
				if (result == null) result = caseEventBaseOperator(efProcessingCount);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_AVG: {
				EFProcessingAvg efProcessingAvg = (EFProcessingAvg)theEObject;
				T result = caseEFProcessingAvg(efProcessingAvg);
				if (result == null) result = caseEFProcessing(efProcessingAvg);
				if (result == null) result = caseEFStatusEvent(efProcessingAvg);
				if (result == null) result = caseEFComposite(efProcessingAvg);
				if (result == null) result = caseEventFilter(efProcessingAvg);
				if (result == null) result = caseEventBaseOperator(efProcessingAvg);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_PROCESSING_ST_DEV: {
				EFProcessingStDev efProcessingStDev = (EFProcessingStDev)theEObject;
				T result = caseEFProcessingStDev(efProcessingStDev);
				if (result == null) result = caseEFProcessing(efProcessingStDev);
				if (result == null) result = caseEFStatusEvent(efProcessingStDev);
				if (result == null) result = caseEFComposite(efProcessingStDev);
				if (result == null) result = caseEventFilter(efProcessingStDev);
				if (result == null) result = caseEventBaseOperator(efProcessingStDev);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_TEMPORAL_SEQUENCE: {
				EFTemporalSequence efTemporalSequence = (EFTemporalSequence)theEObject;
				T result = caseEFTemporalSequence(efTemporalSequence);
				if (result == null) result = caseEFTemporal(efTemporalSequence);
				if (result == null) result = caseEFStatusEvent(efTemporalSequence);
				if (result == null) result = caseEFComposite(efTemporalSequence);
				if (result == null) result = caseEventFilter(efTemporalSequence);
				if (result == null) result = caseEventBaseOperator(efTemporalSequence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_CHANGE_EVENT: {
				EFChangeEvent efChangeEvent = (EFChangeEvent)theEObject;
				T result = caseEFChangeEvent(efChangeEvent);
				if (result == null) result = caseEFComposite(efChangeEvent);
				if (result == null) result = caseEventFilter(efChangeEvent);
				if (result == null) result = caseEventBaseOperator(efChangeEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_CHANGE_INCREASE: {
				EFChangeIncrease efChangeIncrease = (EFChangeIncrease)theEObject;
				T result = caseEFChangeIncrease(efChangeIncrease);
				if (result == null) result = caseEFChangeEvent(efChangeIncrease);
				if (result == null) result = caseEFComposite(efChangeIncrease);
				if (result == null) result = caseEventFilter(efChangeIncrease);
				if (result == null) result = caseEventBaseOperator(efChangeIncrease);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_CHANGE_DECREASE: {
				EFChangeDecrease efChangeDecrease = (EFChangeDecrease)theEObject;
				T result = caseEFChangeDecrease(efChangeDecrease);
				if (result == null) result = caseEFChangeEvent(efChangeDecrease);
				if (result == null) result = caseEFComposite(efChangeDecrease);
				if (result == null) result = caseEventFilter(efChangeDecrease);
				if (result == null) result = caseEventBaseOperator(efChangeDecrease);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EF_CHANGE_REMAIN: {
				EFChangeRemain efChangeRemain = (EFChangeRemain)theEObject;
				T result = caseEFChangeRemain(efChangeRemain);
				if (result == null) result = caseEFChangeEvent(efChangeRemain);
				if (result == null) result = caseEFComposite(efChangeRemain);
				if (result == null) result = caseEventFilter(efChangeRemain);
				if (result == null) result = caseEventBaseOperator(efChangeRemain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.ROOT_DOCUMENT: {
				RootDocument rootDocument = (RootDocument)theEObject;
				T result = caseRootDocument(rootDocument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Base Operator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Base Operator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventBaseOperator(EventBaseOperator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ESequence Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ESequence Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseESequenceFlow(ESequenceFlow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ESimple Filter Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ESimple Filter Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseESimpleFilterConstraint(ESimpleFilterConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventGenerator(EventGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Non Recurring</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Non Recurring</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGNonRecurring(EGNonRecurring object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Immediate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Immediate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGImmediate(EGImmediate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Absolute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Absolute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGAbsolute(EGAbsolute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Offset</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Offset</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGOffset(EGOffset object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Relative</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Relative</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGRelative(EGRelative object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Recurring</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Recurring</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGRecurring(EGRecurring object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Periodic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Periodic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGPeriodic(EGPeriodic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Patterned</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Patterned</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGPatterned(EGPatterned object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EG Distribution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EG Distribution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEGDistribution(EGDistribution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventFilter(EventFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Simple</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Simple</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFSimple(EFSimple object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Composite</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Composite</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFComposite(EFComposite object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Status Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Status Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFStatusEvent(EFStatusEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Logic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Logic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFLogic(EFLogic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessing(EFProcessing object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Temporal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Temporal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFTemporal(EFTemporal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Logic And</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Logic And</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFLogicAnd(EFLogicAnd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Logic Or</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Logic Or</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFLogicOr(EFLogicOr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Logic Not</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Logic Not</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFLogicNot(EFLogicNot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing Min</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing Min</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingMin(EFProcessingMin object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing Max</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing Max</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingMax(EFProcessingMax object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing Sum</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing Sum</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingSum(EFProcessingSum object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing Count</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing Count</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingCount(EFProcessingCount object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing Avg</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing Avg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingAvg(EFProcessingAvg object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Processing St Dev</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Processing St Dev</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFProcessingStDev(EFProcessingStDev object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Temporal Sequence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Temporal Sequence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFTemporalSequence(EFTemporalSequence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Change Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Change Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFChangeEvent(EFChangeEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Change Increase</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Change Increase</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFChangeIncrease(EFChangeIncrease object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Change Decrease</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Change Decrease</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFChangeDecrease(EFChangeDecrease object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EF Change Remain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EF Change Remain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEFChangeRemain(EFChangeRemain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Root Document</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Root Document</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRootDocument(RootDocument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //EventbaseSwitch
