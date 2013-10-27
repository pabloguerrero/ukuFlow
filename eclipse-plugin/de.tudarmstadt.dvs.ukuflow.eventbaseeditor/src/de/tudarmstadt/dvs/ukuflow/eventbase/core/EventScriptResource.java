package de.tudarmstadt.dvs.ukuflow.eventbase.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class EventScriptResource extends BinaryResourceImpl {

	public EventScriptResource(URI uri) {
		super(uri);
	}
	/*
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		getURI();
		
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		
	}
	*/
}
