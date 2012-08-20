package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * represent an unary numerical expression
 * 
 * @author Hien Quoc Dang
 * @suppresswarnings restrict
 */

@SuppressWarnings("restricted")
public class UnaryNumericalExpression extends NumericalExpression {
	public UnaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	protected UkuExpression operand;
	
	public int getOperator(){
		return operator;
	}
	
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
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}
	public int getLength(){
		return 1 + operand.getLength();
	}
}
