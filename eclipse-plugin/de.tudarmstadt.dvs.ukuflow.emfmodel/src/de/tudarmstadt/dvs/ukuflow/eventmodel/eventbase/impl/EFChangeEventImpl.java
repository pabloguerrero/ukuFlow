/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EF Change Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl#getWindowSize <em>Window Size</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EFChangeEventImpl#getChangeThreshold <em>Change Threshold</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EFChangeEventImpl extends EFCompositeImpl implements EFChangeEvent {
	/**
	 * The default value of the '{@link #getWindowSize() <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowSize()
	 * @generated
	 * @ordered
	 */
	protected static final String WINDOW_SIZE_EDEFAULT = "1:00";
	/**
	 * The cached value of the '{@link #getWindowSize() <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowSize()
	 * @generated
	 * @ordered
	 */
	protected String windowSize = WINDOW_SIZE_EDEFAULT;
	/**
	 * The default value of the '{@link #getChangeThreshold() <em>Change Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeThreshold()
	 * @generated
	 * @ordered
	 */
	protected static final int CHANGE_THRESHOLD_EDEFAULT = 10;
	/**
	 * The cached value of the '{@link #getChangeThreshold() <em>Change Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeThreshold()
	 * @generated
	 * @ordered
	 */
	protected int changeThreshold = CHANGE_THRESHOLD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EFChangeEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EF_CHANGE_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWindowSize() {
		return windowSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowSize(String newWindowSize) {
		String oldWindowSize = windowSize;
		windowSize = newWindowSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE, oldWindowSize, windowSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getChangeThreshold() {
		return changeThreshold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangeThreshold(int newChangeThreshold) {
		int oldChangeThreshold = changeThreshold;
		changeThreshold = newChangeThreshold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD, oldChangeThreshold, changeThreshold));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE:
				return getWindowSize();
			case EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD:
				return getChangeThreshold();
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
			case EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE:
				setWindowSize((String)newValue);
				return;
			case EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD:
				setChangeThreshold((Integer)newValue);
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
			case EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE:
				setWindowSize(WINDOW_SIZE_EDEFAULT);
				return;
			case EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD:
				setChangeThreshold(CHANGE_THRESHOLD_EDEFAULT);
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
			case EventbasePackage.EF_CHANGE_EVENT__WINDOW_SIZE:
				return WINDOW_SIZE_EDEFAULT == null ? windowSize != null : !WINDOW_SIZE_EDEFAULT.equals(windowSize);
			case EventbasePackage.EF_CHANGE_EVENT__CHANGE_THRESHOLD:
				return changeThreshold != CHANGE_THRESHOLD_EDEFAULT;
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
		result.append(" (windowSize: ");
		result.append(windowSize);
		result.append(", changeThreshold: ");
		result.append(changeThreshold);
		result.append(')');
		return result.toString();
	}

} //EFChangeEventImpl
