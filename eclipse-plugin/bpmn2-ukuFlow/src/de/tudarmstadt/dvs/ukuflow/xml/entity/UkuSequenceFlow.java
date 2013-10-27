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

package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class UkuSequenceFlow extends UkuEntity {
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public String source;
	public String target;

	private UkuEntity sourceEntity;
	private UkuEntity targetEntity;


	UkuExpression conditionExp = null;
	private boolean hasCondition = false;
	private int priority = -1;
	private boolean isDefault = false;

	public UkuSequenceFlow(String id, String source, String target) {
		super(id);
		this.source = source;
		this.target = target;
	}
	
	public UkuSequenceFlow(String id, UkuEntity source, UkuEntity target){
		super(id);
		this.sourceEntity = source;
		this.targetEntity = target;
	}
	
	public void setTargetID(String target) {
		this.target = target;
	}
	public void setTargetEntity(UkuEntity  target){
		this.targetEntity = target;
	}
	
	public void setSourceID(String source) {
		this.source = source;
	}
	
	public void setSourceEntity(UkuEntity source){
		this.sourceEntity = source;
	}
	
	public void setReference(Map<String, UkuEntity> ref) {
		if (ref.containsKey(source)) {
			sourceEntity = (UkuElement) ref.get(source);
		} else {
			log.error("no source entity in " + id);
			addErrorMessage(" No source entity");
		}
		if (ref.containsKey(target)) {
			targetEntity = (UkuElement) ref.get(target);
		} else {
			log.error("no target entity in " + id);
			addErrorMessage(" No target entity");
		}
	}

	public boolean isDefault() {
		return isDefault;
	}

	public UkuEntity getSourceEntity() {
		return sourceEntity;
	}

	public UkuEntity getTargetEntity() {
		return targetEntity;
	}

	public void setCondition(String condition) {
		if (condition == null || condition.equals("")){
			return;
		}
		hasCondition = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(condition);
		try {
			conditionExp = parser.parseCondition();			
		}catch (Error error) {
			if (parser.token != null)
				addErrorMessage("in sequence flow " + id + ", at line: "
						+ parser.token.beginLine + " column: "
						+ parser.token.beginColumn, "error near the token "
						+ parser.token);
			else
				addErrorMessage(error.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			String msg =e.getMessage().replace("\n", " ");
			String location = "in sequence flow " + id + ", line: "
					+ parser.token.beginLine + " column: "
					+ parser.token.beginColumn;
			addErrorMessage(location, msg);
		}

	}

	public boolean hasCondition() {
		return hasCondition;
	}
	
	public UkuExpression getConditionExp() {
		return conditionExp;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public void setDefaultGateway() {
		isDefault = true;

	}

	public void setPriority(int p) {
		this.priority = p;
	}

	public int getPriority() {
		return priority;
	}
}
