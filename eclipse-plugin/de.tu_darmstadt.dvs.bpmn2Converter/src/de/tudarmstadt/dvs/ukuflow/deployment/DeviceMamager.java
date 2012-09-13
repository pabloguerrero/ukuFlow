package de.tudarmstadt.dvs.ukuflow.deployment;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tudarmstadt.dvs.ukuflow.handler.ConvertCommand;
import de.tudarmstadt.dvs.ukuflow.handler.UkuConsole;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class DeviceMamager {
	public final static int WINDOWS_OS = 0;
	public final static int LINUX_OS = 1;
	public final static int MAC_OS = 2;
	private Map<String, String> devs;
	private int os = -1;
	private static DeviceMamager INSTANCE = null;
	private static UkuConsole console = UkuConsole.getConsole();
	static LinkedBlockingDeque<String> usedPort = new LinkedBlockingDeque<String>();
	private static BpmnLog log = BpmnLog.getInstance(DeviceMamager.class
			.getSimpleName());

	private DeviceMamager() {
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("win"))
			os = 0;
		else if (os_name.contains("nix") || os_name.contains("nux")) {
			os = 1;
		} else if (os_name.contains("mac")) {
			os = 2;
		}
	}

	public static DeviceMamager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeviceMamager();
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

	private List<String> portList() {
		LinkedList<String> portName = new LinkedList<String>();
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			portName.add(portIdentifier.getName());
		}
		return portName;
	}

	private Map<String, String> getDevices_windows() {
		Map<String, String> staticDevice = WindowsRegistry.getFTDIDevices(); // for
																				// sky
																				// mote
		staticDevice.putAll(WindowsRegistry.getZ1Devices()); // for z1 mote
		HashMap<String, String> result = new HashMap<String, String>();
		List<String> portName = portList();
		/*
		 * merge the list of node from windows registry and the list of
		 * available nodes
		 */
		for (String port : portName) {
			if (staticDevice.containsKey(port)) {
				result.put(port, staticDevice.get(port));
			}
		}

		return result;
	}

	private Map<String, String> getDevices_linux() {

		DeviceFinderLinux df = new DeviceFinderLinux();
		return df.getFTDIDevices();
	}

	private Map<String, String> getNodeWithCommand(String command) {
		File f = new File(command);
		String motelist_command = "";
		try {
			FileInputStream fis = new FileInputStream(f);
			int t;
			while ((t = fis.read()) != -1) {
				char ch = (char) t;
				if (ch != '\n')
					motelist_command += ch;
				else
					motelist_command += " ";
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Process p = null;
		try {
			System.out.println(motelist_command);
			Runtime.getRuntime().exec(
					"echo " + motelist_command + " > tmp_motelist.pl");
			p = Runtime.getRuntime().exec("ls");
			// Runtime.getRuntime().exec("rm tmp_motelist.pl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> result = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		System.out.println("here");
		try {
			while ((line = br.readLine()) != null) {
				System.out.print(line);
				String s[] = line.split("[,]");
				if (s.length != 3) {
					System.out.println("< ignored");
					continue;
				}
				System.out.println("< ok");
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
		HashMap<String, String> result = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				String s[] = line.split("[,]");
				System.out.println(line);

				if (s.length != 3) {
					continue;
				}
				result.put(s[1], s[0]);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void deploy(String portName, String fileName, int timeout) {
		deploy(portName, new File(fileName), timeout);
	}

	public void deploy(String portName, File file, int timeout) {
		System.out.println(portName + ">" + portList());
		SerialPort serialPort = null;
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()
					|| usedPort.contains(portName)) {

				console.error("ERROR", portName + " is currently in use");
			} else {
				CommPort commPort = null;
				commPort = portIdentifier.open(this.getClass().getName(),
						timeout);
				usedPort.add(portName);
				if (commPort instanceof SerialPort) {
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
				while ((len = in.available()) >= 0) {
					if (startTime + timeout < System.currentTimeMillis()) {
						console.out("SYSTEM", portName + " is released");
						break;
					}
					if (len == 0) {
						continue;
					}
					if (br.ready()) {
						System.out.println("blocking");
						len = br.read(buffer);
						System.out.println("release");

						String tmp = new String(buffer, 0, len);
						if (tmp.contains("\n")) {
							String tmps[] = tmp.split("\n");
							if (tmps.length <= 0) {
								console.out(portName, newLine);
								newLine = "";
								continue;
							}
							if (tmp.startsWith("\n")) {
								console.out(portName, newLine);
								newLine = tmps[0];
							} else {
								newLine += tmps[0];
								console.out(portName, newLine);
								newLine = "";
							}
							if (tmps.length > 1) {
								for (int i = 1; i < tmps.length - 1; i++) {
									console.out(portName, tmps[i]);
								}
								newLine = tmps[tmps.length - 1];
							}
						} else {
							newLine += tmp;
						}
					}
				}

				usedPort.remove(portName);
				serialPort.close();
				in.close();

			} catch (IOException e) {
				// e.printStackTrace();
				console.info("SYSTEM", "device may be disconnected");
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
					this.out.write(c);
					last2 = last1;
					last1 = c;
				}
				if (last2 != 13 & last1 != 10) {
					/* send "carriage return" symbol */
					if (last1 == 13) {
						this.out.write(10);
					} else {
						this.out.write(13);
						/* send "new line" symbol */
						this.out.write(10);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("done");
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
