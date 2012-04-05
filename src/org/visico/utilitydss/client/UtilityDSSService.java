package org.visico.utilitydss.client;

import java.util.ArrayList;

import org.visico.utilitydss.shared.Project;
import org.visico.utilitydss.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("utilitydss")
public interface UtilityDSSService extends RemoteService 
{
	User login (String username, String password) throws IllegalArgumentException;
	ArrayList<Project> getProjects(User user) throws IllegalArgumentException;
}

