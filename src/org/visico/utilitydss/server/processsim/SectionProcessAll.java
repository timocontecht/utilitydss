package org.visico.utilitydss.server.processsim;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.visico.utilitydss.shared.Scenario;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.advancedModellingFeatures.Bin;

public class SectionProcessAll extends ParentProcess
{
	
	/**
	    * @author Simon
	    * Constructor of the section 
	    *
	    * Used to create a new section of a sewer line to be replaced
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	
	public SectionProcessAll(Model owner, 
			String name, 
			boolean showInTrace, 
			int shore, 
			int replacement,
			int old_separated,
			int new_separated,
			double num_put_connections,
			int old_pavement,
			int new_pavement,
			double section_length,
			double pipe_length,
			double section_width,
			double trench_width,
			double trench_depth,
			String old_sewer_type,
			String new_sewer_type,
			double old_diameter,
			double old_diameter_sep,
			double new_diameter,
			double new_diameter_sep,
			double asphalt_old,
			double asphalt_new,
			double cables,
			double length_connections,
			double diameter_connections,
			double foundation_type, 
			double soil_removed,  	
			double soil_new,  		
			double pipes_old,  		
			double pipes_new,
			double rock_layer,
			double sand_layer,
			double old_put_area,
			double old_put_area_sep,
			double new_put_area,
			double new_put_area_sep,
			double bed_preparation,
			int pipe_connections[]
			) 
	
	{
		super(owner, name, showInTrace, shore, replacement, old_separated, new_separated, num_put_connections, old_pavement, 
				new_pavement, section_length, pipe_length, section_width, trench_width, trench_depth, 
				old_sewer_type, new_sewer_type, old_diameter, new_diameter, asphalt_old, asphalt_new, cables, 
				length_connections, diameter_connections, foundation_type, soil_removed, soil_new, pipes_old, 
				pipes_new, rock_layer, sand_layer, old_put_area, new_put_area, bed_preparation);
			
		myModel = (UtilitySimulation)owner;
		Shore = shore;								// Indicates if shoring is used and if so what type is used.
		Replacement = replacement; 					// indicates if this section has old sewer to be removed
		Old_Separated = old_separated;				// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
		New_Separated = new_separated;				// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
		Old_pavement = old_pavement; 				// type of old pavement
		New_pavement = new_pavement;  				// type of new pavement
		Section_length = section_length;  			// length of section in
		Pipe_length = pipe_length;  				// length of pipes in
		Section_width = section_width;  			// width of section in
		Trench_width = trench_width;  				// width of Trench v
		Trench_depth = trench_depth;  				// depth of Trench in
		Old_sewer_type = old_sewer_type; 			// type of old sewer
		New_sewer_type = new_sewer_type; 			// type of new sewer
		Old_diameter = old_diameter;  				// diameter of old sewer 
		Old_diameter_Sep = old_diameter_sep;  		// diameter of old DWA sewer in case of separated
		New_diameter = new_diameter;  				// diameter of new sewer
		New_diameter_Sep = new_diameter_sep;  		// diameter of new DWA sewer in case of separated
		Asphalt_old = asphalt_old;  				// layer thickness of old asphalt in
		Asphalt_new = asphalt_new;  				// layer thickness of new asphalt in // 
		Cables = cables;  							// weight class of cables in the ground
		Length_connections = length_connections;  	// average length of connections
		Diameter_connections = diameter_connections;// average depth of connections
		Foundation_type = foundation_type; 			// type foundation used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
		Rock_layer = rock_layer;					// height of broken rock layer
		Sand_layer = sand_layer;					// height of sand layer	
		Old_put_area = old_put_area;				// area of the old put
		Old_put_area_sep = old_put_area_sep;				// area of the old put
		New_put_area = new_put_area;				// area of the new put
		New_put_area_sep = new_put_area_sep;				// area of the new put
		Pipe_connections = pipe_connections;		// indicates if a pipe has a connection.
		}
	
	/**
	    * Describes this section's life cycle.
	    * This is the actual description of the work that is done
	    * 
	    * First the section specific parameters are calculated
	    * Next the activity durations are selected based on settings in UtilitySimulation.java
	    * 
	    * After this the actual lifecycle is handled
	    * the section will loop through the following stages: 
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
		 * Calculation of section specific parameters
		 */
		NUM_Pipe = (int) Math.ceil(Section_length / Pipe_length); 		// calculation of the required number of pipes
		Section_area = (Section_length * Section_width);				// total surface of the section
		Pipe_area =	(Pipe_length * Trench_width);						// total surface of area around one pipe
		Trench_area = (Pipe_length * NUM_Pipe * Trench_width);			// total surface of the trench
		Excavation_volume = (Pipe_area * Trench_depth);  				// excavation volume per pipe
		Total_area = (myModel.getTotal_length() * Section_width);		// total working area of all sections
		
