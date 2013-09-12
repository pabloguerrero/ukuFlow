/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */
package de.tudarmstadt.dvs.ukuflow.runtime;

import java.util.Enumeration;

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.ui.wizards.FileService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.xml.sax.InputSource;
import org.eclipse.emf.ecore.EObject;

public class ukuFlowRuntime implements IBpmn2RuntimeExtension {
	private static final String UKUFLOW_NAMESPACE = "http://www.dvs.tu-darmstadt.de/ukuflow";
	private static final String DROOLS_NAMESPACE = "http://www.jboss.org/drools";
	private static final String[] typeLanguages = new String[] {
			"http://www.w3.org/2001/XMLSchema", "XML Schema",
			"http://www.java.com/javaTypes", "Java", };
	private static final String[] expressionLanguages = new String[] {
			"http://www.w3.org/1999/XPath", "XPath", "http://www.mvel.org/2.0",
			"mvel", "http://www.java.com/java", "java", };

	@Override
	public String[] getTypeLanguages() {
		return typeLanguages;
	}

	@Override
	public String[] getExpressionLanguages() {
		return expressionLanguages;
	}

	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		if ("1".equals("1"))
			return true;
		RootElementParser jparser = new RootElementParser(DROOLS_NAMESPACE);
		RootElementParser uparser = new RootElementParser(UKUFLOW_NAMESPACE);
		jparser.parse(new InputSource(FileService.getInputContents(input)));
		uparser.parse(new InputSource(FileService.getInputContents(input)));
		jparser.getResult();
		return jparser.getResult() || uparser.getResult();
	}

	@Override
	public String getTargetNamespace(Bpmn2DiagramType diagramType) {
		return UKUFLOW_NAMESPACE;
	}

	@Override
	public void initialize(DiagramEditor editor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Composite getPreferencesComposite(Composite parent,
			Bpmn2Preferences preferences) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A simple XML parser that checks if the root element of an xml document
	 * contains any namespace definitions matching the given namespace URI.
	 * 
	 * @author bbrodt
	 */
	public static class RootElementParser extends SAXParser {
		private String namespace;
		private boolean result = false;

		/**
		 * @param namespace
		 *            - the namespace URI to scan for.
		 */
		public RootElementParser(String namespace) {
			this.namespace = namespace;
		}

		public boolean getResult() {
			return result;
		}

		public void parse(InputSource source) {
			result = false;
			try {
				super.parse(source);
			} catch (AcceptedException e) {
				result = true;
			} catch (Exception e) {
			}
		}

		@Override
		public void startElement(QName qName, XMLAttributes attributes,
				Augmentations augmentations) throws XNIException {

			super.startElement(qName, attributes, augmentations);

			try {
				// search the "definitions" for a namespace that matches the
				// required namespace
				if ("definitions".equals(qName.localpart)) {
					Enumeration<?> e = fNamespaceContext.getAllPrefixes();
					while (e.hasMoreElements()) {
						String prefix = (String) e.nextElement();
						String namespace = fNamespaceContext.getURI(prefix);
						if (this.namespace.equals(namespace))
							throw new AcceptedException(qName.localpart);
					}
					throw new RejectedException();
				} else {
					throw new RejectedException();
				}
			} catch (Exception e) {
				throw new RejectedException();
			}
		}
	}

	public static class AcceptedException extends RuntimeException {
		public String acceptedRootElement;

		public AcceptedException(String acceptedRootElement) {
			this.acceptedRootElement = acceptedRootElement;
		}

		private static final long serialVersionUID = 1L;
	}

	public static class RejectedException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	@Override
	public void modelObjectCreated(EObject object) {
		// TODO Auto-generated method stub

	}
}
