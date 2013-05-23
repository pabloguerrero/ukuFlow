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
import org.eclipse.jface.dialogs.IInputValidator;

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

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
			Map<Integer, RequestContainer> properties = new HashMap<Integer, RequestContainer>();
			Map<Integer, RequestContainer> result = null;
			if (bo instanceof EventGenerator) {
				EventGenerator eg = (EventGenerator) bo;
				properties.put(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE,
						new RequestContainer(null,
								UkuConstants.SensorTypeConstants.sensor_types,
								"Sensor Type", eg.getSensorType()));
				properties.put(EventbasePackage.EVENT_GENERATOR__SCOPE,
						new RequestContainer(null, (eg.getScope()==null?"":eg.getScope()),
								"Scope (optional)"));
				System.out.println(properties);
				if (bo instanceof EGImmediate) {
					// nothing todo
				} else if (bo instanceof EGAbsolute) {
					EGAbsolute eClass = (EGAbsolute) bo;
					int currentTime = eClass.getAbsoluteTime();
					properties.put(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME,
							new RequestContainer(
									new RequestContainer.IntegerValidator(), "" + currentTime,
									"Absolute Time"));

					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;

					int newTime = Integer
							.parseInt(result
									.get(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						eClass.setAbsoluteTime(newTime);
					}
				} else if (bo instanceof EGOffset) {
					EGOffset off = (EGOffset) bo;
					properties.put(EventbasePackage.EG_OFFSET__OFFSET_TIME,
							new RequestContainer(
									new RequestContainer.IntegerValidator(), off.getOffsetTime() + "",
									"Offset Time"));
					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					int currentTime = off.getOffsetTime();
					int newTime = Integer
							.parseInt(result
									.get(EventbasePackage.EG_OFFSET__OFFSET_TIME).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						off.setOffsetTime(newTime);
					}
				} else if (bo instanceof EGRelative) {
					EGRelative off = (EGRelative) bo;
					properties.put(EventbasePackage.EG_RELATIVE__DELAY_TIME,
							new RequestContainer(
									new RequestContainer.IntegerValidator(), "" + off.getDelayTime(),
									"Delay time"));
					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					int currentTime = off.getDelayTime();
					int newTime = Integer
							.parseInt(result
									.get(EventbasePackage.EG_RELATIVE__DELAY_TIME).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						off.setDelayTime(newTime);
						// updatePictogramElement(pes[0]);
					}
				} else if (bo instanceof EGPeriodic) {
					int i = EventbasePackage.EG_PERIODIC;
					EGPeriodic off = (EGPeriodic) bo;
					properties.put(EventbasePackage.EG_PERIODIC__TIME,
							new RequestContainer(
									new RequestContainer.IntegerValidator(),"" + off.getTime(),
									"Period time" ));

					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					int currentTime = off.getTime();
					int newTime = Integer.parseInt(result
							.get(EventbasePackage.EG_PERIODIC__TIME).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						off.setTime(newTime);
						// updatePictogramElement(pes[0]);
					}

				}

				else if (bo instanceof EGPatterned) {
					EGPatterned off = (EGPatterned) bo;
					Integer key1 = EventbasePackage.EG_PATTERNED__TIME;
					Integer key2 = EventbasePackage.EG_PATTERNED__PATTERN;
					properties.put(key1, new RequestContainer(
							new RequestContainer.IntegerValidator(),
							"Period Time", "" + off.getTime()));
					properties.put(key2, new RequestContainer(
							new RequestContainer.BinaryValidator(),
							"" + off.getPattern(), "Pattern"));
					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					int currentTime = off.getTime();
					int newTime = Integer.parseInt(result.get(key1).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						off.setTime(newTime);
					}
					String currentPattern = off.getPattern();
					String newPattern = result.get(key2).result;
					if (!newPattern.equals(currentPattern)) {
						this.hasDoneChanges = true;
						off.setPattern(newPattern);
					}
				} else if (bo instanceof EGDistribution) {
					EGDistribution off = (EGDistribution) bo;
					Integer key1 = EventbasePackage.EG_DISTRIBUTION__TIME;
					Integer key2 = EventbasePackage.EG_DISTRIBUTION__FUNCTION;
					properties.put(key1, new RequestContainer(
							new RequestContainer.IntegerValidator(), "" + off.getTime(),
							"Time unit"));// TODO
					properties.put(key2, new RequestContainer(null,
							off.getFunction(), "Function"));// off.getFunction());
					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					int currentTime = off.getTime();
					int newTime = Integer.parseInt(result.get(key1).result);
					if (newTime != currentTime) {
						this.hasDoneChanges = true;
						off.setTime(newTime);
						// updatePictogramElement(pes[0]);
					}
					String currentPattern = off.getFunction();
					String newPattern = result.get(key2).result;
					if (!newPattern.equals(currentPattern)) {
						this.hasDoneChanges = true;
						off.setFunction(newPattern);
						// updatePictogramElement(pes[0]);
					}
				}

				// ///

				String scope = eg.getScope();
				String newScope = result
						.get(EventbasePackage.EVENT_GENERATOR__SCOPE).result;
				if (!newScope.equals(scope))
					this.hasDoneChanges = true;
				eg.setScope(newScope);
				String type = eg.getSensorType();
				String newType = result
						.get(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE).result;
				if (!newType.equals(type))
					this.hasDoneChanges = true;
				eg.setSensorType(newType);

			} else if (bo instanceof EventFilter) {
				if (bo instanceof EFSimple) {
					EFSimple efsimple = (EFSimple) bo;
					EList<ESimpleFilterConstraint> offs = efsimple
							.getConstraints();
					ESimpleFilterConstraint off;
					if (offs.size() > 0)
						off = offs.get(0);
					else {
						off = EventbaseFactory.eINSTANCE
								.createESimpleFilterConstraint();
						off.setType("type");
						off.setOperator(0);
						off.setValue("value");
					}
					String key1 = "Type";
					String key2 = "Operation";
					String key3 = "Value";
					// properties.put(key1, off.getType());
					// properties.put(key2, off.getOperator());
					// properties.put(key3, off.getValue());
					result = DialogUtils.askString("Properties", properties);
					if (result == null)
						return;
					String oldType = off.getType();
					String nType = result.get(key1).result;// Integer.parseInt(result.get(key1));
					if (!oldType.equals(nType)) {
						this.hasDoneChanges = true;
						off.setType(nType);
					}
					String oldValue = off.getValue();
					String newValue = result.get(key3).result;
					if (!newValue.equals(oldValue)) {
						this.hasDoneChanges = true;
						off.setValue(newValue);
					}
					int oldOp = off.getOperator();
					int newOp = Integer.parseInt(result.get(key2).result);
					if (newOp != oldOp) {
						this.hasDoneChanges = true;
						off.setOperator(newOp);

					}
				} else {
					System.err.println("this element is not implemented yet");
				}
			}
			if (this.hasDoneChanges)
				updatePictogramElement(pes[0]);
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

}
