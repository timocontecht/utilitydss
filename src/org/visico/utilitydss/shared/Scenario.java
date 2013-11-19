package org.visico.utilitydss.shared;

import java.io.Serializable;

/**
 * @author timo
 *
 */
public class Scenario implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1900805806525469149L;

	// indicator for if project run is Deventer or test
	public static final boolean Deventer = true;

	/**
	    * Model parameters: Project parameters (the number of sections, puts and resources, etc)
	    */
	   // private static int NUM_SEC = 9;			// number of sections --> this is calculated automatically now, based on array length of parameter "put"
	   // Max nr. of sections is restricted by array length of parameters in UtilitySimulation.java.
	   // this should be replaced by variable length ArrayLists filled by data from UI
	   private int NUM_BREAKER = 1;				// number of breakers
	   private int NUM_EXCAVATOR = 1;			// number of excavators
	   private int NUM_CRANE = 0;				// number of truck-mounted cranes
	   private int NUM_CREW = 2;				// number of crews
	   private int NUM_2NDCREW = 1;				// number of 2ndcrews --> if != 0 --> secondCrew should be set to true 
	   private static int NUM_2NDBACKFILLCREW = 0;		// number of crews available for second backfill --> usually an extra spare crane 
	   private int NUM_ROLLER = 1;				// number of rollers
	   private int NUM_TRUCK = 2;				// number of trucks
	   private int NUM_PAVECREWS = 1;			// number of pave crews
	   private int NUM_STONEPAVECREWS = 1;		// number of stone pave crews
	   private int NUM_STARTINGCONDITION = 1;	// forces sections to wait for predecessors to be done with specified activity
	   
	   // this could be removed by linking to NUM_2NDCREW directly
	   private static boolean secondCrew = false;		// indicates if there is a 2nd crew present to perform housing connections
	   private static int sectionWait = 1;				// indicates after which activity the next section starts: 1 = after main loop (only possible if there is a 2nd crew), 
														// 2 = second backfill, 3 = surface prepared, 4 = paving
	   private static double ConnectionWait = 4600;		// determines how far along the main sewer should be before the 2nd crew starts with connections in timeunits of simulation
		/**   
		* Model parameters: Simulation output settings
		*/
		private static int activityMsg = 2;				// indicates what data is collected in main loop: 1 = one activity for all pipes, 2 = per pipe, 3 =  per activity per pipe
		private static int activityMsgConnection = 1;	// indicates what data is collected in connection loop: 1 = total time per connection, 2 = per each activity per connection, 10 = don't show data for connections
		private static int activityMsgPut = 2;			// indicates what data is collected in main loop: 1  = per put, 2 =  per activity per put


	public int getNUM_BREAKER() {
		return NUM_BREAKER;
	}

	public void setNUM_BREAKER(int nUM_BREAKER) {
		NUM_BREAKER = nUM_BREAKER;
	}

	public int getNUM_EXCAVATOR() {
		return NUM_EXCAVATOR;
	}

	public void setNUM_EXCAVATOR(int nUM_EXCAVATOR) {
		NUM_EXCAVATOR = nUM_EXCAVATOR;
	}

	public int getNUM_CRANE() {
		return NUM_CRANE;
	}

	public void setNUM_CRANE(int nUM_CRANE) {
		NUM_CRANE = nUM_CRANE;
	}

	public int getNUM_CREW() {
		return NUM_CREW;
	}

	public void setNUM_CREW(int nUM_CREW) {
		NUM_CREW = nUM_CREW;
	}

	public int getNUM_2NDCREW() {
		return NUM_2NDCREW;
	}

	public void setNUM_2NDCREW(int nUM_2NDCREW) {
		NUM_2NDCREW = nUM_2NDCREW;
	}
	
	public static int getNUM_3RDCREW() {
		return NUM_2NDBACKFILLCREW;
	}

	public void setNUM_3RDCREW(int nUM_3RDCREW) {
		NUM_2NDCREW = nUM_3RDCREW;
	}
	
	public int getNUM_ROLLER() {
		return NUM_ROLLER;
	}

	public void setNUM_ROLLER(int nUM_ROLLER) {
		NUM_ROLLER = nUM_ROLLER;
	}

	public int getNUM_TRUCK() {
		return NUM_TRUCK;
	}

	public void setNUM_TRUCK(int nUM_TRUCK) {
		NUM_TRUCK = nUM_TRUCK;
	}

	public int getNUM_PAVECREWS() {
		return NUM_PAVECREWS;
	}

	public void setNUM_PAVECREWS(int nUM_PAVECREWS) {
		NUM_PAVECREWS = nUM_PAVECREWS;
	}

	public int getNUM_STONEPAVECREWS() {
		return NUM_STONEPAVECREWS;
	}

	public void setNUM_STONEPAVECREWS(int nUM_STONEPAVECREWS) {
		NUM_STONEPAVECREWS = nUM_STONEPAVECREWS;
	}

	public int getNUM_STARTINGCONDITION() {
		return NUM_STARTINGCONDITION;
	}

	public void setNUM_STARTINGCONDITION(int nUM_STARTINGCONDITION) {
		NUM_STARTINGCONDITION = nUM_STARTINGCONDITION;
	}

	public static boolean isSecondCrew() {
		return secondCrew;
	}

	public static void setSecondCrew(boolean secondCrew) {
		Scenario.secondCrew = secondCrew;
	}

	public static int getSectionWait() {
		return sectionWait;
	}

	public static void setSectionWait(int sectionWait) {
		Scenario.sectionWait = sectionWait;
	}

	public static double getConnectionWait() {
		return ConnectionWait;
	}

	public static void setConnectionWait(double connectionWait) {
		ConnectionWait = connectionWait;
	}

	public static int getActivityMsg() {
		return activityMsg;
	}

	public static void setActivityMsg(int activityMsg) {
		Scenario.activityMsg = activityMsg;
	}

	public static int getActivityMsgConnection() {
		return activityMsgConnection;
	}

	public static void setActivityMsgConnection(int activityMsgConnection) {
		Scenario.activityMsgConnection = activityMsgConnection;
	}

	public static int getActivityMsgPut() {
		return activityMsgPut;
	}

	public static void setActivityMsgPut(int activityMsgPut) {
		Scenario.activityMsgPut = activityMsgPut;
	}
	   
}
