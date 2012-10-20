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

public class ukuFlowRuntime implements IBpmn2RuntimeExtension {
	private static final String UKUFLOW_NAMESPACE = "http://www.dvs.tu-darmstadt.de/ukuflow";
	private static final String DROOLS_NAMESPACE = "http://www.jboss.org/drools";

	@Override
	public boolean isContentForRuntime(IEditorInput input) {
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
}
