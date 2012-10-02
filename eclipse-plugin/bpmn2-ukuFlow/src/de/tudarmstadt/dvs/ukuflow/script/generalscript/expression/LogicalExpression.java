package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class LogicalExpression extends BoolExpression {

	public static final int AND = UkuConstants.OPERATOR_AND;
	public static final int OR = UkuConstants.OPERATOR_OR;
	public static final int NOT = UkuConstants.OPERATOR_NOT;
	
	public static final int EQUAL = UkuConstants.PREDICATE_EQ;
	public static final int UNEQUAL = UkuConstants.PREDICATE_NEQ;
	
	public static final int GREATERTHAN = UkuConstants.PREDICATE_GT;
	public static final int LESSTHAN = UkuConstants.PREDICATE_LT;
	public static final int GREATEROREQUAL = UkuConstants.PREDICATE_GET;
	public static final int LESSOREQUAL= UkuConstants.PREDICATE_LET;
	/**
	 * @uml.property  name="operator"
	 */
	public int operator = 0;

	public LogicalExpression(int operator) {
		this.operator = operator;
	}

	/**
	 * @param operator
	 * @uml.property  name="operator"
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

	/**
	 * @return
	 * @uml.property  name="operator"
	 */
	public int getOperator() {
		return operator;
	}
}
