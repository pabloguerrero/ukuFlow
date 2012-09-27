package de.tudarmstadt.dvs.ukuflow.script.eventbasescript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.tools.exception.ChannelExistException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.ChannelNotFoundException;
import de.tudarmstadt.dvs.ukuflow.tools.exception.TooManyChannelException;

public class ChannelIDManager {
	private Map<String, Integer> channelMap;// = new HashMap<String, Integer>();
	private List<Integer> availableID = new ArrayList<Integer>();
	private ChannelIDManager instance = null;

	private void init() {
		for (int i = 0; i < 256; i++)
			availableID.add(i);
		channelMap = new HashMap<String, Integer>();
	}

	public ChannelIDManager getInstance() {
		if (instance == null)
			instance = new ChannelIDManager();
		return instance;
	}

	private ChannelIDManager() {
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
