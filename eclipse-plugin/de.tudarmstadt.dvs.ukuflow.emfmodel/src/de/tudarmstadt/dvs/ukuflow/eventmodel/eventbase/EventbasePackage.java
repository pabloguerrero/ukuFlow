/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase;

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
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory
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
	EventbasePackage eINSTANCE = de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventBaseOperatorImpl <em>Event Base Operator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventBaseOperatorImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventBaseOperator()
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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BASE_OPERATOR__ELEMENT_NAME = 2;

	/**
	 * The number of structural features of the '<em>Event Base Operator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BASE_OPERATOR_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.ESequenceFlowImpl <em>ESequence Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.ESequenceFlowImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getESequenceFlow()
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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventGeneratorImpl <em>Event Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventGeneratorImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventGenerator()
	 * @generated
	 */
	int EVENT_GENERATOR = 2;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR__ELEMENT_NAME = EVENT_BASE_OPERATOR__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR__SENSOR_TYPE = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR__SCOPE = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Event Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_GENERATOR_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGNonRecurringImpl <em>EG Non Recurring</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGNonRecurringImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGNonRecurring()
	 * @generated
	 */
	int EG_NON_RECURRING = 3;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING__ELEMENT_NAME = EVENT_GENERATOR__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING__SENSOR_TYPE = EVENT_GENERATOR__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING__SCOPE = EVENT_GENERATOR__SCOPE;

	/**
	 * The number of structural features of the '<em>EG Non Recurring</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_NON_RECURRING_FEATURE_COUNT = EVENT_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGImmediateImpl <em>EG Immediate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGImmediateImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGImmediate()
	 * @generated
	 */
	int EG_IMMEDIATE = 4;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE__ELEMENT_NAME = EG_NON_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE__SENSOR_TYPE = EG_NON_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE__SCOPE = EG_NON_RECURRING__SCOPE;

	/**
	 * The number of structural features of the '<em>EG Immediate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_IMMEDIATE_FEATURE_COUNT = EG_NON_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGAbsoluteImpl <em>EG Absolute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGAbsoluteImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGAbsolute()
	 * @generated
	 */
	int EG_ABSOLUTE = 5;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__ELEMENT_NAME = EG_NON_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__SENSOR_TYPE = EG_NON_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_ABSOLUTE__SCOPE = EG_NON_RECURRING__SCOPE;

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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGOffsetImpl <em>EG Offset</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGOffsetImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGOffset()
	 * @generated
	 */
	int EG_OFFSET = 6;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__ELEMENT_NAME = EG_NON_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__SENSOR_TYPE = EG_NON_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_OFFSET__SCOPE = EG_NON_RECURRING__SCOPE;

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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRelativeImpl <em>EG Relative</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRelativeImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGRelative()
	 * @generated
	 */
	int EG_RELATIVE = 7;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__ELEMENT_NAME = EG_NON_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__SENSOR_TYPE = EG_NON_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RELATIVE__SCOPE = EG_NON_RECURRING__SCOPE;

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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRecurringImpl <em>EG Recurring</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRecurringImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGRecurring()
	 * @generated
	 */
	int EG_RECURRING = 8;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__ELEMENT_NAME = EVENT_GENERATOR__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__SENSOR_TYPE = EVENT_GENERATOR__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__SCOPE = EVENT_GENERATOR__SCOPE;

	/**
	 * The feature id for the '<em><b>Repetition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING__REPETITION = EVENT_GENERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EG Recurring</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_RECURRING_FEATURE_COUNT = EVENT_GENERATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPeriodicImpl <em>EG Periodic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPeriodicImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGPeriodic()
	 * @generated
	 */
	int EG_PERIODIC = 9;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__ELEMENT_NAME = EG_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__SENSOR_TYPE = EG_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__SCOPE = EG_RECURRING__SCOPE;

	/**
	 * The feature id for the '<em><b>Repetition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PERIODIC__REPETITION = EG_RECURRING__REPETITION;

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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPatternedImpl <em>EG Patterned</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPatternedImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGPatterned()
	 * @generated
	 */
	int EG_PATTERNED = 10;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__ELEMENT_NAME = EG_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__SENSOR_TYPE = EG_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__SCOPE = EG_RECURRING__SCOPE;

	/**
	 * The feature id for the '<em><b>Repetition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_PATTERNED__REPETITION = EG_RECURRING__REPETITION;

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
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl <em>EG Distribution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGDistribution()
	 * @generated
	 */
	int EG_DISTRIBUTION = 11;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__ELEMENT_NAME = EG_RECURRING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Sensor Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__SENSOR_TYPE = EG_RECURRING__SENSOR_TYPE;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__SCOPE = EG_RECURRING__SCOPE;

	/**
	 * The feature id for the '<em><b>Repetition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__REPETITION = EG_RECURRING__REPETITION;

	/**
	 * The feature id for the '<em><b>Period Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__PERIOD_INTERVAL = EG_RECURRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__FUNCTION = EG_RECURRING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__PARAMETERS = EG_RECURRING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Evaluation Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION__EVALUATION_INTERVAL = EG_RECURRING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>EG Distribution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EG_DISTRIBUTION_FEATURE_COUNT = EG_RECURRING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventFilterImpl <em>Event Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventFilterImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventFilter()
	 * @generated
	 */
	int EVENT_FILTER = 12;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FILTER__ELEMENT_NAME = EVENT_BASE_OPERATOR__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>Event Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FILTER_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl <em>EF Simple</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFSimple()
	 * @generated
	 */
	int EF_SIMPLE = 13;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__INCOMING = EVENT_FILTER__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__OUTGOING = EVENT_FILTER__OUTGOING;

	/**
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__ELEMENT_NAME = EVENT_FILTER__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Source Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__SOURCE_EVENT = EVENT_FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE__CONSTRAINTS = EVENT_FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EF Simple</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_SIMPLE_FEATURE_COUNT = EVENT_FILTER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFCompositeImpl <em>EF Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFCompositeImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFComposite()
	 * @generated
	 */
	int EF_COMPOSITE = 14;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_COMPOSITE__ELEMENT_NAME = EVENT_FILTER__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_COMPOSITE_FEATURE_COUNT = EVENT_FILTER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFStatusEventImpl <em>EF Status Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFStatusEventImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFStatusEvent()
	 * @generated
	 */
	int EF_STATUS_EVENT = 15;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_STATUS_EVENT__ELEMENT_NAME = EF_COMPOSITE__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Status Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_STATUS_EVENT_FEATURE_COUNT = EF_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicImpl <em>EF Logic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogic()
	 * @generated
	 */
	int EF_LOGIC = 16;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC__ELEMENT_NAME = EF_STATUS_EVENT__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Logic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingImpl <em>EF Processing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessing()
	 * @generated
	 */
	int EF_PROCESSING = 17;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING__ELEMENT_NAME = EF_STATUS_EVENT__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING__WINDOW_SIZE = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EF Processing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalImpl <em>EF Temporal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFTemporal()
	 * @generated
	 */
	int EF_TEMPORAL = 18;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL__ELEMENT_NAME = EF_STATUS_EVENT__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Temporal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_FEATURE_COUNT = EF_STATUS_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicAndImpl <em>EF Logic And</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicAndImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicAnd()
	 * @generated
	 */
	int EF_LOGIC_AND = 19;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_AND__ELEMENT_NAME = EF_LOGIC__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Logic And</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_AND_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicOrImpl <em>EF Logic Or</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicOrImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicOr()
	 * @generated
	 */
	int EF_LOGIC_OR = 20;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_OR__ELEMENT_NAME = EF_LOGIC__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Logic Or</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_OR_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicNotImpl <em>EF Logic Not</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicNotImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicNot()
	 * @generated
	 */
	int EF_LOGIC_NOT = 21;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_NOT__ELEMENT_NAME = EF_LOGIC__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Logic Not</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_LOGIC_NOT_FEATURE_COUNT = EF_LOGIC_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMinImpl <em>EF Processing Min</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMinImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingMin()
	 * @generated
	 */
	int EF_PROCESSING_MIN = 22;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing Min</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MIN_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMaxImpl <em>EF Processing Max</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMaxImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingMax()
	 * @generated
	 */
	int EF_PROCESSING_MAX = 23;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing Max</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_MAX_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingSumImpl <em>EF Processing Sum</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingSumImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingSum()
	 * @generated
	 */
	int EF_PROCESSING_SUM = 24;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing Sum</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_SUM_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingCountImpl <em>EF Processing Count</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingCountImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingCount()
	 * @generated
	 */
	int EF_PROCESSING_COUNT = 25;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_COUNT_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingAvgImpl <em>EF Processing Avg</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingAvgImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingAvg()
	 * @generated
	 */
	int EF_PROCESSING_AVG = 26;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing Avg</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_AVG_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingStDevImpl <em>EF Processing St Dev</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingStDevImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingStDev()
	 * @generated
	 */
	int EF_PROCESSING_ST_DEV = 27;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV__ELEMENT_NAME = EF_PROCESSING__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV__WINDOW_SIZE = EF_PROCESSING__WINDOW_SIZE;

	/**
	 * The number of structural features of the '<em>EF Processing St Dev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_PROCESSING_ST_DEV_FEATURE_COUNT = EF_PROCESSING_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalSequenceImpl <em>EF Temporal Sequence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalSequenceImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFTemporalSequence()
	 * @generated
	 */
	int EF_TEMPORAL_SEQUENCE = 28;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_SEQUENCE__ELEMENT_NAME = EF_TEMPORAL__ELEMENT_NAME;

	/**
	 * The number of structural features of the '<em>EF Temporal Sequence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_TEMPORAL_SEQUENCE_FEATURE_COUNT = EF_TEMPORAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl <em>EF Change Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeEvent()
	 * @generated
	 */
	int EF_CHANGE_EVENT = 29;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT__ELEMENT_NAME = EF_COMPOSITE__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT__WINDOW_SIZE = EF_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Change Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT__CHANGE_THRESHOLD = EF_COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EF Change Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_EVENT_FEATURE_COUNT = EF_COMPOSITE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeIncreaseImpl <em>EF Change Increase</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeIncreaseImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeIncrease()
	 * @generated
	 */
	int EF_CHANGE_INCREASE = 30;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE__ELEMENT_NAME = EF_CHANGE_EVENT__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE__WINDOW_SIZE = EF_CHANGE_EVENT__WINDOW_SIZE;

	/**
	 * The feature id for the '<em><b>Change Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE__CHANGE_THRESHOLD = EF_CHANGE_EVENT__CHANGE_THRESHOLD;

	/**
	 * The number of structural features of the '<em>EF Change Increase</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_INCREASE_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeDecreaseImpl <em>EF Change Decrease</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeDecreaseImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeDecrease()
	 * @generated
	 */
	int EF_CHANGE_DECREASE = 31;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE__ELEMENT_NAME = EF_CHANGE_EVENT__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE__WINDOW_SIZE = EF_CHANGE_EVENT__WINDOW_SIZE;

	/**
	 * The feature id for the '<em><b>Change Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE__CHANGE_THRESHOLD = EF_CHANGE_EVENT__CHANGE_THRESHOLD;

	/**
	 * The number of structural features of the '<em>EF Change Decrease</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_DECREASE_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeRemainImpl <em>EF Change Remain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeRemainImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeRemain()
	 * @generated
	 */
	int EF_CHANGE_REMAIN = 32;

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
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN__ELEMENT_NAME = EF_CHANGE_EVENT__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN__WINDOW_SIZE = EF_CHANGE_EVENT__WINDOW_SIZE;

	/**
	 * The feature id for the '<em><b>Change Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN__CHANGE_THRESHOLD = EF_CHANGE_EVENT__CHANGE_THRESHOLD;

	/**
	 * The number of structural features of the '<em>EF Change Remain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EF_CHANGE_REMAIN_FEATURE_COUNT = EF_CHANGE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl <em>Root Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getRootDocument()
	 * @generated
	 */
	int ROOT_DOCUMENT = 33;

	/**
	 * The feature id for the '<em><b>Sequence Flow</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__SEQUENCE_FLOW = 0;

	/**
	 * The feature id for the '<em><b>Eg Immediate</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_IMMEDIATE = 1;

	/**
	 * The feature id for the '<em><b>Eg Absolute</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_ABSOLUTE = 2;

	/**
	 * The feature id for the '<em><b>Eg Offset</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_OFFSET = 3;

	/**
	 * The feature id for the '<em><b>Eg Relative</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_RELATIVE = 4;

	/**
	 * The feature id for the '<em><b>Eg Periodic</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_PERIODIC = 5;

	/**
	 * The feature id for the '<em><b>Eg Patterned</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_PATTERNED = 6;

	/**
	 * The feature id for the '<em><b>Eg Distribution</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EG_DISTRIBUTION = 7;

	/**
	 * The feature id for the '<em><b>Ef Logic And</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_LOGIC_AND = 8;

	/**
	 * The feature id for the '<em><b>Ef Logic Or</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_LOGIC_OR = 9;

	/**
	 * The feature id for the '<em><b>Ef Logic Not</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_LOGIC_NOT = 10;

	/**
	 * The feature id for the '<em><b>Ef Processing Min</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_MIN = 11;

	/**
	 * The feature id for the '<em><b>Ef Processing Max</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_MAX = 12;

	/**
	 * The feature id for the '<em><b>Ef Processing Count</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_COUNT = 13;

	/**
	 * The feature id for the '<em><b>Ef Processing Sum</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_SUM = 14;

	/**
	 * The feature id for the '<em><b>Ef Processing Avg</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_AVG = 15;

	/**
	 * The feature id for the '<em><b>Ef Processing St Dev</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_PROCESSING_ST_DEV = 16;

	/**
	 * The feature id for the '<em><b>Ef Temporal Seq</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_TEMPORAL_SEQ = 17;

	/**
	 * The feature id for the '<em><b>Ef Increase</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_INCREASE = 18;

	/**
	 * The feature id for the '<em><b>Ef Decrease</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_DECREASE = 19;

	/**
	 * The feature id for the '<em><b>Ef Remain</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT__EF_REMAIN = 20;

	/**
	 * The number of structural features of the '<em>Root Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DOCUMENT_FEATURE_COUNT = 21;


	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator <em>Event Base Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Base Operator</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator
	 * @generated
	 */
	EClass getEventBaseOperator();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getIncoming()
	 * @see #getEventBaseOperator()
	 * @generated
	 */
	EReference getEventBaseOperator_Incoming();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getOutgoing()
	 * @see #getEventBaseOperator()
	 * @generated
	 */
	EReference getEventBaseOperator_Outgoing();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getElementName <em>Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Name</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator#getElementName()
	 * @see #getEventBaseOperator()
	 * @generated
	 */
	EAttribute getEventBaseOperator_ElementName();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow <em>ESequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ESequence Flow</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow
	 * @generated
	 */
	EClass getESequenceFlow();

	/**
	 * Returns the meta object for the reference '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getSource()
	 * @see #getESequenceFlow()
	 * @generated
	 */
	EReference getESequenceFlow_Source();

	/**
	 * Returns the meta object for the reference '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow#getTarget()
	 * @see #getESequenceFlow()
	 * @generated
	 */
	EReference getESequenceFlow_Target();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator <em>Event Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Generator</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator
	 * @generated
	 */
	EClass getEventGenerator();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator#getSensorType <em>Sensor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sensor Type</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator#getSensorType()
	 * @see #getEventGenerator()
	 * @generated
	 */
	EAttribute getEventGenerator_SensorType();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator#getScope()
	 * @see #getEventGenerator()
	 * @generated
	 */
	EAttribute getEventGenerator_Scope();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGNonRecurring <em>EG Non Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Non Recurring</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGNonRecurring
	 * @generated
	 */
	EClass getEGNonRecurring();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate <em>EG Immediate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Immediate</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate
	 * @generated
	 */
	EClass getEGImmediate();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute <em>EG Absolute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Absolute</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute
	 * @generated
	 */
	EClass getEGAbsolute();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute#getAbsoluteTime <em>Absolute Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Absolute Time</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute#getAbsoluteTime()
	 * @see #getEGAbsolute()
	 * @generated
	 */
	EAttribute getEGAbsolute_AbsoluteTime();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset <em>EG Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Offset</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset
	 * @generated
	 */
	EClass getEGOffset();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset#getOffsetTime <em>Offset Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset Time</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset#getOffsetTime()
	 * @see #getEGOffset()
	 * @generated
	 */
	EAttribute getEGOffset_OffsetTime();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative <em>EG Relative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Relative</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative
	 * @generated
	 */
	EClass getEGRelative();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative#getDelayTime <em>Delay Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delay Time</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative#getDelayTime()
	 * @see #getEGRelative()
	 * @generated
	 */
	EAttribute getEGRelative_DelayTime();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring <em>EG Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Recurring</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring
	 * @generated
	 */
	EClass getEGRecurring();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring#getRepetition <em>Repetition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repetition</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring#getRepetition()
	 * @see #getEGRecurring()
	 * @generated
	 */
	EAttribute getEGRecurring_Repetition();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic <em>EG Periodic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Periodic</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic
	 * @generated
	 */
	EClass getEGPeriodic();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic#getTime()
	 * @see #getEGPeriodic()
	 * @generated
	 */
	EAttribute getEGPeriodic_Time();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned <em>EG Patterned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Patterned</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned
	 * @generated
	 */
	EClass getEGPatterned();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned#getTime()
	 * @see #getEGPatterned()
	 * @generated
	 */
	EAttribute getEGPatterned_Time();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned#getPattern()
	 * @see #getEGPatterned()
	 * @generated
	 */
	EAttribute getEGPatterned_Pattern();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution <em>EG Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EG Distribution</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution
	 * @generated
	 */
	EClass getEGDistribution();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getPeriodInterval <em>Period Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Period Interval</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getPeriodInterval()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_PeriodInterval();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getFunction()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_Function();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getParameters()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getEvaluationInterval <em>Evaluation Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Evaluation Interval</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution#getEvaluationInterval()
	 * @see #getEGDistribution()
	 * @generated
	 */
	EAttribute getEGDistribution_EvaluationInterval();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter <em>Event Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Filter</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter
	 * @generated
	 */
	EClass getEventFilter();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple <em>EF Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Simple</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple
	 * @generated
	 */
	EClass getEFSimple();

	/**
	 * Returns the meta object for the reference '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple#getSourceEvent <em>Source Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Event</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple#getSourceEvent()
	 * @see #getEFSimple()
	 * @generated
	 */
	EReference getEFSimple_SourceEvent();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraints</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple#getConstraints()
	 * @see #getEFSimple()
	 * @generated
	 */
	EAttribute getEFSimple_Constraints();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite <em>EF Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Composite</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite
	 * @generated
	 */
	EClass getEFComposite();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent <em>EF Status Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Status Event</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent
	 * @generated
	 */
	EClass getEFStatusEvent();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic <em>EF Logic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic
	 * @generated
	 */
	EClass getEFLogic();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing <em>EF Processing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing
	 * @generated
	 */
	EClass getEFProcessing();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing#getWindowSize <em>Window Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Size</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing#getWindowSize()
	 * @see #getEFProcessing()
	 * @generated
	 */
	EAttribute getEFProcessing_WindowSize();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal <em>EF Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Temporal</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal
	 * @generated
	 */
	EClass getEFTemporal();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd <em>EF Logic And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic And</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd
	 * @generated
	 */
	EClass getEFLogicAnd();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr <em>EF Logic Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic Or</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr
	 * @generated
	 */
	EClass getEFLogicOr();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot <em>EF Logic Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Logic Not</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot
	 * @generated
	 */
	EClass getEFLogicNot();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin <em>EF Processing Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Min</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin
	 * @generated
	 */
	EClass getEFProcessingMin();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax <em>EF Processing Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Max</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax
	 * @generated
	 */
	EClass getEFProcessingMax();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum <em>EF Processing Sum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Sum</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum
	 * @generated
	 */
	EClass getEFProcessingSum();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount <em>EF Processing Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Count</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount
	 * @generated
	 */
	EClass getEFProcessingCount();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg <em>EF Processing Avg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing Avg</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg
	 * @generated
	 */
	EClass getEFProcessingAvg();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev <em>EF Processing St Dev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Processing St Dev</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev
	 * @generated
	 */
	EClass getEFProcessingStDev();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence <em>EF Temporal Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Temporal Sequence</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence
	 * @generated
	 */
	EClass getEFTemporalSequence();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent <em>EF Change Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Event</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent
	 * @generated
	 */
	EClass getEFChangeEvent();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getWindowSize <em>Window Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Size</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getWindowSize()
	 * @see #getEFChangeEvent()
	 * @generated
	 */
	EAttribute getEFChangeEvent_WindowSize();

	/**
	 * Returns the meta object for the attribute '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getChangeThreshold <em>Change Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change Threshold</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent#getChangeThreshold()
	 * @see #getEFChangeEvent()
	 * @generated
	 */
	EAttribute getEFChangeEvent_ChangeThreshold();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease <em>EF Change Increase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Increase</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease
	 * @generated
	 */
	EClass getEFChangeIncrease();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease <em>EF Change Decrease</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Decrease</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease
	 * @generated
	 */
	EClass getEFChangeDecrease();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain <em>EF Change Remain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EF Change Remain</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain
	 * @generated
	 */
	EClass getEFChangeRemain();

	/**
	 * Returns the meta object for class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument <em>Root Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Document</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument
	 * @generated
	 */
	EClass getRootDocument();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getSequenceFlow <em>Sequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sequence Flow</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getSequenceFlow()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_SequenceFlow();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgImmediate <em>Eg Immediate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Immediate</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgImmediate()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgImmediate();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgAbsolute <em>Eg Absolute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Absolute</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgAbsolute()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgAbsolute();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgOffset <em>Eg Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Offset</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgOffset()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgOffset();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgRelative <em>Eg Relative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Relative</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgRelative()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgRelative();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPeriodic <em>Eg Periodic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Periodic</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPeriodic()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgPeriodic();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPatterned <em>Eg Patterned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Patterned</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgPatterned()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgPatterned();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgDistribution <em>Eg Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Eg Distribution</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEgDistribution()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EgDistribution();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicAnd <em>Ef Logic And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Logic And</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicAnd()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfLogicAnd();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicOr <em>Ef Logic Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Logic Or</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicOr()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfLogicOr();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicNot <em>Ef Logic Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Logic Not</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfLogicNot()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfLogicNot();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMin <em>Ef Processing Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing Min</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMin()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingMin();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMax <em>Ef Processing Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing Max</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingMax()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingMax();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingCount <em>Ef Processing Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing Count</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingCount()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingCount();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingSum <em>Ef Processing Sum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing Sum</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingSum()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingSum();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingAvg <em>Ef Processing Avg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing Avg</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingAvg()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingAvg();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingStDev <em>Ef Processing St Dev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Processing St Dev</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfProcessingStDev()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfProcessingStDev();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfTemporalSeq <em>Ef Temporal Seq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Temporal Seq</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfTemporalSeq()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfTemporalSeq();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfIncrease <em>Ef Increase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Increase</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfIncrease()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfIncrease();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfDecrease <em>Ef Decrease</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Decrease</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfDecrease()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfDecrease();

	/**
	 * Returns the meta object for the reference list '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfRemain <em>Ef Remain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ef Remain</em>'.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument#getEfRemain()
	 * @see #getRootDocument()
	 * @generated
	 */
	EReference getRootDocument_EfRemain();

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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventBaseOperatorImpl <em>Event Base Operator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventBaseOperatorImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventBaseOperator()
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
		 * The meta object literal for the '<em><b>Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BASE_OPERATOR__ELEMENT_NAME = eINSTANCE.getEventBaseOperator_ElementName();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.ESequenceFlowImpl <em>ESequence Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.ESequenceFlowImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getESequenceFlow()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventGeneratorImpl <em>Event Generator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventGeneratorImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventGenerator()
		 * @generated
		 */
		EClass EVENT_GENERATOR = eINSTANCE.getEventGenerator();

		/**
		 * The meta object literal for the '<em><b>Sensor Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_GENERATOR__SENSOR_TYPE = eINSTANCE.getEventGenerator_SensorType();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_GENERATOR__SCOPE = eINSTANCE.getEventGenerator_Scope();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGNonRecurringImpl <em>EG Non Recurring</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGNonRecurringImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGNonRecurring()
		 * @generated
		 */
		EClass EG_NON_RECURRING = eINSTANCE.getEGNonRecurring();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGImmediateImpl <em>EG Immediate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGImmediateImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGImmediate()
		 * @generated
		 */
		EClass EG_IMMEDIATE = eINSTANCE.getEGImmediate();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGAbsoluteImpl <em>EG Absolute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGAbsoluteImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGAbsolute()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGOffsetImpl <em>EG Offset</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGOffsetImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGOffset()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRelativeImpl <em>EG Relative</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRelativeImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGRelative()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRecurringImpl <em>EG Recurring</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRecurringImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGRecurring()
		 * @generated
		 */
		EClass EG_RECURRING = eINSTANCE.getEGRecurring();

		/**
		 * The meta object literal for the '<em><b>Repetition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_RECURRING__REPETITION = eINSTANCE.getEGRecurring_Repetition();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPeriodicImpl <em>EG Periodic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPeriodicImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGPeriodic()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPatternedImpl <em>EG Patterned</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGPatternedImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGPatterned()
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
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl <em>EG Distribution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEGDistribution()
		 * @generated
		 */
		EClass EG_DISTRIBUTION = eINSTANCE.getEGDistribution();

		/**
		 * The meta object literal for the '<em><b>Period Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__PERIOD_INTERVAL = eINSTANCE.getEGDistribution_PeriodInterval();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__FUNCTION = eINSTANCE.getEGDistribution_Function();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__PARAMETERS = eINSTANCE.getEGDistribution_Parameters();

		/**
		 * The meta object literal for the '<em><b>Evaluation Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EG_DISTRIBUTION__EVALUATION_INTERVAL = eINSTANCE.getEGDistribution_EvaluationInterval();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventFilterImpl <em>Event Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventFilterImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEventFilter()
		 * @generated
		 */
		EClass EVENT_FILTER = eINSTANCE.getEventFilter();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl <em>EF Simple</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFSimple()
		 * @generated
		 */
		EClass EF_SIMPLE = eINSTANCE.getEFSimple();

		/**
		 * The meta object literal for the '<em><b>Source Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EF_SIMPLE__SOURCE_EVENT = eINSTANCE.getEFSimple_SourceEvent();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EF_SIMPLE__CONSTRAINTS = eINSTANCE.getEFSimple_Constraints();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFCompositeImpl <em>EF Composite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFCompositeImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFComposite()
		 * @generated
		 */
		EClass EF_COMPOSITE = eINSTANCE.getEFComposite();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFStatusEventImpl <em>EF Status Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFStatusEventImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFStatusEvent()
		 * @generated
		 */
		EClass EF_STATUS_EVENT = eINSTANCE.getEFStatusEvent();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicImpl <em>EF Logic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogic()
		 * @generated
		 */
		EClass EF_LOGIC = eINSTANCE.getEFLogic();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingImpl <em>EF Processing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessing()
		 * @generated
		 */
		EClass EF_PROCESSING = eINSTANCE.getEFProcessing();

		/**
		 * The meta object literal for the '<em><b>Window Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EF_PROCESSING__WINDOW_SIZE = eINSTANCE.getEFProcessing_WindowSize();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalImpl <em>EF Temporal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFTemporal()
		 * @generated
		 */
		EClass EF_TEMPORAL = eINSTANCE.getEFTemporal();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicAndImpl <em>EF Logic And</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicAndImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicAnd()
		 * @generated
		 */
		EClass EF_LOGIC_AND = eINSTANCE.getEFLogicAnd();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicOrImpl <em>EF Logic Or</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicOrImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicOr()
		 * @generated
		 */
		EClass EF_LOGIC_OR = eINSTANCE.getEFLogicOr();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicNotImpl <em>EF Logic Not</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFLogicNotImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFLogicNot()
		 * @generated
		 */
		EClass EF_LOGIC_NOT = eINSTANCE.getEFLogicNot();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMinImpl <em>EF Processing Min</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMinImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingMin()
		 * @generated
		 */
		EClass EF_PROCESSING_MIN = eINSTANCE.getEFProcessingMin();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMaxImpl <em>EF Processing Max</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingMaxImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingMax()
		 * @generated
		 */
		EClass EF_PROCESSING_MAX = eINSTANCE.getEFProcessingMax();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingSumImpl <em>EF Processing Sum</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingSumImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingSum()
		 * @generated
		 */
		EClass EF_PROCESSING_SUM = eINSTANCE.getEFProcessingSum();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingCountImpl <em>EF Processing Count</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingCountImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingCount()
		 * @generated
		 */
		EClass EF_PROCESSING_COUNT = eINSTANCE.getEFProcessingCount();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingAvgImpl <em>EF Processing Avg</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingAvgImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingAvg()
		 * @generated
		 */
		EClass EF_PROCESSING_AVG = eINSTANCE.getEFProcessingAvg();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingStDevImpl <em>EF Processing St Dev</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFProcessingStDevImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFProcessingStDev()
		 * @generated
		 */
		EClass EF_PROCESSING_ST_DEV = eINSTANCE.getEFProcessingStDev();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalSequenceImpl <em>EF Temporal Sequence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFTemporalSequenceImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFTemporalSequence()
		 * @generated
		 */
		EClass EF_TEMPORAL_SEQUENCE = eINSTANCE.getEFTemporalSequence();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl <em>EF Change Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeEvent()
		 * @generated
		 */
		EClass EF_CHANGE_EVENT = eINSTANCE.getEFChangeEvent();

		/**
		 * The meta object literal for the '<em><b>Window Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EF_CHANGE_EVENT__WINDOW_SIZE = eINSTANCE.getEFChangeEvent_WindowSize();

		/**
		 * The meta object literal for the '<em><b>Change Threshold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EF_CHANGE_EVENT__CHANGE_THRESHOLD = eINSTANCE.getEFChangeEvent_ChangeThreshold();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeIncreaseImpl <em>EF Change Increase</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeIncreaseImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeIncrease()
		 * @generated
		 */
		EClass EF_CHANGE_INCREASE = eINSTANCE.getEFChangeIncrease();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeDecreaseImpl <em>EF Change Decrease</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeDecreaseImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeDecrease()
		 * @generated
		 */
		EClass EF_CHANGE_DECREASE = eINSTANCE.getEFChangeDecrease();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeRemainImpl <em>EF Change Remain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeRemainImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getEFChangeRemain()
		 * @generated
		 */
		EClass EF_CHANGE_REMAIN = eINSTANCE.getEFChangeRemain();

		/**
		 * The meta object literal for the '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl <em>Root Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl
		 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventbasePackageImpl#getRootDocument()
		 * @generated
		 */
		EClass ROOT_DOCUMENT = eINSTANCE.getRootDocument();

		/**
		 * The meta object literal for the '<em><b>Sequence Flow</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__SEQUENCE_FLOW = eINSTANCE.getRootDocument_SequenceFlow();

		/**
		 * The meta object literal for the '<em><b>Eg Immediate</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_IMMEDIATE = eINSTANCE.getRootDocument_EgImmediate();

		/**
		 * The meta object literal for the '<em><b>Eg Absolute</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_ABSOLUTE = eINSTANCE.getRootDocument_EgAbsolute();

		/**
		 * The meta object literal for the '<em><b>Eg Offset</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_OFFSET = eINSTANCE.getRootDocument_EgOffset();

		/**
		 * The meta object literal for the '<em><b>Eg Relative</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_RELATIVE = eINSTANCE.getRootDocument_EgRelative();

		/**
		 * The meta object literal for the '<em><b>Eg Periodic</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_PERIODIC = eINSTANCE.getRootDocument_EgPeriodic();

		/**
		 * The meta object literal for the '<em><b>Eg Patterned</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_PATTERNED = eINSTANCE.getRootDocument_EgPatterned();

		/**
		 * The meta object literal for the '<em><b>Eg Distribution</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EG_DISTRIBUTION = eINSTANCE.getRootDocument_EgDistribution();

		/**
		 * The meta object literal for the '<em><b>Ef Logic And</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_LOGIC_AND = eINSTANCE.getRootDocument_EfLogicAnd();

		/**
		 * The meta object literal for the '<em><b>Ef Logic Or</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_LOGIC_OR = eINSTANCE.getRootDocument_EfLogicOr();

		/**
		 * The meta object literal for the '<em><b>Ef Logic Not</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_LOGIC_NOT = eINSTANCE.getRootDocument_EfLogicNot();

		/**
		 * The meta object literal for the '<em><b>Ef Processing Min</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_MIN = eINSTANCE.getRootDocument_EfProcessingMin();

		/**
		 * The meta object literal for the '<em><b>Ef Processing Max</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_MAX = eINSTANCE.getRootDocument_EfProcessingMax();

		/**
		 * The meta object literal for the '<em><b>Ef Processing Count</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_COUNT = eINSTANCE.getRootDocument_EfProcessingCount();

		/**
		 * The meta object literal for the '<em><b>Ef Processing Sum</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_SUM = eINSTANCE.getRootDocument_EfProcessingSum();

		/**
		 * The meta object literal for the '<em><b>Ef Processing Avg</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_AVG = eINSTANCE.getRootDocument_EfProcessingAvg();

		/**
		 * The meta object literal for the '<em><b>Ef Processing St Dev</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_PROCESSING_ST_DEV = eINSTANCE.getRootDocument_EfProcessingStDev();

		/**
		 * The meta object literal for the '<em><b>Ef Temporal Seq</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_TEMPORAL_SEQ = eINSTANCE.getRootDocument_EfTemporalSeq();

		/**
		 * The meta object literal for the '<em><b>Ef Increase</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_INCREASE = eINSTANCE.getRootDocument_EfIncrease();

		/**
		 * The meta object literal for the '<em><b>Ef Decrease</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_DECREASE = eINSTANCE.getRootDocument_EfDecrease();

		/**
		 * The meta object literal for the '<em><b>Ef Remain</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_DOCUMENT__EF_REMAIN = eINSTANCE.getRootDocument_EfRemain();

	}

} //EventbasePackage
