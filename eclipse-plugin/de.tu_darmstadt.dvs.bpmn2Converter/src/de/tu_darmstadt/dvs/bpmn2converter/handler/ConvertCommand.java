package de.tu_darmstadt.dvs.bpmn2converter.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

//import org.jdom2.

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.resources.IFile;
import org.jdom2.Attribute;
import org.jdom2.Element;

import de.tu_darmstadt.dvs.bpmn2Deployment.DeviceFinder;
import de.tu_darmstadt.dvs.bpmn2converter.util.TypeClassifier;
import de.tu_darmstadt.dvs.bpmn2converter.util.XMLNode;

public class ConvertCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("testing get devices");
		System.out.println(DeviceFinder.getInstance().getDevices());
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		
		Object firstElement = selection.getFirstElement();
		System.out.println(selection.size() + " elements");
		if(selection.size() != 1) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "Information",	"Please choose just one BPMN2 file");
			return null;
		}
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;
			String extension =file.getFileExtension();
			System.out.println("location:" +file.getLocation());
			String oFileLocation = file.getLocation().toOSString();
			System.out.println(extension);
			
			String nfileLocation = oFileLocation + "\n";
			nfileLocation = nfileLocation.replace(extension+"\n", "ukuf");
			if (extension.equals("bpmn") || extension.equals("bpmn2")) {
				//TODO: check, convert bpmn code to Ukuflow byte code
				
				System.out.println("checking..");
				System.out.println("converting..");			
				BPMN2XMLParser parser = new BPMN2XMLParser();
				Element e = parser.loadFile(oFileLocation);
				XMLNode root = new XMLNode(e);
				//printElement(e, "");
				printProcesses(e);
				//new Converter(input,null).execute();
				//Converter.execute(input);
				//new Converter(input, "").execute();
				/*	
				File f = new File(oFileLocation);				
				FileWriter fwrite = null;
				try {
					fwrite = new FileWriter(f);
					fwrite.write(content);
					fwrite.flush();
					fwrite.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				*/
			} else {
				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "Information",	"Please select a BPMN or BPMN2 source file");
			}

		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),"Information", "Please select a BPMN or BPMN2 source file");
		}
		/*
		if (firstElement instanceof ICompilationUnit) {
			ICompilationUnit cu = (ICompilationUnit) firstElement;
			IResource res = cu.getResource();
			boolean newDirectory = true;
			directory = getPersistentProperty(res, path);
			System.out.println("directory: " + directory);
			if (directory != null && directory.length() > 0) {
				newDirectory = !(MessageDialog.openQuestion(
						HandlerUtil.getActiveShell(event), "Question",
						"Use the previous output directory?"));
			}
			if (newDirectory) {
				DirectoryDialog fileDialog = new DirectoryDialog(
						HandlerUtil.getActiveShell(event));
				directory = fileDialog.open();

			}
			if (directory != null && directory.length() > 0) {
				analyze(cu);
				setPersistentProperty(res, path, directory);
				write(directory, cu);
			}

		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please select a Java source file");
		}*/
		return null;
	}
/*
	protected String getPersistentProperty(IResource res, QualifiedName qn) {
		try {
			return res.getPersistentProperty(qn);
		} catch (CoreException e) {
			return "";
		}
	}
*/
	// TODO: Include this in the HTML output

	
/*
	protected void setPersistentProperty(IResource res, QualifiedName qn,
			String value) {
		try {
			res.setPersistentProperty(qn, value);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
*/
	private String readFile(InputStream is){
		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		Reader in = null;
		
		try {
			in = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int read = -1;
		do {
			try{
				read = in.read(buffer, 0, buffer.length);
			} catch (IOException e) {
				// TODO: handle exception
			}
		  if (read>0) {
		    out.append(buffer, 0, read);
		  }
		} while (read>=0);
		
		String result = out.toString();
		return result;
	}
	private void printElement(Element e, String prefix){
		System.out.print(prefix);
		
		System.out.print(e.getName()+":"+e.getTextTrim());
		System.out.print(" -> (");
		for(Attribute attrb : e.getAttributes()){
			System.out.print(attrb.getName() + ":" + attrb.getValue() + "; ");
		}
		System.out.println(")");
		for(Element child : e.getChildren()){
			printElement(child, prefix + "   ");
		}
	}
	private void printProcesses(Element e){
		if(e.getName().equals("definitions")){
			for(Element ex : e.getChildren()){
				if(ex.getName().equals("process")){
					printProcesses(ex);
				}
			}
		}
		if(e.getName().equals("process")){
			Map<String, Element> connector = new HashMap<String, Element>();
			Map<String,Element> elements = new HashMap<String, Element>();
			List<String> connector_Id = new ArrayList<String>();
			
			TypeClassifier a = TypeClassifier.getInstance();
			for (Element child : e.getChildren()) {
				switch (a.getType(child.getName())) {
				case 1:
					connector.put(child.getAttributeValue("id"), child);
					connector_Id.add(child.getAttributeValue("id"));
					System.out.println("connector added :" + child.getAttributeValue("id"));
					break;
				case 2:
				case 3:
				case 4:
					elements.put(child.getAttributeValue("id"), child);
					System.out.println("Element added" +child.getAttributeValue("id"));
					break;
				default:
					System.out.println("warning: element with name "
									+ child.getName() + ", id: "
									+ child.getAttributeValue("id")
									+ " is not defined");
				}
			}
			StringBuilder sb = new StringBuilder();
			for(Element ex : elements.values()){
				if(ex.getName().equals("startEvent")){
					sb.append("0, START_EVENT, ");
					for(Element exx : ex.getChildren()){
						if(!exx.getName().equals("outgoing")) continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim())+1) + ", ");
						System.out.println(ex.getTextTrim());
					}					
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("\n");
				} else if (ex.getName().equals("endEvent")){
					for(Element exx : ex.getChildren()){
						if(!exx.getName().equals("incoming")) continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim())+1) + ", ");
						System.out.println(ex.getTextTrim());
					}					
					sb.append("END_EVENT");
					sb.append("\n");
				} else {
					for(Element exx : ex.getChildren()){
						if(!exx.getName().equals("incoming")) continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim())+1) + ", ");
						System.out.println(ex.getTextTrim());
					}
					sb.append(ex.getName() + ", ");
					for(Element exx : ex.getChildren()){
						if(!exx.getName().equals("outgoing")) continue;
						sb.append((connector_Id.indexOf(exx.getTextTrim())+1) + ", ");
						System.out.println(ex.getTextTrim());
					}					
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("\n");
				}
			}
			System.out.println(sb.toString());
		}
	}
}
