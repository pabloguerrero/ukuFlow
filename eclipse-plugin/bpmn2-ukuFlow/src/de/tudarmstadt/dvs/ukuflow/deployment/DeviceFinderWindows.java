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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class DeviceFinderWindows {

	static BpmnLog log = BpmnLog.getInstance("DeviceFinderWindows");
	public static final int HKEY_CURRENT_USER = 0x80000001;
	public static final int HKEY_LOCAL_MACHINE = 0x80000002;
	public static final int REG_SUCCESS = 0;
	public static final int REG_NOTFOUND = 2;
	public static final int REG_ACCESSDENIED = 5;

	private static final int KEY_ALL_ACCESS = 0xf003f;
	private static final int KEY_READ = 0x20019;

	private static Preferences userRoot = Preferences.userRoot();
	private static Preferences systemRoot = Preferences.systemRoot();
	private static Class<? extends Preferences> userClass = userRoot.getClass();
	private static Method regOpenKey = null;
	private static Method regCloseKey = null;
	private static Method regQueryValueEx = null;
	private static Method regEnumValue = null;
	private static Method regQueryInfoKey = null;
	private static Method regEnumKeyEx = null;
	private static Method regCreateKeyEx = null;
	private static Method regSetValueEx = null;
	private static Method regDeleteKey = null;
	private static Method regDeleteValue = null;

	static {
		try {
			regOpenKey = userClass.getDeclaredMethod("WindowsRegOpenKey",
					new Class[] { int.class, byte[].class, int.class });
			regOpenKey.setAccessible(true);
			regCloseKey = userClass.getDeclaredMethod("WindowsRegCloseKey",
					new Class[] { int.class });
			regCloseKey.setAccessible(true);
			regQueryValueEx = userClass.getDeclaredMethod(
					"WindowsRegQueryValueEx", new Class[] { int.class,
							byte[].class });
			regQueryValueEx.setAccessible(true);
			regEnumValue = userClass.getDeclaredMethod("WindowsRegEnumValue",
					new Class[] { int.class, int.class, int.class });
			regEnumValue.setAccessible(true);
			regQueryInfoKey = userClass.getDeclaredMethod(
					"WindowsRegQueryInfoKey1", new Class[] { int.class });
			regQueryInfoKey.setAccessible(true);
			regEnumKeyEx = userClass.getDeclaredMethod("WindowsRegEnumKeyEx",
					new Class[] { int.class, int.class, int.class });
			regEnumKeyEx.setAccessible(true);
			regCreateKeyEx = userClass.getDeclaredMethod(
					"WindowsRegCreateKeyEx", new Class[] { int.class,
							byte[].class });
			regCreateKeyEx.setAccessible(true);
			regSetValueEx = userClass.getDeclaredMethod("WindowsRegSetValueEx",
					new Class[] { int.class, byte[].class, byte[].class });
			regSetValueEx.setAccessible(true);
			regDeleteValue = userClass.getDeclaredMethod(
					"WindowsRegDeleteValue", new Class[] { int.class,
							byte[].class });
			regDeleteValue.setAccessible(true);
			regDeleteKey = userClass.getDeclaredMethod("WindowsRegDeleteKey",
					new Class[] { int.class, byte[].class });
			regDeleteKey.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DeviceFinderWindows() {

	}

	/**
	 * Read a value from key and value name
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @param valueName
	 * @return the value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static String readString(int hkey, String key, String valueName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) {
			return readString(systemRoot, hkey, key, valueName);
		} else if (hkey == HKEY_CURRENT_USER) {
			return readString(userRoot, hkey, key, valueName);
		} else {
			throw new IllegalArgumentException("hkey=" + hkey);
		}
	}

	/**
	 * Read value(s) and value name(s) form given key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @return the value name(s) plus the value(s)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static Map<String, String> readStringValues(int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) {
			return readStringValues(systemRoot, hkey, key);
		} else if (hkey == HKEY_CURRENT_USER) {
			return readStringValues(userRoot, hkey, key);
		} else {
			throw new IllegalArgumentException("hkey=" + hkey);
		}
	}

	/**
	 * Read the value name(s) from a given key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @return the value name(s)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static List<String> readStringSubKeys(int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) {
			return readStringSubKeys(systemRoot, hkey, key);
		} else if (hkey == HKEY_CURRENT_USER) {
			return readStringSubKeys(userRoot, hkey, key);
		} else {
			throw new IllegalArgumentException("hkey=" + hkey);
		}
	}

	/**
	 * Create a key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void createKey(int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		int[] ret;
		if (hkey == HKEY_LOCAL_MACHINE) {
			ret = createKey(systemRoot, hkey, key);
			regCloseKey
					.invoke(systemRoot, new Object[] { new Integer(ret[0]) });
		} else if (hkey == HKEY_CURRENT_USER) {
			ret = createKey(userRoot, hkey, key);
			regCloseKey.invoke(userRoot, new Object[] { new Integer(ret[0]) });
		} else {
			throw new IllegalArgumentException("hkey=" + hkey);
		}
		if (ret[1] != REG_SUCCESS) {
			throw new IllegalArgumentException("rc=" + ret[1] + "  key=" + key);
		}
	}

	/**
	 * Write a value in a given key/value name
	 * 
	 * @param hkey
	 * @param key
	 * @param valueName
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void writeStringValue(int hkey, String key,
			String valueName, String value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) {
			writeStringValue(systemRoot, hkey, key, valueName, value);
		} else if (hkey == HKEY_CURRENT_USER) {
			writeStringValue(userRoot, hkey, key, valueName, value);
		} else {
			throw new IllegalArgumentException("hkey=" + hkey);
		}
	}

	/**
	 * Delete a given key
	 * 
	 * @param hkey
	 * @param key
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void deleteKey(int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		int rc = -1;
		if (hkey == HKEY_LOCAL_MACHINE) {
			rc = deleteKey(systemRoot, hkey, key);
		} else if (hkey == HKEY_CURRENT_USER) {
			rc = deleteKey(userRoot, hkey, key);
		}
		if (rc != REG_SUCCESS) {
			throw new IllegalArgumentException("rc=" + rc + "  key=" + key);
		}
	}

	/**
	 * delete a value from a given key/value name
	 * 
	 * @param hkey
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void deleteValue(int hkey, String key, String value)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		int rc = -1;
		if (hkey == HKEY_LOCAL_MACHINE) {
			rc = deleteValue(systemRoot, hkey, key, value);
		} else if (hkey == HKEY_CURRENT_USER) {
			rc = deleteValue(userRoot, hkey, key, value);
		}
		if (rc != REG_SUCCESS) {
			throw new IllegalArgumentException("rc=" + rc + "  key=" + key
					+ "  value=" + value);
		}
	}

	// =====================

	private static int deleteValue(Preferences root, int hkey, String key,
			String value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		int[] handles = (int[]) regOpenKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key), new Integer(KEY_ALL_ACCESS) });
		if (handles[1] != REG_SUCCESS) {
			return handles[1]; // can be REG_NOTFOUND, REG_ACCESSDENIED
		}
		int rc = ((Integer) regDeleteValue.invoke(root, new Object[] {
				new Integer(handles[0]), toCstr(value) })).intValue();
		regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
		return rc;
	}

	private static int deleteKey(Preferences root, int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		int rc = ((Integer) regDeleteKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key) })).intValue();
		return rc; // can REG_NOTFOUND, REG_ACCESSDENIED, REG_SUCCESS
	}

	private static String readString(Preferences root, int hkey, String key,
			String value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		int[] handles = (int[]) regOpenKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key), new Integer(KEY_READ) });
		if (handles[1] != REG_SUCCESS) {
			return null;
		}
		// TODO markd
		byte[] valb = (byte[]) regQueryValueEx.invoke(root, new Object[] {
				new Integer(handles[0]), toCstr(value) });
		regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
		return (valb != null ? new String(valb).trim() : null);
	}

	private static Map<String, String> readStringValues(Preferences root,
			int hkey, String key) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		HashMap<String, String> results = new HashMap<String, String>();
		int[] handles = (int[]) regOpenKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key), new Integer(KEY_READ) });
		if (handles[1] != REG_SUCCESS) {
			return null;
		}
		int[] info = (int[]) regQueryInfoKey.invoke(root,
				new Object[] { new Integer(handles[0]) });
		/*
		 * int i = 0; for(int x : info){ System.out.println((i++)+":"+x); }
		 */
		int count = info[2]; // count TODO
		int maxlen = info[4]; // value length max

		for (int index = 0; index < count; index++) {
			byte[] name = (byte[]) regEnumValue.invoke(root, new Object[] {
					new Integer(handles[0]), new Integer(index),
					new Integer(maxlen + 1) });
			String value = readString(hkey, key, new String(name));
			// System.out.println(index + ":" + new String(name) + "->" +
			// value);
			results.put(new String(name).trim(), value);
		}
		regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
		return results;
	}

	private static List<String> readStringSubKeys(Preferences root, int hkey,
			String key) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		List<String> results = new ArrayList<String>();

		int[] handles = (int[]) regOpenKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key), new Integer(KEY_READ) });
		if (handles[1] != REG_SUCCESS) {
			return null;
		}

		int[] info = (int[]) regQueryInfoKey.invoke(root,
				new Object[] { new Integer(handles[0]) });

		int count = info[0]; // count//TODO original info[2];
		int maxlen = info[3]; // value length max
		// TODO
		for (int index = 0; index < count; index++) {
			byte[] name = (byte[]) regEnumKeyEx.invoke(root, new Object[] {
					new Integer(handles[0]), new Integer(index),
					new Integer(maxlen + 1) });
			results.add(new String(name).trim());
		}
		regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
		// System.out.println("here1");
		return results;
	}

	private static int[] createKey(Preferences root, int hkey, String key)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return (int[]) regCreateKeyEx.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key) });
	}

	private static void writeStringValue(Preferences root, int hkey,
			String key, String valueName, String value)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		int[] handles = (int[]) regOpenKey.invoke(root, new Object[] {
				new Integer(hkey), toCstr(key), new Integer(KEY_ALL_ACCESS) });

		regSetValueEx.invoke(root, new Object[] { new Integer(handles[0]),
				toCstr(valueName), toCstr(value) });
		regCloseKey.invoke(root, new Object[] { new Integer(handles[0]) });
	}

	// utility
	private static byte[] toCstr(String str) {
		byte[] result = new byte[str.length() + 1];

		for (int i = 0; i < str.length(); i++) {
			result[i] = (byte) str.charAt(i);
		}
		result[str.length()] = 0;
		return result;
	}

	/**
	 * 
	 * @param hkey
	 * @param value
	 * @param location
	 * @deprecated
	 */
	public static void searchFor(int hkey, String value, String location) {

	}

	public static HashMap<String, String> getZ1Devices() {
		HashMap<String, String> result = new HashMap<String, String>();
		String keySet1 = "SYSTEM\\ControlSet001\\Enum\\USB\\VID_10C4&PID_EA60";
		String keySet2 = "SYSTEM\\ControlSet002\\Enum\\USB\\VID_10C4&PID_EA60";
		String postfixKey = "\\Device Parameters";
		List<String> controlSet;
		try {
			controlSet = readStringSubKeys(HKEY_LOCAL_MACHINE, keySet1);
			if (controlSet != null)
				for (String dev : controlSet) {
					String comport = readString(HKEY_LOCAL_MACHINE, keySet1
							+ "\\" + dev + "\\" + postfixKey, "PortName");
					result.put(comport, dev);
				}
			controlSet = readStringSubKeys(HKEY_LOCAL_MACHINE, keySet2);
			if (controlSet != null)
				for (String dev : controlSet) {
					String comport = readString(HKEY_LOCAL_MACHINE, keySet2
							+ "\\" + dev + "\\" + postfixKey, "PortName");
					result.put(comport, dev);
				}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static HashMap<String, String> getFTDIDevices() {
		HashMap<String, String> result = new HashMap<String, String>();
		String key1 = "SYSTEM\\ControlSet001\\Enum\\FTDIBUS";
		String key2 = "SYSTEM\\ControlSet002\\Enum\\FTDIBUS";
		String postfixKey = "\\0000\\Device Parameters";
		try {

			List<String> controlSet001 = readStringSubKeys(HKEY_LOCAL_MACHINE,
					key1);
			if (controlSet001 != null)
				for (String device : controlSet001) {
					String comport = readString(HKEY_LOCAL_MACHINE, key1 + "\\"
							+ device + postfixKey, "PortName");
					String[] deviceName = device.split("[+]");
					if (comport != null && deviceName.length == 3) {
						result.put(
								comport,
								deviceName[2].substring(0,
										deviceName[2].length() - 1));
					} else
						log.debug("warning: null comport");
				}
			List<String> controlSet002 = readStringSubKeys(HKEY_LOCAL_MACHINE,
					"SYSTEM\\ControlSet002\\Enum\\FTDIBUS");
			if (controlSet002 != null)
				for (String device : controlSet002) {
					String comport = readString(HKEY_LOCAL_MACHINE, key2 + "\\"
							+ device + postfixKey, "PortName");
					String[] deviceName = device.split("[+]");
					if (comport != null && deviceName.length == 3) {
						result.put(
								comport,
								deviceName[2].substring(0,
										deviceName[2].length() - 1));
					} else
						log.info("warning: null comport");
				}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String> searchFor(String key, String folder)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<String> result = new LinkedList<String>();
		List<String> nextFolder = readStringSubKeys(HKEY_LOCAL_MACHINE, folder);
		if (nextFolder != null && nextFolder.size() > 0)
			for (String nkey : nextFolder) {
				if (folder.equals(""))
					result.addAll(searchFor(key, nkey));
				else
					result.addAll(searchFor(key, folder + "\\" + nkey));
			}
		Map<String, String> m = readStringValues(HKEY_LOCAL_MACHINE, folder);
		if (m != null)
			for (String s : m.keySet()) {
				if (m.get(s) != null && m.get(s).equals(key))
					result.add(folder + "//" + key);
			}
		return result;
	}

	/**
	 * test
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		HashMap<String, String> hm = DeviceFinderWindows.getZ1Devices();// WindowsRegistry.getFTDIDevices();
		System.out.println(hm);
		List<String> l = searchFor("COM4", "");
		for (String s : l) {
			System.out.println(s);
		}
	}
}