package de.tudarmstadt.dvs.ukuflow.features.generic;

import org.eclipse.jface.dialogs.IInputValidator;

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
	
	public static class IntegerValidator implements IInputValidator{
		public String isValid(String newText){
			if(newText == null || newText.equals(""))
				return "Empty input";
			try {
				int num = Integer.parseInt(newText);
				return null;
			} catch(Exception e){
				return "expecting a number";
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