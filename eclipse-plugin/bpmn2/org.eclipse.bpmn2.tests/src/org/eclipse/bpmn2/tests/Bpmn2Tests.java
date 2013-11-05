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

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>bpmn2</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class Bpmn2Tests extends TestSuite {

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
        TestSuite suite = new Bpmn2Tests("bpmn2 Tests");
        suite.addTestSuite(DocumentRootTest.class);
        suite.addTestSuite(ActivityTest.class);
        suite.addTestSuite(AdHocSubProcessTest.class);
        suite.addTestSuite(BoundaryEventTest.class);
        suite.addTestSuite(BusinessRuleTaskTest.class);
        suite.addTestSuite(CallActivityTest.class);
        suite.addTestSuite(CallChoreographyTest.class);
        suite.addTestSuite(CallConversationTest.class);
        suite.addTestSuite(CategoryValueTest.class);
        suite.addTestSuite(ChoreographyTaskTest.class);
        suite.addTestSuite(ComplexGatewayTest.class);
        suite.addTestSuite(ConversationTest.class);
        suite.addTestSuite(ConversationNodeTest.class);
        suite.addTestSuite(DataInputTest.class);
        suite.addTestSuite(DataOutputTest.class);
        suite.addTestSuite(DocumentationTest.class);
        suite.addTestSuite(EndEventTest.class);
        suite.addTestSuite(EventBasedGatewayTest.class);
        suite.addTestSuite(ExclusiveGatewayTest.class);
        suite.addTestSuite(ExtensionAttributeDefinitionTest.class);
        suite.addTestSuite(ExtensionAttributeValueTest.class);
        suite.addTestSuite(ExtensionDefinitionTest.class);
        suite.addTestSuite(FormalExpressionTest.class);
        suite.addTestSuite(ImplicitThrowEventTest.class);
        suite.addTestSuite(InclusiveGatewayTest.class);
        suite.addTestSuite(InteractionNodeTest.class);
        suite.addTestSuite(IntermediateCatchEventTest.class);
        suite.addTestSuite(IntermediateThrowEventTest.class);
        suite.addTestSuite(ItemDefinitionTest.class);
        suite.addTestSuite(ManualTaskTest.class);
        suite.addTestSuite(ParallelGatewayTest.class);
        suite.addTestSuite(ParticipantTest.class);
        suite.addTestSuite(ReceiveTaskTest.class);
        suite.addTestSuite(ScriptTaskTest.class);
        suite.addTestSuite(SendTaskTest.class);
        suite.addTestSuite(ServiceTaskTest.class);
        suite.addTestSuite(StartEventTest.class);
        suite.addTestSuite(SubChoreographyTest.class);
        suite.addTestSuite(SubConversationTest.class);
        suite.addTestSuite(SubProcessTest.class);
        suite.addTestSuite(TaskTest.class);
        suite.addTestSuite(TransactionTest.class);
        suite.addTestSuite(UserTaskTest.class);
        return suite;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bpmn2Tests(String name) {
        super(name);
    }

} //Bpmn2Tests
