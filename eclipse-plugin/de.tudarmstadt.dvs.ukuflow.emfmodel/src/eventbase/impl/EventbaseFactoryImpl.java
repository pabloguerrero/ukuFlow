/**
 */
package eventbase.impl;

import eventbase.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EventbaseFactoryImpl extends EFactoryImpl implements EventbaseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EventbaseFactory init() {
		try {
			EventbaseFactory theEventbaseFactory = (EventbaseFactory)EPackage.Registry.INSTANCE.getEFactory("http://de/tudarmstadt/dvs/ukuflow/eventbase"); 
			if (theEventbaseFactory != null) {
				return theEventbaseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EventbaseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventbaseFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EventbasePackage.ESEQUENCE_FLOW: return createESequenceFlow();
			case EventbasePackage.EPERIODIC_EG: return createEPeriodicEG();
			case EventbasePackage.EPATTERNED_EG: return createEPatternedEG();
			case EventbasePackage.EDISTRIBUTION_EG: return createEDistributionEG();
			case EventbasePackage.ESIMPLE_FILTER_CONSTRAINT: return createESimpleFilterConstraint();
			case EventbasePackage.ESIMPLE_EF: return createESimpleEF();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ESequenceFlow createESequenceFlow() {
		ESequenceFlowImpl eSequenceFlow = new ESequenceFlowImpl();
		return eSequenceFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPeriodicEG createEPeriodicEG() {
		EPeriodicEGImpl ePeriodicEG = new EPeriodicEGImpl();
		return ePeriodicEG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPatternedEG createEPatternedEG() {
		EPatternedEGImpl ePatternedEG = new EPatternedEGImpl();
		return ePatternedEG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDistributionEG createEDistributionEG() {
		EDistributionEGImpl eDistributionEG = new EDistributionEGImpl();
		return eDistributionEG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ESimpleFilterConstraint createESimpleFilterConstraint() {
		ESimpleFilterConstraintImpl eSimpleFilterConstraint = new ESimpleFilterConstraintImpl();
		return eSimpleFilterConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ESimpleEF createESimpleEF() {
		ESimpleEFImpl eSimpleEF = new ESimpleEFImpl();
		return eSimpleEF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventbasePackage getEventbasePackage() {
		return (EventbasePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EventbasePackage getPackage() {
		return EventbasePackage.eINSTANCE;
	}

} //EventbaseFactoryImpl
