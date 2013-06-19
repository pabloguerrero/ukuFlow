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

import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.Token;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction;

public class UkuExecuteTask extends UkuActivity {
	private List<TaskScriptFunction> statements;
	private boolean hasScript = false;
	private boolean valid = false;

	public UkuExecuteTask(String id) {
		super(id);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
		/*
		if (incoming.size() == 0) {
			addWarningMessage(" this element may never be reached. It has no incoming sequence flow");
		}

		if (outgoing.size() == 0) {
			addWarningMessage(" no outgoing sequence flow");
		}
		if (outgoing.size() > 1 || incoming.size() > 1) {
			addErrorMessage("this element has multiple incoming- or outgoing- sequence flows");
		}*/
	}

	public void setScript(String script) {
		// this.script = script;
		if (script == null || script.equals("")) {
			System.out.println(this.getClass().getSimpleName() + "\t no script");
			return;
		}
		hasScript = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		try {
			System.out.println("parsing "+script);
			statements = parser.parseTaskScript();
			valid = true;
		} catch (Error error) {
			Token tk = parser.token;

			if (tk != null) {
				if (tk.next != null)
					tk = tk.next;
				addErrorMessage(id + ", at line: "
						+ tk.beginLine + "& col: "
						+ tk.beginColumn, "error near the token "
						+ tk);
			} else
				addErrorMessage(error.getMessage());
		} catch (ParseException e) {
			String tkn = null;
			String msg = null;
			Token tk = parser.token;
			if (tk != null) {
				if(tk.next != null)
					tk = tk.next;
				tkn = tk.image;
				msg = "error near the token " + tkn;
				//if (e.getMessage() != null && e.getMessage().startsWith(tkn)) msg = e.getMessage();
				addErrorMessage("element " + id + ", at line: "
						+ tk.beginLine + "& col: "
						+ tk.beginColumn, msg);
			} else {
				addErrorMessage("element " + id,
						"there is an unknown error in the script");
			}			
		}
	}

	public boolean hasScript() {
		return hasScript;
	}
	public boolean isValid(){
		return valid;
	}

	public List<TaskScriptFunction> getStatements() {
		return statements;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

}
