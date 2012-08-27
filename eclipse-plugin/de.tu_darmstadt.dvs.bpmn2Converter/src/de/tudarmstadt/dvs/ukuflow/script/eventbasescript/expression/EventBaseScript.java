package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.List;

public class EventBaseScript implements Visitable{
	List<EventBaseOperator> localExp;
	ETopExpression topExp;
	
	public EventBaseScript(List<EventBaseOperator>local,ETopExpression top){
		this.localExp = local;
		this.topExp  = top;
	}
	
	public List<EventBaseOperator> getLocalExp(){
		return localExp;
	}
	
	public ETopExpression getTopExp(){
		return topExp;
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	
}
