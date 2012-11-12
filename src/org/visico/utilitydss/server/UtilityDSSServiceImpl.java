package org.visico.utilitydss.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import desmoj.core.simulator.TimeInstant;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.visico.utilitydss.server.processsim.SewerExperiment;
import org.visico.utilitydss.server.processsim.UtilitySimulation;
import org.visico.utilitydss.shared.Project;
import org.visico.utilitydss.shared.Scenario;
import org.visico.utilitydss.shared.User;

import javax.xml.parsers.ParserConfigurationException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UtilityDSSServiceImpl extends RemoteServiceServlet implements
		org.visico.utilitydss.client.UtilityDSSService 
{ 
	@Override
	public User login(String username, String password)
			throws IllegalArgumentException
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		
			connect = DriverManager.getConnection("jdbc:mysql://localhost/utilityDSS", "utilityDSS", "utilityDSS");
		
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM user WHERE name = '" + username + "' AND password = SHA1('" + password + "')");
			if (resultSet.first())
			{
				HttpServletRequest request = this.getThreadLocalRequest();
				HttpSession session = request.getSession();
				
				User u = new User();
				u.setId(resultSet.getLong("id"));
				u.setName(resultSet.getString("name"));
				u.setEmail(resultSet.getString("email"));
				u.setSessionId(session.getId());
				return u;
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				if (resultSet != null) {
					resultSet.close();
				}
	
				if (statement != null) {
					statement.close();
				}
	
				if (connect != null) {
					connect.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	@Override
	public ArrayList<Project> getProjects(User user)
			throws IllegalArgumentException 
	{
		if (!checkSession(user.getSessionId()))
		{
			IllegalArgumentException ex = new IllegalArgumentException("You are not logged in.");
			throw ex;
		}
		
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		ArrayList<Project> projects = new ArrayList<Project>();
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		
			connect = DriverManager.getConnection("jdbc:mysql://localhost/utilityDSS", "utilityDSS", "utilityDSS");
		
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT p.* FROM project p, user_project up WHERE p.id = up.projectid AND up.userid = " + user.getId() + ";");
			while (resultSet.next())
			{
				Project p = new Project();
				p.setId(resultSet.getLong("id"));
				p.setName(resultSet.getString("name"));
				p.setLocation(resultSet.getString("location"));
				p.setDescription(resultSet.getString("description"));
				projects.add(p);
			}
			
			return projects;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				if (resultSet != null) {
					resultSet.close();
				}
	
				if (statement != null) {
					statement.close();
				}
	
				if (connect != null) {
					connect.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	private boolean checkSession (String sessionID)
	{
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		if (sessionID.equals(session.getId()))
			return true;
		else return false;
	}

	@Override
	public String simulate(Scenario scenario)
			throws IllegalArgumentException {
		
		// create model and experiment as a first step
		   UtilitySimulation model = new UtilitySimulation(null,
		                         "Simple Process-Oriented Sewerage Re-construction Model", 
		                         true, true, scenario);
		   // null as first parameter because it is the main model and has no master model
		   
		  
		   SewerExperiment exp;
		try {
			exp = new SewerExperiment("Sewer Replacement example", this.getServletContext().getRealPath("/"));
			
			
		
		   // ATTENTION, since the name of the experiment is used in the names of the
		   // output files, you have to specify a string that's compatible with the
		   // filename constraints of your computer's operating system. The remaining three
		   // parameters specify the granularity of simulation time, default unit to
		   // display time and the time formatter to use (null yields a default formatter).
		   
		   // connect both
		   model.connectToExperiment(exp);
		   
		   // set experiment parameters
		   //
		   
		   
		   
		   exp.setShowProgressBar(true);  // display a progress bar (or not)

		   //exp.stop(new TimeInstant(6000, TimeUnit.HOURS));   
		   exp.tracePeriod(new TimeInstant(0), new TimeInstant(6000, TimeUnit.HOURS));

		                                              // set the period of the trace
		   exp.debugPeriod(new TimeInstant(0), new TimeInstant(6000, TimeUnit.HOURS));   // and debug output
		      // ATTENTION!
		      // Don't use too long periods. Otherwise a huge HTML page will
		      // be created which crashes Netscape :-)
		   
		// start the experiment at simulation time 0.0
		   exp.start();

		   // --> now the simulation is running until it reaches its end criterion
		   // ...
		   // ...
		   // <-- afterwards, the main thread returns here

		   // generate the report (and other output files)
		   exp.report();
		   
		   
		  
		   
		   // stop all threads still alive and close all output files
		   exp.finish();  
		   
		   return "Simulation completed.";
		   
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "problem in the simulation";
		}
		// TODO Auto-generated method stub
		
	}
}
