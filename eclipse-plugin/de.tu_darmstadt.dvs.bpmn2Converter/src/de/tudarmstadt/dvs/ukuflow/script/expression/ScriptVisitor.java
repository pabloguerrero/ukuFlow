package de.tudarmstadt.dvs.ukuflow.script.expression;

import de.tudarmstadt.dvs.ukuflow.script.function.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.function.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.function.ScopeFunction;

public interface ScriptVisitor {
	// visit primaryexpression
	public void visit(ukuConstant uConstant);
	public void visit(UkuString uString);
	public void visit(Variable uVariable);
	//visit numericalexpression
	public void visit(UnaryNumericalExpression uNumExp);
	public void visit(BinaryNumericalExpression bNumExp);
	// visit boolexpression
	public void visit(ComparisonExpression compExp);
	public void visit(BinaryLogicalExpression bLogicExp);
	public void visit(UnaryLogicalExpression uLogicExp);
	// visit 3 types of statement
	public void visit(LocalFunction localF);
	public void visit(ScopeFunction scopeF);
	public void visit(ComputationalFunction computationalF);
	
	// visit taskScript
	public void visit(UkuTaskScript ukuTaskScript);
}
