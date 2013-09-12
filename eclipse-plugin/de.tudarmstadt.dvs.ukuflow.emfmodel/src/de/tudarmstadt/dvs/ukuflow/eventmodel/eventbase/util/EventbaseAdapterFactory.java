/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.util;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage
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
			public Adapter caseEventGenerator(EventGenerator object) {
				return createEventGeneratorAdapter();
			}
			@Override
			public Adapter caseEGNonRecurring(EGNonRecurring object) {
				return createEGNonRecurringAdapter();
			}
			@Override
			public Adapter caseEGImmediate(EGImmediate object) {
				return createEGImmediateAdapter();
			}
			@Override
			public Adapter caseEGAbsolute(EGAbsolute object) {
				return createEGAbsoluteAdapter();
			}
			@Override
			public Adapter caseEGOffset(EGOffset object) {
				return createEGOffsetAdapter();
			}
			@Override
			public Adapter caseEGRelative(EGRelative object) {
				return createEGRelativeAdapter();
			}
			@Override
			public Adapter caseEGRecurring(EGRecurring object) {
				return createEGRecurringAdapter();
			}
			@Override
			public Adapter caseEGPeriodic(EGPeriodic object) {
				return createEGPeriodicAdapter();
			}
			@Override
			public Adapter caseEGPatterned(EGPatterned object) {
				return createEGPatternedAdapter();
			}
			@Override
			public Adapter caseEGDistribution(EGDistribution object) {
				return createEGDistributionAdapter();
			}
			@Override
			public Adapter caseEventFilter(EventFilter object) {
				return createEventFilterAdapter();
			}
			@Override
			public Adapter caseEFSimple(EFSimple object) {
				return createEFSimpleAdapter();
			}
			@Override
			public Adapter caseEFComposite(EFComposite object) {
				return createEFCompositeAdapter();
			}
			@Override
			public Adapter caseEFStatusEvent(EFStatusEvent object) {
				return createEFStatusEventAdapter();
			}
			@Override
			public Adapter caseEFLogic(EFLogic object) {
				return createEFLogicAdapter();
			}
			@Override
			public Adapter caseEFProcessing(EFProcessing object) {
				return createEFProcessingAdapter();
			}
			@Override
			public Adapter caseEFTemporal(EFTemporal object) {
				return createEFTemporalAdapter();
			}
			@Override
			public Adapter caseEFLogicAnd(EFLogicAnd object) {
				return createEFLogicAndAdapter();
			}
			@Override
			public Adapter caseEFLogicOr(EFLogicOr object) {
				return createEFLogicOrAdapter();
			}
			@Override
			public Adapter caseEFLogicNot(EFLogicNot object) {
				return createEFLogicNotAdapter();
			}
			@Override
			public Adapter caseEFProcessingMin(EFProcessingMin object) {
				return createEFProcessingMinAdapter();
			}
			@Override
			public Adapter caseEFProcessingMax(EFProcessingMax object) {
				return createEFProcessingMaxAdapter();
			}
			@Override
			public Adapter caseEFProcessingSum(EFProcessingSum object) {
				return createEFProcessingSumAdapter();
			}
			@Override
			public Adapter caseEFProcessingCount(EFProcessingCount object) {
				return createEFProcessingCountAdapter();
			}
			@Override
			public Adapter caseEFProcessingAvg(EFProcessingAvg object) {
				return createEFProcessingAvgAdapter();
			}
			@Override
			public Adapter caseEFProcessingStDev(EFProcessingStDev object) {
				return createEFProcessingStDevAdapter();
			}
			@Override
			public Adapter caseEFTemporalSequence(EFTemporalSequence object) {
				return createEFTemporalSequenceAdapter();
			}
			@Override
			public Adapter caseEFChangeEvent(EFChangeEvent object) {
				return createEFChangeEventAdapter();
			}
			@Override
			public Adapter caseEFChangeIncrease(EFChangeIncrease object) {
				return createEFChangeIncreaseAdapter();
			}
			@Override
			public Adapter caseEFChangeDecrease(EFChangeDecrease object) {
				return createEFChangeDecreaseAdapter();
			}
			@Override
			public Adapter caseEFChangeRemain(EFChangeRemain object) {
				return createEFChangeRemainAdapter();
			}
			@Override
			public Adapter caseRootDocument(RootDocument object) {
				return createRootDocumentAdapter();
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
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator <em>Event Base Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator
	 * @generated
	 */
	public Adapter createEventBaseOperatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow <em>ESequence Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow
	 * @generated
	 */
	public Adapter createESequenceFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator <em>Event Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator
	 * @generated
	 */
	public Adapter createEventGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGNonRecurring <em>EG Non Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGNonRecurring
	 * @generated
	 */
	public Adapter createEGNonRecurringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate <em>EG Immediate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate
	 * @generated
	 */
	public Adapter createEGImmediateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute <em>EG Absolute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute
	 * @generated
	 */
	public Adapter createEGAbsoluteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset <em>EG Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset
	 * @generated
	 */
	public Adapter createEGOffsetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative <em>EG Relative</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative
	 * @generated
	 */
	public Adapter createEGRelativeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring <em>EG Recurring</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring
	 * @generated
	 */
	public Adapter createEGRecurringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic <em>EG Periodic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic
	 * @generated
	 */
	public Adapter createEGPeriodicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned <em>EG Patterned</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned
	 * @generated
	 */
	public Adapter createEGPatternedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution <em>EG Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution
	 * @generated
	 */
	public Adapter createEGDistributionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter <em>Event Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter
	 * @generated
	 */
	public Adapter createEventFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple <em>EF Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple
	 * @generated
	 */
	public Adapter createEFSimpleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite <em>EF Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite
	 * @generated
	 */
	public Adapter createEFCompositeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent <em>EF Status Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent
	 * @generated
	 */
	public Adapter createEFStatusEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic <em>EF Logic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic
	 * @generated
	 */
	public Adapter createEFLogicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing <em>EF Processing</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing
	 * @generated
	 */
	public Adapter createEFProcessingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal <em>EF Temporal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal
	 * @generated
	 */
	public Adapter createEFTemporalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd <em>EF Logic And</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd
	 * @generated
	 */
	public Adapter createEFLogicAndAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr <em>EF Logic Or</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr
	 * @generated
	 */
	public Adapter createEFLogicOrAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot <em>EF Logic Not</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot
	 * @generated
	 */
	public Adapter createEFLogicNotAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin <em>EF Processing Min</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin
	 * @generated
	 */
	public Adapter createEFProcessingMinAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax <em>EF Processing Max</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax
	 * @generated
	 */
	public Adapter createEFProcessingMaxAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum <em>EF Processing Sum</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum
	 * @generated
	 */
	public Adapter createEFProcessingSumAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount <em>EF Processing Count</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount
	 * @generated
	 */
	public Adapter createEFProcessingCountAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg <em>EF Processing Avg</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg
	 * @generated
	 */
	public Adapter createEFProcessingAvgAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev <em>EF Processing St Dev</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev
	 * @generated
	 */
	public Adapter createEFProcessingStDevAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence <em>EF Temporal Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence
	 * @generated
	 */
	public Adapter createEFTemporalSequenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent <em>EF Change Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent
	 * @generated
	 */
	public Adapter createEFChangeEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease <em>EF Change Increase</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease
	 * @generated
	 */
	public Adapter createEFChangeIncreaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease <em>EF Change Decrease</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease
	 * @generated
	 */
	public Adapter createEFChangeDecreaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain <em>EF Change Remain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain
	 * @generated
	 */
	public Adapter createEFChangeRemainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument <em>Root Document</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument
	 * @generated
	 */
	public Adapter createRootDocumentAdapter() {
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
