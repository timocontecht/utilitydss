package org.visico.utilitydss.server.processsim;

import java.util.Date;

public class WorkItem 
{
	String name;
	long start;
	long end;
	
	public WorkItem(String work, long startTime, long endTime) 
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
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

	
}
