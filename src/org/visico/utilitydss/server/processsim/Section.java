package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Section extends SimProcess
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
	public Section(Model owner, String name, boolean showInTrace) 
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
	}
	
	/**
	    * Describes this section's life cycle.
	    *
	    * the section will loop through the following stages
	    * 
	    * 1. wait for breaker to break street 
	    * 2. wait for excavator to excavate (excavator requires truck)(quantity of dirt not yet modelled)
	    * 3. wait for crane to shore the section
	    * 4. wait for crane to remove the old pipe
	    * 5. wait for crew to prepare bed
	    * 6. wait for crane to place pipe (pipe requires truck) - think how to model that a truck can already get a pipe
	    * 7. wait for crew to handbackfill
	    * 8. wait for crane to remove the trench
	    * 9. wait for excavator to backfill (needs truck with backfill - excavator needed to load truck)
	    * 10. wait for crew to prepare surface
	    * 
	    * this assumes that asphalting process is not part of the work for one section
	    */

	   public void lifeCycle() 
	   {
		   // break the section
		   myModel.breakers.provide(1);
		   TimeInstant start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS));
		   TimeInstant end = myModel.presentTime();
		   ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   sendMessage(msg);
		   myModel.breakers.takeBack(1);
		   if (this.getIdentNumber() == UtilitySimulation.NUM_SEC)
			   myModel.breakers.stopUse();
		  
		   
		   // excavate the section
		   myModel.excavators.provide(1);
		   myModel.trucks.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getExcavatingTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Excavating Start: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.trucks.takeBack(1);
		   
		   
		   // shore the section
		   myModel.cranes.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getShoringTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.cranes.takeBack(1);

	 //for replacement projects			   
		// remove the pipe
		   if(myModel.getRenovation())
		   {myModel.cranes.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getPipeRemoveTime(), TimeUnit.MINUTES));
		   sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.cranes.takeBack(1);}

		   
		   // prepare the bed
		   myModel.crews.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getBedPreparationTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);
		  
		   
		   // install the pipe
		   myModel.excavators.provide(1);
		   myModel.cranes.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getPipePlacingTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Install Pipe: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.cranes.takeBack(1);
		   myModel.excavators.takeBack(1);
		   
		   //hand backfill
		   myModel.crews.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getHandBackfillTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Hand Backfill: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);
		   if (this.getIdentNumber() == UtilitySimulation.NUM_SEC)
			   myModel.crews.stopUse();
		   
		   // remove trench
		   myModel.cranes.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getRemoveTrenchTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Remove Trench: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.cranes.takeBack(1);
		   if (this.getIdentNumber() == UtilitySimulation.NUM_SEC)
			   myModel.cranes.stopUse();	
		   
		   // backfill
		   myModel.excavators.provide(1);
		   myModel.trucks.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getBackfillTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Backfill: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.trucks.takeBack(1);
		   if (this.getIdentNumber() == UtilitySimulation.NUM_SEC)
		   {
			   myModel.trucks.stopUse();
			   myModel.excavators.stopUse();
		   }
		   
		   // roll
		   myModel.rollers.provide(1);
		   //start = myModel.presentTime().toString();
		   hold (new TimeSpan(myModel.getSurfacePrepareTime(), TimeUnit.HOURS));
		   sendTraceNote("Activity: " + getName() + " Compact: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   if (this.getIdentNumber() == UtilitySimulation.NUM_SEC)
		   {
			   myModel.rollers.stopUse();
			   myModel.getExperiment().stop();
		   }
		  
		}
			  
	   // parameter for holding the volume of earth to excavate, currently as
	   // number of trucks to fill ....   - Not (yet) used 
	   // TODO make excavation_volume a function of section length, width and depth
	   // TODO make excavation_volume influence nr. of trucks needed and excavating/backfill time
	  private double excavation_volume = 20;
	  
}
