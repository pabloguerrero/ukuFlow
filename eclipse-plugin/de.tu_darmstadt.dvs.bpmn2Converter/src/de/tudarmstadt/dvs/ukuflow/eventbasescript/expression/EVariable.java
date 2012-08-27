package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;

public class EVariable extends EventBaseOperator {
	String variable = null;
	public EVariable(String v){
		this.variable = v.trim();
	}
}
