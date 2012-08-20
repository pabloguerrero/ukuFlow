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
	private int direction = 0;
	private String directionName;
	private String typeGateway;
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
	public void setElementType(String s){
		typeGateway = s;
	}
	public String getElementType(){
		return typeGateway;
	}
	
	@Override
	public void setReference(Map<String,UkuEntity> ref){
		super.setReference(ref);
		String tmpName = "";
		if(outgoing.size()>1 && incoming.size() >1){
			addWarningMessage("a mixed gateway");
			type = 3;
			tmpName = "Mixed";
		}else if(outgoing.size() == 0 || incoming.size() == 0){
			if(outgoing.size() == 0)
				addWarningMessage("no outgoing sequence flow");
			if(incoming.size() == 0)
				addWarningMessage("no incoming sequence flow");
			type=-1;
			return;
		} else if(incoming.size() == 1 && outgoing.size() > 1){
			type = 2;
			tmpName = "Diverging";
		} else if(incoming.size() > 1 && outgoing.size() == 1){
			tmpName = "Converging";
			type = 1;
		} if(incoming.size()==1 && outgoing.size() == 1){
			tmpName = "UnKnown";
			type = 0;
		}
		if(type != direction){
			addWarningMessage("was specified as '"+directionName + "', but it was found as a '"+tmpName + "' gateway");
		}
		if(type == direction && direction == 0){
			addErrorMessage("Please specify the direction of gateway (Converging,Deverging or Mixed)");
		}		
	}
	
	public void setDirection(String d){
		if(d!= null)
			directionName = d;
		else 
			directionName = "";
		if(directionName.equalsIgnoreCase("Diverging")){
			direction = 2;
		} else if(directionName.equalsIgnoreCase("Converging")){
			direction = 1;
		} else if(directionName.equalsIgnoreCase("Mixed")){
			direction = 3;
		} else if(directionName.equalsIgnoreCase("Unspecified")){
			direction = 0;
		} else { // ""
			direction = 0;
			addWarningMessage("the type of gateway is not specified");
		}
	}
	/**
	 * -1: dead (0-n) or (n-0)<br/>
	 * 0 : still unknown (1-1)<br/>
	 * 1 : converging (n-1)<br/>
	 * 2 : diverging (1-n)<br/>
	 * 3 : mixed (n-n)<br/>		
	 * @return type of the gateway
	 */
	public int getType(){
		return type;
	}
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);		
	}
}
