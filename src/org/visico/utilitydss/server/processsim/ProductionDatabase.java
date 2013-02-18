package org.visico.utilitydss.server.processsim;

import java.io.Serializable;

import org.visico.utilitydss.shared.Section;

import desmoj.core.simulator.Model;

/**
 * @author Simon
 * Database containing activity durations
 * For each activity the durations under specified circumstances are stored
 * The circumstances are stored in project and section specific parameters
 */

public class ProductionDatabase 
{
	
	public ProductionDatabase(Model owner,
			ParentProcess parent,
			int shore,
			int replacement, 
			int old_pavement,
			int new_pavement,
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
		myModel = (UtilitySimulation)owner;			// owner of the model
		Parent = parent;							// Parent of the instance of DurationDB
		Shore = shore;								// Indicates if shoring is used and if so what type is used.
		Replacement = replacement; 					// indicates if there is replacement.
		Old_pavement = old_pavement; 				// type of old pavement
		New_pavement = new_pavement;  				// type of new pavement
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
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = loaded in truck, 2 = stored besides trench
		Soil_new = soil_new;  						// where is the new soil placed:  1 = loaded in truck, 2 = stored besides trench
		Pipes_old = pipes_old;  					// where are the removed pipes placed:  1 = loaded in truck, 2 = stored besides trench
		Pipes_new = pipes_new;  					// where are the new pipes placed:  1 = loaded in truck, 2 = stored besides trench
		Rock_layer = rock_layer;					// height of broken rock layer
		Sand_layer = sand_layer;					// height of sand layer	
		Old_put_area = old_put_area;				// area of the old put
		New_put_area = new_put_area;				// area of the new put

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
		
		// Cables & plumbing (weight class)
		if (Cables == 0){cables_weight = 1.0;}
		else if (Cables == 1){cables_weight = 1.1;}
		else if (Cables == 2){cables_weight = 1.2;}
		else if (Cables == 3){cables_weight = 1.3;}
		else if (Cables == 4){cables_weight = 1.4;}
		else if (Cables == 5){cables_weight = 1.5;}
		 
		// Closing down sewer (duration in hours)
		closing_sewer = 0.25; 

		// Excavating (m^3 per hour)
		if(Replacement == 0 ) { excavation = 24;}
		else {excavation = 10;}
		
		if(Soil_removed == 1) {soil_rm_factor = 1.1;}
		else {soil_rm_factor = 1;}
		
		// Shoring (in meter per hours) 											!!! data from gwwkosten.n !!!
		// Average ditch width used													// no data found for removal, therefore placement is used
		if(Shore == 1){shoring = 0.1; shoring_remove = 0.1;}			// sliding cask
		
		else if(Shore == 2){shoring = 0.25; shoring_remove = 0.25;}	// sheet piling (damwand)
		
		else if(Shore == 3){											// supported wall (gestutte wanden)
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
		
		if(Pipes_old == 1) {pipe_rm_factor = 1.1;}
		else {pipe_rm_factor = 1;}
		
		 // Removing Put in units per hour							!!! data from gwwkosten.nl, average put height used (2 m high) !!!
		 // Pre-fab put, brick and mortar puts are not taken into account	
	 		if(Old_put_area < 1) {put_removal = (4.75);}
			else if(Old_put_area == 1) {put_removal = (3.85);}
			else if(Old_put_area > 2) {put_removal = (1.9);}
			else {put_removal = 3;}								// area between 1 and 2 meter

			 
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
		
		if(Pipes_new == 1) {pipe_pl_factor = 1.1;}
		else {pipe_pl_factor = 1;}
		
		// Placing put (duration in hours)								// Pre-fab put, brick and mortar puts are not taken into account								
	 		if(New_put_area < 1) {put_placement = 1/0.63;}
			else if(New_put_area == 1) {put_placement = 1/0.6;}
			else if(New_put_area > 2) {put_placement = 1/0.42;}
			else {put_placement = 1/0.5;}								// area between 1 and 2
		//}
		
		// Connection put (in hours per unit) (used average pipe diameter)					
		connection_put_duration = 1; 
		// I really doubt this number is accurate	
		 
		// Backfilling (m^3 per hour) // 
		
		if(Trench_width <= 1 ) {backfill = 30;}
		else if(Trench_depth <= 1.5 ) {backfill = 30;}
		else {backfill = 25;}
		
		//backfill = 10;
		if(Soil_new == 1) {soil_pl_factor = 1.1;}
		else {soil_pl_factor = 1;}
		 		
		// Housing/Rainwater connection (in hours per unit)	
		// TODO what if no kolk but housing connections?
		if(Diameter_connections <= 200 ) {connection_pipe_duration = 15;}			// duration per meter
		else if(Diameter_connections > 200 ) {connection_pipe_duration = 10;}
		placing_kolk = 1; 						//hours per unit
		pipe_pipe_connection = 0.05;			//hours per unit
		connection_duration_hwa = ((Length_connections / connection_pipe_duration) + placing_kolk + pipe_pipe_connection);
		connection_duration_vw = ((Length_connections / connection_pipe_duration) + pipe_pipe_connection); //TODO check if housing then no put/kolk? 
		
		// Inspection (in m per hour)
		// possible addition for the future
		// inspection = 75; <-- in meter length per hour

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
		 
		// Paving  (in m^2 per hour)							// TODO what if multiple layers of asphalt?
		if(New_pavement == 2){paving_time = 22.5;} 				// brick pavement
		else if (Asphalt_new < 45){paving_time = 20;}			// asphalt 
		else if (Asphalt_new < 55){paving_time = 20;}			
		else if (Asphalt_new < 65){paving_time = 20;}
		else if (Asphalt_new < 75){paving_time = 20;}
		else if (Asphalt_new >= 75){paving_time = 20;}
		else {paving_time = 25;}}
		
		// Safety

		
		// Ground water extraction
		// possible addition for the future
		
	//=================================================================================================
	//=================================================================================================
	

	/**
	 * General section parameters, set in UttilitySimulation.java, recieved from the parent section
	 */
	private UtilitySimulation myModel; 	// owner of the model
	private ParentProcess Parent;		// Parent of the instance of DurationDB
	private double Shore; 				// Indicates if shoring is used and if so what type is used.
	private int Replacement; 			// Indicates if the section has old sewer to be replaced
	private double Old_pavement; 		// type of old pavement
	private double New_pavement;  		// type of new pavement
	private double Trench_width;  		// width of Trench  in m
	private double Trench_depth;  		// depth of Trench in m
	private String Old_sewer_type; 		// type of old sewer
	private String New_sewer_type; 		// type of new sewer
	private double Old_diameter;  		// diameter of old sewer 
	private double New_diameter;  		// diameter of new sewer
	private double Old_put_area;		// Area of the old put						-can be differentiated further in put heights-
	private double New_put_area;		// Area of the new put						-can be differentiated further in put heights-
	private double Asphalt_old;  		// layer thickness of old asphalt in mm
	private double Asphalt_new;  		// layer thickness of new asphalt in mm 
	private double Cables;  			// weight class of cables in the ground
	private double Length_connections;  // average length of connections in m
	private double Diameter_connections;// average depth of connections in m
	private double Foundation_type;  	// type foundation used: 1 = , 2 =
	private double Soil_removed;  		// where is the removed soil placed:  1 = loaded in truck, 2 = stored besides trench
	private double Soil_new;  			// where is the new soil placed:  1 = loaded in truck, 2 = stored besides trench
	private double Pipes_old;  			// where are the removed pipes placed:  1 = loaded in truck, 2 = stored besides trench
	private double Pipes_new;  			// where are the new pipes placed:  1 = loaded in truck, 2 = stored besides trench
	private double Rock_layer;			// height of pavement preparation rock layer in m
	private double Sand_layer;			// height of pavement preparation sand layer in m
		
	/**
	 * Parameter initiation
	 * Initiation of local parameters
	 */
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
	private double connection_duration_vw;	// total production duration of a housing connection in units per hour  
	private double connection_pipe_duration;// production duration of a housing or rain water pipe in meter per hour
	private double pipe_pipe_connection;	// production duration of a housing connection pipe to main sewer pipe in units per hour
	private double placing_kolk;			// production quantity of a rainwater put in units per hour
	private double cables_weight;			// weightclass of cables and plumbing in the ground as a factor
	private double foundation_duration;		// production quantity of foundation in m per hour
	private double connection_put_duration;	// production duration of a connection to a put in units per hour
	private double backfill;				// production quantity of backfill in m^3 per hour
	private int inspection;					// add to code, easier after restructuring as inspection can happen at various times
											// production quantity of inspection in m per hour
	private double Bed_preparation;			// add to utilitysimulation.java production quantity of bed preparation in m^2 per hour
	private int paving_preparation;			// production quantity of sand or rock layer in m^3 per hour
	private double paving_time;				// duration of paving time in hours per m^2			
	private double closing_sewer; 			// duration of closing down sewer in hours
	private double soil_rm_factor;			// delay factor for when soil needs to be put in depot or deported off the site
	private double soil_pl_factor;			// delay factor for when soil needs to taken out of depot or transported to the site
	private double pipe_rm_factor;			// delay factor for when soil needs to be put in depot or deported off the site directly
	private double pipe_pl_factor;			// delay factor for when soil needs to taken out of depot or transported to the site
	
	/**
	 * Getters allowing sections to request the durations of activity's
	 */
	
	   public double getRemove_pavement() {
		      return remove_pavement;
		   }
	   public double getExcavation() {
		      return excavation;
		   }
	   public double getPipe_removal() {
		      return pipe_removal;
		   }
	   public double getPut_removal() {
		      return put_removal;
		   }
	   public double getShoring() {
		      return shoring;
		   }
	   public double getShoring_remove() {
		      return shoring_remove;
		   }
	   public double getPreparation() {
		      return preparation;
		   }
	   public double getPipe_placement() {
		      return pipe_placement;
		   }
	   public double getPut_placement() {
		      return put_placement;
		   }
	   public double getConnection_duration_hwa() {
		      return connection_duration_hwa;
		   }
	   public double getConnection_duration_vw() {
		      return connection_duration_vw;
		   }
	   public double getCables_weight() {
		      return cables_weight;
		   }
	   public double getFoundation_duration() {
		      return foundation_duration;
		   }
	   public double getConnection_put_duration() {
		      return connection_put_duration;
		   }
	   public double getBackfill() {
		      return backfill;
		   }
	   public double getInspection() {
		      return inspection;
		   }
	   public double getPaving_preparation() {
		      return paving_preparation;
		   }
	   public double getPaving_time() {
		      return paving_time;
		   }
	   public double getClosing_sewer() {
		      return closing_sewer;
		   }
	   public double getSoil_rm_factor() {
		      return soil_rm_factor;
		   }
	   public double getSoil_pl_factor() {
		      return soil_pl_factor;
		   }
	   public double getPipe_rm_factor() {
		      return pipe_rm_factor;
		   }
	   public double getPipe_pl_factor() {
		      return pipe_pl_factor;
		   }
}
