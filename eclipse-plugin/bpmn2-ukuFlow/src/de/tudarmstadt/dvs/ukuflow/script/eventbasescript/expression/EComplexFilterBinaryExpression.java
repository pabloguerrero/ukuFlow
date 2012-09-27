package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EComplexFilterBinaryExpression extends EComplexFilterExpression{
	int operator = -1;
	private EEvaluableExpression left;
	private EEvaluableExpression right;
	
	public EComplexFilterBinaryExpression(String op, EEvaluableExpression left, EEvaluableExpression right){
		this.left = left;
		this.right = right;
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
	
}
