/**
 */
package de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EG Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl#getPeriodInterval <em>Period Interval</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EGDistributionImpl#getEvaluationInterval <em>Evaluation Interval</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EGDistributionImpl extends EGRecurringImpl implements EGDistribution {
	/**
	 * The default value of the '{@link #getPeriodInterval() <em>Period Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriodInterval()
	 * @generated
	 * @ordered
	 */
	protected static final String PERIOD_INTERVAL_EDEFAULT = "01:00";

	/**
	 * The cached value of the '{@link #getPeriodInterval() <em>Period Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriodInterval()
	 * @generated
	 * @ordered
	 */
	protected String periodInterval = PERIOD_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String FUNCTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected String function = FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected String parameters = PARAMETERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEvaluationInterval() <em>Evaluation Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvaluationInterval()
	 * @generated
	 * @ordered
	 */
	protected static final String EVALUATION_INTERVAL_EDEFAULT = "01:00";

	/**
	 * The cached value of the '{@link #getEvaluationInterval() <em>Evaluation Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvaluationInterval()
	 * @generated
	 * @ordered
	 */
	protected String evaluationInterval = EVALUATION_INTERVAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EGDistributionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventbasePackage.Literals.EG_DISTRIBUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPeriodInterval() {
		return periodInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPeriodInterval(String newPeriodInterval) {
		String oldPeriodInterval = periodInterval;
		periodInterval = newPeriodInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL, oldPeriodInterval, periodInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(String newFunction) {
		String oldFunction = function;
		function = newFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_DISTRIBUTION__FUNCTION, oldFunction, function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameters(String newParameters) {
		String oldParameters = parameters;
		parameters = newParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_DISTRIBUTION__PARAMETERS, oldParameters, parameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEvaluationInterval() {
		return evaluationInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvaluationInterval(String newEvaluationInterval) {
		String oldEvaluationInterval = evaluationInterval;
		evaluationInterval = newEvaluationInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL, oldEvaluationInterval, evaluationInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL:
				return getPeriodInterval();
			case EventbasePackage.EG_DISTRIBUTION__FUNCTION:
				return getFunction();
			case EventbasePackage.EG_DISTRIBUTION__PARAMETERS:
				return getParameters();
			case EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL:
				return getEvaluationInterval();
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
			case EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL:
				setPeriodInterval((String)newValue);
				return;
			case EventbasePackage.EG_DISTRIBUTION__FUNCTION:
				setFunction((String)newValue);
				return;
			case EventbasePackage.EG_DISTRIBUTION__PARAMETERS:
				setParameters((String)newValue);
				return;
			case EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL:
				setEvaluationInterval((String)newValue);
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
			case EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL:
				setPeriodInterval(PERIOD_INTERVAL_EDEFAULT);
				return;
			case EventbasePackage.EG_DISTRIBUTION__FUNCTION:
				setFunction(FUNCTION_EDEFAULT);
				return;
			case EventbasePackage.EG_DISTRIBUTION__PARAMETERS:
				setParameters(PARAMETERS_EDEFAULT);
				return;
			case EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL:
				setEvaluationInterval(EVALUATION_INTERVAL_EDEFAULT);
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
			case EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL:
				return PERIOD_INTERVAL_EDEFAULT == null ? periodInterval != null : !PERIOD_INTERVAL_EDEFAULT.equals(periodInterval);
			case EventbasePackage.EG_DISTRIBUTION__FUNCTION:
				return FUNCTION_EDEFAULT == null ? function != null : !FUNCTION_EDEFAULT.equals(function);
			case EventbasePackage.EG_DISTRIBUTION__PARAMETERS:
				return PARAMETERS_EDEFAULT == null ? parameters != null : !PARAMETERS_EDEFAULT.equals(parameters);
			case EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL:
				return EVALUATION_INTERVAL_EDEFAULT == null ? evaluationInterval != null : !EVALUATION_INTERVAL_EDEFAULT.equals(evaluationInterval);
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
		result.append(" (periodInterval: ");
		result.append(periodInterval);
		result.append(", function: ");
		result.append(function);
		result.append(", parameters: ");
		result.append(parameters);
		result.append(", evaluationInterval: ");
		result.append(evaluationInterval);
		result.append(')');
		return result.toString();
	}

} //EGDistributionImpl
