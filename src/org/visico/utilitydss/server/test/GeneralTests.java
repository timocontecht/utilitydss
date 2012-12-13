package org.visico.utilitydss.server.test;

import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.visico.utilitydss.server.processsim.ActivityMessage;
import org.visico.utilitydss.server.processsim.SewerExperiment;
import org.visico.utilitydss.server.processsim.UtilitySimulation;

import desmoj.core.simulator.TimeInstant;


public class GeneralTests 
{
	@Test
	public void distributionTest() throws ParserConfigurationException
	{
		UtilitySimulation model = new UtilitySimulation(null,
                "Test", true, true);
		SewerExperiment exp = new SewerExperiment("Test example");
		model.connectToExperiment(exp);
		
		double dist1 = model.getBreakingTime();
		double dist2 = model.getBreakingTime();
		System.out.println(dist1);
		System.out.println(dist2);	
		UtilitySimulation myModel = (UtilitySimulation)owner;
		TimeInstant start_3 = myModel.presentTime();
			ActivityMessage msg_2a = new ActivityMessage(myModel, this, start_3, "Closing sewer " + i, myModel.presentTime(), 3);
			
	}
}
