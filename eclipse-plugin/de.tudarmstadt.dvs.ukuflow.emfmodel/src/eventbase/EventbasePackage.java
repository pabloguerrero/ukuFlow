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
	 * The meta object id for the '{@link eventbase.impl.ESimpleEFImpl <em>ESimple EF</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eventbase.impl.ESimpleEFImpl
	 * @see eventbase.impl.EventbasePackageImpl#getESimpleEF()
	 * @generated
	 */
	int ESIMPLE_EF = 6;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_EF__INCOMING = EVENT_BASE_OPERATOR__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_EF__OUTGOING = EVENT_BASE_OPERATOR__OUTGOING;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_EF__CONSTRAINTS = EVENT_BASE_OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_EF__SOURCE_EVENT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ESimple EF</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESIMPLE_EF_FEATURE_COUNT = EVENT_BASE_OPERATOR_FEATURE_COUNT + 2;


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
	 * Returns the meta object for class '{@link eventbase.ESimpleEF <em>ESimple EF</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ESimple EF</em>'.
	 * @see eventbase.ESimpleEF
	 * @generated
	 */
	EClass getESimpleEF();

	/**
	 * Returns the meta object for the reference list '{@link eventbase.ESimpleEF#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraints</em>'.
	 * @see eventbase.ESimpleEF#getConstraints()
	 * @see #getESimpleEF()
	 * @generated
	 */
	EReference getESimpleEF_Constraints();

	/**
	 * Returns the meta object for the reference '{@link eventbase.ESimpleEF#getSourceEvent <em>Source Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Event</em>'.
	 * @see eventbase.ESimpleEF#getSourceEvent()
	 * @see #getESimpleEF()
	 * @generated
	 */
	EReference getESimpleEF_SourceEvent();

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
		 * The meta object literal for the '{@link eventbase.impl.ESimpleEFImpl <em>ESimple EF</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eventbase.impl.ESimpleEFImpl
		 * @see eventbase.impl.EventbasePackageImpl#getESimpleEF()
		 * @generated
		 */
		EClass ESIMPLE_EF = eINSTANCE.getESimpleEF();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESIMPLE_EF__CONSTRAINTS = eINSTANCE.getESimpleEF_Constraints();

		/**
		 * The meta object literal for the '<em><b>Source Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESIMPLE_EF__SOURCE_EVENT = eINSTANCE.getESimpleEF_SourceEvent();

	}

} //EventbasePackage
