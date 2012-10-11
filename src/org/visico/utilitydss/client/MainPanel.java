package org.visico.utilitydss.client;

import org.visico.utilitydss.shared.User;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		final Image visico_img = new Image();
		visico_img.setUrl("VISICO.jpg");
		final VerticalPanel logopanel = new VerticalPanel();
		logopanel.add(visico_img);
		addNorth(logopanel, 10);
		//addSouth(new HTML("footer"), 2);
		//addWest(new HTML("navigation"), 10);
		add(new SimulationPanel());
	}
	

}
