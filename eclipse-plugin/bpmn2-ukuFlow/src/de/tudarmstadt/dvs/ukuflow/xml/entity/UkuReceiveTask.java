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

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.EventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Token;

/**
 * @author ”Hien Quoc Dang”
 *
 */
public class UkuReceiveTask extends UkuActivity {
	EventBaseOperator topOperator;
	EEventBaseScript ebScript;
	/**
	 * @param id
	 */
	public UkuReceiveTask(String id) {
		super(id);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.xml.entity.VisitableElement#accept(de.tudarmstadt.dvs.ukuflow.xml.entity.ElementVisitor)
	 */
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);

	}
	/**
	 * true if there is a valid top operator;
	 * @return
	 */
	public boolean hasScript(){
		return topOperator != null;
	}
	
	public void setScript(String script){
		if(script == null || script.equals("")){
			System.out.println(" receive task has no script !!");
			return;
		}
		EventBaseScript parser = EventBaseScript.getInstance(script);
		try {
			ebScript = parser.validate();
			topOperator = ebScript.getTopExp();
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
			e.printStackTrace();
		}
	}

}
