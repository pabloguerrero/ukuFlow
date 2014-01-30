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
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuScopeExpression;
import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyScopeException;

public class UkuScope extends UkuEntity {
	//private String script = null;

	private int ttl = -1;
	private int scopeID = 0;
	private String name = null;
	UkuExpression sExpression = null;
	private boolean hasScript = false;
	public UkuScope(String id) {
		super(id);
	}
	public String getName(){
		return name;
	}
	public void setScript(String script) {
		if(script == null || script.equals("")){
			return;
		}
		hasScript = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		UkuScopeExpression exp = null;
		try {
			exp = parser.scopeExpression();			
		} catch (Error error) {
			if (parser.token != null)
				addErrorMessage("element " + id + ", at line: "
						+ parser.token.beginLine + "& col: "
						+ parser.token.beginColumn, "error near the token "
						+ parser.token);
			else
				addErrorMessage(error.getMessage());
		} catch (ParseException e) {
			addErrorMessage("element " + id + ", at line: "
					+ parser.token.beginLine + "& col: "
					+ parser.token.beginColumn, "error near the token "
					+ parser.token);
			return;
		}
		name = exp.name;
		sExpression = exp.scopeExp;
		try {			
			scopeID = ScopeManager.getInstance().registerScope(name);
		} catch (DuplicateScopeNameException e) {
			addErrorMessage("Scope '" + name + "' already exists");
		} catch (TooManyScopeException e) {
			addErrorMessage("There are too many scope definition (allowed are maximal 256 scopes)");
		}
		if (exp.ttl != null) {
			ttl = exp.ttl;			
		} else {
			addWarningMessage("Time-to-Live for scope '"+name+"' wasn't specified -> using default ttl: 60s");			
			ttl = 60;
		}		
		if (ttl < 0 || ttl > 65535) {
			addErrorMessage("Invalid Time-to-Live for scope '"+name+"' (allowed value is in range 0->65535");
		}
	}

	public int getScopeID() {
		return scopeID;
	}

	public int getTTL() {
		return ttl;
	}

	public UkuExpression getScopeExp() {
		return sExpression;
	}

	public boolean hasScript() {
		return hasScript;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {

	}

}
