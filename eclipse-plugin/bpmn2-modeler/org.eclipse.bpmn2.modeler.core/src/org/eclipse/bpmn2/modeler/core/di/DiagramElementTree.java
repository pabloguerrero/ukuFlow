package org.eclipse.bpmn2.modeler.core.di;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class DiagramElementTree extends DiagramElementTreeNode implements ILabelProvider, ITreeContentProvider {

	public DiagramElementTree(DiagramElementTreeNode parent, BaseElement element) {
		super(parent, element);
	}
	
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			return ((List)inputElement).toArray();
		}
		return getChildren().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof DiagramElementTreeNode) {
			return ((DiagramElementTreeNode)parentElement).getChildren().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof DiagramElementTreeNode) {
			return ((DiagramElementTreeNode)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DiagramElementTreeNode) {
			return ((DiagramElementTreeNode)element).hasChildren();
		}
		return super.hasChildren();
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		String text = "Unknown BPMN Element";
		if (element instanceof DiagramElementTreeNode) {
			BaseElement be = ((DiagramElementTreeNode)element).getBaseElement();
			text = be.eClass().getName() + ": " + ModelUtil.getDisplayName(be);
		}
		return text;
	}
	
}