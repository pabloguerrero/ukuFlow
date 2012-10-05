package de.tudarmstadt.dvs.ukuflow.script.generalscript.expression;

/**
 * @author Hien Quoc Dang
 * this class is created so that the script parser could store the result of parsing TextAnnotation.
 * This class is not visitable.
 */
public class UkuScopeExpression {
	
	public String name;
	
	public UkuExpression scopeExp;
	
	public Integer ttl = null;

	public UkuScopeExpression(String name, Integer ttl, UkuExpression sExp) {
		this.name = name;
		this.scopeExp = sExp;
		this.ttl = ttl;
	}
	@Override
	public String toString(){
		return "[scope:"+name+"-"+ttl+"s-<"+scopeExp+">";
	}
}
