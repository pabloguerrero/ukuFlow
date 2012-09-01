package de.tudarmstadt.dvs.ukuflow.handler;

public class ErrorMessage {
	public String description;
	public String element;
	public Integer column, row;
	public ErrorMessage(String description,String element){
		this.description = description;
		this.element = element;		
	}
	public ErrorMessage(String des,String e, Integer row,Integer col){
		this(des,e);
		this.column = col;
		this.row = row;
	}	
}
