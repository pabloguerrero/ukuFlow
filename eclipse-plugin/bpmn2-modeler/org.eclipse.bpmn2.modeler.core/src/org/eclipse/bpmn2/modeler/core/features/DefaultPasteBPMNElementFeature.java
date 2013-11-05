package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.ConversationLink;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.AreaContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

public class DefaultPasteBPMNElementFeature extends AbstractPasteFeature {

	protected Resource resource;
	protected Definitions definitions;
	protected Hashtable<String, String> idMap;
	protected HashMap<ContainerShape, ContainerShape> shapeMap;
	protected HashMap<Connection, Connection> connectionMap;
	protected int xReference;
	protected int yReference;
	protected Diagram diagram;

	public DefaultPasteBPMNElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canPaste(IPasteContext context) {
		// target must be a FlowElementsContainer (Process, etc.)
		if (getTargetContainerShape(context)==null) {
			return false;
		}

		// can paste, if all objects on the clipboard are PictogramElements
		Object[] fromClipboard = getFromClipboard();
		if (fromClipboard == null || fromClipboard.length == 0) {
			return false;
		}
		int count = 0;
		for (Object object : fromClipboard) {
			if (!(object instanceof PictogramElement)) {
				continue;
			}
			PictogramElement pe = (PictogramElement) object;
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(pe);
			if (!(be instanceof FlowElement) && !(be instanceof Lane) && !(be instanceof Participant)) {
				continue;
			}
			// can't paste Boundary Events directly - these are "carried along"
			// by the Activity to which they are attached.
			if (be instanceof BoundaryEvent) {
				continue;
			}
			// can't paste Label shapes
			if (pe instanceof Shape && FeatureSupport.isLabelShape((Shape)pe)) {
				continue;
			}
			++count;
		}
		if (count==0)
			return false;

		return true;
	}

	@Override
	public void paste(IPasteContext context) {
		// TODO: COPY-PASTE
		ContainerShape targetContainerShape = getTargetContainerShape(context);
		BaseElement targetContainerObject = getContainerObject(targetContainerShape);

		// save the Diagram and Resource needed for constructing the new objects
		diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
		resource = targetContainerObject.eResource();
		definitions = ModelUtil.getDefinitions(resource);
		idMap = new Hashtable<String, String>();
		shapeMap = new HashMap<ContainerShape, ContainerShape>();
		connectionMap = new HashMap<Connection, Connection>();
		xReference = 0;
		yReference = 0;
		
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;
		Object[] fromClipboard = getFromClipboard();
		for (Object object : fromClipboard) {
			if (object instanceof ContainerShape) {
				ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram((ContainerShape) object);
				if (loc.getX() < xMin) {
					xMin = loc.getX();
				}
				if (loc.getY() < yMin) {
					yMin = loc.getY();
				}
			}
		}
		if (xMin!=Integer.MAX_VALUE) {
			xReference = xMin;
			yReference = yMin;
		}

		int x = context.getX();
		int y = context.getY();
		if (!(targetContainerShape instanceof Diagram)) {
			ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(targetContainerShape);
			x -= loc.getX();
			y -= loc.getY();
		}

		// First create all shapes. This creates a lookup map of old to new
		// ContainerShape objects.
		for (Object object : fromClipboard) {
			if (object instanceof ContainerShape) {
				copyShape((ContainerShape) object, targetContainerShape, x, y);
			}
		}

		// Handle connections now that we know all shapes have been created
		x = context.getX(); // Connection bendpoint coordinates are always
							// relative to diagram
		y = context.getY();
		for (Object object : fromClipboard) {
			if (object instanceof Connection) {
				copyConnection((Connection) object, targetContainerShape, x, y);
			}
		}
		
		// handle any connections that were not created because of missing source/target
		for (Entry<Connection, Connection> entry : connectionMap.entrySet()) {
			if (entry.getValue()==null) {
				copyConnection(entry.getKey(), targetContainerShape, x, y);
			}
		}
		PictogramElement newPes[] = new PictogramElement[shapeMap.size()];
		int i = 0;
		for (Entry<ContainerShape, ContainerShape> entry : shapeMap.entrySet()) {
			newPes[i++] = entry.getValue();
		}
		
		this.getDiagramEditor().setPictogramElementsForSelection(newPes);
	}
	
