package org.eclipse.bpmn2.modeler.core.features;
import org.eclipse.graphiti.mm.pictograms.Connection;

public interface IConnectionRouter {
	
	// this is a way to pass additional info to the connection router in the form of a string;
	// the info is attached to the Connection as a Property.
	// see Graphiti.getPeService().setPropertyValue()
	public static final String ROUTING_INFO = "routing.info";
	public static final String ROUTING_INFO_FORCE = "force";
	public static final String ROUTING_INFO_BENDPOINT = "bendpoint";
	
	public boolean route(Connection connection);
	public void dispose();
}
