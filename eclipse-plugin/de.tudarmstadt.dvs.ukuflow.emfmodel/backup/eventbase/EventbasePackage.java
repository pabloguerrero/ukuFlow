/**
 */
package eventbase;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eventbase.EventbaseFactory
 * @model kind="package"
 * @generated
 */
public interface EventbasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "eventbase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://de/tudarmstadt/dvs/ukuflow/eventbase";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "de.tudarmstadt.dvs.ukuflow.eventbase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventbasePackage eINSTANCE = eventbase.impl.EventbasePackageImpl.init();

	/**
	 * The meta object id for the '{@link eventbase.impl.EventBaseOperatorImpl <em>Event Base Operator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EventBaseOperatorImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEventBaseOperator()
	 * @generated
	 */
	int EVENT_BASE_OPERATOR = 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BASE_OPERATOR__INCOMING = 0;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BASE_OPERATOR__OUTGOING = 1;

	/**
	 * The number of structural features of the '<em>Event Base Operator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BASE_OPERATOR_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.ESequenceFlowImpl <em>ESequence Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.ESequenceFlowImpl
	 * @see eventbase.impl.EventbasePackageImpl#getESequenceFlow()
	 * @generated
	 */
	int ESEQUENCE_FLOW = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESEQUENCE_FLOW__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESEQUENCE_FLOW__TARGET = 1;

	/**
	 * The number of structural features of the '<em>ESequence Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESEQUENCE_FLOW_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.EPeriodicEGImpl <em>EPeriodic EG</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EPeriodicEGImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEPeriodicEG()
	 * @generated
	 */
	int EPERIODIC_EG = 2;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPERIODIC_EG__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPERIODIC_EG__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPERIODIC_EG__TIME = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EPeriodic EG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPERIODIC_EG_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link eventbase.impl.EPatternedEGImpl <em>EPatterned EG</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EPatternedEGImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEPatternedEG()
	 * @generated
	 */
	int EPATTERNED_EG = 3;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPATTERNED_EG__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPATTERNED_EG__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPATTERNED_EG__TIME = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPATTERNED_EG__PATTERN = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EPatterned EG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPATTERNED_EG_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.EDistributionEGImpl <em>EDistribution EG</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EDistributionEGImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEDistributionEG()
	 * @generated
	 */
	int EDISTRIBUTION_EG = 4;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDISTRIBUTION_EG__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDISTRIBUTION_EG__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The feature id for the '<em><b>Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDISTRIBUTION_EG__FUNCTION_NAME = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDISTRIBUTION_EG__PARAMETERS = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EDistribution EG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDISTRIBUTION_EG_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.ESimpleFilterConstraintImpl <em>ESimple Filter Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.ESimpleFilterConstraintImpl
	 * @see eventbase.impl.EventbasePackageImpl#getESimpleFilterConstraint()
	 * @generated
	 */
	int ESIMPLE_FILTER_CONSTRAINT = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_FILTER_CONSTRAINT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_FILTER_CONSTRAINT__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_FILTER_CONSTRAINT__OPERATOR = 2;

	/**
	 * The number of structural features of the '<em>ESimple Filter Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_FILTER_CONSTRAINT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link eventbase.impl.EventGeneratorImpl <em>Event Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EventGeneratorImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEventGenerator()
	 * @generated
	 */
	int EVENT_GENERATOR = 6;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The number of structural features of the '<em>Event Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGNonRecurringImpl <em>EG Non Recurring</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGNonRecurringImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGNonRecurring()
	 * @generated
	 */
	int EG_NON_RECURRING = 7;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING__INCOMING = EVENT_GENERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING__OUTGOING = EVENT_GENERATOR__OUTGOING;

	/**
	 * The number of structural features of the '<em>EG Non Recurring</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING_FEATURE_COUNT = EVENT_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGImmediateImpl <em>EG Immediate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGImmediateImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGImmediate()
	 * @generated
	 */
	int EG_IMMEDIATE = 8;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE__INCOMING = EG_NON_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE__OUTGOING = EG_NON_RECURRING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EG Immediate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE_FEATURE_COUNT = EG_NON_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGAbsoluteImpl <em>EG Absolute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGAbsoluteImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGAbsolute()
	 * @generated
	 */
	int EG_ABSOLUTE = 9;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__INCOMING = EG_NON_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__OUTGOING = EG_NON_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Absolute Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__ABSOLUTE_TIME = EG_NON_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EG Absolute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE_FEATURE_COUNT = EG_NON_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGOffsetImpl <em>EG Offset</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGOffsetImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGOffset()
	 * @generated
	 */
	int EG_OFFSET = 10;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__INCOMING = EG_NON_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__OUTGOING = EG_NON_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Offset Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__OFFSET_TIME = EG_NON_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EG Offset</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET_FEATURE_COUNT = EG_NON_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGRelativeImpl <em>EG Relative</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGRelativeImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGRelative()
	 * @generated
	 */
	int EG_RELATIVE = 11;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__INCOMING = EG_NON_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__OUTGOING = EG_NON_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Delay Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__DELAY_TIME = EG_NON_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EG Relative</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE_FEATURE_COUNT = EG_NON_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGRecurringImpl <em>EG Recurring</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGRecurringImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGRecurring()
	 * @generated
	 */
	int EG_RECURRING = 12;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__INCOMING = EVENT_GENERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__OUTGOING = EVENT_GENERATOR__OUTGOING;

	/**
	 * The number of structural features of the '<em>EG Recurring</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING_FEATURE_COUNT = EVENT_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGPeriodicImpl <em>EG Periodic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGPeriodicImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGPeriodic()
	 * @generated
	 */
	int EG_PERIODIC = 13;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__INCOMING = EG_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__OUTGOING = EG_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__TIME = EG_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EG Periodic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC_FEATURE_COUNT = EG_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGPatternedImpl <em>EG Patterned</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGPatternedImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGPatterned()
	 * @generated
	 */
	int EG_PATTERNED = 14;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__INCOMING = EG_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__OUTGOING = EG_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__TIME = EG_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__PATTERN = EG_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EG Patterned</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED_FEATURE_COUNT = EG_RECURRING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.EGDistributionImpl <em>EG Distribution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EGDistributionImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEGDistribution()
	 * @generated
	 */
	int EG_DISTRIBUTION = 15;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__INCOMING = EG_RECURRING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__OUTGOING = EG_RECURRING__OUTGOING;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__TIME = EG_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__FUNCTION = EG_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EG Distribution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION_FEATURE_COUNT = EG_RECURRING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.EventFilterImpl <em>Event Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EventFilterImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEventFilter()
	 * @generated
	 */
	int EVENT_FILTER = 16;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FILTER__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FILTER__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The number of structural features of the '<em>Event Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FILTER_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFSimpleImpl <em>EF Simple</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFSimpleImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFSimple()
	 * @generated
	 */
	int EF_SIMPLE = 17;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__CONSTRAINTS = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__SOURCE_EVENT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EF Simple</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFCompositeImpl <em>EF Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFCompositeImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFComposite()
	 * @generated
	 */
	int EF_COMPOSITE = 18;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_COMPOSITE__INCOMING = EVENT_FILTER__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_COMPOSITE__OUTGOING = EVENT_FILTER__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_COMPOSITE_FEATURE_COUNT = EVENT_FILTER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFStatusEventImpl <em>EF Status Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFStatusEventImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFStatusEvent()
	 * @generated
	 */
	int EF_STATUS_EVENT = 19;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_STATUS_EVENT__INCOMING = EF_COMPOSITE__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_STATUS_EVENT__OUTGOING = EF_COMPOSITE__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Status Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_STATUS_EVENT_FEATURE_COUNT = EF_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFLogicImpl <em>EF Logic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFLogicImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFLogic()
	 * @generated
	 */
	int EF_LOGIC = 20;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC__INCOMING = EF_STATUS_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC__OUTGOING = EF_STATUS_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Logic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingImpl <em>EF Processing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessing()
	 * @generated
	 */
	int EF_PROCESSING = 21;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING__INCOMING = EF_STATUS_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING__OUTGOING = EF_STATUS_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFTemporalImpl <em>EF Temporal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFTemporalImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFTemporal()
	 * @generated
	 */
	int EF_TEMPORAL = 22;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL__INCOMING = EF_STATUS_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL__OUTGOING = EF_STATUS_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Temporal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFLogicAndImpl <em>EF Logic And</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFLogicAndImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFLogicAnd()
	 * @generated
	 */
	int EF_LOGIC_AND = 23;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_AND__INCOMING = EF_LOGIC__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_AND__OUTGOING = EF_LOGIC__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Logic And</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_AND_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFLogicOrImpl <em>EF Logic Or</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFLogicOrImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFLogicOr()
	 * @generated
	 */
	int EF_LOGIC_OR = 24;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_OR__INCOMING = EF_LOGIC__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_OR__OUTGOING = EF_LOGIC__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Logic Or</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_OR_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFLogicNotImpl <em>EF Logic Not</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFLogicNotImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFLogicNot()
	 * @generated
	 */
	int EF_LOGIC_NOT = 25;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_NOT__INCOMING = EF_LOGIC__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_NOT__OUTGOING = EF_LOGIC__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Logic Not</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_NOT_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingMinImpl <em>EF Processing Min</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingMinImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingMin()
	 * @generated
	 */
	int EF_PROCESSING_MIN = 26;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing Min</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingMaxImpl <em>EF Processing Max</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingMaxImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingMax()
	 * @generated
	 */
	int EF_PROCESSING_MAX = 27;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing Max</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingSumImpl <em>EF Processing Sum</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingSumImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingSum()
	 * @generated
	 */
	int EF_PROCESSING_SUM = 28;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing Sum</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingCountImpl <em>EF Processing Count</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingCountImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingCount()
	 * @generated
	 */
	int EF_PROCESSING_COUNT = 29;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingAvgImpl <em>EF Processing Avg</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingAvgImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingAvg()
	 * @generated
	 */
	int EF_PROCESSING_AVG = 30;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing Avg</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFProcessingStDevImpl <em>EF Processing St Dev</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFProcessingStDevImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingStDev()
	 * @generated
	 */
	int EF_PROCESSING_ST_DEV = 31;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV__INCOMING = EF_PROCESSING__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV__OUTGOING = EF_PROCESSING__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Processing St Dev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFTemporalSequenceImpl <em>EF Temporal Sequence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFTemporalSequenceImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFTemporalSequence()
	 * @generated
	 */
	int EF_TEMPORAL_SEQUENCE = 32;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_SEQUENCE__INCOMING = EF_TEMPORAL__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_SEQUENCE__OUTGOING = EF_TEMPORAL__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Temporal Sequence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_SEQUENCE_FEATURE_COUNT = EF_TEMPORAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFChangeEventImpl <em>EF Change Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFChangeEventImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFChangeEvent()
	 * @generated
	 */
	int EF_CHANGE_EVENT = 33;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT__INCOMING = EF_COMPOSITE__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT__OUTGOING = EF_COMPOSITE__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Change Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT_FEATURE_COUNT = EF_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFChangeIncreaseImpl <em>EF Change Increase</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFChangeIncreaseImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFChangeIncrease()
	 * @generated
	 */
	int EF_CHANGE_INCREASE = 34;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE__INCOMING = EF_CHANGE_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE__OUTGOING = EF_CHANGE_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Change Increase</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFChangeDecreaseImpl <em>EF Change Decrease</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFChangeDecreaseImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFChangeDecrease()
	 * @generated
	 */
	int EF_CHANGE_DECREASE = 35;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE__INCOMING = EF_CHANGE_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE__OUTGOING = EF_CHANGE_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Change Decrease</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link eventbase.impl.EFChangeRemainImpl <em>EF Change Remain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.EFChangeRemainImpl
	 * @see eventbase.impl.EventbasePackageImpl#getEFChangeRemain()
	 * @generated
	 */
	int EF_CHANGE_REMAIN = 36;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN__INCOMING = EF_CHANGE_EVENT__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN__OUTGOING = EF_CHANGE_EVENT__OUTGOING;

	/**
	 * The number of structural features of the '<em>EF Change Remain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link eventbase.EventBaseOperator <em>Event Base Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Base Operator</em>'.
	 * @see eventbase.EventBaseOperator
	 * @generated
	 */
	EClass getEventBaseOperator();

	/**
	 * Returns the meta object for the reference list '{@link eventbase.EventBaseOperator#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see eventbase.EventBaseOperator#getIncoming()
	 * @see #getEventBaseOperator()
	 * @generated
	 */
	EReference getEventBaseOperator_Incoming();

	/**
	 * Returns the meta object for the reference list '{@link eventbase.EventBaseOperator#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see eventbase.EventBaseOperator#getOutgoing()
	 * @see #getEventBaseOperator()
	 * @generated
	 */
	EReference getEventBaseOperator_Outgoing();

	/**
	 * Returns the meta object for class '{@link eventbase.ESequenceFlow <em>ESequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ESequence Flow</em>'.
	 * @see eventbase.ESequenceFlow
	 * @generated
	 */
	EClass getESequenceFlow();

	/**
	 * Returns the meta object for the reference '{@link eventbase.ESequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see eventbase.ESequenceFlow#getSource()
	 * @see #getESequenceFlow()
	 * @generated
	 */
	EReference getESequenceFlow_Source();

	/**
	 * Returns the meta object for the reference '{@link eventbase.ESequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see eventbase.ESequenceFlow#getTarget()
	 * @see #getESequenceFlow()
	 * @generated
	 */
	EReference getESequenceFlow_Target();

	/**
	 * Returns the meta object for class '{@link eventbase.EPeriodicEG <em>EPeriodic EG</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EPeriodic EG</em>'.
	 * @see eventbase.EPeriodicEG
	 * @generated
	 */
	EClass getEPeriodicEG();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EPeriodicEG#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see eventbase.EPeriodicEG#getTime()
	 * @see #getEPeriodicEG()
	 * @generated
	 */
	EAttribute getEPeriodicEG_Time();

	/**
	 * Returns the meta object for class '{@link eventbase.EPatternedEG <em>EPatterned EG</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EPatterned EG</em>'.
	 * @see eventbase.EPatternedEG
	 * @generated
	 */
	EClass getEPatternedEG();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EPatternedEG#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see eventbase.EPatternedEG#getTime()
	 * @see #getEPatternedEG()
	 * @generated
	 */
	EAttribute getEPatternedEG_Time();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EPatternedEG#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see eventbase.EPatternedEG#getPattern()
	 * @see #getEPatternedEG()
	 * @generated
	 */
	EAttribute getEPatternedEG_Pattern();

	/**
	 * Returns the meta object for class '{@link eventbase.EDistributionEG <em>EDistribution EG</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EDistribution EG</em>'.
	 * @see eventbase.EDistributionEG
	 * @generated
	 */
	EClass getEDistributionEG();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EDistributionEG#getFunctionName <em>Function Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Name</em>'.
	 * @see eventbase.EDistributionEG#getFunctionName()
	 * @see #getEDistributionEG()
	 * @generated
	 */
	EAttribute getEDistributionEG_FunctionName();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EDistributionEG#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see eventbase.EDistributionEG#getParameters()
	 * @see #getEDistributionEG()
	 * @generated
	 */
	EAttribute getEDistributionEG_Parameters();

	/**
	 * Returns the meta object for class '{@link eventbase.ESimpleFilterConstraint <em>ESimple Filter Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ESimple Filter Constraint</em>'.
	 * @see eventbase.ESimpleFilterConstraint
	 * @generated
	 */
	EClass getESimpleFilterConstraint();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.ESimpleFilterConstraint#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eventbase.ESimpleFilterConstraint#getType()
	 * @see #getESimpleFilterConstraint()
	 * @generated
	 */
	EAttribute getESimpleFilterConstraint_Type();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.ESimpleFilterConstraint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eventbase.ESimpleFilterConstraint#getValue()
	 * @see #getESimpleFilterConstraint()
	 * @generated
	 */
	EAttribute getESimpleFilterConstraint_Value();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.ESimpleFilterConstraint#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eventbase.ESimpleFilterConstraint#getOperator()
	 * @see #getESimpleFilterConstraint()
	 * @generated
	 */
	EAttribute getESimpleFilterConstraint_Operator();

	/**
	 * Returns the meta object for class '{@link eventbase.EventGenerator <em>Event Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Generator</em>'.
	 * @see eventbase.EventGenerator
	 * @generated
	 */
	EClass getEventGenerator();

	/**
	 * Returns the meta object for class '{@link eventbase.EGNonRecurring <em>EG Non Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Non Recurring</em>'.
	 * @see eventbase.EGNonRecurring
	 * @generated
	 */
	EClass getEGNonRecurring();

	/**
	 * Returns the meta object for class '{@link eventbase.EGImmediate <em>EG Immediate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Immediate</em>'.
	 * @see eventbase.EGImmediate
	 * @generated
	 */
	EClass getEGImmediate();

	/**
	 * Returns the meta object for class '{@link eventbase.EGAbsolute <em>EG Absolute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Absolute</em>'.
	 * @see eventbase.EGAbsolute
	 * @generated
	 */
	EClass getEGAbsolute();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGAbsolute#getAbsoluteTime <em>Absolute Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Absolute Time</em>'.
	 * @see eventbase.EGAbsolute#getAbsoluteTime()
	 * @see #getEGAbsolute()
	 * @generated
	 */
	EAttribute getEGAbsolute_AbsoluteTime();

	/**
	 * Returns the meta object for class '{@link eventbase.EGOffset <em>EG Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Offset</em>'.
	 * @see eventbase.EGOffset
	 * @generated
	 */
	EClass getEGOffset();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGOffset#getOffsetTime <em>Offset Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset Time</em>'.
	 * @see eventbase.EGOffset#getOffsetTime()
	 * @see #getEGOffset()
	 * @generated
	 */
	EAttribute getEGOffset_OffsetTime();

	/**
	 * Returns the meta object for class '{@link eventbase.EGRelative <em>EG Relative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Relative</em>'.
	 * @see eventbase.EGRelative
	 * @generated
	 */
	EClass getEGRelative();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGRelative#getDelayTime <em>Delay Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delay Time</em>'.
	 * @see eventbase.EGRelative#getDelayTime()
	 * @see #getEGRelative()
	 * @generated
	 */
	EAttribute getEGRelative_DelayTime();

	/**
	 * Returns the meta object for class '{@link eventbase.EGRecurring <em>EG Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Recurring</em>'.
	 * @see eventbase.EGRecurring
	 * @generated
	 */
	EClass getEGRecurring();

	/**
	 * Returns the meta object for class '{@link eventbase.EGPeriodic <em>EG Periodic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Periodic</em>'.
	 * @see eventbase.EGPeriodic
	 * @generated
	 */
	EClass getEGPeriodic();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGPeriodic#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see eventbase.EGPeriodic#getTime()
	 * @see #getEGPeriodic()
	 * @generated
	 */
	EAttribute getEGPeriodic_Time();

	/**
	 * Returns the meta object for class '{@link eventbase.EGPatterned <em>EG Patterned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Patterned</em>'.
	 * @see eventbase.EGPatterned
	 * @generated
	 */
	EClass getEGPatterned();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGPatterned#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see eventbase.EGPatterned#getTime()
	 * @see #getEGPatterned()
	 * @generated
	 */
	EAttribute getEGPatterned_Time();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGPatterned#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see eventbase.EGPatterned#getPattern()
	 * @see #getEGPatterned()
	 * @generated
	 */
	EAttribute getEGPatterned_Pattern();

	/**
	 * Returns the meta object for class '{@link eventbase.EGDistribution <em>EG Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Distribution</em>'.
	 * @see eventbase.EGDistribution
	 * @generated
	 */
	EClass getEGDistribution();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGDistribution#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see eventbase.EGDistribution#getTime()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_Time();

	/**
	 * Returns the meta object for the attribute '{@link eventbase.EGDistribution#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see eventbase.EGDistribution#getFunction()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_Function();

	/**
	 * Returns the meta object for class '{@link eventbase.EventFilter <em>Event Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Filter</em>'.
	 * @see eventbase.EventFilter
	 * @generated
	 */
	EClass getEventFilter();

	/**
	 * Returns the meta object for class '{@link eventbase.EFSimple <em>EF Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Simple</em>'.
	 * @see eventbase.EFSimple
	 * @generated
	 */
	EClass getEFSimple();

	/**
	 * Returns the meta object for the reference list '{@link eventbase.EFSimple#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraints</em>'.
	 * @see eventbase.EFSimple#getConstraints()
	 * @see #getEFSimple()
	 * @generated
	 */
	EReference getEFSimple_Constraints();

	/**
	 * Returns the meta object for the reference '{@link eventbase.EFSimple#getSourceEvent <em>Source Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Event</em>'.
	 * @see eventbase.EFSimple#getSourceEvent()
	 * @see #getEFSimple()
	 * @generated
	 */
	EReference getEFSimple_SourceEvent();

	/**
	 * Returns the meta object for class '{@link eventbase.EFComposite <em>EF Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Composite</em>'.
	 * @see eventbase.EFComposite
	 * @generated
	 */
	EClass getEFComposite();

	/**
	 * Returns the meta object for class '{@link eventbase.EFStatusEvent <em>EF Status Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Status Event</em>'.
	 * @see eventbase.EFStatusEvent
	 * @generated
	 */
	EClass getEFStatusEvent();

	/**
	 * Returns the meta object for class '{@link eventbase.EFLogic <em>EF Logic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic</em>'.
	 * @see eventbase.EFLogic
	 * @generated
	 */
	EClass getEFLogic();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessing <em>EF Processing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing</em>'.
	 * @see eventbase.EFProcessing
	 * @generated
	 */
	EClass getEFProcessing();

	/**
	 * Returns the meta object for class '{@link eventbase.EFTemporal <em>EF Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Temporal</em>'.
	 * @see eventbase.EFTemporal
	 * @generated
	 */
	EClass getEFTemporal();

	/**
	 * Returns the meta object for class '{@link eventbase.EFLogicAnd <em>EF Logic And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic And</em>'.
	 * @see eventbase.EFLogicAnd
	 * @generated
	 */
	EClass getEFLogicAnd();

	/**
	 * Returns the meta object for class '{@link eventbase.EFLogicOr <em>EF Logic Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic Or</em>'.
	 * @see eventbase.EFLogicOr
	 * @generated
	 */
	EClass getEFLogicOr();

	/**
	 * Returns the meta object for class '{@link eventbase.EFLogicNot <em>EF Logic Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic Not</em>'.
	 * @see eventbase.EFLogicNot
	 * @generated
	 */
	EClass getEFLogicNot();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingMin <em>EF Processing Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Min</em>'.
	 * @see eventbase.EFProcessingMin
	 * @generated
	 */
	EClass getEFProcessingMin();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingMax <em>EF Processing Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Max</em>'.
	 * @see eventbase.EFProcessingMax
	 * @generated
	 */
	EClass getEFProcessingMax();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingSum <em>EF Processing Sum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Sum</em>'.
	 * @see eventbase.EFProcessingSum
	 * @generated
	 */
	EClass getEFProcessingSum();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingCount <em>EF Processing Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Count</em>'.
	 * @see eventbase.EFProcessingCount
	 * @generated
	 */
	EClass getEFProcessingCount();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingAvg <em>EF Processing Avg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Avg</em>'.
	 * @see eventbase.EFProcessingAvg
	 * @generated
	 */
	EClass getEFProcessingAvg();

	/**
	 * Returns the meta object for class '{@link eventbase.EFProcessingStDev <em>EF Processing St Dev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing St Dev</em>'.
	 * @see eventbase.EFProcessingStDev
	 * @generated
	 */
	EClass getEFProcessingStDev();

	/**
	 * Returns the meta object for class '{@link eventbase.EFTemporalSequence <em>EF Temporal Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Temporal Sequence</em>'.
	 * @see eventbase.EFTemporalSequence
	 * @generated
	 */
	EClass getEFTemporalSequence();

	/**
	 * Returns the meta object for class '{@link eventbase.EFChangeEvent <em>EF Change Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Event</em>'.
	 * @see eventbase.EFChangeEvent
	 * @generated
	 */
	EClass getEFChangeEvent();

	/**
	 * Returns the meta object for class '{@link eventbase.EFChangeIncrease <em>EF Change Increase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Increase</em>'.
	 * @see eventbase.EFChangeIncrease
	 * @generated
	 */
	EClass getEFChangeIncrease();

	/**
	 * Returns the meta object for class '{@link eventbase.EFChangeDecrease <em>EF Change Decrease</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Decrease</em>'.
	 * @see eventbase.EFChangeDecrease
	 * @generated
	 */
	EClass getEFChangeDecrease();

	/**
	 * Returns the meta object for class '{@link eventbase.EFChangeRemain <em>EF Change Remain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Remain</em>'.
	 * @see eventbase.EFChangeRemain
	 * @generated
	 */
	EClass getEFChangeRemain();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EventbaseFactory getEventbaseFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link eventbase.impl.EventBaseOperatorImpl <em>Event Base Operator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EventBaseOperatorImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEventBaseOperator()
		 * @generated
		 */
		EClass EVENT_BASE_OPERATOR = eINSTANCE.getEventBaseOperator();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_BASE_OPERATOR__INCOMING = eINSTANCE.getEventBaseOperator_Incoming();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_BASE_OPERATOR__OUTGOING = eINSTANCE.getEventBaseOperator_Outgoing();

		/**
		 * The meta object literal for the '{@link eventbase.impl.ESequenceFlowImpl <em>ESequence Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.ESequenceFlowImpl
		 * @see eventbase.impl.EventbasePackageImpl#getESequenceFlow()
		 * @generated
		 */
		EClass ESEQUENCE_FLOW = eINSTANCE.getESequenceFlow();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESEQUENCE_FLOW__SOURCE = eINSTANCE.getESequenceFlow_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESEQUENCE_FLOW__TARGET = eINSTANCE.getESequenceFlow_Target();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EPeriodicEGImpl <em>EPeriodic EG</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EPeriodicEGImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEPeriodicEG()
		 * @generated
		 */
		EClass EPERIODIC_EG = eINSTANCE.getEPeriodicEG();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPERIODIC_EG__TIME = eINSTANCE.getEPeriodicEG_Time();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EPatternedEGImpl <em>EPatterned EG</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EPatternedEGImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEPatternedEG()
		 * @generated
		 */
		EClass EPATTERNED_EG = eINSTANCE.getEPatternedEG();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPATTERNED_EG__TIME = eINSTANCE.getEPatternedEG_Time();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPATTERNED_EG__PATTERN = eINSTANCE.getEPatternedEG_Pattern();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EDistributionEGImpl <em>EDistribution EG</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EDistributionEGImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEDistributionEG()
		 * @generated
		 */
		EClass EDISTRIBUTION_EG = eINSTANCE.getEDistributionEG();

		/**
		 * The meta object literal for the '<em><b>Function Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDISTRIBUTION_EG__FUNCTION_NAME = eINSTANCE.getEDistributionEG_FunctionName();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDISTRIBUTION_EG__PARAMETERS = eINSTANCE.getEDistributionEG_Parameters();

		/**
		 * The meta object literal for the '{@link eventbase.impl.ESimpleFilterConstraintImpl <em>ESimple Filter Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.ESimpleFilterConstraintImpl
		 * @see eventbase.impl.EventbasePackageImpl#getESimpleFilterConstraint()
		 * @generated
		 */
		EClass ESIMPLE_FILTER_CONSTRAINT = eINSTANCE.getESimpleFilterConstraint();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESIMPLE_FILTER_CONSTRAINT__TYPE = eINSTANCE.getESimpleFilterConstraint_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESIMPLE_FILTER_CONSTRAINT__VALUE = eINSTANCE.getESimpleFilterConstraint_Value();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESIMPLE_FILTER_CONSTRAINT__OPERATOR = eINSTANCE.getESimpleFilterConstraint_Operator();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EventGeneratorImpl <em>Event Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EventGeneratorImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEventGenerator()
		 * @generated
		 */
		EClass EVENT_GENERATOR = eINSTANCE.getEventGenerator();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGNonRecurringImpl <em>EG Non Recurring</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGNonRecurringImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGNonRecurring()
		 * @generated
		 */
		EClass EG_NON_RECURRING = eINSTANCE.getEGNonRecurring();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGImmediateImpl <em>EG Immediate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGImmediateImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGImmediate()
		 * @generated
		 */
		EClass EG_IMMEDIATE = eINSTANCE.getEGImmediate();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGAbsoluteImpl <em>EG Absolute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGAbsoluteImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGAbsolute()
		 * @generated
		 */
		EClass EG_ABSOLUTE = eINSTANCE.getEGAbsolute();

		/**
		 * The meta object literal for the '<em><b>Absolute Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_ABSOLUTE__ABSOLUTE_TIME = eINSTANCE.getEGAbsolute_AbsoluteTime();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGOffsetImpl <em>EG Offset</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGOffsetImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGOffset()
		 * @generated
		 */
		EClass EG_OFFSET = eINSTANCE.getEGOffset();

		/**
		 * The meta object literal for the '<em><b>Offset Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_OFFSET__OFFSET_TIME = eINSTANCE.getEGOffset_OffsetTime();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGRelativeImpl <em>EG Relative</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGRelativeImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGRelative()
		 * @generated
		 */
		EClass EG_RELATIVE = eINSTANCE.getEGRelative();

		/**
		 * The meta object literal for the '<em><b>Delay Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_RELATIVE__DELAY_TIME = eINSTANCE.getEGRelative_DelayTime();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGRecurringImpl <em>EG Recurring</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGRecurringImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGRecurring()
		 * @generated
		 */
		EClass EG_RECURRING = eINSTANCE.getEGRecurring();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGPeriodicImpl <em>EG Periodic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGPeriodicImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGPeriodic()
		 * @generated
		 */
		EClass EG_PERIODIC = eINSTANCE.getEGPeriodic();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_PERIODIC__TIME = eINSTANCE.getEGPeriodic_Time();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGPatternedImpl <em>EG Patterned</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGPatternedImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGPatterned()
		 * @generated
		 */
		EClass EG_PATTERNED = eINSTANCE.getEGPatterned();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_PATTERNED__TIME = eINSTANCE.getEGPatterned_Time();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_PATTERNED__PATTERN = eINSTANCE.getEGPatterned_Pattern();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EGDistributionImpl <em>EG Distribution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EGDistributionImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEGDistribution()
		 * @generated
		 */
		EClass EG_DISTRIBUTION = eINSTANCE.getEGDistribution();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__TIME = eINSTANCE.getEGDistribution_Time();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__FUNCTION = eINSTANCE.getEGDistribution_Function();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EventFilterImpl <em>Event Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EventFilterImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEventFilter()
		 * @generated
		 */
		EClass EVENT_FILTER = eINSTANCE.getEventFilter();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFSimpleImpl <em>EF Simple</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFSimpleImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFSimple()
		 * @generated
		 */
		EClass EF_SIMPLE = eINSTANCE.getEFSimple();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EF_SIMPLE__CONSTRAINTS = eINSTANCE.getEFSimple_Constraints();

		/**
		 * The meta object literal for the '<em><b>Source Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EF_SIMPLE__SOURCE_EVENT = eINSTANCE.getEFSimple_SourceEvent();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFCompositeImpl <em>EF Composite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFCompositeImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFComposite()
		 * @generated
		 */
		EClass EF_COMPOSITE = eINSTANCE.getEFComposite();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFStatusEventImpl <em>EF Status Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFStatusEventImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFStatusEvent()
		 * @generated
		 */
		EClass EF_STATUS_EVENT = eINSTANCE.getEFStatusEvent();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFLogicImpl <em>EF Logic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFLogicImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFLogic()
		 * @generated
		 */
		EClass EF_LOGIC = eINSTANCE.getEFLogic();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingImpl <em>EF Processing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessing()
		 * @generated
		 */
		EClass EF_PROCESSING = eINSTANCE.getEFProcessing();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFTemporalImpl <em>EF Temporal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFTemporalImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFTemporal()
		 * @generated
		 */
		EClass EF_TEMPORAL = eINSTANCE.getEFTemporal();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFLogicAndImpl <em>EF Logic And</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFLogicAndImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFLogicAnd()
		 * @generated
		 */
		EClass EF_LOGIC_AND = eINSTANCE.getEFLogicAnd();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFLogicOrImpl <em>EF Logic Or</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFLogicOrImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFLogicOr()
		 * @generated
		 */
		EClass EF_LOGIC_OR = eINSTANCE.getEFLogicOr();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFLogicNotImpl <em>EF Logic Not</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFLogicNotImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFLogicNot()
		 * @generated
		 */
		EClass EF_LOGIC_NOT = eINSTANCE.getEFLogicNot();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingMinImpl <em>EF Processing Min</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingMinImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingMin()
		 * @generated
		 */
		EClass EF_PROCESSING_MIN = eINSTANCE.getEFProcessingMin();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingMaxImpl <em>EF Processing Max</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingMaxImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingMax()
		 * @generated
		 */
		EClass EF_PROCESSING_MAX = eINSTANCE.getEFProcessingMax();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingSumImpl <em>EF Processing Sum</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingSumImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingSum()
		 * @generated
		 */
		EClass EF_PROCESSING_SUM = eINSTANCE.getEFProcessingSum();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingCountImpl <em>EF Processing Count</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingCountImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingCount()
		 * @generated
		 */
		EClass EF_PROCESSING_COUNT = eINSTANCE.getEFProcessingCount();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingAvgImpl <em>EF Processing Avg</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingAvgImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingAvg()
		 * @generated
		 */
		EClass EF_PROCESSING_AVG = eINSTANCE.getEFProcessingAvg();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFProcessingStDevImpl <em>EF Processing St Dev</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFProcessingStDevImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFProcessingStDev()
		 * @generated
		 */
		EClass EF_PROCESSING_ST_DEV = eINSTANCE.getEFProcessingStDev();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFTemporalSequenceImpl <em>EF Temporal Sequence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFTemporalSequenceImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFTemporalSequence()
		 * @generated
		 */
		EClass EF_TEMPORAL_SEQUENCE = eINSTANCE.getEFTemporalSequence();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFChangeEventImpl <em>EF Change Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFChangeEventImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFChangeEvent()
		 * @generated
		 */
		EClass EF_CHANGE_EVENT = eINSTANCE.getEFChangeEvent();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFChangeIncreaseImpl <em>EF Change Increase</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFChangeIncreaseImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFChangeIncrease()
		 * @generated
		 */
		EClass EF_CHANGE_INCREASE = eINSTANCE.getEFChangeIncrease();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFChangeDecreaseImpl <em>EF Change Decrease</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFChangeDecreaseImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFChangeDecrease()
		 * @generated
		 */
		EClass EF_CHANGE_DECREASE = eINSTANCE.getEFChangeDecrease();

		/**
		 * The meta object literal for the '{@link eventbase.impl.EFChangeRemainImpl <em>EF Change Remain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.EFChangeRemainImpl
		 * @see eventbase.impl.EventbasePackageImpl#getEFChangeRemain()
		 * @generated
		 */
		EClass EF_CHANGE_REMAIN = eINSTANCE.getEFChangeRemain();

	}

} //EventbasePackage
