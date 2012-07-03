package org.visico.utilitydss.server.processsim;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;
import desmoj.core.simulator.*;
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
	   exp.tracePeriod(new TimeInstant(0), new TimeInstant(6000, TimeUnit.HOURS));

	                                              // set the period of the trace
	   exp.debugPeriod(new TimeInstant(0), new TimeInstant(6000, TimeUnit.HOURS));   // and debug output
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
		   Section section = new Section(this, "Section" , true);
		   section.activate();
		   sections.add(section);
		   SewerExperiment exp = (SewerExperiment)this.getExperiment();
		   exp.getReceiver().createSectionElement(section);
	   }
	   puts = new ArrayList<Put>();
	// initialize the sections 
	   for (int i=0; i<NUM_PUT; i++)
	   {
		   Put put = new Put(this, "put" , true);
		   put.activate();
		   puts.add(put);
	   }
   }
   
   /**
    * Initialises static model components like distributions and queues.
    */
   public void init() 
   { 
	      // initialise the TimeStreams
	      // Parameters:
	      // this                = belongs to this model
	      // "ServiceTimeStream" = the name of the stream
	      // 3.0                 = minimum time in minutes to deliver a container
	      // 7.0                 = maximum time in minutes to deliver a container
	      // true                = show in report?
	      // false               = show in trace?
	      breakingTime= new ContDistUniform(this, "BreakingTimeStream",
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
	      rollers = new PartTimeRes(this, "Resource rollers", NUM_ROLLER, true, true);
	      trucks = new PartTimeRes(this, "Resource trucks", NUM_TRUCK, true, true);
	      pavecrews = new PartTimeRes(this, "Resource pavecrews", NUM_PAVECREWS, true, true);
	      stonepavecrews = new PartTimeRes(this, "Resource stonepavecrews", NUM_STONEPAVECREWS, true, true);

	      
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
    * Returns if project is replacement project or not.
    * if true implements pipe removal activity in section lifecycle 
    *
    * @return Boolean Replacement
    */  
   public boolean getReplacement() {
	      return Replacement;
	   }
   /**
    * Returns if project is pavement project or stone road surface.
    * if true implements pavement in section lifecycle, if false implements stone road surface pavement
    *
    * @return Boolean Replacement
    */  
   public int getPavement() {
	      return Pavement;
	   }
   /**
    * Returns a sample of the random stream used to determine
    * the next truck arrival time. THIS IS NOT USED ATM because trucks are modeled as a resource.
    *
    * @return double a truckArrivalTime sample
    */
   public double getTruckArrivalTime() {
      return truckArrivalTime.sample();
   }

   public void report()
   {
	   
   }
   
   /**
    * Updates counters after each specific activity has taken place.
    *
    */
   public void roll()   {	
	   rollcounter ++;
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
   /**
    * Returns number of activities that happened.
    * @return int rollcounter
    */  
   public static int getRollCounter() {
	  return rollcounter;
  }
   public static int getBreakCounter() {
	  return breakcounter;
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
    * Model parameters: the number of sections and resources
    */
   public static int NUM_SEC = 7;
   public static int NUM_PUT = 7;
   protected static int NUM_BREAKER = 1;
   protected static int NUM_EXCAVATOR = 1;
   protected static int NUM_CRANE = 1;
   protected static int NUM_CREW = 1;
   protected static int NUM_ROLLER = 1;
   protected static int NUM_TRUCK = 1;
   protected static int NUM_PAVECREWS = 1;
   protected static int NUM_STONEPAVECREWS = 1;
   protected static boolean Replacement = false;
   protected static int Pavement = 1;
   /**
    * Random number stream used to draw an arrival time for the next truck. THIS IS NOT USED ATM 
    * 						because trucks are modeled as a resource.
    * See init() method for stream parameters. 
    */
   //TODO decide if truck is static resource or needs to be modeled 
   private desmoj.core.dist.ContDistExponential truckArrivalTime;
   
   /**
    * Random number stream used to draw a service time for this activity.
    * Describes the time needed by excavator to fetch and load the truck.
    * See init() method for stream parameters.
    */
   private desmoj.core.dist.ContDistUniform breakingTime;
   private desmoj.core.dist.ContDistUniform excavatingTime;
   private desmoj.core.dist.ContDistUniform shoreTime;
   private desmoj.core.dist.ContDistUniform PipeRemoveTime;
   private desmoj.core.dist.ContDistUniform bedPreparationTime;
   private desmoj.core.dist.ContDistUniform pipePlacingTime;
   private desmoj.core.dist.ContDistUniform handbackfillTime;
   private desmoj.core.dist.ContDistUniform removeTrenchTime;
   private desmoj.core.dist.ContDistUniform backfillTime;
   private desmoj.core.dist.ContDistUniform surfacePrepareTime;
   private desmoj.core.dist.ContDistUniform paveTime;
   private desmoj.core.dist.ContDistUniform stonePaveTime;
   
  
   protected PartTimeRes breakers;
   protected PartTimeRes excavators;
   protected PartTimeRes cranes;
   protected PartTimeRes crews;
   protected PartTimeRes rollers;
   protected PartTimeRes trucks;
   protected PartTimeRes pavecrews;
   protected PartTimeRes stonepavecrews;
   
   ArrayList<Section> sections;
   ArrayList<Put> puts;
   
   private static int rollcounter = 0;
   private static int breakcounter = 0;
   private static int backfillcounter = 0;   
   private static int pavecounter = 0;
   private static int stonepavecounter = 0;
}

