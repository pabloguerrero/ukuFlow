package de.tudarmstadt.dvs.ukuflow.eventbase.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import de.tudarmstadt.dvs.ukuflow.preference.UkuPreference;

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
	private static long convert(String pattern, String input){
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
	private static String convertToString(String pattern, long seconds){
		DateFormat format = getFormatter(pattern);
		Date d = new Date(seconds*1000);
		return format.format(d);
	}
	
	public static String getCurrentTime(){
		//DateFormat format = getFormatter(FULL_PATTERN);
		TimeZone zone = TimeZone.getTimeZone(UkuPreference.getTimeZone());
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(zone);
		String result = "";//FULL_PATTERN.trim();		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day= cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		result += ""+year + "-";
		result += (month<10?"0":"")+month + "-";
		result += (day<10?"0":"") + day + " ";
		result += (hour<10?"0":"") + hour + ":";
		result += (minute<10?"0":"") + minute + ":";
		result += (second<10?"0":"") + second;
		return result;
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(convertToString(FULL_PATTERN, 0));
		System.out.println(convertToString(TIME_PATTERN, 12030));
		//System.out.println(getCurrentTime());
		SimpleDateFormat format = new SimpleDateFormat(SHORT_TIME_PATTERN);
		format.setTimeZone(TimeZone.getTimeZone("Asia/Dubei"));
		Date d = format.parse("-10:20");
		System.out.println(d);
	}
}
