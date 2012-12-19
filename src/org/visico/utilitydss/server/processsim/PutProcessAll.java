package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class PutProcessAll extends ParentProcess
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
	public PutProcessAll(Model owner, 
			String name, 
			boolean showInTrace, 
			int put,
			int shore, 
			int connections,
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
			double new_diameter,
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
			double new_put_area,
			double bed_preparation
			) 
	
	{
		super(owner, name, showInTrace, new_pavement, new_pavement, new_pavement, bed_preparation, new_pavement, new_pavement, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, new_sewer_type, new_sewer_type, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation, bed_preparation);
		myModel = (UtilitySimulation)owner;
		PUT = put;									// section or put:  0 is section, 1 is put.  
		Shore = shore;							
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
		Diameter_connections = diameter_connections;// average depth of connections
		Foundation_type = foundation_type; 			// type foundation used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
		Rock_layer = rock_layer;					// height of broken rock layer
		Sand_layer = sand_layer;					// height of sand layer	
		Old_put_area = old_put_area;				// area of the old put
		New_put_area = new_put_area;				// area of the new put
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
		Section_Area = (Section_length * Section_width);				// total surface of the section
		Trench_Area = (Pipe_length * Trench_width);						// total surface of the trench
		Excavation_volume = (Trench_Area * Trench_depth);  				// excavation volume per pipe
		Total_Area = (myModel.getTotal_length() * Section_width);		// total working area of all sections
		if(this.NUM_CONNECTIONS != 0) 		// if there are housing connections backfill is only to top of main sewer pipe
			// height of first backfill in m (pipe diameter + 2x average wall thickness)
			{first_backfill_height = New_diameter * 1.26 * 0.001;
			// height of second backfill in m, only if there are connections
			second_backfill_height = Trench_depth - first_backfill_height;}
		else // if there are no housing connections backfill is to bottom of surface layer
		   	{first_backfill_height = Trench_depth;}
		
		/**
	* production values 
	* This could be put in a different class later. That class can be initialized from a database in future developments.
	* all data is from bouwkostenonline.nl unless stated otherwise.
	**/	
		
		// breaking (m^2 per hour)		
		if(Old_pavement == 2) {remove_pavement = 100;}			// brick pavement
		else if (Asphalt_old < 45){remove_pavement = 169;}		// asphalt 40 mm
		else if (Asphalt_old < 55){remove_pavement = 153;}
		else if (Asphalt_old < 65){remove_pavement = 134;}
		else if (Asphalt_old < 75){remove_pavement = 116;}
		else if (Asphalt_old >= 75){remove_pavement = 99;}
		
		// Cables & plumbing (weightclass)
		if(Cables == 0){cables_weight = 1.0;}
		else if(Cables == 1){cables_weight = 1.1;}
		else if (Cables == 2){cables_weight = 1.2;}
		else if (Cables == 3){cables_weight = 1.3;}
		else if (Cables == 4){cables_weight = 1.4;}
		else if (Cables == 5){cables_weight = 1.5;}
		 
		// Closing down sewer (duration in hours)
		closing_sewer = 0.25; 

		// Excavating (m^3 per hour)
		if(myModel.getReplacement() == false ) { excavation = 24;}
		else {excavation = 20;}
		
		if(Soil_removed == 1) {soil_rm_factor = 1.1;}						// not validated, factor is a guess
		else {soil_rm_factor = 1;}
		
		// Shoring (in meter per hours) 									!!! data from gwwkosten.n !!!
																			// no data found for removal, therefore placement is used
		if(this.Shore == 1){shoring = 0.1; shoring_remove = 0.1;}			// sliding cask
		
		else if(this.Shore  == 2){shoring = 0.25; shoring_remove = 0.25;}	// sheet piling (damwand)
		
		else if(this.Shore  == 3){											// supported wall (gestutte wanden)
			if(Trench_depth <1.50) {shoring = 26; shoring_remove = 26;}
			else if(Trench_depth <1.50) {shoring = 18; shoring_remove = 18;}
			else {shoring = 12; shoring_remove = 12;}
		}	

	// Removing Pipe (m per hour)
		if(Old_sewer_type == "Concrete"){							
			if(Old_diameter <= 300) {pipe_removal = 21.5;} 
			else if(Old_diameter <= 400) {pipe_removal = 19;}
			else if(Old_diameter <= 500) {pipe_removal = 17;}
			else if(Old_diameter <= 600) {pipe_removal = 15;}
			else if(Old_diameter <= 700) {pipe_removal = 13;}
			else if(Old_diameter <= 800) {pipe_removal = 11;}
			else if(Old_diameter <= 900) {pipe_removal = 10;}
			else if(Old_diameter <= 1000) {pipe_removal = 8;}
			else if(Old_diameter <= 1250) {pipe_removal = 6;}
			else if(Old_diameter <= 1500) {pipe_removal = 4;}
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
		
		if(Pipes_old == 1) {pipe_rm_factor = 1.1;}					// not validated, factor is a guess
		else {pipe_rm_factor = 1;}
		
		 // Removing Put in units per hour							!!! data from gwwkosten.nl, average put height used (2 m high) !!!
		 // Only Pre-fab put, brick and mortar puts are not taken into account	
	 		if(Old_put_area < 1) {put_removal = (4.75);}
			else if(Old_put_area == 1) {put_removal = (3.85);}
			else if(Old_put_area > 2) {put_removal = (1.9);}
			else {put_removal = 3;}									// area between 1 and 2 meter

			 
		// Preparing Bed (m^3 per hour)
		if(Trench_width <= 1){
			if(Bed_preparation <= 0.1){preparation = 7;}
			if(Bed_preparation <= 0.2){preparation = 8;}
			if(Bed_preparation > 0.2){preparation = 9;}
		}
		
		else{
			if(Bed_preparation == 0.1){preparation = 9;}
			if(Bed_preparation == 0.2){preparation = 10;}
			if(Bed_preparation == 0.3){preparation = 11;}
		}
		 
		// Foundation (Production in hour per pipelength)
		if(Foundation_type == 0){foundation_duration = 0;}				// geen fundering	 
		else if(Foundation_type == 1){foundation_duration = 30;}		// verstevigd zand	
		else if(Foundation_type == 2){foundation_duration = 10;}		// bodemplaat
		else if(Foundation_type == 3){foundation_duration = 1;}			// geheide palen

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
		
		if(Pipes_new == 1) {pipe_pl_factor = 1.1;}						// not validated, factor is a guess
		else {pipe_pl_factor = 1;}
		
		// Placing put (duration in hours)								// Pre-fab put, brick and mortar puts are not taken into account								
	 		if(New_put_area < 1) {put_placement = 1/0.63;}
			else if(New_put_area == 1) {put_placement = 1/0.6;}
			else if(New_put_area > 2) {put_placement = 1/0.42;}
			else {put_placement = 1/0.5;}								// area between 1 and 2
		//}
		
		// Connection put (in hours per unit) (used average pipe diameter)					
		connection_put_duration = 2; 
		// I really doubt this number is accurate	
		 
		// Backfilling (m^3 per hour) // 
		/*
		if(Trench_width <= 1 ) {backfill = 30;}
		else if(Trench_depth <= 1.5 ) {backfill = 30;}
		else {backfill = 25;}
		*/
		backfill = 10;
		if(Soil_new == 1) {soil_pl_factor = 1.1;}
		else {soil_pl_factor = 1;}
		 		
		// Housing/Rainwater connection (in hours per unit) // TODO find actual production		
		// TODO what if no kolk but housing connections?
		if(Diameter_connections <= 200 ) {connection_pipe_duration = 15;}			// duration per meter
		else if(Diameter_connections > 200 ) {connection_pipe_duration = 10;}
		Placing_kolk = 1; 						//hours per unit
		Pipe_pipe_connection = 0.05;			//hours per unit
		connection_duration_hwa = ((Length_connections / connection_pipe_duration) + Placing_kolk + Pipe_pipe_connection);
		connection_duration_vw = ((Length_connections / connection_pipe_duration) + Pipe_pipe_connection); //TODO check if housing then no put/kolk? TODO implement
		
		// TODO Inspection (in m per hour)
		inspection = 75;

		// Paving preparation (in m^2 per hour)
		if(New_pavement == 2){								// brick pavement
			if (Sand_layer < 0.045){paving_preparation = 30;}		
			else if (Sand_layer < 0.055){paving_preparation = 28;}
			else if (Sand_layer < 0.065){paving_preparation = 26;}
			else if (Sand_layer < 0.075){paving_preparation = 24;}
			else if (Sand_layer > 0.075){paving_preparation = 22;}	
		}
		
		else {												// asphalt pavement
			if (Rock_layer < 0.25){paving_preparation = 65;}		
			else if (Rock_layer < 0.3){paving_preparation = 57;}
			else if (Rock_layer >= 0.3){paving_preparation = 50;}
		}  
		 
		// Paving  (in m^2 per hour)					
		if(New_pavement == 2){paving_time = 22.5;} 				// brick pavement
		else if (Asphalt_new < 45){paving_time = 20;}			// asphalt 
		else if (Asphalt_new < 55){paving_time = 20;}			
		else if (Asphalt_new < 65){paving_time = 20;}
		else if (Asphalt_new < 75){paving_time = 20;}
		else if (Asphalt_new >= 75){paving_time = 20;}
		else {paving_time = 25;}
		// Safety

		
		
		
//=====================================================================================================================================================================
//=====================================================================================================================================================================
		
		/**
	    * FIXME difficult to model work on multiple sections at once --> drawback inherent to hard-coding the model?
	    * Examples: work on connections can start before entire main sewer of section is laid
	    * 			brick paving can start before entire section is closes as this is a slow process anyway
	    * possibility is to work with flags that allow start of these activities after several pipes have been laid
	    */

		// think about when housing connections can start (maybe before entire main loop is finished) --> how to program?)
		
		/**
		 * Section needs to retrieve 1 of this resource in order to start
		 * This allows control to make the section wait to start the lifecycle until the user wants this.
		 * The resource is supplied with 1 unit after a specified activity of the preceding section
		 * Which activity this is can be selected in UtlitySimulation.java
		 */
		myModel.startingCondition.retrieve(1);
	   
		
			/**
			* Lifecycle Put
			**/	
			TimeInstant start_1 = myModel.presentTime();				// starting time of activity corresponding to detail level 1 as selected in UtilitySimulation.java
			TimeInstant start_2 = myModel.presentTime();				// starting time of activity corresponding to detail level 2 as selected in UtilitySimulation.java
			TimeInstant start_3 = myModel.presentTime();				// starting time of activity corresponding to detail level 3 as selected in UtilitySimulation.java
 
			// 1. break the section or remove stone pavement
			removePavement(Old_pavement);
		   	
		
		   // gathers data on total duration of main put loop (1 task contains all activities for all put(s)), only active if turned on in utilitysimulation.java
		    start_1 = myModel.presentTime();
		   
		   
		   // gathers start time of every put in main loop, only active if turned on in utilitysimulation.java
		   start_2 = myModel.presentTime();
		   
		   
		   // 2. excavate the section
		   myModel.excavators.provide(1);
		   myModel.trucks.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getExcavatingTime() * (Excavation_volume/excavation) * soil_rm_factor * cables_weight), TimeUnit.HOURS));
		   ActivityMessage msg_2 = new ActivityMessage(myModel, this, start_3, "Excavate put ", myModel.presentTime(), 9) ;
		   sendMessage(msg_2);
		   sendTraceNote("Activity: " + getName() + " Pipe: " + " Excavating Start: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.excavators.takeBack(1);
		   myModel.trucks.takeBack(1);
		   
		   // Closing sewer
		   // One time activity in first section or put in first iteration of pipe
		   if(this.getIdentNumber() == 1){
			   	myModel.crews.provide(1);
			   	start_3 = myModel.presentTime();
			   	hold (new TimeSpan((myModel.getClosingTime() * closing_sewer), TimeUnit.HOURS));
			   	ActivityMessage msg_2a = new ActivityMessage(myModel, this, start_3, "Closing sewer ", myModel.presentTime(), 9) ;
		   		sendMessage(msg_2a);
			   	sendTraceNote("Activity: " + getName() + " Pipe: " + " Closing sewer: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
			   	myModel.crews.takeBack(1);
		   }
		   
		   // 3. shore the section
		   // only for projects that require shoring (set variable Shore to right value in simulation class)
		   if(this.Shore  != 0) {
			   myModel.excavators.provide(1);
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getShoringTime() * (Pipe_length/shoring)), TimeUnit.HOURS)); 
			   ActivityMessage msg_3 = new ActivityMessage(myModel, this, start_3, "Shore ", myModel.presentTime(), 9) ;
			   sendMessage(msg_3);
			   sendTraceNote("Activity: " + getName() + " Shoring: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.excavators.takeBack(1);
		   }
		      
		   // 4. remove the put
		   // only for replacement projects (set variable Replacement in UtilitySimulation.java class to true/false )
		   if(myModel.getReplacement()) {
			    myModel.excavators.provide(1);
			    for(int j=1; j<=myModel.getOldSeparated(); j++){
				   	start_3 = myModel.presentTime();
				   	hold (new TimeSpan((myModel.getPutRemoveTime() * (1/put_removal) * pipe_rm_factor), TimeUnit.HOURS));
			   		ActivityMessage msg_4 = new ActivityMessage(myModel, this, start_3, "Remove Put " + j, myModel.presentTime(), 9) ;
					sendMessage(msg_4);
			   		sendTraceNote("Activity: " + getName() + " Put removal: " + start_3.toString() + 
			   				" End: " + myModel.presentTime().toString());
			    }
			    myModel.excavators.takeBack(1);
		   }

		   // 5a Foundation
		   if(myModel.getFoundation()) {
			   	myModel.excavators.provide(1);
			   	start_3 = myModel.presentTime();
		   		hold (new TimeSpan((myModel.getPipeRemoveTime() * (Pipe_length/foundation_duration)), TimeUnit.HOURS));
		   		ActivityMessage msg_5 = new ActivityMessage(myModel, this, start_3, "Foundation ", myModel.presentTime(), 9) ;
				sendMessage(msg_5);
		   		sendTraceNote("Activity: " + getName() + " Foundation: " + start_3.toString() + 
		   				" End: " + myModel.presentTime().toString());
		   		myModel.excavators.takeBack(1);
		   }
		   
		   // 5b prepare the bed
		   myModel.crews.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getBedPreparationTime() * ((Trench_Area * Bed_preparation)/preparation)), TimeUnit.HOURS));
		   ActivityMessage msg_6 = new ActivityMessage(myModel, this, start_3, "Prepare Bed ", myModel.presentTime(), 9) ;
		   sendMessage(msg_6);
		   sendTraceNote("Activity: " + getName() + " Prepare Bed: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);
		  
		   // 6. install the put
		   myModel.crews.provide(1);
		   myModel.excavators.provide(1);
		   for(int j=1; j<=myModel.getNewSeparated(); j++){
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPutPlacingTime() * put_placement * pipe_pl_factor), TimeUnit.HOURS));    			
			   ActivityMessage msg_7 = new ActivityMessage(myModel, this, start_3, "Install Put " + j, myModel.presentTime(), 9) ;
			   sendMessage(msg_7);
			   sendTraceNote("Activity: " + getName() + " Install Put: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
		   }
		   myModel.excavators.takeBack(1);
		   myModel.crews.takeBack(1);
		   
		   // 7. Put connections
		   for (int j=1; j<=this.Num_Put_connections; j++) { 
			   myModel.crews.provide(1);
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPutConnectionTime() * connection_put_duration), TimeUnit.HOURS));
			   ActivityMessage msg_8 = new ActivityMessage(myModel, this, start_3, "Put connection " + j, myModel.presentTime(), 9) ;
			   sendMessage(msg_8);
			   sendTraceNote("Activity: " + getName() + " Connect pipes to put: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.crews.takeBack(1);
		   }
		   
		   // 8. First backfill + compacting
		   myModel.crews.provide(1);
		   start_3 = myModel.presentTime();
		   // if there are housing connections backfill is only to top of main sewer pipe
		   hold (new TimeSpan((myModel.getBackfillTime() * ((first_backfill_height * Trench_Area)/backfill) * soil_pl_factor), TimeUnit.HOURS));
		   ActivityMessage msg_8 = new ActivityMessage(myModel, this, start_3, "First Backfill ", myModel.presentTime(), 3);
		   sendMessage(msg_8);
		   sendTraceNote("Activity: " + getName() + " First Backfill: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.crews.takeBack(1);

		   // 9. remove shoring
		   // only for projects that require shoring (set variable Shore to right value in simulation class)
		   if(this.Shore != 0)
		   {	myModel.excavators.provide(1);
		   		start_3 = myModel.presentTime();
		   		hold (new TimeSpan((myModel.getRemoveTrenchTime() * (Pipe_length/shoring_remove)), TimeUnit.HOURS));  
		   		ActivityMessage msg_10 = new ActivityMessage(myModel, this, start_3, "Remove Shoring ", myModel.presentTime(), 9) ;
		   		sendMessage(msg_10);
		   		sendTraceNote("Activity: " + getName() + " Remove Trench: " + start_3.toString() + 
		   				" End: " + myModel.presentTime().toString());
			   		myModel.excavators.takeBack(1);
			   }
 
			   // gathers data on total construction time of put in main sewer loop, only active if turned on in utilitysimulation.java
		   ActivityMessage msg = new ActivityMessage(myModel, this, start_2, "Put construction", myModel.presentTime(), 8) ;
		   sendMessage(msg);  
			  
		   // End of put iteration lifecycle
		   		   
		   // Stops main sewer crew if there are second crews for connections and they completed all their work.   
		   myModel.pipes_done();
		   if(myModel.getSecondCrew()) // if there are second crews:
		   {	
			   if (UtilitySimulation.getPipeCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT()))
			   {	myModel.crews.stopUse();
	   				myModel.excavators.stopUse();
	   				System.out.println("resource crews stopped at simulation time " + myModel.presentTime() + " 2nd crew finishes housing connections");
	   				System.out.println("resource excavators stopped at simulation time " + myModel.presentTime());
	   			}
		   }
		   
		   // gathers data on total duration of main put loop (1 task contains all puts in section), only active if turned on in utilitysimulation.java
		   ActivityMessage msg1 = new ActivityMessage(myModel, this, start_1, "main sewer loop", myModel.presentTime(), 7) ;
		   sendMessage(msg1);
		   
		   
		   // Allows the next section to start after this if setting is set to 1 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 1) 
	   		{
		   	myModel.startingCondition.store(1);
	   		}
		   
		   // 10. backfill + compacting
		   if(this.NUM_CONNECTIONS != 0){
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.provide(1);}
			   else {myModel.crews.provide(1);}
			   myModel.trucks.provide(1);
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBackfillTime() * (Excavation_volume/backfill) * soil_pl_factor), TimeUnit.HOURS));
			   ActivityMessage msg_11 = new ActivityMessage(myModel, this, start_3, "Second Backfill ", myModel.presentTime(), 0) ;
			   sendMessage(msg_11);
			   sendTraceNote("Activity: " + getName() + " Backfill: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.takeBack(1);}
			   else {myModel.crews.takeBack(1);}
			   myModel.trucks.takeBack(1);
		   }
		   myModel.backfill(); 
		   if (UtilitySimulation.getBackfillCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())) {
			   myModel.trucks.stopUse();
			   myModel.crews.stopUse();
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
			

		   // Allows the next section to start after this if setting is set to 2 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 2) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // TODO It could also be that only a broken-stone road is constructed and paving alone is performed later
		   
		   // 11a. roll/prepare surface - sand
		   myModel.rollers.provide(1);
		   start_3 = myModel.presentTime();
		   hold (new TimeSpan((myModel.getSurfacePrepareTime() * (Section_Area/paving_preparation)), TimeUnit.HOURS));
		   ActivityMessage msg_12 = new ActivityMessage(myModel, this, start_3, "Roll ", myModel.presentTime(), 0) ;
		   sendMessage(msg_12);
		   sendTraceNote("Activity: " + getName() + " Compact: " + start_3.toString() + 
				   " End: " + myModel.presentTime().toString());
		   myModel.rollers.takeBack(1);
		   myModel.prepare();
   		   if (UtilitySimulation.getPrepareCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())) {
			   myModel.rollers.stopUse();
			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
		   }
		  
		   // Allows the next section to start after this if setting is set to 3 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 3) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   // 11b. roll/prepare surface - broken rock
   		   // TODO investigate if this is a choice or always the same
		   if(myModel.getPrepareSurface() == 1)
		   {	myModel.trucks.provide(1);
			   start_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((Section_Area * Rock_layer )/paving_preparation)), TimeUnit.HOURS)); 
			   ActivityMessage msg_13 = new ActivityMessage(myModel, this, start_3, "Roll ", myModel.presentTime(), 0) ;
			   sendMessage(msg_13);
			   sendTraceNote("Activity: " + getName() + " Broken rock: " + start_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.trucks.takeBack(1);
			   myModel.prepare();
	   		   if (UtilitySimulation.getPrepareCounter() == (myModel.getScenario().getNUM_SEC() + 
	   				myModel.getScenario().getNUM_PUT() )) {
			    	  myModel.rollers.stopUse();
			       System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
	   		   		}
			   // Allows the next section to start after this if setting is set to 4 in UtilitySimulation.java)
			   if(myModel.getSectionWait() == 4) 
			   {
			   myModel.startingCondition.store(1);
			   }
		   }
   		   
   		   // 12. pave  
		   pave(New_pavement);
		   		   
		   // Allows the next section to start after this if setting is set to 5 in UtilitySimulation.java)
		   if(myModel.getSectionWait() == 5) 
		   {
		   myModel.startingCondition.store(1);
		   }
		   
		   System.out.println("Put " + this + " completed");   
		}   	   

	//=====================================================================================================================================================================
	//=====================================================================================================================================================================
		   
	// make this a seperate class?
   void removePavement(int oldPavement)
   {
	   TimeInstant start = myModel.presentTime();
	   
	   switch(oldPavement){
			case 1:
    			// asphalt pavement removal per section
    			// Break asphalt per section
			   myModel.breakers.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); 
			   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Break Section ", myModel.presentTime(), 0) ;
			   sendMessage(msg_1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
			   break;
    			
    		case 2:
    			// brick pavement removal per section
			   myModel.crews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (Section_Area/remove_pavement)), TimeUnit.HOURS)); 
			   ActivityMessage msg_2 = new ActivityMessage(myModel, this, start, "Remove Stones Section ", myModel.presentTime(), 0);
			   sendMessage(msg_2);
			   myModel.crews.takeBack(1);
			   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   System.out.println("stones removed at simulation time " + myModel.presentTime());
    			break;
    			
    		case 3:
    			// Breaking ashpalt pavement all sections at once.
			   	if (this.getIdentNumber() == (1)){
			   		myModel.breakers.provide(1);
			   		start = myModel.presentTime();
			   		hold (new TimeSpan((myModel.getBreakingTime() * (Total_Area/remove_pavement)), TimeUnit.HOURS));
				   ActivityMessage msg_3 = new ActivityMessage(myModel, this, start, "Break all ", myModel.presentTime(), 0) ;
				   sendMessage(msg_3);
				   sendTraceNote("Activity: " + getName() + " Breaking Start: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.breakers.takeBack(1);
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
		   		}
				   	
			   	else{
			   		// Breaking happens once for all sections
			   		// so all following sections have no breaking activities
			   		System.out.println(this + " No breaking activities, all in first " + myModel.presentTime());
			   	} 
    			break;
    			
    		default:
			    // no pavement removal
		   		System.out.println("no breaking activities performed " + myModel.presentTime());	   
    			break;
   }}

   void pave(int newPavement)
   {
	TimeInstant start = myModel.presentTime();   
   
    	switch (newPavement){
    		case 1:
    			// paving all per section
    			   // asphalt paving
			   if(myModel.getNewPavement() == 1) {
				   myModel.pavecrews.provide(1);
				   start = myModel.presentTime();
				   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
				   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Pave ", myModel.presentTime(), 0) ;
				   sendMessage(msg_1);
				   sendTraceNote("Activity: " + getName() + " Asphalt Paving: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.pavecrews.takeBack(1);
				   myModel.pave();
				   if (UtilitySimulation.getPaveCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())) {
			   			myModel.pavecrews.stopUse();
			   			myModel.getExperiment().stop();
			   			System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());
			   		}   
			   }
			   break;

    		case 2:
    			// brick pavement per section
    			// brick paving
			   myModel.stonepavecrews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getPaveTime() * (Section_Area/paving_time)), TimeUnit.HOURS));
			   ActivityMessage msg_2 = new ActivityMessage(myModel, this, start, "Stone Pave ", myModel.presentTime(), 0) ;
			   sendMessage(msg_2);
			   sendTraceNote("Activity: " + getName() + " Stone Paving: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.stonepavecrews.takeBack(1);
			   myModel.stonepave();
			   
			   if (UtilitySimulation.getStonePaveCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())) {
		   			myModel.stonepavecrews.stopUse();
		   			myModel.getExperiment().stop();
		   			System.out.println("resource stonepavecrews stopped at simulation time " + myModel.presentTime());
			   } 
				break;
    			
    		case 3:
    			// asphalt pavement all sections at once
			   	if (UtilitySimulation.getPrepareCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())){
			   		myModel.pavecrews.provide(1);
				   start = myModel.presentTime();
				   hold (new TimeSpan((myModel.getPaveTime() * (Total_Area/paving_time)), TimeUnit.HOURS));
				   ActivityMessage msg_3 = new ActivityMessage(myModel, this, start, "Pave all ", myModel.presentTime(), 0) ;
				   sendMessage(msg_3);
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
    			break;
    			
    		default:
    			// no paveing activities
			    // no paving activities
			   	if (UtilitySimulation.getPrepareCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())){
			   		myModel.getExperiment().stop();
			   		System.out.println("no paving activities performed " + myModel.presentTime());
			   	}	   
    			break;
   }}
	
