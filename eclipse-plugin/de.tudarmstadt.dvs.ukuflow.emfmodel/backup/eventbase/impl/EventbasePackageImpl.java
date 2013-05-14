/**
 */
package eventbase.impl;

import eventbase.EDistributionEG;
import eventbase.EFChangeDecrease;
import eventbase.EFChangeEvent;
import eventbase.EFChangeIncrease;
import eventbase.EFChangeRemain;
import eventbase.EFComposite;
import eventbase.EFLogic;
import eventbase.EFLogicAnd;
import eventbase.EFLogicNot;
import eventbase.EFLogicOr;
import eventbase.EFProcessing;
import eventbase.EFProcessingAvg;
import eventbase.EFProcessingCount;
import eventbase.EFProcessingMax;
import eventbase.EFProcessingMin;
import eventbase.EFProcessingStDev;
import eventbase.EFProcessingSum;
import eventbase.EFSimple;
import eventbase.EFStatusEvent;
import eventbase.EFTemporal;
import eventbase.EFTemporalSequence;
import eventbase.EGAbsolute;
import eventbase.EGDistribution;
import eventbase.EGImmediate;
import eventbase.EGNonRecurring;
import eventbase.EGOffset;
import eventbase.EGPatterned;
import eventbase.EGPeriodic;
import eventbase.EGRecurring;
import eventbase.EGRelative;
import eventbase.EPatternedEG;
import eventbase.EPeriodicEG;
import eventbase.ESequenceFlow;
import eventbase.ESimpleFilterConstraint;
import eventbase.EventBaseOperator;
import eventbase.EventFilter;
import eventbase.EventGenerator;
import eventbase.EventbaseFactory;
import eventbase.EventbasePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EventbasePackageImpl extends EPackageImpl implements EventbasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventBaseOperatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eSequenceFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ePeriodicEGEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ePatternedEGEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eDistributionEGEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eSimpleFilterConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egNonRecurringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egImmediateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egAbsoluteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egOffsetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egRelativeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egRecurringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egPeriodicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egPatternedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass egDistributionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efSimpleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efCompositeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efStatusEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efLogicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efTemporalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efLogicAndEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efLogicOrEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efLogicNotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingMinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingMaxEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingSumEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingCountEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingAvgEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efProcessingStDevEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efTemporalSequenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efChangeEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efChangeIncreaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efChangeDecreaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass efChangeRemainEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see eventbase.EventbasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EventbasePackageImpl() {
		super(eNS_URI, EventbaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EventbasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EventbasePackage init() {
		if (isInited) return (EventbasePackage)EPackage.Registry.INSTANCE.getEPackage(EventbasePackage.eNS_URI);

		// Obtain or create and register package
		EventbasePackageImpl theEventbasePackage = (EventbasePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EventbasePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EventbasePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theEventbasePackage.createPackageContents();

		// Initialize created meta-data
		theEventbasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEventbasePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EventbasePackage.eNS_URI, theEventbasePackage);
		return theEventbasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventBaseOperator() {
		return eventBaseOperatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventBaseOperator_Incoming() {
		return (EReference)eventBaseOperatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventBaseOperator_Outgoing() {
		return (EReference)eventBaseOperatorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getESequenceFlow() {
		return eSequenceFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getESequenceFlow_Source() {
		return (EReference)eSequenceFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getESequenceFlow_Target() {
		return (EReference)eSequenceFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEPeriodicEG() {
		return ePeriodicEGEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPeriodicEG_Time() {
		return (EAttribute)ePeriodicEGEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEPatternedEG() {
		return ePatternedEGEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPatternedEG_Time() {
		return (EAttribute)ePatternedEGEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPatternedEG_Pattern() {
		return (EAttribute)ePatternedEGEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEDistributionEG() {
		return eDistributionEGEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEDistributionEG_FunctionName() {
		return (EAttribute)eDistributionEGEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEDistributionEG_Parameters() {
		return (EAttribute)eDistributionEGEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getESimpleFilterConstraint() {
		return eSimpleFilterConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getESimpleFilterConstraint_Type() {
		return (EAttribute)eSimpleFilterConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getESimpleFilterConstraint_Value() {
		return (EAttribute)eSimpleFilterConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getESimpleFilterConstraint_Operator() {
		return (EAttribute)eSimpleFilterConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventGenerator() {
		return eventGeneratorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGNonRecurring() {
		return egNonRecurringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGImmediate() {
		return egImmediateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGAbsolute() {
		return egAbsoluteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGAbsolute_AbsoluteTime() {
		return (EAttribute)egAbsoluteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGOffset() {
		return egOffsetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGOffset_OffsetTime() {
		return (EAttribute)egOffsetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGRelative() {
		return egRelativeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGRelative_DelayTime() {
		return (EAttribute)egRelativeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGRecurring() {
		return egRecurringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGPeriodic() {
		return egPeriodicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGPeriodic_Time() {
		return (EAttribute)egPeriodicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGPatterned() {
		return egPatternedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGPatterned_Time() {
		return (EAttribute)egPatternedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGPatterned_Pattern() {
		return (EAttribute)egPatternedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEGDistribution() {
		return egDistributionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGDistribution_Time() {
		return (EAttribute)egDistributionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGDistribution_Function() {
		return (EAttribute)egDistributionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventFilter() {
		return eventFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFSimple() {
		return efSimpleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEFSimple_Constraints() {
		return (EReference)efSimpleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEFSimple_SourceEvent() {
		return (EReference)efSimpleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFComposite() {
		return efCompositeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFStatusEvent() {
		return efStatusEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFLogic() {
		return efLogicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessing() {
		return efProcessingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFTemporal() {
		return efTemporalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFLogicAnd() {
		return efLogicAndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFLogicOr() {
		return efLogicOrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFLogicNot() {
		return efLogicNotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingMin() {
		return efProcessingMinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingMax() {
		return efProcessingMaxEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingSum() {
		return efProcessingSumEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingCount() {
		return efProcessingCountEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingAvg() {
		return efProcessingAvgEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFProcessingStDev() {
		return efProcessingStDevEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFTemporalSequence() {
		return efTemporalSequenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFChangeEvent() {
		return efChangeEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFChangeIncrease() {
		return efChangeIncreaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFChangeDecrease() {
		return efChangeDecreaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEFChangeRemain() {
		return efChangeRemainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventbaseFactory getEventbaseFactory() {
		return (EventbaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		eventBaseOperatorEClass = createEClass(EVENT_BASE_OPERATOR);
		createEReference(eventBaseOperatorEClass, EVENT_BASE_OPERATOR__INCOMING);
		createEReference(eventBaseOperatorEClass, EVENT_BASE_OPERATOR__OUTGOING);

		eSequenceFlowEClass = createEClass(ESEQUENCE_FLOW);
		createEReference(eSequenceFlowEClass, ESEQUENCE_FLOW__SOURCE);
		createEReference(eSequenceFlowEClass, ESEQUENCE_FLOW__TARGET);

		ePeriodicEGEClass = createEClass(EPERIODIC_EG);
		createEAttribute(ePeriodicEGEClass, EPERIODIC_EG__TIME);

		ePatternedEGEClass = createEClass(EPATTERNED_EG);
		createEAttribute(ePatternedEGEClass, EPATTERNED_EG__TIME);
		createEAttribute(ePatternedEGEClass, EPATTERNED_EG__PATTERN);

		eDistributionEGEClass = createEClass(EDISTRIBUTION_EG);
		createEAttribute(eDistributionEGEClass, EDISTRIBUTION_EG__FUNCTION_NAME);
		createEAttribute(eDistributionEGEClass, EDISTRIBUTION_EG__PARAMETERS);

		eSimpleFilterConstraintEClass = createEClass(ESIMPLE_FILTER_CONSTRAINT);
		createEAttribute(eSimpleFilterConstraintEClass, ESIMPLE_FILTER_CONSTRAINT__TYPE);
		createEAttribute(eSimpleFilterConstraintEClass, ESIMPLE_FILTER_CONSTRAINT__VALUE);
		createEAttribute(eSimpleFilterConstraintEClass, ESIMPLE_FILTER_CONSTRAINT__OPERATOR);

		eventGeneratorEClass = createEClass(EVENT_GENERATOR);

		egNonRecurringEClass = createEClass(EG_NON_RECURRING);

		egImmediateEClass = createEClass(EG_IMMEDIATE);

		egAbsoluteEClass = createEClass(EG_ABSOLUTE);
		createEAttribute(egAbsoluteEClass, EG_ABSOLUTE__ABSOLUTE_TIME);

		egOffsetEClass = createEClass(EG_OFFSET);
		createEAttribute(egOffsetEClass, EG_OFFSET__OFFSET_TIME);

		egRelativeEClass = createEClass(EG_RELATIVE);
		createEAttribute(egRelativeEClass, EG_RELATIVE__DELAY_TIME);

		egRecurringEClass = createEClass(EG_RECURRING);

		egPeriodicEClass = createEClass(EG_PERIODIC);
		createEAttribute(egPeriodicEClass, EG_PERIODIC__TIME);

		egPatternedEClass = createEClass(EG_PATTERNED);
		createEAttribute(egPatternedEClass, EG_PATTERNED__TIME);
		createEAttribute(egPatternedEClass, EG_PATTERNED__PATTERN);

		egDistributionEClass = createEClass(EG_DISTRIBUTION);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__TIME);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__FUNCTION);

		eventFilterEClass = createEClass(EVENT_FILTER);

		efSimpleEClass = createEClass(EF_SIMPLE);
		createEReference(efSimpleEClass, EF_SIMPLE__CONSTRAINTS);
		createEReference(efSimpleEClass, EF_SIMPLE__SOURCE_EVENT);

		efCompositeEClass = createEClass(EF_COMPOSITE);

		efStatusEventEClass = createEClass(EF_STATUS_EVENT);

		efLogicEClass = createEClass(EF_LOGIC);

		efProcessingEClass = createEClass(EF_PROCESSING);

		efTemporalEClass = createEClass(EF_TEMPORAL);

		efLogicAndEClass = createEClass(EF_LOGIC_AND);

		efLogicOrEClass = createEClass(EF_LOGIC_OR);

		efLogicNotEClass = createEClass(EF_LOGIC_NOT);

		efProcessingMinEClass = createEClass(EF_PROCESSING_MIN);

		efProcessingMaxEClass = createEClass(EF_PROCESSING_MAX);

		efProcessingSumEClass = createEClass(EF_PROCESSING_SUM);

		efProcessingCountEClass = createEClass(EF_PROCESSING_COUNT);

		efProcessingAvgEClass = createEClass(EF_PROCESSING_AVG);

		efProcessingStDevEClass = createEClass(EF_PROCESSING_ST_DEV);

		efTemporalSequenceEClass = createEClass(EF_TEMPORAL_SEQUENCE);

		efChangeEventEClass = createEClass(EF_CHANGE_EVENT);

		efChangeIncreaseEClass = createEClass(EF_CHANGE_INCREASE);

		efChangeDecreaseEClass = createEClass(EF_CHANGE_DECREASE);

		efChangeRemainEClass = createEClass(EF_CHANGE_REMAIN);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		ePeriodicEGEClass.getESuperTypes().add(this.getEventBaseOperator());
		ePatternedEGEClass.getESuperTypes().add(this.getEventBaseOperator());
		eDistributionEGEClass.getESuperTypes().add(this.getEventBaseOperator());
		eventGeneratorEClass.getESuperTypes().add(this.getEventBaseOperator());
		egNonRecurringEClass.getESuperTypes().add(this.getEventGenerator());
		egImmediateEClass.getESuperTypes().add(this.getEGNonRecurring());
		egAbsoluteEClass.getESuperTypes().add(this.getEGNonRecurring());
		egOffsetEClass.getESuperTypes().add(this.getEGNonRecurring());
		egRelativeEClass.getESuperTypes().add(this.getEGNonRecurring());
		egRecurringEClass.getESuperTypes().add(this.getEventGenerator());
		egPeriodicEClass.getESuperTypes().add(this.getEGRecurring());
		egPatternedEClass.getESuperTypes().add(this.getEGRecurring());
		egDistributionEClass.getESuperTypes().add(this.getEGRecurring());
		eventFilterEClass.getESuperTypes().add(this.getEventBaseOperator());
		efSimpleEClass.getESuperTypes().add(this.getEventBaseOperator());
		efCompositeEClass.getESuperTypes().add(this.getEventFilter());
		efStatusEventEClass.getESuperTypes().add(this.getEFComposite());
		efLogicEClass.getESuperTypes().add(this.getEFStatusEvent());
		efProcessingEClass.getESuperTypes().add(this.getEFStatusEvent());
		efTemporalEClass.getESuperTypes().add(this.getEFStatusEvent());
		efLogicAndEClass.getESuperTypes().add(this.getEFLogic());
		efLogicOrEClass.getESuperTypes().add(this.getEFLogic());
		efLogicNotEClass.getESuperTypes().add(this.getEFLogic());
		efProcessingMinEClass.getESuperTypes().add(this.getEFProcessing());
		efProcessingMaxEClass.getESuperTypes().add(this.getEFProcessing());
		efProcessingSumEClass.getESuperTypes().add(this.getEFProcessing());
		efProcessingCountEClass.getESuperTypes().add(this.getEFProcessing());
		efProcessingAvgEClass.getESuperTypes().add(this.getEFProcessing());
		efProcessingStDevEClass.getESuperTypes().add(this.getEFProcessing());
		efTemporalSequenceEClass.getESuperTypes().add(this.getEFTemporal());
		efChangeEventEClass.getESuperTypes().add(this.getEFComposite());
		efChangeIncreaseEClass.getESuperTypes().add(this.getEFChangeEvent());
		efChangeDecreaseEClass.getESuperTypes().add(this.getEFChangeEvent());
		efChangeRemainEClass.getESuperTypes().add(this.getEFChangeEvent());

		// Initialize classes and features; add operations and parameters
		initEClass(eventBaseOperatorEClass, EventBaseOperator.class, "EventBaseOperator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventBaseOperator_Incoming(), this.getESequenceFlow(), this.getESequenceFlow_Target(), "incoming", null, 0, -1, EventBaseOperator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getEventBaseOperator_Outgoing(), this.getESequenceFlow(), this.getESequenceFlow_Source(), "outgoing", null, 0, -1, EventBaseOperator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(eSequenceFlowEClass, ESequenceFlow.class, "ESequenceFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getESequenceFlow_Source(), this.getEventBaseOperator(), this.getEventBaseOperator_Outgoing(), "source", null, 1, 1, ESequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getESequenceFlow_Target(), this.getEventBaseOperator(), this.getEventBaseOperator_Incoming(), "target", null, 1, 1, ESequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(ePeriodicEGEClass, EPeriodicEG.class, "EPeriodicEG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEPeriodicEG_Time(), ecorePackage.getEInt(), "time", null, 1, 1, EPeriodicEG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ePatternedEGEClass, EPatternedEG.class, "EPatternedEG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEPatternedEG_Time(), ecorePackage.getEInt(), "time", null, 1, 1, EPatternedEG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPatternedEG_Pattern(), ecorePackage.getEString(), "pattern", null, 1, 1, EPatternedEG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eDistributionEGEClass, EDistributionEG.class, "EDistributionEG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEDistributionEG_FunctionName(), ecorePackage.getEString(), "functionName", null, 1, 1, EDistributionEG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEDistributionEG_Parameters(), ecorePackage.getEByteArray(), "parameters", null, 1, 1, EDistributionEG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eSimpleFilterConstraintEClass, ESimpleFilterConstraint.class, "ESimpleFilterConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getESimpleFilterConstraint_Type(), ecorePackage.getEString(), "type", null, 1, 1, ESimpleFilterConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getESimpleFilterConstraint_Value(), ecorePackage.getEString(), "value", null, 1, 1, ESimpleFilterConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getESimpleFilterConstraint_Operator(), ecorePackage.getEInt(), "operator", null, 1, 1, ESimpleFilterConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventGeneratorEClass, EventGenerator.class, "EventGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egNonRecurringEClass, EGNonRecurring.class, "EGNonRecurring", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egImmediateEClass, EGImmediate.class, "EGImmediate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egAbsoluteEClass, EGAbsolute.class, "EGAbsolute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGAbsolute_AbsoluteTime(), ecorePackage.getEInt(), "absoluteTime", null, 1, 1, EGAbsolute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egOffsetEClass, EGOffset.class, "EGOffset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGOffset_OffsetTime(), ecorePackage.getEInt(), "offsetTime", null, 1, 1, EGOffset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egRelativeEClass, EGRelative.class, "EGRelative", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGRelative_DelayTime(), ecorePackage.getEInt(), "delayTime", null, 1, 1, EGRelative.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egRecurringEClass, EGRecurring.class, "EGRecurring", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egPeriodicEClass, EGPeriodic.class, "EGPeriodic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGPeriodic_Time(), ecorePackage.getEInt(), "time", null, 0, 1, EGPeriodic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egPatternedEClass, EGPatterned.class, "EGPatterned", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGPatterned_Time(), ecorePackage.getEInt(), "time", null, 0, 1, EGPatterned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGPatterned_Pattern(), ecorePackage.getEInt(), "pattern", null, 0, 1, EGPatterned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egDistributionEClass, EGDistribution.class, "EGDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGDistribution_Time(), ecorePackage.getEInt(), "time", null, 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGDistribution_Function(), ecorePackage.getEInt(), "function", null, 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventFilterEClass, EventFilter.class, "EventFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efSimpleEClass, EFSimple.class, "EFSimple", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEFSimple_Constraints(), this.getESimpleFilterConstraint(), null, "constraints", null, 1, -1, EFSimple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEFSimple_SourceEvent(), this.getEventBaseOperator(), null, "sourceEvent", null, 1, 1, EFSimple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(efCompositeEClass, EFComposite.class, "EFComposite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efStatusEventEClass, EFStatusEvent.class, "EFStatusEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efLogicEClass, EFLogic.class, "EFLogic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingEClass, EFProcessing.class, "EFProcessing", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efTemporalEClass, EFTemporal.class, "EFTemporal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efLogicAndEClass, EFLogicAnd.class, "EFLogicAnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efLogicOrEClass, EFLogicOr.class, "EFLogicOr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efLogicNotEClass, EFLogicNot.class, "EFLogicNot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingMinEClass, EFProcessingMin.class, "EFProcessingMin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingMaxEClass, EFProcessingMax.class, "EFProcessingMax", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingSumEClass, EFProcessingSum.class, "EFProcessingSum", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingCountEClass, EFProcessingCount.class, "EFProcessingCount", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingAvgEClass, EFProcessingAvg.class, "EFProcessingAvg", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingStDevEClass, EFProcessingStDev.class, "EFProcessingStDev", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efTemporalSequenceEClass, EFTemporalSequence.class, "EFTemporalSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efChangeEventEClass, EFChangeEvent.class, "EFChangeEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efChangeIncreaseEClass, EFChangeIncrease.class, "EFChangeIncrease", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efChangeDecreaseEClass, EFChangeDecrease.class, "EFChangeDecrease", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efChangeRemainEClass, EFChangeRemain.class, "EFChangeRemain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //EventbasePackageImpl
