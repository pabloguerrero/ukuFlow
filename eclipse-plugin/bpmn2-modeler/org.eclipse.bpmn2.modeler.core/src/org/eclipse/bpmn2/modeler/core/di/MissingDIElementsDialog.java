package org.eclipse.bpmn2.modeler.core.di;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

public class MissingDIElementsDialog extends CheckedTreeSelectionDialog {
	
	DiagramElementTree missing;
	
	public MissingDIElementsDialog(DiagramElementTree missing) {
		this(Display.getDefault().getActiveShell(), missing, missing);
		this.missing = missing;
		setTitle("BPMN2 Modeler - Missing Diagram Elements");
		setMessage(
				"These items do not have Diagram Elements and can not be shown in the editor.\n"+
				"Please select the items for which you would like to create Diagram Elements.");
		setInput(missing);
		setContainerMode(true);
		setInitialSelections(missing.getChildren().toArray());
	}

	private MissingDIElementsDialog(Shell parent,
			ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}

	@Override
    protected CheckboxTreeViewer createTreeViewer(Composite parent) {
		CheckboxTreeViewer treeViewer = super.createTreeViewer(parent);
		treeViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				DiagramElementTreeNode node = (DiagramElementTreeNode)event.getElement();
				node.setChecked(event.getChecked());
			}
			
		});
		return treeViewer;
    }
	
	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (id==IDialogConstants.DESELECT_ALL_ID || id==IDialogConstants.SELECT_ALL_ID) {
			final boolean checked = (id==IDialogConstants.SELECT_ALL_ID);
	        SelectionListener listener = new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
					missing.setChecked(checked);
	            }
	        };
	        button.addSelectionListener(listener);
		}
		return button;
	}

}
