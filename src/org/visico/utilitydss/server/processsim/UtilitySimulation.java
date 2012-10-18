package org.visico.utilitydss.server.processsim;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;
import desmoj.core.simulator.*;
import desmoj.core.advancedModellingFeatures.Bin;
import desmoj.core.dist.*;


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
           boolean showInTrace, ArrayList<Integer> resources) {
	   
	   		
			super(owner, modelName, showInReport, showInTrace);
			
			NUM_BREAKER = resources.get(0).intValue();
			NUM_EXCAVATOR = resources.get(1).intValue();
			NUM_CRANE = resources.get(2).intValue();
			NUM_CREW = resources.get(3).intValue();
			NUM_ROLLER = resources.get(4).intValue();
			NUM_TRUCK = resources.get(5).intValue();
			/* 
			NUM_PAVECREWS = resources.get(6).intValue();
			NUM_STONEPAVECREWS = resources.get(7).intValue();
			NUM_2NDCREW = resources.get(8).intValue();
			
			NUM_SEC = resources.get(9).intValue();
			NUM_PUT = resources.get(10).intValue();
			
			Replacement = resources.get(11).intValue();
			OldPavement = resources.get(12).intValue();
			NewPavement = resources.get(13).intValue();
			Shore = resources.get(14).intValue();
			secondCrew = resources.get(15).intValue();
			TODO update to all current resources and other input functions.
	*/
	}

   /** test giving sections a number of pipes to iterate trough
    * TODO make an array with number for each section
    */

   public int getNUM_SEC() {
	return NUM_SEC;
}

