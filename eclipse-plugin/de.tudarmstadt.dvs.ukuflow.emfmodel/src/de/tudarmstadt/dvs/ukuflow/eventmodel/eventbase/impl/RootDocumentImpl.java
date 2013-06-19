/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.RootDocument;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getSequenceFlow <em>Sequence Flow</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgImmediate <em>Eg Immediate</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgAbsolute <em>Eg Absolute</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgOffset <em>Eg Offset</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgRelative <em>Eg Relative</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgPeriodic <em>Eg Periodic</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgPatterned <em>Eg Patterned</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEgDistribution <em>Eg Distribution</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfLogicAnd <em>Ef Logic And</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfLogicOr <em>Ef Logic Or</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfLogicNot <em>Ef Logic Not</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingMin <em>Ef Processing Min</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingMax <em>Ef Processing Max</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingCount <em>Ef Processing Count</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingSum <em>Ef Processing Sum</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingAvg <em>Ef Processing Avg</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfProcessingStDev <em>Ef Processing St Dev</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfTemporalSeq <em>Ef Temporal Seq</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfIncrease <em>Ef Increase</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfDecrease <em>Ef Decrease</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.RootDocumentImpl#getEfRemain <em>Ef Remain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RootDocumentImpl extends EObjectImpl implements RootDocument {
	/**
	 * The cached value of the '{@link #getSequenceFlow() <em>Sequence Flow</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceFlow()
	 * @generated
	 * @ordered
	 */
	protected EList<ESequenceFlow> sequenceFlow;

	/**
	 * The cached value of the '{@link #getEgImmediate() <em>Eg Immediate</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgImmediate()
	 * @generated
	 * @ordered
	 */
	protected EList<EGImmediate> egImmediate;

	/**
	 * The cached value of the '{@link #getEgAbsolute() <em>Eg Absolute</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgAbsolute()
	 * @generated
	 * @ordered
	 */
	protected EList<EGAbsolute> egAbsolute;

	/**
	 * The cached value of the '{@link #getEgOffset() <em>Eg Offset</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgOffset()
	 * @generated
	 * @ordered
	 */
	protected EList<EGOffset> egOffset;

	/**
	 * The cached value of the '{@link #getEgRelative() <em>Eg Relative</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgRelative()
	 * @generated
	 * @ordered
	 */
	protected EList<EGRelative> egRelative;

	/**
	 * The cached value of the '{@link #getEgPeriodic() <em>Eg Periodic</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgPeriodic()
	 * @generated
	 * @ordered
	 */
	protected EList<EGPeriodic> egPeriodic;

	/**
	 * The cached value of the '{@link #getEgPatterned() <em>Eg Patterned</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgPatterned()
	 * @generated
	 * @ordered
	 */
	protected EList<EGPatterned> egPatterned;

	/**
	 * The cached value of the '{@link #getEgDistribution() <em>Eg Distribution</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEgDistribution()
	 * @generated
	 * @ordered
	 */
	protected EList<EGDistribution> egDistribution;

	/**
	 * The cached value of the '{@link #getEfLogicAnd() <em>Ef Logic And</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfLogicAnd()
	 * @generated
	 * @ordered
	 */
	protected EList<EFLogicAnd> efLogicAnd;

	/**
	 * The cached value of the '{@link #getEfLogicOr() <em>Ef Logic Or</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfLogicOr()
	 * @generated
	 * @ordered
	 */
	protected EList<EFLogicOr> efLogicOr;

	/**
	 * The cached value of the '{@link #getEfLogicNot() <em>Ef Logic Not</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfLogicNot()
	 * @generated
	 * @ordered
	 */
	protected EList<EFLogicNot> efLogicNot;

	/**
	 * The cached value of the '{@link #getEfProcessingMin() <em>Ef Processing Min</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingMin()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingMin> efProcessingMin;

	/**
	 * The cached value of the '{@link #getEfProcessingMax() <em>Ef Processing Max</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingMax()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingMax> efProcessingMax;

	/**
	 * The cached value of the '{@link #getEfProcessingCount() <em>Ef Processing Count</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingCount()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingCount> efProcessingCount;

	/**
	 * The cached value of the '{@link #getEfProcessingSum() <em>Ef Processing Sum</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingSum()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingSum> efProcessingSum;

	/**
	 * The cached value of the '{@link #getEfProcessingAvg() <em>Ef Processing Avg</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingAvg()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingAvg> efProcessingAvg;

	/**
	 * The cached value of the '{@link #getEfProcessingStDev() <em>Ef Processing St Dev</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfProcessingStDev()
	 * @generated
	 * @ordered
	 */
	protected EList<EFProcessingStDev> efProcessingStDev;

	/**
	 * The cached value of the '{@link #getEfTemporalSeq() <em>Ef Temporal Seq</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfTemporalSeq()
	 * @generated
	 * @ordered
	 */
	protected EList<EFTemporalSequence> efTemporalSeq;

	/**
	 * The cached value of the '{@link #getEfIncrease() <em>Ef Increase</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfIncrease()
	 * @generated
	 * @ordered
	 */
	protected EList<EFChangeIncrease> efIncrease;

	/**
	 * The cached value of the '{@link #getEfDecrease() <em>Ef Decrease</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfDecrease()
	 * @generated
	 * @ordered
	 */
	protected EList<EFChangeDecrease> efDecrease;

	/**
	 * The cached value of the '{@link #getEfRemain() <em>Ef Remain</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfRemain()
	 * @generated
	 * @ordered
	 */
	protected EList<EFChangeRemain> efRemain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RootDocumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.ROOT_DOCUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ESequenceFlow> getSequenceFlow() {
		if (sequenceFlow == null) {
			sequenceFlow = new EObjectResolvingEList<ESequenceFlow>(ESequenceFlow.class, this, EventbasePackage.ROOT_DOCUMENT__SEQUENCE_FLOW);
		}
		return sequenceFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGImmediate> getEgImmediate() {
		if (egImmediate == null) {
			egImmediate = new EObjectResolvingEList<EGImmediate>(EGImmediate.class, this, EventbasePackage.ROOT_DOCUMENT__EG_IMMEDIATE);
		}
		return egImmediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGAbsolute> getEgAbsolute() {
		if (egAbsolute == null) {
			egAbsolute = new EObjectResolvingEList<EGAbsolute>(EGAbsolute.class, this, EventbasePackage.ROOT_DOCUMENT__EG_ABSOLUTE);
		}
		return egAbsolute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGOffset> getEgOffset() {
		if (egOffset == null) {
			egOffset = new EObjectResolvingEList<EGOffset>(EGOffset.class, this, EventbasePackage.ROOT_DOCUMENT__EG_OFFSET);
		}
		return egOffset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGRelative> getEgRelative() {
		if (egRelative == null) {
			egRelative = new EObjectResolvingEList<EGRelative>(EGRelative.class, this, EventbasePackage.ROOT_DOCUMENT__EG_RELATIVE);
		}
		return egRelative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGPeriodic> getEgPeriodic() {
		if (egPeriodic == null) {
			egPeriodic = new EObjectResolvingEList<EGPeriodic>(EGPeriodic.class, this, EventbasePackage.ROOT_DOCUMENT__EG_PERIODIC);
		}
		return egPeriodic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGPatterned> getEgPatterned() {
		if (egPatterned == null) {
			egPatterned = new EObjectResolvingEList<EGPatterned>(EGPatterned.class, this, EventbasePackage.ROOT_DOCUMENT__EG_PATTERNED);
		}
		return egPatterned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EGDistribution> getEgDistribution() {
		if (egDistribution == null) {
			egDistribution = new EObjectResolvingEList<EGDistribution>(EGDistribution.class, this, EventbasePackage.ROOT_DOCUMENT__EG_DISTRIBUTION);
		}
		return egDistribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFLogicAnd> getEfLogicAnd() {
		if (efLogicAnd == null) {
			efLogicAnd = new EObjectResolvingEList<EFLogicAnd>(EFLogicAnd.class, this, EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_AND);
		}
		return efLogicAnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFLogicOr> getEfLogicOr() {
		if (efLogicOr == null) {
			efLogicOr = new EObjectResolvingEList<EFLogicOr>(EFLogicOr.class, this, EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_OR);
		}
		return efLogicOr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFLogicNot> getEfLogicNot() {
		if (efLogicNot == null) {
			efLogicNot = new EObjectResolvingEList<EFLogicNot>(EFLogicNot.class, this, EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_NOT);
		}
		return efLogicNot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingMin> getEfProcessingMin() {
		if (efProcessingMin == null) {
			efProcessingMin = new EObjectResolvingEList<EFProcessingMin>(EFProcessingMin.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MIN);
		}
		return efProcessingMin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingMax> getEfProcessingMax() {
		if (efProcessingMax == null) {
			efProcessingMax = new EObjectResolvingEList<EFProcessingMax>(EFProcessingMax.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MAX);
		}
		return efProcessingMax;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingCount> getEfProcessingCount() {
		if (efProcessingCount == null) {
			efProcessingCount = new EObjectResolvingEList<EFProcessingCount>(EFProcessingCount.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_COUNT);
		}
		return efProcessingCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingSum> getEfProcessingSum() {
		if (efProcessingSum == null) {
			efProcessingSum = new EObjectResolvingEList<EFProcessingSum>(EFProcessingSum.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_SUM);
		}
		return efProcessingSum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingAvg> getEfProcessingAvg() {
		if (efProcessingAvg == null) {
			efProcessingAvg = new EObjectResolvingEList<EFProcessingAvg>(EFProcessingAvg.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_AVG);
		}
		return efProcessingAvg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFProcessingStDev> getEfProcessingStDev() {
		if (efProcessingStDev == null) {
			efProcessingStDev = new EObjectResolvingEList<EFProcessingStDev>(EFProcessingStDev.class, this, EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_ST_DEV);
		}
		return efProcessingStDev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFTemporalSequence> getEfTemporalSeq() {
		if (efTemporalSeq == null) {
			efTemporalSeq = new EObjectResolvingEList<EFTemporalSequence>(EFTemporalSequence.class, this, EventbasePackage.ROOT_DOCUMENT__EF_TEMPORAL_SEQ);
		}
		return efTemporalSeq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFChangeIncrease> getEfIncrease() {
		if (efIncrease == null) {
			efIncrease = new EObjectResolvingEList<EFChangeIncrease>(EFChangeIncrease.class, this, EventbasePackage.ROOT_DOCUMENT__EF_INCREASE);
		}
		return efIncrease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFChangeDecrease> getEfDecrease() {
		if (efDecrease == null) {
			efDecrease = new EObjectResolvingEList<EFChangeDecrease>(EFChangeDecrease.class, this, EventbasePackage.ROOT_DOCUMENT__EF_DECREASE);
		}
		return efDecrease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EFChangeRemain> getEfRemain() {
		if (efRemain == null) {
			efRemain = new EObjectResolvingEList<EFChangeRemain>(EFChangeRemain.class, this, EventbasePackage.ROOT_DOCUMENT__EF_REMAIN);
		}
		return efRemain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.ROOT_DOCUMENT__SEQUENCE_FLOW:
				return getSequenceFlow();
			case EventbasePackage.ROOT_DOCUMENT__EG_IMMEDIATE:
				return getEgImmediate();
			case EventbasePackage.ROOT_DOCUMENT__EG_ABSOLUTE:
				return getEgAbsolute();
			case EventbasePackage.ROOT_DOCUMENT__EG_OFFSET:
				return getEgOffset();
			case EventbasePackage.ROOT_DOCUMENT__EG_RELATIVE:
				return getEgRelative();
			case EventbasePackage.ROOT_DOCUMENT__EG_PERIODIC:
				return getEgPeriodic();
			case EventbasePackage.ROOT_DOCUMENT__EG_PATTERNED:
				return getEgPatterned();
			case EventbasePackage.ROOT_DOCUMENT__EG_DISTRIBUTION:
				return getEgDistribution();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_AND:
				return getEfLogicAnd();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_OR:
				return getEfLogicOr();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_NOT:
				return getEfLogicNot();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MIN:
				return getEfProcessingMin();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MAX:
				return getEfProcessingMax();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_COUNT:
				return getEfProcessingCount();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_SUM:
				return getEfProcessingSum();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_AVG:
				return getEfProcessingAvg();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_ST_DEV:
				return getEfProcessingStDev();
			case EventbasePackage.ROOT_DOCUMENT__EF_TEMPORAL_SEQ:
				return getEfTemporalSeq();
			case EventbasePackage.ROOT_DOCUMENT__EF_INCREASE:
				return getEfIncrease();
			case EventbasePackage.ROOT_DOCUMENT__EF_DECREASE:
				return getEfDecrease();
			case EventbasePackage.ROOT_DOCUMENT__EF_REMAIN:
				return getEfRemain();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EventbasePackage.ROOT_DOCUMENT__SEQUENCE_FLOW:
				getSequenceFlow().clear();
				getSequenceFlow().addAll((Collection<? extends ESequenceFlow>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_IMMEDIATE:
				getEgImmediate().clear();
				getEgImmediate().addAll((Collection<? extends EGImmediate>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_ABSOLUTE:
				getEgAbsolute().clear();
				getEgAbsolute().addAll((Collection<? extends EGAbsolute>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_OFFSET:
				getEgOffset().clear();
				getEgOffset().addAll((Collection<? extends EGOffset>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_RELATIVE:
				getEgRelative().clear();
				getEgRelative().addAll((Collection<? extends EGRelative>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_PERIODIC:
				getEgPeriodic().clear();
				getEgPeriodic().addAll((Collection<? extends EGPeriodic>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_PATTERNED:
				getEgPatterned().clear();
				getEgPatterned().addAll((Collection<? extends EGPatterned>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_DISTRIBUTION:
				getEgDistribution().clear();
				getEgDistribution().addAll((Collection<? extends EGDistribution>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_AND:
				getEfLogicAnd().clear();
				getEfLogicAnd().addAll((Collection<? extends EFLogicAnd>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_OR:
				getEfLogicOr().clear();
				getEfLogicOr().addAll((Collection<? extends EFLogicOr>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_NOT:
				getEfLogicNot().clear();
				getEfLogicNot().addAll((Collection<? extends EFLogicNot>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MIN:
				getEfProcessingMin().clear();
				getEfProcessingMin().addAll((Collection<? extends EFProcessingMin>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MAX:
				getEfProcessingMax().clear();
				getEfProcessingMax().addAll((Collection<? extends EFProcessingMax>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_COUNT:
				getEfProcessingCount().clear();
				getEfProcessingCount().addAll((Collection<? extends EFProcessingCount>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_SUM:
				getEfProcessingSum().clear();
				getEfProcessingSum().addAll((Collection<? extends EFProcessingSum>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_AVG:
				getEfProcessingAvg().clear();
				getEfProcessingAvg().addAll((Collection<? extends EFProcessingAvg>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_ST_DEV:
				getEfProcessingStDev().clear();
				getEfProcessingStDev().addAll((Collection<? extends EFProcessingStDev>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_TEMPORAL_SEQ:
				getEfTemporalSeq().clear();
				getEfTemporalSeq().addAll((Collection<? extends EFTemporalSequence>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_INCREASE:
				getEfIncrease().clear();
				getEfIncrease().addAll((Collection<? extends EFChangeIncrease>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_DECREASE:
				getEfDecrease().clear();
				getEfDecrease().addAll((Collection<? extends EFChangeDecrease>)newValue);
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_REMAIN:
				getEfRemain().clear();
				getEfRemain().addAll((Collection<? extends EFChangeRemain>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EventbasePackage.ROOT_DOCUMENT__SEQUENCE_FLOW:
				getSequenceFlow().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_IMMEDIATE:
				getEgImmediate().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_ABSOLUTE:
				getEgAbsolute().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_OFFSET:
				getEgOffset().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_RELATIVE:
				getEgRelative().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_PERIODIC:
				getEgPeriodic().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_PATTERNED:
				getEgPatterned().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EG_DISTRIBUTION:
				getEgDistribution().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_AND:
				getEfLogicAnd().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_OR:
				getEfLogicOr().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_NOT:
				getEfLogicNot().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MIN:
				getEfProcessingMin().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MAX:
				getEfProcessingMax().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_COUNT:
				getEfProcessingCount().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_SUM:
				getEfProcessingSum().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_AVG:
				getEfProcessingAvg().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_ST_DEV:
				getEfProcessingStDev().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_TEMPORAL_SEQ:
				getEfTemporalSeq().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_INCREASE:
				getEfIncrease().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_DECREASE:
				getEfDecrease().clear();
				return;
			case EventbasePackage.ROOT_DOCUMENT__EF_REMAIN:
				getEfRemain().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EventbasePackage.ROOT_DOCUMENT__SEQUENCE_FLOW:
				return sequenceFlow != null && !sequenceFlow.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_IMMEDIATE:
				return egImmediate != null && !egImmediate.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_ABSOLUTE:
				return egAbsolute != null && !egAbsolute.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_OFFSET:
				return egOffset != null && !egOffset.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_RELATIVE:
				return egRelative != null && !egRelative.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_PERIODIC:
				return egPeriodic != null && !egPeriodic.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_PATTERNED:
				return egPatterned != null && !egPatterned.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EG_DISTRIBUTION:
				return egDistribution != null && !egDistribution.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_AND:
				return efLogicAnd != null && !efLogicAnd.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_OR:
				return efLogicOr != null && !efLogicOr.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_LOGIC_NOT:
				return efLogicNot != null && !efLogicNot.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MIN:
				return efProcessingMin != null && !efProcessingMin.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_MAX:
				return efProcessingMax != null && !efProcessingMax.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_COUNT:
				return efProcessingCount != null && !efProcessingCount.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_SUM:
				return efProcessingSum != null && !efProcessingSum.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_AVG:
				return efProcessingAvg != null && !efProcessingAvg.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_PROCESSING_ST_DEV:
				return efProcessingStDev != null && !efProcessingStDev.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_TEMPORAL_SEQ:
				return efTemporalSeq != null && !efTemporalSeq.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_INCREASE:
				return efIncrease != null && !efIncrease.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_DECREASE:
				return efDecrease != null && !efDecrease.isEmpty();
			case EventbasePackage.ROOT_DOCUMENT__EF_REMAIN:
				return efRemain != null && !efRemain.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RootDocumentImpl
