package de.tu.darmstadt.dvs.ukuflow.script.expression;

import java.io.Serializable;

/**
 * represents a ukuFlow Constant. It could be either an integer constant or
 * boolean constant
 * 
 * @author Hien Quoc Dang
 * 
 */
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

	public Constant(String s) {
		boolean tmp = false;
		try {
			tmp = Boolean.parseBoolean(s);
			boolValue = tmp;
			isBool = true;
		} catch (Exception e) {
			try {
				int t = Integer.parseInt(s);
				intValue = t;
				isBool = false;
			} catch (Exception ex) {
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String toString() {
		return "" + (isBool ? boolValue : intValue);
	}
}
