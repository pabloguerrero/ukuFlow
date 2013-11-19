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

package de.tudarmstadt.dvs.ukuflow.script;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class UkuConstants {
	public static class WorkflowOperators {
		/* workflow-types.h */
		public static final byte START_EVENT = 0;
		public static final byte END_EVENT = 1;
		public static final byte EXECUTE_TASK = 2;
		public static final byte PUBLISH_TASK = 3;
		public static final byte SUBSCRIBE_TASK = 4;
		public static final byte SUBWORKFLOW_TASK = 5;
		public static final byte FORK_GATEWAY = 6;
		public static final byte JOIN_GATEWAY = 7;
		public static final byte INCLUSIVE_DECISION_GATEWAY = 8;
		public static final byte INCLUSIVE_JOIN_GATEWAY = 9;
		public static final byte EXCLUSIVE_DECISION_GATEWAY = 10;
		public static final byte EXCLUSIVE_MERGE_GATEWAY = 11;
		public static final byte EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY = 12;
	}

	public static class DistributionFunction {
		public static final byte GAUSSIAN_DISTRIBUTION = 0;// event-types.h
		public static final byte CHI_SQUARE_DISTRIBUTION = 1;// event-types.h
		public static final byte PARETO_DISTRIBUTION = 2;//
		public static List<String> FUNCTIONS = new ArrayList<String>(3);

		static {
			FUNCTIONS.add("GAUSSIAN_DISTRIBUTION");
			FUNCTIONS.add("CHI_SQUARE_DISTRIBUTION");
			FUNCTIONS.add("PARETO_DISTRIBUTION");
		}

		public static List<String> getParameters(byte function) {
			List<String> result = new ArrayList<String>();
			switch (function) {
			case GAUSSIAN_DISTRIBUTION:
				result.add("Mean value (in second)s");
				result.add("Variance (in seconds");
				result.add("Peak (in units");
				break;
			case CHI_SQUARE_DISTRIBUTION:
				result.add("Degrees of freedom");
				break;
			case PARETO_DISTRIBUTION:
				result.add("Shape value");
				break;
			default:
				return null;
			}
			return result;
		}
	}

	public static class EGConstants {
		public static final byte IMMEDIATE_EG = 0;
		public static final byte ABSOLUTE_EG = 1;
		public static final byte OFFSET_EG = 2;
		public static final byte RELATIVE_EG = 3;
		public static final byte PERIODIC_EG = 4;
		public static final byte PATTERNED_EG = 5;
		public static final byte FUNCTIONAL_EG = 6;
	}

	public static class EFConstants {
		/* Event Filter */
		public static final byte SIMPLE_EF = 7;
		public static final byte AND_EC = 8;
		public static final byte OR_EC = 9;
		public static final byte NOT_EC = 10;
		public static final byte SEQUENCE_EC = 11;
		public static final byte MIN_EC = 12;
		public static final byte MAX_EC = 13;
		public static final byte COUNT_EC = 14;
		public static final byte SUM_EC = 15;
		public static final byte AVG_EC = 16;
		public static final byte STDEV_EC = 17;
		public static final byte INCREASE_EC = 18;
		public static final byte DECREASE_EC = 19;
		public static final byte REMAIN_EC = 20;
	}

	public static class OperatorConstants {
		/* expression-types.h */
		public static final byte OPERATOR_AND = 0;
		public static final byte OPERATOR_OR = 1;
		public static final byte OPERATOR_NOT = 2;

		public static final byte PREDICATE_EQ = 3;
		public static final byte PREDICATE_NEQ = 4;
		public static final byte PREDICATE_LT = 5;
		public static final byte PREDICATE_GT = 6;
		public static final byte PREDICATE_LET = 7;
		public static final byte PREDICATE_GET = 8;

		public static final byte OPERATOR_ADD = 9;
		public static final byte OPERATOR_SUB = 10;
		public static final byte OPERATOR_DIV = 11;
		public static final byte OPERATOR_MULT = 12;
		public static final byte OPERATOR_MOD = 13;
	}

	public static class DataTypeConstants {
		public static final byte UINT8_VALUE = 14;
		public static final byte UINT16_VALUE = 15;
		public static final byte INT8_VALUE = 16;
		public static final byte INT16_VALUE = 17;

		public static final byte STRING_VALUE = 18;

		public static final byte REPOSITORY_VALUE = 19;

		public static final byte CUSTOM_INPUT_VALUE = 20;
	}

	public static class SensorTypeConstants {
		public final static byte SENSOR_LIGHT_PAR_RAW = 0;
		public final static byte SENSOR_LIGHT_TSR_RAW = 1;
		public final static byte SENSOR_TEMPERATURE_RAW = 2;
		public final static byte SENSOR_TEMPERATURE_CELSIUS = 3;
		public final static byte SENSOR_TEMPERATURE_FAHRENHEIT = 4;
		public final static byte SENSOR_HUMIDITY_RAW = 5;
		public final static byte SENSOR_HUMIDITY_PERCENT = 6;
		public final static byte SENSOR_ACCM_X_AXIS = 7;
		public final static byte SENSOR_ACCM_Y_AXIS = 8;
		public final static byte SENSOR_ACCM_Z_AXIS = 9;
		public final static byte SENSOR_VOLTAGE_RAW = 10;
		public final static byte SENSOR_CO2_RAW = 11;
		public final static byte SENSOR_CO_RAW = 12;
		public final static byte NODE_ID = 13;
		public final static byte NODE_TIME = 14;

		public static List<String> sensor_types = new ArrayList<String>();

		static {
			sensor_types.add("SENSOR_LIGHT_PAR_RAW");
			sensor_types.add("SENSOR_LIGHT_TSR_RAW");
			sensor_types.add("SENSOR_TEMPERATURE_RAW");
			sensor_types.add("SENSOR_TEMPERATURE_CELSIUS");
			sensor_types.add("SENSOR_TEMPERATURE_FAHRENHEIT");
			sensor_types.add("SENSOR_HUMIDITY_RAW");
			sensor_types.add("SENSOR_HUMIDITY_PERCENT");
			sensor_types.add("SENSOR_ACCM_X_AXIS");
			sensor_types.add("SENSOR_ACCM_Y_AXIS");
			sensor_types.add("SENSOR_ACCM_Z_AXIS");
			sensor_types.add("SENSOR_VOLTAGE_RAW");
			sensor_types.add("SENSOR_CO2_RAW");
			sensor_types.add("SENSOR_CO_RAW");
			sensor_types.add("NODE_ID");
			// this should not be added!!?
			sensor_types.add("NODE_TIME");
		}
	}

	public static class EventFields {
		public final static byte EVENT_TYPE_F = 0;
		public final static byte EVENT_OPERATOR_ID_F = 1;
		public final static byte SOURCE_F = 2;
		public final static byte MAGNITUDE_F = 3;
		public final static byte TIMESTAMP_F = 4;
		public final static byte ORIGIN_NODE_F = 5;
		public final static byte ORIGIN_SCOPE_F = 6;
	}

	/*
	 * register and unregister code
	 */
	public final static byte WF_REGISTER = 0;
	public final static byte WF_DEREGISTER = 1;

	/* statement types */
	public static final byte COMPUTATION_STATEMENT = 0;
	public static final byte LOCAL_FUNCTION_STATEMENT = 1;
	public static final byte SCOPED_FUNCTION_STATEMENT = 2;

	public static String[] getConstantWithValue(byte value, Class clazz) {
		List<String> rs = new ArrayList<String>();
		for (Field f : clazz.getFields()) {

			String name = f.getName();
			if (name.startsWith("SENSOR_") || name.startsWith("NODE_")) {

			} else {
				continue;
			}

			int v;
			try {
				v = f.getByte(clazz.newInstance());
				if (v == value) {
					rs.add(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		String[] result = new String[rs.size()];
		result = (String[]) rs.toArray(new String[rs.size()]);
		return result;
	}
	public static BpmnLog log = BpmnLog.getInstance(UkuConstants.class.getSimpleName());
	public static byte getConstantByName(String name, Class clazz) {
		byte r = -1;
		
		try {
			Field f = clazz.getField(name);
			if (f != null) {
				r = f.getByte(clazz.newInstance());
			}
		} catch (Exception e) {
		}
		
		return r;
	}

	/**
	 * this method return the value of constant with name = '{@code name}'
	 * 
	 * @param name
	 * @return constant's value if it exists, otherwise -1
	 */
	public static byte getConstantByName(String name) {
		byte result = -1;
		result = getConstantByName(name, WorkflowOperators.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, DistributionFunction.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, EGConstants.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, EFConstants.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, OperatorConstants.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, DataTypeConstants.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, EventFields.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, SensorTypeConstants.class);
		if (result != -1)
			return result;
		result = getConstantByName(name, UkuConstants.class);
		
		if(result == -1)
			log.error(name + " is not a defined constant in UkuConstants");
		return result;
	}

	public static void main(String[] args) {
		// test:
		System.out.println(getConstantByName("MAX_EC"));
		System.out.println(getConstantByName("OPERATOR_AND"));
	}
}
