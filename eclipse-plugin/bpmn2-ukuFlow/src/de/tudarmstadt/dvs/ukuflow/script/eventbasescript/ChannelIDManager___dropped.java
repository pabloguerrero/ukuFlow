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

package de.tudarmstadt.dvs.ukuflow.script.eventbasescript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.tools.exception.ChannelExistException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ChannelNotFoundException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyChannelException;

public class ChannelIDManager___dropped {
	private Map<String, Integer> channelMap;// = new HashMap<String, Integer>();
	private List<Integer> availableID = new ArrayList<Integer>();
	private static ChannelIDManager___dropped instance = null;

	private void init() {
		for (int i = 0; i < 256; i++)
			availableID.add(i);
		channelMap = new HashMap<String, Integer>();
	}

	public static ChannelIDManager___dropped getInstance() {
		if (instance == null)
			instance = new ChannelIDManager___dropped();
		return instance;
	}
	
	private ChannelIDManager___dropped() {
		init();
	}
	
	public int registerChannel(String ch) throws ChannelExistException,
			TooManyChannelException {
		if (channelMap.containsKey(ch))
			throw new ChannelExistException();
		if (availableID.size() == 0)
			throw new TooManyChannelException();
		int t = Math.abs(ch.hashCode()) % availableID.size();
		return availableID.remove(t);
	}
	
	public int getChannelID(String ch) throws ChannelNotFoundException {
		if(channelMap.containsKey(ch)){
			return channelMap.get(ch);
		}
		throw new ChannelNotFoundException();
	}

	/**
	 * reset the mapping data but the used channel ids will still be retained
	 */
	public void resetMap() {
		channelMap = new HashMap<String, Integer>();
	}

	/**
	 * reset all the mapping data and the used channel id data
	 */
	public void resetAll() {
		init();
	}
}
