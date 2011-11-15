package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jotov.versia.orm.Product;
import com.jotov.versia.orm.Release;

public class ReleaseBean extends aDBbean {
	private UserSessionBean session;
	private List<Release> releases = new ArrayList<Release>();
	private int selectedRow;
	private String newReleaseName;

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return loadRelease();
		case 2:
			return newRelease();
		case 3:
			return saveRelease();
		default:
			return null;
		}
	}

//	public void openRelease() {
//		Release selectedRelease = this.releases.get(selectedRow);
//		session.setRelease(selectedRelease);
//		session.doOpenWorkspace();
//	}
	public String openRelease() {
		Release selectedRelease = this.releases.get(selectedRow);
		session.setRelease(selectedRelease);
		return "open_workspace";
	}

	private String saveRelease() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		Release selectedRelease = this.releases.get(selectedRow);
		em.persist(selectedRelease);
		trx.commit();

		return null;
	}

	private String newRelease() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		
		Release selectedRelease = this.releases.get(selectedRow);
		List<Release> presedors = new ArrayList<Release>();
		presedors.add(selectedRelease);
		List<Object> objArr = Release.createRelease(session.getProduct(),
				"New Release " + System.currentTimeMillis(), presedors, em);
		for (Object o : objArr) {
			em.persist(o);
		}
		
		trx.commit();
		return null;
	}

	@SuppressWarnings("unchecked")
	private String loadRelease() {
		releases.clear();
		if (session.getProduct() != null) {
			Query query = em
					.createQuery("select r from Release r where r.product=:prod ORDER BY r.releaseId");
			query.setParameter("prod", session.getProduct());
			List<Release> result = query.getResultList();
			releases.addAll(result);
		}
		return null;
	}

	public void createRelease() {
		dbean.executeQuery(this, 2);
	}

	public void updateRelease() {
		dbean.executeQuery(this, 3);
	}

	// GETTERS & SETTERS
	public List<Release> getReleases() {
		// releases = new ArrayList<Release>();
		// dbean.executeQuery(this, 1);
		releases.clear();
		Product p = session.getProduct();
		releases.addAll(p.getRealeses());
		return releases;
	}

	public void setReleases(List<Release> release) {
		this.releases = release;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getNewReleaseName() {
		return newReleaseName;
	}

	public void setNewReleaseName(String newReleaseName) {
		this.newReleaseName = newReleaseName;
	}

}
