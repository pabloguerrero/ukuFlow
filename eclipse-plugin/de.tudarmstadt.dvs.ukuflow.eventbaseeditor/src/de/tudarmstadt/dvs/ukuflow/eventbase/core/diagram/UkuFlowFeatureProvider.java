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
import java.util.List;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
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
import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.TutorialReconnectionFeature;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporalSequence;
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
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent.EFChangeDecreaseFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent.EFChangeIncreaseFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.changeevent.EFChangeRemainFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.logical.EFLogicAndFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.logical.EFLogicNotFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.logical.EFLogicOrFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingAvgFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingCountFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingMaxFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingMinFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingStDevFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.processing.EFProcessingSumFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.complex.status.temporal.EFTemporalSequenceFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.simple.EFSimpleFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.distribution.EGDistributionFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGAbsoluteFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGImmediateFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGOffsetFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGRelativeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.patterned.EGPatternedFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.periodic.EGPeriodicFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;

import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;

@SuppressWarnings("rawtypes")
public class UkuFlowFeatureProvider extends DefaultFeatureProvider {
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
		containers.put(EGDistribution.class, new EGDistributionFeatureContainer());

		containers.put(EFSimple.class, new EFSimpleFeatureContainer());
		
		containers.put(EFLogicAnd.class, new EFLogicAndFeatureContainer() );
		containers.put(EFLogicOr.class, new EFLogicOrFeatureContainer());
		containers.put(EFLogicNot.class, new EFLogicNotFeatureContainer());
		
		containers.put(EFProcessingMin.class, new EFProcessingMinFeatureContainer());
		containers.put(EFProcessingMax.class, new EFProcessingMaxFeatureContainer());
		containers.put(EFProcessingCount.class, new EFProcessingCountFeatureContainer());
		containers.put(EFProcessingSum.class, new EFProcessingSumFeatureContainer());
		containers.put(EFProcessingAvg.class, new EFProcessingAvgFeatureContainer());
		containers.put(EFProcessingStDev.class, new EFProcessingStDevFeatureContainer());
		
		containers.put(EFTemporalSequence.class, new EFTemporalSequenceFeatureContainer());
		containers.put(EFChangeDecrease.class, new EFChangeDecreaseFeatureContainer());
		containers.put(EFChangeIncrease.class, new EFChangeIncreaseFeatureContainer());
		containers.put(EFChangeRemain.class, new EFChangeRemainFeatureContainer());
	}	
	@SuppressWarnings("unchecked")
	public List<ICreateFeature> getCreateFeatures(Class clazz){
		List<ICreateFeature> result = new ArrayList<ICreateFeature>();
		for(Class z : containers.keySet()){
			if(clazz.isAssignableFrom(z))
				result.add(containers.get(z).getCreateFeature(this));
		}
		return result;
	}
	/**
	 *  return the matched feature container
	 * @param bo
	 * @return
	 */
	public static FeatureContainer getFeatureContainer(Object bo){
		FeatureContainer result = null;
		for(Class<?> c : bo.getClass().getInterfaces()){
			result = containers.get(c);
			if(result!= null)
				break;
		}
		return result;
	}
	
	//EventObjIndependenceSolver solver;

	public UkuFlowFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		//if (solver == null) {
		//	solver = new EventObjIndependenceSolver();
		//}
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

	//public EventObjIndependenceSolver getCustomIndependenceSolver() {
	//	return solver;
	//}
	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(context);
		ICustomFeature[] thisFeatures = null;
		PictogramElement[] pes = context.getPictogramElements();
		if(pes.length==1 && pes[0] instanceof ContainerShape){
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			FeatureContainer fc = getFeatureContainer(bo);
			if(fc != null){
				thisFeatures = fc.getCustomFeatures(this);
			}
		}
		return mergeFeatures(thisFeatures, superFeatures);
	}
	
	/**
	 * merge 2 list of ICustomFeature into one
	 * @param thisFeatures
	 * @param superFeatures
	 * @return
	 */
	private ICustomFeature[] mergeFeatures(ICustomFeature[] thisFeatures, ICustomFeature[] superFeatures){
		if(superFeatures == null)
			return thisFeatures;
		if(thisFeatures== null)
			return superFeatures;
		ICustomFeature[] mergeFeatures = new ICustomFeature[thisFeatures.length+superFeatures.length];		
	
			for (int i = 0; i < thisFeatures.length; i++) {
				mergeFeatures[i] = thisFeatures[i];
			}
			for(int i = 0; i < superFeatures.length;i++){
				mergeFeatures[i+thisFeatures.length] = superFeatures[i];
			}
		return mergeFeatures;
	}
	
	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		// is object for add request a EClass or EReference?
		final Object newObject = context.getNewObject();
		if (newObject instanceof ESequenceFlow)
			return new SequenceFlowAddFeature(this);
		
		FeatureContainer fc = getFeatureContainer(newObject);
		
		if (fc != null && fc.getAddFeature(this) != null)
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
		return new GenericUpdateFeature(this);
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
		return new GenericDirectEditFeature(this);
		// TODO the container contains the interface classes, but "bo" is the implemented version of this interface!!
		/**
		FeatureContainer fc = containers.get(bo.getClass());
		if (fc != null && fc.getDirectEditingFeature(this) != null)
				return fc.getDirectEditingFeature(this);
		return super.getDirectEditingFeature(context);
		*/
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

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		return new GenericDeleteFeature(this);
	}
}
