package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Paving extends ParentProcess
{	

	/**
	    * @author Simon
	    * Constructor of the paving class 
	    *
	    * Used to create a new paving operation in a section of a sewer line to be replaced
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	
	public Paving(Model owner, ParentProcess parent, String name, boolean showInTrace, int New_pavement, double Total_Area, 
			double Section_Area, double paving_time, double rock_layer, double paving_preparation)
	{
		super(owner, name, showInTrace, New_pavement, New_pavement, New_pavement, New_pavement, paving_time, New_pavement, New_pavement, paving_time, paving_time, paving_time, paving_time, paving_time, name, name, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time, paving_time);
		myModel = (UtilitySimulation)owner;
		newPavement = New_pavement;
		total_area = Total_Area;
		section_area = Section_Area;
		Paving_Time = paving_time;
		Parent = parent; 
		Rock_layer = rock_layer;
		Paving_preparation = paving_preparation;				
	}
	
	/**
	    * Describes this section's life cycle.
	    * 
	    */
	public void lifeCycle() {

		pave(newPavement);
		Parent.activate();
		
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
				   ActivityMessage msg_1 = new ActivityMessage(myModel, Parent, start, "Pave ", myModel.presentTime(), 0) ;
				   sendMessage(msg_1);
				   sendTraceNote("Activity: " + Parent + " Asphalt Paving: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.pavecrews.takeBack(1);
				   myModel.pave();
				   
				   if (UtilitySimulation.getPaveCounter() == myModel.getNUM_SEC()) {
			   			myModel.pavecrews.stopUse();
			   			myModel.stonepavecrews.stopUse();
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
			   ActivityMessage msg_2 = new ActivityMessage(myModel, Parent, start, "Stone Pave ", myModel.presentTime(), 0) ;
			   sendMessage(msg_2);
			   sendTraceNote("Activity: " + Parent + " Stone Paving: " + start.toString() + 
			   " End: " + myModel.presentTime().toString());
			   myModel.stonepavecrews.takeBack(1);
			   myModel.pave();
			   
			   System.out.println(UtilitySimulation.getPaveCounter());
			   if (UtilitySimulation.getPaveCounter() == myModel.getNUM_SEC()) {
		   			myModel.stonepavecrews.stopUse();
		   			myModel.pavecrews.stopUse();
		   			myModel.getExperiment().stop();
		   			System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());
				}  
				break;
    			
    		case 3:
    			// roll/prepare surface - broken rock for asphalt paving
    			if (UtilitySimulation.getBackfillCounter() == myModel.getNUM_SEC()){
    				myModel.trucks.provide(1);
    			   start = myModel.presentTime();
    			   hold (new TimeSpan((myModel.getSurfacePrepareTime() * ((total_area * Rock_layer )/Paving_preparation)), TimeUnit.HOURS));
    			   ActivityMessage msg_3 = new ActivityMessage(myModel, Parent, start, "Roll all", myModel.presentTime(), 0) ;
    			   sendMessage(msg_3);
    			   sendTraceNote("Activity: " + getName() + " Broken rock all: " + start.toString() + 
    					   " End: " + myModel.presentTime().toString());
    			   myModel.trucks.takeBack(1);
    			   myModel.rollers.stopUse();
    			   System.out.println("resource rollers stopped at simulation time " + myModel.presentTime());
    	   		}
    			
    			// asphalt pavement all sections at once
			   	if (UtilitySimulation.getBackfillCounter() == myModel.getNUM_SEC()){
			   		myModel.pavecrews.provide(1);
				   start = myModel.presentTime();
				   hold (new TimeSpan((myModel.getPaveTime() * (total_area/Paving_Time)), TimeUnit.HOURS));
				   ActivityMessage msg_4 = new ActivityMessage(myModel, Parent, start, "Pave all ", myModel.presentTime(), 0) ;
				   sendMessage(msg_4);
				   sendTraceNote("Activity: " + Parent + " Asphalt Paving: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.pavecrews.takeBack(1);
				   myModel.pavecrews.stopUse();
				   myModel.stonepavecrews.stopUse();
				   myModel.getExperiment().stop();
				   System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());
			   	}   				   	
			   	
		   		else{
			   		// After last section paving of entire work starts.
			   		// so all preceding sections have no paving activities
			   		System.out.println(Parent + " No paving activities, all in last " +  myModel.presentTime());
			   	}
			   	break;
    			
    		default:
	    		
    			// no paving activities
		   		myModel.pave();
		   		System.out.println("No paving activities performed " + myModel.presentTime());
		   		System.out.println(Parent + " completed");
		   				   		
		   		if (UtilitySimulation.getPaveCounter() == (myModel.getNUM_SEC())) {
		   			myModel.stonepavecrews.stopUse();
		   			myModel.pavecrews.stopUse();
		   			myModel.getExperiment().stop();
		   			System.out.println("resource pavecrews stopped at simulation time " + myModel.presentTime());
		   		}	
		   		break;  	
	 		}
    	}
	  
	private UtilitySimulation myModel;
	private int newPavement;
	private double section_area;
	private double total_area;
	private double Paving_Time;
	private ParentProcess Parent;
	private double Rock_layer;
	private double Paving_preparation;
}
