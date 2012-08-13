package de.tu.darmstadt.dvs.ukuflow.script.expression;

/**
 * represents ukuFlow variable
 * 
 * @author Hien Quoc Dang
 *
 */
public class Variable extends PrimaryExpression {
	protected String name;
	
	public Variable(String name) {		
		this.name = name.trim();		
	}
	
	@Override
	public void accept(Visitor visitor){
		visitor.visit(this);
	}
	
	public String toString(){
		return name;
	}
}
