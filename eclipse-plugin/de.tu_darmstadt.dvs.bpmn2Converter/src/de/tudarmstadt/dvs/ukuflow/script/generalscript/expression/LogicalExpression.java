package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.constant.ExpressionTypes;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class LogicalExpression extends BoolExpression {

	public static final int AND = ExpressionTypes.OPERATOR_AND;
	public static final int OR = ExpressionTypes.OPERATOR_OR;

	@Deprecated
	// still not supported
	public static final int XOR = -1;// TODO: define or remove it!?

	public static final int NOT = ExpressionTypes.OPERATOR_NOT;

	public int operator = 0;

	public LogicalExpression(int operator) {
		this.operator = operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public int getOperator() {
		return operator;
	}
}
