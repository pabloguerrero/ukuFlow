package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class UnaryNumericalExpression extends NumericalExpression {
	public UnaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	protected UkuExpression operand;
	public void setOperand(UkuExpression n){
		this.operand = n;
	}
	
	public UkuExpression getOperand(){
		return operand;
	}
	public String toString(){
		return "("+operator+" "+operand+")";
	}
}
