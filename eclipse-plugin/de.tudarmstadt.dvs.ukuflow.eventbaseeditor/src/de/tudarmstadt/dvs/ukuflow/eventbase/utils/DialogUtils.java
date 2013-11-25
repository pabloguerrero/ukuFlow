package de.tudarmstadt.dvs.ukuflow.eventbase.utils;

import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;

public class DialogUtils {

	public static Map<Integer,RequestContainer> askString(String dialogTitle, Map<Integer,RequestContainer> requests) {
		Map ret = null;
		Shell shell = getShell();
		//DialogPage dp;// = new Dialog

		UkuInputDialog inputDialog = new UkuInputDialog(shell, dialogTitle, requests);
		
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValues();
			return ret;
		}
		//PopupDialog
		return null;
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
