package de.tudarmstadt.dvs.ukuflow.validation;

public class WarningMessage {
	public String message;
	public String location;

	public WarningMessage(String location, String msg) {
		this.message = msg;
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof WarningMessage) {
			return ((WarningMessage) o).location.equals(location)
					&& ((WarningMessage) o).message.equals(message);
		}
		return false;
	}
}