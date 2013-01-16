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

import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorMessage;
import de.tudarmstadt.dvs.ukuflow.validation.WarningMessage;

/**
 * @author Hien Quoc Dang
 * 
 */
public abstract class UkuEntity implements VisitableElement {

	// protected boolean syntax = false;
	private ErrorManager errsManager;

	/**
	 * an unique id of for each entity
	 */
	protected String id;

	public UkuEntity(String id, ErrorManager errsM) {
		this.id = id;
		if (errsM == null) {
			errsM = ErrorManager.getInstance();
		}
		errsManager = errsM;
	}

	public UkuEntity(String id) {
		this(id, null);
	}

	public String getID() {
		return id;
	}

	public void addErrorMessage(String msg) {
		addErrorMessage(null, msg);
	}

	public void addErrorMessage(String location, String msg) {
		String l = location;
		if (l == null) {
			l = "element " + id;
		}
		ErrorMessage em = new ErrorMessage(l, msg);
		errsManager.addError(em);
	}

	public void addWarningMessage(String msg) {
		addWarningMessage(null, msg);
	}

	public void addWarningMessage(String location, String msg) {
		String l = location;
		if (l == null) {
			l = "element " + id;
		}
		WarningMessage wm = new WarningMessage(l, msg);
		errsManager.addWarning(wm);
	}

	public abstract void setReference(Map<String, UkuEntity> ref);

	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof UkuEntity) {
			return ((UkuEntity) o).getID().equals(getID());
		}
		return false;
	}
}
