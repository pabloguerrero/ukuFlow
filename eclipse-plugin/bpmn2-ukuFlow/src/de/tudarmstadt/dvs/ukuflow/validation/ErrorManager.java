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

package de.tudarmstadt.dvs.ukuflow.validation;

import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.handler.UkuConsole;

public class ErrorManager {
	private static ErrorManager instance = null;
	private Set<ErrorMessage> errMsg = new HashSet<ErrorMessage>();
	private Set<WarningMessage> warnMsg = new HashSet<WarningMessage>();

	private ErrorManager() {
	}

	public static ErrorManager getInstance() {
		if (instance == null)
			instance = new ErrorManager();
		return instance;
	}

	/*-----------------------------------------*/
	public void reset() {
		errMsg.clear();
		warnMsg.clear();
		// instance = null;
	}

	public void addError(ErrorMessage msg) {
		errMsg.add(msg);
	}

	public void addError(String location, String msg) {
		addError(new ErrorMessage(location, msg));
	}

	public Set<ErrorMessage> getErrors() {
		return errMsg;
	}

	public int countError() {
		return errMsg.size();
	}

	/*-----------------------------------------*/

	public void addWarning(WarningMessage msg) {
		warnMsg.add(msg);
	}

	public void addWarning(String location, String msg) {
		addWarning(new WarningMessage(location, msg));
	}

	public int countWarnings() {
		return warnMsg.size();
	}

	public Set<WarningMessage> getWarnings() {
		return warnMsg;
	}

	public boolean isValid() {
		return errMsg.size() == 0;
	}

	public void exportTo(UkuConsole console) {
		for (ErrorMessage em : errMsg) {
			console.error("ERROR", em.location, em.message);
		}
		for (WarningMessage wm : warnMsg) {
			console.warn("WARNING", wm.location, wm.message);
		}
	}
}
