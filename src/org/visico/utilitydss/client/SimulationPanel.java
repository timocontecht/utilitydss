package org.visico.utilitydss.client;

import java.util.ArrayList;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;


public class SimulationPanel extends FlexTable implements ClickHandler
{
	
	public SimulationPanel()
	{
		numBreaker_tb = new TextBox();
		numExcavator_tb = new TextBox();
		numCrane_tb = new TextBox();
		numCrew_tb = new TextBox();
		numRoller_tb = new TextBox();
		numTruck_tb = new TextBox();
		
		this.setWidget(0, 0, new Label("Number of Breakers: "));
		this.setWidget(0, 1, numBreaker_tb);
		
		this.setWidget(1, 0, new Label("Number of Excavators: "));
		this.setWidget(1, 1, numExcavator_tb);
		
		this.setWidget(2, 0, new Label("Number of Cranes: "));
		this.setWidget(2, 1, numCrane_tb);
		
		this.setWidget(3, 0, new Label("Number of Crews: "));
		this.setWidget(3, 1, numCrew_tb);
		
		this.setWidget(4, 0, new Label("Number of Rollers: "));
		this.setWidget(4, 1, numRoller_tb);
		
		this.setWidget(5, 0, new Label("Number of Trucks: "));
		this.setWidget(5, 1, numTruck_tb);
		
		Button simulate_btn = new Button("Simulate");
		simulate_btn.addClickHandler(this);
		this.setWidget(6, 0, simulate_btn);
		
		this.setWidget(7, 0, new Label("Follow this link to open the latest simulation report: "));
		this.setWidget(7,1, new Anchor("Report", "Sewer%20Replacement%20example_report.html", "_blank") );
		
		this.setWidget(8, 0, new Label("Follow this link to open the latest simulation trace: "));
		this.setWidget(8,1, new Anchor("Trace", "Sewer%20Replacement%20example_trace.html", "_blank") );
	}

	@Override
	public void onClick(ClickEvent event) 
	{
		ArrayList<Integer> resources = new ArrayList<Integer>();
		resources.add(Integer.parseInt(numBreaker_tb.getValue()));
		resources.add(Integer.parseInt(numExcavator_tb.getValue()));
		resources.add(Integer.parseInt(numCrane_tb.getValue()));
		resources.add(Integer.parseInt(numCrew_tb.getValue()));
		resources.add(Integer.parseInt(numRoller_tb.getValue()));
		resources.add(Integer.parseInt(numTruck_tb.getValue()));
		
		UtilityDSSServiceAsync service = GWT.create(UtilityDSSService.class);
		
		try 
		{
			service.simulate(resources, new AsyncCallback<String>()
			{
					public void onFailure(Throwable caught) 
					{
						// Show the RPC error message to the user
						Window.alert("Simulation did not work properly");
						
					}

					public void onSuccess(String result) 
					{
						Window.alert(result);
					}
			});
		}
		catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	private TextBox numBreaker_tb;
	private TextBox numExcavator_tb;
	private TextBox numCrane_tb;
	private TextBox numCrew_tb;
	private TextBox numRoller_tb;
	private TextBox numTruck_tb;

}
