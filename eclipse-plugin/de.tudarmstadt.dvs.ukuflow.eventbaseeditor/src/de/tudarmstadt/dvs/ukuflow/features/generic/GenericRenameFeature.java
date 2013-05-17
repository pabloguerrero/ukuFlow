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
package de.tudarmstadt.dvs.ukuflow.features.generic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;

public class GenericRenameFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges = false;

	public GenericRenameFeature(IFeatureProvider fp) {
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

			if (bo instanceof EGImmediate)
				return false;

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
			Map<String, Object> properties = new HashMap<String, Object>();
			
			if (bo instanceof EGImmediate) {
				// nothing todo
			} else if (bo instanceof EGAbsolute) {
				EGAbsolute eClass = (EGAbsolute) bo;
				int currentTime = eClass.getAbsoluteTime();
				properties.put("Absolute Time", "" + currentTime);

				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;

				int newTime = Integer.parseInt(result.get("Absolute Time"));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					eClass.setAbsoluteTime(newTime);
					//updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EGOffset) {
				EGOffset off = (EGOffset) bo;
				properties.put("Offset Time", off.getOffsetTime());
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				int currentTime = off.getOffsetTime();
				int newTime = Integer.parseInt(result.get("Offset Time"));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					off.setOffsetTime(newTime);
					//updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EGRelative) {
				EGRelative off = (EGRelative) bo;
				properties.put("Delay Time", off.getDelayTime());
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				int currentTime = off.getDelayTime();
				int newTime = Integer.parseInt(result.get("Delay Time"));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					off.setDelayTime(newTime);
					//updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EGPeriodic) {
				EGPeriodic off = (EGPeriodic) bo;
				String key = "Period Time";
				String key1 ="Sensor Type";
				String key2 ="Scope";
				properties.put(key, off.getTime());
				properties.put(key1, off.getSensorType());
				properties.put(key2, off.getScope());
				
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				int currentTime = off.getTime();
				int newTime = Integer.parseInt(result.get(key));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					off.setTime(newTime);
					//updatePictogramElement(pes[0]);
				}
				
				String scope= off.getScope();
				String newScope= result.get(key2);
				if (!newScope.equals(scope)) {
					this.hasDoneChanges = true;
					off.setScope(newScope);
					//updatePictogramElement(pes[0]);
				}
				String type= off.getSensorType();
				String newType= result.get(key1);
				if (!newType.equals(type)) {
					this.hasDoneChanges = true;
					off.setSensorType(newType);
					//updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EGPatterned) {
				EGPatterned off = (EGPatterned) bo;
				String key1 = "Slot Time";
				String key2 = "Pattern";
				properties.put(key1, off.getTime());
				properties.put(key2, off.getPattern());
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				int currentTime = off.getTime();
				int newTime = Integer.parseInt(result.get(key1));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					off.setTime(newTime);
					//updatePictogramElement(pes[0]);
				}
				String currentPattern = off.getPattern();
				String newPattern = result.get(key2);
				if(!newPattern.equals(currentPattern)){
					this.hasDoneChanges = true;
					off.setPattern(newPattern);
					//updatePictogramElement(pes[0]);
				}
			}else if (bo instanceof EGDistribution) {
				EGDistribution off = (EGDistribution) bo;
				String key1 = "Slot Time";
				String key2 = "Function";
				properties.put(key1, off.getTime());
				properties.put(key2, off.getFunction());
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				int currentTime = off.getTime();
				int newTime = Integer.parseInt(result.get(key1));
				if (newTime != currentTime) {
					this.hasDoneChanges = true;
					off.setTime(newTime);
					//updatePictogramElement(pes[0]);
				}
				String currentPattern = off.getFunction();
				String newPattern = result.get(key2);
				if(!newPattern.equals(currentPattern)){
					this.hasDoneChanges = true;
					off.setFunction(newPattern);
					//updatePictogramElement(pes[0]);
				}
			} else if (bo instanceof EFSimple){
				EFSimple efsimple = (EFSimple) bo;
				EList<ESimpleFilterConstraint> offs = efsimple.getConstraints();
				ESimpleFilterConstraint off;
				if(offs.size() > 0)
					off = offs.get(0);
				else {
					off = EventbaseFactory.eINSTANCE.createESimpleFilterConstraint();
					off.setType("type");
					off.setOperator(0);
					off.setValue("value");
				}
				String key1 = "Type";
				String key2 = "Operation";
				String key3 = "Value";
				properties.put(key1, off.getType());
				properties.put(key2, off.getOperator());
				properties.put(key3, off.getValue());
				Map<String, String> result = DialogUtils.askString(
						"Properties", properties);
				if (result == null)
					return;
				String oldType = off.getType();
				String newType = result.get(key1);//Integer.parseInt(result.get(key1));
				if (!oldType.equals(newType)) {
					this.hasDoneChanges = true;
					off.setType(newType);
				}
				String oldValue = off.getValue();
				String newValue = result.get(key3);
				if(!newValue.equals(oldValue)){
					this.hasDoneChanges = true;
					off.setValue(newValue);
				}
				int oldOp = off.getOperator();
				int newOp = Integer.parseInt(result.get(key2));
				if(newOp != oldOp){
					this.hasDoneChanges = true;
					off.setOperator(newOp);
					
				}
			}
			if(this.hasDoneChanges)
				updatePictogramElement(pes[0]);
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}
}
