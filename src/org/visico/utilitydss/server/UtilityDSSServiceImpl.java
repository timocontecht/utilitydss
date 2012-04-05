package org.visico.utilitydss.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.visico.utilitydss.shared.Project;
import org.visico.utilitydss.shared.User;

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
}
