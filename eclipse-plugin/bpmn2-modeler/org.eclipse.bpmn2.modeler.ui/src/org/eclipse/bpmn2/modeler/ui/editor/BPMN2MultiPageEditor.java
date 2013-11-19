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

package org.eclipse.bpmn2.modeler.ui.editor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.Bpmn2DiagramEditorInput;
import org.eclipse.core.internal.resources.Container;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.internal.WorkbenchMessages;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

/**
 * This class implements a multi-page version of the BPMN2 Modeler (BPMN2Editor
 * class). To revert back to the original, single-page version simply change the
 * editor extension point in plugin.xml (see comments there).
 * 
 * This is still in the experimental phase and currently only supports a single
 * diagram per .bpmn file. An optional second page, which displays the XML
 * source, can be created from the context menu. The source view is not yet
 * synchronized to the design view and can only show the XML as of the last
 * "Save" i.e. the current state of the file on disk, not the in-memory model.
 * Design/Source view synchronization will be implemented in a future version,
 * but direct editing of the XML will not be supported - it will remain
 * "view only".
 * 
 * Future versions will support multiple diagrams per .bpmn file with the
 * ability to add and remove pages containing different diagram types. It should
 * be possible for the user to create a single file that contains a mix of
 * Process, Collaboration and Choreography diagrams. Whether or not these types
 * of files are actually deployable and/or executable is another story ;)
 */
