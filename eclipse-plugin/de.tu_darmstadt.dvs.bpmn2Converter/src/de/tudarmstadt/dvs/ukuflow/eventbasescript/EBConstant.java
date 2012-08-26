package de.tudarmstadt.dvs.ukuflow.eventbasescript;

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
	public static final int SENSOR_LIGHT_PAR_RAW = 1;
	public static final int SENSOR_LIGHT_TSR_RAW = 2;
	public static final int SENSOR_TEMPERATURE_RAW = 3;
	public static final int SENSOR_TEMPERATURE_CELSIUS = 4;
	public static final int SENSOR_TEMPERATURE_FAHRENHEIT = 5;
	public static final int SENSOR_HUMIDITY_RAW = 6;
	public static final int SENSOR_HUMIDITY_PERCENT = 7;
	public static final int SENSOR_ACCM_X_AXIS = 8;
	public static final int SENSOR_ACCM_Y_AXIS = 9;
	public static final int SENSOR_ACCM_Z_AXIS = 10;
	public static final int SENSOR_VOLTAGE_RAW = 11;
	public static final int SENSOR_CO2 = 12;
	public static final int SENSOR_CO = 13;
	public static final int NODE_ID = 14;

	/**
	 * this method return the value of constant with name = '{@code name}'
	 * 
	 * @param name
	 * @return constant's value if it exists, otherwise -1
	 */
	public static int getConstantWithName(String name) {
		if(instance == null)
			instance = new EBConstant();
		
		Field f = null;
		
		try {
			f = instance.getClass().getField(name);
		} catch (NoSuchFieldException e) {
			return -1;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e){			
			return -1;
		}
		if(f!= null){
			try {
				return f.getInt(instance);
			} catch (IllegalArgumentException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * @test
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {
		System.out.println(getConstantWithName("SENSOR"));
		System.out.println(getConstantWithName("test"));
		System.out.println(getConstantWithName("SENSOR_VOLTAGE_RAW"));
	}
}
