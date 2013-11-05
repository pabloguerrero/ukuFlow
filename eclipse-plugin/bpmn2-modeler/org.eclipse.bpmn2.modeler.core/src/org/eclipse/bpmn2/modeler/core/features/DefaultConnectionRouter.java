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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

/**
 * Router for straight-line connections from source to target.
 * Currently this does nothing but serves as a container for common
 * fields and methods.
 */
public class DefaultConnectionRouter extends AbstractConnectionRouter {

	protected List<ContainerShape> allShapes;
	protected Connection connection;
	protected Shape source;
	protected Shape target;
	protected Anchor sourceAnchor, targetAnchor;
	
	public DefaultConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		this.connection = connection;
		this.source = (Shape) connection.getStart().getParent();
		this.target = (Shape) connection.getEnd().getParent();

		if (AnchorUtil.useAdHocAnchors(source, connection) && AnchorUtil.isAdHocAnchor(connection.getStart()))
			sourceAnchor = connection.getStart();
		else
			sourceAnchor = null;
		if (AnchorUtil.useAdHocAnchors(target, connection) && AnchorUtil.isAdHocAnchor(connection.getEnd()))
			targetAnchor = connection.getEnd();
		else
			targetAnchor = null;
		return false;
	}
	
	protected void initialize() {
	}
	
	@Override
	public void dispose() {
		// be sure to clean up the routing info
		removeRoutingInfo(connection);
	}

	/**
	 * Check if the connection's source and target nodes are identical.
	 * 
	 * @return true if connection source == target
	 */
	protected boolean isSelfConnection() {
		if (source != target)
			return false;
		return true;
	}

	protected List<ContainerShape> findAllShapes() {
		allShapes = new ArrayList<ContainerShape>();
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		TreeIterator<EObject> iter = diagram.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			if (o instanceof ContainerShape) {
				// this is a potential collision shape
				ContainerShape shape = (ContainerShape)o;
				BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(shape, BPMNShape.class);
				if (bpmnShape==null)
					continue;
//				if (shape==source || shape==target)
//					continue;
				// ignore containers (like Lane, SubProcess, etc.) if the source
				// or target shapes are children of the container's hierarchy
				if (shape==source.eContainer() || shape==target.eContainer())
					continue;
				
				// ignore some containers altogether
				BaseElement be = bpmnShape.getBpmnElement();
				if (be instanceof Lane)
					continue;
				// TODO: other criteria here?
	
				allShapes.add(shape);
			}
		}
		GraphicsUtil.dump("All Shapes", allShapes);
		return allShapes;
	}

	protected LineSegment getCollisionEdge(Point p1, Point p2) {
		ContainerShape shape = getCollision(p1, p2);
		if (shape!=null) {
			return GraphicsUtil.findNearestEdge(shape, p1);
		}
		return null;
	}

	protected ContainerShape getCollision(Point p1, Point p2) {
		List<ContainerShape> collisions = findCollisions(p1, p2);
		if (collisions.size()==0)
			return null;
		if (collisions.size()==1)
			return collisions.get(0);
		sortCollisions(collisions, p2);
		return collisions.get(0);
	}
	
	protected List<ContainerShape> findCollisions(Point p1, Point p2) {
		List<ContainerShape> collisions = new ArrayList<ContainerShape>();
		if (allShapes==null)
			findAllShapes();
		for (ContainerShape shape : allShapes) {
			if (!FeatureSupport.isGroupShape(shape) && !FeatureSupport.isLabelShape(shape) && !FeatureSupport.isParticipant(shape))
				if (GraphicsUtil.intersectsLine(shape, p1, p2))
					collisions.add(shape);
		}
//		if (collisions.size()>0)
//			GraphicsUtil.dump("Collisions with line ["+p1.getX()+", "+p1.getY()+"]"+" ["+p2.getX()+", "+p2.getY()+"]", collisions);
		return collisions;
	}

	protected void sortCollisions(List<ContainerShape> collisions, final Point p) {
		Collections.sort(collisions, new Comparator<ContainerShape>() {
	
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				LineSegment seg1 = GraphicsUtil.findNearestEdge(s1, p);
				double d1 = seg1.getDistance(p);
				LineSegment seg2 = GraphicsUtil.findNearestEdge(s2, p);
				double d2 = seg2.getDistance(p);
				return (int) (d2 - d1);
			}
		});
	}
	
	protected List<Connection> findCrossings(Point start, Point end) {
		List<Connection> crossings = new ArrayList<Connection>();
		List<Connection> allConnections = fp.getDiagramTypeProvider().getDiagram().getConnections();
		for (Connection connection : allConnections) {
			if (Graphiti.getPeService().getProperty(connection, RoutingNet.CONNECTION)!=null) {
				continue;
			}
			Point p1 = GraphicsUtil.createPoint(connection.getStart());
			Point p3 = GraphicsUtil.createPoint(connection.getEnd());
			if (connection instanceof FreeFormConnection) {
				FreeFormConnection ffc = (FreeFormConnection) connection;
				Point p2 = p1;
				for (Point p : ffc.getBendpoints()) {
					if (GraphicsUtil.intersects(start, end, p1, p)) {
						crossings.add(connection);
						break;
					}
					p2 = p1 = p;
				}
				if (GraphicsUtil.intersects(start, end, p2, p3)) {
					crossings.add(connection);
				}
			}
			else if (GraphicsUtil.intersects(start, end, p1, p3)) {
				crossings.add(connection);
			}
		}
		return crossings;
	}

	protected static double length(Point p1, Point p2) {
		return GraphicsUtil.getLength(p1, p2);
	}

	protected void drawConnectionRoutes(List<ConnectionRoute> allRoutes) {
		if (GraphicsUtil.debug) {

			DeleteRoutingConnectionFeature deleteFeature = new DeleteRoutingConnectionFeature(fp);
			deleteFeature.delete();

			Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
			for (int i=0; i<allRoutes.size(); ++i) {
				ConnectionRoute r = allRoutes.get(i);
				Anchor sa = AnchorUtil.findNearestAnchor(source, r.get(0));
				Anchor ta = AnchorUtil.findNearestAnchor(target, r.get( r.size()-1 ));
				AddConnectionContext context = new AddConnectionContext(sa, ta);
				context.setTargetContainer(diagram);
				context.setNewObject(r);
				AddRoutingConnectionFeature feature = new AddRoutingConnectionFeature(fp);
				feature.add(context);
				
				GraphicsUtil.dump(r.toString());
			}
		}
	}
	
	protected class AddRoutingConnectionFeature extends AbstractAddShapeFeature {
		public static final String CONNECTION = "ROUTING_NET_CONNECTION";

		public AddRoutingConnectionFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canAdd(IAddContext ac) {
			return true;
		}

		@Override
		public PictogramElement add(IAddContext ac) {
			IAddConnectionContext context = (IAddConnectionContext) ac;
			Anchor sourceAnchor = context.getSourceAnchor();
			Anchor targetAnchor = context.getTargetAnchor();
			ConnectionRoute route = (ConnectionRoute) context.getNewObject();

			Diagram diagram = getDiagram();
			FreeFormConnection connection = peService
					.createFreeFormConnection(diagram);
			connection.setStart(sourceAnchor);
			connection.setEnd(targetAnchor);
			for (int i = 1; i < route.size() - 1; ++i) {
				connection.getBendpoints().add(route.get(i));
			}

			peService.setPropertyValue(connection, CONNECTION, "" + route.id);

			Polyline connectionLine = Graphiti.getGaService().createPolyline(
					connection);

			connectionLine.setLineWidth(1);
			connectionLine.setLineStyle(LineStyle.DASH);

			IColorConstant foreground = new ColorConstant(255, 120, 255);

			int w = 3;
			int l = 15;

			ConnectionDecorator decorator = peService
					.createConnectionDecorator(connection, false, 1.0, true);
			Polyline arrowhead = gaService.createPolygon(decorator, new int[] {
					-l, w, 0, 0, -l, -w, -l, w });
			arrowhead.setForeground(gaService.manageColor(diagram, foreground));
			connectionLine.setForeground(gaService.manageColor(diagram,
					foreground));

			return connection;
		}
	}
	
	protected class DeleteRoutingConnectionFeature extends DefaultDeleteFeature {

		public DeleteRoutingConnectionFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canDelete(IDeleteContext context) {
			return true;
		}

		public void delete() {
			delete(null);
		}
		
		@Override
		public void delete(IDeleteContext context) {
			List<Connection> deleted = new ArrayList<Connection>();
			deleted.addAll(getDiagram().getConnections());
			
			for (Connection connection : deleted) {
				if (Graphiti.getPeService().getProperty(connection, RoutingNet.CONNECTION)!=null) {
					context = new DeleteContext(connection);
					super.delete(context);
				}
			}
		}
		
	}
}
