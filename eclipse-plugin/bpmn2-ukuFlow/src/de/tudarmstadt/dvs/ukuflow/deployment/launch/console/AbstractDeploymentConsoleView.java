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
package de.tudarmstadt.dvs.ukuflow.deployment.launch.console;

import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

/**
 * @author hien
 *
 */
public abstract class AbstractDeploymentConsoleView extends ViewPart{
	public static final String DEPLOYMENT_VIEW_ID = "de.tudarmstadt.dvs.ukuflow.deploymentView";
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public abstract void createPartControl(Composite parent);
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public abstract void setFocus();
	/// scala code:
	//protected void createCommandField(Composite parent, )
	
	StyledText textWidget = null;
	Color codeBgColor = null;
	Color codeFgColor = null;
	Color errorFgColor = null;
	Display display = null;
	
	protected void createDeploymentPartControl(Composite parent){
		display = parent.getDisplay();
		codeBgColor = new Color(display, 230, 230, 230); // light gray
	    codeFgColor = new Color(display, 60, 0, 128); // eggplant
	    errorFgColor = new Color(display, 128, 0, 64); // maroon
	    SashForm panel = new SashForm(parent, SWT.VERTICAL);
	    panel.setLayout(new FillLayout());
	    textWidget =new StyledText(panel, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    textWidget.setLayout(new FillLayout());
	    textWidget.setEditable(false);
	    textWidget.setCaret(new Caret(textWidget,SWT.NONE));
	    textWidget.setAlwaysShowScrollBars(false);
	    Font editorFont = JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
	    textWidget.setFont(editorFont);
	    /* TODO : add input field: 
	    val inputField = createCommandField(panel, Seq(SWT.BORDER, SWT.MULTI, SWT.H_SCROLL, SWT.V_SCROLL, SWT.RESIZE))
    	inputField.setFont(editorFont)
    	inputField.setLayout(new FillLayout)
    	inputField.setAlwaysShowScrollBars(false)
	    */
	    panel.setWeights(new int[]{3});
	}
	@Override
	public void dispose(){
		if(codeBgColor!=null) codeBgColor.dispose();
		if(codeFgColor != null) codeFgColor.dispose();
		if(errorFgColor != null) errorFgColor.dispose();
	}

	protected void appendText(String text, Color fg, Color bg, int fontStyle) {
		appendText(text, fg, bg, fontStyle, null, false);
	}
	protected void appendText(final String text, final Color fg, final Color bg, final int fontStyle,final Font font, final boolean newLine){
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				int charCount = textWidget.getCharCount();
				int lineCount = textWidget.getLineCount();
				String output = newLine?"\n"+ text.replace("\n", "")+"\n":text;
				textWidget.append(output);
				StyleRange style1 = new StyleRange(charCount,output.length(),fg,null,fontStyle);
				style1.font = font;
				textWidget.setStyleRange(style1);
				
				int lastLine = textWidget.getLineCount();
				if(bg != null)
					textWidget.setLineBackground(lineCount-1, lastLine-lineCount, bg);
				textWidget.setTopIndex(textWidget.getLineCount()-1);
				StyleRange style2 = new StyleRange(charCount,output.length(),fg,null,fontStyle);
				style2.font = font;
				textWidget.setStyleRange(style2);
			}
		});
		
	}
	
	protected void displayOutput(String text){
		appendText(text, null, null, SWT.NORMAL);
	}
	protected void displayError(String text){
		appendText(text, errorFgColor, null, SWT.NORMAL);
	}
}
