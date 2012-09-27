package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor;

import java.util.Vector;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicDistributionEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EAperiodicPatternedEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterBinaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterPolicy;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EComplexFilterUnaryExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EPeriodicEG;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleFilterConstraint;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ETopExpression;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseScript;

public class EventBaseVisitorImpl implements EventBaseVisitor{
	Vector<Byte> out = new Vector<Byte>();
	@Override
	public void visit(EventBaseScript eventBaseScript) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ESimpleEF sef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ESimpleFilterConstraint sec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ETopExpression top) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EAperiodicDistributionEG a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EAperiodicPatternedEG a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EPeriodicEG ep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EventBaseOperator op) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reset(){
		out.clear();
	}
}
