package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

public class UkuGateway extends UkuElement {

	/**
	 * -1: dead <br/>
	 * 0 : still unknown <br/>
	 * 1 : converging <br/>
	 * 2 : diverging <br/>
	 * 3 : mixed <br/>
	 * 
	 * @return type of the gateway
	 */
	public int type = 0;
	public int direction = 0;
	public String directionName;
	private String typeGateway;
	private String defaultGway = null;
	public int ukuGatewayType = -1;

	public UkuGateway(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc) 0 x/x 0 1/>1 >1/1 >1/>1
	 * 
	 * @see
	 * de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement#setReference(java.util
	 * .Map)
	 */
	public void setElementType(String s) {
		typeGateway = s;
	}

	public void setDefaultGway(String d) {
		defaultGway = d;
	}
	public String getDefaultGway(){
		return defaultGway;
	}
	public String getElementType() {
		return typeGateway;
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
		if (defaultGway != null) {
			UkuSequenceFlow defGateway = (UkuSequenceFlow) ref.get(defaultGway);
			defGateway.setDefaultGateway();
		}
	}
	public void setType(int type){
		this.type = type;
	}

	public void setDirection(String d) {
		if (d != null)
			directionName = d;
		else
			directionName = "";
		if (directionName.equalsIgnoreCase("Diverging")) {
			direction = 2;
		} else if (directionName.equalsIgnoreCase("Converging")) {
			direction = 1;
		} else if (directionName.equalsIgnoreCase("Mixed")) {
			direction = 3;
		} else if (directionName.equalsIgnoreCase("Unspecified")) {
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
	 * 
	 * @return type of the gateway
	 */
	public int getType() {
		return type;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public UkuGateway clone(String newID) {
		UkuGateway ob = new UkuGateway(newID);
		if (defaultGway != null)
			ob.defaultGway = defaultGway.trim();
		ob.direction = direction;
		if (directionName != null)
			ob.directionName = directionName.trim();
		ob.type = type;
		if (typeGateway != null)
			ob.typeGateway = typeGateway.trim();
		return ob;
	}
}
