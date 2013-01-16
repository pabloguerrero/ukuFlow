/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package de.tudarmstadt.dvs.ukuflow.script.generalscript;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.tools.exception.DuplicateScopeNameException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ScopeNotExistException;
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

	public void reset() {
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
			// System.out.println("collision " + sName + " > " + result);
			int tmp = Math.abs(result) % availableID.size();
			result = availableID.get(tmp);
		}

		availableID.remove(new Integer(result));
		namePool.put(sName, result);
		return result;
	}

	public int getScopeID(String sName) throws ScopeNotExistException {
		if (namePool.containsKey(sName))
			return namePool.get(sName);
		//System.out.println(sName + " : " + namePool);
		throw new ScopeNotExistException();
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
			// e.printStackTrace();
			System.out.println("namePool size: " + instance.namePool.size());
			System.out.println("availableID size: "
					+ instance.availableID.size());
			for (int i = 0; i < 256; i++) {
				if (!instance.namePool.containsValue(i)) {
					System.out.println(i);
				}
			}
			System.out.println(instance.namePool.values());
		}
	}
}
