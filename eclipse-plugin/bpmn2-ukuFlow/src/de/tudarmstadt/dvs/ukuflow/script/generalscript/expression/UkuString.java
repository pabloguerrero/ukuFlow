package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

/**
 * represents a ukuFlow String
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UkuString extends PrimaryExpression {
	/**
	 * @uml.property  name="s"
	 */
	String s;

	public UkuString(String s) {
		this.s = s;
	}

	public String getString() {
		return s.trim();
	}

	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}

	public String toString() {
		return "'" + s + "'";
	}

	public int getLength() {
		return s.getBytes().length +2; //STRING_VALUE + LENGTH + s
	}
}
