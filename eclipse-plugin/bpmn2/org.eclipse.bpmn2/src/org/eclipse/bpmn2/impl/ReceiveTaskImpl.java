/**
 * <copyright>
 * 
 * Copyright (c) 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Reiner Hille-Doering (SAP AG) - initial API and implementation and/or initial documentation
 * 
 * </copyright>
 */
package org.eclipse.bpmn2.impl;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Receive Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#isInstantiate <em>Instantiate</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getMessageRef <em>Message Ref</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getOperationRef <em>Operation Ref</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getEventScript <em>Event Script</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getScriptHash <em>Script Hash</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.impl.ReceiveTaskImpl#getFileHash <em>File Hash</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReceiveTaskImpl extends TaskImpl implements ReceiveTask {
    /**
     * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected static final String IMPLEMENTATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected String implementation = IMPLEMENTATION_EDEFAULT;

    /**
     * The default value of the '{@link #isInstantiate() <em>Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInstantiate()
     * @generated
     * @ordered
     */
    protected static final boolean INSTANTIATE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isInstantiate() <em>Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInstantiate()
     * @generated
     * @ordered
     */
    protected boolean instantiate = INSTANTIATE_EDEFAULT;

    /**
     * The cached value of the '{@link #getMessageRef() <em>Message Ref</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessageRef()
     * @generated
     * @ordered
     */
    protected Message messageRef;

    /**
     * The cached value of the '{@link #getOperationRef() <em>Operation Ref</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperationRef()
     * @generated
     * @ordered
     */
    protected Operation operationRef;

    /**
     * The default value of the '{@link #getEventScript() <em>Event Script</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEventScript()
     * @generated
     * @ordered
     */
    protected static final String EVENT_SCRIPT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEventScript() <em>Event Script</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEventScript()
     * @generated
     * @ordered
     */
    protected String eventScript = EVENT_SCRIPT_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptHash() <em>Script Hash</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptHash()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_HASH_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getScriptHash() <em>Script Hash</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptHash()
     * @generated
     * @ordered
     */
    protected String scriptHash = SCRIPT_HASH_EDEFAULT;

    /**
     * The default value of the '{@link #getFileHash() <em>File Hash</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileHash()
     * @generated
     * @ordered
     */
    protected static final String FILE_HASH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFileHash() <em>File Hash</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileHash()
     * @generated
     * @ordered
     */
    protected String fileHash = FILE_HASH_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReceiveTaskImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Bpmn2Package.Literals.RECEIVE_TASK;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(String newImplementation) {
        String oldImplementation = implementation;
        implementation = newImplementation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__IMPLEMENTATION, oldImplementation, implementation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isInstantiate() {
        return instantiate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInstantiate(boolean newInstantiate) {
        boolean oldInstantiate = instantiate;
        instantiate = newInstantiate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__INSTANTIATE, oldInstantiate, instantiate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message getMessageRef() {
        if (messageRef != null && messageRef.eIsProxy()) {
            InternalEObject oldMessageRef = (InternalEObject) messageRef;
            messageRef = (Message) eResolveProxy(oldMessageRef);
            if (messageRef != oldMessageRef) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            Bpmn2Package.RECEIVE_TASK__MESSAGE_REF, oldMessageRef, messageRef));
            }
        }
        return messageRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message basicGetMessageRef() {
        return messageRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMessageRef(Message newMessageRef) {
        Message oldMessageRef = messageRef;
        messageRef = newMessageRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__MESSAGE_REF, oldMessageRef, messageRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Operation getOperationRef() {
        if (operationRef != null && operationRef.eIsProxy()) {
            InternalEObject oldOperationRef = (InternalEObject) operationRef;
            operationRef = (Operation) eResolveProxy(oldOperationRef);
            if (operationRef != oldOperationRef) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            Bpmn2Package.RECEIVE_TASK__OPERATION_REF, oldOperationRef, operationRef));
            }
        }
        return operationRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Operation basicGetOperationRef() {
        return operationRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOperationRef(Operation newOperationRef) {
        Operation oldOperationRef = operationRef;
        operationRef = newOperationRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__OPERATION_REF, oldOperationRef, operationRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEventScript() {
        return eventScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEventScript(String newEventScript) {
        String oldEventScript = eventScript;
        eventScript = newEventScript;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__EVENT_SCRIPT, oldEventScript, eventScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptHash() {
        return scriptHash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptHash(String newScriptHash) {
        String oldScriptHash = scriptHash;
        scriptHash = newScriptHash;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__SCRIPT_HASH, oldScriptHash, scriptHash));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFileHash(String newFileHash) {
        String oldFileHash = fileHash;
        fileHash = newFileHash;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Bpmn2Package.RECEIVE_TASK__FILE_HASH, oldFileHash, fileHash));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Bpmn2Package.RECEIVE_TASK__IMPLEMENTATION:
            return getImplementation();
        case Bpmn2Package.RECEIVE_TASK__INSTANTIATE:
            return isInstantiate();
        case Bpmn2Package.RECEIVE_TASK__MESSAGE_REF:
            if (resolve)
                return getMessageRef();
            return basicGetMessageRef();
        case Bpmn2Package.RECEIVE_TASK__OPERATION_REF:
            if (resolve)
                return getOperationRef();
            return basicGetOperationRef();
        case Bpmn2Package.RECEIVE_TASK__EVENT_SCRIPT:
            return getEventScript();
        case Bpmn2Package.RECEIVE_TASK__SCRIPT_HASH:
            return getScriptHash();
        case Bpmn2Package.RECEIVE_TASK__FILE_HASH:
            return getFileHash();
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
        case Bpmn2Package.RECEIVE_TASK__IMPLEMENTATION:
            setImplementation((String) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__INSTANTIATE:
            setInstantiate((Boolean) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__MESSAGE_REF:
            setMessageRef((Message) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__OPERATION_REF:
            setOperationRef((Operation) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__EVENT_SCRIPT:
            setEventScript((String) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__SCRIPT_HASH:
            setScriptHash((String) newValue);
            return;
        case Bpmn2Package.RECEIVE_TASK__FILE_HASH:
            setFileHash((String) newValue);
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
        case Bpmn2Package.RECEIVE_TASK__IMPLEMENTATION:
            setImplementation(IMPLEMENTATION_EDEFAULT);
            return;
        case Bpmn2Package.RECEIVE_TASK__INSTANTIATE:
            setInstantiate(INSTANTIATE_EDEFAULT);
            return;
        case Bpmn2Package.RECEIVE_TASK__MESSAGE_REF:
            setMessageRef((Message) null);
            return;
        case Bpmn2Package.RECEIVE_TASK__OPERATION_REF:
            setOperationRef((Operation) null);
            return;
        case Bpmn2Package.RECEIVE_TASK__EVENT_SCRIPT:
            setEventScript(EVENT_SCRIPT_EDEFAULT);
            return;
        case Bpmn2Package.RECEIVE_TASK__SCRIPT_HASH:
            setScriptHash(SCRIPT_HASH_EDEFAULT);
            return;
        case Bpmn2Package.RECEIVE_TASK__FILE_HASH:
            setFileHash(FILE_HASH_EDEFAULT);
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
        case Bpmn2Package.RECEIVE_TASK__IMPLEMENTATION:
            return IMPLEMENTATION_EDEFAULT == null ? implementation != null
                    : !IMPLEMENTATION_EDEFAULT.equals(implementation);
        case Bpmn2Package.RECEIVE_TASK__INSTANTIATE:
            return instantiate != INSTANTIATE_EDEFAULT;
        case Bpmn2Package.RECEIVE_TASK__MESSAGE_REF:
            return messageRef != null;
        case Bpmn2Package.RECEIVE_TASK__OPERATION_REF:
            return operationRef != null;
        case Bpmn2Package.RECEIVE_TASK__EVENT_SCRIPT:
            return EVENT_SCRIPT_EDEFAULT == null ? eventScript != null : !EVENT_SCRIPT_EDEFAULT
                    .equals(eventScript);
        case Bpmn2Package.RECEIVE_TASK__SCRIPT_HASH:
            return SCRIPT_HASH_EDEFAULT == null ? scriptHash != null : !SCRIPT_HASH_EDEFAULT
                    .equals(scriptHash);
        case Bpmn2Package.RECEIVE_TASK__FILE_HASH:
            return FILE_HASH_EDEFAULT == null ? fileHash != null : !FILE_HASH_EDEFAULT
                    .equals(fileHash);
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
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (implementation: ");
        result.append(implementation);
        result.append(", instantiate: ");
        result.append(instantiate);
        result.append(", eventScript: ");
        result.append(eventScript);
        result.append(", scriptHash: ");
        result.append(scriptHash);
        result.append(", fileHash: ");
        result.append(fileHash);
        result.append(')');
        return result.toString();
    }

} //ReceiveTaskImpl
