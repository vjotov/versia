package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jotov.versia.orm.Release;
import com.jotov.versia.orm.UserProfile;
import com.jotov.versia.orm.WSpace;

public class WSListBean extends aDBbean {
	private UserSessionBean session;
	private List<WSpace> workspaces = new ArrayList<WSpace>();
	private int selectedWorkspaceId;
	private int selectedRow;

	public WSListBean() {
	}

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return loadWorspaces();
		case 2:
			return createOffspringWorkspace();
		case 3:
			return saveWorkspace();
		case 4:
			return doOpenWorkspace();
		default:
			return null;
		}
	}

	private String createOffspringWorkspace() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		// WSpace selectedWorkspace = this.workspaces.get(selectedRow);
		em.clear();
		WSpace selectedWorkspace = em.find(WSpace.class, selectedWorkspaceId);
		WSpace newOffspring = WSpace.createWorkspace("New Offspring Workspace",
				selectedWorkspace, null, em);
		em.persist(newOffspring);
		trx.commit();
		em.clear();
		//em.merge(session.getRelease());
		return null;
	}

	private String saveWorkspace() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		WSpace selectedWorkspace = this.workspaces.get(selectedRow);
		em.persist(selectedWorkspace);
		trx.commit();

		return null;
	}

	public void updateWorkspace() {
		dbean.executeQuery(this, 3);
	}

	public void creareOffspring() {
		dbean.executeQuery(this, 2);
	}

	public String openWorkspace() {
		return dbean.executeQuery(this, 4);
	}

	private String doOpenWorkspace() {
		WSpace selectedWorkspace = em.find(WSpace.class, selectedWorkspaceId);
		UserProfile user = session.getUserProfile();
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		UserProfile openUser = selectedWorkspace.getOpenedByUser();
		Boolean b2 = Object.class.isInstance(selectedWorkspace
				.getPublicationByUser());
		if ((openUser == null || user.equals(openUser)) && b2 == false) {
			user.setOpenedWorkspace(selectedWorkspace);
			em.persist(user);
			em.persist(selectedWorkspace);
			trx.commit();
			session.setWorkspace(selectedWorkspace);
			return "open_workspace";
		} else {
			trx.rollback();
			return "busy_workspace";
		}
	}

	@SuppressWarnings("unchecked")
	private String loadWorspaces() {
		Release release = session.getRelease();
		if (!em.contains(release)){
			Release r = em.find(Release.class, release.getReleaseId());
			session.setRelease(r);
		}
			
		if (session.getRelease() != null) {
			Query query = em
					.createQuery("select w from WSpace w where w.release=:rel ORDER BY w.wSpaceId");
			query.setParameter("rel", session.getRelease());
			List<WSpace> result = query.getResultList();
			workspaces.addAll(result);
		}
		return null;
	}

	public List<WSpace> getWorkspaces() {
		workspaces.clear();
		dbean.executeQuery(this, 1);
		return workspaces;
	}

	public void setWorkspaces(List<WSpace> workspaces) {
		this.workspaces = workspaces;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public Integer getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public int getSelectedWorkspaceId() {
		return selectedWorkspaceId;
	}

	public void setSelectedWorkspaceId(int selectedWorkspaceId) {
		this.selectedWorkspaceId = selectedWorkspaceId;
	}

}
