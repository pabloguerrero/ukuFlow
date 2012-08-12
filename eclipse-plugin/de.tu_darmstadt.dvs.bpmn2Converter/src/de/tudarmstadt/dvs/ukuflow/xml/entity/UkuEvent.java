package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.util.Map;

public class UkuEvent extends UkuElement {

	public UkuEvent(String id) {
		super(id);
		syntax = true;
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
	}
}
