package de.tudarmstadt.dvs.ukuflow.application;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

public class TestLauncher implements org.eclipse.debug.core.model.ILaunchConfigurationDelegate{

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		System.out.println("launching");
	}

}
