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

import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.ConnectionCreationToolEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.PaletteSeparatorEntry;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextEntryHelper;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;
import org.eclipse.graphiti.tb.IDecorator;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventImageProvider;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.FeatureContainer;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.features.TutorialCollapseDummyFeature;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class ukuFlowToolBehaviorProvider extends DefaultToolBehaviorProvider {

	public ukuFlowToolBehaviorProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}
	
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	
	@Override
	public IContextButtonPadData getContextButtonPad(
			IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();

		// 1. set the generic context buttons
		// note, that we do not add 'remove' (just as an example)
		setGenericContextButtons(data, pe, CONTEXT_BUTTON_DELETE
				| CONTEXT_BUTTON_UPDATE);

		// 2. set the collapse button
		// simply use a dummy custom feature (senseless example)
		CustomContext cc = new CustomContext(new PictogramElement[] { pe });
		ICustomFeature[] cf = getFeatureProvider().getCustomFeatures(cc);
		for (int i = 0; i < cf.length; i++) {
			ICustomFeature iCustomFeature = cf[i];
			if (iCustomFeature instanceof TutorialCollapseDummyFeature) {
				IContextButtonEntry collapseButton = ContextEntryHelper
						.createCollapseContextButton(true, iCustomFeature, cc);
				data.setCollapseContextButton(collapseButton);
				break;
			}
		}

		// 3. add one domain specific context-button, which offers all
		// available connection-features as drag&drop features...

		// 3.a. create new CreateConnectionContext
		CreateConnectionContext ccc = new CreateConnectionContext();
		ccc.setSourcePictogramElement(pe);
		Anchor anchor = null;
		if (pe instanceof Anchor) {
			anchor = (Anchor) pe;
		} else if (pe instanceof AnchorContainer) {
			// assume, that our shapes always have chopbox anchors
			anchor = Graphiti.getPeService().getChopboxAnchor(
					(AnchorContainer) pe);
		}
		ccc.setSourceAnchor(anchor);

		// 3.b. create context button and add all applicable features
		ContextButtonEntry button = new ContextButtonEntry(null, context);
		button.setText("Create connection"); //$NON-NLS-1$
		button.setIconId(EventImageProvider.CONNECTION_ARROW);
		ICreateConnectionFeature[] features = getFeatureProvider()
				.getCreateConnectionFeatures();
		for (ICreateConnectionFeature feature : features) {
			if (feature.isAvailable(ccc) && feature.canStartConnection(ccc))
				button.addDragAndDropFeature(feature);
		}

		// 3.c. add context button, if it contains at least one feature
		if (button.getDragAndDropFeatures().size() > 0) {
			data.getDomainSpecificContextButtons().add(button);
		}

		return data;
	}

	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
		// create a sub-menu for all custom features
		ContextMenuEntry subMenu = new ContextMenuEntry(null, context);
		subMenu.setText("Cu&stom"); //$NON-NLS-1$
		subMenu.setDescription("Custom features submenu"); //$NON-NLS-1$
		// display sub-menu hierarchical or flat
		subMenu.setSubmenu(true);

		// create a menu-entry in the sub-menu for each custom feature
		ICustomFeature[] customFeatures = getFeatureProvider()
				.getCustomFeatures(context);
		for (int i = 0; i < customFeatures.length; i++) {
			ICustomFeature customFeature = customFeatures[i];
			if (customFeature.isAvailable(context)) {
				ContextMenuEntry menuEntry = new ContextMenuEntry(
						customFeature, context);
				subMenu.add(menuEntry);
			}
		}

		IContextMenuEntry ret[] = new IContextMenuEntry[] { subMenu };
		return ret;
	}

	@Override
	public IPaletteCompartmentEntry[] getPalette() {
		List<IPaletteCompartmentEntry> ret = new ArrayList<IPaletteCompartmentEntry>();
		
		PaletteCompartmentEntry connectionEntry = new PaletteCompartmentEntry(
				"Connections", EventImageProvider.CONNECTION_ARROW); //$NON-NLS-1$
		PaletteCompartmentEntry eventGeneratorEntry = new PaletteCompartmentEntry(
				"Event Generators", EventImageProvider.GEARS_ICON); //$NON-NLS-1$
		PaletteCompartmentEntry eventFilterEntry = new PaletteCompartmentEntry(
				"Event Filters", EventImageProvider.FUNNEL_ICON); //$NON-NLS-1$
		PaletteCompartmentEntry eventComposerEntry = new PaletteCompartmentEntry("Event Composers", EventImageProvider.MERGING_ICON);
		
		// simple fashion
		ret.add(connectionEntry);
		ret.add(eventGeneratorEntry);
		ret.add(eventFilterEntry);
		ret.add(eventComposerEntry);
		

		IFeatureProvider featureProvider = getFeatureProvider();

		// add all create-connection-features
		ICreateConnectionFeature[] createConnectionFeatures = featureProvider
				.getCreateConnectionFeatures();
		for (ICreateConnectionFeature cf : createConnectionFeatures) {
			ConnectionCreationToolEntry connectionCreationToolEntry = new ConnectionCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId());
			connectionCreationToolEntry.addCreateConnectionFeature(cf);
			connectionEntry.addToolEntry(connectionCreationToolEntry);
			// stackEntry.addCreationToolEntry(connectionCreationToolEntry);
		}
		/** Event generator**/
		
		for (ICreateFeature cf : FeatureManager
				.getNonRecurringEGFeatures(featureProvider)) {
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			eventGeneratorEntry.addToolEntry(objectCreationToolEntry);
		}
		// PaletteSeparatorEntry
		eventGeneratorEntry.addToolEntry(new PaletteSeparatorEntry());
		// add recurring event generator features
		for (ICreateFeature cf : FeatureManager
				.getRecurringEGFeature(featureProvider)) {
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			eventGeneratorEntry.addToolEntry(objectCreationToolEntry);
		}
		/** add event filter feature **/
		for (ICreateFeature cf : FeatureManager
				.getSimpleEFCreateFeatures(featureProvider)) {
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			eventFilterEntry.addToolEntry(objectCreationToolEntry);
		}
		/** add event composers  **/
		if (featureProvider instanceof UkuFlowFeatureProvider) {
			UkuFlowFeatureProvider ufp = (UkuFlowFeatureProvider) featureProvider;
			for (ICreateFeature cf : ufp.getCreateFeatures(EFLogic.class)) {
				ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
						cf.getCreateName(), cf.getCreateDescription(),
						cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
				eventComposerEntry.addToolEntry(objectCreationToolEntry);
			}
			eventComposerEntry.addToolEntry(new PaletteSeparatorEntry());
			for (ICreateFeature cf : ufp.getCreateFeatures(EFProcessing.class)) {
				ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
						cf.getCreateName(), cf.getCreateDescription(),
						cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
				eventComposerEntry.addToolEntry(objectCreationToolEntry);
			}
			eventComposerEntry.addToolEntry(new PaletteSeparatorEntry());
			for(ICreateFeature cf : ufp.getCreateFeatures(EFTemporal.class)){
				ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
						cf.getCreateName(), cf.getCreateDescription(),
						cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
				eventComposerEntry.addToolEntry(objectCreationToolEntry);
			}
			eventComposerEntry.addToolEntry(new PaletteSeparatorEntry());
			for(ICreateFeature cf : ufp.getCreateFeatures(EFChangeEvent.class)){
				ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
						cf.getCreateName(), cf.getCreateDescription(),
						cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
				eventComposerEntry.addToolEntry(objectCreationToolEntry);
			}
		}
		return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
	}
	/*
	private void createEntries(List<Class> neededEntries,
			PaletteCompartmentEntry compartmentEntry) {
		for (Object o : neededEntries) {
			if (o instanceof Class) {
				createEntry((Class) o, compartmentEntry);
			}
		}
	}

	private void createEntry(Class c, PaletteCompartmentEntry compartmentEntry) {
		UkuFlowFeatureProvider featureProvider = (UkuFlowFeatureProvider) getFeatureProvider();
		IFeature feature = featureProvider.getCreateFeatureForBusinessObject(c);
		if (feature instanceof ICreateFeature) {
			ICreateFeature cf = (ICreateFeature) feature;
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			compartmentEntry.addToolEntry(objectCreationToolEntry);
		} else if (feature instanceof ICreateConnectionFeature) {
			ICreateConnectionFeature cf = (ICreateConnectionFeature) feature;
			ConnectionCreationToolEntry connectionCreationToolEntry = new ConnectionCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(),
					cf.getCreateImageId(), cf.getCreateLargeImageId());
			connectionCreationToolEntry.addCreateConnectionFeature(cf);
			compartmentEntry.addToolEntry(connectionCreationToolEntry);
		}

	}*/

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if(pes.length != 1){
			//debug needed
			log.warn("2 objects were passed to DoubleClickFeature. -> ignored!");
			return super.getDoubleClickFeature(context);
		}
		log.debug("Double Click feature activated: obj: "+pes[0]);
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pes[0]);
		FeatureContainer fc = UkuFlowFeatureProvider.getFeatureContainer(bo);
		if(fc != null){
			log.debug("return doubleClickFeature from FeatureContainer : "+fc);
			return fc.getDoubleClickFeature(getFeatureProvider());
		}
		ICustomFeature customFeature = new GenericEditPropertiesFeature(
				getFeatureProvider());
		// canExecute() tests especially if the context contains a EClass
		if (customFeature.canExecute(context)) {
			log.debug("return GenericEditProperties feature as DoubleClickFeature");
			return customFeature;
		}
		log.debug("return super.getDoubeclickFeature()");
		return super.getDoubleClickFeature(context);
	}

	@Override
	public IDecorator[] getDecorators(PictogramElement pe) {
		IFeatureProvider featureProvider = getFeatureProvider();
		Object bo = featureProvider.getBusinessObjectForPictogramElement(pe);
		if (bo instanceof EventBaseOperator) {
			EventBaseOperator eClass = (EventBaseOperator) bo;
			String name = eClass.getElementName();
			if (name != null && name.length() > 0
					&& !(name.charAt(0) >= 'A' && name.charAt(0) <= 'Z')) {
				// IDecorator imageRenderingDecorator = new
				// ImageDecorator(IPlatformImageConstants.IMG_ECLIPSE_WARNING_TSK);
				// imageRenderingDecorator.setMessage("Name should start with upper case letter"); //$NON-NLS-1$
				// return new IDecorator[] { imageRenderingDecorator};
			}
		}

		return super.getDecorators(pe);
	}

	@Override
	public GraphicsAlgorithm[] getClickArea(PictogramElement pe) {
		IFeatureProvider featureProvider = getFeatureProvider();
		Object bo = featureProvider.getBusinessObjectForPictogramElement(pe);
		if (bo instanceof EventBaseOperator) {
			Iterator<PictogramElement> iterator = Graphiti.getPeService()
					.getPictogramElementChildren(pe).iterator();
			GraphicsAlgorithm[] algorithms = new GraphicsAlgorithm[2];

			algorithms[0] = iterator.next().getGraphicsAlgorithm();
			algorithms[1] = iterator.next().getGraphicsAlgorithm();
			return algorithms;			
		}
		return super.getClickArea(pe);
	}

	@Override
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {
		if (pe instanceof ContainerShape) {
			GraphicsAlgorithm invisible = pe.getGraphicsAlgorithm();
			if (!invisible.getLineVisible()) {
				EList<GraphicsAlgorithm> graphicsAlgorithmChildren = invisible
						.getGraphicsAlgorithmChildren();
				if (!graphicsAlgorithmChildren.isEmpty()) {
					return graphicsAlgorithmChildren.get(0);
				}
			}
		}
		return super.getSelectionBorder(pe);
	}

	@Override
	public String getToolTip(GraphicsAlgorithm ga) {
		PictogramElement pe = ga.getPictogramElement();
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(
				pe);
		if (bo instanceof EventBaseOperator) {
			String name = ((EventBaseOperator) bo).getElementName();
			if (name != null && !(name.length() == 0)) {
				return name;
			}
		}
		return super.getToolTip(ga);
	}

}