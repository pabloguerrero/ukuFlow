package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction;

public class UkuExecuteTask extends UkuElement {
	private List<TaskScriptFunction> statements;
	private boolean hasScript = false;

	public UkuExecuteTask(String id) {
		super(id);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);

		if (incoming.size() == 0) {
			addWarningMessage(" this element may never be reached. It has no incoming sequence flow");
		}

		if (outgoing.size() == 0) {
			addWarningMessage(" no outgoing sequence flow");
		}
		if (outgoing.size() > 1 || incoming.size() > 1) {
			addErrorMessage("this element has multiple incoming- or outgoing- sequence flows");
		}
	}

	public void setScript(String script) {
		// this.script = script;
		if (script == null || script.equals("")) {
			System.err.println(script);
			return;
		}
		hasScript = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		try {
			statements = parser.parseTaskScript();
		} catch (Error error) {
			if (parser.token != null)
				addErrorMessage("element " + id + ", at line: "
						+ parser.token.beginLine + "& col: "
						+ parser.token.beginColumn, "error near the token "
						+ parser.token);
			else
				addErrorMessage(error.getMessage());
		} catch (ParseException e) {
			String tkn = null;
			String msg = null;

			if (parser.token != null) {
				tkn = parser.token.image;
				msg = "error near the token " + tkn;
				if (e.getMessage() != null && e.getMessage().startsWith(tkn))
					msg = e.getMessage();
				addErrorMessage("element " + id + ", at line: "
						+ parser.token.beginLine + "& col: "
						+ parser.token.beginColumn, msg);
			} else {
				addErrorMessage("element "+id,"there is an unknown error in the script");
			}
		}
	}

	public boolean hasScript() {
		return hasScript;
	}

	public List<TaskScriptFunction> getStatements() {
		return statements;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

}
