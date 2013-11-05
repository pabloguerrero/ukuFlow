/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.core.validation;

import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.ChoreographyTask;
import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.ComplexGateway;
import org.eclipse.bpmn2.ConditionalEventDefinition;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.bpmn2.EscalationEventDefinition;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.EventBasedGateway;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.Resource;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Signal;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

public class BPMN2ValidationConstraints extends AbstractModelConstraint {

	public final static String ERROR_ID = "org.eclipse.bpmn2.modeler.core.validation.error";
	public final static String WARNING_ID = "org.eclipse.bpmn2.modeler.core.validation.warning";
	
	private boolean warnings = false;
	
	public BPMN2ValidationConstraints() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		EMFEventType eType = ctx.getEventType();
		String id = ctx.getCurrentConstraintId();
		if (WARNING_ID.equals(id))
			warnings = true;
		else
			warnings = false;
		
		// In the case of batch mode.
		if (eType == EMFEventType.NULL) {
			if (eObj instanceof Definitions) {
				return validateDefinitions(ctx, (Definitions) eObj);
			}
			if (eObj instanceof BaseElement) {
				return validateBaseElement(ctx, (BaseElement) eObj);
			}
		} else { // In the case of live mode.
			if (eObj instanceof BaseElement) {
				return validateBaseElementLive(ctx, (BaseElement) eObj);
			}
		}

