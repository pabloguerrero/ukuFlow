package org.eclipse.bpmn2.modeler.wsil;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.bpel.wsil.model.inspection.InspectionPackage;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterRegistry;
import org.eclipse.bpmn2.modeler.wsil.ui.adapter.Bpmn2WSILAdapterFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.bpmn2.modeler.wsil"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	// Adapter Factory registration
	static {
		AdapterRegistry.INSTANCE.registerAdapterFactory(
			    InspectionPackage.eINSTANCE, Bpmn2WSILAdapterFactory.getInstance() );
	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static void logStatus(IStatus status) {
		Platform.getLog(plugin.getBundle()).log(status);
	}

	public static void logError(Exception e) {
		logStatus(createStatus(e));
	}

	private static Status createStatus(Exception e) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
	}

	/**
	 * Initializes the table of images used in this plugin.
	 */
	@Override
	protected ImageRegistry createImageRegistry() {
		ImageRegistry registry = super.createImageRegistry();
		URL baseURL = getBundle().getEntry("/"); //$NON-NLS-1$

		// A little reflection magic ... so that we don't
		// have to add the createImageDescriptor every time
		// we add it to the IConstants ..
		Field fields[] = IConstants.class.getFields();	
		for(int i=0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.getType() != String.class) { 
				continue;
			}
			String name = f.getName();
			if (name.startsWith("ICON_") || name.startsWith("CURSOR_") || name.startsWith("IMAGE_")) {   //$NON-NLS-1$ //$NON-NLS-2$
				try {
					String value = (String) f.get(null);
					createImageDescriptor(registry, value, baseURL);
				} catch (Exception e) {
					logError(e);
				}
			}			
		}
		return registry;
	}

	/**
	 * Creates an image descriptor and places it in the image registry.
	 */
	private void createImageDescriptor(ImageRegistry registry, String id, URL baseURL) {
		URL url = null;
		try {
			url = new URL(baseURL, IConstants.ICON_PATH + id);
		} catch (MalformedURLException e) {
			logError(e);
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		registry.put(id, desc);
	}

	public Image getImage(String id) {
		return getImageRegistry().get(id);
	}
	
    public ImageDescriptor getImageDescriptor(String id) {
		return getImageRegistry().getDescriptor(id);
    }

}
