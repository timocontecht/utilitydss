package org.visico.utilitydss.server.processsim;

import java.util.Date;

import org.jfree.data.gantt.Task;
import org.jfree.data.time.SimpleTimePeriod;

public class UtilityTask extends Task 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2618812146164268595L;

	public UtilityTask(String description, Date start, Date end) {
		super(description, start, end);
		// TODO Auto-generated constructor stub
	}
	
	public UtilityTask(String string, SimpleTimePeriod simpleTimePeriod) {
		super(string, simpleTimePeriod);
	}

	public boolean isSummaryTask() {
		return isSummaryTask;
	}

	public void setSummaryTask(boolean isSummaryTask) {
		this.isSummaryTask = isSummaryTask;
	}
	
	private boolean isSummaryTask = false;

	
}
