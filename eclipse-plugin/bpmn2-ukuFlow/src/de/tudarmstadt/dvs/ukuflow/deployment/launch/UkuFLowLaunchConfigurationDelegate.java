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
package de.tudarmstadt.dvs.ukuflow.deployment.launch;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.internal.launching.LaunchingMessages;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author dang quoc hien
 * 
 */
public class UkuFLowLaunchConfigurationDelegate extends
		AbstractJavaLaunchConfigurationDelegate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		IProgressMonitor mon = monitor == null ? new NullProgressMonitor()
				: monitor;
		mon.beginTask(configuration.getName(), 3);
		if (mon.isCanceled())
			return;
		try {
			String mainClass = "__?__";
			// From JavaLauncher:
			mon.subTask(LaunchingMessages.JavaLocalApplicationLaunchConfigurationDelegate_Verifying_launch_attributes____1);

			verifyJavaProject(configuration);
			verifyMainTypeName(configuration);
			IVMRunner vmRunner = getVMRunner(configuration, mode);
			File workingDir = getWorkingDirectory(configuration);

			String workingDirName = workingDir == null ? null : workingDir
					.getAbsolutePath();

			String[] envp = getEnvironment(configuration);
			String pgmArgs = getProgramArguments(configuration);
			String vmArgs = getVMArguments(configuration);
			Map<String, Object> vmAttrs = getVMSpecificAttributesMap(configuration);
			ExecutionArguments execArgs = new ExecutionArguments(vmArgs,
					pgmArgs);
			String[] vmArgsArray = execArgs.getVMArgumentsArray();

			// vm specific attributes:
			Map vmAttributesMap = getVMSpecificAttributesMap(configuration);

			// classpath
			String classpath = "";
			for (String clp : getClasspath(configuration)) {
				classpath += (clp + File.pathSeparator);
			}
			classpath += ".";
			// ???
			String[] programArgs = { "-classpath", classpath };

			VMRunnerConfiguration runConfig = new VMRunnerConfiguration(
					mainClass, getClasspath(configuration));
			runConfig.setWorkingDirectory(workingDirName);
			runConfig.setEnvironment(envp);
			runConfig.setVMArguments(vmArgsArray);
			runConfig.setVMSpecificAttributesMap(vmAttrs);
			runConfig.setProgramArguments(programArgs);

			runConfig.setBootClassPath(getBootpath(configuration));
			if (mon.isCanceled())
				return;

			prepareStopInMain(configuration);

			mon.worked(1);
			setDefaultSourceLocator(launch, configuration);
			mon.worked(1);
			vmRunner.run(runConfig, launch, monitor);
			if (mon.isCanceled())
				return;
			runSeedscript();
			if (mon.isCanceled())
				return;
		} finally {
			mon.done();
		}
	}
	private void runSeedscript(){
		//TODO
	}
}
