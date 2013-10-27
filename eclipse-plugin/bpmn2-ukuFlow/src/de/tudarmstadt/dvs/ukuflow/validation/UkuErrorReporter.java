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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Single Ton UkuErrorReporter is used for adding new error while validating a
 * workflow the error could be add to the buid-in "Problems" tab in eclipse or
 * print out to the console
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UkuErrorReporter {

	private static UkuErrorReporter instance;
	public static final String CONSOLE_NAME = "ukuflow console";

	public boolean toProblems = true;

	MessageConsole myConsole = null;
	MessageConsoleStream out = null;
	IResource file = null;
	int errorCounter;
	int warningCounter;

	private UkuErrorReporter() {
		myConsole = findConsole(CONSOLE_NAME);
		out = myConsole.newMessageStream();
		out.setActivateOnWrite(true);
		errorCounter = 0;
		warningCounter = 0;
	}

	public static UkuErrorReporter getInstance() {
		if (instance == null) {
			instance = new UkuErrorReporter();
		}
		return instance;
	}

	public void setFile(IResource file) {
		this.file = file;
	}

	public void addError(String msg, String location) {
		if (toProblems) {
			addErrortoProblems(msg, location);
		}
		addErrortoConsole(msg, location);
		errorCounter++;
	}

	public void addWarning(String msg, String location) {
		if (toProblems) {
			addWarningtoProblems(msg, location);
		}
		addWarningtoConsole(msg, location);
		warningCounter++;
	}

	public int countErrors() {
		return errorCounter;
	}

	public int countWarning() {
		return warningCounter;
	}

	private void addWarningtoConsole(String msg, String location) {
		// TODO Auto-generated method stub

	}

	private void addWarningtoProblems(String msg, String location) {
		// TODO Auto-generated method stub

	}

	private void addErrortoConsole(String msg, String location) {
		writeMarkers(location, msg, false);
	}

	private void addErrortoProblems(String msg, String location) {
		writeMarkers(location, msg, true);
	}

	/**
	 * find the console with name = {@code name}. If no console is found, create
	 * one.
	 * 
	 * @param name
	 * @return
	 */
	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

	private void writeMarkers(String location, String msg, boolean error) {
		if (file == null) {
			return;
		}
		IMarker m = null;
		int type = error ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING;
		try {
			m = file.createMarker(IMarker.PROBLEM);
			m.setAttribute(IMarker.LOCATION, location);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, type);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
