package org.eclipse.bpmn2.modeler.core.di;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;

public class DiagramElementTreeNode {
	private static List<DiagramElementTreeNode> EMPTY = new ArrayList<DiagramElementTreeNode>();
	private DiagramElementTreeNode parent;
	private BaseElement baseElement;
	private List<DiagramElementTreeNode> children;
	private boolean checked = true;
	
	public DiagramElementTreeNode(DiagramElementTreeNode parent, BaseElement element) {
		this.parent = parent;
		this.baseElement = element;
	}
	
	public BaseElement getBaseElement() {
		return baseElement;
	}
	
	public void setBaseElement(BaseElement baseElement) {
		this.baseElement = baseElement;
	}
	
	public DiagramElementTreeNode getParent() {
		return parent;
	}
	
	public boolean getChecked() {
		return checked;
	}
	
	private void setParentChecked(boolean checked) {
		if (parent!=null) {
			if (!checked) {
				// grayed?
				if (parent.hasChildren()) {
					for (DiagramElementTreeNode child : parent.children) {
						if (child.getChecked()) {
							checked = true;
							break;
						}
					}
				}
				parent.checked = checked;
			}
			else
				parent.checked = true;
			parent.setParentChecked(checked);
		}
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
		if (hasChildren()) {
			for (DiagramElementTreeNode child : children) {
				child.setChecked(checked);
			}
		}
		setParentChecked(checked);
	}
	
	public DiagramElementTreeNode addChild(BaseElement element) {
		DiagramElementTreeNode child = getChild(element);
		if (child!=null)
			return child;
		
		if (children==null)
			children = new ArrayList<DiagramElementTreeNode>();
		DiagramElementTreeNode newElement = new DiagramElementTreeNode(this, element);
		children.add(newElement);
		return newElement;
	}
	
	public DiagramElementTreeNode getChild(BaseElement element) {
		if (hasChildren()) {
			for (DiagramElementTreeNode child : children) {
				if (child.getBaseElement() == element) {
					return child;
				}
			}
		}
		return null;
	}
	
	public void removeChild(BaseElement element) {
		if (hasChildren()) {
			for (DiagramElementTreeNode child : children) {
				if (child.getBaseElement() == element) {
					children.remove(child);
					break;
				}
			}
		}
	}
	
	public boolean hasChildren() {
		return children!=null && children.size()>0;
	}
	
	public List<DiagramElementTreeNode> getChildren() {
		if (hasChildren())
			return children;
		return EMPTY;
	}
}