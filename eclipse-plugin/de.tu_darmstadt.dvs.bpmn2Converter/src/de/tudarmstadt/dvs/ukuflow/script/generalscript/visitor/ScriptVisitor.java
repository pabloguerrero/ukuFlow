package de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ComparisonExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuConstant;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuRepositoryField;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuString;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuVariable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ScopeFunction;


public interface ScriptVisitor {
	public void reset();
	// visit primaryexpression
	public void visit(UkuConstant uConstant);
	public void visit(UkuString uString);
	public void visit(UkuVariable uVariable);
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
	//public void visit(UkuTaskScript ukuTaskScript);
	
	public void visit(UkuRepositoryField field);
	public Vector<Byte> getOutput();
}