	protected Object[] getFromClipboard() {
		List<Object> allObjects = new ArrayList<Object>();
		Object[] objects = super.getFromClipboard();
		for (Object o : objects)
			allObjects.add(o);
		
		List<Object> filteredObjects = new ArrayList<Object>();
		for (Object object : allObjects) {
			if (object instanceof ContainerShape && !FeatureSupport.isLabelShape((Shape)object)) {
				filteredObjects.add(object);
			}
			else if (object instanceof Connection) {
				Connection c = (Connection)object;
				if (allObjects.contains(c.getStart().getParent()) &&
						allObjects.contains(c.getEnd().getParent())) {
					filteredObjects.add(object);
				}
			}
		}
		
		return filteredObjects.toArray();
	}
	
	private BaseElement createNewObject(BaseElement oldObject, BaseElement targetContainerObject) {
		Bpmn2ModelerFactory.setEnableModelExtensions(false);
		BaseElement newObject = EcoreUtil.copy(oldObject);
		Bpmn2ModelerFactory.setEnableModelExtensions(true);

		// get rid of some of the objects created by EcoreUtil.copy() as these will be
		// constructed here because we need to create the Graphiti shapes and DI elements
		// along with these
		if (newObject instanceof FlowElementsContainer) {
			// we will create our own FlowElements, thank you!
			((FlowElementsContainer)newObject).getFlowElements().clear();
		}
		
		if (newObject instanceof Lane) {
			// we will construct these ourselves
			((Lane) newObject).getFlowNodeRefs().clear();
			((Lane) newObject).setChildLaneSet(null);
			if (targetContainerObject instanceof FlowElementsContainer) {
				FlowElementsContainer fc = (FlowElementsContainer)targetContainerObject;
				if (fc.getLaneSets().size()!=0) {
					fc.getLaneSets().get(0).getLanes().add((Lane)newObject);
				}
				else {
					LaneSet ls = Bpmn2ModelerFactory.create(resource, LaneSet.class);
					fc.getLaneSets().add(ls);
					ls.getLanes().add((Lane)newObject);
				}
			}
			else if (targetContainerObject instanceof Lane) {
				Lane ln = (Lane)targetContainerObject;
				if (ln.getChildLaneSet()==null) {
					LaneSet ls = Bpmn2ModelerFactory.create(resource, LaneSet.class);
					ln.setChildLaneSet(ls);
				}
				ln.getChildLaneSet().getLanes().add((Lane)newObject);
			}
		}
		else if (newObject instanceof FlowElement) {
			if (targetContainerObject instanceof Lane) {
				Lane ln = (Lane)targetContainerObject;
				targetContainerObject = getFlowElementsContainer(ln);
				// newObject could be either a Shape (FlowNode) or a Connection;
				// only add FlowNodes to the Lane's FlowNodeRefs list.
				if (newObject instanceof FlowNode)
					ln.getFlowNodeRefs().add((FlowNode)newObject);
			}
			if (targetContainerObject instanceof FlowElementsContainer) {
				((FlowElementsContainer)targetContainerObject).getFlowElements().add((FlowElement) newObject);
			}
		}
		else if (newObject instanceof Participant) {
			Participant participant = (Participant)newObject;
			if (((Participant) newObject).getProcessRef()!=null) {
				// need to create a new Process for this thing
				Process process = Bpmn2ModelerFactory.create(resource, Process.class);
				participant.setProcessRef(process);
			}
			if (targetContainerObject instanceof Collaboration) {
				Collaboration collab = (Collaboration)targetContainerObject;
				collab.getParticipants().add((Participant)newObject);
			}
		}

		// Ensure IDs are unique
		setId(newObject);

		TreeIterator<EObject> iter = newObject.eAllContents();
		while (iter.hasNext()) {
			EObject newChild = iter.next();
			setId(newChild);
		}

		for (EReference ref : newObject.eClass().getEAllReferences()) {
			if (!ref.isContainment()) {
				Object oldValue = oldObject.eGet(ref);
				// TODO: do we need this?
				// this mess also duplicates "incoming" and "outgoing" (for SequenceFlows)
				// which are already being handled in copyConnection()...
//				if (oldValue instanceof EObjectEList) {
//					EObjectEList oldList = (EObjectEList)oldObject.eGet(ref);
//					EObjectEList newList = (EObjectEList)newObject.eGet(ref);
//					for (Object oldRefObject : oldList) {
//						if (oldRefObject instanceof EObject) {
//							String oldId = getId((EObject)oldRefObject);
//							if (oldId!=null) {
//								String newId = idMap.get(oldId);
//								EObject newRefObject = findObjectById(newId);
//								newList.add(newRefObject);
//							}
//						}
//					}
//				}
//				else
				if (oldValue instanceof EObject){
					EObject oldRefObject = (EObject)oldValue;
					String oldId = getId(oldRefObject);
					if (oldId!=null) {
						String newId = idMap.get(oldId);
						if (newId!=null) {
							EObject newRefObject = findObjectById(newId);
							newObject.eSet(ref, newRefObject);
						}
						else if (newObject.eGet(ref) != null){
							EObject newRefObject = (EObject) newObject.eGet(ref);
							newId = getId(newRefObject);
							if (newId!=null)
								idMap.put(oldId, newId);
						}
					}
				}
			}
		}
		return newObject;
	}

