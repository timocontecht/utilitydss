package org.visico.utilitydss.server.processsim;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.visico.utilitydss.shared.Scenario;

import desmoj.core.advancedModellingFeatures.Bin;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;


public class UtilitySimulation extends Model 
{
	/**
	 * Runs the model.
	 *
	 * @param args is an array of command-line arguments (will be ignored here)
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(java.lang.String[] args) throws IOException, ParserConfigurationException 
	{
	   // create model and experiment as a first step
	   UtilitySimulation model = new UtilitySimulation(null,
	                         "Simple Process-Oriented Sewerage Re-construction Model", true, true);
	   // null as first parameter because it is the main model and has no master model

	   SewerExperiment exp = new SewerExperiment("Sewer Replacement example");
	   // ATTENTION, since the name of the experiment is used in the names of the
	   // output files, you have to specify a string that's compatible with the
	   // filename constraints of your computer's operating system. The remaining three
	   // parameters specify the granularity of simulation time, default unit to
	   // display time and the time formatter to use (null yields a default formatter).
	   
	   // connect both
	   model.connectToExperiment(exp);
	   
	   // set experiment parameters
	   //
	   	   	   
	   exp.setShowProgressBar(true);  // display a progress bar (or not)

	   //exp.stop(new TimeInstant(6000, TimeUnit.HOURS));   
	   exp.tracePeriod(new TimeInstant(0), new TimeInstant(10000, TimeUnit.HOURS));

	                                              // set the period of the trace
	   exp.debugPeriod(new TimeInstant(0), new TimeInstant(10000, TimeUnit.HOURS));   // and debug output
	      // ATTENTION!
	      // Don't use too long periods. Otherwise a huge HTML page will
	      // be created which crashes Netscape :-)
	   
	// start the experiment at simulation time 0.0
	   exp.start();

	   // --> now the simulation is running until it reaches its end criterion
	   // ...
	   // ...
	   // <-- afterwards, the main thread returns here

	   // generate the report (and other output files)
	   exp.report();

	   // stop all threads still alive and close all output files
	   exp.finish();   
	}
	
   /**
    * ProcessesExample constructor.
    *
    * Creates a new ProcessesExample model via calling
    * the constructor of the superclass.
    *
    * @param owner the model this model is part of (set to null when there is 
    *              no such model)
    * @param modelName this model's name
    * @param showInReport flag to indicate if this model shall produce output 
    *                     to the report file
    * @param showInTrace flag to indicate if this model shall produce output 
    *                    to the trace file
    */
   public UtilitySimulation(Model owner, String modelName, boolean showInReport, 
                                           boolean showInTrace) {
      super(owner, modelName, showInReport, showInTrace);
   }
   
   public UtilitySimulation(Model owner, String modelName, boolean showInReport, 
           boolean showInTrace, Scenario scenario) {
	   
	   		
			super(owner, modelName, showInReport, showInTrace);
			

			this.scenario = scenario;


	}

   /** test giving sections a number of pipes to iterate trough
    * TODO make an array with number for each section
    */

   public int getNUM_SEC() {
	return scenario.getNUM_SEC();
}

public void setNUM_SEC(int nUM_SEC) {
	scenario.setNUM_SEC(nUM_SEC);
}

/**
    * Returns a description of the model to be used in the report.
    * @return model description as a string
    */
   public String description() {
      return ".";
   }
   
