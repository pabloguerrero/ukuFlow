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
package de.tudarmstadt.dvs.ukuflow.deployment.launch.console;

import org.eclipse.debug.internal.ui.DebugPluginImages;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.internal.console.ConsolePluginImages;
import org.eclipse.ui.internal.console.IInternalConsoleConstants;

/**
 * @author hien
 * 
 */
public class DeploymentConsoleView extends AbstractDeploymentConsoleView {

	private boolean isStopped = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tudarmstadt.dvs.ukuflow.deployment.launch.console.
	 * AbstractDeploymentConsoleView#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private class StopAction extends Action {
		public StopAction() {
			setText("Terminate");
			setToolTipText("Erase History and Terminate");
			setImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_LCL_TERMINATE));
			setDisabledImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_TERMINATE));
			setHoverImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_LCL_TERMINATE));
		}

		@Override
		public void run() {
			setStopped();
		}
	}

	private class ClearAction extends Action {
		public ClearAction() {
			setText("Clear Output");
			setToolTipText("Clear Output");
			setImageDescriptor(ConsolePluginImages
					.getImageDescriptor(IInternalConsoleConstants.IMG_ELCL_CLEAR));
			setDisabledImageDescriptor(ConsolePluginImages
					.getImageDescriptor(IInternalConsoleConstants.IMG_DLCL_CLEAR));
			setHoverImageDescriptor(ConsolePluginImages
					.getImageDescriptor(IConsoleConstants.IMG_LCL_CLEAR));
		}

		@Override
		public boolean isEnabled() {
			return textWidget.getText().length() > 0;
		}

		@Override
		public void run() {
			textWidget.setText("");
			setEnabled(false);
		}
	}

	private class RelaunchAction extends Action {
		public RelaunchAction() {
			setText("Reconnect");
			setToolTipText("Reconnect to the sensor node");
			setImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_ELCL_TERMINATE_AND_RELAUNCH));
			setDisabledImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_TERMINATE_AND_RELAUNCH));
			setHoverImageDescriptor(DebugPluginImages
					.getImageDescriptor(IInternalDebugUIConstants.IMG_ELCL_TERMINATE_AND_RELAUNCH));
		}

		public void run() {
			// TODO:?
		}
	}

	private Action stopAction = new StopAction();
	private Action clearAction = new ClearAction();
	private Action relaunchAction = new RelaunchAction();

	private String processName = "";
	private String portName = "";

	private void setStarted() {
		isStopped = false;
		stopAction.setEnabled(true);
		setContentDescription("UkuFlow Deployment (" + processName + "->"
				+ portName + ")");
		// TODO connect to terminal
	}

	private void setStopped() {
		// TODO disconnect terminal
		isStopped = true;
		stopAction.setEnabled(false);
		setContentDescription("<terminated>" + getContentDescription());
	}

	public boolean isStopped() {
		return isStopped;
	}

	@Override
	public void createPartControl(Composite parent) {
		processName = getViewSite().getSecondaryId();// ???TODO what is
														// secondary id?
		createDeploymentPartControl(parent);
	}

	@Override
	protected void createDeploymentPartControl(Composite parent) {
		super.createDeploymentPartControl(parent);
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
		toolbarManager.add(stopAction);
		toolbarManager.add(relaunchAction);
		toolbarManager.add(new Separator());
		toolbarManager.add(clearAction);
		// String processName = getViewSite().getSecondaryId();

	}

	private String buildString(String... in) {
		String result = "";
		for (int i = 0; i < in.length - 1; i++) {
			result += "[" + in[i] + "]";
		}
		result += in[in.length - 1];
		if (!result.endsWith("\n"))
			result += "\n";
		return result;
	}

	public void error(String... errs) {

		displayError(buildString(errs));
	}

	public void out(String... out) {
		displayOutput(buildString(out));
	}

	// / static method:

	private static DeploymentConsoleView show(String fileName, int mode,
			IWorkbenchPage page, String port) {
		try {
			DeploymentConsoleView console = (DeploymentConsoleView) page
					.showView(DEPLOYMENT_VIEW_ID, fileName, mode);
			console.setContentDescription("UkuFlow Deployment (" + port + ")");
			console.setStarted();
			return console;
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DeploymentConsoleView makeVisible(IWorkbenchPage page,
			String fileName, String port) {
		return show(fileName, IWorkbenchPage.VIEW_VISIBLE, page, port);
	}

	public static DeploymentConsoleView makeActive(IWorkbenchPage page,
			String fileName, String port) {
		return show(fileName, IWorkbenchPage.VIEW_ACTIVATE, page, port);
		
	}
}
