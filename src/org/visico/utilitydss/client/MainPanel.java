package org.visico.utilitydss.client;

import org.visico.utilitydss.shared.User;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;

public class MainPanel extends DockLayoutPanel 
{
	static private MainPanel instance = null;
	
	static public MainPanel getInstance()
	{
		if (instance == null)
			instance = new MainPanel();
		return instance;
	}

	private User user = null;
	
	
	
	public User getUser() 
	{
		return user;
	}

	public void setUser(User user) 
	{
		this.user = user;
	}

	private MainPanel() 
	{
		super(Unit.EM);
		
	}
	
	public void draw()
	{
		addNorth(new UserPanel(), 10);
		addSouth(new HTML("footer"), 2);
		addWest(new HTML("navigation"), 10);
		add(new HTML("center"));
	}
	

}
