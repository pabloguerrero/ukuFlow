package de.tudarmstadt.dvs.ukuflow.deployment;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import de.tudarmstadt.dvs.ukuflow.handler.UkuConsole;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class DeviceManager {
	public static Object lock = new Object();
	public final static int WINDOWS_OS = 0;
	public final static int LINUX_OS = 1;
	public final static int MAC_OS = 2;
	private Map<String, String> devs;
	private int os = -1;
	private static DeviceManager INSTANCE = null;
	private static UkuConsole console = UkuConsole.getConsole();
	static LinkedBlockingDeque<String> usedPort = new LinkedBlockingDeque<String>();
	private static BpmnLog log = BpmnLog.getInstance(DeviceManager.class
			.getSimpleName());

	private DeviceManager() {
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("win"))
			os = 0;
		else if (os_name.contains("nix") || os_name.contains("nux")) {
			os = 1;
		} else if (os_name.contains("mac")) {
			os = 2;
		}
	}

	public static DeviceManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeviceManager();
		}
		return INSTANCE;
	}

	public Map<String, String> getDevices() {
		switch (os) {
		case 0:
			devs = getDevices_windows();
			break;
		case 1:
			devs = getDevices_linux();
			break;
		case 2:
			devs = getDevices_mac();
			break;
		default:
			System.err.println("your operating system is not supported yet");
			return null;
			// TODO:
		}
		for (String used : usedPort) {
			devs.remove(used);
		}
		return devs;
	}

	private Set<String> portList(Set<String> ports) {
		Set<String> portName = new HashSet<String>();
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			String name = portIdentifier.getName();
			if (usedPort.contains(name)) {
				System.out.println("port " + name + " is currently in use");
				continue;
			}
			if (!ports.contains(name)) {
				System.out.println(name + " is not a sensor device");
				continue;
			}
			if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				CommPort cp;
				try {
					cp = portIdentifier.open("testopen", 50);
					cp.close();
					portName.add(portIdentifier.getName());
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
		return portName;
	}

	private Map<String, String> getDevices_windows() {
		// sky mote
		Map<String, String> staticDevice = DeviceFinderWindows.getFTDIDevices();
		// for z1 mote
		staticDevice.putAll(DeviceFinderWindows.getZ1Devices());
		return getAvailableDevices(staticDevice);
	}

	/**
	 * 
	 * @param staticDev
	 * @param devs
	 * @return
	 */
	private Map<String, String> getAvailableDevices(
			Map<String, String> staticDev) {
		Set<String> devs = portList(staticDev.keySet());
		Map<String, String> result = new HashMap<String, String>();

		/*
		 * merge the list of node from windows registry and the list of
		 * available nodes
		 */
		for (String port : devs) {
			if (staticDev.containsKey(port)) {
				result.put(port, staticDev.get(port));
			}
		}
		return result;

	}

	private Map<String, String> getDevices_linux() {
		DeviceFinderLinux df = new DeviceFinderLinux();
		return getAvailableDevices(df.getDevices());
	}

	private Map<String, String> getDevices_mac() {
		return getAvailableDevices(DeviceFinderMac.getDevs());
	}

	public void deploy(String portName, String fileName, int timeout) {
		File f = new File(fileName);
		if(!f.exists()){
			console.error("file \""+fileName +"\" doesn't exist");
			return;
		}
		deploy(portName, f, timeout);
	}

	public void deploy(String portName, File file, int timeout) {
		SerialPort serialPort = null;
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()
					|| usedPort.contains(portName)) {

				console.error("ERROR", portName + " is currently in use");
			} else {
				CommPort commPort = null;
				commPort = portIdentifier.open(this.getClass().getName(), 2000);

				if (commPort instanceof SerialPort) {
					usedPort.add(portName);
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(115200,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();

					(new Thread(new SerialReader(in, timeout, serialPort,
							portName))).start();
					(new Thread(
							new SerialWriter(out, new FileInputStream(file))))
							.start();
				} else {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			usedPort.remove(portName);
			if (serialPort != null)
				serialPort.close();
			console.error("ERROR", portName + " is busy");
		}

	}

	public static class SerialReader implements Runnable {
		InputStream in;
		int timeout = 10000;
		SerialPort serialPort;
		String portName;
		BufferedReader br;

		public SerialReader(InputStream in, int timeout, SerialPort port,
				String portName) {
			this.in = in;
			br = new BufferedReader(new InputStreamReader(in));
			this.timeout = timeout;
			this.serialPort = port;
			this.portName = portName;

		}

		public void run() {
			char[] buffer = new char[1024];
			int len = -1;
			String newLine = "";
			long startTime = System.currentTimeMillis();
			try {
				// while ((len = in.available()) >= 0) {
				while (true) {
					if (startTime + timeout < System.currentTimeMillis()) {
						console.out("SYSTEM", portName + " is released");
						break;
					}
					// if (len == 0) {
					// continue;
					// }
					synchronized (lock) {
						if (br.ready()) {
							len = br.read(buffer);

							String tmp = new String(buffer, 0, len);
							System.out.println("[" + tmp + "]");
							if (tmp.contains("\n")) {
								String tmps[] = tmp.split("\n");
								if (tmps.length <= 0) {
									console.out(portName, newLine);
									newLine = "";
									continue;
								}
								if (tmp.startsWith("\n")) {
									console.out(portName, newLine);
									newLine = "";
								}
								for (int i = 0; i < tmps.length - 1; i++) {
									newLine += tmps[i];
									console.out(portName, newLine);
									newLine = "";
								}
								newLine += tmps[tmps.length - 1];
								if (tmp.endsWith("\n")) {
									console.out(portName, newLine);
									newLine = "";
								}

							} else {
								newLine += tmp;
							}
						}
					}
				}

			} catch (IOException e) {
				console.info("SYSTEM", "device may be disconnected");
			} finally {
				usedPort.remove(portName);
				try {
					// in.close();
					// serialPort.getOutputStream().flush();
					// serialPort.getOutputStream().close();
				} catch (Exception e) {
				}
				if (serialPort != null)
					serialPort.close();
			}
		}

	}

	public static class SerialWriter implements Runnable {
		OutputStream out;
		InputStream in;

		public SerialWriter(OutputStream out, InputStream in) {
			this.out = out;
			this.in = in;
		}

		public void run() {
			try {
				int c = 0;
				int last1 = 0, last2 = 0;
				while ((c = in.read()) > -1) {
					synchronized (lock) {
						this.out.write(c);
						last2 = last1;
						last1 = c;
					}
				}
				if (last2 != 13 & last1 != 10) {
					synchronized (lock) {
						/* send "carriage return" symbol */
						if (last1 == 13) {
							this.out.write(10);
						} else {
							this.out.write(13);
							/* send "new line" symbol */
							this.out.write(10);
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// out.flush();
				// out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
