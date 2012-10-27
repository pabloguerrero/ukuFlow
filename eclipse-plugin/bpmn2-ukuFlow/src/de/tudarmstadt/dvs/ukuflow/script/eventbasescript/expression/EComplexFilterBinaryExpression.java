package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EComplexFilterBinaryExpression extends EComplexFilterExpression{
	int operator = -1;
	private EEvaluableExpression left;
	private EEvaluableExpression right;
	private String op = null;
	public EComplexFilterBinaryExpression(String op, EEvaluableExpression left, EEvaluableExpression right){
		this.left = left;
		this.right = right;
		this.op = op;
		operator = UkuConstants.getConstantWithName(op);
	}
	public EEvaluableExpression getLeft(){
		return left;
	}
	public EEvaluableExpression getRight(){
		return right;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		
	}
	@Override
	public String toString(){
		return left +""+ op + right;
	}
	
}
