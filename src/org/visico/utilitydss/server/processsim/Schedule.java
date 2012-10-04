package org.visico.utilitydss.server.processsim;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import com.google.gwt.dev.util.collect.HashSet;


public class Schedule 
{
	HashSet<Location> locations;
	
	public Schedule()
	{
		locations = new HashSet<Location>();
	}

	public HashSet<Location> getLocations() {
		return locations;
	}

	public void setLocations(HashSet<Location> locations) {
		this.locations = locations;
	}
	
	public void addLocation(Location l)
	{
		this.locations.add(l);
	}
	
	public Location getLocation(String locationName)
	{
		Iterator<Location> it = locations.iterator();
		
		while (it.hasNext())
		{
			Location l = it.next();
			if (locationName.equals(l.getName()))
				return l;
		}
		
		return null;
	}
	
	public Location addNew(String locationName)
	{
		Location existing = getLocation(locationName); 
		if (existing == null)
		{
			Location newLocation = new Location();
			newLocation.setName(locationName);
			locations.add(newLocation);
			return newLocation;
		}
		else
			return existing;
	}
	
	public void createGanttJPG()
	{
		try
		{
			final TaskSeriesCollection collection = new TaskSeriesCollection();
	     
			
			
			Iterator<Location> locIt = this.locations.iterator();
			while (locIt.hasNext())
			{
				Location l = locIt.next();
				TaskSeries ts = new TaskSeries(l.getName());
				
				
				Iterator<WorkItem> wiIt = l.getTasks().iterator();
				while (wiIt.hasNext())
				{
					WorkItem item = wiIt.next();
					Task task = new Task(item.getName(), new SimpleTimePeriod(item.getStart(), item.getEnd()));
					ts.add(task);
				}
				collection.add(ts);
			}			
			
			
	        final JFreeChart chart = createChart(collection);
	        ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 1000, 1500);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "Gantt Chart Demo",  // chart title
            "Task",              // domain axis label
            "Date",              // range axis label
            dataset,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );    
//        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
        return chart;    
    }
}
