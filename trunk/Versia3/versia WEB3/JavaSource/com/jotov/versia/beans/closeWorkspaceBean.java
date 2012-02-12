package com.jotov.versia.beans;

import com.jotov.versia.orm.UserProfile;

public class closeWorkspaceBean extends aDBbean {
	private UserSessionBean session;

	public String closeWorkspace() {
		return dbean.executeQuery(this);
	}

	@Override
	public String executeQuery() {
		UserProfile userProfile = session.getUserProfile();
		em.getTransaction().begin();
		
		userProfile.setOpenedWorkspace(null);
		session.getWorkspace().setOpenedByUser(null);
		
		em.persist(session.getUserProfile());
		em.getTransaction().commit();
		
		session.getOpenWsRegistry().unregister(session.getWorkspace());
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
