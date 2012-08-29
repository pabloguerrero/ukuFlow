package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor;

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


public interface EventBaseVisitor {

	void visit(EventBaseScript eventBaseScript);
	void visit(EComplexEF ef);
	void visit(EComplexFilterBinaryExpression exp);
	void visit(EComplexFilterPolicy policy);
	void visit(EComplexFilterUnaryExpression exp);
	void visit(ESimpleEF sef);
	void visit(ESimpleFilterConstraint sec);
	void visit(ETopExpression top);
	void visit(EAperiodicDistributionEG a);
	void visit(EAperiodicPatternedEG a);
	void visit(EPeriodicEG ep);
	/** FIXME this function might not be unused because EventBaseOperator is abstract */
	void visit(EventBaseOperator op); 	
	void reset();
}
