/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui;

/**
 * @author Bob Brodt
 *
 */
public interface IConstants {

	public final String ICON_PATH = "icons/";

	public final String ICON_MESSAGE_16 = "obj16/message.gif"; //$NON-NLS-1$
	public final String ICON_MESSAGE_32 = "obj20/message.png"; //$NON-NLS-1$
	public final String ICON_OPERATION_16 = "obj16/operation.gif"; //$NON-NLS-1$
	public final String ICON_PART_16 = "obj16/message_part.gif";  //$NON-NLS-1$
	public final String ICON_PART_32 = "obj20/message_part.png"; //$NON-NLS-1$
	public final String ICON_PORTTYPE_16 = "obj16/wsdl_port_type.gif";  //$NON-NLS-1$
	public final String ICON_PORTTYPE_32 = "obj20/wsdl_port_type.png"; //$NON-NLS-1$
	public final String ICON_INPUT_16 = "obj16/input.gif"; //$NON-NLS-1$
	public final String ICON_INPUT_32 = "obj20/input.png"; //$NON-NLS-1$
	public final String ICON_OUTPUT_16 = "obj16/output.gif"; //$NON-NLS-1$
	public final String ICON_OUTPUT_32 = "obj20/output.png"; //$NON-NLS-1$
	public final String ICON_THROW_16 = "obj16/throw.gif"; //$NON-NLS-1$
	public final String ICON_THROW_32 = "obj20/throw.png"; //$NON-NLS-1$
	public final String ICON_CHECKBOX_CHECKED_16 = "obj16/checkbox-checked.png"; //$NON-NLS-1$
	public final String ICON_CHECKBOX_UNCHECKED_16 = "obj16/checkbox-unchecked.png"; //$NON-NLS-1$

	public final String ICON_XSD_ATTRIBUTE_DECLARATION_16 = "obj16/XSDAttributeDeclaration.gif"; //$NON-NLS-1$
	public final String ICON_XSD_ELEMENT_DECLARATION_16 = "obj16/XSDElementDeclaration.gif"; //$NON-NLS-1$
	public final String ICON_XSD_COMPLEX_TYPE_DEFINITION_16 = "obj16/XSDComplexTypeDefinition.gif"; //$NON-NLS-1$
	public final String ICON_XSD_SIMPLE_TYPE_DEFINITION_16 = "obj16/XSDSimpleTypeDefinition.gif"; //$NON-NLS-1$
	
	public final String IMAGE_PROCESS = "wizards/process.gif";
	public final String IMAGE_COLLABORATION = "wizards/collaboration.gif";
	public final String IMAGE_CHOREOGRAPHY = "wizards/choreography.gif";
	public final String IMAGE_PROCESS_PUSHED = "wizards/process-pushed.gif";
	public final String IMAGE_COLLABORATION_PUSHED = "wizards/collaboration-pushed.gif";
	public final String IMAGE_CHOREOGRAPHY_PUSHED = "wizards/choreography-pushed.gif";

	public final String ICON_BPMN2_PROCESS_16 = "obj16/bpmn2process.png"; //$NON-NLS-1$
	public final String ICON_BPMN2_INTERFACE_16 = "obj16/bpmn2interface.png"; //$NON-NLS-1$
	public final String ICON_BPMN2_OPERATION_16 = "obj16/bpmn2operation.png"; //$NON-NLS-1$
	public final String ICON_BPMN2_INPUT_16 = "obj16/bpmn2input.png"; //$NON-NLS-1$
	public final String ICON_BPMN2_OUTPUT_16 = "obj16/bpmn2output.png"; //$NON-NLS-1$
	public final String ICON_BPMN2_ERROR_16 = "obj16/bpmn2error.png"; //$NON-NLS-1$

	public final String ICON_JAVA_CLASS_16 = "obj16/javaClass.png"; //$NON-NLS-1$
	public final String ICON_JAVA_INTERFACE_16 = "obj16/javaInterface.png"; //$NON-NLS-1$
	public final String ICON_JAVA_PUBLIC_METHOD_16 = "obj16/javaPublicMethod.png"; //$NON-NLS-1$
	public final String ICON_JAVA_PUBLIC_FIELD_16 = "obj16/javaPublicField.png"; //$NON-NLS-1$
	
