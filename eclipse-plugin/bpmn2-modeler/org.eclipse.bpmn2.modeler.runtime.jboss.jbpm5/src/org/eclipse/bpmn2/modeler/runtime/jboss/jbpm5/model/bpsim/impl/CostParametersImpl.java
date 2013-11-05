/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.impl;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Parameter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cost Parameters</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.impl.CostParametersImpl#getFixedCost <em>Fixed Cost</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.impl.CostParametersImpl#getUnitCost <em>Unit Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CostParametersImpl extends EObjectImpl implements CostParameters {
	/**
	 * The cached value of the '{@link #getFixedCost() <em>Fixed Cost</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedCost()
	 * @generated
	 * @ordered
	 */
	protected Parameter fixedCost;

	/**
	 * The cached value of the '{@link #getUnitCost() <em>Unit Cost</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnitCost()
	 * @generated
	 * @ordered
	 */
	protected Parameter unitCost;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CostParametersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BpsimPackage.Literals.COST_PARAMETERS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getFixedCost() {
		return fixedCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFixedCost(Parameter newFixedCost, NotificationChain msgs) {
		Parameter oldFixedCost = fixedCost;
		fixedCost = newFixedCost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpsimPackage.COST_PARAMETERS__FIXED_COST, oldFixedCost, newFixedCost);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFixedCost(Parameter newFixedCost) {
		if (newFixedCost != fixedCost) {
			NotificationChain msgs = null;
			if (fixedCost != null)
				msgs = ((InternalEObject)fixedCost).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BpsimPackage.COST_PARAMETERS__FIXED_COST, null, msgs);
			if (newFixedCost != null)
				msgs = ((InternalEObject)newFixedCost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BpsimPackage.COST_PARAMETERS__FIXED_COST, null, msgs);
			msgs = basicSetFixedCost(newFixedCost, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpsimPackage.COST_PARAMETERS__FIXED_COST, newFixedCost, newFixedCost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getUnitCost() {
		return unitCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnitCost(Parameter newUnitCost, NotificationChain msgs) {
		Parameter oldUnitCost = unitCost;
		unitCost = newUnitCost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpsimPackage.COST_PARAMETERS__UNIT_COST, oldUnitCost, newUnitCost);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnitCost(Parameter newUnitCost) {
		if (newUnitCost != unitCost) {
			NotificationChain msgs = null;
			if (unitCost != null)
				msgs = ((InternalEObject)unitCost).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BpsimPackage.COST_PARAMETERS__UNIT_COST, null, msgs);
			if (newUnitCost != null)
				msgs = ((InternalEObject)newUnitCost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BpsimPackage.COST_PARAMETERS__UNIT_COST, null, msgs);
			msgs = basicSetUnitCost(newUnitCost, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpsimPackage.COST_PARAMETERS__UNIT_COST, newUnitCost, newUnitCost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BpsimPackage.COST_PARAMETERS__FIXED_COST:
				return basicSetFixedCost(null, msgs);
			case BpsimPackage.COST_PARAMETERS__UNIT_COST:
				return basicSetUnitCost(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BpsimPackage.COST_PARAMETERS__FIXED_COST:
				return getFixedCost();
			case BpsimPackage.COST_PARAMETERS__UNIT_COST:
				return getUnitCost();
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
			case BpsimPackage.COST_PARAMETERS__FIXED_COST:
				setFixedCost((Parameter)newValue);
				return;
			case BpsimPackage.COST_PARAMETERS__UNIT_COST:
				setUnitCost((Parameter)newValue);
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
			case BpsimPackage.COST_PARAMETERS__FIXED_COST:
				setFixedCost((Parameter)null);
				return;
			case BpsimPackage.COST_PARAMETERS__UNIT_COST:
				setUnitCost((Parameter)null);
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
			case BpsimPackage.COST_PARAMETERS__FIXED_COST:
				return fixedCost != null;
			case BpsimPackage.COST_PARAMETERS__UNIT_COST:
				return unitCost != null;
		}
		return super.eIsSet(featureID);
	}

} //CostParametersImpl
