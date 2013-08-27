/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package de.tudarmstadt.dvs.ukuflow.validation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.ScopeManager;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ComputationalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.LocalFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.ScopeFunction;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.TaskScriptFunction;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ScopeNotExistException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.xml.entity.*;

public class UkuProcessValidation {
	UkuProcess process = null;
	// UkuErrorReporter reporter;
	// UkuConsole console = UkuConsole.getConsole();
	// public Set<ErrorMessage> errors = new HashSet<ErrorMessage>();
	// public Set<WarningMessage> warnings = new HashSet<WarningMessage>();
	UkuEvent start = null;
	UkuEvent end = null;

	public UkuProcessValidation(UkuProcess process) {
		this.process = process;
		// reporter = UkuErrorReporter.getInstance();
	}

	public void validate(boolean balanceCheck) {
		/*
		 * for(UkuEntity e : process.getElements()){ if(e instanceof
		 * UkuGateway){ ((UkuGateway)e).selfValidate(); } }
		 */
		for (UkuEntity e : process.getEntities()) {
			if (e instanceof UkuEvent) {
				UkuEvent ev = (UkuEvent) e;
				if (ev.getType() == UkuConstants.WorkflowOperators.START_EVENT) {
					if (start == null) {
						start = ev;
					} else {
						ev.addErrorMessage("process should have only one Start Event");
					}
				} else if (ev.getType() == UkuConstants.WorkflowOperators.END_EVENT) {
					if (end == null) {
						end = ev;
					} else {
						ev.addErrorMessage("Process should have only one End Event");
					}
				} else {
					// not supported ?
					System.err.println(ev.getID() + " is not supported");
				}
				validate((UkuEvent) e);
			} else if (e instanceof UkuExecuteTask) {
				validate((UkuExecuteTask) e);
			} else if (e instanceof UkuScope) {
				validate((UkuScope) e);
			} else if (e instanceof UkuSequenceFlow) {
				validate((UkuSequenceFlow) e);
			} else if (e instanceof UkuGateway) {
				validate((UkuGateway) e);
			} else if (e instanceof UkuReceiveTask){
				validate((UkuReceiveTask)e);
			} else {
				System.err.println(e);
			}
		}
		/**
		 * for (UkuEntity e : process.getEntities()) {
		 * errors.addAll(e.getError()); warnings.addAll(e.getWarnings()); } for
		 * (ErrorMessage em : errors) { console.error("ERROR", em.location,
		 * em.message); } for (WarningMessage wm : warnings) {
		 * console.warn("WARNING", wm.location, wm.message); }
		 */
		/**
		 * if (errors.size() != 0) return false;
		 */
		boolean noCycle = checkingNoCyclus();
		if (!noCycle) {
			ErrorManager.getInstance().addError(
					new ErrorMessage(process.name, " Process contains cycle"));
			System.err.println(" process contains circlus");
			return;
		}
		if (balanceCheck && ErrorManager.getInstance().isValid()) {
			UkuElement last = wellformednessChecking3(start);
			if (last != null && last instanceof UkuEvent && ((UkuEvent)last).getType()==UkuConstants.WorkflowOperators.END_EVENT)
				System.out.println("workflow is well-formed");
			else 
				System.err.println("workflow is not well-formed "+ last);
		}

	}

