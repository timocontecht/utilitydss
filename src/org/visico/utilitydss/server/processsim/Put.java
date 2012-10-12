package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Put extends SimProcess
{
	private UtilitySimulation myModel;
		
		/**
		    * Constructor of the put 
		    *
		    * Used to create a new put in a sewer line to be replaced
		    *
		    * @param owner the model this process belongs to
		    * @param name this puts name
		    * @param showInTrace flag to indicate if this process shall produce output
		    *                    for the trace
		    */
	public Put(Model owner, String name, boolean showInTrace) 
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
	}
	
	/**
	    * Describes this section's life cycle.
	    * This is the actual description of the work that is done, the parameters are stored in UtilitySimulation.java
	    *
	    * the section will loop through the following stages:
	    * 1. wait for breaker to break street or remove stones
	    * 2. wait for excavator to excavate (excavator requires truck)(quantity of dirt not yet modelled)
	    * 3. If shoring required: wait for crane to shore the section
	    * 4. If replacement project: wait for crane to remove the old pipe 
	    * 5. wait for crew to prepare bed
	    * 6. wait for crane to place pipe (pipe requires truck) - think how to model that a truck can already get a pipe
	    * 7. If shoring required: wait for crane to remove the shoring
	    * 8. wait for excavator to backfill (needs truck with backfill - excavator needed to load truck) (second backfill)
	    * 9. wait for crew to prepare surface
	    * 10. wait for road crew to pave the surface with asphalt or stones
	    * 
	    * No housing connections with put, 
	    * TODO Think about also only one backfill activity or two when sections do have housing connections?
	    */

	// TODO implement lifecycle changes from section or make put a section with specific behavior.
	
	   public void lifeCycle() 
	   {
		// 1. break the section or remove stone pavement
		   TimeInstant start = myModel.presentTime();
		   if(myModel.getOldPavement() == 1){
			   myModel.breakers.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   //TimeInstant end = myModel.presentTime(); //TODO is this necessary as ActivityMessage uses presentTime as end time?
			   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   //sendMessage(msg);
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
		   }
	  
		   else if(myModel.getOldPavement() == 2)
		   {   myModel.crews.provide(1);
		   		//start = myModel.presentTime();
		   		hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   		//TimeInstant end = myModel.presentTime(); //TODO is this necessary as ActivityMessage uses presentTime as end time?
		   		// ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime());
		   		//sendMessage(msg);
		   		myModel.crews.takeBack(1);
		   		System.out.println("stones " + myModel.presentTime());
		   }
			  
		   // 2. excavate the section
		   myModel.excavators.provide(1);
		   myModel.trucks.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getExcavatingTime(), TimeUnit.HOURS)); //multiply by This.volume_section
		   //TimeInstant end = myModel.presentTime();
		   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   //sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Excavating Start: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.trucks.takeBack(1);
		   
		   // 3. shore the section
		   // only for projects that require shoring (set variable Shore to right value in simulation class)
		   if(myModel.getShore() == 1)
		   		{   myModel.excavators.provide(1);
		   		//start = myModel.presentTime();
		   		hold (new TimeSpan(myModel.getShoringTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
				//TimeInstant end = myModel.presentTime();
				//ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
				//sendMessage(msg);
		   		sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.excavators.takeBack(1);
		   }
		   	else if(myModel.getShore() != (0 | 1)){
		   		System.out.println("Shoring setting incorrect, experiment aborted. Choose one of the following settings: 1 for shoring, " +
					  "2 for (not implemented yet), 0 for no shoring activities");
				myModel.getExperiment().stop();
				   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
				}
		   
		   // 4. remove the put
		   //TODO find out if puts always get replaced when pipes are.
		   // only for replacement projects (set variable Replacement to true/false in simulation class)	   
		   if(myModel.getReplacement())
		   {	myModel.excavators.provide(1);
		   		//start = myModel.presentTime();
		   		hold (new TimeSpan(myModel.getPipeRemoveTime(), TimeUnit.MINUTES));
				//TimeInstant end = myModel.presentTime();
				//ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
				//sendMessage(msg);
		   		sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.excavators.takeBack(1);
		   }

		   // 5. prepare the bed
		   myModel.crews.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getBedPreparationTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   //TimeInstant end = myModel.presentTime();
		   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   //sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);
		  
		   // 6. install the put
		   //TODO implement option for installing big puts (above weight threshold for excavators) with truck-mounted crane (resource cranes)
		   myModel.crews.provide(1);
		   myModel.excavators.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getPipePlacingTime(), TimeUnit.HOURS));
		   //TimeInstant end = myModel.presentTime();
		   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   //sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Install Pipe: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.crews.takeBack(1);
		   
		   // 7. hand backfill
		   // TODO remove or not?
		   myModel.crews.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getHandBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   sendTraceNote("Activity: " + getName() + " Hand Backfill: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);
		   myModel.handbackfill();
		   if(myModel.getSecondCrew())
		   {	if (UtilitySimulation.getHandBackfillCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT))
		   		{myModel.crews.stopUse();
		   		System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " because 2nd crew takes over");
		   		}
		   }
	   
		   // 7. remove shoring
		   // only for projects that require shoring (set variable Shore right value in simulation class)
		   if(myModel.getShore() == 1)
		   {	myModel.excavators.provide(1);
		   		//start = myModel.presentTime();
		   		hold (new TimeSpan(myModel.getRemoveTrenchTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
				//TimeInstant end = myModel.presentTime();
				//ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
				//sendMessage(msg);
		   		sendTraceNote("Activity: " + getName() + " Remove Trench: " + start.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.excavators.takeBack(1);
		   		//  if (this.getIdentNumber() == UtilitySimulation.NUM_SEC){
		   		//	myModel.excavators.stopUse();	
		   		//	System.out.println("resource cranes stopped at simulation time " + myModel.presentTime());
		   		//}
		   }
		   
		   // 8. backfill
		   myModel.excavators.provide(1);
		   myModel.trucks.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   //TimeInstant end = myModel.presentTime();
		   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   //sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Backfill: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.trucks.takeBack(1);
		   myModel.backfill();
		   if (UtilitySimulation.getBackfillCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.trucks.stopUse();
			   myModel.excavators.stopUse();
			   myModel.crews.stopUse();
			   myModel.secondcrews.stopUse();
			   System.out.println("resource trucks stopped at simulation time " + myModel.presentTime());
			   System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
			   if(myModel.getSecondCrew()){
				   System.out.println("resource second crews stopped at simulation time " + myModel.presentTime());
			   }
			   else { 
				   System.out.println("resource crews stopped at simulation time " + myModel.presentTime());
			   }
		   }
 
		   // 9. roll
		   myModel.rollers.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getSurfacePrepareTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   //TimeInstant end = myModel.presentTime();
		   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
		   //sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Compact: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   myModel.roll();
   
		   if (UtilitySimulation.getRollCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
		  
		   // 10. pave  
		   if(myModel.getNewPavement() == 1) {
			   myModel.pavecrews.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getPaveTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   //TimeInstant end = myModel.presentTime();
			   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   //sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Asphalt Paving: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.pavecrews.takeBack(1);
			   myModel.pave();
   		   		if (UtilitySimulation.getPaveCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
		   			myModel.pavecrews.stopUse();
		   			myModel.getExperiment().stop();
		   			System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());
		   		}   
		   }
		   
		   else if(myModel.getNewPavement() == 2) {
			   myModel.stonepavecrews.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getStonePaveTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   //TimeInstant end = myModel.presentTime();
			   //ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   //sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Stone Paving: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.stonepavecrews.takeBack(1);
			   myModel.stonepave();
   
			   if (UtilitySimulation.getStonePaveCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
				   	myModel.stonepavecrews.stopUse();
		   			myModel.getExperiment().stop();
		   			System.out.println("resource stonepavecrews stopped at simulation time " + myModel.presentTime());
		   		}   
		   }   
		   
		   else if(myModel.getNewPavement() == 0) {
			   	if (UtilitySimulation.getRollCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
			   		myModel.getExperiment().stop();
			   	}	
		   }
		   
		   else {
			   System.out.println("Pavement setting incorrect, experiment aborted. Choose one of the following settings: 1 for asphalt, " +
			   			"2 for stone, 0 for no paving activities");
			   myModel.getExperiment().stop();
			   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
		   }
		
		   // parameter for holding the volume of earth to excavate, currently as
		   // number of trucks to fill ....   - Not (yet) used 
		   // TODO make excavation_volume a function of section length, width and depth
		   // TODO make excavation_volume influence nr. of trucks needed and excavating/backfill time
		  //private double excavation_volume = 20;
		  
}}

