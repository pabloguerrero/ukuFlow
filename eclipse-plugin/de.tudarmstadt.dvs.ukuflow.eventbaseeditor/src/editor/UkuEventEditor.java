package editor;

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.ui.IEditorInput;

import de.tudarmstadt.dvs.ukuflow.eventbase.core.EventScriptResource;

public class UkuEventEditor extends DiagramEditor{

	EventScriptResource resource;
	
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		//org.eclipse.graphiti.examples.common.ui.CreateDiagramWizard t;
		// Hook a transaction exception handler so we can get diagnostics about EMF validation errors.
		//getEditingDomainListener();
		
		BasicCommandStack basicCommandStack = (BasicCommandStack) getEditingDomain().getCommandStack();

		if (input instanceof DiagramEditorInput) {
			ResourceSet resourceSet = getEditingDomain().getResourceSet();
			//getTargetRuntime().setResourceSet(resourceSet);
			
			resource = new EventScriptResource(((DiagramEditorInput) input).getUri());

			//resourceSet.setURIConverter(new ProxyURIConverterImplExtension());
			//resourceSet.eAdapters().add(editorAdapter = new DiagramEditorAdapter(this));

			//modelHandler = ModelHandlerLocator.createModelHandler(modelUri, bpmnResource);
			//ModelHandlerLocator.put(diagramUri, modelHandler);

			//getTargetRuntime(input);
			//setActiveEditor(this);

			// allow the runtime extension to construct custom tasks and whatever else it needs
			// custom tasks should be added to the current target runtime's custom tasks list
			// where they will be picked up by the toolpalette refresh.
			//getTargetRuntime().getRuntimeExtension().initialize(this);

			/*
			try {
				if (getModelFile()==null || getModelFile().exists()) {
					bpmnResource.load(null);
				} else {
					saveModelFile();
				}
			} catch (IOException e) {
				Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
				ErrorUtils.showErrorWithLogging(status);
			}
			basicCommandStack.execute(new RecordingCommand(getEditingDomain()) {

				@Override
				protected void doExecute() {
					importDiagram();
					getTargetRuntime().setResource(bpmnResource);
				}
			});
		}
		basicCommandStack.saveIsDone();
		basicCommandStack.flush();
		loadMarkers();
	*/	
	}
}
}
