/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAbsoluteEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChangeEvent;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChiSquareFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterBinaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterPolicy;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterUnaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EGausianFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EImmediateEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ELogicalCompositionEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EOffsetEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EParetoFunction;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EProcessingEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERelativeEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterNestedConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EVariable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_binary_exp;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_expression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_not;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_primary;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.tools.NumericTools;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ScopeNotExistException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyScopeException;

public class EventBaseVisitorImpl implements EventBaseVisitor{
	
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	StringBuilder debugtext = new StringBuilder();
	@Override
	public void reset(){
		out.clear();
	}

	Vector<Byte> out = new Vector<Byte>();
	@Override
	public void visit(EEventBaseScript eventBaseScript) {
		throw new NullPointerException();
	}

	@Override
	public void visit(EComplexEF ef) {
		throw new NullPointerException();
	}

	@Override
	public void visit(EComplexFilterBinaryExpression exp) {
		throw new NullPointerException();
	}

	@Override
	public void visit(EComplexFilterPolicy policy) {
		throw new NullPointerException();		
	}

	@Override
	public void visit(EComplexFilterUnaryExpression exp) {
		
	}

	@Override
	public void visit(ESimpleEF sef) {
		log.debug("visiting Simple EF : " +sef);
		out.add(UkuConstants.EFConstants.SIMPLE_EF);
		debugtext.append("\n"+UkuConstants.EFConstants.SIMPLE_EF+"[SimpleEF] ");
		out.add(sef.getOperatorID());
		out.add(sef.getChannel());
		debugtext.append(sef.getOperatorID() + "[op_id] "+sef.getChannel() + "[channel] ");
		out.add((byte)sef.getConstraints().size());
		debugtext.append(sef.getConstraints().size() + "[number_of_constraint] ");
		
		for(sef_expression exp : sef.getConstraints()){
			int ph = debugtext.length();
			debugtext.append("<pl> [size]");
			int index = out.size();
			out.add((byte)-1); //placeholder
			exp.accept(this);
			out.set(index,(byte)(out.size()-index-1)); // update site
			debugtext.replace(ph, ph+4, ""+(out.size()-index-1));
		}
		sef.getSource().accept(this);
	}

	@Override
	public void visit(ESimpleFilterConstraint sec) {
		out.add(sec.getComparator());
		if(sec.isValueFirst()){
			out.addAll(sec.getValues());
			out.addAll(sec.getType());
		} else {
			out.addAll(sec.getType());
			out.addAll(sec.getValues());
		}
	}


	@Override
	public void visit(EAperiodicDistributionEG a) {
		debugtext.append("\n");
		out.add((byte)UkuConstants.EGConstants.FUNCTIONAL_EG);
		debugtext.append(UkuConstants.EGConstants.FUNCTIONAL_EG+ "[func_eg] ");
		debugtext.append(a.getOperatorID() + "[op_id] ");
		out.add(a.getOperatorID());
		debugtext.append(a.getChannel() + "[channel] ");
		out.add(a.getChannel());
		debugtext.append(a.getSensorType() + "["+a.sensortype_+"] ");
		out.add(a.getSensorType());
		byte sc = getScope(a);
		debugtext.append(sc + "["+a.getScope()+"] ");
		out.add(sc);
		debugtext.append(a.getRepetition()+ "[repetition] ");
		out.add(a.getRepetition());
		
		//TODO:
		//1. period length(2bytes in seconds)
		debugtext.append(a.getPeriodInterval().getValue(2) + "[period time] ");
		out.addAll(a.getPeriodInterval().getValue(2));
		//2. evaluation frequency (2 bytes, in seconds)
		debugtext.append(a.getEvaluationInterval().getValue(2)+"[evaluation] ");
		out.addAll(a.getEvaluationInterval().getValue(2));
		//3. mathematical function (1 byte)
		a.getFunction().accept(this);
		/*
		//4. list of parameters
		for(int param : a.getParameters()){
			out.add((byte)param);
		}*/
		
	}

