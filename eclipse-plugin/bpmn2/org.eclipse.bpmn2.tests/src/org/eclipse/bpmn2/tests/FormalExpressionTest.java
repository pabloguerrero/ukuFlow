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
import org.eclipse.bpmn2.FormalExpression;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Formal Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.FormalExpression#getBody() <em>Body</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class FormalExpressionTest extends ExpressionTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(FormalExpressionTest.class);
    }

    /**
     * Constructs a new Formal Expression test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormalExpressionTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Formal Expression test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected FormalExpression getFixture() {
        return (FormalExpression) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#setUp()
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(Bpmn2Factory.eINSTANCE.createFormalExpression());
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

    /**
     * Tests the '{@link org.eclipse.bpmn2.FormalExpression#getBody() <em>Body</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.bpmn2.FormalExpression#getBody()
     * @generated
     */
    public void testGetBody() {
        // TODO: implement this feature getter test method
        // Ensure that you remove @generated or mark it @generated NOT
        fail();
    }

    /**
     * Tests the '{@link org.eclipse.bpmn2.FormalExpression#setBody(java.lang.String) <em>Body</em>}' feature setter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.bpmn2.FormalExpression#setBody(java.lang.String)
     * @generated
     */
    public void testSetBody() {
        // TODO: implement this feature setter test method
        // Ensure that you remove @generated or mark it @generated NOT
        fail();
    }

} //FormalExpressionTest
