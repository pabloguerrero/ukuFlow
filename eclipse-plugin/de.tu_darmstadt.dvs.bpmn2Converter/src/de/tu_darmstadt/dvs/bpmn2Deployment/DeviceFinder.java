package de.tu_darmstadt.dvs.bpmn2Deployment;

import gnu.io.CommPortIdentifier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DeviceFinder {
	public final static int WINDOWS_OS = 0;
	public final static int LINUX_OS = 1;
	public final static int MAC_OS = 2;

	private int os = -1;
	private static DeviceFinder INSTANCE = null;

	private DeviceFinder() {
		// TODO find out which os is that;
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("win"))
			os = 0;
		else if (os_name.contains("nix") || os_name.contains("nux")) {
			os = 1;
		} else if (os_name.contains("mac")) {
			os = 2;
		}
	}

	public static DeviceFinder getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeviceFinder();
		}
		return INSTANCE;
	}

	public HashMap<String, String> getDevices() {
		switch (os) {
		case 0:
			return getDevices_windows();
		case 1:
			return getDevices_linux();
		case 2:
			return getDevices_mac();
		default:
			System.err.println("your operating system is not supported yet");
			return null;
			// TODO:
		}
	}

	private List<String> portList() {
		LinkedList<String> portName = new LinkedList<String>();
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {

			CommPortIdentifier portIdentifier = portEnum.nextElement();
			// System.out.println(portIdentifier.getName() + " - " +
			// getPortTypeName(portIdentifier.getPortType())
			// +":"+portIdentifier.getPortType());
			portName.add(portIdentifier.getName());
		}
		return portName;
	}

	private HashMap<String, String> getDevices_windows() {
		HashMap<String, String> staticDevice = WinRegistry.getFTDIDevices();
		HashMap<String, String> result = new HashMap<String, String>();
		List<String> portName = portList();
		
		for (String port : portName) {
			if (staticDevice.containsKey(port)) {
				result.put(port, staticDevice.get(port));
			}
		}
		
		return result;
	}

	private HashMap<String, String> getDevices_linux() {
		// TODO:
		return null;
	}

	private HashMap<String, String> getDevices_mac() {
		// TODO:
		return null;
	}

	/**
	 * test
	 */
	public static void main(String[] args) {
		DeviceFinder df = DeviceFinder.getInstance();
		System.out.println(df.getDevices());
	}
}
