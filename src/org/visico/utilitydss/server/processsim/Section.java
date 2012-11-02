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
			int connections,
			int num_put_connections,
			int old_pavement,
			int new_pavement,
			int section_length,
			double pipe_length,
			double section_width,
			double trench_width,
			double trench_depth,
			String old_sewer_type,
			String new_sewer_type,
			int old_diameter,
			int new_diameter,
			int asphalt_old,
			int asphalt_new,
			double cables,
			double length_connections,
			double depth_connections,
			int foundation_type, 
			int soil_removed,  	
			int soil_new,  		
			int pipes_old,  		
			int pipes_new,
			double total_length,
			double rock_layer,
			double sand_layer,
			double old_put_area,
			double new_put_area
			) 
	
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
		PUT = put;									// section or put:  0 is section, 1 is put.  
		NUM_PIPE = pipes;
		NUM_CONNECTIONS = connections;
		
		Num_Put_connections = num_put_connections; 	// number of connections the put has, only if put
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
		New_diameter = new_diameter;  				// diameter of new sewer
		Asphalt_old = asphalt_old;  				// layer thickness of old asphalt in
		Asphalt_new = asphalt_new;  				// layer thickness of new asphalt in // 
		Cables = cables;  							// weight class of cables in the ground
		Length_connections = length_connections;  	// average length of connections
		Depth_connections = depth_connections;  	// average depth of connections
		Foundation_type = foundation_type; 			// type foundation used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
		Total_length = total_length;				// length of all sections
		Rock_layer = rock_layer;					// height of broken rock layer
		Sand_layer = sand_layer;					// height of sand layer	
		Old_put_area = old_put_area;				// area of the old put
		New_put_area = new_put_area;				// area of the new put
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
		 * Calculation of section specific parameters
		 */
		NUM_Pipe = (int) Math.ceil(Section_length / Pipe_length); 			// calculation of the required numberof pip
		Section_Area = (Section_length * Section_width);					// total surface of the section
		Trench_Area = (Pipe_length * Trench_width);						// total surface of the trench
		Excavation_volume = (Trench_Area * Trench_depth);  				// excavation volume per pipe
		Total_Area = (Total_length * Section_width);						// total working area of all sections
		first_backfill_height = New_diameter * 1.26 * 0.001;				// height of first backfill in m (pipe diameter + 2x average wall thinkness)
		second_backfill_height = Trench_depth - first_backfill_height;	// height of second backfill in m, only if there are connections
		
		/**
	* production values 
	* This could be put in a different class later. That class can be initialized from a database in future developments.
	* all data is from bouwkostenonline.nl unless stated otherwise.
	**/	
		
		// breaking (m^2 per hour)
		if(Old_pavement == 2) {remove_pavement = 100;}			// brick pavement
		else if (Asphalt_old == 40){remove_pavement = 169;}	// asphalt 40 mm
		else if (Asphalt_old == 50){remove_pavement = 153;}
		else if (Asphalt_old == 60){remove_pavement = 134;}
		else if (Asphalt_old == 70){remove_pavement = 116;}
		else if (Asphalt_old == 80){remove_pavement = 99;}
		
		// Cables & plumbing (weightclass)
		if(Cables == 1){cables_weight = 1.1;}
		else if (Cables == 2){cables_weight = 1.2;}
		else if (Cables == 3){cables_weight = 1.3;}
		else if (Cables == 4){cables_weight = 1.4;}
		else if (Cables == 5){cables_weight = 1.5;}
		 
		// Closing down sewer (duration in hours)
		closing_sewer = 0.25; 

		// Excavating (m^3 per hour)
		if(myModel.getReplacement() == false ) { excavation = 24;}
		else {excavation = 20;}
		
		// Shoring (in meter per hours) 											!!! data from gwwkosten.n !!!
		// Average ditch width used													no data found for removal, placement used
		if(myModel.getShore() == 1){shoring = 0.1; shoring_remove = 0.1;}			// sliding cask
		
		else if(myModel.getShore() == 2){shoring = 0.25; shoring_remove = 0.25;}	// sheet piling (damwand)
		
		else if(myModel.getShore() == 3){											// supported wall (gestutte wanden)
			if(Trench_depth <1.50) {shoring = 26; shoring_remove = 26;}
			else if(Trench_depth <1.50) {shoring = 18; shoring_remove = 18;}
			else {shoring = 12; shoring_remove = 12;}
		}	

	// Removing Pipe (m per hour)
		if(Old_sewer_type == "Concrete"){							
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
		
		else if(Old_sewer_type == "Gres"){						
			if(Old_diameter == 250) {pipe_removal = 24;} 
			else if(Old_diameter == 110) {pipe_removal = 20;} 
			else if(Old_diameter == 125) {pipe_removal = 20;}
			else if(Old_diameter == 160) {pipe_removal = 25;}
			else if(Old_diameter == 200) {pipe_removal = 40;}
			else if(Old_diameter == 250) {pipe_removal = 40;}
			else if(Old_diameter == 315) {pipe_removal = 30;}
		}
		
		else if(Old_sewer_type == "Plastic"){						
			pipe_removal = 20; 
		}
		 
		 // Removing Put in hours per unit							!!! data from gwwkosten.nl, average put height used (2 m high) !!!
		if(Old_sewer_type == "Pre-fab"){							
	 		if(Old_put_area < 1) {put_removal = 1/4.75;}
			else if(Old_put_area == 1) {put_removal = 1/3.85;}
			else if(Old_put_area > 2) {put_removal = 1/1.9;}
			else {put_removal = 1/3;}								// area between 1 and 2
		}
		
		else if(Old_sewer_type == "Bricks"){						
	 		if(Old_put_area < 1) {put_removal = 1/3.4;}
			else if(Old_put_area == 1) {put_removal = 1/2.9;}
			else if(Old_put_area > 2) {put_removal = 1/1.65;}
			else {put_removal = 1/2.45;}							// area between 1 and 2
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
		//if(Foundation == 1){foundation_time = ?,? * pipe length);}
		//if(Foundation == 2){foundation_time = ?,? * pipe length);}
		//if(Foundation == 3){foundation_time = ?,? * pipe length);}
		 
		// Placing pipe (m per hour)
		if(New_sewer_type == "Concrete"){							
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
		
		else if(New_sewer_type == "Gres"){						
	 		if(New_diameter == 110) {pipe_placement = 15;} 
			else if(New_diameter == 125) {pipe_placement = 15;}
			else if(New_diameter == 160) {pipe_placement = 15;}
			else if(New_diameter == 200) {pipe_placement = 15;}
			else if(New_diameter == 250) {pipe_placement = 10;}
			else if(New_diameter == 315) {pipe_placement = 10;}
		}
		
		else if(New_sewer_type == "Plastic"){						
			if(New_diameter == 200) {pipe_placement = 7;} 
			else if(New_diameter == 250) {pipe_placement = 7;}
			else if(New_diameter == 300) {pipe_placement = 6.5;}
			else if(New_diameter == 400) {pipe_placement = 6;}
			else if(New_diameter == 500) {pipe_placement = 5;}
			else if(New_diameter == 600) {pipe_placement = 5;}
		}
		
		// Placing put (duration in hours)							
		//if(New_put_type == "Pre-Fab"){														
	 		if(New_put_area < 1) {put_placement = 1/0.63;}
			else if(New_put_area == 1) {put_placement = 1/0.6;}
			else if(New_put_area > 2) {put_placement = 1/0.42;}
			else {put_placement = 1/0.5;}								// area between 1 and 2
		//}
		
		//else if(New_put_type == "Brick"){						
	 		if(New_put_area < 1) {put_placement = 1/0.23;}
			else if(New_put_area == 1) {put_placement = 1/0.15;}
			else if(New_put_area > 2) {put_placement = 1/0.09;}
			else {put_placement = 1/0.1;}								// area between 1 and 2
		//}
		
		//connection put
		/*if(New_connection_type == "concrete"){						
			connection_put_duration = 7;} 

		
		else if(New_connection_type == "brick"){						
	 		connection_put_duration = 15;} 
			
		*/ 
		// Backfilling (m^3 per hour) // 
		if(Trench_width <= 1 ) {backfill = 30;}
		else if(Trench_depth >= 1.5 ) {backfill = 30;}
		else {excavation = 25;}
		 		
		// Housing/Rainwater connection (in hours)
		/*connection_duration = (Length_connections * standard_connection_time + Placing_kolk + Pipe_pipe_connection);
		Placing_kolk = 1; 						//hours per unit
		Pipe_pipe_connection = 0.1;				//hours per unit
		*/
		
		// Inspection (in m per hour)
		inspection = 75;

		// Paving preparation (in m^2 per hour)
		if(New_pavement == 2){								// brick pavement
			if (Sand_layer == 0.04){paving_preparation = 30;}		
			else if (Sand_layer == 0.05){paving_preparation = 28;}
			else if (Sand_layer == 0.06){paving_preparation = 26;}
			else if (Sand_layer == 0.07){paving_preparation = 24;}
			else if (Sand_layer == 0.08){paving_preparation = 22;}	
		}
		
		else {												// asphalt pavement
			if (Rock_layer == 0.2){paving_preparation = 65;}		
			else if (Rock_layer == 0.25){paving_preparation = 57;}
			else if (Rock_layer == 0.3){paving_preparation = 50;}
		}  
		 
		// Paving  (in m^2 per hour)
		if(New_pavement == 2){paving_time = 22.5;} 				// brick pavement
		else if (Asphalt_new == 40){paving_time = 20;}			// asphalt 
		else if (Asphalt_new == 50){paving_time = 20;}			// TODO make seperate parameters
		else if (Asphalt_new == 60){paving_time = 20;}
		else if (Asphalt_new == 70){paving_time = 20;}
		else if (Asphalt_new == 80){paving_time = 20;}
		else {paving_time = 25;}
		// Safety

		
		// Groundwater extraction
		/**
	    * Makes  sections wait till preceding section is finished.
	    * when other sections are allowed to start the resource is given back to the bin in UtilitySimulation.java
	    * After which activity this is can be controlled by setting  in UtilitySimulation.java
	    * 
	    * FIXME difficult to model work on multiple sections at once --> drawback inherent to hard-coding the model?
	    */
	   
		// make section/put hold until all preceding sections & puts are done with certain activities
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
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); 
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Remove Stones Section", myModel.presentTime());
			   sendMessage(msg_1);
			   myModel.crews.takeBack(1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   System.out.println("stones removed at simulation time " + myModel.presentTime());
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
			   if(myModel.getShore() == 3) {
				   myModel.excavators.provide(1);
				   if(myModel.getActivityMsg() == 3)
				   		{start = myModel.presentTime();}
				   hold (new TimeSpan((myModel.getShoringTime() * (Pipe_length/shoring)), TimeUnit.HOURS)); 
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
			   if(myModel.getShore() == 3)
			   {	myModel.excavators.provide(1);
			   		if(myModel.getActivityMsg() == 3)
			   			{start = myModel.presentTime();}
			   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * (Pipe_length/shoring_remove)), TimeUnit.HOURS)); 
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
		   //think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		   
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
		   
		   // 11a. roll/prepare surface - sand TODO work further on 11a & b.
		   myModel.rollers.provide(1);
		   start = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_Area * Sand_layer )/paving_preparation)), TimeUnit.HOURS));
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
		   //TODO usually only one put so no iteration, but with separated sewer there are two puts.
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
			   if(myModel.getShore() == 3) {
				   myModel.excavators.provide(1);
				   if(myModel.getActivityMsgPut() == 3)
				   		{start = myModel.presentTime();}
				   hold (new TimeSpan((myModel.getShoringTime() * (Pipe_length/shoring)), TimeUnit.HOURS)); 
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
				   hold (new TimeSpan((myModel.getHousingConnectionTime() * connection_put_duration), TimeUnit.HOURS));
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
			   if(myModel.getShore() == 3)
			   {	myModel.excavators.provide(1);
			   		if(myModel.getActivityMsgPut() == 3)
			   			{start = myModel.presentTime();}
			   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * (Pipe_length/shoring_remove)), TimeUnit.HOURS)); 
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
		   //think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		   
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
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_Area * Sand_layer )/paving_preparation)), TimeUnit.HOURS));
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
			   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_Area * Rock_layer )/paving_preparation)), TimeUnit.HOURS)); 
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

		   // Allows the next section to start if setting is set to 5 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 5) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   System.out.println("Put " + this + " completed");   
		}   
		
	   	// parameter for holding the volume of earth to excavate, currently as
	   	// number of trucks to fill ....   - Not (yet) used 
	   	// TODO make excavation_volume influence number of trucks needed and excavating/backfill time
		// private double excavation_volume = 20;	   
	}
	