	public static byte getScope(EventGenerator e){
		String scope = e.getScope();
		//Random rand = new Random(System.currentTimeMillis());
		byte result = 0;
		if(scope.equalsIgnoreCase("world"))
			return (byte)255;
		if(scope.equalsIgnoreCase("local")){
			return (byte)0;
		}
		System.out.println("getScope: "+scope);
		try {
			return (byte) ScopeManager.getInstance().getScopeID(scope);
		} catch (ScopeNotExistException e1) {
			
		}
		try {
			result = (byte)ScopeManager.getInstance().registerScope(scope);
		} catch (DuplicateScopeNameException e1) {
			e1.printStackTrace();
		} catch (TooManyScopeException e1) {
			e1.printStackTrace();
		}
		/*
		if(scope != null && !scope.equals("")){
			while(result == 0 || result == 255){
				result = (byte)scope.hashCode();
				scope += rand.nextInt();
			}
		}*/
		return result;
	}

	@Override
	public void visit(EAperiodicPatternedEG a) {
		out.add((byte)UkuConstants.EGConstants.PATTERNED_EG);
		out.add(a.getOperatorID());
		out.add(a.getChannel());
		out.add((byte)a.getSensorType());
		out.add(getScope(a));
		
		out.add(a.getRepetition());
		//period length
		out.addAll(a.getTime().getValue(2));
		// pattern
		Collection<? extends Byte> pattern = a.getPatternInByte();
		out.add(a.getPatternLength());
		out.addAll(pattern);
	}
	
