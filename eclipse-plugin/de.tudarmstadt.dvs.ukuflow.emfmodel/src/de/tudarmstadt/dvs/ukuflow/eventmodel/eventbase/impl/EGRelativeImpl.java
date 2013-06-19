/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EG Relative</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGRelativeImpl#getDelayTime <em>Delay Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EGRelativeImpl extends EGNonRecurringImpl implements EGRelative {
	/**
	 * The default value of the '{@link #getDelayTime() <em>Delay Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelayTime()
	 * @generated
	 * @ordered
	 */
	protected static final int DELAY_TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDelayTime() <em>Delay Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelayTime()
	 * @generated
	 * @ordered
	 */
	protected int delayTime = DELAY_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EGRelativeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EG_RELATIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDelayTime() {
		return delayTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDelayTime(int newDelayTime) {
		int oldDelayTime = delayTime;
		delayTime = newDelayTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_RELATIVE__DELAY_TIME, oldDelayTime, delayTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EG_RELATIVE__DELAY_TIME:
				return getDelayTime();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EventbasePackage.EG_RELATIVE__DELAY_TIME:
				setDelayTime((Integer)newValue);
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
			case EventbasePackage.EG_RELATIVE__DELAY_TIME:
				setDelayTime(DELAY_TIME_EDEFAULT);
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
			case EventbasePackage.EG_RELATIVE__DELAY_TIME:
				return delayTime != DELAY_TIME_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (delayTime: ");
		result.append(delayTime);
		result.append(')');
		return result.toString();
	}

} //EGRelativeImpl
