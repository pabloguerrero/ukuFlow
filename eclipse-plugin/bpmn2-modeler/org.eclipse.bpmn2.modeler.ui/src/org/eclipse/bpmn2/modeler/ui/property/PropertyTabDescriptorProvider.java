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
package org.eclipse.bpmn2.modeler.ui.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.runtime.Bpmn2SectionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.Bpmn2TabDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider;

public class PropertyTabDescriptorProvider implements ITabDescriptorProvider {

	public PropertyTabDescriptorProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part,
			ISelection selection) {
		
		TargetRuntime rt = TargetRuntime.getDefaultRuntime();
		Object bpmn2Editor = part.getAdapter(BPMN2Editor.class);
		if (bpmn2Editor instanceof BPMN2Editor) {
			rt = ((BPMN2Editor)bpmn2Editor).getTargetRuntime(this);
		}
		
		List<Bpmn2TabDescriptor> desc = null;
		if (rt!=TargetRuntime.getDefaultRuntime()) {
			desc = TargetRuntime.getDefaultRuntime().getTabDescriptors();
			desc.addAll(rt.getTabDescriptors());
		}
		else
			desc = rt.getTabDescriptors();
		
		// do tab replacement
		ArrayList<Bpmn2TabDescriptor> replaced = new ArrayList<Bpmn2TabDescriptor>();
		for (Bpmn2TabDescriptor d : desc) {
			String replacedId = d.getReplaceTab();
			if (replacedId!=null) {
				String[] replacements = replacedId.split(" ");
				// tab replacement is only done if the replacement tab has section descriptors
				// that want the replacement to happen.
				for (String id : replacements) {
					for (Bpmn2SectionDescriptor s : (List<Bpmn2SectionDescriptor>) d.getSectionDescriptors()) {
//						if (s.appliesTo(part, selection)) 
						{
							// ask the section if it wants to replace this tab
							if (s.doReplaceTab(id, part, selection)) {
								// replace the tab whose ID is specified as "replaceTab" in this tab.
								Bpmn2TabDescriptor replacedTab = TargetRuntime.findTabDescriptor(id);
								if (replacedTab!=null) {
									replaced.add(replacedTab);
									int i = desc.indexOf(replacedTab);
									if (i>=0) {
										desc.set(i, d);
									}
								}
							}
						}
					}
				}
			}
		}
		if (replaced.size()>0)
			desc.removeAll(replaced);

		for (int i=desc.size()-1; i>=0; --i) {
			Bpmn2TabDescriptor d = desc.get(i);
			for (int j=i-1; j>=0; --j) {
				if (desc.get(j)==d) {
					desc.remove(i);
					break;
				}
			}
		}
		
		// remove empty tabs
		// also move the advanced tab to end of list
		ArrayList<Bpmn2TabDescriptor> emptyTabs = new ArrayList<Bpmn2TabDescriptor>();
		Bpmn2TabDescriptor advancedPropertyTab = null;
		for (Bpmn2TabDescriptor d : desc) {
			boolean empty = true;
			for (Bpmn2SectionDescriptor s : (List<Bpmn2SectionDescriptor>) d.getSectionDescriptors()) {
				if (s.appliesTo(part, selection)) {
					empty = false;
				}
				if (s.getSectionClass() instanceof AdvancedPropertySection) {
					advancedPropertyTab = d;
				}
			}
			if (empty) {
				emptyTabs.add(d);
			}
		}
		
		if (emptyTabs.size()>0)
			desc.removeAll(emptyTabs);
		
		if (advancedPropertyTab!=null) {
			desc.remove(advancedPropertyTab);
			desc.add(advancedPropertyTab);
		}
		
		return desc.toArray(new ITabDescriptor[desc.size()]);
	}

}
