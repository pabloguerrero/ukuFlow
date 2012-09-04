package de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.BinaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ComparisonExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuString;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuTaskScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuVariable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryLogicalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UnaryNumericalExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuConstant;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ScopeFunction;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class ScriptVisitorImpl implements ScriptVisitor {

	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());

	protected Vector<Byte> out = new Vector<Byte>();

	public ScriptVisitorImpl() {
	}

	public byte toByte(int v) {
		if (v > 255)
			return 0;
		// TODO checking v
		return (byte) v;
	}

	@Override
	public void visit(UkuConstant uConstant) {
		if (uConstant.isBoolValue()) {
			boolean v = (Boolean) uConstant.getValue();
			log.error("boolean is not supported yet");
		} else {
			int v = (Integer) uConstant.getValue();
			// out.add(toByte(ExpressionTypes.REPOSITORY_VALUE));
			out.add(uConstant.getType());
			if (uConstant.getLength() == 2) {
				out.add(toByte(v));
				log.debug("length  = 2");
			} else if (uConstant.getLength() == 3) {
				out.add(toByte(v / 256));
				out.add(toByte(v % 256));
			}
			// out.add(toByte(v));
		}

	}

	@Override
	public void visit(UkuString uString) {
		out.add(toByte(UkuConstants.STRING_VALUE));
		log.debug("string :" + uString.getString());

		out.add((byte) (uString.getLength() - 2));

		for (byte b : uString.getString().getBytes()) {
			out.add(b);
		}
	}

	@Override
	public void visit(UkuVariable uVariable) {
		out.add(toByte(UkuConstants.REPOSITORY_VALUE));
		out.add(toByte(uVariable.getID()));
	}

	@Override
	public void visit(UnaryNumericalExpression uNumExp) {
		out.add(toByte(uNumExp.getOperator()));
		uNumExp.getOperand().accept(this);
	}

	@Override
	public void visit(BinaryNumericalExpression bNumExp) {
		out.add(toByte(bNumExp.getOperator()));
		bNumExp.getOperand1().accept(this);
		bNumExp.getOperand2().accept(this);
	}

	@Override
	public void visit(ComparisonExpression compExp) {
		out.add(toByte(compExp.operator));
		compExp.getOperand1().accept(this);
		compExp.getOperand2().accept(this);
	}

	@Override
	public void visit(BinaryLogicalExpression bLogicExp) {
		out.add(toByte(bLogicExp.operator));
		bLogicExp.operand1.accept(this);
		bLogicExp.operand2.accept(this);
	}

	@Override
	public void visit(UnaryLogicalExpression uLogicExp) {
		out.add(toByte(uLogicExp.operator));
		uLogicExp.operand.accept(this);

	}

	@Override
	public void visit(LocalFunction localF) {
		String fname = localF.getFunctionName();
		// LOCAL_FUNCTION_STATEMENT
		out.add(toByte(UkuConstants.LOCAL_FUNCTION_STATEMENT));
		// id of variable
		if (localF.hasVariable()) {
			// TODO a variable manager
			byte variable_id = (byte) localF.getVariable().getID();
			out.add(variable_id);
		} else {
			out.add((byte) 0);
		}

		int length = fname.length();
		// length of command
		out.add(toByte(length));
		// no of parameters
		out.add(toByte(localF.getParams().size()));
		// command
		for (byte b : fname.getBytes()) {
			out.add(b);
		}
		// parameters
		for (UkuExpression pa : localF.getParams()) {
			pa.accept(this);
		}
	}

	@Override
	public void visit(ScopeFunction scopeF) {
		out.add(toByte(UkuConstants.SCOPED_FUNCTION_STATEMENT));
		// TODO HIEN Scope id management?
		// Scope id
		byte scope_id = scopeF.getScopeID();
		out.add(scope_id);
		// command length
		String fName = scopeF.getFunctionName();
		out.add(toByte(fName.length()));

		// no of parameters
		out.add(toByte(scopeF.getParams().size()));

		// command
		for (byte b : fName.getBytes()) {
			out.add(b);
		}

		// parameters
		for (UkuExpression pa : scopeF.getParams()) {
			pa.accept(this);
		}
	}

	@Override
	public void visit(ComputationalFunction computationalF) {
		out.add(toByte(UkuConstants.COMPUTATION_STATEMENT));

		byte id = 0;
		id = toByte(computationalF.getVariable().getID());
		out.add(id);

		int l = computationalF.getParam().getLength();
		out.add(toByte(l));
		computationalF.getParam().accept(this);
	}

	public static void main(String[] args) {
		System.out.println("?@@@".getBytes()[0]);
		StringBuilder sb = new StringBuilder();
		char c = 256;
		for (int i = 0; i < 20; i++)
			sb.append((char) (c + i));

	}

	@Override
	public void visit(UkuTaskScript ukuTaskScript) {
		// TODO Auto-generated method stub
		log.error("visit is called");
	}

	@Override
	public Vector<Byte> getOutput() {
		return out;
	}

	@Override
	public void reset() {
		out.clear();
	}
}
