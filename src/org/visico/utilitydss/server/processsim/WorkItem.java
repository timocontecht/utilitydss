package org.visico.utilitydss.server.processsim;

import java.util.Date;

public class WorkItem 
{
	String name;
	Date start;
	Date end;
	
	public WorkItem(String work, Date startTime, Date endTime) 
	{
		this.name = work;
		this.start = startTime;
		this.end = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	
}
