package de.tudarmstadt.dvs.ukuflow.validation;

public class WarningMessage {
	public String message;
	public String location;

	public WarningMessage(String location, String msg) {
		this.message = msg;
		this.location = location;
	}
}