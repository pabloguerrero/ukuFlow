/**
 */
package eventbase.impl;

import eventbase.ESimpleEF;
import eventbase.ESimpleFilterConstraint;
import eventbase.EventBaseOperator;
import eventbase.EventbasePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ESimple EF</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eventbase.impl.ESimpleEFImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link eventbase.impl.ESimpleEFImpl#getSourceEvent <em>Source Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ESimpleEFImpl extends EventBaseOperatorImpl implements ESimpleEF {
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
	protected ESimpleEFImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.ESIMPLE_EF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ESimpleFilterConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectResolvingEList<ESimpleFilterConstraint>(ESimpleFilterConstraint.class, this, EventbasePackage.ESIMPLE_EF__CONSTRAINTS);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EventbasePackage.ESIMPLE_EF__SOURCE_EVENT, oldSourceEvent, sourceEvent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.ESIMPLE_EF__SOURCE_EVENT, oldSourceEvent, sourceEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.ESIMPLE_EF__CONSTRAINTS:
				return getConstraints();
			case EventbasePackage.ESIMPLE_EF__SOURCE_EVENT:
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
			case EventbasePackage.ESIMPLE_EF__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends ESimpleFilterConstraint>)newValue);
				return;
			case EventbasePackage.ESIMPLE_EF__SOURCE_EVENT:
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
			case EventbasePackage.ESIMPLE_EF__CONSTRAINTS:
				getConstraints().clear();
				return;
			case EventbasePackage.ESIMPLE_EF__SOURCE_EVENT:
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
			case EventbasePackage.ESIMPLE_EF__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case EventbasePackage.ESIMPLE_EF__SOURCE_EVENT:
				return sourceEvent != null;
		}
		return super.eIsSet(featureID);
	}

} //ESimpleEFImpl
