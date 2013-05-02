/**
 */
package eventbase.util;

import eventbase.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eventbase.EventbasePackage
 * @generated
 */
public class EventbaseAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EventbasePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventbaseAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EventbasePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventbaseSwitch<Adapter> modelSwitch =
		new EventbaseSwitch<Adapter>() {
			@Override
			public Adapter caseEventBaseOperator(EventBaseOperator object) {
				return createEventBaseOperatorAdapter();
			}
			@Override
			public Adapter caseESequenceFlow(ESequenceFlow object) {
				return createESequenceFlowAdapter();
			}
			@Override
			public Adapter caseEPeriodicEG(EPeriodicEG object) {
				return createEPeriodicEGAdapter();
			}
			@Override
			public Adapter caseEPatternedEG(EPatternedEG object) {
				return createEPatternedEGAdapter();
			}
			@Override
			public Adapter caseEDistributionEG(EDistributionEG object) {
				return createEDistributionEGAdapter();
			}
			@Override
			public Adapter caseESimpleFilterConstraint(ESimpleFilterConstraint object) {
				return createESimpleFilterConstraintAdapter();
			}
			@Override
			public Adapter caseESimpleEF(ESimpleEF object) {
				return createESimpleEFAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link eventbase.EventBaseOperator <em>Event Base Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.EventBaseOperator
	 * @generated
	 */
	public Adapter createEventBaseOperatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.ESequenceFlow <em>ESequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.ESequenceFlow
	 * @generated
	 */
	public Adapter createESequenceFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.EPeriodicEG <em>EPeriodic EG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.EPeriodicEG
	 * @generated
	 */
	public Adapter createEPeriodicEGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.EPatternedEG <em>EPatterned EG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.EPatternedEG
	 * @generated
	 */
	public Adapter createEPatternedEGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.EDistributionEG <em>EDistribution EG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.EDistributionEG
	 * @generated
	 */
	public Adapter createEDistributionEGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.ESimpleFilterConstraint <em>ESimple Filter Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.ESimpleFilterConstraint
	 * @generated
	 */
	public Adapter createESimpleFilterConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eventbase.ESimpleEF <em>ESimple EF</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eventbase.ESimpleEF
	 * @generated
	 */
	public Adapter createESimpleEFAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //EventbaseAdapterFactory
