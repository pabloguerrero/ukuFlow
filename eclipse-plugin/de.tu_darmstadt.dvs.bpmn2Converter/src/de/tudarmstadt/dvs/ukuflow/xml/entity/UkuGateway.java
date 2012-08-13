package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

public class UkuGateway extends UkuElement{

	/**
	 * -1: dead <br/>
	 * 0 : still unknown <br/>
	 * 1 : converging <br/>
	 * 2 : diverging <br/>
	 * 3 : mixed <br/>		
	 * @return type of the gateway
	 */
	private int type = 0;
	
	public UkuGateway(String id){
		super(id);
	}


	/* (non-Javadoc)
	 * 0 x/x 0
	 * 1/>1
	 * >1/1
	 * >1/>1
	 * @see de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement#setReference(java.util.Map)
	 */
	@Override
	public void setReference(Map<String,UkuEntity> ref){
		super.setReference(ref);
		if(outgoing.size()>1 && incoming.size() >1){
			addWarningMessage(" a mixed gateway");
			type = 3;
			return;
		}
		if(outgoing.size() == 0){
			addWarningMessage("no outgoing sequence flow");
			type = -1;
		}
		if(incoming.size() == 0){
			addWarningMessage("no incoming sequence flow");
			type=-1;
			return;
		}
		if(incoming.size() == 1 && outgoing.size() > 1){
			type = 2;
			return;
		}
		if(incoming.size() > 1 && outgoing.size() == 1){
			type = 1;
			return;
		}
		if(incoming.size()==1 && outgoing.size() == 1){
			type = 0;
			return;
		}
	}
	
	/**
	 * -1: dead <br/>
	 * 0 : still unknown <br/>
	 * 1 : converging <br/>
	 * 2 : diverging <br/>
	 * 3 : mixed <br/>		
	 * @return type of the gateway
	 */
	public int getType(){
		return type;
	}
}
