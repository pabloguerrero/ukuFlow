package de.tudarmstadt.dvs.ukuflow.script.expression;

/**
 * represents ukuFlow variable
 * 
 * @author Hien Quoc Dang
 * 
 */
public class Variable extends PrimaryExpression {
	protected String name;
	boolean declaration = false;

	public Variable(String name) {
		if (name.startsWith("$")) {
			this.name = name.substring(1);
		} else {
			this.name = name.trim();
			declaration = true;
		}
		VariableManager.getInstance().addVariable(this);
	}

	@Override
	public void accept(Visitor visitor) {
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
		if (!(o instanceof Variable))
			return false;
		Variable ov = (Variable) o;
		return name.equals(ov.name);
	}

	public boolean equals(Variable v) {
		return equals(v);
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
}
