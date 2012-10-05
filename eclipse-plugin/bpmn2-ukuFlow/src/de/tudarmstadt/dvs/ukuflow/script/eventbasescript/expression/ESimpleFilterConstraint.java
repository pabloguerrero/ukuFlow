package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

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
		this.type = UkuConstants.getConstantWithName(type);		
	}
	public void setValue(String value){
		
	}
	
	public void setComparator(String op){
		comparator = UkuConstants.getConstantWithName(op);
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}
