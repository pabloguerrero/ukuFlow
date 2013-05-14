/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESimpleFilterConstraint;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EF Simple</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFSimpleImpl#getSourceEvent <em>Source Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EFSimpleImpl extends EventBaseOperatorImpl implements EFSimple {
	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<ESimpleFilterConstraint> constraints;

	/**
	 * The cached value of the '{@link #getSourceEvent() <em>Source Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceEvent()
	 * @generated
	 * @ordered
	 */
	protected EventBaseOperator sourceEvent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EFSimpleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EF_SIMPLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ESimpleFilterConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectResolvingEList<ESimpleFilterConstraint>(ESimpleFilterConstraint.class, this, EventbasePackage.EF_SIMPLE__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventBaseOperator getSourceEvent() {
		if (sourceEvent != null && sourceEvent.eIsProxy()) {
			InternalEObject oldSourceEvent = (InternalEObject)sourceEvent;
			sourceEvent = (EventBaseOperator)eResolveProxy(oldSourceEvent);
			if (sourceEvent != oldSourceEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EventbasePackage.EF_SIMPLE__SOURCE_EVENT, oldSourceEvent, sourceEvent));
			}
		}
		return sourceEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventBaseOperator basicGetSourceEvent() {
		return sourceEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceEvent(EventBaseOperator newSourceEvent) {
		EventBaseOperator oldSourceEvent = sourceEvent;
		sourceEvent = newSourceEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EF_SIMPLE__SOURCE_EVENT, oldSourceEvent, sourceEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EF_SIMPLE__CONSTRAINTS:
				return getConstraints();
			case EventbasePackage.EF_SIMPLE__SOURCE_EVENT:
				if (resolve) return getSourceEvent();
				return basicGetSourceEvent();
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
			case EventbasePackage.EF_SIMPLE__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends ESimpleFilterConstraint>)newValue);
				return;
			case EventbasePackage.EF_SIMPLE__SOURCE_EVENT:
				setSourceEvent((EventBaseOperator)newValue);
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
			case EventbasePackage.EF_SIMPLE__CONSTRAINTS:
				getConstraints().clear();
				return;
			case EventbasePackage.EF_SIMPLE__SOURCE_EVENT:
				setSourceEvent((EventBaseOperator)null);
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
			case EventbasePackage.EF_SIMPLE__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case EventbasePackage.EF_SIMPLE__SOURCE_EVENT:
				return sourceEvent != null;
		}
		return super.eIsSet(featureID);
	}

} //EFSimpleImpl
