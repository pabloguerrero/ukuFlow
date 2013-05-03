/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *    cbrand - Bug 377475 - Fix AbstractCustomFeature.execute and canExecute
 *
 * </copyright>
 *
 *******************************************************************************/
package de.tudarmstadt.dvs.ukuflow.features.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;

import eventbase.EDistributionEG;
import eventbase.EPeriodicEG;
import eventbase.EventBaseOperator;

public class TutorialRenameEClassFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges = false;

	public TutorialRenameEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Edit Property"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Change the properties of the selected element"; //$NON-NLS-1$
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		// allow rename if exactly one pictogram element
		// representing an EClass is selected
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof EventBaseOperator) {
				ret = true;
			}
		}
		return ret;
	}

	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof EPeriodicEG) {
				EPeriodicEG eClass = (EPeriodicEG) bo;
				int currentTime = eClass.getTime();
				//String currentName = eClass.getName();
				// ask user for a new class name
				Map<String,String> t = new HashMap<String, String>();
				t.put("Time", ""+currentTime);
				t.put("t","Text text");
				//String newName = ExampleUtil.askString("Enter Time", getDescription(), ""+currentTime); //$NON-NLS-1$
				Map<String,String>result = DialogUtils.askString("Properties", t);
				if (result == null) return;
				
				
				int newTime = Integer.parseInt(result.get("Time"));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					//eClass.setName(newName);
					eClass.setTime(newTime);
					updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EDistributionEG){
				
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}
}
