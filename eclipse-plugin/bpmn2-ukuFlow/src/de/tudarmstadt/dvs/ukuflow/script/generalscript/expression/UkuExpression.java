package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.Visitable;

/**
 * Super class of an ukuFlow expression. Every ukuFlow primitives must extends this class direct or indirectly.
 * @author Hien Quoc Dang
 *
 */
public abstract class UkuExpression implements Visitable{
	public abstract int getLength();
}
