package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import org.visico.utilitydss.shared.Scenario;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Connections extends ParentProcess
{ 	
	//TODO fix crews/cranes/excavators!!!!!!!!!!!!!!!
	
	
	/**
	    * Constructor of the housing connection class 
	    *
	    * Used to start work on housing connections by second crew while main crew is stil working on main sewer
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	
	public Connections(Model owner, SectionProcessAll parent, String name, boolean showInTrace, double connection_duration_hwa, 
			double second_backfill_height, double pipe_area, double trench_Area, double backfill, double soil_pl_factor)
	{
		super(owner, name, showInTrace, Old_pavement, Old_pavement, Old_pavement, remove_pavement, Old_pavement, Old_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, name, name, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement);
		myModel = (UtilitySimulation)owner;
		Connection_duration_hwa = connection_duration_hwa;
		Second_backfill_height = second_backfill_height;
		Pipe_Area = pipe_area;
		Trench_Area = trench_Area;
		Backfill = backfill;
		Soil_pl_factor = soil_pl_factor;
		Parent = parent;
	}
	
	/**
	    * Describes this section's life cycle.
	    * 
	    */
	
	public void lifeCycle() {
		
		// check if there is a third crew for back filling the ditch after the connections are done.
		if (Scenario.getNUM_3RDCREW() != 0){
			ThirdCrew = 1;
		}
		
		else {
			ThirdCrew = 0;
		}
		
		// perform connection lifecycle
		connections(ThirdCrew);
		
		// if necessary reactivate the main process. This happens if there is one crew after each connection. The main process waits for the connection to be finished.
		// This also happens after the last connection is done and back filled.
		if (myModel.getSecondCrew() == false){
			Parent.activate();
		}
		
		if (Parent.connections_done == Parent.Num_Connections){
			Parent.activate();
		}
		
	}
	
	void connections(int third_crew)
	{	
		TimeInstant startConnection_1 = myModel.presentTime();		// starting time of connection activity corresponding to detail level 1 as selected in UtilitySimulation.java
		TimeInstant startConnection_2 = myModel.presentTime();		// starting time of connection activity corresponding to detail level 2 as selected in UtilitySimulation.java
		
		switch(third_crew)
		{
			case 0:		// one or two crews available
				// 1. install the connections, only if there are connections and if there is a second crew.	   
			   startConnection_1 = myModel.presentTime();
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.provide(1);}
			   else {myModel.crews.provide(1); myModel.excavators.provide(1);}
			   startConnection_2 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getHousingConnectionTime() * Connection_duration_hwa), TimeUnit.HOURS)); //multiply by this.NUM_CONNECTIONS or iterate trough them
			   ActivityMessage msg_10 = new ActivityMessage(myModel, Parent, startConnection_2, "Housing pipe " + this, myModel.presentTime(), 5) ;
			   sendMessage(msg_10);
			   sendTraceNote("Activity: " + Parent + " Install housing connection: " + startConnection_2.toString() + 
					   " End: " + myModel.presentTime().toString());
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.takeBack(1);}
			   else {myModel.crews.takeBack(1); myModel.excavators.takeBack(1);}
		   
	   
			   // 10. (second) backfill + compacting
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.provide(1);}
			   else {myModel.crews.provide(1); myModel.excavators.provide(1);}
			   myModel.trucks.provide(1);
			   startConnection_2 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBackfillTime() * ((Second_backfill_height * Pipe_Area)/Backfill) * Soil_pl_factor), TimeUnit.HOURS));
			   ActivityMessage msg_11 = new ActivityMessage(myModel, Parent, startConnection_2, "Second Backfill " + this, myModel.presentTime(), 5);
			   sendMessage(msg_11);
			   sendTraceNote("Activity: " + Parent + " Backfill: " + startConnection_2.toString() + 
					   " End: " + myModel.presentTime().toString());
			   if(myModel.getSecondCrew()) {
				   	myModel.secondcrews.takeBack(1);}
			   else {myModel.crews.takeBack(1); myModel.excavators.takeBack(1);}
			   myModel.trucks.takeBack(1);
			   
			   ActivityMessage msg_12 = new ActivityMessage(myModel, Parent, startConnection_1, "Housing connection + backfill", myModel.presentTime(), 4);
			   sendMessage(msg_12);
			   
			   Parent.connections_done ++;
			   break;
				
				
			case 1:		// third crew available to fill up trench
				// 1. install the connections, only if there are connections and if there is a second crew.	   
			   startConnection_1 = myModel.presentTime();
			   myModel.secondcrews.provide(1);
			   hold (new TimeSpan((myModel.getHousingConnectionTime() * Connection_duration_hwa), TimeUnit.HOURS)); //multiply by this.NUM_CONNECTIONS or iterate trough them
			   ActivityMessage msg_13 = new ActivityMessage(myModel, Parent, startConnection_1, "Housing pipe " + this, myModel.presentTime(), 0) ;
			   sendMessage(msg_13);
			   sendTraceNote("Activity: " + Parent + " Install housing connection: " + startConnection_1.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.secondcrews.takeBack(1);
			   Parent.connections_done ++; 
			      
			   // 10. (second) backfill + compacting
			   // this happens by third crew when all connections in a section have been completed.
			   System.out.println(this + "connections done" + Parent.connections_done + "total number of connections "+ Parent.Num_Connections); //aslfj;lf;lf does not workyet
			   if (Parent.connections_done == Parent.Num_Connections){
				   //TODO needs to wait till all pipes are done also(there can be pipes stil active that don't have a connection)
				   myModel.thirdcrews.provide(1);
				   myModel.trucks.provide(1);
				   startConnection_1 = myModel.presentTime();
				   hold (new TimeSpan((myModel.getBackfillTime() * ((Second_backfill_height * Trench_Area)/Backfill) * Soil_pl_factor), TimeUnit.HOURS));
				   ActivityMessage msg_14 = new ActivityMessage(myModel, Parent, startConnection_1, "Second Backfill total trench ", myModel.presentTime(), 0);
				   sendMessage(msg_14);
				   sendTraceNote("Activity: " + Parent + " Backfill total trench: " + startConnection_1.toString() + 
						   " End: " + myModel.presentTime().toString());
				   myModel.thirdcrews.takeBack(1);
				   myModel.trucks.takeBack(1);
			   }
			   break;
			
			default:
			    // no connections
			   	System.out.println("Error: no connection activities performed " + myModel.presentTime());	   
			   	break;
				}
				
	}
		
	
   	   private UtilitySimulation myModel;			// model owner
	   private double Connection_duration_hwa;		// duration of one connection
	   private double Second_backfill_height;		// height of the second backfill
	   private double Pipe_Area;					// total area around one pipe
	   private double Trench_Area;					// total area of the trench
	   private double Backfill;						// backfill production in m^3 per hour
	   private double Soil_pl_factor;				// factor for where the soil is placed before backfilling ( in depot, off site or besides the ditch)
	   private SectionProcessAll Parent;				// parent of this class
	   private int ThirdCrew;
	   private static final int Old_pavement = 0;	
	   private static final double remove_pavement = 0;

}
