package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.util.LinkedList;
import java.util.List;


public class UkuTaskScript implements Visitable{
	List<TaskScriptFunction> funs;
	
	public UkuTaskScript(){
		funs = new LinkedList<TaskScriptFunction>();
	}
	
	public UkuTaskScript(List<TaskScriptFunction> funs){
		this.funs = funs;
	}
	
	public void addFunction(TaskScriptFunction ts){
		funs.add(ts);
	}
	
	public List<TaskScriptFunction> getFuns(){
		return funs;
	}
	
	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
		
	}

}
