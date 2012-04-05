package org.visico.utilitydss.server.processsim;


import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import desmoj.core.simulator.Experiment;

public class SewerExperiment extends Experiment 
{

	public SewerExperiment(String name) throws ParserConfigurationException 
	{
		super(name, TimeUnit.HOURS, TimeUnit.HOURS, null);
		
		rec = new Receiver();
		this.addReceiver(rec, ActivityMessage.class);
		
	}

	public Receiver getReceiver()
	{
		return rec;
	}
	
	public void report()
	{
		super.report();
		rec.writeXML();
	}
	private Receiver rec;
}
