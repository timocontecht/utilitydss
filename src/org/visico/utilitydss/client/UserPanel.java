package org.visico.utilitydss.client;

import java.util.ArrayList;

import org.visico.utilitydss.shared.Project;
import org.visico.utilitydss.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class UserPanel extends HorizontalPanel implements ClickHandler
{
	public UserPanel()
	{
		refresh();
	}
	
	private void refresh()
	{
		this.clear();
		drawLoginTable();
		drawProjectTable();
	}
	
	private void drawLoginTable()
	{
		loginTable = new FlexTable();
		
		User u = MainPanel.getInstance().getUser();
		if (u == null)
		{
			loginTable = new FlexTable();
			loginTable.setText(0, 0, "Username:");
		    userNameTB = new TextBox();
		    loginTable.setWidget(0, 1, userNameTB);
		    
		    loginTable.setText(1, 0, "Password:");
		    passwordTB = new PasswordTextBox();
		    loginTable.setWidget(1, 1, passwordTB);
		    
		    //rememberMeCB = new CheckBox("remember me");
		    //loginTable.setWidget(2, 1, rememberMeCB);
		    
		    final Button loginBtn = new Button("Login");
		    loginBtn.addClickHandler(this);
		    loginTable.setWidget(2, 1, loginBtn);
		}
		else
		{
			loginTable.setText(0,0, "loged in as");
			loginTable.setText(1,0, u.getName());
			
			Button logout_btn = new Button("Log out");
			logout_btn.addClickHandler(new ClickHandler()
			{

				public void onClick(ClickEvent event) 
				{
					MainPanel.getInstance().setUser(null);
					refresh();
				}
				
			});
			loginTable.setWidget(2,0, logout_btn);
			
		}
		add(loginTable);
	}
	
	private void drawProjectTable()
	{
		User u = MainPanel.getInstance().getUser();
		if (u != null)
		{
			projectTable = new FlexTable();
			
			UtilityDSSServiceAsync service = GWT.create(UtilityDSSService.class);
			
			try 
			{
				service.getProjects(MainPanel.getInstance().getUser(), new AsyncCallback<ArrayList<Project>>()
				{
						public void onFailure(Throwable caught) 
						{
							// Show the RPC error message to the user
							DialogBox dialogBox = new DialogBox();	
							dialogBox.setText("Remote Procedure Call - Failure");
							dialogBox.center();
						}
	
						public void onSuccess(ArrayList<Project> result) 
						{
							for (int i=0; i<result.size(); i++)
							{
								Project p = result.get(i);
								projectTable.setText(i,0, p.getName());
								projectTable.setText(i,1, p.getLocation());
								projectTable.setText(i,2, p.getDescription());
								projectTable.setWidget(i,3, new Button("Open"));
							}
						}
				});
				
				add(projectTable);
			} 
			catch (IllegalArgumentException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	private TextBox userNameTB;
	private PasswordTextBox passwordTB;
	private CheckBox rememberMeCB;
	private FlexTable loginTable;
	private FlexTable projectTable;
	
	
	
	@Override
	public void onClick(ClickEvent event) 
	{
		UtilityDSSServiceAsync service = GWT.create(UtilityDSSService.class);

		try 
		{
			service.login(userNameTB.getText(), passwordTB.getText(), new AsyncCallback<User>() 
			{
					public void onFailure(Throwable caught) 
					{
						// Show the RPC error message to the user
						DialogBox dialogBox = new DialogBox();	
						dialogBox.setText("Remote Procedure Call - Failure");
						dialogBox.center();
					}

					public void onSuccess(User result) 
					{
						if (result == null)
							Window.alert("Login failed. Please verify your user name and password!");
						else
						{
							MainPanel.getInstance().setUser(result);
							refresh();
						}
					}
			});
		} 
		catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
