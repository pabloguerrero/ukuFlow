package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class LogicalExpression extends BoolExpression{
	public static final int AND  = 1;
	public static final int OR = 1;
	public static final int XOR  = 1;
	public static final int NOT  = 1;
	
	protected int operator = 0;
	
	public LogicalExpression(int operator){
		this.operator = operator;
	}
	
	public void setOperator(int operator){
		this.operator = operator;
	}
	
	public int getOperator(){
		return operator;
	}
}
