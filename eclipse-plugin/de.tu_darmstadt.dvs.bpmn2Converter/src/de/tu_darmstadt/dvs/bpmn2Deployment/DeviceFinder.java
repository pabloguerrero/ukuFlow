package de.tu_darmstadt.dvs.bpmn2Deployment;

import gnu.io.CommPortIdentifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("win"))
			os = 0;
		else if (os_name.contains("nix") || os_name.contains("nux")) {
			os = 1;
		} else if (os_name.contains("mac")) {
			os = 2;
		}
		//test TODO:remove
		//os = 1;
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
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
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
		HashMap<String, String> staticDevice = WindowsRegistry.getFTDIDevices();
		staticDevice.putAll(WindowsRegistry.getZ1Devices());
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
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("perl lib/motelist/motelist-linux -c");
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String,String> result = new HashMap<String, String>();		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		try {
			while((line = br.readLine())!= null){
				String s[] = line.split("[,]");
				if(s.length != 3){
					System.out.println(line);
					continue;
				}
				result.put(s[1], s[0]);
				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return result;
	}

	private HashMap<String, String> getDevices_mac() {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("perl lib/motelist/motelist-mac -c");
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String,String> result = new HashMap<String, String>();		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		try {
			while((line = br.readLine())!= null){
				String s[] = line.split("[,]");
				if(s.length != 3){
					System.out.println(line);
					continue;
				}
				result.put(s[1], s[0]);
				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * test
	 */
	public static void main(String[] args) {
		DeviceFinder df = DeviceFinder.getInstance();
		System.out.println(df.getDevices());
	}
}
