package de.tu_darmstadt.dvs.debuger;

public class BpmnLog {
	public static boolean debug = true;
	public static boolean info = true;
	public static void debug(Object message){
		if(debug)
			System.out.println(message);
	}
	
	public static void info(Object message){
		if(info)
			System.out.println(message);
	}
}