public class BPMN2MultiPageEditor extends MultiPageEditorPart implements
		IGotoMarker {

	DesignEditor designEditor;
	SourceViewer sourceViewer;
	private CTabFolder tabFolder;
	private int defaultTabHeight;
	private List<BPMNDiagram> bpmnDiagrams = new ArrayList<BPMNDiagram>();
	// HIENADD: additional attributes:
	Map<EditorPart, Tupel> mapping = new HashMap<EditorPart, Tupel>();
	// providerID is the id for event viewer editor. so that Graphiti knows how
	// to open it.
	private static String ProviderID = "ukuFlowEvent";
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	// HIENDONE
	public BPMN2MultiPageEditor() {
		super();
	}

	@Override
	protected IEditorSite createSite(IEditorPart editor) {
		if (editor instanceof DesignEditor)
			return new DesignEditorSite(this, editor);
		return new MultiPageEditorSite(this, editor);
	}

	@Override
	public String getTitle() {
		if (designEditor != null)
			return designEditor.getTitle();
		return super.getTitle();
	}

	@Override
	public String getPartName() {
		if (designEditor != null)
			return designEditor.getPartName();
		return super.getPartName();
	}

	/**
	 * Method declared on IEditorPart.
	 * 
	 * @param marker
	 *            Marker to look for
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		if (getActivePage() < 0) {
			setActivePage(0);
		}
		IDE.gotoMarker(getEditor(getActivePage()), marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		tabFolder = (CTabFolder) getContainer();
		tabFolder.addCTabFolder2Listener(new CTabFolder2Listener() {

			@Override
			public void close(CTabFolderEvent event) {
				// System.out.println("Convert phase should be here");
				
				if (event.item.getData() == sourceViewer)
					removeSourceViewer();
				else if (event.item.getData() instanceof DiagramEditor) {
					// TODO: checking modification:
					String MESSAGE = "Resource has been modified. Save changes?";
					int choice = -1;
					DiagramEditor editor = (DiagramEditor) event.item.getData();
					if (editor.isDirty()) {
						// ASK FOR SAVE:
						Shell shell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						String[] buttons = new String[] {
								IDialogConstants.YES_LABEL,
								IDialogConstants.NO_LABEL
								,IDialogConstants.CANCEL_LABEL 
								};
						MessageDialog d = new MessageDialog(shell,
								WorkbenchMessages.Save_Resource, null, MESSAGE,
								MessageDialog.QUESTION, buttons, 0) {
							protected int getShellStyle() {
								return super.getShellStyle() | SWT.SHEET;
							}
						};
						choice = d.open();
						switch (choice) {
						case ISaveablePart2.YES: // yes
							System.out.println("yes");
							editor.doSave(new NullProgressMonitor());
							break;
						case ISaveablePart2.NO: // no
							System.out.println("no");
							break;
						default:
						case ISaveablePart2.CANCEL: // cancel
							System.out.println("cancel");
							event.doit = false;
							return;
						}
					}
					removeEventViewer(editor);
				}
			}

			@Override
			public void minimize(CTabFolderEvent event) {
			}

			@Override
			public void maximize(CTabFolderEvent event) {
			}

			@Override
			public void restore(CTabFolderEvent event) {
			}

			@Override
			public void showList(CTabFolderEvent event) {
			}

		});
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int pageIndex = tabFolder.getSelectionIndex();
				if (pageIndex >= 0 && pageIndex < bpmnDiagrams.size()
						&& designEditor != null) {
					BPMNDiagram bpmnDiagram = bpmnDiagrams.get(pageIndex);
					designEditor.selectBpmnDiagram(bpmnDiagram);
				}
			}
		});

		// defer editor layout until all pages have been created
		tabFolder.setLayoutDeferred(true);

		createDesignEditor();

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				setActivePage(0);
				designEditor.selectBpmnDiagram(bpmnDiagrams.get(0));
				tabFolder.setLayoutDeferred(false);
				tabFolder.setTabPosition(SWT.TOP);
				updateTabs();
			}
		});
	}

	protected void createDesignEditor() {
		if (designEditor == null) {
			designEditor = new DesignEditor(this, this);

			try {
				int pageIndex = tabFolder.getItemCount();
				if (sourceViewer != null)
					--pageIndex;
				addPage(pageIndex, designEditor,
						BPMN2MultiPageEditor.this.getEditorInput());
				defaultTabHeight = tabFolder.getTabHeight();
				setPageText(pageIndex,
						ModelUtil.getDiagramTypeName(designEditor
								.getBpmnDiagram()));

				defaultTabHeight = tabFolder.getTabHeight();

				updateTabs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public DesignEditor getDesignEditor() {
		return designEditor;
	}

	protected void addDesignPage(final BPMNDiagram bpmnDiagram) {
		createDesignEditor();
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {

					int pageIndex = tabFolder.getItemCount();
					if (sourceViewer != null)
						--pageIndex;
					Bpmn2DiagramEditorInput input = (Bpmn2DiagramEditorInput) designEditor
							.getEditorInput();
					input.setBpmnDiagram(bpmnDiagram);
					addPage(pageIndex, designEditor, input);
					CTabItem oldItem = tabFolder.getItem(pageIndex - 1);
					CTabItem newItem = tabFolder.getItem(pageIndex);
					newItem.setControl(oldItem.getControl());
					setPageText(pageIndex, bpmnDiagram.getName());

					setActivePage(pageIndex);
					updateTabs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void showDesignPage(final BPMNDiagram bpmnDiagram) {
		final int pageIndex = bpmnDiagrams.indexOf(bpmnDiagram);
		if (pageIndex >= 0) {
			if (getDesignEditor().getBpmnDiagram() != bpmnDiagram) {
				setActivePage(pageIndex);
			}
		} else {
			designEditor.showDesignPage(bpmnDiagram);
		}
	}

	protected void removeDesignPage(final BPMNDiagram bpmnDiagram) {
		final int pageIndex = bpmnDiagrams.indexOf(bpmnDiagram);
		if (pageIndex > 0) {
			// go back to "Design" page - the only page that can't be removed
			Display.getCurrent().asyncExec(new Runnable() {
				@Override
				public void run() {
					setActivePage(0);

					IEditorPart editor = getEditor(pageIndex);
					if (editor instanceof DesignEditor) {
						((DesignEditor) editor).deleteBpmnDiagram(bpmnDiagram);
					}

					// need to manage this ourselves so that the CTabFolder
					// doesn't
					// dispose our editor site (a child of the CTabItem.control)
					tabFolder.getItem(pageIndex).setControl(null);

					removePage(pageIndex);

					tabFolder.getSelection().getControl().setVisible(true);
				}
			});
		}
	}

	public int getDesignPageCount() {
		int count = getPageCount();
		if (sourceViewer != null)
			--count;
		return count;
	}

	protected void createSourceViewer() {
		if (sourceViewer == null) {
			sourceViewer = new SourceViewer(this);

			try {
				int pageIndex = tabFolder.getItemCount();
				FileEditorInput input = new FileEditorInput(
						designEditor.getModelFile());
				addPage(pageIndex, sourceViewer, input);
				tabFolder.getItem(pageIndex).setShowClose(true);

				setPageText(pageIndex, "Source");
				setActivePage(pageIndex);

				updateTabs();
			} catch (Exception e) {
				e.printStackTrace();
				if (sourceViewer != null)
					sourceViewer.dispose();
			}
		}
	}

	public SourceViewer getSourceViewer() {
		return sourceViewer;
	}

	public void removeSourceViewer() {
		// there will only be one source page and it will always be the last
		// page in the tab folder
		if (sourceViewer != null) {
			for(EditorPart ed : mapping.keySet()){
				if(ed.equals(sourceViewer)){
					removePage(mapping.get(ed).index);
					mapping.remove(ed);
					sourceViewer = null;
					break;
				}
				
			}			
		}
	}

	public void addPage(int pageIndex, IEditorPart editor, IEditorInput input)
			throws PartInitException {
		super.addPage(pageIndex, editor, input);
		if (editor instanceof DesignEditor) {
			bpmnDiagrams.add(pageIndex,
					((DesignEditor) editor).getBpmnDiagram());
		}
		String id = null;
		if(editor instanceof EventViewer){
			id = ((EventViewer)editor).id;
		}
		mapping.put((EditorPart) editor, new Tupel(pageIndex, id));
	}

	@Override
	public void removePage(int pageIndex) {		
		Object page = tabFolder.getItem(pageIndex).getData();
		super.removePage(pageIndex);
		updateTabs();
		if (page instanceof DesignEditor) {
			bpmnDiagrams.remove(pageIndex);
		}
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);

		IEditorPart editor = getEditor(newPageIndex);
		if (editor instanceof DesignEditor) {
			BPMNDiagram bpmnDiagram = bpmnDiagrams.get(newPageIndex);
			((DesignEditor) editor).pageChange(bpmnDiagram);
			// Diagram diagram = DIUtils.findDiagram(designEditor, bpmnDiagram);
			// if (diagram != null)
			// designEditor.selectPictogramElements(new PictogramElement[]
			// {(PictogramElement)diagram});
		}
	}

	public int getPageCount() {
		return tabFolder.getItemCount();
	}

	public CTabItem getTabItem(int pageIndex) {
		return tabFolder.getItem(pageIndex);
	}

	public BPMNDiagram getBpmnDiagram(int i) {
		if (i >= 0 && i < bpmnDiagrams.size()) {
			return bpmnDiagrams.get(i);
		}
		return null;
	}

	private void updateTabs() {
		if (!tabFolder.getLayoutDeferred()) {
			if (tabFolder.getItemCount() == 1) {
				tabFolder.setTabHeight(0);
			} else
				tabFolder.setTabHeight(defaultTabHeight);
		}
		tabFolder.layout();
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		IEditorPart editor = getActiveEditor();
		if (editor instanceof DesignEditor){
			for(EditorPart ep :mapping.keySet()){
				ep.doSave(monitor);
			}
			designEditor.doSave(monitor);
		}
		else {
			System.out.println("converting & saving event diagram ...");
			editor.doSave(monitor);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		IEditorPart activeEditor = getActiveEditor();
		activeEditor.doSaveAs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {

		/*
		 * Depending upon the active page in multipage editor, call the
		 * saveAsAllowed. It helps to see whether a particular editor allows
		 * 'save as' feature
		 */
		IEditorPart activeEditor = getActiveEditor();
		if (activeEditor != null)
			return activeEditor.isSaveAsAllowed();
		return false;
	}

	@Override
	public void dispose() {
		designEditor.dispose();
		for(EditorPart editor : mapping.keySet()){
			if(editor != null && !(editor instanceof DesignEditor))
				editor.dispose();
		}
	}


	@Override
	public IEditorPart getActiveEditor() {
		return super.getActiveEditor();
	}

	public void createEventViewer(ReceiveTask task, String nid, String text,EEventBaseScript ebs) {
		boolean recreteDiagram = false;
		EventViewer eventView = null;
		for (EditorPart ed : mapping.keySet()) {
			if (ed instanceof EventViewer) {
				if (((EventViewer) ed).id.equals(nid)) {
					setActivePage(((EventViewer) ed).index);
					return;
				}
			}
		}
		try {
			int pageIndex = tabFolder.getItemCount();
			IContainer folder = designEditor.getModelFile().getParent();
			
			IFile target = null;
			try {
				if (folder instanceof Container){
					target = ((Container) folder).getFile(nid + ".evb");
					target = ResourcesPlugin.getWorkspace().getRoot().getFile(folder.getFullPath().addTrailingSeparator().append(nid+".evb"));
				}
				else 
					log.error("cannot get file '"+nid+".evb' from folder " + folder);
				/*
				 * if (folder instanceof IFolder) { target = ((IFolder)
				 * folder).getFile(nid + ".evb"); } else if (folder instanceof
				 * IProject){ target = ((IProject)folder).getFile(nid+".evb"); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (target.exists()) {
				log.debug("target exists -> check matching and openfile?");
				// check if matched & open file
				// TODO:
			} else
			{
				recreteDiagram = true;
				log.debug("create file " + nid+".evb and try to open it");
				// create & convert!
				final Diagram diagram = Graphiti.getPeCreateService()
						.createDiagram(ProviderID, nid + ".evb", true);				
				URI uri = URI.createPlatformResourceURI(target.getFullPath().toString(), true);
				// FileService
				@SuppressWarnings("restriction")
				final TransactionalEditingDomain editingDomain = GraphitiUiInternal
						.getEmfService().createResourceSetAndEditingDomain();
				final ResourceSet resourceSet = editingDomain.getResourceSet();
				final Resource resource = resourceSet.createResource(uri);
				final CommandStack cmdStack = editingDomain.getCommandStack();
				cmdStack.execute(new RecordingCommand(editingDomain) {

					@Override
					protected void doExecute() {
						resource.setTrackingModification(true);
						resource.getContents().add(diagram);

					}
				});
				save(editingDomain,
						Collections.<Resource, Map<?, ?>> emptyMap());
				editingDomain.dispose();
			}
			// FileEditorInput input = new FileEditorInput();
			String id = GraphitiUi.getExtensionManager().getDiagramTypeProviderId(ProviderID);
			DiagramEditorInput input = new DiagramEditorInput(
					// This is, folks, it works :D
					//URI.createFileURI(target.getLocation().toOSString()),
					//URI.createFileURI(target.getFullPath().toOSString()),
					URI.createPlatformResourceURI(target.getFullPath().toString(), true),
					id);

			eventView = new EventViewer(task, nid, pageIndex,recreteDiagram,ebs);//TODO
			addPage(pageIndex, eventView, input);
			tabFolder.getItem(pageIndex).setShowClose(true);

			setPageText(pageIndex, nid);
			setActivePage(pageIndex);

			updateTabs();
		} catch (Exception e) {
			e.printStackTrace();
			if (eventView != null)
				eventView.dispose();
		}
	}
	private static void save(TransactionalEditingDomain editingDomain, Map<Resource, Map<?, ?>> options) {
		saveInWorkspaceRunnable(editingDomain, options);
	}

	private static void saveInWorkspaceRunnable(final TransactionalEditingDomain editingDomain,
			final Map<Resource, Map<?, ?>> options) {

		final Map<URI, Throwable> failedSaves = new HashMap<URI, Throwable>();
		final IWorkspaceRunnable wsRunnable = new IWorkspaceRunnable() {
			@Override
			public void run(final IProgressMonitor monitor) throws CoreException {

				final Runnable runnable = new Runnable() {

					@Override
					public void run() {
						Transaction parentTx;
						if (editingDomain != null
								&& (parentTx = ((TransactionalEditingDomainImpl) editingDomain).getActiveTransaction()) != null) {
							do {
								if (!parentTx.isReadOnly()) {
									throw new IllegalStateException(
											"FileService.save() called from within a command (likely produces a deadlock)"); //$NON-NLS-1$
								}
							} while ((parentTx = ((TransactionalEditingDomainImpl) editingDomain)
									.getActiveTransaction().getParent()) != null);
						}

						final EList<Resource> resources = editingDomain.getResourceSet().getResources();
						// Copy list to an array to prevent
						// ConcurrentModificationExceptions
						// during the saving of the dirty resources
						Resource[] resourcesArray = new Resource[resources.size()];
						resourcesArray = resources.toArray(resourcesArray);
						final Set<Resource> savedResources = new HashSet<Resource>();
						for (int i = 0; i < resourcesArray.length; i++) {
							// In case resource modification tracking is
							// switched on, we can check if a resource
							// has been modified, so that we only need to same
							// really changed resources; otherwise
							// we need to save all resources in the set
							final Resource resource = resourcesArray[i];
							if (resource.isModified()) {
								try {
									resource.save(options.get(resource));
									savedResources.add(resource);
								} catch (final Throwable t) {
									failedSaves.put(resource.getURI(), t);
								}
							}
						}
					}
				};

				try {
					editingDomain.runExclusive(runnable);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
				editingDomain.getCommandStack().flush();
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(wsRunnable, null);
			if (!failedSaves.isEmpty()) {
				throw new WrappedException(createMessage(failedSaves), new RuntimeException());
			}
		} catch (final CoreException e) {
			final Throwable cause = e.getStatus().getException();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			throw new RuntimeException(e);
		}
	}

	private static String createMessage(Map<URI, Throwable> failedSaves) {
		final StringBuilder buf = new StringBuilder("The following resources could not be saved:");
		for (final Entry<URI, Throwable> entry : failedSaves.entrySet()) {
			buf.append("\nURI: ").append(entry.getKey().toString()).append(", cause: \n")
					.append(getExceptionAsString(entry.getValue()));
		}
		return buf.toString();
	}
	private static String getExceptionAsString(Throwable t) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		final String result = stringWriter.toString();
		try {
			stringWriter.close();
		} catch (final IOException e) {
			// $JL-EXC$ ignore
		}
		printWriter.close();
		return result;
	}
	private void removeEventViewer(DiagramEditor view) {
		// there will only be one source page and it will always be the last
		// page in the tab folder
		for(EditorPart ed : mapping.keySet()){
			if(ed.equals(view)){
				removePage(mapping.get(ed).index);
				mapping.remove(ed);
				view = null;
				break;
			}
			
		}	
	}
	class Tupel {
		public int index;
		public String id;

		public Tupel(int index, String id) {
			this.index = index;
			this.id = id;
		}
	}
}
