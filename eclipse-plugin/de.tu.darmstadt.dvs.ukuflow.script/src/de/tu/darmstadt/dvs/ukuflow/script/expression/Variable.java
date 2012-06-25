package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class Variable extends PrimaryExpression {
	protected String name;
	
	public Variable(String name) {		
		this.name = name.trim();		
	}
	
	public String toString(){
		return name;
	}
}
