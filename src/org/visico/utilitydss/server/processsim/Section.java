package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Section extends SimProcess
{
	
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
	public Section(Model owner, 
			String name, 
			boolean showInTrace, 
			int put,
			int pipes, 
			int connections
			/*,
			int num_put_connections,
			int old_pavement,
			int new_pavement,
			int section_length,
			int pipe_length,
			int section_width,
			int Trench_width,
			int Trench_depth,
			int old_sewer_type,
			int new_sewer_type,
			int old_diamete,
			int new_diameter,
			int asphalt_old,
			int asphalt_new,
			int cables,
			int length_connections,
			int depth_connections,
			int foundation_type, 
			int Trench_protection,
			int soil_removed,  	
			int soil_new,  		
			int pipes_old,  		
			int pipes_new,
			int total_length
			int rock_layer
			int sand_layer
			*/  ) 
	
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
		PUT = put;									// section or put:  0 is section, 1 is put.  
		NUM_PIPE = pipes;
		NUM_CONNECTIONS = connections;
		/*
		Num_Put_connections = num_put_connections; 	// number of connections the put has, only if put
		Old_pavement = old_pavement; 				// type of old pavement
		New_pavement = new_pavement;  				// type of new pavement
		Section_length = section_length;  							// length of section in
		Pipe_length = pipe_length;  				// length of pipes in
		Section_width = section_width;  			// width of section in
		Trench_width = Trench_width;  				// width of Trench v
		Trench_depth = Trench_depth;  				// depth of Trench in
		Old_sewer_type = old_sewer_type; 			// type of old sewer
		New_sewer_type = new_sewer_type; 			// type of new sewer
		Old_diameter = old_diameter;  				// diameter of old sewer 
		New_diameter = new_diameter;  				// diameter of new sewer
		Asphalt_old = asphalt_old;  				// layer thickness of old asphalt in
		Asphalt_new = asphalt_new;  				// layer thickness of new asphalt in // 
		Cables = cables;  							// weight class of cables in the ground
		Length_connections = length_connections;  	// average length of connections
		Depth_connections = depth_connections;  	// average depth of connections
		Foundation_type = funcation_type;  			// type foundation used: 1 = , 2 =
		Trench_protection = trench_protection;  		// Type of Trench protection used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
		Total_length = total_length					// length of all sections
		Rock_layer = rock_layer						// height of broken rock layer
		Sand_layer = sand_layer						// height of sand layer	
		etc
		 */
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
	   /**
	    * Makes  sections wait till preceding section is finished.
	    * when other sections are allowed to start the resource is given back to the bin in UtilitySimulation.java
	    * After which activity this is can be controlled by setting  in UtilitySimulation.java
	    * 
	    * FIXME only works when puts are sections with special characteristics (with 2 different classes there is no way of maintaining chronological order)
	    * 		So puts should become sections with special characteristics
	    * FIXME difficult to model work on multiple sections at once --> drawback inherent to hard-coding the model?
	    */
	   
		// TODO make section/put hold until all preceding sections & puts are done with certain activities
		// which activities this are is process dependent 
		// this requires a section to asses what its predecessors (puts and sections) are &
		// And if all these predecessors completed the specified activities. <-- use bins or counters?
		// this would prolly be a lot easier if puts where sections with specific put behavior as identitynumber then could be used, or a simple bin/res
		// think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		
		myModel.startingCondition.retrieve(1);
	   
		// variables to create messages for output charts
		// TimeInstant start, end; 
		// ActivityMessage msg;
		
		/**
		 * selects the right lifecycle for section or put based on setting in UtilitySimulation.java
		 */
		if(this.PUT == 0)
		{
		
			/**
			 * Lifecycle Sewer section
			 */
		   // 1. break the section or remove stone pavement
		   TimeInstant start = myModel.presentTime();
		   // Break all sections at once.
		   if(myModel.getOldPavement() == 3) {
			   	if (this.getIdentNumber() == (1)){
			   		myModel.breakers.provide(1);
			   		start = myModel.presentTime();
			   		hold (new TimeSpan((myModel.getBreakingTime() * (Total_Area/remove_pavement)), TimeUnit.HOURS));
			   		ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   		sendMessage(msg_1);
			   		sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
			   		myModel.breakers.takeBack(1);
			   		myModel.breakers.stopUse();
			   		System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   // TODO add if statement for creating temporary broken rock road or deploying rubbing plates
			   	}
			   	
			   	else{
			   		// Breaking happens once for all sections
			   		// so all preceding sections have no breaking activities
			   		System.out.println(this + " No breaking activities, all in first " + myModel.presentTime());
			   	}
		   }
		   
		   // Break asphalt per section
		   else if(myModel.getOldPavement() == 1) {
			   myModel.breakers.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); //multiply by This.lenght_section
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   sendMessage(msg_1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
		   }
 
		   // Remove brick pavement
		   // TODO think about who removes stones
		   else if(myModel.getOldPavement() == 2) { 
			   myModel.crews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); //multiply by This.lenght_section
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Remove Stones Section", myModel.presentTime());
			   sendMessage(msg_1);
			   myModel.crews.takeBack(1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   System.out.println("stones removed at simulation time " + myModel.presentTime());
		   }
		   
		   else {
			   System.out.println("Breaking setting incorrect, experiment aborted. Choose one of the following settings: 1 for asphalt, " +
			   		"2 for stone, 3 for breaking all sections at once, 0 for no breaking activities");
			   myModel.getExperiment().stop();
			   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
		   }
		   
		   
		   // gathers data on total duration of main sewer loop (1 task contains all pipes in section), only active if turned on in utilitysimulation.java
		   if(myModel.getActivityMsg() == 1)
		   {
			   start = myModel.presentTime();
		   }
		   
		   // for loop iterating trough main loop (iterates trough all pipes in this section)
		   for (int i=1; i<=this.NUM_PIPE; i++)
		   {
			   // gathers start time of every pipe in main loop, only active if turned on in utilitysimulation.java
			   if(myModel.getActivityMsg() == 2)
			   {start = myModel.presentTime();
			   }
			   
			   // 2. excavate the section
			   myModel.excavators.provide(1);
			   myModel.trucks.provide(1);
			   if(myModel.getActivityMsg() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getExcavatingTime() * (Excavation_volume/excavation)), TimeUnit.HOURS));
			   if(myModel.getActivityMsg() == 3)
			   		{ActivityMessage msg_2 = new ActivityMessage(myModel, this, start, "Excavate pipe" + i, myModel.presentTime()) ;
			   		sendMessage(msg_2);}
			   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Excavating Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.trucks.takeBack(1);
			   
			   // 3. shore the section
			   // only for projects that require shoring (set variable Shore to right value in simulation class)
			   if(myModel.getShore() == 1) {
				   myModel.excavators.provide(1);
				   if(myModel.getActivityMsg() == 3)
				   		{start = myModel.presentTime();}
				   hold (new TimeSpan((myModel.getShoringTime() * shoring_time * Pipe_length), TimeUnit.HOURS)); //TODO depends on of expression of shoring_time 
				   if(myModel.getActivityMsg() == 3)
				   		{ActivityMessage msg_3 = new ActivityMessage(myModel, this, start, "Shore" + i, myModel.presentTime()) ;
				   		sendMessage(msg_3);}
				   sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.excavators.takeBack(1);
			   }
			   
			   // No shoring
			   else if(myModel.getShore() == 0) {
			   }
			   
			   else {
				   System.out.println("Shoring setting incorrect, experiment aborted. Choose one of the following settings: 1 for shoring, " +
						 "2 for (not implemented yet), 0 for no shoring activities");
				   myModel.getExperiment().stop();
				   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
			   }
			   
			   // 4. remove the pipe
			   // only for replacement projects (set variable Replacement in UtilitySimulation.java class to true/false )
			   if(myModel.getReplacement()) {
				   	myModel.excavators.provide(1);
				   	if(myModel.getActivityMsg() == 3)
					   {start = myModel.presentTime();}
			   		hold (new TimeSpan((myModel.getPipeRemoveTime() * (Pipe_length/pipe_removal)), TimeUnit.HOURS));
			   		if(myModel.getActivityMsg() == 3)
					   {ActivityMessage msg_4 = new ActivityMessage(myModel, this, start, "Remove Pipe" + i, myModel.presentTime()) ;
					   sendMessage(msg_4);}
			   		sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   }
	
			   // 5. prepare the bed
			   myModel.crews.provide(1);
			   if(myModel.getActivityMsg() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getBedPreparationTime() * ((Trench_Area * Bed_preparation)/preparation)), TimeUnit.HOURS)); 
			   if(myModel.getActivityMsg() == 3)
			   		{ActivityMessage msg_5 = new ActivityMessage(myModel, this, start, "Prepare Bed" + i, myModel.presentTime()) ;
			   		sendMessage(msg_5);}
			   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
			   
			   // add foundation
			  
			   // 6. install the pipe
			   myModel.crews.provide(1);
			   myModel.excavators.provide(1);
			   if(myModel.getActivityMsg() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getPipePlacingTime() * (Pipe_length/pipe_placement)), TimeUnit.HOURS));
			   if(myModel.getActivityMsg() == 3)
			   		{ActivityMessage msg_6 = new ActivityMessage(myModel, this, start, "Install Pipe" + i, myModel.presentTime()) ;
			   		sendMessage(msg_6);}
			   sendTraceNote("Activity: " + getName() + " Install Pipe: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.crews.takeBack(1);
			   
			   // 7. hand/first backfill + compacting
			   //TODO make time neccesary dependent on partial or full backfill, depending on if there are housing connections in the section)
			   myModel.crews.provide(1);
			   if(myModel.getActivityMsg() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getBackfillTime() * ((first_backfill_height * Trench_Area)/backfill)), TimeUnit.HOURS));
			   if(myModel.getActivityMsg() == 3)
			   		{ActivityMessage msg_7 = new ActivityMessage(myModel, this, start, "First Backfill" + i, myModel.presentTime()) ;
			   		sendMessage(msg_7);}
			   sendTraceNote("Activity: " + getName() + " First Backfill: " + start.toString() + 
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
			   // TODO add types of shoring.
			   if(myModel.getShore() == 1)
			   {	myModel.excavators.provide(1);
			   		if(myModel.getActivityMsg() == 3)
			   			{start = myModel.presentTime();}
			   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * shoring_remove_time * Pipe_length), TimeUnit.HOURS)); //TODO depends on of expression of shoring_time
			   		if(myModel.getActivityMsg() == 3)
					   {ActivityMessage msg_8 = new ActivityMessage(myModel, this, start, "Remove Shoring" + i, myModel.presentTime()) ;
			   			sendMessage(msg_8);}
			   		sendTraceNote("Activity: " + getName() + " Remove Trench: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   		if(myModel.getSecondCrew()){ // if there are second crews:
			   			if (UtilitySimulation.getShoreCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) 
			   			{
			   				//TODO Fix: counters get updated after every completed pipe instead of completed section so crews stop way to early if there are second crews
			   				// FIX is to place counters outside of loop.
			   				myModel.crews.stopUse();
			   				myModel.excavators.stopUse();	
			   				System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
			   				System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
			   			}
			   		}
			   }
 
			   // gathers data on total construction time of pipe in main sewer loop, only active if turned on in utilitysimulation.java
			   if(myModel.getActivityMsg() == 2){
				   ActivityMessage msg = new ActivityMessage(myModel, this, start, "Pipe" + i + " construction", myModel.presentTime()) ;
				   sendMessage(msg);  
			   }
			   
			   //	set flag to allow work on connections to start after a certain amount of sewer has been completed. 
			   // 	TODO what if connections overtake pipes?
			   //	if(i * pipe_length >= starting_distance){
			   //   process_connections.initialize // or smth like that
			   //}
			   
		   }
		   
		   // gathers data on total duration of main sewer loop (1 task contains all pipes in section), only active if turned on in utilitysimulation.java
		   if(myModel.getActivityMsg() == 1)
		   {
			   ActivityMessage msg = new ActivityMessage(myModel, this, start, "main sewer loop", myModel.presentTime()) ;
			   sendMessage(msg);
		   }
		   
		   // Allows the next section to start if setting is set to 1 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 1) 
	   		{
		   	myModel.startingCondition.store(1);
	   		}
		   
		   //TODO	think if model should iterate trough connections or if a sum of needed times suffices.
		   //		think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		   
		   // 9. install the connections, only if there are connections.
		   if(this.NUM_CONNECTIONS != 0)
			{
			   if(myModel.getActivityMsgConnection() == 1)
			   		{ start = myModel.presentTime();
			   }
			   for (int j=1; j<=this.NUM_CONNECTIONS; j++) {
				   if(myModel.getSecondCrew()) {
					   myModel.secondcrews.provide(1);}
				   else {myModel.crews.provide(1);}
				   if(myModel.getActivityMsgConnection() == 2 ){
				   		start = myModel.presentTime();
				   }
				   hold (new TimeSpan((myModel.getHousingConnectionTime() * connection_duration), TimeUnit.HOURS)); //multiply by this.NUM_CONNECTIONS or iterate trough them
				   if(myModel.getActivityMsgConnection() == 2 ){
				   		ActivityMessage msg_9 = new ActivityMessage(myModel, this, start, "Housing connections " + j, myModel.presentTime()) ;
				   		sendMessage(msg_9);
				   }
				   sendTraceNote("Activity: " + getName() + " Install housing connection: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   if(myModel.getSecondCrew()){
					   myModel.secondcrews.takeBack(1);}
				   else {myModel.crews.takeBack(1);}
			   }
			   if(myModel.getActivityMsgConnection() == 1)
			   {
			   		ActivityMessage msg_9 = new ActivityMessage(myModel, this, start, "Housing connections", myModel.presentTime()) ;
			   		sendMessage(msg_9);
			   }
	   		}
		   
		   // 10. (second) backfill + compacting
		   //TODO make this action dependent on if there are housing connections, and on crews
		   if(myModel.getSecondCrew()) {
			   	myModel.secondcrews.provide(1);}
		   else {myModel.excavators.provide(1);}
		   myModel.trucks.provide(1);
		   start = myModel.presentTime();
		   hold (new TimeSpan((myModel.getBackfillTime() * ((second_backfill_height * Trench_Area)/backfill)), TimeUnit.HOURS));
		   ActivityMessage msg_10 = new ActivityMessage(myModel, this, start, "Second Backfill", myModel.presentTime()) ;
		   sendMessage(msg_10);
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

		   // Allows the next section to start if setting is set to 2 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 2) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // TODO It could also be that only a broken-stone road is constructed and paving alone is performed later
		   
		   // 11a. roll/prepare surface - sand
		   myModel.rollers.provide(1);
		   start = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_length * Sand_layer )/paving_preparation)), TimeUnit.HOURS));
		   ActivityMessage msg_11 = new ActivityMessage(myModel, this, start, "Roll", myModel.presentTime()) ;
		   sendMessage(msg_11);
		   sendTraceNote("Activity: " + getName() + " Compact: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   myModel.prepare();
   		   if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
		  
		   // Allows the next section to start if setting is set to 3 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 3) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // 11b. roll/prepare surface - broken rock
   		   // TODO finish & investigate if this is a choice or always the same
		   if(myModel.getPrepareSurface() == 1)
			   {myModel.trucks.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_length * Rock_layer )/paving_preparation)), TimeUnit.HOURS)); 
			   ActivityMessage msg_12 = new ActivityMessage(myModel, this, start, "Roll", myModel.presentTime()) ;
			   sendMessage(msg_12);
			   sendTraceNote("Activity: " + getName() + " Broken rock: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.trucks.takeBack(1);
			   myModel.prepare();
	   		   if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			    	  myModel.rollers.stopUse();
			       System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
	   		   		}
			   // Allows the next section to start if setting is set to 4 in UtilitySimulation.java)
			   if(myModel.getSectionWait() == 4) 
			   {
			   myModel.startingCondition.store(1);
			   }
			   }
   		   
   		   // 12. pave  
		   // asphalt paving
		   if(myModel.getNewPavement() == 1) {
			   myModel.pavecrews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
			   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Pave", myModel.presentTime()) ;
			   sendMessage(msg_13);
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
		   
		   // brick paving
		   else if(myModel.getNewPavement() == 2) {
			   myModel.stonepavecrews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
			   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Stone Pave", myModel.presentTime()) ;
			   sendMessage(msg_13);
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
		   
		   // no paving activities
		   else if(myModel.getNewPavement() == 0) {
			   	if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
			   		myModel.getExperiment().stop();
			   		System.out.println("no paving activities performed " + myModel.presentTime());
			   	}	
		   }
		   
		   // paving all sections at once.
		   else if(myModel.getNewPavement() == 3) {
			   	if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
			   		myModel.pavecrews.provide(1);
					   start = myModel.presentTime();
					   hold (new TimeSpan((myModel.getPaveTime() * (Total_Area/paving_time)), TimeUnit.HOURS));
					   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Pave", myModel.presentTime()) ;
					   sendMessage(msg_13);
					   sendTraceNote("Activity: " + getName() + " Asphalt Paving: " + start.toString() + 
							   " End: " + myModel.presentTime().toString());
					   myModel.pavecrews.takeBack(1);
					   myModel.pavecrews.stopUse();
					   myModel.getExperiment().stop();
					   System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());			   		
					   }
			   	
			   	
		   		else{
			   		// After last section paving of entire work starts.
			   		// so all preceding sections have no paving activities
			   		System.out.println(this + " No paving activities, all in last " +  myModel.presentTime());
			   	}
		   }
		   
		   else {
			   System.out.println("Pavement setting incorrect, experiment aborted. Choose one of the following settings: 1 for asphalt, " +
			   		"2 for stone, 3 for paving all sections at once, 0 for no paving activities");
			   myModel.getExperiment().stop();
			   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
		   }

		   // Allows the next section to start if setting is set to 5 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 5) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   System.out.println("Section " + this + " completed");
		}   
		   
		
