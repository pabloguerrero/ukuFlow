package de.tudarmstadt.dvs.ukuflow.hash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String getHash(InputStream is) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		DigestInputStream dis = new DigestInputStream(is, md);
		try {
			while (dis.read() != -1)
				;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesToHex(md.digest());
	}

	public static String getHash(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(str.getBytes());

		return bytesToHex(md.digest());
	}

	public static String getHash(File file) {
		InputStream is = null;
		try {
			is = Files.newInputStream(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getHash(is);
	}

	public static void main(String[] args) {
		String str1 = "alsdjflkajsdfl;jadsl;fjal;dskjf;";
		String hash1 = getHash(str1);
		System.out.println(hash1);
		long start = System.currentTimeMillis();
		System.out.println(getHash(str1));
		System.out.println(System.currentTimeMillis() - start + "ms");
	}
}
