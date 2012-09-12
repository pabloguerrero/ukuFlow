package de.tudarmstadt.dvs.ukuflow.deployment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceFinderLinux {
	String[] dev_prefix = { "/dev/usb/tts/", "/dev/ttyUSB", "/dev/tts/USB" };
	String kernel;

	public DeviceFinderLinux() {
		kernel = getKenerl();
		if (kernel.equals("2.4")) {
			// not supported yet
		}
	}

	private String runCommand(String cmd) {
		Process p = null;
		String result = "";
		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getKenerl() {
		String s = runCommand("cat /proc/version");
		int ind = s.indexOf("version") + 8;
		return s.substring(ind, ind + 3);
	}

	public Map<String, String> getFTDIDevices() {
		Map<String, String> result = new HashMap<String, String>();
		File f = new File("/sys/bus/usb/drivers/usb/");
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (File fx : fs) {
				if (fx.isFile())
					continue;
				String usbVendor = getFileContent(fx.getAbsolutePath()
						+ "/idVendor");
				if (!usbVendor.startsWith("10c4"))
					continue;
				String usbProduct = getFileContent(fx.getAbsolutePath()
						+ "/idProduct");
				if (!usbProduct.startsWith("ea60"))
					continue;
				String ProductString = getFileContent(fx.getAbsolutePath()
						+ "/product");
				if (!ProductString.startsWith("Zolertia Z1"))
					continue;
				String infoSerial = getFileContent(fx.getAbsolutePath()
						+ "/serial").replace('\n', ' ');
				if (infoSerial == null)
					infoSerial = ProductString.replace('\n', ' ');
				String devPath = getDevInfo(fx.getAbsolutePath(), fx.getName());
				if (devPath != null) {
					result.put(devPath,infoSerial);
				}
			}
		}
		return result;
	}

	private String getDevInfo(String path, String fname) {
		String port = path + "/" + fname + ":1.0";
		File f = new File(port);
		int portNo = 0;
		String tty = null;
		for (String s : f.list()) {
			if (s.startsWith("tty")) {
				tty = s;
				break;
			}
		}
		if (tty != null) {
			boolean started = false;
			for (char c : tty.toCharArray()) {
				if ('0' <= c && c <= '9') {
					started = true;
					portNo *= 10;
					portNo += c - '0';
				} else if (started) {
					break;
				}
			}
		}
		for (String prefix : dev_prefix) {
			File ff = new File(prefix + portNo);
			if (ff.exists())
				return prefix + portNo;
		}
		return null;
	}

	private String getFileContent(String fname) {
		return getFileContent(new File(fname));
	}

	private String getFileContent(File f) {
		String content = "";
		try {
			FileInputStream fis = new FileInputStream(f);
			int ch;
			while ((ch = fis.read()) != -1) {
				content += (char) ch;
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return content;
	}

	public static void main(String[] args) {
		DeviceFinderLinux dfl = new DeviceFinderLinux();
		System.out.println(dfl.getFTDIDevices());
	}
}
