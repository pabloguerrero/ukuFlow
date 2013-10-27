package de.tudarmstadt.dvs.ukuflow.features.eg.distribution;

import java.util.Map;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGDistribution;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbaseFactory;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.FeatureUtil;
import de.tudarmstadt.dvs.ukuflow.features.eg.EGFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericLayoutFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericMoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericRemoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericResizeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.abstr.UkuAbstractAddShapeFeature;
import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class EGDistributionFeatureContainer extends EGFeatureContainer {
	private BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	
	@Override
	public Object getApplyObject(IContext context) {
		return super.getApplyObject(context);
	}

	public boolean canApplyTo(Object o) {
		return (o instanceof EGDistribution);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EGImmediateCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EGDistributionAddFeature(fp);
	}

	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {

		return new GenericUpdateFeature(fp);
	}

	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new GenericDirectEditFeature(fp);
	}

	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new GenericLayoutFeature(fp);
	}

	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return new GenericRemoveFeature(fp);
	}

	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new GenericMoveFeature(fp);
	}

	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new GenericResizeFeature(fp);
	}

	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return new DefaultDeleteFeature(fp);
	}

	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return null;
	}

	class EGImmediateCreateFeature extends AbstractCreateFeature {

		public EGImmediateCreateFeature(IFeatureProvider fp) {
			super(fp, "Distribution", "Create an distribution event generator");
		}

		public EGImmediateCreateFeature(IFeatureProvider fp, String name,
				String description) {
			super(fp, name, description);
		}

		public boolean canCreate(ICreateContext context) {
			return context.getTargetContainer() instanceof Diagram;
		}

		public Object[] create(ICreateContext context) {
			EGDistribution newClass = EventbaseFactory.eINSTANCE
					.createEGDistribution();
			getDiagram().eResource().getContents().add(newClass);

			// Use the following instead of the above line to store the model
			// data in a seperate file parallel to the diagram file
			// try {
			// try {
			// TutorialUtil.saveToModelFile(newClass, getDiagram());
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// } catch (CoreException e) {
			// e.printStackTrace();
			// }

			// do the add
			addGraphicalRepresentation(context, newClass);

			// activate direct editing after object creation
			getFeatureProvider().getDirectEditingInfo().setActive(true);
			// return newly created business object(s)
			return new Object[] { newClass };
		}

	}

	public class EGDistributionAddFeature extends UkuAbstractAddShapeFeature {

		public static final int INVISIBLE_RECT_RIGHT = 6;

		public EGDistributionAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EGDistribution)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}

	}
	public class EGDistributionDoubleClickFeature extends GenericEditPropertiesFeature{

		public EGDistributionDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}
		public void execute(ICustomContext context){
			EventBaseOperator bo = (EventBaseOperator)getBusinessObj(context);
			if(bo == null)
				return;
			Map<Integer,RequestContainer> properties = FeatureUtil.createQuestions((EventBaseOperator)bo);
			EGDistribution off = (EGDistribution) bo;
			Integer key0 = EventbasePackage.EG_DISTRIBUTION__PERIOD_INTERVAL;
			Integer key1 = EventbasePackage.EG_DISTRIBUTION__EVALUATION_INTERVAL;
			Integer key2 = EventbasePackage.EG_DISTRIBUTION__FUNCTION;
			properties.put(key0,new RequestContainer(new RequestContainer.OffsetTimeValidator(),
					"" +off.getPeriodInterval(),"Period Interval (mm:ss)"));
			properties.put(key1,
					new RequestContainer(
							new RequestContainer.OffsetTimeValidator(), ""
									+ off.getEvaluationInterval(), "Evaluation Interval (mm:ss)"));// TODO
			properties.put(key2,
					new RequestContainer(null,UkuConstants.DistributionFunction.FUNCTIONS ,
							"Function",off.getFunction()));// off.getFunction());
			String pa = off.getParameters();
			if(pa == null ||pa.equals(""))
				pa = "0 0 0";
			RequestContainer rc = new RequestContainer(null,null,null);
			rc.currentValue = pa;
			properties.put(-1, rc);
			
			Map<Integer,RequestContainer> results = asking(bo,properties);
			if(results == null)
				return;
			hasDoneChanges = FeatureUtil.fetchAnswer(bo, results);
			//parameters:
			// 1 element is the function type the rest are parameters
			String params = results.get(-1).result;
			log.debug(params);
			off.setParameters(params);
			String currentTime = off.getPeriodInterval();
			String newTime = results.get(key0).result;
			//int newTime = Integer.parseInt(result.get(key1).result);
			if (!newTime.equals(currentTime)) {
				this.hasDoneChanges = true;
				off.setPeriodInterval(newTime);
				// updatePictogramElement(pes[0]);
			}
			
			currentTime = off.getEvaluationInterval();
			newTime = results.get(key1).result;
			//int newTime = Integer.parseInt(result.get(key1).result);
			if (!newTime.equals(currentTime)) {
				this.hasDoneChanges = true;
				off.setEvaluationInterval(newTime);
				// updatePictogramElement(pes[0]);
			}
			
			String currentPattern = off.getFunction();
			String newPattern = results.get(key2).result;
			if (!newPattern.equals(currentPattern)) {
				this.hasDoneChanges = true;
				off.setFunction(newPattern);
				// updatePictogramElement(pes[0]);
			}
		}
	}
	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		return new EGDistributionDoubleClickFeature(fb);
	}
	
}