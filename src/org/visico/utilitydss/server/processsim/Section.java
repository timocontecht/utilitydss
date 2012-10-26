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
	private int NUM_CONNECTIONS;
	/*
	private int NUM_Pipe = Math.ceil(Section_length / pipe_length)
	private int Area = (Section_length * Section_width)
	private int put_connections;  		// number of connections the put has, only if put
	private int old_pavement; 			// type of old pavement
	private int new_pavement;  			// type of new pavement
	private int length;  				// length of section in
	private int pipe_length;  			// length of pipes in
	private int section_nwidth;  		// width of section in
	private int ditch_width;  			// width of ditch v
	private int ditch_depth;  			// depth of ditch in
	private int old_sewer_type; 		// type of old sewer
	private int new_sewer_type; 		// type of new sewer
	private int old_diamete ;  			// diameter of old sewer 
	private int new_diameter;  			// diameter of new sewer
	private int asphalt_old;  			// layer thickness of old asphalt in
	private int asphalt_new;  			// layer thickness of new asphalt in
	private int pavement_old;  			// type of old pavement
	private int pavement_new;  			// type of new pavement
	private int Cables;  				// weight class of cables in the ground
	private int length_connections;  	// average length of connections
	private int depth_connections;  	// average depth of connections
	private int funcation_type;  		// type foundation used: 1 = , 2 =
	private int ditch_protection;  		// Type of ditch protection used: 1 = , 2 =
	private int Soil_removed;  			// where is the removed soil placed: 1 = , 2 =
	private int Soil_new;  				// where is the new soil placed: 1 = , 2 =
	private int pipes_old;  			// where are the removed pipes placed: 1 = , 2 =
	private int pipes_new;  			// where are the new pipes placed: 1 = , 2 =
	etc
	*/
	
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
			int pipes, 
			int connections
			/*,
			int put_connections,
			int old_pavement,
			int new_pavement,
			int length,
			int pipe_length,
			int section_nwidth,
			int ditch_width,
			int ditch_depth,
			int old_sewer_type,
			int new_sewer_type,
			int old_diamete,
			int new_diameter,
			int asphalt_old,
			int asphalt_new,
			int pavement_old,
			int pavement_new,
			int cables,
			int length_connections,
			int depth_connections,
			int funcation_type, 
			int ditch_protection,
			int soil_removed,  	
			int soil_new,  		
			int pipes_old,  		
			int pipes_new,
			*/  ) 
	
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
		NUM_PIPE = pipes;
		NUM_CONNECTIONS = connections;
		/*
		Put_connections = put_connections;  		// number of connections the put has, only if put
		Old_pavement = old_pavement; 				// type of old pavement
		New_pavement = new_pavement;  				// type of new pavement
		Length = length;  							// length of section in
		Pipe_length = pipe_length;  				// length of pipes in
		Section_nwidth = section_nwidth;  			// width of section in
		Ditch_width = ditch_width;  				// width of ditch v
		Ditch_depth = ditch_depth;  				// depth of ditch in
		Old_sewer_type = old_sewer_type; 			// type of old sewer
		New_sewer_type = new_sewer_type; 			// type of new sewer
		Old_diameter = old_diameter;  				// diameter of old sewer 
		New_diameter = new_diameter;  				// diameter of new sewer
		Asphalt_old = asphalt_old;  				// layer thickness of old asphalt in
		Asphalt_new = asphalt_new;  				// layer thickness of new asphalt in // TODO what if multiple layers?
		Pavement_old = pavement_old;  				// type of old pavement
		Pavement_new = pavement_new;  				// type of new pavement
		Cables = cables;  							// weight class of cables in the ground
		Length_connections = length_connections;  	// average length of connections
		Depth_connections = depth_connections;  	// average depth of connections
		Funcation_type = funcation_type;  			// type fundation used: 1 = , 2 =
		Ditch_protection = ditch_protection;  		// Type of ditch protection used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
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
		   
		   myModel.startingCondition.retrieve(1);
		   
		   // variables to create messages for output charts
		   // TimeInstant start, end; 
		   // ActivityMessage msg;
		   
		   // TODO make section/put hold until all preceding sections & puts are done with certain activities
		   // which activities this are is process dependent 
		   // this requires a section to asses what its predecessors (puts and sections) are &
		   // And if all these predecessors completed the specified activities. <-- use bins or counters?
		   // this would prolly be a lot easier if puts where sections with specific put behavior as identitynumber then could be used, or a simple bin/res
		   // think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		   
		   // 1. break the section or remove stone pavement
		   TimeInstant start = myModel.presentTime();
		   // Break all sections at once.
		   if(myModel.getOldPavement() == 3) {
			   	if (this.getIdentNumber() == (1)){
			   		myModel.breakers.provide(1);
			   		start = myModel.presentTime();
			   		hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by Sum{All.lenght_sections)
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
			   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   hold (new TimeSpan(myModel.getBreakingTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
		   
		   
		   // gathers start time of every activity main sewer, only active if turned on in utilitysimulation.java
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
			   hold (new TimeSpan(myModel.getExcavatingTime(), TimeUnit.HOURS)); //multiply by This.volume_section
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
				   hold (new TimeSpan(myModel.getShoringTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   		hold (new TimeSpan(myModel.getPipeRemoveTime(), TimeUnit.MINUTES));
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
			   hold (new TimeSpan(myModel.getBedPreparationTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
			   if(myModel.getActivityMsg() == 3)
			   		{ActivityMessage msg_5 = new ActivityMessage(myModel, this, start, "Prepare Bed" + i, myModel.presentTime()) ;
			   		sendMessage(msg_5);}
			   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
			  
			   // 6. install the pipe
			   myModel.crews.provide(1);
			   myModel.excavators.provide(1);
			   if(myModel.getActivityMsg() == 3)
			   		{start = myModel.presentTime();}
			   hold (new TimeSpan(myModel.getPipePlacingTime(), TimeUnit.HOURS));
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
			   hold (new TimeSpan(myModel.getHandBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   		hold (new TimeSpan(myModel.getRemoveTrenchTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   
		   }
		   // gathers data on total duration of main sewer loop (all pipes in section), only active if turned on in utilitysimulation.java
		   if(myModel.getActivityMsg() == 1)
		   {
			   ActivityMessage msg_4 = new ActivityMessage(myModel, this, start, "main sewer loop", myModel.presentTime()) ;
			   sendMessage(msg_4);
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
			   for (int i=1; i<=this.NUM_CONNECTIONS; i++) {
				   if(myModel.getSecondCrew()) {
					   myModel.secondcrews.provide(1);}
				   else {myModel.crews.provide(1);}
				   if(myModel.getActivityMsgConnection() == 2 ){
				   		start = myModel.presentTime();
				   }
				   hold (new TimeSpan(myModel.getHousingConnectionTime(), TimeUnit.HOURS)); //multiply by this.NUM_CONNECTIONS or iterate trough them
				   if(myModel.getActivityMsgConnection() == 2 ){
				   		ActivityMessage msg_9 = new ActivityMessage(myModel, this, start, "Housing connections " + i, myModel.presentTime()) ;
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
		   hold (new TimeSpan(myModel.getBackfillTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
		   hold (new TimeSpan(myModel.getSurfacePrepareTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   hold (new TimeSpan(myModel.getSurfacePrepareTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   hold (new TimeSpan(myModel.getPaveTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
			   hold (new TimeSpan(myModel.getStonePaveTime(), TimeUnit.HOURS)); //multiply by This.lenght_section
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
					   hold (new TimeSpan(myModel.getPaveTime(), TimeUnit.HOURS)); //multiply by Sum{ALL.lenght_section}
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
		   
	   // parameter for holding the volume of earth to excavate, currently as
	   // number of trucks to fill ....   - Not (yet) used 
	   // TODO make excavation_volume a function of section length, width and depth
	   // TODO make excavation_volume influence nr. of trucks needed and excavating/backfill time
	  //private double excavation_volume = 20;
	  
	   }

}
