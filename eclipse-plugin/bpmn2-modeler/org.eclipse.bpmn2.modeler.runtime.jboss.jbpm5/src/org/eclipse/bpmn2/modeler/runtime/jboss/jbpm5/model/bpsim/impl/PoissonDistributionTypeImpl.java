/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.impl;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PoissonDistributionType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Poisson Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.impl.PoissonDistributionTypeImpl#getMean <em>Mean</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PoissonDistributionTypeImpl extends DistributionParameterImpl implements PoissonDistributionType {
	/**
	 * The default value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected double mean = MEAN_EDEFAULT;

	/**
	 * This is true if the Mean attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean meanESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PoissonDistributionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BpsimPackage.Literals.POISSON_DISTRIBUTION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMean(double newMean) {
		double oldMean = mean;
		mean = newMean;
		boolean oldMeanESet = meanESet;
		meanESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN, oldMean, mean, !oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMean() {
		double oldMean = mean;
		boolean oldMeanESet = meanESet;
		mean = MEAN_EDEFAULT;
		meanESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN, oldMean, MEAN_EDEFAULT, oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMean() {
		return meanESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN:
				return getMean();
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
			case BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN:
				setMean((Double)newValue);
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
			case BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN:
				unsetMean();
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
			case BpsimPackage.POISSON_DISTRIBUTION_TYPE__MEAN:
				return isSetMean();
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
		result.append(" (mean: ");
		if (meanESet) result.append(mean); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PoissonDistributionTypeImpl
