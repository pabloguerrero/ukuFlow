package de.tudarmstadt.dvs.ukuflow.deployment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DeviceFinderMac {
	private final static String dev_folder = "/dev/";
	private final static String tmote = "tty.usbserial-";
	private final static String z1 = "tty.SLAB_";

	/**
	 * 
	 * 
	 * @return
	 */
	public static Map<String, String> getDevs() {
		Map<String, String> result = new HashMap<String, String>();
		File f = new File(dev_folder);

		for (String s : f.list()) {
			if (s.startsWith(tmote))
				result.put(dev_folder + s, s.substring(tmote.length()));
			if (s.startsWith(z1))
				result.put(dev_folder + s, s.substring(z1.length()));
		}
		return result;
	}
}
