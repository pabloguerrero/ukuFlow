package de.tu.darmstadt.dvs.ukuflow.script.expression;

/**
 * represent an unary logical expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UnaryLogicalExpression extends LogicalExpression {

	protected UkuExpression operand;

	public UnaryLogicalExpression(int operator) {
		super(operator);
	}

	public UnaryLogicalExpression(int operator, UkuExpression operand) {
		super(operator);
		this.operand = operand;
	}

	public void setOperand(UkuExpression b) {
		operand = b;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
