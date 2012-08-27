package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.io.Serializable;

import de.tudarmstadt.dvs.ukuflow.constant.ExpressionTypes;

/**
 * represents a ukuFlow Constant. It could be either an integer constant or
 * boolean constant
 * 
 * @author Hien Quoc Dang
 * 
 */
public class ukuConstant extends PrimaryExpression {
	int intValue;
	boolean boolValue;
	boolean isBool;

	public ukuConstant(int value) {
		intValue = value;
		isBool = false;
	}

	public ukuConstant(boolean value) {
		boolValue = value;
		isBool = true;
	}

	public ukuConstant(String s) {
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
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 
	 * @return
	 */
	public byte getType() {
		if (intValue >= 0 && intValue < 256)
			return (byte) ExpressionTypes.UINT8_VALUE;
		else if (intValue >= 0 && intValue < 65536)
			return (byte) ExpressionTypes.UINT16_VALUE;
		else if (intValue >= -128 && intValue < 128)
			return (byte) ExpressionTypes.INT8_VALUE;
		else if (intValue >= -32768 && intValue < 32768)
			return (byte) ExpressionTypes.INT16_VALUE;
		// TODO throw an exeception
		return -1;
	}

	public String toString() {
		return "" + (isBool ? boolValue : intValue);
	}

	public int getLength() {
		
		switch (getType()) {
		case ExpressionTypes.UINT8_VALUE:
		case ExpressionTypes.INT8_VALUE:
			return 2;
		case ExpressionTypes.UINT16_VALUE:
		case ExpressionTypes.INT16_VALUE:
			return 3;
		}
		
		return 0;
	}
}
