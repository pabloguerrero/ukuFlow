package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuExpression;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.UkuScopeExpression;
import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyScopeException;

public class UkuScope extends UkuEntity {
	private String script = null;

	private int ttl = -1;
	private int scopeID = 0;
	private String name = null;
	UkuExpression sExpression = null;

	public UkuScope(String id) {
		super(id);
	}

	public void setScript(String script) {
		this.script = script;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		UkuScopeExpression exp = null;
		try {
			exp = parser.scopeExpression();
		} catch (ParseException e) {
			addErrorMessage("element " + id + ", at line: "
					+ parser.token.beginLine + "& col: "
					+ parser.token.beginColumn, "error near the token "
					+ parser.token);
			return;
		}
		name = exp.name;
		sExpression = exp.scopeExp;
		try {
			scopeID = ScopeManager.getInstance().registerScope(name);
		} catch (DuplicateScopeNameException e) {
			addErrorMessage("scope '" + name + "' already exists");
		} catch (TooManyScopeException e) {
			addErrorMessage("there are too many scope definition (allowed are maximal 256 scopes)");
		}
		if (exp.ttl != null) {
			ttl = exp.ttl;			
		} else {
			addWarningMessage("Time-to-Live for scope "+name+" isn't specified -> used default ttl: 60s");
			ttl = 60;
		}		
		if (ttl < 0 || ttl > 65535) {
			addErrorMessage("invalid time-to-live (allowed value is in range 0->65535");
		}
	}

	public int getScopeID() {
		return scopeID;
	}

	public int getTTL() {
		return ttl;
	}

	public UkuExpression getScopeExp() {
		return sExpression;
	}

	public boolean hasScript() {
		return script != null;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {

	}

}
