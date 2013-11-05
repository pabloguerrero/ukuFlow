/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.transaction.ExceptionHandler;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomainEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomainListenerImpl;

public class BPMN2EditingDomainListener extends TransactionalEditingDomainListenerImpl implements ExceptionHandler {
	
	protected BPMN2Editor bpmn2Editor;
	protected BasicDiagnostic diagnostics;

	public BPMN2EditingDomainListener(BPMN2Editor bpmn2Editor) {
		super();
		this.bpmn2Editor = bpmn2Editor;
		TransactionalCommandStack stack = (TransactionalCommandStack) bpmn2Editor.getEditingDomain().getCommandStack();
		stack.setExceptionHandler(this);
	}

	@Override
	public void transactionStarting(TransactionalEditingDomainEvent event) {
		diagnostics = null;
	}
	
	/**
	 * this will be called in case of rollback
	 */
	@Override
	public void transactionClosed(TransactionalEditingDomainEvent event) {
		super.transactionClosed(event);
	}
	
	@Override
	public void handleException(Exception e) {
		String source = null;
		int code = 0;
		String message = e.getMessage();
		Object[] data = null;
		StackTraceElement trace[] = e.getStackTrace();
		if (trace!=null && trace.length>0) {
			source = trace[0].getMethodName();
		}
		if (diagnostics==null) {
			diagnostics = new BasicDiagnostic(source,code,message,data);
		}
		else
			diagnostics.add(new BasicDiagnostic(source,code,message,data));
	}

	public BasicDiagnostic getDiagnostics() {
		return diagnostics;
	}
	
	public IMarker createMarker(IResource resource, int severity, String msg) {
		try {
			IMarker m = resource.createMarker(IMarker.PROBLEM);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, severity);
			return m;
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	
}
