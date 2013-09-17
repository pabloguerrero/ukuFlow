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
	public static String toDisplayName(String anyName) {
		// get rid of the "Impl" java suffix
		anyName = anyName.replaceAll("Impl$", "");
		
		String displayName = "";
		boolean first = true;
		char[] chars = anyName.toCharArray();
		for (int i=0; i<chars.length; ++i) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				if (displayName.length()>0 && i+1<chars.length && !Character.isUpperCase(chars[i+1]))
					displayName += " ";
			}
			if (first) {
				c = Character.toUpperCase(c);
				first = false;
			}
			if (c=='_')
				c = ' ';
			displayName += c;
		}
		displayName = displayName.replaceAll("dot", ".");
		return displayName.trim();
	}
}
