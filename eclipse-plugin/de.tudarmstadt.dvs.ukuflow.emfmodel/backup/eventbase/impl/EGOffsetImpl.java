/**
 */
package eventbase.impl;

import eventbase.EGOffset;
import eventbase.EventbasePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EG Offset</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eventbase.impl.EGOffsetImpl#getOffsetTime <em>Offset Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EGOffsetImpl extends EGNonRecurringImpl implements EGOffset {
	/**
	 * The default value of the '{@link #getOffsetTime() <em>Offset Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffsetTime()
	 * @generated
	 * @ordered
	 */
	protected static final int OFFSET_TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOffsetTime() <em>Offset Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffsetTime()
	 * @generated
	 * @ordered
	 */
	protected int offsetTime = OFFSET_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EGOffsetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EG_OFFSET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOffsetTime() {
		return offsetTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffsetTime(int newOffsetTime) {
		int oldOffsetTime = offsetTime;
		offsetTime = newOffsetTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_OFFSET__OFFSET_TIME, oldOffsetTime, offsetTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EG_OFFSET__OFFSET_TIME:
				return getOffsetTime();
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
			case EventbasePackage.EG_OFFSET__OFFSET_TIME:
				setOffsetTime((Integer)newValue);
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
			case EventbasePackage.EG_OFFSET__OFFSET_TIME:
				setOffsetTime(OFFSET_TIME_EDEFAULT);
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
			case EventbasePackage.EG_OFFSET__OFFSET_TIME:
				return offsetTime != OFFSET_TIME_EDEFAULT;
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
		result.append(" (offsetTime: ");
		result.append(offsetTime);
		result.append(')');
		return result.toString();
	}

} //EGOffsetImpl
