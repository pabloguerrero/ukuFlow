package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.Visitable;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorMessage;
import de.tudarmstadt.dvs.ukuflow.validation.WarningMessage;

/**
 * @author Hien Quoc Dang
 * 
 */
public abstract class UkuEntity implements VisitableElement {

	protected boolean syntax = false;
	protected List<ErrorMessage> errors = new LinkedList<ErrorMessage>();
	protected List<WarningMessage> warnings = new LinkedList<WarningMessage>();

	/**
	 * an unique id of for each entity
	 */
	protected String id;

	public UkuEntity(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	/**
	 * get the error messages
	 * 
	 * @return
	 */
	public List<ErrorMessage> getError() {
		final List<ErrorMessage> result = new LinkedList<ErrorMessage>(errors);
		return result;
	}

	/**
	 * get the warning messages
	 * 
	 * @return
	 */
	public List<WarningMessage> getWarnings() {
		final List<WarningMessage> result = new LinkedList<WarningMessage>(
				warnings);
		return result;
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
		errors.add(em);
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
		warnings.add(wm);
	}

	/**
	 * 
	 * @return true if the syntax of this entity was correct
	 */
	public boolean isCorrect() {
		return syntax;
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
