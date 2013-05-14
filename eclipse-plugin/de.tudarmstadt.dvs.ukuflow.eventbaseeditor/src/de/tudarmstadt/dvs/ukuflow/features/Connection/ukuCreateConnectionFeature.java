/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package de.tudarmstadt.dvs.ukuflow.features.Connection;


import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;


public class ukuCreateConnectionFeature extends AbstractCreateConnectionFeature {

	public ukuCreateConnectionFeature(IFeatureProvider fp) {
		// provide name and description for the UI, e.g. the palette
		super(fp, "Connection", "Create SequenceFlow"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean canCreate(ICreateConnectionContext context) {
		//TODO: checking ukuflow rule here!!
		
		// return true if both anchors belong to a EClass
		// and those EClasses are not identical
		EventBaseOperator source = getEClass(context.getSourceAnchor());
		EventBaseOperator target = getEClass(context.getTargetAnchor());
		//if(source instanceof EPeriodicEG && target instanceof EPeriodicEG)
		//	return false;
		
		if (source != null && target != null && source != target) {
			return true;
		}
		return false;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		// return true if start anchor belongs to a EClass
		if (getEClass(context.getSourceAnchor()) != null) {
			//TODO checking
			return true;
		}
		return false;
	}

	public Connection create(ICreateConnectionContext context) {
		Connection newConnection = null;

		// get EClasses which should be connected
		EventBaseOperator source = getEClass(context.getSourceAnchor());
		EventBaseOperator target = getEClass(context.getTargetAnchor());

		if (source != null && target != null) {
			// create new business object
			ESequenceFlow eReference = createEReference(source, target);			
			// add connection for business object
			AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
			addContext.setNewObject(eReference);
			newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);
			//link(newConnection, eReference);
		}
		
		return newConnection;
	}

	/**
	 * Returns the EClass belonging to the anchor, or null if not available.
	 */
	private EventBaseOperator getEClass(Anchor anchor) {
		if (anchor != null) {
			Object obj = getBusinessObjectForPictogramElement(anchor.getParent());
			if (obj instanceof EventBaseOperator) {
				return (EventBaseOperator) obj;
			}
		}
		return null;
	}

	/**
	 * Creates a EReference between two EClasses.
	 */
	private ESequenceFlow createEReference(EventBaseOperator source, EventBaseOperator target) {
		//EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		ESequenceFlow eReference = EventbaseFactory.eINSTANCE.createESequenceFlow();//EventbasePackage.eINSTANCE.getESequenceFlow();//new EConnection((IEEvaluableExpression)source, (IEEvaluableExpression)target);
		eReference.setSource(source);
		eReference.setTarget(target);
		
		//eReference.setSource((EventBaseOperator) source);
		//eReference.setTarget((EventBaseOperator) target);//setName("new connection"); //$NON-NLS-1$
		//eReference.eSet(EventbasePackage.eINSTANCE.getESequenceFlow_Source(), source);
		//eReference.eSet(EventbasePackage.eINSTANCE.getESequenceFlow_Target(), target);
		
		//EList<ESequenceFlow> ins = target.getIncoming();
		//ins.add(eReference);
		//target.eSet(EventbasePackage.eINSTANCE.getEventBaseOperator_Incoming(), ins);
		//EList<ESequenceFlow> outs = source.getOutgoing();
		//outs.add(eReference);
		//source.eSet(EventbasePackage.eINSTANCE.getEventBaseOperator_Outgoing(), outs);
		//source
		//addGraphicalRepresentation(context, newObject)
		return eReference;
	}

}
