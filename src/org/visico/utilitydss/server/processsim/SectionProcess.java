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
		
		   myModel.crews.provide(1);
		   TimeInstant start = myModel.presentTime();
		   start = myModel.presentTime();
		   hold (new TimeSpan((getDuration()), TimeUnit.HOURS)); 
		   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Remove Stones Section", myModel.presentTime());
		   sendMessage(msg_1);
		   myModel.crews.takeBack(1);
		   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		
		
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	protected UtilitySimulation myModel;
	protected int duration = 5; 
}
