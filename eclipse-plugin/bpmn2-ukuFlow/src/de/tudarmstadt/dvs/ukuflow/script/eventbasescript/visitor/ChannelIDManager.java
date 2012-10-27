package de.tudarmstadt.dvs.ukuflow.script.eventbasescript.visitor;

import java.util.HashMap;
import java.util.Map;

public class ChannelIDManager {
	private static ChannelIDManager instance = null;
	byte id = 0;
	private Map<String, Byte> reg = new HashMap<String, Byte>();
	private ChannelIDManager(){	
		//nothing todo;
	}
	public static ChannelIDManager getInstance(){
		if(instance==null)
			instance = new ChannelIDManager();
		return instance;
	}
	/**
	 * reset everything to initiation state
	 */
	public void reset(){
		id = 0;
		reg = new HashMap<String, Byte>();
	}
	/**
	 * keep the used ids, but remove all registered channel.
	 * 
	 */
	public void removeAllRegisteredChannel(){
		reg = new HashMap<String, Byte>();
	}
	
	public byte getChannelID(String regs){
		if(reg.containsKey(regs))
			return reg.get(regs);
		reg.put(regs,id);
		id++;
		return reg.get(regs);
	}
}
