/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGNonRecurring;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootDocumentEClass = null;

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
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage#eNS_URI
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
	public EAttribute getEventBaseOperator_ElementName() {
		return (EAttribute)eventBaseOperatorEClass.getEStructuralFeatures().get(2);
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
	public EClass getEventGenerator() {
		return eventGeneratorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventGenerator_SensorType() {
		return (EAttribute)eventGeneratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventGenerator_Scope() {
		return (EAttribute)eventGeneratorEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getEGRecurring_Repetition() {
		return (EAttribute)egRecurringEClass.getEStructuralFeatures().get(0);
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
	public EAttribute getEGDistribution_PeriodInterval() {
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
	public EAttribute getEGDistribution_Parameters() {
		return (EAttribute)egDistributionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEGDistribution_EvaluationInterval() {
		return (EAttribute)egDistributionEClass.getEStructuralFeatures().get(3);
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
	public EReference getEFSimple_SourceEvent() {
		return (EReference)efSimpleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEFSimple_Constraints() {
		return (EAttribute)efSimpleEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getEFProcessing_WindowSize() {
		return (EAttribute)efProcessingEClass.getEStructuralFeatures().get(0);
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
	public EClass getRootDocument() {
		return rootDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_SequenceFlow() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgImmediate() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgAbsolute() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgOffset() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgRelative() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgPeriodic() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgPatterned() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EgDistribution() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfLogicAnd() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfLogicOr() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfLogicNot() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingMin() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingMax() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingCount() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingSum() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingAvg() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfProcessingStDev() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfTemporalSeq() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfIncrease() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfDecrease() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootDocument_EfRemain() {
		return (EReference)rootDocumentEClass.getEStructuralFeatures().get(20);
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
		createEAttribute(eventBaseOperatorEClass, EVENT_BASE_OPERATOR__ELEMENT_NAME);

		eSequenceFlowEClass = createEClass(ESEQUENCE_FLOW);
		createEReference(eSequenceFlowEClass, ESEQUENCE_FLOW__SOURCE);
		createEReference(eSequenceFlowEClass, ESEQUENCE_FLOW__TARGET);

		eventGeneratorEClass = createEClass(EVENT_GENERATOR);
		createEAttribute(eventGeneratorEClass, EVENT_GENERATOR__SENSOR_TYPE);
		createEAttribute(eventGeneratorEClass, EVENT_GENERATOR__SCOPE);

		egNonRecurringEClass = createEClass(EG_NON_RECURRING);

		egImmediateEClass = createEClass(EG_IMMEDIATE);

		egAbsoluteEClass = createEClass(EG_ABSOLUTE);
		createEAttribute(egAbsoluteEClass, EG_ABSOLUTE__ABSOLUTE_TIME);

		egOffsetEClass = createEClass(EG_OFFSET);
		createEAttribute(egOffsetEClass, EG_OFFSET__OFFSET_TIME);

		egRelativeEClass = createEClass(EG_RELATIVE);
		createEAttribute(egRelativeEClass, EG_RELATIVE__DELAY_TIME);

		egRecurringEClass = createEClass(EG_RECURRING);
		createEAttribute(egRecurringEClass, EG_RECURRING__REPETITION);

		egPeriodicEClass = createEClass(EG_PERIODIC);
		createEAttribute(egPeriodicEClass, EG_PERIODIC__TIME);

		egPatternedEClass = createEClass(EG_PATTERNED);
		createEAttribute(egPatternedEClass, EG_PATTERNED__TIME);
		createEAttribute(egPatternedEClass, EG_PATTERNED__PATTERN);

		egDistributionEClass = createEClass(EG_DISTRIBUTION);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__PERIOD_INTERVAL);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__FUNCTION);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__PARAMETERS);
		createEAttribute(egDistributionEClass, EG_DISTRIBUTION__EVALUATION_INTERVAL);

		eventFilterEClass = createEClass(EVENT_FILTER);

		efSimpleEClass = createEClass(EF_SIMPLE);
		createEReference(efSimpleEClass, EF_SIMPLE__SOURCE_EVENT);
		createEAttribute(efSimpleEClass, EF_SIMPLE__CONSTRAINTS);

		efCompositeEClass = createEClass(EF_COMPOSITE);

		efStatusEventEClass = createEClass(EF_STATUS_EVENT);

		efLogicEClass = createEClass(EF_LOGIC);

		efProcessingEClass = createEClass(EF_PROCESSING);
		createEAttribute(efProcessingEClass, EF_PROCESSING__WINDOW_SIZE);

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

		rootDocumentEClass = createEClass(ROOT_DOCUMENT);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__SEQUENCE_FLOW);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_IMMEDIATE);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_ABSOLUTE);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_OFFSET);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_RELATIVE);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_PERIODIC);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_PATTERNED);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EG_DISTRIBUTION);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_LOGIC_AND);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_LOGIC_OR);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_LOGIC_NOT);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_MIN);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_MAX);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_COUNT);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_SUM);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_AVG);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_PROCESSING_ST_DEV);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_TEMPORAL_SEQ);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_INCREASE);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_DECREASE);
		createEReference(rootDocumentEClass, ROOT_DOCUMENT__EF_REMAIN);
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
		efSimpleEClass.getESuperTypes().add(this.getEventFilter());
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
		initEAttribute(getEventBaseOperator_ElementName(), ecorePackage.getEString(), "elementName", null, 1, 1, EventBaseOperator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eSequenceFlowEClass, ESequenceFlow.class, "ESequenceFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getESequenceFlow_Source(), this.getEventBaseOperator(), this.getEventBaseOperator_Outgoing(), "source", null, 1, 1, ESequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getESequenceFlow_Target(), this.getEventBaseOperator(), this.getEventBaseOperator_Incoming(), "target", null, 1, 1, ESequenceFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(eventGeneratorEClass, EventGenerator.class, "EventGenerator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventGenerator_SensorType(), ecorePackage.getEString(), "sensorType", null, 0, 1, EventGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventGenerator_Scope(), ecorePackage.getEString(), "scope", "WORLD", 0, 1, EventGenerator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egNonRecurringEClass, EGNonRecurring.class, "EGNonRecurring", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egImmediateEClass, EGImmediate.class, "EGImmediate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(egAbsoluteEClass, EGAbsolute.class, "EGAbsolute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGAbsolute_AbsoluteTime(), ecorePackage.getEString(), "absoluteTime", null, 1, 1, EGAbsolute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egOffsetEClass, EGOffset.class, "EGOffset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGOffset_OffsetTime(), ecorePackage.getEString(), "offsetTime", "01:00", 1, 1, EGOffset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egRelativeEClass, EGRelative.class, "EGRelative", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGRelative_DelayTime(), ecorePackage.getEString(), "delayTime", "01:00", 1, 1, EGRelative.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egRecurringEClass, EGRecurring.class, "EGRecurring", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGRecurring_Repetition(), ecorePackage.getEInt(), "repetition", "0", 0, 1, EGRecurring.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egPeriodicEClass, EGPeriodic.class, "EGPeriodic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGPeriodic_Time(), ecorePackage.getEString(), "time", "01:00", 0, 1, EGPeriodic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egPatternedEClass, EGPatterned.class, "EGPatterned", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGPatterned_Time(), ecorePackage.getEString(), "time", "01:00", 0, 1, EGPatterned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGPatterned_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, EGPatterned.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(egDistributionEClass, EGDistribution.class, "EGDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEGDistribution_PeriodInterval(), ecorePackage.getEString(), "periodInterval", "01:00", 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGDistribution_Function(), ecorePackage.getEString(), "function", null, 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGDistribution_Parameters(), ecorePackage.getEString(), "parameters", null, 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEGDistribution_EvaluationInterval(), ecorePackage.getEString(), "evaluationInterval", "01:00", 0, 1, EGDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventFilterEClass, EventFilter.class, "EventFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efSimpleEClass, EFSimple.class, "EFSimple", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEFSimple_SourceEvent(), this.getEventBaseOperator(), null, "sourceEvent", null, 1, 1, EFSimple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEFSimple_Constraints(), ecorePackage.getEString(), "constraints", null, 0, 1, EFSimple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(efCompositeEClass, EFComposite.class, "EFComposite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efStatusEventEClass, EFStatusEvent.class, "EFStatusEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efLogicEClass, EFLogic.class, "EFLogic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(efProcessingEClass, EFProcessing.class, "EFProcessing", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEFProcessing_WindowSize(), ecorePackage.getEInt(), "windowSize", null, 0, 1, EFProcessing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(rootDocumentEClass, RootDocument.class, "RootDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRootDocument_SequenceFlow(), this.getESequenceFlow(), null, "sequenceFlow", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgImmediate(), this.getEGImmediate(), null, "egImmediate", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgAbsolute(), this.getEGAbsolute(), null, "egAbsolute", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgOffset(), this.getEGOffset(), null, "egOffset", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgRelative(), this.getEGRelative(), null, "egRelative", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgPeriodic(), this.getEGPeriodic(), null, "egPeriodic", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgPatterned(), this.getEGPatterned(), null, "egPatterned", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EgDistribution(), this.getEGDistribution(), null, "egDistribution", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfLogicAnd(), this.getEFLogicAnd(), null, "efLogicAnd", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfLogicOr(), this.getEFLogicOr(), null, "efLogicOr", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfLogicNot(), this.getEFLogicNot(), null, "efLogicNot", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingMin(), this.getEFProcessingMin(), null, "efProcessingMin", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingMax(), this.getEFProcessingMax(), null, "efProcessingMax", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingCount(), this.getEFProcessingCount(), null, "efProcessingCount", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingSum(), this.getEFProcessingSum(), null, "efProcessingSum", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingAvg(), this.getEFProcessingAvg(), null, "efProcessingAvg", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfProcessingStDev(), this.getEFProcessingStDev(), null, "efProcessingStDev", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfTemporalSeq(), this.getEFTemporalSequence(), null, "efTemporalSeq", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfIncrease(), this.getEFChangeIncrease(), null, "efIncrease", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfDecrease(), this.getEFChangeDecrease(), null, "efDecrease", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRootDocument_EfRemain(), this.getEFChangeRemain(), null, "efRemain", null, 0, -1, RootDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EventbasePackageImpl
