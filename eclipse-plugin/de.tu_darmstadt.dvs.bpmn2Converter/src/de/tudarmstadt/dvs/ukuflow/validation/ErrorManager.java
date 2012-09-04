package de.tudarmstadt.dvs.ukuflow.validation;

import java.util.LinkedList;
import java.util.List;

public class ErrorManager {
	private static ErrorManager instance = null;
	private List<ErrorMessage> errMsg = new LinkedList<ErrorMessage>();
	
	private ErrorManager(){		
	}
	
	public static ErrorManager getInstance(){
		if(instance == null)
			instance = new ErrorManager();
		return instance;
	}
	/*-----------------------------------------*/
	
	public void addError(ErrorMessage msg){
		errMsg.add(msg);
	}
	
	public void addError(String msg, String element){
		addError(new ErrorMessage(msg, element));
	}
	
	public List<ErrorMessage> getErrors(){
		return errMsg;
	}
	
	public int countError(){
		return errMsg.size();
	}
}
