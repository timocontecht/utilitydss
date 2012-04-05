package org.visico.utilitydss.client;

import org.visico.utilitydss.client.MainPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UtilityDSS implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		RootLayoutPanel.get().add(MainPanel.getInstance());
		MainPanel.getInstance().draw();
	}
}
