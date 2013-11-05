package org.eclipse.bpmn2.modeler.core.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.bpmn2.Artifact;
import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.ConversationNode;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataObjectReference;
import org.eclipse.bpmn2.DataStore;
import org.eclipse.bpmn2.DataStoreReference;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.BpmnDiFactory;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.ShapeLayoutManager;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.Size;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcFactory;
import org.eclipse.dd.dc.Point;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.window.Window;

public class DIGenerator {

	private DIImport importer;
	private Diagram diagram;
	private BPMNDiagram bpmnDiagram;
	private DiagramEditor editor;
	private Definitions definitions;
	private HashMap<BaseElement, PictogramElement> elements;
	private ImportDiagnostics diagnostics;
	private DiagramElementTree missingElements;
	
	public DIGenerator(DIImport importer) {
		this.importer = importer;
		elements = importer.getImportedElements();
		diagnostics = importer.getDiagnostics();
		editor = importer.getEditor();
		diagram = editor.getDiagramTypeProvider().getDiagram();
		bpmnDiagram = BusinessObjectUtil.getFirstElementOfType(diagram, BPMNDiagram.class);
		definitions = ModelUtil.getDefinitions(bpmnDiagram);
	}
	
	public boolean hasMissingDIElements() {
		if (missingElements==null)
			missingElements = findMissingDIElements();
		return missingElements.hasChildren();
	}
	
	public void generateMissingDIElements() {
		if (hasMissingDIElements()) {
			// Display a dialog of the missing elements and allow user
			// to choose which ones to create
			MissingDIElementsDialog dlg = new MissingDIElementsDialog(missingElements);
			if (dlg.open()==Window.OK) {
				createMissingDIElements(missingElements);
				
				ShapeLayoutManager layoutManager = new ShapeLayoutManager(editor);
				for (DiagramElementTreeNode node : missingElements.getChildren()) {
					if (node.getChecked()) {
						layoutManager.layout(node.getBaseElement());
					}
				}
			}
		}
	}
	
	private DiagramElementTree findMissingDIElements() {
		
		DiagramElementTree missing = new DiagramElementTree(null,null);
		
		// look for any BPMN2 elements that do not have corresponding DI elements
		for (BaseElement be : definitions.getRootElements()) {
			findMissingDIElements(missing, be);
		}
		return missing;
	}
	
	private boolean isMissingDIElement(BaseElement be) {
		// ignore DataObjects and DataStores - there are bound to be references
		// to these, which *should* be rendered
		if (be instanceof DataObject || be instanceof DataStore)
			return false;
		BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(be);
		if (bpmnDiagram!=null)
			return false;
		return elements.get(be) == null && diagnostics.get(be) == null;
	}
	
	private int findMissingDIElements(DiagramElementTreeNode missing, LaneSet laneSet, List<FlowElement> laneElements) {
		int added = 0;
		if (laneSet!=null) {
			for (Lane lane : laneSet.getLanes()) {
				// create the missing tree node for this Lane's container
				// this is either a FlowElementsContainer or another Lane
				BaseElement container = (BaseElement) lane.eContainer().eContainer();
				DiagramElementTreeNode containerNode = missing.getChild(container);
				if (containerNode==null)
					containerNode = missing.addChild(container);
				DiagramElementTreeNode parentNode = containerNode.addChild(lane);
				
				for (FlowNode fn : lane.getFlowNodeRefs()) {
					if (isMissingDIElement(fn)) {
						parentNode.addChild(fn);
						laneElements.add(fn);
						++added;
					}
				}
				added += findMissingDIElements(parentNode, lane.getChildLaneSet(), laneElements);
				
				if (added==0) {
					containerNode.removeChild(lane);
					missing.removeChild(container);
				}
			}
		}
		return added;
	}
	