	@Override
	public void visit(EPeriodicEG ep) {
		out.add((byte)UkuConstants.EGConstants.PERIODIC_EG);
		out.add(ep.getOperatorID());
		out.add(ep.getChannel());
		out.add((byte)ep.getSensorType());
		byte code = getScope(ep);
		out.add(code);
		
		out.add(ep.getRepetition());
		// period length (2 bytes)
		out.addAll(ep.getTime().getValue(2));
		//done
	}

	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAbsoluteEG)
	 */
	@Override
	public void visit(EAbsoluteEG e) {
		out.add((byte)UkuConstants.EGConstants.ABSOLUTE_EG);
		out.add(e.getOperatorID());		
		out.add(e.getChannel());
		out.add(e.getSensorType());
		out.add(getScope(e));
		out.addAll(e.getTime().getValue());
		//done
	}
	

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EImmediateEG)
	 */
	@Override
	public void visit(EImmediateEG e) {
		out.add(UkuConstants.EGConstants.IMMEDIATE_EG);
		out.add(e.getOperatorID());
		out.add(e.getChannel());
		out.add(e.getSensorType());
		out.add(getScope(e));
		//done
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EOffsetEG)
	 */
	@Override
	public void visit(EOffsetEG e) {
		out.add((byte)UkuConstants.EGConstants.OFFSET_EG);
		out.add(e.getOperatorID());
		out.add(e.getChannel());
		out.add(e.getSensorType());
		out.add(getScope(e));
		out.addAll(e.getTimeExpression().getValue(2));
		//done
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ERelativeEG)
	 */
	@Override
	public void visit(ERelativeEG e) {
		out.add((byte)UkuConstants.EGConstants.RELATIVE_EG);
		out.add(e.getOperatorID());
		out.add(e.getChannel());
		out.add(e.getSensorType()); 
		out.add(getScope(e)); 
		out.addAll(e.getTimeExpression().getValue(2));
		// switching: the input is now the whole event description, not only the channel
		// out.add(e.getSource().getChannel());
		// ->
		
		e.getSource().accept(this);
		//done
	}

	

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EVariable)
	 */
	@Override
	public void visit(EVariable e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChiSquareFunction)
	 */
	@Override
	public void visit(EChiSquareFunction eChiSquareFunction) {
		out.add((byte)UkuConstants.DistributionFunction.CHI_SQUARE_DISTRIBUTION);
		out.add((byte)eChiSquareFunction.getK());
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EGausianFunction)
	 */
	@Override
	public void visit(EGausianFunction eGausianFunction) {
		out.add((byte)UkuConstants.DistributionFunction.GAUSSIAN_DISTRIBUTION);
		out.addAll(convertToArray(eGausianFunction.getM(), 2));
		out.addAll(convertToArray(eGausianFunction.getV(), 2));
		out.add((byte)eGausianFunction.getA());
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EParetoFunction)
	 */
	@Override
	public void visit(EParetoFunction eParetoFunction) {
		out.add((byte)UkuConstants.DistributionFunction.PARETO_DISTRIBUTION);
		out.addAll(convertToArray(eParetoFunction.getA(), 2));
		
	}
	
	private Collection<Byte> convertToArray(int input, int size){
		Collection<Byte> result = new ArrayList<Byte>(size);
		for(int i = 0; i < size; i++){
			input = input >> i*8;
			byte tmp = (byte)(input % 256);
			result.add(tmp);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#getOutput()
	 */
	@Override
	public Vector<Byte> getOutput() {
		return out;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterNestedConstraint)
	 */
	@Override
	public void visit(ESimpleFilterNestedConstraint e) {
		log.debug(" visiting nested constraint: "+e);
		List<ESimpleFilterConstraint> cons = e.getConstraints();
		if(cons.size() == 1){
			int length = out.size();
			out.add((byte)-1);// placeholder
			cons.get(0).accept(this);
			out.set(length, (byte)(out.size() - length-1));// update length
		}
		if(cons.size() > 1){
			int length = out.size();
			out.add((byte)0);//placeholder
			out.add(UkuConstants.OperatorConstants.OPERATOR_OR);
			cons.get(0).accept(this);
			for(int i = 1; i < cons.size(); i++){
				if(i < cons.size()-1){
					out.add(UkuConstants.OperatorConstants.OPERATOR_OR);
				}
				cons.get(i).accept(this);
			}
			out.set(length,(byte)(out.size() - length)); // update length
		}
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EProcessingEF)
	 */
	@Override
	public void visit(EProcessingEF f) {
		log.debug("visiting Processing EF: " +f);
		out.add(f.getTypecode()); // pcf type
		out.add(f.getOperatorID());
		out.add(f.getChannel());
		out.addAll(f.getWindow().getValue(2));
		f.getSource().accept(this);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ELogicalCompositionEF)
	 */
	@Override
	public void visit(ELogicalCompositionEF ef) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChangeEvent)
	 */
	@Override
	public void visit(EChangeEvent eChangeEvent) {
		out.add(eChangeEvent.getTypecode()); // type
		out.add(eChangeEvent.getOperatorID());
		out.add(eChangeEvent.getChannel());
		out.addAll(eChangeEvent.getTime().getValue(2));
		byte first = (byte) (eChangeEvent.getThreshold()/ 256);
		out.add(first);
		out.add((byte)(eChangeEvent.getThreshold()%256));
		EventBaseOperator source = eChangeEvent.getSource();
		if(source != null){
			source.accept(this);
		} else {
			System.err.println("source of "+eChangeEvent + " is null");
		}
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_binary_exp)
	 */
	@Override
	public void visit(sef_binary_exp sef_binary_exp) {
		out.add(sef_binary_exp.getOperator());
		sef_binary_exp.getLeft().accept(this);
		sef_binary_exp.getRight().accept(this);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_primary)
	 */
	@Override
	public void visit(sef_primary sef_primary_) {
		switch (sef_primary_.type){
		case sef_primary.BOOLEAN:
			out.add(UkuConstants.DataTypeConstants.UINT8_VALUE);
			if(sef_primary_.token.equalsIgnoreCase("true"))
				out.add((byte)1);
			else 
				out.add((byte)0);
			break;
		case sef_primary.EVENT_OUTPUT_TYPE:
			out.add(UkuConstants.DataTypeConstants.CUSTOM_INPUT_VALUE);
			out.add(UkuConstants.getConstantByName(sef_primary_.token+"_F"));
			break;
		case sef_primary.NUMBER:
			int n = NumericTools.toInt(sef_primary_.token);
			out.addAll(NumericTools.toByteArray(n));			
			break;
		case sef_primary.SENSOR_TYPE:
			out.add(UkuConstants.DataTypeConstants.UINT8_VALUE);
			out.add(UkuConstants.getConstantByName(sef_primary_.token));
			break;
		case sef_primary.STRING: break;
		}
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor#visit(de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.sef.sef_not)
	 */
	@Override
	public void visit(sef_not sef_not_) {
		out.add(UkuConstants.OperatorConstants.OPERATOR_NOT);
		sef_not_.getExp().accept(this);
	}
}
