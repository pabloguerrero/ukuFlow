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
