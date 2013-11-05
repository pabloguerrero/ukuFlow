package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.DataOutputPropertiesAdapter;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ItemDefinitionRefFeatureDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public class JbpmDataOutputPropertiesAdapter extends DataOutputPropertiesAdapter {

	public JbpmDataOutputPropertiesAdapter(AdapterFactory adapterFactory, DataOutput object) {
		super(adapterFactory, object);

    	EStructuralFeature feature = Bpmn2Package.eINSTANCE.getItemAwareElement_ItemSubjectRef();
    	setProperty(feature, UI_CAN_CREATE_NEW, Boolean.TRUE);
    	setProperty(feature, UI_CAN_EDIT, Boolean.FALSE);
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
		
    	setFeatureDescriptor(feature,
			new ItemDefinitionRefFeatureDescriptor<DataOutput>(adapterFactory,object,feature) {
				
				@Override
				public void setValue(Object context, final Object value) {
					final DataOutput dataInput = adopt(context);

					TransactionalEditingDomain domain = getEditingDomain(object);
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							dataInput.setItemSubjectRef(JbpmModelUtil.getDataType(dataInput, value));
						}
					});
				}
				
				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					final DataOutput property = adopt(context);
					return JbpmModelUtil.collectAllDataTypes(property);
				}
				
				@Override
				public boolean isMultiLine(Object context) {
					return true;
				}
			}
    	);
	}

}
