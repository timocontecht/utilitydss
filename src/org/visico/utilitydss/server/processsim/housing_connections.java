package org.visico.utilitydss.server.processsim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class housing_connections extends ParentProcess
{ 
	//TODO prevent overtaking of main sewer
	

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
	
	public housing_connections(Model owner, ParentProcess parent, String name, boolean showInTrace, int num_connections, double connection_duration_hwa, 
			double second_backfill_height, double trench_Area, double backfill, double soil_pl_factor)
	{
		super(owner, name, showInTrace, Old_pavement, Old_pavement, Old_pavement, remove_pavement, Old_pavement, Old_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, name, name, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement, remove_pavement);
		myModel = (UtilitySimulation)owner;
		Num_connections = num_connections;
		Connection_duration_hwa = connection_duration_hwa;
		Second_backfill_height = second_backfill_height;
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
		
		TimeInstant startConnection_1 = myModel.presentTime();		// starting time of connection activity corresponding to detail level 1 as selected in UtilitySimulation.java
		TimeInstant startConnection_2 = myModel.presentTime();		// starting time of connection activity corresponding to detail level 2 as selected in UtilitySimulation.java
		TimeInstant startConnection_3 = myModel.presentTime();		// starting time of connection activity corresponding to detail level 3 as selected in UtilitySimulation.java

		for (int i=1; i<=Num_connections; i++)
		{
			// 1. install the connections, only if there are connections and if there is a second crew.	   
		   startConnection_1 = myModel.presentTime();
		   for (int j=1; j<=(Num_connections); j++) {
			   myModel.secondcrews.provide(1);
			   startConnection_2 = myModel.presentTime();
			   startConnection_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getHousingConnectionTime() * Connection_duration_hwa), TimeUnit.HOURS)); //multiply by this.NUM_CONNECTIONS or iterate trough them
			   ActivityMessage msg_10 = new ActivityMessage(myModel, Parent, startConnection_3, "Housing pipe " + j, myModel.presentTime(), 6) ;
			   sendMessage(msg_10);
			   sendTraceNote("Activity: " + Parent + " Install housing connection: " + startConnection_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.secondcrews.takeBack(1);
		   
	   
			   // 10. (second) backfill + compacting
			   myModel.secondcrews.provide(1);
			   myModel.trucks.provide(1);
			   startConnection_3 = myModel.presentTime();
			   hold (new TimeSpan((myModel.getBackfillTime() * ((Second_backfill_height * Trench_Area)/Backfill) * Soil_pl_factor), TimeUnit.HOURS));
			   ActivityMessage msg_11 = new ActivityMessage(myModel, Parent, startConnection_3, "Second Backfill " + j, myModel.presentTime(), 6);
			   sendMessage(msg_11);
			   sendTraceNote("Activity: " + Parent + " Backfill: " + startConnection_3.toString() + 
					   " End: " + myModel.presentTime().toString());
			   myModel.secondcrews.takeBack(1);
			   myModel.trucks.takeBack(1);
			   
			   ActivityMessage msg_12 = new ActivityMessage(myModel, this, startConnection_2, "Housing connection " + j, myModel.presentTime(), 5);
			   sendMessage(msg_12);
			   
			   Parent.connections_done ++;
		   }
		   ActivityMessage msg_10 = new ActivityMessage(myModel, this, startConnection_1, "All Housing connections ", myModel.presentTime(), 4) ;
		   sendMessage(msg_10);
				
		}
		Parent.activate();
	}
	
   	   private UtilitySimulation myModel;
	   private int Num_connections;
	   private double Connection_duration_hwa;
	   private double Second_backfill_height;
	   private double Trench_Area;
	   private double Backfill;
	   private double Soil_pl_factor;
	   private ParentProcess Parent;
	   private static final int Old_pavement = 0;
	   private static final double remove_pavement = 0;
}
