package de.tudarmstadt.dvs.ukuflow.features.generic;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.dialogs.IInputValidator;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.TimeUtil;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.EventBaseScript;

public class RequestContainer {
	public IInputValidator validator;
	public Object requests;
	public String result;
	public String currentValue;
	public String title;

	public RequestContainer(IInputValidator validator, Object requests,
			String title) {
		this.validator = validator;
		this.requests = requests;
		this.title = title;
	}

	public RequestContainer(IInputValidator validator, Object requests,
			String title, String currentValue) {
		this(validator, requests, title);
		this.currentValue = currentValue;
	}

	public String validateInput() {
		if (validator == null)
			return null;
		return validator.isValid(requests.toString());
	}

	public static IntegerValidator getIntValidator() {
		return new RequestContainer.IntegerValidator();
	}

	public static class OffsetTimeValidator implements IInputValidator {

		public String isValid(String newText) {
			if (newText == null || newText.equals(""))
				return "empty input!";
			if (newText.matches("[0-9]+:[0-9]+"))
				return null;
			else
				return "expecting offset time in format: \"mm:ss\"";
		}

	}

	public static class AbsoluteTimeValidator implements IInputValidator {
		public String isValid(String newText) {
			if (newText == null || newText.equals(""))
				return "empty input";
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						TimeUtil.FULL_PATTERN);
				Date d = format.parse(newText);
				if (d.getTime() < 0)
					throw new IllegalArgumentException(
							"Negative time is now allowed");
				return null;
			} catch (IllegalArgumentException ie) {
				return ie.getMessage();
			} catch (Exception e) {
				return "expecting absolute time in format of \""
						+ TimeUtil.FULL_PATTERN + "\"";
			}
		}
	}
	/*
	public static class DatePatternValidator implements IInputValidator {
		String pattern = "";

		public DatePatternValidator(String pattern) {
			this.pattern = pattern;
		}

		public String isValid(String newText) {
			if (newText == null || newText.equals(""))
				return "empty input";
			try {
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				Date d = format.parse(newText);
				if (d.getTime() < 0)
					throw new IllegalArgumentException("negative time is now allowed");
				return null;
			} catch (IllegalArgumentException ie) {
				return ie.getMessage();
			} catch (Exception e) {
				return "expecting input in format of \"" + pattern + "\"!";
			}
		}
	}
	*/
	public static class IntegerValidator implements IInputValidator {
		int min = 0;
		int max = 0;
		boolean minmaxset = false;

		public IntegerValidator() {

		}

		public IntegerValidator(int min, int max) {
			minmaxset = true;
			this.min = min;
			this.max = max;
		}

		public String isValid(String newText) {
			if (newText == null || newText.equals(""))
				return "Empty input";
			try {
				int num = Integer.parseInt(newText);
				if (minmaxset) {
					if (num < min || num > max) {
						return num + " is not in range of (" + min + ", " + max
								+ ")";
					}
				}
				return null;
			} catch (Exception e) {
				return "Expecting a number";
			}
		}
	}

	public static class BinaryValidator implements IInputValidator {

		public String isValid(String newText) {
			if (newText == null || newText.equals(""))
				return "empty input";
			if (newText.matches("[1,0]+"))
				return null;
			else
				return "expecting binary input (e.g 10110)";
		}

	}
	
	public static class SEFConstraintsValidator implements IInputValidator{

		public String isValid(String newText) {
			if(newText.equals(""))
				return null;
			try{
				EventBaseScript.getInstance(newText).is_a_valid_sef_constraint();
			} catch (Exception e){
				return "Invalid format :"+e.getMessage();
			} catch (Error er){
				return "Invalid format :"+er.getMessage();
			}
			return null;
		}
	}
}