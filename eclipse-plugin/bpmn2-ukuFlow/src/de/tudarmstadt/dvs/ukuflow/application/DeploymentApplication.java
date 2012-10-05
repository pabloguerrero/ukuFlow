package de.tudarmstadt.dvs.ukuflow.application;

import org.eclipse.equinox.app.IApplicationContext;

public class DeploymentApplication implements org.eclipse.equinox.app.IApplication{

	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");		
		return null;
	}

	@Override
	public void stop() {
		System.out.println("stop");
		// TODO Auto-generated method stub
		
	}

}
