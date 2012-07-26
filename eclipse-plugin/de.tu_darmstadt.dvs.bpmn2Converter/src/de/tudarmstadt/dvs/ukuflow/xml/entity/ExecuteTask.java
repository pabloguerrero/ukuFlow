package de.tudarmstadt.dvs.ukuflow.xml.entity;

public class ExecuteTask extends Entity{
	public Entity nextEntity;
	public String nextEntityID;
	public String script;
	public ExecuteTask(String id) {
		super(id);
		script ="";
	}
	
	public void setNextEntity(Entity nextEntity){
		this.nextEntity = nextEntity;
	}
	
	public void setNextEntityID(String id){
		this.nextEntityID = id;
	}
	public void setScript(String script){
		this.script = script;
	}
	
}
