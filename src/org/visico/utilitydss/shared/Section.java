package org.visico.utilitydss.shared;

import org.visico.utilitydss.server.processsim.PutProcessAll;
import org.visico.utilitydss.server.processsim.UtilitySimulation;

import desmoj.core.simulator.Model;

public class Section
{
	/** 
	 * Not used ATM
	 * this section should store section parameters
	 */
	
	/**
	* Calculation of section specific parameters
	*/
	/*
	NUM_Pipe = (int) Math.ceil(Section_length / Pipe_length); // calculation of the required number of pipes
	Section_Area = (Section_length * Section_width); // total surface of the section
	Trench_Area = (Pipe_length * Trench_width); // total surface of the trench
	Excavation_volume = (Trench_Area * Trench_depth); // excavation volume per pipe
	Total_Area = (myModel.getTotal_length() * Section_width); // total working area of all sections
	first_backfill_height = New_diameter * 1.26 * 0.001; // height of first backfill in m (pipe diameter + 2x average wall thickness)
	second_backfill_height = Trench_depth - first_backfill_height; // height of second backfill in m, only if there are connections
	
	private int NUM_Pipe; // calculation of the required number of pipes
	private double Section_Area; // total surface of the section
	private double Trench_Area; // total surface of the trench
	private double Excavation_volume; // excavation volume per pipe
	private double Total_Area; // total working area of all sections
	private double first_backfill_height; // height of first backfill in m (pipe diameter + 2x average wall thickness)
	private double second_backfill_height; // height of second backfill in m, only if there are connections
	*/

/**
* Section specific parameters.
*/
	private int PUT; 
	private int Shore;					// Indicates if shoring is used and if so what type is used.
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
	private double Bed_preparation;		// height of bed preparation layer
	private int[] Pipe_connections;    // indicates if a pipe has a connection.

	
	/**
	 * Section specific parameters setters & getters
	 */
	public int getPUT() {
		return PUT;
	}
	public void setPUT(int pUT) {
		PUT = pUT;
	}
	public int getShore() {
		return Shore;
	}
	public void setShore(int shore) {
		Shore = shore;
	}
	public double getNum_Put_connections() {
		return Num_Put_connections;
	}
	public void setNum_Put_connections(double num_Put_connections) {
		Num_Put_connections = num_Put_connections;
	}
	public int getOld_pavement() {
		return Old_pavement;
	}
	public void setOld_pavement(int old_pavement) {
		Old_pavement = old_pavement;
	}
	public int getNew_pavement() {
		return New_pavement;
	}
	public void setNew_pavement(int new_pavement) {
		New_pavement = new_pavement;
	}
	public double getSection_length() {
		return Section_length;
	}
	public void setSection_length(double section_length) {
		Section_length = section_length;
	}
	public double getPipe_length() {
		return Pipe_length;
	}
	public void setPipe_length(double pipe_length) {
		Pipe_length = pipe_length;
	}
	public double getSection_width() {
		return Section_width;
	}
	public void setSection_width(double section_width) {
		Section_width = section_width;
	}
	public double getTrench_width() {
		return Trench_width;
	}
	public void setTrench_width(double trench_width) {
		Trench_width = trench_width;
	}
	public double getTrench_depth() {
		return Trench_depth;
	}
	public void setTrench_depth(double trench_depth) {
		Trench_depth = trench_depth;
	}
	public String getOld_sewer_type() {
		return Old_sewer_type;
	}
	public void setOld_sewer_type(String old_sewer_type) {
		Old_sewer_type = old_sewer_type;
	}
	public String getNew_sewer_type() {
		return New_sewer_type;
	}
	public void setNew_sewer_type(String new_sewer_type) {
		New_sewer_type = new_sewer_type;
	}
	public double getOld_diameter() {
		return Old_diameter;
	}
	public void setOld_diameter(double old_diameter) {
		Old_diameter = old_diameter;
	}
	public double getNew_diameter() {
		return New_diameter;
	}
	public void setNew_diameter(double new_diameter) {
		New_diameter = new_diameter;
	}
	public double getOld_put_area() {
		return Old_put_area;
	}
	public void setOld_put_area(double old_put_area) {
		Old_put_area = old_put_area;
	}
	public double getNew_put_area() {
		return New_put_area;
	}
	public void setNew_put_area(double new_put_area) {
		New_put_area = new_put_area;
	}
	public double getAsphalt_old() {
		return Asphalt_old;
	}
	public void setAsphalt_old(double asphalt_old) {
		Asphalt_old = asphalt_old;
	}
	public double getAsphalt_new() {
		return Asphalt_new;
	}
	public void setAsphalt_new(double asphalt_new) {
		Asphalt_new = asphalt_new;
	}
	public double getCables() {
		return Cables;
	}
	public void setCables(double cables) {
		Cables = cables;
	}
	public double getLength_connections() {
		return Length_connections;
	}
	public void setLength_connections(double length_connections) {
		Length_connections = length_connections;
	}
	public double getDiameter_connections() {
		return Diameter_connections;
	}
	public void setDiameter_connections(double diameter_connections) {
		Diameter_connections = diameter_connections;
	}
	public double getFoundation_type() {
		return Foundation_type;
	}
	public void setFoundation_type(double foundation_type) {
		Foundation_type = foundation_type;
	}
	public double getSoil_removed() {
		return Soil_removed;
	}
	public void setSoil_removed(double soil_removed) {
		Soil_removed = soil_removed;
	}
	public double getSoil_new() {
		return Soil_new;
	}
	public void setSoil_new(double soil_new) {
		Soil_new = soil_new;
	}
	public double getPipes_old() {
		return Pipes_old;
	}
	public void setPipes_old(double pipes_old) {
		Pipes_old = pipes_old;
	}
	public double getPipes_new() {
		return Pipes_new;
	}
	public void setPipes_new(double pipes_new) {
		Pipes_new = pipes_new;
	}
	public double getRock_layer() {
		return Rock_layer;
	}
	public void setRock_layer(double rock_layer) {
		Rock_layer = rock_layer;
	}
	public double getSand_layer() {
		return Sand_layer;
	}
	public void setSand_layer(double sand_layer) {
		Sand_layer = sand_layer;
	}
	public double getBed_preparation() {
		return Bed_preparation;
	}
	public void setBed_preparation(double bed_preparation) {
		Bed_preparation = bed_preparation;
	}
	public int[] getPipe_connections() {
		return Pipe_connections;
	}
	public void setPipe_connections(int[] pipe_connections) {
		Pipe_connections = pipe_connections;
	}
}

