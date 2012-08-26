package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.eventbasescript.EBConstant;

public class ESimpleFilterConstraint implements Visitable{
	int type = -1;
	int value = -1;
	int comparator = -1;
	public ESimpleFilterConstraint(String type , String op, String value){
		setType(type);
		setValue(value);
		setComparator(op);
	}
	
	public void setType(String type){
		this.type = EBConstant.getConstantWithName(type);		
	}
	public void setValue(String value){
		
	}
	
	public void setComparator(String op){
		comparator = EBConstant.getConstantWithName(op);
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}