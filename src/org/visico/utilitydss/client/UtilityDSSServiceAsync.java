package org.visico.utilitydss.client;

import java.util.ArrayList;

import org.visico.utilitydss.shared.Project;
import org.visico.utilitydss.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface UtilityDSSServiceAsync 
{
	void login (String username, String password, AsyncCallback<User> callback) throws IllegalArgumentException;
	void getProjects(User user, AsyncCallback<ArrayList<Project>> callback) throws IllegalArgumentException;
}
