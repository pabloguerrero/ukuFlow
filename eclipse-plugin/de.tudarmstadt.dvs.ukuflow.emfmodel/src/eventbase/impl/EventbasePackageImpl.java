/**
 */
package eventbase.impl;

import eventbase.EDistributionEG;
import eventbase.EPatternedEG;
import eventbase.EPeriodicEG;
import eventbase.ESequenceFlow;
import eventbase.ESimpleEF;
import eventbase.ESimpleFilterConstraint;
import eventbase.EventBaseOperator;
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
	private EClass eSimpleEFEClass = null;

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
	public EClass getESimpleEF() {
		return eSimpleEFEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getESimpleEF_Constraints() {
		return (EReference)eSimpleEFEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getESimpleEF_SourceEvent() {
		return (EReference)eSimpleEFEClass.getEStructuralFeatures().get(1);
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

		eSimpleEFEClass = createEClass(ESIMPLE_EF);
		createEReference(eSimpleEFEClass, ESIMPLE_EF__CONSTRAINTS);
		createEReference(eSimpleEFEClass, ESIMPLE_EF__SOURCE_EVENT);
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
		eSimpleEFEClass.getESuperTypes().add(this.getEventBaseOperator());

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

		initEClass(eSimpleEFEClass, ESimpleEF.class, "ESimpleEF", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getESimpleEF_Constraints(), this.getESimpleFilterConstraint(), null, "constraints", null, 1, -1, ESimpleEF.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getESimpleEF_SourceEvent(), this.getEventBaseOperator(), null, "sourceEvent", null, 1, 1, ESimpleEF.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EventbasePackageImpl
