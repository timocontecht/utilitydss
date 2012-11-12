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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;




public class Schedule 
{
	ArrayList<Location> locations;
	
	public Schedule()
	{
		locations = new ArrayList<Location>();
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
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
	
	public void createGanttJPGSectionMain()
	{
		try
		{
			final TaskSeriesCollection collection = new TaskSeriesCollection();
			TaskSeries series = new TaskSeries("Sewer Construction Schedule");
			collection.add(series);
			
			Iterator<Location> locIt = this.locations.iterator();
			int locNumber = 0;
			while (locIt.hasNext())
			{
				locNumber++;
				// one array list to store subtasks
				ArrayList<Task> tasks = new ArrayList<Task>();
				// two longs to store earliest start and latest end
				long start = Long.MAX_VALUE, end = Long.MIN_VALUE;
				
				Location l = locIt.next();
				
				
				Iterator<WorkItem> wiIt = l.getTasks().iterator();
				while (wiIt.hasNext())
				{
					WorkItem item = wiIt.next();
					if (item.getStart() < start) 
						start = item.getStart();
					if (item.getEnd() > end) 
						end = item.getEnd();
					UtilityTask task = new UtilityTask(item.getName() + " " + locNumber, new SimpleTimePeriod(item.getStart(), item.getEnd()));
					tasks.add(task);
				}
				
				// one task as an overarching summary task
				UtilityTask summaryTask = new UtilityTask(l.getName(), new SimpleTimePeriod(start, end));
				summaryTask.setSummaryTask(true);
				series.add(summaryTask);
				
				Iterator<Task> taskIt = tasks.iterator();
				while (taskIt.hasNext())
				{
					series.add(taskIt.next());
				}
				
			}			
			
			
	        final JFreeChart chart = createChart(collection);
	        ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 1000, 1500);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createGanttJPGTaskMain()
	{
		try
		{
			final TaskSeriesCollection collection = new TaskSeriesCollection();
			
			Iterator<Location> locIt = this.locations.iterator();
			boolean first = true;
			while (locIt.hasNext())
			{
				Location l = locIt.next();
				
				// establish one task series per work item during the first run
				if (first == true)
				{
					Iterator<WorkItem> wiIt = l.getTasks().iterator();
					while (wiIt.hasNext())
					{
						WorkItem item = wiIt.next();
						TaskSeries ts = new TaskSeries(item.getName());
						collection.add(ts);
					}
					first = false;
				}
				
				// then fill these work sets
				Iterator<WorkItem> wiIt = l.getTasks().iterator();
				int i=0;
				while (wiIt.hasNext())
				{
					WorkItem item = wiIt.next();
					Task task = new Task(l.getName(), new SimpleTimePeriod(item.getStart(), item.getEnd()));
					collection.getSeries(i).add(task);
					i++;
				}
				
				
			}			
			
			
	        final JFreeChart chart = createChart(collection);
	        
	        ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 1000, 1500);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private JFreeChart createChart(final TaskSeriesCollection dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "Gantt Chart Demo",  // chart title
            "Task",              // domain axis label
            "Date",              // range axis label
            dataset,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );   
       
        CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
        categoryplot.getDomainAxis().setMaximumCategoryLabelWidthRatio(10F);
        MyGanttRenderer ganttrenderer = new MyGanttRenderer(dataset);
        categoryplot.setRenderer(ganttrenderer);

        return chart;    
    }
}
