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
package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

/**
 * @author �Hien Quoc Dang�
 *
 */
public class TimeExpression extends EEvaluableExpression {
	
	public int year = 0;
	public int month = 0 ;
	public int day = 0;
	public int hour = 0;
	public int minute = 0;
	public int second = 0;
	public int minisecond = 0;
	
	@Override
	public String toString(){
		String r = "";		
		r += day + ":"+month+":" +year + " "+hour+":"+minute+":"+second+":"+minisecond;
		
		return r;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable#accept(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor)
	 */
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	public int getValueInt(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = String.format("%04d-%02d-%02d %02d:%02d:%02d", year,month,day,hour,minute,second);
		
			Date d;
			try {
				d = format.parse(date);
				long seconds = (int) (d.getTime()/1000L);
				if(seconds < 0)
					seconds = (60*hour+minute)*60 +second;
				return (int)seconds;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return 0;
	}
	public Collection<? extends Byte> getValue(){
		return getValue(4);
	}
	/**
	 * return the time in bytes of seconds
	 * @return
	 */
	public Collection<? extends Byte> getValue(int length) {
		Collection<Byte> result = new ArrayList<Byte>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = String.format("%04d-%02d-%02d %02d:%02d:%02d", year,month,day,hour,minute,second);
		try {
			Date d = format.parse(date);
			long seconds = d.getTime()/1000L;
			if(seconds < 0)
				seconds = (hour*60+minute)*60 + second;
			if(length==4)
			result.add((byte)(seconds / (256*256*256)));
			if(length>=3)
			result.add((byte)(seconds / (256*256)));
			if(length>=2)
			result.add((byte)(seconds / (256)));
			if(length>=1)
			result.add((byte)(seconds % (256)));
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}