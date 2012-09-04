package de.tudarmstadt.dvs.ukuflow.validation;

public class ErrorMessage {
	public String message;
	public String location;
	
	public ErrorMessage(String location, String msg) {
		this.message = msg;
		this.location = location;
	}	
}
