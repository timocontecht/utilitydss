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

	/**
	    * Model parameters: Project parameters (the number of sections, puts and resources, etc)
	    */
	   private int NUM_SEC = 3;					// number of sections
	   // MAX 4 FOR NOW as the arrays in UtilitySimulation.java arent longer.
	   private int NUM_PUT = 0;					// number of puts
	   private int NUM_BREAKER = 1;				// number of breakers
	   private int NUM_EXCAVATOR = 2;			// number of excavators
	   private int NUM_CRANE = 0;				// number of truck-mounted cranes
	   private int NUM_CREW = 1;				// number of crews
	   private int NUM_2NDCREW = 1;				// number of 2ndcrews
	   private int NUM_ROLLER = 2;				// number of rollers
	   private int NUM_TRUCK = 1;				// number of trucks
	   private int NUM_PAVECREWS = 1;			// number of pave crews
	   private int NUM_STONEPAVECREWS = 1;		// number of stone pave crews
	   private int NUM_STARTINGCONDITION = 1;	// forces sections to wait for predecessors to be done with specified activity
	  
	   private static boolean secondCrew = false;		// indicates if there is a 2nd crew present to perform housing connections

	public int getNUM_SEC() {
		return NUM_SEC;
	}

	public void setNUM_SEC(int nUM_SEC) {
		NUM_SEC = nUM_SEC;
	}

	public int getNUM_PUT() {
		return NUM_PUT;
	}

	public void setNUM_PUT(int nUM_PUT) {
		NUM_PUT = nUM_PUT;
	}

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

	   
	
	   
}
