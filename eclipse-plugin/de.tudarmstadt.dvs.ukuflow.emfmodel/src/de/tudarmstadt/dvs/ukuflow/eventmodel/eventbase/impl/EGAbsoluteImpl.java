/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EG Absolute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGAbsoluteImpl#getAbsoluteTime <em>Absolute Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EGAbsoluteImpl extends EGNonRecurringImpl implements EGAbsolute {
	/**
	 * The default value of the '{@link #getAbsoluteTime() <em>Absolute Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbsoluteTime()
	 * @generated
	 * @ordered
	 */
	protected static final String ABSOLUTE_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAbsoluteTime() <em>Absolute Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbsoluteTime()
	 * @generated
	 * @ordered
	 */
	protected String absoluteTime = ABSOLUTE_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EGAbsoluteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EG_ABSOLUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAbsoluteTime() {
		return absoluteTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbsoluteTime(String newAbsoluteTime) {
		String oldAbsoluteTime = absoluteTime;
		absoluteTime = newAbsoluteTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME, oldAbsoluteTime, absoluteTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME:
				return getAbsoluteTime();
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
			case EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME:
				setAbsoluteTime((String)newValue);
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
			case EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME:
				setAbsoluteTime(ABSOLUTE_TIME_EDEFAULT);
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
			case EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME:
				return ABSOLUTE_TIME_EDEFAULT == null ? absoluteTime != null : !ABSOLUTE_TIME_EDEFAULT.equals(absoluteTime);
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
		result.append(" (absoluteTime: ");
		result.append(absoluteTime);
		result.append(')');
		return result.toString();
	}

} //EGAbsoluteImpl
