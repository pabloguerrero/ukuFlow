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
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ETopExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EVariable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ScopeNotExistException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyScopeException;

public class EventBaseVisitorImpl implements EventBaseVisitor{
	
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	@Override
	public void reset(){
		out.clear();
	}

	Vector<Byte> out = new Vector<Byte>();
	@Override
	public void visit(EEventBaseScript eventBaseScript) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexEF ef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterBinaryExpression exp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterPolicy policy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EComplexFilterUnaryExpression exp) {
		
	}

	@Override
	public void visit(ESimpleEF sef) {
		log.debug("visiting Simple EF : " +sef);
		out.add(UkuConstants.EFConstants.SIMPLE_EF);
		out.add(sef.getOperatorID());
		out.add(sef.getChannel());
		out.add((byte)sef.getConstraints().size());
		for(ESimpleFilterNestedConstraint c : sef.getConstraints()){
			c.accept(this);
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
		out.add((byte)UkuConstants.EGConstants.FUNCTIONAL_EG);
		out.add(a.getOperatorID());
		out.add(a.getChannel());
		out.add(a.getSensorType());
		out.add(getScope(a));
		
		out.add(a.getRepetition());
		
		//TODO:
		//1. period length(2bytes in seconds)
		out.addAll(a.getPeriodInterval().getValue(2));
		//2. evaluation frequency (2 bytes, in seconds)
		out.addAll(a.getEvaluationInterval().getValue(2));
		//3. mathematical function (1 byte)
		a.getFunction().accept(this);
		/*
		//4. list of parameters
		for(int param : a.getParameters()){
			out.add((byte)param);
		}*/
		
	}

	private byte getScope(EventGenerator e){
		String scope = e.getScope();
		Random rand = new Random(System.currentTimeMillis());
		byte result = 0;
		if(scope.equalsIgnoreCase("world"))
			return (byte)255;
		if(scope.equalsIgnoreCase("local")){
			return (byte)0;
		}
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
		//TODO
	}
}
