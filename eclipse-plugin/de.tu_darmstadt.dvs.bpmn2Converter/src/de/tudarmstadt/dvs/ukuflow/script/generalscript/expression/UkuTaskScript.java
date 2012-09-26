package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.Visitable;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;


/**
 * @author  Hien Quoc Dang
 */
public class UkuTaskScript implements Visitable{
	/**
	 * @uml.property  name="funs"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction"
	 */
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
	
	/**
	 * @return
	 * @uml.property  name="funs"
	 */
	public List<TaskScriptFunction> getFuns(){
		return funs;
	}
	
	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
		
	}

}