		return ctx.createSuccessStatus();
	}
	
	private IStatus validateDefinitions(IValidationContext ctx, Definitions def) {
		if (def.getTargetNamespace()==null || def.getTargetNamespace().isEmpty()) {
			if (warnings) {
			}
			else {
				ctx.addResult(def);
				return ctx.createFailureStatus("No targetNamespace defined");
			}			
		}
		
		return ctx.createSuccessStatus();
	}

	public IStatus createFailureStatus(IValidationContext ctx, EObject object, Object... messageArgs) {
		IStatus status = ctx.createFailureStatus(messageArgs);
		ctx.addResult(object);
		return status;
	}

	public IStatus createMissingFeatureStatus(IValidationContext ctx, EObject object, String featureName) {
		EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
		// change error message slightly for connections
		String message;
		if (feature.getEType() == Bpmn2Package.eINSTANCE.getSequenceFlow())
			message = ModelUtil.getLabel(object) + " has no " + ModelUtil.getLabel(object, feature) + " Connections";
		else
			message = ModelUtil.getLabel(object) + " has missing or incomplete " + ModelUtil.getLabel(object, feature);
		IStatus status = ctx.createFailureStatus(message);
		ctx.addResult(object);
		return status;
	}

	private IStatus validateBaseElement(IValidationContext ctx, BaseElement be) {

		if (be instanceof Process) {
			Process process = (Process) be;

			if (warnings) {
				// report warnings only
				boolean foundStartEvent = false;
				boolean foundEndEvent = false;
				List<FlowElement> flowElements = process.getFlowElements();
				for (FlowElement fe : flowElements) {
					if (fe instanceof StartEvent) {
						foundStartEvent = true;
					}
					if (fe instanceof EndEvent) {
						foundEndEvent = true;
					}
				}
				if (!foundStartEvent) {
					return createFailureStatus(ctx, be, "Process has no Start Event");
				}
				if (!foundEndEvent) {
					return createFailureStatus(ctx, be, "Process has no End Event");
				}
				if (isEmpty(process.getName())) {
					return createMissingFeatureStatus(ctx,be,"name");
				}
			}
			else {
				// report errors only
			}
		}
		else if (be instanceof Import) {
			Import elem = (Import)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getLocation())) {
					return createMissingFeatureStatus(ctx,be,"location");
				}
				if (isEmpty(elem.getNamespace())) {
					return createMissingFeatureStatus(ctx,be,"namespace");
				}
				if (isEmpty(elem.getImportType())) {
					return createMissingFeatureStatus(ctx,be,"importType");
				}
			}
		}
		else if (be instanceof Error) {
			if (!warnings) {
				if (!isValidForExecutableProcess((BaseElement)be.eContainer(), be)) {
					return createMissingFeatureStatus(ctx,be,"structureRef");
				}
			}
		}
		else if (be instanceof Escalation) {
			if (!warnings) {
				if (!isValidForExecutableProcess((BaseElement)be.eContainer(), be)) {
					return createMissingFeatureStatus(ctx,be,"structureRef");
				}
			}
		}
		else if (be instanceof Message) {
			if (!warnings) {
				if (!isValidForExecutableProcess((BaseElement)be.eContainer(), be)) {
					return createMissingFeatureStatus(ctx,be,"itemRef");
				}
			}
		}
		else if (be instanceof Signal) {
			if (!warnings) {
				if (!isValidForExecutableProcess((BaseElement)be.eContainer(), be)) {
					return createMissingFeatureStatus(ctx,be,"structureRef");
				}
			}
		}
		else if (be instanceof ItemDefinition) {
			if (!warnings) {
				if (!isValidForExecutableProcess((BaseElement)be.eContainer(), be)) {
					return createMissingFeatureStatus(ctx,be,"structureRef");
				}
			}
		}
		else if (be instanceof StartEvent) {
			StartEvent elem = (StartEvent) be;
			
			if (!warnings) {
				if (elem.getOutgoing() == null || elem.getOutgoing().size() < 1) {
					return createMissingFeatureStatus(ctx,be,"outgoing");
				}
			}
		}
		else if (be instanceof EndEvent) {
			EndEvent elem = (EndEvent) be;
			
			if (!warnings) {
				if (elem.getIncoming() == null || elem.getIncoming().size() < 1) {
					return createMissingFeatureStatus(ctx,be,"incoming");
				}
			}
		}
		else if (be instanceof ScriptTask) {
			ScriptTask elem = (ScriptTask) be;
			
			if (warnings) {
				if (isEmpty(elem.getScript())) {
					return createMissingFeatureStatus(ctx,be,"script");
				}
			}
		}
		else if (be instanceof SendTask) {
			SendTask elem = (SendTask) be;

			if (!warnings) {
				if (elem.getOperationRef() == null) {
					return createMissingFeatureStatus(ctx,be,"operationRef");
				}
				if (elem.getMessageRef() == null) {
					return createMissingFeatureStatus(ctx,be,"messageRef");
				}
			}
		}
		else if (be instanceof CatchEvent) {
			CatchEvent elem = (CatchEvent) be;

			if (warnings) {
				if (elem.getOutgoing() == null || elem.getOutgoing().size() < 1) {
					return createMissingFeatureStatus(ctx,be,"outgoing");
				}
			}
			else {
				IStatus status = validateEvent(ctx,(Event)be);
				if (status!=null)
					return status;
			}
			// no more validations on this
			be = null;
		}
		else if (be instanceof ThrowEvent) {
			ThrowEvent elem = (ThrowEvent) be;

			if (warnings) {
				if (elem.getOutgoing() == null || elem.getOutgoing().size() < 1) {
					return createMissingFeatureStatus(ctx,be,"outgoing");
				}
			}
			else {
				IStatus status = validateEvent(ctx,(Event)be);
				if (status!=null)
					return status;
			}
			// no more validations on this
			be = null;
		}
		else if (be instanceof SequenceFlow) {
			SequenceFlow elem = (SequenceFlow) be;

			if (!warnings) {
				if (elem.getSourceRef() == null) {
					return createMissingFeatureStatus(ctx,be,"sourceRef");
				}
				if (elem.getTargetRef() == null) {
					return createMissingFeatureStatus(ctx,be,"targetRef");
				}
			}
		}
		else if (be instanceof Association) {
			Association elem = (Association) be;

			if (!warnings) {
				if (elem.getSourceRef() == null) {
					return createMissingFeatureStatus(ctx,be,"sourceRef");
				}
				if (elem.getTargetRef() == null) {
					return createMissingFeatureStatus(ctx,be,"targetRef");
				}
			}
		}
		else if (be instanceof Gateway) {
			Gateway elem = (Gateway) be;

			if (!warnings) {
				if (elem.getGatewayDirection() == null
						|| elem.getGatewayDirection().getValue() == GatewayDirection.UNSPECIFIED.getValue()) {
					ctx.addResult(Bpmn2Package.eINSTANCE.getGateway_GatewayDirection());
					return createMissingFeatureStatus(ctx,be,"gatewayDirection");
				}
				if (elem instanceof ExclusiveGateway) {
					if (elem.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& elem.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return createFailureStatus(ctx,be,
								"Invalid Gateway direction for Exclusing Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (elem instanceof EventBasedGateway) {
					if (elem.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()) {
						return createFailureStatus(ctx,be,
								"Invalid Gateway direction for EventBased Gateway. It should be 'Diverging'");
					}
				}
				if (elem instanceof ParallelGateway) {
					if (elem.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& elem.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return createFailureStatus(ctx,be,
								"Invalid Gateway direction for Parallel Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (elem instanceof InclusiveGateway) {
					if (elem.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& elem.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return createFailureStatus(ctx,be,
								"Invalid Gateway direction for Inclusive Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (elem instanceof ComplexGateway) {
					if (elem.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& elem.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return createFailureStatus(ctx,be,
								"Invalid Gateway direction for Complex Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
			}
		}
		else if (be instanceof CallActivity) {
			CallActivity elem = (CallActivity) be;

			if (!warnings) {
				if (elem.getCalledElementRef() == null) {
					return createMissingFeatureStatus(ctx,be,"calledElementRef");
				}
			}
		}
		else if (be instanceof DataObject) {
			DataObject elem = (DataObject) be;

			if (!warnings) {
				if (elem.getName() == null || elem.getName().length() < 1) {
					return createMissingFeatureStatus(ctx,be,"name");
				}
			}
		}
		else if (be instanceof Interface) {
			Interface elem = (Interface)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getOperations())) {
					return createMissingFeatureStatus(ctx,be,"operations");
				}
				if (isEmpty(elem.getName())) {
					return createMissingFeatureStatus(ctx,be,"name");
				}
			}
		}
		else if (be instanceof Operation) {
			Operation elem = (Operation)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getInMessageRef())) {
					return createMissingFeatureStatus(ctx,be,"inMessageRef");
				}
				if (isEmpty(elem.getName())) {
					return createMissingFeatureStatus(ctx,be,"name");
				}
			}
		}
		else if (be instanceof DataAssociation) {
			DataAssociation elem = (DataAssociation)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getTargetRef()) && elem.getAssignment().size()==0 && elem.getTransformation()==null) {
					return createMissingFeatureStatus(ctx,be,"targetRef");
				}
			}
		}
		else if (be instanceof Assignment) {
			Assignment elem = (Assignment)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getFrom())) {
					return createMissingFeatureStatus(ctx,be,"from");
				}
				if (isEmpty(elem.getTo())) {
					return createMissingFeatureStatus(ctx,be,"to");
				}
			}
		}
		else if (be instanceof InputOutputSpecification) {
			InputOutputSpecification elem = (InputOutputSpecification)be;
			if (warnings) {
			}
			else {
				if (isEmpty(elem.getInputSets())) {
					return createMissingFeatureStatus(ctx,be,"inputSets");
				}
				if (isEmpty(elem.getOutputSets())) {
					return createMissingFeatureStatus(ctx,be,"outputSets");
				}
			}
		}
		else if (be instanceof ChoreographyActivity) {
			ChoreographyActivity elem = (ChoreographyActivity)be;
			if (elem.getParticipantRefs().size()<2) {
				return createFailureStatus(ctx,be,"ChoreographyActivity must have at least two Participants");
			}
			if (elem.getInitiatingParticipantRef()==null) {
				return createFailureStatus(ctx,be,"ChoreographyActivity has no initiating Participant");
			}
		}
		else if (be instanceof Resource) {
			Resource elem = (Resource)be;
			if (isEmpty(elem.getName())) {
				return createMissingFeatureStatus(ctx,be,"name");
			}
		}
		else if (be instanceof ChoreographyTask) {
			ChoreographyTask elem = (ChoreographyTask)be;
			for (MessageFlow mf : elem.getMessageFlowRef()) {
				InteractionNode in = mf.getSourceRef();
				if (!elem.getParticipantRefs().contains(in)) {
					return createFailureStatus(ctx,be,"Message Flow source is not a Participant of the Choreography Task");
				}
				in = mf.getTargetRef();
				if (!elem.getParticipantRefs().contains(in)) {
					return createFailureStatus(ctx,be,"Message Flow target is not a Participant of the Choreography Task");
				}
			}
		}


		if (be instanceof ItemAwareElement) {
			return validateItemAwareElement(ctx, (ItemAwareElement)be);
		}
		if (be instanceof FlowNode) {
			return validateFlowNode(ctx, (FlowNode) be);
		}

		return ctx.createSuccessStatus();
	}

	private Process findProcess(BaseElement be) {
		while (be!=null && !(be instanceof Process) && be.eContainer() instanceof BaseElement) {
			be = (BaseElement)be.eContainer();
		}
		if (be instanceof Process)
			return (Process) be;
		return null;
	}
	
	private boolean isValidForExecutableProcess(BaseElement be, BaseElement ref) {
		Process process = findProcess(be);
		if (!warnings && process!=null && process.isIsExecutable()) {
			if (ref==null)
				return false;
			if (ref instanceof Expression) {
				// Executable processes MUST have FormalExpressions defined
				if (!(ref instanceof FormalExpression))
					return false;
				String body = ModelUtil.getExpressionBody((FormalExpression)ref);
				if (isEmpty(body))
					return false;
			}
			else if (ref instanceof Message) {
				// Messages must have complete ItemDefinitions
				Message message = (Message) ref;
				if (!isValidForExecutableProcess(message, message.getItemRef()))
					return false;
			}
			else if (ref instanceof ItemDefinition) {
				// ItemDefinitions must have non-empty structure definitions
				ItemDefinition itemDefinition = (ItemDefinition) ref;
				if (isEmpty(itemDefinition.getStructureRef()))
					return false;
			}
			else if (ref instanceof Signal) {
				Signal signal = (Signal) ref;
				if (!isValidForExecutableProcess(signal, signal.getStructureRef()))
					return false;
			}
			else if (ref instanceof Error) {
				Error error = (Error) ref;
				if (isEmpty(error.getErrorCode()))
					return false;
				if (!isValidForExecutableProcess(error, error.getStructureRef()))
					return false;
			}
			else if (ref instanceof Escalation) {
				Escalation escalation = (Escalation) ref;
				if (isEmpty(escalation.getEscalationCode()))
					return false;
				if (!isValidForExecutableProcess(escalation, escalation.getStructureRef()))
					return false;
			}
			else if (ref instanceof Activity) {
				
			}
			else
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private IStatus validateEvent(IValidationContext ctx, Event event) {
		Process process = findProcess(event);
		if (process!=null && process.isIsExecutable()) {
			EStructuralFeature feature = event.eClass().getEStructuralFeature("eventDefinitions");
			Assert.isNotNull(feature);
			List<EventDefinition> eventdefs = (List<EventDefinition>) event.eGet(feature);
			if (eventdefs.size()==0) {
				return createMissingFeatureStatus(ctx,event,"eventDefinitions");
			}
			for (EventDefinition ed : eventdefs) {
				IStatus status = validateEventDefinition(ctx,ed);
				if (status!=null)
					return status;
			}
		}
		return null;
	}

	private IStatus validateEventDefinition(IValidationContext ctx, EventDefinition ed) {
		if (ed instanceof TimerEventDefinition) {
			TimerEventDefinition ted = (TimerEventDefinition) ed;
			if (	ted.getTimeDate() == null
					&& ted.getTimeDuration() == null
					&& ted.getTimeCycle() == null
			) {
				return createFailureStatus(ctx,ed,"Timer Event has no Timer definition");
			}
		} else if (ed instanceof SignalEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((SignalEventDefinition) ed).getSignalRef())) {
				return createMissingFeatureStatus(ctx,ed,"signalRef");
			}
		} else if (ed instanceof ErrorEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((ErrorEventDefinition) ed).getErrorRef())) {
				return createMissingFeatureStatus(ctx,ed,"errorRef");
			}
		} else if (ed instanceof ConditionalEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((ConditionalEventDefinition) ed).getCondition())) {
				return createMissingFeatureStatus(ctx,ed,"condition");
			}
		} else if (ed instanceof EscalationEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((EscalationEventDefinition) ed).getEscalationRef())) {
				return createMissingFeatureStatus(ctx,ed,"escalationRef");
			}
		} else if (ed instanceof MessageEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((MessageEventDefinition) ed).getMessageRef())) {
				return createMissingFeatureStatus(ctx,ed,"messageRef");
			}
		} else if (ed instanceof CompensateEventDefinition) {
			if (!isValidForExecutableProcess(ed, ((CompensateEventDefinition) ed).getActivityRef())) {
				return createMissingFeatureStatus(ctx,ed,"activityRef");
			}
		}
		return null;
	}
	
	private IStatus validateFlowNode(IValidationContext ctx, FlowNode fn) {
		if (!warnings) {
			boolean needIncoming = true;
			boolean needOutgoing = true;
			if (fn instanceof ThrowEvent)
				needOutgoing = false;
			if (fn instanceof CatchEvent)
				needIncoming = false;
			
			if (needOutgoing) {
				if ((fn.getOutgoing() == null || fn.getOutgoing().size() < 1)) {
					return createMissingFeatureStatus(ctx,fn,"outgoing");
				}
			}
			if (needIncoming) {
				if ((fn.getIncoming() == null || fn.getIncoming().size() < 1)) {
					return createMissingFeatureStatus(ctx,fn,"incoming");
				}
			}
		}

		return ctx.createSuccessStatus();
	}
	
	private IStatus validateItemAwareElement(IValidationContext ctx, ItemAwareElement elem) {
		if (!isValidForExecutableProcess(elem, elem.getItemSubjectRef())) {
			return createMissingFeatureStatus(ctx,elem,"itemSubjectRef");
		}
		return ctx.createSuccessStatus();
	}

	@SuppressWarnings("rawtypes")
	private static boolean isEmpty(Object object) {
		if (object instanceof String) {
			String str = (String) object;
			return str == null || str.isEmpty();
		}
		else if (object instanceof List) {
			return ((List)object).isEmpty();
		}
		else if (ModelUtil.isStringWrapper(object)) {
			String w = ModelUtil.getStringWrapperValue(object);
			if (w==null || w.isEmpty())
				return true;
		}
		else if (object==null)
			return true;
		return false;
	}

	private IStatus validateBaseElementLive(IValidationContext ctx, BaseElement be) {

		if (be instanceof ItemDefinition) {
			ItemDefinition itemDefinition = (ItemDefinition) be;
			final Definitions defs = ModelUtil.getDefinitions(itemDefinition);
			if (defs!=null) {
				// check for duplicate ItemDefinitions
				List<ItemDefinition> itemDefs = ModelUtil.getAllRootElements(defs, ItemDefinition.class);
				for (ItemDefinition id : itemDefs) {
					if (id == itemDefinition)
						continue;
					
					if (ModelUtil.compare(id, itemDefinition,true)) {
						Object structureRef = itemDefinition.getStructureRef();
						if (structureRef==null ||
								(ModelUtil.isStringWrapper(structureRef) &&
								ModelUtil.getStringWrapperValue(structureRef).isEmpty()))
							continue;
						return createFailureStatus(ctx,be,
								"The "+ id.getItemKind() + " " +
								(id.isIsCollection() ? "Collection " : "")+
								"Data Type \"" +
								ModelUtil.getDisplayName(id) +
								"\" is already defined.");
					}
				}
			}
			
			Object structureRef = itemDefinition.getStructureRef();
			if (structureRef==null ||
					(ModelUtil.isStringWrapper(structureRef) &&
					ModelUtil.getStringWrapperValue(structureRef).isEmpty())) {
				
				return createMissingFeatureStatus(ctx,be,"structureRef");
			}
		}
		else if (be instanceof Event) {
			IStatus status = validateEvent(ctx,(Event)be);
			if (status!=null)
				return status;
		}
		else if (be instanceof EventDefinition) {
			IStatus status = validateEventDefinition(ctx,(EventDefinition)be);
			if (status!=null)
				return status;
		}
		else if (be instanceof DataInput) {
			DataInput param = (DataInput) be;
			List<DataInput> allParams = null;
			if (be.eContainer() instanceof InputOutputSpecification) {
				allParams = ((InputOutputSpecification)param.eContainer()).getDataInputs();
			}
			else if (be.eContainer() instanceof ThrowEvent) {
				allParams = ((ThrowEvent)param.eContainer()).getDataInputs();
			}
			if (allParams!=null) {
				for (DataInput i : allParams) {
					if (i!=param) {
						String n1 = param.getName();
						String n2 = i.getName();
						if (n1!=null && n2!=null && n1.equals(n2))
							return ctx.createFailureStatus("Input Parameter \""+param.getName()+"\" is already defined");
					}
				}
			}
		}
		else if (be instanceof DataOutput) {
			DataOutput param = (DataOutput) be;
			List<DataOutput> allParams = null;
			if (be.eContainer() instanceof InputOutputSpecification) {
				allParams = ((InputOutputSpecification)param.eContainer()).getDataOutputs();
			}
			else if (be.eContainer() instanceof CatchEvent) {
				allParams = ((CatchEvent)param.eContainer()).getDataOutputs();
			}
			if (allParams!=null) {
				for (DataOutput i : allParams) {
					if (i!=param) {
						String n1 = param.getName();
						String n2 = i.getName();
						if (n1!=null && n2!=null && n1.equals(n2))
							return ctx.createFailureStatus("Output Parameter \""+param.getName()+"\" is already defined");
					}
				}
			}
		}
		return ctx.createSuccessStatus();
	}
}
