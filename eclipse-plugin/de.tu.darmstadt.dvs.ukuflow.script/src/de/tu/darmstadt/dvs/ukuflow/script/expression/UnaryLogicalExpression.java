package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class UnaryLogicalExpression extends LogicalExpression{

	protected UkuExpression operand;
	
	public UnaryLogicalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}
	
	public void setOperand(UkuExpression b ){
		operand = b;
	}
}
