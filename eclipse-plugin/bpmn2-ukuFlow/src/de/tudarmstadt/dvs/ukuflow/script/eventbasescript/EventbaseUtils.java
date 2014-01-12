/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */
package de.tudarmstadt.dvs.ukuflow.script.eventbasescript;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAbsoluteEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChangeEvent;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventFilter;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EImmediateEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ELogicalCompositionEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ENonRecurringEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EOffsetEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EProcessingEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERecurringEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERelativeEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_expression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_or;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

/**
 * @author �Hien Quoc Dang�
 * 
 */
public class EventbaseUtils {
	
	static BpmnLog log = BpmnLog.getInstance(EventbaseUtils.class.getSimpleName());
	/**
	 * convert an script expression entity to corresponding eObject
	 * @param ebo
	 * @return
	 */
	public static EventBaseOperator convert(
			de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator ebo) {
		log.debug("try to convert element :'"+ebo+"'");
		EventBaseOperator result = null;
		EventbaseFactory factory = EventbaseFactory.eINSTANCE;
		if (ebo instanceof de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator) {
			if (ebo instanceof ENonRecurringEG) {
				if (ebo instanceof EAbsoluteEG) {
					result = factory.createEGAbsolute();
					((EGAbsolute)result).setAbsoluteTime(((EAbsoluteEG) ebo).getTime().toString());
				} else if (ebo instanceof EImmediateEG) {
					result = factory.createEGImmediate();
				} else if (ebo instanceof EOffsetEG) {
					result= factory.createEGOffset();
					int tmp = ((EOffsetEG)ebo).getTimeExpression().getValueInt();
					
					((EGOffset)result).setOffsetTime((tmp/60) + ":"+(tmp%60));
				} else if (ebo instanceof ERelativeEG) {
					result = factory.createEGRelative();
					((EGRelative)result).setDelayTime(((ERelativeEG)ebo).getTimeExpression().getOffsetTime());
					//((EGRelative)result).set
				}
				///
				 
			} else if (ebo instanceof ERecurringEG) {
				if (ebo instanceof EAperiodicDistributionEG) {
					result = factory.createEGDistribution();
					//TODO
				} else if (ebo instanceof EAperiodicPatternedEG) {
					result = factory.createEGPatterned();
					
					((EGPatterned)result).setPattern(((EAperiodicPatternedEG) ebo).getPattern());
					((EGPatterned)result).setTime(((EAperiodicPatternedEG) ebo).getTime().getOffsetTime());
				} else if (ebo instanceof EPeriodicEG) {
					result = factory.createEGPeriodic();
					((EGPeriodic)result).setTime(((EPeriodicEG) ebo).getTime().getOffsetTime());
				}
				ERecurringEG rec = (ERecurringEG)ebo;
				((EGRecurring)result).setRepetition(rec.getRepetition());
			}
			((EventGenerator) result).setScope(((de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator) ebo)
					.getScope());
			String sensors[] = UkuConstants
					.getConstantWithValue(
							((de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator) ebo)
									.getSensorType(),
							UkuConstants.SensorTypeConstants.class);
			if (sensors.length == 1){
				((EventGenerator) result).setSensorType(sensors[0]);
			} else {
				log.error("there are more than one sensors with same value " + sensors);
			}
		} else if (ebo instanceof EEventFilter) {
			if(ebo instanceof ESimpleEF){
				result = factory.createEFSimple();
				ESimpleEF simple = (ESimpleEF) ebo;
				String constraints = "";
				for(sef_expression exp : simple.getConstraints()){
					constraints += exp.toString() + ", ";
				}
				if(constraints.endsWith(", ")){
					constraints = constraints.substring(0, constraints.length()-2);
				}
				((EFSimple)result).setConstraints(constraints);
				return result;
			} else if(ebo instanceof EChangeEvent){
				log.error("TODO: implementation for echangeEvent");
			} else if (ebo instanceof ELogicalCompositionEF){
				log.error("TODO: implementation for elogical");
			} else if(ebo instanceof EProcessingEF){
				byte type = ((EProcessingEF)ebo).getTypecode();
				switch (type){
				case UkuConstants.EFConstants.MIN_EC: result = factory.createEFProcessingMin(); break;
				case UkuConstants.EFConstants.MAX_EC: result = factory.createEFProcessingMax(); break;
				case UkuConstants.EFConstants.AVG_EC: result = factory.createEFProcessingAvg(); break;
				case UkuConstants.EFConstants.COUNT_EC: result = factory.createEFProcessingCount(); break;
				case UkuConstants.EFConstants.SUM_EC: result = factory.createEFProcessingSum(); break;
				case UkuConstants.EFConstants.STDEV_EC: result = factory.createEFProcessingStDev(); break;
				}
				((EFProcessing) result).setWindowSize(((EProcessingEF)ebo).getWindow().getOffsetTime());
				return result;
			}
			log.error("TODO: implementing for event filter");
			// TODO
		} else {
			log.error(ebo.getClass().getSimpleName() + "is unknown event base operator?");
		}
		return result;
	}
}
