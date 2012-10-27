package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.TokenMgrError;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ParseException;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.Token;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ukuFlowScript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction;

public class UkuExecuteTask extends UkuActivity {
	private List<TaskScriptFunction> statements;
	private boolean hasScript = false;
	private boolean valid = false;

	public UkuExecuteTask(String id) {
		super(id);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
		/*
		if (incoming.size() == 0) {
			addWarningMessage(" this element may never be reached. It has no incoming sequence flow");
		}

		if (outgoing.size() == 0) {
			addWarningMessage(" no outgoing sequence flow");
		}
		if (outgoing.size() > 1 || incoming.size() > 1) {
			addErrorMessage("this element has multiple incoming- or outgoing- sequence flows");
		}*/
	}

	public void setScript(String script) {
		// this.script = script;
		if (script == null || script.equals("")) {			
			return;
		}
		hasScript = true;
		ukuFlowScript parser = ukuFlowScript.getInstance(script);
		try {
			System.out.println("parsing "+script);
			statements = parser.parseTaskScript();
			valid = true;
		} catch (Error error) {
			Token tk = parser.token;

			if (tk != null) {
				if (tk.next != null)
					tk = tk.next;
				addErrorMessage(id + ", at line: "
						+ tk.beginLine + "& col: "
						+ tk.beginColumn, "error near the token "
						+ tk);
			} else
				addErrorMessage(error.getMessage());
		} catch (ParseException e) {
			String tkn = null;
			String msg = null;
			Token tk = parser.token;
			if (tk != null) {
				if(tk.next != null)
					tk = tk.next;
				tkn = tk.image;
				msg = "error near the token " + tkn;
				//if (e.getMessage() != null && e.getMessage().startsWith(tkn)) msg = e.getMessage();
				addErrorMessage("element " + id + ", at line: "
						+ tk.beginLine + "& col: "
						+ tk.beginColumn, msg);
			} else {
				addErrorMessage("element " + id,
						"there is an unknown error in the script");
			}			
		}
	}

	public boolean hasScript() {
		return hasScript;
	}
	public boolean isValid(){
		return valid;
	}

	public List<TaskScriptFunction> getStatements() {
		return statements;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

}
