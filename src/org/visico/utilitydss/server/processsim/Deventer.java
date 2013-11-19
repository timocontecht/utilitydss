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
	
		// Section nr.									{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
	   public static int[] put = 						{ 0, 1,};//, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 }; 	// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																											// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 			// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 2, 2, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 };			// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 0, 2, 0, 3, 0, 3, 0, 0, 2, 0, 2, 0, 1 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																											// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																										// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 10, 2.25, 19, 2.25, 63, 2.25, 35, 80, 2, 64, 1.5, 46, 0.64 }; // length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 13, 13, 13, 18, 18, 18, 18, 18, 8 };  			// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  					// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 4.4, 4.4, 4.4, 4.2, 4, 4, 4, 3, 3, 2.5, 1.8, 1.8, 1.6, 1.6}; 	// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 600, 600, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 315, 315, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 800, 000, 800, 000, 800, 000, 800, 500, 000, 400, 000, 300, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 000, 315, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 			{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		  	{ 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  					// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };					// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 	//height of bed preparation layer.
	   
	   // each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   													
	   private static int[] section0_connections =		{};				// indicates if a pipe in section 1 has a connection.
	   private static int[] section1_connections =		{};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{ 3, 7};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{ 3, 7, 11, 15, 20, 24};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ 3, 7, 12};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 5, 21, 29};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 4, 13, 20};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =		{ 2, 10, 17};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section0_connections, section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections, 
		   												section11_connections, section12_connections};
	   */

	   /**
		 * Fase 1B2
		 */

		// Section nr.									{ 1, 2, 3, 4, 5}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   ///*
		public static int[] put = 						{ 0, 1};//, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0}; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0}; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1}; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1};		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 0, 2, 0, 2, 0};  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0}; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 3, 3, 3, 3, 3};  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0};  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0};  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0};  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0};  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0};  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0};  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 82, 2, 88, 2, 58}; 															// length of pipes in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7};  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7};  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 3.7, 3.7, 3.7, 3.7, 3.7};  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000};  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000}; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 600, 000, 600, 000, 600};  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315};  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 			{ 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4};		// Area of the old put in m^2
	   public static double[] old_put_area_sep =			{ 4, 4, 4, 4, 4};		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3 };		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =			{ 0, 0, 0, 0, 0 }; //height of bed preparation layer.   
	   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{4, 8, 12, 17, 21, 25, 30 };							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{5, 9, 15, 19, 23, 27, 33};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{3, 8, 12, 16, 20, 23 };							// indicates if a pipe in section 2 has a connection.
		public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,
														section5_connections};
	   //*/

	   /**
		 * Fase 1B3
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 0, 2, 0, 2, 0, 2, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 20, 2.25, 55, 2.2, 24, 2, 80, 2, 47};  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 13, 13, 13, 13, 13, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 3.8, 3.8, 3.8, 3.7, 3.7, 3.7, 3.6, 3.5, 3.4 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 800, 000, 700, 000, 700, 000, 700, 000, 700 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 800, 315, 000, 315 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3 };		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; //height of bed preparation layer.   
	   	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{ 2, 6 };							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{ 4, 8, 13, 17, 21 };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 3, 7 };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 1, 5, 9, 14, 18, 23, 28, 32 };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 5, 9, 13, 18 };							// indicates if a pipe in section 2 has a connection.
		public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections};
	   */
	   
	   
	   /**
		 * Fase 1B4
		 */
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0, 1 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections =  	{ 0, 2, 0, 1, 0, 1, 0, 1 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 37, 2.25, 54, 2.25, 59, 2.25, 76, 2,25 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 3.5, 3.4, 3.3, 3.1, 2.5, 2.4, 2.2, 2 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 800, 000, 800, 000, 800, 000, 500, 000 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 000, 315, 000 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0, 0 }; //height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{2, 6, 10, 15};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{ 4, 8, 12, 17, 21};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 1, 5, 9, 14, 19, 23};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 6, 11, 15, 19, 23, 27, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections };
	   */
	   
	   
	   /**
		 * Fase 1B5
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																											// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections =  	{ 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																											// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																										// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 33, 2, 39, 2.25, 80, 2.25, 80, 2.25, 80, 2.25, 70, 2.25, 39, 2.25, 31 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 3.4, 3.4, 3.3, 3.3, 3.2, 3.2, 3.2, 3.0, 2.8, 2.7, 2.6, 2.5, 2.4, 2.4, 2.4 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 700, 000, 700, 000, 800, 000, 800, 000, 800, 000, 800, 000, 800, 000, 400 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; //height of bed preparation layer.   
	   	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{ 2, 6, 10};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{ 1, 5, 10, 14};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 3, 7, 12, 16, 21, 25, 29, 32};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 3, 7, 12, 16, 22, 26, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 3, 7, 12, 16, 21, 25, 29, 32};		 					// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =		{ 3, 7, 12, 16, 22, 26,};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section13_connections =		{ 2, 6, 10, 14};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section14_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section15_connections =		{ 2, 7, 11, 15};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections,
		   												section11_connections, section12_connections, section13_connections, section14_connections, section15_connections};
	   */
	   
	   
	   /**
		 * Fase 1C1
		 */
	
	   // Section nr.									{ 1, 2, 3, 4, 5, 6, 7 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*
		public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections =  	{ 0, 2, 0, 2, 0, 2, 0};  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 			 	{ 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 25, 1.5, 80, 1.5, 80, 2, 15};  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 2.2, 2.2, 2, 1.8, 1.7, 1.5, 1.5 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 400, 000, 400, 000, 400, 000, 500 };  			// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 000, 315 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5};  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0 }; 	//height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{4, 8};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{3, 7, 12, 16, 21, 25, 29, 32};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 3, 7, 12, 16, 22, 26, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{ };							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 2, 3, 4};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections};
	   */
	   
	   
	   /**
		 * Fase 1C2
		 */
	
		// Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 3, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																												// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 			 	{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																											// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site

	   //Dimensions
	   public static double[] section_length = 			{ 25, 2, 80, 2.25, 46, 2.25, 80, 2.25, 80, 2.25, 93, 2.25,31, 2, 24 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 4.3, 4.1, 3.9, 3.7, 3.4, 3.4, 3.3, 3.3, 3.2, 3.1, 3, 2.9, 2.8, 2.8, 2.7 };  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 		 	{ 600, 000, 700, 000, 700, 000, 800, 000, 900, 000, 900, 000, 500, 000, 500 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 	 	{ 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315, 000, 315 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40 };  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 	//height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{4, 9};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{3, 7, 12, 16, 22, 26, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 2, 3, 5};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 3, 7, 12, 16, 22, 26, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 3, 7, 12, 16, 21, 25, 29, 32};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section10_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section11_connections =		{ 3, 7, 12, 16, 22, 26, 30, 34};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section12_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section13_connections =		{ 2, 6, 10};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section14_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section15_connections =		{ 3, 7};							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections, section10_connections,
		   												section11_connections, section12_connections, section13_connections, section14_connections, section15_connections};
	   */
	   
	   
	   /**
		 * Fase 1C3
		 */
	
		// Section nr.									{ 1, 2, 3, 4, 5, 6, 7, 8, 9}; 		// indicates the number of the section for ease of characteristics input
	   // project characteristics
	   /*   												
	   public static int[] put = 						{ 0, 1, 0, 1, 0, 1, 0, 1, 0 }; 		// indicates if section is pipe section or put, 0 is section, 1 is put, 2 is overstort  
	   public static int[] shore = 						{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// indicates if project requires shoring, 0 means no shoring, 1 means sliding cask, 
																								// 2 means Sheet piling (damwand), 3 means supported walls  
	   public static int [] replacement =				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 			// Indicates if this section has existing old sewer: 0 is no, 1 is yes
	   public static int [] oldSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 		// Indicates if the old section has combined or separated sewer: 0 is combined, 2 is separated
	   public static int [] newSeparated = 				{ 1, 1, 1, 1, 1, 1, 1, 1, 1 };		// Indicates if the new section has combined or separated sewer:: 0 is combined, 2 is separated
	   public static double[] num_put_connections = 	{ 0, 2, 0, 2, 0, 2, 0, 2, 0 };  		// number of connections the put has, only if put
	   public static int[] old_pavement = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		// type of old pavement indicates old pavement type, 0 means no pavement, 1 means asphalt; break section, 2 means stones, 
																								// 3 means asphalt; break all sections at start--> 3 can only be used if this goes for all sections
	   public static int[] new_pavement = 				{ 3, 3, 3, 3, 3, 3, 3, 3, 3 };  		// type of new pavement indicates new pavement type, 0 means no pavement, 1 means asphalt; pave section, 2 means stones,
	   																							// 3 means asphalt; pave all sections at end --> 3 can only be used if this goes for all sections
	   public static double[] cables =		 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// weight class of cables in the ground 
	   public static double[] foundation_type =			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// type foundation used: 1 = solidified sand, 2 = styrofoam plate, 3 = pole construction
	   public static double[] soil_removed = 			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the removed soil placed: 0 = next to trench 1 = in depot, 2 = transported off site=
	   public static double[] soil_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where is the new soil placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_old = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the removed pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	   public static double[] pipes_new = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };  		// where are the new pipes placed: 0 = next to trench 1 = in depot, 2 = transported off site
	
	   //Dimensions
	   public static double[] section_length = 			{ 55, 2, 80, 1.5, 80, 1.5, 80, 2, 67 };  				// length of section in
	   public static double pipe_length = 				2.4; 															// length of pipes in
	   public static double[] section_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of section in
	   public static double[] trench_width = 			{ 7, 7, 7, 7, 7, 7, 7, 7, 7 };  								// width of Trench in  					///////////// bigger with puts?
	   public static double[] trench_depth = 			{ 2.7, 2.5, 2.4, 2.3, 2.2, 2.1, 1.9, 1.8, 1.5};  								// depth of Trench in
	   public static String old_sewer_type = 		 	"Concrete"; 													// type of old sewer	Concrete, Gres, Plastic
	   public static String new_sewer_type = 		 	"Concrete"; 													// type of new sewer	Concrete, Gres, Plastic
	   public static double[] old_diameter = 			{ 000, 000, 000, 000, 000, 000, 000, 000, 000 };  		// diameter of old sewer (HWA if separated)
	   public static double[] old_diameter_sep =		{ 000, 000, 000, 000, 000, 000, 000, 000, 000 }; 		// diameter of old sewer separated DWA streng 
	   public static double[] new_diameter = 			{ 500, 000, 400, 000, 400, 000, 400, 000, 500 };  		// diameter of new sewer (HWA if separated)
	   public static double[] new_diameter_sep = 		{ 315, 000, 315, 000, 315, 000, 315, 000, 315 };  		// diameter of old sewer separated DWA streng 
	   public static double[] asphalt_old = 		 	{ 40, 40, 40, 40, 40, 40, 40, 40, 40};  		// layer thickness of old asphalt in
	   public static double[] asphalt_new = 		 	{ 80, 80, 80, 80, 80, 80, 80, 80, 80 };  		// layer thickness of new asphalt in
	   public static double[] length_connections =		{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };  		// average length of connections
	   public static double[] diameter_connections =	{ 160, 160, 160, 160, 160, 160, 160, 160, 160 };  		// average depth of connections
	   public static double[] old_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] old_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the old put in m^2
	   public static double[] new_put_area =			{ 4, 4, 4, 4, 4, 4, 4, 4, 4};		// Area of the new put in m^2
	   public static double[] new_put_area_sep =		{ 4, 4, 4, 4, 4, 4, 4, 4, 4 };		// Area of the new put in m^2
	   public static double[] rock_layer = 			 	{ 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3};		// height of pavement preparation rock layer in m 
	   public static double[] sand_layer = 				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };		// height of pavement preparation sand layer in m
	   public static double[] bed_preparation =		 	{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 		//height of bed preparation layer.   
	   
	   //each pipe should have a flag if it has a connection or not, this indicates a geometry.
	   // make connections independent connections.
	   
	   private static int[] section1_connections =		{3, 7, 12, 16, 22};							// indicates if a pipe in section 1 has a connection.
	   private static int[] section2_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section3_connections =		{ 3, 7, 12, 16, 21, 25, 29, 32};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section4_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section5_connections =		{ 3, 7, 12, 16, 22, 26, 30};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section6_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section7_connections =		{ 3, 7, 12, 16, 21, 25, 29, 32};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section8_connections =		{};							// indicates if a pipe in section 2 has a connection.
	   private static int[] section9_connections =		{ 3, 7, 12, 16, 22, 26 };							// indicates if a pipe in section 2 has a connection.
	   public static int[][] pipe_connections = 		{ section1_connections, section2_connections, section3_connections, section4_connections,section5_connections,
		   												section6_connections, section7_connections, section8_connections, section9_connections };
	   */
}

