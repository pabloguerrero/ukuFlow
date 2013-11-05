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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.activity;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.Compensation;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

public class UpdateActivityCompensateMarkerFeature extends AbstractUpdateMarkerFeature<Activity> {
	
	public static String IS_COMPENSATE_PROPERTY = "marker.compensate";
	
	public UpdateActivityCompensateMarkerFeature(IFeatureProvider fp) {
	    super(fp);
    }

	@Override
	protected String getPropertyKey() {
	    return IS_COMPENSATE_PROPERTY;
    }

	@Override
	protected boolean isPropertyChanged(Activity activity, String propertyValue) {
		return activity.isIsForCompensation() != new Boolean(propertyValue);
	}
	
	@Override
	protected void doUpdate(Activity activity, ContainerShape markerContainer) {
		if (activity.isIsForCompensation()) {
			GraphicsUtil.showActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_COMPENSATE);
		} else {
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_COMPENSATE);
		}
	}
	
	@Override
	protected String convertPropertyToString(Activity activity) {
	    return Boolean.toString(activity.isIsForCompensation());
    }
}