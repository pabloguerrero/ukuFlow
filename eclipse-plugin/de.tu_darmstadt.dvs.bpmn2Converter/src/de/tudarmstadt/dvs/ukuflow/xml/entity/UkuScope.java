package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

public class UkuScope extends UkuEntity {

	public UkuScope(String id) {
		super(id);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		// TODO Auto-generated method stub

	}

}
