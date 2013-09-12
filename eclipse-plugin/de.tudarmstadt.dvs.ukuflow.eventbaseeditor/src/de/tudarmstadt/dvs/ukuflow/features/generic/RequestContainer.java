package de.tudarmstadt.dvs.ukuflow.features.generic;

import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.IInputValidator;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.TimeUtil;

public class RequestContainer{
	public IInputValidator validator;
	public Object requests;
	public String result;
	public String currentValue;
	public String title;
	public RequestContainer(IInputValidator validator, Object requests, String title){
		this.validator = validator;
		this.requests = requests;
		this.title = title;
	}
	public RequestContainer(IInputValidator validator, Object requests, String title, String currentValue){
		this(validator,requests,title);
		this.currentValue = currentValue;
	}
	public String validateInput(){
		if(validator == null)
			return null;
		return validator.isValid(requests.toString());
	}
	public static IntegerValidator getIntValidator(){
		return new RequestContainer.IntegerValidator();
	}
	public static class OffsetTimeValidator implements IInputValidator{

		public String isValid(String newText) {
			if(newText == null || newText.equals(""))
				return "empty input!";
			if(newText.matches("[0-9]+:[0-9]+"))
				return null;
			else 
				return "expecting offset time in format: \"mm:ss\"";
		}
		
	}
	public static class AbsoluteTimeValidator implements IInputValidator {
		public String isValid(String newText){
			if(newText == null || newText.equals(""))
				return "Empty input";
			try {
				SimpleDateFormat format = new SimpleDateFormat(TimeUtil.FULL_PATTERN);
				format.parse(newText);
				return null;
			} catch(Exception e){
				return "expecting absolute time in format of \""+TimeUtil.FULL_PATTERN+"\"";
			}
		}
	}
	public static class DatePatternValidator implements IInputValidator{
		String pattern = "";
		public DatePatternValidator(String pattern) {
			this.pattern = pattern;
		}
		public String isValid(String newText){
			if(newText == null || newText.equals(""))
				return "Empty input";
			try{
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				format.parse(newText);
				return null;
			}catch (Exception e){
				return "expecting input in format of \""+pattern + "\"!";
			}
		}
	}
	public static class IntegerValidator implements IInputValidator{
		public String isValid(String newText){
			if(newText == null || newText.equals(""))
				return "Empty input";
			try {
				int num = Integer.parseInt(newText);
				return null;
			} catch(Exception e){
				return "Expecting a number";
			}
		}
	}
	public static class BinaryValidator implements IInputValidator{

		public String isValid(String newText) {
			if(newText == null || newText.equals(""))
				return "empty input";
			if( newText.matches("[1,0]+"))
				return null;
			else 
				return "expecting binary input (e.g 10110)";
		}
		
	}
}