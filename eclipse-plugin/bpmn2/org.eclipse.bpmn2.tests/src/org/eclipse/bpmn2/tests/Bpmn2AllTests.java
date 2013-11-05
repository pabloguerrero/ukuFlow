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

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import org.eclipse.bpmn2.di.tests.BpmnDiTests;

import org.eclipse.dd.dc.tests.DcTests;

import org.eclipse.dd.di.tests.DiTests;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Bpmn2</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class Bpmn2AllTests extends TestSuite {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Test suite() {
        TestSuite suite = new Bpmn2AllTests("Bpmn2 Tests");
        suite.addTest(Bpmn2Tests.suite());
        suite.addTest(BpmnDiTests.suite());
        suite.addTest(DiTests.suite());
        suite.addTest(DcTests.suite());
        return suite;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bpmn2AllTests(String name) {
        super(name);
    }

} //Bpmn2AllTests
