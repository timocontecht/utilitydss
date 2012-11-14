package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class SectionProcess extends SimProcess
{

	public SectionProcess(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
		myModel = (UtilitySimulation)arg0;
	}

	@Override
	public void lifeCycle() {
		
		  
		
		
	}
	
	
	protected UtilitySimulation myModel;
	
}
