package org.visico.utilitydss.server.processsim;

import java.util.ArrayList;

public class Location 
{
	String name;
	ArrayList<WorkItem> workItems;
	
	public Location()
	{
		workItems = new ArrayList<WorkItem>();
	}

	public ArrayList<WorkItem> getTasks() {
		return workItems;
	}

	public void setWorkItem(ArrayList<WorkItem> workItems) {
		this.workItems = workItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addWorkItem(WorkItem t)
	{
		this.workItems.add(t);
	}

	@Override
	public boolean equals(Object other) 
	{
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Location))return false;
	    
	    Location l = (Location)other;
	    if (l.getName().equals(this.getName()))
	    	return true;
	    else 
	    	return false;
	}
	
}
