package de.tudarmstadt.dvs.ukuflow.xml.entity;

public class UkuExecuteTask extends UkuEntity{
	public UkuEntity nextEntity;
	public String nextEntityID;
	public String script;
	public UkuExecuteTask(String id) {
		super(id);
		script ="";
	}
	
	public void setNextEntity(UkuEntity nextEntity){
		this.nextEntity = nextEntity;
	}
	
	public void setNextEntityID(String id){
		this.nextEntityID = id;
	}
	public void setScript(String script){
		this.script = script;
	}
	
}
