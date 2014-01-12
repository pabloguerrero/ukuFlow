package org.eclipse.bpmn2.modeler.ui.editor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeDecrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeIncrease;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFChangeRemain;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFComposite;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicAnd;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicNot;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFLogicOr;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingAvg;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingCount;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMax;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingMin;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingStDev;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessingSum;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFSimple;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFStatusEvent;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFTemporal;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGAbsolute;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGImmediate;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGOffset;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPeriodic;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRecurring;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.ESequenceFlow;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventFilter;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventGenerator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.impl.EventBaseOperatorImpl;
import de.tudarmstadt.dvs.ukuflow.hash.HashUtil;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.EventbaseUtils;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EChangeEvent;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ECompositeEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EEventBaseScript;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ELogicalCompositionEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EProcessingEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.ESimpleEF;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.IEConnection;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class EventViewer extends DiagramEditor {
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());

	public String id;
	public int index;
	private ReceiveTask task;

	Resource resource;
	URI modelUri;

	private boolean recreateDiagram = false;
	EEventBaseScript script;

	public EventViewer(ReceiveTask task, String id, int index,
			boolean recreateDiagram, EEventBaseScript script) {

		super();
		log.debug("EventViewer() : script[" + script + "] id [" + id
				+ "] recreate[" + recreateDiagram + "]" + "receivetask[" + task
				+ "]");
		this.script = script;
		this.id = id;
		this.index = index;
		this.task = task;
		this.recreateDiagram = recreateDiagram;
		// Hien palettesize
		getPalettePreferences().setPaletteWidth(170);
	}

	public boolean checking(List<ESequenceFlow> cons,
			List<EventBaseOperator> ele) {
		return true;
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		modelUri = ((DiagramEditorInput) input).getUri();
		super.init(site, input);

	}

	public IFile getModelFile() {
		if (modelUri != null) {
			String uriString = modelUri.trimFragment().toPlatformString(true);
			if (uriString != null) {
				IPath fullPath = new Path(uriString);
				return ResourcesPlugin.getWorkspace().getRoot()
						.getFile(fullPath);
			}
		}
		return null;
	}

	@Override
	public void setInput(IEditorInput input) {
		super.setInput(input);

		DiagramEditorInput dInput = (DiagramEditorInput) input;
		resource = getResourceSet().createResource(dInput.getUri());
		if (input instanceof DiagramEditorInput) {
			final TransactionalEditingDomain domain = getEditingDomain();
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					importDiagram();
				}
			});
		}

	}

	private void importDiagram() {

		if (!recreateDiagram || script == null) {
			log.debug("recreate Diagram is set to " + recreateDiagram + " and script is: " + script);
			return;
		}
		log.debug("importing diagram..");
		de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator top = script
				.getTopExp();

		recreateDiagram = false;
		Diagram diagram = getDiagramTypeProvider().getDiagram();
		IFeatureProvider featureProvider = getDiagramTypeProvider()
				.getFeatureProvider();
		diagram.setActive(true);

		int x = 0;
		int y = 40;
		for (de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator ebo : script
				.getLocalExp()) {
			//EventBaseOperator eb = EventbaseUtils.convert(ebo);
			addElement(diagram,featureProvider,ebo,x,y);
			y+= 25;
		}		
		y+= 40;
		x = 20;
		addElement(diagram,featureProvider,top,x,y);
		
		/*
		AddContext context = new AddContext();
		context.setNewObject(EventbaseUtils.convert(top));
		context.setLocation(x, y);
		context.setTargetContainer(diagram);
		log.info("adding "+top+" to diagram");
		featureProvider.addIfPossible(context);
		log.debug("adding top exp (): "+script.getTopExp());
		*/
	}
	private void addConnection(Diagram diagram, IFeatureProvider featureProvider, EventBaseOperator source, EventBaseOperator target){
		ContainerShape s = (ContainerShape) featureProvider.getPictogramElementForBusinessObject(source);
		ContainerShape t = (ContainerShape) featureProvider.getPictogramElementForBusinessObject(target);		
		ICreateConnectionFeature[] createConnection = featureProvider.getCreateConnectionFeatures();
		ICreateConnectionFeature createConnectionF = createConnection[0];
		
		if (source != null && target != null) {
			// create new business object
			ESequenceFlow seqFlow = createEReference(source, target);
			// add connection for business object
			AddConnectionContext addConnectionContext = new AddConnectionContext(s.getAnchors().get(0), t.getAnchors().get(0));
			addConnectionContext.setNewObject(seqFlow);
			featureProvider.addIfPossible(
					addConnectionContext);
			// link(newConnection, eReference);
		}
	}
	/**
	 * Creates a EReference between two EClasses.
	 */
	private ESequenceFlow createEReference(EventBaseOperator source,
			EventBaseOperator target) {
		// EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		ESequenceFlow eReference = EventbaseFactory.eINSTANCE
				.createESequenceFlow();		// EventbasePackage.eINSTANCE.getESequenceFlow();//new
											// EConnection((IEEvaluableExpression)source,
											// (IEEvaluableExpression)target);
		eReference.setSource(source);
		eReference.setTarget(target);

		
		return eReference;
	}
	private EventBaseOperator addElement(Diagram diagram, IFeatureProvider featureProvider, de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator eb,int x, int y){
		//x = x >= 400 ? 40 : x + 40;
		//if (x == 40)
		//	y += 40;
		if(x<0) x = 0;
		AddContext context = new AddContext();
		EventBaseOperator tmp = EventbaseUtils.convert(eb);
		context.setNewObject(tmp);
		context.setLocation(x, y);
		context.setTargetContainer(diagram);
		featureProvider.addIfPossible(context);
		log.debug("adding "+eb);
		if(eb instanceof ESimpleEF){
			ESimpleEF ef = (ESimpleEF) eb;
			de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator source = ef.getSource();
			EventBaseOperator tar = addElement(diagram, featureProvider, source, x+150, y);
			addConnection(diagram,featureProvider,tar,tmp);
		} else if (eb instanceof EProcessingEF){
			EProcessingEF cef = (EProcessingEF)eb;
			//for(EventBaseOperator c :cef.getSource()){
				//de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator source = (de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator)c.getSource();
			de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator source = cef.getSource();
			EventBaseOperator tar = addElement(diagram, featureProvider, source, x+150, y);
			addConnection(diagram, featureProvider, tar, tmp);
			//}
		} else if (eb instanceof EChangeEvent){
			EChangeEvent ec = (EChangeEvent)eb;
			de.tudarmstadt.dvs.ukuflow.script.eventbasescript.expression.EventBaseOperator source = ec.getSource();
			EventBaseOperator tar = addElement(diagram, featureProvider, source, x+150, y);
			addConnection(diagram, featureProvider, tar, tmp);
		} else if (eb instanceof ELogicalCompositionEF){
			log.debug("TODO: logical composition ef");
		}
		return tmp;
	}
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		return super.getEditingDomain();
	}

	/**
	 * finding the top operator which has no outgoing sequence flow.
	 * 
	 * @param cons
	 * @param ele
	 * @return
	 */
	private EventBaseOperator findTopExp(List<ESequenceFlow> cons,
			List<EventBaseOperator> ele) {
		for (EventBaseOperator ebOp : ele) {
			if (ebOp.getOutgoing() == null || ebOp.getOutgoing().size() == 0)
				return ebOp;
		}
		return null;
	}

	public void parse(EventBaseOperator top, StringBuilder sb,
			List<ESequenceFlow> connection, List<EventBaseOperator> elements) {

		if (top instanceof EGAbsolute) {
			EGAbsolute local = (EGAbsolute) top;
			sb.append(" ABSOLUTE_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" " + local.getAbsoluteTime());

		} else if (top instanceof EGImmediate) {
			EGImmediate local = (EGImmediate) top;
			sb.append(" IMMEDIATE_EG");
			sb.append(" " + local.getSensorType());

		} else if (top instanceof EGOffset) {
			EGOffset local = (EGOffset) top;
			sb.append(" OFFSET_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" " + local.getOffsetTime());
		} else if (top instanceof EGDistribution) {
			EGDistribution local = (EGDistribution) top;
			sb.append(" DISTRIBUTION_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" ^" + local.getPeriodInterval());
			sb.append(" #" + local.getEvaluationInterval());
			sb.append(" " + local.getFunction());
			sb.append(" " + local.getParameters());

		} else if (top instanceof EGPatterned) {
			EGPatterned local = (EGPatterned) top;
			sb.append(" PATTERN_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" p" + local.getPattern());
			sb.append(" ^" + local.getTime());

		} else if (top instanceof EGPeriodic) {
			EGPeriodic local = (EGPeriodic) top;
			sb.append(" PERIODIC_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" ^" + local.getTime());

		} else if (top instanceof EGRelative) {
			EGRelative local = (EGRelative) top;
			sb.append(" RELATIVE_EG");
			sb.append(" " + local.getSensorType());
			sb.append(" " + local.getDelayTime());

		}
		// special:
		// scope
		if (top instanceof EventGenerator) {
			// TODO more restriction:i.e if the scope is empty
			EventGenerator local = (EventGenerator) top;
			if (local.getScope() != null) {
				sb.append(" @s " + local.getScope());
			}
			// repetition
			if (top instanceof EGRecurring) {
				EGRecurring local2 = (EGRecurring) top;
				sb.append(" x" + local2.getRepetition());

			}
		}
		// source for relative_eg
		if (top instanceof EGRelative) {
			// source:
			sb.append(" (");
			if (top.getIncoming() != null && top.getIncoming().size() == 1) {
				EventBaseOperator source = top.getIncoming().get(0).getSource();
				parse(source, sb, connection, elements);
			} else {
				// error!!!!
				// TODO relative_EG should have one incoming sequence flow
			}
			sb.append(")");
		}
		if (top instanceof EventFilter) {
			// AAAAA:
			if (top instanceof EFSimple) {
				EFSimple efSimple = (EFSimple) top;
				sb.append(" SIMPLE_EF");
				sb.append(" [" + efSimple.getConstraints() + "]");
				sb.append(" (");
				if (top.getIncoming() != null && top.getIncoming().size() == 1) {
					EventBaseOperator source = top.getIncoming().get(0)
							.getSource();
					parse(source, sb, connection, elements);
				} else {
					// error!!!!
					// TODO relative_EG should have one incoming sequence flow
				}
				sb.append(")");
			} else if (top instanceof EFComposite) {
				if (top instanceof EFChangeEvent) {
					if (top instanceof EFChangeDecrease) {
						sb.append(" DECREASE_EC");
					} else if (top instanceof EFChangeIncrease) {
						sb.append(" INCREASE_EC");
					} else if (top instanceof EFChangeRemain) {
						sb.append(" REMAIN_EC");
					}
					EFChangeEvent local = (EFChangeEvent)top;
					sb.append(" "+ local.getWindowSize());
					sb.append(" "+ local.getChangeThreshold());
					sb.append(" (");
					parse(local.getIncoming().get(0).getSource(),sb,connection,elements);
					sb.append(")");
				} else if (top instanceof EFStatusEvent) {
					if (top instanceof EFLogic) {
						sb.append("TODO_EFLOGIC");
						if (top instanceof EFLogicAnd) {
							// TODO
						} else if (top instanceof EFLogicOr) {
							// TODO
						} else if (top instanceof EFLogicNot) {
							// TODO
						}
					} else if (top instanceof EFProcessing) {
						if (top instanceof EFProcessingAvg) {
							sb.append(" AVG_EC");
						} else if (top instanceof EFProcessingCount) {
							sb.append(" COUNT_EC");
						} else if (top instanceof EFProcessingMax) {
							sb.append(" MAX_EC");
						} else if (top instanceof EFProcessingMin) {
							sb.append(" MIN_EC");
						} else if (top instanceof EFProcessingStDev) {
							sb.append(" STDEV_EC");
						} else if (top instanceof EFProcessingSum) {
							sb.append(" SUM_EC");
						}
						EFProcessing local = (EFProcessing) top;
						sb.append(" " + local.getWindowSize());
						// source!!!::
						sb.append(" (");
						if (top.getIncoming() != null
								&& top.getIncoming().size() == 1) {
							EventBaseOperator source = top.getIncoming().get(0)
									.getSource();
							parse(source, sb, connection, elements);
						} else {
							System.err
									.println("processing EF should have one incoming sequence flow");
							// error!!!!
							// TODO processing EF should have one incoming
							// sequence flow
						}
						top.getIncoming();
						sb.append(")");
					} else if (top instanceof EFTemporal) {
						// TODO
					}

				}
			}
		}

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		List<EventBaseOperator> elements = new ArrayList<EventBaseOperator>();
		List<ESequenceFlow> connections = new ArrayList<ESequenceFlow>();

		StringBuilder sb = new StringBuilder();
		Diagram diagram = getDiagramTypeProvider().getDiagram();
		EList<Connection> lcon = diagram.getConnections();
		EList<Shape> lshape = diagram.getChildren();
		for (Shape s : lshape) {
			Object i = Graphiti.getLinkService()
					.getBusinessObjectForLinkedPictogramElement(s);
			if (i instanceof EventBaseOperator) {
				elements.add((EventBaseOperator) i);
			}
		}
		for (Connection con : lcon) {
			Object j = Graphiti.getLinkService()
					.getBusinessObjectForLinkedPictogramElement(con);
			if (j instanceof ESequenceFlow) {
				ESequenceFlow jj = (ESequenceFlow) j;
				connections.add(jj);
			}
		}

		EventBaseOperator top = findTopExp(connections, elements);
		sb.append("TOP =");
		parse(top, sb, connections, elements);

		// wrap up!!
		final String newContent = sb.toString();
		final String hashScript = HashUtil.getHash(newContent);
		InputStream is = null;
		try {
			is = getModelFile().getContents(true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		final String hashFile = HashUtil.getHash(is);
		//final String hash2 = HashUtil.getHash();
		TransactionalEditingDomain editingDomain = TransactionUtil
				.getEditingDomain(task);
		if (editingDomain == null)
			return;
		final CommandStack commandStack = editingDomain.getCommandStack();
		commandStack.execute(new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
				task.setEventScript(newContent);
				System.out.format("file %s, script %s \n",hashFile,hashScript);
				task.setScriptHash(hashScript);
				task.setFileHash(hashFile);
			}
		});
	}
}