   /**
    * Activates dynamic model components (simulation processes).
    *
    * This method is used to place all events or processes on the
    * internal event list of the simulator which are necessary to start
    * the simulation.
    *
    * In this case, the sections to replace are added.
    */
   public void doInitialSchedules() 
   { 
	   sections = new ArrayList<SectionProcess>();

	   // initialize the sections 
	   for (int i=0; i<scenario.getNUM_SEC(); i++)
	   {
		   // TODO, currently a very cumbersome way to have a put named put and a section named section, see if it can be shortened.
		   if(put[i]==0)
		   {
			   
			   SectionProcessAll section = new SectionProcessAll(
						this, 					//owner
						"Section", 				//name
						true, 					// ?
						put[i],					// section or put:  0 is section, 1 is put.  
						pipes[i],				// number of pipes in section
						connections[i],		// number of connections in section
						
						num_put_connections[i],	// number of connections the put has, only if put
						old_pavement[i],		// type of old pavement
						new_pavement[i],		// type of new pavement
						section_length[i],		// length of section in
						pipe_length[i],			// length of pipes in
						section_width[i],		// width of section in
						trench_width[i],		// width of Trench 
						trench_depth[i],		// depth of Trench in
						old_sewer_type[i],		// type of old sewer
						new_sewer_type[i],		// type of new sewer
						old_diameter[i],			// diameter of old sewer 
						new_diameter[i],		// diameter of new sewer
						asphalt_old[i],			// layer thickness of old asphalt in
						asphalt_new[i],			// layer thickness of new asphalt in
						cables[i],				// weight class of cables in the ground
						length_connections[i],	// average length of connections
						diameter_connections[i],	// average depth of connections
						foundation_type[i], 	// type foundation used: 1 = , 2 =
						soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						rock_layer,				// height of pavement preparation rock layer in m
						sand_layer,				// height of pavement preparation sand layer in m
						old_put_area[i],			// area of the old put
						new_put_area[i],			// area of the new put
						put_connection_type[i]	// type of put connection (concrete or brick)
						);
	
			   section.activate();
			   sections.add(section);
			   SewerExperiment exp = (SewerExperiment)this.getExperiment();
			   exp.getReceiver().createSectionElement(section);
		   }
		   
		   else
		   {
			   //SectionProcess section = new SectionProcess(this, "Test", true);
			   
			   SectionProcessAll section = new SectionProcessAll(
						this, 					//owner
						"Put", 					//name
						true, 					// ?
						put[i],					// section or put:  0 is section, 1 is put.  
						pipes[i],				// number of pipes in section
						connections[i],		// number of connections in section
						
						num_put_connections[i],	// number of connections the put has, only if put
						old_pavement[i],		// type of old pavement
						new_pavement[i],		// type of new pavement
						section_length[i],		// length of section in
						pipe_length[i],			// length of pipes in
						section_width[i],		// width of section in
						trench_width[i],		// width of Trench 
						trench_depth[i],		// depth of Trench in
						old_sewer_type[i],		// type of old sewer
						new_sewer_type[i],		// type of new sewer
						old_diameter[i],			// diameter of old sewer 
						new_diameter[i],		// diameter of new sewer
						asphalt_old[i],			// layer thickness of old asphalt in
						asphalt_new[i],			// layer thickness of new asphalt in
						cables[i],				// weight class of cables in the ground
						length_connections[i],	// average length of connections
						diameter_connections[i],	// average depth of connections
						foundation_type[i], 	// type foundation used: 1 = , 2 =
						soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						rock_layer,				// height of pavement preparation rock layer in m
						sand_layer,				// height of pavement preparation sand layer in m
						old_put_area[i],			// area of the old put
						new_put_area[i],			// area of the new put
						put_connection_type[i]	// type of put connection (concrete or brick)
						);
	 
			   section.activate();
			   sections.add(section);
			   SewerExperiment exp = (SewerExperiment)this.getExperiment();
			   exp.getReceiver().createSectionElement(section);
			     
		   }

	   }
   }
   
