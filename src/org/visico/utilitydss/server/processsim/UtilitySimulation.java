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
    * 
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
	   sections = new ArrayList<ParentProcess>();
	   
	   /*if (Scenario.Deventer){
		   // initialize the sections 
		   for (int i=0; i<scenario.getNUM_SEC(); i++)
		   {
			   
			   if(Deventer.put[i]==0)
			   {
						   
				   SectionProcessAll section = new SectionProcessAll(
				   //ParentProcess section = new ParentProcess(
						this, 					//owner
						"Section", 				//name
						true, 					// ?
						Deventer.shore[i],				// number of pipes in section
						Deventer.replacement[i],			// indicates if this section has old sewer to be removed
						Deventer.oldSeparated[i],		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
						Deventer.newSeparated[i],		// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
						connections = Deventer.pipe_connections[i].length,
						Deventer.num_put_connections[i],	// number of connections the put has, only if put
						Deventer.old_pavement[i],		// type of old pavement
						Deventer.new_pavement[i],		// type of new pavement
						Deventer.section_length[i],		// length of section in
						Deventer.pipe_length,			// length of pipes in
						Deventer.section_width[i],		// width of section in
						Deventer.trench_width[i],		// width of Trench 
						Deventer.trench_depth[i],		// depth of Trench in
						Deventer.old_sewer_type,		// type of old sewer
						Deventer.new_sewer_type,		// type of new sewer
						Deventer.old_diameter[i],		// diameter of old sewer 
						Deventer.old_diameter_sep,
						Deventer.new_diameter[i],		// diameter of new sewer
						Deventer.new_diameter_sep,
						Deventer.asphalt_old[i],			// layer thickness of old asphalt in
						Deventer.asphalt_new[i],			// layer thickness of new asphalt in
						Deventer.cables[i],				// weight class of cables in the ground
						Deventer.length_connections[i],	// average length of connections
						Deventer.diameter_connections[i],// average depth of connections
						Deventer.foundation_type[i], 	// type foundation used: 1 = , 2 =
						Deventer.soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						Deventer.soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						Deventer.pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						Deventer.pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						Deventer.rock_layer[i],			// height of pavement preparation rock layer in m
						Deventer.sand_layer[i],			// height of pavement preparation sand layer in m
						Deventer.old_put_area[i],		// area of the old put
						Deventer.new_put_area[i],		// area of the new put
						Deventer.bed_preparation[i],		// height of bed preparation layer
						Deventer.pipe_connections[i]		// indicates if a pipe has a connection.
					);
		
				   section.activate();
				   sections.add(section);
				   SewerExperiment exp = (SewerExperiment)this.getExperiment();
				   exp.getReceiver().createSectionElement(section);
			   }
			   
			   else 
			   {
				   
				   PutProcessAll section = new PutProcessAll(
				   //ParentProcess section = new ParentProcess(
						this, 					//owner
						"Put",					//name
						true, 					// ?
						Deventer.shore[i],				// number of pipes in section
						Deventer.replacement[i],			// indicates if this section has old sewer to be removed
						Deventer.oldSeparated[i],		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
						Deventer.newSeparated[i],		// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
						connections = Deventer.pipe_connections[i].length,
						Deventer.num_put_connections[i],	// number of connections the put has, only if put
						Deventer.old_pavement[i],		// type of old pavement
						Deventer.new_pavement[i],		// type of new pavement
						Deventer.section_length[i],		// length of section in
						Deventer.pipe_length,			// length of pipes in
						Deventer.section_width[i],		// width of section in
						Deventer.trench_width[i],		// width of Trench 
						Deventer.trench_depth[i],		// depth of Trench in
						Deventer.old_sewer_type,		// type of old sewer
						Deventer.new_sewer_type,		// type of new sewer
						Deventer.old_diameter[i],		// diameter of old sewer 
						Deventer.old_diameter_sep,
						Deventer.new_diameter[i],		// diameter of new sewer
						Deventer.new_diameter_sep,
						Deventer.asphalt_old[i],			// layer thickness of old asphalt in
						Deventer.asphalt_new[i],			// layer thickness of new asphalt in
						Deventer.cables[i],				// weight class of cables in the ground
						Deventer.length_connections[i],	// average length of connections
						Deventer.diameter_connections[i],// average depth of connections
						Deventer.foundation_type[i], 	// type foundation used: 1 = , 2 =
						Deventer.soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						Deventer.soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						Deventer.pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						Deventer.pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						Deventer.rock_layer[i],			// height of pavement preparation rock layer in m
						Deventer.sand_layer[i],			// height of pavement preparation sand layer in m
						Deventer.old_put_area[i],		// area of the old put
						Deventer.new_put_area[i],		// area of the new put
						Deventer.bed_preparation[i]		// height of bed preparation layer
					);
				   
				   section.activate();
				   sections.add(section);
				   SewerExperiment exp = (SewerExperiment)this.getExperiment();
				   exp.getReceiver().createSectionElement(section);
				    
			   }
		   }
		   
		   // calculates the total length of the work area for breaking and paving operations over the full length of the project.
		   for (int i=0; i<=scenario.getNUM_SEC()-1; i++)
		   { total_length = total_length + section_length[i];
		   }
	   }
		
	   else {*/
		   // initialize the sections 
		   for (int i=0; i<scenario.getNUM_SEC(); i++)
		   {
			   
			   if(put[i]==0)
			   {
						   
				   SectionProcessAll section = new SectionProcessAll(
				   //ParentProcess section = new ParentProcess(
						this, 					//owner
						"Section", 				//name
						true, 					// ?
						shore[i],				// number of pipes in section
						replacement[i],			// indicates if this section has old sewer to be removed
						oldSeparated[i],		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
						newSeparated[i],		// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
						connections = pipe_connections[i].length,
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
						old_diameter[i],		// diameter of old sewer 
						new_diameter[i],		// diameter of new sewer
						asphalt_old[i],			// layer thickness of old asphalt in
						asphalt_new[i],			// layer thickness of new asphalt in
						cables[i],				// weight class of cables in the ground
						length_connections[i],	// average length of connections
						diameter_connections[i],// average depth of connections
						foundation_type[i], 	// type foundation used: 1 = , 2 =
						soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						rock_layer[i],			// height of pavement preparation rock layer in m
						sand_layer[i],			// height of pavement preparation sand layer in m
						old_put_area[i],		// area of the old put
						new_put_area[i],		// area of the new put
						bed_preparation[i],		// height of bed preparation layer
						pipe_connections[i]		// indicates if a pipe has a connection.
					);
		
				   section.activate();
				   sections.add(section);
				   SewerExperiment exp = (SewerExperiment)this.getExperiment();
				   exp.getReceiver().createSectionElement(section);
			   }
			   
			   else 
			   {
				   
				   PutProcessAll section = new PutProcessAll(
				   //ParentProcess section = new ParentProcess(
						this, 					//owner
						"Put",					//name
						true, 					// ?
						shore[i],				// number of pipes in section
						replacement[i],			// indicates if this section has old sewer to be removed
						oldSeparated[i],		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
						newSeparated[i],		// Indicates if the new section has combined or separated sewer: 0 is combined, 2 is separated
						connections = pipe_connections[i].length,
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
						old_diameter[i],		// diameter of old sewer 
						new_diameter[i],		// diameter of new sewer
						asphalt_old[i],			// layer thickness of old asphalt in
						asphalt_new[i],			// layer thickness of new asphalt in
						cables[i],				// weight class of cables in the ground
						length_connections[i],	// average length of connections
						diameter_connections[i],// average depth of connections
						foundation_type[i], 	// type foundation used: 1 = , 2 =
						soil_removed[i],  		// where is the removed soil placed: 1 = , 2 =
						soil_new[i],  			// where is the new soil placed: 1 = , 2 =
						pipes_old[i],  			// where are the removed pipes placed: 1 = , 2 =
						pipes_new[i],  			// where are the new pipes placed: 1 = , 2 =
						rock_layer[i],			// height of pavement preparation rock layer in m
						sand_layer[i],			// height of pavement preparation sand layer in m
						old_put_area[i],		// area of the old put
						new_put_area[i],		// area of the new put
						bed_preparation[i]		// height of bed preparation layer 
					);
		 
				   section.activate();
				   sections.add(section);
				   SewerExperiment exp = (SewerExperiment)this.getExperiment();
				   exp.getReceiver().createSectionElement(section);
				     
			   }
	
		   }
		   
		   // calculates the total length of the work area for breaking and paving operations over the full length of the project.
		   for (int i=0; i<=scenario.getNUM_SEC()-1; i++)
		   { total_length = total_length + section_length[i];
		   }
	   }