public void setNUM_SEC(int nUM_SEC) {
	NUM_SEC = nUM_SEC;
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
	   sections = new ArrayList<Section>();
	// initialize the sections 
	   for (int i=0; i<NUM_SEC; i++)
	   {
		   Section section = new Section(
				   this, 			//owner
				   "Section", 		//name
				   true, 			// ?
				   a);				// number of pipes in section
		   
		   section.activate();
		   sections.add(section);
		   SewerExperiment exp = (SewerExperiment)this.getExperiment();
		   exp.getReceiver().createSectionElement(section);
	   }
	   
	   puts = new ArrayList<Put>();
	// initialize the puts 
	   for (int i=0; i<NUM_PUT; i++)
	   {
		   Put put = new Put(this, "put" , true);
		   put.activate();
		   puts.add(put);
		   //SewerExperiment exp = (SewerExperiment)this.getExperiment();
		   //exp.getReceiver().createPutElement(put);
		   //TODO needs changes to Reciever.java and maybe ActivityMessage.java (those focus on section now which should be general to allow section, put, breaking and other processes)
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
	      // 3.0                 = minimum time in minutes to deliver a container
	      // 7.0                 = maximum time in minutes to deliver a container
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
	      bedPreparationTime= new ContDistUniform(this, "BedPreparationTimeStream",
                  10.0, 30.0, true, false);
	      pipePlacingTime= new ContDistUniform(this, "PipePlacingTimeStream",
                  10.0, 30.0, true, false);
	      handbackfillTime= new ContDistUniform(this, "HandBackfillTimeStream",
                  10.0, 30.0, true, false);
	      removeTrenchTime= new ContDistUniform(this, "TrenchRemoverTimeStream",
                  10.0, 30.0, true, false);
	      housingConnectionTime= new ContDistUniform(this, "HousingConnectionTimeStream",
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
	      breakers = new PartTimeRes(this, "Resource breakers", NUM_BREAKER, true, true);
	      excavators = new PartTimeRes(this, "Resource Excavators", NUM_EXCAVATOR, true, true);
	      cranes = new PartTimeRes(this, "Resource cranes", NUM_CRANE, true, true);
	      crews = new PartTimeRes(this, "Resource crews", NUM_CREW, true, true);
	      secondcrews = new PartTimeRes(this, "Resource 2ndcrews", NUM_2NDCREW, true, true);
	      rollers = new PartTimeRes(this, "Resource rollers", NUM_ROLLER, true, true);
	      trucks = new PartTimeRes(this, "Resource trucks", NUM_TRUCK, true, true);
	      pavecrews = new PartTimeRes(this, "Resource pavecrews", NUM_PAVECREWS, true, true);
	      stonepavecrews = new PartTimeRes(this, "Resource stonepavecrews", NUM_STONEPAVECREWS, true, true);
	      startingCondition = new Bin (this, "starting condition check", NUM_STARTINGCONDITION, true, true);
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
   public double getBedPreparationTime() {
	      return bedPreparationTime.sample();
	   }
   public double getPipePlacingTime() {
	      return pipePlacingTime.sample();
	   }
   public double getHandBackfillTime() {
	      return handbackfillTime.sample();
	   }
   public double getRemoveTrenchTime() {
	      return removeTrenchTime.sample();
   		}
   public double getHousingConnectionTime() {
	      return housingConnectionTime.sample();
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
   
   public boolean getSecondCrew() {
    	return secondCrew;
		}
   
   public int getPrepareSurface() {
	    return prepareSurface;
		}
   
      public int getNewPavement() {
	      return newPavement;
	   }
   
   public int getActivitymsg() {
	    return activityMsg;
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
   public void handbackfill()   {	
	   handbackfillcounter ++;
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
   public static int getShoreCounter() {
	  return shorecounter;
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
   public static int getHandBackfillCounter() {
	  return handbackfillcounter;
  }

/**
    * Model parameters: Project parameters (the number of sections, puts and resources, etc)
    */
   public static int NUM_SEC = 7;					// number of sections
   public static int NUM_PUT = 0;					// number of puts
   private static int NUM_BREAKER = 1;				// number of breakers
   private static int NUM_EXCAVATOR = 2;			// number of excavators
   private static int NUM_CRANE = 0;				// number of truck-mounted cranes
   private static int NUM_CREW = 1;					// number of crews
   private static int NUM_2NDCREW = 1;				// number of 2ndcrews //TODO think about making a second process for when 2nd crews are used, just like for breaking entire road.
   private static int NUM_ROLLER = 2;				// number of rollers
   private static int NUM_TRUCK = 1;				// number of trucks
   private static int NUM_PAVECREWS = 1;			// number of pave crews
   private static int NUM_STONEPAVECREWS = 1;		// number of stone pave crews
   private static int NUM_STARTINGCONDITION = 1;	// forces sections to wait for predecessors to be done with specified activity
   
/**
   * Model parameters: Simulation settings
   */
   private static int OldPavement = 3;				// indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
													// 3 means asphalt; break all sections at start, other gives error
   private static int Shore = 0;					// indicates if project requires shoring, 0 means no shoring, 1 means shoring //TODO needs expansion with different types of shoring
   private static boolean Replacement = false;		// indicates if the project is a replacement project
   private static boolean pipeHeavy	= false; 		// indicates if the pipes are to heavy to be placed by mobile excavator and therefore require mobile crane	
   													// for puts this is indicated by an array per put as sizes differ.
   private static boolean secondCrew = false;		// indicates if there is a 2nd crew present to perform housing connections
   private static int prepareSurface = 2;			// indicates if broken rock is placed per section or for all sections at once: 1 = per section, 2 = all sections
   private static int newPavement =3;				// indicates new pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
													// 3 means asphalt; pave all sections at start, other gives error
   private static int activityMsg = 2;				// indicates what data is collected: 1 = per pipe, 2 is per activity per pipe, 3 = ?
   													// TODO invoeren in section.

   /** test giving sections a number of pipes to iterate trough
    * TODO make an array with number for each section
    * other arrays can be made for other characteristics
    * 
    * examples: housing connections, K&L, puts to be placed with mobile crane
    */
   private static int a = 10; 						// number of pipes
   
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
   private desmoj.core.dist.ContDistUniform bedPreparationTime;
   private desmoj.core.dist.ContDistUniform pipePlacingTime;
   private desmoj.core.dist.ContDistUniform handbackfillTime;
   private desmoj.core.dist.ContDistUniform removeTrenchTime;
   private desmoj.core.dist.ContDistUniform housingConnectionTime;
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
   
   ArrayList<Section> sections;
   ArrayList<Put> puts;
   
   /**
    * Activity counters
    * Counts activities after a section or put is finished with that activity
    * Allows end of part time resources when the required number of activities has passed
    */   
   private static int breakcounter = 0;
   private static int handbackfillcounter = 0;
   private static int shorecounter = 0;
   private static int backfillcounter = 0;   
   private static int preparecounter = 0;
   private static int pavecounter = 0;
   private static int stonepavecounter = 0;

}

