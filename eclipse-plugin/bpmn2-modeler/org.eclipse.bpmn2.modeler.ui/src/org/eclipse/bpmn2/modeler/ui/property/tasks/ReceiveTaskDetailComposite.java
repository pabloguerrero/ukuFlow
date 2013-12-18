package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ReceiveTaskDetailComposite extends DefaultDetailComposite {
	private TextObjectEditor scriptEditor;

	public ReceiveTaskDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	public ReceiveTaskDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
		scriptEditor = null;
	}

	@Override
	public void createBindings(EObject be) {
		// bindAttribute(be, "scriptFormat");
		//bindAttribute(be,"eventScript");
		
		ModelUtil.setMultiLine(be, be.eClass().getEStructuralFeature("eventScript"),true);
		scriptEditor = new TextObjectEditor(this, be, be.eClass()
				.getEStructuralFeature("eventScript"));
		scriptEditor.setStyle(SWT.WRAP | SWT.MULTI);
		scriptEditor.createControl(getAttributesParent(), "Script");
		
	}
}
