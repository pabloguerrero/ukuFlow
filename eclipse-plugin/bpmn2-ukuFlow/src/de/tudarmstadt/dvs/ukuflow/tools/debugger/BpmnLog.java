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
