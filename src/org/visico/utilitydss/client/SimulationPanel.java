package org.visico.utilitydss.client;

import java.util.ArrayList;

import org.visico.utilitydss.shared.Scenario;



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
		numSection_tb = new TextBox();
		numPut_tb = new TextBox();
		numBreaker_tb = new TextBox();
		numExcavator_tb = new TextBox();
		numCrane_tb = new TextBox();
		numCrew_tb = new TextBox();
		numConnectionCrew_tb = new TextBox();
		numRoller_tb = new TextBox();
		numTruck_tb = new TextBox();
		numPaveCrew_tb = new TextBox();
		numStonePaveCrew_tb = new TextBox();
		
		
		this.setWidget(0, 0, new Label("Number of Sections:"));
		this.setWidget(0, 1, numSection_tb);
		
		this.setWidget(1, 0, new Label("Number of Puts: "));
		this.setWidget(1, 1, numPut_tb);
		
		this.setWidget(2, 0, new Label("Number of Breakers: "));
		this.setWidget(2, 1, numBreaker_tb);
		
		this.setWidget(3, 0, new Label("Number of Excavators: "));
		this.setWidget(3, 1, numExcavator_tb);
		
		this.setWidget(4, 0, new Label("Number of Cranes: "));
		this.setWidget(4, 1, numCrane_tb);
		
		this.setWidget(5, 0, new Label("Number of Crews: "));
		this.setWidget(5, 1, numCrew_tb);
		
		this.setWidget(6, 0, new Label("Number of Connection Crews (if any): "));
		this.setWidget(6, 1, numConnectionCrew_tb);
		
		this.setWidget(7, 0, new Label("Number of Rollers: "));
		this.setWidget(7, 1, numRoller_tb);
		
		this.setWidget(8, 0, new Label("Number of Trucks: "));
		this.setWidget(8, 1, numTruck_tb);
		
		this.setWidget(9, 0, new Label("Number of Paving Crews: "));
		this.setWidget(9, 1, numPaveCrew_tb);
		
		this.setWidget(10, 0, new Label("Number of Stone Paving Crews: "));
		this.setWidget(10, 1, numStonePaveCrew_tb);
		
		Button simulate_btn = new Button("Simulate");
		simulate_btn.addClickHandler(this);
		this.setWidget(11, 0, simulate_btn);
		
		this.setWidget(12, 0, new Label("Follow this link to open the latest simulation report: "));
		this.setWidget(12,1, new Anchor("Report", "Sewer%20Replacement%20example_report.html", "_blank") );
		
		this.setWidget(13, 0, new Label("Follow this link to open the latest simulation trace: "));
		this.setWidget(13,1, new Anchor("Trace", "Sewer%20Replacement%20example_trace.html", "_blank") );
		
		this.setWidget(14, 0, new Label("Follow this link to open the latest Gantt chart: "));
		this.setWidget(14,1, new Anchor("Gantt Chart", "chart.jpg", "_blank") );
	}

	@Override
	public void onClick(ClickEvent event) 
	{
		
		Scenario scenario = new Scenario();
		scenario.setNUM_SEC(Integer.parseInt(numSection_tb.getValue()));
		scenario.setNUM_PUT(Integer.parseInt(numPut_tb.getValue()));
		scenario.setNUM_BREAKER(Integer.parseInt(numBreaker_tb.getValue()));
		scenario.setNUM_EXCAVATOR(Integer.parseInt(numExcavator_tb.getValue()));
		scenario.setNUM_CRANE(Integer.parseInt(numCrane_tb.getValue()));
		scenario.setNUM_CREW(Integer.parseInt(numCrew_tb.getValue()));
		scenario.setNUM_2NDCREW(Integer.parseInt(numConnectionCrew_tb.getValue()));
		scenario.setNUM_ROLLER(Integer.parseInt(numRoller_tb.getValue()));
		scenario.setNUM_TRUCK(Integer.parseInt(numTruck_tb.getValue()));
		scenario.setNUM_PAVECREWS(Integer.parseInt(numPaveCrew_tb.getValue()));
		scenario.setNUM_STONEPAVECREWS(Integer.parseInt(numStonePaveCrew_tb.getValue()));
		
		UtilityDSSServiceAsync service = GWT.create(UtilityDSSService.class);
		
		try 
		{
			service.simulate(scenario, new AsyncCallback<String>()
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
	
	
	private TextBox numSection_tb;
	private TextBox numPut_tb;
	private TextBox numBreaker_tb;
	private TextBox numExcavator_tb;
	private TextBox numCrane_tb;
	private TextBox numCrew_tb;
	private TextBox numConnectionCrew_tb;
	private TextBox numRoller_tb;
	private TextBox numTruck_tb;
	private TextBox numPaveCrew_tb;
	private TextBox numStonePaveCrew_tb;
	

}
