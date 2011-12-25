package com.jotov.versia.beans;

public class NavigationBean {
	private UserSessionBean session;

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public String manageProducts() {
		return "manage_products";
	}

	public String manageReleases() {
		if (Object.class.isInstance(session.getProduct()))
			return "manage_release";
		else
			return "not_selected_product";

	}

	public String manageWorkspaces() {
		if (Object.class.isInstance(session.getRelease()))
			return "manage_workspaces";
		else
			return "not_selected_release";
	}

	public String doneManage() {
		return "done";
	}

}
