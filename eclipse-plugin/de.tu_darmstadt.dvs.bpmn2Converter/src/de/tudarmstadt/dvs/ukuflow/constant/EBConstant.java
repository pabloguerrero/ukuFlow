package de.tudarmstadt.dvs.ukuflow.constant;

import java.lang.reflect.Field;

/**
 * this class constaint all constant value which can be used in a event base
 * script
 * 
 * @author Hien Quoc Dang
 * 
 */
public class EBConstant {
	private static EBConstant instance = null;
	/** LOGICAL OPERATOR */
	public static final int AND = 0;
	public static final int OR = 1;
	public static final int NOT = 2;
	/** COMPERISON */
	public static final int GREATERTHAN = 0;
	public static final int LESSTHAN = 1;
	public static final int LESSOREQUAL = 2;
	public static final int GREATEROREQUAL = 3;
	public static final int EQUAL = 4;
	public static final int UNEQUAL = 5;
	/** EVENT OPERATOR KEYWORDS */
	public static final int PERIODIC_EG = 3;
	public static final int PATTERNED_EG = 4;
	public static final int DISTRIBUTION_EG = 5;
	public static final int SIMPLE_EF = 6;
	public static final int COMPLEX_EF = 7;
	/** EVENT OUTPUT TYPE */
	public static final int TYPE = 10;
	public static final int NODE = 11;
	public static final int SENSOR = 12;
	public static final int MAGNITUDE = 13;
	public static final int TIME = 14;
	public static final int SCOPE = 15;
	public static final int TOP = 16;
	
	/** SENSOR TYPE */
	public static final int SENSOR_LIGHT_PAR_RAW = 0;
	public static final int SENSOR_LIGHT_TSR_RAW = 1;
	public static final int SENSOR_TEMPERATURE_RAW = 2;
	public static final int SENSOR_TEMPERATURE_CELSIUS = 3;
	public static final int SENSOR_TEMPERATURE_FAHRENHEIT = 4;
	public static final int SENSOR_HUMIDITY_RAW = 5;
	public static final int SENSOR_HUMIDITY_PERCENT = 6;
	public static final int SENSOR_ACCM_X_AXIS = 7;
	public static final int SENSOR_ACCM_Y_AXIS = 8;
	public static final int SENSOR_ACCM_Z_AXIS = 9;
	public static final int SENSOR_VOLTAGE_RAW = 10;
	public static final int SENSOR_CO2 = 11;
	public static final int SENSOR_CO = 12;
	public static final int NODE_ID = 13;

	public static int getConstantWithNamex(String name, Class clazz){
		int r = -1;
		try {
			Field f = clazz.getField(name);
			if(f!=null){
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
	public static int getConstantWithNamex(String name) {		
		return getConstantWithNamex(name, EBConstant.class);
	}

	/**
	 * @test
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {
		System.out.println(getConstantWithNamex("SENSOR"));
		System.out.println(getConstantWithNamex("test"));
		System.out.println(getConstantWithNamex("SENSOR_VOLTAGE_RAW"));
		System.out.println(getConstantWithNamex("SENSOR_VOLTAGE_RAW",EBConstant.class));
	}
}