		if(this.Num_Connections != 0) 		// if there are housing connections backfill is only to top of main sewer pipe
			// height of first backfill in m (pipe diameter + 2x average wall thickness (wall = 0.13 * diameter) calculated by multiplying the pipe diameter with 1.26)
			{First_backfill_height = New_diameter * 1.26 * 0.001;
			// height of second backfill in m, only if there are connections
			Second_backfill_height = Trench_depth - First_backfill_height;}
		else // if there are no housing connections backfill is to bottom of surface layer
		   	{First_backfill_height = Trench_depth;}

		
		/**
		 * Initiation of a duration database containing the activity durations for this section
		 */
		ProductionDatabase ProductionDB = new ProductionDatabase(
			myModel,				//owner
			this, 					//name
			Shore,					// number of pipes in section
			Replacement,			// indicates if there is replacement
			Old_pavement,			// type of old pavement
			New_pavement,			// type of new pavement
			Trench_width,			// width of Trench 
			Trench_depth,			// depth of Trench in
			Old_sewer_type,			// type of old sewer
			New_sewer_type,			// type of new sewer
			Old_diameter,			// diameter of old sewer 
			Old_diameter_Sep,		// diameter of old DWA sewer in case of separated sewer
			New_diameter,			// diameter of new sewer
			New_diameter_Sep,		// diameter of new DWA sewer in case of separated sewer
			Asphalt_old,			// layer thickness of old asphalt in
			Asphalt_new,			// layer thickness of new asphalt in
			Cables,					// weight class of cables in the ground
			Length_connections,		// average length of connections
			Diameter_connections,	// average depth of connections
			Foundation_type, 		// type foundation used: 1 = , 2 =
			Soil_removed,  			// where is the removed soil placed: 1 = , 2 =
			Soil_new,  				// where is the new soil placed: 1 = , 2 =
			Pipes_old,  			// where are the removed pipes placed: 1 = , 2 =
			Pipes_new,  			// where are the new pipes placed: 1 = , 2 =
			Rock_layer,				// height of pavement preparation rock layer in m
			Sand_layer,				// height of pavement preparation sand layer in m
			Old_put_area,			// area of the old put
			Old_put_area_sep,		// area of the new DWA put in case of separated sewer
			New_put_area,			// area of the new put
			New_put_area_sep,		// area of the new DWA put in case of separated sewer
			Bed_preparation			// height of bed preparation layer 
			);
		
				   
//=====================================================================================================================================================================
//=====================================================================================================================================================================
		/** Actual life cycle **/
		
		/**
		 * Section needs to retrieve 1 of this resource in order to start
		 * This allows control to make the section wait to start the lifecycle until the user wants this.
		 * The resource is supplied with 1 unit after a specified activity of the preceding section
		 * Which activity this is can be selected in UtlitySimulation.java
		 */
		myModel.startingCondition.retrieve(1);
		
		//time instants for the different activitymsg's
		TimeInstant start_1 = myModel.presentTime();				// starting time of activity corresponding to detail level 1 as selected in UtilitySimulation.java
		TimeInstant start_2 = myModel.presentTime();				// starting time of activity corresponding to detail level 2 as selected in UtilitySimulation.java
		TimeInstant start_3 = myModel.presentTime();				// starting time of activity corresponding to detail level 3 as selected in UtilitySimulation.java
		connections_done = 0;
		
		/**
		 * Delay before 2nd crews becomes operational
		 * this allows practitioners to test what delay in 2nd crew arival on work site is optimal.
		 *  Only activated if there is a second crew and in first section (first sewer line or put)
		 * 
		 * COMMENT: this results in errors in usage percentages since the program thinks the resource is in use
		 * A better solution would be to instantiate the resource after the selected amount of time has passed.
		 */
		if(myModel.getSecondCrew()== true && this.getIdentNumber() == 1){
			SecondCrewDelay Delay = new SecondCrewDelay(
					myModel,					//owner
					"Delay for second crew", 	//name
					true);
			
			Delay.activate();
		}
		
