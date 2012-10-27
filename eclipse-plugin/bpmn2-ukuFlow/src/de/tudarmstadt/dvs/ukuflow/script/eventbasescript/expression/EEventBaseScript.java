package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression;

import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor.EventBaseVisitor;

public class EEventBaseScript implements Visitable{
	List<EventBaseOperator> localExp;
	EventBaseOperator topExp;
	/**
	 * 
	 * @param local reference list
	 * @param top top expression
	 */
	public EEventBaseScript(List<EventBaseOperator>local,EventBaseOperator top){
		this.localExp = local;
		this.topExp  = top;
	}
	
	public List<EventBaseOperator> getLocalExp(){
		return localExp;
	}
	
	public EventBaseOperator getTopExp(){
		return topExp;
	}
	
	@Override
	public void accept(EventBaseVisitor visitor) {
		visitor.visit(this);
	}
	
}
