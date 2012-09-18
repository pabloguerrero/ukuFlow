package de.tudarmstadt.dvs.ukuflow.script.generalscript;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyScopeException;

public class ScopeManager {
	private static ScopeManager instance = null;
	private List<Integer> availableID = null;
	private HashMap<String, Integer> namePool;

	private ScopeManager() {
		availableID = new LinkedList<Integer>();
		for (int i = 0; i < 256; i++)
			availableID.add(i);
		namePool = new HashMap<String, Integer>();
	}

	public static ScopeManager getInstance() {
		if (instance == null)
			instance = new ScopeManager();
		return instance;
	}
	public void reset(){
		availableID = new LinkedList<Integer>();
		for (int i = 0; i < 256; i++)
			availableID.add(i);
		namePool = new HashMap<String, Integer>();
	}
	public int registerScope(String sName) throws DuplicateScopeNameException,
			TooManyScopeException {
		if (namePool.containsKey(sName)) {
			throw new DuplicateScopeNameException(sName);
		}
		if (namePool.size() >= 256)
			throw new TooManyScopeException();
		int result = sName.hashCode() % 256;

		if (availableID.contains(result)) {

		} else {
			//System.out.println("collision " + sName + " > " + result);
			int tmp = Math.abs(result) % availableID.size();
			result = availableID.get(tmp);
		}

		availableID.remove(new Integer(result));
		namePool.put(sName, result);
		return result;
	}

	// *** test ***//
	public static void main(String[] args) throws DuplicateScopeNameException,
			TooManyScopeException {
		String s = "a";
		char c = 'x';
		ScopeManager.getInstance().registerScope("abbbb");
		try {
			while (true) {
				int r = ScopeManager.getInstance().registerScope(s + (c + 11));
				c += 11;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("namePool size: "+instance.namePool.size());
			System.out.println("availableID size: "+instance.availableID.size());
			for(int i = 0; i < 256; i++){
				if(!instance.namePool.containsValue(i)){
					System.out.println(i);
				}
			}
			System.out.println(instance.namePool.values());
		}
	}
}