   /**
    * Initializes static model components like distributions and queues.
    */
   public void init() 
   { 
	      // Initialize the TimeStreams
	      // Parameters:
	      // this                = belongs to this model
	      // "ServiceTimeStream" = the name of the stream
	      // 10.0                 = minimum time in minutes to deliver a container
	      // 30.0                 = maximum time in minutes to deliver a container
	      // true                = show in report?
	      // false               = show in trace?
	      breakingTime= new ContDistUniform(this, "BreakingTimeStream",
                  10.0, 30.0, true, false);
	      stoneRemovalTime= new ContDistUniform(this, "StoneRemovalTimeStream",
                  10.0, 30.0, true, false);
	      excavatingTime= new ContDistUniform(this, "ExcavatingTimeStream",
                  10.0, 30.0, true, false);	
	      shoreTime= new ContDistUniform(this, "ShoringTimeStream",
                  10.0, 30.0, true, false);
	      PipeRemoveTime = new ContDistUniform(this, "PipeRemoveTimeStream",
                  10.0, 30.0, true, false);
	      PutRemoveTime = new ContDistUniform(this, "putRemoveTimeStream",
                  10.0, 30.0, true, false);
	      bedPreparationTime= new ContDistUniform(this, "BedPreparationTimeStream",
                  10.0, 30.0, true, false);
	      pipePlacingTime= new ContDistUniform(this, "PipePlacingTimeStream",
                  10.0, 30.0, true, false);
	      putPlacingTime= new ContDistUniform(this, "PutPlacingTimeStream",
                  10.0, 30.0, true, false);
	      removeTrenchTime= new ContDistUniform(this, "TrenchRemoveTimeStream",
                  10.0, 30.0, true, false);
	      housingConnectionTime= new ContDistUniform(this, "HousingConnectionTimeStream",
                  10.0, 30.0, true, false);
	      putConnectionTime= new ContDistUniform(this, "PutConnectionTimeStream",
                  10.0, 30.0, true, false);
	      backfillTime= new ContDistUniform(this, "BackfillTimeStream",
                  10.0, 30.0, true, false);
	      surfacePrepareTime= new ContDistUniform(this, "SurfacePrepareTimeStream",
                  10.0, 30.0, true, false);
	      paveTime= new ContDistUniform(this, "PaveTimeStream",
                  10.0, 30.0, true, false);
	      stonePaveTime= new ContDistUniform(this, "StonePaveTimeStream",
                  10.0, 30.0, true, false);

	      // resources
	      breakers = new PartTimeRes(this, "Resource breakers", scenario.getNUM_BREAKER(), true, true);
	      excavators = new PartTimeRes(this, "Resource Excavators", scenario.getNUM_EXCAVATOR(), true, true);
	      cranes = new PartTimeRes(this, "Resource cranes", scenario.getNUM_CRANE(), true, true);
	      crews = new PartTimeRes(this, "Resource crews", scenario.getNUM_CREW(), true, true);
	      secondcrews = new PartTimeRes(this, "Resource 2ndcrews", scenario.getNUM_2NDCREW(), true, true);
	      rollers = new PartTimeRes(this, "Resource rollers", scenario.getNUM_ROLLER(), true, true);
	      trucks = new PartTimeRes(this, "Resource trucks", scenario.getNUM_TRUCK(), true, true);
	      pavecrews = new PartTimeRes(this, "Resource pavecrews", scenario.getNUM_PAVECREWS(), true, true);
	      stonepavecrews = new PartTimeRes(this, "Resource stonepavecrews", scenario.getNUM_STONEPAVECREWS(), true, true);
	      startingCondition = new Bin (this, "starting condition check", scenario.getNUM_STARTINGCONDITION(), true, true);
}

   
   /**
    * Returns a sample of the random stream used to determine the
    * time needed to perform the described activity.
    *
    * @return double a "activity"Time sample
    */
   public double getBreakingTime(){
	      return breakingTime.sample();
	   }
   public double getStoneRemovalTime() {
	      return stoneRemovalTime.sample();
		}
   public double getExcavatingTime() {
	      return excavatingTime.sample();
	   }
   public double getShoringTime() {
	      return shoreTime.sample();
	   }
   public double getPipeRemoveTime() {
	      return PipeRemoveTime.sample();
		}
   public double getPutRemoveTime() {
	      return PutRemoveTime.sample();
		}
   public double getBedPreparationTime() {
	      return bedPreparationTime.sample();
	   }
   public double getPipePlacingTime() {
	      return pipePlacingTime.sample();
	   }
   public double getPutPlacingTime() {
	      return putPlacingTime.sample();
	   }
   public double getRemoveTrenchTime() {
	      return removeTrenchTime.sample();
   		}
   public double getHousingConnectionTime() {
	      return housingConnectionTime.sample();
		}
   public double getPutConnectionTime() {
	      return putConnectionTime.sample();
		}
   public double getBackfillTime() {
	      return backfillTime.sample();
	   }
   public double getSurfacePrepareTime() {
	      return surfacePrepareTime.sample();
   		}
   public double getPaveTime() {
	      return paveTime.sample();
		}
   public double getStonePaveTime() {
	      return stonePaveTime.sample();
		}

