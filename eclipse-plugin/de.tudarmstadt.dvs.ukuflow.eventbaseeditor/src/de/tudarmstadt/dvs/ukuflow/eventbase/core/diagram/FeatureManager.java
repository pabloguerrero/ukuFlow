package de.tudarmstadt.dvs.ukuflow.eventbase.core.diagram;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;

import de.tudarmstadt.dvs.ukuflow.features.eg.distribution.EGDistributionFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.ef.simple.EFSimpleFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGAbsoluteFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGImmediateFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGOffsetFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.nonrecurring.EGRelativeFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.patterned.EGPatternedFeatureContainer;
import de.tudarmstadt.dvs.ukuflow.features.eg.periodic.EGPeriodicFeatureContainer;

public class FeatureManager {
	public List<ICreateFeature> ALL_CreateFeature;
	private FeatureManager(){
		ALL_CreateFeature = new LinkedList<ICreateFeature>();
	}
	public static List<ICreateFeature> getCreateFeatures(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		
		//features.add(new EAperiodicDistributionEGCreateFeature(fp));
		//features.add(new EAperiodicPatternedEGCreateFeature(fp));
		//features.add(new EComplexEFCreateFeature(fp));
		//features.add(new EPeriodicEGCreateFeature(fp));
		//features.add(new ESimpleEFCreateFeature(fp));
		
		return features;
	}
	public static List<ICreateFeature> getRecurringEGFeature(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		features.add((new EGPeriodicFeatureContainer()).getCreateFeature(fp));
		
		features.add((new EGDistributionFeatureContainer()).getCreateFeature(fp));
		features.add((new EGPatternedFeatureContainer()).getCreateFeature(fp));
		//features.add((new EGRelativeFeatureContainer()).getCreateFeature(fp));
		return features;
	}
	public static List<ICreateFeature> getNonRecurringEGFeatures(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		features.add((new EGImmediateFeatureContainer()).getCreateFeature(fp));
		features.add((new EGAbsoluteFeatureContainer()).getCreateFeature(fp));
		features.add((new EGOffsetFeatureContainer()).getCreateFeature(fp));
		features.add((new EGRelativeFeatureContainer()).getCreateFeature(fp));
		return features;
	}
	
	public static List<ICreateFeature> getSimpleEFCreateFeatures(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		features.add((new EFSimpleFeatureContainer()).getCreateFeature(fp));

		return features;
	}
	/**
	 * 
	 * @param fp
	 * @return
	 * @deprecated
	 */
	public static List<ICreateFeature> getEFCreateFeatures__(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();

		//features.add(new EComplexEFCreateFeature(fp));
		//features.add(new ESimpleEFCreateFeature(fp));
		
		return features;
	}
}
