package de.tudarmstadt.dvs.ukuflow.validation;

import de.tudarmstadt.dvs.ukuflow.xml.entity.*;

public class UkuProcessValidation {
	UkuProcess process = null;
	UkuErrorReporter reporter;
	public UkuProcessValidation(UkuProcess process){
		this.process = process;
		reporter = UkuErrorReporter.getInstance();
	}
	
	public boolean validate(){
		for(UkuEntity e : process.getEntities()){
			if(e instanceof UkuEvent){
				validate((UkuEvent)e);
			}else if(e instanceof UkuExecuteTask){
				validate((UkuExecuteTask)e);
			}else if(e instanceof UkuScope){
				validate((UkuScope)e);
			} else if(e instanceof UkuGateway){
				validate((UkuGateway)e);
			} else if(e instanceof UkuSequenceFlow){
				validate((UkuSequenceFlow)e);
			} else {
				System.out.println("error");
			}
		}
		return false;
	}
	private void validate(UkuEvent event){
		//TODO
	}
	private void validate(UkuExecuteTask task){
		System.out.println(task.getOutgoing().size());
		//TODO		
	}
	public void validate(UkuScope scope){
		//TODO
	}
	public void validate(UkuGateway gway){
		//TODO
	}
	public void validate(UkuSequenceFlow sef){
		//TODO
	}
	
}
