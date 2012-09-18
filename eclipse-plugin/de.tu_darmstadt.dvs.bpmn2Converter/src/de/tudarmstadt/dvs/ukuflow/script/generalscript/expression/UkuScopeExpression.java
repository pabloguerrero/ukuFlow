package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;

public class UkuScopeExpression implements Visitable {
	public String name;
	public UkuExpression scopeExp;
	public int ttl = -1;

	public UkuScopeExpression(String name, int ttl, UkuExpression sExp) {
		this.name = name;
		scopeExp = sExp;
		this.ttl = ttl;
	}
	
	@Override
	public void accept(ScriptVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
