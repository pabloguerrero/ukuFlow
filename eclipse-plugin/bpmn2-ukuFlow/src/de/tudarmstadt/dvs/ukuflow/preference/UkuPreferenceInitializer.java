package de.tudarmstadt.dvs.ukuflow.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.tudarmstadt.dvs.ukuflow.converter.Activator;

public class UkuPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	    store.setDefault(UkuPreference.TIMEOUT,10);
	}

}
