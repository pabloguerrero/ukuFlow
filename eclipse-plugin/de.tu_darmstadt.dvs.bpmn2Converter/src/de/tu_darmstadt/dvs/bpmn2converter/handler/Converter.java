package de.tu_darmstadt.dvs.bpmn2converter.handler;

import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Converter {
	InputStream input;
	String outFile;
	public Converter(InputStream input, String outputFile){
		this.input = input;
		outFile = outputFile;
	}
	public void execute1(){
		try {
			SAXParser sax = SAXParserFactory.newInstance().newSAXParser();
			XMLReader xmlr = sax.getXMLReader();
			//xmlr.parse(systemId);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public void execute(){		
		Element root = null;
		SAXBuilder builder = new SAXBuilder();
		//builder.setValidation(false);
		builder.setIgnoringElementContentWhitespace(true);	
		Document doc = null;
		try {
			doc = builder.build(input);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root = doc.getRootElement();
		StringBuilder sb = new StringBuilder();
		for(Attribute attrib : root.getAttributes()){
			sb.append(attrib.getName()+"|");
		}
		System.out.println(sb.toString());
	}
}
