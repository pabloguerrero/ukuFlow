package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AddTaskFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.TaskFeatureContainer;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class JbpmTaskFeatureContainer extends TaskFeatureContainer {

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new JbpmAddTaskFeature(fp);
	}

	public static class JbpmAddTaskFeature extends AddTaskFeature<Task> {

		public JbpmAddTaskFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public PictogramElement add(IAddContext context) {
			PictogramElement pe = super.add(context);
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(pe);
			ElementParameters ep = JbpmModelUtil.getElementParameters(be);
			getFeatureProvider().link(pe, ep);
			return pe;
		}
		
	}
}
