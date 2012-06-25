package de.tu.darmstadt.dvs.ukuflow.script.expression;

import java.io.Serializable;

public class Constant extends PrimaryExpression {
	int intValue;
	boolean boolValue;
	boolean isBool;

	public Constant(int value) {		
		intValue = value;
		isBool = false;
	}

	public Constant(boolean value) {
		boolValue = value;
		isBool = true;
	}
	public Constant(String s){
		boolean tmp= false;
		try{
			tmp = Boolean.parseBoolean(s);
			boolValue = tmp;
			isBool = true;
		} catch (Exception e) {
			try{
				int t = Integer.parseInt(s);
				intValue = t;
				isBool = false;
			}catch (Exception ex) {
				System.err.println("The token is neither boolean nor integer");
				e.printStackTrace();
			}
		}
	}

	

	public boolean isBoolValue() {
		return isBool;
	}

	public Serializable getValue() {
		if (isBool)
			return boolValue;
		else
			return intValue;
	}
	public String toString(){
		return ""+ (isBool?boolValue:intValue);
	}
}
