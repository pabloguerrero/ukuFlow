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

package de.tudarmstadt.dvs.ukuflow.deployment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class DeviceFinderLinux {
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	String[] dev_prefix = { "/dev/usb/tts/", "/dev/ttyUSB", "/dev/tts/USB" };
	String kernel;

	public DeviceFinderLinux() {
		kernel = getKernel();
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
	
	/**
	 * Get kernel version of linux system 
	 * @return
	 */
	private String getKernel() {
		String s = runCommand("cat /proc/version");
		int ind = s.indexOf("version") + 8;
		return s.substring(ind, ind + 3);
	}
	
	/**
	 * fetching z1 and tmote sky devices
	 * 
	 * @return map of device identification and port id
	 */
	public Map<String, String> getDevices() {
		Map<String, String> result = new HashMap<String, String>();
		File f = new File("/sys/bus/usb/drivers/usb/");
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (File fx : fs) {
				if (fx.isFile())
					continue;
				String usbVendor = getFileContent(fx.getAbsolutePath()
						+ "/idVendor");
				String usbProduct = getFileContent(fx.getAbsolutePath()
						+ "/idProduct");
				String ProductString = getFileContent(fx.getAbsolutePath()
						+ "/product");
				boolean found = false;
				if (usbVendor.startsWith("10c4")
						&& usbProduct.startsWith("ea60")
						&& ProductString.startsWith("Zolertia Z1")) {
					found = true;
					log.debug("z1 found");
				}
				if (usbVendor.startsWith("0403")
						&& usbProduct.startsWith("6001")) {
					found = true;
					log.debug("sky found");
				}
				if (!found) {
					continue;
				}
				String infoSerial = getFileContent(
						fx.getAbsolutePath() + "/serial").replace('\n', ' ');
				if (infoSerial == null)
					infoSerial = ProductString.replace('\n', ' ');
				String devPath = getDevInfo(fx.getAbsolutePath(), fx.getName());
				if (devPath != null) {
					result.put(devPath, infoSerial);
				}
			}
		}
		return result;
	}
	/**
	 * 
	 * @param path
	 * @param fname
	 * @return
	 */
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
	
	/**
	 * Get content of a file 
	 * @param fname : file name
	 * @return
	 */
	private String getFileContent(String fname) {
		return getFileContent(new File(fname));
	}
	
	/**
	 * Get content of a file.
	 * @param f
	 * @return
	 */
	public static String getFileContent(File f) {
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
	/*
	 //testing
	public static void main(String[] args) {
		DeviceFinderLinux dfl = new DeviceFinderLinux();
		System.out.println(dfl.getFTDIDevices());
	}
	*/
}
