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
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.RoutingStyle;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;

/**
 * Layout Feature class for Connections. This simply invokes one of the Connection Routers
 * depending on the user preferences for each type of connection: SequenceFlow, MessageFlow,
 * Association and Conversation. See the Bpmn2EditorPreferencePage for details.
 */
public class DefaultLayoutBPMNConnectionFeature extends AbstractLayoutFeature {

	boolean hasDoneChanges = false;
	
	public DefaultLayoutBPMNConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		// Check if automatic routing has been disabled by the user.
		Connection connection = (Connection) context.getPictogramElement();
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
		Bpmn2Preferences prefs = Bpmn2Preferences.getInstance(be);
		return prefs.getEnableConnectionRouting();
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		if (canLayout(context)) {
			Connection connection = (Connection) context.getPictogramElement();
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
			if (be!=null) {
				// get the user preference of routing style for this connection
				Bpmn2Preferences preferences = Bpmn2Preferences.getInstance(be);
				if (preferences!=null) {
					ShapeStyle ss = preferences.getShapeStyle(be);
					if (ss!=null) {
						IFeatureProvider fp = getFeatureProvider();
						IConnectionRouter router = null;
						if (ss.getRoutingStyle() == RoutingStyle.Manhattan)
							router = new ManhattanConnectionRouter(fp);
						else if (ss.getRoutingStyle() == RoutingStyle.ManualBendpoint) {
							router = new BendpointConnectionRouter(fp);
							((BendpointConnectionRouter)router).setManualRouting(true);
						}
						else if (ss.getRoutingStyle() == RoutingStyle.AutomaticBendpoint) {
							router = new BendpointConnectionRouter(fp);
							((BendpointConnectionRouter)router).setManualRouting(false);
						}

						if (router!=null) {
							hasDoneChanges = router.route(connection);
							router.dispose();
						}
					}
				}
			}
		}
		return hasDoneChanges;
	}
}
