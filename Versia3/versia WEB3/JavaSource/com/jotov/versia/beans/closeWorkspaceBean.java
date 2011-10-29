package com.jotov.versia.beans;

public class closeWorkspaceBean extends aDBbean {
	private UserSessionBean session;

	public String closeWorkspace() {
		return dbean.executeQuery(this);
	}

	@Override
	public String executeQuery() {
		em.getTransaction().begin();
		session.getUserProfile().setOpenedWorkspace(null);
		em.persist(session.getUserProfile());
		em.getTransaction().commit();
		session.setWorkspace(null);
		return "workspace_closed";
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

}
