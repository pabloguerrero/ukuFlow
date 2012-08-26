package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.eventbasescript.EBConstant;

public class EComplexFilterUnaryExpression extends EComplexFilterExpression{
	private int operator = -1;
	private EEvaluableExpression exp;
	public EComplexFilterUnaryExpression(String op,EEvaluableExpression exp){
		this.operator = EBConstant.getConstantWithName(op);
		this.exp = exp;
	}
	public int getOperator(){
		return operator;
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}
