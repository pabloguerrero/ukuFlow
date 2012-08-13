package de.tu.darmstadt.dvs.ukuflow.script.expression;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */
public class BinaryLogicalExpression extends LogicalExpression {

	protected UkuExpression operand1;
	protected UkuExpression operand2;

	public BinaryLogicalExpression(int operator) {
		super(operator);
	}

	public BinaryLogicalExpression(int operator, UkuExpression operand1,
			UkuExpression operand2) {
		super(operator);

		this.operand1 = operand1;
		this.operand2 = operand2;
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
