package de.tudarmstadt.dvs.ukuflow.features.ef.complex.status;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tudarmstadt.dvs.ukuflow.eventbase.utils.DialogUtils;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EFProcessing;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;
import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.ef.EFFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericDirectEditFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericEditPropertiesFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericLayoutFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericMoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericRemoveFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericResizeFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.GenericUpdateFeature;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public abstract class EFProcessingFeatureContainer extends EFFeatureContainer{
	private BpmnLog log = BpmnLog.getInstance(getClass().getSimpleName());
	@Override
	public Object getApplyObject(IContext context) {
		return super.getApplyObject(context);
	}

	public boolean canApplyTo(Object o) {
		return (o instanceof EFProcessing);
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
	
	@Override
	public AbstractCustomFeature getDoubleClickFeature(IFeatureProvider fb){
		
		return null;
	}
	public class EFProcessingDoubleClickFeature extends GenericEditPropertiesFeature{

		public EFProcessingDoubleClickFeature(IFeatureProvider fp) {
			super(fp);
		}
		public void execute(ICustomContext context){
			EventBaseOperator bo = (EventBaseOperator) getBusinessObj(context);
			EFProcessing efCount = (EFProcessing) bo;
			int currentWindowSize = efCount.getWindowSize();
			Map<Integer,RequestContainer> properties = new HashMap<Integer, RequestContainer>();
			properties.put(EventbasePackage.EF_PROCESSING__WINDOW_SIZE,
					new RequestContainer(
							new RequestContainer.IntegerValidator(),
							currentWindowSize + "", "Window Size"));
			Map<Integer,RequestContainer> result = asking(bo,properties);
			if (result == null)
				return;
			String newConstraint = result
					.get(EventbasePackage.EF_PROCESSING__WINDOW_SIZE).result;
			if (!newConstraint.equals(currentWindowSize)) {
				this.hasDoneChanges = true;
				int window = Integer.parseInt(newConstraint);
				efCount.setWindowSize(window);
				log.debug("new window size is " +window);
			}
		}
		
	}
}
