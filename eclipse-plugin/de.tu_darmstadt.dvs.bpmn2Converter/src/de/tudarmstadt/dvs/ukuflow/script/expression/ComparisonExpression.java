package de.tudarmstadt.dvs.ukuflow.script.expression;

import de.tudarmstadt.dvs.ukuflow.converter.constant.ExpressionTypes;

/**
 * A comparison expression, which can compare two {@link #UkuExpression}. <br />
 * Supported operators:
 * {@link #EQUAL}, {@link #UNEQUAL}, {@link #GREATERTHAN}, {@link #LESSTHAN}, {@link #GREATEROREQUAL} or {@link #LESSOREQUAL}
 * @author Hien Quoc Dang
 *
 */
public class ComparisonExpression extends BoolExpression {
	
	public static final int EQUAL = ExpressionTypes.PREDICATE_EQ;
	public static final int UNEQUAL = ExpressionTypes.PREDICATE_NEQ;
	public static final int GREATERTHAN = ExpressionTypes.PREDICATE_GT;
	public static final int LESSTHAN = ExpressionTypes.PREDICATE_LT;
	public static final int GREATEROREQUAL = ExpressionTypes.PREDICATE_GET;
	public static final int LESSOREQUAL = ExpressionTypes.PREDICATE_LET;

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
	public int getLength(){
		return 1 + operand1.getLength() + operand2.getLength();
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
	public void accept(ScriptVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		String operator = "";
		switch (this.operator) {
		case EQUAL:
			operator = "==";
			break;
		case UNEQUAL:
			operator = "!=";
			break;
		case GREATERTHAN:
			operator = ">";
			break;
		case LESSTHAN:
			operator = "<";
			break;
		case GREATEROREQUAL:
			operator = ">=";
			break;
		case LESSOREQUAL:
			operator = "<=";
			break;
		}
		String result = "(";
		if(operand1 != null)
			result += operand1 + " ";
		
		result +=operator + " ";
		if(operand2 != null)
			result += operand2;
		result += ")";
		return result;
		
	}

}
