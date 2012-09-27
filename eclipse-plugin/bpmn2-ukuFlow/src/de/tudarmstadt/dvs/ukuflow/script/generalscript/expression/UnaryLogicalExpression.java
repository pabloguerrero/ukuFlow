package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

/**
 * represent an unary logical expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UnaryLogicalExpression extends LogicalExpression {

	/**
	 * @uml.property  name="operand"
	 * @uml.associationEnd  
	 */
	public UkuExpression operand;

	public UnaryLogicalExpression(int operator) {
		super(operator);
	}

	public UnaryLogicalExpression(int operator, UkuExpression operand) {
		super(operator);
		this.operand = operand;
	}

	/**
	 * @param b
	 * @uml.property  name="operand"
	 */
	public void setOperand(UkuExpression b) {
		operand = b;
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}
	@Override
	public String toString(){
		return "(NOT "+operand + ")";
	}
	
	public int getLength(){
		return 1 + operand.getLength();
	}
}
