package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.constant.ExpressionTypes;
import de.tudarmstadt.dvs.ukuflow.constant.UkuConstants;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class LogicalExpression extends BoolExpression {

	public static final int AND = UkuConstants.OPERATOR_AND;
	public static final int OR = UkuConstants.OPERATOR_OR;
	public static final int NOT = UkuConstants.OPERATOR_NOT;

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
