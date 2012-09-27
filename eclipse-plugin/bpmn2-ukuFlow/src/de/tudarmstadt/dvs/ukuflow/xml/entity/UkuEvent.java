package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

public class UkuEvent extends UkuElement {
	byte type;

	public UkuEvent(String id) {
		super(id);		
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);

	}

	public void setType(int eventType) {
		type = (byte) eventType;
	}

	public byte getType() {
		return type;
	}

}
