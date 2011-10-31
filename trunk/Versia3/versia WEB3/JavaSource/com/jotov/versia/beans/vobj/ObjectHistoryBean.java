package com.jotov.versia.beans.vobj;

import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;

public class ObjectHistoryBean extends aDBbean {
	private UserSessionBean session;

	public ObjectHistoryBean() {
	}

	public String showWorkspace() {
		return "show_workspace";
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

}
