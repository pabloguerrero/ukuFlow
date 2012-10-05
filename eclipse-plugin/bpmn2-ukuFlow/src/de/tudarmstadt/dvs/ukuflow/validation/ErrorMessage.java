package de.tudarmstadt.dvs.ukuflow.validation;

public class ErrorMessage {
	public String message;
	public String location;
	
	public ErrorMessage(String location, String msg) {
		this.message = msg;
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ErrorMessage) {
			return ((ErrorMessage) o).location.equals(location)
					&& ((ErrorMessage) o).message.equals(message);
		}
		return false;
	}
}
