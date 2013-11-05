package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.ItemDefinitionListComposite;
import org.eclipse.swt.widgets.Composite;

public class JbpmItemDefinitionListComposite extends
		ItemDefinitionListComposite {

	public JbpmItemDefinitionListComposite(AbstractBpmn2PropertySection section, int style) {
		super(section, DEFAULT_STYLE);
	}

	public JbpmItemDefinitionListComposite(AbstractBpmn2PropertySection section) {
		super(section, DEFAULT_STYLE);
	}

	public JbpmItemDefinitionListComposite(Composite parent, int style) {
		super(parent, DEFAULT_STYLE);
	}

	public JbpmItemDefinitionListComposite(Composite parent) {
		super(parent, DEFAULT_STYLE);
	}

}
