package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

public class ReceiveTaskPropertySection extends DefaultPropertySection implements ITabbedPropertyConstants {
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new ReceiveTaskDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new ReceiveTaskDetailComposite(parent, style);
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject be = super.getBusinessObjectForSelection(selection);
		if (be instanceof ReceiveTask)
			return be;
		return null;
	}
}
