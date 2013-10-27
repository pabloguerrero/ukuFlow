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

package de.tudarmstadt.dvs.ukuflow.script.generalscript.functions;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ScopeNotExistException;


public class ScopeFunction extends TaskScriptFunction{
	private String scopeName;
	private String functionName;
	private List<UkuExpression> params;
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public ScopeFunction(String name) {
		scopeName = name;
	}
	public byte getScopeID(){		
		try {
			return (byte) ScopeManager.getInstance().getScopeID(scopeName);
		} catch (ScopeNotExistException e) {			
			//e.printStackTrace();
		}
		log.info("Captain Sum Ting Wong");
		return -1;
	}
	public String getScopeName(){
		return scopeName;
	}
	public String getFunctionName(){
		return functionName;
	}
	public List<UkuExpression> getParams(){
		return params;
	}
	
	public ScopeFunction(String name, String function, List<UkuExpression> params) {
		this.functionName = function;
		this.scopeName = name;
		this.params = params;
		
	}
	
	public void setParams(List<UkuExpression> params){
		this.params = params;
	}
	
	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public String toString(){
		String s = "";
		s += scopeName + " : " + functionName + "(";
		for(int i = 0; i < params.size(); i++){
			if(i == params.size()-1)
				s += params.get(i) + ")";
			else
				s += params.get(i) + ",";
		}
		return s;
	}
	
}
