package org.visico.utilitydss.server.processsim;

import java.util.Calendar;

import org.visico.utilitydss.shared.Section;

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

/**
 * 
 * @author Simon
 * All activity messages of all detail levels get send. Based on the detail level attached to the message the receiver determines if the message is used.
 * The detail level the receiver accepts is selected in UtilitySimulation.java.
 *
 */
public class ActivityMessage extends Message 
{
	
	//constructor for activitymsg's for class ProcessAll.java
	public ActivityMessage(Model model, ParentProcess s, TimeInstant st, 
			String w, TimeInstant e, int d) 
	{
		super(model, "Customized Schedule Message", st);
		
		sec = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
		
	public ParentProcess getSection()
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
	
	public int getDetaillevel()
	{
		return detailLevel;
	}
	
	private String work;
	private TimeInstant starttime;
	private double duration;
	private ParentProcess sec; 
	private TimeInstant endtime;
	private int detailLevel;
}
