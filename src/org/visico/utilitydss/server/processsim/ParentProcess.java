package org.visico.utilitydss.server.processsim;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.visico.utilitydss.server.processsim.ProcessAll;
import org.visico.utilitydss.server.processsim.PutProcessAll;
import org.visico.utilitydss.server.processsim.SectionProcessAll;
import org.visico.utilitydss.server.processsim.SewerExperiment;
import org.visico.utilitydss.server.processsim.UtilitySimulation;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class ParentProcess extends SimProcess
{

	public ParentProcess(Model owner, 
			String name, 
			boolean showInTrace, 
			int put,
			int shore, 
			int connections,
			double num_put_connections,
			int old_pavement,
			int new_pavement,
			double section_length,
			double pipe_length,
			double section_width,
			double trench_width,
			double trench_depth,
			String old_sewer_type,
			String new_sewer_type,
			double old_diameter,
			double new_diameter,
			double asphalt_old,
			double asphalt_new,
			double cables,
			double length_connections,
			double diameter_connections,
			double foundation_type, 
			double soil_removed,  	
			double soil_new,  		
			double pipes_old,  		
			double pipes_new,
			double rock_layer,
			double sand_layer,
			double old_put_area,
			double new_put_area,
			double Bed_preparation
			) 
	
	{
		super(owner, name, showInTrace);
		}

	@Override
	public void lifeCycle() {
		// TODO Auto-generated method stub

	}
	public int connections_done;

}
