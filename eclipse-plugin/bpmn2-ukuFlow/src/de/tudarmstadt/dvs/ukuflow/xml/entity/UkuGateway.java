package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;

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
	
	public void selfValidate(){
		String tmpName = "";
		if (getOutgoingID().size() > 1 && getIncomingID().size() > 1) {
			 addWarningMessage("a mixed gateway");
			 setType(3);
			tmpName = "Mixed";
		} else if ( getOutgoingID().size() == 0
				||  getIncomingID().size() == 0) {
			if ( getOutgoingID().size() == 0)
				 addWarningMessage("no outgoing sequence flow");
			if ( getIncomingID().size() == 0)
				 addWarningMessage("no incoming sequence flow");
			 setType(-1);
			return;
		} else if ( getIncomingID().size() == 1
				&&  getOutgoingID().size() > 1) {
			 setType(2);
			tmpName = "Diverging";
		} else if ( getIncomingID().size() > 1
				&&  getOutgoingID().size() == 1) {
			tmpName = "Converging";
			 setType(1);
		}
		if ( getIncomingID().size() == 1
				&&  getOutgoingID().size() == 1) {
			tmpName = "UnKnown";
			 setType(0);
		}
		if ( getType() !=  direction) {
			 addWarningMessage("was specified as '" +  directionName
					+ "', but it was found as a '" + tmpName + "' gateway");
		}
		if ( type ==  direction &&  direction == 0) {
			 addErrorMessage("Please specify the direction of gateway (Converging,Deverging or Mixed)");
		}
		int tp = 0;
		try {
			tp = TypeClassifier.getInstance().getGatewayType(this);
			 ukuGatewayType = tp;
		} catch (UnspecifiedGatewayException e) {
			 addErrorMessage(e.getMessage());
			return;
		}
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
