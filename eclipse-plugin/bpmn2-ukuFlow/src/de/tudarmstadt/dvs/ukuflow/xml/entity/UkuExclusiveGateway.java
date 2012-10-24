package de.tudarmstadt.dvs.ukuflow.xml.entity;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;

public class UkuExclusiveGateway extends UkuGateway{

	public UkuExclusiveGateway(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getUkuType(){
		if(calculateType()==1)
			return UkuConstants.EXCLUSIVE_MERGE_GATEWAY;
		if(calculateType()==2)
			return UkuConstants.EXCLUSIVE_DECISION_GATEWAY;		
		return -1;
	}

}
