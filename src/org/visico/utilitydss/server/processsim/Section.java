package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Section extends SimProcess
{
	private UtilitySimulation myModel;
	
	private int NUM_PIPE;
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
	public Section(Model owner, String name, boolean showInTrace , int a) 
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
		NUM_PIPE = a;
	}
	
	/**
	    * Describes this section's life cycle.
	    * This is the actual description of the work that is done, the parameters are stored in UtilitySimulation.java
	    *
	    * the section will loop through the following stages:
	    * TODO first section should perform action: setting out underground cables and tubes. -- if breaking is general process it can be done within that process
	    * otherwise it should be done within first section/put (whatever is first object)
	    * 
	    * 1. wait for breaker to break street or remove stones
	    * 2. wait for excavator to excavate (excavator requires truck)(quantity of dirt not yet modelled)
	    * 3. If shoring required: wait for crane to shore the section
	    * 4. If replacement project: wait for crane to remove the old pipe 
	    * 5. wait for crew to prepare bed
	    * 6. wait for crane to place pipe (pipe requires truck) - think how to model that a truck can already get a pipe
	    * 7. wait for crew to handbackfill (first backfill)
	    * 8. If shoring required: wait for crane to remove the trench
	    * 9. If there are housing connections: housing connections
	    * 10. wait for excavator to backfill (needs truck with backfill - excavator needed to load truck) (second backfill)
	    * 11. wait for crew to prepare surface
	    * 12. wait for road crew to pave the surface with asphalt or stones
	    */

	   public void lifeCycle() 
	   {	
		   // TODO make section/put hold until all previous sections & puts are done with certain activities
		   //		which activity this is is process dependent ( give different options) 
		   // this requires a section to asses what its predecessors (puts and sections) are &
		   // And if all these predecessors completed the specified activities. <-- use bins or counters
		   // this would prolly be a lot easier if puts where sections with specific put behavior as identitynumber then could be used.
		   
	   
		   // 1. break the section or remove stone pavement
		   // TODO insert if statement to only perform activity breaking if this happens per section
		   // 		if breaking is a general activity it will become be a separate process and no breaking should occur in section or put lifecycle.
		   TimeInstant start = myModel.presentTime();
		   if(myModel.getOldPavement() == 1) {
			   myModel.breakers.provide(1);
			   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   TimeInstant end = myModel.presentTime(); //TODO is this necessary as ActivityMessage uses presentTime as end time?
			   ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   sendMessage(msg);
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
		   }
  
		   // TODO think about who removes stones
		   else if(myModel.getOldPavement() == 2) { 
			   myModel.crews.provide(1);
			   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   TimeInstant end = myModel.presentTime(); //TODO is this necessary as ActivityMessage uses presentTime as end time?
			   ActivityMessage msg = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime());
			   sendMessage(msg);
			   myModel.crews.takeBack(1);
			   System.out.println("stones " + myModel.presentTime());
		   }
			  
		   // for loop iterating trough all pipes in this section
		   // TODO make different number of pipes possible per section. --> make an array for number of pipes for all sections.
		   for (int i=1; i<=this.NUM_PIPE; i++)
		   {
			   // 2. excavate the section
			   myModel.excavators.provide(1);
			   myModel.trucks.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getExcavatingTime(), TimeUnit.HOURS)); //multiply by This.volume_section
			   // end = myModel.presentTime(); 
			   // msg = new ActivityMessage(myModel, this, start, "Excavate", myModel.presentTime()) ;
			   // sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Excavating Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.trucks.takeBack(1);
			   
			   // 3. shore the section
			   // only for projects that require shoring (set variable Shore to right value in simulation class)
			   if(myModel.getShore() == 1) {
				   myModel.excavators.provide(1);
				   //start = myModel.presentTime();
				   hold (new TimeSpan(myModel.getShoringTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
				   // end = myModel.presentTime();
				   // msg = new ActivityMessage(myModel, this, start, "Shore", myModel.presentTime()) ;
				   // sendMessage(msg);
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
			   
			   // 4. remove the pipe
			   // only for replacement projects (set variable Replacement in UtilitySimulation.java class to true/false )	
			   // TODO implement different types of sewer to be removed (material, size, combined/seperate)
			   if(myModel.getReplacement()) {
				   	myModel.excavators.provide(1);
			   		//start = myModel.presentTime();
			   		hold (new TimeSpan(myModel.getPipeRemoveTime(), TimeUnit.MINUTES));
					// end = myModel.presentTime();
					// msg = new ActivityMessage(myModel, this, start, "Remove Pipe", myModel.presentTime()) ;
					// sendMessage(msg);
			   		sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   }
	
	
			   // 5. prepare the bed
			   myModel.crews.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getBedPreparationTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   // end = myModel.presentTime();
			   // msg = new ActivityMessage(myModel, this, start, "Prepare Bed", myModel.presentTime()) ;
			   // sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
			  
			   // 6. install the pipe
			   // TODO implement different types of sewer to be placed (material, size, combined/seperate)
			   myModel.crews.provide(1);
			   myModel.excavators.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getPipePlacingTime(), TimeUnit.HOURS));
			   // end = myModel.presentTime();
			   // msg = new ActivityMessage(myModel, this, start, "Install Pipe", myModel.presentTime()) ;
			   // sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Install Pipe: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.crews.takeBack(1);
			   
			   // 7. hand/first backfill + compacting
			   //TODO make time neccesary dependent on partial or full backfill, depending on if there are housing connections in the section)
			   myModel.crews.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getHandBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   // end = myModel.presentTime();
			   // msg = new ActivityMessage(myModel, this, start, "Hand Backfill", myModel.presentTime()) ;
			   // sendMessage(msg);
			   sendTraceNote("Activity: " + getName() + " Hand Backfill: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
			   myModel.handbackfill();
			   if(myModel.getSecondCrew()) // if there are second crews:
			   {	if(myModel.getShore() == 0) // if there is no shoring to be removed:
			   		{	if (UtilitySimulation.getHandBackfillCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT))
			   			{	myModel.crews.stopUse();
			   				myModel.excavators.stopUse();
			   				System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
			   				System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
			   			}
			   		}
			   }
			   
	 		   // 8. remove shoring
			   // only for projects that require shoring (set variable Shore to right value in simulation class)
			   if(myModel.getShore() == 1)
			   {	myModel.excavators.provide(1);
			   		//start = myModel.presentTime();
			   		hold (new TimeSpan(myModel.getRemoveTrenchTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
					// end = myModel.presentTime();
					// msg = new ActivityMessage(myModel, this, start, "Remove Shoring", myModel.presentTime()) ;
					// sendMessage(msg);
			   		sendTraceNote("Activity: " + getName() + " Remove Trench: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   		if(myModel.getSecondCrew()){ // if there are second crews:
			   			if (UtilitySimulation.getShoreCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) 
			   			{
			   				//TODO Fix: counters get updated after every completed pipe instead of completed section so crews stop way to early if there are secondcrews
			   				myModel.crews.stopUse();
			   				myModel.excavators.stopUse();	
			   				System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
			   				System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
			   			}
			   		}
			   }
			   
			   // test if model is iterating trough pipes
			   System.out.println("pipe " + i + " completed at " + myModel.presentTime()); 
		   }
		   
		   //TODO	make housing connections optional, not every section has them, watch out for crews stopping when other sections do have housing connections.
		   //		add characteristics to connections, per connection are average per section/project these characteristics need to determine time needed
		   //		think if model should iterate trough connections or if a sum of needed times suffices.
		   // 9. install the housing connections
		   if(myModel.getSecondCrew()) {
			   	myModel.secondcrews.provide(1);}
		   else {myModel.crews.provide(1);}
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getHousingConnectionTime(), TimeUnit.HOURS)); //multiply by This.Num_HousingConnections
		   sendTraceNote("Activity: " + getName() + " Install housing connection: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   if(myModel.getSecondCrew()){
			   myModel.secondcrews.takeBack(1);}
		   else {myModel.crews.takeBack(1);}
		   	   
		   // 10. (second) backfill + compacting
		   //TODO make this action dependent on if there are housing connections, and on crews
		   if(myModel.getSecondCrew()) {
			   	myModel.secondcrews.provide(1);}
		   else {myModel.excavators.provide(1);}
		   myModel.trucks.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   // end = myModel.presentTime();
		   // msg = new ActivityMessage(myModel, this, start, "Second Backfill", myModel.presentTime()) ;
		   // sendMessage(msg);
		   sendTraceNote("Activity: " + getName() + " Backfill: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   if(myModel.getSecondCrew()) {
			   	myModel.secondcrews.takeBack(1);}
		   else {myModel.excavators.takeBack(1);}
		   myModel.trucks.takeBack(1);
		   myModel.backfill();
		   if (UtilitySimulation.getBackfillCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.trucks.stopUse();
			   myModel.excavators.stopUse();
			   if(myModel.getSecondCrew()){
				   myModel.secondcrews.stopUse();
				   System.out.println("resource second crews stopped at simulation time " + myModel.presentTime());
			   }
			   else {
				   myModel.crews.stopUse();
				   System.out.println("resource crews stopped at simulation time " + myModel.presentTime());
			   }
			   System.out.println("resource trucks stopped at simulation time " + myModel.presentTime());
			   System.out.println("resource excavators stopped at simulation time " + myModel.presentTime()); 
		   }

		   // TODO insert if statement to only perform activity paving if this happens per section
		   // if paving is a general activity it will become be a separate process and no paving should occur in section or put lifecycle.
		   // It could also be that only a broken-stone road is constructed and paving alone is performed later
		   
		   // 11. roll/prepare surface
		   myModel.rollers.provide(1);
		   //start = myModel.presentTime();
		   hold (new TimeSpan(myModel.getSurfacePrepareTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
		   // end = myModel.presentTime();
		   // msg = new ActivityMessage(myModel, this, start, "Roll", myModel.presentTime()) ;
		   // sendMessage(msg);
			sendTraceNote("Activity: " + getName() + " Compact: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   myModel.roll();
   		   if (UtilitySimulation.getRollCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
		  
		   // 12. pave  
		   if(myModel.getNewPavement() == 1) {
			   myModel.pavecrews.provide(1);
			   //start = myModel.presentTime();
			   hold (new TimeSpan(myModel.getPaveTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   // end = myModel.presentTime();
			   // msg = new ActivityMessage(myModel, this, start, "Pave", myModel.presentTime()) ;
			   // sendMessage(msg);
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
			   // end = myModel.presentTime();
			   // msg = new ActivityMessage(myModel, this, start, "Stone Pave", myModel.presentTime()) ;
			   // sendMessage(msg);
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
