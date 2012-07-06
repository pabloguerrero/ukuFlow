package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class ComparisonExpression extends BoolExpression {
	public static final int EQUAL = 1;
	public static final int UNEQUAL = 2;
	public static final int GREATERTHAN = 3;
	public static final int LESSTHAN = 4;
	public static final int GREATEROREQUAL = 5;
	public static final int LESSOREQUAL = 6;

	protected int operator = 0;
	protected UkuExpression operand1, operand2;

	public ComparisonExpression(int operator) {
		this.operator = operator;
	}

	public ComparisonExpression(int operator, UkuExpression exp1,
			UkuExpression exp2) {
		this(operator);
		this.operand1 = exp1;
		this.operand2 = exp2;
	}

	public UkuExpression getOperand2() {
		return operand2;
	}

	public void setOperand2(UkuExpression operand2) {
		this.operand2 = operand2;
	}

	public UkuExpression getOperand1() {
		return operand1;
	}

	public void setOperand1(UkuExpression operand1) {
		this.operand1 = operand1;
	}

	@Override
	public String toString() {
		String operator = "";
		switch (this.operator) {
		case 1:
			operator = "==";
			break;
		case 2:
			operator = "!=";
			break;
		case 3:
			operator = ">";
			break;
		case 4:
			operator = "<";
			break;
		case 5:
			operator = ">=";
			break;
		case 6:
			operator = "<=";
			break;
		}
		return ("(" + operand1.toString() + operator + operand2.toString() + ")");
	}

}