   /**
    * Returns project characteristics.
    * Sections and puts request these functions to identify characteristics of the project
    * These include, among others, the current and new type of pavement and if shoring is required
    * @return Boolean/Int Characteristic
    */  
   public boolean getReplacement() {
	      return Replacement;
	   }
   
   public int getOldPavement() {
	      return OldPavement;
	   }
     
   public int getShore() {
	    return Shore;
		}
   
   public boolean getOldPipeHeavy() {
	    return oldPipeHeavy;
		}
   
   public boolean getNewPipeHeavy() {
	    return newPipeHeavy;
		}
    
   public boolean getSecondCrew() {
    	return scenario.isSecondCrew();
		}
   
   public Scenario getScenario() {
	return scenario;
}

public void setScenario(Scenario scenario) {
	this.scenario = scenario;
}

public int getPrepareSurface() {
	    return prepareSurface;
		}
   
   public int getInspectionType() {
	    return inspectionType;
		}
  
      public int getNewPavement() {
	      return newPavement;
	   }
      
      public int getSectionWait() {
   	    return sectionWait;
   		}
        
   public int getActivityMsg() {
	    return activityMsg;
		}
   
   public int getActivityMsgConnection() {
	    return activityMsgConnection;
		}
   
   public int getActivityMsgPut() {
	    return activityMsgPut;
		}
   public int getTotal_length() {
	   return total_length;
   }
   
    /**
    * Returns a sample of the random stream used to determine
    * the next truck arrival time. This is not used atm because trucks are modeled as a resource.
    *
    * @return double a truckArrivalTime sample
    */
   public double getTruckArrivalTime()  {
      return truckArrivalTime.sample();
   }

   public void report()
   {
	   
   }
   

   
   Scenario scenario = new Scenario();
/*
   /** FOR TESTING PURPOSES (arraylists should get filled by GUI in final code)
    * Model parameters: Project parameters per section in static arrays 
=======
/**
    * Model parameters: GENERAL PROJECT PARAMETERS (the number of sections, puts and resources, etc)
    *
   public static int NUM_SEC = 3;					// number of sections
   public static int NUM_PUT = 0;					// number of puts
   private static int NUM_BREAKER = 1;				// number of breakers
   private static int NUM_EXCAVATOR = 1;			// number of excavators
   private static int NUM_CRANE = 0;				// number of truck-mounted cranes
   private static int NUM_CREW = 1;					// number of crews
   private static int NUM_2NDCREW = 1;				// number of 2ndcrews
   private static int NUM_ROLLER = 2;				// number of rollers
   private static int NUM_TRUCK = 1;				// number of trucks
   private static int NUM_PAVECREWS = 1;			// number of pave crews
   private static int NUM_STONEPAVECREWS = 1;		// number of stone pave crews
   private static int NUM_STARTINGCONDITION = 1;	// forces sections to wait for predecessors to be done with specified activity
 */ 
   
   private static int total_length = 400;			// total length of all sections (for breaking all sections at once) in meter
  
