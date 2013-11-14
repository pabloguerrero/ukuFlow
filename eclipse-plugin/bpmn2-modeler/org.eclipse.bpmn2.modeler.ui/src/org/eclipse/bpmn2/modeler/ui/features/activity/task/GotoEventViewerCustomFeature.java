package org.eclipse.bpmn2.modeler.ui.features.activity.task;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.InputSet;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.editor.DesignEditor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jdt.internal.core.ModelUpdater;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.EventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

/**
 * 
 * @author �Hien Quoc Dang�
 * 
 *         custom feature for jumping from a receive task the event view editor.
 *         for embedding this feature into bpmn2 plugin, the getCustomFeatures()
 *         in {@link ReceiveTaskFeatureContainer} must be implemeted
 */
public class GotoEventViewerCustomFeature extends AbstractCustomFeature {
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	public GotoEventViewerCustomFeature(IFeatureProvider fp) {
		super(fp);
	}

	private boolean changesDone = false;

	@Override
	public String getName() {
		return "Edit Event Script";
	}

	@Override
	public String getDescription() {
		return "Open an event editor for this receive task";
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_16_TASK;
	}
	@Deprecated
	private String createDataInputParameterifNeeded(ReceiveTask task) {
		if("!".equals("!"))
			return task.getEventScript();
		try {
			for (DataInputAssociation dia : task.getDataInputAssociations()) {
				for (Assignment assg : dia.getAssignment()) {
					return ((FormalExpression) assg.getFrom()).getBody();
				}
			}
		} catch (Exception e) {

		}
		// check if there is iospecificaion, if not, create one
		InputOutputSpecification iospec = task.getIoSpecification();
		if (iospec == null) {
			final EStructuralFeature ioSpecificationFeature = task.eClass()
					.getEStructuralFeature("ioSpecification");
			iospec = Bpmn2Factory.eINSTANCE.createInputOutputSpecification();
			InsertionAdapter.add(task, ioSpecificationFeature, iospec);
			InsertionAdapter.executeIfNeeded(iospec);
		}
		// check if the inputset is there, if not, create one
		List<InputSet> inputSets = task.getIoSpecification().getInputSets();
		InputSet inputSet = null;
		if (inputSets.size() == 0) {
			inputSets.add(Bpmn2Factory.eINSTANCE.createInputSet());
		}
		inputSet = inputSets.get(0);
		if (inputSet.getId() == null) {
			ModelUtil.setID(inputSet);
		}
		DataInput param = null;
		if (inputSet.getDataInputRefs().size() == 0) {
			param = createDataInput(inputSet);
			inputSet.getDataInputRefs().add(param);
		}
		param = inputSet.getDataInputRefs().get(0);

		// create datainputassociation if it doesn't exist
		DataInputAssociation inputAssociation = null;
		if (task.getDataInputAssociations().size() == 0) {
			inputAssociation = Bpmn2Factory.eINSTANCE
					.createDataInputAssociation();
			ModelUtil.setID(inputAssociation);
			task.getDataInputAssociations().add(inputAssociation);
			// InsertionAdapter.add(task, Bpmn2Package.eINSTANCE.getRecei,
			// value)
		}
		inputAssociation = task.getDataInputAssociations().get(0);
		// if(inputAssociation.getTargetRef().equals(param));
		inputAssociation.setTargetRef(param);
		task.getIoSpecification().getDataInputs().add(param);
		InsertionAdapter
				.add(task.getIoSpecification(), Bpmn2Package.eINSTANCE
						.getInputOutputSpecification_DataInputs(), param);

		// create
		DataInputAssociation diAssociation = task.getDataInputAssociations()
				.get(0);
		Assignment assignment = Bpmn2Factory.eINSTANCE.createAssignment();
		ModelUtil.setID(assignment);
		Expression exp_ = Bpmn2Factory.eINSTANCE.createFormalExpression();
		((FormalExpression) exp_).setBody("");
		ModelUtil.setID(exp_);
		assignment.setFrom(exp_);
		diAssociation.getAssignment().add(assignment);
		InsertionAdapter.add(diAssociation,
				Bpmn2Package.eINSTANCE.getDataAssociation_Assignment(),
				assignment);
		return "";
	}

	private DataInput createDataInput(InputSet inputSet) {
		// create data input:
		DataInput param = Bpmn2Factory.eINSTANCE.createDataInput();
		String base = "input";
		int suffix = 1;
		String name = base + suffix;
		for (;;) {
			boolean found = false;
			for (DataInput p : inputSet.getDataInputRefs()) {
				if (name.equals(p.getName())) {
					found = true;
					break;
				}
			}
			if (!found)
				break;
			name = base + ++suffix;
		}
		((DataInput) param).setName(name);
		return param;
	}

	@Override
	public void execute(ICustomContext context) {
		IPeService peService = Graphiti.getPeService();
		
		String text = null;
		ReceiveTask task = null;
		
		// getting process information
		PictogramElement picto = context.getPictogramElements()[0];
		PictogramElement parent = peService.getPictogramElementParent(picto);
		Object be_parent = getFeatureProvider().getBusinessObjectForPictogramElement(parent);
		BPMNDiagram di = (BPMNDiagram)be_parent;
		BaseElement be = di.getPlane().getBpmnElement();
		EEventBaseScript top = null;
		try {
			Object obj = getFeatureProvider().getBusinessObjectForPictogramElement(picto);
			task = (ReceiveTask) obj;
			text = task.getEventScript();//createDataInputParameterifNeeded(task);
			log.info("script from receiveTask: \"" + text + "\"");
			try {
				if (!text.equals("")) {
					top = EventBaseScript.getInstance(text)
							.validate();
					log.info("successfully validate script : " + text);
				}
			} catch (Exception e) {
				log.error("invalid script! : "+text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DesignEditor editor = (DesignEditor) getDiagramEditor();
		if (task != null) {
			editor.getMultipageEditor().createEventViewer(task, (be.getId() + "_" + task.getId()).toLowerCase(),
					text,top);
		} else
			log.error("cannot jump to receiveTask editor, id doesn't exist!");

	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}

	protected List<EClass> getAvailableTypes() {
		DiagramEditor editor = (DiagramEditor) getDiagramEditor();
		ModelEnablementDescriptor enablements = (ModelEnablementDescriptor) editor
				.getAdapter(ModelEnablementDescriptor.class);
		EClass newType = getBusinessObjectClass();

		// build a list of possible subclasses for the popup menu
		List<EClass> subtypes = new ArrayList<EClass>();
		for (EClassifier ec : Bpmn2Package.eINSTANCE.getEClassifiers()) {
			if (ec instanceof EClass) {
				if (((EClass) ec).isAbstract()) {
					continue;
				}
				EList<EClass> superTypes = ((EClass) ec).getEAllSuperTypes();
				if (superTypes.contains(newType)
						&& enablements.isEnabled((EClass) ec)) {
					if (ec != Bpmn2Package.eINSTANCE.getBoundaryEvent()
							&& ec != Bpmn2Package.eINSTANCE.getStartEvent()) {
						subtypes.add((EClass) ec);
					}
				}
			}
		}
		return subtypes;
	}

	public EClass getBusinessObjectClass() {
		return Bpmn2Package.eINSTANCE.getReceiveTask();
	}
}
