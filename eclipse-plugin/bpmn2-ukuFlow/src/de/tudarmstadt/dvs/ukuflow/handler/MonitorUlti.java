package de.tudarmstadt.dvs.ukuflow.handler;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;

import org.eclipse.ui.PlatformUI;

public final class MonitorUlti {
	public static Display getDisplay() {
		Display retVal = Display.getCurrent();
		if (retVal == null || retVal.isDisposed()) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				retVal = workbench.getDisplay();
			}
			if (retVal == null || retVal.isDisposed()) {
				retVal = Display.getDefault();
			}
		}
		return retVal;
	}

	private MonitorUlti() {
	}

}