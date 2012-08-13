package de.tudarmstadt.dvs.ukuflow.converter.constant;

/**
 * @author Hien Quoc Dang
 * 
 */
public class RepositoryField {
	
	/**
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
	
	/**
	 *  register and unregister code
	 */
	public final static int WF_REGISTER = 0;
	public final static int WF_DEREGISTER = 1;
}
