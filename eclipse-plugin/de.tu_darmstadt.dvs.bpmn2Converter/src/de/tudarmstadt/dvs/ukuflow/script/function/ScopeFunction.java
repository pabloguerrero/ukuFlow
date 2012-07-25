package de.tudarmstadt.dvs.ukuflow.script.function;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.expression.PrimaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.expression.Visitor;

public class ScopeFunction implements Visitable{
	private String scopeName;
	private List<PrimaryExpression> params;

	public ScopeFunction(String name) {
		scopeName = name;
	}

	public ScopeFunction(String name, List<PrimaryExpression> params) {
		this.params = params;
	}

	
	@Override
	public void accept(Visitor visitor) {
		//visitor.visit(this);
		// TODO Auto-generated method stub
		
	}
	
}