//=====================================================================================================================================================================
//=====================================================================================================================================================================
	
	/**
	 * General section parameters, set in UttilitySimulation.java
	 */
	private UtilitySimulation myModel;
	private int PUT; 
	private int NUM_PIPE;
	private int NUM_CONNECTIONS;
	
	private int Num_Put_connections;  	// number of connections the put has, only if put
	private int Old_pavement; 			// type of old pavement
	private int New_pavement;  			// type of new pavement
	private int Section_length = 10;  	// length of section in m 
	private double Pipe_length = 1;  	// length of pipes in m
	private double Section_width;  		// width of section in m
	private double Trench_width;  		// width of Trench  in m
	private double Trench_depth;  		// depth of Trench in m
	private String Old_sewer_type; 		// type of old sewer
	private String New_sewer_type; 		// type of new sewer
	private int Old_diameter;  			// diameter of old sewer 
	private int New_diameter;  			// diameter of new sewer
	private double Old_put_area;		// Area of the old put						-can be differentiated further in put heights-
	private double New_put_area;		// Area of the new put						-can be differentiated further in put heights-
	private int Asphalt_old;  			// layer thickness of old asphalt in mm
	private int Asphalt_new;  			// layer thickness of new asphalt in mm 
	private double Cables;  			// weight class of cables in the ground
	private double Length_connections;  // average length of connections in m
	private double Depth_connections;  	// average depth of connections in m
	private int Foundation_type;  		// type foundation used: 1 = , 2 =
	private int Soil_removed;  			// where is the removed soil placed: 1 = , 2 =
	private int Soil_new;  				// where is the new soil placed: 1 = , 2 =
	private int Pipes_old;  			// where are the removed pipes placed: 1 = , 2 =
	private int Pipes_new;  			// where are the new pipes placed: 1 = , 2 =
	private double Total_length;		// length of all sections
	private double Rock_layer;			// height of pavement preparation rock layer in m
	private double Sand_layer;			// height of pavement preparation sand layer in m
	/**
	 * Test for reading the data from an arraylist in UtilitySimulation corresponding to this section
	 */
	// private int pipe = (int) myModel.pipes.get((this.getIdentNumber()).intValue();
	
	/**
	* local section parameters
	**/	
	private int remove_pavement;			// production quantity of pavement removal in m^2 per hour
	private int excavation;					// production quantity of excavation in m^3 per hour
	private double pipe_removal;			// production quantity of pipe removal in m per hour
	private double put_removal; 			// production quantity of pipe removal in hour per unit
	private double shoring;					// production quantity of shoring in m per hour
	private double shoring_remove;			// production quantity of shoring removal in m per hour
	private int preparation;				// production quantity of pavement removal in m^3 per hour
	private double pipe_placement;			// production quantity of pipe placement in m per hour
	private double put_placement;			// production quantity of put placement in hours per unit
	private int connection_duration;		// production duration of a housing or rain water connection in units per hour
	private int Placing_kolk;				// TODO production quantity of a kolk in units per hour
	private double cables_weight;			// TODO weightclass of cables and plumbing in the ground as a factor
	private int foundation_time;			// TODO production quantity of foundation in m per hour
	private int connection_put_duration;	// production duration of a connection to a put in units per hour
	private int backfill;					// production quantity of backfill in m^3 per hour
	private int inspection;					// TODO production quantity of inspection in m per hour
	private double Bed_preparation;			// production quantity of bed preparation in m^2 per hour
	private int paving_preparation;			// production quantity of sand or rock layer in m^3 per hour
	private double paving_time;				// duration of paving time in hours per m^2			
	private double closing_sewer; 			// TODO duration of closing down sewer in hours
	
	private int NUM_Pipe; 					// calculation of the required number of pipes
	private double Section_Area;			// total surface of the section
	private double Trench_Area;				// total surface of the trench
	private double Excavation_volume;  		// excavation volume per pipe
	private double Total_Area;				// total working area of all sections
	private double first_backfill_height;	// height of first backfill in m (pipe diameter + 2x average wall thinkness)
	private double second_backfill_height;	// height of second backfill in m, only if there are connections
	
}