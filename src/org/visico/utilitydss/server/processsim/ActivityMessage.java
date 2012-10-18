package org.visico.utilitydss.server.processsim;

import java.util.Calendar;

import desmoj.core.report.*;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;


/**
 * 
 * @author timo
 * This class can be used to construct messages from sections to track the work activities on each section. 
 * The messages contain the information required to generate the CPM and LOB schedules and other 
 * related outputs. Messages can be send to the standard DesmoJ receiver class.  
 *  
 */
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
		endtime = e;
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
	
	public Calendar end()
	{
		return endtime.getTimeAsCalender();
	}
	
	private String work;
	private TimeInstant starttime;
	private double duration;
	private Section sec; 
	private TimeInstant endtime;

}
