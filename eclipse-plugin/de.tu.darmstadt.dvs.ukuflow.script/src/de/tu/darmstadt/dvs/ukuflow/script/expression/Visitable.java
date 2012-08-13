package de.tu.darmstadt.dvs.ukuflow.script.expression;

/**
 * 
 * @author Hien Quoc Dang
 *
 */
public interface Visitable {
	public void accept(Visitor visitor);
}