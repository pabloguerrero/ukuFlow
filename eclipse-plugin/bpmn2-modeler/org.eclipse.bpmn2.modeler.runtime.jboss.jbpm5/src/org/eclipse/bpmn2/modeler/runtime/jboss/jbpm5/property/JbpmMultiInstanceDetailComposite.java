package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.ui.property.tasks.MultiInstanceLoopCharacteristicsDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

public class JbpmMultiInstanceDetailComposite extends
		MultiInstanceLoopCharacteristicsDetailComposite {

	public JbpmMultiInstanceDetailComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public JbpmMultiInstanceDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"inputDataItem",
						"loopDataInputRef",
						"loopCardinality",
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

}
