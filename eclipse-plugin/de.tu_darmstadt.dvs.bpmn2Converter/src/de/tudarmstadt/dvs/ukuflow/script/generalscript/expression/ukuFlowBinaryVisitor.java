package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.constant.EventTypes;
import de.tudarmstadt.dvs.ukuflow.constant.ExpressionTypes;
import de.tudarmstadt.dvs.ukuflow.constant.RepositoryField;
import de.tudarmstadt.dvs.ukuflow.constant.WorkflowTypes;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class ukuFlowBinaryVisitor implements ScriptVisitor{
	
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	
	protected Vector<Byte> out = new Vector<Byte>();
	
	public ukuFlowBinaryVisitor(){		
	}
	
	public byte toByte(int v){
		if(v > 255) return 0;
		//TODO checking v
		return(byte)v;
	}
	
	@Override
	public void visit(ukuConstant uConstant) {
		if(uConstant.isBoolValue()){
			//TODO boolean value
			boolean v = (Boolean)uConstant.getValue();
			log.error("boolean is not supported yet");
		} else {
			int v = (Integer)uConstant.getValue();
			//out.add(toByte(ExpressionTypes.REPOSITORY_VALUE));
			out.add(uConstant.getType());
			if(uConstant.getLength() == 2){
				out.add(toByte(v));
			} else if (uConstant.getLength() == 3){
				out.add(toByte(v/256));
				out.add(toByte(v%256));
			}
			out.add(toByte(v));
		}
		
		
	}

	@Override
	public void visit(UkuString uString) {
		out.add(toByte(ExpressionTypes.STRING_VALUE));
		log.debug("string :" + uString.getString());
		
		out.add((byte)(uString.getLength()-2));
		
		for(byte b : uString.getString().getBytes()){
			out.add(b);
		}
	}

	@Override
	public void visit(Variable uVariable) {
		
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
		out.add(toByte(WorkflowTypes.LOCAL_FUNCTION_STATEMENT));
		// id of variable
		if(localF.hasVariable()){
			//TODO a variable manager
			byte variable_id = 0;
			out.add(variable_id);
		} else {
			out.add((byte)0);
		}
		
		int length = fname.length();
		// length of command
		out.add(toByte(length));
		//command
		for(byte b : fname.getBytes()){
			out.add(b);
		}
		// number of parameters
		out.add(toByte(localF.getParams().size()));
		// parameters
		for(UkuExpression pa : localF.getParams()){
			pa.accept(this);
		}
	}

	@Override
	public void visit(ScopeFunction scopeF) {
		out.add(toByte(WorkflowTypes.SCOPED_FUNCTION_STATEMENT));
		//TODO Scope id management or hash?
		//Scope id
		byte scope_id = 0;
		out.add(scope_id);
		//command length
		String fName = scopeF.getFunctionName();
		out.add(toByte(fName.length()));
		//command
		for(byte b : fName.getBytes()){
			out.add(b);
		}
		//no of parameters
		out.add(toByte(scopeF.getParams().size()));
		//parameters
		for(UkuExpression pa: scopeF.getParams()){
			pa.accept(this);
		}
	}

	@Override
	public void visit(ComputationalFunction computationalF) {
		out.add(toByte(WorkflowTypes.COMPUTATION_STATEMENT));
		
		computationalF.getVariable();
		byte id = 0;
		//TODO : variable management
		
		int l = computationalF.getParam().getLength();
		out.add(toByte(l));
		computationalF.getParam().accept(this);
	}	
	
	public static void main(String[] args) {
		System.out.println("?@@@".getBytes()[0]);
		StringBuilder sb = new StringBuilder();
		char c = 256;
		for(int i = 0; i < 20; i++)
			sb.append((char)(c+i));		
		
	}

	@Override
	public void visit(UkuTaskScript ukuTaskScript) {
		// TODO Auto-generated method stub
		
	}
}
