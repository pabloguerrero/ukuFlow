package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * 
 * @author Hien Quoc Dang
 *
 */
public interface Visitable {
	public void accept(ScriptVisitor visitor);
	
}