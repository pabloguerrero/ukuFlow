package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * represents ukuFlow variable
 * 
 * @author Hien Quoc Dang
 *
 */
public class Variable extends PrimaryExpression {
	protected String name;
	
	public Variable(String name) {		
		if(name.startsWith("$"))
			this.name = name.substring(1);
		else 
			this.name = name.trim();
	}
	
	@Override
	public void accept(Visitor visitor){
		visitor.visit(this);
	}
	
	public String toString(){
		return "$"+name;
	}
}
