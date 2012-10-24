package de.tudarmstadt.dvs.ukuflow.xml.entity;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

public class UkuParallelGateway extends UkuGateway{

	public UkuParallelGateway(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getUkuType() {
		if(calculateType()==1)
			return UkuConstants.JOIN_GATEWAY;
		if(calculateType()==2)
			return UkuConstants.FORK_GATEWAY;
		return -1;
	}

}
