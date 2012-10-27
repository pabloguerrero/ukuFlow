package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class ESimpleFilterConstraint implements Visitable{
	int type = -1;
	int value = -1;
	int comparator = -1;
	boolean valueFirst;
	public ESimpleFilterConstraint(String type , String op, String value,boolean valueFirst){
		setType(type);
		setValue(value);
		setComparator(op);
		this.valueFirst = valueFirst;
	}
	
	public void setType(String type){
		this.type = UkuConstants.getConstantWithName(type);		
	}
	public void setValue(String value){
		int v = Integer.parseInt(value);
		this.value=v;
	}
	
	public void setComparator(String op){
		comparator = UkuConstants.getConstantWithName(op);
	}
	public boolean isValueFirst(){
		return valueFirst;
	}
	@Override
	public void accept(EventBaseVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}
