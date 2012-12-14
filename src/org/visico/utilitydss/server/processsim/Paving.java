package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Paving extends SimProcess
{
	private UtilitySimulation myModel;
	
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
	public Paving(Model owner, String name, boolean showInTrace, int New_pavement, double Total_Area, double Section_Area, 
			double paving_time)
	{
		super(owner, name, showInTrace);
		myModel = (UtilitySimulation)owner;
		newPavement = New_pavement;
		total_area = Total_Area;
		section_area = Section_Area;
		Paving_Time = paving_time;
	}
	
	/**
	    * Describes this section's life cycle.
	    *process for breaking all sections upfront, only active if 
	    * experiments process version is set to BREAK_ALL_UPFRONT
	    */
	public void lifeCycle() {

		pave(newPavement);
		
	}
	
	void pave(int newPavement)
	   {
		TimeInstant start = myModel.presentTime();   
	   
	    	switch (newPavement){
	    		case 1:
	    			// paving all per section
	    			   // asphalt paving
	    				   myModel.pavecrews.provide(1);
	    				   start = myModel.presentTime();
	    				   hold (new TimeSpan((myModel.getPaveTime() * (section_area/Paving_Time)), TimeUnit.HOURS));
	    				   ActivityMessage msg_1 = new ActivityMessage(myModel, this, start, "Pave ", myModel.presentTime(), 0, true) ;
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
	    			break;

	    		case 2:
	    			// brick pavement per section
	    			// brick paving
					   myModel.stonepavecrews.provide(1);
					   start = myModel.presentTime();
					   hold (new TimeSpan((myModel.getPaveTime() * (section_area/Paving_Time)), TimeUnit.HOURS));
					   ActivityMessage msg_2 = new ActivityMessage(myModel, this, start, "Stone Pave ", myModel.presentTime(), 0, true) ;
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
					   hold (new TimeSpan((myModel.getPaveTime() * (total_area/Paving_Time)), TimeUnit.HOURS));
					   ActivityMessage msg_3 = new ActivityMessage(myModel, this, start, "Pave all ", myModel.presentTime(), 0, true) ;
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
	    			// no paving activities
			    // no paving activities
				   	if (UtilitySimulation.getPrepareCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())){
				   		myModel.getExperiment().stop();
				   		System.out.println("no paving activities performed " + myModel.presentTime());
				   	}	   
	    			break;
	   }}
	  
	   private int newPavement;
	   private double section_area;
	   private double total_area;
	   private double Paving_Time;
}
