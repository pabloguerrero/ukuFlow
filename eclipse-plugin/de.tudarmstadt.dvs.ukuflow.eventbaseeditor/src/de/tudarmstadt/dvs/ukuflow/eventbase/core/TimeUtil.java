package de.tudarmstadt.dvs.ukuflow.eventbase.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtil {
	public static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String SHORT_TIME_PATTERN = "mm:ss";
	private static DateFormat getFormatter(String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format;
	}
	/**
	 * convert into miniseconds using pattern: yyyy-MM-dd HH:mm:ss
	 * @param input
	 * @return
	 */
	public static long convert(String pattern, String input){
		long result = -1;
		DateFormat format = getFormatter(pattern);
		try{
			result = format.parse(input).getTime();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * convert from second to a string of date
	 * @param pattern
	 * @param seconds
	 * @return
	 */
	public static String convertToString(String pattern, long seconds){
		DateFormat format = getFormatter(pattern);
		Date d = new Date(seconds*1000);
		return format.format(d);
	}
	
	public static String getCurrentTime(){
		DateFormat format = getFormatter(FULL_PATTERN);
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));		
		Date d = cal.getTime();	
		return format.format(d);
	}
	
	public static void main(String[] args) {
		System.out.println(convertToString(FULL_PATTERN, 0));
		System.out.println(convertToString(TIME_PATTERN, 12030));
		System.out.println(getCurrentTime());
	}
}
