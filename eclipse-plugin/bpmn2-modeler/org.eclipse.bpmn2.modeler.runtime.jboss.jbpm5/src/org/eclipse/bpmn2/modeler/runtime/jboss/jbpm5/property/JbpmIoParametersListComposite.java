package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeContentProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.ModelExtensionAdapter;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParameterNameColumn;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersListComposite;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class JbpmIoParametersListComposite extends IoParametersListComposite {

	public JbpmIoParametersListComposite(
			IoParametersDetailComposite detailComposite, EObject container,
			InputOutputSpecification ioSpecification,
			EStructuralFeature ioFeature) {
		super(detailComposite, container, ioSpecification, ioFeature);
	}
	
	@Override
	public void bindList(final EObject theobject, final EStructuralFeature thefeature) {
		super.bindList(theobject, thefeature);

		// Add a selection listener so we can enable and disable the Remove button.
		// This should only be enabled for I/O Parameters that were initially added by
		// the user, i.e. only those that are not defined in the WID file.
		tableViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (removeAction!=null) {
					IStructuredSelection ss = (IStructuredSelection)event.getSelection();
					ItemAwareElement element = (ItemAwareElement)ss.getFirstElement();
					boolean enable = !event.getSelection().isEmpty();
					if (JbpmIoParametersDetailComposite.isCustomTaskIOParameter(element))
						enable = false;
					removeAction.setEnabled(enable);
				}
			}
		});
	}
	
	@Override
	protected int createColumnProvider(EObject object, EStructuralFeature feature) {
		super.createColumnProvider(object, feature);
		// if the owning Activity is a Custom Task, then make this table read-only
		if (CustomTaskDescriptor.getDescriptor(activity) != null) {
			for (TableColumn tc : (List<TableColumn>) columnProvider.getColumns()) {
				if (tc instanceof IoParameterNameColumn) {
					tc.setEditable(false);
				}
			}
		}
		return columnProvider.getColumns().size();
	}

	@Override
	public ListCompositeContentProvider getContentProvider(EObject object, EStructuralFeature feature, EList<EObject>list) {
		if (contentProvider==null) {
			contentProvider = new ListCompositeContentProvider(this, object, feature, list) {
				@Override
				public Object[] getElements(Object inputElement) {
					
					Object elements[] = super.getElements(inputElement);
					List<Property> props = null;
					ModelExtensionAdapter adapter = ModelExtensionDescriptor.getModelExtensionAdapter(activity);
					if (adapter!=null && !(adapter.getDescriptor() instanceof CustomTaskDescriptor)) {
						if (JbpmIoParametersListComposite.this.isInput)
							props = adapter.getProperties("ioSpecification/dataInputs/name");
						else
							props = adapter.getProperties("ioSpecification/dataOutputs/name");
					
						List<Object> filtered = new ArrayList<Object>();
						for (Object e : elements) {
							boolean skip = false;
							EStructuralFeature f = ((EObject)e).eClass().getEStructuralFeature("name");
							if (f!=null) {
								Object elementName = (String) ((EObject)e).eGet(f);
								for (Property p : props) {
									Object propName = p.getFirstStringValue();
									if (elementName!=null && propName!=null && elementName.equals(propName)) {
										skip = true;
										break;
									}
								}
							}
							if (!skip)
								filtered.add(e);
						}
						return filtered.toArray();
					}
					return elements;
				}
			};
		}
		return contentProvider;
	}
}