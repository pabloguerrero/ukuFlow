package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

/**
 * represent a binary numerical expression
 * 
 * @author Hien Quoc Dang
 * 
 */
public class BinaryNumericalExpression extends NumericalExpression {
	public BinaryNumericalExpression(int operator) {
		super(operator);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="operand1"
	 * @uml.associationEnd  
	 */
	protected UkuExpression operand1;
	/**
	 * @uml.property  name="operand2"
	 * @uml.associationEnd  
	 */
	protected UkuExpression operand2;
	public int getOperator(){
		return operator;
	}
	public int getLength(){
		return 1 + operand1.getLength() + operand2.getLength();
	}
	
	/**
	 * @param op1
	 * @uml.property  name="operand1"
	 */
	public void setOperand1(UkuExpression op1) {
		operand1 = op1;
	}

	/**
	 * @param op2
	 * @uml.property  name="operand2"
	 */
	public void setOperand2(UkuExpression op2) {
		operand2 = op2;
	}

	/**
	 * @return
	 * @uml.property  name="operand1"
	 */
	public UkuExpression getOperand1() {
		return operand1;
	}

	/**
	 * @return
	 * @uml.property  name="operand2"
	 */
	public UkuExpression getOperand2() {
		return operand2;
	}

	public String toString() {
		return ("(" + operand1.toString() + " " + getStringOperator() + " "
				+ operand2.toString() + ")");
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}
}
