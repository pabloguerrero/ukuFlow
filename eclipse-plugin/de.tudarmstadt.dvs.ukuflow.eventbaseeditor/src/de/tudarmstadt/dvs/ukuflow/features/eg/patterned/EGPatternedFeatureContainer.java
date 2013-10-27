package de.tudarmstadt.dvs.ukuflow.features.eg.patterned;

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

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGPatterned;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EGRelative;
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

public class EGPatternedFeatureContainer extends EGFeatureContainer {

	@Override
	public Object getApplyObject(IContext context) {
		return super.getApplyObject(context);
	}

	public boolean canApplyTo(Object o) {
		return (o instanceof EGPatterned);
	}

	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new EGPatternedCreateFeature(fp);
	}

	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new EGPatternedAddFeature(fp);
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

	class EGPatternedCreateFeature extends AbstractCreateFeature {

		public EGPatternedCreateFeature(IFeatureProvider fp) {
			super(fp, "Patterned", "Create an patterned event generator");
		}

		public EGPatternedCreateFeature(IFeatureProvider fp, String name,
				String description) {
			super(fp, name, description);
		}

		public boolean canCreate(ICreateContext context) {
			return context.getTargetContainer() instanceof Diagram;
		}

		public Object[] create(ICreateContext context) {
			EGPatterned newClass = EventbaseFactory.eINSTANCE
					.createEGPatterned();
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

	public class EGPatternedAddFeature extends UkuAbstractAddShapeFeature {

		public static final int INVISIBLE_RECT_RIGHT = 6;

		public EGPatternedAddFeature(IFeatureProvider fp) {
			super(fp);
		}

		public boolean canAdd(IAddContext context) {
			final Object newObject = context.getNewObject();
			if (newObject instanceof EGPatterned)
				if (context.getTargetContainer() instanceof Diagram) {
					return true;
				}
			return false;
		}
	}
	
	public class EGPatternedDoubleClickFeature extends GenericEditPropertiesFeature{

		public EGPatternedDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		public void execute(ICustomContext context){
			EventBaseOperator bo = (EventBaseOperator) getBusinessObj(context);
			Map<Integer, RequestContainer> properties = FeatureUtil
					.createQuestions(bo);
			
			EGPatterned off = (EGPatterned) bo;
			Integer key1 = EventbasePackage.EG_PATTERNED__TIME;
			Integer key2 = EventbasePackage.EG_PATTERNED__PATTERN;
			properties.put(key1, new RequestContainer(
					new RequestContainer.OffsetTimeValidator(), "" + off.getTime(),
					"Period Time (in mm:ss format)"));
			properties.put(key2,
					new RequestContainer(
							new RequestContainer.BinaryValidator(), ""
									+ off.getPattern(), "Pattern"));
			Map<Integer,RequestContainer>result = asking(bo, properties);
			if (result == null)
				return;
			hasDoneChanges = FeatureUtil.fetchAnswer(bo, result);
			
			String currentTime = off.getTime();
			String newTime = result.get(key1).result;
			if (!newTime.equals(currentTime)) {
				this.hasDoneChanges = true;
				off.setTime(newTime);
			}
			String currentPattern = off.getPattern();
			String newPattern = result.get(key2).result;
			if (!newPattern.equals(currentPattern)) {
				this.hasDoneChanges = true;
				off.setPattern(newPattern);
			}
		}
	}

	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb) {
		// TODO Auto-generated method stub
		return null;
	}
}