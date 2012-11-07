package org.visico.utilitydss.server.test;

import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.visico.utilitydss.server.processsim.SewerExperiment;
import org.visico.utilitydss.server.processsim.UtilitySimulation;


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
		
	}
}
