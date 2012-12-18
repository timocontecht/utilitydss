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
		myModel = (UtilitySimulation)owner;
		PUT = put;									// section or put:  0 is section, 1 is put.  
		Shore = shore;							
		NUM_CONNECTIONS = connections;
		
		Num_Put_connections = num_put_connections; 	// number of connections the put has, only if put
		Old_pavement = old_pavement; 				// type of old pavement
		New_pavement = new_pavement;  				// type of new pavement
		Section_length = section_length;  			// length of section in
		Pipe_length = pipe_length;  				// length of pipes in
		Section_width = section_width;  			// width of section in
		Trench_width = trench_width;  				// width of Trench v
		Trench_depth = trench_depth;  				// depth of Trench in
		Old_sewer_type = old_sewer_type; 			// type of old sewer
		New_sewer_type = new_sewer_type; 			// type of new sewer
		Old_diameter = old_diameter;  				// diameter of old sewer 
		New_diameter = new_diameter;  				// diameter of new sewer
		Asphalt_old = asphalt_old;  				// layer thickness of old asphalt in
		Asphalt_new = asphalt_new;  				// layer thickness of new asphalt in / 
		Cables = cables;  							// weight class of cables in the ground
		Length_connections = length_connections;  	// average length of connections
		Diameter_connections = diameter_connections;// average depth of connections
		Foundation_type = foundation_type; 			// type foundation used: 1 = , 2 =
		Soil_removed = 	soil_removed;  				// where is the removed soil placed: 1 = , 2 =
		Soil_new = soil_new;  						// where is the new soil placed: 1 = , 2 =
		Pipes_old = pipes_old;  					// where are the removed pipes placed: 1 = , 2 =
		Pipes_new = pipes_new;  					// where are the new pipes placed: 1 = , 2 =
		Rock_layer = rock_layer;					// height of broken rock layer
		Sand_layer = sand_layer;					// height of sand layer	
		Old_put_area = old_put_area;				// area of the old put
		New_put_area = new_put_area;				// area of the new put
		}

	@Override
	public void lifeCycle() {
		// TODO Auto-generated method stub
		

			   // TODO, currently a very cumbersome way to have a put named put and a section named section, see if it can be shortened.
			   if(PUT==0)
			   {
				   
				   ProcessAll subsection = new ProcessAll(
						    myModel, 			//owner
							"Section", 			//name
							true, 				// ?
							PUT,				// section or put:  0 is section, 1 is put.  
							Shore,				// number of pipes in section
							NUM_CONNECTIONS,		// number of connections in section
							
							Num_Put_connections,// number of connections the put has, only if put
							Old_pavement,		// type of old pavement
							New_pavement,		// type of new pavement
							Section_length,		// length of section in
							Pipe_length,			// length of pipes in
							Section_width,		// width of section in
							Trench_width,		// width of Trench 
							Trench_depth,		// depth of Trench in
							Old_sewer_type,		// type of old sewer
							New_sewer_type,		// type of new sewer
							Old_diameter,			// diameter of old sewer 
							New_diameter,		// diameter of new sewer
							Asphalt_old,			// layer thickness of old asphalt in
							Asphalt_new,			// layer thickness of new asphalt in
							Cables,				// weight class of cables in the ground
							Length_connections,	// average length of connections
							Diameter_connections,	// average depth of connections
							Foundation_type, 	// type foundation used: 1 = , 2 =
							Soil_removed,  		// where is the removed soil placed: 1 = , 2 =
							Soil_new,  			// where is the new soil placed: 1 = , 2 =
							Pipes_old,  			// where are the removed pipes placed: 1 = , 2 =
							Pipes_new,  			// where are the new pipes placed: 1 = , 2 =
							Rock_layer,				// height of pavement preparation rock layer in m
							Sand_layer,				// height of pavement preparation sand layer in m
							Old_put_area,			// area of the old put
							New_put_area,			// area of the new put
							Bed_preparation		// height of bed preparation layer 
							);
		
				   subsection.activate();
				   }
				   
				   else
				   {
					   //SectionProcess section = new SectionProcess(this, "Test", true);
					   
					   PutProcessAll subsection = new PutProcessAll(
								myModel, 					//owner
								"Put", 					//name
								true, 					// ?
								PUT,					// section or put:  0 is section, 1 is put.  
								Shore,				// number of pipes in section
								NUM_CONNECTIONS,		// number of connections in section
								
								Num_Put_connections,	// number of connections the put has, only if put
								Old_pavement,		// type of old pavement
								New_pavement,		// type of new pavement
								Section_length,		// length of section in
								Pipe_length,			// length of pipes in
								Section_width,		// width of section in
								Trench_width,		// width of Trench 
								Trench_depth,		// depth of Trench in
								Old_sewer_type,		// type of old sewer
								New_sewer_type,		// type of new sewer
								Old_diameter,			// diameter of old sewer 
								New_diameter,		// diameter of new sewer
								Asphalt_old,			// layer thickness of old asphalt in
								Asphalt_new,			// layer thickness of new asphalt in
								Cables,				// weight class of cables in the ground
								Length_connections,	// average length of connections
								Diameter_connections,	// average depth of connections
								Foundation_type, 	// type foundation used: 1 = , 2 =
								Soil_removed,  		// where is the removed soil placed: 1 = , 2 =
								Soil_new,  			// where is the new soil placed: 1 = , 2 =
								Pipes_old,  			// where are the removed pipes placed: 1 = , 2 =
								Pipes_new,  			// where are the new pipes placed: 1 = , 2 =
								Rock_layer,				// height of pavement preparation rock layer in m
								Sand_layer,				// height of pavement preparation sand layer in m
								Old_put_area,			// area of the old put
								New_put_area,			// area of the new put
								Bed_preparation		// height of bed preparation layer 
								);
			 
					   subsection.activate();					     
				   }

			   
	}
		
	
	/**
	 * Calculation of section specific parameters
	 */
	/*
	NUM_Pipe = (int) Math.ceil(Section_length / Pipe_length); 		// calculation of the required number of pipes
	Section_Area = (Section_length * Section_width);				// total surface of the section
	Trench_Area = (Pipe_length * Trench_width);						// total surface of the trench
	Excavation_volume = (Trench_Area * Trench_depth);  				// excavation volume per pipe
	Total_Area = (myModel.getTotal_length() * Section_width);		// total working area of all sections
	first_backfill_height = New_diameter * 1.26 * 0.001;			// height of first backfill in m (pipe diameter + 2x average wall thickness)
	second_backfill_height = Trench_depth - first_backfill_height;	// height of second backfill in m, only if there are connections
	
	private int NUM_Pipe; 					// calculation of the required number of pipes
	private double Section_Area;			// total surface of the section
	private double Trench_Area;				// total surface of the trench
	private double Excavation_volume;  		// excavation volume per pipe
	private double Total_Area;				// total working area of all sections
	private double first_backfill_height;	// height of first backfill in m (pipe diameter + 2x average wall thinkness)
	private double second_backfill_height;	// height of second backfill in m, only if there are connections
	*/

	
	/**
	 * Section specific parameters.
	 */
	private UtilitySimulation myModel;
	private int PUT; 
	private int Shore;					// Indicates if shoring is used and if so what type is used.
	private int NUM_CONNECTIONS;
	
	private double Num_Put_connections;  	// number of connections the put has, only if put
	private int Old_pavement; 			// type of old pavement
	private int New_pavement;  			// type of new pavement
	private double Section_length;  	// length of section in m 
	private double Pipe_length;  	// length of pipes in m
	private double Section_width;  		// width of section in m
	private double Trench_width;  		// width of Trench  in m
	private double Trench_depth;  		// depth of Trench in m
	private String Old_sewer_type; 		// type of old sewer
	private String New_sewer_type; 		// type of new sewer
	private double Old_diameter;  			// diameter of old sewer 
	private double New_diameter;  			// diameter of new sewer
	private double Old_put_area;		// Area of the old put						
	private double New_put_area;		// Area of the new put						
	private double Asphalt_old;  			// layer thickness of old asphalt in mm
	private double Asphalt_new;  			// layer thickness of new asphalt in mm 
	private double Cables;  			// weight class of cables in the ground
	private double Length_connections;  // average length of connections in m
	private double Diameter_connections;// average depth of connections in m
	private double Foundation_type;  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	private double Soil_removed;  			// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Soil_new;  				// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_old;  			// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Pipes_new;  			// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	private double Rock_layer;			// height of pavement preparation rock layer in m
	private double Sand_layer;			// height of pavement preparation sand layer in m
	private double Bed_preparation;
}
