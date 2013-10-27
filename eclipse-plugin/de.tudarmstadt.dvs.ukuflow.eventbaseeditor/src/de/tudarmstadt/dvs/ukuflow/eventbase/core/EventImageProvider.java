/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package de.tudarmstadt.dvs.ukuflow.eventbase.core;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class EventImageProvider extends AbstractImageProvider {

	// The prefix for all identifiers of this image provider
	protected static final String PREFIX = "de.tudarmstadt.dvs.ukuflow.icon."; //$NON-NLS-1$
	
	// The image identifier for an EReference.
	public static final String IMG_EREFERENCE = PREFIX + "ereference"; //$NON-NLS-1$	
	public static final String GEARS_ICON = PREFIX + "gears";
	public static final String GEARS2_ICON = PREFIX + "gears2";
	public static final String FUNNEL_ICON = PREFIX + "funnel";
	@Override
	protected void addAvailableImages() {
		// register the path for each image identifier
		addImageFilePath(IMG_EREFERENCE, "icons/ereference.gif"); //$NON-NLS-1$
		addImageFilePath(GEARS_ICON, "icons/16/gears.png");
		addImageFilePath(GEARS2_ICON, "icons/gears2.png");
		addImageFilePath(FUNNEL_ICON, "/icons/16/funnel.png");
	}
}