	public final String ICON_ADHOCSUBPROCESS = "16/AdHocSubProcess.png";
	public final String ICON_ASSOCIATION = "16/Association.png";
	public final String ICON_BOUNDARYEVENT = "16/BoundaryEvent.png";
	public final String ICON_BUSINESSRULETASK = "16/BusinessRuleTask.png";
	public final String ICON_CALLACTIVITY = "16/CallActivity.png";
	public final String ICON_CANCEL = "16/Cancel.png";
	public final String ICON_CANCELEVENTDEFINITION = "16/CancelEventDefinition.png";
	public final String ICON_CATEGORY = "16/Category.png";
	public final String ICON_CHOREOGRAPHYTASK = "16/ChoreographyTask.png";
	public final String ICON_COMPENSATEEVENTDEFINITION = "16/CompensateEventDefinition.png";
	public final String ICON_COMPLEXGATEWAY = "16/ComplexGateway.png";
	public final String ICON_CONDITIONALEVENTDEFINITION = "16/ConditionalEventDefinition.png";
	public final String ICON_CONVERSATION = "16/Conversation.png";
	public final String ICON_CONVERSATIONLINK = "16/ConversationLink.png";
	public final String ICON_CUSTOMTASK = "16/CustomTask.png";
	public final String ICON_DATAASSOCIATION = "16/DataAssociation.png";
	public final String ICON_DATAINPUTASSOCIATION = "16/DataInputAssociation.png";
	public final String ICON_DATAOUTPUTASSOCIATION = "16/DataOutputAssociation.png";
	public final String ICON_DATAINPUT = "16/DataInput.png";
	public final String ICON_DATAOBJECT = "16/DataObject.png";
	public final String ICON_DATAOUTPUT = "16/DataOutput.png";
	public final String ICON_DATASTORE = "16/DataStore.png";
	public final String ICON_DATASTOREREFERENCE = "16/DataStoreReference.png";
	public final String ICON_ENDEVENT = "16/EndEvent.png";
	public final String ICON_ERROR = "16/Error.png";
	public final String ICON_ERROREVENTDEFINITION = "16/ErrorEventDefinition.png";
	public final String ICON_ESCALATION = "16/Escalation.png";
	public final String ICON_ESCALATIONEVENTDEFINITION = "16/EscalationEventDefinition.png";
	public final String ICON_EVENTBASEDGATEWAY = "16/EventBasedGateway.png";
	public final String ICON_EXCLUSIVEGATEWAY = "16/ExclusiveGateway.png";
	public final String ICON_GLOBALBUSINESSRULETASK = "16/GlobalBusinessRuleTask.png";
	public final String ICON_GLOBALMANUALTASK = "16/GlobalManualTask.png";
	public final String ICON_GLOBALSCRIPTTASK = "16/GlobalScriptTask.png";
	public final String ICON_GLOBALUSERTASK = "16/GlobalUserTask.png";
	public final String ICON_GROUP = "16/Group.png";
	public final String ICON_INCLUSIVEGATEWAY = "16/InclusiveGateway.png";
	public final String ICON_INTERMEDIATECATCHEVENT = "16/IntermediateCatchEvent.png";
	public final String ICON_INTERMEDIATETHROWEVENT = "16/IntermediateThrowEvent.png";
	public final String ICON_ITEMDEFINITION = "16/ItemDefinition.png";
	public final String ICON_LANE = "16/Lane.png";
	public final String ICON_LINKEVENTDEFINITION = "16/LinkEventDefinition.png";
	public final String ICON_MANUALTASK = "16/ManualTask.png";
	public final String ICON_MESSAGE = "16/Message.png";
	public final String ICON_INMESSAGE = "16/InMessage.png";
	public final String ICON_OUTMESSAGE = "16/OutMessage.png";
	public final String ICON_MESSAGEEVENTDEFINITION = "16/MessageEventDefinition.png";
	public final String ICON_MESSAGEFLOW = "16/MessageFlow.png";
	public final String ICON_OPERATION = "16/Operation.png";
	public final String ICON_PARALLELGATEWAY = "16/ParallelGateway.png";
	public final String ICON_PARTICIPANT = "16/Participant.png";
	public final String ICON_RECEIVETASK = "16/ReceiveTask.png";
	public final String ICON_SCRIPTTASK = "16/ScriptTask.png";
	public final String ICON_SENDTASK = "16/SendTask.png";
	public final String ICON_SEQUENCEFLOW = "16/SequenceFlow.png";
	public final String ICON_SERVICETASK = "16/ServiceTask.png";
	public final String ICON_SIGNAL = "16/Signal.png";
	public final String ICON_SIGNALEVENTDEFINITION = "16/SignalEventDefinition.png";
	public final String ICON_STARTEVENT = "16/StartEvent.png";
	public final String ICON_SUBPROCESS = "16/SubProcess.png";
	public final String ICON_TASK = "16/Task.png";
	public final String ICON_TERMINATEEVENTDEFINITION = "16/TerminateEventDefinition.png";
	public final String ICON_TEXTANNOTATION = "16/TextAnnotation.png";
	public final String ICON_TIMEREVENTDEFINITION = "16/TimerEventDefinition.png";
	public final String ICON_TRANSACTION = "16/Transaction.png";
	public final String ICON_USERTASK = "16/UserTask.png";
	public final String ICON_BPMNSHAPE = "16/BPMNShape.png";
	public final String ICON_BPMNEDGE = "16/BPMNEdge.png";
	public final String ICON_BPMNDIAGRAM = "16/BPMNDiagram.png";
	public final String ICON_BPMNSUBDIAGRAM = "16/BPMNSubDiagram.png";
	public final String ICON_PROCESS = "16/Process.png";
	public final String ICON_RESOURCE = "16/Resource.png";
	public final String ICON_COLLABORATION = "16/Collaboration.png";
	public final String ICON_CHOREOGRAPHY = "16/Choreography.png";
	public final String ICON_INTERFACE = "16/Interface.png";
	public final String ICON_PARTNERROLE = "16/PartnerRole.png";
	public final String ICON_PARTNERENTITY = "16/PartnerEntity.png";

	public final String ICON_BUSINESS_MODEL = "20/BusinessModel.png";
	public final String ICON_INTERCHANGE_MODEL = "20/InterchangeModel.png";
	public final String ICON_THUMBNAIL = "20/Thumbnail.png";
}