//=====================================================================================================================================================================
//=====================================================================================================================================================================
		
/**
* Lifecycle Put
**/	
		else
		{
	   
			// 1. break the section or remove stone pavement
		   TimeInstant start = myModel.presentTime();
		   // Break all sections at once.
		   if(myModel.getOldPavement() == 3) {
			   	if (this.getIdentNumber() == (1)){
			   		myModel.breakers.provide(1);
			   		start = myModel.presentTime();
			   		hold (new TimeSpan((myModel.getBreakingTime() * (Total_Area/remove_pavement)), TimeUnit.HOURS));
				   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
				   sendMessage(msg_1);
				   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.breakers.takeBack(1);
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   // TODO add if statement for creating temporary broken rock road or deploying rubbing plates
			   	}
			   	
			   	else{
			   		// Breaking happens once for all sections
			   		// so all preceding sections have no breaking activities
			   		System.out.println(this + " No breaking activities, all in first " + myModel.presentTime());
			   	}
		   }
		   
		   // Break asphalt per section
		   else if(myModel.getOldPavement() == 1) {
			   myModel.breakers.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); //multiply by This.lenght_section
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Break Section", myModel.presentTime()) ;
			   sendMessage(msg_1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
		   }
 
		   // Remove brick pavement
		   // TODO think about who removes stones
		   else if(myModel.getOldPavement() == 2) { 
			   myModel.crews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); //multiply by This.lenght_section
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Remove Stones Section", myModel.presentTime());
			   sendMessage(msg_1);
			   myModel.crews.takeBack(1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   System.out.println("stones removed at simulation time " + myModel.presentTime());
		   }
		   
		   else {
			   System.out.println("Breaking setting incorrect, experiment aborted. Choose one of the following settings: 1 for asphalt, " +
			   		"2 for stone, 3 for breaking all sections at once, 0 for no breaking activities");
			   myModel.getExperiment().stop();
			   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
		   }
		   
		   
		   // gathers data on total duration of main put loop (1 task contains all activities for all put(s)), only active if turned on in utilitysimulation.java
		   if(myModel.getActivityMsgPut() == 1)
		   {
			   start = myModel.presentTime();
		   }
		   
		   // for loop iterating trough main loop (iterates trough all pipes in this section)
		   //TODO usually only one put so no iteration, but with seperated sewer there are two puts.
		   for (int i=1; i<=this.NUM_PIPE; i++)
		   {
			   // gathers start time of every put in main loop, only active if turned on in utilitysimulation.java
			   if(myModel.getActivityMsgPut() == 2)
			   {
				   start = myModel.presentTime();
			   }
			   
			   // 2. excavate the section
			   myModel.excavators.provide(1);
			   myModel.trucks.provide(1);
			   if(myModel.getActivityMsgPut() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getExcavatingTime() * (Excavation_volume/excavation)), TimeUnit.HOURS));
			   if(myModel.getActivityMsgPut() == 3)
			   		{ActivityMessage msg_2 = new ActivityMessage(myModel, this, start, "Excavate pipe" + i, myModel.presentTime()) ;
			   		sendMessage(msg_2);}
			   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Excavating Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.trucks.takeBack(1);
			   
			   // 3. shore the section
			   // only for projects that require shoring (set variable Shore to right value in simulation class)
			   if(myModel.getShore() == 1) {
				   myModel.excavators.provide(1);
				   if(myModel.getActivityMsgPut() == 3)
				   		{start = myModel.presentTime();}
				   hold (new TimeSpan((myModel.getShoringTime() * shoring_time * Pipe_length), TimeUnit.HOURS)); //TODO depends on of expression of shoring_time
				   if(myModel.getActivityMsgPut() == 3)
				   		{ActivityMessage msg_3 = new ActivityMessage(myModel, this, start, "Shore" + i, myModel.presentTime()) ;
				   		sendMessage(msg_3);}
				   sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.excavators.takeBack(1);
			   }
			   
			   // No shoring
			   else if(myModel.getShore() == 0) {
			   }
			   
			   else {
				   System.out.println("Shoring setting incorrect, experiment aborted. Choose one of the following settings: 1 for shoring, " +
						 "2 for (not implemented yet), 0 for no shoring activities");
				   myModel.getExperiment().stop();
				   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
			   }
			   
			   // 4. remove the put
			   // only for replacement projects (set variable Replacement in UtilitySimulation.java class to true/false )
			   if(myModel.getReplacement()) {
				   	myModel.excavators.provide(1);
				   	if(myModel.getActivityMsgPut() == 3)
					   {start = myModel.presentTime();}
				   	hold (new TimeSpan((myModel.getPipeRemoveTime() * put_removal), TimeUnit.HOURS)); 		// TODO PutRemoveTimeStream needed?
			   		if(myModel.getActivityMsgPut() == 3)
					   {ActivityMessage msg_4 = new ActivityMessage(myModel, this, start, "Remove Put" + i, myModel.presentTime()) ;
					   sendMessage(msg_4);}
			   		sendTraceNote("Activity: " + getName() + " Shoring: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   }
	
			   // 5. prepare the bed
			   myModel.crews.provide(1);
			   if(myModel.getActivityMsgPut() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getBedPreparationTime() * ((Trench_Area * Bed_preparation)/preparation)), TimeUnit.HOURS));
			   if(myModel.getActivityMsgPut() == 3)
			   		{ActivityMessage msg_5 = new ActivityMessage(myModel, this, start, "Prepare Bed" + i, myModel.presentTime()) ;
			   		sendMessage(msg_5);}
			   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
			   
			   // add foundation
			  
			   // 6. install the put
			   myModel.crews.provide(1);
			   myModel.excavators.provide(1);
			   if(myModel.getActivityMsgPut() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan((myModel.getPipePlacingTime() * put_placement), TimeUnit.HOURS));    			// TODO PutplacingTimeStream needed?
			   if(myModel.getActivityMsgPut() == 3)
			   		{ActivityMessage msg_6 = new ActivityMessage(myModel, this, start, "Install Put" + i, myModel.presentTime()) ;
			   		sendMessage(msg_6);}
			   sendTraceNote("Activity: " + getName() + " Install Put: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
			   myModel.crews.takeBack(1);
			   
			   // 7. Put connections
			   //TODO make time neccesary dependent on partial or full backfill, depending on if there are housing connections in the section)
			   for (int j=1; j<=this.Num_Put_connections; j++) { // TODO change to put connections
				   myModel.crews.provide(1);
				   if(myModel.getActivityMsgPut() == 3 ){
				   		start = myModel.presentTime();
				   }
				   hold (new TimeSpan((myModel.getHousingConnectionTime() * connection_put_duration), TimeUnit.HOURS)); // TODO change to putConnectionTime
				   if(myModel.getActivityMsgPut() == 3 ){ ////////////////// put connections instead of pipe connections
				   		ActivityMessage msg_7 = new ActivityMessage(myModel, this, start, "Put connections " + j, myModel.presentTime()) ;
				   		sendMessage(msg_7);
				   }
				   sendTraceNote("Activity: " + getName() + " Connect pipes to put: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.crews.takeBack(1);
			   }
			   		
			   // 8. remove shoring
			   // only for projects that require shoring (set variable Shore to right value in simulation class)
			   // TODO add types of shoring.
			   if(myModel.getShore() == 1)
			   {	myModel.excavators.provide(1);
			   		if(myModel.getActivityMsgPut() == 3)
			   			{start = myModel.presentTime();}
			   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * shoring_remove_time * Pipe_length), TimeUnit.HOURS)); //TODO depends on of expression of shoring_time
			   		// TODO bigger length than pipes needed? 
			   		if(myModel.getActivityMsgPut() == 3)
					   {ActivityMessage msg_8 = new ActivityMessage(myModel, this, start, "Remove Shoring" + i, myModel.presentTime()) ;
			   			sendMessage(msg_8);}
			   		sendTraceNote("Activity: " + getName() + " Remove Trench: " + start.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   		if(myModel.getSecondCrew()){ // if there are second crews:
			   			if (UtilitySimulation.getShoreCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) 
			   			{
			   				//TODO Fix: counters get updated after every completed pipe instead of completed section so crews stop way to early if there are second crews
			   				// FIX is to place counters outside of loop.
			   				myModel.crews.stopUse();
			   				myModel.excavators.stopUse();	
			   				System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
			   				System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
			   			}
			   		}
			   }
 
			   // gathers data on total construction time of put in main sewer loop, only active if turned on in utilitysimulation.java
			   if(myModel.getActivityMsgPut() == 2){
				   ActivityMessage msg = new ActivityMessage(myModel, this, start, "Pipe" + i + " construction", myModel.presentTime()) ;
				   sendMessage(msg);  
			   }
			   
		   }
		   // gathers data on total duration of main put loop (1 task contains all puts in section), only active if turned on in utilitysimulation.java
		   if(myModel.getActivityMsgPut() == 1)
		   {
			   ActivityMessage msg = new ActivityMessage(myModel, this, start, "main sewer loop", myModel.presentTime()) ;
			   sendMessage(msg);
		   }
		   
		   // Allows the next section to start if setting is set to 1 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 1) 
	   		{
		   	myModel.startingCondition.store(1);
	   		}
		   
		   //TODO	think if model should iterate trough connections or if a sum of needed times suffices.
		   //		think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		   
		   // 10. backfill + compacting
		   //TODO make this action dependent on if there are housing connections, and on crews
		   if(myModel.getSecondCrew()) {
			   	myModel.secondcrews.provide(1);}
		   else {myModel.excavators.provide(1);}
		   myModel.trucks.provide(1);
		   start = myModel.presentTime();
		   hold (new TimeSpan((myModel.getBackfillTime() * (Excavation_volume/backfill)), TimeUnit.HOURS));
		   ActivityMessage msg_10 = new ActivityMessage(myModel, this, start, "Second Backfill", myModel.presentTime()) ;
		   sendMessage(msg_10);
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

		   // Allows the next section to start if setting is set to 2 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 2) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // TODO It could also be that only a broken-stone road is constructed and paving alone is performed later
		   
		   // 11a. roll/prepare surface - sand
		   myModel.rollers.provide(1);
		   start = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_length * Sand_layer )/paving_preparation)), TimeUnit.HOURS));
		   ActivityMessage msg_11 = new ActivityMessage(myModel, this, start, "Roll", myModel.presentTime()) ;
		   sendMessage(msg_11);
		   sendTraceNote("Activity: " + getName() + " Compact: " + start.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   myModel.prepare();
   		   if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
		  
		   // Allows the next section to start if setting is set to 3 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 3) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // 11b. roll/prepare surface - broken rock
   		   // TODO investigate if this is a choice or always the same
		   if(myModel.getPrepareSurface() == 1)
			   {myModel.trucks.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_length * Rock_layer )/paving_preparation)), TimeUnit.HOURS)); 
			   ActivityMessage msg_12 = new ActivityMessage(myModel, this, start, "Roll", myModel.presentTime()) ;
			   sendMessage(msg_12);
			   sendTraceNote("Activity: " + getName() + " Broken rock: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.trucks.takeBack(1);
			   myModel.prepare();
	   		   if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)) {
			    	  myModel.rollers.stopUse();
			       System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
	   		   		}
			   // Allows the next section to start if setting is set to 4 in UtilitySimulation.java)
			   if(myModel.getSectionWait() == 4) 
			   {
			   myModel.startingCondition.store(1);
			   }
			   }
   		   
   		   // 12. pave  
		   // asphalt paving
		   if(myModel.getNewPavement() == 1) {
			   myModel.pavecrews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
			   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Pave", myModel.presentTime()) ;
			   sendMessage(msg_13);
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
		   
		   // brick paving
		   else if(myModel.getNewPavement() == 2) {
			   myModel.stonepavecrews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
			   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Stone Pave", myModel.presentTime()) ;
			   sendMessage(msg_13);
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
		   
		   // no paving activities
		   else if(myModel.getNewPavement() == 0) {
			   	if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
			   		myModel.getExperiment().stop();
			   		System.out.println("no paving activities performed " + myModel.presentTime());
			   	}	
		   }
		   
		   // paving all sections at once.
		   else if(myModel.getNewPavement() == 3) {
			   	if (UtilitySimulation.getPrepareCounter() == (UtilitySimulation.NUM_SEC + UtilitySimulation.NUM_PUT)){
			   		myModel.pavecrews.provide(1);
					   start = myModel.presentTime();
					   hold (new TimeSpan((myModel.getPaveTime() * (Total_Area/paving_time)), TimeUnit.HOURS));
					   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start, "Pave", myModel.presentTime()) ;
					   sendMessage(msg_13);
					   sendTraceNote("Activity: " + getName() + " Asphalt Paving: " + start.toString() + 
							   " End: " + myModel.presentTime().toString());
					   myModel.pavecrews.takeBack(1);
					   myModel.pavecrews.stopUse();
					   myModel.getExperiment().stop();
					   System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());			   		
					   }
			   	
			   	
		   		else{
			   		// After last section paving of entire work starts.
			   		// so all preceding sections have no paving activities
			   		System.out.println(this + " No paving activities, all in last " +  myModel.presentTime());
			   	}
		   }
		   
		   else {
			   System.out.println("Pavement setting incorrect, experiment aborted. Choose one of the following settings: 1 for asphalt, " +
			   		"2 for stone, 3 for paving all sections at once, 0 for no paving activities");
			   myModel.getExperiment().stop();
			   //TODO can be removed when numbers are connected to GUI selection so no false selection can be made
		   }

		   // Allows the next section to start if setting is set to 5 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 5) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   System.out.println("Put " + this + " completed");   
		}   
		
	   	// parameter for holding the volume of earth to excavate, currently as
	   	// number of trucks to fill ....   - Not (yet) used 
	   	// TODO make excavation_volume a function of section length, width and depth
	   	// TODO make excavation_volume influence nr. of trucks needed and excavating/backfill time
		// private double excavation_volume = 20;	   
	}
	
	
