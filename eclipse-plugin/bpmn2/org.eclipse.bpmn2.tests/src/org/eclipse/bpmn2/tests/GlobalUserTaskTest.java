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
package org.eclipse.bpmn2.tests;

import junit.textui.TestRunner;

import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.GlobalUserTask;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Global User Task</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalUserTaskTest extends GlobalTaskTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(GlobalUserTaskTest.class);
    }

    /**
     * Constructs a new Global User Task test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalUserTaskTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Global User Task test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected GlobalUserTask getFixture() {
        return (GlobalUserTask) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#setUp()
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(Bpmn2Factory.eINSTANCE.createGlobalUserTask());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#tearDown()
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }

} //GlobalUserTaskTest
