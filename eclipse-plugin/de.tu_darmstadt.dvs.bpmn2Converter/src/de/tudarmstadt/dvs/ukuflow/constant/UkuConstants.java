package de.tudarmstadt.dvs.ukuflow.constant;

import java.lang.reflect.Field;

public class UkuConstants {
	/* event-types.h */
	/* Event Generator */
	public static final int IMMEDIATE_E_GEN = 0;
	public static final int ABSOLUTE_E_GEN = 1;
	public static final int OFFSET_E_GEN = 2;
	public static final int RELATIVE_E_GEN = 3;
	public static final int PERIODIC_E_GEN = 4;
	public static final int PATTERNED_E_GEN = 5;
	public static final int DISTRIBUTED_E_GEN = 6;

	/* Event Filter */
	public static final int SIMPLE_FILTER = 7;
	public static final int AND_COMPOSITION_FILTER = 8;
	public static final int OR_COMPOSITION_FILTER = 9;
	public static final int NOT_COMPOSITION_FILTER = 10;
	public static final int SEQUENCE_COMPOSITION_FILTER = 11;
	public static final int MIN_COMPOSITION_FILTER = 12;
	public static final int MAX_COMPOSITION_FILTER = 13;
	public static final int COUNT_COMPOSITION_FILTER = 14;
	public static final int SUM_COMPOSITION_FILTER = 15;
	public static final int AVG_COMPOSITION_FILTER = 16;
	public static final int STDEV_COMPOSITION_FILTER = 17;
	public static final int INCREASE_FILTER = 18;
	public static final int DECREASE_FILTER = 19;
	public static final int REMAIN_FILTER = 20;

	/* expression-types.h */
	public static final int OPERATOR_AND = 0;
	public static final int OPERATOR_OR = 1;
	public static final int OPERATOR_NOT = 2;

	public static final int PREDICATE_EQ = 3;
	public static final int PREDICATE_NEQ = 4;
	public static final int PREDICATE_LT = 5;
	public static final int PREDICATE_GT = 6;
	public static final int PREDICATE_LET = 7;
	public static final int PREDICATE_GET = 8;

	public static final int OPERATOR_ADD = 9;
	public static final int OPERATOR_SUB = 10;
	public static final int OPERATOR_DIV = 11;
	public static final int OPERATOR_MULT = 12;
	public static final int OPERATOR_MOD = 13;

	public static final int UINT8_VALUE = 14;
	public static final int UINT16_VALUE = 15;
	public static final int INT8_VALUE = 16;
	public static final int INT16_VALUE = 17;

	public static final int STRING_VALUE = 18;

	public static final int REPOSITORY_VALUE = 19;

	public static final int CUSTOM_INPUT_VALUE = 20;

	/*
	 * sensor id
	 */
	public final static int SENSOR_LIGHT_PAR_RAW = 0;
	public final static int SENSOR_LIGHT_TSR_RAW = 1;
	public final static int SENSOR_TEMPERATURE_RAW = 2;
	public final static int SENSOR_TEMPERATURE_CELSIUS = 3;
	public final static int SENSOR_TEMPERATURE_FAHRENHEIT = 4;
	public final static int SENSOR_HUMIDITY_RAW = 5;
	public final static int SENSOR_HUMIDITY_PERCENT = 6;
	public final static int SENSOR_ACCM_X_AXIS = 7;
	public final static int SENSOR_ACCM_Y_AXIS = 8;
	public final static int SENSOR_ACCM_Z_AXIS = 9;
	public final static int SENSOR_VOLTAGE_RAW = 10;
	public final static int SENSOR_CO2 = 11;
	public final static int SENSOR_CO = 12;
	public final static int NODE_ID = 13;

	/*
	 * register and unregister code
	 */
	public final static int WF_REGISTER = 0;
	public final static int WF_DEREGISTER = 1;

	/* workflow-types.h */
	public static final int START_EVENT = 0;
	public static final int END_EVENT = 1;
	public static final int EXECUTE_TASK = 2;
	public static final int PUBLISH_TASK = 3;
	public static final int SUBSCRIBE_TASK = 4;
	public static final int SUBWORKFLOW_TASK = 5;
	public static final int FORK_GATEWAY = 6;
	public static final int JOIN_GATEWAY = 7;
	public static final int INCLUSIVE_DECISION_GATEWAY = 8;
	public static final int INCLUSIVE_JOIN_GATEWAY = 9;
	public static final int EXCLUSIVE_DECISION_GATEWAY = 10;
	public static final int EXCLUSIVE_MERGE_GATEWAY = 11;
	public static final int EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY = 12;

	/* statement types */
	public static final int COMPUTATION_STATEMENT = 0;
	public static final int LOCAL_FUNCTION_STATEMENT = 1;
	public static final int SCOPED_FUNCTION_STATEMENT = 2;
	
	
	public static int getConstantWithName(String name, Class clazz) {
		int r = -1;
		try {
			Field f = clazz.getField(name);
			if (f != null) {
				r = f.getInt(clazz.newInstance());
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
	public static int getConstantWithName(String name) {
		return getConstantWithName(name, UkuConstants.class);
	}
}
