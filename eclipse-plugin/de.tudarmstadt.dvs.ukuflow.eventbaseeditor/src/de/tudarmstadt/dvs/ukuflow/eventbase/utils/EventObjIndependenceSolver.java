package de.tudarmstadt.dvs.ukuflow.eventbase.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.graphiti.features.impl.IIndependenceSolver;
import de.tudarmstadt.dvs.ukuflow.script.eventbasescript.*;

public class EventObjIndependenceSolver implements IIndependenceSolver {
	
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	
	private Map<String, Object> graphicalObjectsMap = new HashMap<String, Object>();
	
	//private Map<String, Predicate> predicatesMap = new HashMap<String, Predicate>();

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.IIndependenceSolver#getKeyForBusinessObject(java.lang.Object)
	 */
	public String getKeyForBusinessObject(Object bo) {
		String result = null;
		if(bo != null && bo instanceof INonEMFEventbaseElement ) {
			result = ((INonEMFEventbaseElement) bo).getID();
			
			if(!objectMap.containsKey(result))
				objectMap.put(result, bo);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.IIndependenceSolver#getBusinessObjectForKey(java.lang.String)
	 */
	
	public Object getBusinessObjectForKey(String key) {
		return objectMap.get(key);
	}

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}
	
	public void removeBusinessObject(Object bo) {
		if(bo != null && bo instanceof INonEMFEventbaseElement ) {
			String key = ((INonEMFEventbaseElement) bo).getID();
			
			objectMap.remove(key);
			
			//remove connections/statement rules
			Collection<Object> objects = Collections.synchronizedCollection(objectMap.values());
			
			List<String> toDeleteList = new ArrayList<String>();
			/* TODO
			for(Object obj:objects) {
				if(obj instanceof StatementRule) {
					StatementRule stRule = (StatementRule) obj;
					
					if(stRule.getFromClass().getId().equals(key) ||
							stRule.getToClass().getId().equals(key))
						toDeleteList.add(stRule.getId());
				}
			}
			*/
			for(String toDeleteKey : toDeleteList) {
				objectMap.remove(toDeleteKey);
			}
			
		}
	}

	
	public void registerGraphicalObject(Class<?> graphicalObjectClass, Object businessObject, Object graphicalObject) {
		
		String key = graphicalObjectClass.getCanonicalName() + "_" + getKeyForBusinessObject(businessObject);
		
		if(this.graphicalObjectsMap == null)
			this.graphicalObjectsMap = new HashMap<String, Object>();
			
		this.graphicalObjectsMap.put(key, graphicalObject);
	}
	
	public Object getGraphicalObject(Class<?> graphicalObjectClass, Object businessObject) {
		String key = graphicalObjectClass.getCanonicalName() + "_" + getKeyForBusinessObject(businessObject);
		
		if(this.graphicalObjectsMap == null)
			this.graphicalObjectsMap = new HashMap<String, Object>();
		
		return this.graphicalObjectsMap.get(key);
	}
	
	/*
	public void registerPredicate(Predicate predicate) {
		if(predicatesMap == null)
			predicatesMap = new HashMap<String, Predicate>();
		
		if(!predicatesMap.containsKey(predicate.getId()))
			predicatesMap.put(predicate.getId(), predicate);
		
	}
	
	public List<Predicate> getPredicatesList() {
		List<Predicate> result = new ArrayList<Predicate>();
		
		if(predicatesMap == null)
			predicatesMap = new HashMap<String, Predicate>();
		
		result.addAll(predicatesMap.values());
		
		Comparator<Predicate> comparator = new Comparator<Predicate>() {
			@Override
			public int compare(Predicate o1, Predicate o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		
		Collections.sort(result, comparator);
		
		return result;	
	}

	public Map<String, Predicate> getPredicatesMap() {
		return predicatesMap;
	}*/
}