		// 1. break the section or remove stone pavement
		/**
		 * start new breaking process
		 */
		Breaking pavementbreaking = new Breaking(
		myModel, this, "Breaking Section ", true, Old_pavement, Total_area, Section_area, ProductionDB.getRemove_pavement());
		pavementbreaking.activate();
		this.passivate();			// this needs to passivate while breaking performs it's activities
   
	   // gathers data on total duration of main sewer loop (1 task contains all pipes in section), only active if turned on in utilitysimulation.java
	   start_1 = myModel.presentTime();
	   
	   // for loop iterating trough main loop (iterates trough all pipes in this section)
	   for (int i=1; i<=this.NUM_Pipe; i++)
	   {
		   // gathers start time of every pipe in main loop, only active if turned on in utilitysimulation.java
		   start_2 = myModel.presentTime();
		   
		   // 2. excavate the section
		   myModel.crews.provide(1); myModel.excavators.provide(1); myModel.trucks.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getExcavatingTime() * (Excavation_volume/ProductionDB.getExcavation()) * ProductionDB.getSoil_rm_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));
		   ActivityMessage msg_2 = new ActivityMessage(myModel, this, start_3, "Excavate pipe " + i, myModel.presentTime(), 3) ;
		   sendMessage(msg_2);
		   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Excavating Start: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1); myModel.excavators.takeBack(1); myModel.trucks.takeBack(1);
		   
		   // Closing sewer
		   // One time activity in first section or put in first iteration of pipe
		   if(this.getIdentNumber() == 1 && i == 1){
			   	myModel.crews.provide(1);
			   	start_3 = myModel.presentTime();
			   	hold (new TimeSpan((myModel.getClosingTime() * ProductionDB.getClosing_sewer()), TimeUnit.HOURS));
			   	ActivityMessage msg_2a = new ActivityMessage(myModel, this, start_3, "Closing sewer " + i, myModel.presentTime(), 3) ;
		   		sendMessage(msg_2a);
			   	sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Closing sewer: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
			   	myModel.crews.takeBack(1);
		   }
		   
		   // 3. shore the section
		   // only for projects that require shoring (set variable Shore to right value in simulation class)
		   if(this.Shore  != 0) {
			   myModel.crews.provide(1); myModel.excavators.provide(1);
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getShoringTime() * (Pipe_length*ProductionDB.getShoring()) * ProductionDB.getCables_weight() * ProductionDB.getCables_weight()), TimeUnit.HOURS)); 
			   ActivityMessage msg_3 = new ActivityMessage(myModel, this, start_3, "Shore " + i, myModel.presentTime(), 3) ;
			   sendMessage(msg_3);
			   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Shoring: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   }
		   			   
		   // 4. remove the pipe
		   // only for replacement projects (set variable Replacement in UtilitySimulation.java class to true/false )
		   if(Replacement == 1) {
			    myModel.crews.provide(1); myModel.excavators.provide(1);
			   	for(int j=1; j<=Old_Separated; j++){
				   	start_3 = myModel.presentTime();
			   		if(j == 1){hold (new TimeSpan((myModel.getPipeRemoveTime() * (Pipe_length/ProductionDB.getPipe_removal()) * ProductionDB.getPipe_rm_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));} // pipe removal combined sewer or HWA sewer
			   		else{hold (new TimeSpan((myModel.getPipeRemoveTime() * (Pipe_length/ProductionDB.getPipe_removal_sep()) * ProductionDB.getPipe_rm_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));} // pipe removal separated sewer  Dwa sewer
			   		ActivityMessage msg_4 = new ActivityMessage(myModel, this, start_3, "Remove Pipe " + i +"."+ j, myModel.presentTime(), 3) ;
					sendMessage(msg_4);
			   		sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Remove pipe: " + start_3.toString() + 
			   				" End: " + myModel.presentTime().toString());
			   	}
			   	myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   }

		   // 5a Foundation
		   if(Foundation_type != 0) {
			    myModel.crews.provide(1); myModel.excavators.provide(1);
			   	start_3 = myModel.presentTime();
		   		hold (new TimeSpan((myModel.getPipeRemoveTime() * (Pipe_length/ProductionDB.getFoundation_duration())), TimeUnit.HOURS));
		   		ActivityMessage msg_5 = new ActivityMessage(myModel, this, start_3, "Foundation " + i, myModel.presentTime(), 3) ;
				sendMessage(msg_5);
		   		sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Foundation: " + start_3.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   }
		   
		   // 5b prepare the bed
		   // TODO bed preparations not always necessary? is already possible by keeping the activity 0 but then it still shows in output.
		   // TODO possibly make it the lightest variant of foundation?
		   myModel.crews.provide(1); myModel.excavators.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getBedPreparationTime() * ((Pipe_area * Bed_preparation)/ProductionDB.getPreparation())), TimeUnit.HOURS)); 
		   ActivityMessage msg_6 = new ActivityMessage(myModel, this, start_3, "Prepare Bed " + i, myModel.presentTime(), 3) ;
		   sendMessage(msg_6);
		   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Prepare Bed: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   
		   // 6. install the pipe
		   myModel.crews.provide(1); myModel.excavators.provide(1);
		   for(int j=1; j<=New_Separated; j++){
			   start_3 = myModel.presentTime();
			   if(j == 1){hold (new TimeSpan((myModel.getPipePlacingTime() * (Pipe_length/ProductionDB.getPipe_placement()) * ProductionDB.getPipe_pl_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));}// pipe placement combined sewer or HWA sewer
			   else{hold (new TimeSpan((myModel.getPipePlacingTime() * (Pipe_length/ProductionDB.getPipe_placement_sep()) * ProductionDB.getPipe_pl_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));} // pipe placement separated sewer  Dwa sewer
			   ActivityMessage msg_7 = new ActivityMessage(myModel, this, start_3, "Install Pipe " + i + "."+ j, myModel.presentTime(), 3) ;
			   sendMessage(msg_7);
			   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Install Pipe: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
		   }
		   myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   
		   // 7. First backfill + compacting
		   myModel.crews.provide(1); myModel.excavators.provide(1);
		   start_3 = myModel.presentTime();
		   // if there are housing connections backfill is only to top of main sewer pipe
		   hold (new TimeSpan((myModel.getBackfillTime() * ((First_backfill_height * Pipe_area)/ProductionDB.getBackfill()) * ProductionDB.getSoil_pl_factor() * ProductionDB.getCables_weight()), TimeUnit.HOURS));
		   ActivityMessage msg_8 = new ActivityMessage(myModel, this, start_3, "First Backfill " + i, myModel.presentTime(), 3);
		   sendMessage(msg_8);
		   sendTraceNote("Activity: " + getName() + " Pipe: " + i + " First Backfill: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
		   
 		   // 8. remove shoring
		   // only for projects that require shoring (set variable Shore to right value in simulation class)
		   if(this.Shore  != 0)
		   {	myModel.crews.provide(1); myModel.excavators.provide(1);
		   		start_3 = myModel.presentTime();
		   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * (Pipe_length*ProductionDB.getShoring_remove()) * ProductionDB.getCables_weight()), TimeUnit.HOURS)); 
		   		ActivityMessage msg_9 = new ActivityMessage(myModel, this, start_3, "Remove Shoring " + i, myModel.presentTime(), 3) ;
		   		sendMessage(msg_9);
		   		sendTraceNote("Activity: " + getName() + " Pipe: " + i + " Remove Shoring: " + start_3.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.crews.takeBack(1); myModel.excavators.takeBack(1);
			   }
 
		   // gathers data on total construction time of pipe in main sewer loop, only active if turned on in utilitysimulation.java
		   ActivityMessage msg = new ActivityMessage(myModel, this, start_2, "Pipe " + i + " construction", myModel.presentTime(), 2) ;
		   sendMessage(msg);  
		   
		   /**
		    * If this pipe has a connection (rain or house) a process for developing this connection should be initialed.
		    * 
		    * The  loop iterates trough the total length of the array. 
		    * A connection process is only started if there is a connection in this pipe.
		    */
		   
		   
		   for(int j=0; j<Pipe_connections.length; j++)
			   {
			   if(Pipe_connections[j] == i)
			   	   {
		   
	   			        	
		        	/**
		    		 * Initiation of the connections process. This process can be performed by the same crew as the main sewer after the main 
		    		 * sewer is completed or simultaneously by another crew after a certain amount of work is done on the main sewer.
		    		 * 
		    		 */
		    		Connections housing_connection = new Connections(
			   		myModel, 				// owner
					this,					// parent
					"housing connection ",	// name
					true, 					// show in trace
					ProductionDB.getConnection_duration_hwa(),
					Second_backfill_height,
					Pipe_area,
					ProductionDB.getBackfill(),
					ProductionDB.getSoil_pl_factor()); 
				   	
				   	housing_connection.activate(); 
				   	System.out.println("connection started");
				   	this.Num_Connections ++;
				   	// if there is only one crew the main process waits till the connection is constructed
				   	if (myModel.getSecondCrew() == false) {
				   		this.passivate();
				   	}
			   	}
			 
	    	}
		   
		   
	   // End of pipe iteration lifecycle
	   }
	   
	   // sets flags that this section is done with all pipes
	   myModel.pipes_done();
	   
	   // Stops main sewer crew if there are second crews for connections and main crews completed all their work in all sections.   
	   if(myModel.getSecondCrew()) // if there are second crews:
	   {	
		   if (UtilitySimulation.getPipeCounter() == myModel.getNUM_SEC())
		   {	myModel.crews.stopUse();
	   			myModel.excavators.stopUse();
	   			System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
	   			System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
   			}
	   }   
		   
	   // gathers data on total duration of main sewer loop (1 task contains all pipes in section), only active if turned on in utilitysimulation.java
	   ActivityMessage msg = new ActivityMessage(myModel, this, start_1, "main sewer loop", myModel.presentTime(), 1) ;
	   sendMessage(msg);
	   
	   // Allows the next section to start after this if setting is set to 1 in UtilitySimulation.java)
	   // setting 1 is only possible if there are 2ND crews for connection work
	   if(myModel.getSectionWait() == 1) 
		{
	   		myModel.startingCondition.store(1);
		}
	   
	   // if there are connections and not all connections are finished yet this passivates and waits until all connections have finished.
	   if(Num_Connections != 0 && connections_done < Num_Connections ){	  
		   setPassive(true);
		   this.passivate();
	   }
	   
	   if (Scenario.getNUM_3RDCREW() != 0 && Num_Connections != 0){	  
		   SecondBackfill second_backfill = new SecondBackfill(
   		   		myModel, 				// owner
   				this,					// parent
   				"Second Backfill ",		// name
   				true, 					// show in trace
   				Second_backfill_height,
   				Trench_area,
   				ProductionDB.getBackfill(),
   				ProductionDB.getSoil_pl_factor());
   			   	
		   		second_backfill.activate(); 
   			   	System.out.println("second backfill started");
		   
		   setPassive(true);
		   this.passivate();
	   }

	   /**
	    * work cannot proceed until all connections have been made and second backfill is completed
	    * for each connection completed one unit is stored in: Connections_finished_counter
	    * a number of units equal to the total number of connections is requested and needs to be delivered before the process continues
	    */
	   
  	   myModel.backfill();
	   if (UtilitySimulation.getBackfillCounter() == myModel.getNUM_SEC()) {
		   myModel.trucks.stopUse();
		   
		   if(myModel.getSecondCrew()){
			   myModel.secondcrews.stopUse();
			   System.out.println("resource second crews stopped at simulation time " + myModel.presentTime());
		   
			   if(Scenario.getNUM_3RDCREW() != 0){
				   myModel.thirdcrews.stopUse();
				   System.out.println("resource third crews stopped at simulation time " + myModel.presentTime());
			   }
		   }
		   else {
			   myModel.crews.stopUse();
			   myModel.excavators.stopUse();
			   System.out.println("resource crews stopped at simulation time " + myModel.presentTime());
			   System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
		   }
		   System.out.println("resource trucks stopped at simulation time " + myModel.presentTime());
	   }
	   

	   // Allows the next section to start after this if setting is set to 2 in UtilitySimulation.java)
	   if(myModel.getSectionWait() == 2) 
	   {
		   myModel.startingCondition.store(1);
	   }
	   
	   // TODO It could also be that only a broken-stone road is constructed and paving alone is performed later
	   
	   // 11a. roll/prepare surface - sand for brick paving
	   if(New_pavement == 2){
		   myModel.rollers.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * (Section_area/ProductionDB.getPaving_preparation())), TimeUnit.HOURS));
		   ActivityMessage msg_12 = new ActivityMessage(myModel, this, start_3, "Roll ", myModel.presentTime(), 0) ;
		   sendMessage(msg_12);
		   sendTraceNote("Activity: " + getName() + " Compact: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
	
		   myModel.rollers.takeBack(1);
		   myModel.prepare();
		   if (UtilitySimulation.getPrepareCounter() == myModel.getNUM_SEC()) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
	   }
	  
	   // 11b. roll/prepare surface - broken rock for asphalt paving
	   // TODO finish & investigate if this is a choice or always the same + who does this?
	   if(New_pavement == 1 )
		   {myModel.trucks.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_length * Rock_layer )/ProductionDB.getPaving_preparation())), TimeUnit.HOURS));
		   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start_3, "Roll ", myModel.presentTime(), 0) ;
		   sendMessage(msg_13);
		   sendTraceNote("Activity: " + getName() + " Broken rock: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.trucks.takeBack(1);
		   myModel.prepare();
   		   if (UtilitySimulation.getPrepareCounter() == myModel.getNUM_SEC()) {
		    	  myModel.rollers.stopUse();
		       System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
   		   		}
	   }
	   
	   // Allows the next section to start after this if setting is set to 3 in UtilitySimulation.java)
	   if(myModel.getSectionWait() == 3) 
	   {
		   myModel.startingCondition.store(1);
	   }
	   
	   // 12. pave 
	   /**
		 * start new paving process
		 */
	    Paving pavement = new Paving(
	    		myModel, this, "Paving Section ", true, New_pavement, Total_area, Section_area, ProductionDB.getPaving_time(), Rock_layer, ProductionDB.getPaving_preparation());
		pavement.activate();
		this.passivate();		//this needs to passivate while paving performs it's activities
	   
	   // Allows the next section to start after this if setting is set to 5 in UtilitySimulation.java)
	   if(myModel.getSectionWait() == 4) 
	   {
		   myModel.startingCondition.store(1);
	   }
	   System.out.println(this + " completed");
	}
		   
	
