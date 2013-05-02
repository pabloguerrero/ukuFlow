/**
 */
package eventbase.util;

import eventbase.*;

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
 * @see eventbase.EventbasePackage
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
			case EventbasePackage.EPERIODIC_EG: {
				EPeriodicEG ePeriodicEG = (EPeriodicEG)theEObject;
				T result = caseEPeriodicEG(ePeriodicEG);
				if (result == null) result = caseEventBaseOperator(ePeriodicEG);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EPATTERNED_EG: {
				EPatternedEG ePatternedEG = (EPatternedEG)theEObject;
				T result = caseEPatternedEG(ePatternedEG);
				if (result == null) result = caseEventBaseOperator(ePatternedEG);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.EDISTRIBUTION_EG: {
				EDistributionEG eDistributionEG = (EDistributionEG)theEObject;
				T result = caseEDistributionEG(eDistributionEG);
				if (result == null) result = caseEventBaseOperator(eDistributionEG);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.ESIMPLE_FILTER_CONSTRAINT: {
				ESimpleFilterConstraint eSimpleFilterConstraint = (ESimpleFilterConstraint)theEObject;
				T result = caseESimpleFilterConstraint(eSimpleFilterConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EventbasePackage.ESIMPLE_EF: {
				ESimpleEF eSimpleEF = (ESimpleEF)theEObject;
				T result = caseESimpleEF(eSimpleEF);
				if (result == null) result = caseEventBaseOperator(eSimpleEF);
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
	 * Returns the result of interpreting the object as an instance of '<em>EPeriodic EG</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPeriodic EG</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPeriodicEG(EPeriodicEG object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EPatterned EG</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPatterned EG</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPatternedEG(EPatternedEG object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EDistribution EG</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EDistribution EG</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEDistributionEG(EDistributionEG object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>ESimple EF</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ESimple EF</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseESimpleEF(ESimpleEF object) {
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
