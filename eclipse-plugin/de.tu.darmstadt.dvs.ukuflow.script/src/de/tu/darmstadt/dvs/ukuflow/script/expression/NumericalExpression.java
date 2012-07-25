package de.tu.darmstadt.dvs.ukuflow.script.expression;
import de.tudarmstadt.dvs.ukuflow.converter.constant.*;
/**
 * Numerical Expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class NumericalExpression extends UkuExpression {
	public static final int ADD = 11;
	public static final int SUB = 12;
	public static final int MUL = 13;
	public static final int DIV = 14;
	public static final int MOD = 15;

	public static final int MINUS = 16;
	public static final int PLUS = 17;

	protected int operator = 10;

	public NumericalExpression(int operator) {
		this.operator = operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
}
