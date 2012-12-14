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
	
	//constructor for activitymsg's for class ProcessAll.java
	public ActivityMessage(Model model, ProcessAll s, TimeInstant st, 
			String w, TimeInstant e, int d) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
	//constructor for activitymsg's for class SectionProcessAll.java
	public ActivityMessage(Model model, SectionProcessAll s, TimeInstant st, 
			String w, TimeInstant e, int d) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec1 = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
	//constructor for activitymsg's for class PutProcessAll.java (last argument is just for identification of constructor)
	public ActivityMessage(Model model, PutProcessAll s, TimeInstant st, 
			String w, TimeInstant e, int d, String a) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec2 = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
	//constructor for activitymsg's for class Breaking.java (last argument is just for identification of constructor)
	public ActivityMessage(Model model, Breaking s, TimeInstant st, 
			String w, TimeInstant e, int d, int a) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec3 = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
	//constructor for activitymsg's for class Paving.java (last argument is just for identification of constructor)
	public ActivityMessage(Model model, Paving s, TimeInstant st, 
			String w, TimeInstant e, int d, boolean a) 
	{
		super(model, "Customized Schedule Message", st);
		// TODO Auto-generated constructor stub
		
		sec4 = s;
		starttime = st;
		work = w;
		duration = e.getTimeAsDouble() - st.getTimeAsDouble();
		endtime = e;
		detailLevel = d;
	}
	
		
	public ProcessAll getSection()
	{
		return sec;
	}
	
	public SectionProcessAll getSection1()
	{
		return sec1;
	}
	public PutProcessAll getSection2()
	{
		return sec2;
	}
	public Breaking getSection3()
	{
		return sec3;
	}
	public Paving getSection4()
	{
		return sec4;
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
	private ProcessAll sec = null; 
	private SectionProcessAll sec1 = null;
	private PutProcessAll sec2 = null;
	private Breaking sec3 = null;
	private Paving sec4 = null;
	private TimeInstant endtime;
	private int detailLevel;
}
