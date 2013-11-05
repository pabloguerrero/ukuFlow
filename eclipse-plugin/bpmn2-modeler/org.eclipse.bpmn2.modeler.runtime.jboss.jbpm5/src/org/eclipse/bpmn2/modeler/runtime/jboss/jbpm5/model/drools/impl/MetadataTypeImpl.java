/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl;

import java.util.Collection;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetadataTypeImpl#getMetaentry <em>Metaentry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetadataTypeImpl extends EObjectImpl implements MetadataType {
	/**
	 * The cached value of the '{@link #getMetaentry() <em>Metaentry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaentry()
	 * @generated
	 * @ordered
	 */
	protected EList<MetaentryType> metaentry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetadataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DroolsPackage.Literals.METADATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MetaentryType> getMetaentry() {
		if (metaentry == null) {
			metaentry = new EObjectContainmentEList<MetaentryType>(MetaentryType.class, this, DroolsPackage.METADATA_TYPE__METAENTRY);
		}
		return metaentry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DroolsPackage.METADATA_TYPE__METAENTRY:
				return ((InternalEList<?>)getMetaentry()).basicRemove(otherEnd, msgs);
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
			case DroolsPackage.METADATA_TYPE__METAENTRY:
				return getMetaentry();
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
			case DroolsPackage.METADATA_TYPE__METAENTRY:
				getMetaentry().clear();
				getMetaentry().addAll((Collection<? extends MetaentryType>)newValue);
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
			case DroolsPackage.METADATA_TYPE__METAENTRY:
				getMetaentry().clear();
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
			case DroolsPackage.METADATA_TYPE__METAENTRY:
				return metaentry != null && !metaentry.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MetadataTypeImpl
