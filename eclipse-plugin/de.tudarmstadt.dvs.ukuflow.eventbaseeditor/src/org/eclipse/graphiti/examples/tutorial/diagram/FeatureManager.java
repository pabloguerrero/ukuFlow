package org.eclipse.graphiti.examples.tutorial.diagram;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;

import de.tudarmstadt.dvs.ukuflow.features.EAperiodicDistributionEG.EAperiodicDistributionEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.EAperiodicPatternedEG.EAperiodicPatternedEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.EComplexEF.EComplexEFCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.EPeriodicEG.EPeriodicEGCreateFeature;
import de.tudarmstadt.dvs.ukuflow.features.ESimpleEF.ESimpleEFCreateFeature;

public class FeatureManager {
	public List<ICreateFeature> ALL_CreateFeature;
	private FeatureManager(){
		ALL_CreateFeature = new LinkedList<ICreateFeature>();
	}
	public static List<ICreateFeature> getCreateFeatures(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		
		features.add(new EAperiodicDistributionEGCreateFeature(fp));
		features.add(new EAperiodicPatternedEGCreateFeature(fp));
		features.add(new EComplexEFCreateFeature(fp));
		features.add(new EPeriodicEGCreateFeature(fp));
		features.add(new ESimpleEFCreateFeature(fp));
		
		return features;
	}
	public static List<ICreateFeature> getEGCreateFeatures(IFeatureProvider fp){
List<ICreateFeature> features = new LinkedList<ICreateFeature>();
		
		features.add(new EAperiodicDistributionEGCreateFeature(fp));
		features.add(new EAperiodicPatternedEGCreateFeature(fp));		
		features.add(new EPeriodicEGCreateFeature(fp));
		
		return features;
	}
	public static List<ICreateFeature> getEFCreateFeatures(IFeatureProvider fp){
		List<ICreateFeature> features = new LinkedList<ICreateFeature>();

		features.add(new EComplexEFCreateFeature(fp));
		features.add(new ESimpleEFCreateFeature(fp));
		
		return features;
	}
}
