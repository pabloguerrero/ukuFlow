package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * represent an unary numerical expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UnaryNumericalExpression extends NumericalExpression {
	public UnaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	protected UkuExpression operand;

	public void setOperand(UkuExpression n) {
		this.operand = n;
	}

	public UkuExpression getOperand() {
		return operand;
	}

	public String toString() {
		return "(" + getStringOperator() + " " + operand + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
