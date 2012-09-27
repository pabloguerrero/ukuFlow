package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.VariableManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.visitor.ScriptVisitor;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.NotRegisteredVariableException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyVariableException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.VariableAlreadyExistException;

/**
 * represents ukuFlow variable
 * @author  Hien Quoc Dang
 */
public class UkuVariable extends PrimaryExpression {
	/**
	 * @uml.property  name="name"
	 */
	public String name;
	/**
	 * @uml.property  name="declaration"
	 */
	boolean declaration = false;
	/**
	 * @uml.property  name="id"
	 */
	private int id = 0;
	
	/**
	 * @uml.property  name="log"
	 * @uml.associationEnd  
	 */
	private static final BpmnLog log = BpmnLog.getInstance(UkuVariable.class.getSimpleName());
	
	public UkuVariable(String name) {
		if (name.startsWith("$")) {
			this.name = name.substring(1);
		} else {
			this.name = name.trim();
			declaration = true;
		}
		VariableManager vm = VariableManager.getInstance();
		try{
			id = vm.registerVariable(this);
		}catch (TooManyVariableException e) {
			//HIEN TODO handle if there are too many variables are declared			
		} catch (VariableAlreadyExistException e) {
			id = vm.getVariableID(name);
		}
	}
	public int getID(){
		log.debug("return id = "  + id);
		return id;
	}
	@Override
	public void accept(ScriptVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "$" + name;
	}

	/**
	 * Enable to compare 2 variables with {@link #equals(Object)}:<br />
	 * 2 variables are when they have the same name
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UkuVariable))
			return false;
		UkuVariable ov = (UkuVariable) o;
		return name.equals(ov.name);
	}

	/**
	 * indicate whether the variable receives a value at least one time. For
	 * examples: <br/>
	 * {@code local var = plus 2 3;} <br/>
	 * {@code var = 1 + 2;}<br />
	 * then the variable with name "var" is declared
	 * 
	 * @return {@code true} if the variable appears at least one time on the
	 *         left side of a local function statement or computation statement,
	 *         otherwise {@code false}
	 */

	public boolean isDeclared() {
		return declaration;
	}

	/**
	 * @return 2 <br/>
	 *         variable = REPOSITORY_VALUE | ID
	 */
	public int getLength() {
		return 2;
	}

	public void setID(int id) {
		this.id = id;
	}
}
