/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;

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
			case EventbasePackage.EVENT_GENERATOR: return createEventGenerator();
			case EventbasePackage.EG_NON_RECURRING: return createEGNonRecurring();
			case EventbasePackage.EG_IMMEDIATE: return createEGImmediate();
			case EventbasePackage.EG_ABSOLUTE: return createEGAbsolute();
			case EventbasePackage.EG_OFFSET: return createEGOffset();
			case EventbasePackage.EG_RELATIVE: return createEGRelative();
			case EventbasePackage.EG_RECURRING: return createEGRecurring();
			case EventbasePackage.EG_PERIODIC: return createEGPeriodic();
			case EventbasePackage.EG_PATTERNED: return createEGPatterned();
			case EventbasePackage.EG_DISTRIBUTION: return createEGDistribution();
			case EventbasePackage.EVENT_FILTER: return createEventFilter();
			case EventbasePackage.EF_SIMPLE: return createEFSimple();
			case EventbasePackage.EF_COMPOSITE: return createEFComposite();
			case EventbasePackage.EF_STATUS_EVENT: return createEFStatusEvent();
			case EventbasePackage.EF_LOGIC: return createEFLogic();
			case EventbasePackage.EF_PROCESSING: return createEFProcessing();
			case EventbasePackage.EF_TEMPORAL: return createEFTemporal();
			case EventbasePackage.EF_LOGIC_AND: return createEFLogicAnd();
			case EventbasePackage.EF_LOGIC_OR: return createEFLogicOr();
			case EventbasePackage.EF_LOGIC_NOT: return createEFLogicNot();
			case EventbasePackage.EF_PROCESSING_MIN: return createEFProcessingMin();
			case EventbasePackage.EF_PROCESSING_MAX: return createEFProcessingMax();
			case EventbasePackage.EF_PROCESSING_SUM: return createEFProcessingSum();
			case EventbasePackage.EF_PROCESSING_COUNT: return createEFProcessingCount();
			case EventbasePackage.EF_PROCESSING_AVG: return createEFProcessingAvg();
			case EventbasePackage.EF_PROCESSING_ST_DEV: return createEFProcessingStDev();
			case EventbasePackage.EF_TEMPORAL_SEQUENCE: return createEFTemporalSequence();
			case EventbasePackage.EF_CHANGE_EVENT: return createEFChangeEvent();
			case EventbasePackage.EF_CHANGE_INCREASE: return createEFChangeIncrease();
			case EventbasePackage.EF_CHANGE_DECREASE: return createEFChangeDecrease();
			case EventbasePackage.EF_CHANGE_REMAIN: return createEFChangeRemain();
			case EventbasePackage.ROOT_DOCUMENT: return createRootDocument();
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
	public EventGenerator createEventGenerator() {
		EventGeneratorImpl eventGenerator = new EventGeneratorImpl();
		return eventGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGNonRecurring createEGNonRecurring() {
		EGNonRecurringImpl egNonRecurring = new EGNonRecurringImpl();
		return egNonRecurring;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGImmediate createEGImmediate() {
		EGImmediateImpl egImmediate = new EGImmediateImpl();
		return egImmediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGAbsolute createEGAbsolute() {
		EGAbsoluteImpl egAbsolute = new EGAbsoluteImpl();
		return egAbsolute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGOffset createEGOffset() {
		EGOffsetImpl egOffset = new EGOffsetImpl();
		return egOffset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGRelative createEGRelative() {
		EGRelativeImpl egRelative = new EGRelativeImpl();
		return egRelative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGRecurring createEGRecurring() {
		EGRecurringImpl egRecurring = new EGRecurringImpl();
		return egRecurring;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGPeriodic createEGPeriodic() {
		EGPeriodicImpl egPeriodic = new EGPeriodicImpl();
		return egPeriodic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGPatterned createEGPatterned() {
		EGPatternedImpl egPatterned = new EGPatternedImpl();
		return egPatterned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EGDistribution createEGDistribution() {
		EGDistributionImpl egDistribution = new EGDistributionImpl();
		return egDistribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventFilter createEventFilter() {
		EventFilterImpl eventFilter = new EventFilterImpl();
		return eventFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFSimple createEFSimple() {
		EFSimpleImpl efSimple = new EFSimpleImpl();
		return efSimple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFComposite createEFComposite() {
		EFCompositeImpl efComposite = new EFCompositeImpl();
		return efComposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFStatusEvent createEFStatusEvent() {
		EFStatusEventImpl efStatusEvent = new EFStatusEventImpl();
		return efStatusEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFLogic createEFLogic() {
		EFLogicImpl efLogic = new EFLogicImpl();
		return efLogic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessing createEFProcessing() {
		EFProcessingImpl efProcessing = new EFProcessingImpl();
		return efProcessing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFTemporal createEFTemporal() {
		EFTemporalImpl efTemporal = new EFTemporalImpl();
		return efTemporal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFLogicAnd createEFLogicAnd() {
		EFLogicAndImpl efLogicAnd = new EFLogicAndImpl();
		return efLogicAnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFLogicOr createEFLogicOr() {
		EFLogicOrImpl efLogicOr = new EFLogicOrImpl();
		return efLogicOr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFLogicNot createEFLogicNot() {
		EFLogicNotImpl efLogicNot = new EFLogicNotImpl();
		return efLogicNot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingMin createEFProcessingMin() {
		EFProcessingMinImpl efProcessingMin = new EFProcessingMinImpl();
		return efProcessingMin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingMax createEFProcessingMax() {
		EFProcessingMaxImpl efProcessingMax = new EFProcessingMaxImpl();
		return efProcessingMax;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingSum createEFProcessingSum() {
		EFProcessingSumImpl efProcessingSum = new EFProcessingSumImpl();
		return efProcessingSum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingCount createEFProcessingCount() {
		EFProcessingCountImpl efProcessingCount = new EFProcessingCountImpl();
		return efProcessingCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingAvg createEFProcessingAvg() {
		EFProcessingAvgImpl efProcessingAvg = new EFProcessingAvgImpl();
		return efProcessingAvg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFProcessingStDev createEFProcessingStDev() {
		EFProcessingStDevImpl efProcessingStDev = new EFProcessingStDevImpl();
		return efProcessingStDev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFTemporalSequence createEFTemporalSequence() {
		EFTemporalSequenceImpl efTemporalSequence = new EFTemporalSequenceImpl();
		return efTemporalSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeEvent createEFChangeEvent() {
		EFChangeEventImpl efChangeEvent = new EFChangeEventImpl();
		return efChangeEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeIncrease createEFChangeIncrease() {
		EFChangeIncreaseImpl efChangeIncrease = new EFChangeIncreaseImpl();
		return efChangeIncrease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeDecrease createEFChangeDecrease() {
		EFChangeDecreaseImpl efChangeDecrease = new EFChangeDecreaseImpl();
		return efChangeDecrease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EFChangeRemain createEFChangeRemain() {
		EFChangeRemainImpl efChangeRemain = new EFChangeRemainImpl();
		return efChangeRemain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootDocument createRootDocument() {
		RootDocumentImpl rootDocument = new RootDocumentImpl();
		return rootDocument;
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
