package de.tu.darmstadt.dvs.ukuflow.script.expression;

import de.tudarmstadt.dvs.ukuflow.script.function.*;

public interface Visitor {
	public void visit(UkuString uString);
	public void visit(Variable uVariable);
	public void visit(UnaryNumericalExpression uNumExp);
	public void visit(BinaryNumericalExpression bNumExp);
	public void visit(ComparisonExpression compExp);
	public void visit(BinaryLogicalExpression bLogicExp);
	public void visit(UnaryLogicalExpression uLogicExp);
	public void visit(LocalFunction localF);
	public void visit(ScopeFunction scopeF);
	public void visit(ComputationalFunction computationalFunction);
}
