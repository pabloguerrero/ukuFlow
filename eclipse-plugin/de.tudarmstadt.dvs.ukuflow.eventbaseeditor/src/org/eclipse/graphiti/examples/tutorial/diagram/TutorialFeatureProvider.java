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
package org.eclipse.graphiti.examples.tutorial.diagram;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.examples.tutorial.features.TutorialCollapseDummyFeature;
import org.eclipse.graphiti.examples.tutorial.features.TutorialReconnectionFeature;
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

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.EventObjIndependenceSolver;
import de.tudarmstadt.dvs.ukuflow.features.Connection.TutorialAddEReferenceFeature;
import de.tudarmstadt.dvs.ukuflow.features.Connection.ukuCreateConnectionFeature;
import de.tudarmstadt.dvs.ukuflow.features.EAperiodicDistributionEG.EAperiodicDistributionEGAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.EAperiodicDistributionEG.EAperiodicDistributionEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.EAperiodicPatternedEG.EAperiodicPatternedEGAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.EAperiodicPatternedEG.EAperiodicPatternedEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.EComplexEF.EComplexEFAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.EPeriodicEG.EPeriodicEGAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.EPeriodicEG.EPeriodicEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.ESimpleEF.ESimpleEFAddFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialCreateEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialAddEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialAssociateDiagramEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialChangeColorEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialCopyEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialDirectEditEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialDrillDownEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialLayoutEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialMoveEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialPasteEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialRenameEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialResizeEClassFeature;
import de.tudarmstadt.dvs.ukuflow.features.tutorial.TutorialUpdateEClassFeature;
import eventbase.*;


public class TutorialFeatureProvider extends DefaultFeatureProvider {
	private static HashMap<Class, IFeature> mapBusinessObjectClassToCreateFeature;
	static{
		mapBusinessObjectClassToCreateFeature= new HashMap<Class, IFeature>();//.put(TutorialAddEClassFeature.class,new TutorialAddEClassFeature(null));
	}
	EventObjIndependenceSolver solver;
	public TutorialFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		if(solver == null){
			solver = new EventObjIndependenceSolver();
		}
		//setIndependenceSolver(solver);
	}
	/**
	 * provide IFeature base on the input class
	 * @param be
	 * @return
	 */
	public IFeature getCreateFeatureForBusinessObject(Object be) {
		IFeature feature = null;
		if (be!=null) {
			Class[] ifs = be.getClass().getInterfaces();
			for (int i=0; i<ifs.length && feature==null; ++i) {
				feature = mapBusinessObjectClassToCreateFeature.get(ifs[i]);
			}
		}
		return feature;
	}
	
	public EventObjIndependenceSolver getCustomIndependenceSolver(){
		return solver;
	}
	
	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		// is object for add request a EClass or EReference?
		final Object newObject = context.getNewObject();
		//if (context.getNewObject() instanceof EClass) {
		//	return new TutorialAddEClassFeature(this);
		//} else 
		if (context.getNewObject() instanceof eventbase.ESequenceFlow) {
			return new TutorialAddEReferenceFeature(this);
		} else if (newObject instanceof eventbase.EPeriodicEG){
			return new EPeriodicEGAddFeature(this);
		} else if (newObject instanceof EDistributionEG){
			return new EAperiodicDistributionEGAddFeature(this);
		} else if(newObject instanceof EPatternedEG){
			return new EAperiodicPatternedEGAddFeature(this);
		} else if(newObject instanceof EFSimple){
			return new ESimpleEFAddFeature(this);
		//} else if(newObject instanceof EComplexEF){
		//	return new EComplexEFAddFeature(this);
		}
		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		List features = FeatureManager.getCreateFeatures(this);
		ICreateFeature a[] = new ICreateFeature[features.size()];
		features.toArray(a);
		return a;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo instanceof EClass) {
				return new TutorialUpdateEClassFeature(this);
			}
		}
		return super.getUpdateFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof EClass) {
			return new TutorialMoveEClassFeature(this);
		}
		return super.getMoveShapeFeature(context);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof EClass) {
			return new TutorialResizeEClassFeature(this);
		}
		return super.getResizeShapeFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof EClass) {
			return new TutorialLayoutEClassFeature(this);
		}
		return super.getLayoutFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] { 
				new TutorialRenameEClassFeature(this), 
				new TutorialDrillDownEClassFeature(this),
				new TutorialAssociateDiagramEClassFeature(this), 
				new TutorialCollapseDummyFeature(this),
				new TutorialChangeColorEClassFeature(this) };
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] { new ukuCreateConnectionFeature(this) };
	}

	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		// simply return all create connection features
		return getCreateConnectionFeatures();
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof EClass) {
			return new TutorialDirectEditEClassFeature(this);
		}
		return super.getDirectEditingFeature(context);
	}
/*
	@Override
	public ICopyFeature getCopyFeature(ICopyContext context) {
		return new TutorialCopyEClassFeature(this);
	}

	@Override
	public IPasteFeature getPasteFeature(IPasteContext context) {
		return new TutorialPasteEClassFeature(this);
	}
*/
	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		return new TutorialReconnectionFeature(this);
	}
	
	
}
