package de.tudarmstadt.dvs.ukuflow.features.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

public class GenericDeleteFeature extends DefaultDeleteFeature {

	public GenericDeleteFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}
	/**
	 * askbeforedelete properties !
	@Override
	protected boolean getUserDecision(IDeleteContext context) {
		return super.getUserDecision();
		//return true;
	}*/
	@Override
	public void delete(IDeleteContext context) {
		deletePeEnvironment(context.getPictogramElement());
		super.delete(context);
	}
	public boolean canDelete(IDeleteContext context) {
		// don't delete the Diagram!
		if (context.getPictogramElement() instanceof Diagram)
			return false;
		return true;
	}
	
	@Override
	protected void deleteBusinessObject(Object bo) {
		List<PictogramElement> pictElements = Graphiti.getLinkService().getPictogramElements(getDiagram(), (EObject) bo);
		for (Iterator<PictogramElement> iterator = pictElements.iterator(); iterator.hasNext();) {
			PictogramElement pe = iterator.next();
			deletePeEnvironment(pe);
			Graphiti.getPeService().deletePictogramElement(pe);
		}
		super.deleteBusinessObject(bo);
	}
	
	protected void deletePeEnvironment(PictogramElement pictogramElement){
		if (pictogramElement instanceof ContainerShape) {
			ContainerShape cShape = (ContainerShape) pictogramElement;
			EList<Anchor> anchors = cShape.getAnchors();
			for (Anchor anchor : anchors) {
				deleteConnections(getFeatureProvider(), anchor.getIncomingConnections());
				deleteConnections(getFeatureProvider(), anchor.getOutgoingConnections());
			}
			deleteContainer(getFeatureProvider(), cShape);
		}
	}
	
	protected void deleteContainer(IFeatureProvider fp, ContainerShape cShape) {
		Object[] children = cShape.getChildren().toArray();
		for (Object shape : children) {
			if (shape instanceof ContainerShape) {
				DeleteContext context = new DeleteContext((PictogramElement) shape);
				fp.getDeleteFeature(context).delete(context);
			}
		}
	}

	protected void deleteConnections(IFeatureProvider fp, EList<Connection> connections) {
		List<Connection> con = new ArrayList<Connection>();
		con.addAll(connections);
		for (Connection connection : con) {
			IDeleteContext conDelete = new DeleteContext(connection);
			fp.getDeleteFeature(conDelete).delete(conDelete);
		}
	}
}
