package de.tudarmstadt.dvs.ukuflow.eventbase.core;

import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventBaseOperator;

public class ModelUtil {
	private static Set<String> namePool = new HashSet<String>();
	public static String setName(EventBaseOperator e){
		if(namePool == null)
			namePool = new HashSet<String>();
		String tmp = e.getClass().getSimpleName().toLowerCase()+"_";
		int i = 1;
		while(namePool.contains(tmp + i))
			i++;
		tmp = tmp + i;
		namePool.add(tmp);
		e.setElementName(tmp);
		return tmp;
	}
}
