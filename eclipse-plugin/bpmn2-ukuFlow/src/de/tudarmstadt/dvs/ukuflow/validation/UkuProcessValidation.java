package de.tudarmstadt.dvs.ukuflow.validation;


import de.tudarmstadt.dvs.ukuflow.handler.UkuConsole;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;
import de.tudarmstadt.dvs.ukuflow.xml.entity.*;

public class UkuProcessValidation {
	UkuProcess process = null;
	//UkuErrorReporter reporter;
	//UkuConsole console = UkuConsole.getConsole();
	//public Set<ErrorMessage> errors = new HashSet<ErrorMessage>();
	//public Set<WarningMessage> warnings = new HashSet<WarningMessage>();

	public UkuProcessValidation(UkuProcess process) {
		this.process = process;
		//reporter = UkuErrorReporter.getInstance();
	}

	public boolean validate() {
		for (UkuEntity e : process.getEntities()) {
			if (e instanceof UkuEvent) {
				validate((UkuEvent) e);
			} else if (e instanceof UkuExecuteTask) {
				validate((UkuExecuteTask) e);
			} else if (e instanceof UkuScope) {
				validate((UkuScope) e);
			} else if (e instanceof UkuGateway) {
				validate((UkuGateway) e);
			} else if (e instanceof UkuSequenceFlow) {
				validate((UkuSequenceFlow) e);
			} else {
				System.err.println("error");
			}
		}
		/**
		for (UkuEntity e : process.getEntities()) {
			errors.addAll(e.getError());
			warnings.addAll(e.getWarnings());
		}
		for (ErrorMessage em : errors) {
			console.error("ERROR", em.location, em.message);
		}
		for (WarningMessage wm : warnings) {
			console.warn("WARNING", wm.location, wm.message);
		}*/
		/**
		if (errors.size() != 0)
			return false;
			*/
		return true;

	}

	private void validate(UkuEvent event) {
		switch (event.getType()) {
		case UkuConstants.END_EVENT:
			if (event.getIncomingID().size() == 0)
				event.addErrorMessage("End event should have at least one incoming connection");
			if (event.getOutgoingID().size() > 0)
				event.addErrorMessage("End Event cannot have outoging connection");
			break;
		case UkuConstants.START_EVENT:
			if (event.getOutgoingID().size() == 0)
				event.addErrorMessage("StartEvent should have at least one outgoing connection");
			if (event.getIncomingID().size() > 0)
				event.addErrorMessage("StartEvent cannot have incoming connection");
			break;
		default:
			event.addErrorMessage("this type of event is not supported yet");
			break;
		}
	}

	private void validate(UkuExecuteTask task) {
		if (task.getIncomingID().size() != 1 || task.getOutgoingID().size() != 1)
			task.addErrorMessage("A Script task must have exactly one incoming and one outgoing connection");
		if(!task.hasScript()){
			task.addErrorMessage("has no script");
		}
	}

	public void validate(UkuScope scope) {
		// TODO
	}

	public void validate(UkuGateway gway) {
		String tmpName = "";
		if (gway.getOutgoingID().size() > 1 && gway.getIncomingID().size() > 1) {
			gway.addWarningMessage("a mixed gateway");
			gway.setType(3);
			tmpName = "Mixed";
		} else if (gway.getOutgoingID().size() == 0
				|| gway.getIncomingID().size() == 0) {
			if (gway.getOutgoingID().size() == 0)
				gway.addWarningMessage("no outgoing sequence flow");
			if (gway.getIncomingID().size() == 0)
				gway.addWarningMessage("no incoming sequence flow");
			gway.setType(-1);
			return;
		} else if (gway.getIncomingID().size() == 1
				&& gway.getOutgoingID().size() > 1) {
			gway.setType(2);
			tmpName = "Diverging";
		} else if (gway.getIncomingID().size() > 1
				&& gway.getOutgoingID().size() == 1) {
			tmpName = "Converging";
			gway.setType(1);
		}
		if (gway.getIncomingID().size() == 1
				&& gway.getOutgoingID().size() == 1) {
			tmpName = "UnKnown";
			gway.setType(0);
		}
		if (gway.getType() != gway.direction) {
			gway.addWarningMessage("was specified as '" + gway.directionName
					+ "', but it was found as a '" + tmpName + "' gateway");
		}
		if (gway.type == gway.direction && gway.direction == 0) {
			gway.addErrorMessage("Please specify the direction of gateway (Converging,Deverging or Mixed)");
		}
		int tp = 0;
		try {
			tp = TypeClassifier.getInstance().getGatewayType(gway);
			gway.ukuGatewayType = tp;
		} catch (UnspecifiedGatewayException e) {
			gway.addErrorMessage(e.getMessage());
			return;
		}
		if (gway.ukuGatewayType == UkuConstants.INCLUSIVE_DECISION_GATEWAY) {
			if (gway.getDefaultGway() == null);
				//gway.addErrorMessage("inclusive decision gateway should have a default gateway");
		}
	}

	public void validate(UkuSequenceFlow sef) {
		if (sef.getSource() instanceof UkuGateway) {
			UkuGateway sourceGway = (UkuGateway) sef.getSource();

			switch (sourceGway.ukuGatewayType) {
			case UkuConstants.INCLUSIVE_DECISION_GATEWAY:
			case UkuConstants.EXCLUSIVE_DECISION_GATEWAY:
				if (!sef.hasCondition() && sef.isDefault()) {
					sef.addWarningMessage("this sequence flow should be default or have a condition");
				}
				break;
			case UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY:
				if (sef.hasCondition()) {
					sef.addErrorMessage("this sequence flow shouldn't have condition");
				}
			default:
				break;
			}
		}
	}
}
