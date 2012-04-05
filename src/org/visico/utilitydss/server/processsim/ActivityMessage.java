package org.visico.utilitydss.server.processsim;

import java.util.Calendar;

import desmoj.core.report.*;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;



public class ActivityMessage extends Message 
{

	public ActivityMessage(Model model, Section s, TimeInstant st, 
			String w, TimeInstant e) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
	}
	
	public Section getSection()
	{
		return sec;
	}
	
	public String work()
	{
		return work;
	}
	
	public Calendar start()
	{
		return starttime.getTimeAsCalender();
	}
	
	public double duration()
	{
		return duration;
	}
	
	private String work;
	private TimeInstant starttime;
	private double duration;
	private Section sec; 

}
