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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EAperiodicPatternedEG extends ERecurringEG{	
	public TimeExpression time = null;
	String pattern = "";	
	
	public void setTime(TimeExpression time){
		this.time = time;
	}
	
	public TimeExpression getTime(){
		return time;
	}
	public String getPattern(){
		return pattern;
	}
	public byte getPatternLength(){
		return (byte)pattern.length();
	}
	/**
	 * return the pattern of this event generator in byte
	 * ex "00001010" -> 10
	 * @return
	 */
	public Collection<? extends Byte> getPatternInByte(){
		if(pattern == null)
			return null;
		try{
			int length = pattern.length()/8 + 1;
			List<Byte> result = new ArrayList<Byte>(length);
			
			int t =  Integer.parseInt(pattern, 2);
			for(int i = 0; i < length; i++){
				byte tmp = (byte) (t % 256);
				result.add(length-i-1,tmp);
				log.debug((i+1) + ". byte of the pattern!");
			}
			return result;
		} catch (Exception e){
			return null;
		}
	}
	public void setPattern(String pattern){
		if(pattern.startsWith("p"))
			pattern = pattern.substring(1);
		if(pattern.matches("[0-1]+")){
			//double check?! too much?
			this.pattern = pattern;
			//TODO
			return;
		}
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
		
	}
	@Override
	public String toString(){
		String s ="[PATTERNED="+pattern+"("+time+"s)]";		
		return "PEG_"+getSensorType()+"_"+s+"@"+getScope();
	}
	public static void main(String[] args) {
		String pattern = "00001010";
		int num = Integer.parseInt(pattern, 2);
		System.out.println(num);
	}
}