//=====================================================================================================================================================================
//=====================================================================================================================================================================
	
	/**
	 * General section parameters, set in UttilitySimulation.java
	 */
	private UtilitySimulation myModel;
	private int Shore;					// Indicates if shoring is used and if so what type is used.
	private int Replacement; 			// indicates if this section has old sewer to be removed
	private int Old_Separated;			// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	private int New_Separated;			// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
	int Num_Connections;				// indicates the number of connections in this section
	private int Old_pavement; 			// type of old pavement
	private int New_pavement;  			// type of new pavement
	private double Section_length;  	// length of section in m 
	private double Pipe_length;  		// length of pipes in m
	private double Section_width;  		// width of section in m
	private double Trench_width;  		// width of Trench  in m
	private double Trench_depth;  		// depth of Trench in m
	private String Old_sewer_type; 		// type of old sewer
	private String New_sewer_type; 		// type of new sewer
	private double Old_diameter;  		// diameter of old sewer 
	private double Old_diameter_Sep;  	// diameter of old DWA sewer in case of separated sewer
	private double New_diameter;  		// diameter of new sewer
	private double New_diameter_Sep;  	// diameter of new DWA sewer in case of separated sewer
	private double Old_put_area;		// Area of the old put			
	private double Old_put_area_sep;	// Area of the old DWA put in case of separated sewer
	private double New_put_area;		// Area of the new put 
	private double New_put_area_sep;	// Area of the new DWA put in case of separated sewer
	private double Asphalt_old;  		// layer thickness of old asphalt in mm
	private double Asphalt_new;  		// layer thickness of new asphalt in mm 
	private double Cables;  			// weight class of cables in the ground
	private double Length_connections;  // average length of connections in m
	private double Diameter_connections;// average depth of connections in m
	private double Foundation_type;  	// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	private double Soil_removed;  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Soil_new;  			// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_old;  			// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_new;  			// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Rock_layer;			// height of pavement preparation rock layer in m
	private double Sand_layer;			// height of pavement preparation sand layer in m
	private double Bed_preparation;		// height of bed preparation sand layer in m
	private int Pipe_connections[];		// indicates if a pipe has a connection.
	
	/**
	 * Test for reading the data from an arraylist in UtilitySimulation corresponding to this section
	 */
	// private int pipe = (int) myModel.pipes.get((this.getIdentNumber()).intValue();
	
	/**
	* local section parameters
	**/	
	int connections_done;					// number of connections done by 2nd crew already while main crew is still working on main sewer
	private int NUM_Pipe; 					// calculation of the required number of pipes
	private double Section_area;			// total surface of the section
	private double Pipe_area;				// total area around one pipe
	private double Trench_area;				// total surface of the trench
	private double Excavation_volume;  		// excavation volume per pipe
	private double Total_area;				// total working area of all sections
	private double First_backfill_height;	// height of first backfill in m (pipe diameter + 2x average wall thinkness)
	private double Second_backfill_height;	// height of first backfill in m (pipe diameter + 2x average wall thinkness)
	
	/**
	 * Indicator for state of the class
	 */
	private boolean passive;
	
	public boolean isPassive() {
		return passive;
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}
}