//=====================================================================================================================================================================
//=====================================================================================================================================================================
	
	/**
	 * Section parameters, set in UttilitySimulation.java
	 */
	private UtilitySimulation myModel;
	private int PUT; 
	private int NUM_PIPE;
	private int NUM_CONNECTIONS;
	
	private int Num_Put_connections;  	// number of connections the put has, only if put
	private int Old_pavement; 			// type of old pavement
	private int New_pavement;  			// type of new pavement
	private int Section_length = 10;  	// length of section in
	private int Pipe_length = 1;  		// length of pipes in
	private int Section_width;  		// width of section in
	private int Trench_width;  			// width of Trench v
	private int Trench_depth;  			// depth of Trench in
	private int Old_sewer_type; 		// type of old sewer
	private int New_sewer_type; 		// type of new sewer
	private int Old_diameter;  			// diameter of old sewer 
	private int New_diameter;  			// diameter of new sewer
	private int Asphalt_old;  			// layer thickness of old asphalt in
	private int Asphalt_new;  			// layer thickness of new asphalt in
	private int Cables;  				// weight class of cables in the ground
	private int Length_connections;  	// average length of connections
	private int Depth_connections;  	// average depth of connections
	private int Foundation_type;  		// type foundation used: 1 = , 2 =
	private int Trench_protection;  	// Type of Trench protection used: 1 = , 2 =
	private int Soil_removed;  			// where is the removed soil placed: 1 = , 2 =
	private int Soil_new;  				// where is the new soil placed: 1 = , 2 =
	private int Pipes_old;  			// where are the removed pipes placed: 1 = , 2 =
	private int Pipes_new;  			// where are the new pipes placed: 1 = , 2 =
	private int Total_length;			// length of all sections
	private int Rock_layer;				// height of pavement preparation rock layer in m
	private int Sand_layer;				// height of pavement preparation sand layer in m
	/**
	 * Test for reading the data from an arraylist in utilitysimulation corresponding to this section
	 */
	// private int pipe = (int) myModel.pipes.get((this.getIdentNumber()).intValue();
	
	/**
	* local section parameters
	**/	
	private int remove_pavement;			// production quantity of pavement removal in m^2 per hour
	private int excavation;					// production quantity of excavation in m^3 per hour
	private double pipe_removal;			// production quantity of pipe removal in m per hour
	private double put_removal; 			// production quantity of pipe removal in hour per units
	private double shoring_time;			// production quantity of shoring in m per hour
	private double shoring_remove_time;		// production quantity of shoring removal in m per hour
	private int preparation;				// production quantity of pavement removal in m^3 per hour
	private int pipe_placement;				// production quantity of pipe placement in m per hour
	private int put_placement;				// production quantity of pipe placement in m per hour
	private int connection_duration;		// production duration of a housing or rain water connection in units per hour
	private int Placing_kolk;				// production quantity of a kolk in units per hour
	private double cables_weight;			// weightclass of cables and plumbing in the ground as a factor
	private int foundation_time;			// production quantity of foundation in m per hour
	private int connection_put_duration;	// production duration of a connection to a put in units per hour
	private int backfill;					// production quantity of backfill in m^3 per hour
	private int inspection;					// production quantity of inspection in m per hour
	private double Bed_preparation;			// TODO add to rest of code production quantity of bed preparation in m^2 per hour
	private int paving_preparation;			// TODO add to rest of code production quantity of sand or rock layer in m^3 per hour
	private int paving_time;				// duration of paving time in hours per m^2			
	
	private int NUM_Pipe = (int) Math.ceil(Section_length / Pipe_length); 			// calculation of the required numberof pip
	private int Section_Area = (Section_length * Section_width);					// total surface of the section
	private int Trench_Area = (Pipe_length * Trench_width);							// total surface of the trench
	private int Excavation_volume = (Trench_Area * Trench_depth);  	// excavation volume per pipe
	private int Total_Area = (Total_length * Section_width);						// total working area of alls sections
	private double first_backfill_height = New_diameter * 1.26;						// height of first backfill (pipe diameter + 2x average wall thinkness)
	private double second_backfill_height = Trench_depth - first_backfill_height;	// height of second backfill, only if there are connections
