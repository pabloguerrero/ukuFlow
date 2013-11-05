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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType;
import org.eclipse.bpmn2.modeler.ui.property.ExtensionValueListComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ActivityDetailComposite;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author Bob Brodt
 *
 */
public class JbpmActivityDetailComposite extends ActivityDetailComposite {

//	ScriptTableComposite onEntryScriptTable;
//	ScriptTableComposite onExitScriptTable;
	JbpmScriptTaskDetailComposite onEntryScriptEditor;
	JbpmScriptTaskDetailComposite onExitScriptEditor;
	
	/**
	 * @param section
	 */
	public JbpmActivityDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmActivityDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
//		onEntryScriptTable = null;
//		onExitScriptTable = null;
	}

	@Override
	public void createBindings(EObject be) {
		super.createBindings(be);
		bindEntryExitScripts(be);
	}
	
	protected void bindEntryExitScripts(EObject be) {
//		onEntryScriptTable = new ScriptTableComposite(this);
//		onEntryScriptTable.bindList(be, DroolsPackage.eINSTANCE.getDocumentRoot_OnEntryScript());
//		onEntryScriptTable.setTitle("On Entry Scripts");
		boolean enable = isModelObjectEnabled(be.eClass().getName(), "onEntryScript");
		onEntryScriptEditor = new JbpmScriptTaskDetailComposite(this, SWT.NONE);
		OnEntryScriptType onEntryScript = getOrCreateEntryExitScript((Activity)be, OnEntryScriptType.class);
		onEntryScriptEditor.setBusinessObject(onEntryScript);
		onEntryScriptEditor.setTitle("On Entry Script");
		
//		onExitScriptTable = new ScriptTableComposite(this);
//		onExitScriptTable.bindList(be, DroolsPackage.eINSTANCE.getDocumentRoot_OnExitScript());
//		onExitScriptTable.setTitle("On Exit Scripts");
		onExitScriptEditor = new JbpmScriptTaskDetailComposite(this, SWT.NONE);
		OnExitScriptType onExitScript = getOrCreateEntryExitScript((Activity)be, OnExitScriptType.class);
		onExitScriptEditor.setBusinessObject(onExitScript);
		onExitScriptEditor.setTitle("On Exit Script");
	}
	
	@SuppressWarnings("unchecked")
	private <T extends EObject> T getOrCreateEntryExitScript(final Activity be, final Class<T> clazz) {
		final Object[] result = new Object[] {null};
		List<T> onEntryScriptList = ModelUtil.getAllExtensionAttributeValues(be, clazz);
		if (onEntryScriptList.size()>0)
			result[0] = onEntryScriptList.get(0);
		else {
			TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					EClass eclass = (EClass)DroolsPackage.eINSTANCE.getEClassifier(clazz.getSimpleName());
					T script = (T) DroolsFactory.eINSTANCE.create(eclass);
					EStructuralFeature f = script.eClass().getEStructuralFeature("script");
					if (f!=null)
						script.eSet(f, "");
					f = script.eClass().getEStructuralFeature("scriptFormat");
					if (f!=null)
						script.eSet(f,"http://www.java.com/java");
					if (clazz == OnEntryScriptType.class)
						f = DroolsPackage.eINSTANCE.getDocumentRoot_OnEntryScript();
					else
						f = DroolsPackage.eINSTANCE.getDocumentRoot_OnExitScript();
					ModelUtil.addExtensionAttributeValue(be, f, script);
					result[0] = script;
				}
				
			});
		}
		return (T)result[0];
	}
	
	public class ScriptTableComposite extends ExtensionValueListComposite {

		/**
		 * @param parent
		 * @param style
		 */
		public ScriptTableComposite(Composite parent) {
			super(parent, AbstractListComposite.DEFAULT_STYLE);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.ui.property.ExtensionValueTableComposite#addListItem(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
		 */
		@Override
		protected EObject addListItem(EObject object, EStructuralFeature feature) {
			EObject newScript = DroolsFactory.eINSTANCE.create(listItemClass);
			EStructuralFeature f = newScript.eClass().getEStructuralFeature("script");
			if (f!=null)
				newScript.eSet(f, "");
			f = newScript.eClass().getEStructuralFeature("scriptFormat");
			if (f!=null)
				newScript.eSet(f,"http://www.java.com/java");
			addExtensionValue(newScript);
			return newScript;
		}

		@Override
		public AbstractDetailComposite createDetailComposite(final Composite parent, Class eClass) {
			return new JbpmScriptTaskDetailComposite(parent, SWT.NONE) {
				@Override
				public Composite getAttributesParent() {
					((Section)parent).setText("Script Details");
					return (Composite) ((Section)parent).getClient();
				}
			};
		}
	}
}