//}
   
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
                  8, 12, true, false);
	      stoneRemovalTime= new ContDistUniform(this, "StoneRemovalTimeStream",
	    		  8, 12, true, false);
	      excavatingTime= new ContDistUniform(this, "ExcavatingTimeStream",
	    		  8, 12, true, false);	
	      closingTime= new ContDistUniform(this, "ClosingTimeStream",
	    		  8, 12, true, false);	
	      shoreTime= new ContDistUniform(this, "ShoringTimeStream",
	    		  8, 12, true, false);
	      pipeRemoveTime = new ContDistUniform(this, "PipeRemoveTimeStream",
	    		  8, 12, true, false);
	      putRemoveTime = new ContDistUniform(this, "putRemoveTimeStream",
	    		  8, 12, true, false);
	      bedPreparationTime= new ContDistUniform(this, "BedPreparationTimeStream",
	    		  8, 12, true, false);
	      foundationTime= new ContDistUniform(this, "FoundationTimeSream",
	    		  8, 12, true, false);
	      pipePlacingTime= new ContDistUniform(this, "PipePlacingTimeStream",
	    		  8, 12, true, false);
	      putPlacingTime= new ContDistUniform(this, "PutPlacingTimeStream",
	    		  8, 12, true, false);
	      removeTrenchTime= new ContDistUniform(this, "TrenchRemoveTimeStream",
	    		  8, 12, true, false);
	      housingConnectionTime= new ContDistUniform(this, "HousingConnectionTimeStream",
	    		  8, 12, true, false);
	      putConnectionTime= new ContDistUniform(this, "PutConnectionTimeStream",
	    		  8, 12, true, false);
	      backfillTime= new ContDistUniform(this, "BackfillTimeStream",
	    		  8, 12, true, false);
	      surfacePrepareTime= new ContDistUniform(this, "SurfacePrepareTimeStream",
	    		  8, 12, true, false);
	      paveTime= new ContDistUniform(this, "PaveTimeStream",
	    		  8, 12, true, false);
	      stonePaveTime= new ContDistUniform(this, "StonePaveTimeStream",
	    		  8, 12, true, false);

	      // Initialize the resources and bins
	      // Parameters:
	      // this                		= belongs to this model
	      // "Resource breakers" 		= the name of the r
	      // scenario.getNUM_CRANE()    = the number of resources available
	      // true                		= show in report?
	      // false               		= show in trace?
	      breakers = new PartTimeRes(this, "Resource breakers", scenario.getNUM_BREAKER(), true, true);
	      excavators = new PartTimeRes(this, "Resource Excavators", scenario.getNUM_EXCAVATOR(), true, true);
	      cranes = new PartTimeRes(this, "Resource cranes", scenario.getNUM_CRANE(), true, true);
	      crews = new PartTimeRes(this, "Resource crews", scenario.getNUM_CREW(), true, true);
	      secondcrews = new PartTimeRes(this, "Resource 2ndcrews", scenario.getNUM_2NDCREW(), true, true);
	      thirdcrews = new PartTimeRes(this, "Resource 3rdcrews", Scenario.getNUM_3RDCREW(), true, true);
	      rollers = new PartTimeRes(this, "Resource rollers", scenario.getNUM_ROLLER(), true, true);
	      trucks = new PartTimeRes(this, "Resource trucks", scenario.getNUM_TRUCK(), true, true);
	      pavecrews = new PartTimeRes(this, "Resource pavecrews", scenario.getNUM_PAVECREWS(), true, true);
	      stonepavecrews = new PartTimeRes(this, "Resource stonepavecrews", scenario.getNUM_STONEPAVECREWS(), true, true);
	      startingCondition = new Bin (this, "starting condition check", scenario.getNUM_STARTINGCONDITION(), true, true);
	      
	      // when there is a second crew a new section can commence work after the main sewer loop of the previous section has been completed
	      // this checks if there is a second crew and sets the section wait prioritization accordingly if this is the case
	      if (Scenario.isSecondCrew() == true){
	   	  sectionWait = 1;				// indicates after which activity the next section starts: 1 = after main loop (only possible if there is a 2nd crew), 
	      }
     
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
   public double getClosingTime() {
	      return closingTime.sample();
	   }
   public double getShoringTime() {
	      return shoreTime.sample();
	   }
   public double getPipeRemoveTime() {
	      return pipeRemoveTime.sample();
		}
   public double getPutRemoveTime() {
	      return putRemoveTime.sample();
		}
   public double getBedPreparationTime() {
	      return bedPreparationTime.sample();
	   }
   public double getFoundationTime() {
	   	  return foundationTime.sample();
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
   public boolean getOldPipeHeavy() {
	    return oldPipeHeavy;
		}
   
   public boolean getNewPipeHeavy() {
	    return newPipeHeavy;
		}
    
   public boolean getSecondCrew() {
    	return Scenario.isSecondCrew();
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
     
   public int getSectionWait() {
	   return sectionWait;
   }
        
   public static int getActivityMsg() {
	   return activityMsg;
   }
   
   public static int getActivityMsgConnection() {
	   return activityMsgConnection;
   }
   
   public static int getActivityMsgPut() {
	   return activityMsgPut;
   }
   
   public double getTotal_length() {
	   return total_length;
   }

   public double getConnectionWait() {
	   return ConnectionWait;
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
  
   
   /** THIS IS FOR TESTING PURPOSES (arraylists should get filled by GUI in final code)
    * 
    * SECTION PROJECT PARAMETERS: Project parameters per section in static arrays 
    * Characteristics of each section/put, stored in arrays
    * examples: housing connections, K&L, puts to be placed with mobile crane
    */
   // Section nr									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; 		// indicates the number of the section for ease of characteristics input
   // project characteristics
   private static int[] put = 						{ 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put.  
   private static int[] shore = 					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																							// 2 means Sheet piling (damwand), 3 means supported walls  
   private static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 }; 		// Indicates if this section has existing old sewer: 0 is no, 1 is yes
   private static int [] oldSeparated = 			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
   private static int [] newSeparated = 			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
   private static double[] num_put_connections = 	{ 2, 2, 2, 2, 2, 2, 2, 3, 2, 2 };  		// number of connections the put has, only if put
   private static int[] old_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																							// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
   private static int[] new_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
   private static double[] cables =		 			{ 0, 0, 0, 0, 0, 5, 5, 0, 0, 0 };  		// weight class of cables in the ground 
   private static double[] foundation_type =		{ 0, 0, 0, 3, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
   private static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
   private static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
   private static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
   private static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

   //Dimensions
   private static double[] section_length = 		{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12, 4 };  		// length of section in
   private static double[] pipe_length = 			{ 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2,4 }; // length of pipes in
   private static double[] section_width = 			{ 5, 5, 5, 5, 5, 7, 7, 7, 7, 7 };  		// width of section in
   private static double[] trench_width = 			{ 4, 4, 4, 4, 4, 5, 5, 5, 5, 5 };  		// width of Trench in  					///////////// bigger with puts?
   private static double[] trench_depth = 			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };  		// depth of Trench in
   private static String[] old_sewer_type = 		{ "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete"}; 		// type of old sewer	Concrete, Gres, Plastic
   private static String[] new_sewer_type = 		{ "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete", "Concrete"}; 		// type of new sewer	Concrete, Gres, Plastic
   private static double[] old_diameter = 			{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// diameter of old sewer 
   private static double[] old_diameter_sep = 		{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// diameter of old sewer 
   private static double[] new_diameter = 			{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// diameter of new sewer
   private static double[] new_diameter_sep = 		{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// diameter of old sewer 
   private static double[] asphalt_old = 			{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
   private static double[] asphalt_new = 			{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
   private static double[] length_connections =		{ 4, 4, 4, 4, 4, 7, 7, 7, 7, 7,};  		// average length of connections
   private static double[] diameter_connections =	{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// average depth of connections
   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
   
   // TODO doe eens fixen
   private static double[] rock_layer = 			{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
   private static double[] sand_layer = 			{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
   private static double[] bed_preparation =		{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.
   
   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
   // make connections independent connections.
   
   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
   private static int[] section10_connections =		{ };							// indicates if a pipe in section 2 has a connection.
   private static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
	   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections};
   

   
   /**  COMMENTED OUT as the arrays above are used for testing purposes. 
    * This is preparation for use with GUI, the arraylists can be filled from there
    * 
    * Array lists are necessary as they are flexible in length allowing as much sections to be defined as necessary for the project.
    * 
    * Model parameters: Project parameters per section in dynamic arraylists
    * Characteristics of each section/put, stored in arrays
    */
   /* 
   ArrayList<Integer> put = new ArrayList<Integer>(0); 					// indicates if section is pipe section or put, 0 is section, 1 is put.
   ArrayList<Integer> shore = new ArrayList<Integer>(0); 				// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																		// 2 means Sheet piling (damwand), 3 means supported walls  // number of pipes, only if pipe section
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
   ArrayList<Integer> foundation_type = new ArrayList<Integer>(0);		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
   ArrayList<Integer> trench_protection = new ArrayList<Integer>(0);	// Type of Trench protection used: 0 = next to trench 1 = in depot, 2 = transported off site
   ArrayList<Integer> soil_removed = new ArrayList<Integer>(0);			// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
   ArrayList<Integer> soil_new = new ArrayList<Integer>(0);				// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
   ArrayList<Integer> pipes_old = new ArrayList<Integer>(0);			// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
   ArrayList<Integer> pipes_new = new ArrayList<Integer>(0);			// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
   ArrayList<Integer> old_put_area = new ArrayList<Integer>(0);			// Area of the old put
   ArrayList<Integer> new_put_area = new ArrayList<Integer>(0);			// Area of the new put
   private static double total_length;									// total length of all sections (for breaking all sections at once)
   private static double rock_layer = 0,3								// height of pavement preparation rock layer in m 
   private static double sand_layer = 0,3								// height of pavement preparation sand layer in m
   ArrayList<Integer> put_connection_type = new ArrayList<Integer>(0);
   */  
   
   
/**
   * Model parameters: SIMULATION SETTINGS
   */
   //TODO this should all move to scenario. having this in to places leads to mistakes.
   private static boolean oldPipeHeavy	= false; 	// indicates if the old pipes are to heavy to be placed by mobile excavator and therefore require mobile crane	
   private static boolean newPipeHeavy	= false; 	// indicates if the new pipes are to heavy to be placed by mobile excavator and therefore require mobile crane	
   													// for puts this is indicated by an array per put as sizes differ.
   private static int prepareSurface = 2;			// indicates if broken rock is placed : 1 = yes, 2 = no
   private static int sectionWait = 1;				// indicates after which activity the next section starts: 1 = after main loop (only possible if there is a 2nd crew), 
   													// 2 = second backfill, 3 = surface prepared, 4 = broken rock placed (only in combination with broken rock set to true), 5 = paving
   private static int inspectionType = 1;			// type of inspection applied: 1 = ????
   private static double ConnectionWait = 500;		// determines how far along the main sewer should be before the 2nd crew starts with connections
   
   /**   
   * Model parameters: Simulation output settings
   */
   private static int activityMsg = 1;				// indicates what data is collected in main loop: 1 = one activity for all pipes, 2 = per pipe, 3 =  per activity per pipe
   private static int activityMsgConnection = 1;	// indicates what data is collected in connection loop: 1 = total time per connection, 2 = per each activity per connection, 10 = don't show data for connections
   private static int activityMsgPut = 1;			// indicates what data is collected in main loop: 1  = per put, 2 =  per activity per put
   /**
    * Process versions
    */
   
  // static private processVersion pv = 1;  NOT USED
   /*
   private enum processVersion
   {
	   BREAK_ALL_UPFRONT,
	   BREAK_PER_SECTION
   };
   */
   
   /**
    * Random number stream used to draw an arrival time for the next truck. THIS IS NOT USED ATM 
    * 						because trucks are modeled as a resource.
    * See init() method for stream parameters. 
    * 
    * Truck has been kept in code for now for possible future users, however it is not used or taken into account
    */
   private desmoj.core.dist.ContDistExponential truckArrivalTime;
   
   /**
    * Random number streams used to draw a service time for this activity.
    * Describes the time needed by excavator to fetch and load the truck.
    * See init() method for stream parameters.
    */
   private desmoj.core.dist.ContDistUniform breakingTime;
   private desmoj.core.dist.ContDistUniform stoneRemovalTime;
   private desmoj.core.dist.ContDistUniform excavatingTime;
   private desmoj.core.dist.ContDistUniform closingTime;
   private desmoj.core.dist.ContDistUniform shoreTime;
   private desmoj.core.dist.ContDistUniform pipeRemoveTime;
   private desmoj.core.dist.ContDistUniform putRemoveTime;
   private desmoj.core.dist.ContDistUniform bedPreparationTime;
   private desmoj.core.dist.ContDistUniform foundationTime;
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
   protected PartTimeRes thirdcrews;
   protected PartTimeRes rollers;
   protected PartTimeRes trucks;
   protected PartTimeRes pavecrews;
   protected PartTimeRes stonepavecrews;
   
   protected Bin startingCondition;
   
   ArrayList<ParentProcess> sections;
   ArrayList<ProcessAll> puts;
   
   
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
   public void pipes_done()	{
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

   // parameter total length of all sections, used for calculations on breaking and paving operations performed on all sections at once.
   private static double total_length;				// total length of all sections, calculated by summing up the length of all sections.
   
   /**
    * Activity counters
    * Counts activities after a section or put is finished with that activity
    * Allows end of part time resources when the required number of activities has passed and those resources are no longer required
    */   
   private static int breakcounter = 0;
   private static int pipecounter = 0;
   private static int backfillcounter = 0;   
   private static int preparecounter = 0;
   private static int pavecounter = 0;
   @SuppressWarnings("unused")
private static int connections;
}

