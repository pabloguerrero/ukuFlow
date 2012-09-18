package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.exception.InvalidRepositoryNameException;

public class UkuRepositoryField extends PrimaryExpression {
	int fieldID = -1;

	public UkuRepositoryField(String name)
			throws InvalidRepositoryNameException {
		if (name.startsWith("NODE_") || name.startsWith("SENSOR_")) {
			int t = UkuConstants.getConstantWithName(name);
			if (0<=t && t <=UkuConstants.getConstantWithName("NODE_ID") )
				fieldID = t;
			else
				throw new InvalidRepositoryNameException();
		} else {
			throw new InvalidRepositoryNameException();
		}
	}
	
	public int getFieldID() {
		return fieldID;
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
