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

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class ESimpleFilterConstraint implements Visitable{
	public int type = -1;
	public int value = -1;
	public int comparator = -1;
	boolean valueFirst;
	public ESimpleFilterConstraint(String type , String op, String value,boolean valueFirst){
		setType(type);
		setValue(value);
		setComparator(op);
		this.valueFirst = valueFirst;
	}
	public Collection<? extends Byte> getValues(){
		ArrayList<Byte> result = new ArrayList<Byte>();
		
		return null;
	}
	public void setType(String type){
		this.type = UkuConstants.getConstantByName(type);		
	}
	public void setValue(String value){
		int v = UkuConstants.getConstantByName(value);
		if(v==-1)
			try{
				v = Integer.parseInt(value);
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
		this.value=v;
	}
	
	public void setComparator(String op){
		comparator = UkuConstants.getConstantByName(op);
	}
	public boolean isValueFirst(){
		return valueFirst;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);	
	}
	
}
