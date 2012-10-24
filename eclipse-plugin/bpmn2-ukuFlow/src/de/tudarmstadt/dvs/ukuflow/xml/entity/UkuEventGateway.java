package de.tudarmstadt.dvs.ukuflow.xml.entity;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

public class UkuEventGateway extends UkuGateway {
	
	public UkuEventGateway(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getUkuType() {		
		return UkuConstants.EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY;
	}

}
