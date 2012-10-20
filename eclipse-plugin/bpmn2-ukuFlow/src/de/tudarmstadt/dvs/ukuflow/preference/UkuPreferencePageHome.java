package de.tudarmstadt.dvs.ukuflow.preference;


import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFieldEditors() {
		IntegerFieldEditor connectionTimeout = new IntegerFieldEditor(UkuPreference.TIMEOUT, "Connection &timeout", getFieldEditorParent());
		connectionTimeout.setValidRange(5, 300);
		connectionTimeout.setValidateStrategy(IntegerFieldEditor.VALIDATE_ON_FOCUS_LOST);		
		addField(connectionTimeout);
		
		StringFieldEditor outputFolder = new StringFieldEditor(UkuPreference.OUTPUT_DIR, "Output directory name",getFieldEditorParent());		
		addField(outputFolder);
		
	}

}