   /** THIS IS FOR TESTING PURPOSES (arraylists should get filled by GUI in final code)
    * 
    * SECTION PROJECT PARAMETERS: Project parameters per section in static arrays 
    * Characteristics of each section/put, stored in arrays
    * examples: housing connections, K&L, puts to be placed with mobile crane
    */
   private static int[] put = 					{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put.  
   private static int[] pipes = 				{ 2, 1, 1, 1, 1, 1, 2, 2, 2, 5 }; 		// number of pipes, only if pipe section
   private static int[] connections = 			{ 1, 1, 1, 1, 1, 1, 2, 2, 2, 2 };  		// number of connections only if pipe section

   private static int[] num_put_connections = 	{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// number of connections the put has, only if put
   private static int[] old_pavement = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 }; 		// type of old pavement
   private static int[] new_pavement = 			{ 1, 1, 1, 1, 1, 1, 2, 2, 2, 2 };  		// type of new pavement
   private static int[] section_length = 		{ 20, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  	// length of section in
   private static int[] pipe_length = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// length of pipes in
   private static int[] section_width = 		{ 4, 4, 4, 4, 1, 1, 2, 2, 2, 2 };  		// width of section in
   private static int[] trench_width = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// width of Trench in  					///////////// bigger with puts?
   private static int[] trench_depth = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// depth of Trench in
   private static String[] old_sewer_type = 	{ "Concrete", "Concrete", "Concrete"}; 		// type of old sewer	Concrete, Gres, Plastic
   private static String[] new_sewer_type = 	{ "Concrete", "Concrete", "Concrete"}; 		// type of new sewer	Concrete, Gres, Plastic
   private static int[] old_diameter = 			{ 300, 300, 300, 300, 300, 300, 2, 2, 2, 2 };  		// diameter of old sewer 
   private static int[] new_diameter = 			{ 300, 300, 300, 300, 300, 300, 2, 2, 2, 2 };  		// diameter of new sewer
   private static int[] asphalt_old = 			{ 40, 40, 40, 40, 1, 1, 2, 2, 2, 2 };  		// layer thickness of old asphalt in
   private static int[] asphalt_new = 			{ 40, 40, 40, 40, 1, 1, 2, 2, 2, 2 };  		// layer thickness of new asphalt in // TODO what if multiple layers?
   private static int[] cables =		 		{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// weight class of cables in the ground
   private static int[] length_connections =	{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// average length of connections
   private static int[] diameter_connections =	{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// average depth of connections
   private static int[] foundation_type =		{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// type foundation used: 1 = , 2 =
   private static int[] soil_removed = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// where is the removed soil placed: 1 = , 2 =
   private static int[] soil_new = 				{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// where is the new soil placed: 1 = , 2 =
   private static int[] pipes_old = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// where are the removed pipes placed: 1 = , 2 =
   private static int[] pipes_new = 			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };  		// where are the new pipes placed: 1 = , 2 =
   private static int[] old_put_area =			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };		// Area of the old put
   private static int[] new_put_area =			{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };		// Area of the new put
   private static double rock_layer = 0.3;		//{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation rock layer in m 
   private static double sand_layer = 0.04;		//{ 2, 2, 2, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
   private static String[] put_connection_type = { "Concrete", "Concrete", "Concrete" };		// type of put connection (concrete or brick) 
   
   /**  COMMENTED OUT as the arrays above are used for testing purposes. 
    * This is preparation for use with GUI
    * Model parameters: Project parameters per section in dynamic arraylists
    * Characteristics of each section/put, stored in arrays
    */
   /* 
   ArrayList<Integer> put = new ArrayList<Integer>(0); 					// indicates if section is pipe section or put, 0 is section, 1 is put.
   ArrayList<Integer> pipes = new ArrayList<Integer>(0); 				// number of pipes, only if pipe section
   ArrayList<Integer> connections = new ArrayList<Integer>(0);			// number of connections only if pipe section
   ArrayList<Integer> num_put_connections = new ArrayList<Integer>(0);	// number of connections the put has, only if put
   ArrayList<Integer> old_pavement = new ArrayList<Integer>(0);			// type of old pavement
   ArrayList<Integer> new_pavement = new ArrayList<Integer>(0);			// type of new pavement
   ArrayList<Integer> section_length = new ArrayList<Integer>(0);		// length of section in
   ArrayList<Integer> pipe_length = new ArrayList<Integer>(0);			// length of pipes in
   ArrayList<Integer> section_width = new ArrayList<Integer>(0);		// width of section in	
   ArrayList<Integer> trench_width = new ArrayList<Integer>(0);			// width of Trench in
   ArrayList<Integer> trench_depth = new ArrayList<Integer>(0);			// depth of Trench in
   ArrayList<Integer> old_sewer_type = new ArrayList<Integer>(0);		// type of old sewer	Concrete, Gres, Plastic
   ArrayList<Integer> new_sewer_type = new ArrayList<Integer>(0);		// type of new sewer	Concrete, Gres, Plastic
   ArrayList<Integer> old_diameter = new ArrayList<Integer>(0);			// diameter of old sewer
   ArrayList<Integer> new_diameter = new ArrayList<Integer>(0);			// diameter of new sewer
   ArrayList<Integer> asphalt_old = new ArrayList<Integer>(0);			// layer thickness of old asphalt in
   ArrayList<Integer> asphalt_new = new ArrayList<Integer>(0);			// layer thickness of new asphalt in 
   ArrayList<Integer> cables = new ArrayList<Integer>(0);				// weight class of cables in the ground
   ArrayList<Integer> length_connections = new ArrayList<Integer>(0);	// average length of connections	
   ArrayList<Integer> depth_connections = new ArrayList<Integer>(0);	// average depth of connections
   ArrayList<Integer> foundation_type = new ArrayList<Integer>(0);		// type foundation used: 1 = , 2 =
   ArrayList<Integer> trench_protection = new ArrayList<Integer>(0);	// Type of Trench protection used: 1 = , 2 =
   ArrayList<Integer> soil_removed = new ArrayList<Integer>(0);			// where is the removed soil placed: 1 = , 2 =
   ArrayList<Integer> soil_new = new ArrayList<Integer>(0);				// where is the new soil placed: 1 = , 2 =
   ArrayList<Integer> pipes_old = new ArrayList<Integer>(0);			// where are the removed pipes placed: 1 = , 2 =
   ArrayList<Integer> pipes_new = new ArrayList<Integer>(0);			// where are the new pipes placed: 1 = , 2 =
   ArrayList<Integer> old_put_area = new ArrayList<Integer>(0);			// Area of the old put
   ArrayList<Integer> new_put_area = new ArrayList<Integer>(0);			// Area of the new put
   private static int total_length = 400								// total length of all sections (for breaking all sections at once)
   private static double rock_layer = 0,3								// height of pavement preparation rock layer in m 
   private static double sand_layer = 0,3								// height of pavement preparation sand layer in m
   ArrayList<Integer> put_connection_type = new ArrayList<Integer>(0);
   */  
   
/**
   * Model parameters: SIMULATION SETTINGS
   */
   private static int OldPavement = 3;				// indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
													// 3 means asphalt; break all sections at start, other gives error
   private static int Shore = 3;					// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 2 means Sheet piling (damwand), 3 means supported walls
   private static boolean Replacement = true;		// indicates if the project is a replacement project
   private static boolean oldPipeHeavy	= false; 	// indicates if the old pipes are to heavy to be placed by mobile excavator and therefore require mobile crane	
   private static boolean newPipeHeavy	= false; 	// indicates if the new pipes are to heavy to be placed by mobile excavator and therefore require mobile crane	
   													// for puts this is indicated by an array per put as sizes differ.

   private static boolean secondCrew = false;		// indicates if there is a 2nd crew present to perform housing connections

   private static int prepareSurface = 2;			// indicates if broken rock is placed : 1 = yes, 2 = no
   private static int newPavement = 2;				// indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones, 

													// 3 means asphalt; pave all sections at start, other gives error
   private static int sectionWait = 1;				// indicates after which activity the next section starts: 1 = after main loop, 2 = second backfill, 3 = surface prepared
													// 4 = broken rock placed (only in combination with broken rock set to true), 5 = paving
   private static int inspectionType = 1;			// type of inspection applied: 1 = ????

   /**   
   * Model parameters: Simulation output settings
   */
   private static int activityMsg = 2;				// indicates what data is collected in main loop: 1 = without pipes, 2 = per pipe, 3 =  per activity per pipe, 4 = ?
   private static int activityMsgConnection = 1;	// indicates what data is collected in connection loop: 1 = overall activity connections, 2 = per connection, 3 = ?
   private static int activityMsgPut = 2;			// indicates what data is collected in main loop: 1 = without pipes, 2 = per pipe, 3 =  per activity per pipe, 4 = ?

   /**
    * Process versions
    */
   
  // static private processVersion pv = 1;  
   
   private enum processVersion
   {
	   BREAK_ALL_UPFRONT,
	   BREAK_PER_SECTION
   };
   
   /**
    * Random number stream used to draw an arrival time for the next truck. THIS IS NOT USED ATM 
    * 						because trucks are modeled as a resource.
    * See init() method for stream parameters. 
    */
   //TODO decide if truck is static resource or needs to be modeled 
   private desmoj.core.dist.ContDistExponential truckArrivalTime;
   
   /**
    * Random number streams used to draw a service time for this activity.
    * Describes the time needed by excavator to fetch and load the truck.
    * See init() method for stream parameters.
    */
   private desmoj.core.dist.ContDistUniform breakingTime;
   private desmoj.core.dist.ContDistUniform stoneRemovalTime;
   private desmoj.core.dist.ContDistUniform excavatingTime;
   private desmoj.core.dist.ContDistUniform shoreTime;
   private desmoj.core.dist.ContDistUniform PipeRemoveTime;
   private desmoj.core.dist.ContDistUniform PutRemoveTime;
   private desmoj.core.dist.ContDistUniform bedPreparationTime;
   private desmoj.core.dist.ContDistUniform pipePlacingTime;
   private desmoj.core.dist.ContDistUniform putPlacingTime;
   private desmoj.core.dist.ContDistUniform removeTrenchTime;
   private desmoj.core.dist.ContDistUniform housingConnectionTime;
   private desmoj.core.dist.ContDistUniform putConnectionTime;
   private desmoj.core.dist.ContDistUniform backfillTime;
   private desmoj.core.dist.ContDistUniform surfacePrepareTime;
   private desmoj.core.dist.ContDistUniform paveTime;
   private desmoj.core.dist.ContDistUniform stonePaveTime;
   
   /**
    * Parttime resource containers for the resources.
    * contains the resources during the simulation. 
    * Provides resources when requested and available and takes back resources
    */
   protected PartTimeRes breakers;
   protected PartTimeRes excavators;
   protected PartTimeRes cranes;
   protected PartTimeRes crews;
   protected PartTimeRes secondcrews;
   protected PartTimeRes rollers;
   protected PartTimeRes trucks;
   protected PartTimeRes pavecrews;
   protected PartTimeRes stonepavecrews;
   
   protected Bin startingCondition;
   
   ArrayList<SectionProcess> sections;
   ArrayList<Put> puts;
   
   
   /**
    * Updates counters after each specific activity has taken place.
    *
    */
   public void prepare()   {	
	   preparecounter ++;
   }
   public void breaking()   {	
	   breakcounter ++;
   }
   public void backfill()   {	
	   backfillcounter ++;
   }
   public void pave()   {	
	   pavecounter ++;
   }
   public void stonepave()   {	
	   stonepavecounter ++;
   }
   public void shore()	{
	   pipecounter ++;
   }
   
   /**
    * Returns counter value indicating the number of activities that happened.
    * @return int rollcounter
    */  
   public static int getPrepareCounter() {
	  return preparecounter;
  }
   public static int getBreakCounter() {
	  return breakcounter;
  }
   public static int getPipeCounter() {
	  return pipecounter;
  }
   public static int getBackfillCounter() {
	  return backfillcounter;
  }
   public static int getPaveCounter() {
	  return pavecounter;
  }
   public static int getStonePaveCounter() {
	  return stonepavecounter;
  }

   
   /**
    * Activity counters
    * Counts activities after a section or put is finished with that activity
    * Allows end of part time resources when the required number of activities has passed
    */   
   private static int breakcounter = 0;
   private static int pipecounter = 0;
   private static int backfillcounter = 0;   
   private static int preparecounter = 0;
   private static int pavecounter = 0;
   private static int stonepavecounter = 0;

}

