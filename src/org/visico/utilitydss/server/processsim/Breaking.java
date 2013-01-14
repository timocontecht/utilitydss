package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Breaking extends ParentProcess
{
	
	/**
	    * Constructor of the breaking class 
	    *
	    * Used to create a new breaking operation in a section of a sewer line to be replaced
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	public Breaking(Model owner, ParentProcess parent, String name, boolean showInTrace, int Old_pavement, double Total_Area, 
			double Section_Area, double remove_pavement)
	{
		super(owner, name, showInTrace, Old_pavement, Old_pavement, Old_pavement, remove_pavement, Old_pavement, Old_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, name, name, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement);
		myModel = (UtilitySimulation)owner;
		oldPavement = Old_pavement;
		total_area = Total_Area;
		section_area = Section_Area;
		Remove_Pavement = remove_pavement;
		Parent = parent;
	}
	
	/**
	    * Describes this section's life cycle.
	    * 
	    */
	public void lifeCycle() {

		removePavement(oldPavement);
		
	}
	
   void removePavement(int oldPavement)
   {
	   TimeInstant start = myModel.presentTime();
	   
	   switch(oldPavement){
    		case 1:
    			// asphalt pavement removal per section
    			// Break asphalt per section
			   myModel.breakers.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (section_area/Remove_Pavement)), TimeUnit.HOURS)); 
			   ActivityMessage msg_1 = new ActivityMessage(myModel, Parent, start, "Break Section ", myModel.presentTime(), 0) ;
			   sendMessage(msg_1);
			   sendTraceNote("Activity: " + Parent + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.breakers.takeBack(1);
			   myModel.breaking();
			   if (UtilitySimulation.getBreakCounter() == (myModel.getScenario().getNUM_SEC() + myModel.getScenario().getNUM_PUT())){
				   myModel.breakers.stopUse();
				   System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   }
			   Parent.activate();
			   break;
    			
    		case 2:
    			// brick pavement removal per section
			   myModel.crews.provide(1);
			   start = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBreakingTime() * (section_area/Remove_Pavement)), TimeUnit.HOURS)); 
			   ActivityMessage msg_2 = new ActivityMessage(myModel, Parent, start, "Remove Stones Section ", myModel.presentTime(), 0);
			   sendMessage(msg_2);
			   myModel.crews.takeBack(1);
			   sendTraceNote("Activity: " + Parent + " Breaking Start: " + start.toString() + 
					   " End: " + myModel.presentTime().toString());
			   System.out.println("stones removed at simulation time " + myModel.presentTime());
			   Parent.activate();
			   break;
    			
    		case 3:
    			// Breaking ashpalt pavement all sections at once.
			   	if (Parent.getIdentNumber() == (1)){
			   		myModel.breakers.provide(1);
			   		start = myModel.presentTime();
			   		hold (new TimeSpan((myModel.getBreakingTime() * (total_area/Remove_Pavement)), TimeUnit.HOURS));
			   		ActivityMessage msg_3 = new ActivityMessage(myModel, Parent, start, "Break all ", myModel.presentTime(), 0) ;
			   		sendMessage(msg_3);
			   		sendTraceNote("Activity: " + Parent + " Breaking Start: " + start.toString() + 
						   " End: " + myModel.presentTime().toString());
			   		myModel.breakers.takeBack(1);
			   		myModel.breakers.stopUse();
			   		System.out.println("resource breakers stopped at simulation time " + myModel.presentTime());
			   	}
			   	
			   	else{
			   		// Breaking happens once for all sections
			   		// so all following sections have no breaking activities
			   		System.out.println(Parent + " No breaking activities, all in first " + myModel.presentTime());
			   	}
			   Parent.activate();   
			   break;
    			
    		default:
		    // no pavement removal
		   		System.out.println("no breaking activities performed " + myModel.presentTime());	   
		   		Parent.activate();
		   		break;
	   }}
	  
	   private UtilitySimulation myModel;
	   private int oldPavement;
	   private double section_area;
	   private double total_area;
	   private double Remove_Pavement;
	   private ParentProcess Parent;
}
