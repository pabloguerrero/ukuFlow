package de.tu.darmstadt.dvs.ukuflow.script.expression;

public class UkuString extends PrimaryExpression {
	String s;

	public UkuString(String s) {
		this.s = s;
	}

	public String getString() {
		return s.trim();
	}
	
	public String toString(){
		return s;
	}
}
