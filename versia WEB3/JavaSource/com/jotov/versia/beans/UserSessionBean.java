package com.jotov.versia.beans;

import com.jotov.versia.beans.vobj.Visibility;
import com.jotov.versia.orm.Product;
import com.jotov.versia.orm.Release;
import com.jotov.versia.orm.UserProfile;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class UserSessionBean extends aDBbean {
	private UserProfile userProfile;
	// private String currentPage = "/pages/00_welcome.xhtml";
	private Product product;
	private Release release;
	private WSpace workspace;
	private Visibility visibleVersions;
	private VObjectVersion selectedVersion;

	public UserSessionBean() {
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	// /////////////////////////////////////////
	public String closeProduct() {
		this.setProduct(null);
		return "open_product";
	}

	public String closeRelease() {
		this.setRelease(null);
		return "open_release";
	}

	public String closeWorkspace() {
		this.executeQuery(1);
		return "open_workspace";
	}

	// GETTERS & SETTERS
	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public Release getRelease() {
		return this.release;
	}

	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspace) {
		this.workspace = workspace;
	}

	public Visibility getVisibleVersions() {
		return visibleVersions;
	}

	public void setVisibleVersions(Visibility visibleVersions) {
		this.visibleVersions = visibleVersions;
	}

	public VObjectVersion getSelectedVersion() {
		return selectedVersion;
	}

	public void setSelectedVersion(VObjectVersion selectedVersion) {
		this.selectedVersion = selectedVersion;
	}

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return pCloseWorkspace();
		default:
			return null;
		}
	}

	private String pCloseWorkspace() {
		em.getTransaction().begin();
		workspace.setOpenedByUser(null);
		userProfile.setOpenedWorkspace(null);
		em.getTransaction().commit();
		return null;
	}

}