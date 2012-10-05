package de.tudarmstadt.dvs.ukuflow.tools.exception;

public class DuplicateScopeNameException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5357229256970036839L;

	public DuplicateScopeNameException(String sName) {
		super(sName);
	}

}
