package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.io.Serializable;
import java.math.BigInteger;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.InvalidConstantValueException;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;

/**
 * represents a ukuFlow Constant. It could be either an integer constant or
 * boolean constant
 * 
 * @author Hien Quoc Dang
 */
public class UkuConstant extends PrimaryExpression {

	byte type = 0;

	int intValue;

	boolean boolValue;

	boolean isBool;
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public UkuConstant(int value) throws InvalidConstantValueException{
		setValue(value);
		isBool = false;
	}

	public UkuConstant(boolean value) {
		boolValue = value;
		isBool = true;
	}

	public UkuConstant(String s) throws InvalidConstantValueException{
		boolean tmp = false;
		try {
			tmp = Boolean.parseBoolean(s);
			boolValue = tmp;
			isBool = true;
		} catch (Exception e) {
			try {
				int radix = 10;
				if(s.startsWith("0x")){
					radix =16;
					s = s.substring(2);
				} else if(s.startsWith("0b")){
					radix = 2;
					s = s.substring(2);
				}
				int t = new BigInteger(s,radix).intValue();
				setValue(t);
				isBool = false;
			} catch(InvalidConstantValueException ex){
				throw ex;
			}catch (Exception ex) {
				log.debug("The token is neither boolean nor integer");
				e.printStackTrace();
			}
		}
	}

	public void setValue(int intValue) throws InvalidConstantValueException{
		if (intValue >= 0 && intValue < 256)
			type = (byte) UkuConstants.UINT8_VALUE;
		else if (intValue >= 0 && intValue < 65536)
			type = (byte) UkuConstants.UINT16_VALUE;
		else if (intValue >= -128 && intValue < 128)
			type = (byte) UkuConstants.INT8_VALUE;
		else if (intValue >= -32768 && intValue < 32768)
			type = (byte) UkuConstants.INT16_VALUE;
		else {
			throw new InvalidConstantValueException();
		}
		this.intValue = intValue;
	}

	/**
	 * @return
	 * @uml.property name="boolValue"
	 */
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
		return type;
	}

	public String toString() {
		return "" + (isBool ? boolValue : intValue);
	}

	public int getLength() {

		switch (getType()) {
		case UkuConstants.UINT8_VALUE:
		case UkuConstants.INT8_VALUE:
			return 2;
		case UkuConstants.UINT16_VALUE:
		case UkuConstants.INT16_VALUE:
			return 3;
		}
		return 0;
	}
}
