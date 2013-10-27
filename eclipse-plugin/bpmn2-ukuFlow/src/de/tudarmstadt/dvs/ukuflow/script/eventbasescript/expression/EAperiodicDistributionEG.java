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

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EAperiodicDistributionEG extends ERecurringEG{
	private String functionName;
	//private List<Integer> parameters;
	private EDistributionFunction function;
	private TimeExpression periodInterval;
	private TimeExpression evaluationInterval;
	
	//public void setFunction(String name, List<Integer> params){
	//	this.functionName = name;
		//this.parameters = params;
	//}
	public void setFunction(EDistributionFunction f){
		function = f;
	}
	public EDistributionFunction getFunction(){
		return function;
	}
	
	//public List<Integer> getParameters(){
	//	return parameters;
	//}
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	public void setPeriodInterval(TimeExpression period){
		this.periodInterval = period;
	}
	public void setEvaluationInterval(TimeExpression eval){
		this.evaluationInterval = eval;
	}
	public TimeExpression getPeriodInterval(){
		return periodInterval;
	}
	public TimeExpression getEvaluationInterval(){
		return evaluationInterval;
	}
	@Override
	public String toString(){
		String s="";
		s+= "[DISTRIBUTION="+functionName+"(";	
		//if(parameters != null)
		//for(Integer i : parameters){
		//	s+= i +",";
		//}
		s+="__";
		s= s.replace(",__",")");
		return "PEG_"+getSensorType()+"_"+s+"@"+getScope();
	}
}