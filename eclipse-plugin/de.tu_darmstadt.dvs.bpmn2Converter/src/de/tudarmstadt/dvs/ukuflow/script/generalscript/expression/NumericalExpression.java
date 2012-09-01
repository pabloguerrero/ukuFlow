package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;
import de.tudarmstadt.dvs.ukuflow.constant.*;
/**
 * Numerical Expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public abstract class NumericalExpression extends UkuExpression {
	public static final int ADD = UkuConstants.OPERATOR_ADD;
	public static final int SUB = UkuConstants.OPERATOR_SUB;
	public static final int MUL = UkuConstants.OPERATOR_MULT;
	public static final int DIV = UkuConstants.OPERATOR_DIV;
	public static final int MOD = UkuConstants.OPERATOR_MOD;
	
	public static final int MINUS = 0;//TODO: mapping negative & positive number
	public static final int PLUS = 0;//TODO:

	protected int operator = -1;

	public NumericalExpression(int operator) {
		this.operator = operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getStringOperator(){
		switch(operator) {
		case ADD: return "+";
		case SUB: return "-";
		case MUL: return "*";
		case DIV: return "/";
		case MOD: return "%";
		default : return "?";
		//case MINUS: 
		}
	}
}
