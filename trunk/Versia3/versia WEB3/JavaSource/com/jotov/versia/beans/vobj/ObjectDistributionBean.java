package com.jotov.versia.beans.vobj;

import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;

public class ObjectDistributionBean extends aDBbean{
	private UserSessionBean session;
	
	public String showWorkspace() {
		System.out.println("show_workspace");
		return "show_workspace";
	}
	
	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}
}
