/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.merrimac.dialogs;

import java.lang.reflect.Field;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.merrimac.IConstants;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.ErrorUtils;
import org.eclipse.bpmn2.modeler.core.utils.JavaReflectionUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.validation.ValidationStatusAdapter;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Base class for EObject feature editors. All subclasses must render the given object's feature,
 * which may be either an attribute, a reference to an EObject, or a list of EObject references.
 * Subclasses must also provide means to populate the display widgets from the feature and save
 * modifications to the feature made in the display widget.
 * 
 * @author Bob Brodt
 */
public abstract class ObjectEditor implements INotifyChangedListener {
	protected EObject object;
	protected EStructuralFeature feature;
	protected AbstractDetailComposite parent;
	private Label label;
	protected ControlDecoration decoration;
	protected int style;
	protected Class messages;
	protected boolean isWidgetUpdating = false;

	public ObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		this.parent = parent;
		this.object = object;
		this.feature = feature;
		this.style = SWT.NONE;
    	messages = JavaReflectionUtil.findClass(parent, "Messages");
	}
	
	/**
	 * This must be implemented by the ObjectEditor subclasses and should not be made public - clients
	 * should use one of the public createControl() methods instead so that the notifyChange listeners
	 * are hooked in to the change notification chain.
	 *  
	 * @param composite - parent composite for this ObjectEditor control
	 * @param label - a text label displayed to the left of the control
	 * @param style - editor control SWT style flags
	 * @return the control created by the ObjectEditor subclasses (e.g. a Text, or Combo)
	 */
	protected abstract Control createControl(Composite composite, String label, int style);
	
	public void setStyle(int style) {
		this.style = style;
	}
	
	public Control createControl(Composite composite, String label) {
		Control c = createControl(composite,label,style);
		c.setData(IConstants.NOTIFY_CHANGE_LISTENER_KEY, this);
		return c; 
	}
	
	public Control createControl(String label) {
		Control c = createControl(parent,label,style);
		c.setData(IConstants.NOTIFY_CHANGE_LISTENER_KEY, this);
		return c; 
	}
	
	public EStructuralFeature getFeature() {
		return feature;
	}

	public EObject getObject() {
		return object;
	}

	public void setObject(EObject object) {
		this.object = object;
	}
	
	public void setObject(EObject object, EStructuralFeature feature) {
		this.object = object;
		this.feature = feature;
	}
	
	protected FormToolkit getToolkit() {
		return parent.getToolkit();
	}
	
	protected DiagramEditor getDiagramEditor() {
		return parent.getDiagramEditor();
	}

	protected Diagram getDiagram() {
		return getDiagramEditor().getDiagramTypeProvider().getDiagram();
	}
	
	protected Label createLabel(Composite parent, String name) {
		label = getToolkit().createLabel(parent, name);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		updateLabelDecorator();
		return label;
	}
	
	public Label getLabel() {
		return label;
	}

	private boolean statusApplies(IStatus status) {
        if (status instanceof ConstraintStatus) {
        	ConstraintStatus cs = (ConstraintStatus)status;
        	for (EObject f : cs.getResultLocus()) {
        		if (f instanceof EStructuralFeature && f==this.feature) {
        			return true;
        		}
        	}
        }
        return false;
	}
	
	protected FeatureEditingDialog createFeatureEditingDialog(EObject value) {
		return new FeatureEditingDialog(getDiagramEditor(), object, feature, value);
	}

	/**
	 * Returns a descriptive text string for use as a tooltip on the Label for this editor.
	 * The default implementation constructs a key from the object and feature name owned by this
	 * editor, and looks up the description text in the messages resource of the parent plugin.
	 * 
	 * @return
	 */
	protected String getToolTipText() {
		String fieldName;
		Field field;
		String text = "";
    	if (messages!=null) {
    		try {
    			// fetch the description for this EClass and feature
	    		fieldName = "UI_" + object.eClass().getName() + "_" + feature.getName() + "_description";
//				text += "\n" + fieldName + "\n";
	    		field = messages.getField(fieldName);
	    		text += (String)field.get(null);
    		}
    		catch (Exception e) {
	    		try {
	    			// if a description is not found for this EClass, try "Any"
		    		fieldName = "UI_Any_" + feature.getName() + "_description";
//	    			text += "\n" + fieldName + "\n";
		    		field = messages.getField(fieldName);
		    		text += (String)field.get(null);
	    		}
	    		catch (Exception e2) {
	    		}
    		}
    		if (text==null || text.isEmpty())
    			text = "No description for "+label.getText();
    	}
    	return text;
	}
	
	/**
	 * Updates the error decorators and tooltips of this editor's Label widget.
	 */
	protected void updateLabelDecorator() {
		String tooltip = label.getToolTipText();
		
		if (tooltip==null && object!=null && feature!=null) {
   			label.setToolTipText(getToolTipText());
		}
		
		
		boolean applies = false;
    	String image = null;
    	String text = null;
        ValidationStatusAdapter statusAdapter = (ValidationStatusAdapter) EcoreUtil.getRegisteredAdapter(
        		object, ValidationStatusAdapter.class);
        if (statusAdapter != null) {
            final IStatus status = statusAdapter.getValidationStatus();
            if (status.isMultiStatus()) {
            	for (IStatus s : status.getChildren()) {
            		if (statusApplies(s)) {
            			applies = true;
            			break;
            		}
            	}
            }
            else if (statusApplies(status))
            	applies = true;
            
            if (applies) {
	            text = status.getMessage();
	            switch (status.getSeverity()) {
	            case IStatus.INFO:
	                image = ISharedImages.IMG_OBJS_INFO_TSK;
	                break;
	            case IStatus.WARNING:
	                image = ISharedImages.IMG_DEC_FIELD_WARNING;
	                break;
	            case IStatus.ERROR:
	                image = ISharedImages.IMG_DEC_FIELD_ERROR;
	                break;
	            default:
	                break;
	            }
            }
        }
        
        if (applies) {
        	if (decoration==null) {
        		decoration = new ControlDecoration(label, SWT.TOP | SWT.LEFT);
        	}
        	decoration.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(image));
        	decoration.show();
        	decoration.setDescriptionText(text);
        }
        else {
        	if (decoration!=null) {
        		decoration.hide();
        		decoration.dispose();
        		decoration = null;
        	}
        }
	}
	
	protected boolean setValue(final Object result) {
		TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
		boolean success = ModelUtil.setValue(domain, object, feature, result);
		if (!success) {
			ErrorUtils.showErrorMessage(
					"Can't set '"+ModelUtil.getDisplayName(object)+
					"' "+ModelUtil.getLabel(object,feature)+
					" with value '"+ModelUtil.getDisplayName(result)+"'");
			return false;
		}
		return true;
	}

	public abstract Object getValue();
	
	@Override
	public void notifyChanged(Notification notification) {
		updateLabelDecorator();
	}
	
	public void setVisible(boolean visible) {
		label.setVisible(visible);
		GridData data = (GridData)label.getLayoutData();
		data.exclude = !visible;
	}
	
	public boolean isVisible() {
		return label.isVisible();
	}

	public void dispose() {
		if (label!=null && !label.isDisposed()) {
			label.dispose();
			label = null;
		}
		if (decoration!=null) {
			decoration.dispose();
			decoration = null;
		}
	}
	
	public Control getControl() {
		return label;
	}
}