	private String getId(EObject newObject) {
		EStructuralFeature feature = newObject.eClass().getEStructuralFeature("id");
		if (feature != null) {
			return (String) newObject.eGet(feature);
		}
		return null;
	}
	
	private String setId(EObject newObject) {
		String newId = null;
		String oldId = null;
		EStructuralFeature feature = newObject.eClass().getEStructuralFeature("id");
		if (feature != null) {
			oldId = (String) newObject.eGet(feature);
			if (idMap.contains(oldId)) {
				newId = idMap.get(oldId);
				newObject.eSet(feature, newId);
			}
			else {
				newObject.eUnset(feature);
				newId = ModelUtil.setID(newObject);
				idMap.put(oldId, newId);
			}
		}
		return oldId;
	}

	private EObject findObjectById(String id) {
		TreeIterator<EObject> iter = definitions.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			EStructuralFeature feature = o.eClass().getEStructuralFeature("id");
			if (feature != null) {
				String thisId = (String) o.eGet(feature);
				if (thisId != null && !thisId.isEmpty() && thisId.equals(id))
					return o;
			}
		}
		return null;
	}

	private ContainerShape findShape(EObject object) {
		List<PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(diagram, object);
		for (PictogramElement pe : pes) {
			if (pe instanceof ContainerShape)
				return (ContainerShape) pe;
		}
		return null;
	}

	private Connection findConnection(EObject object) {
		List<PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(diagram, object);
		for (PictogramElement pe : pes) {
			if (pe instanceof Connection)
				return (Connection) pe;
		}
		return null;
	}

	private BaseElement copyShape(ContainerShape oldShape, ContainerShape targetContainerShape, int x, int y) {
		if (shapeMap.get(oldShape)!=null)
			return null;
		
		BaseElement targetContainerObject = getContainerObject(targetContainerShape);
		BaseElement oldObject = BusinessObjectUtil.getFirstBaseElement(oldShape);
		BaseElement newObject = createNewObject(oldObject, targetContainerObject);

		AddContext ac = new AddContext(new AreaContext(), newObject);
		ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldShape);
		IDimension size = GraphicsUtil.calculateSize(oldShape);
		// The default Add BPMN Shape feature will position the new shape so its
		// center is at the target location; for copy/paste we want to use the
		// top-left corner instead so that copied connection bendpoints (if any)
		// line up properly.
		int deltaX = 0;
		int deltaY = 0;
		if (oldObject instanceof FlowNode) {
			deltaX = loc.getX() - xReference + size.getWidth() / 2;
			deltaY = loc.getY() - yReference + size.getHeight() / 2;
		}
		ac.setLocation(x + deltaX, y + deltaY);
		ac.setSize(size.getWidth(), size.getHeight());
		ac.setTargetContainer(targetContainerShape);

		IAddFeature af = getFeatureProvider().getAddFeature(ac);
		ContainerShape newShape = (ContainerShape) af.add(ac);

		shapeMap.put(oldShape, newShape);

		if (oldObject instanceof Participant) {
			// copy the contained Process elements
			oldObject = ((Participant)oldObject).getProcessRef();
		}

		// create shapes and connections for children if this is a FlowElementsContainer
		if (oldObject instanceof FlowElementsContainer) {
			TreeIterator<EObject> iter = oldObject.eAllContents();
			while (iter.hasNext()) {
				// look up the old child object that corresponds to the new child object 
				EObject oldChildObject = iter.next();
				if (oldChildObject instanceof BoundaryEvent) {
					// Defer Boundary Event creation until we're sure that the
					// new attachedToRef task is actually created.
					continue;
				}
				
				// if the old child has a Graphiti ContainerShape, duplicate it.
				ContainerShape oldChildShape = findShape(oldChildObject);
				if (oldChildShape != null) {
					copyShape(oldChildShape, newShape, 0, 0);
				}
			}
			
			iter = oldObject.eAllContents();
			while (iter.hasNext()) {
				// do the same for connections 
				EObject oldChildObject = iter.next();
				// if the old child has a Graphiti Connection, duplicate it.
				Connection oldChildConnection = findConnection(oldChildObject);
				if (oldChildConnection != null) {
					// the old BPMN2 object is a Connection, copy it
					copyConnection(oldChildConnection, newShape, x, y);
				}
			}
		}
		else if (oldObject instanceof Lane) {
	        List<PictogramElement> shapes = new ArrayList<PictogramElement>();
	        Lane oldLane = (Lane)oldObject;
	        if (oldLane.getChildLaneSet()!=null) {
	        	for (Lane oldChildLaneObject : oldLane.getChildLaneSet().getLanes()) {
	        		ContainerShape oldChildLaneShape = findShape(oldChildLaneObject);
	        		if (oldChildLaneShape != null) {
	        			copyShape(oldChildLaneShape, newShape, 0, 0);
	        		}
	        	}
	        }
			for (FlowNode oldChildObject : oldLane.getFlowNodeRefs()) {
				ContainerShape oldChildShape = findShape(oldChildObject);
				if (oldChildShape != null) {
					copyShape(oldChildShape, newShape, 0, 0);
					shapes.add(oldChildShape);
				}
			}
			List<Connection> connections = DefaultCopyBPMNElementFeature.findAllConnections(shapes);
			for (Connection oldChildConnection : connections) {
				copyConnection(oldChildConnection, newShape, x, y);
			}
		}
		
		// also copy the BPMNShape properties
		if (oldObject instanceof BaseElement) {
			BPMNShape oldBpmnShape = DIUtils.findBPMNShape((BaseElement)oldObject);
			if (oldBpmnShape!=null) {
				BPMNShape newBpmnShape = DIUtils.findBPMNShape((BaseElement)newObject);
				newBpmnShape.setIsExpanded(oldBpmnShape.isIsExpanded());
				newBpmnShape.setIsHorizontal(oldBpmnShape.isIsHorizontal());
				newBpmnShape.setIsMarkerVisible(oldBpmnShape.isIsMarkerVisible());
				newBpmnShape.setIsMessageVisible(oldBpmnShape.isIsMessageVisible());
				newBpmnShape.setParticipantBandKind(oldBpmnShape.getParticipantBandKind());
			}
		}

		UpdateContext uc = new UpdateContext(newShape);
		IUpdateFeature uf = getFeatureProvider().getUpdateFeature(uc);
		uf.update(uc);
		
		if (newObject instanceof Activity) {
			// copy the Activity's Boundary Events if it has any
			TreeIterator<EObject> i = definitions.eAllContents();
			while (i.hasNext()) {
				EObject o = i.next();
				if (o instanceof BoundaryEvent) {
					BoundaryEvent oldBeObject = (BoundaryEvent)o;
					if (oldBeObject.getAttachedToRef() == oldObject) {
						// here's one...
						ContainerShape oldBeShape = findShape(oldBeObject);
						copyShape(oldBeShape, targetContainerShape, x, y);
					}
				}
			}
		}

		return newObject;
	}

	private BaseElement copyConnection(Connection oldConnection, ContainerShape targetContainerShape, int x, int y) {
		if (connectionMap.get(oldConnection)!=null)
			return null;
		
		BaseElement targetContainerObject = getContainerObject(targetContainerShape);
		BaseElement oldObject = BusinessObjectUtil.getFirstBaseElement(oldConnection);
		BaseElement newObject = createNewObject(oldObject, targetContainerObject);

		Anchor oldStart = oldConnection.getStart();
		Anchor oldEnd = oldConnection.getEnd();
		ContainerShape newSource = shapeMap.get(oldStart.getParent());
		ContainerShape newTarget = shapeMap.get(oldEnd.getParent());
		if (newSource==null || newTarget==null) {
			// source or target does not exist yet - handle this connection later
			connectionMap.put(oldConnection, null);
			return null;
		}
		
		Anchor newStart;
		Anchor newEnd;
		if (AnchorUtil.isBoundaryAnchor(oldStart)) {
			AnchorLocation al = AnchorUtil.getBoundaryAnchorLocation(oldStart);
			Map<AnchorLocation, BoundaryAnchor> bas = AnchorUtil.getBoundaryAnchors(newSource);
			newStart = bas.get(al).anchor;
		}
		else {
			ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldStart);
			newStart = AnchorUtil.createAdHocAnchor(newSource, loc.getX(), loc.getY());
		}
		if (AnchorUtil.isBoundaryAnchor(oldEnd)) {
			AnchorLocation al = AnchorUtil.getBoundaryAnchorLocation(oldEnd);
			Map<AnchorLocation, BoundaryAnchor> bas = AnchorUtil.getBoundaryAnchors(newTarget);
			newEnd = bas.get(al).anchor;
		}
		else {
			ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldEnd);
			newEnd = AnchorUtil.createAdHocAnchor(newTarget, loc.getX(), loc.getY());
		}

		BaseElement newSourceObject = BusinessObjectUtil.getFirstBaseElement(newSource);
		BaseElement newTargetObject = BusinessObjectUtil.getFirstBaseElement(newTarget);
		if (newObject instanceof SequenceFlow) {
			((SequenceFlow) newObject).setSourceRef((FlowNode) newSourceObject);
			((SequenceFlow) newObject).setTargetRef((FlowNode) newTargetObject);
		}
		else if (newObject instanceof Association) {
			((Association) newObject).setSourceRef((FlowNode) newSourceObject);
			((Association) newObject).setTargetRef((FlowNode) newTargetObject);
		}
		else if (newObject instanceof MessageFlow) {
			((MessageFlow) newObject).setSourceRef((InteractionNode) newSourceObject);
			((MessageFlow) newObject).setTargetRef((InteractionNode) newTargetObject);
		}
		else if (newObject instanceof ConversationLink) {
			((ConversationLink) newObject).setSourceRef((InteractionNode) newSourceObject);
			((ConversationLink) newObject).setTargetRef((InteractionNode) newTargetObject);
		}
		AddConnectionContext acc = new AddConnectionContext(newStart, newEnd);
		acc.setNewObject(newObject);

		IAddFeature af = getFeatureProvider().getAddFeature(acc);
		Connection newConnection = (Connection) af.add(acc);
		connectionMap.put(oldConnection, newConnection);

		if (oldConnection instanceof FreeFormConnection && newConnection instanceof FreeFormConnection) {
			for (Point p : ((FreeFormConnection) oldConnection).getBendpoints()) {
				int deltaX = p.getX() - xReference;
				int deltaY = p.getY() - yReference;
				Point newPoint = GraphicsUtil.createPoint(x + deltaX, y + deltaY);
				((FreeFormConnection) newConnection).getBendpoints().add(newPoint);
			}
		}
		
		// also copy the BPMNShape properties
		if (oldObject instanceof BaseElement) {
			BPMNEdge oldBpmnEdge = DIUtils.findBPMNEdge((BaseElement)oldObject);
			if (oldBpmnEdge!=null) {
				BPMNEdge newBpmnEdge = DIUtils.findBPMNEdge((BaseElement)newObject);
				newBpmnEdge.setMessageVisibleKind(oldBpmnEdge.getMessageVisibleKind());
			}
		}

		FeatureSupport.updateConnection(getFeatureProvider(), newConnection);

		return newObject;
	}
	
	private ContainerShape getTargetContainerShape(IPasteContext context) {
		Diagram diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
		
		Point p = GraphicsUtil.createPoint(context.getX(), context.getY());
		for (Shape c : diagram.getChildren()) {
			if (GraphicsUtil.contains(c, p) && c instanceof ContainerShape) {
				BaseElement be = getContainerObject((ContainerShape) c);
				if (be instanceof FlowElementsContainer) {
					return (ContainerShape) c;
				}
				return null;
			}
		}
		return diagram;
	}
	
	private BaseElement getContainerObject(ContainerShape targetContainerShape) {
		EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(targetContainerShape);
		if (bo instanceof BPMNDiagram) {
			bo = ((BPMNDiagram) bo).getPlane().getBpmnElement();
		}
		if (bo instanceof Participant) {
			bo = ((Participant) bo).getProcessRef();
		}
		if (bo instanceof BaseElement)
			return (BaseElement) bo;
		return null;
	}
	
	private FlowElementsContainer getFlowElementsContainer(Lane lane) {
		EObject container = lane.eContainer();
		while (!(container instanceof FlowElementsContainer) && container!=null)
			container = container.eContainer();
		return (FlowElementsContainer)container;
	}
}