	/**
	 * @param e
	 */
	private void validate(UkuReceiveTask e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * checking if there is a cycle in the workflow
	 * 
	 * @return
	 */
	private boolean checkingNoCyclus() {
		return goToNextEntity(start, process.getElements().size() + 1);
	}

	private boolean goToNextEntity(UkuEntity current, int max) {
		if (max == 0)
			return false;
		if (current instanceof UkuSequenceFlow) {
			return goToNextEntity(
					((UkuSequenceFlow) current).getTargetEntity(), max);
		} else if (current instanceof UkuEvent
				&& ((UkuEvent) current).getType() == UkuConstants.WorkflowOperators.END_EVENT) {
			return true;
		} else {
			UkuElement e = (UkuElement) current;
			for (UkuEntity s : e.getOutgoingEntity()) {
				if (!goToNextEntity(s, max - 1))
					return false;
			}
			return true;
		}
	}

	private UkuElement wellformednessChecking3(UkuElement start) {
		if (start == null)
			return null;
		if (start instanceof UkuActivity) {
			if (start.getOutgoingEntity().size() == 0)
				return start;
			else if (start.getOutgoingEntity().size() == 1)
				return wellformednessChecking3((UkuElement) start
						.getOutgoingEntity().get(0).getTargetEntity());
			else {
				System.err.println("Activity has more than one outgoing???");// TODO:ERROR:
				return null;
			}

		} else if (start instanceof UkuGateway) {
			UkuGateway tmp = (UkuGateway) start;
			if (tmp.calculateType() == 1) {
				return tmp;
			} else {
				Set<UkuElement> nextElement = new HashSet<UkuElement>();
				for (UkuSequenceFlow seq : tmp.getOutgoingEntity()) {
					UkuElement t = wellformednessChecking3((UkuElement) seq
							.getTargetEntity());
					nextElement.add(t);
					
				}
				if (nextElement.size() > 1) {
					String msg ="found "+nextElement.size() + " matched gateway:";
					boolean coma= false;
					for(UkuElement e : nextElement){
						if(coma)
							msg+=", ";
						coma=true;
						if(e!=null)
							msg += e.getID();
						else {
							msg =null;
							break;
						}
					}
					if(msg!=null){
						msg+=" (Note that each diverging gateway must have one and only one matched converging gateway)";
						start.addErrorMessage(msg);
					} else{
						start.addErrorMessage("couldn't find a matched converging gateway for diverging gateway "+start.getID());
					}
					return null;
				} else {
					UkuElement res = (UkuElement) nextElement.toArray()[0];
					if (res instanceof UkuGateway) {
						UkuGateway g = (UkuGateway) res;

						if (checkmatchedGateway((UkuGateway)start, g))
							return wellformednessChecking3((UkuElement) g.getOutgoingEntity().get(0).getTargetEntity());
					}
					return null;
				}
			}
		} else {
			return null;
		}
	}

	private boolean checkmatchedGateway(UkuGateway start, UkuGateway end) {
		if (start.getOutgoingEntity().size() != end.getIncomingEntity().size())
			return false;
		if (start instanceof UkuExclusiveGateway
				&& end instanceof UkuExclusiveGateway)
			return true;
		if (start instanceof UkuInclusiveGateway
				&& end instanceof UkuInclusiveGateway)
			return true;
		if (start instanceof UkuParallelGateway
				&& end instanceof UkuParallelGateway)
			return true;
		if (start instanceof UkuEventGateway
				&& end instanceof UkuExclusiveGateway)
			return true;
		return false;
	}

	/*
	 * require a workflow without cirle
	 */
	private UkuElement wellformednessChecking2(UkuElement start) {
		if (start == null)
			return null;
		UkuElement tmp = start;
		while (true) {
			if (tmp instanceof UkuGateway) {
				UkuGateway gate = (UkuGateway) tmp;

				switch (gate.calculateType()) {
				case 0:
					return gate;
				case 1:
					Set<UkuElement> nextGateways = new HashSet<UkuElement>();
					for (UkuSequenceFlow seq : gate.getOutgoingEntity()) {
						UkuElement r = wellformednessChecking2((UkuElement) seq
								.getTargetEntity());
						if (r == null) {
							System.err.println("couldn't found next gateway");
						}
						if (!(r instanceof UkuGateway)) {
							// TODO add an error
							return r;
						} else if (((UkuGateway) r).calculateType() == 1) {
							nextGateways.add(r);
						} else {
							System.err
									.println("this statement will never be reached<UkuProcessValidation.wellformednessCheching2>");
						}
					}
					switch (nextGateways.size()) {
					case 0:
						return null;
					case 1:
						return (UkuElement) nextGateways.toArray()[0];
					default:

					}
				case 2:
				case 3:
				default:
				}
			} else if (tmp instanceof UkuActivity) {
				if (tmp instanceof UkuEvent
						&& ((UkuEvent) tmp).getType() == UkuConstants.WorkflowOperators.END_EVENT) {
					return tmp;
				}
			} else {

			}
		}
		// return null;
	}

	private void wellformednessChecking() {
		List<UkuGateway> converging = new LinkedList<UkuGateway>();
		List<UkuGateway> diverging = new LinkedList<UkuGateway>();
		for (UkuElement u : process.getElements()) {
			if (u instanceof UkuGateway) {
				UkuGateway g = (UkuGateway) u;
				if (g.calculateType() == 1) {
					converging.add(g);
				} else if (g.calculateType() == 2) {
					diverging.add(g);
				} else {
					// TODO
					g.addErrorMessage("this error shouldn't happend, please contact dangquochien@gmail.com");
					return;
				}
			}
		}
		// int oldsize = diverging.size();
		// List<UkuGateway> toRemove = new LinkedList<UkuGateway>();
		boolean done = false;
		while (!done) {
			done = true;
			for (UkuGateway g : diverging) {
				UkuGateway to = null;
				List<UkuGateway> lfrom = g.getNextGateways();
				if (lfrom.size() == 1) {
					to = lfrom.get(0);
					List<UkuGateway> lto = to.getPreviousGateways();
					if (lto.size() == 1 && lto.get(0).equals(g)) {
						to.setSkip();
						g.setSkip();
						System.out.println(g + " & " + to + " are skipped");
						diverging.remove(g);
						converging.remove(to);
						done = false;
						break;
					} else {
						System.out.println("not match: " + g + " -> " + lfrom
								+ "\n" + to + " -> " + lto);
					}
				}
			}
		}
		if (diverging.size() != 0 || converging.size() != 0) {
			ErrorManager.getInstance().addError(process.name,
					"The process is not balanced");
			System.err.println("workflow is not balanced");
		}
	}

	private void validate(UkuEvent event) {
		switch (event.getType()) {
		case UkuConstants.WorkflowOperators.END_EVENT:
			if (event.getIncomingID().size() == 0)
				event.addErrorMessage("End Event should have at least one incoming connection");
			if (event.getOutgoingID().size() > 0)
				event.addErrorMessage("End Event cannot have outoging connection");
			break;
		case UkuConstants.WorkflowOperators.START_EVENT:
			if (event.getOutgoingID().size() == 0)
				event.addErrorMessage("Start Event should have at least one outgoing connection");
			if (event.getIncomingID().size() > 0)
				event.addErrorMessage("StartEvent cannot have incoming connection");
			break;
		default:
			event.addErrorMessage("this type of event is not supported yet");
			break;
		}
	}

	private void validate(UkuExecuteTask task) {
		if (task.getIncomingID().size() != 1
				|| task.getOutgoingID().size() != 1)
			task.addErrorMessage("A Script task must have exactly one incoming and one outgoing connection");
		if (!task.hasScript()) {
			task.addErrorMessage("has no script");
		} else if (task.isValid()) {
			for (TaskScriptFunction tsf : task.getStatements()) {
				validate(task, tsf);
			}
		}
	}

	private void validate(UkuExecuteTask parent, TaskScriptFunction tsf) {
		if (tsf instanceof ComputationalFunction) {
			// TODO anything to validate?
		} else if (tsf instanceof LocalFunction) {
			// TODO anything to validate?
		} else if (tsf instanceof ScopeFunction) {
			String sName = ((ScopeFunction) tsf).getScopeName();
			try {
				ScopeManager.getInstance().getScopeID(sName);
			} catch (ScopeNotExistException e) {
				parent.addErrorMessage("couldn't found the declaration for scope "
						+ sName);
			}
		}
	}

	private void validate(UkuScope scope) {
		// nothing do validate with scope.
	}

	private void validate(UkuGateway gway) {
		// TODO is there anything to validate?
	}

	private void validate(UkuSequenceFlow sef) {
		if (sef.getSourceEntity() instanceof UkuGateway) {
			UkuGateway sourceGway = (UkuGateway) sef.getSourceEntity();
			if (sourceGway.calculateType() != 2)
				return;
			switch (sourceGway.getUkuType()) {
			case UkuConstants.WorkflowOperators.INCLUSIVE_DECISION_GATEWAY:
			case UkuConstants.WorkflowOperators.EXCLUSIVE_DECISION_GATEWAY:
				if (!sef.hasCondition() && !sef.isDefault()) {
					sef.addErrorMessage("sequence Flow has no condition or condition expression has incorrect syntax");
				}
				break;
			case UkuConstants.WorkflowOperators.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY:
				if (sef.hasCondition()) {
					sef.addErrorMessage("this sequence flow shouldn't have condition");
				}
			default:
				break;
			}
		}
	}
}
