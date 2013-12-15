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

package de.tudarmstadt.dvs.ukuflow.preference;

import java.util.TimeZone;

import de.tudarmstadt.dvs.ukuflow.converter.Activator;

public class UkuPreference {
	public static final String TIMEOUT = "ukuflow_connection_timeout";
	public static final String OUTPUT_DIR = "ukuflow_output_directory";
	public static final String TIME_ZONE = "ukuflow_timezone";
	public static final String DEFAULT_SCOPE = "ukuflow_default_scope";
	
	public static final String[][] timezones;
	public static String currentTimeZone = "";
	static {
		String[] ids = TimeZone.getAvailableIDs();
	
		timezones = new String[ids.length][2];
		for(int i = 0; i< ids.length; i++){
			timezones[i][0] = ids[i];
			timezones[i][1] = ids[i];
		}
		//Calendar.getInstance().getTimeZone().getID();
	}
	
	public static String getTimeZone(){
		String tmp = Activator.getDefault().getPreferenceStore().getString(UkuPreference.TIME_ZONE);
		return tmp;
	}
	
	public static String getDefaultScope(){
		try{
			String tmp = Activator.getDefault().getPreferenceStore().getString(DEFAULT_SCOPE);
			if(tmp != null)
				return tmp;
			return "";
		}catch (Exception e){
			System.out.println("there is no default value for scope");
			return "";
		}
		
	}
}