	private void findMissingDIElements(DiagramElementTreeNode missing, BaseElement be) {
		if (be instanceof FlowElementsContainer) {
			FlowElementsContainer container = (FlowElementsContainer)be;
			DiagramElementTreeNode parentNode = null;
			
			List<FlowElement> laneElements = new ArrayList<FlowElement>();
			for (LaneSet laneSet : container.getLaneSets()) {
				findMissingDIElements(missing, laneSet, laneElements);
			}
			
			for (FlowElement fe : container.getFlowElements()) {
				if (isMissingDIElement(fe) && !laneElements.contains(fe)) {
					if (!(fe instanceof SequenceFlow)) {
						if (parentNode==null)
							parentNode = missing.addChild(container);
						parentNode.addChild(fe);
						if (fe instanceof FlowElementsContainer) {
							findMissingDIElements(parentNode, fe);
						}
					}
				}
			}
			List<Artifact> artifacts = getArtifacts(container);
			if (artifacts!=null) {
				for (Artifact a : artifacts) {
					if (isMissingDIElement(a) && !(a instanceof Association)) {
						if (parentNode==null)
							parentNode = missing.addChild(container);
						parentNode.addChild(a);
					}
				}
			}
		}
		else if (be instanceof Collaboration) {
			Collaboration container = (Collaboration)be;
			DiagramElementTreeNode parentNode = null;
			for (Artifact a : container.getArtifacts()) {
				if (isMissingDIElement(a) && !(a instanceof Association)) {
					if (parentNode==null)
						parentNode = missing.addChild(container);
					parentNode.addChild(a);
				}
			}
			for (Participant p : container.getParticipants()) {
				if (isMissingDIElement(p)) {
					if (p.getProcessRef()==null) {
						if (parentNode==null)
							parentNode = missing.addChild(container);
						parentNode.addChild(p);
					}
				}
			}
			for (ConversationNode c : container.getConversations()) {
				if (isMissingDIElement(c)) {
					if (parentNode==null)
						parentNode = missing.addChild(container);
					parentNode.addChild(c);
				}
			}
		}
		else if (be instanceof DataStore) {
			if (isMissingDIElement(be)) {
				missing.addChild(be);
			}
		}
	}
	
	private List<Artifact> getArtifacts(BaseElement container) {
		if (container instanceof Process) {
			return ((Process)container).getArtifacts();
		}
		if (container instanceof SubProcess) {
			return ((SubProcess)container).getArtifacts();
		}
		if (container instanceof SubChoreography) {
			return ((SubChoreography)container).getArtifacts();
		}
		if (container instanceof Collaboration) {
			return ((Collaboration)container).getArtifacts();
		}
		return null;
	}

	private FlowElementsContainer getRootElementContainer(EObject o) {
		while (o!=null) {
			if (o instanceof FlowElementsContainer && o instanceof RootElement) {
				return (FlowElementsContainer)o;
			}
			o = o.eContainer();
		}
		return null;
	}
	
