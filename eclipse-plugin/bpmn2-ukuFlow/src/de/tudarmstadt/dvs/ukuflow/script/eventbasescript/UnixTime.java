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
package de.tudarmstadt.dvs.ukuflow.script.eventbasescript;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author ”Hien Quoc Dang”
 *
 */
public class UnixTime {
	public static void main(String[] args) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(0);
		
		//cal.set(0, 0, 0);
		//cal.set(1970,1,1,0,0,1);
		
		System.out.print(cal.get(Calendar.YEAR) + "-");
		
		System.out.print(cal.get(Calendar.MONTH)+ "-");
		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		System.out.print(cal.get(Calendar.HOUR_OF_DAY)+ ":");
		System.out.print(cal.get(Calendar.MINUTE)+ ":");
		System.out.println(cal.get(Calendar.SECOND));
		System.out.println(cal.getTimeInMillis()/1000L);
		//Date date = new Date();
		//date.
		//date.setYear(2000);
		String t = "1990-03-12 03:39:44";
		String t1 = "1970-01-01 01:00:01";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = formatter.parse(t1);
			long time = d.getTime()/1000L;
			System.out.println(time);
			//System.out.println(d.getYear());
			//System.out.println(d.getMonth());
			d.setTime(0L);
			String s = formatter.format(d);
			System.out.println(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
