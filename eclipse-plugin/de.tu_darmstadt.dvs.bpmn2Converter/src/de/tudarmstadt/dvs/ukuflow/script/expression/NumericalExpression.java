package de.tudarmstadt.dvs.ukuflow.script.expression;
import de.tudarmstadt.dvs.ukuflow.converter.constant.*;
/**
 * Numerical Expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class NumericalExpression extends UkuExpression {
	public static final int ADD = ExpressionTypes.OPERATOR_ADD;
	public static final int SUB = ExpressionTypes.OPERATOR_SUB;
	public static final int MUL = ExpressionTypes.OPERATOR_MULT;
	public static final int DIV = ExpressionTypes.OPERATOR_DIV;
	public static final int MOD = ExpressionTypes.OPERATOR_MOD;

	public static final int MINUS = 0;//TODO: mapping negative & positive number
	public static final int PLUS = 0;//TODO:

	protected int operator = -1;

	public NumericalExpression(int operator) {
		this.operator = operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
}
