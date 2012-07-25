package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * represents a ukuFlow String
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UkuString extends PrimaryExpression {
	String s;

	public UkuString(String s) {
		this.s = s;
	}

	public String getString() {
		return s.trim();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String toString() {
		return s;
	}
}