	private BPMNShape createMissingDIElement(DiagramElementTreeNode node, int x, int y, List<BaseElement> created) {
		BaseElement element = node.getBaseElement();
		BPMNShape bpmnShape = null;
		BPMNDiagram bpmnDiagram = createDIDiagram(element);
		
		if (element instanceof Lane) {
			Lane lane = (Lane)element;
			bpmnShape = createDIShape(bpmnDiagram, lane, x, y);

			for (DiagramElementTreeNode childNode : node.getChildren()) {
				if (childNode.getChecked()) {
					BPMNShape shape = createMissingDIElement(childNode, x, y, created);
					if (shape!=null) {
						y += shape.getBounds().getHeight() + 10;
					}
				}
			}
			created.add(lane);
		}
		else if (element instanceof FlowElementsContainer) {
			FlowElementsContainer container = (FlowElementsContainer)element;

			for (DiagramElementTreeNode childNode : node.getChildren()) {
				if (childNode.getChecked()) {
					BPMNShape shape = createMissingDIElement(childNode, x, y, created);
					if (shape!=null) {
						y += shape.getBounds().getHeight() + 10;
					}
				}
			}
			
			if (!(container instanceof RootElement)) {
				// This can only be either a SubChoreography or SubProcess.
				created.add(container);
			}			
		}
		else if (element instanceof Collaboration) {
			for (DiagramElementTreeNode childNode : node.getChildren()) {
				if (childNode.getChecked()) {
					BPMNShape shape = createMissingDIElement(childNode, x, y, created);
					if (shape!=null) {
						y += shape.getBounds().getHeight() + 10;
					}
				}
			}
		}
		else if (element instanceof Artifact) {
			Artifact participant = (Artifact)element;
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof Participant) {
			Participant participant = (Participant)element;
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof ConversationNode) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof FlowNode) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof DataObject) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof DataObjectReference) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof DataStore) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		else if (element instanceof DataStoreReference) {
			bpmnShape = createDIShape(bpmnDiagram, element, x, y);
			created.add(element);
		}
		return bpmnShape;
	}
	
	private void createMissingDIElements(DiagramElementTree missing) {

		// look for any BPMN2 elements that do not have corresponding DI elements
		// and create DI elements for them. First, handle the BPMNShape objects:
		int x = 102400;
		int y = 0;
		List<BaseElement> shapes = new ArrayList<BaseElement>();
		for (DiagramElementTreeNode node : missing.getChildren()) {
			if (node.getChecked()) {
				BPMNShape shape = createMissingDIElement(node, x, y, shapes);
				if (shape!=null) {
					y += shape.getBounds().getHeight() + 10;
				}
			}
		}
		
		// Next create the BPMNEdge objects. At this point, all of the source
		// and target elements for the connections should already exist, so
		// we don't have to worry about that.
		List<BaseElement> connections = new ArrayList<BaseElement>();
		for (BaseElement be : shapes) {
			if (be instanceof FlowNode) {
				FlowNode flowNode = (FlowNode)be;
				// find the BPMNDiagram that contains this flow node
				BPMNDiagram bpmnDiagram = createDIDiagram(flowNode);

				for (SequenceFlow sf : flowNode.getIncoming()) {
					if (!connections.contains(sf)) {
						BPMNEdge bpmnEdge = createDIEdge(bpmnDiagram, sf);
						if (bpmnEdge!=null)
							connections.add(sf);
					}
				}

				for (SequenceFlow sf : flowNode.getOutgoing()) {
					if (!connections.contains(sf)) {
						BPMNEdge bpmnEdge = createDIEdge(bpmnDiagram, sf);
						if (bpmnEdge!=null)
							connections.add(sf);
					}
				}
			}
			else if (be instanceof ConversationNode) {
				ConversationNode convNode = (ConversationNode)be;
				BPMNDiagram bpmnDiagram = createDIDiagram(convNode);
				for (MessageFlow mf : convNode.getMessageFlowRefs()) {
					if (!connections.contains(mf)) {
						BPMNEdge bpmnEdge = createDIEdge(bpmnDiagram, mf);
						if (bpmnEdge!=null)
							connections.add(mf);
					}
				}
			}
		}
		// Finally, Associations are RootElements and since we only include shapes
		// in the missing elements tree, we'll have to revisit all of the RootElements
		TreeIterator<EObject> iter = definitions.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			if (o instanceof Association) {
				Association assoc = (Association)o;
				BPMNDiagram bpmnDiagram = createDIDiagram(assoc);
				BPMNEdge bpmnEdge = createDIEdge(bpmnDiagram, assoc);
				if (bpmnEdge!=null)
					connections.add(assoc);
			}
		}
	}
	
	// TODO: can these be merged into DIUtils?
	
	private BPMNDiagram createDIDiagram(BaseElement bpmnElement) {

		BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(bpmnElement, true);
	
		// if this container does not have a BPMNDiagram, create one
		if (bpmnDiagram==null) {
			FlowElementsContainer container = getRootElementContainer(bpmnElement);
			if (container==null) {
				DIUtils.findBPMNDiagram(bpmnElement, true);
				diagnostics.add(IStatus.ERROR, bpmnElement, "Cannot find Diagram");
				return this.bpmnDiagram;
			}
			BPMNPlane plane = BpmnDiFactory.eINSTANCE.createBPMNPlane();
			plane.setBpmnElement(container);

			bpmnDiagram = BpmnDiFactory.eINSTANCE.createBPMNDiagram();
			bpmnDiagram.setName(container.getId());
			bpmnDiagram.setPlane(plane);

			definitions.getDiagrams().add(bpmnDiagram);
		}

		return bpmnDiagram;
	}
	
	private BPMNShape createDIShape(BPMNDiagram bpmnDiagram, BaseElement bpmnElement, float x, float y) {
		
		BPMNPlane plane = bpmnDiagram.getPlane();
		BPMNShape bpmnShape = null;
		for (DiagramElement de : plane.getPlaneElement()) {
			if (de instanceof BPMNShape) {
				if (bpmnElement == ((BPMNShape)de).getBpmnElement()) {
					bpmnShape = (BPMNShape)de;
					break;
				}
			}
		}
		
		if (bpmnShape==null) {
			bpmnShape = BpmnDiFactory.eINSTANCE.createBPMNShape();
			bpmnShape.setBpmnElement(bpmnElement);
			Bounds bounds = DcFactory.eINSTANCE.createBounds();
			bounds.setX(x);
			bounds.setY(y);
			Size size = GraphicsUtil.getShapeSize(bpmnElement, diagram);
			bounds.setWidth(size.getWidth());
			bounds.setHeight(size.getHeight());
			bpmnShape.setBounds(bounds);
			plane.getPlaneElement().add(bpmnShape);
			Bpmn2Preferences.getInstance(bpmnDiagram.eResource()).applyBPMNDIDefaults(bpmnShape, null);

			ModelUtil.setID(bpmnShape);
			importer.importShape(bpmnShape);
		}
		
		return bpmnShape;
	}
	
	private BPMNEdge createDIEdge(BPMNDiagram bpmnDiagram, BaseElement bpmnElement) {
		BPMNPlane plane = bpmnDiagram.getPlane();
		BPMNEdge bpmnEdge = null;
		for (DiagramElement de : plane.getPlaneElement()) {
			if (de instanceof BPMNEdge) {
				if (bpmnElement == ((BPMNEdge)de).getBpmnElement()) {
					bpmnEdge = (BPMNEdge)de;
					break;
				}
			}
		}

		if (bpmnEdge==null) {
			bpmnEdge = BpmnDiFactory.eINSTANCE.createBPMNEdge();
			bpmnEdge.setBpmnElement(bpmnElement);
	
			BaseElement sourceElement = null;
			BaseElement targetElement = null;
			if (bpmnElement instanceof SequenceFlow) {
				sourceElement = ((SequenceFlow)bpmnElement).getSourceRef();
				targetElement = ((SequenceFlow)bpmnElement).getTargetRef();
			}
			else if (bpmnElement instanceof MessageFlow) {
				sourceElement = (BaseElement) ((MessageFlow)bpmnElement).getSourceRef();
				targetElement = (BaseElement) ((MessageFlow)bpmnElement).getTargetRef();
			}
			else if (bpmnElement instanceof Association) {
				sourceElement = ((Association)bpmnElement).getSourceRef();
				targetElement = ((Association)bpmnElement).getTargetRef();
			}
			
			if (sourceElement!=null && targetElement!=null) {
				DiagramElement de;
				de = DIUtils.findPlaneElement(plane.getPlaneElement(), sourceElement);
				bpmnEdge.setSourceElement(de);
				
				de = DIUtils.findPlaneElement(plane.getPlaneElement(), targetElement);
				bpmnEdge.setTargetElement(de);
				
				// the source and target elements should already have been created:
				// we know the PictogramElements for these can be found in our elements map
				Shape sourceShape = (Shape)elements.get(sourceElement);
				Shape targetShape = (Shape)elements.get(targetElement);
				if (sourceShape!=null && targetShape!=null) {
					Tuple<FixPointAnchor,FixPointAnchor> anchors =
							AnchorUtil.getSourceAndTargetBoundaryAnchors(sourceShape, targetShape, null);
					org.eclipse.graphiti.mm.algorithms.styles.Point sourceLoc = GraphicsUtil.createPoint(anchors.getFirst());
					org.eclipse.graphiti.mm.algorithms.styles.Point targetLoc = GraphicsUtil.createPoint(anchors.getSecond());
					Point point = DcFactory.eINSTANCE.createPoint();
					point.setX(sourceLoc.getX());
					point.setY(sourceLoc.getY());
					bpmnEdge.getWaypoint().add(point);
			
					point = DcFactory.eINSTANCE.createPoint();
					point.setX(targetLoc.getX());
					point.setY(targetLoc.getY());
					bpmnEdge.getWaypoint().add(point);
					
					plane.getPlaneElement().add(bpmnEdge);
					
					ModelUtil.setID(bpmnEdge);
					importer.importConnection(bpmnEdge);
				}
			}
		}
		
		return bpmnEdge;
	}
}
