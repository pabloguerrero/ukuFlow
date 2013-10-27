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
import java.util.List;
import java.util.Map;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.ModelUtil;
import de.tudarmstadt.dvs.ukuflow.eventbase.core.TimeUtil;
import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.*;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class GenericEditPropertiesFeature extends AbstractCustomFeature {
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	protected boolean hasDoneChanges = false;

	public GenericEditPropertiesFeature(IFeatureProvider fp) {
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

			// if (bo instanceof EGImmediate)
			// return false;

			if (bo instanceof EventBaseOperator) {
				ret = true;
			}
		}
		return ret;
	}

	protected String getQuestion(Object o) {
		String className = o.getClass().getSimpleName();
		String question = ModelUtil.toDisplayName(
				className.substring(2, className.length() - 4)).toLowerCase()
				+ " ";

		if (o instanceof EventGenerator)
			question += "event generator '";
		else
			question += "event filter '";
		question += ((EventBaseOperator) o).getElementName();
		question = "Properties of " + question + "'";
		return question;
	}
	protected Map<Integer,RequestContainer> asking(Object bo, Map<Integer,RequestContainer> properties){
		return DialogUtils.askString(getQuestion(bo), properties);
	}
	
	protected Object getBusinessObj(ICustomContext context){
		PictogramElement[] pes = context.getPictogramElements();
		if(pes != null && pes.length==1){
			return getBusinessObjectForPictogramElement(pes[0]);
		}
		return null;
	}
	
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			String question = getQuestion(bo);

			Map<Integer, RequestContainer> properties = new HashMap<Integer, RequestContainer>();
			Map<Integer, RequestContainer> result = null;
			if (bo instanceof EventGenerator) {
				if (bo instanceof EGRecurring) {
					properties.put(EventbasePackage.EG_RECURRING__REPETITION,
							new RequestContainer(
									new RequestContainer.IntegerValidator(),
									((EGRecurring) bo).getRepetition() + "",
									"Number of repetitions (0-255, 0 means infinite)"));
				}
				EventGenerator eg = (EventGenerator) bo;
				properties.put(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE,
						new RequestContainer(null,
								UkuConstants.SensorTypeConstants.sensor_types,
								"Sensor type", eg.getSensorType()));
				properties.put(EventbasePackage.EVENT_GENERATOR__SCOPE,
						new RequestContainer(null, (eg.getScope() == null ? ""
								: eg.getScope()),
								"Scope identifier ('WORLD': for all nodes & 'LOCAL': only at base node):"));
				// switched
				if (bo instanceof EGImmediate) {
					
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
				} else if (bo instanceof EGAbsolute) {
					EGAbsolute eClass = (EGAbsolute) bo;
					String currentTime = eClass.getAbsoluteTime();
					if (currentTime == null)
						currentTime = "";
					properties
							.put(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME,
									new RequestContainer(
											new RequestContainer.AbsoluteTimeValidator(),
											currentTime,
											"Absolute time (" + TimeUtil.TIME_PATTERN + ")"));

					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;

					String newTime = result
							.get(EventbasePackage.EG_ABSOLUTE__ABSOLUTE_TIME).result;
					if (!newTime.equals(currentTime)) {
						this.hasDoneChanges = true;
						eClass.setAbsoluteTime(newTime);
					}
				} else if (bo instanceof EGOffset) {
					EGOffset off = (EGOffset) bo;
					properties.put(EventbasePackage.EG_OFFSET__OFFSET_TIME,
							new RequestContainer(
									new RequestContainer.OffsetTimeValidator(),
									off.getOffsetTime() + "",
									"Offset time (in mm:ss format)"));
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String currentTime = off.getOffsetTime();
					String newTime = result.get(EventbasePackage.EG_OFFSET__OFFSET_TIME).result;
					if (!newTime.equals(currentTime)) {
						this.hasDoneChanges = true;
						off.setOffsetTime(newTime);
					}
				} else if (bo instanceof EGRelative) {
					EGRelative off = (EGRelative) bo;
					properties.put(EventbasePackage.EG_RELATIVE__DELAY_TIME,
									new RequestContainer(
											new RequestContainer.OffsetTimeValidator(),
											 off.getDelayTime()+"",
											"Delay time (in mm:ss format)"));
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String currentTime = off.getDelayTime();
					String newTime = result.get(EventbasePackage.EG_RELATIVE__DELAY_TIME).result;
					if (!newTime.equals(currentTime)) {
						this.hasDoneChanges = true;
						off.setDelayTime(newTime);
						// updatePictogramElement(pes[0]);
					}
				} else if (bo instanceof EGPeriodic) {
					EGPeriodic off = (EGPeriodic) bo;
					properties.put(EventbasePackage.EG_PERIODIC__TIME,
							new RequestContainer(
									new RequestContainer.DatePatternValidator(
											TimeUtil.SHORT_TIME_PATTERN), ""
											+ off.getTime(),
									"Period duration (in mm:ss format)"));

					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String currentTime = off.getTime();
					String newTime = result
							.get(EventbasePackage.EG_PERIODIC__TIME).result;
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
							new RequestContainer.OffsetTimeValidator(), "" + off.getTime(),
							"Period Time (in mm:ss format)"));
					properties.put(key2,
							new RequestContainer(
									new RequestContainer.BinaryValidator(), ""
											+ off.getPattern(), "Pattern"));
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String currentTime = off.getTime();
					String newTime = result.get(key1).result;
					if (!newTime.equals(currentTime)) {
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
					Integer key0 = EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL;
					Integer key1 = EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL;
					Integer key2 = EventbasePackage.EG_DISTRIBUTION__FUNCTION;
					properties.put(key0,new RequestContainer(new RequestContainer.OffsetTimeValidator(),
							"" +off.getPeriodInterval(),"Period Interval (mm:ss)"));
					properties.put(key1,
							new RequestContainer(
									new RequestContainer.OffsetTimeValidator(), ""
											+ off.getEvaluationInterval(), "Evaluation Interval (mm:ss)"));// TODO
					properties.put(key2,
							new RequestContainer(null,UkuConstants.DistributionFunction.FUNCTIONS ,
									"Function",off.getFunction()));// off.getFunction());
					String pa = off.getParameters();
					if(pa == null ||pa.equals(""))
						pa = "0 0 0";
					RequestContainer rc = new RequestContainer(null,null,null);
					rc.currentValue = pa;
					properties.put(-1, rc);
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					//parameters:
					// 1 element is the function type the rest are parameters
					String params = result.get(-1).result;
					log.debug(params);
					off.setParameters(params);
					String currentTime = off.getPeriodInterval();
					String newTime = result.get(key0).result;
					//int newTime = Integer.parseInt(result.get(key1).result);
					if (!newTime.equals(currentTime)) {
						this.hasDoneChanges = true;
						off.setPeriodInterval(newTime);
						// updatePictogramElement(pes[0]);
					}
					
					currentTime = off.getEvaluationInterval();
					newTime = result.get(key1).result;
					//int newTime = Integer.parseInt(result.get(key1).result);
					if (!newTime.equals(currentTime)) {
						this.hasDoneChanges = true;
						off.setEvaluationInterval(newTime);
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
				if(newScope.equalsIgnoreCase("local") || newScope.equalsIgnoreCase("world")){
					newScope = newScope.toUpperCase();
				}
				if (!newScope.equals(scope))
					this.hasDoneChanges = true;
				eg.setScope(newScope);
				String type = eg.getSensorType();
				String newType = result
						.get(EventbasePackage.EVENT_GENERATOR__SENSOR_TYPE).result;
				if (!newType.equals(type))
					this.hasDoneChanges = true;
				eg.setSensorType(newType);
				// repetation
				if (bo instanceof EGRecurring) {
					int newRepetition = Integer
							.parseInt(result
									.get(EventbasePackage.EG_RECURRING__REPETITION).result);
					if (((EGRecurring) bo).getRepetition() != newRepetition) {
						this.hasDoneChanges = true;
					}
					((EGRecurring) bo).setRepetition(newRepetition);
				}

			} else if (bo instanceof EventFilter) {
				if (bo instanceof EFSimple) {
					EFSimple efsimple = (EFSimple) bo;
					String currentConstraints = efsimple.getConstraints();
					
					/*
					if (currentConstraints.contains(","))
						currentConstraints = currentConstraints.substring(0,
								currentConstraints.length() - 1);
					*/
					properties.put(EventbasePackage.EF_SIMPLE__CONSTRAINTS,
							new RequestContainer(null, ""+currentConstraints,
									"Filter's constraints"));

					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String newConstraint = result.get(EventbasePackage.EF_SIMPLE__CONSTRAINTS).result;
					if (!newConstraint.equals(currentConstraints)) {
						this.hasDoneChanges = true;
						efsimple.setConstraints(newConstraint);
						for (String x : newConstraint.split(",")) {
							log.info(x);
						}
					}

				} else if (bo instanceof EFProcessing) {
					EFProcessing efCount = (EFProcessing) bo;
					int currentWindowSize = efCount.getWindowSize();

					properties.put(EventbasePackage.EF_PROCESSING__WINDOW_SIZE,
							new RequestContainer(
									new RequestContainer.IntegerValidator(),
									currentWindowSize + "", "Window Size"));
					result = DialogUtils.askString(question, properties);
					if (result == null)
						return;
					String newConstraint = result
							.get(EventbasePackage.EF_PROCESSING__WINDOW_SIZE).result;
					if (!newConstraint.equals(currentWindowSize)) {
						this.hasDoneChanges = true;
						int window = Integer.parseInt(newConstraint);
						efCount.setWindowSize(window);
						/*
						for (String x : newConstraint.split(",")) {
							System.out.println(x);
						}*/
					}
				} else {
					log.error(bo.getClass().getSimpleName() + " is not implemented yet");
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
