package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class BinaryNumericalExpression extends NumericalExpression {
	public BinaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	protected UkuExpression operand1;
	protected UkuExpression operand2;

	public void setOperand1(UkuExpression op1) {
		operand1 = op1;
	}

	public void setOperand2(UkuExpression op2) {
		operand2 = op2;
	}

	public UkuExpression getOperand1() {
		return operand1;
	}

	public UkuExpression getOperand2() {
		return operand2;
	}
	public String toString(){
		return ("("+ operand1.toString() + " "+ operator + " "+operand2.toString()+")");
	}
}
