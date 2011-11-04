package com.jotov.versia.beans.vobj;

import java.util.List;

import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;

public class ObjectHistoryBean extends aDBbean {
	private UserSessionBean session;
private List<HistoryItem> historyItems;

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
