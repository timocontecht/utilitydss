package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Breaking extends SimProcess
{
	private UtilitySimulation myModel;
	
	/**
	    * Constructor of the section 
	    *
	    * Used to create a new section of a sewer line to be replaced
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	public Breaking(Model owner, String name, boolean showInTrace)
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
	}
	
	/**
	    * Describes this section's life cycle.
	    *process for breaking all sections upfront, only active if 
	    * experiments process version is set to BREAK_ALL_UPFRONT
	    */

	   public void lifeCycle() 
	   {
			myModel.startingCondition.retrieve(1);
		   // break the section
		   myModel.breakers.provide(1);
		   TimeInstant start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getBreakingTime() * myModel.getNUM_SEC(), TimeUnit.HOURS));
		   TimeInstant end = myModel.presentTime();
		   ActivityMessage msg = new ActivityMessage(myModel, null, start, "Breaking all sections", myModel.presentTime()) ;
		   sendMessage(msg);
		   myModel.breakers.takeBack(1);
		   myModel.breakers.stopUse();
		   myModel.startingCondition.store(1);
		}
	  
}
