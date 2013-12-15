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

package de.tudarmstadt.dvs.ukuflow.preference;


import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tudarmstadt.dvs.ukuflow.converter.Activator;

public class UkuPreferencePageHome extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {	
	public UkuPreferencePageHome(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Configuration for ukuFlow plugin");
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}
	private boolean validateString(String input){
		return (input.matches("[a-zA-Z0-9_-]+"));
	}
	@Override
	public void propertyChange(PropertyChangeEvent event){
		
		if(event.getProperty().equals("field_editor_value") && event.getSource() instanceof StringFieldEditor){
			StringFieldEditor sfe = (StringFieldEditor)event.getSource();
			String fieldID = sfe.getPreferenceName();
			if(fieldID.equals(UkuPreference.DEFAULT_SCOPE) || fieldID.equals(UkuPreference.OUTPUT_DIR)){
				if(validateString((String)event.getNewValue())){
					setValid(true);
					setErrorMessage(null);
					super.propertyChange(event);
				} else {
					setValid(false);
					setErrorMessage("'"+sfe.getLabelText()+"' should consists of these characters 'a..z,A..Z,0..9,_-'");
				}
			} else {
				super.propertyChange(event);
			}
		} else {
			super.propertyChange(event);
		}
	}

	@Override
	protected void createFieldEditors() {
		// Time out 
		IntegerFieldEditor connectionTimeout = new IntegerFieldEditor(UkuPreference.TIMEOUT, "Connection &timeout", getFieldEditorParent());
		connectionTimeout.setValidRange(5, 300);
		connectionTimeout.setValidateStrategy(IntegerFieldEditor.VALIDATE_ON_FOCUS_LOST);		
		addField(connectionTimeout);
		
		// output dir
		StringFieldEditor outputFolder = new StringFieldEditor(UkuPreference.OUTPUT_DIR, "Output directory name",getFieldEditorParent());		
		addField(outputFolder);
		
		// default timezone
		ComboFieldEditor timezone = new UkuComboFieldEditor(UkuPreference.TIME_ZONE, "Timezone", UkuPreference.timezones, getFieldEditorParent());
		addField(timezone);
		
		//default scope
		StringFieldEditor defaultScope = new StringFieldEditor(UkuPreference.DEFAULT_SCOPE, "Default scope for event generator", getFieldEditorParent());
		defaultScope.setEmptyStringAllowed(false);
		defaultScope.setValidateStrategy(StringFieldEditor.VALIDATE_ON_FOCUS_LOST);		
		addField(defaultScope);
	}

}
