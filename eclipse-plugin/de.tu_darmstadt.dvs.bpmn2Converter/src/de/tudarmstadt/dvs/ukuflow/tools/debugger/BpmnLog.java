package de.tudarmstadt.dvs.ukuflow.tools.debugger;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * for debugging
 * 
 * @author Hien Quoc Dang
 *
 */
public class BpmnLog {
	public static boolean debug = true;
	public static boolean info = true;
	public static boolean warn = true;
	public static boolean error = true;
	private String className = "unknown";
	public static final int SIZE = 20;
	public static final String pattern = "                    ";
	private BpmnLog(String clazz){
		this.className= clazz;
	}
	
	public static BpmnLog getInstance(String className){
		return new BpmnLog(className);
	}
	public static String timeNow(){
		return "["+DateFormat.getTimeInstance(DateFormat.MEDIUM,Locale.GERMANY).format(new Date(System.currentTimeMillis()))+"]";
	}
	public void debug(Object message){
		if(debug)
			System.out.println(generateMessage(timeNow(), "DEBUG:", className, message));	
	}
	
	public void info(Object message){
		if(info)
			System.out.println(generateMessage(timeNow(), "INFO:", className, message));
	}
	
	public void warn(Object message){
		if(warn)
			System.out.println(generateMessage(timeNow(), "INFO:", className, message));
	}
	public void error(Object message){
		if(error)
			System.out.println(generateMessage(timeNow(), "ERROR:", className, message));
	}
	private String generateMessage(String time,String type, String clazz, Object msg){
		StringBuilder sb = new StringBuilder();
		sb.append(time+"\t");
		sb.append(type +"\t");
		if(clazz.length()>20)
			sb.append(clazz);
		else {
			sb.append(pattern.substring(0,SIZE-clazz.length()));
			sb.append(clazz);
		}
		sb.append(" - "+msg);
		return sb.toString();
	}
}
