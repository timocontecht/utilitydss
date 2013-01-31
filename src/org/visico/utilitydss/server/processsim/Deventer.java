package org.visico.utilitydss.server.processsim;

public class Deventer {
	
	/**
	    * @author Simon
	    * Class containing project information on test project Bedrijvenpark Deventer A1
	    *
	    * Used to instantiate sections and puts with the right information.
	    *
	    * @param owner the model this process belongs to
	    * @param name this section's name
	    * @param showInTrace flag to indicate if this process shall produce output
	    *                    for the trace
	    */
	   
		/**
		 * Fase 1B1
		 */
	
		// Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																										// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 					// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0};			// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0, 2, 0, 1 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																										// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																									// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12, 4, 12, 4 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =	d	{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =	d	{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =	d	{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =	d	{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =	d	{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =	d	{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =	d	{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =	d	{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =	d	{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =	d	{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =	d	{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =	d	{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 	d	{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections, 
		   												section11_connections, section12_connections};
	   

	   /**
		 * Fase 1B2
		 */

		// Section nr.									{ 1, 2, 3, 4, 5}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		   												public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0}; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0}; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1}; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3};  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0}; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0};  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0};  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0};  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0};  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0};  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0};  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0};  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5}; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5};  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4};  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2};  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000};  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000}; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000};  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000};  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40};  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4};		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4};		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
		public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,
														section5_connections};
	   */

	   /**
		 * Fase 1B3
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		   												public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0, 2, 0, 1, 0, 0, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12, 4, 4, 12, 4, 5, 15 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000, xxx, xxx };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000, xxx, xxx };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section13_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section14_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section15_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections
		   												section11_connections, section12_connections, section13_connections, section14_connections, section15_connections};
	   */
	   
	   
	   /**
		 * Fase 1B4
		 */
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections };
	   */
	   
	   
	   /**
		 * Fase 1B5
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		   												public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0, 2, 0, 1, 0, 0, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12, 4, 4, 12, 4, 5, 15 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000, xxx, xxx };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000, 500, 000, 300, 000, xxx, xxx };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section13_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section14_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section15_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections
		   												section11_connections, section12_connections, section13_connections, section14_connections, section15_connections};
	   */
	   
	   
	   /**
		 * Fase 1C1
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4};  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections};
	   */
	   
	   
	   /**
		 * Fase 1C2
		 */
	
		// Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections };
	   */
	   
	   
	   /**
		 * Fase 1C3
		 */
	
		S// Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 1, 0, 1, 0, 2, 0, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 					?	{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 0, 0, 0};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 2, 0, 3, 0, 3, 0, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	
	   //Dimensions
	   public static double[] section_length = 		d	{ 4, 12.5, 4, 12.5, 12.5, 4, 12.5, 4, 12 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 		d	{ 5, 5, 5, 5, 5, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 		d	{ 4, 4, 4, 4, 4, 5, 5, 5, 5 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 		d	{ 2, 2, 2, 2, 2, 2, 2, 2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 000, 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		?	{ 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =	d	{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =d	{ 300, 300, 300, 300, 300, 300, 300, 300, 300 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep=			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   // TODO doe eens fixen
	   public static double[] rock_layer = 			d	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3,0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 			d	{ 0.04, 0.04, 0.04, 2, 1, 1, 2, 2, 2, 2 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		d	{ 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2 }; //height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 1, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections };
	   */
}

