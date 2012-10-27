package de.tudarmstadt.dvs.ukuflow.xml;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.validation.ErrorManager;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuEntity;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuGateway;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuProcess;
import de.tudarmstadt.dvs.ukuflow.xml.entity.UkuSequenceFlow;

public class ProcessOptimizer {

	private static final BpmnLog log = BpmnLog
			.getInstance(ProcessOptimizer.class.getSimpleName());
	public static final String DEFAULT_GENERATED_ID = "generated_id_";
	private Set<String> idPool = null;
	UkuProcess process = null;

	public ProcessOptimizer(UkuProcess p) {
		this.process = p;
		idPool = new HashSet<String>();
		for (UkuEntity e : process.getEntities()) {
			idPool.add(e.getID());
		}
	}

	public boolean optimize() {
		boolean valid = true;
		boolean done = false;
		while (!done) {
			for (UkuElement e : process.getElements()) {
				if (e instanceof UkuGateway) {
					int type = ((UkuGateway) e).calculateType();
					if (type == 3) {
						splitMixedGateway((UkuGateway) e);
						break;
					}
					if (type == 0) {
						valid = false; //TODO
						if(e.getIncomingEntity().size()>1)
							e.addErrorMessage("Gateway should have at least one outgoing sequence flow");
						else if(e.getOutgoingEntity().size()>1)
							e.addErrorMessage("Gateway should have at least one incoming sequence flow");
						else 
							e.addErrorMessage("Gateway with one incoming and one outgoing is not allowed");
					}
				}
				done = true;
			}
		}
		return valid;
	}

	private void splitMixedGateway(UkuGateway g) {

		UkuGateway newGateway = g.clone(generateID(g.getID()));
		UkuSequenceFlow newSq = new UkuSequenceFlow(generateID(null), g,
				newGateway);

		List<UkuSequenceFlow> tmp = new LinkedList<UkuSequenceFlow>();
		tmp.add(newSq);

		newGateway.setOutgoingEntity(g.getOutgoingEntity());
		for (UkuEntity s : g.getOutgoingEntity()) {
			UkuSequenceFlow sq = (UkuSequenceFlow) s;
			sq.setSourceEntity(newGateway);
		}
		newGateway.setIncomingEntity(tmp);

		g.setOutgoingEntity(tmp);
		process.addEntity(newGateway);
		process.addEntity(newSq);
	}

	/**
	 * this function will search for a mixed gateway in a process and try to
	 * split it in 2 simple gateway and also add a sequence flow between them
	 * 
	 * @param process
	 * @need tests!
	 */
	/*
	 * private void optimize(UkuProcess process) { List<UkuEntity> tmp = new
	 * LinkedList<UkuEntity>(); boolean done = false; while (!done) { for
	 * (UkuEntity entity : process.getEntities()) { if (entity instanceof
	 * UkuGateway) { UkuGateway gway = (UkuGateway) entity; if
	 * (gway.getOutgoingID().size() > 1 && gway.getIncomingID().size() > 1) {
	 * log.info("optimizing"); process.removeEntity(gway);
	 * reference.remove(gway.getID()); UkuGateway left =
	 * gway.clone(generateID(gway.getID()));
	 * left.setIncoming(gway.getIncomingID()); for (String inc :
	 * left.getIncomingID()) { UkuSequenceFlow sf = (UkuSequenceFlow) reference
	 * .get(inc); sf.setTargetID(left.getID()); } UkuGateway right =
	 * gway.clone(generateID(gway.getID()));
	 * right.setOutgoing(gway.getOutgoingID()); for (String outg :
	 * right.getOutgoingID()) { UkuSequenceFlow sf = (UkuSequenceFlow) reference
	 * .get(outg); sf.setSourceID(right.getID()); } UkuSequenceFlow seq = new
	 * UkuSequenceFlow( generateID("generated_sequenceflow"), left.getID(),
	 * right.getID()); left.addOutgoing(seq.getID());
	 * right.addIncoming(seq.getID());
	 * 
	 * tmp.add(left); reference.put(left.getID(), left); tmp.add(right);
	 * reference.put(right.getID(), right); tmp.add(seq);
	 * reference.put(seq.getID(), seq); process.addEntities(tmp); break; } } }
	 * done = true; } }
	 */
	private String generateID(String rootID) {
		String newID = null;
		if (rootID == null)
			newID = DEFAULT_GENERATED_ID;
		else
			newID = rootID + "_";

		int i = 0;
		while (idPool.contains(newID + i)) {
			i++;
		}
		idPool.add(newID + i);
		log.info("add new id to pool -> " + (newID + i));
		return newID + i;
	}
}
