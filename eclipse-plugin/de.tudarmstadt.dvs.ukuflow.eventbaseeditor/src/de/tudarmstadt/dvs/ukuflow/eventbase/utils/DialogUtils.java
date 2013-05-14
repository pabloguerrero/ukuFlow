package de.tudarmstadt.dvs.ukuflow.eventbase.utils;

import java.util.Map;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DialogUtils {

	public static Map<String,String> askString(String dialogTitle, Map<String,Object> requests) {
		Map ret = null;
		Shell shell = getShell();
		//DialogPage dp;// = new Dialog

		UkuInputDialog inputDialog = new UkuInputDialog(shell, dialogTitle, requests, null);
		
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValues();
		}
		//PopupDialog
		return ret;
	}
	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
