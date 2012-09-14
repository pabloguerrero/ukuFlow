package de.tudarmstadt.dvs.ukuflow.deployment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public class DeviceFinderMac {
	private final static String tmote_prefix = "/dev/tty.usbserial-";
	private final static String z1_prefix = "/dev/tty.SLAB_";
	
	/**
	 * don't know if it works under Macos 10.7.4
	 * @return
	 */
	public static Map<String, String> getDevs() {
		Map<String, String> result = new HashMap<String, String>();
		File f = new File("/dev/");
		String[] devs = f.list(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg0.getAbsolutePath().equals("/dev/")
						&& (arg1.startsWith("tty.usbserial-")||arg1.startsWith("tty.SLAB_"));
			}
		});
		for (String s : devs) {
			if(s.startsWith(tmote_prefix))
				result.put(s, s.substring(tmote_prefix.length()));
			if(s.startsWith(z1_prefix))
				result.put(s, s.substring(z1_prefix.length()));
		}
		return result;
	}
}