//=====================================================================================================================================================================
//=====================================================================================================================================================================
	
	/**
	 * General section parameters, set in UttilitySimulation.java
	 */
	private UtilitySimulation myModel;
	private int PUT; 
	private int Shore;					// Indicates if shoring is used and if so what type is used.
	private int NUM_CONNECTIONS;
	
	private double Num_Put_connections;  	// number of connections the put has, only if put
	private int Old_pavement; 			// type of old pavement
	private int New_pavement;  			// type of new pavement
	private double Section_length;  	// length of section in m 
	private double Pipe_length;  	// length of pipes in m
	private double Section_width;  		// width of section in m
	private double Trench_width;  		// width of Trench  in m
	private double Trench_depth;  		// depth of Trench in m
	private String Old_sewer_type; 		// type of old sewer
	private String New_sewer_type; 		// type of new sewer
	private double Old_diameter;  			// diameter of old sewer 
	private double New_diameter;  			// diameter of new sewer
	private double Old_put_area;		// Area of the old put						
	private double New_put_area;		// Area of the new put						
	private double Asphalt_old;  			// layer thickness of old asphalt in mm
	private double Asphalt_new;  			// layer thickness of new asphalt in mm 
	private double Cables;  			// weight class of cables in the ground
	private double Length_connections;  // average length of connections in m
	private double Diameter_connections;// average depth of connections in m
	private double Foundation_type;  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	private double Soil_removed;  			// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Soil_new;  				// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_old;  			// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_new;  			// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
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
	private double preparation;				// production quantity of pavement removal in m^3 per hour
	private double pipe_placement;			// production quantity of pipe placement in m per hour
	private double put_placement;			// production quantity of put placement in hours per unit
	private double connection_duration_hwa;	// total production duration of a rain water connection in units per hour
	private double connection_duration_vw;	// total production duration of a housing connection in units per hour  //TODO implement
	private double connection_pipe_duration;// production duration of a housing or rain water pipe in meter per hour
	private double Pipe_pipe_connection;	// production duration of a housing connection pipe to main sewer pipe in units per hour
	private double Placing_kolk;			// TODO production quantity of a rainwater put in units per hour
	private double cables_weight;			// TODO weightclass of cables and plumbing in the ground as a factor
	private double foundation_duration;		// TODO production quantity of foundation in m per hour
	private double connection_put_duration;	// production duration of a connection to a put in units per hour
	private double backfill;				// production quantity of backfill in m^3 per hour
	private int inspection;					// TODO add to code, easier after restructuring as inspection can happen at various times
											// production quantity of inspection in m per hour
	private double Bed_preparation;			// TODO add to utilitysimulation.java production quantity of bed preparation in m^2 per hour
	private int paving_preparation;			// production quantity of sand or rock layer in m^3 per hour
	private double paving_time;				// duration of paving time in hours per m^2			
	private double closing_sewer; 			// TODO duration of closing down sewer in hours
	private double soil_rm_factor;			// delay factor for when soil needs to be put in depot or deported off the site
	private double soil_pl_factor;			// delay factor for when soil needs to taken out of depot or transported to the site
	private double pipe_rm_factor;			// delay factor for when soil needs to be put in depot or deported off the site directly
	private double pipe_pl_factor;			// delay factor for when soil needs to taken out of depot or transported to the site
	
	private int NUM_Pipe; 					// calculation of the required number of pipes
	private double Section_Area;			// total surface of the section
	private double Trench_Area;				// total surface of the trench
	private double Excavation_volume;  		// excavation volume per pipe
	private double Total_Area;				// total working area of all sections
	private double first_backfill_height;	// height of first backfill in m (pipe diameter + 2x average wall thinkness)
	private double second_backfill_height;	// height of second backfill in m, only if there are connections
	
}
