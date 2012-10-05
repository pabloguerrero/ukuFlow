package de.tudarmstadt.dvs.ukuflow.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QuickFileReader {
	/**
	 * Get content of a file 
	 * @param fname : file name
	 * @return
	 */
	public static String getFileContent(String fname) {
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
}