/**
* production values 
* This could be put in a different class later. That class can be initialized from a database in future developments.
**/	
	/*
	// breaking (m^2 per hour)
	if(old_pavement == 1) {remove_pavement = 100;}			// brick pavement
	else if (old_pavement == 40){remove_pavement = 169;}	// asphalt 40 mm
	else if (old_pavement == 50){remove_pavement = 153;}
	else if (old_pavement == 60){remove_pavement = 134;}
	else if (old_pavement == 70){remove_pavement = 116;}
	else if (old_pavement == 80){remove_pavement = 99;}
	
	// Cables & plumbing (weightclass)
	if(Cables == 1){cables_weight = 1.1;}
	else if (Cables == 1){cables_weight = 1.2;}
	else if (Cables == 1){cables_weight = 1.3;}
	else if (Cables == 1){cables_weight = 1.4;}
	else if (Cables == 1){cables_weight = 1.5;}
	 
	// Closing down sewer (duration in hours)
	Closing_sewer = 0.25; 

	// Excavating (m^3 per hour)
	if(myModel.getReplacement == false ) { excavation = 24;}
	else {excavation = 20;}
	
		// Shoring (duration in hours)
	if(myModel.getShore() == 1){shoring_time = 0.1; shoring_remove_time = 0.1;}			// sliding cask
	else if(myModel.getShore() == 2){shoring_time = 0.25; shoring_remove_time = 0.25;}	// sheet piling (damwand)
	else if(myModel.getShore() == 3){shoring_time = 0.3; shoring_remove_time = 0.3;}	// supported wall (gestutte wanden)

// Removing Pipe (m per hour)
	if(old_sewer_type == "Concrete"){							
		if(Old_diameter == 300) {pipe_removal = 21.5;} 
		else if(Old_diameter == 400) {pipe_removal = 19;}
		else if(Old_diameter == 500) {pipe_removal = 17;}
		else if(Old_diameter == 600) {pipe_removal = 15;}
		else if(Old_diameter == 700) {pipe_removal = 13;}
		else if(Old_diameter == 800) {pipe_removal = 11;}
		else if(Old_diameter == 900) {pipe_removal = 10;}
		else if(Old_diameter == 1000) {pipe_removal = 8;}
		else if(Old_diameter == 1250) {pipe_removal = 6;}
		else if(Old_diameter == 1500) {pipe_removal = 4;}
	}
	
	else if(old_sewer_type == "Gres"){						
		if(Old_diameter == 250) {pipe_removal = 24;} 
		else if(Old_diameter == 110) {pipe_removal = 20;} 
		else if(Old_diameter == 125) {pipe_removal = 20;}
		else if(Old_diameter == 160) {pipe_removal = 25;}
		else if(Old_diameter == 200) {pipe_removal = 40;}
		else if(Old_diameter == 250) {pipe_removal = 40;}
		else if(Old_diameter == 315) {pipe_removal = 30;}
	}
	
	else if(old_sewer_type == "Plastic"){						
		pipe_removal = 20; 
	}
	 
	 // Removing Put in hours per units								!!! nog veranderen naar put !!!
	if(old_sewer_type == "Concrete"){							
		if(Old_diameter == 300) {put_removal = 21.5;} 
		else if(Old_diameter == 400) {put_removal = 19;}
		else if(Old_diameter == 500) {put_removal = 17;}
		else if(Old_diameter == 600) {put_removal = 15;}
		else if(Old_diameter == 700) {put_removal = 13;}
		else if(Old_diameter == 800) {put_removal = 11;}
		else if(Old_diameter == 900) {put_removal = 10;}
		else if(Old_diameter == 1000) {put_removal = 8;}
		else if(Old_diameter == 1250) {put_removal = 6;}
		else if(Old_diameter == 1500) {put_removal = 4;}
	}
	
	else if(old_sewer_type == "Gres"){						
		if(Old_diameter == 250) {pipe_removal = 24;} 
		else if(Old_diameter == 110) {put_removal = 20;} 
		else if(Old_diameter == 125) {put_removal = 20;}
		else if(Old_diameter == 160) {put_removal = 25;}
		else if(Old_diameter == 200) {put_removal = 40;}
		else if(Old_diameter == 250) {put_removal = 40;}
		else if(Old_diameter == 315) {put_removal = 30;}
	}
	
	else if(old_sewer_type == "Plastic"){						
		pipe_removal = 20; 
	}
	 
	// Preparing Bed (m^3 per hour)
	if(Trench_width <= 1){
		if(Bed_preparation == 0.1){preparation = 7;}
		if(Bed_preparation == 0.2){preparation = 8;}
		if(Bed_preparation == 0.3){preparation = 9;}
	}
	else{
		if(Bed_preparation == 0.1){preparation = 9;}
		if(Bed_preparation == 0.2){preparation = 10;}
		if(Bed_preparation == 0.3){preparation = 11;}
	}
	 
	// Foundation (duration per pipe)
	if(Foundation == 1){foundation_time = ?,? * pipe length);}
	if(Foundation == 2){foundation_time = ?,? * pipe length);}
	if(Foundation == 3){foundation_time = ?,? * pipe length);}
	 
	// Placing pipe (m per hour)
	if(new_sewer_type == "Concrete"){							
		if(New_diameter == 300) {pipe_placement = 8;} 
		else if(New_diameter == 400) {pipe_placement = 7;}
		else if(New_diameter == 500) {pipe_placement = 6.5;}
		else if(New_diameter == 600) {pipe_placement = 6;}
		else if(New_diameter == 700) {pipe_placement = 5.5;}
		else if(New_diameter == 800) {pipe_placement = 5;}
		else if(New_diameter == 900) {pipe_placement = 4.5;}
		else if(New_diameter == 1000) {pipe_placement = 4;}
		else if(New_diameter == 1250) {pipe_placement = 4;}
		else if(New_diameter == 1500) {pipe_placement = 3;}
	}
	
	else if(new_sewer_type == "Gres"){						
 		if(New_diameter == 110) {pipe_placement = 15;} 
		else if(New_diameter == 125) {pipe_placement = 15;}
		else if(New_diameter == 160) {pipe_placement = 15;}
		else if(New_diameter == 200) {pipe_placement = 15;}
		else if(New_diameter == 250) {pipe_placement = 10;}
		else if(New_diameter == 315) {pipe_placement = 10;}
	}
	
	else if(new_sewer_type == "Plastic"){						
		if(New_diameter == 200) {pipe_placement = 7;} 
		else if(New_diameter == 250) {pipe_placement = 7;}
		else if(New_diameter == 300) {pipe_placement = 6.5;}
		else if(New_diameter == 400) {pipe_placement = 6;}
		else if(New_diameter == 500) {pipe_placement = 5;}
		else if(New_diameter == 600) {pipe_placement = 5;}
	}
	 
	// Placing put (duration in hours)							!!! nog veranderen naar put !!!
	if(new_put_type == "Pre-Fab"){							
		if(New_diameter == 300) {put_placement = 8;} 
		else if(New_diameter == 400) {put_placement = 7;}
		else if(New_diameter == 500) {put_placement = 6.5;}
		else if(New_diameter == 600) {put_placement = 6;}
		else if(New_diameter == 700) {put_placement = 5.5;}
		else if(New_diameter == 800) {put_placement = 5;}
		else if(New_diameter == 900) {put_placement = 4.5;}
		else if(New_diameter == 1000) {put_placement = 4;}
		else if(New_diameter == 1250) {put_placement = 4;}
		else if(New_diameter == 1500) {put_placement = 3;}
	}
	
	else if(new_put_type == "Brick"){						
 		if(New_diameter == 110) {put_placement = 15;} 
		else if(New_diameter == 125) {put_placement = 15;}
		else if(New_diameter == 160) {put_placement = 15;}
		else if(New_diameter == 200) {put_placement = 15;}
		else if(New_diameter == 250) {put_placement = 10;}
		else if(New_diameter == 315) {put_placement = 10;}
	}
	
	//connection put
	if(new_connection_type == "concrete"){						
		if(new_diameter == 200) {connection_put_duration = 7;} 

	
	else if(new_connection_type == "brick"){						
 		if(new_diameter == 110) {connection_put_duration = 15;} 
		
	 
	// Backfilling (m^3 per hour) // 
	if(Trench_width <= 1 ) { backfill = 30;}
	else if(Trench_depth <= 1.5 ) { backfill = 30;}
	else {excavation = 25;}
	 
	// Remove shoring (duration in hours)
	if(myModel.getShore() == "1"){shoring_remove_time = 0.1;}
	else if(myModel.getShore() == "2"){shoring_remove_time = 0.25;}
	else if(myModel.getShore() == "3"){shoring_remove_time = 0.3;}
	
	// Connection (in hours)
	connection_duration = (Length_connections * standard_connection_time + Placing_kolk + Pipe_pipe_connection);
	Placing_kolk = 1; 						//hours per unit
	Pipe_pipe_connection = 2;				//hours per unit
	 
	// Inspection (in m per hour)
	inspection = 75;

	// Paving preparation (in m^2 per hour)
	if(New_pavement == 2)								// brick pavement
	{	if (Sand_layer == 40){paving_preparation = 30;}		
		else if (Sand_layer == 50){paving_preparation = 28;}
		else if (Sand_layer == 60){paving_preparation = 26;}
		else if (Sand_layer == 70){paving_preparation = 24;}
		else if (Sand_layer == 80){paving_preparation = 22;}	
	}
	
	else {												// asphalt pavement
		if (Rock_layer == 200){paving_preparation = 65;}		
		else if (Rock_layer == 250){paving_preparation = 57;}
		else if (Rock_layer == 300){paving_preparation = 50;}
	}  
	 
	// Paving  (in m^2 per hour)
	if(New_pavement == 2){paving_time = 22.5;} 				// brick pavement
	else if (New_pavement == 40){paving_time = ;}			// asphalt 
	else if (New_pavement == 50){paving_time = ;}
	else if (New_pavement == 60){paving_time = ;}
	else if (New_pavement == 70){paving_time = ;}
	else if (New_pavement == 80){paving_time = ;}
	
	// Safety

	
	// Groundwater extraction

	*/	
}
