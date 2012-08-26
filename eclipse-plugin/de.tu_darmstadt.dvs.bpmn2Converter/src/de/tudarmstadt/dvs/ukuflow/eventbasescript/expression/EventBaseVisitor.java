package de.tudarmstadt.dvs.ukuflow.eventbasescript.expression;


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
}
