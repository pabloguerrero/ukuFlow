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
package org.eclipse.bpmn2.modeler.core.features.artifact;

import java.io.IOException;

import org.eclipse.bpmn2.Artifact;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;

public abstract class AbstractCreateArtifactFeature<T extends Artifact> extends AbstractBpmn2CreateFeature<T> {

	public AbstractCreateArtifactFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return FeatureSupport.isValidArtifactTarget(context);
	}

	@Override
	public Object[] create(ICreateContext context) {
		T artifact = createBusinessObject(context);
		addGraphicalRepresentation(context, artifact);
		return new Object[] { artifact };
	}

	protected abstract String getStencilImageId();

	@Override
	public String getCreateImageId() {
		return getStencilImageId();
	}

	@Override
	public String getCreateLargeImageId() {
		return getStencilImageId();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T createBusinessObject(ICreateContext context) {
		T artifact = null;
		try {
			artifact = super.createBusinessObject(context);
			ModelHandler handler = ModelHandler.getInstance(getDiagram());
			handler.addArtifact(FeatureSupport.getTargetParticipant(context, handler), artifact);
		} catch (IOException e) {
			Activator.logError(e);
		}
		return artifact;
	}
}