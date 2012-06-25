package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class BinaryLogicalExpression extends LogicalExpression {
	
	protected UkuExpression operand1;
	protected UkuExpression operand2;

	public BinaryLogicalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	public void setOperand1(UkuExpression b) {
		operand1 = b;
	}

	public void setOperand2(UkuExpression b) {
		operand2 = b;
	}

	public UkuExpression getOperand1() {
		return operand1;
	}

	public UkuExpression getOperand2() {
		return operand2;
	}
}
