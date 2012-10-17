package org.visico.utilitydss.server.processsim;


import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import desmoj.core.simulator.Experiment;

public class SewerExperiment extends Experiment 
{

	public SewerExperiment(String name) throws ParserConfigurationException 
	{
		super(name, TimeUnit.HOURS, TimeUnit.HOURS, null);
		
		this.setSeedGenerator(System.nanoTime());
		rec = new Receiver();
		this.addReceiver(rec, ActivityMessage.class);
		
	}
	
	public SewerExperiment(String name, String path) throws ParserConfigurationException 
	{
		super(name, path, TimeUnit.HOURS, TimeUnit.HOURS, null, DEFAULT_REPORT_OUTPUT_TYPE, DEFAULT_TRACE_OUTPUT_TYPE, DEFAULT_ERROR_OUTPUT_TYPE , DEFAULT_DEBUG_OUTPUT_TYPE);
		
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
		rec.exportGantt();
	}
	
	private Receiver rec;
}
