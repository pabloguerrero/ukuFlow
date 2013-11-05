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
package de.tudarmstadt.dvs.ukuflow.features.connection;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;

public class SequenceFlowCreateFeature extends AbstractCreateConnectionFeature {

	public SequenceFlowCreateFeature(IFeatureProvider fp) {
		// provide name and description for the UI, e.g. the palette
		super(fp, "Connection", "Create SequenceFlow"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getCreateImageId() {
		return EventImageProvider.IMG_EREFERENCE;
	}

	private boolean containCircle(EventBaseOperator source,
			EventBaseOperator target) {
		Set<EventBaseOperator> pool = new HashSet<EventBaseOperator>();
		pool.add(source);
		return cr(pool, target);
	}

	private boolean cr(Set<EventBaseOperator> pool, EventBaseOperator target) {
		if (pool.contains(target))
			return true;
		pool.add(target);
		if (target.getOutgoing() != null)
			for (ESequenceFlow sq : target.getOutgoing()) {
				EventBaseOperator next = sq.getTarget();
				if (next != null)
					if (cr(pool, next))
						return true;
			}
		return false;
	}

	public boolean canCreate(ICreateConnectionContext context) {
		// TODO: checking ukuflow rule here!!

		// return true if both anchors belong to a EClass
		// and those EClasses are not identical
		EventBaseOperator source = getEClass(context.getSourceAnchor());
		EventBaseOperator target = getEClass(context.getTargetAnchor());
		// if(source instanceof EPeriodicEG && target instanceof EPeriodicEG)
		// return false;

		if (source != null && target != null && source != target) {
			if (target.getIncoming().size() >= 2)
				return false;
			if (containCircle(source, target)) {
				return false;
			}
			if (target instanceof EventGenerator) {
				if (!(target instanceof EGRelative)) {
					// Event generator should not have incoming!
					return false;
				} else {
					if (!target.getIncoming().isEmpty())
						// EG Relative should have only one incoming!
						return false;
				}
			} else if (target instanceof EventFilter) {
				return ((EventFilter) target).getIncoming().size() < 2;
			}
			return true;
		}
		return false;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		// return true if start anchor belongs to a EClass
		if (getEClass(context.getSourceAnchor()) != null) {
			// TODO checking
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
			ESequenceFlow seqFlow = createEReference(source, target);
			// add connection for business object
			AddConnectionContext addContext = new AddConnectionContext(
					context.getSourceAnchor(), context.getTargetAnchor());
			addContext.setNewObject(seqFlow);
			newConnection = (Connection) getFeatureProvider().addIfPossible(
					addContext);
			// link(newConnection, eReference);
		}

		return newConnection;
	}

	/**
	 * Returns the EClass belonging to the anchor, or null if not available.
	 */
	private EventBaseOperator getEClass(Anchor anchor) {
		if (anchor != null) {
			Object obj = getBusinessObjectForPictogramElement(anchor
					.getParent());
			if (obj instanceof EventBaseOperator) {
				return (EventBaseOperator) obj;
			}
		}
		return null;
	}

	/**
	 * Creates a EReference between two EClasses.
	 */
	private ESequenceFlow createEReference(EventBaseOperator source,
			EventBaseOperator target) {
		// EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		ESequenceFlow eReference = EventbaseFactory.eINSTANCE
				.createESequenceFlow();// EventbasePackage.eINSTANCE.getESequenceFlow();//new
										// EConnection((IEEvaluableExpression)source,
										// (IEEvaluableExpression)target);
		eReference.setSource(source);
		eReference.setTarget(target);

		// eReference.setSource((EventBaseOperator) source);
		//eReference.setTarget((EventBaseOperator) target);//setName("new connection"); //$NON-NLS-1$
		// eReference.eSet(EventbasePackage.eINSTANCE.getESequenceFlow_Source(),
		// source);
		// eReference.eSet(EventbasePackage.eINSTANCE.getESequenceFlow_Target(),
		// target);

		// EList<ESequenceFlow> ins = target.getIncoming();
		// ins.add(eReference);
		// target.eSet(EventbasePackage.eINSTANCE.getEventBaseOperator_Incoming(),
		// ins);
		// EList<ESequenceFlow> outs = source.getOutgoing();
		// outs.add(eReference);
		// source.eSet(EventbasePackage.eINSTANCE.getEventBaseOperator_Outgoing(),
		// outs);
		// source
		// addGraphicalRepresentation(context, newObject)
		return eReference;
	}

}
