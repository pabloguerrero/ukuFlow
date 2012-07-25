package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * 
 * @author Hien Quoc Dang
 *
 */
public abstract class LogicalExpression extends BoolExpression{
	
	public static final int AND  = 0;
	public static final int OR = 1;
	@Deprecated //this should not be supported
	public static final int XOR  = 1;
	public static final int NOT = 2;
	
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
