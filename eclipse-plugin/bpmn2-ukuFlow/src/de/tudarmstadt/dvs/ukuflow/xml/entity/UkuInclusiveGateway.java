package de.tudarmstadt.dvs.ukuflow.xml.entity;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

public class UkuInclusiveGateway extends UkuGateway{

	public UkuInclusiveGateway(String id) {
		super(id); 
	}

	@Override
	public int getUkuType() {
		if(calculateType() == 1)
			return UkuConstants.INCLUSIVE_JOIN_GATEWAY;
		if(calculateType() == 2)
			return UkuConstants.INCLUSIVE_DECISION_GATEWAY;
		return -1;
	}

}
