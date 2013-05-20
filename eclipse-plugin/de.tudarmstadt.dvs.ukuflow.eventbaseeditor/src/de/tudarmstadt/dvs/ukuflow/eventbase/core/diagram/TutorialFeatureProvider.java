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
package de.tudarmstadt.dvs.ukuflow.eventbase.core.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.FeatureContainer;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.TutorialCollapseDummyFeature;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.TutorialReconnectionFeature;
import de.tudarmstadt.dvs.ukuflow.eventbase.utils.EventObjIndependenceSolver;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.features.connection.SequenceFlowAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.connection.SequenceFlowCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.ef.simple.EFSimpleFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.distribution.EGDistributionFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGAbsoluteFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGImmediateFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGOffsetFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGRelativeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.patterned.EGPatternedFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.periodic.EGPeriodicFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialAddEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialAssociateDiagramEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialChangeColorEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialCopyEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialCreateEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialDrillDownEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericLayoutFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericMoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.TutorialPasteEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericRenameFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericResizeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;

public class TutorialFeatureProvider extends DefaultFeatureProvider {
	private static HashMap<Class, IFeature> mapBusinessObjectClassToCreateFeature;
	private static Hashtable<Class, FeatureContainer> containers;
	private static ICreateFeature[] creates = null;
	static {
		mapBusinessObjectClassToCreateFeature = new HashMap<Class, IFeature>();// .put(TutorialAddEClassFeature.class,new
																				// TutorialAddEClassFeature(null));
		containers = new Hashtable<Class, FeatureContainer>();
		containers.put(EGImmediate.class, new EGImmediateFeatureContainer());
		containers.put(EGAbsolute.class, new EGAbsoluteFeatureContainer());
		containers.put(EGOffset.class, new EGOffsetFeatureContainer());
		containers.put(EGRelative.class, new EGRelativeFeatureContainer());
		containers.put(EGPeriodic.class, new EGPeriodicFeatureContainer());
		containers.put(EGPatterned.class, new EGPatternedFeatureContainer());
		containers.put(EGDistribution.class,
				new EGDistributionFeatureContainer());

		containers.put(EFSimple.class, new EFSimpleFeatureContainer());

	}
	EventObjIndependenceSolver solver;

	public TutorialFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		if (solver == null) {
			solver = new EventObjIndependenceSolver();
		}
		// setIndependenceSolver(solver);
	}

	private void initCreateFeatures() {
		List<ICreateFeature> createFeaturesList = new ArrayList<ICreateFeature>();
		for (FeatureContainer fc : containers.values()) {
			createFeaturesList.add(fc.getCreateFeature(this));
		}
		creates = createFeaturesList
				.toArray(new ICreateFeature[createFeaturesList.size()]);
	}

	/**
	 * provide IFeature base on the input class
	 * 
	 * @param be
	 * @return
	 */
	public IFeature getCreateFeatureForBusinessObject(Object be) {
		IFeature feature = null;
		if (be != null) {
			Class[] ifs = be.getClass().getInterfaces();
			for (int i = 0; i < ifs.length && feature == null; ++i) {
				feature = mapBusinessObjectClassToCreateFeature.get(ifs[i]);
			}
		}
		return feature;
	}

	public EventObjIndependenceSolver getCustomIndependenceSolver() {
		return solver;
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		// is object for add request a EClass or EReference?
		final Object newObject = context.getNewObject();
		if (newObject instanceof ESequenceFlow)
			return new SequenceFlowAddFeature(this);
		/*
		 * if (context.getNewObject() instanceof eventbase.ESequenceFlow) {
		 * return new TutorialAddEReferenceFeature(this); } else if (newObject
		 * instanceof eventbase.EPeriodicEG){ return new
		 * EPeriodicEGAddFeature(this); } else if (newObject instanceof
		 * EDistributionEG){ return new
		 * EAperiodicDistributionEGAddFeature(this); } else if(newObject
		 * instanceof EPatternedEG){ return new
		 * EAperiodicPatternedEGAddFeature(this); } else if(newObject instanceof
		 * EFSimple){ return new ESimpleEFAddFeature(this); //} else
		 * if(newObject instanceof EComplexEF){ // return new
		 * EComplexEFAddFeature(this); }
		 */
		FeatureContainer fc = null;
		for (@SuppressWarnings("rawtypes")
		Class c : newObject.getClass().getInterfaces()) {
			fc = containers.get(c);
			if (fc != null)
				break;
		}
		if (fc != null)
			if (fc.getAddFeature(this) != null)
				return fc.getAddFeature(this);

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		if (creates == null)
			initCreateFeatures();
		return creates;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo != null) {
				FeatureContainer fc = containers.get(bo.getClass());
				if (fc != null)
					if (fc.getUpdateFeature(this) != null)
						return fc.getUpdateFeature(this);
			}
		}
		return super.getUpdateFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		FeatureContainer fc = containers.get(bo.getClass());
		if (fc != null)
			if (fc.getMoveFeature(this) != null)
				return fc.getMoveFeature(this);
		return super.getMoveShapeFeature(context);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo != null) {
			FeatureContainer fc = containers.get(bo.getClass());
			if (fc != null)
				if (fc.getResizeFeature(this) != null)
					return fc.getResizeFeature(this);
		}
		return super.getResizeShapeFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		FeatureContainer fc = containers.get(bo.getClass());
		if (fc != null)
			if (fc.getLayoutFeature(this) != null)
				return fc.getLayoutFeature(this);
		return super.getLayoutFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] { new GenericRenameFeature(this),
				new TutorialDrillDownEClassFeature(this),
				new TutorialAssociateDiagramEClassFeature(this),
				new TutorialCollapseDummyFeature(this),
				new TutorialChangeColorEClassFeature(this) };
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] { new SequenceFlowCreateFeature(
				this) };
	}

	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		// simply return all create connection features
		return getCreateConnectionFeatures();
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(
			IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo == null)
			return super.getDirectEditingFeature(context);
		// TODO
		FeatureContainer fc = containers.get(bo.getClass());
		if (fc != null)
			if (fc.getDirectEditingFeature(this) != null)
				return fc.getDirectEditingFeature(this);
		return super.getDirectEditingFeature(context);
	}

	/*
	 * @Override public ICopyFeature getCopyFeature(ICopyContext context) {
	 * return new TutorialCopyEClassFeature(this); }
	 * 
	 * @Override public IPasteFeature getPasteFeature(IPasteContext context) {
	 * return new TutorialPasteEClassFeature(this); }
	 */
	@Override
	public IReconnectionFeature getReconnectionFeature(
			IReconnectionContext context) {
		return new TutorialReconnectionFeature(this);
	}

}
