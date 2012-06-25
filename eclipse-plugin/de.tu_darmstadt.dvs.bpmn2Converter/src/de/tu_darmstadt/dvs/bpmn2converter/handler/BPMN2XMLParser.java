package de.tu_darmstadt.dvs.bpmn2converter.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;




public class BPMN2XMLParser {

	public Element loadFile(String configFile) {
		File file = new File(configFile);
		if (!file.isFile()) {
			throw new IllegalArgumentException("File is a directory!");
		}
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			return loadXML(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {					
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	private Element loadXML(InputStream in){
		SAXBuilder builder = new SAXBuilder();
		//builder.setValidation(false);
		builder.setIgnoringElementContentWhitespace(true);		
		try {
			Document doc = builder.build(in);			
			Element root = doc.getRootElement();
			
			return root;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
