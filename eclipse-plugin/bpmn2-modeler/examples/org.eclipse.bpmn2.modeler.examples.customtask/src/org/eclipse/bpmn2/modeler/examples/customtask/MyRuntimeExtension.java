package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.ui.DefaultBpmn2RuntimeExtension.RootElementParser;
import org.eclipse.bpmn2.modeler.ui.wizards.FileService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.xml.sax.InputSource;

public class MyRuntimeExtension implements IBpmn2RuntimeExtension {

	public MyRuntimeExtension() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		  InputSource source = new InputSource( FileService.getInputContents(input) );
		  RootElementParser parser = new RootElementParser("http://org.eclipse.bpmn2.modeler.examples.customtask");
		  parser.parse(source);
		  return parser.getResult();
	}

	@Override
	public String getTargetNamespace(Bpmn2DiagramType diagramType) {
		return "http://org.eclipse.bpmn2.modeler.examples.customtask";
	}

	@Override
	public String[] getTypeLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getExpressionLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(DiagramEditor editor) {
		// TODO Auto-generated method stub

	}
}
